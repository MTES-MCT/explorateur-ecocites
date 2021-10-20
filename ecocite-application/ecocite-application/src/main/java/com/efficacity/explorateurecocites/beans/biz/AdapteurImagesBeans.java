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

public class AdapteurImagesBeans {
    private String href;
    private String libelle;
    private Boolean autorisationSiteEE;

    public AdapteurImagesBeans(final FileUploadBean f) {
        href = f.getUrl();
        libelle = f.getTitle();
        autorisationSiteEE = f.getAutorisationsiteee();
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Boolean isAutorisationSiteEE() {
        return autorisationSiteEE;
    }

    public void setAutorisationSiteEE(Boolean autorisationSiteEE) {
        this.autorisationSiteEE = autorisationSiteEE;
    }
}
