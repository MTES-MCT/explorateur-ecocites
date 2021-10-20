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

import com.efficacity.explorateurecocites.beans.biz.ContenuRecherche;
import com.efficacity.explorateurecocites.beans.biz.ResultatRecherche;
import com.efficacity.explorateurecocites.beans.model.Axe;
import com.efficacity.explorateurecocites.beans.service.*;
import com.efficacity.explorateurecocites.utils.ExpBeanToLightBeanUtils;
import com.efficacity.explorateurecocites.utils.enumeration.ETAT_AVANCEMENT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by tfossurier on 29/01/2018.
 */
@Controller
@RequestMapping("/")
public class ModalRechercheController {

    @Autowired
    ActionService actionService;

    @Autowired
    EcociteService ecociteService;

    @Autowired
    AxeService axeServ;

    @Autowired
    EtiquetteFinaliteService etiquetteFinaliteService;

    @Autowired
    FinaliteService finaliteService;

    @GetMapping("modal/showAxis")
    public String showAxis(Model model, @RequestParam Map<String, String> filtres){
        remplireListeFilter(model);
        model.addAttribute("selectAxe", filtres.getOrDefault("axePrincipale", "option-0"));
        filterPage(model, filtres);
        model.addAttribute("selectedAxePrincipale", filtres.getOrDefault("axePrincipale", "option-0"));
        model.addAttribute("selectedEcocite", filtres.getOrDefault("ecocite", "option-0"));
        model.addAttribute("selectedEtatAvancement", filtres.getOrDefault("etatAvancement", "option-0"));
        model.addAttribute("selectedObjectifVille", filtres.getOrDefault("objectifVille", "option-0"));
        model.addAttribute("selectedFinalite", filtres.getOrDefault("finalite", "option-0"));
        return "fo/modal/modalRecherche";
    }

    @PostMapping("modal/showAxis/filtrage")
    public String rechercheFiltre(Model model, @RequestParam Map<String, String> filtres) {
        filterPage(model, filtres);
        return "fo/modal/modalResultatRecherche";
    }

    private void remplireListeFilter(Model model){
        model.addAttribute("axis", axeServ.getMap());
        model.addAttribute("ecocite", ecociteService.getMapPublishedByNomAsc());
        model.addAttribute("etiquetteFinalite", etiquetteFinaliteService.getMap());
        model.addAttribute("finalite", finaliteService.getMap());
        model.addAttribute("progress", ETAT_AVANCEMENT.toMap());
    }

    private void filterPage(Model model, Map<String, String> filtres) {
        List<ContenuRecherche> contenus = actionService.getMapGroupeByAxe(filtres).entrySet()
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
                    contenu.setResultatRechercheList(entry.getValue()
                            .stream()
                            .filter(action -> action.getIdEcocite() != null)
                            .map(action ->
                                    ExpBeanToLightBeanUtils.copyFromAE(action, ecociteService.findById(action.getIdEcocite()))
                            ).collect(Collectors.toList()));
                    return contenu;
                })
                .collect(Collectors.toList());
        model.addAttribute("contenuRecherche", contenus);
    }

    @GetMapping("modal/recherche/search")
    public String rechercheString(Model model, @RequestParam(value="searchedString", required=false) String searchedString){
        ContenuRecherche contenuRechercheAction = new ContenuRecherche();
        contenuRechercheAction.setResultatRechercheList(
                actionService.findAllVisibleQuery(searchedString)
                    .stream()
                    .filter(action -> action.getIdEcocite() != null)
                    .map(a -> new ResultatRecherche(a, ecociteService.findOneEcocite(a.getIdEcocite())))
                    .collect(Collectors.toList()));
        contenuRechercheAction.setTitre("Action");

        ContenuRecherche contenuRechercheEcoCite = new ContenuRecherche();
        contenuRechercheEcoCite.setResultatRechercheList(
                ecociteService.findAllVisibleQuery(searchedString)
                        .stream()
                        .map(ResultatRecherche::new)
                        .collect(Collectors.toList()));
        contenuRechercheEcoCite.setTitre("ÉcoCité");

        List<ContenuRecherche> contenus = new ArrayList<>();
        contenus.add(contenuRechercheAction);
        contenus.add(contenuRechercheEcoCite);
        model.addAttribute("contenuRecherche", contenus);
        return "fo/modal/modalResultatRecherche";
    }

    @GetMapping("modal/recherche")
    public String modalRecherche(Model model){
        return "fo/modal/modalRecherche";
    }
}

