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

package com.efficacity.explorateurecocites.ui.bo.forms.tables;

import com.efficacity.explorateurecocites.beans.model.Axe;
import com.efficacity.explorateurecocites.utils.table.InputType;
import com.efficacity.explorateurecocites.utils.table.TableColumn;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AxeTableForm {

    @JsonSerialize(
            using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
    )
    private Long id;

    @NotBlank
    @TableColumn("axe.colum.nom")
    private String nom;

    @NotBlank
    @TableColumn("axe.colum.color_1")
    private String color_1;

    @NotBlank
    @TableColumn("axe.colum.color_2")
    private String color_2;

    @TableColumn(value = "axe.colum.icon", type = InputType.FILE)
    private String icon;

    @NotNull
    private Integer icon_changed;

    private MultipartFile icon_file;

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

    public String getColor_1() {
        return color_1;
    }

    public void setColor_1(final String color_1) {
        this.color_1 = color_1;
    }

    public String getColor_2() {
        return color_2;
    }

    public void setColor_2(final String color_2) {
        this.color_2 = color_2;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(final String icon) {
        this.icon = icon;
    }

    public Integer getIcon_changed() {
        return icon_changed;
    }

    public Boolean hasIconChanged() {
        return icon_changed != 0;
    }

    public void setIcon_changed(final Integer icon_changed) {
        this.icon_changed = icon_changed;
    }

    public MultipartFile getIcon_file() {
        return icon_file;
    }

    public void setIcon_file(final MultipartFile icon_file) {
        this.icon_file = icon_file;
    }

    public Axe getAxe() {
        Axe axe = new Axe();
        axe.setCodeCouleur1(getColor_1());
        axe.setCodeCouleur2(getColor_2());
        axe.setLibelle(getNom());
        axe.setDateModification(LocalDateTime.now());
        // axe.setIcone();
        return axe;
    }
}
