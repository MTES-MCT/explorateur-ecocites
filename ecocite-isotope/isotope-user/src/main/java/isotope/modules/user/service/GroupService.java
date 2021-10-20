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

import isotope.modules.user.model.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

/**
 * Service utilisé pour gérer les actions sur les groupes
 *
 * Created by qletel on 06/07/2016.
 */
public interface GroupService {

	Optional<Group> getGroupById(int id);

	Optional<Group> getGroupByShortcut(String shortcut);

	List<Group> getGroups();

	List<Group> getGroups(Sort sort);

	Page<Group> getGroups(Pageable page);

}
