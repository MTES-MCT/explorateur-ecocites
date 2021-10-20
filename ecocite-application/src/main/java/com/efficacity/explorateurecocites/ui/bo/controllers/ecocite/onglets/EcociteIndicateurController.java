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

package com.efficacity.explorateurecocites.ui.bo.controllers.ecocite.onglets;


import com.efficacity.explorateurecocites.beans.biz.AssoIndicateurObjetBean;
import com.efficacity.explorateurecocites.beans.biz.EcociteBean;
import com.efficacity.explorateurecocites.beans.biz.EtapeBean;
import com.efficacity.explorateurecocites.beans.biz.IndicateurBean;
import com.efficacity.explorateurecocites.beans.model.*;
import com.efficacity.explorateurecocites.beans.service.*;
import com.efficacity.explorateurecocites.ui.bo.forms.AjoutIndicateurObjetForm;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.ui.bo.service.RightUserService;
import com.efficacity.explorateurecocites.ui.bo.service.ecocite.onglets.EcociteIndicateurService;
import com.efficacity.explorateurecocites.ui.bo.service.notifications.EmailNotificationService;
import com.efficacity.explorateurecocites.utils.ApplicationConstants;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import com.efficacity.explorateurecocites.utils.enumeration.*;
import com.efficacity.explorateurecocites.utils.log.LoggingUtils;
import isotope.commons.exceptions.ForbiddenException;
import isotope.modules.security.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by tfossurier on 10/01/2018.
 */
