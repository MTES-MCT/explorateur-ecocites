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

package com.efficacity.explorateurecocites.beans.service;

import com.efficacity.explorateurecocites.beans.biz.QuestionsEvaluationBean;
import com.efficacity.explorateurecocites.beans.model.QuestionsEvaluation;
import com.efficacity.explorateurecocites.beans.repository.QuestionsEvaluationRepository;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.InnovationForm;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import isotope.commons.services.CrudEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
        return new QuestionsEvaluationBean(repository.findById(id).orElseThrow(() -> new EntityNotFoundException()));
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
            repository.deleteById(id);
            return "";
        }
    }

    public void update(final Long id, final InnovationForm tableform) {
        QuestionsEvaluation questionsEvaluation = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        questionsEvaluation.setTitre(tableform.getNom());
        questionsEvaluation.setDescription(tableform.getDescription());
        repository.save(questionsEvaluation);
    }

    public QuestionsEvaluation createOne(final InnovationForm tableform) {
        return repository.save(tableform.getQuestionEvaluation());
    }
}
