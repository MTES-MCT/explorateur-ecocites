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

package com.efficacity.explorateurecocites.beans.service;

import com.efficacity.explorateurecocites.beans.biz.QuestionQuestionnaireEvaluationBean;
import com.efficacity.explorateurecocites.beans.model.QuestionQuestionnaireEvaluation;
import com.efficacity.explorateurecocites.beans.repository.QuestionQuestionnaireEvaluationRepository;
import com.efficacity.explorateurecocites.utils.enumeration.QUESTIONNAIRE_CODE;
import isotope.commons.services.CrudEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.beans.specification.QuestionQuestionnaireEvaluationSpecifications.hasCodeQuestionnaire;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 * <p>
 * Date de génération : 23/02/2018
 */
@Service
public class QuestionQuestionnaireEvaluationService extends CrudEntityService<QuestionQuestionnaireEvaluationRepository, QuestionQuestionnaireEvaluation, Long> {

    @Autowired
    QuestionQuestionnaireEvaluationRepository questionQuestionnaireEvaluationRepository;

    public QuestionQuestionnaireEvaluationService(QuestionQuestionnaireEvaluationRepository repository) {
        super(repository);
    }

    public QuestionQuestionnaireEvaluationBean findById(long id) {
        return new QuestionQuestionnaireEvaluationBean(questionQuestionnaireEvaluationRepository.findOne(id));
    }

    public List<QuestionQuestionnaireEvaluationBean> findByIdQuestionMere(long idQuestionMere) {
        return toQuestionQuestionnaireEvaluationBeanList(questionQuestionnaireEvaluationRepository.findAllByIdQuestionMere(idQuestionMere));
    }

    public LinkedList<QuestionQuestionnaireEvaluationBean> getListeCodeQuestionnaireAndCodeCategorie(String codeQuestionnaire, String codeCatégorie) {
        LinkedList<QuestionQuestionnaireEvaluationBean> retour = new LinkedList<>();
        List<QuestionQuestionnaireEvaluation> liste = questionQuestionnaireEvaluationRepository.findAllByCodeQuestionnaireAndCodeCategorieOrderByCodeCategorieAscOrdreAsc(codeQuestionnaire, codeCatégorie);
        if (liste != null && !liste.isEmpty()) {
            for (QuestionQuestionnaireEvaluation item : liste) {
                retour.add(new QuestionQuestionnaireEvaluationBean(item));
            }
        }
        return retour;
    }

    public List<QuestionQuestionnaireEvaluationBean> getListeCodeQuestionnaire(QUESTIONNAIRE_CODE code) {
        return questionQuestionnaireEvaluationRepository.findAll(where(hasCodeQuestionnaire(code)))
                .stream()
                .map(QuestionQuestionnaireEvaluationBean::new)
                .collect(Collectors.toList());
    }

    private List<QuestionQuestionnaireEvaluationBean> toQuestionQuestionnaireEvaluationBeanList(List<QuestionQuestionnaireEvaluation> questionQuestionnaireEvaluations) {
        if (questionQuestionnaireEvaluations != null) {
            List<QuestionQuestionnaireEvaluationBean> questionQuestionnaireEvaluationBeans = new ArrayList<>();
            for (QuestionQuestionnaireEvaluation questionQuestionnaireEvaluation : questionQuestionnaireEvaluations) {
                questionQuestionnaireEvaluationBeans.add(toQuestionQuestionnaireEvaluationBean(questionQuestionnaireEvaluation));
            }
            return questionQuestionnaireEvaluationBeans;
        } else {
            return null;
        }
    }

    private QuestionQuestionnaireEvaluationBean toQuestionQuestionnaireEvaluationBean(QuestionQuestionnaireEvaluation questionQuestionnaireEvaluation) {
        if (questionQuestionnaireEvaluation != null) {
            return new QuestionQuestionnaireEvaluationBean(questionQuestionnaireEvaluation);
        } else {
            return null;
        }
    }

    public List<QuestionQuestionnaireEvaluation> findByCodeQuestionnaire(final QUESTIONNAIRE_CODE code) {
        return repository.findAll(where(hasCodeQuestionnaire(code)));
    }
}
