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

import com.efficacity.explorateurecocites.beans.model.Business;
import com.opencsv.bean.ColumnPositionMappingStrategy;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Created by ktoomey on 21/03/2018.
 */
public class BusinessOnlyExportCSVBean {
    private String idAction= "";
    private String idBusiness= "";
    private String nomAffaire= "";
    private String numeroAction= "";
    private String numeroAffaire= "";
    private String numeroOperation= "";
    private String nomOperation= "";
    private String typeFinancement= "";
    private String tranche= "";
    private String statutFinancier= "";
    private String dateDebut= "";
    private String dateFin= "";
    private String ecocite= "";
    private String idEcocite= "";

    public static final String END_LINE_REGEX = "[\n\r]";

    public BusinessOnlyExportCSVBean(Business business) {
        if(business.getId()!=null){this.idBusiness=business.getId().toString();}
        if(business.getIdAction()!=null){this.idAction= business.getIdAction().toString();}
        this.numeroAffaire = business.getNumeroAffaire();
        this.numeroOperation = business.getNumeroOperation();
        this.nomOperation = business.getNomOperation();
        this.typeFinancement = business.getTypeFinancement();
        this.tranche = business.getTranche();
        this.statutFinancier = business.getStatutFinancier();
        if(business.getDateDebut()!=null) {
            this.dateDebut = business.getDateDebut().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT));
        }
        if(business.getDateFin()!=null) {
            this.dateFin = business.getDateFin().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT));
        }
        this.ecocite = business.getEcocite();
        this.nomAffaire = business.getNom();
    }

    public String getIdBusiness() {
        return idBusiness == null ? "" : idBusiness.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdBusiness(String idBusiness) {
        this.idBusiness = idBusiness;
    }

    public String getNumeroAction() {
        return numeroAction;
    }

    public void setNumeroAction(String numeroAction) {
        this.numeroAction = numeroAction;
    }

    public String getNumeroAffaire() {
        return numeroAffaire == null ? "" : numeroAffaire.replaceAll(END_LINE_REGEX, " ");
    }

    public void setNumeroAffaire(String numeroAffaire) {
        this.numeroAffaire = numeroAffaire;
    }

    public String getNumeroOperation() {
        return numeroOperation == null ? "" : numeroOperation.replaceAll(END_LINE_REGEX, " ");
    }

    public void setNumeroOperation(String numeroOperation) {
        this.numeroOperation = numeroOperation;
    }

    public String getNomOperation() {
        return nomOperation == null ? "" : nomOperation.replaceAll(END_LINE_REGEX, " ");
    }

    public void setNomOperation(String nomOperation) {
        this.nomOperation = nomOperation;
    }

    public String getTypeFinancement() {
        return typeFinancement == null ? "" : typeFinancement.replaceAll(END_LINE_REGEX, " ");
    }

    public void setTypeFinancement(String typeFinancement) {
        this.typeFinancement = typeFinancement;
    }

    public String getTranche() {
        return tranche == null ? "" : tranche.replaceAll(END_LINE_REGEX, " ");
    }

    public void setTranche(String tranche) {
        this.tranche = tranche;
    }

    public String getStatutFinancier() {
        return statutFinancier == null ? "" : statutFinancier.replaceAll(END_LINE_REGEX, " ");
    }

    public void setStatutFinancier(String statutFinancier) {
        this.statutFinancier = statutFinancier;
    }

    public String getDateDebut() {
        return dateDebut == null ? "" : dateDebut.replaceAll(END_LINE_REGEX, " ");
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin == null ? "" : dateFin.replaceAll(END_LINE_REGEX, " ");
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getEcocite() {
        return ecocite == null ? "" : ecocite.replaceAll(END_LINE_REGEX, " ");
    }

    public String getIdAction() {
        return idAction == null ? "" : idAction.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdAction(String idAction) {
        this.idAction = idAction;
    }

    public void setEcocite(String ecocite) {
        this.ecocite = ecocite;
    }

    public String getNomAffaire() {
        return nomAffaire == null ? "" : nomAffaire.replaceAll(END_LINE_REGEX, " ");
    }

    public void setNomAffaire(String nomAffaire) {
        this.nomAffaire = nomAffaire;
    }

    public String getIdEcocite() {
        return idEcocite == null ? "" : idEcocite.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdEcocite(String idEcocite) {
        this.idEcocite = idEcocite;
    }

    public static ColumnPositionMappingStrategy<BusinessOnlyExportCSVBean> getBusinessOnlyMappingStrategy() {
        ColumnPositionMappingStrategy<BusinessOnlyExportCSVBean> strategy = new ColumnPositionMappingStrategy<BusinessOnlyExportCSVBean>() {
            @Override
            public String[] generateHeader() {
                return new String[]{"Id affaire","Numéro d'affaire","Nom de l'affaire","Id ÉcoCité","ÉcoCité","Type de financement",
                        "Tranche","Statut financier","Date de début","Date de fin","Nom d'opération","Numéro d'opération",
                        "Id action","Numero d'action"}; // Les Headers
            }
        };
        strategy.setType(BusinessOnlyExportCSVBean.class);
        String[] fields = {"idBusiness","numeroAffaire","nomAffaire","idEcocite","ecocite","typeFinancement",
                "tranche","statutFinancier","dateDebut","dateFin","nomOperation","numeroOperation","idAction","numeroAction"}; // Les attributs dans le bon ordre
        strategy.setColumnMapping(fields);
        strategy.generateHeader();
        return strategy;
    }
}
