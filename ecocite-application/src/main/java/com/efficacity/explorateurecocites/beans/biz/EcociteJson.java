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

import com.efficacity.explorateurecocites.beans.model.Ecocite;

public class EcociteJson {
    private String id;
    private String nom;
    private String latitude;
    private String longitude;

    public EcociteJson(Ecocite ecocite) {
        this.id = ecocite.getId() != null ? String.valueOf(ecocite.getId()) : "";
        this.nom = ecocite.getNom() != null ? ecocite.getNom() : "";
        this.latitude = ecocite.getLatitude() != null ? ecocite.getLatitude().replace(",", ".") : "";
        this.longitude = ecocite.getLongitude() != null ? ecocite.getLongitude().replace(",", ".") : "";
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(final String nom) {
        this.nom = nom;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(final String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(final String longitude) {
        this.longitude = longitude;
    }
}

//{
    //content : '<p class="fs-14"><a class="cursorPointer" onclick="openModalShowEcocite({0});">{1}</a></p>'.format('${ecocite.id?c}', '${ecocite.nom?js_string}'),
    //position: {
        //y: ${ecocite.latitude?replace(',','.')},
        //x: ${ecocite.longitude?replace(',','.')},
        //projection: "CRS:84"
    //},
    //url:"/img/icons/ecocite_popup_map_spot_ecocite.png"
//},
