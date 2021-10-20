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
import com.efficacity.explorateurecocites.utils.enumeration.QUESTIONNAIRE_CATEGORIE_CODE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuestionnaireReport {
    private String name;
    private List<ThematiqueReport> thematiques;

    public QuestionnaireReport(final String name, final Map<String, ? extends List<QuestionQuestionnaireEvaluationBean>> thematiques) {
        this.name = name;
        this.thematiques = new ArrayList<>();
        thematiques.forEach((code, questionQuestionnaireEvaluationBeans) -> {
            QUESTIONNAIRE_CATEGORIE_CODE categorieCode = QUESTIONNAIRE_CATEGORIE_CODE.getByCode(code);
            if (categorieCode != null) {
                ThematiqueReport report = new ThematiqueReport(categorieCode.getLibelle(), questionQuestionnaireEvaluationBeans);
                if (!report.getQuestions().isEmpty()) {
                    this.thematiques.add(report);
                }
            }
        });
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<ThematiqueReport> getThematiques() {
        return thematiques;
    }

    public void setThematiques(final List<ThematiqueReport> thematiques) {
        this.thematiques = thematiques;
    }
}
