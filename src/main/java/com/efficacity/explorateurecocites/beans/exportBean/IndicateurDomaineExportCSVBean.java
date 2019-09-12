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

import com.efficacity.explorateurecocites.beans.model.AssoIndicateurDomaine;
import com.opencsv.bean.ColumnPositionMappingStrategy;

/**
 * Created by ktoomey on 15/03/2018.
 */
public class IndicateurDomaineExportCSVBean {
    private String idIndicateur= "";
    private String nomCourt= "";
    private String idEtiquetteDomaine= "";
    private String idDomaine= "";
    private String etiquetteDomaine= "";
    private String domaine= "";

    public IndicateurDomaineExportCSVBean(AssoIndicateurDomaine assoIndicateurDomaine) {
        if(assoIndicateurDomaine.getIdDomaine()!=null){this.idEtiquetteDomaine = assoIndicateurDomaine.getIdDomaine().toString();}
        if(assoIndicateurDomaine.getIdIndicateur()!=null){this.idIndicateur = assoIndicateurDomaine.getIdIndicateur().toString();}
    }

    public String getIdIndicateur() {
        return idIndicateur == null ? "" : idIndicateur.replaceAll("[\n\r]", " ");
    }

    public void setIdIndicateur(String idIndicateur) {
        this.idIndicateur = idIndicateur;
    }

    public String getNomCourt() {
        return nomCourt == null ? "" : nomCourt.replaceAll("[\n\r]", " ");
    }

    public void setNomCourt(String nomCourt) {
        this.nomCourt = nomCourt;
    }

    public String getIdEtiquetteDomaine() {
        return idEtiquetteDomaine == null ? "" : idEtiquetteDomaine.replaceAll("[\n\r]", " ");
    }

    public void setIdEtiquetteDomaine(String idEtiquetteDomaine) {
        this.idEtiquetteDomaine = idEtiquetteDomaine;
    }

    public String getIdDomaine() {
        return idDomaine == null ? "" : idDomaine.replaceAll("[\n\r]", " ");
    }

    public void setIdDomaine(String idDomaine) {
        this.idDomaine = idDomaine;
    }

    public String getEtiquetteDomaine() {
        return etiquetteDomaine == null ? "" : etiquetteDomaine.replaceAll("[\n\r]", " ");
    }

    public void setEtiquetteDomaine(String etiquetteDomaine) {
        this.etiquetteDomaine = etiquetteDomaine;
    }

    public String getDomaine() {
        return domaine == null ? "" : domaine.replaceAll("[\n\r]", " ");
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public static ColumnPositionMappingStrategy<IndicateurDomaineExportCSVBean> getIndicateurDomaineMappingStrategy() {
        ColumnPositionMappingStrategy<IndicateurDomaineExportCSVBean> strategy = new ColumnPositionMappingStrategy<IndicateurDomaineExportCSVBean>() {
            @Override
            public String[] generateHeader() {
                return new String[]{"Id Indicateur", "Nom Court", "Id Axe", "Nom Axe", "Id domaine", "Nom domaine"};
            }
        };
        strategy.setType(IndicateurDomaineExportCSVBean.class);
        String[] fields = {"idIndicateur","nomCourt", "idDomaine", "domaine", "idEtiquetteDomaine", "etiquetteDomaine"};
        strategy.setColumnMapping(fields);
        strategy.generateHeader();
        return strategy;
    }
}
