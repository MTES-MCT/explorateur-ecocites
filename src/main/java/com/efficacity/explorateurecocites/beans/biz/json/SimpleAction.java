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
