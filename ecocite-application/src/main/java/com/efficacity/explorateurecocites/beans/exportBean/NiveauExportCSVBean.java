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

    public static final String END_LINE_REGEX = "[\n\r]";

    public NiveauExportCSVBean( ReponsesEvaluationBean reponseEvaluation) {
        if(reponseEvaluation.getIdAction()!=null){this.idAction = reponseEvaluation.getIdAction().toString();}
        if(reponseEvaluation.getIdQuestion()!=null){this.idQuestion = reponseEvaluation.getIdQuestion().toString();}
        if(reponseEvaluation.getNiveau()!=null){this.niveau = reponseEvaluation.getNiveau().toString();}
    }

    public NiveauExportCSVBean() {
    }

    public String getIdAction() {
        return idAction == null ? "" : idAction.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdAction(String idAction) {
        this.idAction = idAction;
    }

    public String getIdQuestion() {
        return idQuestion == null ? "" : idQuestion.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdQuestion(String idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getQuestion() {
        return question == null ? "" : question.replaceAll(END_LINE_REGEX, " ");
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getNiveau() {return niveau == null ? "" : niveau.replaceAll(END_LINE_REGEX, " ");}

    public void setNiveau(String niveau) {this.niveau = niveau;}

    public String getNomAction() {
        return nomAction;
    }

    public void setNomAction(String nomAction) {
        this.nomAction = nomAction;
    }

    public String getCommentaire() {
        return commentaire == null ? "" : commentaire.replaceAll(END_LINE_REGEX, " ");
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public static ColumnPositionMappingStrategy<NiveauExportCSVBean> getNiveauMappingStrategy() {
        ColumnPositionMappingStrategy<NiveauExportCSVBean> strategy = new ColumnPositionMappingStrategy<>() {
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
