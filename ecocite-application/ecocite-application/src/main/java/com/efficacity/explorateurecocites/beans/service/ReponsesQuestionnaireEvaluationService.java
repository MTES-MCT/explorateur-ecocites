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

import com.efficacity.explorateurecocites.beans.biz.ReponsesQuestionnaireEvaluationBean;
import com.efficacity.explorateurecocites.beans.model.FileUpload;
import com.efficacity.explorateurecocites.beans.model.QuestionQuestionnaireEvaluation;
import com.efficacity.explorateurecocites.beans.model.ReponsesQuestionnaireEvaluation;
import com.efficacity.explorateurecocites.beans.repository.ReponsesQuestionnaireEvaluationRepository;
import com.efficacity.explorateurecocites.utils.enumeration.QUESTIONNAIRE_CODE;
import com.efficacity.explorateurecocites.utils.enumeration.QUESTIONNAIRE_TYPE_REPONSE;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import isotope.commons.services.CrudEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.beans.specification.ReponsesQuestionnaireEvaluationSpecifications.*;
import static org.springframework.data.jpa.domain.Specification.where;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 *
 * Date de génération : 01/03/2018
 */
@Service
public class ReponsesQuestionnaireEvaluationService extends CrudEntityService<ReponsesQuestionnaireEvaluationRepository, ReponsesQuestionnaireEvaluation, Long> {

    @Autowired
    QuestionQuestionnaireEvaluationService questionQuestionnaireEvaluationService;

    public ReponsesQuestionnaireEvaluationService(ReponsesQuestionnaireEvaluationRepository repository) {
        super(repository);
    }
    public List<ReponsesQuestionnaireEvaluationBean> findAllIdQuestionAndIdObjetAndTypeObjet(final Long questionId, final Long idObjet, String typeObjet) {
        return toReponsesQuestionnaireEvaluationBeanList(repository.findAllByIdQuestionAndAndIdObjetAndTypeObjet(questionId, idObjet, typeObjet));
    }
    public List<ReponsesQuestionnaireEvaluationBean> findAllByIdObjetAndTypeObjet( final Long idObjet, String typeObjet) {
        return toReponsesQuestionnaireEvaluationBeanList(repository.findAllByIdObjetAndTypeObjet(idObjet, typeObjet));
    }
    public List<ReponsesQuestionnaireEvaluationBean> findAllByIdObjetAndTypeObjetOrderByIdQuestionAsc( final Long idObjet, String typeObjet) {
        return toReponsesQuestionnaireEvaluationBeanList(repository.findAllByIdObjetAndTypeObjetOrderByIdQuestionAsc(idObjet, typeObjet));
    }

    public ReponsesQuestionnaireEvaluationBean findOrCreateByIdQuestionAndIdObjetAndTypeObjet(final Long questionId, final Long idObjet, String typeObjet) {
        return toReponsesQuestionnaireEvaluationBean(
                repository.findByIdQuestionAndAndIdObjetAndTypeObjet(questionId, idObjet, typeObjet)
                        .orElseGet(() -> {
                          ReponsesQuestionnaireEvaluation reponse = new ReponsesQuestionnaireEvaluation();
                          reponse.setIdObjet(idObjet);
                          reponse.setTypeObjet(typeObjet);
                          reponse.setIdQuestion(questionId);
                          return reponse;
                        }));
    }

    public ReponsesQuestionnaireEvaluationBean findByIdQuestionAndIdObjetAndTypeObjetAndValue(final Long questionId, final Long idObjet, String typeObjet, String value) {
        return toReponsesQuestionnaireEvaluationBean(repository.findByIdQuestionAndAndIdObjetAndTypeObjetAndReponsePrincipale(questionId, idObjet, typeObjet, value));
    }

    public void save(final ReponsesQuestionnaireEvaluationBean reponsesQuestionnaireEvaluationBean) {
        repository.save(reponsesQuestionnaireEvaluationBean.getTo());
    }

    public void delete(final ReponsesQuestionnaireEvaluationBean reponsesQuestionnaireEvaluation) {
        repository.delete(reponsesQuestionnaireEvaluation.getTo());
    }

    private List<ReponsesQuestionnaireEvaluationBean> toReponsesQuestionnaireEvaluationBeanList(List<ReponsesQuestionnaireEvaluation> reponsesQuestionnaireEvaluations){
        if(reponsesQuestionnaireEvaluations != null) {
            List<ReponsesQuestionnaireEvaluationBean> reponsesQuestionnaireEvaluationBeans = new ArrayList<>();
            for (ReponsesQuestionnaireEvaluation reponsesQuestionnaireEvaluation : reponsesQuestionnaireEvaluations) {
                reponsesQuestionnaireEvaluationBeans.add(toReponsesQuestionnaireEvaluationBean(reponsesQuestionnaireEvaluation));
            }
            return reponsesQuestionnaireEvaluationBeans;
        } else {
            return null;
        }
    }

