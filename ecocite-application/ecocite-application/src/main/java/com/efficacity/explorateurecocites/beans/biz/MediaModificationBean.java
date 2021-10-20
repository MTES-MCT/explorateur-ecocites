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

import com.efficacity.explorateurecocites.ajaris.enums.MediaModificationOriginType;
import com.efficacity.explorateurecocites.beans.model.MediaModification;
import com.efficacity.explorateurecocites.ajaris.enums.JobStatus;

import java.time.format.DateTimeFormatter;

public class MediaModificationBean {
    private static final DateTimeFormatter DATE_TIME_FORMATER = DateTimeFormatter.ofPattern("YYYY/MM/dd HH:mm:ss");
    private final String relatedObject;
    private MediaModification to;

    public MediaModificationBean(final MediaModification to, String relatedObject) {
        this.to = to;
        this.relatedObject = relatedObject;
    }

    public String getTypeObject() {
        return MediaModificationOriginType.getByCode(to.getTypeObject()).name();
    }

    public String getUrl() {
        switch (MediaModificationOriginType.getByCode(to.getTypeObject())) {
            case ACTION:
                return "/bo/actions/edition/" + to.getIdObject();
            case ECOCITE:
                return "/bo/ecocites/edition/" + to.getIdObject();
            case AXE:
                return "/bo/administration/axes?openId=" + to.getIdObject();
            case DOMAINE:
                return "/bo/administration/domaines?openId=" + to.getIdObject();
            case OBJECTIF:
                return "/bo/administration/objectifs?openId=" + to.getIdObject();
            case OTHER:
            default:
                return "#";
        }
    }

    public String getRelatedObject() {
        return relatedObject;
    }

    public Long getId() {
        return to.getId();
    }

    public String getLastModified() {
        return to.getLastModified().format(DATE_TIME_FORMATER);
    }

    public String getStatus() {
        return JobStatus.getByCode(to.getStatus()).libelle;
    }

    public Integer getNumberTry() {
        return to.getNumberTry();
    }

    public String getStatusCSSClass() {
        switch (JobStatus.getByCode(to.getStatus())) {
            case RUNNING:
                return "text-warning";
            case FINISHED:
                return "text-success";
            case PENDING:
                return "text-primary";
            case ERROR:
            default:
                return "text-danger";
        }
    }
}
