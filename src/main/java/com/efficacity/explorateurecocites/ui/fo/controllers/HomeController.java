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

package com.efficacity.explorateurecocites.ui.fo.controllers;

import com.efficacity.explorateurecocites.beans.biz.EcociteBean;
import com.efficacity.explorateurecocites.beans.biz.EcociteJson;
import com.efficacity.explorateurecocites.beans.model.Ecocite;
import com.efficacity.explorateurecocites.beans.service.ActionService;
import com.efficacity.explorateurecocites.beans.service.AxeService;
import com.efficacity.explorateurecocites.beans.service.EcociteService;
import com.efficacity.explorateurecocites.beans.service.LibelleFoService;
import com.efficacity.explorateurecocites.configuration.ServiceConfiguration;
import com.efficacity.explorateurecocites.ui.bo.service.GrapheService;
import com.efficacity.explorateurecocites.utils.enumeration.LIBELLE_FO_CODE;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tfossurier on 10/01/2018.
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    AxeService axeService;

    @Autowired
    EcociteService ecociteService;

    @Autowired
    ActionService actionService;

    @Autowired
    ServiceConfiguration serviceConfiguration;

    @Autowired
    LibelleFoService libelleFoServ;

    @Autowired
    GrapheService grapheService;

    @GetMapping
    public String index(Model model,
                        @RequestParam(value="ecocite", required=false) String ecociteId,
                        @RequestParam(value="action", required=false) String actionId,
                        @RequestParam(value="searchMode", required=false) String searchMode,
                        @RequestParam(value="axePrincipale", required=false) String axePrincipale,
                        @RequestParam(value="ecocite", required=false) String ecocite,
                        @RequestParam(value="etatAvancement", required=false) String etatAvancement,
                        @RequestParam(value="objectifVille", required=false) String objectifVille,
                        @RequestParam(value="finalite", required=false) String finalite,
                        @RequestParam(value="searchQuery", required=false) String searchQuery) throws JAXBException {
        model.addAttribute("axis", axeService.getList());

        List<Ecocite> ecocites = ecociteService.getListVisible();
        model.addAttribute("ecocites", ecocites);
        model.addAttribute("geoportailApiKey", serviceConfiguration.getGeoportailApiKey());

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            String ecociteJson = ow.writeValueAsString(ecocites.stream().map(EcociteJson::new).collect(Collectors.toList()));

            model.addAttribute("ecocitesJson", ecociteJson);
        } catch (JsonProcessingException e) {
            model.addAttribute("ecocitesJson", "[]");
        }
        model.addAttribute("titrePrincipal", libelleFoServ.findByCode(LIBELLE_FO_CODE.TITRE_PRINCIPAL));
        model.addAttribute("sousTitrePrincipal", libelleFoServ.findByCode(LIBELLE_FO_CODE.SOUS_TITRE_PRINCIPAL));
        model.addAttribute("titreEcocite", libelleFoServ.findByCode(LIBELLE_FO_CODE.TITRE_ECOCITE));
        model.addAttribute("sousTitreEcocite", libelleFoServ.findByCode(LIBELLE_FO_CODE.SOUS_TITRE_ECOCITE));
        model.addAttribute("titreAction", libelleFoServ.findByCode(LIBELLE_FO_CODE.TITRE_ACTION));
        model.addAttribute("sousTitreAction", libelleFoServ.findByCode(LIBELLE_FO_CODE.SOUS_TITRE_ACTION));
        model.addAttribute("titreMinistere", libelleFoServ.findByCode(LIBELLE_FO_CODE.TITRE_MINISTERE));
        model.addAttribute("copyright", libelleFoServ.findByCode(LIBELLE_FO_CODE.COPYRIGHT));
        model.addAttribute("paramEcocite", ecociteId);
        model.addAttribute("paramAction", actionId);
        model.addAttribute("searchMode", searchMode);
        model.addAttribute("axePrincipale", axePrincipale);
        model.addAttribute("ecocite", ecocite);
        model.addAttribute("etatAvancement", etatAvancement);
        model.addAttribute("objectifVille", objectifVille);
        model.addAttribute("finalite", finalite);
        model.addAttribute("searchQuery", searchQuery);
        List<EcociteBean> ecociteBeans = ecociteService.findAllVisibleEcocite();
        libelleFoServ.fillDynamicNews(model);
        grapheService.fillGraphModel(model, ecociteBeans);
        return "fo/index";
    }

    @GetMapping("analytics")
    public String index(Model model) {
        model.addAttribute("javascriptAnalytics", libelleFoServ.findByCode(LIBELLE_FO_CODE.JAVASCRIPT_ANALYTICS));
        return "fo/analytics";
    }
}
