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

package isotope.modules.user.controllers.forms;

import isotope.modules.user.lightbeans.UserLightbean;

import javax.validation.constraints.NotNull;

/**
 * Ce formulaire est utilisé lors de la création d'utilisateur pour obliger l'ajout de mot de passe
 *
 * Created by bbauduin on 31/10/2016.
 */
public class UserForm extends UserLightbean {

    @NotNull
    private String passwordRepeated;

    @NotNull
    private String password;

    @Override
    public String getPasswordRepeated() {
        return passwordRepeated;
    }

    @Override
    public void setPasswordRepeated(String passwordRepeated) {
        this.passwordRepeated = passwordRepeated;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
