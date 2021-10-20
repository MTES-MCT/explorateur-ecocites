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

package com.efficacity.explorateurecocites.ui.bo.controllers.commun;


import com.efficacity.explorateurecocites.ui.bo.service.exportCSV.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.LinkedList;
import java.util.List;

import static com.efficacity.explorateurecocites.ui.bo.service.RightUserService.checkAutority;


/**
 * Created by ktoomey on 28/02/2018.
 */


@Controller
@RequestMapping("/bo/exportCSV/{codeObjectToExport}")
public class ExportCSVController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExportCSVController.class);

    @Autowired
    ExportCSVService exportServ;

    @Autowired
    ZipService zipService;
    @Autowired
    ExportCSVEcociteService exportCSVEcociteService;
    @Autowired
    ExportCSVActionService exportCSVActionService;
    @Autowired
    ExportCSVIndicateurService exportCSVIndicateurService;
    @Autowired
    ExportCSVContactService exportCSVContactService;
    @Autowired
    ExportCSVBusinessService exportCSVBusinessService;


    private void addFileNameHeader(HttpServletResponse response, String name, final byte[] output) throws IOException {
        String fileName =  String.format("%s-%s.zip", name, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        response.setContentType("application/zip");
        response.setHeader("content-disposition",  "attachment; filename=\"" + fileName +"\"");
        OutputStream outStream = response.getOutputStream();
        outStream.write(output);
        outStream.flush();
        response.flushBuffer();
    }

    /**
     * Selon le type de l'objet : action, ecocite, indicateur, contact:
     * On créé ne liste d'objet par fichier.
     * Un objet d'une liste contient toutes les informations pour une ligne d'un fichier csv.
     */
    @ResponseBody
    @GetMapping
    public void exportCSV(@PathVariable String codeObjectToExport, Model model, HttpServletResponse response) throws IOException {
        checkAutority(model);

        switch (codeObjectToExport) {
            case "actions": {
                exportAction(codeObjectToExport, response, "actions", exportCSVActionService.actionFilesByteStream(null));
                return;
            }
            case "ecocites": {
                exportAction(codeObjectToExport, response, "Ecocites", exportCSVEcociteService.ecociteFilesByteStream());
                return;
            }
            case "indicateurs": {
                exportAction(codeObjectToExport, response, "Indicateurs", exportCSVIndicateurService.indicateurFilesByteStream());
                return;
            }
            case "contacts": {
                exportAction(codeObjectToExport, response, "Contacts", exportCSVContactService.contactFilesByteStream());
                return;
            }
            case "business": {
                exportAction(codeObjectToExport, response, "Affaires",  exportCSVBusinessService.businessFilesByteStream());
                return;
            }
            default:
                throw new IllegalArgumentException("Illegal Argument Exception");
        }
    }

    private void exportAction(@PathVariable String codeObjectToExport, HttpServletResponse response, String actionName, List<byte[]> bytes) throws IOException {
        List<byte[]> contents;
        LOGGER.info("Debut de l'export des fichiers CSV pour les {}", actionName);
        contents = bytes;
        List<String> fileNames = createFileNameList(codeObjectToExport);
        byte[] output = zipService.zipFromStreamList(contents, fileNames);
        LOGGER.info("Les fichiers pour l'export CSV des {} ont ete exportes", actionName);
        addFileNameHeader(response, actionName, output);
    }

    private List<String> createFileNameList(String codeDataToExport){
        List<String> fileNames=new LinkedList<>();
        switch (codeDataToExport){
            case "actions":
                fileNames.add("ACTIONS.csv");
                fileNames.add("ACTIONS_AFFAIRES.csv");
                fileNames.add("ACTIONS_CONTACTS.csv");
                fileNames.add("ACTIONS_DOMAINES.csv");
                fileNames.add("ACTIONS_OBJECTIFS.csv");
                fileNames.add("ACTIONS_MISSION_INGE.csv");
                fileNames.add("ACTIONS_EVAL_INNOVATION.csv");
                fileNames.add("ACTIONS_QUESTIONNAIRES.csv");
                fileNames.add("ACTIONS_INDICATEURS.csv");
                break;
            case "ecocites":
                fileNames.add("ECOCITES.csv");
                fileNames.add("ECOCITES_OBJECTIFS.csv");
                fileNames.add("ECOCITES_CONTACTS.csv");
                fileNames.add("ECOCITES_QUESTIONNAIRES.csv");
                fileNames.add("ECOCITES_INDICATEURS.csv");
                break;
            case "indicateurs":
                fileNames.add("INDICATEURS.csv");
                fileNames.add("INDICATEURS_OBJECTIFS.csv");
                fileNames.add("INDICATEURS_DOMAINES.csv");
                fileNames.add("INDICATEURS_ACTIONS.csv");
                fileNames.add("INDICATEURS_ECOCITES.csv");
                break;
            case "contacts":
                fileNames.add("CONTACTS.csv");
                break;
            case "business":
                fileNames.add("AFFAIRES.csv");
                break;
            default:
                break;
        }
        return fileNames;
    }
}
