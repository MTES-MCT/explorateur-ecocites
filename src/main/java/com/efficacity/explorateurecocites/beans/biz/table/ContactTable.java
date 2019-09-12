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

package com.efficacity.explorateurecocites.beans.biz.table;

import com.efficacity.explorateurecocites.utils.table.TableColumn;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ContactTable {

    @JsonSerialize(
            using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
    )
    private Long id;

    @TableColumn(value = "contact.column.nom")
    private String nom;

    @TableColumn(value = "contact.column.prenom")
    private String prenom;

    @TableColumn(value = "contact.colum.ecocite")
    private String ecocite;

    @TableColumn(value = "contact.column.entite")
    private String entite;

    @TableColumn(value = "contact.column.fonction")
    private String fonction;

    @TableColumn(value = "contact.column.telephone")
    private String telephone;

    @TableColumn(value = "contact.column.email")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(final String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(final String prenom) {
        this.prenom = prenom;
    }

    public String getEntite() {
        return entite;
    }

    public void setEntite(final String entite) {
        this.entite = entite;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(final String fonction) {
        this.fonction = fonction;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(final String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getEcocite() {
        return ecocite;
    }

    public void setEcocite(final String ecocite) {
        this.ecocite = ecocite;
    }
}
