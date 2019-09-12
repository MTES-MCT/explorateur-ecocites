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

    public QuestionnaireExportCSVBean(ReponsesQuestionnaireEvaluationBean reponsesQuestionnaireEvaluation) {
        if(reponsesQuestionnaireEvaluation.getIdObjet()!=null){this.idObjet = reponsesQuestionnaireEvaluation.getIdObjet().toString();}
        if(reponsesQuestionnaireEvaluation.getId()!=null){this.idReponse = reponsesQuestionnaireEvaluation.getId().toString();}
        if(reponsesQuestionnaireEvaluation.getReponsePrincipale()!=null) {
            this.reponsePrincipale = reponsesQuestionnaireEvaluation.getReponsePrincipale().replaceAll("[\n\r]"," ");
        }
        if(reponsesQuestionnaireEvaluation.getReponseSecondaire()!=null) {
            this.reponseSecondaire = reponsesQuestionnaireEvaluation.getReponseSecondaire().replaceAll("[\n\r]"," ");
        }
        if(reponsesQuestionnaireEvaluation.getIdQuestion()!=null){this.idQuestion = reponsesQuestionnaireEvaluation.getIdQuestion().toString();}
    }
    public QuestionnaireExportCSVBean() {
    }

    public String getIdObjet() {
        return idObjet == null ? "" : idObjet.replaceAll("[\n\r]", " ");
    }

    public void setIdObjet(String idObjet) {
        this.idObjet = idObjet;
    }

    public String getIdReponse() {
        return idReponse == null ? "" : idReponse.replaceAll("[\n\r]", " ");
    }

    public void setIdReponse(String idReponse) {
        this.idReponse = idReponse;
    }

    public String getReponsePrincipale() {
        return reponsePrincipale == null ? "" : reponsePrincipale.replaceAll("[\n\r]", " ");
    }

    public void setReponsePrincipale(String reponsePrincipale) {
        this.reponsePrincipale = reponsePrincipale;
    }

    public String getReponseSecondaire() {
        return reponseSecondaire == null ? "" : reponseSecondaire.replaceAll("[\n\r]", " ");
    }

    public void setReponseSecondaire(String reponseSecondaire) {
        this.reponseSecondaire = reponseSecondaire;
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

    public String getCodeQuestionnaire() {
        return codeQuestionnaire == null ? "" : codeQuestionnaire.replaceAll("[\n\r]", " ");
    }

    public void setCodeQuestionnaire(String codeQuestionnaire) {
        this.codeQuestionnaire = codeQuestionnaire;
    }

    public String getNomObjet() {
        return nomObjet == null ? "" : nomObjet.replaceAll("[\n\r]", " ");
    }

    public void setNomObjet(String nomObjet) {
        this.nomObjet = nomObjet;
    }

    public static ColumnPositionMappingStrategy<QuestionnaireExportCSVBean> getQuestionnaireMappingStrategyForAction() {
        ColumnPositionMappingStrategy<QuestionnaireExportCSVBean> strategy = new ColumnPositionMappingStrategy<QuestionnaireExportCSVBean>() {
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
        ColumnPositionMappingStrategy<QuestionnaireExportCSVBean> strategy = new ColumnPositionMappingStrategy<QuestionnaireExportCSVBean>() {
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
