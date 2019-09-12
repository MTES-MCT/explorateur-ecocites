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

package com.efficacity.explorateurecocites.beans.biz.reporting.ecocite;

import com.efficacity.explorateurecocites.beans.biz.EcociteBean;
import com.efficacity.explorateurecocites.beans.biz.reporting.misc.ContactReport;
import com.efficacity.explorateurecocites.beans.model.Contact;

import java.util.List;

import static com.efficacity.explorateurecocites.ui.bo.utils.ReportingHelper.getContentOrPlaceHolder;

public class FicheIdentite {
    private String nombreCommunes;
    private String nombreHabitants;
    private String superficie;
    private String ecociteDepuis;
    private String porteurProjet;
    private String soutientPIA;
    private String descriptionStrategie;
    private String descriptionPerimetre;
    private String lien;
    private List<ContactReport> contacts;
    private String partenaires;

    public FicheIdentite(final EcociteBean ecocite, List<Contact> contacts) {
        this.nombreCommunes = getContentOrPlaceHolder(ecocite.getNbCommunes());
        this.nombreHabitants = getContentOrPlaceHolder(ecocite.getNbHabitants());
        this.superficie = getContentOrPlaceHolder(ecocite.getSuperficieKm2(), " km²");
        this.ecociteDepuis = getContentOrPlaceHolder(ecocite.getAnneeAdhesion());
        this.porteurProjet = getContentOrPlaceHolder(ecocite.getPorteur());
        this.partenaires = getContentOrPlaceHolder(ecocite.getAllPartenaires());
        this.soutientPIA = getContentOrPlaceHolder(ecocite.getSoutienPiaDetail());
        this.descriptionStrategie = getContentOrPlaceHolder(ecocite.getDescStrategie());
        this.descriptionPerimetre = getContentOrPlaceHolder(ecocite.getDescPerimetre());
        this.lien = getContentOrPlaceHolder(ecocite.getLien());
        this.contacts = ContactReport.fromListContacts(contacts);
    }

    public String getNombreCommunes() {
        return nombreCommunes;
    }

    public void setNombreCommunes(final String nombreCommunes) {
        this.nombreCommunes = nombreCommunes;
    }

    public String getNombreHabitants() {
        return nombreHabitants;
    }

    public void setNombreHabitants(final String nombreHabitants) {
        this.nombreHabitants = nombreHabitants;
    }

    public String getSuperficie() {
        return superficie;
    }

    public void setSuperficie(final String superficie) {
        this.superficie = superficie;
    }

    public String getEcociteDepuis() {
        return ecociteDepuis;
    }

    public void setEcociteDepuis(final String ecociteDepuis) {
        this.ecociteDepuis = ecociteDepuis;
    }

    public String getPorteurProjet() {
        return porteurProjet;
    }

    public void setPorteurProjet(final String porteurProjet) {
        this.porteurProjet = porteurProjet;
    }

    public String getSoutientPIA() {
        return soutientPIA;
    }

    public void setSoutientPIA(final String soutientPIA) {
        this.soutientPIA = soutientPIA;
    }

    public String getDescriptionStrategie() {
        return descriptionStrategie;
    }

    public void setDescriptionStrategie(final String descriptionStrategie) {
        this.descriptionStrategie = descriptionStrategie;
    }

    public String getDescriptionPerimetre() {
        return descriptionPerimetre;
    }

    public void setDescriptionPerimetre(final String descriptionPerimetre) {
        this.descriptionPerimetre = descriptionPerimetre;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(final String lien) {
        this.lien = lien;
    }

    public List<ContactReport> getContacts() {
        return contacts;
    }

    public void setContacts(final List<ContactReport> contacts) {
        this.contacts = contacts;
    }

    public String getPartenaires() {
        return partenaires;
    }

    public void setPartenaires(final String partenaires) {
        this.partenaires = partenaires;
    }
}
