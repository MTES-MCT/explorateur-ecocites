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

package com.efficacity.explorateurecocites.beans.exportBean;

import com.efficacity.explorateurecocites.beans.model.AssoIndicateurObjectif;
import com.opencsv.bean.ColumnPositionMappingStrategy;

/**
 * Created by ktoomey on 15/03/2018.
 */
public class IndicateurObjectifExportCSVBean {
    private String idIndicateur= "";
    private String nomCourt= "";
    private String idObjectif= "";
    private String idEtiquetteObjectif= "";
    private String objectif= "";
    private String etiquetteObjectif= "";

    public static final String END_LINE_REGEX = "[\n\r]";

    public IndicateurObjectifExportCSVBean(AssoIndicateurObjectif assoIndicateurObjectif) {
        if(assoIndicateurObjectif.getIdIndicateur()!=null){this.idIndicateur = assoIndicateurObjectif.getIdIndicateur().toString();}
        if(assoIndicateurObjectif.getIdObjectif()!=null){this.idEtiquetteObjectif = assoIndicateurObjectif.getIdObjectif().toString();}
    }

    public String getIdIndicateur() {
        return idIndicateur == null ? "" : idIndicateur.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdIndicateur(String idIndicateur) {
        this.idIndicateur = idIndicateur;
    }

    public String getNomCourt() {
        return nomCourt == null ? "" : nomCourt.replaceAll(END_LINE_REGEX, " ");
    }

    public void setNomCourt(String nomCourt) {
        this.nomCourt = nomCourt;
    }

    public String getIdObjectif() {
        return idObjectif;
    }

    public void setIdObjectif(String idObjectif) {
        this.idObjectif = idObjectif;
    }

    public String getIdEtiquetteObjectif() {
        return idEtiquetteObjectif == null ? "" : idEtiquetteObjectif.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdEtiquetteObjectif(String idEtiquetteObjectif) {
        this.idEtiquetteObjectif = idEtiquetteObjectif;
    }

    public String getObjectif() {
        return objectif == null ? "" : objectif.replaceAll(END_LINE_REGEX, " ");
    }

    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }

    public String getEtiquetteObjectif() {
        return etiquetteObjectif == null ? "" : etiquetteObjectif.replaceAll(END_LINE_REGEX, " ");
    }

    public void setEtiquetteObjectif(String etiquetteObjectif) {
        this.etiquetteObjectif = etiquetteObjectif;
    }

    public static ColumnPositionMappingStrategy<IndicateurObjectifExportCSVBean> getIndicateurObjectifMappingStrategy() {
        ColumnPositionMappingStrategy<IndicateurObjectifExportCSVBean> strategy = new ColumnPositionMappingStrategy<>() {
            @Override
            public String[] generateHeader() {
                return new String[]{"Id indicateur","Nom court", "Id finalité", "Nom finalité", "Id objectif", "Objectif"};
            }
        };
        strategy.setType(IndicateurObjectifExportCSVBean.class);
        String[] fields = {"idIndicateur","nomCourt","idObjectif","objectif", "idEtiquetteObjectif","etiquetteObjectif"};
        strategy.setColumnMapping(fields);
        strategy.generateHeader();
        return strategy;
    }
}
