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

import com.efficacity.explorateurecocites.beans.biz.QuestionsEvaluationBean;
import com.efficacity.explorateurecocites.beans.model.QuestionsEvaluation;
import com.efficacity.explorateurecocites.beans.repository.QuestionsEvaluationRepository;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.InnovationForm;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import isotope.commons.services.CrudEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 * <p>
 * Date de génération : 15/02/2018
 */
@Service
public class QuestionsEvaluationService extends CrudEntityService<QuestionsEvaluationRepository, QuestionsEvaluation, Long> {

    @Autowired
    ReponsesEvaluationService reponsesEvaluationService;

    @Autowired
    MessageSourceService messageSourceService;

    public QuestionsEvaluationService(QuestionsEvaluationRepository repository) {
        super(repository);
    }

    public QuestionsEvaluationBean findById(long id) {
        return new QuestionsEvaluationBean(repository.findOne(id));
    }

    public List<QuestionsEvaluationBean> getAllQuestions() {
        return toQuestionsEvaluationBeanList(repository.findAll());
    }

    private List<QuestionsEvaluationBean> toQuestionsEvaluationBeanList(List<QuestionsEvaluation> questionsEvaluations) {
        if (questionsEvaluations != null) {
            List<QuestionsEvaluationBean> questionsEvaluationBeans = new ArrayList<>();
            for (QuestionsEvaluation questionsEvaluation : questionsEvaluations) {
                questionsEvaluationBeans.add(toQuestionsEvaluationBean(questionsEvaluation));
            }
            return questionsEvaluationBeans;
        } else {
            return null;
        }
    }

    private QuestionsEvaluationBean toQuestionsEvaluationBean(QuestionsEvaluation questionsEvaluation) {
        if (questionsEvaluation != null) {
            return new QuestionsEvaluationBean(questionsEvaluation);
        } else {
            return null;
        }
    }

    public List<QuestionsEvaluation> getList() {
        return repository.findAll();
    }

    public String delete(final Long id, final Locale locale) {
        if(reponsesEvaluationService.countByQuestion(id) > 0){
            return messageSourceService.getMessageSource().getMessage("error.objet.suppression.liee", null, locale);
        } else {
            repository.delete(id);
            return "";
        }
    }

    public void update(final Long id, final InnovationForm tableform) {
        QuestionsEvaluation questionsEvaluation = repository.findOne(id);
        questionsEvaluation.setTitre(tableform.getNom());
        questionsEvaluation.setDescription(tableform.getDescription());
        repository.save(questionsEvaluation);
    }

    public QuestionsEvaluation createOne(final InnovationForm tableform) {
        return repository.save(tableform.getQuestionEvaluation());
    }
}
