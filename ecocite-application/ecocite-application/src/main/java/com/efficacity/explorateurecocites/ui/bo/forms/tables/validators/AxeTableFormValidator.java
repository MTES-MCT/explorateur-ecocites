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

import com.efficacity.explorateurecocites.ui.bo.forms.tables.AxeTableForm;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.awt.*;

@Component
public class AxeTableFormValidator implements Validator {

    public static final String ERROR_ATTRIBUT_UNKNOWN = "error.attribut.unknown";
    public static final String CE_CHAMP_DOIT_ETRE_UNE_COULEUR = "Ce champ doit être une couleur";

    @Autowired
    MessageSourceService messageSourceService;

    @Override
    public boolean supports(final Class<?> aClass) {
        return AxeTableForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(final Object o, final Errors errors) {
        AxeTableForm form = (AxeTableForm) o;
        if (form.getIcon_changed() != 0) {
            if (form.getIcon_file() == null || form.getIcon_file().isEmpty()) {
                errors.rejectValue("icon_file", ERROR_ATTRIBUT_UNKNOWN, "Veuillez choisir un fichier");
            }
        }
        if (form.getColor_1() != null && form.getColor_1().startsWith("#")) {
            try {
                Color.decode(form.getColor_1());
            } catch (NumberFormatException nfe) {
                errors.rejectValue("color_1", ERROR_ATTRIBUT_UNKNOWN, CE_CHAMP_DOIT_ETRE_UNE_COULEUR);
            }
        } else {
            errors.rejectValue("color_1", ERROR_ATTRIBUT_UNKNOWN, CE_CHAMP_DOIT_ETRE_UNE_COULEUR);
        }
        if (form.getColor_2() != null && form.getColor_2().startsWith("#")) {
            try {
                Color.decode(form.getColor_2());
            } catch (NumberFormatException nfe) {
                errors.rejectValue("color_2", ERROR_ATTRIBUT_UNKNOWN, CE_CHAMP_DOIT_ETRE_UNE_COULEUR);
            }
        } else {
            errors.rejectValue("color_2", ERROR_ATTRIBUT_UNKNOWN, CE_CHAMP_DOIT_ETRE_UNE_COULEUR);
        }
    }
}
