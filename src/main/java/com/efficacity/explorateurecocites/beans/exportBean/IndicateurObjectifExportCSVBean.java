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

import com.efficacity.explorateurecocites.beans.model.AssoIndicateurObjectif;
import com.opencsv.bean.ColumnPositionMappingStrategy;

/**
 * Created by ktoomey on 15/03/2018.
 */
public class IndicateurObjectifExportCSVBean {
    private String idIndicateur= "";
    private String nomCourt= "";
    private String idObjectif= "";
    private String idEtiquetteObjectif= "";
    private String objectif= "";
    private String etiquetteObjectif= "";

    public IndicateurObjectifExportCSVBean(AssoIndicateurObjectif assoIndicateurObjectif) {
        if(assoIndicateurObjectif.getIdIndicateur()!=null){this.idIndicateur = assoIndicateurObjectif.getIdIndicateur().toString();}
        if(assoIndicateurObjectif.getIdObjectif()!=null){this.idEtiquetteObjectif = assoIndicateurObjectif.getIdObjectif().toString();}
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

    public String getIdObjectif() {
        return idObjectif;
    }

    public void setIdObjectif(String idObjectif) {
        this.idObjectif = idObjectif;
    }

    public String getIdEtiquetteObjectif() {
        return idEtiquetteObjectif == null ? "" : idEtiquetteObjectif.replaceAll("[\n\r]", " ");
    }

    public void setIdEtiquetteObjectif(String idEtiquetteObjectif) {
        this.idEtiquetteObjectif = idEtiquetteObjectif;
    }

    public String getObjectif() {
        return objectif == null ? "" : objectif.replaceAll("[\n\r]", " ");
    }

    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }

    public String getEtiquetteObjectif() {
        return etiquetteObjectif == null ? "" : etiquetteObjectif.replaceAll("[\n\r]", " ");
    }

    public void setEtiquetteObjectif(String etiquetteObjectif) {
        this.etiquetteObjectif = etiquetteObjectif;
    }

    public static ColumnPositionMappingStrategy<IndicateurObjectifExportCSVBean> getIndicateurObjectifMappingStrategy() {
        ColumnPositionMappingStrategy<IndicateurObjectifExportCSVBean> strategy = new ColumnPositionMappingStrategy<IndicateurObjectifExportCSVBean>() {
            @Override
            public String[] generateHeader() {
                return new String[]{"Id indicateur","Nom court", "Id finalité", "Nom finalité", "Id objectif", "Objectif"};
            }
        };
        strategy.setType(IndicateurObjectifExportCSVBean.class);
        String[] fields = {"idIndicateur","nomCourt","idObjectif","objectif", "idEtiquetteObjectif","etiquetteObjectif"};
        strategy.setColumnMapping(fields);
        strategy.generateHeader();
        return strategy;
    }
}
