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

import com.efficacity.explorateurecocites.beans.model.QuestionsEvaluation;
import com.efficacity.explorateurecocites.beans.service.QuestionsEvaluationService;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.EtiquetteIngenierieForm;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.InnovationForm;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/bo/administration/innovations")
public class QuestionInnovationController {

    private static final Integer MODE_AFFICHAGE = 0;
    private static final Integer MODE_CREATION = 1;
    private static final Integer MODE_EDITION = 2;

    public static final String TYPE = "innovations";

    @Autowired
    QuestionsEvaluationService questionsEvaluationService;

    @Autowired
    MessageSourceService messageSourceService;

    @Autowired
    TablePaginationService tablePaginationService;

    private void fillCommon(Model model, Locale locale) {
        model.addAttribute("title", "Liste des natures d'innovation");
        model.addAttribute("currentMenu", "administration");
        model.addAttribute("currentTab", TYPE);
        model.addAttribute("objectType", TYPE);
        model.addAttribute("usesModal", true);
        model.addAttribute("modalUrl", "/bo/administration/" + TYPE + "/edit/");
        model.addAttribute("canAddObject", true);
        model.addAttribute("canExportObject", false);
        model.addAttribute("deleteUrl", "/bo/administration/" +  TYPE + "/");
        model.addAttribute("colonnes", tablePaginationService.processColumns(InnovationForm.class, locale));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @GetMapping
    public String getList(Model model, Locale locale) {
        fillCommon(model, locale);
        List<InnovationForm> objs = questionsEvaluationService.getList()
                .stream()
                .map(ExpBeanToLightBeanUtils::copyFrom)
                .collect(Collectors.toList());
        model.addAttribute("data", tablePaginationService.processData(objs, InnovationForm.class));
        return "bo/listeEntites";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @GetMapping("/edit/{id}")
    public String getModalEdit(Model model, Locale locale, @PathVariable Long id) {
        if (id == -1) {
            return getModalCreate(model, locale);
        }
        fillCommon(model, locale);
        Optional<QuestionsEvaluation> questionsEvaluation = questionsEvaluationService.findOne(id);
        if (questionsEvaluation.isPresent()) {
            model.addAttribute("titreModal", questionsEvaluation.get().getTitre());
            model.addAttribute("data", tablePaginationService.processSingleObject(ExpBeanToLightBeanUtils.copyFrom(questionsEvaluation.get()), locale));
            model.addAttribute("typeModal", MODE_EDITION);
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
        EtiquetteIngenierieForm etiquetteIngenierieForm = new EtiquetteIngenierieForm();
        model.addAttribute("titreModal", "Créer une nature d'innovation");
        model.addAttribute("data", tablePaginationService.processSingleObject(etiquetteIngenierieForm, locale));
        model.addAttribute("typeModal", MODE_CREATION);
        model.addAttribute("createUrl", "/bo/administration/" + TYPE + "/");
        return "bo/administration/modal_edition_objet";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(Locale locale, @PathVariable Long id) {
        Map<String, String> mapErreur = new HashMap<>();
        QuestionsEvaluation questionsEvaluation = questionsEvaluationService.findOne(id).orElse(null);
        if(CustomValidator.isEmpty(questionsEvaluation)){
            mapErreur.put(ApplicationConstants.GENERAL_ERROR, messageSourceService.getMessageSource().getMessage("error.attribut.unknown", null, locale));
        } else {
            try {
                String result = questionsEvaluationService.delete(id, locale);
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
    public ResponseEntity update(@RequestBody @Valid InnovationForm tableform, @PathVariable Long id) {
        questionsEvaluationService.update(id, tableform);
        return ResponseEntity.ok("");
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @PostMapping
    public ResponseEntity create(@RequestBody @Valid InnovationForm tableform) {
        QuestionsEvaluation questionsEvaluation = questionsEvaluationService.createOne(tableform);
        if (questionsEvaluation.getId() != null) {
            return ResponseEntity.ok("");
        } else {
            return ResponseEntity.badRequest().body("");
        }
    }
}