    private ReponsesQuestionnaireEvaluationBean toReponsesQuestionnaireEvaluationBean(ReponsesQuestionnaireEvaluation reponsesQuestionnaireEvaluation){
        if(reponsesQuestionnaireEvaluation != null) {
            return new ReponsesQuestionnaireEvaluationBean (reponsesQuestionnaireEvaluation);
        } else {
            return null;
        }
    }

    public boolean saveFileToObject(final Long idSecondary, final Long objectId, final TYPE_OBJET typeObjet, final Long id) {
        ReponsesQuestionnaireEvaluationBean reponsesQuestionnaireEvaluationBean = findOrCreateByIdQuestionAndIdObjetAndTypeObjet(idSecondary, objectId, typeObjet.getCode());
        String files = reponsesQuestionnaireEvaluationBean.getReponsePrincipale();
        if (files != null && !files.isEmpty()) {
            List<String> test = new ArrayList<>(Arrays.asList(files.split(";")));
            test.add(String.valueOf(id));
            if (test.size() > 3) {
                return false;
            }
            files = test.stream().collect(Collectors.joining(";"));
        } else {
            files = String.valueOf(id);
        }
        reponsesQuestionnaireEvaluationBean.setReponsePrincipale(files);
        save(reponsesQuestionnaireEvaluationBean);
        return true;
    }

    public void deleteFile(final Long fileId, final Long idReponse) {
        ReponsesQuestionnaireEvaluation reponsesQuestionnaireEvaluation = repository.findById(idReponse).orElseThrow(() -> new EntityNotFoundException());
        List<String> strings = new ArrayList<>(Arrays.asList(reponsesQuestionnaireEvaluation.getReponsePrincipale().split(";")));
        reponsesQuestionnaireEvaluation.setReponsePrincipale(strings.stream().filter(id -> !Objects.equals(id, String.valueOf(fileId))).collect(Collectors.joining(";")));
        repository.save(reponsesQuestionnaireEvaluation);
    }

    public void deleteByEcocite(final Long id) {
        repository.deleteAll(repository.findAll(where(hasIdObjet(id)).and(belongToEcocite())));
    }

    public void deleteByAction(final Long id) {
        repository.deleteAll(repository.findAll(where(hasIdObjet(id)).and(belongToAction())));
    }

    public void deleteByActionAndQuestionnaireCode(final Long id, final QUESTIONNAIRE_CODE code) {
        List<Long> idQuestions = questionQuestionnaireEvaluationService.findByCodeQuestionnaire(code)
                .stream()
                .map(QuestionQuestionnaireEvaluation::getId)
                .collect(Collectors.toList());
        if (!idQuestions.isEmpty()) {
            repository.deleteAll(repository.findAll(where(hasIdObjet(id)).and(belongToAction()).and(hasIdQuestionIn(idQuestions))));
        }
    }

    public List<ReponsesQuestionnaireEvaluation> cloneForAction(final Long idClone, final Long idOriginal) {
        List<ReponsesQuestionnaireEvaluation> clones = repository.findAllByIdObjetAndTypeObjet(idOriginal, TYPE_OBJET.ACTION.getCode())
                .stream()
                .map(original -> {
                    ReponsesQuestionnaireEvaluation clone = new ReponsesQuestionnaireEvaluation();
                    clone.setIdObjet(idClone);
                    clone.setIdQuestion(original.getIdQuestion());
                    clone.setTypeObjet(TYPE_OBJET.ACTION.getCode());
                    clone.setReponsePrincipale(original.getReponsePrincipale());
                    clone.setReponseSecondaire(original.getReponseSecondaire());
                    return clone;
                })
                .collect(Collectors.toList());
        return repository.saveAll(clones);
    }

    public List<ReponsesQuestionnaireEvaluation> fixCloneLink(final Long idClone, final Map<Long, FileUpload> cloneDocumentReferenceMap) {
        List<ReponsesQuestionnaireEvaluation> clones = repository.findAll(where(belongToAction()).and(hasIdObjet(idClone)).and(hasQuestionType(QUESTIONNAIRE_TYPE_REPONSE.FILE)));
        clones.forEach(reponse -> {
            List<String> keys = new ArrayList<>();
            Arrays.stream(reponse.getReponsePrincipale().split(";")).forEach(stringKey -> {
                Long key = Long.parseLong(stringKey);
                if (cloneDocumentReferenceMap.containsKey(key)) {
                    keys.add(String.valueOf(cloneDocumentReferenceMap.get(key).getId()));
                }
            });
            reponse.setReponsePrincipale(keys.stream().collect(Collectors.joining(";")));
        });
        return repository.saveAll(clones);
    }
}
