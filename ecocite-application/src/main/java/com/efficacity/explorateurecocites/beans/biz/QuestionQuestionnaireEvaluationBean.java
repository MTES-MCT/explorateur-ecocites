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

package com.efficacity.explorateurecocites.beans.biz;

import com.efficacity.explorateurecocites.beans.model.QuestionQuestionnaireEvaluation;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import com.efficacity.explorateurecocites.utils.enumeration.QUESTIONNAIRE_CATEGORIE_CODE;
import com.efficacity.explorateurecocites.utils.enumeration.QUESTIONNAIRE_CODE;
import com.efficacity.explorateurecocites.utils.enumeration.QUESTIONNAIRE_TYPE_REPONSE;

import java.util.List;

/**
 * Created by tfossurier on 23/02/2018.
 */
public class QuestionQuestionnaireEvaluationBean {

    private QuestionQuestionnaireEvaluation to;
    private List<ReponsesQuestionnaireEvaluationBean> listeReponses;
    private boolean mereHasReponseVoulu;

    public QuestionQuestionnaireEvaluationBean (QuestionQuestionnaireEvaluation questionQuestionnaireEvaluation) {
        super();
        this.to=questionQuestionnaireEvaluation;
    }

    public QUESTIONNAIRE_CODE getCodeQuestionnaireEnum() {
        return QUESTIONNAIRE_CODE.getByCode(this.to.getCodeQuestionnaire());
    }

    public QUESTIONNAIRE_CATEGORIE_CODE getCodeCategorieEnum() {
        return QUESTIONNAIRE_CATEGORIE_CODE.getByCode(this.to.getCodeCategorie());
    }

    public QUESTIONNAIRE_TYPE_REPONSE getTypeReponseEnum() {
        return QUESTIONNAIRE_TYPE_REPONSE.getByCode(this.to.getTypeReponse());
    }

    public QuestionQuestionnaireEvaluation getTo() {
        return to;
    }

    public Long getId() {
        return this.to.getId();
    }

    public void setId(Long id) {
        this.to.setId(id);
    }

    public String getCodeQuestionnaire() {
        return this.to.getCodeQuestionnaire();
    }

    public void setCodeQuestionnaire(String codeQuestionnaire) {
        this.to.setCodeQuestionnaire(codeQuestionnaire);
    }

    public String getCodeCategorie() {
        return this.to.getCodeCategorie();
    }

    public void setCodeCategorie(String codeCategorie) {
        this.to.setCodeCategorie(codeCategorie);
    }

    public Integer getOrdre() {
        return this.to.getOrdre();
    }

    public void setOrdre(Integer ordre) {
        this.to.setOrdre(ordre);
    }

    public String getQuestion() {
        return this.to.getQuestion();
    }

    public void setQuestion(String question) {
        this.to.setQuestion(question);
    }

    public String getTypeReponse() {
        return this.to.getTypeReponse();
    }

    public void setTypeReponse(String typeReponse) {
        this.to.setTypeReponse(typeReponse);
    }

    public String getReponses() {
        return this.to.getReponses();
    }

    public void setReponses(String reponses) {
        this.to.setReponses(reponses);
    }

    public Long getIdQuestionMere() {
        return this.to.getIdQuestionMere();
    }

    public void setIdQuestionMere(Long idQuestionMere) {
        this.to.setIdQuestionMere(idQuestionMere);
    }

    public String getReponseAttendu() {
        return this.to.getReponseAttendu();
    }

    public void setReponseAttendu(String reponseAttendu) {
        this.to.setReponseAttendu(reponseAttendu);
    }

    public List<ReponsesQuestionnaireEvaluationBean> getListeReponses() {
        return listeReponses;
    }

    public void setListeReponses(List<ReponsesQuestionnaireEvaluationBean> listeReponses) {
        this.listeReponses = listeReponses;
    }

    public ReponsesQuestionnaireEvaluationBean getReponseByValue(String value){
        // Parcour la liste est check les réponses principale
        if (listeReponses != null) {
            for (ReponsesQuestionnaireEvaluationBean reponse : listeReponses) {
                if (CustomValidator.isNotEmpty(reponse.getReponsePrincipale()) && reponse.getReponsePrincipale().equals(value)) {
                    return reponse;
                }
            }
        }
        return null;
    }
    public boolean getIsReponsePrincipale(String value){
        // Parcour la liste est check les réponses principale
        if(listeReponses != null && listeReponses.size() ==1){
            if(value.equals(listeReponses.get(0).getReponsePrincipale())) {
                return true;
            }
        }
        return false;
    }
    public ReponsesQuestionnaireEvaluationBean getReponseUnique(){
        // Parcour la liste est check les réponses principale
        if(listeReponses != null && listeReponses.size() ==1){
            return listeReponses.get(0);
        }
        return null;
    }
    public boolean getHasReponse(){
        // Parcour la liste est check les réponses principale
        if(null != listeReponses && !listeReponses.isEmpty()){
            return true;
        }
        return false;
    }

    public boolean isMereHasReponseVoulu() {
        return mereHasReponseVoulu;
    }

    public void setMereHasReponseVoulu(boolean mereHasReponseVoulu) {
        this.mereHasReponseVoulu = mereHasReponseVoulu;
    }
}
