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

    public static final String END_LINE_REGEX = "[\n\r]";

    public IndicateurExportCSVBean(AssoIndicateurObjet assoIndicateurObjet) {
        if(assoIndicateurObjet.getIdObjet()!=null){this.idObjet= assoIndicateurObjet.getIdObjet().toString();}
        if(assoIndicateurObjet.getIdIndicateur()!=null){this.idIndicateur = assoIndicateurObjet.getIdIndicateur().toString();}
        if(assoIndicateurObjet.getUnite()!=null){this.unite = assoIndicateurObjet.getUnite().replaceAll(END_LINE_REGEX," ");}
        this.posteCalcul = assoIndicateurObjet.getPosteCalcule(); // on verifie la nulité après car recherche de l'indicateur
        if(assoIndicateurObjet.getCommentaireCible()!=null){this.commentaireCible = assoIndicateurObjet.getCommentaireCible().replaceAll(END_LINE_REGEX," ");}
        if(assoIndicateurObjet.getCommentaireMesure()!=null){this.commentaireMesure = assoIndicateurObjet.getCommentaireMesure().replaceAll(END_LINE_REGEX," ");}
    }

    public IndicateurExportCSVBean() {
    }


    public String getIdObjet() {
        return idObjet == null ? "" : idObjet.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdObjet(String idObjet) {
        this.idObjet = idObjet;
    }

    public String getIdIndicateur() {
        return idIndicateur == null ? "" : idIndicateur.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdIndicateur(String idIndicateur) {
        this.idIndicateur = idIndicateur;
    }

    public String getUnite() {
        return unite == null ? "" : unite.replaceAll(END_LINE_REGEX, " ");
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public String getPosteCalcul() {
        return posteCalcul == null ? "" : posteCalcul.replaceAll(END_LINE_REGEX, " ");
    }

    public void setPosteCalcul(String posteCalcul) {
        this.posteCalcul = posteCalcul;
    }

    public String getDatesMesures() {
        return datesMesures == null ? "" : datesMesures.replaceAll(END_LINE_REGEX, " ");
    }

    public void setDatesMesures(String datesMesures) {
        this.datesMesures = datesMesures;
    }

    public String getDatesCibles() {
        return datesCibles == null ? "" : datesCibles.replaceAll(END_LINE_REGEX, " ");
    }

    public void setDatesCibles(String datesCibles) {
        this.datesCibles = datesCibles;
    }

    public String getCible() {return cible == null ? "" : cible.replaceAll(END_LINE_REGEX, " ");}

    public void setCible(String cible) {this.cible = cible;}

    public String getMesure() {return mesure == null ? "" : mesure.replaceAll(END_LINE_REGEX, " ");}

    public void setMesure(String mesure) {this.mesure = mesure;}

    public String getCommentaireMesure() {return commentaireMesure == null ? "" : commentaireMesure.replaceAll(END_LINE_REGEX, " ");}

    public void setCommentaireMesure(String commentaireMesure) {this.commentaireMesure = commentaireMesure;}

    public String getCommentaireCible() {return commentaireCible == null ? "" : commentaireCible.replaceAll(END_LINE_REGEX, " ");}

    public void setCommentaireCible(String commentaireCible) {this.commentaireCible = commentaireCible;}

    public String getNatureIndicateur() {return natureIndicateur == null ? "" : natureIndicateur.replaceAll(END_LINE_REGEX, " ");}

    public void setNatureIndicateur(String natureIndicateur) {this.natureIndicateur = natureIndicateur;}

    public String getEtatValidation() {return etatValidation == null ? "" : etatValidation.replaceAll(END_LINE_REGEX, " ");}

    public void setEtatValidation(String etatValidation) {this.etatValidation = etatValidation;}

    public String getNomCourt() {
        return nomCourt == null ? "" : nomCourt.replaceAll(END_LINE_REGEX, " ");
    }

    public void setNomCourt(String nomCourt) {
        this.nomCourt = nomCourt;
    }

    public String getNomObjet() {
        return nomObjet == null ? "" : nomObjet.replaceAll(END_LINE_REGEX, " ");
    }

    public void setNomObjet(String nomObjet) {
        this.nomObjet = nomObjet;
    }

    public String getCommentaireOngletRealisation() {
        return commentaireOngletRealisation == null ? "" : commentaireOngletRealisation.replaceAll(END_LINE_REGEX, " ");
    }

    public void setCommentaireOngletRealisation(String commentaireOngletRealisation) {
        this.commentaireOngletRealisation = commentaireOngletRealisation;
    }

    public String getCommentaireOngletResultat() {
        return commentaireOngletResultat == null ? "" : commentaireOngletResultat.replaceAll(END_LINE_REGEX, " ");
    }

    public void setCommentaireOngletResultat(String commentaireOngletResultat) {
        this.commentaireOngletResultat = commentaireOngletResultat;
    }

    public static ColumnPositionMappingStrategy<IndicateurExportCSVBean> getContactMappingStrategyForAction() {
        ColumnPositionMappingStrategy<IndicateurExportCSVBean> strategy = new ColumnPositionMappingStrategy<>() {
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
        ColumnPositionMappingStrategy<IndicateurExportCSVBean> strategy = new ColumnPositionMappingStrategy<>() {
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
