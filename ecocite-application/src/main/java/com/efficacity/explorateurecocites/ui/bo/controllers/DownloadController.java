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

package com.efficacity.explorateurecocites.ui.bo.controllers;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by tfossurier on 21/09/2018.
 */
@Controller
@RequestMapping("/download")
public class DownloadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadController.class);

    @Value("${efficacity.explorateurecocites.path.logs}")
    private String logsPath;

    @ResponseBody
    @GetMapping("logs/{fileName}")
    public void downloadFile(Model model, Locale locale, @PathVariable String fileName, HttpServletResponse response) throws IOException {

        LOGGER.info("Download du fichier de log/Bdd : {}", fileName);

        File fileToDownload = new File(logsPath + fileName +".png");
        try (InputStream inputStream = new FileInputStream(fileToDownload)) {
            response.setHeader("content-disposition", "attachment; filename=\"" + fileName + "\"");
            response.setContentType("text/plain");

            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
            LOGGER.info( "FIN - Download du fichier de log/Bdd : {}", fileName);
        }
    }

}
