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
