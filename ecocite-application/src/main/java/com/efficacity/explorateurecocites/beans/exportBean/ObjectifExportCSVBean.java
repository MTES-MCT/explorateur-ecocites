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

import com.efficacity.explorateurecocites.beans.model.AssoObjetObjectif;
import com.opencsv.bean.ColumnPositionMappingStrategy;

/**
 * Created by ktoomey on 09/03/2018.
 */
public class ObjectifExportCSVBean {
    private String idObjet= "";
    private String nomObjet= "";
    private String idObjectif= "";
    private String idEtiquetteObjectif= "";
    private String objectif= "";
    private String etiquetteObjectif= "";
    private String typeObjectif= "";
    private String commentaire= "";

    public static final String END_LINE_REGEX = "[\n\r]";

    public ObjectifExportCSVBean(AssoObjetObjectif assoObjetObjectif) {
        if(assoObjetObjectif.getIdObjet()!=null){this.idObjet = assoObjetObjectif.getIdObjet().toString();}
        if(assoObjetObjectif.getIdObjectif()!=null){this.idEtiquetteObjectif = assoObjetObjectif.getIdObjectif().toString();}
        this.typeObjectif = ""+assoObjetObjectif.getPoid();
    }

    public ObjectifExportCSVBean() {
    }

    public String getIdObjet() {
        return idObjet == null ? "" : idObjet.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdObjet(String idObjet) {
        this.idObjet = idObjet;
    }

    public String getNomObjet() {
        return nomObjet == null ? "" : nomObjet.replaceAll(END_LINE_REGEX, " ");
    }

    public void setNomObjet(String nomObjet) {
        this.nomObjet = nomObjet;
    }

    public String getIdObjectif() {
        return idObjectif == null ? "" : idObjectif.replaceAll(END_LINE_REGEX, " ");
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

    public String getTypeObjectif() {
        return typeObjectif == null ? "" : typeObjectif.replaceAll(END_LINE_REGEX, " ");
    }

    public void setTypeObjectif(String typeObjectif) {
        this.typeObjectif = typeObjectif;
    }

    public String getCommentaire() {
        return commentaire == null ? "" : commentaire.replaceAll(END_LINE_REGEX, " ");
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getEtiquetteObjectif() {return etiquetteObjectif;}

    public void setEtiquetteObjectif(String etiquetteObjectif) {this.etiquetteObjectif = etiquetteObjectif;}

    public static ColumnPositionMappingStrategy<ObjectifExportCSVBean> getObjectifMappingStrategyForAction() {
        ColumnPositionMappingStrategy<ObjectifExportCSVBean> strategy = new ColumnPositionMappingStrategy<>() {
            @Override
            public String[] generateHeader() {
                return new String[]{"Id action","Nom action", "Id finalité", "Nom finalité", "Id objectif", "Objectif", "Niveau objectif (majeur,modéré,mineur)", "Commentaires objectif"};
            }
        };
        strategy.setType(ObjectifExportCSVBean.class);
        String[] fields = {"idObjet","nomObjet","idObjectif","objectif", "idEtiquetteObjectif","etiquetteObjectif","typeObjectif","commentaire"};
        strategy.setColumnMapping(fields);
        strategy.generateHeader();
        return strategy;
    }

    public static ColumnPositionMappingStrategy<ObjectifExportCSVBean> getObjectifMappingStrategyForEcocite() {
        ColumnPositionMappingStrategy<ObjectifExportCSVBean> strategy = new ColumnPositionMappingStrategy<>() {
            @Override
            public String[] generateHeader() {
                return new String[]{"Id ÉcoCité","ÉcoCité", "Id finalité", "Nom finalité", "Id objectif", "Objectif", "Niveau objectif (majeur,modéré,mineur)", "Commentaires objectif"};
            }
        };
        strategy.setType(ObjectifExportCSVBean.class);
        String[] fields = {"idObjet","nomObjet","idObjectif","objectif", "idEtiquetteObjectif","etiquetteObjectif","typeObjectif","commentaire"};
        strategy.setColumnMapping(fields);
        strategy.generateHeader();
        return strategy;
    }


}
