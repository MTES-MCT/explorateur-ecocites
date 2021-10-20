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

import com.efficacity.explorateurecocites.beans.Enums;
import com.efficacity.explorateurecocites.beans.biz.ActionBean;
import com.efficacity.explorateurecocites.beans.biz.EtapeBean;
import com.efficacity.explorateurecocites.beans.service.ActionService;
import com.efficacity.explorateurecocites.beans.service.EtapeService;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.ui.bo.service.RightUserService;
import com.efficacity.explorateurecocites.ui.bo.service.notifications.EmailNotificationService;
import com.efficacity.explorateurecocites.utils.enumeration.*;
import com.efficacity.explorateurecocites.utils.log.LoggingUtils;
import isotope.modules.security.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

@Controller
@RequestMapping("/bo/actions/evaluation")
public class ActionEvaluationController {
    @Autowired
    EmailNotificationService emailNotificationService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ActionEvaluationController.class);

    private final ActionService actionService;

    private final EtapeService etapeService;

    private final MessageSourceService messageSourceService;

    private final RightUserService rightUserService;

    public ActionEvaluationController(final RightUserService rightUserService, final ActionService actionService, final EtapeService etapeService, final MessageSourceService messageSourceService) {
        this.rightUserService = rightUserService;
        this.actionService = actionService;
        this.etapeService = etapeService;
        this.messageSourceService = messageSourceService;
    }

    @GetMapping("/{actionId}")
    public String getActionEvaluationTab(Model model, @PathVariable Long actionId) {
        gestionDroit(model, actionId);

        ActionBean action = actionService.findOneAction(actionId);
        Enums.ProfilsUtilisateur profil = null;
        if (model.containsAttribute("userProfileCode")){
            profil = Enums.ProfilsUtilisateur.getByCode((String) model.asMap().get("userProfileCode"));
        }
        if(Enums.ProfilsUtilisateur.ACCOMPAGNATEUR.equals(profil) || Enums.ProfilsUtilisateur.ADMIN.equals(profil)) {
            model.addAttribute("isAdminAccompaModif", true);
        }

        model.addAttribute("action", action);
        model.addAttribute("valeurs_evaluation_niveau_global", EVALUATION_NIVEAU_INNOVATION_GLOBAL.getValueContentMap());
        model.addAttribute("valeurs_evaluation_niveau_nature", EVALUATION_NIVEAU_INNOVATION_NATURE.getValueContentMap());
        return "bo/actions/ongletEdition/innovationEvaluation";
    }

    @PostMapping("request_validation/{actionId}")
    public ResponseEntity requestEvaluationValidation(Model model, Locale locale, @PathVariable Long actionId) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        gestionDroit(model,actionId);
        String error = "";
        if(model.containsAttribute("evaluationLectureSeule") && !(boolean) model.asMap().get("evaluationLectureSeule")){
            ActionBean action = actionService.findOneAction(actionId);
            if (action.getQuestionsAvecReponseBeanList().stream().allMatch(questionsAvecReponseBean -> questionsAvecReponseBean.getReponse() != null)) {
                Long idUser = null;
                if (model.containsAttribute("user")){
                    idUser = ((JwtUser) model.asMap().get("user")).getId();
                }
                etapeService.updateStatusEtape(action.getEtapeByStatus(ETAPE_ACTION.EVALUATION_INNOVATION), ETAPE_STATUT.ENVOYER, idUser);
                LoggingUtils.logAction(LOGGER,LoggingUtils.ActionType.SOUMISSION,LoggingUtils.SecondaryType.INNOVATION,action.getTo(),user);
                emailNotificationService.sendNotificationEmailEtapeAction(model, ETAPE_ACTION.EVALUATION_INNOVATION,actionId);
                return ResponseEntity.ok(true);
            } else {
                error = messageSourceService.getMessageSource().getMessage("error.action.evaluation.missing", null, locale);
            }
        }else{
            error = messageSourceService.getMessageSource().getMessage("error.user.right", null, locale);
        }
        return ResponseEntity.ok(error);
    }

    @PostMapping("accepte_validation/{actionId}")
    public ResponseEntity requestAccepteValidation(Model model, Locale locale, @PathVariable Long actionId) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        gestionDroit(model,actionId);
        String error = "";
        if(model.containsAttribute("canValide") && (boolean) model.asMap().get("canValide")){
            ActionBean action = actionService.findOneAction(actionId);
            if (action.getQuestionsAvecReponseBeanList().stream().allMatch(questionsAvecReponseBean -> questionsAvecReponseBean.getReponse() != null) ) {
                if(action.getEvaluationNiveauGlobal() != null && action.getEvaluationNiveauGlobal() > 0){
                    Long idUser = null;
                    if (model.containsAttribute("user")) {
                        idUser = ((JwtUser) model.asMap().get("user")).getId();
                    }
                    etapeService.updateStatusEtape(action.getEtapeByStatus(ETAPE_ACTION.EVALUATION_INNOVATION), ETAPE_STATUT.VALIDER, idUser);
                    LoggingUtils.logAction(LOGGER,LoggingUtils.ActionType.VALIDATION,LoggingUtils.SecondaryType.INNOVATION,action.getTo(),user);
                    emailNotificationService.sendNotificationEmailEtapeAction(model, ETAPE_ACTION.EVALUATION_INNOVATION, actionId);
                    return ResponseEntity.ok(true);
                } else {
                    error = messageSourceService.getMessageSource().getMessage("error.action.evaluation.niveauglobal.missing", null, locale);
                }
            } else {
                error = messageSourceService.getMessageSource().getMessage("error.action.evaluation.missing", null, locale);
            }
        }else{
            error = messageSourceService.getMessageSource().getMessage("error.user.right", null, locale);
        }
        return ResponseEntity.ok(error);
    }

    @PostMapping("annulation_validation/{actionId}")
    public ResponseEntity requestAnnulationValidation(Model model, Locale locale, @PathVariable Long actionId) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        gestionDroit(model,actionId);
        String error = "";
        if(model.containsAttribute("canEdit") && (boolean) model.asMap().get("canEdit")){
            ActionBean action = actionService.findOneAction(actionId);
            Long idUser = null;
            if (model.containsAttribute("user")){
                idUser = ((JwtUser) model.asMap().get("user")).getId();
            }
            etapeService.updateStatusEtape(action.getEtapeByStatus(ETAPE_ACTION.EVALUATION_INNOVATION), ETAPE_STATUT.A_RENSEIGNER, idUser);
            LoggingUtils.logAction(LOGGER,LoggingUtils.ActionType.ANNULATION,LoggingUtils.SecondaryType.INNOVATION,action.getTo(),user);
            return ResponseEntity.ok(true);
        }else{
            error = messageSourceService.getMessageSource().getMessage("error.user.right", null, locale);
        }
        return ResponseEntity.ok(error);
    }

    private void gestionDroit(Model model, Long actionId){
        EtapeBean etape = etapeService.toEtapeBean(etapeService.getEtapeByActionAndCode(actionId, ETAPE_ACTION.EVALUATION_INNOVATION.getCode()));
        if (ETAPE_STATUT.VALIDER.getCode().equals(etape.getStatut())) {
            model.addAttribute("etapeValide", true);
            model.addAttribute("titrePageValidation", etapeService.getTitrePage(etape));
            model.addAttribute("evaluationLectureSeule", true);
            // On test si il peuvent modifier aprés validation pour afficher le bouton
            if(rightUserService.canModifObjet( model, actionId, CODE_FUNCTION_PROFILE.MODIF_EVALUATION_INNOVATION_ACTION.getCode(), TYPE_OBJET.ACTION.getCode())){
                model.addAttribute("canEdit", true);
            }  else {
                model.addAttribute("canEdit", false);
            }
        } else {
            if (ETAPE_STATUT.ENVOYER.getCode().equals(etape.getStatut())) {
                model.addAttribute("titrePageValidation", etapeService.getTitrePage(etape));
                // On test si il peuvent valider une étape pour afficher le bouton
                if(rightUserService.canModifObjet( model, actionId, CODE_FUNCTION_PROFILE.VALIDATE_EVALUATION_INNOVATION_ACTION.getCode(), TYPE_OBJET.ACTION.getCode())){
                    model.addAttribute("canValide", true);
                    model.addAttribute("evaluationLectureSeule", false);
                }  else {
                    model.addAttribute("evaluationLectureSeule", true);
                }
            } else {
                if(rightUserService.canModifObjet( model, actionId, CODE_FUNCTION_PROFILE.EDIT_EVALUATION_INNOVATION_ACTION.getCode(), TYPE_OBJET.ACTION.getCode())){
                    model.addAttribute("canEdit", true);
                    model.addAttribute("evaluationLectureSeule", false);
                }  else {
                    model.addAttribute("evaluationLectureSeule", true);
                }
            }
        }
    }
}
