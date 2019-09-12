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

import com.efficacity.explorateurecocites.ui.bo.forms.IndicateurForm;
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
        if (NATURE_INDICATEUR.getByCode(form.getNature()) == null) {
            errors.rejectValue("nature", "error.attribut.unknown");
        }
        if (ECHELLE_INDICATEUR.getByCode(form.getEchelle()) == null) {
            errors.rejectValue("echelle", "error.attribut.unknown");
        }
        if (Objects.equals(form.getEchelle(), ECHELLE_INDICATEUR.SPECIFIQUE.getCode())) {
            if (!Objects.equals(form.getNature(), NATURE_INDICATEUR.REALISATIONS.getCode())) {
                if (form.getObjectifs() == null || form.getObjectifs().size() < 1) {
                    errors.rejectValue("objectifs", "error.attribut.notNull");
                }
            }
            if (form.getDomaines() == null || form.getDomaines().size() < 1) {
                errors.rejectValue("domaines", "error.attribut.notNull");
            }
        } else {
            if (!Objects.equals(form.getNature(), NATURE_INDICATEUR.REALISATIONS.getCode())) {
                if (form.getObjectifs() == null || form.getObjectifs().size() < 1) {
                    errors.rejectValue("objectifs", "error.attribut.notNull");
                }
            } else {
                if (form.getDomaines() == null || form.getDomaines().size() < 1) {
                    errors.rejectValue("domaines", "error.attribut.notNull");
                }
            }
        }
    }
}
