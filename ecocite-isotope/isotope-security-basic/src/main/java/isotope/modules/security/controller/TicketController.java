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

package isotope.modules.security.controller;

import isotope.modules.security.beans.Ticket;
import isotope.modules.security.service.TicketService;
import isotope.modules.user.IJwtUser;
import isotope.modules.user.controllers.beans.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Permet de récupérer un ticket pour une URL donnée
 *
 * Created by bbauduin on 17/03/2017.
 */
@RestController
@RequestMapping(value = "/api/ticket")
public class TicketController {

	@Autowired
	private TicketService ticketService;

	/**
	 * Permet de récupérer un ticket. On utilise un post pour évite d'avoir à encoder le path.
	 *
	 * @param user le user qui a fait la demande de ticket
	 * @param path le path pour lequel on demande un ticket
	 * @return un ticket au format JWT
	 */
	@PostMapping
	public Ticket getTicket(@AuthenticationPrincipal IJwtUser user, @RequestBody Path path) {
		return ticketService.generateTicket(user, path.getPath());
	}

}
