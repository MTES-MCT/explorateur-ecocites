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

package com.efficacity.explorateurecocites.ui.bo.controllers.ecocite;


import com.efficacity.explorateurecocites.beans.biz.EcociteBean;
import com.efficacity.explorateurecocites.beans.service.EcociteService;
import com.efficacity.explorateurecocites.beans.service.EtiquetteFinaliteService;
import com.efficacity.explorateurecocites.beans.service.FinaliteService;
import com.efficacity.explorateurecocites.beans.service.RegionService;
import com.efficacity.explorateurecocites.utils.enumeration.ETAT_PUBLICATION;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class EcociteEditionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EcociteEditionController.class);

    @Autowired
    EcociteService ecociteService ;
    @Autowired
    RegionService regionServ;
    @Autowired
    FinaliteService finaliteServ;
    @Autowired
    EtiquetteFinaliteService etiquetteFinaliteServ;

    @GetMapping(value = {"ecocites/edition/{ecociteId}", "ecocites/edition/{ecociteId}/onglet/{onglet}"})
    public String getEcociteEditionView(Model model, @PathVariable Long ecociteId, @PathVariable(required = false) String onglet) {

        EcociteBean ecocite = ecociteService.findOneEcocite(ecociteId);
        model.addAttribute("ecocite", ecocite);

        // On va pas envoye toute les bean ecocite, c'est trop gros. Juste une map ID / nom
        model.addAttribute("regions", regionServ.getMapOrderByNomAsc());
        model.addAttribute("etatsPublication", ETAT_PUBLICATION.values());


        if (onglet != null) {
            switch (onglet) {
                case "categorisation":
                    model.addAttribute("ongletActif", "categorisation");
                    break;
                case "choix_indicateur":
                    model.addAttribute("ongletActif", "indicateurOngletRealisation");
                    break;
                case "mesure_indicateur":
                    model.addAttribute("ongletActif", "mesureIndicateurOnglet");
                    break;
                case "impact_programme":
                    model.addAttribute("ongletActif", "questionnaire_ecocite");
                    break;
                default:
                    model.addAttribute("ongletActif", "presentation");
                    break;
            }
        } else {
            model.addAttribute("ongletActif", "presentation");
        }
        return "bo/ecocites/editionEcocite";
    }

    @GetMapping("ecocites/edition/{ecociteId}/onglet")
    public String getActionEditionView(Model model, @PathVariable Long ecociteId) {
        EcociteBean ecocite = ecociteService.findOneEcocite(ecociteId);
        model.addAttribute("ecocite", ecocite);
        return "bo/ecocites/ongletEcocite";
    }
}
