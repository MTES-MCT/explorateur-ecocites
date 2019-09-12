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

import com.efficacity.explorateurecocites.beans.biz.ActionBean;
import com.efficacity.explorateurecocites.utils.enumeration.EVALUATION_NIVEAU_INNOVATION_GLOBAL;
import com.efficacity.explorateurecocites.utils.enumeration.EVALUATION_NIVEAU_INNOVATION_NATURE;

import java.util.List;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.ui.bo.utils.ReportingHelper.EMPTY;

public class EvaluationInnovationReport {
    private List<QuestionReponseReport> questions;
    private String niveauGlobal;
    private String comment;

    public EvaluationInnovationReport(final ActionBean action, final String commentaireEtape) {
        questions = action.getQuestionsAvecReponseBeanList()
                .stream()
                .filter(q -> q.getReponse() != null
                        && q.getReponse().getNiveauEnum() != null
                        && !q.getReponse().getNiveauEnum().equals(EVALUATION_NIVEAU_INNOVATION_NATURE.NON_CONCERNE))
                .map(q -> new QuestionReponseReport(q.getQuestion().getTitre(), q.getReponse().getNiveauEnum().getTitle()))
                .collect(Collectors.toList());
        if (action.getEvaluationNiveauGlobal() != null) {
            EVALUATION_NIVEAU_INNOVATION_GLOBAL niveau = EVALUATION_NIVEAU_INNOVATION_GLOBAL.getByValue(action.getEvaluationNiveauGlobal());
            if (niveau != null) {
                niveauGlobal = niveau.getContent();
            } else {
                niveauGlobal = EMPTY;
            }
        } else {
            niveauGlobal = EMPTY;
        }
        comment = commentaireEtape;
    }

    public List<QuestionReponseReport> getQuestions() {
        return questions;
    }

    public void setQuestions(final List<QuestionReponseReport> questions) {
        this.questions = questions;
    }

    public String getNiveauGlobal() {
        return niveauGlobal;
    }

    public void setNiveauGlobal(final String niveauGlobal) {
        this.niveauGlobal = niveauGlobal;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }
}
