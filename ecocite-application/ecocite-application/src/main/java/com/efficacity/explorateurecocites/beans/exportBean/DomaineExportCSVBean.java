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

import com.efficacity.explorateurecocites.beans.model.AssoActionDomain;
import com.opencsv.bean.ColumnPositionMappingStrategy;

/**
 * Created by ktoomey on 09/03/2018.
 */

public class DomaineExportCSVBean {
    private String idAction= "";
    private String nomAction= "";
    private String idEtiquetteDomaine= "";
    private String idDomaine= "";
    private String etiquetteDomaine= "";
    private String domaine= "";
    private String poids= "";
    private String commentaire= "";

    public static final String END_LINE_REGEX = "[\n\r]";

    public DomaineExportCSVBean(AssoActionDomain assoActionDomaine) {
        if(assoActionDomaine.getIdAction()!=null){this.idAction = assoActionDomaine.getIdAction().toString();}
        if(assoActionDomaine.getIdDomain()!=null){this.idEtiquetteDomaine = assoActionDomaine.getIdDomain().toString();}
        this.poids = ""+assoActionDomaine.getPoid();
    }

    public DomaineExportCSVBean() {
    }

    public String getIdAction() {
        return idAction == null ? "" : idAction.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdAction(String idObjet) {
        this.idAction = idObjet;
    }

    public String getNomAction() {
        return nomAction;
    }

    public void setNomAction(String nomAction) {
        this.nomAction = nomAction;
    }

    public String getIdDomaine() {
        return idDomaine == null ? "" : idDomaine.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdDomaine(String idDomaine) {
        this.idDomaine = idDomaine;
    }

    public String getIdEtiquetteDomaine() { return idEtiquetteDomaine == null ? "" : idEtiquetteDomaine.replaceAll(END_LINE_REGEX, " ");}

    public void setIdEtiquetteDomaine(String domaine) { this.idEtiquetteDomaine = domaine;}

    public String getEtiquetteDomaine() {
        return etiquetteDomaine == null ? "" : etiquetteDomaine.replaceAll(END_LINE_REGEX, " ");
    }

    public void setEtiquetteDomaine(String etiquetteDomaine) {this.etiquetteDomaine = etiquetteDomaine;}

    public String getCommentaire() {
        return commentaire == null ? "" : commentaire.replaceAll(END_LINE_REGEX, " ");
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getDomaine() {return domaine == null ? "" : domaine.replaceAll(END_LINE_REGEX, " ");}

    public void setDomaine(String domaine) {this.domaine = domaine;}

    public String getPoids() {return poids == null ? "" : poids.replaceAll(END_LINE_REGEX, " ");}

    public void setPoids(String poids) { this.poids = poids; }

    public static ColumnPositionMappingStrategy<DomaineExportCSVBean> getDomaineMappingStrategy() {
        ColumnPositionMappingStrategy<DomaineExportCSVBean> strategy = new ColumnPositionMappingStrategy<>() {
            @Override
            public String[] generateHeader() {
                return new String[]{"Id action", "Nom action", "Id axe", "Nom Axe", "Id domaine", "Nom domaine", "Niveau domaine (principal/secondaire)", "Commentaires domaine d'action"};
            }
        };
        strategy.setType(DomaineExportCSVBean.class);
        String[] fields = {"idAction","nomAction", "idDomaine", "domaine", "idEtiquetteDomaine", "etiquetteDomaine", "poids", "commentaire"};
        strategy.setColumnMapping(fields);
        strategy.generateHeader();
        return strategy;
    }
}
