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
import com.efficacity.explorateurecocites.beans.biz.json.SimpleAction;
import com.efficacity.explorateurecocites.beans.model.Axe;
import com.efficacity.explorateurecocites.beans.model.Ecocite;
import com.efficacity.explorateurecocites.beans.model.Finalite;
import com.efficacity.explorateurecocites.beans.service.*;
import com.efficacity.explorateurecocites.utils.ExpBeanToLightBeanUtils;
import com.efficacity.explorateurecocites.utils.enumeration.FILE_TYPE;
import com.efficacity.explorateurecocites.utils.enumeration.MAITRISE_OUVRAGE;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
public class ModalEcociteController {
    @Autowired
    ActionService actionServ;

    @Autowired
    AxeService axeServ;

    @Autowired
    EcociteService ecociteServ;

    @Autowired
    RegionService regionServ;

    @Autowired
    IndicateurService indicateurService;

    @Autowired
    AssoObjetObjectifService assoObjetObjectifService;

    @Autowired
    FinaliteService finaliteService;

    @Autowired
    EtiquetteFinaliteService etiquetteFinaliteService;

    @Autowired
    AssoIndicateurObjetService assoIndicateurObjetService;

    @Autowired
    EtapeService etapeService;

    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    ContactService contactServ;

    @Autowired
    AssoObjetContactService assoObjetContactServ;

    @Autowired
    MediaService mediaService;

    @GetMapping("modal/ecocite/{ecociteId}")
    public String modalEcocite(Model model, @PathVariable("ecociteId") Long ecociteId){
        Ecocite ecociteModel = ecociteServ.findById(ecociteId);
        ecociteServ.findOne(ecociteId)
                .ifPresent(ecocite -> {
                    Map<String, List<EtiquetteCommonBean>> sousCategoriesObjectif = new LinkedHashMap<>();
                    sousCategoriesObjectif.put("1", new ArrayList<>());
                    sousCategoriesObjectif.put("2", new ArrayList<>());
                    sousCategoriesObjectif.put("3", new ArrayList<>());
                    List<Finalite> finalites = finaliteService.getList();
                    assoObjetObjectifService.getListByEcocite(ecociteId)
                            .forEach(f -> sousCategoriesObjectif.get(Integer.toString(f.getPoid())).add(EtiquetteCommonBean.toBean(etiquetteFinaliteService.findById(f.getIdObjectif()), finalites)));

                    EtiquettesBean etiquettesList = new EtiquettesBean(sousCategoriesObjectif);
                    AdapteurIndicateursBean indicateursBean = new AdapteurIndicateursBean();
                    assoIndicateurObjetService.findAllByEcocite(ecociteId)
                            .stream()
                            .map(ia -> indicateurService.findOneVisibleIndicateur(ia.getIdIndicateur()))
                            .filter(Objects::nonNull)
                            .forEach(i -> indicateursBean.put(i.getNature(), i.getTo()));
                    AdaptateurEtapesBean etapes = new AdaptateurEtapesBean(TYPE_OBJET.ECOCITE, etapeService.getEtapeByIdEcocite(ecociteId));
                    EcociteBean ecociteBean = ExpBeanToLightBeanUtils.copyFrom(ecocite, fileUploadService.getFileVisibleForEcocite(ecociteId), etiquettesList, indicateursBean, etapes);
                    model.addAttribute("ecocite", ecociteBean);
                    List<MediaBean> images = mediaService.getAllMediaForEcocite(ecociteBean);
                    model.addAttribute("images", images);
                    if (ecocite.getIdRegion() != null) {
                        regionServ.findOne(ecocite.getIdRegion())
                                .ifPresent(region ->
                                        model.addAttribute("region", region.getNom())
                                );
                    }
                });
        List<ContenuRecherche> contenus = actionServ.getMapGroupeByAxe(ecociteId).entrySet()
                .stream()
                .sorted((a, b) -> {
                    if (a.getKey() != -1 && b.getKey() != -1) {
                        return a.getKey().compareTo(b.getKey());
                    } else {
                        return b.getKey().compareTo(a.getKey());
                    }
                })
                .map(entry -> {
                    ContenuRecherche contenu = new ContenuRecherche();
                    contenu.setTitre(entry.getKey() != -1 ?
                            axeServ.findOne(entry.getKey())
                                    .map(Axe::getLibelle)
                                    .orElse("") :
                            "Pas d'axe associé"
                    );
                    contenu.setResultatRechercheList(entry.getValue().stream()
                            .map(a -> ExpBeanToLightBeanUtils.copyFrom(a, ecociteServ.findOneEcocite(a.getIdEcocite())))
                            .collect(Collectors.toList()));
                    return contenu;
                }).collect(Collectors.toList());
        model.addAttribute("actionsContenu", contenus);
        model.addAttribute("perimetreOperationnel", fileUploadService.getFirstFileEcociteOfType(ecociteId, FILE_TYPE.ECOCITE_PERIMETRE_OPERATIONNEL));
        model.addAttribute("perimetreStrategique", fileUploadService.getFirstFileEcociteOfType(ecociteId, FILE_TYPE.ECOCITE_PERIMETRE_STRATEGIQUE));
        model.addAttribute("contacts", assoObjetContactServ.findAllContactPrincipaleForEcocite(ecociteId));
        if(ecociteModel.getPartenaire() != null) {
            model.addAttribute("partenaires", Arrays.stream(ecociteModel.getPartenaire().split("\\s*;\\s*")).map(MAITRISE_OUVRAGE::getLibelleForCode).collect(Collectors.toList()));
        }
        return "fo/modal/modalEcocite";
    }

    @GetMapping("modal/ecocite/{ecociteId}/mapActionCoordonnee")
    public ResponseEntity<?> modalEcociteOngletMapActionCoordonnee(Model model, @PathVariable("ecociteId") Long ecociteId){
        List<SimpleAction> listeAction = actionServ.getListPublishedByEcocite(ecociteId)
                .stream()
                .map(a -> new SimpleAction(String.valueOf(a.getId()), a.getNomPublic(),fileUploadService.getPathUrlFile(a.getId(), FILE_TYPE.ACTION_PERIMETRE), a.getLongitude(), a.getLatitude()))
                .sorted(Comparator.comparing(SimpleAction::getName))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listeAction);
    }
}
