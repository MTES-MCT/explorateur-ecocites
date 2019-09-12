/*
 * Explorateur Écocités
 * Copyright (C) 2019 l'État, ministère chargé du logement
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
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

    public IngenierieExportCSVBean(AssoActionIngenierie assoActionIngenierie) {
        if(assoActionIngenierie.getIdAction()!=null){this.idAction = assoActionIngenierie.getIdAction().toString();}
        if(assoActionIngenierie.getIdEtiquetteIngenierie()!=null){this.idEtiquetteIngenierie = assoActionIngenierie.getIdEtiquetteIngenierie().toString();}
        this.typeMission = ""+assoActionIngenierie.getPoid();
    }

    public IngenierieExportCSVBean() {
    }

    public String getIdAction() {
        return idAction == null ? "" : idAction.replaceAll("[\n\r]", " ");
    }

    public void setIdAction(String idAction) {
        this.idAction = idAction;
    }

    public String getNomAction() {
        return nomAction == null ? "" : nomAction.replaceAll("[\n\r]", " ");
    }

    public void setNomAction(String nomAction) {
        this.nomAction = nomAction;
    }

    public String getIdIngenierie() {
        return idIngenierie == null ? "" : idIngenierie.replaceAll("[\n\r]", " ");
    }

    public void setIdIngenierie(String idIngenierie) {
        this.idIngenierie = idIngenierie;
    }

    public String getIdReponse() {
        return idEtiquetteIngenierie == null ? "" : idEtiquetteIngenierie.replaceAll("[\n\r]", " ");
    }

    public void setIdReponse(String idReponse) {
        this.idEtiquetteIngenierie = idReponse;
    }

    public String getIngenierie() {
        return ingenierie == null ? "" : ingenierie.replaceAll("[\n\r]", " ");
    }

    public String getEtiquetteIngenierie() {return etiquetteIngenierie == null ? "" : etiquetteIngenierie.replaceAll("[\n\r]", " ");}

    public void setEtiquetteIngenierie(String etiquetteIngenierie) {this.etiquetteIngenierie = etiquetteIngenierie;}

    public String getIdEtiquetteIngenierie() {return idEtiquetteIngenierie == null ? "" : idEtiquetteIngenierie.replaceAll("[\n\r]", " ");}

    public void setIdEtiquetteIngenierie(String idEtiquetteIngenierie) {this.idEtiquetteIngenierie = idEtiquetteIngenierie;}

    public void setIngenierie(String ingenierie) {
        this.ingenierie = ingenierie;
    }

    public String getTypeMission() {
        return typeMission == null ? "" : typeMission.replaceAll("[\n\r]", " ");
    }

    public void setTypeMission(String typeMission) {
        this.typeMission = typeMission;
    }

    public String getCommentaire() {
        return commentaire == null ? "" : commentaire.replaceAll("[\n\r]", " ");
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public static ColumnPositionMappingStrategy<IngenierieExportCSVBean> getIngenierieMappingStrategy() {
        ColumnPositionMappingStrategy<IngenierieExportCSVBean> strategy = new ColumnPositionMappingStrategy<IngenierieExportCSVBean>() {
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
