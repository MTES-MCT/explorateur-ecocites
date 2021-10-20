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

package com.efficacity.explorateurecocites.ui.bo.controllers.indicateurs;

import com.efficacity.explorateurecocites.beans.Enums;
import com.efficacity.explorateurecocites.beans.biz.ActionBean;
import com.efficacity.explorateurecocites.beans.biz.EcociteBean;
import com.efficacity.explorateurecocites.beans.biz.EtiquetteCommonBean;
import com.efficacity.explorateurecocites.beans.biz.IndicateurBean;
import com.efficacity.explorateurecocites.beans.model.*;
import com.efficacity.explorateurecocites.beans.service.*;
import com.efficacity.explorateurecocites.ui.bo.forms.IndicateurForm;
import com.efficacity.explorateurecocites.ui.bo.forms.validator.IndicateurFormValidator;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.ui.bo.service.notifications.EmailNotificationService;
import com.efficacity.explorateurecocites.utils.ApplicationConstants;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import com.efficacity.explorateurecocites.utils.enumeration.*;
import com.efficacity.explorateurecocites.utils.log.LoggingUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import isotope.commons.exceptions.ForbiddenException;
import isotope.modules.security.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

import static com.efficacity.explorateurecocites.utils.log.LoggingUtils.ActionType.CREATION;
import static com.efficacity.explorateurecocites.utils.log.LoggingUtils.ActionType.MODIFICATION;
import static com.efficacity.explorateurecocites.utils.log.LoggingUtils.ActionType.SUPPRESSION;

/**
 * Created by rxion on 20/02/2018.
 */
