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

    public DomaineExportCSVBean(AssoActionDomain assoActionDomaine) {
        if(assoActionDomaine.getIdAction()!=null){this.idAction = assoActionDomaine.getIdAction().toString();}
        if(assoActionDomaine.getIdDomain()!=null){this.idEtiquetteDomaine = assoActionDomaine.getIdDomain().toString();}
        this.poids = ""+assoActionDomaine.getPoid();
    }

    public DomaineExportCSVBean() {
    }

    public String getIdAction() {
        return idAction == null ? "" : idAction.replaceAll("[\n\r]", " ");
    }

    public void setIdAction(String idObjet) {
        this.idAction = idAction;
    }

    public String getNomAction() {
        return nomAction;
    }

    public void setNomAction(String nomAction) {
        this.nomAction = nomAction;
    }

    public String getIdDomaine() {
        return idDomaine == null ? "" : idDomaine.replaceAll("[\n\r]", " ");
    }

    public void setIdDomaine(String idDomaine) {
        this.idDomaine = idDomaine;
    }

    public String getIdEtiquetteDomaine() { return idEtiquetteDomaine == null ? "" : idEtiquetteDomaine.replaceAll("[\n\r]", " ");}

    public void setIdEtiquetteDomaine(String domaine) { this.idEtiquetteDomaine = domaine;}

    public String getEtiquetteDomaine() {
        return etiquetteDomaine == null ? "" : etiquetteDomaine.replaceAll("[\n\r]", " ");
    }

    public void setEtiquetteDomaine(String etiquetteDomaine) {this.etiquetteDomaine = etiquetteDomaine;}

    public String getCommentaire() {
        return commentaire == null ? "" : commentaire.replaceAll("[\n\r]", " ");
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getDomaine() {return domaine == null ? "" : domaine.replaceAll("[\n\r]", " ");}

    public void setDomaine(String domaine) {this.domaine = domaine;}

    public String getPoids() {return poids == null ? "" : poids.replaceAll("[\n\r]", " ");}

    public void setPoids(String poids) { this.poids = poids; }

    public static ColumnPositionMappingStrategy<DomaineExportCSVBean> getDomaineMappingStrategy() {
        ColumnPositionMappingStrategy<DomaineExportCSVBean> strategy = new ColumnPositionMappingStrategy<DomaineExportCSVBean>() {
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
