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

package com.efficacity.explorateurecocites.beans.biz;

import com.efficacity.explorateurecocites.beans.model.ReponsesEvaluation;
import com.efficacity.explorateurecocites.utils.ApplicationConstants;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import com.efficacity.explorateurecocites.utils.enumeration.EVALUATION_NIVEAU_INNOVATION_NATURE;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;

import java.util.Locale;

/**
 * Classe qui étent le bean model du générator
 *
 * Date de génération : 06/02/2018
 */

public class ReponsesEvaluationBean {

    private ReponsesEvaluation to;

    public ReponsesEvaluationBean(ReponsesEvaluation reponsesEvaluation) {
        super();
        this.to = reponsesEvaluation;
    }

    public Long getId() {
        return this.to.getId();
    }

    public void setId(Long id) {
        this.to.setId(id);
    }

    public Long getIdAction() {
        return this.to.getIdAction();
    }

    public void setIdAction(Long idAction) {
        this.to.setIdAction(idAction);
    }

    public Long getIdQuestion() {
        return this.to.getIdQuestion();
    }

    public void setIdQuestion(Long idQuestion) {
        this.to.setIdQuestion(idQuestion);
    }

    public Integer getNiveau() {
        return this.to.getNiveau();
    }

    public EVALUATION_NIVEAU_INNOVATION_NATURE getNiveauEnum() {
        return EVALUATION_NIVEAU_INNOVATION_NATURE.getByValue(to.getNiveau());
    }
    public void setNiveau(Integer niveau) {
        this.to.setNiveau(niveau);
    }

    public ReponsesEvaluation getTo() {
        return to;
    }

    public void validateChamps(String idChamps, Object value, MessageSource messages, Errors errors, Locale locale){
        if (idChamps.startsWith("niveau")) {
            if (CustomValidator.isEmpty(value)) {
                errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE,"error.attribut.notNull", messages.getMessage("error.attribut.notNull", null, locale));
            }
        }
    }
}
