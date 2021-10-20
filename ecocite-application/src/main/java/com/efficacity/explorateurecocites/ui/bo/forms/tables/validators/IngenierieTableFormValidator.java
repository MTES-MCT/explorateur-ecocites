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

import com.efficacity.explorateurecocites.ui.bo.forms.tables.IngenierieTableForm;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.awt.*;

@Component
public class IngenierieTableFormValidator implements Validator {

    @Autowired
    MessageSourceService messageSourceService;

    @Override
    public boolean supports(final Class<?> aClass) {
        return IngenierieTableForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(final Object o, final Errors errors) {
        IngenierieTableForm form = (IngenierieTableForm) o;
        if (form.getColor().startsWith("#")) {
            try {
                Color.decode(form.getColor());
            } catch (NumberFormatException nfe) {
                errors.rejectValue("color_1", "error.attribut.unknown", "Ce champ doit être une couleur");
            }
        } else {
            errors.rejectValue("color_1", "error.attribut.unknown", "Ce champ doit être une couleur");
        }
    }
}
