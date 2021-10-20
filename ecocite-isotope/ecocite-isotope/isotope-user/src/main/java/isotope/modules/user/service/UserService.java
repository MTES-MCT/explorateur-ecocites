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

package isotope.modules.user.service;


import isotope.modules.user.lightbeans.UserLightbean;

import java.util.Collection;
import java.util.Optional;

/**
 * Service de modification des user isotope
 *
 * Created by spigot on 15/06/16.
 */
public interface UserService {
    Optional<UserLightbean> getUserById(Long id);

    Optional<UserLightbean> getUserByEmail(String email);

    Optional<UserLightbean> getOtherUserByEmail(Long id, String email);

    Optional<UserLightbean> getUserByLogin(String login);

    Optional<UserLightbean> getOtherUserByLogin(Long id, String login);

    Collection<UserLightbean> getAllUsers(boolean showDisabled);

    UserLightbean create(Long idUser, UserLightbean userLightbean);

    boolean delete(Long idUser, Long idUserDelete);

    UserLightbean restore(Long idUser, Long idUserRestore);

    UserLightbean save(Long idUser, UserLightbean userLightbean);

}
