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

package com.efficacity.explorateurecocites.ui.bo.controllers.action.onglets;

import com.efficacity.explorateurecocites.beans.biz.ActionBean;
import com.efficacity.explorateurecocites.beans.biz.AssoIndicateurObjetBean;
import com.efficacity.explorateurecocites.beans.biz.EtapeBean;
import com.efficacity.explorateurecocites.beans.biz.IndicateurBean;
import com.efficacity.explorateurecocites.beans.model.*;
import com.efficacity.explorateurecocites.beans.service.*;
import com.efficacity.explorateurecocites.configuration.ServiceConfiguration;
import com.efficacity.explorateurecocites.ui.bo.forms.AjoutIndicateurObjetForm;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.ui.bo.service.RightUserService;
import com.efficacity.explorateurecocites.ui.bo.service.action.onglets.ActionIndicateurService;
import com.efficacity.explorateurecocites.ui.bo.service.notifications.EmailNotificationService;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import com.efficacity.explorateurecocites.utils.enumeration.*;
import com.efficacity.explorateurecocites.utils.log.LoggingUtils;
import com.efficacity.explorateurecocites.utils.service.email.EnvoieEmailService;
import isotope.commons.exceptions.BadRequestException;
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
@RequestMapping("/bo/actions/indicateur")
public class ActionIndicateurController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActionIndicateurController.class);

    @Autowired
    ActionService actionService ;
    @Autowired
    EtapeService etapeService;
    @Autowired
    AxeService axeService;
    @Autowired
    FinaliteService finaliteService;
    @Autowired
    IndicateurService indicateurService ;
    @Autowired
    EtiquetteAxeService etiquetteAxeService;
    @Autowired
    EtiquetteFinaliteService etiquetteFinaliteService ;
    @Autowired
    AssoActionDomainService assoActionDomainService;
    @Autowired
    AssoObjetObjectifService assoObjetObjectifService;
    @Autowired
    AssoIndicateurDomaineService assoIndicateurDomaineService;
    @Autowired
    AssoIndicateurObjectifService assoIndicateurObjectifService;
    @Autowired
    AssoIndicateurObjetService assoIndicateurObjetService;
    @Autowired
    ActionIndicateurService actionIndicateurService;
    @Autowired
    MessageSourceService messageSourceService;
    @Autowired
    RightUserService rightUserService;
    @Autowired
    AssoObjetContactService assoObjetContactService;
    @Autowired
    ContactService contactService;
    @Autowired
    EnvoieEmailService envoieEmailService;
    @Autowired
    EcociteService ecociteService;
    @Autowired
    ServiceConfiguration serviceConfiguration;
    @Autowired
    EmailNotificationService emailNotificationService;

    private String ONGLET_REALISATION = "indicateurOngletRealisation";
    private String ONGLET_RESULTAT = "indicateurOngletResultat";

    @PostMapping("request_validation/{actionId}")
    public ResponseEntity<?> requestEvaluationValidation(Model model, Locale locale, @PathVariable Long actionId) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        gestionDroit(model,actionId);
        String error = "";
        if(model.containsAttribute("indicateurLectureSeule") && !(boolean) model.asMap().get("indicateurLectureSeule")){
            error = actionIndicateurService.canRequestIndicateurValidation(actionId,locale);
            if (CustomValidator.isEmpty(error)) {
                ActionBean action = actionService.findOneAction(actionId);
                Long idUser = null;
                if (model.containsAttribute("user")){
                    idUser = ((JwtUser) model.asMap().get("user")).getId();
                }
                etapeService.updateStatusEtape(action.getEtapeByStatus(ETAPE_ACTION.CARACTERISATION), ETAPE_STATUT.ENVOYER, idUser);
                etapeService.updateStatusEtape(action.getEtapeByStatus(ETAPE_ACTION.INDICATEUR), ETAPE_STATUT.ENVOYER, idUser);
                LoggingUtils.logAction(LOGGER,LoggingUtils.ActionType.SOUMISSION,LoggingUtils.SecondaryType.CHOIX_INDICATEURS,action.getTo(),user);
                emailNotificationService.sendNotificationEmailEtapeAction(model,ETAPE_ACTION.INDICATEUR,actionId);
              return ResponseEntity.ok(true);
            }
        }else{
            error = messageSourceService.getMessageSource().getMessage("error.user.right", null, locale);
        }
        return ResponseEntity.ok(error);
    }

    @PostMapping("accepte_validation/{actionId}")
    public ResponseEntity<?> requestAccepteValidation(Model model, Locale locale, @PathVariable Long actionId) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        gestionDroit(model,actionId);
        String error = "";
        if(model.containsAttribute("canValide") && (boolean) model.asMap().get("canValide")){
            error = actionIndicateurService.canRequestIndicateurValidation(actionId,locale);
            if (CustomValidator.isEmpty(error)) {
                ActionBean action = actionService.findOneAction(actionId);
                Long idUser = null;
                if (model.containsAttribute("user")){
                    idUser = ((JwtUser) model.asMap().get("user")).getId();
                }
                etapeService.updateStatusEtape(action.getEtapeByStatus(ETAPE_ACTION.CARACTERISATION), ETAPE_STATUT.VALIDER, idUser);
                etapeService.updateStatusEtape(action.getEtapeByStatus(ETAPE_ACTION.INDICATEUR), ETAPE_STATUT.VALIDER, idUser);
                LoggingUtils.logAction(LOGGER,LoggingUtils.ActionType.VALIDATION,LoggingUtils.SecondaryType.CHOIX_INDICATEURS,action.getTo(),user);
                emailNotificationService.sendNotificationEmailEtapeAction(model, ETAPE_ACTION.INDICATEUR,actionId);
                return ResponseEntity.ok(true);
            }
        }else{
            error = messageSourceService.getMessageSource().getMessage("error.user.right", null, locale);
        }
        return ResponseEntity.ok(error);
    }

    @PostMapping("annulation_validation/{actionId}")
    public ResponseEntity<?> requestAnnulationValidation(Model model, Locale locale, @PathVariable Long actionId) {
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
            etapeService.updateStatusEtape(action.getEtapeByStatus(ETAPE_ACTION.CARACTERISATION), ETAPE_STATUT.A_RENSEIGNER, idUser);
            etapeService.updateStatusEtape(action.getEtapeByStatus(ETAPE_ACTION.INDICATEUR), ETAPE_STATUT.A_RENSEIGNER, idUser);
            LoggingUtils.logAction(LOGGER,LoggingUtils.ActionType.ANNULATION,LoggingUtils.SecondaryType.CHOIX_INDICATEURS,action.getTo(),user);
            return ResponseEntity.ok(true);
        }else{
            error = messageSourceService.getMessageSource().getMessage("error.user.right", null, locale);
        }
        return ResponseEntity.ok(error);
    }

    @GetMapping("can_request_validation/{actionId}")
    public ResponseEntity<?> canRequestEvaluationValidation(Model model, Locale locale, @PathVariable Long actionId) {
        return ResponseEntity.ok(Objects.equals(actionIndicateurService.canRequestIndicateurValidation(actionId, locale), ""));
    }

    private List<Indicateur> getListIndicateurFiltre(Long actionId, Long idDomaine, Long idObjectif, String typeIndicateur,
                                                    Map<String, String> listFiltreDomaine, Map<String, String> listFiltreObjectif, final List<String> natures, final JwtUser user) {
        List<Indicateur> listIndicateurDomain = new ArrayList<>();
        List<AssoActionDomain> assoDomaine = assoActionDomainService.getListByAction(actionId);
        if (idDomaine != null) {
            assoDomaine = assoDomaine.stream().filter(aad -> Objects.equals(aad.getIdDomain(), idDomaine)).collect(Collectors.toList());
        }
        for (AssoActionDomain assoActionDomain : assoDomaine) {
            List<AssoIndicateurDomaine> listIndicateurDomaine = assoIndicateurDomaineService.getListByDomaine(assoActionDomain.getIdDomain());
            for (AssoIndicateurDomaine assoIndicateurDomaine : listIndicateurDomaine) {
                // On recup le domaine de l'étiquette pour la liste des filtres
                EtiquetteAxe etiquetteAxe = etiquetteAxeService.findById(assoIndicateurDomaine.getIdDomaine());
                listFiltreDomaine.putIfAbsent(String.valueOf(etiquetteAxe.getId()), etiquetteAxe.getLibelle());

                Indicateur indicateur = indicateurService.findOneByIdVisibleForUser(assoIndicateurDomaine.getIdIndicateur(),
                        ECHELLE_INDICATEUR.SPECIFIQUE.getCode(), natures, user);
                if (indicateur != null) {
                    listIndicateurDomain.add(indicateur);
                }
            }
        }
        if(!ONGLET_RESULTAT.equals(typeIndicateur) ) {
            Set<Indicateur> setIndicateursDomain = new HashSet<>(listIndicateurDomain);
            listIndicateurDomain.clear();
            listIndicateurDomain.addAll(setIndicateursDomain);
            listIndicateurDomain.sort(Comparator.comparing(Indicateur::getNomCourt));
            return listIndicateurDomain;
        }
        List<Indicateur> listIndicateurObj = new ArrayList<>();
        List<AssoObjetObjectif> assoObjectif = assoObjetObjectifService.getListByAction(actionId);
        if (idObjectif != null) {
            assoObjectif = assoObjectif.stream().filter(aad -> Objects.equals(aad.getIdObjectif(), idObjectif)).collect(Collectors.toList());
        }
        for (AssoObjetObjectif assoActionObjectif : assoObjectif) {
            List<AssoIndicateurObjectif> listIndicateurDomaine = assoIndicateurObjectifService.getListByObjectif(assoActionObjectif.getIdObjectif());
            for (AssoIndicateurObjectif assoIndicateurObjectif : listIndicateurDomaine) {
                // On recup l'etiquette pour la liste des filtres
                EtiquetteFinalite etiquetteFinalite = etiquetteFinaliteService.findById(assoIndicateurObjectif.getIdObjectif());
                listFiltreObjectif.putIfAbsent(String.valueOf(etiquetteFinalite.getId()), etiquetteFinalite.getLibelle());
                Indicateur indicateur = indicateurService.findOneByIdVisibleForUser(assoIndicateurObjectif.getIdIndicateur(),
                        ECHELLE_INDICATEUR.SPECIFIQUE.getCode(), natures, user);
                if (indicateur != null) {
                    listIndicateurObj.add(indicateur);
                }
            }
        }
        //On retire les doublons residuels
        List<Indicateur> listIndicateurs = IndicateurService.mergeList(listIndicateurDomain, listIndicateurObj);
        Set<Indicateur> setIndicateurs = new HashSet<>(listIndicateurs);
        listIndicateurs.clear();
        listIndicateurs.addAll(setIndicateurs);
        listIndicateurs.sort(Comparator.comparing(Indicateur::getNomCourt));
        return listIndicateurs;
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

    @GetMapping("filtreIndicateurs/{actionId}/{typeIndicateur}")
    public String filtreIndicateurs(Model model, Locale locale, @PathVariable Long actionId, @PathVariable String typeIndicateur,
                                    @RequestParam(required = false) Long idDomaine, @RequestParam(required = false) Long idObjectif) {
        JwtUser user = (JwtUser) model.asMap().getOrDefault("user", null);
        if (user == null) {
            throw new ForbiddenException("Pas d'utilisateur connecté");
        }
        List<String> natures = getNature(typeIndicateur);
        model.addAttribute("listIndicateur",
                getListIndicateurFiltre(actionId, idDomaine, idObjectif, typeIndicateur, new LinkedHashMap<>(), new LinkedHashMap<>(), natures, user));
        return "bo/components/listeIndicateurs";
    }

    @GetMapping("loadIndicateurs/{actionId}/{typeIndicateur}")
    public String loadIndicateurs(Model model, Locale locale, @PathVariable Long actionId, @PathVariable String typeIndicateur) {
        gestionDroit(model,actionId);
        JwtUser user = (JwtUser) model.asMap().getOrDefault("user", null);
        if (user == null) {
            throw new ForbiddenException("Pas d'utilisateur connecté");
        }
        if(model.containsAttribute("canConsulte") && !(boolean) model.asMap().get("canConsulte")){
            return "bo/components/errorAccess";
        }
        List<String> natures = getNature(typeIndicateur);
        Map<String,String> listFiltreDomaine = new LinkedHashMap<>();
        Map<String,String> listFiltreObjectif = new LinkedHashMap<>();
        model.addAttribute("listIndicateur",
                getListIndicateurFiltre(actionId, null, null, typeIndicateur, listFiltreDomaine, listFiltreObjectif, natures, user));
        model.addAttribute("actionId", actionId);
        ActionBean action=actionService.findOneAction(actionId);
        if(action!=null){
            model.addAttribute("action",action);
        }
        model.addAttribute("type_indicateur", typeIndicateur);
        model.addAttribute("listFiltreDomaine", listFiltreDomaine);
        model.addAttribute("listFiltreObjectif", listFiltreObjectif);
        List<AssoIndicateurObjetBean> listAssoIndicateurAction = assoIndicateurObjetService.findAllByAction(actionId, natures);
        model.addAttribute("listAssoIndicateurAction", listAssoIndicateurAction);
        model.addAttribute("canSubmitIndicateurs", CustomValidator.isEmpty(actionIndicateurService.canRequestIndicateurValidation(actionId,locale)));

        return "bo/actions/ongletEdition/indicateurAction";
    }

    @GetMapping("loadInfoIndicateur/{indicateurId}")
    public String loadIndicateurs(Model model,@PathVariable Long indicateurId) {
        JwtUser user = (JwtUser) model.asMap().getOrDefault("user", null);
        if (user == null) {
            throw new ForbiddenException("Pas d'utilisateur connecté");
        }
        IndicateurBean indicateur =  indicateurService.findOneIndicateurValideVisibleForUser(indicateurId, user);
        model.addAttribute("indicateur", indicateur);
        return "bo/actions/ongletEdition/indicateurInfo";
    }

    private String DEFAULT_VALUE_SELECT = "Choisissez";

    @PostMapping("ajoutToAction")
    public String ajoutToAction(Model model, Locale locale, @RequestBody AjoutIndicateurObjetForm object) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        gestionDroit(model,Long.valueOf(object.getIdObjet()));

        if(model.containsAttribute("indicateurLectureSeule") && !(boolean) model.asMap().get("indicateurLectureSeule")) {

            ActionBean action = actionService.findOneAction(Long.valueOf(object.getIdObjet()));
            EtapeBean etapeBean = action.getEtapeByStatus(ETAPE_ACTION.INDICATEUR);
            boolean lectureSeule = etapeBean.getStatutEnum() != ETAPE_STATUT.A_RENSEIGNER;
            boolean hasError = false;
            if (CustomValidator.isEmpty(object.getUnite()) || DEFAULT_VALUE_SELECT.equals(object.getUnite())) {
                hasError = true;
                throw new BadRequestException( messageSourceService.getMessageSource().getMessage("error.attribut.notNull", null, locale));
            }
            if (DEFAULT_VALUE_SELECT.equals(object.getPoste_calcule())) {
                object.setPoste_calcule(null);
            }

            AssoIndicateurObjetBean assoIndicateurActionBean = assoIndicateurObjetService.findByIdObjetAndTypeObjetAndIdIndicateurAndUniteAndPosteCalcule(Long.valueOf(object.getIdObjet()), TYPE_OBJET.ACTION, Long.valueOf(object.getIdIndicateur()), object.getUnite(), object.getPoste_calcule());

            if (assoIndicateurActionBean != null) {
                hasError = true;
                throw new BadRequestException("Cette association existe déjà");
            }

            model.addAttribute("indicateurLectureSeule", lectureSeule);
            if (!hasError) {
                AssoIndicateurObjet assoIndicateurObjet = new AssoIndicateurObjet();
                assoIndicateurObjet.setIdObjet(Long.valueOf(object.getIdObjet()));
                assoIndicateurObjet.setTypeObjet(TYPE_OBJET.ACTION.getCode());
                assoIndicateurObjet.setIdIndicateur(Long.valueOf(object.getIdIndicateur()));
                assoIndicateurObjet.setPosteCalcule(object.getPoste_calcule());
                assoIndicateurObjet.setUnite(object.getUnite());
                Indicateur indicateur = indicateurService.findOne(Long.valueOf(object.getIdIndicateur())).orElse(null);
                assoIndicateurActionBean = new AssoIndicateurObjetBean(assoIndicateurObjet, indicateur);
                try {
                    actionService.markActionModified(action.getTo(), user);
                    assoIndicateurObjetService.save(assoIndicateurActionBean);
                    LoggingUtils.logAction(LOGGER,LoggingUtils.ActionType.MODIFICATION,LoggingUtils.SecondaryType.CHOIX_INDICATEURS,action.getTo(),user);
                } catch (Exception e) {
                    throw new BadRequestException(messageSourceService.getMessageSource().getMessage("error.technical", null, locale));
                }
                model.addAttribute("assoIndicateurAction", assoIndicateurActionBean);
            }
        } else {
            throw new BadRequestException(messageSourceService.getMessageSource().getMessage("error.user.right", null, locale));
        }
        return "bo/actions/ongletEdition/indicateurAjout";
    }

    @DeleteMapping("supprimeToAction/{actionId}/{indicateurAssoId}")
    public ResponseEntity<?> supprimeToAction(Model model, Locale locale, @PathVariable Long actionId, @PathVariable Long indicateurAssoId) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        Map<String, String> mapErreur = new HashMap<String, String>();
        gestionDroit(model, actionId);
        if(model.containsAttribute("indicateurLectureSeule") && !(boolean) model.asMap().get("indicateurLectureSeule")){
            AssoIndicateurObjet assoIndicateurObjet = assoIndicateurObjetService.findOne(indicateurAssoId).orElse(null);
            if (CustomValidator.isEmpty(assoIndicateurObjet)) {
                mapErreur.put("general_error", messageSourceService.getMessageSource().getMessage("error.attribut.unknown", null, locale));
            } else {
                try {
                    actionService.markActionModified(actionId, user);
                    assoIndicateurObjetService.delete(actionId, TYPE_OBJET.ACTION.getCode(), indicateurAssoId);
                    Supplier<Action> findActionById = () -> actionService.findById(actionId);
                    LoggingUtils.logActionSupplierA(LOGGER,LoggingUtils.ActionType.MODIFICATION,LoggingUtils.SecondaryType.CHOIX_INDICATEURS,findActionById,user);
                } catch (Exception e) {
                    mapErreur.put("general_error", messageSourceService.getMessageSource().getMessage("error.technical", null, locale));
                }
            }
        } else {
            mapErreur.put("general_error", messageSourceService.getMessageSource().getMessage("error.user.right", null, locale));
        }

        return ResponseEntity.ok(mapErreur);
    }

    private void gestionDroit(Model model, Long actionId){
        EtapeBean etape = etapeService.toEtapeBean(etapeService.getEtapeByActionAndCode(actionId, ETAPE_ACTION.CARACTERISATION.getCode()));
        if (ETAPE_STATUT.VALIDER.getCode().equals(etape.getStatut())) {
            model.addAttribute("titrePageValidation", etapeService.getTitrePage(etape));
            model.addAttribute("indicateurLectureSeule", true);
            // On test si il peuvent modifier aprés validation pour afficher le bouton
            if(rightUserService.canModifObjet( model, actionId, CODE_FUNCTION_PROFILE.MODIF_INDICATEUR_ACTION.getCode(), TYPE_OBJET.ACTION.getCode())){
                model.addAttribute("canEdit", true);
            }  else {
                model.addAttribute("canEdit", false);
            }
        } else {
            if (ETAPE_STATUT.ENVOYER.getCode().equals(etape.getStatut())) {
                model.addAttribute("titrePageValidation", etapeService.getTitrePage(etape));
                // On test si il peuvent valider une étape pour afficher le bouton
                if(rightUserService.canModifObjet( model, actionId, CODE_FUNCTION_PROFILE.VALIDATE_INDICATEUR_ACTION.getCode(), TYPE_OBJET.ACTION.getCode())){
                    model.addAttribute("canValide", true);
                    model.addAttribute("indicateurLectureSeule", false);
                }  else {
                    model.addAttribute("indicateurLectureSeule", true);
                }
            } else {
                if(rightUserService.canModifObjet( model, actionId, CODE_FUNCTION_PROFILE.EDIT_INDICATEUR_ACTION.getCode(), TYPE_OBJET.ACTION.getCode())){
                    model.addAttribute("canEdit", true);
                    model.addAttribute("indicateurLectureSeule", false);
                }  else {
                    model.addAttribute("indicateurLectureSeule", true);
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
