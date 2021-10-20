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

import com.efficacity.explorateurecocites.beans.model.AssoActionIngenierie;
import com.opencsv.bean.ColumnPositionMappingStrategy;

/**
 * Created by ktoomey on 09/03/2018.
 */
public class IngenierieExportCSVBean {
    private String idAction= "";
    private String nomAction= "";
    private String idIngenierie= "";
    private String idEtiquetteIngenierie= "";
    private String etiquetteIngenierie= "";
    private String ingenierie= "";
    private String typeMission= "";
    private String commentaire= "";

    public static final String END_LINE_REGEX = "[\n\r]";

    public IngenierieExportCSVBean(AssoActionIngenierie assoActionIngenierie) {
        if(assoActionIngenierie.getIdAction()!=null){this.idAction = assoActionIngenierie.getIdAction().toString();}
        if(assoActionIngenierie.getIdEtiquetteIngenierie()!=null){this.idEtiquetteIngenierie = assoActionIngenierie.getIdEtiquetteIngenierie().toString();}
        this.typeMission = ""+assoActionIngenierie.getPoid();
    }

    public IngenierieExportCSVBean() {
    }

    public String getIdAction() {
        return idAction == null ? "" : idAction.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdAction(String idAction) {
        this.idAction = idAction;
    }

    public String getNomAction() {
        return nomAction == null ? "" : nomAction.replaceAll(END_LINE_REGEX, " ");
    }

    public void setNomAction(String nomAction) {
        this.nomAction = nomAction;
    }

    public String getIdIngenierie() {
        return idIngenierie == null ? "" : idIngenierie.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdIngenierie(String idIngenierie) {
        this.idIngenierie = idIngenierie;
    }

    public String getIdReponse() {
        return idEtiquetteIngenierie == null ? "" : idEtiquetteIngenierie.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdReponse(String idReponse) {
        this.idEtiquetteIngenierie = idReponse;
    }

    public String getIngenierie() {
        return ingenierie == null ? "" : ingenierie.replaceAll(END_LINE_REGEX, " ");
    }

    public String getEtiquetteIngenierie() {return etiquetteIngenierie == null ? "" : etiquetteIngenierie.replaceAll(END_LINE_REGEX, " ");}

    public void setEtiquetteIngenierie(String etiquetteIngenierie) {this.etiquetteIngenierie = etiquetteIngenierie;}

    public String getIdEtiquetteIngenierie() {return idEtiquetteIngenierie == null ? "" : idEtiquetteIngenierie.replaceAll(END_LINE_REGEX, " ");}

    public void setIdEtiquetteIngenierie(String idEtiquetteIngenierie) {this.idEtiquetteIngenierie = idEtiquetteIngenierie;}

    public void setIngenierie(String ingenierie) {
        this.ingenierie = ingenierie;
    }

    public String getTypeMission() {
        return typeMission == null ? "" : typeMission.replaceAll(END_LINE_REGEX, " ");
    }

    public void setTypeMission(String typeMission) {
        this.typeMission = typeMission;
    }

    public String getCommentaire() {
        return commentaire == null ? "" : commentaire.replaceAll(END_LINE_REGEX, " ");
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public static ColumnPositionMappingStrategy<IngenierieExportCSVBean> getIngenierieMappingStrategy() {
        ColumnPositionMappingStrategy<IngenierieExportCSVBean> strategy = new ColumnPositionMappingStrategy<>() {
            @Override
            public String[] generateHeader() {
                return new String[]{"Id action", "Nom action", "Id nature d'ingé", "Nom nature d'ingé", "Id type de mission d'ingé", "Type de mision d'ingénierie", "Commentaires type de mission d'ingénierie"};
            }
        };
        strategy.setType(IngenierieExportCSVBean.class);
        String[] fields = {"idAction","nomAction", "idIngenierie", "ingenierie", "idEtiquetteIngenierie", "etiquetteIngenierie", "typeMission", "commentaire"};
        strategy.setColumnMapping(fields);
        strategy.generateHeader();
        return strategy;
    }

}
