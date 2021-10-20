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

package com.efficacity.explorateurecocites.ui.bo.controllers.action.onglets;

import com.efficacity.explorateurecocites.beans.biz.ActionBean;
import com.efficacity.explorateurecocites.beans.biz.EtapeBean;
import com.efficacity.explorateurecocites.beans.biz.QuestionQuestionnaireEvaluationBean;
import com.efficacity.explorateurecocites.beans.service.ActionService;
import com.efficacity.explorateurecocites.beans.service.EtapeService;
import com.efficacity.explorateurecocites.ui.bo.forms.QuestionnaireReponseForm;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.ui.bo.service.QuestionnaireEvaluationService;
import com.efficacity.explorateurecocites.ui.bo.service.RightUserService;
import com.efficacity.explorateurecocites.ui.bo.service.notifications.EmailNotificationService;
import com.efficacity.explorateurecocites.utils.enumeration.*;
import com.efficacity.explorateurecocites.utils.log.LoggingUtils;
import isotope.commons.validation.ValidationErrorBuilder;
import isotope.modules.security.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;

@Controller
@RequestMapping("bo/actions")
public class ActionQuestionnaireController {

    public static final String CAN_CONSULTE = "canConsulte";
    public static final String CAN_VALIDE = "canValide";
    public static final String ERROR_USER_RIGHT = "error.user.right";
    public static final String ETAPE_DISABLED = "etapeDisabled";
    public static final String CAN_EDIT = "canEdit";

    private static final Logger LOGGER = LoggerFactory.getLogger(ActionQuestionnaireController.class);

    @Autowired
    EmailNotificationService emailNotificationService;

    private final ActionService actionService;

    private final EtapeService etapeService;

    private final RightUserService rightUserService;

    private final QuestionnaireEvaluationService questionnaireEvaluationService;

    private final  MessageSourceService messageSourceService;

    public ActionQuestionnaireController(final MessageSourceService messageSourceService, final RightUserService rightUserService, final ActionService actionService, final EtapeService etapeService, final QuestionnaireEvaluationService questionnaireEvaluationService) {
        this.messageSourceService = messageSourceService;
        this.rightUserService = rightUserService;
        this.actionService = actionService;
        this.etapeService = etapeService;
        this.questionnaireEvaluationService = questionnaireEvaluationService;
    }

    @GetMapping("questionnaire/{actionId}/{codeQuestionnaire}")
    public String getActionQuestionnaireView(Model model, @PathVariable Long actionId, @PathVariable String codeQuestionnaire) {

        gestionDroit(model, actionId);

        if(model.containsAttribute(CAN_CONSULTE) && !(boolean) model.asMap().get(CAN_CONSULTE)){
            return "bo/components/errorAccess";
        }

        LinkedHashMap<String, LinkedList<QuestionQuestionnaireEvaluationBean>> questionnaire = questionnaireEvaluationService.genererQuestionnaire(codeQuestionnaire, actionId, TYPE_OBJET.ACTION.getCode());
        model.addAttribute("objetId", actionId);
        model.addAttribute("objetType", TYPE_OBJET.ACTION.getCode());
        model.addAttribute("codeQuestionnaire", codeQuestionnaire);
        model.addAttribute("questionnaire", questionnaire);

        return "bo/questionnaire/questionnaire";
    }

    @PostMapping(value="questionnaire/validation", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity validationQuestionnaire(QuestionnaireReponseForm questionnaireReponseForm, Model model,
                                                     BindingResult result, Locale locale) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        Long actionId = Long.valueOf(questionnaireReponseForm.getObjetId());
        gestionDroit(model, actionId);
        if(model.containsAttribute(CAN_VALIDE) && (boolean) model.asMap().get(CAN_VALIDE)){
            ActionBean action = actionService.findOneAction(actionId);
            // La sauvegarde auto ayant déjà tout sauvegardé en base, on a pas besoin de le refaire,
            // En FO si aucune erreur n'est renvoyé, on va indiquer que la sauvegarde a marché.
            // Validation des champs du questionnaire
            if (validationQuestionnaire(questionnaireReponseForm.getCodeQuestionnaire(), action, result, locale)) {
                // Update du statue de l'étape
                Long idUser = null;
                if (model.containsAttribute("user")){
                    idUser = ((JwtUser) model.asMap().get("user")).getId();
                }
                etapeService.updateStatusEtape(action.getEtapeByStatus(ETAPE_ACTION.CONTEXTE_ET_FACTEUR), ETAPE_STATUT.VALIDER, idUser);
                LoggingUtils.logAction(LOGGER,LoggingUtils.ActionType.VALIDATION,LoggingUtils.SecondaryType.QUESTIONNAIRE,action.getTo(),user);
                emailNotificationService.sendNotificationEmailEtapeAction(model,ETAPE_ACTION.CONTEXTE_ET_FACTEUR,action.getId());
            }
            model.addAttribute("");
        } else {
            result.reject(ERROR_USER_RIGHT, messageSourceService.getMessageSource().getMessage(ERROR_USER_RIGHT, null, locale));
        }
        return ResponseEntity.ok(ValidationErrorBuilder.fromBindingErrors(result));
    }

