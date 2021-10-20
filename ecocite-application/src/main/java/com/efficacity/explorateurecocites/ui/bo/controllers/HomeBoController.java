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

package com.efficacity.explorateurecocites.ui.bo.controllers;

import com.efficacity.explorateurecocites.beans.Enums;
import com.efficacity.explorateurecocites.beans.biz.ActionBean;
import com.efficacity.explorateurecocites.beans.biz.EcociteBean;
import com.efficacity.explorateurecocites.beans.biz.FileWithAttributesBean;
import com.efficacity.explorateurecocites.beans.model.Action;
import com.efficacity.explorateurecocites.beans.service.*;
import com.efficacity.explorateurecocites.configuration.ServiceConfiguration;
import com.efficacity.explorateurecocites.ui.bo.service.GrapheService;
import com.efficacity.explorateurecocites.ui.bo.service.RightUserService;
import com.efficacity.explorateurecocites.ui.bo.service.admin.tech.FileSystemService;
import com.efficacity.explorateurecocites.utils.enumeration.ETAPE_ACTION;
import com.efficacity.explorateurecocites.utils.enumeration.ETAPE_STATUT;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import i2.application.cerbere.commun.Cerbere;
import i2.application.cerbere.commun.CerbereConnexionException;
import isotope.commons.entities.BaseEntity;
import isotope.commons.exceptions.ForbiddenException;
import isotope.modules.security.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.utils.enumeration.TYPE_FINANCEMENT.ingenierieTypes;
import static com.efficacity.explorateurecocites.utils.enumeration.TYPE_FINANCEMENT.investissementTypes;

/**
 * Created by tfossurier on 10/01/2018.
 */
@Controller
@RequestMapping("/bo")
public class HomeBoController {


    private static final Logger LOGGER = LoggerFactory.getLogger(HomeBoController.class);
    @Autowired
    AxeService axeService;

    @Autowired
    ActionService actionService;

    @Autowired
    EcociteService ecociteService;

    @Autowired
    AssoIndicateurObjetService assoIndicateurObjetService;

    @Autowired
    IndicateurService indicateurService;

    @Autowired
    EtapeService etapeService;

    @Autowired
    AssoObjetObjectifService assoObjetObjectifService;

    @Autowired
    FinaliteService finaliteService;

    @Autowired
    GrapheService grapheService;

    @Autowired
    RightUserService rightUserService;

    @Autowired
    ServiceConfiguration serviceConfiguration;

    @Autowired
    FileSystemService fileSystemService;

    @Value("${efficacity.explorateurecocites.path.logs}")
    private String logsPath;
    @Value("${efficacity.explorateurecocites.path.dump}")
    private String dumpPath;

