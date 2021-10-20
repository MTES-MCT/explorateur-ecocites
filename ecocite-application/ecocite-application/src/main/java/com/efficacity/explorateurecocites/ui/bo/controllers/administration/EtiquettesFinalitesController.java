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

import com.efficacity.explorateurecocites.beans.biz.table.EtiquetteFinaliteTable;
import com.efficacity.explorateurecocites.beans.model.EtiquetteFinalite;
import com.efficacity.explorateurecocites.beans.model.Finalite;
import com.efficacity.explorateurecocites.beans.service.EtiquetteFinaliteService;
import com.efficacity.explorateurecocites.beans.service.FinaliteService;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.EtiquetteFinaliteForm;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.helpers.SelectOption;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.ui.bo.service.TablePaginationService;
import com.efficacity.explorateurecocites.utils.ApplicationConstants;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import com.efficacity.explorateurecocites.utils.ExpBeanToLightBeanUtils;
import isotope.commons.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/bo/administration/objectifs")
public class EtiquettesFinalitesController {

    private static final Integer MODE_AFFICHAGE = 0;
    private static final Integer MODE_CREATION = 1;
    private static final Integer MODE_EDITION = 2;

    public static final String TYPE = "objectifs";

    @Autowired
    EtiquetteFinaliteService etiquetteFinaliteService;

    @Autowired
    MessageSourceService messageSourceService;

    @Autowired
    TablePaginationService tablePaginationService;

    @Autowired
    FinaliteService finaliteService;

    private void fillCommon(Model model, Locale locale) {
        model.addAttribute("title", "Liste des étiquettes objectifs de la ville durable");
        model.addAttribute("currentMenu", "administration");
        model.addAttribute("currentTab", TYPE);
        model.addAttribute("objectType", TYPE);
        model.addAttribute("usesModal", true);
        model.addAttribute("modalUrl", "/bo/administration/" + TYPE + "/edit/");
        model.addAttribute("canAddObject", true);
        model.addAttribute("canExportObject", false);
        model.addAttribute("deleteUrl", "/bo/administration/" +  TYPE + "/");
        model.addAttribute("colonnes", tablePaginationService.processColumns(EtiquetteFinaliteTable.class, locale));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @GetMapping
    public String getList(Model model, Locale locale) {
        fillCommon(model, locale);
        List<Finalite> finaliteList = finaliteService.getList();
        List<EtiquetteFinaliteTable> objs = etiquetteFinaliteService.getList()
                .stream()
                .map(ea -> ExpBeanToLightBeanUtils.copyFrom(ea, finaliteList.stream().filter(a -> Objects.equals(ea.getIdFinalite(), a.getId())).findFirst().orElseThrow(NotFoundException::new)))
                .collect(Collectors.toList());
        model.addAttribute("data", tablePaginationService.processData(objs, EtiquetteFinaliteTable.class));
        return "bo/listeEntites";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @GetMapping("/edit/{id}")
    public String getModalEdit(Model model, Locale locale, @PathVariable Long id) {
        if (id == -1) {
            return getModalCreate(model, locale);
        }
        fillCommon(model, locale);
        EtiquetteFinalite etiquetteFinalite = etiquetteFinaliteService.findById(id);
        model.addAttribute("titreModal", etiquetteFinalite.getLibelle());
        List<SelectOption> selectOptions = finaliteService.getList().stream().map(a -> new SelectOption(String.valueOf(a.getId()), a.getLibelle())).collect(Collectors.toList());
        model.addAttribute("data", tablePaginationService.processSingleObject(ExpBeanToLightBeanUtils.copyFrom(etiquetteFinalite, selectOptions), locale));
        model.addAttribute("typeModal", MODE_EDITION);
        model.addAttribute("updateUrl", "/bo/administration/" + TYPE + "/" + id);
        model.addAttribute("deleteUrl", "/bo/administration/" + TYPE + "/" + id);
        return "bo/administration/modal_edition_objet";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @GetMapping("/new")
    public String getModalCreate(Model model, Locale locale) {
        fillCommon(model, locale);
        EtiquetteFinaliteForm etiquetteFinaliteForm = new EtiquetteFinaliteForm();
        model.addAttribute("titreModal", "Créer un objectif");
        etiquetteFinaliteForm.setIdFinaliteDefaults(finaliteService.getList().stream().map(a -> new SelectOption(String.valueOf(a.getId()), a.getLibelle())).collect(Collectors.toList()));
        model.addAttribute("data", tablePaginationService.processSingleObject(etiquetteFinaliteForm, locale));
        model.addAttribute("typeModal", MODE_CREATION);
        model.addAttribute("createUrl", "/bo/administration/" + TYPE + "/");
        return "bo/administration/modal_edition_objet";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(Locale locale, @PathVariable Long id) {
        Map<String, String> mapErreur = new HashMap<>();
        EtiquetteFinalite etiquetteFinalite = etiquetteFinaliteService.findOne(id).orElse(null);
        if(CustomValidator.isEmpty(etiquetteFinalite)){
            mapErreur.put(ApplicationConstants.GENERAL_ERROR, messageSourceService.getMessageSource().getMessage("error.attribut.unknown", null, locale));
        } else {
            try {
                String result = etiquetteFinaliteService.delete(id, locale);
                if(CustomValidator.isNotEmpty(result)){
                    mapErreur.put(ApplicationConstants.GENERAL_ERROR, result);
                }
            } catch (Exception e) {
                mapErreur.put(ApplicationConstants.GENERAL_ERROR, messageSourceService.getMessageSource().getMessage(ApplicationConstants.ERROR_TECHNICAL, null, locale));
            }
        }
        return ResponseEntity.ok(mapErreur);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @PostMapping("/{id}")
    public ResponseEntity update(@RequestBody @Valid EtiquetteFinaliteForm tableform, @PathVariable Long id) {
        etiquetteFinaliteService.update(id, tableform);
        return ResponseEntity.ok("");
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @PostMapping
    public ResponseEntity create(@RequestBody @Valid EtiquetteFinaliteForm tableform) {
        EtiquetteFinalite etiquetteFinalite = etiquetteFinaliteService.createOne(tableform);
        if (etiquetteFinalite.getId() != null) {
            return ResponseEntity.ok("");
        } else {
            return ResponseEntity.badRequest().body("");
        }
    }
}
