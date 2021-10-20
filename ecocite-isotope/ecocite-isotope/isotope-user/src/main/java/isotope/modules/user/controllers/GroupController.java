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


import isotope.commons.exceptions.NotFoundException;
import isotope.modules.user.model.Group;
import isotope.modules.user.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlleur REST permettant de récupérer les informations sur les groupes
 *
 * Created by spigot on 20/05/2016.
 */
@RestController
@RequestMapping("/api/admin/groups")
public class GroupController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GroupController.class);

	@Autowired
	private GroupService groupService;

	@RequestMapping(method = RequestMethod.GET)
	public List<Group> getGroups(Sort sort) {
		return groupService.getGroups(sort);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public Group getGroup(@PathVariable int id) {
		return groupService.getGroupById(id).orElseThrow(NotFoundException::new);
	}

}
