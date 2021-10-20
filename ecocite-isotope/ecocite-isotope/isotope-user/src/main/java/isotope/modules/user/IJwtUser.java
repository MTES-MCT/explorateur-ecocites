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

package isotope.modules.user;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Locale;

/**
 * Interface qui représente un user JWT d'isotope (relié au bean {{@link isotope.modules.user.model.User}})
 *
 * Created by bbauduin on 02/11/2016.
 */
public interface IJwtUser extends UserDetails {

    /**
     * Id utilisé pour retrouver le user en base
     * @return l'id de l'utilisateur isotope
     */
    Long getId();
    String getEmail();
    String getFirstname();
    String getLastname();
    Locale getLocale();

}
