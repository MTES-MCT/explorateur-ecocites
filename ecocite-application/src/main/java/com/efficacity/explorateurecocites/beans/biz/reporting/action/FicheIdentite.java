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

package com.efficacity.explorateurecocites.beans.biz.reporting.action;

import com.efficacity.explorateurecocites.beans.biz.ActionBean;
import com.efficacity.explorateurecocites.beans.biz.reporting.misc.ContactReport;
import com.efficacity.explorateurecocites.beans.model.Axe;
import com.efficacity.explorateurecocites.beans.model.Contact;

import java.util.List;

import static com.efficacity.explorateurecocites.ui.bo.utils.ReportingHelper.EMPTY;
import static com.efficacity.explorateurecocites.ui.bo.utils.ReportingHelper.getContentOrPlaceHolder;

public class FicheIdentite {
    private String typeFinancement;
    private String etatAvancement;
    private String axePrincipal;
    private String tranche;
    private String echelle;
    private List<String> maitriseOuvrages;
    private String description;
    private List<ContactReport> contacts;

    public FicheIdentite(final ActionBean bean, final Axe axe, List<Contact> contacts) {
        typeFinancement = bean.getTypeFinancementEnum() != null ? bean.getTypeFinancementEnum().getLibelle() : EMPTY;
        etatAvancement = bean.getEtatAvancementEnum() != null ? bean.getEtatAvancementEnum().getLibelle() : EMPTY;
        axePrincipal = axe == null ? EMPTY : axe.getLibelle();
        tranche = bean.getTrancheExecutionEnum() != null ? bean.getTrancheExecutionEnum().getLibelle() : EMPTY;
        echelle = bean.getEchelleEnum() != null ? bean.getEchelleEnum().getLibelle() : EMPTY;
        maitriseOuvrages = bean.getAllMaitriseOuvrage();
        description = getContentOrPlaceHolder(bean.getDescription());
        this.contacts = ContactReport.fromListContacts(contacts);
    }

    public String getTypeFinancement() {
        return typeFinancement;
    }

    public void setTypeFinancement(final String typeFinancement) {
        this.typeFinancement = typeFinancement;
    }

    public String getEtatAvancement() {
        return etatAvancement;
    }

    public void setEtatAvancement(final String etatAvancement) {
        this.etatAvancement = etatAvancement;
    }

    public String getAxePrincipal() {
        return axePrincipal;
    }

    public void setAxePrincipal(final String axePrincipal) {
        this.axePrincipal = axePrincipal;
    }

    public String getTranche() {
        return tranche;
    }

    public void setTranche(final String tranche) {
        this.tranche = tranche;
    }

    public String getEchelle() {
        return echelle;
    }

    public void setEchelle(final String echelle) {
        this.echelle = echelle;
    }

    public List<String> getMaitriseOuvrages() {
        return maitriseOuvrages;
    }

    public void setMaitriseOuvrages(final List<String> maitriseOuvrages) {
        this.maitriseOuvrages = maitriseOuvrages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public List<ContactReport> getContacts() {
        return contacts;
    }

    public void setContacts(final List<ContactReport> contacts) {
        this.contacts = contacts;
    }
}
