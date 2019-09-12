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

package com.efficacity.explorateurecocites.beans.specification;

import com.efficacity.explorateurecocites.beans.model.QuestionQuestionnaireEvaluation;
import com.efficacity.explorateurecocites.beans.model.QuestionQuestionnaireEvaluation_;
import com.efficacity.explorateurecocites.beans.model.ReponsesQuestionnaireEvaluation;
import com.efficacity.explorateurecocites.beans.model.ReponsesQuestionnaireEvaluation_;
import com.efficacity.explorateurecocites.utils.enumeration.QUESTIONNAIRE_TYPE_REPONSE;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Root;
import java.util.List;

public class ReponsesQuestionnaireEvaluationSpecifications {
    public static Specification<ReponsesQuestionnaireEvaluation> hasIdObjet(Long id) {
        return (root, query, cb) -> cb.equal(root.get(ReponsesQuestionnaireEvaluation_.idObjet), id);
    }

    private static Specification<ReponsesQuestionnaireEvaluation> hasTypeObject(TYPE_OBJET typeObjet) {
        return (root, query, cb) -> cb.equal(root.get(ReponsesQuestionnaireEvaluation_.typeObjet), typeObjet.getCode());
    }

    public static Specification<ReponsesQuestionnaireEvaluation> hasQuestionType(QUESTIONNAIRE_TYPE_REPONSE typeReponse) {
        return (root, query, cb) -> {
            Root<QuestionQuestionnaireEvaluation> questionRoot = query.from(QuestionQuestionnaireEvaluation.class);
            return cb.and(
                    cb.equal(questionRoot.get(QuestionQuestionnaireEvaluation_.id), root.get(ReponsesQuestionnaireEvaluation_.idQuestion)),
                    cb.equal(questionRoot.get(QuestionQuestionnaireEvaluation_.typeReponse), typeReponse.getCode())
            );
        };
    }

    public static Specification<ReponsesQuestionnaireEvaluation> hasIdQuestionIn(List<Long> ids) {
        return (root, query, cb) -> root.get(ReponsesQuestionnaireEvaluation_.idQuestion).in(ids);
    }

    public static Specification<ReponsesQuestionnaireEvaluation> belongToEcocite() {
        return hasTypeObject(TYPE_OBJET.ECOCITE);
    }

    public static Specification<ReponsesQuestionnaireEvaluation> belongToAction() {
        return hasTypeObject(TYPE_OBJET.ACTION);
    }
}
