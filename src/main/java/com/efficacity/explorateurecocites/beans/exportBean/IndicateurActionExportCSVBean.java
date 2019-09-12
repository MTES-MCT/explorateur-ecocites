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

import com.efficacity.explorateurecocites.beans.model.AssoIndicateurObjet;
import com.opencsv.bean.ColumnPositionMappingStrategy;

/**
 * Created by ktoomey on 15/03/2018.
 */
public class IndicateurActionExportCSVBean {
    private String idAction= "";
    private String idIndicateur= "";
    private String nomCourt= "";
    private String poste= "";
    private String unite= "";
    private String nomAction= "";

    public IndicateurActionExportCSVBean(AssoIndicateurObjet assoIndicateurObjet) {
        if(assoIndicateurObjet.getIdObjet()!=null){this.idAction = assoIndicateurObjet.getIdObjet().toString();}
        if(assoIndicateurObjet.getIdIndicateur()!=null){this.idIndicateur = assoIndicateurObjet.getIdIndicateur().toString();}
        if(assoIndicateurObjet.getPosteCalcule()!=null){this.poste=assoIndicateurObjet.getPosteCalcule().replaceAll("[\n\r]"," ");}
        if(assoIndicateurObjet.getUnite()!=null){this.unite=assoIndicateurObjet.getUnite().replaceAll("[\n\r]"," ");}
    }

    public String getIdAction() {
        return idAction == null ? "" : idAction.replaceAll("[\n\r]", " ");
    }

    public void setIdAction(String idAction) {
        this.idAction = idAction;
    }

    public String getIdIndicateur() {
        return idIndicateur == null ? "" : idIndicateur.replaceAll("[\n\r]", " ");
    }

    public void setIdIndicateur(String idIndicateur) {
        this.idIndicateur = idIndicateur;
    }

    public String getNomAction() {
        return nomAction == null ? "" : nomAction.replaceAll("[\n\r]", " ");
    }

    public void setNomAction(String nomAction) {
        this.nomAction = nomAction;
    }

    public String getNomCourt() {
        return nomCourt;
    }

    public String getPoste() {
        return poste == null ? "" : poste.replaceAll("[\n\r]", " ");
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getUnite() {
        return unite == null ? "" : unite.replaceAll("[\n\r]", " ");
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public void setNomCourt(String nomCourt) {
        this.nomCourt = nomCourt;
    }

    public static ColumnPositionMappingStrategy<IndicateurActionExportCSVBean> getIndicateurActionMappingStrategy() {
        ColumnPositionMappingStrategy<IndicateurActionExportCSVBean> strategy = new ColumnPositionMappingStrategy<IndicateurActionExportCSVBean>() {
            @Override
            public String[] generateHeader() {
                return new String[]{"Id indicateur","Nom court","Poste","Unite","Id action","Nom action"}; // Les Headers
            }
        };
        strategy.setType(IndicateurActionExportCSVBean.class);
        String[] fields = {"idIndicateur","nomCourt","poste","unite","idAction","nomAction"};// Les attributs dans le bon ordre
        strategy.setColumnMapping(fields);
        strategy.generateHeader();
        return strategy;
    }
}
