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

package com.efficacity.explorateurecocites.ui.bo.service;

import com.efficacity.explorateurecocites.beans.biz.QuestionQuestionnaireEvaluationBean;
import com.efficacity.explorateurecocites.beans.biz.ReponsesQuestionnaireEvaluationBean;
import com.efficacity.explorateurecocites.beans.model.ReponsesQuestionnaireEvaluation;
import com.efficacity.explorateurecocites.beans.service.FileUploadService;
import com.efficacity.explorateurecocites.beans.service.QuestionQuestionnaireEvaluationService;
import com.efficacity.explorateurecocites.beans.service.ReponsesQuestionnaireEvaluationService;
import com.efficacity.explorateurecocites.ui.bo.forms.BeanSaveAttribut;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import com.efficacity.explorateurecocites.utils.enumeration.QUESTIONNAIRE_CATEGORIE_CODE;
import com.efficacity.explorateurecocites.utils.enumeration.QUESTIONNAIRE_CODE;
import com.efficacity.explorateurecocites.utils.enumeration.QUESTIONNAIRE_TYPE_REPONSE;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by dylan on 14/02/18.
 */
@Service
public class QuestionnaireEvaluationService {
    private static final String AUTRE_VALUE = "Autre";
    private static final String NON_CONCERNE_VALUE = "Non concerné";

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionnaireEvaluationService.class);

    @Autowired
    MessageSourceService messageSourceService;

    @Autowired
    QuestionQuestionnaireEvaluationService questionQuestionnaireEvaluationService;

    @Autowired
    ReponsesQuestionnaireEvaluationService reponsesQuestionnaireEvaluationService;

    @Autowired
    FileUploadService fileUploadService;


    public LinkedHashMap<String, LinkedList<QuestionQuestionnaireEvaluationBean>> genererQuestionnaire(String codeQuestionnaire, Long idObjet, String typeObjet) {

        // On va faire un LinkedHashMap ou la clé sera les catégorie du questionnaire pour facilité la génération dans le ftl
        LinkedHashMap<String, LinkedList<QuestionQuestionnaireEvaluationBean>> questionnaire = new LinkedHashMap<>();

        for (QUESTIONNAIRE_CATEGORIE_CODE categorie : QUESTIONNAIRE_CATEGORIE_CODE.valuesByCodeQuestionnaire(codeQuestionnaire)) {
            LinkedList<QuestionQuestionnaireEvaluationBean>  questions = questionQuestionnaireEvaluationService.getListeCodeQuestionnaireAndCodeCategorie(codeQuestionnaire,categorie.getCode());
            // Pour chaque question on lui charge ces réponses
            for (QuestionQuestionnaireEvaluationBean question : questions) {
                associateResponseToQuestion(question, idObjet, typeObjet);
            }

            questionnaire.put(categorie.getCode(),questions);
        }

        return questionnaire;
    }

    public LinkedHashMap<Long, QuestionQuestionnaireEvaluationBean> getQuestionsAndReponses(final QUESTIONNAIRE_CODE codeQuestionnaire, final Long idObjet, final String typeObjet) {
        // On va faire un LinkedHashMap ou la clé sera les catégorie du questionnaire pour facilité la génération dans le ftl
        LinkedHashMap<Long, QuestionQuestionnaireEvaluationBean> questionnaire = new LinkedHashMap<>();
        List<QuestionQuestionnaireEvaluationBean> questions = questionQuestionnaireEvaluationService.getListeCodeQuestionnaire(codeQuestionnaire);
        // Pour chaque question on lui charge ces réponses
        for(QuestionQuestionnaireEvaluationBean question : questions){
            associateResponseToQuestion(question, idObjet, typeObjet);
            questionnaire.put(question.getId(), question);
        }
        return questionnaire;
    }

    private void associateResponseToQuestion(QuestionQuestionnaireEvaluationBean question, final Long idObjet, final String typeObjet) {
        List<ReponsesQuestionnaireEvaluationBean> reponseQuestions = reponsesQuestionnaireEvaluationService.findAllIdQuestionAndIdObjetAndTypeObjet(question.getId(), idObjet, typeObjet);
        if (Objects.equals(question.getTypeReponseEnum(), QUESTIONNAIRE_TYPE_REPONSE.FILE)) {
            question.setListeReponses(fileUploadService.associateFileToResponse(reponseQuestions));
        } else {
            question.setListeReponses(reponseQuestions);
        }
        if(CustomValidator.isNotEmpty(question.getIdQuestionMere())) {
            List<ReponsesQuestionnaireEvaluationBean> reponseQuestionsMeres = reponsesQuestionnaireEvaluationService.findAllIdQuestionAndIdObjetAndTypeObjet(question.getIdQuestionMere(), idObjet, typeObjet);
            for (ReponsesQuestionnaireEvaluationBean reponseQuestionsMere : reponseQuestionsMeres) {
                if (Objects.equals(reponseQuestionsMere.getReponsePrincipale(), question.getReponseAttendu())) {
                    question.setMereHasReponseVoulu(true);
                }
            }
        }
    }

    public Boolean validationQuestionnaire(final QUESTIONNAIRE_CODE code, final Long id, final TYPE_OBJET typeObjet, final Errors errors, final Locale locale, final Boolean shouldProcessError){
        LinkedHashMap<Long, QuestionQuestionnaireEvaluationBean> questionnaire = getQuestionsAndReponses(code, id, typeObjet.getCode());
        Boolean tag = true;
        for (QuestionQuestionnaireEvaluationBean question : questionnaire.values()) {
            if ((question != null && (question.isMereHasReponseVoulu() || question.getIdQuestionMere() == null))
                    && !validationQuestion(question, errors, locale)) {
                if (shouldProcessError) {
                    tag = false;
                } else {
                    return false;
                }
            }
        }
        return tag;
    }

    public Boolean validationQuestion(QuestionQuestionnaireEvaluationBean question, Errors errors, Locale locale) {
        Boolean tag = true;
        switch (question.getTypeReponseEnum()) {
            case TEXTE_LIBRE:
                if(question.getListeReponses() == null || question.getListeReponses().isEmpty() || question.getListeReponses().stream().allMatch(r -> CustomValidator.isEmpty(r.getReponsePrincipale()))){
                    errors.rejectValue("reponses[" + question.getId() + "].reponseText","error.attribut.notNull", messageSourceService.getMessageSource().getMessage("error.attribut.notNull", null, locale));
                    tag = false;
                }
                break;
            case RADIO_BOUTON:
                if(question.getListeReponses() == null || question.getListeReponses().isEmpty() || question.getListeReponses().stream().allMatch(r -> CustomValidator.isEmpty(r.getReponsePrincipale()))){
                    errors.rejectValue("reponses[" + question.getId() + "].reponseText","error.attribut.notNull", messageSourceService.getMessageSource().getMessage("error.attribut.notNull", null, locale));
                    tag = false;
                }
                break;
            case RADIO_BOUTON_AUTRE:
                if(question.getListeReponses() == null || question.getListeReponses().isEmpty() || question.getListeReponses().stream().allMatch(r -> CustomValidator.isEmpty(r.getReponsePrincipale()))){
                    errors.rejectValue("reponses[" + question.getId() + "].reponseText","error.attribut.notNull", messageSourceService.getMessageSource().getMessage("error.attribut.notNull", null, locale));
                    tag = false;
                } else if(AUTRE_VALUE.equals(question.getReponses()) && CustomValidator.isEmpty(question.getReponses())){
                    errors.rejectValue("reponses[" + question.getId() + "].reponseTextSecondaire","error.attribut.notNull", messageSourceService.getMessageSource().getMessage("error.attribut.notNull", null, locale));
                    tag = false;
                }
                break;
            case RADIO_BOUTON_OUI_NON_NONCONCERNE:
                if(question.getListeReponses() == null || question.getListeReponses().isEmpty() || question.getListeReponses().stream().allMatch(r -> CustomValidator.isEmpty(r.getReponsePrincipale()))){
                    errors.rejectValue("reponses["+question.getId()+"].reponseText","error.attribut.notNull", messageSourceService.getMessageSource().getMessage("error.attribut.notNull", null, locale));
                    tag = false;
                }
                break;
            case CHECKBOX_AUTRE:
                if(CustomValidator.isEmpty(question.getListeReponses()) || question.getListeReponses().isEmpty()) {
                    errors.rejectValue("reponses[" + question.getId() + "].reponseList","error.attribut.notNull", messageSourceService.getMessageSource().getMessage("error.attribut.notNull", null, locale));
                    tag = false;
                } else if (question.getListeReponses().stream()
                            .anyMatch(r -> r.getReponsePrincipale() != null && AUTRE_VALUE.equals(r.getReponsePrincipale()) && CustomValidator.isEmpty(r.getReponseSecondaire()))) {
                        errors.rejectValue("reponses["+question.getId()+"].reponseTextSecondaire","error.attribut.notNull", messageSourceService.getMessageSource().getMessage("error.attribut.notNull", null, locale));
                    tag = false;
                }
                break;
            case CHECKBOX_TEXTE_LIBRE:
                if(question.getListeReponses() == null || question.getListeReponses().isEmpty()){
                    errors.rejectValue("reponses["+question.getId()+"].reponseList","error.attribut.notNull", messageSourceService.getMessageSource().getMessage("error.attribut.notNull", null, locale));
                    tag = false;
                } else {
                    final List<ReponsesQuestionnaireEvaluationBean> listeReponses = question.getListeReponses();
                    for (final ReponsesQuestionnaireEvaluationBean r : listeReponses) {
                        if (CustomValidator.isNotEmpty(r.getReponsePrincipale()) && CustomValidator.isEmpty(r.getReponseSecondaire())) {
                            String[] reponsesAttendues = question.getReponses().split("\\$\\$\\$");
                            Integer index = IntStream.range(0, reponsesAttendues.length).filter(j -> reponsesAttendues[j].equals(r.getReponsePrincipale())).findFirst().orElse(-1);
                            if (index != -1) {
                                errors.rejectValue("reponses[" + question.getId() + "].reponseListSecondaire[" + index + "]", "error.attribut.notNull", messageSourceService.getMessageSource().getMessage("error.attribut.notNull", null, locale));
                                tag = false;
                            }
                        }
                    }
                }
                break;
            case FILE:
                // Do Nothing
                break;
        }
        return tag;
    }

    public ReponsesQuestionnaireEvaluationBean sauvegardeReponse(BeanSaveAttribut object){
        // On récup la question
        QuestionQuestionnaireEvaluationBean question = questionQuestionnaireEvaluationService.findById(Long.parseLong(object.getReferenceId()));
        ReponsesQuestionnaireEvaluationBean reponse = null;

        if(QUESTIONNAIRE_TYPE_REPONSE.CHECKBOX_AUTRE.getCode().equals(question.getTypeReponse()) || QUESTIONNAIRE_TYPE_REPONSE.CHECKBOX_TEXTE_LIBRE.getCode().equals(question.getTypeReponse())){
            /*
             En cas de checkBox traitement particulier
             une ligne en base = une checkbox saisie, donc si elle existe on sup => car en fo on a décheck et inversement si existe pas on créer
             Attention s'il s'agit de la réponse secondaire de la checkobx on delete pas, juste un update
            */
            ReponsesQuestionnaireEvaluationBean reponseCheckBox;
            if(CustomValidator.isNotEmpty(object.getCheckBoxValue())){
                // Cas de la réponse secondaire de checkbox texte libre ou le autre
                reponse = reponsesQuestionnaireEvaluationService.findByIdQuestionAndIdObjetAndTypeObjetAndValue(Long.parseLong(object.getReferenceId()),
                        Long.parseLong(object.getObjectId()),
                        object.getReferenceTypeObjet(),
                        object.getCheckBoxValue());

                // Dans ce cas la y a que un update pas de delete on va donc continuer le processus
            } else {
                // Cas checkBox valeur principale
                reponseCheckBox = reponsesQuestionnaireEvaluationService.findByIdQuestionAndIdObjetAndTypeObjetAndValue(Long.parseLong(object.getReferenceId()),
                        Long.parseLong(object.getObjectId()),
                        object.getReferenceTypeObjet(),
                        object.getAttributValue());

                if(reponseCheckBox == null){
                    // On créé
                    if (object.getChecked()) {
                        ReponsesQuestionnaireEvaluation newReponsereponseCheckBox = new ReponsesQuestionnaireEvaluation();
                        newReponsereponseCheckBox.setIdObjet(Long.parseLong(object.getObjectId()));
                        newReponsereponseCheckBox.setTypeObjet(object.getReferenceTypeObjet());
                        newReponsereponseCheckBox.setIdQuestion(Long.parseLong(object.getReferenceId()));
                        reponse = new ReponsesQuestionnaireEvaluationBean(newReponsereponseCheckBox);
                    } else {
                        return null;
                    }
                } else if (!object.getChecked()) {
                    // On va delete
                    reponsesQuestionnaireEvaluationService.delete(reponseCheckBox);
                    return null;
                } else {
                    return null;
                }
            }
        } else {
            // Autre cas de type de réponse
            reponse = reponsesQuestionnaireEvaluationService.findOrCreateByIdQuestionAndIdObjetAndTypeObjet(
                    Long.parseLong(object.getReferenceId()),
                    Long.parseLong(object.getObjectId()),
                    object.getReferenceTypeObjet());
        }

        // On set les champs
        reponse.validateChamps(object.getAttributId(), object.getAttributValue(), messageSourceService.getMessageSource());

        /*
        * En cas de question mére modifié, si elle n'a plus la valeur demandé il faut faire un clean les réponse des questions filles.
        * On récup TOUTE (car cas des checkBox) les réponses de la questions, check pour chacune des filles si y a la réponse necessaire
        */
        List<ReponsesQuestionnaireEvaluationBean> reponseQuestions = reponsesQuestionnaireEvaluationService.findAllIdQuestionAndIdObjetAndTypeObjet(question.getId(),Long.parseLong(object.getObjectId()),object.getReferenceTypeObjet());
        List<String> listeReponsePrincipale= new ArrayList<>();
        for(ReponsesQuestionnaireEvaluationBean reponseQuestion : reponseQuestions){
            listeReponsePrincipale.add(reponseQuestion.getReponsePrincipale());
        }
        checkQuestionFille(object, listeReponsePrincipale);

        return reponse;
    }

    private void checkQuestionFille(BeanSaveAttribut object, List<String> reponseQuestionPrincipales){
        List<QuestionQuestionnaireEvaluationBean> questionFilles =  questionQuestionnaireEvaluationService.findByIdQuestionMere(Long.valueOf(object.getReferenceId()));
        if(questionFilles == null) {
            return;
        }
        for(QuestionQuestionnaireEvaluationBean questionFille : questionFilles){
            // check si la réponse attendu est dans la liste des réponses sauvegardé
            if(CustomValidator.isNotEmpty(questionFille.getReponseAttendu()) && !reponseQuestionPrincipales.contains(questionFille.getReponseAttendu())){
                // On delete les réponses saisie pour cette question si ce n'est plus la réponse attendu
                List<ReponsesQuestionnaireEvaluationBean> reponseQuestionFilles = reponsesQuestionnaireEvaluationService.findAllIdQuestionAndIdObjetAndTypeObjet(questionFille.getId(),Long.parseLong(object.getObjectId()),object.getReferenceTypeObjet());
                if(reponseQuestionFilles == null) {
                    break;
                }
                for(ReponsesQuestionnaireEvaluationBean reponseQuestionFille : reponseQuestionFilles){
                    reponsesQuestionnaireEvaluationService.delete(reponseQuestionFille);
                }
            }
        }
    }
}