    @PostMapping(value="questionnaire/soumission", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity soumissionQuestionnaire(QuestionnaireReponseForm questionnaireReponseForm, Model model,
                                                     BindingResult result, Locale locale) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        Long actionId = Long.valueOf(questionnaireReponseForm.getObjetId());
        gestionDroit(model, actionId);
        if(!model.containsAttribute(ETAPE_DISABLED)){
            ActionBean action = actionService.findOneAction(actionId);
            // La sauvegarde auto ayant déjà tout sauvegardé en base, on a pas besoin de le refaire,
            // En FO si aucune erreur n'est renvoyé, on va indiquer que la sauvegarde a marché.
            // Validation des champs du questionnaire
            if (validationQuestionnaire(questionnaireReponseForm.getCodeQuestionnaire(), action, result, locale)) {
                // Update du statue de l'étape
                Long idUser = null;
                if (model.containsAttribute("user")){
                    idUser = ((JwtUser) model.asMap().get("user")).getId();
                }
                etapeService.updateStatusEtape(action.getEtapeByStatus(ETAPE_ACTION.CONTEXTE_ET_FACTEUR), ETAPE_STATUT.ENVOYER, idUser);
                LoggingUtils.logAction(LOGGER,LoggingUtils.ActionType.SOUMISSION,LoggingUtils.SecondaryType.QUESTIONNAIRE,action.getTo(),user);
                emailNotificationService.sendNotificationEmailEtapeAction(model,ETAPE_ACTION.CONTEXTE_ET_FACTEUR,action.getId());
            }
        } else {
            result.reject(ERROR_USER_RIGHT, messageSourceService.getMessageSource().getMessage(ERROR_USER_RIGHT, null, locale));
        }
        return ResponseEntity.ok(ValidationErrorBuilder.fromBindingErrors(result));
    }

    private Boolean validationQuestionnaire(String codeForm, ActionBean action, BindingResult result, Locale locale) {
        Boolean correct = true;
        QUESTIONNAIRE_CODE code = QUESTIONNAIRE_CODE.getByCode(codeForm);
        for (final QUESTIONNAIRE_CODE questionnaire_code : QUESTIONNAIRE_CODE.getQuestionnaireActions(action.getTypeFinancementEnum())) {
            if (code != null && code == questionnaire_code) {
                correct = correct && questionnaireEvaluationService.validationQuestionnaire(questionnaire_code, action.getId(), TYPE_OBJET.ACTION, result, locale, true);
            } else if (!questionnaireEvaluationService.validationQuestionnaire(questionnaire_code, action.getId(), TYPE_OBJET.ACTION, result, locale, true)) {
                correct = false;
                result.reject("error.questionnaire.other", messageSourceService.getMessageSource().getMessage("error.questionnaire.other", new Object[] {questionnaire_code.getLibelle()}, locale));
            }
        }
        return correct && !result.hasErrors();
    }

    @PostMapping(value="questionnaire/annulationValidation", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity annulationValidationQuestionnaire(QuestionnaireReponseForm questionnaireReponseForm, Model model,
                                                     BindingResult result, Locale locale) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        gestionDroit(model, Long.valueOf(questionnaireReponseForm.getObjetId()));
        if(model.containsAttribute(CAN_EDIT) && (boolean) model.asMap().get(CAN_EDIT)){
            ActionBean action = actionService.findOneAction(Long.valueOf(questionnaireReponseForm.getObjetId()));
            Long idUser = null;
            if (model.containsAttribute("user")){
                idUser = ((JwtUser) model.asMap().get("user")).getId();
            }
            etapeService.updateStatusEtape(action.getEtapeByStatus(ETAPE_ACTION.CONTEXTE_ET_FACTEUR), ETAPE_STATUT.A_RENSEIGNER, idUser);
            LoggingUtils.logAction(LOGGER,LoggingUtils.ActionType.ANNULATION,LoggingUtils.SecondaryType.QUESTIONNAIRE,action.getTo(),user);
        } else {
            result.reject(ERROR_USER_RIGHT, messageSourceService.getMessageSource().getMessage(ERROR_USER_RIGHT, null, locale));
        }
        return ResponseEntity.ok(ValidationErrorBuilder.fromBindingErrors(result));
    }

    private void gestionDroit(Model model, Long actionId){
        EtapeBean etape = etapeService.toEtapeBean(etapeService.getEtapeByActionAndCode(actionId, ETAPE_ACTION.CONTEXTE_ET_FACTEUR.getCode()));
        if (ETAPE_STATUT.VALIDER.getCode().equals(etape.getStatut())) {
            model.addAttribute("titrePageValidation", etapeService.getTitrePage(etape));
            model.addAttribute(ETAPE_DISABLED, true);
            // On test si il peuvent modifier aprés validation pour afficher le bouton
            model.addAttribute(CAN_EDIT,
                    rightUserService.canModifObjet( model, actionId, CODE_FUNCTION_PROFILE.MODIF_CONTEXTE_ET_FACTEUR_ACTION.getCode(), TYPE_OBJET.ACTION.getCode()));
            model.addAttribute("canEditFile",
                    rightUserService.canModifObjet(model, actionId, CODE_FUNCTION_PROFILE.EDIT_CONTEXTE_ET_FACTEUR_ACTION.getCode(), TYPE_OBJET.ACTION.getCode()));
            model.addAttribute(CAN_CONSULTE, rightUserService.isHisObject( model, actionId, TYPE_OBJET.ACTION.getCode()));
        } else if(rightUserService.canModifObjet( model, actionId, CODE_FUNCTION_PROFILE.EDIT_CONTEXTE_ET_FACTEUR_ACTION.getCode(), TYPE_OBJET.ACTION.getCode())){
            model.addAttribute(CAN_VALIDE, true);
            model.addAttribute("canEditFile", true);
            model.addAttribute(CAN_CONSULTE, true);
        }  else {
            model.addAttribute(ETAPE_DISABLED, true);
            model.addAttribute(CAN_CONSULTE, rightUserService.isHisObject( model, actionId, TYPE_OBJET.ACTION.getCode()));
        }
    }


}
