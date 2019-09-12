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

package com.efficacity.explorateurecocites.ui.bo.forms.validator;

import com.efficacity.explorateurecocites.beans.biz.AssoIndicateurObjetBean;
import com.efficacity.explorateurecocites.beans.service.AssoIndicateurObjetService;
import com.efficacity.explorateurecocites.beans.service.IndicateurService;
import com.efficacity.explorateurecocites.ui.bo.forms.AjoutIndicateurMesureForm;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_MESURE;
import com.efficacity.explorateurecocites.utils.enumeration.mesure.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;

@Component
public class AjoutIndicateurMesureFormValidator implements Validator {

    @Autowired
    IndicateurService indicateurService;

    @Autowired
    AssoIndicateurObjetService assoIndicateurObjetService;

    @Override
    public boolean supports(final Class<?> aClass) {
        return AjoutIndicateurMesureForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(final Object o, final Errors errors) {
        AjoutIndicateurMesureForm form = (AjoutIndicateurMesureForm) o;
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.parseDefaulting(ChronoField.DAY_OF_MONTH, 1);
        builder.append(DateTimeFormatter.ofPattern("MM/yyyy"));
        DateTimeFormatter dtf = builder.toFormatter();
        try {
            LocalDate.parse(form.getDate(), dtf);
        } catch (DateTimeParseException e) {
            errors.rejectValue("date", "error.attribut.format", "Veuillez vérifier le format de la date");
        }
        Long id = null;
        try {
            id = Long.parseLong(form.getIdAssoObjetIndicateur());
        } catch (NumberFormatException e) {
            errors.rejectValue("idAssoObjetIndicateur", "error.attribut.format", "Id incorrect");
        }
        if (id != null) {
            AssoIndicateurObjetBean assoIndicateurObjetBean = assoIndicateurObjetService.findOneById(id);
            TYPE_MESURE typeMesure = TYPE_MESURE.getByCode(assoIndicateurObjetBean.getIndicateur().getTypeMesure());
            Float value = null;
            try {
                value = Float.parseFloat(form.getValue());
            } catch (NumberFormatException e) {
                errors.rejectValue("value", "error.attribut.format", "Le format de la valeur est incorrect");
            }
            if (typeMesure != null && value != null) {
                switch (typeMesure) {
                    case NOMBRE_DEC_O:
                        if (value < 0) {
                            errors.rejectValue("value", "error.attribut.format", "Le format de la valeur est incorrect, choisissez un nombre positif");
                        }
                        break;
                    case NOMBRE_DEC_0_1:
                        if (value < 0 || value > 1) {
                            errors.rejectValue("value", "error.attribut.format", "Le format de la valeur est incorrect, choisissez une valeur entre 0 et 1");
                        }
                        break;
                    case OUI_NON:
                        if (NON_OUI.getByValue(value.intValue()) == null) {
                            errors.rejectValue("value", "error.attribut.format", "Veuillez entrer une des valeurs proposées");
                        }
                        break;
                    case ECHELLE_LICKERT_ACCORD:
                        if (LIKERT_ACCORD.getByValue(value.intValue()) == null) {
                            errors.rejectValue("value", "error.attribut.format", "Veuillez entrer une des valeurs proposées");
                        }
                        break;
                    case ECHELLE_LICKERT_BENEFICE:
                        if (LIKERT_BENEFICE.getByValue(value.intValue()) == null) {
                            errors.rejectValue("value", "error.attribut.format", "Veuillez entrer une des valeurs proposées");
                        }
                        break;
                    case ECHELLE_LICKERT_EXCELLENT:
                        if (LIKERT_EXCELLENT.getByValue(value.intValue()) == null) {
                            errors.rejectValue("value", "error.attribut.format", "Veuillez entrer une des valeurs proposées");
                        }
                        break;
                    case ECHELLE_LICKERT_CHIFFRES_SEULS:
                        if (LIKERT_CHIFFRES.getByValue(value.intValue()) == null) {
                            errors.rejectValue("value", "error.attribut.format", "Veuillez entrer une des valeurs proposées");
                        }
                        break;
                }
            }
        }
    }
}
