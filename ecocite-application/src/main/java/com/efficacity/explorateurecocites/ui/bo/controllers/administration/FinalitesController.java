/*
 * Explorateur Écocités
 * Copyright (C) 2019 l'État, ministère chargé du logement
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.
 *
 * You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.
 */

package com.efficacity.explorateurecocites.ui.bo.controllers.administration;

import com.efficacity.explorateurecocites.beans.model.Finalite;
import com.efficacity.explorateurecocites.beans.service.FinaliteService;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.FinaliteTableForm;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.validators.FinaliteTableFormValidator;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.ui.bo.service.TablePaginationService;
import com.efficacity.explorateurecocites.utils.ApplicationConstants;
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
@RequestMapping("/bo/administration/finalites")
public class FinalitesController {

    private static final Integer MODE_AFFICHAGE = 0;
    private static final Integer MODE_CREATION = 1;
    private static final Integer MODE_EDITION = 2;

    @Autowired
    FinaliteTableFormValidator finaliteTableFormValidator;

    @Autowired
    FinaliteService finaliteService;

    @Autowired
    MessageSourceService messageSourceService;

    @Autowired
    TablePaginationService tablePaginationService;

    private void fillCommon(Model model, Locale locale) {
        model.addAttribute("title", "Liste des finalités");
        model.addAttribute("currentMenu", "administration");
        model.addAttribute("currentTab", "finalites");
        model.addAttribute("objectType", "finalites");
        model.addAttribute("usesModal", true);
        model.addAttribute("modalUrl", "/bo/administration/finalites/edit/");
        model.addAttribute("canAddObject", true);
        model.addAttribute("canExportObject", false);
        model.addAttribute("deleteUrl", "/bo/administration/" +  "finalites" + "/");
        model.addAttribute("colonnes", tablePaginationService.processColumns(FinaliteTableForm.class, locale));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @GetMapping
    public String getList(Model model, Locale locale) {
        fillCommon(model, locale);
        List<FinaliteTableForm> objs = finaliteService.getList()
                .stream()
                .map(ExpBeanToLightBeanUtils::copyFrom)
                .collect(Collectors.toList());
        model.addAttribute("data", tablePaginationService.processData(objs, FinaliteTableForm.class));
        return "bo/listeEntites";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @GetMapping("/edit/{id}")
    public String getModalEdit(Model model, Locale locale, @PathVariable Long id) {
        if (id == -1) {
            return getModalCreate(model, locale);
        }
        fillCommon(model, locale);
        Finalite finalite = finaliteService.findById(id);
        model.addAttribute("titreModal", finalite.getLibelle());
        model.addAttribute("data", tablePaginationService.processSingleObject(
                ExpBeanToLightBeanUtils.copyFrom(finalite), locale)
        );
        model.addAttribute("typeModal", MODE_EDITION);
        model.addAttribute("updateUrl", "/bo/administration/finalites/" + id);
        model.addAttribute("deleteUrl", "/bo/administration/finalites/" + id);
        return "bo/administration/modal_edition_objet";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @GetMapping("/new")
    public String getModalCreate(Model model, Locale locale) {
        fillCommon(model, locale);
        model.addAttribute("titreModal", "Créer une finalité");
        model.addAttribute("data", tablePaginationService.processSingleObject(new FinaliteTableForm(), locale));
        model.addAttribute("typeModal", MODE_CREATION);
        model.addAttribute("createUrl", "/bo/administration/finalites/");
        return "bo/administration/modal_edition_objet";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteFinalite(Locale locale, @PathVariable Long id) {
        Map<String, String> mapErreur = new HashMap<>();
        Finalite finalite = finaliteService.findOne(id).orElse(null);
        if(CustomValidator.isEmpty(finalite)){
            mapErreur.put(ApplicationConstants.GENERAL_ERROR,
                    messageSourceService.getMessageSource().getMessage("error.attribut.unknown", null, locale));
        } else {
            try {
                String result = finaliteService.delete(id, locale);
                if(CustomValidator.isNotEmpty(result)){
                    mapErreur.put(ApplicationConstants.GENERAL_ERROR, result);
                }
            } catch (Exception e) {
                mapErreur.put(ApplicationConstants.GENERAL_ERROR,
                        messageSourceService.getMessageSource().getMessage(ApplicationConstants.ERROR_TECHNICAL, null, locale));
            }
        }
        return ResponseEntity.ok(mapErreur);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @PostMapping("/{id}")
    public ResponseEntity createFinalite(@RequestBody @Valid FinaliteTableForm finaliteTableForm, @PathVariable Long id) {
        Finalite finalite = finaliteTableForm.getFinalite();
        finalite.setId(id);
        finaliteService.save(finalite);
        return ResponseEntity.ok("");
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @PostMapping
    public ResponseEntity createFinalite(@RequestBody @Valid FinaliteTableForm finaliteTableForm) {
        Finalite finalite = finaliteService.createOne(finaliteTableForm);
        if (finalite.getId() != null) {
            return ResponseEntity.ok("");
        } else {
            return ResponseEntity.badRequest().body("");
        }
    }

    @InitBinder("finaliteTableForm")
    public void bindValidator(WebDataBinder binder) {
        binder.addValidators(finaliteTableFormValidator);
    }
}
