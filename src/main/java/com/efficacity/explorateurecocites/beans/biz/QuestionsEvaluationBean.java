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

package com.efficacity.explorateurecocites.beans.biz;

import com.efficacity.explorateurecocites.beans.model.QuestionsEvaluation;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe qui étent le bean model du générator
 *
 * Date de génération : 06/02/2018
 */

public class QuestionsEvaluationBean {

    private QuestionsEvaluation to;

    public QuestionsEvaluationBean(QuestionsEvaluation questionsEvaluation) {
        super();
        this.to = questionsEvaluation;
    }

    public Long getId() {
        return this.to.getId();
    }

    public void setId(Long id) {
        this.to.setId(id);
    }

    public String getDescription() {
        return this.to.getDescription();
    }

    public void setDescription(String description) {
        this.to.setDescription(description);
    }

    public String getTitre() {
        return this.to.getTitre();
    }

    public void setTitre(String titre) {
        this.to.setTitre(titre);
    }

    public QuestionsEvaluation getTo() {
        return to;
    }

    public static List<QuestionsAvecReponseBean> createListFromData(final List<ReponsesEvaluationBean> reponses, final List<QuestionsEvaluationBean> questions) {
        return questions.stream()
                .map(question -> new QuestionsAvecReponseBean(question, reponses.stream()
                        .filter(reponse -> reponse.getIdQuestion().equals(question.getId()))
                        .findFirst()
                        .orElse(null)))
                .collect(Collectors.toList());
    }
}
