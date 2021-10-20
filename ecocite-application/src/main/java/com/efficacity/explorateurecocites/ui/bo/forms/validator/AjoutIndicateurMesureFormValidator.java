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

package com.efficacity.explorateurecocites.ui.bo.forms.validator;

import com.efficacity.explorateurecocites.beans.biz.AssoIndicateurObjetBean;
import com.efficacity.explorateurecocites.beans.service.AssoIndicateurObjetService;
import com.efficacity.explorateurecocites.beans.service.IndicateurService;
import com.efficacity.explorateurecocites.ui.bo.forms.AjoutIndicateurMesureForm;
import com.efficacity.explorateurecocites.utils.ApplicationConstants;
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

    public static final String VALUE = "value";
    public static final String VEUILLEZ_ENTRER_UNE_DES_VALEURS_PROPOSEES = "Veuillez entrer une des valeurs proposées";

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

        validateDateFormat(form.getDate(), errors);

        Long id = null;
        try {
            id = Long.parseLong(form.getIdAssoObjetIndicateur());
        } catch (NumberFormatException e) {
            errors.rejectValue("idAssoObjetIndicateur", ApplicationConstants.ERROR_ATTRIBUT_FORMAT, "Id incorrect");
        }
        if (id != null) {
            AssoIndicateurObjetBean assoIndicateurObjetBean = assoIndicateurObjetService.findOneById(id);
            TYPE_MESURE typeMesure = TYPE_MESURE.getByCode(assoIndicateurObjetBean.getIndicateur().getTypeMesure());
            Float value = null;
            try {
                value = Float.parseFloat(form.getValue());
            } catch (NumberFormatException e) {
                errors.rejectValue(VALUE, ApplicationConstants.ERROR_ATTRIBUT_FORMAT, "Le format de la valeur est incorrect");
            }
            if (typeMesure != null && value != null) {
                switch (typeMesure) {
                    case NOMBRE_DEC_O:
                        rejectValueIfMinusZero(value, errors);
                        break;
                    case NOMBRE_DEC_0_1:
                        rejectValueIfMinusZeroAndOverOne(value, errors);
                        break;
                    case OUI_NON:
                        rejectValueIfNull(NON_OUI.getByValue(value.intValue()), errors);
                        break;
                    case ECHELLE_LICKERT_ACCORD:
                        rejectValueIfNull(LIKERT_ACCORD.getByValue(value.intValue()), errors);
                        break;
                    case ECHELLE_LICKERT_BENEFICE:
                        rejectValueIfNull(LIKERT_BENEFICE.getByValue(value.intValue()), errors);
                        break;
                    case ECHELLE_LICKERT_EXCELLENT:
                        rejectValueIfNull(LIKERT_EXCELLENT.getByValue(value.intValue()), errors);
                        break;
                    case ECHELLE_LICKERT_CHIFFRES_SEULS:
                        rejectValueIfNull(LIKERT_CHIFFRES.getByValue(value.intValue()), errors);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void rejectValueIfNull(Enum<?> value, final Errors errors) {

        if (value == null) {
            errors.rejectValue(VALUE, ApplicationConstants.ERROR_ATTRIBUT_FORMAT, VEUILLEZ_ENTRER_UNE_DES_VALEURS_PROPOSEES);
        }


    }

    private void validateDateFormat(String date, Errors errors) {

        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.parseDefaulting(ChronoField.DAY_OF_MONTH, 1);
        builder.append(DateTimeFormatter.ofPattern("MM/yyyy"));
        DateTimeFormatter dtf = builder.toFormatter();

        try {
            LocalDate.parse(date, dtf);
        } catch (DateTimeParseException e) {
            errors.rejectValue("date", ApplicationConstants.ERROR_ATTRIBUT_FORMAT, "Veuillez vérifier le format de la date");
        }

    }

    private void rejectValueIfMinusZero(Float value, Errors errors) {
        if (value < 0) {
            errors.rejectValue(VALUE, ApplicationConstants.ERROR_ATTRIBUT_FORMAT, "Le format de la valeur est incorrect, choisissez un nombre positif");
        }
    }


    private void rejectValueIfMinusZeroAndOverOne(Float value, Errors errors) {
        if (value < 0 || value > 1) {
            errors.rejectValue(VALUE, ApplicationConstants.ERROR_ATTRIBUT_FORMAT, "Le format de la valeur est incorrect, choisissez une valeur entre 0 et 1");
        }
    }

}
