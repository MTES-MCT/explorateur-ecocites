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

import com.efficacity.explorateurecocites.beans.model.AssoIndicateurObjet;
import com.opencsv.bean.ColumnPositionMappingStrategy;

/**
 * Created by ktoomey on 09/03/2018.
 */
public class IndicateurExportCSVBean {
    private String idObjet= "";
    private String nomObjet= "";
    private String idIndicateur= "";
    private String nomCourt= "";
    private String natureIndicateur= "";
    private String unite= "";
    private String posteCalcul= "";
    private String commentaireOngletRealisation= "";
    private String commentaireOngletResultat= "";
    private String datesMesures= "";
    private String datesCibles= "";
    private String cible= "";
    private String mesure= "";
    private String commentaireMesure= "";
    private String commentaireCible= "";
    private String etatValidation= "";


    public IndicateurExportCSVBean(AssoIndicateurObjet assoIndicateurObjet) {
        if(assoIndicateurObjet.getIdObjet()!=null){this.idObjet= assoIndicateurObjet.getIdObjet().toString();}
        if(assoIndicateurObjet.getIdIndicateur()!=null){this.idIndicateur = assoIndicateurObjet.getIdIndicateur().toString();}
        if(assoIndicateurObjet.getUnite()!=null){this.unite = assoIndicateurObjet.getUnite().replaceAll("[\n\r]"," ");}
        this.posteCalcul = assoIndicateurObjet.getPosteCalcule(); // on verifie la nulité après car recherche de l'indicateur
        if(assoIndicateurObjet.getCommentaireCible()!=null){this.commentaireCible = assoIndicateurObjet.getCommentaireCible().replaceAll("[\n\r]"," ");}
        if(assoIndicateurObjet.getCommentaireMesure()!=null){this.commentaireMesure = assoIndicateurObjet.getCommentaireMesure().replaceAll("[\n\r]"," ");}
    }

    public IndicateurExportCSVBean() {
    }


    public String getIdObjet() {
        return idObjet == null ? "" : idObjet.replaceAll("[\n\r]", " ");
    }

    public void setIdObjet(String idObjet) {
        this.idObjet = idObjet;
    }

    public String getIdIndicateur() {
        return idIndicateur == null ? "" : idIndicateur.replaceAll("[\n\r]", " ");
    }

    public void setIdIndicateur(String idIndicateur) {
        this.idIndicateur = idIndicateur;
    }

