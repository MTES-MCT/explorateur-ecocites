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

import com.efficacity.explorateurecocites.beans.biz.ReponsesEvaluationBean;
import com.efficacity.explorateurecocites.beans.model.ReponsesEvaluation;
import com.efficacity.explorateurecocites.beans.repository.ReponsesEvaluationRepository;
import isotope.commons.services.CrudEntityService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.beans.specification.ReponsesEvaluationSpecifications.hasIdQuestion;
import static org.springframework.data.jpa.domain.Specification.where;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 * <p>
 * Date de génération : 15/02/2018
 */
@Service
public class ReponsesEvaluationService extends CrudEntityService<ReponsesEvaluationRepository, ReponsesEvaluation, Long> {
    public ReponsesEvaluationService(ReponsesEvaluationRepository repository) {
        super(repository);
    }

    public List<ReponsesEvaluationBean> getReponseByIdAction(final Long actionId) {
        return toReponsesEvalationBeanList(repository.findByIdAction(actionId));
    }
    public List<ReponsesEvaluationBean> findByIdActionOrderByIdQuestionAsc(final Long actionId) {
        return toReponsesEvalationBeanList(repository.findByIdActionOrderByIdQuestionAsc(actionId));
    }

    public void save(final ReponsesEvaluationBean reponsesEvaluationBean) {
        repository.save(reponsesEvaluationBean.getTo());
    }

    public ReponsesEvaluationBean findOrCreateByIdQuestionAndIdAction(final Long questionId, final Long actionId) {
        return toReponsesEvalationBean(
                repository.findByIdQuestionAndIdAction(questionId, actionId)
                        .orElseGet(() -> {
                            ReponsesEvaluation reponse = new ReponsesEvaluation();
                            reponse.setIdAction(actionId);
                            reponse.setIdQuestion(questionId);
                            return reponse;
                        }));
    }


    private List<ReponsesEvaluationBean> toReponsesEvalationBeanList(List<ReponsesEvaluation> reponsesEvaluations) {
        if (reponsesEvaluations != null) {
            List<ReponsesEvaluationBean> reponsesEvaluationBeans = new ArrayList<>();
            for (ReponsesEvaluation reponseEvaluation : reponsesEvaluations) {
                reponsesEvaluationBeans.add(toReponsesEvalationBean(reponseEvaluation));
            }
            return reponsesEvaluationBeans;
        } else {
            return null;
        }
    }

    private ReponsesEvaluationBean toReponsesEvalationBean(ReponsesEvaluation reponsesEvaluation) {
        if (reponsesEvaluation != null) {
            return new ReponsesEvaluationBean(reponsesEvaluation);
        } else {
            return null;
        }
    }

    public Long countByQuestion(final Long id) {
        return repository.count(where(hasIdQuestion(id)));
    }

    public void deleteByAction(final Long id) {
        repository.deleteAll(repository.findByIdAction(id));
    }

    public List<ReponsesEvaluation> cloneForAction(final Long idClone, final Long idOriginal) {
        List<ReponsesEvaluation> clones = repository.findByIdAction(idOriginal)
                .stream()
                .map(original -> {
                    ReponsesEvaluation clone = new ReponsesEvaluation();
                    clone.setIdAction(idClone);
                    clone.setIdQuestion(original.getIdQuestion());
                    clone.setNiveau(original.getNiveau());
                    return clone;
                })
                .collect(Collectors.toList());
        return repository.saveAll(clones);
    }
}
