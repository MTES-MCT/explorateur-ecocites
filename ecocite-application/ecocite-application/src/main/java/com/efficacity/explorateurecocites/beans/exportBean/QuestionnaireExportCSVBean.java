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

import com.efficacity.explorateurecocites.beans.biz.ReponsesQuestionnaireEvaluationBean;
import com.opencsv.bean.ColumnPositionMappingStrategy;

/**
 * Created by ktoomey on 09/03/2018.
 */
public class QuestionnaireExportCSVBean {
    private String idObjet= "";
    private String nomObjet= "";
    private String idReponse= "";
    private String reponsePrincipale= "";
    private String reponseSecondaire= "";
    private String idQuestion= "";
    private String question= "";
    private String codeQuestionnaire= "";

    public static final String END_LINE_REGEX = "[\n\r]";

    public QuestionnaireExportCSVBean(ReponsesQuestionnaireEvaluationBean reponsesQuestionnaireEvaluation) {
        if(reponsesQuestionnaireEvaluation.getIdObjet()!=null){this.idObjet = reponsesQuestionnaireEvaluation.getIdObjet().toString();}
        if(reponsesQuestionnaireEvaluation.getId()!=null){this.idReponse = reponsesQuestionnaireEvaluation.getId().toString();}
        if(reponsesQuestionnaireEvaluation.getReponsePrincipale()!=null) {
            this.reponsePrincipale = reponsesQuestionnaireEvaluation.getReponsePrincipale().replaceAll(END_LINE_REGEX," ");
        }
        if(reponsesQuestionnaireEvaluation.getReponseSecondaire()!=null) {
            this.reponseSecondaire = reponsesQuestionnaireEvaluation.getReponseSecondaire().replaceAll(END_LINE_REGEX," ");
        }
        if(reponsesQuestionnaireEvaluation.getIdQuestion()!=null){this.idQuestion = reponsesQuestionnaireEvaluation.getIdQuestion().toString();}
    }
    public QuestionnaireExportCSVBean() {
    }

    public String getIdObjet() {
        return idObjet == null ? "" : idObjet.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdObjet(String idObjet) {
        this.idObjet = idObjet;
    }

    public String getIdReponse() {
        return idReponse == null ? "" : idReponse.replaceAll(END_LINE_REGEX, " ");
    }

    public void setIdReponse(String idReponse) {
        this.idReponse = idReponse;
    }

    public String getReponsePrincipale() {
        return reponsePrincipale == null ? "" : reponsePrincipale.replaceAll(END_LINE_REGEX, " ");
    }

    public void setReponsePrincipale(String reponsePrincipale) {
        this.reponsePrincipale = reponsePrincipale;
    }

    public String getReponseSecondaire() {
        return reponseSecondaire == null ? "" : reponseSecondaire.replaceAll(END_LINE_REGEX, " ");
    }

    public void setReponseSecondaire(String reponseSecondaire) {
        this.reponseSecondaire = reponseSecondaire;
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

    public String getCodeQuestionnaire() {
        return codeQuestionnaire == null ? "" : codeQuestionnaire.replaceAll(END_LINE_REGEX, " ");
    }

    public void setCodeQuestionnaire(String codeQuestionnaire) {
        this.codeQuestionnaire = codeQuestionnaire;
    }

    public String getNomObjet() {
        return nomObjet == null ? "" : nomObjet.replaceAll(END_LINE_REGEX, " ");
    }

    public void setNomObjet(String nomObjet) {
        this.nomObjet = nomObjet;
    }

    public static ColumnPositionMappingStrategy<QuestionnaireExportCSVBean> getQuestionnaireMappingStrategyForAction() {
        ColumnPositionMappingStrategy<QuestionnaireExportCSVBean> strategy = new ColumnPositionMappingStrategy<>() {
            @Override
            public String[] generateHeader() {
                return new String[]{"Id action", "Nom action", "Code du questionnaire", "Id question", "Nom question", "Id réposne",
                "Réponse princiaple", "Réponse secondaire"};
            }
        };
        strategy.setType(QuestionnaireExportCSVBean.class);
        String[] fields = {"idObjet","nomObjet", "codeQuestionnaire", "idQuestion", "question",
                "idReponse", "reponsePrincipale","reponseSecondaire"};
        strategy.setColumnMapping(fields);
        strategy.generateHeader();
        return strategy;
    }
    public static ColumnPositionMappingStrategy<QuestionnaireExportCSVBean> getQuestionnaireMappingStrategyForEcocite() {
        ColumnPositionMappingStrategy<QuestionnaireExportCSVBean> strategy = new ColumnPositionMappingStrategy<>() {
            @Override
            public String[] generateHeader() {
                return new String[]{"Id ÉcoCité", "Nom ÉcoCité", "Code du questionnaire", "Id question", "Nom question", "Id réposne",
                        "Réponse princiaple", "Réponse secondaire"};
            }
        };
        strategy.setType(QuestionnaireExportCSVBean.class);
        String[] fields = {"idObjet","nomObjet", "codeQuestionnaire", "idQuestion", "question",
                "idReponse", "reponsePrincipale","reponseSecondaire"};
        strategy.setColumnMapping(fields);
        strategy.generateHeader();
        return strategy;
    }
}
