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

package com.efficacity.explorateurecocites.beans.biz.reporting.misc;

import com.efficacity.explorateurecocites.beans.biz.QuestionQuestionnaireEvaluationBean;
import com.efficacity.explorateurecocites.utils.enumeration.QUESTIONNAIRE_TYPE_REPONSE;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ThematiqueReport {
    private String name;
    private List<QuestionMultipleReponsesReport> questions;

    public ThematiqueReport(final String name, final List<QuestionQuestionnaireEvaluationBean> questions) {
        this.name = name;
        this.questions = questions.stream()
                .filter(q -> !Objects.equals(QUESTIONNAIRE_TYPE_REPONSE.FILE, q.getTypeReponseEnum()))
                .filter(q -> q.getListeReponses() != null && !q.getListeReponses().isEmpty())
                .map(q -> new QuestionMultipleReponsesReport(q.getQuestion(), q.getListeReponses()
                        .stream()
                        .map(r -> r.getReponsePrincipale()
                                + (r.getReponseSecondaire() != null ? ("\n" + r.getReponseSecondaire()) : ""))
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    public List<QuestionMultipleReponsesReport> getQuestions() {
        return questions;
    }

    public void setQuestions(final List<QuestionMultipleReponsesReport> questions) {
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
