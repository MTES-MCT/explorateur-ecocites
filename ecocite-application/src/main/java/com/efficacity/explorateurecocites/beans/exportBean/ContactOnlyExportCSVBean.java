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

    public static final String END_LINE_REGEX = "[\n\r]";

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
        return idContact == null ? "" : idContact.replaceAll(END_LINE_REGEX, " ");
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
        return prenom == null ? "" : prenom.replaceAll(END_LINE_REGEX, " ");
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEntite() {
        return entite == null ? "" : entite.replaceAll(END_LINE_REGEX, " ");
    }

    public void setEntite(String entite) {
        this.entite = entite;
    }

    public String getFonction() {
        return fonction == null ? "" : fonction.replaceAll(END_LINE_REGEX, " ");
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getTelephone() {
        return telephone == null ? "" : telephone.replaceAll(END_LINE_REGEX, " ");
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email == null ? "" : email.replaceAll(END_LINE_REGEX, " ");
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEcocite() {
        return ecocite == null ? "" : ecocite.replaceAll(END_LINE_REGEX, " ");
    }

    public void setEcocite(String ecocite) {
        this.ecocite = ecocite;
    }

    public String getIdEcocite() {
        return idEcocite == null ? "" : idEcocite.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdEcocite(String idEcocite) {
        this.idEcocite = idEcocite;
    }

    public static ColumnPositionMappingStrategy<ContactOnlyExportCSVBean> getContactOnlyMappingStrategy() {
        ColumnPositionMappingStrategy<ContactOnlyExportCSVBean> strategy = new ColumnPositionMappingStrategy<>() {
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
