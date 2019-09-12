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
        LOGGER.info("Récupération de la liste des fichiers de logs dans le répertoire : " + path);
        List<FileWithAttributesBean> files = new ArrayList<>();
        try(Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        files.add(new FileWithAttributesBean(new File(String.valueOf(filePath)), Files.readAttributes(filePath, BasicFileAttributes.class)));
                    } catch (Exception e) {
                        LOGGER.error("ERREUR - Récupération de la liste des fichiers de logs dans le répertoire : " + path + " sur le filePath : " + filePath, e);
                    }
                }
            });

        } catch (IOException e) {
            LOGGER.error("ERREUR -  Récupération de la liste des fichiers de logs dans le répertoire : " + path, e);
        }
        LOGGER.info("FIN - Récupération de la liste des fichiers de logs dans le répertoire : " + path);

        return files;
    }
}
