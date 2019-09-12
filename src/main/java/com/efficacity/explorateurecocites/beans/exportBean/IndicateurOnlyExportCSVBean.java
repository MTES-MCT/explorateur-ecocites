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

import com.efficacity.explorateurecocites.beans.model.Indicateur;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvBindByName;

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

    public IndicateurOnlyExportCSVBean(Indicateur indicateur) {
        if(indicateur.getId()!=null){this.idIndicateur = indicateur.getId().toString();}
        this.nomLong = indicateur.getNom();
        this.nomCourt = indicateur.getNomCourt();
        this.echelle = indicateur.getEchelle();
        if(indicateur.getNature()!=null){this.natureIndicateur = indicateur.getNature().replaceAll("[\n\r]"," ");}
        if( indicateur.getMethodeCalcule()!=null){this.methodeCalcul = indicateur.getMethodeCalcule().replaceAll("[\n\r]"," ");}
        if(indicateur.getOrigine()!=null){this.origine = indicateur.getOrigine().replaceAll("[\n\r]"," ");}
        if(indicateur.getTypeMesure()!=null){this.typeMesures = indicateur.getTypeMesure().replaceAll("[\n\r]"," ");}
        if(indicateur.getSourceDonnees()!=null){this.sourceDonnees = indicateur.getSourceDonnees().replaceAll("[\n\r]"," ");}
        if(indicateur.getDescription()!=null){this.definition = indicateur.getDescription().replaceAll("[\n\r]"," ");}
        this.etatValidation = indicateur.getEtatValidation();
        this.userCreation = indicateur.getUserCreation();
        this.statutBibliotheque = indicateur.getEtatBibliotheque();
        if(indicateur.getUnite()!=null){this.unite= indicateur.getUnite().replaceAll("[\n\r]"," ");}
        if(indicateur.getPosteCalcule()!=null){this.posteCalcul = indicateur.getPosteCalcule().replaceAll("[\n\r]"," ");}
    }

    public IndicateurOnlyExportCSVBean() {
    }

    public String getIdIndicateur() {
        return idIndicateur == null ? "" : idIndicateur.replaceAll("[\n\r]", " ");
    }

    public void setIdIndicateur(String idIndicateur) {
        this.idIndicateur = idIndicateur;
    }

    public String getNomLong() {
        return nomLong == null ? "" : nomLong.replaceAll("[\n\r]", " ");
    }

    public void setNomLong(String nomLong) {
        this.nomLong = nomLong;
    }

    public String getNomCourt() {
        return nomCourt == null ? "" : nomCourt.replaceAll("[\n\r]", " ");
    }

    public void setNomCourt(String nomCourt) {
        this.nomCourt = nomCourt;
    }

    public String getEchelle() {
        return echelle == null ? "" : echelle.replaceAll("[\n\r]", " ");
    }

    public void setEchelle(String echelle) {
        this.echelle = echelle;
    }

    public String getNatureIndicateur() {
        return natureIndicateur == null ? "" : natureIndicateur.replaceAll("[\n\r]", " ");
    }

    public void setNatureIndicateur(String natureIndicateur) {
        this.natureIndicateur = natureIndicateur;
    }

    public String getMethodeCalcul() {
        return methodeCalcul == null ? "" : methodeCalcul.replaceAll("[\n\r]", " ");
    }

    public void setMethodeCalcul(String methodeCalcul) {
        this.methodeCalcul = methodeCalcul;
    }

    public String getOrigine() {
        return origine == null ? "" : origine.replaceAll("[\n\r]", " ");
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public String getTypeMesures() {
        return typeMesures == null ? "" : typeMesures.replaceAll("[\n\r]", " ");
    }

    public void setTypeMesures(String typeMesures) {
        this.typeMesures = typeMesures;
    }

    public String getSourceDonnees() {
        return sourceDonnees == null ? "" : sourceDonnees.replaceAll("[\n\r]", " ");
    }

    public void setSourceDonnees(String sourceDonnees) {
        this.sourceDonnees = sourceDonnees;
    }

    public String getEtatValidation() {
        return etatValidation == null ? "" : etatValidation.replaceAll("[\n\r]", " ");
    }

    public void setEtatValidation(String etatValidation) {
        this.etatValidation = etatValidation;
    }

    public String getPosteCalcul() {
        return posteCalcul == null ? "" : posteCalcul.replaceAll("[\n\r]", " ");
    }

    public void setPosteCalcul(String posteCalcul) {
        this.posteCalcul = posteCalcul;
    }

    public String getUnite() {
        return unite == null ? "" : unite.replaceAll("[\n\r]", " ");
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public String getUserCreation() {
        return userCreation == null ? "" : userCreation.replaceAll("[\n\r]", " ");
    }

    public void setUserCreation(String userCreation) {
        this.userCreation = userCreation;
    }

    public String getStatutBibliotheque() {
        return statutBibliotheque == null ? "" : statutBibliotheque.replaceAll("[\n\r]", " ");
    }

    public void setStatutBibliotheque(String statutBibliotheque) {
        this.statutBibliotheque = statutBibliotheque;
    }

    public String getDefinition() {
        return definition == null ? "" : definition.replaceAll("[\n\r]", " ");
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public static ColumnPositionMappingStrategy<IndicateurOnlyExportCSVBean> getIndicateurOnlyMappingStrategy() {
        ColumnPositionMappingStrategy<IndicateurOnlyExportCSVBean> strategy = new ColumnPositionMappingStrategy<IndicateurOnlyExportCSVBean>() {
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
