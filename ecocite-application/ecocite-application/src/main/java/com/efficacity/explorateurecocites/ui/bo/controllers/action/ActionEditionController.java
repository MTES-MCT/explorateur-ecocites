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

package com.efficacity.explorateurecocites.ui.bo.controllers.action;


import com.efficacity.explorateurecocites.beans.biz.ActionBean;
import com.efficacity.explorateurecocites.beans.service.*;
import com.efficacity.explorateurecocites.utils.enumeration.ETAPE_ACTION;
import com.efficacity.explorateurecocites.utils.enumeration.ETAPE_STATUT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tfossurier on 10/01/2018.
 */
@Controller
@RequestMapping("bo")
public class ActionEditionController {

    public static final String ONGLET_ACTIF = "ongletActif";

    @Autowired
    ActionService actionService ;
    @Autowired
    EtapeService etapeService;
    @Autowired
    AxeService axeServ;
    @Autowired
    EtiquetteAxeService etiquetteAxeServ;
    @Autowired
    IngenierieService ingenierieServ;
    @Autowired
    EtiquetteIngenierieService etiquetteIngenierieServ;
    @Autowired
    FinaliteService finaliteServ;
    @Autowired
    EtiquetteFinaliteService etiquetteFinaliteServ;

    @GetMapping(value = {"actions/edition/{actionId}", "actions/edition/{actionId}/onglet/{onglet}"})
    public String getActionEditionView(Model model, @PathVariable Long actionId, @PathVariable(required = false) String onglet) {

        ActionBean action = actionService.findOneAction(actionId);
        model.addAttribute("action", action);

    if(action.getEtapeByStatus(ETAPE_ACTION.CARACTERISATION)!=null) {
        model.addAttribute("caracteristiqueLectureSeule", action.getEtapeByStatus(ETAPE_ACTION.CARACTERISATION).getStatutEnum() != ETAPE_STATUT.A_RENSEIGNER);
        if (!action.isTypeFinancementIngenierie()) {
            model.addAttribute("evaluationLectureSeule", action.getEtapeByStatus(ETAPE_ACTION.EVALUATION_INNOVATION).getStatutEnum() != ETAPE_STATUT.A_RENSEIGNER);
            model.addAttribute("indicateurLectureSeule", action.getEtapeByStatus(ETAPE_ACTION.INDICATEUR).getStatutEnum() != ETAPE_STATUT.A_RENSEIGNER);
        }
    }

        if (onglet != null) {
            switch (onglet) {
                case "categorisation":
                    model.addAttribute(ONGLET_ACTIF, "domaine");
                    break;
                case "choix_indicateur":
                    model.addAttribute(ONGLET_ACTIF, "indicateurOngletRealisation");
                    break;
                case "evaluation_innovation":
                    model.addAttribute(ONGLET_ACTIF, "evaluation_innovation");
                    break;
                case "mesure_indicateur":
                    model.addAttribute(ONGLET_ACTIF, "mesureIndicateurOnglet");
                    break;
                case "contexte_et_facteur":
                    model.addAttribute(ONGLET_ACTIF, action.isTypeFinancementIngenierie() ? "questionnaire_action_ingenierie" : "questionnaire_action_investissement");
                    break;
                default:
                    model.addAttribute(ONGLET_ACTIF, "presentation");
                    break;
            }
        } else {
            model.addAttribute(ONGLET_ACTIF, "presentation");
        }
        return "bo/actions/editionAction";
    }

    @GetMapping("actions/edition/{actionId}/onglet")
    public String getActionEditionView(Model model, @PathVariable Long actionId) {
        ActionBean action = actionService.findOneAction(actionId);
        model.addAttribute("action", action);
        return "bo/actions/ongletAction";
    }
}
