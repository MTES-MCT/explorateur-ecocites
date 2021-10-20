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

package com.efficacity.explorateurecocites.beans.biz.reporting.misc;

import com.efficacity.explorateurecocites.beans.model.Contact;

import java.util.List;
import java.util.stream.Collectors;

public class ContactReport {
    private String nom;
    private String prenom;
    private String entite;
    private String mail;

    public ContactReport(Contact contact) {
        nom = contact.getNom();
        prenom = contact.getPrenom();
        entite = contact.getEntite();
        mail = contact.getEmail();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(final String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(final String prenom) {
        this.prenom = prenom;
    }

    public String getEntite() {
        return entite;
    }

    public void setEntite(final String entite) {
        this.entite = entite;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(final String mail) {
        this.mail = mail;
    }

    public static List<ContactReport> fromListContacts(final List<Contact> contacts) {
        return contacts.stream()
                .map(ContactReport::new)
                .collect(Collectors.toList());
    }
}
