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

package com.efficacity.explorateurecocites.ui.bo.forms.tables.validators;

import com.efficacity.explorateurecocites.beans.service.ActionService;
import com.efficacity.explorateurecocites.beans.service.EcociteService;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.ActionForm;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_FINANCEMENT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ActionFormValidator implements Validator {

    @Autowired
    MessageSourceService messageSourceService;

    @Autowired
    ActionService actionService;

    @Autowired
    EcociteService ecociteService;

    @Override
    public boolean supports(final Class<?> aClass) {
        return ActionForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(final Object o, final Errors errors) {
        ActionForm form = (ActionForm) o;
        if (actionService.countByNumero(form.getNumero()) > 0L) {
            errors.rejectValue("numero", "error.attribut.unique", "Ce numéro d'action est déjà utilisé, merci de vérifier sa validité");
        }
        if (!ecociteService.existWithId(form.getIdEcocite())) {
            errors.rejectValue("ecocite", "error.attribut.notfound", "Cette valeur ne correspond pas à une valeur connue");
        }
        if (TYPE_FINANCEMENT.getByCode(form.getTypeFinancement()) == null) {
            errors.rejectValue("typeFinancement", "error.attribut.unknown", "Valeur non reconnue");
        }
    }
}
