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

package com.efficacity.explorateurecocites.beans.biz.json;

import com.efficacity.explorateurecocites.utils.CustomValidator;

public class SimpleAction {
    private String id;
    private String name;

    // Pour la modal front onglet map
    private String urlPerimetre;
    private String longitude;
    private String latitude;


    public SimpleAction(final String id, final String name) {
        this.id = id;
        this.name = name;
    }
    public SimpleAction(final String id, final String name, final String urlPerimetre, final String longitude, final String latitude) {
        this.id = id;
        this.name = name;
        this.urlPerimetre = urlPerimetre;
        this.longitude = longitude;
        this.latitude = latitude;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLongitude() {
        if(CustomValidator.isNotEmpty(longitude)){
            return longitude.replace(",", ".");
        }
        return null;
    }

    public String getLatitude() {
        if(CustomValidator.isNotEmpty(latitude)){
            return latitude.replace(",", ".");
        }
        return null;
    }

    public String getUrlPerimetre() {
        if(CustomValidator.isNotEmpty(urlPerimetre)){
            return urlPerimetre;
        }
        return null;
    }
}
