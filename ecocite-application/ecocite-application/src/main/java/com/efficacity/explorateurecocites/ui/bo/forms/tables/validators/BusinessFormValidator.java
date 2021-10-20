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

package com.efficacity.explorateurecocites.ui.bo.forms.tables.validators;

import com.efficacity.explorateurecocites.beans.service.BusinessService;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.BusinessForm;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.utils.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class BusinessFormValidator implements Validator {

    @Autowired
    MessageSourceService messageSourceService;

    @Autowired
    BusinessService businessService;

    @Override
    public boolean supports(final Class<?> aClass) {
        return BusinessForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(final Object o, final Errors errors) {
        BusinessForm form = (BusinessForm) o;
        LocalDateTime dateDebut = null;
        LocalDateTime dateFin = null;
        if (form.getId() != null) {
            if (businessService.countByNumeroAffaire(form.getNumero(), form.getId()) > 0L) {
                errors.rejectValue("numero", "error.attribut.unique", "Ce numéro d'affaire est déjà utilisé, merci de vérifier sa validité");
            }
        }
        if (form.getDateDebut() != null && !form.getDateDebut().trim().isEmpty()) {
            try {
                dateDebut = LocalDate.parse(form.getDateDebut(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay();
            } catch (DateTimeParseException e) {
                errors.rejectValue("dateDebut", ApplicationConstants.ERROR_ATTRIBUT_FORMAT, "Veuillez v\u00E9rifier le format");
            }
        }
        if (form.getDateFin() != null && !form.getDateFin().trim().isEmpty()) {
            try {
                dateFin = LocalDate.parse(form.getDateFin(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay();
            } catch (DateTimeParseException e) {
                errors.rejectValue("dateFin", ApplicationConstants.ERROR_ATTRIBUT_FORMAT, "Veuillez v\u00E9rifier le format");
            }
        }
        if (dateDebut != null && dateFin != null) {
            if (dateDebut.isAfter(dateFin)) {
                errors.rejectValue("dateDebut", "error.attribut.dateDebut.after", "La date de d\u00E9but ne peut \u00EAtre apr\u00E8s la date de fin");
                errors.rejectValue("dateFin", "error.attribut.dateFin.before", "La date de fin ne peut \u00EAtre avant la date de d\u00E9but");
            }
        }
    }
}
