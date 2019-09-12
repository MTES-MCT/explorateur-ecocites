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

package com.efficacity.explorateurecocites.ui.bo.service.exportCSV;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by ktoomey on 20/03/2018.
 */
@Service
public class ZipService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZipService.class);
    public byte[] zipFromStreamList(List<byte[]> contents, List<String> fileNames){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int index=0;
        if(contents.size() == fileNames.size()) {
            LOGGER.info("Les fichiers sont pret a etre compresse en zip");
            try {
                ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
                for (String fileName : fileNames) {
                    //On créé une entrée par fichier à mettre dans le zip
                    ZipEntry zipEntry = new ZipEntry(fileName);
                    zipOutputStream.putNextEntry(zipEntry);
                    zipOutputStream.write(contents.get(index));
                    zipOutputStream.closeEntry();
                    index=index+1;
                }
                zipOutputStream.close();
            }
            catch(IOException e){
                LOGGER.info(e.getMessage());
            }
            return byteArrayOutputStream.toByteArray();
        }
        else{
            return "".getBytes();
        }
    }
}
