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

package com.efficacity.explorateurecocites.ui.bo.forms;

import com.efficacity.explorateurecocites.beans.model.Media;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class MediaForm {
    @JsonSerialize(
            using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
    )
    private Long id;
    private String title;
    private String legende;
    private Double latitude;
    private Double longitude;
    private String description;
    private String copyright;
    private Integer idAjaris;
    private String datePriseVue;
    private Integer numerisation;
    private String previewUrl;

    public MediaForm() {}

    public MediaForm(Media media) {
        this.id = media.getId();
        this.idAjaris = media.getIdAjaris();
        this.title = media.getTitle();
        this.numerisation = media.getNumero();
    }

    public Integer getIdAjaris() {
        return idAjaris;
    }

    public void setIdAjaris(final Integer idAjaris) {
        this.idAjaris = idAjaris;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getLegende() {
        return legende;
    }

    public void setLegende(String legende) {
        this.legende = legende;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(final Double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Integer getNumerisation() {
        return numerisation;
    }

    public void setNumerisation(Integer numerisation) {
        this.numerisation = numerisation;
    }

    public void setPreviewUrl(final String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getDatePriseVue() {
        return datePriseVue;
    }

    public void setDatePriseVue(final String datePriseVue) {
        this.datePriseVue = datePriseVue;
    }
}
