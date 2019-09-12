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
                    model.addAttribute("ongletActif", "domaine");
                    break;
                case "choix_indicateur":
                    model.addAttribute("ongletActif", "indicateurOngletRealisation");
                    break;
                case "evaluation_innovation":
                    model.addAttribute("ongletActif", "evaluation_innovation");
                    break;
                case "mesure_indicateur":
                    model.addAttribute("ongletActif", "mesureIndicateurOnglet");
                    break;
                case "contexte_et_facteur":
                    model.addAttribute("ongletActif", action.isTypeFinancementIngenierie() ? "questionnaire_action_ingenierie" : "questionnaire_action_investissement");
                    break;
                default:
                    model.addAttribute("ongletActif", "presentation");
                    break;
            }
        } else {
            model.addAttribute("ongletActif", "presentation");
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
