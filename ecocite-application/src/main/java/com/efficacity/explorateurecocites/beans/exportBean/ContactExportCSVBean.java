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

import com.efficacity.explorateurecocites.beans.model.AssoObjetContact;
import com.opencsv.bean.ColumnPositionMappingStrategy;

/**
 * Created by ktoomey on 09/03/2018.
 */
public class ContactExportCSVBean {
    private String idObjet= "";
    private String email= "";
    private String nomObjet="";
    private String idContact= "";
    private String nomContact= "";
    private String prenomContact= "";
    private String typeContact= "";

    public static final String END_LINE_REGEX = "[\n\r]";

    public ContactExportCSVBean(AssoObjetContact assoObjetContact) {
        if(assoObjetContact.getIdObjet()!=null){ this.idObjet = assoObjetContact.getIdObjet().toString();}
        if(assoObjetContact.getIdContact()!=null){this.idContact = assoObjetContact.getIdContact().toString();}
        if (assoObjetContact.getImportance()!=null){this.typeContact = assoObjetContact.getImportance().toString();}
    }

    public ContactExportCSVBean() {
    }

    public String getIdObjet() {
        return idObjet == null ? "" : idObjet.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdObjet(String idObjet) {
        this.idObjet = idObjet;
    }

    public String getIdContact() {
        return idContact == null ? "" : idContact.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdContact(String idContact) {
        this.idContact = idContact;
    }

    public String getNomContact() {
        return nomContact;
    }

    public void setNomContact(String nomContact) {
        this.nomContact = nomContact;
    }

    public String getPrenomContact() {
        return prenomContact == null ? "" : prenomContact.replaceAll(END_LINE_REGEX, " ");
    }

    public void setPrenomContact(String prenomContact) {
        this.prenomContact = prenomContact;
    }

    public String getTypeContact() {
        return typeContact == null ? "" : typeContact.replaceAll(END_LINE_REGEX, " ");
    }

    public void setTypeContact(String typeContact) {
        this.typeContact = typeContact;
    }

    public String getEmail() {
        return email == null ? "" : email.replaceAll(END_LINE_REGEX, " ");
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomObjet() {
        return nomObjet == null ? "" : nomObjet.replaceAll(END_LINE_REGEX, " ");
    }

    public void setNomObjet(String nomObjet) {
        this.nomObjet = nomObjet;
    }

    public static ColumnPositionMappingStrategy<ContactExportCSVBean> getContactMappingStrategyForAction() {
        ColumnPositionMappingStrategy<ContactExportCSVBean> strategy = new ColumnPositionMappingStrategy<>() {
            @Override
            public String[] generateHeader() {
                return new String[]{"id Action", "Nom action", "id Contact", "Prénom contact", "Nom Contact", "Email", "Niveau Contact (principal/secondaire)"};
            }
        };
        strategy.setType(ContactExportCSVBean.class);
        String[] fields = {"idObjet", "nomObjet", "idContact", "prenomContact", "nomContact", "email", "typeContact"};
        strategy.setColumnMapping(fields);
        strategy.generateHeader();
        return strategy;
    }
    public static ColumnPositionMappingStrategy<ContactExportCSVBean> getContactMappingStrategyForEcocite() {
        ColumnPositionMappingStrategy<ContactExportCSVBean> strategy = new ColumnPositionMappingStrategy<>() {
            @Override
            public String[] generateHeader() {
                return new String[]{"Id ÉcoCité", "ÉcoCité", "Id contact", "Prénom du contact", "Nom du contact", "Email", "Niveau du contact (principal/secondaire)"};
            }
        };
        strategy.setType(ContactExportCSVBean.class);
        String[] fields = {"idObjet", "nomObjet", "idContact", "prenomContact", "nomContact", "email", "typeContact"};
        strategy.setColumnMapping(fields);
        strategy.generateHeader();
        return strategy;
    }

}
