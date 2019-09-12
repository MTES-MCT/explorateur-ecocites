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

import com.efficacity.explorateurecocites.beans.model.Contact;
import com.opencsv.bean.ColumnPositionMappingStrategy;

/**
 * Created by ktoomey on 16/03/2018.
 */
public class ContactOnlyExportCSVBean {
    private String idContact= "";
    private String nom= "";
    private String prenom= "";
    private String idEcocite= "";
    private String ecocite= "";
    private String entite= "";
    private String fonction= "";
    private String telephone= "";
    private String email= "";

    public ContactOnlyExportCSVBean( ) {
    }

    public ContactOnlyExportCSVBean(Contact contact) {
        if(contact.getId()!=null){this.idContact = contact.getId().toString();}
        this.nom = contact.getNom();
        this.prenom = contact.getPrenom();
        this.entite = contact.getEntite();
        this.fonction = contact.getFonction();
        this.telephone = contact.getTelephone();
        this.email = contact.getEmail();
        if(contact.getIdEcocite()!=null){this.idEcocite = contact.getIdEcocite().toString();}
    }

    public String getIdContact() {
        return idContact == null ? "" : idContact.replaceAll("[\n\r]", " ");
    }

    public void setIdContact(String idContact) {
        this.idContact = idContact;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom == null ? "" : prenom.replaceAll("[\n\r]", " ");
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEntite() {
        return entite == null ? "" : entite.replaceAll("[\n\r]", " ");
    }

    public void setEntite(String entite) {
        this.entite = entite;
    }

    public String getFonction() {
        return fonction == null ? "" : fonction.replaceAll("[\n\r]", " ");
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getTelephone() {
        return telephone == null ? "" : telephone.replaceAll("[\n\r]", " ");
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email == null ? "" : email.replaceAll("[\n\r]", " ");
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEcocite() {
        return ecocite == null ? "" : ecocite.replaceAll("[\n\r]", " ");
    }

    public void setEcocite(String ecocite) {
        this.ecocite = ecocite;
    }

    public String getIdEcocite() {
        return idEcocite == null ? "" : idEcocite.replaceAll("[\n\r]", " ");
    }

    public void setIdEcocite(String idEcocite) {
        this.idEcocite = idEcocite;
    }

    public static ColumnPositionMappingStrategy<ContactOnlyExportCSVBean> getContactOnlyMappingStrategy() {
        ColumnPositionMappingStrategy<ContactOnlyExportCSVBean> strategy = new ColumnPositionMappingStrategy<ContactOnlyExportCSVBean>() {
            @Override
            public String[] generateHeader() {
                return new String[]{"Id contact", "Prénom", "Nom", "Entité", "Fonction", "Téléphone", "Email","ÉcoCité","Id ÉcoCité"};
            }
        };
        strategy.setType(ContactOnlyExportCSVBean.class);
        String[] fields = {"idContact", "prenom", "nom", "entite", "fonction", "telephone", "email","ecocite","idEcocite"};
        strategy.setColumnMapping(fields);
        strategy.generateHeader();
        return strategy;
    }
}