@Controller
@RequestMapping("/bo/ecocites/indicateur")
public class EcociteIndicateurController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EcociteIndicateurController.class);

    @Autowired
    EcociteService ecociteService;
    @Autowired
    IndicateurService indicateurService ;
    @Autowired
    FinaliteService finaliteService;
    @Autowired
    EtiquetteAxeService etiquetteAxeService;
    @Autowired
    EtiquetteFinaliteService etiquetteFinaliteService ;
    @Autowired
    AssoObjetObjectifService assoObjetObjectifService;
    @Autowired
    AssoIndicateurDomaineService assoIndicateurDomaineService;
    @Autowired
    AssoIndicateurObjectifService assoIndicateurObjectifService;
    @Autowired
    AssoIndicateurObjetService assoIndicateurObjetService;
    @Autowired
    MessageSourceService messageSourceService;
    @Autowired
    EcociteIndicateurService ecociteIndicateurService;
    @Autowired
    EtapeService etapeService;
    @Autowired
    AxeService axeService;
    @Autowired
    RightUserService rightUserService;
    @Autowired
    EmailNotificationService emailNotificationService;

    private String ONGLET_REALISATION = "indicateurOngletRealisation";
    private String ONGLET_RESULTAT = "indicateurOngletResultat";

    @PostMapping("accepte_validation/{ecociteId}")
    public ResponseEntity requestAccepteValidation(Model model, Locale locale, @PathVariable Long ecociteId) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        gestionDroit(model,ecociteId);
        String error = "";
        if(model.containsAttribute("canValide") && (boolean) model.asMap().get("canValide")){
            error = ecociteIndicateurService.canRequestIndicateurValidation(ecociteId,locale);
            if (CustomValidator.isEmpty(error)) {
                EcociteBean ecocite = ecociteService.findOneEcocite(ecociteId);
                Long idUser = null;
                if (model.containsAttribute("user")) {
                    idUser = ((JwtUser) model.asMap().get("user")).getId();
                }
                etapeService.updateStatusEtape(ecocite.getEtapeByStatus(ETAPE_ECOCITE.CARACTERISATION), ETAPE_STATUT.VALIDER, idUser);
                etapeService.updateStatusEtape(ecocite.getEtapeByStatus(ETAPE_ECOCITE.INDICATEUR), ETAPE_STATUT.VALIDER, idUser);
                LoggingUtils.logAction(LOGGER,LoggingUtils.ActionType.VALIDATION,LoggingUtils.SecondaryType.CHOIX_INDICATEURS,ecocite.getTo(),user);
                emailNotificationService.sendNotificationEmailEtapeEcocite(model, ETAPE_ECOCITE.INDICATEUR,ecociteId);
                return ResponseEntity.ok(true);
            }
        }else{
            error = messageSourceService.getMessageSource().getMessage("error.user.right", null, locale);
        }
        return ResponseEntity.ok(error);
    }

    @PostMapping("annulation_validation/{ecociteId}")
    public ResponseEntity requestAnnulationValidation(Model model, Locale locale, @PathVariable Long ecociteId) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        gestionDroit(model,ecociteId);
        String error = "";
        if(model.containsAttribute("canEdit") && (boolean) model.asMap().get("canEdit")){
            EcociteBean ecocite = ecociteService.findOneEcocite(ecociteId);
            Long idUser = null;
            if (model.containsAttribute("user")) {
                idUser = ((JwtUser) model.asMap().get("user")).getId();
            }
            etapeService.updateStatusEtape(ecocite.getEtapeByStatus(ETAPE_ECOCITE.CARACTERISATION), ETAPE_STATUT.A_RENSEIGNER, idUser);
            etapeService.updateStatusEtape(ecocite.getEtapeByStatus(ETAPE_ECOCITE.INDICATEUR), ETAPE_STATUT.A_RENSEIGNER, idUser);
            LoggingUtils.logAction(LOGGER,LoggingUtils.ActionType.ANNULATION,LoggingUtils.SecondaryType.CHOIX_INDICATEURS,ecocite.getTo(),user);
            emailNotificationService.sendNotificationEmailEtapeEcocite(model,ETAPE_ECOCITE.INDICATEUR,ecociteId);
            return ResponseEntity.ok(true);
        }else{
            error = messageSourceService.getMessageSource().getMessage("error.user.right", null, locale);
        }
        return ResponseEntity.ok(error);
    }

    @GetMapping("/can_request_validation/{ecociteId}")
    public ResponseEntity canRequestEvaluationValidation(Model model, Locale locale, @PathVariable Long ecociteId) {
        return ResponseEntity.ok(Objects.equals(ecociteIndicateurService.canRequestIndicateurValidation(ecociteId, locale), ""));
    }

    private List<String> getNature(String typeIndicateur) {
        List<String> natures = new ArrayList<>();
        if(ONGLET_RESULTAT.equals(typeIndicateur)){
            natures.add(NATURE_INDICATEUR.RESULTATS.getCode());
            natures.add(NATURE_INDICATEUR.IMPACTS.getCode());
        } else {
            natures.add(NATURE_INDICATEUR.REALISATIONS.getCode());
        }
        return natures;
    }

    private List<Indicateur> getListIndicateurFiltre(Long idEcocite, Long idDomaine, Long idObjectif, Map<String, String> listFiltreDomaine,
                                                     Map<String, String> listFiltreObjectif, final List<String> natures, final JwtUser user) {
        List<Indicateur> listIndicateurs;
        etiquetteAxeService
                .findAll().
                forEach(etiquetteAxe -> listFiltreDomaine.put(String.valueOf(etiquetteAxe.getId()), etiquetteAxe.getLibelle()));
        List<Indicateur> listIndicateurDomain = idDomaine == null ? indicateurService.getAllDomaineIndicateurVisibleByUser(ECHELLE_INDICATEUR.TERRITORIALE, natures, user)
                : indicateurService.getAllDomaineIndicateurVisibleByUser(idDomaine, ECHELLE_INDICATEUR.TERRITORIALE, natures, user);
        if(natures!=null && natures.size()>1) {
            List<Indicateur> listIndicateurObj = new ArrayList<>();
            List<AssoObjetObjectif> assoObjectif = assoObjetObjectifService.getListByEcocite(idEcocite);
            if (idObjectif != null) {
                assoObjectif = assoObjectif.stream().filter(aad -> Objects.equals(aad.getIdObjectif(), idObjectif)).collect(Collectors.toList());
            }
            for (AssoObjetObjectif assoEcociteObjectif : assoObjectif) {
                List<AssoIndicateurObjectif> listIndicateurDomaine = assoIndicateurObjectifService.getListByObjectif(assoEcociteObjectif.getIdObjectif());
                for (AssoIndicateurObjectif assoIndicateurObjectif : listIndicateurDomaine) {
                    // On recup la finalité de étiquette
                    EtiquetteFinalite etiquetteFinalite = etiquetteFinaliteService.findById(assoIndicateurObjectif.getIdObjectif());
                    if (!listFiltreObjectif.containsKey(String.valueOf(etiquetteFinalite.getId()))) {
                        listFiltreObjectif.put(String.valueOf(etiquetteFinalite.getId()), etiquetteFinalite.getLibelle());
                    }

                    Indicateur indicateur = indicateurService.findOneByIdVisibleForUser(assoIndicateurObjectif.getIdIndicateur(),
                            ECHELLE_INDICATEUR.TERRITORIALE.getCode(), natures, user);
                    if (indicateur != null) {
                        listIndicateurObj.add(indicateur);
                    }
                }
            }
            if (idDomaine == null) {
                listIndicateurs= listIndicateurObj;
            } else {
                listIndicateurs = IndicateurService.mergeList(listIndicateurDomain, listIndicateurObj);
            }

        }
        else{
            listIndicateurs = listIndicateurDomain;
        }
        //On retire les doublons residuels et on les ordonne selon le nom court
        Set<Indicateur> setIndicateurs = new HashSet<>(listIndicateurs);
        listIndicateurs.clear();
        listIndicateurs.addAll(setIndicateurs);
        listIndicateurs.sort(Comparator.comparing(Indicateur::getNomCourt));
        return listIndicateurs;
    }

    @GetMapping("filtreIndicateurs/{idEcocite}/{typeIndicateur}")
    public String filtreIndicateurs(Model model, Locale locale, @PathVariable Long idEcocite, @PathVariable String typeIndicateur,
                                    @RequestParam(required = false) Long idDomaine, @RequestParam(required = false) Long idObjectif) {
        JwtUser user = (JwtUser) model.asMap().getOrDefault("user", null);
        if (user == null) {
            throw new ForbiddenException("Pas d'utilisateur connecté");
        }
        model.addAttribute("listIndicateur",
                getListIndicateurFiltre(idEcocite, idDomaine, idObjectif, new LinkedHashMap<>(), new LinkedHashMap<>(), getNature(typeIndicateur), user));
        return "bo/components/listeIndicateurs";
    }

    @GetMapping("/loadIndicateurs/{idEcocite}/{typeIndicateur}")
    public String loadIndicateurs(Model model, Locale locale, @PathVariable Long idEcocite, @PathVariable String typeIndicateur) {

        gestionDroit(model,idEcocite);

        JwtUser user = (JwtUser) model.asMap().getOrDefault("user", null);
        if (user == null) {
            throw new ForbiddenException("Pas d'utilisateur connecté");
        }
        if(model.containsAttribute("canConsulte") && !(boolean) model.asMap().get("canConsulte")){
            return "bo/components/errorAccess";
        }

        List<String> natures = getNature(typeIndicateur);
        Map<String,String> listFiltreObjectif = new LinkedHashMap<>();
        Map<String,String> listFiltreDomaine = new LinkedHashMap<>();

        // On récupére les indicateurs selon les étiquettes selectionnées.

        model.addAttribute("ecociteId", idEcocite);
        model.addAttribute("listIndicateur", getListIndicateurFiltre(idEcocite, null, null, listFiltreDomaine, listFiltreObjectif, natures, user));
        model.addAttribute("listFiltreObjectif", listFiltreObjectif);
        model.addAttribute("listFiltreDomaine", listFiltreDomaine);
        model.addAttribute("type_indicateur", typeIndicateur);


        List<AssoIndicateurObjetBean> listAssoIndicateurEcocite = assoIndicateurObjetService.findAllByEcocite(idEcocite, natures);
        model.addAttribute("listAssoIndicateurEcocite", listAssoIndicateurEcocite);
        model.addAttribute("canSubmitIndicateurs", CustomValidator.isEmpty(ecociteIndicateurService.canRequestIndicateurValidation(idEcocite, locale)));
        return "bo/ecocites/ongletEdition/indicateurEcocite";
    }

    @GetMapping("/loadInfoIndicateur/{indicateurId}")
    public String loadInfoIndicateurs(Model model,@PathVariable Long indicateurId) {
        JwtUser user = (JwtUser) model.asMap().getOrDefault("user", null);
        if (user == null) {
            throw new ForbiddenException("Pas d'utilisateur connecté");
        }
        IndicateurBean indicateur =  indicateurService.findOneIndicateurValideVisibleForUser(indicateurId, user);
        model.addAttribute("indicateur", indicateur);
        return "bo/ecocites/ongletEdition/indicateurInfo";
    }

    private String DEFAULT_VALUE_SELECT = "Choisissez";

    @PostMapping("/ajoutToEcocite")
    public String ajoutToEcocite(Locale locale, Model model, @RequestBody AjoutIndicateurObjetForm object) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        gestionDroit(model,Long.valueOf(object.getIdObjet()));

        if(model.containsAttribute("indicateurLectureSeule") && !(boolean) model.asMap().get("indicateurLectureSeule")) {
            EcociteBean ecocite = ecociteService.findOneEcocite(Long.valueOf(object.getIdObjet()));
            EtapeBean etapeBean = ecocite.getEtapeByStatus(ETAPE_ECOCITE.INDICATEUR);
            boolean hasError = false;
            if(CustomValidator.isEmpty(object.getUnite()) || DEFAULT_VALUE_SELECT.equals(object.getUnite())){
                model.addAttribute(ApplicationConstants.GENERAL_ERROR, messageSourceService.getMessageSource().getMessage("error.attribut.notNull", null, locale));
                hasError = true;
            }
            if(DEFAULT_VALUE_SELECT.equals(object.getPoste_calcule())){
                object.setPoste_calcule(null);
            }

            AssoIndicateurObjetBean assoIndicateurEcociteBean = assoIndicateurObjetService.findByIdObjetAndTypeObjetAndIdIndicateurAndUniteAndPosteCalcule(Long.valueOf(object.getIdObjet()), TYPE_OBJET.ECOCITE, Long.valueOf(object.getIdIndicateur()),object.getUnite(), object.getPoste_calcule());

            if(assoIndicateurEcociteBean != null){
                model.addAttribute(ApplicationConstants.GENERAL_ERROR, "Cette association existe déjà");
                hasError = true;
            }

            if(!hasError) {
                AssoIndicateurObjet assoIndicateurObjet = new AssoIndicateurObjet();
                assoIndicateurObjet.setIdObjet(Long.valueOf(object.getIdObjet()));
                assoIndicateurObjet.setTypeObjet(TYPE_OBJET.ECOCITE.getCode());
                assoIndicateurObjet.setIdIndicateur(Long.valueOf(object.getIdIndicateur()));
                assoIndicateurObjet.setPosteCalcule(object.getPoste_calcule());
                assoIndicateurObjet.setUnite(object.getUnite());
                Indicateur indicateur = indicateurService.findOne(Long.valueOf(object.getIdIndicateur())).orElse(null);
                assoIndicateurEcociteBean = new AssoIndicateurObjetBean(assoIndicateurObjet, indicateur);
                try {
                    assoIndicateurObjetService.save(assoIndicateurEcociteBean);
                    LoggingUtils.logAction(LOGGER,LoggingUtils.ActionType.MODIFICATION,LoggingUtils.SecondaryType.CHOIX_INDICATEURS,ecocite.getTo(),user);
                } catch (Exception e) {
                    model.addAttribute(ApplicationConstants.GENERAL_ERROR, messageSourceService.getMessageSource().getMessage(ApplicationConstants.ERROR_TECHNICAL, null, locale));
                    return "bo/ecocites/ongletEdition/indicateurAjout";
                }
                model.addAttribute("assoIndicateurEcocite", assoIndicateurEcociteBean);
            }
        } else {
            model.addAttribute(ApplicationConstants.GENERAL_ERROR, messageSourceService.getMessageSource().getMessage("error.user.right", null, locale));
        }
        return "bo/ecocites/ongletEdition/indicateurAjout";
    }
    @DeleteMapping("/supprimeToEcocite/{idEcocite}/{indicateurAssoId}")
    public ResponseEntity supprimeToEcocite(Model model, Locale locale, @PathVariable Long idEcocite, @PathVariable Long indicateurAssoId) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        Map<String, String> mapErreur = new HashMap<String, String>();
        gestionDroit(model, idEcocite);
        if(model.containsAttribute("indicateurLectureSeule") && !(boolean) model.asMap().get("indicateurLectureSeule")){
            AssoIndicateurObjet assoIndicateurObjet = assoIndicateurObjetService.findOne(indicateurAssoId).orElse(null);
            if(CustomValidator.isEmpty(assoIndicateurObjet)){
                mapErreur.put(ApplicationConstants.GENERAL_ERROR, messageSourceService.getMessageSource().getMessage("error.attribut.unknown", null, locale));
            } else {
                try {
                    assoIndicateurObjetService.delete(idEcocite, TYPE_OBJET.ECOCITE.getCode(), indicateurAssoId);
                    Supplier<Ecocite> findEcociteById = () -> ecociteService.findById(idEcocite);
                    LoggingUtils.logActionSupplierE(LOGGER,LoggingUtils.ActionType.MODIFICATION,LoggingUtils.SecondaryType.CHOIX_INDICATEURS,findEcociteById,user);
                } catch (Exception e) {
                    mapErreur.put(ApplicationConstants.GENERAL_ERROR, messageSourceService.getMessageSource().getMessage(ApplicationConstants.ERROR_TECHNICAL, null, locale));
                }
            }
        } else {
            mapErreur.put(ApplicationConstants.GENERAL_ERROR, messageSourceService.getMessageSource().getMessage("error.user.right", null, locale));
        }
        return ResponseEntity.ok(mapErreur);
    }

    private void gestionDroit(Model model, Long ecociteId){
        EtapeBean etape = etapeService.toEtapeBean(etapeService.getEtapeByEcociteAndCode(ecociteId, ETAPE_ECOCITE.INDICATEUR.getCode()));
        if(etape!=null) {
            if (ETAPE_STATUT.VALIDER.getCode().equals(etape.getStatut())) {
                model.addAttribute("titrePageValidation", etapeService.getTitrePage(etape));
                model.addAttribute("indicateurLectureSeule", true);
                // On test si il peuvent modifier aprés validation pour afficher le bouton
                if (rightUserService.canModifObjet(model, ecociteId, CODE_FUNCTION_PROFILE.MODIF_INDICATEUR_ECOCITE.getCode(), TYPE_OBJET.ECOCITE.getCode())) {
                    model.addAttribute("canEdit", true);
                } else {
                    model.addAttribute("canEdit", false);
                }
            } else {
                if (ETAPE_STATUT.ENVOYER.getCode().equals(etape.getStatut())) {
                    model.addAttribute("titrePageValidation", etapeService.getTitrePage(etape));
                    // On test si il peuvent valider une étape pour afficher le bouton
                    if (rightUserService.canModifObjet(model, ecociteId, CODE_FUNCTION_PROFILE.VALIDATE_INDICATEUR_ECOCITE.getCode(), TYPE_OBJET.ECOCITE.getCode())) {
                        model.addAttribute("canValide", true);
                        model.addAttribute("indicateurLectureSeule", false);
                    } else {
                        model.addAttribute("indicateurLectureSeule", true);
                    }
                } else {
                    if (rightUserService.canModifObjet(model, ecociteId, CODE_FUNCTION_PROFILE.EDIT_INDICATEUR_ECOCITE.getCode(), TYPE_OBJET.ECOCITE.getCode())) {
                        model.addAttribute("canValide", true);
                        model.addAttribute("indicateurLectureSeule", false);
                    } else {
                        model.addAttribute("indicateurLectureSeule", true);
                        if (rightUserService.isHisObject(model, ecociteId, TYPE_OBJET.ECOCITE.getCode())) {
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
}
