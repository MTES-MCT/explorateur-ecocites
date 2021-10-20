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

package com.efficacity.explorateurecocites.beans.biz;

import java.io.File;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 *
 */
public class FileWithAttributesBean {

    private File file = null;
    private FileTime dateCreationFileTime = null;

    /**
     *
      */
    public FileWithAttributesBean(){}

    /**
     * Constructeur.
     *
     * @param file fichier
     * @param basicFileAttributes attributes du fichier
     */
    public FileWithAttributesBean(File file, BasicFileAttributes basicFileAttributes){
        this.file = file;
        this.dateCreationFileTime = basicFileAttributes.creationTime();
    }

    public String getDateCreationAffichable() {
        if (dateCreationFileTime != null) {
            Timestamp dateCreationTimeStamp = new Timestamp(dateCreationFileTime.toMillis());
            return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(dateCreationTimeStamp);
        }
        return "";
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public FileTime getDateCreationFileTime() {
        return dateCreationFileTime;
    }

    public void setDateCreationFileTime(FileTime dateCreationFileTime) {
        this.dateCreationFileTime = dateCreationFileTime;
    }
}
