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

package isotope.modules.cm.menu.service;

import isotope.modules.cm.menu.lightbeans.MenuProfilEntry;
import isotope.modules.user.IJwtUser;

import java.util.Map;

/**
 * Service permettant de manipuler les objets Menu profilés
 *
 * Created by oturpin on 18/04/17.
 */
public interface MenuService {

    /**
     * Retourne la liste des menus qu'un utilisateur a le droit de voir par rapport à un type
     *
     * @param user
     * @param code
     * @return
     */
    Map<Long, MenuProfilEntry> getMenus(IJwtUser user, String code);
}
