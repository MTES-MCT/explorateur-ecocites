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

import com.efficacity.explorateurecocites.beans.biz.json.ImportResponse;
import com.efficacity.explorateurecocites.beans.biz.table.BusinessTable;
import com.efficacity.explorateurecocites.beans.model.Business;
import com.efficacity.explorateurecocites.beans.service.ActionService;
import com.efficacity.explorateurecocites.beans.service.BusinessService;
import com.efficacity.explorateurecocites.ui.bo.forms.FileUploadForm;
import com.efficacity.explorateurecocites.ui.bo.forms.ListeFiltrageForm;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.BusinessForm;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.helpers.SelectOption;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.validators.BusinessFormValidator;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.ui.bo.service.RightUserService;
import com.efficacity.explorateurecocites.ui.bo.service.TablePaginationService;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import com.efficacity.explorateurecocites.utils.ExpBeanToLightBeanUtils;
import com.efficacity.explorateurecocites.utils.enumeration.STATUT_FINANCIER_AFFAIRE;
import com.efficacity.explorateurecocites.utils.log.LoggingUtils;
import isotope.modules.security.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.utils.log.LoggingUtils.ActionType.CREATION;
import static com.efficacity.explorateurecocites.utils.log.LoggingUtils.ActionType.MODIFICATION;
import static com.efficacity.explorateurecocites.utils.log.LoggingUtils.ActionType.SUPPRESSION;

