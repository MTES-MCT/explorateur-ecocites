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
        List<byte[]> contents;
        switch (codeObjectToExport) {
            case "actions": {
                LOGGER.info("Debut de l'export des fichiers CSV pour les Actions");
                contents = exportCSVActionService.actionFilesByteStream(null);
                List<String> fileNames = createFileNameList(codeObjectToExport);
                byte[] output = zipService.zipFromStreamList(contents, fileNames);
                LOGGER.info("Les fichiers pour l'export CSV des Actions ont ete exportes");
                addFileNameHeader(response, "actions", output);
                return;
            }
            case "ecocites": {
                LOGGER.info("Debut de l'export des fichiers CSV pour les Ecocites");
                contents = exportCSVEcociteService.ecociteFilesByteStream();
                List<String> fileNames = createFileNameList(codeObjectToExport);
                byte[] output = zipService.zipFromStreamList(contents, fileNames);
                LOGGER.info("Les fichiers pour l'export CSV des Ecocites ont ete exportes");
                addFileNameHeader(response, "ecocites", output);
                return;
            }
            case "indicateurs": {
                LOGGER.info("Debut de l'export des fichiers CSV pour les Indicateurs");
                List<String> fileNames = createFileNameList(codeObjectToExport);
                contents = exportCSVIndicateurService.indicateurFilesByteStream();
                byte[] output = zipService.zipFromStreamList(contents, fileNames);
                LOGGER.info("Les fichiers pour l'export CSV des Indicateurs ont ete exportes");
                addFileNameHeader(response, "indicateurs", output);
                return;
            }
            case "contacts": {
                LOGGER.info("Debut de l'export des fichiers CSV pour les Contacts");
                List<String> fileNames = createFileNameList(codeObjectToExport);
                contents = exportCSVContactService.contactFilesByteStream();
                byte[] output = zipService.zipFromStreamList(contents, fileNames);
                LOGGER.info("Les fichiers pour l'export CSV des Contacts ont ete exportes");
                addFileNameHeader(response, "contacts", output);
                return;
            }
            case "business": {
                LOGGER.info("Debut de l'export des fichiers CSV pour les Affaires");
                List<String> fileNames = createFileNameList(codeObjectToExport);
                contents = exportCSVBusinessService.businessFilesByteStream();
                byte[] output = zipService.zipFromStreamList(contents, fileNames);
                LOGGER.info("Les fichiers pour l'export CSV des Affaires ont ete exportes");
                addFileNameHeader(response, "affaires", output);
                return;
            }
            default:
                throw new IllegalArgumentException("Illegal Argument Exception");
        }
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
