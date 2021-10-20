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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final String DATE_FORMAT_LONG = "dd/MM/yyyy HH:mm:ss";

    private DateUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String getDateAffichable(LocalDateTime date){
        if(date != null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return date.format(formatter);
        }
        return "";
    }

    public static String getHeureAffichable(LocalDateTime date){
        if(date != null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_LONG);
            return date.format(formatter);
        }
        return "";
    }

    public static String getTimestampAffichable(Timestamp timestamp){
        SimpleDateFormat formatLong = new SimpleDateFormat(DATE_FORMAT_LONG);
        if (timestamp != null){
            return formatLong.format(timestamp);
        }
        return "";
    }


}
