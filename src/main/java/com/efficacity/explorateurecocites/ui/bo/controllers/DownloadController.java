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
        LOGGER.info("Download du fichier de log/Bdd : " +  fileName);

        File fileToDownload = new File(logsPath + fileName +".png");
        InputStream inputStream = new FileInputStream(fileToDownload);
        response.setHeader("content-disposition",  "attachment; filename=\"" + fileName +"\"");
        response.setContentType("text/plain");

        IOUtils.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
        inputStream.close();
        LOGGER.info("FIN - Download du fichier de log/Bdd : " +  fileName);
    }

}