@Controller
@RequestMapping("/bo/administration/business")
public class BusinessController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessController.class);


    private static final Integer MODE_AFFICHAGE = 0;
    private static final Integer MODE_CREATION = 1;
    private static final Integer MODE_EDITION = 2;

    public static final String TYPE = "business";

    @Autowired
    BusinessFormValidator businessFormValidator;

    @Autowired
    BusinessService businessService;

    @Autowired
    MessageSourceService messageSourceService;

    @Autowired
    TablePaginationService tablePaginationService;

    @Autowired
    ActionService actionService;
    @Autowired
    RightUserService rightUserService;

    private void fillFiltre(Model model, Locale locale) {
        HashMap<String, List<Object>> filtres = new LinkedHashMap<>();
        filtres.put("nom", new LinkedList<>(Arrays.asList("Nom", "text", null)));
        filtres.put("ecocite", new LinkedList<>(Arrays.asList("ÉcoCité", "text", null)));
        filtres.put("statutFinancier", new LinkedList<>(Arrays.asList("Statut financier", "select", STATUT_FINANCIER_AFFAIRE.toMap())));
        filtres.put("numeroAffaire", new LinkedList<>(Arrays.asList("Numéro d'Affaire", "text", null)));
        model.addAttribute("filtres", filtres);
        model.addAttribute("urlFilter", "/bo/administration/" + TYPE + "/filtrer");
    }

    private void fillCommon(Model model, Locale locale) {
        model.addAttribute("title", "Listes des affaires");
        model.addAttribute("currentMenu", "administration");
        model.addAttribute("currentTab", TYPE);
        model.addAttribute("objectType", TYPE);
        model.addAttribute("usesModal", true);
        model.addAttribute("modalUrl", "/bo/administration/" + TYPE + "/edit/");
        model.addAttribute("canAddObject", rightUserService.isAdmin(model));
        model.addAttribute("canExportObject", rightUserService.isAdmin(model));
        model.addAttribute("deleteUrl", "/bo/administration/" +  TYPE + "/");
        model.addAttribute("hasUpload", rightUserService.isAdmin(model));
        model.addAttribute("uploadUrl", "/bo/administration/" +  TYPE + "/upload");
        model.addAttribute("importModalTitle", "Importer un fichier d'affaires de la base LAGON");
        model.addAttribute("importFormat", ".xlsx");
        model.addAttribute("colonnes", tablePaginationService.processColumns(BusinessTable.class, locale));
    }


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    public ResponseEntity<?> uploadBusinessFile(FileUploadForm form, BindingResult bindingResult, Locale locale) {
        ImportResponse result = businessService.uploadFiles(form.getFile(), bindingResult, locale);
        if (result == null || result.failed() || bindingResult.hasErrors()) {
            return ResponseEntity.unprocessableEntity().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ACCOMPAGNATEUR')")
    public String getList(Model model, Locale locale, @AuthenticationPrincipal JwtUser user) {
        fillFiltre(model, locale);
        fillCommon(model, locale);
        List<BusinessTable> objs = businessService.getList()
                .stream()
                .map(b -> ExpBeanToLightBeanUtils.copyFrom(b, b.getIdAction() == null ? null : actionService.findById(b.getIdAction())))
                .collect(Collectors.toList());
        model.addAttribute("data", tablePaginationService.processData(objs, BusinessTable.class));
        return "bo/listeEntites";
    }

    @PostMapping(value="/filtrer", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String soumissionFiltrer(ListeFiltrageForm filtres, Model model,
                                    BindingResult result, Locale locale) {
        fillCommon(model, locale);
        fillFiltre(model, locale);
        model.addAttribute("selectedFiltres", filtres.getFiltres());
        List<BusinessTable> objs = businessService.getListFiltre(filtres.getFiltres())
                .stream()
                .map(b -> ExpBeanToLightBeanUtils.copyFrom(b, b.getIdAction() == null ? null : actionService.findById(b.getIdAction())))
                .collect(Collectors.toList());
        model.addAttribute("data", tablePaginationService.processData(objs, BusinessTable.class));

        return "bo/pagination/liste-pagination-tableau";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAuthority('ROLE_ACCOMPAGNATEUR')")
    @GetMapping("/edit/{id}")
    public String getModalEdit(Model model, Locale locale, @PathVariable Long id) {
        if (id == -1) {
            return getModalCreate(model, locale);
        }
        fillCommon(model, locale);
        Optional<Business> business = businessService.findOne(id);
        if (business.isPresent()) {
            model.addAttribute("titreModal", business.get().getNom());
            List<SelectOption> actions = actionService.findAllAction().stream().map(a -> new SelectOption("" + a.getId(), a.getNomPublic())).collect(Collectors.toList());
            model.addAttribute("data", tablePaginationService.processSingleObject(ExpBeanToLightBeanUtils.copyFrom(business.get(), actions), locale));
            if(rightUserService.isAdmin(model)){
                model.addAttribute("typeModal", MODE_EDITION);
            }
            else{
                model.addAttribute("typeModal", MODE_AFFICHAGE);
            }
            model.addAttribute("updateUrl", "/bo/administration/" + TYPE + "/" + id);
            model.addAttribute("deleteUrl", "/bo/administration/" + TYPE + "/" + id);
            return "bo/administration/modal_edition_objet";
        }
        model.addAttribute("message", "L'objet demandé n'existe pas");
        model.addAttribute("idTitre", "Erreur lors de l'affichage de la modal");
        return "bo/modal/error_modal";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @GetMapping("/new")
    public String getModalCreate(Model model, Locale locale) {
        fillCommon(model, locale);
        BusinessForm businessForm = new BusinessForm();
        model.addAttribute("titreModal", "Créer une affaire");
        businessForm.setIdActionDefaults(actionService.findAllActionOrderedByName().stream().map(a -> new SelectOption("" + a.getId(), a.getNomPublic())).collect(Collectors.toList()));
        model.addAttribute("data", tablePaginationService.processSingleObject(businessForm, locale));
        model.addAttribute("typeModal", MODE_CREATION);
        model.addAttribute("createUrl", "/bo/administration/" + TYPE + "/");
        return "bo/administration/modal_edition_objet";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(Locale locale, @PathVariable Long id, Model model) {
        Map<String, String> mapErreur = new HashMap<>();
        Business business = businessService.findOne(id).orElse(null);
        if(CustomValidator.isEmpty(business)){
            mapErreur.put("general_error", messageSourceService.getMessageSource().getMessage("error.attribut.unknown", null, locale));
        } else {
            try {
                businessService.delete(id);
                LoggingUtils.logAction(LOGGER, SUPPRESSION, business, (JwtUser) model.asMap().getOrDefault("user", null));
            } catch (Exception e) {
                mapErreur.put("general_error", messageSourceService.getMessageSource().getMessage("error.technical", null, locale));
            }
        }
        return ResponseEntity.ok(mapErreur);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @PostMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid BusinessForm tableform, @PathVariable Long id, Model model) {
        Business business = businessService.update(id, tableform, (JwtUser) model.asMap().get("user"));
        LoggingUtils.logAction(LOGGER, MODIFICATION, business, (JwtUser) model.asMap().getOrDefault("user", null));
        return ResponseEntity.ok("");
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid BusinessForm tableform, Model model) {
        Business business = businessService.createOne(tableform, (JwtUser) model.asMap().get("user"));
        LoggingUtils.logAction(LOGGER, CREATION, business, (JwtUser) model.asMap().getOrDefault("user", null));
        if (business.getId() != null) {
            return ResponseEntity.ok("");
        } else {
            return ResponseEntity.badRequest().body("");
        }
    }

    @InitBinder("businessForm")
    public void bindValidator(WebDataBinder binder) {
        binder.addValidators(businessFormValidator);
    }
}
