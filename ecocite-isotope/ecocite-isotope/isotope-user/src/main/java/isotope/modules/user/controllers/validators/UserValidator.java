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

package isotope.modules.user.controllers.validators;

import isotope.modules.user.lightbeans.UserLightbean;
import isotope.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


/**
 * Effectue des contrôles à la création d'un nouvel User
 * A savoir, vérifie que le username ou l'email du formulaire ne sont pas déjà associés à un utilisateur existant
 * Created by jrateau on 29/01/2018.
 */
@Component
public class UserValidator implements Validator {

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return UserLightbean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserLightbean userLightbean = (UserLightbean) target;

		// je vérifie qu'un user autre que l'utilisateur qu'on édite n'existe pas avec le même login...
		if (userService.getOtherUserByLogin(userLightbean.getId(), userLightbean.getLogin()).isPresent()) {
			errors.rejectValue("login", "exists");
		}
		// ... et qu'un user n'existe pas avec le même email
		if (userService.getOtherUserByEmail(userLightbean.getId(), userLightbean.getEmail()).isPresent()) {
			errors.rejectValue("email", "exists");
		}
	}
}
