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

import com.efficacity.explorateurecocites.beans.model.Action;
import com.efficacity.explorateurecocites.beans.model.Ecocite;
import com.efficacity.explorateurecocites.ui.fo.utils.UrlUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ResultatRecherche {
    @JsonSerialize(
            using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
    )
    private Long idAction;
    @JsonSerialize(
            using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
    )
    private Long idEcocite;
    private String titre;
    private String description;
    private String href;

    public ResultatRecherche(final ActionBean action, final EcociteBean ecocite){
        setIdAction(action.getId());
        setIdEcocite(action.getIdEcocite());
        setTitre(action.getNomPublic());
        setDescription(ecocite.getNom());
        setHref(UrlUtils.getUrlIdAction(action.getId().toString()));
    }

    public ResultatRecherche(final Action action, final EcociteBean ecocite) {
        setIdAction(action.getId());
        setIdEcocite(action.getIdEcocite());
        setTitre(action.getNomPublic());
        setDescription(ecocite.getNom());
        setHref(UrlUtils.getUrlIdAction(action.getId().toString()));
    }

    public ResultatRecherche(final Action action, final Ecocite ecocite){
        setIdAction(action.getId());
        setIdEcocite(action.getIdEcocite());
        setTitre(action.getNomPublic());
        setDescription(ecocite.getNom());
        setHref(UrlUtils.getUrlIdAction(action.getId().toString()));
    }

    public ResultatRecherche(final EcociteBean ecocite) {
        setIdEcocite(ecocite.getId());
        setTitre(ecocite.getNom());
        setHref(UrlUtils.getUrlIdEcocite(getIdEcocite().toString()));
    }

    public Long getIdAction() {
        return idAction;
    }

    public void setIdAction(Long idAction) {
        this.idAction = idAction;
    }

    public Long getIdEcocite() {
        return idEcocite;
    }

    public void setIdEcocite(Long idEcocite) {
        this.idEcocite = idEcocite;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
