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

import com.efficacity.explorateurecocites.beans.Enums;
import com.efficacity.explorateurecocites.beans.biz.AssoIndicateurObjetBean;
import com.efficacity.explorateurecocites.beans.biz.ReponsesEvaluationBean;
import com.efficacity.explorateurecocites.beans.biz.ReponsesQuestionnaireEvaluationBean;
import com.efficacity.explorateurecocites.beans.model.*;
import com.efficacity.explorateurecocites.beans.service.*;
import com.efficacity.explorateurecocites.ui.bo.exceptions.SaveAttributException;
import com.efficacity.explorateurecocites.ui.bo.forms.BeanSaveAttribut;
import com.efficacity.explorateurecocites.ui.bo.service.ChoixIndicateursService;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.ui.bo.service.QuestionnaireEvaluationService;
import com.efficacity.explorateurecocites.ui.bo.service.SaveAttributeService;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_FINANCEMENT;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import com.efficacity.explorateurecocites.utils.log.LoggingUtils;
import isotope.commons.exceptions.NotFoundException;
import isotope.commons.validation.ValidationErrorBuilder;
import isotope.modules.security.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by tfossurier on 10/01/2018.
 */
@Controller
public class SaveAttributController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaveAttributController.class);
    @Autowired
    MediaModificationService mediaModificationService;
    @Autowired
    ReponsesEvaluationService reponsesEvaluationService;
    @Autowired
    ActionService actionService;
    @Autowired
    EcociteService ecociteService;
    @Autowired
    AxeService axeService;
    @Autowired
    AssoIndicateurObjetService assoIndicateurObjetService;
    @Autowired
    ChoixIndicateursService choixIndicateursService;
    @Autowired
    SaveAttributeService saveAttrServ;
    @Autowired
    MessageSourceService messageSourceService;
    @Autowired
    ReponsesQuestionnaireEvaluationService reponsesQuestionnaireEvaluationService;
    @Autowired
    QuestionnaireEvaluationService questionnaireEvaluationService;
    @Autowired
    MesureIndicateurService mesureIndicateurService;
    @Autowired
    CibleIndicateurService cibleIndicateurService;

    @PostMapping("/bo/saveAttribut")
    public ResponseEntity<?> saveAttribut(@RequestBody @Valid BeanSaveAttribut object, Model model,
                                          BindingResult result, Locale locale) {

        if(!useCanModify(model, object)){
            result.reject("general_error", messageSourceService.getMessageSource().getMessage("error.user.right", null, locale));
            return ResponseEntity.ok(ValidationErrorBuilder.fromBindingErrors(result));
        }

        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }

        Object biz;
        if (result.hasErrors()) {
            throw new SaveAttributException(result);
        }
        switch (object.getObjectClass()) {
            case "ReponseEvaluation":
                final ReponsesEvaluationBean reponse = reponsesEvaluationService.findOrCreateByIdQuestionAndIdAction(
                        Long.parseLong(object.getReferenceId()),
                        Long.parseLong(object.getObjectId()));
                saveAttrServ.majAttribut(reponse, "niveau", object.getAttributValue(), result, locale);
                if (!result.hasErrors()) {
                    try {
                        actionService.markActionModified(Long.valueOf(object.getObjectId()), user);
                        reponsesEvaluationService.save(reponse);
                        Supplier<Action> findActionById = () -> actionService.findById(Long.valueOf(object.getObjectId()));
                        LoggingUtils.logActionSupplierA(LOGGER, LoggingUtils.ActionType.MODIFICATION, LoggingUtils.SecondaryType.INNOVATION,findActionById,user);
                        return ResponseEntity.ok(reponse);
                    } catch (Exception e) {
                        result.reject("general_error", messageSourceService.getMessageSource().getMessage("error.technical", null, locale));
                    }
                }
                break;
            case "Action":
                if (Objects.equals(object.getAttributId(), "typeFinancement")) {
                    String value = object.getAttributValue();
                    if(CustomValidator.isEmpty(value)){
                        result.rejectValue("attributValue","error.attribut.notNull", messageSourceService.getMessageSource().getMessage("error.attribut.notNull", null, locale));
                    } else if (CustomValidator.isEmpty(TYPE_FINANCEMENT.getByCode(value))) {
                        result.rejectValue("error.attribut.unknown","error.attribut.unknown", messageSourceService.getMessageSource().getMessage("error.attribut.unknown", null, locale));
                    } else {
                        actionService.changeActionTypeFinancement(Long.valueOf(object.getObjectId()), object.getAttributValue(), user);
                        Action action = actionService.findById((Long.valueOf(object.getObjectId())));
                        mediaModificationService.markModified(action);
                        LoggingUtils.logAction(LOGGER, LoggingUtils.ActionType.MODIFICATION, LoggingUtils.SecondaryType.NONE, action, user);
                    }
                } else {
                    biz = saveAttrServ.majAttribut(actionService.findOneAction(Long.valueOf(object.getObjectId())),
                            object.getAttributId(), object.getAttributValue(), result, locale);
                    if (Objects.equals(object.getAttributId(), "numeroAction") && actionService.countByNumeroDifferentFromAction(object.getAttributValue(),
                            Long.valueOf(object.getObjectId())) > 0) {
                        result.rejectValue("attributValue", "error.attribut.unique", "Ce numéro d'action est déjà utilisé, merci de vérifier sa validité");
                    }
                    if (!result.hasErrors()) {
                        try {
                            Action action = (Action) biz;
                            mediaModificationService.markModified(action);
                            actionService.save(action, user);
                            Supplier<Action> findActionById = () -> actionService.findById(Long.valueOf(object.getObjectId()));
                            if(object.getAttributId().equals("evaluationNiveauGlobal")){
                                LoggingUtils.logActionSupplierA(LOGGER, LoggingUtils.ActionType.MODIFICATION, LoggingUtils.SecondaryType.INNOVATION, findActionById, user);
                            }
                            else {
                                LoggingUtils.logActionSupplierA(LOGGER, LoggingUtils.ActionType.MODIFICATION, LoggingUtils.SecondaryType.NONE, findActionById, user);
                            }
                        } catch (Exception e) {
                            result.reject("general_error", messageSourceService.getMessageSource().getMessage("error.technical", null, locale));
                        }
                    }
                }
                break;
            case "Ecocite":
                biz = saveAttrServ.majAttribut(ecociteService.findOneEcocite(Long.valueOf(object.getObjectId())),
                        object.getAttributId(), object.getAttributValue(), result, locale);
                if (!result.hasErrors()) {
                    try {
                        Ecocite ecocite = (Ecocite) biz;
                        mediaModificationService.markModified(ecocite);
                        ecociteService.save(ecocite, user);
                        Supplier<Ecocite> findEcociteById = () -> ecociteService.findById(Long.valueOf(object.getObjectId()));
                        LoggingUtils.logActionSupplierE(LOGGER, LoggingUtils.ActionType.MODIFICATION, LoggingUtils.SecondaryType.NONE,findEcociteById,user);
                    } catch (Exception e) {
                        result.reject("general_error", messageSourceService.getMessageSource().getMessage("error.technical", null, locale));
                    }
                }
                break;
            case "AssoIndicateurObjet":
                biz = saveAttrServ.majAttribut(assoIndicateurObjetService.findOneById(Long.valueOf(object.getObjectId())),
                        object.getAttributId(), object.getAttributValue(), result, locale);
                if (!result.hasErrors()) {
                    try {
                        assoIndicateurObjetService.save((AssoIndicateurObjet)biz);
                    } catch (Exception e) {
                        result.reject("general_error",messageSourceService.getMessageSource().getMessage("error.technical", null, locale));
                    }
                }
                break;
            case "ReponsesQuestionnaireEvaluation":
                ReponsesQuestionnaireEvaluationBean reponsesQuestionnaireEvaluationBean = questionnaireEvaluationService.sauvegardeReponse(object);
                if (reponsesQuestionnaireEvaluationBean != null) {
                    try {
                        reponsesQuestionnaireEvaluationService.save(reponsesQuestionnaireEvaluationBean);
                        Supplier<Action> findActionById = () -> actionService.findById(Long.valueOf(object.getObjectId()));
                        Supplier<Ecocite> findEcociteById = () -> ecociteService.findById(Long.valueOf(object.getObjectId()));
                        TYPE_OBJET typeObjet = TYPE_OBJET.getByCode(object.getReferenceTypeObjet());
                        if (TYPE_OBJET.ACTION.equals(typeObjet)) {
                            actionService.markActionModified(findActionById.get(), user);
                        }
                        LoggingUtils.logAction(LOGGER, LoggingUtils.ActionType.MODIFICATION, LoggingUtils.SecondaryType.QUESTIONNAIRE, findActionById, findEcociteById, user, typeObjet);
                    } catch (Exception e) {
                        result.reject("general_error", messageSourceService.getMessageSource().getMessage("error.technical", null, locale));
                    }
                }
                break;
            case "MesureIndicateur":
                biz = saveAttrServ.majAttribut(mesureIndicateurService.findOne(Long.valueOf(object.getObjectId())).orElseThrow(NotFoundException::new),
                        object.getAttributId(), object.getAttributValue(), result, locale);
                if (!result.hasErrors()) {
                    try {
                        MesureIndicateur mesure = (MesureIndicateur) biz;
                        mesureIndicateurService.save(mesure);
                        choixIndicateursService.updateEtapeForAsso(mesure.getIdAssoIndicateurObjet(),user);
                        Optional<MesureIndicateur> optAssoMesure=mesureIndicateurService.findOne(Long.valueOf(object.getObjectId()));
                        if(optAssoMesure.isPresent()) {
                            Long idAssoIndicateurObjet = optAssoMesure.get().getIdAssoIndicateurObjet();
                            Optional<AssoIndicateurObjet> optAssoIndicateurObjet = assoIndicateurObjetService.findOne(idAssoIndicateurObjet);
                            if (optAssoIndicateurObjet.isPresent()) {
                                Supplier<Action> findActionById = () -> actionService.findById(optAssoIndicateurObjet.get().getIdObjet());
                                Supplier<Ecocite> findEcociteById = () -> ecociteService.findById(optAssoIndicateurObjet.get().getIdObjet());
                                if(Objects.equals(object.getAttributValue(),"true")){
                                    LoggingUtils.logAction(LOGGER, LoggingUtils.ActionType.VALIDATION, LoggingUtils.SecondaryType.RENSEIGNEMENT_INDICATEURS_MESURE, findActionById,findEcociteById, user,TYPE_OBJET.getByCode(object.getReferenceTypeObjet()));
                                }
                                else if(Objects.equals(object.getAttributValue(), "false")){
                                    LoggingUtils.logAction(LOGGER, LoggingUtils.ActionType.ANNULATION, LoggingUtils.SecondaryType.RENSEIGNEMENT_INDICATEURS_MESURE, findActionById,findEcociteById, user,TYPE_OBJET.getByCode(object.getReferenceTypeObjet()));
                                }
                                else{
                                    LoggingUtils.logAction(LOGGER, LoggingUtils.ActionType.MODIFICATION, LoggingUtils.SecondaryType.RENSEIGNEMENT_INDICATEURS_MESURE, findActionById,findEcociteById, user,TYPE_OBJET.getByCode(object.getReferenceTypeObjet()));
                                }
                            }
                        }
                      } catch (Exception e) {
                        result.reject("general_error",messageSourceService.getMessageSource().getMessage("error.technical", null, locale));
                    }
                }
                break;
            case "CibleIndicateur":
                biz = saveAttrServ.majAttribut(cibleIndicateurService.findOne(Long.valueOf(object.getObjectId())).orElseThrow(NotFoundException::new),
                        object.getAttributId(), object.getAttributValue(), result, locale);
                if (!result.hasErrors()) {
                    try {
                        CibleIndicateur cible = (CibleIndicateur) biz;
                        cibleIndicateurService.save(cible);
                        choixIndicateursService.updateEtapeForAsso(cible.getIdAssoIndicateurObjet(),user);
                        Optional<CibleIndicateur> optAssoCible=cibleIndicateurService.findOne(Long.valueOf(object.getObjectId()));
                        if(optAssoCible.isPresent()) {
                            Long idAssoIndicateurObjet = optAssoCible.get().getIdAssoIndicateurObjet();
                            Optional<AssoIndicateurObjet> optAssoIndicateurObjet = assoIndicateurObjetService.findOne(idAssoIndicateurObjet);
                            if (optAssoIndicateurObjet.isPresent()) {
                                Supplier<Action> findActionById = () -> actionService.findById(optAssoIndicateurObjet.get().getIdObjet());
                                Supplier<Ecocite> findEcociteById = () -> ecociteService.findById(optAssoIndicateurObjet.get().getIdObjet());
                                if(Objects.equals(object.getAttributValue(),"true")){
                                    LoggingUtils.logAction(LOGGER, LoggingUtils.ActionType.VALIDATION, LoggingUtils.SecondaryType.RENSEIGNEMENT_INDICATEURS_CIBLE, findActionById,findEcociteById, user,TYPE_OBJET.getByCode(object.getReferenceTypeObjet()));
                                }
                                else if(Objects.equals(object.getAttributValue(), "false")){
                                    LoggingUtils.logAction(LOGGER, LoggingUtils.ActionType.ANNULATION, LoggingUtils.SecondaryType.RENSEIGNEMENT_INDICATEURS_CIBLE, findActionById,findEcociteById, user,TYPE_OBJET.getByCode(object.getReferenceTypeObjet()));
                                }
                                else{
                                    LoggingUtils.logAction(LOGGER, LoggingUtils.ActionType.MODIFICATION, LoggingUtils.SecondaryType.RENSEIGNEMENT_INDICATEURS_CIBLE, findActionById,findEcociteById, user,TYPE_OBJET.getByCode(object.getReferenceTypeObjet()));
                                }
                            }
                        }
                    } catch (Exception e) {
                        result.reject("general_error",messageSourceService.getMessageSource().getMessage("error.technical", null, locale));
                    }
                }
                break;
            default:
                break;
        }
        return ResponseEntity.ok(ValidationErrorBuilder.fromBindingErrors(result));
    }

    private boolean useCanModify(final Model model, BeanSaveAttribut object) {
        JwtUser user = null;
        Enums.ProfilsUtilisateur profil = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        if (model.containsAttribute("userProfileCode")){
            profil = Enums.ProfilsUtilisateur.getByCode((String) model.asMap().get("userProfileCode"));
        }

        if(user != null && profil != null) {
            if(!Enums.ProfilsUtilisateur.isAdmin(profil.getCode())){
                // Si pas un admin on va check
                if(object.getObjectClass().equals("Action") || object.getObjectClass().equals("ReponseEvaluation") || (object.getObjectClass().equals("ReponsesQuestionnaireEvaluation") && object.getReferenceTypeObjet().equals(TYPE_OBJET.ACTION.getCode()))){
                    return user.getAuthorities().contains(new SimpleGrantedAuthority(TYPE_OBJET.ACTION.getCode()+"_ALL"))
                            || user.getAuthorities().contains(new SimpleGrantedAuthority(TYPE_OBJET.ACTION.getCode()+"_" + object.getObjectId()));
                } else if(object.getObjectClass().equals("Ecocite") || (object.getObjectClass().equals("ReponsesQuestionnaireEvaluation") && object.getReferenceTypeObjet().equals(TYPE_OBJET.ECOCITE.getCode()))){
                    return user.getAuthorities().contains(new SimpleGrantedAuthority(TYPE_OBJET.ECOCITE.getCode()+"_ALL"))
                            || user.getAuthorities().contains(new SimpleGrantedAuthority(TYPE_OBJET.ECOCITE.getCode()+"_" + object.getObjectId()));
                } else if(object.getObjectClass().equals("AssoIndicateurObjet")) {
                    // cas de sauvegarde de com des cible et mesure
                    AssoIndicateurObjetBean bean = assoIndicateurObjetService.findOneById(Long.valueOf(object.getObjectId()));
                    if(bean != null){
                        if(TYPE_OBJET.ACTION.getCode().equals(bean.getTypeObjet())) {
                            return user.getAuthorities().contains(new SimpleGrantedAuthority(TYPE_OBJET.ACTION.getCode() + "_ALL"))
                                    || user.getAuthorities().contains(new SimpleGrantedAuthority(TYPE_OBJET.ACTION.getCode() + "_" + bean.getIdObjet()));
                        } else if(TYPE_OBJET.ECOCITE.getCode().equals(bean.getTypeObjet())){
                            return user.getAuthorities().contains(new SimpleGrantedAuthority(TYPE_OBJET.ECOCITE.getCode()+"_ALL"))
                                    || user.getAuthorities().contains(new SimpleGrantedAuthority(TYPE_OBJET.ECOCITE.getCode() + "_" + bean.getIdObjet()));
                        }
                    }
                }
            } else {
                return true;
            }
        }
        return false;
    }
}
