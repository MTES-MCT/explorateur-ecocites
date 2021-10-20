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
public class IndicateurEcociteExportCSVBean {
    private String idEcocite= "";
    private String nomEcocite= "";
    private String poste= "";
    private String unite= "";
    private String idIndicateur= "";
    private String nomCourt= "";

    public static final String END_LINE_REGEX = "[\n\r]";

    public IndicateurEcociteExportCSVBean(AssoIndicateurObjet assoIndicateurObjet) {
        if(assoIndicateurObjet.getIdObjet()!=null){this.idEcocite = assoIndicateurObjet.getIdObjet().toString();}
        if(assoIndicateurObjet.getIdIndicateur()!=null){this.idIndicateur = assoIndicateurObjet.getIdIndicateur().toString();}
        if(assoIndicateurObjet.getPosteCalcule()!=null){this.poste=assoIndicateurObjet.getPosteCalcule().replaceAll(END_LINE_REGEX," ");}
        if(assoIndicateurObjet.getUnite()!=null){this.unite=assoIndicateurObjet.getUnite().replaceAll(END_LINE_REGEX," ");}
    }

    public String getIdEcocite() {
        return idEcocite == null ? "" : idEcocite.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdEcocite(String idEcocite) {
        this.idEcocite = idEcocite;
    }

    public String getIdIndicateur() {
        return idIndicateur == null ? "" : idIndicateur.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdIndicateur(String idIndicateur) {
        this.idIndicateur = idIndicateur;
    }

    public String getPoste() {
        return poste == null ? "" : poste.replaceAll(END_LINE_REGEX, " ");
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public String getNomEcocite() {
        return nomEcocite == null ? "" : nomEcocite.replaceAll(END_LINE_REGEX, " ");
    }

    public void setNomEcocite(String nomEcocite) {
        this.nomEcocite = nomEcocite;
    }

    public String getNomCourt() {
        return nomCourt == null ? "" : nomCourt.replaceAll(END_LINE_REGEX, " ");
    }

    public void setNomCourt(String nomCourt) {
        this.nomCourt = nomCourt;
    }

    public static ColumnPositionMappingStrategy<IndicateurEcociteExportCSVBean> getIndicateurEcociteMappingStrategy() {
        ColumnPositionMappingStrategy<IndicateurEcociteExportCSVBean> strategy = new ColumnPositionMappingStrategy<>() {
            @Override
            public String[] generateHeader() {
                return new String[]{"Id indicateur","Nom court","Poste","Unité","Id ÉcoCité","Nom ÉcoCité"}; // Les Headers
            }
        };
        strategy.setType(IndicateurEcociteExportCSVBean.class);
        String[] fields = {"idIndicateur","nomCourt","poste","unite","idEcocite","nomEcocite"};// Les attributs dans le bon ordre
        strategy.setColumnMapping(fields);
        strategy.generateHeader();
        return strategy;
    }

}
