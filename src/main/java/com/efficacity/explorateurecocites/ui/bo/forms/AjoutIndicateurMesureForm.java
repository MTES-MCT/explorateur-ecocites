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

package com.efficacity.explorateurecocites.ui.bo.forms;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by rxion on 14/02/2018.
 */
public class AjoutIndicateurMesureForm {

    @NotBlank
    private String idAssoObjetIndicateur;

    @NotBlank(message = "La valeur ne peut pas être vide")
    private String value;

    @NotBlank(message = "La date ne peut pas être vide")
    @Size(max=255)
    private String date;

    @Size(max=255)
    private String type;

    public String getIdAssoObjetIndicateur() {
        return idAssoObjetIndicateur;
    }

    public void setIdAssoObjetIndicateur(String idAssoObjetIndicateur) {
        this.idAssoObjetIndicateur = idAssoObjetIndicateur;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
