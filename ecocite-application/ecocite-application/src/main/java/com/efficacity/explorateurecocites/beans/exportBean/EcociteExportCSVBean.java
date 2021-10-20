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

import com.efficacity.explorateurecocites.beans.model.Ecocite;
import com.efficacity.explorateurecocites.ui.bo.utils.ReportingHelper;
import com.opencsv.bean.ColumnPositionMappingStrategy;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Created by ktoomey on 09/03/2018.
 */
public class EcociteExportCSVBean {
    private String idEcocite= "";
    private String nomEcocite= "";
    private String region= "";
    private String dateAdhesion= "";
    private String etatValidation= "";
    private String etatPublication= "";
    private String dateModification= "";
    private String utilisateurModification="";
    private String descriptionStrategie= "";
    private String descriptionPerimetre= "";
    private String porteurProjet= "";
    private String partenairesProjet= "";
    private String superficie= "";
    private String nombreCommunes= "";
    private String soutienPIA= "";
    private String nombreHabitants= "";
    private String lien= "";
    private String latitude= "";
    private String longitude= "";

    private String etapeCaracterisation= "";
    private String etapeIndicateur= "";
    private String etapeMesure= "";
    private String etapeContexte= "";

    public static final String END_LINE_REGEX = "[\n\r]";

    public EcociteExportCSVBean(Ecocite ecocite) {
        if (ecocite.getId() != null) {
            this.idEcocite = ecocite.getId().toString();
        }
        this.nomEcocite = ecocite.getNom();
        this.dateAdhesion = ecocite.getAnneeAdhesion();
        this.etatPublication = ecocite.getEtatPublication();
        this.porteurProjet = ecocite.getPorteur();
        if(ecocite.getPartenaire()!=null) {
            this.partenairesProjet = ecocite.getPartenaire().replaceAll(END_LINE_REGEX, " ");
        }
        this.utilisateurModification = ecocite.getUserModification();
        this.lien = ecocite.getLien();
        this.latitude = ecocite.getLatitude();
        this.longitude = ecocite.getLongitude();
        this.descriptionStrategie = ReportingHelper
                .toSimpleTexte(ecocite.getDescStrategie())
                .replaceAll(END_LINE_REGEX, " ");
        this.descriptionPerimetre = ReportingHelper
                .toSimpleTexte(ecocite.getDescPerimetre())
                .replaceAll(END_LINE_REGEX, " ");
        this.nombreCommunes = ecocite.getNbCommunes() == null ? "" :
                ecocite.getNbCommunes().toString();
        this.superficie = ecocite.getSuperficieKm2() == null ? "" :
                ecocite.getSuperficieKm2().toString();
        this.nombreHabitants = ecocite.getNbHabitants() == null ? "" :
                ecocite.getNbHabitants().toString();
        this.soutienPIA = ecocite.getSoutienPiaDetail() == null ? "" :
                ecocite.getSoutienPiaDetail();
        this.dateModification = ecocite.getDateModification() == null ? "" :
                ecocite.getDateModification().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT));
    }

    public EcociteExportCSVBean() {
    }

    public String getIdEcocite() {
        return idEcocite == null ? "" : idEcocite.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdEcocite(String idEcocite) {
        this.idEcocite = idEcocite;
    }

    public String getNomEcocite() {return nomEcocite == null ? "" : nomEcocite.replaceAll(END_LINE_REGEX, " ");}

    public void setNomEcocite(String nomEcocite) {this.nomEcocite = nomEcocite;}

    public String getRegion() {
        return region == null ? "" : region.replaceAll(END_LINE_REGEX, " ");
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDateAdhesion() {
        return dateAdhesion == null ? "" : dateAdhesion.replaceAll(END_LINE_REGEX, " ");
    }

    public void setDateAdhesion(String dateAdhesion) {
        this.dateAdhesion = dateAdhesion;
    }

    public String getEtatValidation() {
        return etatValidation == null ? "" : etatValidation.replaceAll(END_LINE_REGEX, " ");
    }

    public void setEtatValidation(String etatValidation) {
        this.etatValidation = etatValidation;
    }

    public String getEtatPublication() {
        return etatPublication == null ? "" : etatPublication.replaceAll(END_LINE_REGEX, " ");
    }

    public void setEtatPublication(String etatPublication) {
        this.etatPublication = etatPublication;
    }

    public String getDateModification() {
        return dateModification == null ? "" : dateModification.replaceAll(END_LINE_REGEX, " ");
    }

    public void setDateModification(String dateModification) {
        this.dateModification = dateModification;
    }

    public String getUtilisateurModification() {
        return utilisateurModification == null ? "" : utilisateurModification.replaceAll(END_LINE_REGEX, " ");
    }

    public void setUtilisateurModification(String utilisateurModification) {
        this.utilisateurModification = utilisateurModification;
    }

    public String getDescriptionStrategie() {
        return descriptionStrategie == null ? "" : descriptionStrategie.replaceAll(END_LINE_REGEX, " ");
    }

    public void setDescriptionStrategie(String descriptionStrategie) {
        this.descriptionStrategie = descriptionStrategie;
    }

    public String getDescriptionPerimetre() {
        return descriptionPerimetre == null ? "" : descriptionPerimetre.replaceAll(END_LINE_REGEX, " ");
    }

    public void setDescriptionPerimetre(String descriptionPerimetre) {
        this.descriptionPerimetre = descriptionPerimetre;
    }

    public String getPorteurProjet() {
        return porteurProjet == null ? "" : porteurProjet.replaceAll(END_LINE_REGEX, " ");
    }

    public void setPorteurProjet(String porteurProjet) {
        this.porteurProjet = porteurProjet;
    }

    public String getPartenairesProjet() {
        return partenairesProjet == null ? "" : partenairesProjet.replaceAll(END_LINE_REGEX, " ");
    }

    public void setPartenairesProjet(String partenairesProjet) {
        this.partenairesProjet = partenairesProjet;
    }

    public String getNombreCommunes() {
        return nombreCommunes == null ? "" : nombreCommunes.replaceAll(END_LINE_REGEX, " ");
    }

    public void setNombreCommunes(String nombreCommunes) {
        this.nombreCommunes = nombreCommunes;
    }

    public String getSuperficie() {
        return superficie == null ? "" : superficie.replaceAll(END_LINE_REGEX, " ");
    }

    public void setSuperficie(String superficie) {
        this.superficie = superficie;
    }

    public String getNombreHabitants() {
        return nombreHabitants == null ? "" : nombreHabitants.replaceAll(END_LINE_REGEX, " ");
    }

    public void setNombreHabitants(String nombreHabitants) {
        this.nombreHabitants = nombreHabitants;
    }

    public String getSoutienPIA() {
        return soutienPIA == null ? "" : soutienPIA.replaceAll(END_LINE_REGEX, " ");
    }

    public void setSoutienPIA(String soutienPIA) {
        this.soutienPIA = soutienPIA;
    }

    public String getLien() {
        return lien == null ? "" : lien.replaceAll(END_LINE_REGEX, " ");
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getLatitude() {
        return latitude == null ? "" : latitude.replaceAll(END_LINE_REGEX, " ");
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude == null ? "" : longitude.replaceAll(END_LINE_REGEX, " ");
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getEtapeCaracterisation() {
        return etapeCaracterisation == null ? "" : etapeCaracterisation.replaceAll(END_LINE_REGEX, " ");
    }

    public void setEtapeCaracterisation(String etapeCaracterisation) {
        this.etapeCaracterisation = etapeCaracterisation;
    }

    public String getEtapeIndicateur() {
        return etapeIndicateur == null ? "" : etapeIndicateur.replaceAll(END_LINE_REGEX, " ");
    }

    public void setEtapeIndicateur(String etapeIndicateur) {
        this.etapeIndicateur = etapeIndicateur;
    }

    public String getEtapeMesure() {
        return etapeMesure == null ? "" : etapeMesure.replaceAll(END_LINE_REGEX, " ");
    }

    public void setEtapeMesure(String etapeMesure) {
        this.etapeMesure = etapeMesure;
    }

    public String getEtapeContexte() {
        return etapeContexte == null ? "" : etapeContexte.replaceAll(END_LINE_REGEX, " ");
    }

    public void setEtapeContexte(String etapeContexte) {
        this.etapeContexte = etapeContexte;
    }

    public static ColumnPositionMappingStrategy<EcociteExportCSVBean> getEcociteMappingStrategy() {
        ColumnPositionMappingStrategy<EcociteExportCSVBean> strategy = new ColumnPositionMappingStrategy<>() {
            @Override
            public String[] generateHeader() {
                return new String[]{"Id ÉcoCité","Nom ÉcoCité","Région","ÉcoCité depuis","Porteur du projet", "Partenaires du projet",
                        "Description de la stratégie territoriale","Description du périmètre ÉcoCité","Nombre de communes",
                        "Nombre d'habitants","Superficie (km2)","Soutien PIA","Lien","Latitude","Longitude","Etape caractérisation",
                        "Etape choix indicateur","Etape renseignement indicateur","Etape impact du programme","Etat de publication",
                        "Date dernière modification","Utilisateur dernière modification"}; // Les Headers
            }
        };
        strategy.setType(EcociteExportCSVBean.class);
        String[] fields = {"idEcocite","nomEcocite","region","dateAdhesion","porteurProjet","partenairesProjet","descriptionStrategie",
                "descriptionPerimetre","nombreCommunes","nombreHabitants","superficie","soutienPIA","lien","latitude","longitude",
                "etapeCaracterisation","etapeIndicateur","etapeMesure","etapeContexte","etatPublication",
                "dateModification","utilisateurModification"}; // Les attributs dans le bon ordre
        strategy.setColumnMapping(fields);
        strategy.generateHeader();
        return strategy;
    }
}
