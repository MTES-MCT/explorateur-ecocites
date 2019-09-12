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

import com.efficacity.explorateurecocites.beans.biz.ReponsesEvaluationBean;
import com.opencsv.bean.ColumnPositionMappingStrategy;

/**
 * Created by ktoomey on 09/03/2018.
 */
public class NiveauExportCSVBean {
    private String idAction= "";
    private String nomAction= "";
    private String idQuestion= "";
    private String niveau= "";
    private String question= "";
    private String commentaire= "";

    public NiveauExportCSVBean( ReponsesEvaluationBean reponseEvaluation) {
        if(reponseEvaluation.getIdAction()!=null){this.idAction = reponseEvaluation.getIdAction().toString();}
        if(reponseEvaluation.getIdQuestion()!=null){this.idQuestion = reponseEvaluation.getIdQuestion().toString();}
        if(reponseEvaluation.getNiveau()!=null){this.niveau = reponseEvaluation.getNiveau().toString();}
    }

    public NiveauExportCSVBean() {
    }

    public String getIdAction() {
        return idAction == null ? "" : idAction.replaceAll("[\n\r]", " ");
    }

    public void setIdAction(String idAction) {
        this.idAction = idAction;
    }

    public String getIdQuestion() {
        return idQuestion == null ? "" : idQuestion.replaceAll("[\n\r]", " ");
    }

    public void setIdQuestion(String idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getQuestion() {
        return question == null ? "" : question.replaceAll("[\n\r]", " ");
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getNiveau() {return niveau == null ? "" : niveau.replaceAll("[\n\r]", " ");}

    public void setNiveau(String niveau) {this.niveau = niveau;}

    public String getNomAction() {
        return nomAction;
    }

    public void setNomAction(String nomAction) {
        this.nomAction = nomAction;
    }

    public String getCommentaire() {
        return commentaire == null ? "" : commentaire.replaceAll("[\n\r]", " ");
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public static ColumnPositionMappingStrategy<NiveauExportCSVBean> getNiveauMappingStrategy() {
        ColumnPositionMappingStrategy<NiveauExportCSVBean> strategy = new ColumnPositionMappingStrategy<NiveauExportCSVBean>() {
            @Override
            public String[] generateHeader() {
                return new String[]{"Id action", "Nom action", "Id nature d'innov", "Nom nature d'innov", "Niveau d'innovation", "Commentaire"};
            }
        };
        strategy.setType(NiveauExportCSVBean.class);
        String[] fields = {"idAction", "nomAction", "idQuestion", "Question", "niveau", "commentaire"};
        strategy.setColumnMapping(fields);
        strategy.generateHeader();
        return strategy;
    }
}
