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

package com.efficacity.explorateurecocites.ui.bo.controllers.export;

import com.efficacity.explorateurecocites.beans.biz.ActionBean;
import com.efficacity.explorateurecocites.beans.biz.EcociteBean;
import com.efficacity.explorateurecocites.beans.biz.IndicateurBean;
import com.efficacity.explorateurecocites.beans.service.ActionService;
import com.efficacity.explorateurecocites.beans.service.EcociteService;
import com.efficacity.explorateurecocites.beans.service.IndicateurService;
import com.efficacity.explorateurecocites.ui.bo.service.reporting.ActionRapportService;
import com.efficacity.explorateurecocites.ui.bo.service.reporting.EcociteRapportService;
import com.efficacity.explorateurecocites.ui.bo.service.reporting.IndicateurRapportService;
import com.efficacity.explorateurecocites.ui.bo.service.reporting.ReportService;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.efficacity.explorateurecocites.ui.bo.service.RightUserService.checkAutority;

@RequestMapping("/bo/rapports")
@Controller
public class RapportController {

    public static final String ECOCITE_JSON = "ecociteJson";
    @Value(value = "classpath:templates/bo/odt/ecocites.odt")
    private Resource templateEcocite;

    @Autowired
    ActionService actionService;

    @Autowired
    EcociteService ecociteService;

    @Autowired
    IndicateurService indicateurService;

    @Autowired
    ReportService reportService;

    @Autowired
    ActionRapportService actionRapportService;

    @Autowired
    IndicateurRapportService indicateurRapportService;

    @Autowired
    EcociteRapportService ecociteRapportService;

    @GetMapping("actions")
    public String rapportAction(Model model) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        model.addAttribute("currentMenu", "export_rapport");
        model.addAttribute("objectType", "actions");
        try {
            model.addAttribute(ECOCITE_JSON, ow.writeValueAsString(reportService.getMapAutorizedEcocite(model)));
        } catch (JsonProcessingException e) {
            model.addAttribute(ECOCITE_JSON, "[]");
        }
        return "bo/odt/rapport_actions";
    }

    @GetMapping("ecocites")
    public String rapportEcocite(Model model) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        model.addAttribute("currentMenu", "export_rapport");
        model.addAttribute("objectType", "ecocites");
        try {
            model.addAttribute(ECOCITE_JSON, ow.writeValueAsString(reportService.getMapAutorizedEcocite(model)));
        } catch (JsonProcessingException e) {
            model.addAttribute(ECOCITE_JSON, "[]");
        }
        return "bo/odt/rapport_ecocites";
    }

    @ResponseBody
    @GetMapping("actions/{id}")
    public void rapportAction(@PathVariable Long id, Model model, HttpServletResponse response) throws IOException {
        checkAutority(model, TYPE_OBJET.ACTION, id);
        ActionBean action = actionService.findOneAction(id);
        addFileNameHeaderAndSendResponse(response, TYPE_OBJET.ACTION, id, actionRapportService.generateActionReport(action));
    }

    @ResponseBody
    @GetMapping("indicateurs/{id}")
    public void rapportIndicateur(@PathVariable Long id, HttpServletResponse response) throws IOException {
        IndicateurBean indicateur = indicateurService.findOneIndicateur(id);
        addFileNameHeaderAndSendResponse(response, TYPE_OBJET.INDICATEUR, id, indicateurRapportService.generateIndicateurReport(indicateur));
    }

    @ResponseBody
    @GetMapping("ecocites/{id}")
    public void rapportEcocite(@PathVariable Long id, Model model, HttpServletResponse response) throws IOException {
        checkAutority(model, TYPE_OBJET.ECOCITE, id);
        EcociteBean ecocite = ecociteService.findOneEcocite(id);
        addFileNameHeaderAndSendResponse(response, TYPE_OBJET.ECOCITE, id, ecociteRapportService.generateEcociteReport(ecocite));
    }

    private void addFileNameHeaderAndSendResponse(HttpServletResponse response, TYPE_OBJET type, Long id, final byte[] output) throws IOException {
        String fileName =  String.format("%s-%s-%s.odt", type.getLibelle(), id, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        response.setHeader("content-disposition",  "attachment; filename=\"" + fileName +"\"");
        response.setContentType("application/vnd.oasis.opendocument.text");
        OutputStream outStream = response.getOutputStream();
        outStream.write(output);
        outStream.flush();
        response.flushBuffer();
    }
}
