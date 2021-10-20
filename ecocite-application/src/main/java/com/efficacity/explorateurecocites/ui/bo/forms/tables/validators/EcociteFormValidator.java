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

import com.efficacity.explorateurecocites.beans.service.EcociteService;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.EcociteForm;
import com.efficacity.explorateurecocites.utils.ApplicationConstants;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EcociteFormValidator implements Validator {

    @Autowired
    EcociteService ecociteService;

    @Override
    public boolean supports(final Class<?> aClass) {
        return EcociteForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(final Object o, final Errors errors) {
        EcociteForm form = (EcociteForm) o;
        if (!CustomValidator.isSiren(form.getSiren())) {
            errors.rejectValue("siren", ApplicationConstants.ERROR_ATTRIBUT_FORMAT, "Veuillez vérifier le format");
        } else if (ecociteService.countBySiren(form.getSiren()) > 0L) {
            errors.rejectValue("siren", "error.attribut.unique", "Ce numéro SIREN est déjà utilisé, merci de vérifier sa validité");
        }
    }
}
