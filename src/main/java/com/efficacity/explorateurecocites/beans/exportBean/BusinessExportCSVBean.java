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

import com.efficacity.explorateurecocites.beans.model.Business;
import com.opencsv.bean.ColumnPositionMappingStrategy;

/**
 * Created by ktoomey on 09/03/2018.
 */
public class BusinessExportCSVBean {
    private String idAction= "";
    private String nomAction= "";
    private String numeroAction= "";
    private String idBusiness= "";
    private String numeroAffaire= "";
    private String nomAffaire= "";

    public BusinessExportCSVBean(Business business) {
        if(business.getIdAction()!=null){this.idAction = business.getIdAction().toString();}
        if(business.getId()!=null){this.idBusiness = business.getId().toString();}
        this.numeroAffaire = business.getNumeroAffaire();
        this.nomAffaire = business.getNom();
    }

    public BusinessExportCSVBean() {
    }

    public String getIdAction() {
        return idAction == null ? "" : idAction.replaceAll("[\n\r]", " ");
    }

    public void setIdAction(String idAction) {
        this.idAction = idAction;
    }

    public String getIdBusiness() {
        return idBusiness;
    }

    public void setIdBusiness(String idBusiness) {
        this.idBusiness = idBusiness;
    }

    public String getNumeroAffaire() {
        return numeroAffaire;
    }

    public void setNumeroAffaire(String numeroAffaire) {
        this.numeroAffaire = numeroAffaire;
    }

    public String getNomAction() {
        return nomAction;
    }

    public void setNomAction(String nomAction) {
        this.nomAction = nomAction;
    }

    public String getNomAffaire() {
        return nomAffaire;
    }

    public void setNomAffaire(String nomAffaire) {
        this.nomAffaire = nomAffaire;
    }

    public String getNumeroAction() {
        return numeroAction;
    }

    public void setNumeroAction(String numeroAction) {
        this.numeroAction = numeroAction;
    }

    public static ColumnPositionMappingStrategy<BusinessExportCSVBean> getBusinessMappingStrategy() {
        ColumnPositionMappingStrategy<BusinessExportCSVBean> strategy = new ColumnPositionMappingStrategy<BusinessExportCSVBean>() {
            @Override
            public String[] generateHeader() {
                return new String[]{"Id action","Nom action","Numéro action","Id affaire","Numéro affaire","Nom affaire"}; // Les Headers
            }
        };
        strategy.setType(BusinessExportCSVBean.class);
        String[] fields = {"idAction","nomAction", "numeroAction","idBusiness","numeroAffaire","nomAffaire"}; // Les attributs dans le bon ordre
        strategy.setColumnMapping(fields);
        strategy.generateHeader();
        return strategy;
    }

}
