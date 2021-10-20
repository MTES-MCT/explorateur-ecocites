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

import isotope.commons.services.CrudEntityService;
import isotope.modules.user.lightbeans.RoleLightBean;
import isotope.modules.user.model.Role;
import isotope.modules.user.repository.RoleRepository;
import isotope.modules.user.utils.LightBeanToBeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RoleService extends CrudEntityService<RoleRepository, Role, Long> {

	/**
	 * Base constructor
	 *
	 * @param repository
	 */
	public RoleService(RoleRepository repository) {
		super(repository);
	}

	/**
	 * @return un stream de tous les rôles enregistrés
	 */
	public List<RoleLightBean> getRoles() {
		return StreamSupport.stream(findAll().spliterator(), false)
				.map(LightBeanToBeanUtils::copyFrom)
				.collect(Collectors.toList());
	}

}
