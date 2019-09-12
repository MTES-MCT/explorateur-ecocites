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

package com.efficacity.explorateurecocites.beans.biz.reporting.action;

import com.efficacity.explorateurecocites.beans.biz.reporting.misc.EvaluationInnovationReport;
import com.efficacity.explorateurecocites.beans.biz.reporting.misc.QuestionnaireReport;

public class EvaluationQualitative {
    private EvaluationInnovationReport innovation;
    private QuestionnaireReport questionnaireIngenierie;
    private QuestionnaireReport questionnaireInvestissement;

    public EvaluationQualitative(final EvaluationInnovationReport innovation, final QuestionnaireReport questionnaireIngenierie, final QuestionnaireReport questionnaireInvestissement) {
        this.innovation = innovation;
        this.questionnaireIngenierie = questionnaireIngenierie;
        this.questionnaireInvestissement = questionnaireInvestissement;
    }

    public EvaluationInnovationReport getInnovation() {
        return innovation;
    }

    public void setInnovation(final EvaluationInnovationReport innovation) {
        this.innovation = innovation;
    }

    public QuestionnaireReport getQuestionnaireIngenierie() {
        return questionnaireIngenierie;
    }

    public void setQuestionnaireIngenierie(final QuestionnaireReport questionnaireIngenierie) {
        this.questionnaireIngenierie = questionnaireIngenierie;
    }

    public QuestionnaireReport getQuestionnaireInvestissement() {
        return questionnaireInvestissement;
    }

    public void setQuestionnaireInvestissement(final QuestionnaireReport questionnaireInvestissement) {
        this.questionnaireInvestissement = questionnaireInvestissement;
    }
}
