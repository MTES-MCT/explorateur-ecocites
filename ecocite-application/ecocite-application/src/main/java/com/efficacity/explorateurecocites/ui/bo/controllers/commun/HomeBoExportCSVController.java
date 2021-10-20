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

import com.efficacity.explorateurecocites.beans.service.ActionService;
import com.efficacity.explorateurecocites.beans.service.EcociteService;
import com.efficacity.explorateurecocites.ui.bo.service.RightUserService;
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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by ktoomey on 23/03/2018.
 *
 */

@Controller
@RequestMapping("/bo/referent")
public class HomeBoExportCSVController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeBoExportCSVController.class);

    @Autowired
    ExportCSVService exportServ;

    @Autowired
    EcociteService ecociteServ;

    @Autowired
    ActionService actionServ;

    @Autowired
    ZipService zipService;

    @Autowired
    RightUserService rightUserService;
    @Autowired
    ExportCSVActionService exportActionService;
    @Autowired
    ExportCSVEcociteService exportCSVEcociteService;
    @Autowired
    ExportCSVIndicateurService exportCSVIndicateurService;
    @Autowired
    ExportCSVContactService exportCSVContactService;
    @Autowired
    ExportCSVBusinessService exportCSVBusinessService;

    private void addFileNameHeader(HttpServletResponse response, String name, final byte[] output) throws IOException {
        String fileName =  String.format("%s-%s.zip", name, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        response.setHeader("content-disposition",  "attachment; filename=\"" + fileName +"\"");
        response.setContentType("application/zip");
        OutputStream outStream = response.getOutputStream();
        outStream.write(output);
        outStream.flush();
        response.flushBuffer();
    }

    @ResponseBody
    @GetMapping("/ecocite/exportActionsCSV/{ecociteId}")
    public void exportActionsCSV(Model model, Locale locale, @PathVariable Long ecociteId, HttpServletResponse response) throws IOException {
        LOGGER.info("Verification des droits de l'utilisateur qui effectue l'export");
        List<Long> listeEcociteId = rightUserService.getUserRightListeEcociteID(model);
        if(ecociteId>-1){
            listeEcociteId.retainAll(Arrays.asList(ecociteId));
        }
        if (listeEcociteId==null || listeEcociteId.isEmpty() ){
            throw new IllegalArgumentException("Illegal Argument Exception");
        }
        LOGGER.info("Debut de l'export des fichiers CSV pour les Actions du Referent Ecocite");
        List<byte[]> contents = exportActionService.actionFilesByteStream(listeEcociteId);

        List<String> fileNames = createActionFileNameList();
        byte[] output = zipService.zipFromStreamList(contents,fileNames);

        LOGGER.info("Les fichiers pour l'export CSV des Actions ont ete exportes");
        addFileNameHeader(response, "actions.zip", output);
    }

    @ResponseBody
    @GetMapping("/exportAdminCSV")
    public void exportActionsCSV(Model model, Locale locale, HttpServletResponse response) throws IOException {
        LOGGER.info("Verification des droits de l'utilisateur qui effectue l'export");
        List<Long> listeEcociteId = rightUserService.getUserRightListeEcociteID(model);
        if (listeEcociteId==null || listeEcociteId.isEmpty() ){
            throw new IllegalArgumentException("Illegal Argument Exception");
        }
        LOGGER.info("Debut de l'export des fichiers CSV pour l'administrateur/accompagnateur");
        List<byte[]> contents = exportActionService.actionFilesByteStream(listeEcociteId);
        contents.addAll(exportCSVEcociteService.ecociteFilesByteStream());
        contents.addAll(exportCSVIndicateurService.indicateurFilesByteStream());
        contents.addAll(exportCSVContactService.contactFilesByteStream());
        contents.addAll(exportCSVBusinessService.businessFilesByteStream());

        List<String> fileNames = createCompleteFileNameList();
        byte[] output = zipService.zipFromStreamList(contents,fileNames);
        LOGGER.info("Les fichiers pour l'export CSV Admin ont ete exportes");
        addFileNameHeader(response, "explorateur_ecocite", output);
    }

    private List<String> createActionFileNameList(){
        List<String> fileNames=new LinkedList<>();
        fileNames.add("ACTIONS.csv");
        fileNames.add("ACTIONS_AFFAIRES.csv");
        fileNames.add("ACTIONS_CONTACTS.csv");
        fileNames.add("ACTIONS_DOMAINES.csv");
        fileNames.add("ACTIONS_OBJECTIFS.csv");
        fileNames.add("ACTIONS_MISSION_INGE.csv");
        fileNames.add("ACTIONS_EVAL_INNOVATION.csv");
        fileNames.add("ACTIONS_QUESTIONNAIRES.csv");
        fileNames.add("ACTIONS_INDICATEURS.csv");
        return fileNames;
    }

    private List<String> createCompleteFileNameList(){
        List<String> fileNames=new LinkedList<>();
        fileNames.add("actions/ACTIONS.csv");
        fileNames.add("actions/ACTIONS_AFFAIRES.csv");
        fileNames.add("actions/ACTIONS_CONTACTS.csv");
        fileNames.add("actions/ACTIONS_DOMAINES.csv");
        fileNames.add("actions/ACTIONS_OBJECTIFS.csv");
        fileNames.add("actions/ACTIONS_MISSION_INGE.csv");
        fileNames.add("actions/ACTIONS_EVAL_INNOVATION.csv");
        fileNames.add("actions/ACTIONS_QUESTIONNAIRES.csv");
        fileNames.add("actions/ACTIONS_INDICATEURS.csv");
        fileNames.add("ecocites/ECOCITES.csv");
        fileNames.add("ecocites/ECOCITES_OBJECTIFS.csv");
        fileNames.add("ecocites/ECOCITES_CONTACTS.csv");
        fileNames.add("ecocites/ECOCITES_QUESTIONNAIRES.csv");
        fileNames.add("ecocites/ECOCITES_INDICATEURS.csv");
        fileNames.add("indicateurs/INDICATEURS.csv");
        fileNames.add("indicateurs/INDICATEURS_OBJECTIFS.csv");
        fileNames.add("indicateurs/INDICATEURS_DOMAINES.csv");
        fileNames.add("indicateurs/INDICATEURS_ACTIONS.csv");
        fileNames.add("indicateurs/INDICATEURS_ECOCITES.csv");
        fileNames.add("contacts/CONTACTS.csv");
        fileNames.add("affaires/AFFAIRES.csv");
        return fileNames;
    }

}


