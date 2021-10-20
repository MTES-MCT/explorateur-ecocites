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

package com.efficacity.explorateurecocites.ui.bo.service.admin.tech;

import com.efficacity.explorateurecocites.beans.biz.FileWithAttributesBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FileSystemService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileSystemService.class);

    public List<FileWithAttributesBean> listAllFiles(String path){
        LOGGER.info("Récupération de la liste des fichiers de logs dans le répertoire : {}", path);
        List<FileWithAttributesBean> files = new ArrayList<>();
        try(Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        files.add(new FileWithAttributesBean(new File(String.valueOf(filePath)), Files.readAttributes(filePath, BasicFileAttributes.class)));
                    } catch (Exception e) {
                        LOGGER.error("ERREUR - Récupération de la liste des fichiers de logs dans le répertoire : {} sur le filePath : {}", path, filePath, e);
                    }
                }
            });

        } catch (IOException e) {
            LOGGER.error("ERREUR -  Récupération de la liste des fichiers de logs dans le répertoire : {}", path, e);
        }
        LOGGER.info("FIN - Récupération de la liste des fichiers de logs dans le répertoire : {}", path);

        return files;
    }
}
