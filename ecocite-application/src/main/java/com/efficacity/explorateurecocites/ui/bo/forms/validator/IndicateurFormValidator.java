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

import com.efficacity.explorateurecocites.ui.bo.forms.IndicateurForm;
import com.efficacity.explorateurecocites.utils.ApplicationConstants;
import com.efficacity.explorateurecocites.utils.enumeration.ECHELLE_INDICATEUR;
import com.efficacity.explorateurecocites.utils.enumeration.NATURE_INDICATEUR;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class IndicateurFormValidator implements Validator {

    @Override
    public boolean supports(final Class<?> aClass) {
        return IndicateurForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(final Object o, final Errors errors) {
        IndicateurForm form = (IndicateurForm) o;

        errorAttributUnknown(null == NATURE_INDICATEUR.getByCode(form.getNature()), "nature", errors);
        errorAttributUnknown(null == ECHELLE_INDICATEUR.getByCode(form.getEchelle()), "echelle", errors);

        if (Objects.equals(form.getEchelle(), ECHELLE_INDICATEUR.SPECIFIQUE.getCode())) {
            errorAttributNotNull(!Objects.equals(form.getNature(), NATURE_INDICATEUR.REALISATIONS.getCode()) && null == form.getObjectifs() || form.getObjectifs().isEmpty(), "objectifs", errors);
            errorAttributNotNull((null == form.getDomaines() || form.getDomaines().isEmpty()), "domaines", errors);
        } else {
            if (!Objects.equals(form.getNature(), NATURE_INDICATEUR.REALISATIONS.getCode())) {
                errorAttributNotNull((null ==  form.getObjectifs() || form.getObjectifs().isEmpty()), "objectifs", errors);

            } else {
                errorAttributNotNull((null == form.getDomaines() || form.getDomaines().isEmpty()), "domaines", errors);
            }
        }
    }


    private void errorAttributNotNull(Boolean valueToTest, String value, Errors errors) {
        if (Boolean.TRUE.equals(valueToTest)) {
            errors.rejectValue(value, ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL);
        }
    }

    private void errorAttributUnknown(Boolean valueToTest, String value, Errors errors) {
        if (Boolean.TRUE.equals(valueToTest)) {
            errors.rejectValue(value, ApplicationConstants.ERROR_ATTRIBUT_UNKNOWN);
        }
    }

}
