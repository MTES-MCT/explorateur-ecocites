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

package isotope.modules.cm.menu.controllers;

import isotope.modules.cm.menu.lightbeans.MenuProfilEntry;
import isotope.modules.cm.menu.service.MenuService;
import isotope.modules.cm.menu.service.impl.MenuServiceImpl;
import isotope.modules.user.IJwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Permet de récupérer les informations sur le menu pour un user
 *
 * Created by bbauduin on 23/12/2016.
 */
@RestController
@RequestMapping("/api/admin/menu")
public class MenuController {

	@Autowired
	MenuService menuService;

	/**
	 * Retourne le menu demandé sous la forme d'une hashmap
	 *
	 * @param user
	 * @param code
	 * @return
	 */
	@RequestMapping(value="/{code}", method = RequestMethod.GET)
	public Map<Long, MenuProfilEntry> getMenus(@AuthenticationPrincipal IJwtUser user, @PathVariable String code) {
		return menuService.getMenus(user, code);
	}

}
