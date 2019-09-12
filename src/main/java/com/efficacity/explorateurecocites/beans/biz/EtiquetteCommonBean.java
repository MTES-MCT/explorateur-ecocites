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

package com.efficacity.explorateurecocites.beans.biz;

import com.efficacity.explorateurecocites.beans.model.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;
import java.util.stream.Collectors;

public class EtiquetteCommonBean {

    @JsonSerialize(
            using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
    )
    private Long id;
    private String text;
    private String description;
    private String color;

    private EtiquetteCommonBean(final EtiquetteAxe etiquetteAxe, final Axe axe) {
        id = etiquetteAxe.getId();
        text = etiquetteAxe.getLibelle();
        description = etiquetteAxe.getDescription();
        if (axe != null) {
            color = axe.getCodeCouleur1();
        } else {
            color = "#000000";
        }
    }

    private EtiquetteCommonBean(final EtiquetteFinalite etiquetteFinalite, final Finalite objectif) {
        id = etiquetteFinalite.getId();
        text = etiquetteFinalite.getLibelle();
        description = etiquetteFinalite.getDescription();
        if (objectif != null) {
            color = objectif.getCodeCouleur();
        } else {
            color = "#000000";
        }
    }

    private EtiquetteCommonBean(final EtiquetteIngenierie etiquetteIngenierie, final Ingenierie ingenierie) {
        id = etiquetteIngenierie.getId();
        text = etiquetteIngenierie.getLibelle();
        description = etiquetteIngenierie.getDescription();
        if (ingenierie != null) {
            color = ingenierie.getCodeCouleur();
        } else {
            color = "#000000";
        }
    }

    public static EtiquetteCommonBean toBean(final EtiquetteAxe etiquetteAxe, final List<Axe> domaines) {
        return new EtiquetteCommonBean(etiquetteAxe,
                domaines.stream()
                        .filter(o -> o.getId().equals(etiquetteAxe.getIdAxe()))
                        .findFirst()
                        .orElse(null));
    }

    public static List<EtiquetteCommonBean> toBeanListDomain(final List<EtiquetteAxe> etiquetteAxeList, final List<Axe> domaines) {
        return etiquetteAxeList.stream().map(o -> toBean(o, domaines)).collect(Collectors.toList());
    }

    public static EtiquetteCommonBean toBean(final EtiquetteFinalite etiquetteFinalite, final List<Finalite> objectifs) {
        return new EtiquetteCommonBean(etiquetteFinalite,
                objectifs.stream()
                        .filter(o -> o.getId().equals(etiquetteFinalite.getIdFinalite()))
                        .findFirst()
                        .orElse(null));
    }

    public static EtiquetteCommonBean toBean(final EtiquetteIngenierie etiquetteIngenierie, final List<Ingenierie> ingenieries) {
        return new EtiquetteCommonBean(etiquetteIngenierie,
                ingenieries.stream()
                        .filter(o -> o.getId().equals(etiquetteIngenierie.getIdIngenierie()))
                        .findFirst()
                        .orElse(null));
    }

    public static List<EtiquetteCommonBean> toBeanListFinalite(final List<EtiquetteFinalite> etiquetteFinaliteList, final List<Finalite> objectifs) {
        return etiquetteFinaliteList.stream().map(o -> toBean(o, objectifs)).collect(Collectors.toList());
    }

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