@Controller
@RequestMapping("/bo/indicateurs")
public class IndicateursEditionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndicateursEditionController.class);
    @Autowired
    IndicateurService indicateurService;
    @Autowired
    ActionService actionService;
    @Autowired
    EcociteService ecociteService;
    @Autowired
    AxeService axeService;
    @Autowired
    FinaliteService finaliteService;
    @Autowired
    EtiquetteAxeService etiquetteAxeService;
    @Autowired
    EtiquetteFinaliteService etiquetteFinaliteService ;
    @Autowired
    MessageSourceService messageSourceService;
    @Autowired
    AssoIndicateurDomaineService assoIndicateurDomaineService;
    @Autowired
    AssoIndicateurObjectifService assoIndicateurObjectifService;
    @Autowired
    AssoActionDomainService assoActionDomainService;
    @Autowired
    AssoObjetObjectifService assoObjetObjectifService;
    @Autowired
    IndicateurFormValidator indicateurFormValidator;
    @Autowired
    EmailNotificationService emailNotificationService;

    @GetMapping("edition/{origine}/{idIndicateur}")
    public String loadModalIndicateur(Model model, Locale locale, @PathVariable String origine, @PathVariable Long idIndicateur,
                                      @RequestParam(value="idObjet", required=false) Long idObjet) {
        IndicateurBean indicateur = null;
        ObjectWriter ow = new ObjectMapper().writer();
        List<Finalite> objectifs = finaliteService.getList();
        List<Axe> domaines = axeService.findAll();

        JwtUser user = (JwtUser) model.asMap().getOrDefault("user", null);
        Enums.ProfilsUtilisateur profil = Enums.ProfilsUtilisateur.getByCode((String) model.asMap().get("userProfileCode"));
        if (user == null || profil == null) {
            throw new ForbiddenException();
        }
        if(idIndicateur != -1){
            indicateur = indicateurService.findOneIndicateur(idIndicateur);
        }

        if(indicateur != null){
            model.addAttribute("indicateur", indicateur);

            try {
                List<AssoIndicateurObjectif> listIndicateurObjectif = assoIndicateurObjectifService.getListByIndicateur(idIndicateur);
                List<String> idSelectedObjectifs = new ArrayList<>();
                for(AssoIndicateurObjectif assos : listIndicateurObjectif){
                    idSelectedObjectifs.add(String.valueOf(assos.getIdObjectif()));
                }
                String objectifsJson = ow.writeValueAsString(idSelectedObjectifs);
                model.addAttribute("selectedObjectifsJson", objectifsJson.replaceAll("(\r|\n)", " "));
            } catch (JsonProcessingException e) {
                // fait rien
            }
            try {
                List<AssoIndicateurDomaine> listIndicateurDomaine = assoIndicateurDomaineService.getListByIndicateur(idIndicateur);
                List<String> idSelectedDomains = new ArrayList<>();
                for(AssoIndicateurDomaine assos : listIndicateurDomaine){
                    idSelectedDomains.add(String.valueOf(assos.getIdDomaine()));
                }
                String selectedDomainsJson = ow.writeValueAsString(idSelectedDomains);
                model.addAttribute("selectedDomainsJson", selectedDomainsJson.replaceAll("(\r|\n)", " "));
            } catch (JsonProcessingException e) {
                // fait rien
            }


            if(!TYPE_OBJET.ECOCITE.getCode().equals(origine) && !TYPE_OBJET.ACTION.getCode().equals(origine)) {
                // On vient de la liste => on peut éditer
                if(user.getAuthorities().contains(new SimpleGrantedAuthority(CODE_FUNCTION_PROFILE.EDIT_INDICATEUR.getCode()))){
                    if((profil.equals(Enums.ProfilsUtilisateur.PORTEUR_ACTION) || profil.equals(Enums.ProfilsUtilisateur.REFERENT_ECOCITE))
                            && !user.getEmail().equals(indicateur.getUserCreation())) {
                        model.addAttribute("typeModal", 0);
                    }else {
                        model.addAttribute("typeModal", 4);
                    }
                } else {
                    model.addAttribute("typeModal", 0);
                }
            } else {
                model.addAttribute("typeModal", 0);
            }
        } else {
            if(user.getAuthorities().contains(new SimpleGrantedAuthority(CODE_FUNCTION_PROFILE.CREATION_INDICATEUR.getCode()))) {
                if (TYPE_OBJET.ECOCITE.getCode().equals(origine)) {
                    model.addAttribute("typeModal", 3);
                    if(idObjet!=null) {
                        EcociteBean ecocite=ecociteService.findOneEcocite(idObjet);
                        if(ecocite!=null){
                            String nomOrigineFromEco="ÉcoCité "+ecocite.getNom();
                            model.addAttribute("origineEcociteAuto", nomOrigineFromEco);
                        }
                        try {
                            List<AssoObjetObjectif> listEcociteObjectif = assoObjetObjectifService.getListByEcocite(idObjet);
                            List<String> idSelectedObjectifs = new ArrayList<>();
                            for (AssoObjetObjectif assos : listEcociteObjectif) {
                                idSelectedObjectifs.add(String.valueOf(assos.getIdObjectif()));
                            }
                            String objectifsJson = ow.writeValueAsString(idSelectedObjectifs);
                            model.addAttribute("selectedObjectifsJson", objectifsJson.replaceAll("(\r|\n)", " "));
                        } catch (JsonProcessingException e) {
                            // fait rien
                        }
                    }
                } else if (TYPE_OBJET.ACTION.getCode().equals(origine)) {
                    model.addAttribute("typeModal", 2);
                    if(idObjet!=null) {
                        ActionBean action=actionService.findOneAction(idObjet);
                        if(action!=null) {
                            EcociteBean ecocite = ecociteService.findOneEcocite(action.getIdEcocite());
                            if (ecocite != null) {
                                String nomOrigineFromEco = "ÉcoCité " + ecocite.getNom();
                                model.addAttribute("origineEcociteAuto", nomOrigineFromEco);
                            }
                        }
                        try {
                            List<AssoObjetObjectif> listActionObjectif = assoObjetObjectifService.getListByAction(idObjet);
                            List<String> idSelectedObjectifs = new ArrayList<>();
                            for (AssoObjetObjectif assos : listActionObjectif) {
                                idSelectedObjectifs.add(String.valueOf(assos.getIdObjectif()));
                            }
                            String objectifsJson = ow.writeValueAsString(idSelectedObjectifs);
                            model.addAttribute("selectedObjectifsJson", objectifsJson.replaceAll("(\r|\n)", " "));
                        } catch (JsonProcessingException e) {
                            // fait rien
                        }
                        try {
                            List<AssoActionDomain> listActionDomaine = assoActionDomainService.getListByAction(idObjet);
                            List<String> idSelectedDomains = new ArrayList<>();
                            for (AssoActionDomain assos : listActionDomaine) {
                                idSelectedDomains.add(String.valueOf(assos.getIdDomain()));
                            }
                            String selectedDomainsJson = ow.writeValueAsString(idSelectedDomains);
                            model.addAttribute("selectedDomainsJson", selectedDomainsJson.replaceAll("(\r|\n)", " "));
                        } catch (JsonProcessingException e) {
                            // fait rien
                        }
                    }
                } else {
                    model.addAttribute("typeModal", 1);
                }
            } else {
                throw new ForbiddenException("Vous n'avez pas les droits pour effectuer cette action");
            }
        }

        if(Enums.ProfilsUtilisateur.ACCOMPAGNATEUR.equals(profil) || Enums.ProfilsUtilisateur.ADMIN.equals(profil)){
            model.addAttribute("userIsAdmin", true);
        } else {
            model.addAttribute("userIsAdmin", false);
        }
        model.addAttribute("natures", Arrays.asList(NATURE_INDICATEUR.values()));
        model.addAttribute("echelles", Arrays.asList(ECHELLE_INDICATEUR.values()));
        model.addAttribute("etatsIndicateur", Arrays.asList(ETAT_VALIDATION.values()));
        model.addAttribute("etatsBibliotheque", Arrays.asList(ETAT_BIBLIOTHEQUE.values()));
        model.addAttribute("typesMesure", Arrays.asList(TYPE_MESURE.values()));
        model.addAttribute("originsChoices", ORIGINE_INDICATEUR.values());

        // Dans tout les cas on charge les objectifs
        List<EtiquetteFinalite> etiquettesobjectifs = etiquetteFinaliteService.findAll();
        model.addAttribute("objectifs", etiquettesobjectifs);
        try {
            String objectifsJson = ow.writeValueAsString(EtiquetteCommonBean.toBeanListFinalite(etiquettesobjectifs, objectifs));
            model.addAttribute("objectifsJson", objectifsJson.replaceAll("(\r|\n)", " "));
        } catch (JsonProcessingException e) {
            model.addAttribute("objectifsJson", "[]");
        }
        List<EtiquetteAxe> etiquettesdomaines = etiquetteAxeService.findAll();
        model.addAttribute("domaines", etiquettesdomaines);
        try {
          String domainesJson = ow.writeValueAsString(EtiquetteCommonBean.toBeanListDomain(etiquettesdomaines, domaines));
          model.addAttribute("domainesJson", domainesJson.replaceAll("(\r|\n)", " "));
        } catch (JsonProcessingException e) {
          model.addAttribute("domainesJson", "[]");
        }


        return "bo/modal/modal_ajout_indicateur_content";
    }

    @Transactional
    @PostMapping("ajoutIndicateurs/{origin}")
    public ResponseEntity creationIndicateur(@RequestBody @Valid IndicateurForm indicateurForm, Model model, @PathVariable String origin) {
        JwtUser user = ((JwtUser) model.asMap().getOrDefault("user", null));
        String profilCode = (String) model.asMap().getOrDefault("userProfileCode", Enums.ProfilsUtilisateur.VISITEUR_PUBLIC.getCode());
        if (user != null) {
            if (user.getAuthorities().contains(new SimpleGrantedAuthority(CODE_FUNCTION_PROFILE.CREATION_INDICATEUR.getCode()))) {
                Indicateur indic = null;
                if (Objects.equals(profilCode, Enums.ProfilsUtilisateur.ADMIN.getCode()) ||
                        Objects.equals(profilCode, Enums.ProfilsUtilisateur.ACCOMPAGNATEUR.getCode())) {
                    indic = indicateurService.createOneIndicateurAdmin(indicateurForm, user);
                } else {
                    switch (origin) {
                        case "action":
                            indic = indicateurService.createOneActionIndicateur(indicateurForm, user);
                            break;
                        case "ecocite":
                            indic = indicateurService.createOneEcociteIndicateur(indicateurForm, user);
                            break;
                        default:
                            indic = indicateurService.createOneIndicateur(indicateurForm, user);
                    }
                }
                if (indic != null) {
                    assoIndicateurDomaineService.createSeveral(indic, indicateurForm.getDomaines());
                    assoIndicateurObjectifService.createSeveral(indic, indicateurForm.getObjectifs());
                    emailNotificationService.creationIndicateurNotification(model, indic.getId());
                    LoggingUtils.logAction(LOGGER, CREATION, indic, user);
                    return ResponseEntity.ok(String.valueOf(indic.getId()));
                }
            }
        }
        return ResponseEntity.badRequest().body(false);
    }

    @Transactional
    @PostMapping("modifIndicateur/{id}")
    public ResponseEntity modificationIndicateur(@RequestBody @Valid IndicateurForm indicateurForm, @PathVariable Long id, Model model) {

        JwtUser user = (JwtUser) model.asMap().getOrDefault("user", null);
        Enums.ProfilsUtilisateur profil = Enums.ProfilsUtilisateur.getByCode((String) model.asMap().getOrDefault("userProfileCode", null));
        if (user == null || profil == null) {
            throw new ForbiddenException();
        }
        if(user.getAuthorities().contains(new SimpleGrantedAuthority(CODE_FUNCTION_PROFILE.EDIT_INDICATEUR.getCode())) ) {
            Indicateur bean = indicateurService.findOne(id).orElse(null);
            if ((profil.equals(Enums.ProfilsUtilisateur.PORTEUR_ACTION) || profil.equals(Enums.ProfilsUtilisateur.REFERENT_ECOCITE))
                    && (bean == null || !user.getEmail().equals(bean.getUserCreation()))) {
                return ResponseEntity.ok(false);
            } else {
                if (bean != null && bean.getEtatValidation().equals(ETAT_VALIDATION.NON_VALIDE.getCode())) {
                    bean = indicateurService.updateIndicateur(id, indicateurForm, user);
                    if (bean.getEtatValidation().equals(ETAT_VALIDATION.VALIDE.getCode())) {
                        emailNotificationService.creationIndicateurNotification(model,id); // validation de l'accompagnateur
                    }
                }
                else {
                    bean = indicateurService.updateIndicateur(id, indicateurForm, user);
                }
                LoggingUtils.logAction(LOGGER, MODIFICATION, bean, user);
                return ResponseEntity.ok(true);
            }
        }
        return ResponseEntity.ok(false);
    }

    @InitBinder("indicateurForm")
    public void bindValidator(WebDataBinder binder) {
        binder.addValidators(indicateurFormValidator);
    }

    @Transactional
    @DeleteMapping("supprimeIndicateur/{idIndicateur}")
    public ResponseEntity supprimeToAction(Model model, Locale locale,@PathVariable Long idIndicateur) {
        JwtUser user = (JwtUser) model.asMap().getOrDefault("user", null);
        if (user == null) {
            throw new ForbiddenException();
        }
        Map<String, String> mapErreur = new HashMap<>();
        if(user.getAuthorities().contains(new SimpleGrantedAuthority(CODE_FUNCTION_PROFILE.SUPPRESSION_INDICATEUR.getCode()))) {
            Indicateur indicateur = indicateurService.findOne(idIndicateur).orElse(null);
            if (CustomValidator.isEmpty(indicateur)) {
                mapErreur.put(ApplicationConstants.GENERAL_ERROR, messageSourceService.getMessageSource().getMessage("error.attribut.unknown", null, locale));
            } else {
                try {
                    String result = indicateurService.delete(idIndicateur, locale);
                    if (CustomValidator.isNotEmpty(result)) {
                        mapErreur.put(ApplicationConstants.GENERAL_ERROR, result);
                    } else {
                        LoggingUtils.logAction(LOGGER, SUPPRESSION, indicateur, user);
                    }
                } catch (Exception e) {
                    mapErreur.put(ApplicationConstants.GENERAL_ERROR, messageSourceService.getMessageSource().getMessage(ApplicationConstants.ERROR_TECHNICAL, null, locale));
                }
            }
        } else{
            mapErreur.put(ApplicationConstants.GENERAL_ERROR, messageSourceService.getMessageSource().getMessage("error.user.right", null, locale));
        }

        return ResponseEntity.ok(mapErreur);
    }
}
