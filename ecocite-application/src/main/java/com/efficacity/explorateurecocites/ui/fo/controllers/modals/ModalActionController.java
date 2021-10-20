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

package com.efficacity.explorateurecocites.ui.fo.controllers.modals;

import com.efficacity.explorateurecocites.beans.biz.*;
import com.efficacity.explorateurecocites.beans.model.*;
import com.efficacity.explorateurecocites.beans.service.*;
import com.efficacity.explorateurecocites.utils.ExpBeanToLightBeanUtils;
import com.efficacity.explorateurecocites.utils.enumeration.FILE_TYPE;
import com.efficacity.explorateurecocites.utils.enumeration.MAITRISE_OUVRAGE;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by tfossurier on 29/01/2018.
 */
@Controller
@RequestMapping("/")
public class ModalActionController {

    @Autowired
    ActionService actionServ;

    @Autowired
    AxeService axeServ;

    @Autowired
    FinaliteService finaliteService;

    @Autowired
    IngenierieService ingenierieService;

    @Autowired
    EcociteService ecociteServ;

    @Autowired
    ContactService contactServ;

    @Autowired
    AssoObjetContactService assoObjetContactServ;

    @Autowired
    RegionService regionServ;

    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    AssoActionDomainService assoActionDomainService;

    @Autowired
    AssoActionIngenierieService assoActionIngenierieService;

    @Autowired
    AssoObjetObjectifService assoObjetObjectifService;

    @Autowired
    EtiquetteAxeService etiquetteAxeService;

    @Autowired
    EtiquetteFinaliteService etiquetteFinaliteService;

    @Autowired
    EtiquetteIngenierieService etiquetteIngenierieService;

    @Autowired
    AssoIndicateurObjetService assoIndicateurObjetService;

    @Autowired
    IndicateurService indicateurService;

    @Autowired
    QuestionsEvaluationService questionsEvaluationService;

    @Autowired
    ReponsesEvaluationService reponsesEvaluationService;

    @Autowired
    EtapeService etapeService;

    @Autowired
    BusinessService businessService;

    @Autowired
    MediaService mediaService;

    @GetMapping("modal/action/{actionId}")
    public String modalAction(Model model, @PathVariable("actionId") Long actionId){
        Action actionModel = actionServ.findById(actionId);
        actionServ.findOne(actionId)
                .ifPresent(action -> {
                    Map<String, List<EtiquetteCommonBean>> sousCategoriesDomaines = new LinkedHashMap<>();
                    sousCategoriesDomaines.put("1", new ArrayList<>());
                    sousCategoriesDomaines.put("2", new ArrayList<>());
                    List<Axe> domains = axeServ.getList();
                    assoActionDomainService.getListByAction(actionId)
                            .forEach(f -> sousCategoriesDomaines.get(Integer.toString(f.getPoid())).add(EtiquetteCommonBean.toBean(etiquetteAxeService.findById(f.getIdDomain()), domains)));
                    Map<String, List<EtiquetteCommonBean>> sousCategoriesObjectif = new LinkedHashMap<>();
                    sousCategoriesObjectif.put("1", new ArrayList<>());
                    sousCategoriesObjectif.put("2", new ArrayList<>());
                    sousCategoriesObjectif.put("3", new ArrayList<>());
                    List<Finalite> finalites = finaliteService.getList();
                    assoObjetObjectifService.getListByAction(actionId)
                            .forEach(f -> sousCategoriesObjectif.get(Integer.toString(f.getPoid())).add(EtiquetteCommonBean.toBean(etiquetteFinaliteService.findById(f.getIdObjectif()), finalites)));
                    Map<String, List<EtiquetteCommonBean>> sousCategoriesIngenierie = new LinkedHashMap<>();
                    sousCategoriesIngenierie.put("1", new ArrayList<>());
                    List<Ingenierie> ingenieries = ingenierieService.getList();
                    assoActionIngenierieService.getListByAction(actionId)
                            .forEach(f -> sousCategoriesIngenierie.get(Integer.toString(f.getPoid())).add(EtiquetteCommonBean.toBean(etiquetteIngenierieService.findById(f.getIdEtiquetteIngenierie()), ingenieries)));
                    EtiquettesBean etiquettesList = new EtiquettesBean(sousCategoriesDomaines, sousCategoriesObjectif, sousCategoriesIngenierie);
                    AdapteurIndicateursBean indicateursBean = new AdapteurIndicateursBean();
                    assoIndicateurObjetService.findAllByAction(actionId)
                            .stream()
                            .map(ia -> indicateurService.findOneVisibleIndicateur(ia.getIdIndicateur()))
                            .filter(Objects::nonNull)
                            .forEach(i -> indicateursBean.put(i.getNature(), i.getTo()));
                    List<QuestionsAvecReponseBean> questions = QuestionsEvaluationBean.createListFromData(reponsesEvaluationService.getReponseByIdAction(action.getId()), questionsEvaluationService.getAllQuestions());
                    AdaptateurEtapesBean etapes = new AdaptateurEtapesBean(TYPE_OBJET.ACTION, etapeService.getEtapeByIdAction(actionId));
                    List<Business> businessList = businessService.findByIdAction(action.getId());
                    ActionBean actionBean = ExpBeanToLightBeanUtils.copyFromAC(action, fileUploadService.getFileVisibleForAction(actionId), etiquettesList, indicateursBean, questions, etapes, businessList);
                    model.addAttribute("action", actionBean);
                    List<MediaBean> images = mediaService.getAllMediaForAction(actionBean);
                    model.addAttribute("images", images);
                    if (action.getIdEcocite() != null) {
                        ecociteServ.findOne(action.getIdEcocite())
                                .ifPresent(ecocite -> model.addAttribute("ecocite", ecocite));
                    }
                    if (action.getIdAxe() != null) {
                        axeServ.findOne(action.getIdAxe())
                                .ifPresent(axe -> model.addAttribute("axe", axe));
                    }
                    model.addAttribute("perimetre", fileUploadService.getFirstFileActionOfType(actionId, FILE_TYPE.ACTION_PERIMETRE));
                });
        model.addAttribute("contacts", assoObjetContactServ.findAllContactPrincipaleForAction(actionId));
        if(actionModel.getMaitriseOuvrage()!=null) {
            model.addAttribute("maitriseOuvrage", Arrays.stream(actionModel.getMaitriseOuvrage().split("\\s*;\\s*")).map(MAITRISE_OUVRAGE::getLibelleForCode).collect(Collectors.toList()));
        }
        return "fo/modal/modalAction";
    }
}
