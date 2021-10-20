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

import com.efficacity.explorateurecocites.beans.model.AssoIndicateurObjet;
import com.opencsv.bean.ColumnPositionMappingStrategy;

/**
 * Created by ktoomey on 15/03/2018.
 */
public class IndicateurActionExportCSVBean {
    private String idAction= "";
    private String idIndicateur= "";
    private String nomCourt= "";
    private String poste= "";
    private String unite= "";
    private String nomAction= "";

    public static final String END_LINE_REGEX = "[\n\r]";

    public IndicateurActionExportCSVBean(AssoIndicateurObjet assoIndicateurObjet) {
        if(assoIndicateurObjet.getIdObjet()!=null){this.idAction = assoIndicateurObjet.getIdObjet().toString();}
        if(assoIndicateurObjet.getIdIndicateur()!=null){this.idIndicateur = assoIndicateurObjet.getIdIndicateur().toString();}
        if(assoIndicateurObjet.getPosteCalcule()!=null){this.poste=assoIndicateurObjet.getPosteCalcule().replaceAll(END_LINE_REGEX," ");}
        if(assoIndicateurObjet.getUnite()!=null){this.unite=assoIndicateurObjet.getUnite().replaceAll(END_LINE_REGEX," ");}
    }

    public String getIdAction() {
        return idAction == null ? "" : idAction.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdAction(String idAction) {
        this.idAction = idAction;
    }

    public String getIdIndicateur() {
        return idIndicateur == null ? "" : idIndicateur.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdIndicateur(String idIndicateur) {
        this.idIndicateur = idIndicateur;
    }

    public String getNomAction() {
        return nomAction == null ? "" : nomAction.replaceAll(END_LINE_REGEX, " ");
    }

    public void setNomAction(String nomAction) {
        this.nomAction = nomAction;
    }

    public String getNomCourt() {
        return nomCourt;
    }

    public String getPoste() {
        return poste == null ? "" : poste.replaceAll(END_LINE_REGEX, " ");
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getUnite() {
        return unite == null ? "" : unite.replaceAll(END_LINE_REGEX, " ");
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public void setNomCourt(String nomCourt) {
        this.nomCourt = nomCourt;
    }

    public static ColumnPositionMappingStrategy<IndicateurActionExportCSVBean> getIndicateurActionMappingStrategy() {
        ColumnPositionMappingStrategy<IndicateurActionExportCSVBean> strategy = new ColumnPositionMappingStrategy<>() {
            @Override
            public String[] generateHeader() {
                return new String[]{"Id indicateur","Nom court","Poste","Unite","Id action","Nom action"}; // Les Headers
            }
        };
        strategy.setType(IndicateurActionExportCSVBean.class);
        String[] fields = {"idIndicateur","nomCourt","poste","unite","idAction","nomAction"};// Les attributs dans le bon ordre
        strategy.setColumnMapping(fields);
        strategy.generateHeader();
        return strategy;
    }
}
