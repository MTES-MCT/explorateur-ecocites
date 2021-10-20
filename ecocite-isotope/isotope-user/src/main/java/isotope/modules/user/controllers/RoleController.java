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

package isotope.modules.user.controllers;

import isotope.modules.user.IJwtUser;
import isotope.modules.user.lightbeans.RoleLightBean;
import isotope.modules.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller qui permet de récupérer toutes les informations sur les rôles isotope
 *
 * Created by bbauduin on 29/12/2016.
 */
@RestController
@RequestMapping("/api/admin/role")
public class RoleController {

	@Autowired
	RoleService roleService;

	@RequestMapping(method = RequestMethod.GET)
	public List<RoleLightBean> getRoles(@AuthenticationPrincipal IJwtUser user) {
		return roleService.getRoles();
	}

}
