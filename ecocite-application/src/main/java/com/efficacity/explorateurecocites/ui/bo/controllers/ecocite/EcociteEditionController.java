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

package com.efficacity.explorateurecocites.ui.bo.controllers.ecocite;


import com.efficacity.explorateurecocites.beans.biz.EcociteBean;
import com.efficacity.explorateurecocites.beans.service.EcociteService;
import com.efficacity.explorateurecocites.beans.service.EtiquetteFinaliteService;
import com.efficacity.explorateurecocites.beans.service.FinaliteService;
import com.efficacity.explorateurecocites.beans.service.RegionService;
import com.efficacity.explorateurecocites.utils.enumeration.ETAT_PUBLICATION;
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

    public static final String ONGLET_ACTIF = "ongletActif";
    @Autowired
    EcociteService ecociteService;
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
                    model.addAttribute(ONGLET_ACTIF, "categorisation");
                    break;
                case "choix_indicateur":
                    model.addAttribute(ONGLET_ACTIF, "indicateurOngletRealisation");
                    break;
                case "mesure_indicateur":
                    model.addAttribute(ONGLET_ACTIF, "mesureIndicateurOnglet");
                    break;
                case "impact_programme":
                    model.addAttribute(ONGLET_ACTIF, "questionnaire_ecocite");
                    break;
                default:
                    model.addAttribute(ONGLET_ACTIF, "presentation");
                    break;
            }
        } else {
            model.addAttribute(ONGLET_ACTIF, "presentation");
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
