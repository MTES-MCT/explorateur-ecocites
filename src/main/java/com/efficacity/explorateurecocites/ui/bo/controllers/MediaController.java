/*
 * Explorateur Écocités
 * Copyright (C) 2019 l'État, ministère chargé du logement
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.efficacity.explorateurecocites.ui.bo.controllers;

import com.efficacity.explorateurecocites.beans.service.MediaModificationService;
import com.efficacity.explorateurecocites.beans.service.MediaService;
import com.efficacity.explorateurecocites.ui.bo.forms.MediaForm;
import com.efficacity.explorateurecocites.ui.bo.forms.MediaFormFromFile;
import com.efficacity.explorateurecocites.ui.bo.forms.MediaFromAjarisForm;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Controller
public class MediaController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MediaController.class);
    public static final String BASE_PUBLIC_MEDIA_URL = "/ajaris/images/";

    @Value(value = "classpath:img/pas_dimage_disponible.jpg")
    private Resource placeHolder;

    protected MediaService mediaService;

    protected MediaModificationService mediaModificationService;

    protected MessageSourceService messageSourceService;

    @Autowired
    public MediaController(final MediaService mediaService, final MediaModificationService mediaModificationService, final MessageSourceService messageSourceService) {
        this.mediaService = mediaService;
        this.mediaModificationService = mediaModificationService;
        this.messageSourceService = messageSourceService;
    }

    @GetMapping("/bo/ajaris/modal/create/{type}/{level}/{idObject}")
    public String getModalAddMedia(@PathVariable String type, @PathVariable Integer level, @PathVariable Long idObject, Model model) {
        if (mediaService.isEnabled()) {
            model.addAttribute("typeModal", type);
            model.addAttribute("level", level);
            model.addAttribute("idObject", idObject);
            return "/bo/modal/media/modal_ajout_image";
        }
        return "/bo/modal/media/modal_ajaris_disabled";
    }

    @GetMapping("/bo/ajaris/modal/edit/{type}/{idObject}/{idMedia}")
    public String getModalEditMedia(@PathVariable String type, @PathVariable Long idObject, @PathVariable Long idMedia, Model model) {
        if (mediaService.isEnabled()) {
            try {
                model.addAttribute("typeModal", type);
                model.addAttribute("idObject", idObject);
                model.addAttribute("idMedia", idMedia);
                MediaForm media = mediaService.getMediaFormById(idMedia);
                if (media == null) {
                    LOGGER.warn("Cannot open image with ID : " + idMedia);
                    return "/bo/modal/media/modal_ajaris_unavailable";
                }
                model.addAttribute("media", media);
                return "/bo/modal/media/modal_modif_image";
            } catch (Exception e) {
                LOGGER.warn("Cannot open image with ID : " + idMedia);
                return "/bo/modal/media/modal_ajaris_unavailable";
            }
        }
        return "/bo/modal/media/modal_ajaris_disabled";
    }

    @PostMapping("/bo/ajaris/createId/{type}/{level}/{idObject}")
    public ResponseEntity createMedia(@PathVariable String type, @PathVariable Integer level, @PathVariable Long idObject, @Valid @RequestBody MediaFromAjarisForm form, BindingResult bindingResult, Locale locale) {
        if (form != null && !bindingResult.hasErrors()) {
            try {
                MediaForm mediaForm = mediaService.createById(bindingResult, locale, messageSourceService, form.getIdAjaris(), type, level, idObject);
                if (mediaForm != null) {
                    return ResponseEntity.ok(mediaForm);
                }
                if (!bindingResult.hasErrors()) {
                    bindingResult.reject("error.technical", messageSourceService.getMessageSource().getMessage("error.technical", null, locale));
                }
            } catch (DataIntegrityViolationException exception) {
                bindingResult.reject("error.media.existing.id", messageSourceService.getMessageSource().getMessage("error.media.existing.id", null, locale));
            }
            catch (Exception exception) {
                LOGGER.info("Error when creating image from ajaris ID", exception);
                bindingResult.reject("error.technical", messageSourceService.getMessageSource().getMessage("error.technical", null, locale));
            }
        }
        return ResponseEntity.badRequest().body(com.efficacity.explorateurecocites.utils.ValidationErrorBuilder.fromBindingErrorsIgnoreValue(bindingResult));
    }

    @PostMapping(value = "/bo/ajaris/createFile/{type}/{level}/{idObject}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity createMedia(MediaFormFromFile form, BindingResult bindingResult, @PathVariable String type, @PathVariable Integer level, @PathVariable Long idObject, Locale locale) {
        if (form == null || bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(com.efficacity.explorateurecocites.utils.ValidationErrorBuilder.fromBindingErrorsIgnoreValue(bindingResult));
        } else {
            return ResponseEntity.ok(mediaService.uploadImage(bindingResult, messageSourceService, locale, form, type, level, idObject));
        }
    }

    @PostMapping("/bo/ajaris/edit/{idMedia}")
    public ResponseEntity editMedia(@PathVariable Long idMedia, @Valid @RequestBody MediaForm form, BindingResult bindingResult) {
        if (form == null || bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(com.efficacity.explorateurecocites.utils.ValidationErrorBuilder.fromBindingErrorsIgnoreValue(bindingResult));
        } else {
            return ResponseEntity.ok(mediaService.updateImage(form, idMedia));
        }
    }

    @DeleteMapping("/bo/ajaris/edit/{idMedia}")
    public ResponseEntity delete(@PathVariable Long idMedia) {
        LOGGER.info("DELETE Media" + idMedia);
//        mediaService.deleteMedia(idMedia);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/ajaris/images/{idMedia}")
    public ResponseEntity<StreamingResponseBody> stream(@PathVariable("idMedia") Long idMedia) throws IOException {
        InputStream input = null;
        HttpHeaders headers = new HttpHeaders();
        if (mediaService.isEnabled()) {
            String urlImgAjaris = mediaService.getUrlForIdMedia(idMedia);
            try {
                URL url = new URL(urlImgAjaris);
                try {
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    if (connection.getResponseCode() < 300 && connection.getResponseCode() >= 200) {
                        Map<String, List<String>> requestHeaders = connection.getHeaderFields();
                        if (requestHeaders != null) {
                            requestHeaders.forEach((s, strings) -> {
                                if (s != null && strings != null && !strings.isEmpty()) {
                                    headers.put(s, strings);
                                }
                            });
                        }
                        input = connection.getInputStream();
                    }
                } catch (IOException e) {
                    LOGGER.info("IOException was thrown when creating stream");
                    LOGGER.info("Exception: ", e);
                    return new ResponseEntity<>(OutputStream::flush, headers, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } catch (MalformedURLException e) {
                String errorMsg = "Problem with the URL of the image on Ajaris : " + urlImgAjaris;
                LOGGER.info(errorMsg);
                LOGGER.info("Exception: ", e);
                return new ResponseEntity<>(OutputStream::flush, headers, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            input = placeHolder.getInputStream();
        }
        if (input != null) {
            final InputStream finalInput = input;
            return new ResponseEntity<>(os -> {
                try {
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = finalInput.read(buffer)) != -1) {
                        os.write(buffer, 0, len);
                    }
                    os.flush();
                } catch (IOException e) {
                    LOGGER.info("IOException was thrown when reading stream");
                    LOGGER.info("Exception: ", e);
                    throw e;
                } finally {
                    finalInput.close();
                }
            }, headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(OutputStream::flush, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/bo/administration/terra")
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAuthority('ROLE_ADMIN_TECH')")
    public String getPageGestionTerra(Model model) {
        model.addAttribute("jobQueue", mediaModificationService.findAllJobs());
        model.addAttribute("currentMenu", "administration");
        model.addAttribute("currentTab", "ajaris");
        return "bo/administration/gestion_terra";
    }

    @PostMapping("/bo/administration/terra/job/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAuthority('ROLE_ADMIN_TECH')")
    public String startJobById(@PathVariable("id") Long id) {
        mediaModificationService.startJob(id);
        return "redirect:/bo/administration/terra";
    }

    @PostMapping("/bo/administration/terra/job/delete/{id}")
    @DeleteMapping("/bo/administration/terra/job/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAuthority('ROLE_ADMIN_TECH')")
    public String deleteJobById(@PathVariable("id") Long id) {
        mediaModificationService.deleteJob(id);
        return "redirect:/bo/administration/terra";
    }

    @GetMapping("/bo/administration/terra/migration")
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAuthority('ROLE_ADMIN_TECH')")
    public String getPageMigration(Model model) {
        model.addAttribute("numberImages", mediaService.imagesToMigrate().size());
        return "bo/administration/migration_terra";
    }

    @PostMapping("/bo/administration/terra/migration")
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAuthority('ROLE_ADMIN_TECH')")
    public String doMigration() {
        CompletableFuture.supplyAsync(mediaService::migrate);
        return "redirect:/bo/administration/terra/migration";
    }

    @PostMapping("/bo/administration/terra/migration/one")
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAuthority('ROLE_ADMIN_TECH')")
    public String doOneMigration() {
        CompletableFuture.supplyAsync(mediaService::migrateOne);
        return "redirect:/bo/administration/terra/migration";
    }
}