    public String getUnite() {
        return unite == null ? "" : unite.replaceAll("[\n\r]", " ");
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public String getPosteCalcul() {
        return posteCalcul == null ? "" : posteCalcul.replaceAll("[\n\r]", " ");
    }

    public void setPosteCalcul(String posteCalcul) {
        this.posteCalcul = posteCalcul;
    }

    public String getDatesMesures() {
        return datesMesures == null ? "" : datesMesures.replaceAll("[\n\r]", " ");
    }

    public void setDatesMesures(String datesMesures) {
        this.datesMesures = datesMesures;
    }

    public String getDatesCibles() {
        return datesCibles == null ? "" : datesCibles.replaceAll("[\n\r]", " ");
    }

    public void setDatesCibles(String datesCibles) {
        this.datesCibles = datesCibles;
    }

    public String getCible() {return cible == null ? "" : cible.replaceAll("[\n\r]", " ");}

    public void setCible(String cible) {this.cible = cible;}

    public String getMesure() {return mesure == null ? "" : mesure.replaceAll("[\n\r]", " ");}

    public void setMesure(String mesure) {this.mesure = mesure;}

    public String getCommentaireMesure() {return commentaireMesure == null ? "" : commentaireMesure.replaceAll("[\n\r]", " ");}

    public void setCommentaireMesure(String commentaireMesure) {this.commentaireMesure = commentaireMesure;}

    public String getCommentaireCible() {return commentaireCible == null ? "" : commentaireCible.replaceAll("[\n\r]", " ");}

    public void setCommentaireCible(String commentaireCible) {this.commentaireCible = commentaireCible;}

    public String getNatureIndicateur() {return natureIndicateur == null ? "" : natureIndicateur.replaceAll("[\n\r]", " ");}

    public void setNatureIndicateur(String natureIndicateur) {this.natureIndicateur = natureIndicateur;}

    public String getEtatValidation() {return etatValidation == null ? "" : etatValidation.replaceAll("[\n\r]", " ");}

    public void setEtatValidation(String etatValidation) {this.etatValidation = etatValidation;}

    public String getNomCourt() {
        return nomCourt == null ? "" : nomCourt.replaceAll("[\n\r]", " ");
    }

    public void setNomCourt(String nomCourt) {
        this.nomCourt = nomCourt;
    }

    public String getNomObjet() {
        return nomObjet == null ? "" : nomObjet.replaceAll("[\n\r]", " ");
    }

    public void setNomObjet(String nomObjet) {
        this.nomObjet = nomObjet;
    }

    public String getCommentaireOngletRealisation() {
        return commentaireOngletRealisation == null ? "" : commentaireOngletRealisation.replaceAll("[\n\r]", " ");
    }

    public void setCommentaireOngletRealisation(String commentaireOngletRealisation) {
        this.commentaireOngletRealisation = commentaireOngletRealisation;
    }

    public String getCommentaireOngletResultat() {
        return commentaireOngletResultat == null ? "" : commentaireOngletResultat.replaceAll("[\n\r]", " ");
    }

    public void setCommentaireOngletResultat(String commentaireOngletResultat) {
        this.commentaireOngletResultat = commentaireOngletResultat;
    }

    public static ColumnPositionMappingStrategy<IndicateurExportCSVBean> getContactMappingStrategyForAction() {
        ColumnPositionMappingStrategy<IndicateurExportCSVBean> strategy = new ColumnPositionMappingStrategy<IndicateurExportCSVBean>() {
            @Override
            public String[] generateHeader() {
                return new String[]{"Id action", "Nom de l'action", "Id indicateur", "Nom court indicateur", "Nature indicateur",
                        "Unité indicateur", "Poste indicateur","Commentaire choix d'indicateurs de réalisation" ,"Commentaire choix d'indicateurs de résultat/impact","Dates Cibles","Dates Mesures",
                        "Cible","Mesure","Etat de validation","Commentaire mesure","Commentaire cible"};
            }
        };
        strategy.setType(IndicateurExportCSVBean.class);
        String[] fields = {"idObjet", "nomObjet", "idIndicateur", "nomCourt", "natureIndicateur", "unite", "posteCalcul",
                "commentaireOngletRealisation","commentaireOngletResultat","datesCibles","datesMesures", "cible", "mesure", "etatValidation", "commentaireMesure", "commentaireCible"};
        strategy.setColumnMapping(fields);
        strategy.generateHeader();
        return strategy;
    }

    public static ColumnPositionMappingStrategy<IndicateurExportCSVBean> getContactMappingStrategyForEcocite() {
        ColumnPositionMappingStrategy<IndicateurExportCSVBean> strategy = new ColumnPositionMappingStrategy<IndicateurExportCSVBean>() {
            @Override
            public String[] generateHeader() {
                return new String[]{"Id ÉcoCité", "ÉcoCité", "Id indicateur", "Nom court Indicateur", "Nature indicateur",
                        "Unité indicateur", "Poste indicateur","Commentaire choix d'indicateurs de réalisation" ,"Commentaire choix d'indicateurs de résultat/impact","Dates Cibles","Dates Mesures",
                        "Cible","Mesure","Etat de validation","Commentaire mesure","Commentaire cible"};
            }
        };
        strategy.setType(IndicateurExportCSVBean.class);
        String[] fields = {"idObjet", "nomObjet", "idIndicateur", "nomCourt", "natureIndicateur", "unite", "posteCalcul",
                "commentaireOngletRealisation","commentaireOngletResultat","datesCibles","datesMesures", "cible", "mesure", "etatValidation", "commentaireMesure", "commentaireCible"};
        strategy.setColumnMapping(fields);
        strategy.generateHeader();
        return strategy;
    }

}
