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

package com.efficacity.explorateurecocites.utils;

import isotope.commons.validation.ValidationError;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

public class ValidationErrorBuilder {

    public static ValidationError fromBindingErrors(Errors errors) {
        return isotope.commons.validation.ValidationErrorBuilder.fromBindingErrors(errors);
    }

    /**
     * A utiliser lorsque l'on a pas besoin de la valeurs des champs rejeté, par exemple lorsque le formulaire contient des fichiers
     * @param errors La champs contenant les erreurs.
     * @return l'objets contenant tous les champs d'erreur à serialiser.
     */
    public static ValidationError fromBindingErrorsIgnoreValue(Errors errors) {
        List<ObjectError> globalErrors = errors.getGlobalErrors();
        List<FieldError> fieldErrors = errors.getFieldErrors()
                .stream()
                .map(fe -> new FieldError(fe.getObjectName(), fe.getField(), null, fe.isBindingFailure(), fe.getCodes(), fe.getArguments(), fe.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ValidationError(globalErrors, fieldErrors);
    }
}
