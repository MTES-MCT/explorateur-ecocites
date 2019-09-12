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

import com.efficacity.explorateurecocites.beans.model.Action;
import com.efficacity.explorateurecocites.ui.bo.utils.HtmlToPlainText;
import com.efficacity.explorateurecocites.ui.bo.utils.ReportingHelper;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Created by ktoomey on 01/03/2018.
 */
public class ActionExportCSVBean {

    private String idAction = "";
    private String numeroAction = "";
    private String nomPublic = "";
    private String idEcocite = "";
    private String ecocite = "";
    private String dateModification = "";
    private String utilisateurModification = "";
    private String tranche = "";
    private String axePrincipal = "";
    private String typeFinancement = "";
    private String etatAvancement = "";
    private String description = "";
    private String etatPublication = "";
    private String latitude = "";
    private String longitude = "";
    private String lien = "";
    private String echelle = "";
    private String dateDebut = "";
    private String dateFin = "";
    private String evaluationNiveauGlobal = "";
    private String maitriseOuvrage = "";
    private String etapeCaracterisation = "";
    private String etapeIndicateur = "";
    private String etapeEvaluation = "";
    private String etapeMesure = "";
    private String etapeContexte = "";


    public static String toSimpleTexte(String html) {
        if (html == null)  {
            return "";
        }
        Document doc = Jsoup.parse(html);
        return new HtmlToPlainText().getPlainText(doc);
    }

