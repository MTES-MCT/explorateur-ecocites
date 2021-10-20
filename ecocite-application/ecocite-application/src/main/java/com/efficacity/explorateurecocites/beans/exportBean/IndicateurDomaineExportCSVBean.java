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

import com.efficacity.explorateurecocites.beans.model.AssoIndicateurDomaine;
import com.opencsv.bean.ColumnPositionMappingStrategy;

/**
 * Created by ktoomey on 15/03/2018.
 */
public class IndicateurDomaineExportCSVBean {
    private String idIndicateur= "";
    private String nomCourt= "";
    private String idEtiquetteDomaine= "";
    private String idDomaine= "";
    private String etiquetteDomaine= "";
    private String domaine= "";

    public static final String END_LINE_REGEX = "[\n\r]";

    public IndicateurDomaineExportCSVBean(AssoIndicateurDomaine assoIndicateurDomaine) {
        if(assoIndicateurDomaine.getIdDomaine()!=null){this.idEtiquetteDomaine = assoIndicateurDomaine.getIdDomaine().toString();}
        if(assoIndicateurDomaine.getIdIndicateur()!=null){this.idIndicateur = assoIndicateurDomaine.getIdIndicateur().toString();}
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

    public String getIdEtiquetteDomaine() {
        return idEtiquetteDomaine == null ? "" : idEtiquetteDomaine.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdEtiquetteDomaine(String idEtiquetteDomaine) {
        this.idEtiquetteDomaine = idEtiquetteDomaine;
    }

    public String getIdDomaine() {
        return idDomaine == null ? "" : idDomaine.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdDomaine(String idDomaine) {
        this.idDomaine = idDomaine;
    }

    public String getEtiquetteDomaine() {
        return etiquetteDomaine == null ? "" : etiquetteDomaine.replaceAll(END_LINE_REGEX, " ");
    }

    public void setEtiquetteDomaine(String etiquetteDomaine) {
        this.etiquetteDomaine = etiquetteDomaine;
    }

    public String getDomaine() {
        return domaine == null ? "" : domaine.replaceAll(END_LINE_REGEX, " ");
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public static ColumnPositionMappingStrategy<IndicateurDomaineExportCSVBean> getIndicateurDomaineMappingStrategy() {
        ColumnPositionMappingStrategy<IndicateurDomaineExportCSVBean> strategy = new ColumnPositionMappingStrategy<>() {
            @Override
            public String[] generateHeader() {
                return new String[]{"Id Indicateur", "Nom Court", "Id Axe", "Nom Axe", "Id domaine", "Nom domaine"};
            }
        };
        strategy.setType(IndicateurDomaineExportCSVBean.class);
        String[] fields = {"idIndicateur","nomCourt", "idDomaine", "domaine", "idEtiquetteDomaine", "etiquetteDomaine"};
        strategy.setColumnMapping(fields);
        strategy.generateHeader();
        return strategy;
    }
}
