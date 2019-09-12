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

package com.efficacity.explorateurecocites.ui.bo.forms.tables;

import com.efficacity.explorateurecocites.beans.model.EtiquetteFinalite;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.helpers.SelectOption;
import com.efficacity.explorateurecocites.utils.table.InputType;
import com.efficacity.explorateurecocites.utils.table.TableColumn;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

public class EtiquetteFinaliteForm {

    @JsonSerialize(
            using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
    )
    private Long id;

    @NotBlank
    @TableColumn("objectif.colum.nom")
    private String nom;

    @NotBlank
    @TableColumn("objectif.colum.description")
    private String description;

    @NotNull
    @TableColumn(value = "objectif.colum.finalite", type = InputType.SELECT)
    private Long idFinalite;

    private List<SelectOption> idFinaliteDefaults;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Long getIdFinalite() {
        return idFinalite;
    }

    public void setIdFinalite(final Long idFinalite) {
        this.idFinalite = idFinalite;
    }

    public List<SelectOption> getIdFinaliteDefaults() {
        return idFinaliteDefaults;
    }

    public void setIdFinaliteDefaults(final List<SelectOption> idFinaliteDefaults) {
        this.idFinaliteDefaults = idFinaliteDefaults;
    }

    public EtiquetteFinalite getEtiquetteFinalite() {
        EtiquetteFinalite etiquetteFinalite = new EtiquetteFinalite();
        etiquetteFinalite.setDescription(getDescription());
        etiquetteFinalite.setIdFinalite(getIdFinalite());
        etiquetteFinalite.setLibelle(getNom());
        return etiquetteFinalite;
    }
}
