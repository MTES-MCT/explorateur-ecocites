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

    public ObjectifExportCSVBean(AssoObjetObjectif assoObjetObjectif) {
        if(assoObjetObjectif.getIdObjet()!=null){this.idObjet = assoObjetObjectif.getIdObjet().toString();}
        if(assoObjetObjectif.getIdObjectif()!=null){this.idEtiquetteObjectif = assoObjetObjectif.getIdObjectif().toString();}
        this.typeObjectif = ""+assoObjetObjectif.getPoid();
    }

    public ObjectifExportCSVBean() {
    }

    public String getIdObjet() {
        return idObjet == null ? "" : idObjet.replaceAll("[\n\r]", " ");
    }

    public void setIdObjet(String idObjet) {
        this.idObjet = idObjet;
    }

    public String getNomObjet() {
        return nomObjet == null ? "" : nomObjet.replaceAll("[\n\r]", " ");
    }

    public void setNomObjet(String nomObjet) {
        this.nomObjet = nomObjet;
    }

    public String getIdObjectif() {
        return idObjectif == null ? "" : idObjectif.replaceAll("[\n\r]", " ");
    }

    public void setIdObjectif(String idObjectif) {
        this.idObjectif = idObjectif;
    }

    public String getIdEtiquetteObjectif() {
        return idEtiquetteObjectif == null ? "" : idEtiquetteObjectif.replaceAll("[\n\r]", " ");
    }

    public void setIdEtiquetteObjectif(String idEtiquetteObjectif) {
        this.idEtiquetteObjectif = idEtiquetteObjectif;
    }

    public String getObjectif() {
        return objectif == null ? "" : objectif.replaceAll("[\n\r]", " ");
    }

    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }

    public String getTypeObjectif() {
        return typeObjectif == null ? "" : typeObjectif.replaceAll("[\n\r]", " ");
    }

    public void setTypeObjectif(String typeObjectif) {
        this.typeObjectif = typeObjectif;
    }

    public String getCommentaire() {
        return commentaire == null ? "" : commentaire.replaceAll("[\n\r]", " ");
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getEtiquetteObjectif() {return etiquetteObjectif;}

    public void setEtiquetteObjectif(String etiquetteObjectif) {this.etiquetteObjectif = etiquetteObjectif;}

    public static ColumnPositionMappingStrategy<ObjectifExportCSVBean> getObjectifMappingStrategyForAction() {
        ColumnPositionMappingStrategy<ObjectifExportCSVBean> strategy = new ColumnPositionMappingStrategy<ObjectifExportCSVBean>() {
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
        ColumnPositionMappingStrategy<ObjectifExportCSVBean> strategy = new ColumnPositionMappingStrategy<ObjectifExportCSVBean>() {
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
