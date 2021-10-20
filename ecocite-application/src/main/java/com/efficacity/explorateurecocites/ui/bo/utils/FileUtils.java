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

package com.efficacity.explorateurecocites.ui.bo.utils;

import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public class FileUtils {
    private FileUtils() {}

    static final String PDF_EXTENSION = ".pdf";
    static final String PDF_MIME_TYPE = "application/pdf";
    static final String ODT_EXTENSION = ".odt";
    static final String ODT_MIME_TYPE = "application/vnd.oasis.opendocument.text";
    static final String DOCX_EXTENSION = ".docx";
    static final String DOCX_MIME_TYPE = "application/vnd.oasis.opendocument.text";
    static final String TIKA_DOCX_MIME_TYPE = "application/x-tika-ooxml";
    static final String DOC_EXTENSION = ".doc";
    static final String TIKA_DOC_MIME_TYPE = "application/x-tika-msoffice";
    static final String DOC_MIME_TYPE = "application/msword";
    static final String TXT_EXTENSION = ".txt";
    static final String TXT_MIME_TYPE = "text/plain";
    static final String JPG_EXTENSION = ".jpg";
    static final String JPEG_EXTENSION = ".jpeg";
    static final String JPG_MIME_TYPE = "image/jpeg";
    static final String PNG_EXTENSION = ".png";
    static final String PNG_MIME_TYPE = "image/png";
    static final String BMP_EXTENSION = ".bmp";
    static final String BMP_MIME_TYPE = "image/bmp";
    static final String KML_EXTENSION = ".kml";
    static final String KML_MIME_TYPE = "application/vnd.google-earth.kml+xml";

    static final Tika TIKA = new Tika();

    public static String getExtension(String fileName) {
        if (fileName == null) {
            return "";
        }
        int len = fileName.length();
        if (len == 0) {
            return "";
        }
        char ch = fileName.charAt(len - 1);
        if (ch == '/' || ch=='\\' || ch=='.' ) {
            return "";
        }
        int dotInd = fileName.lastIndexOf('.');
        int sepInd = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
        if( dotInd <= sepInd ) {
            return "";
        } else {
            return fileName.substring(dotInd).toLowerCase();
        }
    }

    public static boolean hasCorrectExtention(final String originalFilename, final List<String> documents) {
        return documents.contains(getExtension(originalFilename));
    }

    public static boolean deleteDirectory(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDirectory(f);
            }
        }
        return file.delete();
    }

    public static boolean hasCorrectMimeType(final MultipartFile file, final List<String> mimesTypes) {
        try {
            final String fileMimeType = TIKA.detect(file.getInputStream());
            return mimesTypes.contains(fileMimeType);
        } catch (Exception ignored) {
            return false;
        }
    }
}
