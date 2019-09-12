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

package com.efficacity.explorateurecocites.ui.bo.controllers.administration;

import com.efficacity.explorateurecocites.beans.model.Ingenierie;
import com.efficacity.explorateurecocites.beans.service.IngenierieService;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.IngenierieTableForm;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.validators.IngenierieTableFormValidator;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.ui.bo.service.TablePaginationService;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import com.efficacity.explorateurecocites.utils.ExpBeanToLightBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/bo/administration/ingenieries")
public class IngenierieController {

    private static final Integer MODE_AFFICHAGE = 0;
    private static final Integer MODE_CREATION = 1;
    private static final Integer MODE_EDITION = 2;

    @Autowired
    IngenierieTableFormValidator ingenierieTableFormValidator;

    @Autowired
    IngenierieService ingenierieService;

    @Autowired
    MessageSourceService messageSourceService;

    @Autowired
    TablePaginationService tablePaginationService;

    private void fillCommon(Model model, Locale locale) {
        model.addAttribute("title", "Liste des natures d'ingénierie");
        model.addAttribute("currentMenu", "administration");
        model.addAttribute("currentTab", "ingenieries");
        model.addAttribute("objectType", "ingenieries");
        model.addAttribute("usesModal", true);
        model.addAttribute("modalUrl", "/bo/administration/ingenieries/edit/");
        model.addAttribute("canAddObject", true);
        model.addAttribute("canExportObject", false);
        model.addAttribute("deleteUrl", "/bo/administration/" +  "ingenieries" + "/");
        model.addAttribute("colonnes", tablePaginationService.processColumns(IngenierieTableForm.class, locale));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @GetMapping
    public String getList(Model model, Locale locale) {
        fillCommon(model, locale);
        List<IngenierieTableForm> objs = ingenierieService.getList()
                .stream()
                .map(ExpBeanToLightBeanUtils::copyFrom)
                .collect(Collectors.toList());
        model.addAttribute("data", tablePaginationService.processData(objs, IngenierieTableForm.class));
        return "bo/listeEntites";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @GetMapping("/edit/{id}")
    public String getModalEdit(Model model, Locale locale, @PathVariable Long id) {
        if (id == -1) {
            return getModalCreate(model, locale);
        }
        fillCommon(model, locale);
        Ingenierie ingenierie = ingenierieService.findById(id);
        model.addAttribute("titreModal", ingenierie.getLibelle());
        model.addAttribute("data", tablePaginationService.processSingleObject(
                ExpBeanToLightBeanUtils.copyFrom(ingenierie), locale)
        );
        model.addAttribute("typeModal", MODE_EDITION);
        model.addAttribute("updateUrl", "/bo/administration/ingenieries/" + id);
        model.addAttribute("deleteUrl", "/bo/administration/ingenieries/" + id);
        return "bo/administration/modal_edition_objet";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @GetMapping("/new")
    public String getModalCreate(Model model, Locale locale) {
        fillCommon(model, locale);
        model.addAttribute("titreModal", "Créer une nature d'ingénierie");
        model.addAttribute("data", tablePaginationService.processSingleObject(new IngenierieTableForm(), locale));
        model.addAttribute("typeModal", MODE_CREATION);
        model.addAttribute("createUrl", "/bo/administration/ingenieries/");
        return "bo/administration/modal_edition_objet";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFinalite(Locale locale, @PathVariable Long id) {
        Map<String, String> mapErreur = new HashMap<>();
        Ingenierie ingenierie = ingenierieService.findOne(id).orElse(null);
        if(CustomValidator.isEmpty(ingenierie)){
            mapErreur.put("general_error",
                    messageSourceService.getMessageSource().getMessage("error.attribut.unknown", null, locale));
        } else {
            try {
                String result = ingenierieService.delete(id, locale);
                if(CustomValidator.isNotEmpty(result)){
                    mapErreur.put("general_error", result);
                }
            } catch (Exception e) {
                mapErreur.put("general_error",
                        messageSourceService.getMessageSource().getMessage("error.technical", null, locale));
            }
        }
        return ResponseEntity.ok(mapErreur);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @PostMapping("/{id}")
    public ResponseEntity<?> createFinalite(@RequestBody @Valid IngenierieTableForm ingenierieTableForm, @PathVariable Long id) {
        Ingenierie ingenierie = ingenierieTableForm.getIngenierie();
        ingenierie.setId(id);
        ingenierieService.save(ingenierie);
        return ResponseEntity.ok("");
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @PostMapping
    public ResponseEntity<?> createFinalite(@RequestBody @Valid IngenierieTableForm ingenierieTableForm) {
        Ingenierie ingenierie = ingenierieService.createOne(ingenierieTableForm);
        if (ingenierie.getId() != null) {
            return ResponseEntity.ok("");
        } else {
            return ResponseEntity.badRequest().body("");
        }
    }

    @InitBinder("finaliteTableForm")
    public void bindValidator(WebDataBinder binder) {
        binder.addValidators(ingenierieTableFormValidator);
    }
}
