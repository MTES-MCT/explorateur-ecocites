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