    @GetMapping("deconnexion")
    public void deconnexion(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (session != null) {
                SecurityContextHolder.clearContext();
                session.invalidate();
            }
            Cerbere cerbere = Cerbere.creation();
            cerbere.logoff(request, response, serviceConfiguration.getPublicUrl());
        } catch (CerbereConnexionException e) {
            LOGGER.error("Erreur lors de la deconnexion: ", e);
        }
    }

    @GetMapping()
    public String index(Model model) {
        JwtUser user = ((JwtUser) model.asMap().getOrDefault("user", null));
        Enums.ProfilsUtilisateur profil = Enums.ProfilsUtilisateur.getByCode((String) model.asMap().getOrDefault("userProfileCode", null));
        // Remplissage de l'accueil en fonction du profil de l'utilisateur connecté
        // les porteur d'action et ecocite peuvent voir que leurs Actions et ÉcoCités
        if (user == null || profil == null) {
            throw new ForbiddenException();
        }
        switch (profil) {
            case ADMIN_TECH:
                return homeAdminTech(model);
            case ACTEUR_ECOCITE_AUTRE:
            case REFERENT_ECOCITE:
                return homeReferentEcocite(model);
            case ADMIN:
            case ACCOMPAGNATEUR:
                return homeReferent(model);
            case PORTEUR_ACTION:
                // Dans tout les cas on prend les action du profile
                if (user.getAuthorities().contains(new SimpleGrantedAuthority(TYPE_OBJET.ACTION.getCode() + "_ALL"))) {
                    model.addAttribute("actions", actionService.findAllByOrderByNomAsc());
                } else {
                    List<Long> listId = new ArrayList<>();
                    user.getAuthorities().forEach(authoritie -> {
                        if (authoritie.getAuthority().startsWith(TYPE_OBJET.ACTION.getCode() + "_")) {
                            listId.add(Long.valueOf(authoritie.getAuthority().substring(7)));
                        }
                    });
                    List<ActionBean> listActions = new ArrayList<>(actionService.findAllByIdIn(listId));
                    // On tris par nom
                    listActions.sort(Comparator.comparing(ActionBean::getNomPublic));
                    model.addAttribute("actions", listActions);
                    model.addAttribute("profilUtilisateur",profil.getCode());
                }
                model.addAttribute("nomEtapeQuestionnaire",ETAPE_ACTION.CONTEXTE_ET_FACTEUR.getLibelle());
                model.addAttribute("renseignementIndicateur",ETAPE_ACTION.MESURE_INDICATEUR.getLibelle());
                return "bo/home/index";
            case VISITEUR_PUBLIC:
            default:
                return "redirect:/";
        }
    }

    @GetMapping("admin/tech")
    public String homeAdminTech(Model model){
        List<FileWithAttributesBean> filesLog = fileSystemService.listAllFiles(logsPath);
        List<FileWithAttributesBean> filesDump = fileSystemService.listAllFiles(dumpPath);
        model.addAttribute("filesLog", filesLog);
        model.addAttribute("filesDump", filesDump);
        model.addAttribute("dumpPath", dumpPath);
        model.addAttribute("logsPath", logsPath);
        return "/bo/home/homeAdminTech";
    }

    @GetMapping("referent")
    public String homeReferent(Model model) {
        List<EcociteBean> ecocites = ecociteService.findAllEcocite();
        grapheService.fillGraphModel(model, ecocites);
        model.addAttribute("ecocitesProgramme", ecocites.size() + "");
        List<Action> listeActions = actionService.getList(null);
        LocalDateTime dateActuelle = LocalDateTime.now();
        final Long actionsMaj30Jours = listeActions.stream()
                .filter(action ->
                        action.getDateModification() != null &&
                                dateActuelle.isAfter(action.getDateModification()) &&
                                ChronoUnit.DAYS.between(dateActuelle, action.getDateModification()) < 30)
                .count();
        final Long nbrEvaluationAValider = etapeService.countEtapeByActionCodeAndStatus(listeActions.stream().map(Action::getId).collect(Collectors.toList()), ETAPE_ACTION.EVALUATION_INNOVATION, ETAPE_STATUT.ENVOYER);
        //Nombre d'actions modifiées sous 30 jours
        model.addAttribute("actionsMaj30Jours", actionsMaj30Jours + "");
        //Nombre d'actions dans le Programme
        model.addAttribute("actionsProgramme", listeActions.size() + "");
        //Nombre d'actions ingenierie
        model.addAttribute("actionsIng", actionService.getCountFiltreTypeFinancement(ingenierieTypes).toString());
        //Nombre d'actions investissement
        model.addAttribute("actionsInv", actionService.getCountFiltreTypeFinancement(investissementTypes).toString());
        model.addAttribute("nouveauIndicAValider", indicateurService.countNonValide());
        //Nombre des evaulations d'innovation d'action a valider
        model.addAttribute("evalAValider", nbrEvaluationAValider + "");
        return "bo/home/homeReferent";
    }

    @GetMapping("referent/ecocite")
    public String homeReferentEcocite(Model model) {
        List<Long> listeEcociteId = rightUserService.getUserRightListeEcociteID(model);
        if(listeEcociteId.isEmpty()){
            return "bo/home/homeReferentEcocite";
        }
        else {
            List<EcociteBean> listeEcociteBean = ecociteService.findAllByIdIn(listeEcociteId);
            listeEcociteBean.sort(Comparator.comparing(EcociteBean::getNom));
            model.addAttribute("ecocites", listeEcociteBean);
            grapheService.fillGraphModel(model, listeEcociteBean);
            Long firstEcociteId = listeEcociteBean.get(0).getId();
            model.addAttribute("selectedEcocite", ecociteService.findOneEcocite(firstEcociteId));
            List<Action> listeActionsFromEcocites = actionService.getListFiltreIdEcocite(Collections.singletonList(firstEcociteId.toString()));
            String nbrActions = listeActionsFromEcocites.size() + "";
            model.addAttribute("nbrActionsFromEcocites", nbrActions);
            //Nombre d'actions d'ingenierie
            model.addAttribute("actionsIng", actionService.getCountFiltreTypeFinancement(ingenierieTypes, Collections.singletonList(firstEcociteId.toString())).toString());
            model.addAttribute("actionsInv", actionService.getCountFiltreTypeFinancement(investissementTypes, Collections.singletonList(firstEcociteId.toString())).toString());
            List<Long> idActions = listeActionsFromEcocites.stream().map(BaseEntity::getId).collect(Collectors.toList());
            //Nombre d'indicateur a valider
            model.addAttribute("indicValide", etapeService.countEtapeByActionCodeAndStatus(idActions, ETAPE_ACTION.INDICATEUR, ETAPE_STATUT.VALIDER).toString());
            //Nombre d'indicateur validées
            model.addAttribute("indicAValider", etapeService.countEtapeByActionCodeAndStatus(idActions, ETAPE_ACTION.INDICATEUR, ETAPE_STATUT.ENVOYER).toString());
            //Nombre d'actions validees
            model.addAttribute("actionValide", etapeService.countEtapeByActionCodeAndStatus(idActions, ETAPE_ACTION.CARACTERISATION, ETAPE_STATUT.VALIDER).toString());
            //Nombre d'actions en attente de validation
            model.addAttribute("actionAValider", etapeService.countEtapeByActionCodeAndStatus(idActions, ETAPE_ACTION.CARACTERISATION, ETAPE_STATUT.ENVOYER).toString());
            model.addAttribute("CategorisationCode", ETAPE_STATUT.ENVOYER.getCode());
            model.addAttribute("ChoixIndicateurCode", ETAPE_STATUT.ENVOYER.getCode());
            return "bo/home/homeReferentEcocite";
        }
    }

    @GetMapping("referent/ecocite/vignettes/{ecociteId}")
    public String homeReferentEcocite(Model model, @PathVariable Long ecociteId) {
        List<Long> listeEcociteId = rightUserService.getUserRightListeEcociteID(model);
        List<EcociteBean> listeEcociteBean = ecociteService.findAllByIdIn(listeEcociteId);
        for(EcociteBean ecociteBean : listeEcociteBean) {
            if(ecociteId.equals(ecociteBean.getId())) {
                model.addAttribute("selectedEcocite", ecociteBean);
                List<Action> listeActionsFromEcocites = actionService.findAllActionForEcocite(ecociteBean);
                String nbrActions = listeActionsFromEcocites.size() + "";
                model.addAttribute("nbrActionsFromEcocites", nbrActions);
                model.addAttribute("actionsIng", actionService.getCountFiltreTypeFinancement(ingenierieTypes, Collections.singletonList(ecociteBean.getId().toString())).toString());
                model.addAttribute("actionsInv", actionService.getCountFiltreTypeFinancement(investissementTypes, Collections.singletonList(ecociteBean.getId().toString())).toString());
                List<Long> idActions = listeActionsFromEcocites.stream().map(BaseEntity::getId).collect(Collectors.toList());
                //Nombre d'indicateur a valider
                model.addAttribute("indicValide", etapeService.countEtapeByActionCodeAndStatus(idActions, ETAPE_ACTION.INDICATEUR, ETAPE_STATUT.VALIDER).toString());
                //Nombre d'indicateur validées
                model.addAttribute("indicAValider", etapeService.countEtapeByActionCodeAndStatus(idActions, ETAPE_ACTION.INDICATEUR, ETAPE_STATUT.ENVOYER).toString());
                //Nombre d'actions validees
                model.addAttribute("actionValide", etapeService.countEtapeByActionCodeAndStatus(idActions, ETAPE_ACTION.CARACTERISATION, ETAPE_STATUT.VALIDER).toString());
                //Nombre d'actions en attente de validation
                model.addAttribute("actionAValider", etapeService.countEtapeByActionCodeAndStatus(idActions, ETAPE_ACTION.CARACTERISATION, ETAPE_STATUT.ENVOYER).toString());
                model.addAttribute("CategorisationCode", ETAPE_STATUT.ENVOYER.getCode());
                model.addAttribute("ChoixIndicateurCode", ETAPE_STATUT.ENVOYER.getCode());
            }
        }
        grapheService.fillGraphModel(model, listeEcociteBean); //Arrays.asList(ecociteBean) pour la selectedEcocite
        model.addAttribute("ecocites", listeEcociteBean);
        return "bo/home/vignettesRefEco";
    }
}
