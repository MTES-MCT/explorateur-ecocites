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
     *
     * @param file
     * @param basicFileAttributes
     */
    public FileWithAttributesBean(File file, BasicFileAttributes basicFileAttributes){
        this.file = file;
        this.dateCreationFileTime = basicFileAttributes.creationTime();
    }

    public String getDateCreationAffichable() {
        if (dateCreationFileTime != null) {
            Timestamp dateCreationTimeStamp = new Timestamp(dateCreationFileTime.toMillis());
            if(dateCreationTimeStamp != null){
                return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(dateCreationTimeStamp);
            }
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
        dateCreationFileTime = dateCreationFileTime;
    }
}
