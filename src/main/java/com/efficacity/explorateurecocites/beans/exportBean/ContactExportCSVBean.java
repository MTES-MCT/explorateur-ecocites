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

    public ContactExportCSVBean(AssoObjetContact assoObjetContact) {
        if(assoObjetContact.getIdObjet()!=null){ this.idObjet = assoObjetContact.getIdObjet().toString();}
        if(assoObjetContact.getIdContact()!=null){this.idContact = assoObjetContact.getIdContact().toString();}
        if (assoObjetContact.getImportance()!=null){this.typeContact = assoObjetContact.getImportance().toString();}
    }

    public ContactExportCSVBean() {
    }

    public String getIdObjet() {
        return idObjet == null ? "" : idObjet.replaceAll("[\n\r]", " ");
    }

    public void setIdObjet(String idObjet) {
        this.idObjet = idObjet;
    }

    public String getIdContact() {
        return idContact == null ? "" : idContact.replaceAll("[\n\r]", " ");
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
        return prenomContact == null ? "" : prenomContact.replaceAll("[\n\r]", " ");
    }

    public void setPrenomContact(String prenomContact) {
        this.prenomContact = prenomContact;
    }

    public String getTypeContact() {
        return typeContact == null ? "" : typeContact.replaceAll("[\n\r]", " ");
    }

    public void setTypeContact(String typeContact) {
        this.typeContact = typeContact;
    }

    public String getEmail() {
        return email == null ? "" : email.replaceAll("[\n\r]", " ");
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomObjet() {
        return nomObjet == null ? "" : nomObjet.replaceAll("[\n\r]", " ");
    }

    public void setNomObjet(String nomObjet) {
        this.nomObjet = nomObjet;
    }

    public static ColumnPositionMappingStrategy<ContactExportCSVBean> getContactMappingStrategyForAction() {
        ColumnPositionMappingStrategy<ContactExportCSVBean> strategy = new ColumnPositionMappingStrategy<ContactExportCSVBean>() {
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
        ColumnPositionMappingStrategy<ContactExportCSVBean> strategy = new ColumnPositionMappingStrategy<ContactExportCSVBean>() {
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
