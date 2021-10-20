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

import com.efficacity.explorateurecocites.beans.model.LibelleFo;
import com.efficacity.explorateurecocites.utils.enumeration.LIBELLE_FO_CODE;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.time.format.DateTimeFormatter;

public class LibelleFoBean {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");

    private String date;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String description;
    private String code;
    private String texte;
    private String user;

    public LibelleFoBean() {}

    public LibelleFoBean(LibelleFo libelleFo) {
        this.date = formatter.format(libelleFo.getDateModification());
        this.id = libelleFo.getId();
        this.description = libelleFo.getDescription();
        this.texte = libelleFo.getTexte();
        this.code = libelleFo.getCode();
        this.user = libelleFo.getUserModification();
    }

    public String getUser() {
        return user;
    }

    public void setUser(final String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(final String texte) {
        this.texte = texte;
    }

    public boolean isJavascript() {
        return this.code.equals(LIBELLE_FO_CODE.JAVASCRIPT_ANALYTICS.code);
    }
}
