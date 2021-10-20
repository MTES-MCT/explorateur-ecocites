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
import com.efficacity.explorateurecocites.beans.model.*;
import com.efficacity.explorateurecocites.beans.service.*;
import com.efficacity.explorateurecocites.ui.bo.forms.EtiquetteFrom;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.ui.bo.service.RightUserService;
import com.efficacity.explorateurecocites.ui.bo.service.action.onglets.ActionCategoryService;
import com.efficacity.explorateurecocites.ui.bo.service.notifications.EmailNotificationService;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import com.efficacity.explorateurecocites.utils.enumeration.*;
import com.efficacity.explorateurecocites.utils.log.LoggingUtils;
import com.efficacity.explorateurecocites.utils.service.email.EnvoieEmailService;
import isotope.commons.exceptions.ForbiddenException;
import isotope.modules.security.JwtUser;
import isotope.modules.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Created by rxion on 12/02/2018.
 */
@Controller
@RequestMapping("bo/actions")
public class ActionCategorisationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActionCategorisationController.class);
    private static final String PATH_LIST_MODAL = "bo/modal/listEtiquettes";

    @Autowired
    ActionService actionService;

    @Autowired
    ActionCategoryService actionCategoryService;

    @Autowired
    EtapeService etapeService;

    @Autowired
    EtiquetteAxeService etiquetteAxeServ;
    @Autowired
    EtiquetteIngenierieService etiquetteIngenierieServ;
    @Autowired
    EtiquetteFinaliteService etiquetteFinaliteServ;

    @Autowired
    AxeService axeService;
    @Autowired
    IngenierieService ingenierieService;
    @Autowired
    FinaliteService finaliteService;

    @Autowired
    AssoActionIngenierieService actionIngenierieServ;
    @Autowired
    AssoActionDomainService actionDomainServ;
    @Autowired
    AssoObjetObjectifService objetObjectifServ;

    @Autowired
    UserService userService;
    @Autowired
    RightUserService rightUserService;
    @Autowired
    MessageSourceService messageSourceService;
    @Autowired
    EmailNotificationService emailNotificationService;
    @Autowired
    EnvoieEmailService envoieEmailService;


    @GetMapping("{cat}/etiquettes/{idCat}/{idAction}")

    public String getListEtiquettesModalView(@PathVariable("cat") String categorie, @PathVariable("idCat") Long idCat, @PathVariable("idAction") Long idAction, Model model) {
        model.addAttribute("currentCategory", categorie);
        model.addAttribute("currentCategoryId", idCat);
        switch (categorie) {
            case "axe":
                model.addAttribute("etiquettes", etiquetteAxeServ.getListSelectedByAction(idAction, idCat).getEtiquettes());
                break;
            case "finalite":
                model.addAttribute("etiquettes", etiquetteFinaliteServ.getListSelectedByAction(idAction, idCat).getEtiquettes());
                break;
            case "ingenierie":
                model.addAttribute("etiquettes", etiquetteIngenierieServ.getListSelectedByAction(idAction, idCat).getEtiquettes());
                break;
            default:
                break;
        }
        return PATH_LIST_MODAL;
    }


    private void remplirAttributCommun(Model model, ActionBean action) {
        model.addAttribute("action", action);
        model.addAttribute("isIngenierie", action.isTypeFinancementIngenierie());
    }

    @GetMapping("domaine/{actionId}")
    public String getCategoryAxeTab(Model model, @PathVariable Long actionId) {
        gestionDroit(model,actionId);
        ActionBean action = actionService.findOneAction(actionId);

        if(model.containsAttribute("canConsulte") && !(boolean) model.asMap().get("canConsulte")){
            return "bo/components/errorAccess";
        }

        remplirAttributCommun(model, action);
        model.addAttribute("axes", axeService.getList());
        Axe axe = axeService.getByAction(actionId);
        axe = (axe == null ? axeService.getList().get(0) : axe);
        model.addAttribute("axeSelec", axe);
        model.addAttribute("actionDomain", etiquetteAxeServ.getListSelectedByAction(actionId, axe.getId()));
        return "bo/actions/ongletEdition/domaineAction";
    }


    @GetMapping("objectif/{actionId}")
    public String getCategoryFinaliteTab(Model model, @PathVariable Long actionId) {
        gestionDroit(model,actionId);

        if(model.containsAttribute("canConsulte") && !(boolean) model.asMap().get("canConsulte")){
            return "bo/components/errorAccess";
        }

        ActionBean action = actionService.findOneAction(actionId);
        remplirAttributCommun(model, action);
        List<Finalite> finalites = finaliteService.getList();
        model.addAttribute("finalites", finalites);
        model.addAttribute("actionObjectif", etiquetteFinaliteServ.getListSelectedByAction(actionId, finalites.get(0).getId()));
        Finalite finalite = finalites.get(0);
        model.addAttribute("finaliteSelec", finalite);
        return "bo/actions/ongletEdition/objectifAction";
    }


    @GetMapping("ingenierie/{actionId}")
    public String getCategoryIngenierieTab(Model model, @PathVariable Long actionId) {
        gestionDroit(model,actionId);

        if(model.containsAttribute("canConsulte") && !(boolean) model.asMap().get("canConsulte")){
            return "bo/components/errorAccess";
        }

        ActionBean action = actionService.findOneAction(actionId);
        if (action.isTypeFinancementIngenierie() || action.isTypeFinancementIngenierieEtInvestissement()
                || action.isTypeFinancementIngenierieEtPriseParticipation()) {
            remplirAttributCommun(model, action);
            List<Ingenierie> ingenieries = ingenierieService.getList();
            model.addAttribute("ingenieries", ingenieries);
            model.addAttribute("actionIngenierie", etiquetteIngenierieServ.getListSelectedByAction(actionId, ingenieries.get(0).getId()));
            Ingenierie ingenierie = ingenieries.get(0);
            model.addAttribute("ingenierieSelec", ingenierie);
            return "bo/actions/ongletEdition/ingenierie";
        }
        return "bo/actions/commun/errorAccess";
    }

    @GetMapping("category/can_request_validation/{actionId}")
    public ResponseEntity canRequestEvaluationValidation(Locale locale, @PathVariable Long actionId) {
        return ResponseEntity.ok(Objects.equals(actionCategoryService.canRequestCategorieValidation(actionId, locale), ""));
    }

    @PostMapping("category/request_validation/{actionId}")
    public ResponseEntity requestEvaluationValidation(Model model, Locale locale, @PathVariable Long actionId) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        gestionDroit(model,actionId);
        String error = "";
        if(model.containsAttribute("caracteristiqueLectureSeule") && !(boolean) model.asMap().get("caracteristiqueLectureSeule")){
            error = actionCategoryService.canRequestCategorieValidation(actionId, locale);
            if (CustomValidator.isEmpty(error)) {
                ActionBean action = actionService.findOneAction(actionId);
                if (action.isTypeFinancementIngenierie()) {
                    Long idUser = null;
                    if (model.containsAttribute("user")) {
                        idUser = ((JwtUser) model.asMap().get("user")).getId();
                    }
                    etapeService.updateStatusEtape(action.getEtapeByStatus(ETAPE_ACTION.CARACTERISATION), ETAPE_STATUT.ENVOYER, idUser);
                    LoggingUtils.logAction(LOGGER,LoggingUtils.ActionType.SOUMISSION,LoggingUtils.SecondaryType.CARACTERISATION,action.getTo(),user);
                    emailNotificationService.sendNotificationEmailEtapeAction(model,ETAPE_ACTION.CARACTERISATION,actionId);
                    return ResponseEntity.ok(true);
                }
            }
        }else{
            error = messageSourceService.getMessageSource().getMessage("error.user.right", null, locale);
        }
        return ResponseEntity.ok(error);
    }

    @PostMapping("category/accepte_validation/{actionId}")
    public ResponseEntity requestAccepteValidation(Model model, Locale locale, @PathVariable Long actionId) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        gestionDroit(model,actionId);
        String error = "";
        if(model.containsAttribute("canValide") && (boolean) model.asMap().get("canValide")){
            error = actionCategoryService.canRequestCategorieValidation(actionId, locale);
            if (CustomValidator.isEmpty(error)) {
                ActionBean action = actionService.findOneAction(actionId);
                if (action.isTypeFinancementIngenierie()) {
                    Long idUser = null;
                    if (model.containsAttribute("user")) {
                        idUser = ((JwtUser) model.asMap().get("user")).getId();
                    }
                    etapeService.updateStatusEtape(action.getEtapeByStatus(ETAPE_ACTION.CARACTERISATION), ETAPE_STATUT.VALIDER, idUser);
                    LoggingUtils.logAction(LOGGER,LoggingUtils.ActionType.VALIDATION,LoggingUtils.SecondaryType.CARACTERISATION,action.getTo(),user);
                    emailNotificationService.sendNotificationEmailEtapeAction(model,ETAPE_ACTION.CARACTERISATION,actionId);
                    return ResponseEntity.ok(true);
                }
            }
        }else{
            error = messageSourceService.getMessageSource().getMessage("error.user.right", null, locale);
        }
        return ResponseEntity.ok(error);
    }

    @PostMapping("category/annulation_validation/{actionId}")
    public ResponseEntity requestAnnulationValidation(Model model, Locale locale, @PathVariable Long actionId) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        gestionDroit(model,actionId);
        String error = "";
        if(model.containsAttribute("canEdit") && (boolean) model.asMap().get("canEdit")){
            error = actionCategoryService.canRequestCategorieValidation(actionId, locale);
            if (CustomValidator.isEmpty(error)) {
                ActionBean action = actionService.findOneAction(actionId);
                if (action.isTypeFinancementIngenierie()) {
                    Long idUser = null;
                    if (model.containsAttribute("user")) {
                        idUser = ((JwtUser) model.asMap().get("user")).getId();
                    }
                    etapeService.updateStatusEtape(action.getEtapeByStatus(ETAPE_ACTION.CARACTERISATION), ETAPE_STATUT.A_RENSEIGNER, idUser);
                    LoggingUtils.logAction(LOGGER,LoggingUtils.ActionType.ANNULATION,LoggingUtils.SecondaryType.CARACTERISATION,action.getTo(),user);
                    return ResponseEntity.ok(true);
                }
            }
        }else{
            error = messageSourceService.getMessageSource().getMessage("error.user.right", null, locale);
        }
        return ResponseEntity.ok(error);
    }

    @PostMapping("axe/majEtiquette")
    @ResponseBody
    public ResponseEntity majAssoActiondomain(Model model,@RequestBody @Valid EtiquetteFrom etiquetteFrom) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        Long idAction = Long.parseLong(etiquetteFrom.getIdObjet());
        gestionDroit(model,idAction);

        if(model.containsAttribute("caracteristiqueLectureSeule") && !(boolean) model.asMap().get("caracteristiqueLectureSeule")){
            Action action = actionService.findById(idAction);
            Long idEtiquette = Long.parseLong(etiquetteFrom.getIdEtiquette());
            if (actionDomainServ.etiquettesExistByAction(idAction, idEtiquette)) {
                return ResponseEntity.badRequest().body("");
            }
            if (etiquetteFrom.getPoid() == 1
                    && actionDomainServ.countEtiquettesPrinByAction(idAction) == 0L
                    || etiquetteFrom.getPoid() == 2
                    && actionDomainServ.countEtiquettesSecondByAction(idAction) < 5L) {
                EtiquetteAxe domain = etiquetteAxeServ.findById(Long.parseLong(etiquetteFrom.getIdEtiquette()));
                LoggingUtils.logAction(LOGGER,LoggingUtils.ActionType.MODIFICATION,LoggingUtils.SecondaryType.CARACTERISATION_DOMAINES,action,user);
                return ResponseEntity.ok(actionDomainServ.maj(action, domain, etiquetteFrom, user));
            }
        } else {
            throw new ForbiddenException("Vous n'avez pas les droits pour effectuer cette action");
        }
        return ResponseEntity.badRequest().body("");
    }

    @DeleteMapping("axe/etiquette/{idAction}/{idAssoActionEtiquette}")
    @ResponseBody
    public void deleteAssoActionDomain(Model model,@PathVariable("idAction") Long idAction,
                                       @PathVariable("idAssoActionEtiquette") Long idAssoActionEtiquette) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        gestionDroit(model,idAction);

        if(model.containsAttribute("caracteristiqueLectureSeule") && !(boolean) model.asMap().get("caracteristiqueLectureSeule")) {
            actionDomainServ.delete(idAction, idAssoActionEtiquette, user);
            Supplier<Action> findActionById = () -> actionService.findById(idAction);
            LoggingUtils.logActionSupplierA(LOGGER,LoggingUtils.ActionType.MODIFICATION,LoggingUtils.SecondaryType.CARACTERISATION_DOMAINES,findActionById,user);
        } else {
            throw new ForbiddenException("Vous n'avez pas les droits pour effectuer cette action");
        }
    }

    @PostMapping("finalite/majEtiquette")
    @ResponseBody
    public ResponseEntity majAssoActionObjectif(Model model,@RequestBody @Valid EtiquetteFrom etiquetteFrom) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        Long idAction = Long.parseLong(etiquetteFrom.getIdObjet());
        gestionDroit(model,idAction);

        if(model.containsAttribute("caracteristiqueLectureSeule") && !(boolean) model.asMap().get("caracteristiqueLectureSeule")){
            Long idEtiquette = Long.parseLong(etiquetteFrom.getIdEtiquette());
            if (objetObjectifServ.etiquettesExistByAction(idAction, idEtiquette)) {
                return ResponseEntity.badRequest().body("");
            }
            if (etiquetteFrom.getPoid() == 1
                    || etiquetteFrom.getPoid() == 2
                    && objetObjectifServ.countEtiquettesSecondByAction(idAction) < 5L
                    || etiquetteFrom.getPoid() == 3
                    && objetObjectifServ.countEtiquettesThirdByAction(idAction) < 5L) {
                EtiquetteFinalite objectif = etiquetteFinaliteServ.findById(idEtiquette);
                Supplier<Action> findActionById = () -> actionService.findById(idAction);
                LoggingUtils.logActionSupplierA(LOGGER,LoggingUtils.ActionType.MODIFICATION,LoggingUtils.SecondaryType.CARACTERISATION_OBJECTIF,findActionById,user);
                return ResponseEntity.ok(objetObjectifServ.maj(idAction, TYPE_OBJET.ACTION, objectif, etiquetteFrom, user));
            }
        }else {
            throw new ForbiddenException("Vous n'avez pas les droits pour effectuer cette action");
        }
        return ResponseEntity.badRequest().body("");
    }

    @DeleteMapping("finalite/etiquette/{idAction}/{idAssoActionEtiquette}")
    @ResponseBody
    public void deleteAssoActionObjectif(Model model,@PathVariable("idAction") Long idAction,
                                         @PathVariable("idAssoActionEtiquette") Long idAssoActionEtiquette) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        gestionDroit(model,idAction);

        if(model.containsAttribute("caracteristiqueLectureSeule") && !(boolean) model.asMap().get("caracteristiqueLectureSeule")){
            objetObjectifServ.delete(idAction, TYPE_OBJET.ACTION, idAssoActionEtiquette, user);
            Supplier<Action> findActionById = () -> actionService.findById(idAction);
            LoggingUtils.logActionSupplierA(LOGGER,LoggingUtils.ActionType.MODIFICATION,LoggingUtils.SecondaryType.CARACTERISATION_OBJECTIF,findActionById,user);
        } else {
            throw new ForbiddenException("Vous n'avez pas les droits pour effectuer cette action");
        }
    }

    @PostMapping("ingenierie/majEtiquette")
    @ResponseBody
    public ResponseEntity majAssoActionIngenierie(Model model,@RequestBody @Valid EtiquetteFrom etiquetteFrom) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        Long idAction = Long.parseLong(etiquetteFrom.getIdObjet());

        gestionDroit(model,idAction);

        if(model.containsAttribute("caracteristiqueLectureSeule") && !(boolean) model.asMap().get("caracteristiqueLectureSeule")){
            ActionBean action = new ActionBean(actionService.findById(idAction), null, null, null);
            if (!action.isTypeFinancementInvestissementOuPriseParticipation()) {
                Long idEtiquette = Long.parseLong(etiquetteFrom.getIdEtiquette());
                if (actionIngenierieServ.etiquettesExistByAction(idAction, idEtiquette)) {
                    return ResponseEntity.badRequest().body("");
                }
                if (etiquetteFrom.getPoid() == 1) {
                    EtiquetteIngenierie ingenierie = etiquetteIngenierieServ.findById(idEtiquette);
                    Supplier<Action> findActionById = () -> actionService.findById(idAction);
                    LoggingUtils.logActionSupplierA(LOGGER,LoggingUtils.ActionType.MODIFICATION,LoggingUtils.SecondaryType.CARACTERISATION_INGENIERIE,findActionById,user);
                    return ResponseEntity.ok(actionIngenierieServ.maj(action.getTo(), ingenierie, etiquetteFrom, user));
                }
            }
            throw new ForbiddenException("Le type de financement de l'action " + etiquetteFrom.getIdObjet() + " n'est pas ingénierie");
        } else {
            throw new ForbiddenException("Vous n'avez pas les droits pour effectuer cette action");
        }
    }

    @DeleteMapping("ingenierie/etiquette/{idAction}/{idAssoActionEtiquette}")
    @ResponseBody
    public void deleteAssoActionIngenierie(Model model,@PathVariable("idAction") Long idAction,
                                           @PathVariable("idAssoActionEtiquette") Long idAssoActionEtiquette) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        gestionDroit(model,idAction);

        if(model.containsAttribute("caracteristiqueLectureSeule") && !(boolean) model.asMap().get("caracteristiqueLectureSeule")){
            ActionBean action = new ActionBean(actionService.findById(idAction), null, null, null);
            if (!action.isTypeFinancementInvestissementOuPriseParticipation()) {
                actionIngenierieServ.delete(idAction, idAssoActionEtiquette, user);
                Supplier<Action> findActionById = () -> actionService.findById(idAction);
                LoggingUtils.logActionSupplierA(LOGGER,LoggingUtils.ActionType.MODIFICATION,LoggingUtils.SecondaryType.CARACTERISATION_INGENIERIE,findActionById,user);
            }
        }
    }


    private void gestionDroit(Model model, Long actionId){
        EtapeBean etape = etapeService.toEtapeBean(etapeService.getEtapeByActionAndCode(actionId, ETAPE_ACTION.CARACTERISATION.getCode()));
        if (ETAPE_STATUT.VALIDER.getCode().equals(etape.getStatut())) {
            model.addAttribute("titrePageValidation", etapeService.getTitrePage(etape));
            model.addAttribute("caracteristiqueLectureSeule", true);
            // On test si il peuvent modifier aprés validation pour afficher le bouton
            if(rightUserService.canModifObjet( model, actionId, CODE_FUNCTION_PROFILE.MODIF_CARACTERISATION_ACTION.getCode(), TYPE_OBJET.ACTION.getCode())){
                model.addAttribute("canEdit", true);
            }  else {
                model.addAttribute("canEdit", false);
            }
        } else {
            if (ETAPE_STATUT.ENVOYER.getCode().equals(etape.getStatut())) {
                model.addAttribute("titrePageValidation", etapeService.getTitrePage(etape));// On test si il peuvent valider une étape pour afficher le bouton
                if(rightUserService.canModifObjet( model, actionId, CODE_FUNCTION_PROFILE.VALIDATE_CARACTERISATION_ACTION.getCode(), TYPE_OBJET.ACTION.getCode())){
                    model.addAttribute("canValide", true);
                    model.addAttribute("caracteristiqueLectureSeule", false);
                }  else {
                    model.addAttribute("caracteristiqueLectureSeule", true);
                }
            } else {
                if(rightUserService.canModifObjet( model, actionId, CODE_FUNCTION_PROFILE.EDIT_CARACTERISATION_ACTION.getCode(), TYPE_OBJET.ACTION.getCode())){
                    model.addAttribute("canEdit", true);
                    model.addAttribute("caracteristiqueLectureSeule", false);
                }  else {
                    model.addAttribute("caracteristiqueLectureSeule", true);
                    // Cas particuleir du porteur autre qui peut voir mais sans éditer
                    if(rightUserService.isHisObject( model, actionId, TYPE_OBJET.ACTION.getCode())){
                        model.addAttribute("canConsulte", true);
                    } else {
                        // On vérifie que étape est validé, sinon il a pas le droit de voir.
                        if (ETAPE_STATUT.VALIDER.getCode().equals(etape.getStatut())) {
                            model.addAttribute("canConsulte", true);
                        } else {
                            model.addAttribute("canConsulte", false);
                        }
                    }
                }
            }
        }
    }
}
