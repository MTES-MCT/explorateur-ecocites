/*
 * l'État, ministère chargé du logement
 * Copyright (C) 2019 IpsoSenso
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
import com.efficacity.explorateurecocites.ui.bo.forms.tables.ActionCloneForm;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ActionCloneFormValidator implements Validator {

    @Autowired
    MessageSourceService messageSourceService;

    @Autowired
    ActionService actionService;

    @Autowired
    EcociteService ecociteService;

    @Override
    public boolean supports(final Class<?> aClass) {
        return ActionCloneForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(final Object o, final Errors errors) {
        ActionCloneForm form = (ActionCloneForm) o;
        if (!actionService.findOne(form.getIdOriginal()).isPresent()) {
            errors.rejectValue("idOriginal", "error.attribut.notfound", "Cette action n'existe pas");
        }
        if (actionService.countByNumero(form.getNumero()) > 0L) {
            errors.rejectValue("numero", "error.attribut.unique", "Ce numéro d'action est déjà utilisé, merci de vérifier sa validité");
        }
    }
}
