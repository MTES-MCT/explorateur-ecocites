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