    public ActionExportCSVBean(Action action) {
        if (action.getId() != null) {
            this.idAction = action.getId().toString();
        }
        this.numeroAction = action.getNumeroAction();
        this.nomPublic = action.getNomPublic();
        this.tranche = action.getTrancheExecution();
        this.typeFinancement = action.getTypeFinancement();
        this.etatAvancement = action.getEtatAvancement();
        this.description = ReportingHelper
                .toSimpleTexte(action.getDescription())
                .replaceAll("[\n\r]", " ");
        this.etatPublication = action.getEtatPublication();
        this.latitude = action.getLatitude();
        this.longitude = action.getLongitude();
        this.lien = action.getLien();
        this.echelle = action.getEchelle();
        if (action.getDateDebut() != null) {
            this.dateDebut = action.getDateDebut().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT));
        }
        if (action.getDateFin() != null) {
            this.dateFin = action.getDateFin().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT));
        }
        if (action.getDateModification() != null) {
            this.dateModification = action.getDateModification().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT));
        }
        this.utilisateurModification = action.getUserModification();
        if (action.getEvaluationNiveauGlobal() != null) {
            this.evaluationNiveauGlobal = action.getEvaluationNiveauGlobal().toString();
        }
        if (action.getIdEcocite() != null) {
            this.idEcocite = action.getIdEcocite().toString();
        }
    }

    public ActionExportCSVBean() {

    }

    public String getIdAction() {
        return idAction == null ? "" : idAction.replaceAll("[\n\r]", " ");
    }

    public void setIdAction(String idAction) {
        this.idAction = idAction;
    }

    public String getNumeroAction() {
        return numeroAction == null ? "" : numeroAction.replaceAll("[\n\r]", " ");
    }

    public void setNumeroAction(String numeroAction) {
        this.numeroAction = numeroAction;
    }

    public String getNomPublic() {
        return nomPublic == null ? "" : nomPublic.replaceAll("[\n\r]", " ");
    }

    public void setNomPublic(String nomPublic) {
        this.nomPublic = nomPublic;
    }

    public String getIdEcocite() {
        return idEcocite == null ? "" : idEcocite.replaceAll("[\n\r]", " ");
    }

    public void setIdEcocite(String idEcocite) {
        this.idEcocite = idEcocite;
    }

    public String getEcocite() {
        return ecocite == null ? "" : ecocite.replaceAll("[\n\r]", " ");
    }

    public void setEcocite(String ecocite) {
        this.ecocite = ecocite;
    }

    public String getTranche() {
        return tranche == null ? "" : tranche.replaceAll("[\n\r]", " ");
    }

    public void setTranche(String tranche) {
        this.tranche = tranche;
    }

    public String getTypeFinancement() {
        return typeFinancement == null ? "" : typeFinancement.replaceAll("[\n\r]", " ");
    }

    public void setTypeFinancement(String typeFinancement) {
        this.typeFinancement = typeFinancement;
    }

    public String getEtatAvancement() {
        return etatAvancement == null ? "" : etatAvancement.replaceAll("[\n\r]", " ");
    }

    public void setEtatAvancement(String etatAvancement) {
        this.etatAvancement = etatAvancement;
    }

    public String getDescription() {
        return description == null ? "" : description.replaceAll("[\n\r]", " ");
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEtatPublication() {
        return etatPublication == null ? "" : etatPublication.replaceAll("[\n\r]", " ");
    }

    public void setEtatPublication(String etatPublication) {
        this.etatPublication = etatPublication;
    }

    public String getLatitude() {
        return latitude == null ? "" : latitude.replaceAll("[\n\r]", " ");
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude == null ? "" : longitude.replaceAll("[\n\r]", " ");
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLien() {
        return lien == null ? "" : lien.replaceAll("[\n\r]", " ");
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getEchelle() {
        return echelle == null ? "" : echelle.replaceAll("[\n\r]", " ");
    }

    public void setEchelle(String echelle) {
        this.echelle = echelle;
    }

    public String getDateDebut() {
        return dateDebut == null ? "" : dateDebut.replaceAll("[\n\r]", " ");
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin == null ? "" : dateFin.replaceAll("[\n\r]", " ");
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getEvaluationNiveauGlobal() {
        return evaluationNiveauGlobal == null ? "" : evaluationNiveauGlobal.replaceAll("[\n\r]", " ");
    }

    public void setEvaluationNiveauGlobal(String niveauEvaluationGlobal) {
        this.evaluationNiveauGlobal = niveauEvaluationGlobal;
    }

    public String getDateModification() {
        return dateModification == null ? "" : dateModification.replaceAll("[\n\r]", " ");
    }

    public void setDateModification(String dateModification) {
        this.dateModification = dateModification;
    }

    public String getUtilisateurModification() {
        return utilisateurModification == null ? "" : utilisateurModification.replaceAll("[\n\r]", " ");
    }

    public void setUtilisateurModification(String utilisateurModification) {
        this.utilisateurModification = utilisateurModification;
    }

    public String getMaitriseOuvrage() {
        return maitriseOuvrage == null ? "" : maitriseOuvrage.replaceAll("[\n\r]", " ");
    }

    public void setMaitriseOuvrage(String maitriseOuvrage) {
        this.maitriseOuvrage = maitriseOuvrage;
    }

    public String getAxePrincipal() {
        return axePrincipal == null ? "" : axePrincipal.replaceAll("[\n\r]", " ");
    }

    public void setAxePrincipal(String axePrincipal) {
        this.axePrincipal = axePrincipal;
    }

    public String getEtapeCaracterisation() {
        return etapeCaracterisation == null ? "" : etapeCaracterisation.replaceAll("[\n\r]", " ");
    }

    public void setEtapeCaracterisation(String etapeCaracterisation) {
        this.etapeCaracterisation = etapeCaracterisation;
    }

    public String getEtapeIndicateur() {
        return etapeIndicateur == null ? "" : etapeIndicateur.replaceAll("[\n\r]", " ");
    }

    public void setEtapeIndicateur(String etapeIndicateur) {
        this.etapeIndicateur = etapeIndicateur;
    }

    public String getEtapeEvaluation() {
        return etapeEvaluation == null ? "" : etapeEvaluation.replaceAll("[\n\r]", " ");
    }

    public void setEtapeEvaluation(String etapeEvaluation) {
        this.etapeEvaluation = etapeEvaluation;
    }

    public String getEtapeMesure() {
        return etapeMesure == null ? "" : etapeMesure.replaceAll("[\n\r]", " ");
    }

    public void setEtapeMesure(String etapeMesure) {
        this.etapeMesure = etapeMesure;
    }

    public String getEtapeContexte() {
        return etapeContexte == null ? "" : etapeContexte.replaceAll("[\n\r]", " ");
    }

    public void setEtapeContexte(String etapeContexte) {
        this.etapeContexte = etapeContexte;
    }

    public static ColumnPositionMappingStrategy<ActionExportCSVBean> getActionMappingStrategy() {
        ColumnPositionMappingStrategy<ActionExportCSVBean> strategy = new ColumnPositionMappingStrategy<ActionExportCSVBean>() {
            @Override
            public String[] generateHeader() {
                return new String[]{"Id action", "Numéro d'action", "Nom action", "Id ÉcoCité", "ÉcoCité", "Axe principale", "Type de financement",
                        "Tranche", "Etat d'avancement", "Niveau d'innovation global", "Maîtrise d'ouvrage", "Description", "Echelle",
                        "Date de début", "Date de fin", "Latitude", "Longitude", "Lien", "Etat de publication", "Date dernière modification",
                        "Utilisateur dernière modification", "Etape caractérisation", "Etape choix indicateur", "Etape évaluation",
                        "Etape renseignement indicateur", "Etape facteurs de succès"}; // Les Headers
            }
        };
        strategy.setType(ActionExportCSVBean.class);
        String[] fields = {"idAction", "numeroAction", "nomPublic", "idEcocite", "ecocite", "axePrincipal", "typeFinancement", "tranche", "etatAvancement",
                "evaluationNiveauGlobal", "maitriseOuvrage", "description", "echelle", "dateDebut", "dateFin", "latitude", "longitude", "lien",
                "etatPublication", "dateModification", "utilisateurModification", "etapeCaracterisation", "etapeIndicateur",
                "etapeEvaluation", "etapeMesure", "etapeContexte"}; // Les attributs dans le bon ordre
        strategy.setColumnMapping(fields);
        strategy.generateHeader();
        return strategy;
    }
}
