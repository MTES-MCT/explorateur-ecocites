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

import com.efficacity.explorateurecocites.beans.model.Indicateur;
import com.opencsv.bean.ColumnPositionMappingStrategy;


/**
 * Created by ktoomey on 14/03/2018.
 */
public class IndicateurOnlyExportCSVBean {
    private String idIndicateur= "";
    private String nomLong= "";
    private String nomCourt= "";
    private String echelle= "";
    private String natureIndicateur= "";
    private String methodeCalcul= "";
    private String origine= "";
    private String typeMesures= "";
    private String sourceDonnees= "";
    private String etatValidation= "";
    private String posteCalcul= "";
    private String unite= "";
    private String userCreation= "";
    private String statutBibliotheque= "";
    private String definition= "";

    public static final String END_LINE_REGEX = "[\n\r]";

    public IndicateurOnlyExportCSVBean(Indicateur indicateur) {
        if(indicateur.getId()!=null){this.idIndicateur = indicateur.getId().toString();}
        this.nomLong = indicateur.getNom();
        this.nomCourt = indicateur.getNomCourt();
        this.echelle = indicateur.getEchelle();
        if(indicateur.getNature()!=null){this.natureIndicateur = indicateur.getNature().replaceAll(END_LINE_REGEX," ");}
        if( indicateur.getMethodeCalcule()!=null){this.methodeCalcul = indicateur.getMethodeCalcule().replaceAll(END_LINE_REGEX," ");}
        if(indicateur.getOrigine()!=null){this.origine = indicateur.getOrigine().replaceAll(END_LINE_REGEX," ");}
        if(indicateur.getTypeMesure()!=null){this.typeMesures = indicateur.getTypeMesure().replaceAll(END_LINE_REGEX," ");}
        if(indicateur.getSourceDonnees()!=null){this.sourceDonnees = indicateur.getSourceDonnees().replaceAll(END_LINE_REGEX," ");}
        if(indicateur.getDescription()!=null){this.definition = indicateur.getDescription().replaceAll(END_LINE_REGEX," ");}
        this.etatValidation = indicateur.getEtatValidation();
        this.userCreation = indicateur.getUserCreation();
        this.statutBibliotheque = indicateur.getEtatBibliotheque();
        if(indicateur.getUnite()!=null){this.unite= indicateur.getUnite().replaceAll(END_LINE_REGEX," ");}
        if(indicateur.getPosteCalcule()!=null){this.posteCalcul = indicateur.getPosteCalcule().replaceAll(END_LINE_REGEX," ");}
    }

    public IndicateurOnlyExportCSVBean() {
    }

    public String getIdIndicateur() {
        return idIndicateur == null ? "" : idIndicateur.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdIndicateur(String idIndicateur) {
        this.idIndicateur = idIndicateur;
    }

    public String getNomLong() {
        return nomLong == null ? "" : nomLong.replaceAll(END_LINE_REGEX, " ");
    }

    public void setNomLong(String nomLong) {
        this.nomLong = nomLong;
    }

    public String getNomCourt() {
        return nomCourt == null ? "" : nomCourt.replaceAll(END_LINE_REGEX, " ");
    }

    public void setNomCourt(String nomCourt) {
        this.nomCourt = nomCourt;
    }

    public String getEchelle() {
        return echelle == null ? "" : echelle.replaceAll(END_LINE_REGEX, " ");
    }

    public void setEchelle(String echelle) {
        this.echelle = echelle;
    }

    public String getNatureIndicateur() {
        return natureIndicateur == null ? "" : natureIndicateur.replaceAll(END_LINE_REGEX, " ");
    }

    public void setNatureIndicateur(String natureIndicateur) {
        this.natureIndicateur = natureIndicateur;
    }

    public String getMethodeCalcul() {
        return methodeCalcul == null ? "" : methodeCalcul.replaceAll(END_LINE_REGEX, " ");
    }

    public void setMethodeCalcul(String methodeCalcul) {
        this.methodeCalcul = methodeCalcul;
    }

    public String getOrigine() {
        return origine == null ? "" : origine.replaceAll(END_LINE_REGEX, " ");
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public String getTypeMesures() {
        return typeMesures == null ? "" : typeMesures.replaceAll(END_LINE_REGEX, " ");
    }

    public void setTypeMesures(String typeMesures) {
        this.typeMesures = typeMesures;
    }

    public String getSourceDonnees() {
        return sourceDonnees == null ? "" : sourceDonnees.replaceAll(END_LINE_REGEX, " ");
    }

    public void setSourceDonnees(String sourceDonnees) {
        this.sourceDonnees = sourceDonnees;
    }

    public String getEtatValidation() {
        return etatValidation == null ? "" : etatValidation.replaceAll(END_LINE_REGEX, " ");
    }

    public void setEtatValidation(String etatValidation) {
        this.etatValidation = etatValidation;
    }

    public String getPosteCalcul() {
        return posteCalcul == null ? "" : posteCalcul.replaceAll(END_LINE_REGEX, " ");
    }

    public void setPosteCalcul(String posteCalcul) {
        this.posteCalcul = posteCalcul;
    }

    public String getUnite() {
        return unite == null ? "" : unite.replaceAll(END_LINE_REGEX, " ");
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public String getUserCreation() {
        return userCreation == null ? "" : userCreation.replaceAll(END_LINE_REGEX, " ");
    }

    public void setUserCreation(String userCreation) {
        this.userCreation = userCreation;
    }

    public String getStatutBibliotheque() {
        return statutBibliotheque == null ? "" : statutBibliotheque.replaceAll(END_LINE_REGEX, " ");
    }

    public void setStatutBibliotheque(String statutBibliotheque) {
        this.statutBibliotheque = statutBibliotheque;
    }

    public String getDefinition() {
        return definition == null ? "" : definition.replaceAll(END_LINE_REGEX, " ");
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public static ColumnPositionMappingStrategy<IndicateurOnlyExportCSVBean> getIndicateurOnlyMappingStrategy() {
        ColumnPositionMappingStrategy<IndicateurOnlyExportCSVBean> strategy = new ColumnPositionMappingStrategy<>() {
            @Override
            public String[] generateHeader() {
                return new String[]{"Id indicateur", "Nom court", "Nom long", "Echelle", "Nature", "Définition",
                        "Méthode de calcul", "Poste de calcul (piste)", "Unité (liste)",
                        "Source de données", "Origine (liste)", "Type de mesure","Contact du Créateur", "Etat de validation", "Etat de publication"};
            }
        };
        strategy.setType(IndicateurOnlyExportCSVBean.class);
        String[] fields = {"idIndicateur","nomCourt","nomLong","echelle","natureIndicateur","definition","methodeCalcul",
                "posteCalcul","unite","sourceDonnees","origine","typeMesures","userCreation","etatValidation","statutBibliotheque"}; // Les attributs dans le bon ordre
        strategy.setColumnMapping(fields);
        strategy.generateHeader();
        return strategy;
    }
}
