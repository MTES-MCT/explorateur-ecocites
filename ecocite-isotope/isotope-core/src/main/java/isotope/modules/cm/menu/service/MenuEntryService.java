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

import isotope.commons.services.CrudEntityService;
import isotope.modules.cm.menu.constant.MenuEntryType;
import isotope.modules.cm.menu.lightbeans.MenuProfilEntry;
import isotope.modules.cm.menu.model.MenuEntry;
import isotope.modules.cm.menu.repository.MenuEntryRepository;
import isotope.modules.cm.menu.repository.specifications.MenuEntrySpecification;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.stream.Stream;

@Service
public class MenuEntryService extends CrudEntityService<MenuEntryRepository, MenuEntry, Long> {

	/**
	 * Base constructor
	 *
	 * @param repository
	 */
	public MenuEntryService(MenuEntryRepository repository) {
		super(repository);
	}

	/***
	 * Récupère toutes les entrées de menu associées à un menu
	 * @param idMenu
	 * @return
	 */
	public Stream<MenuProfilEntry> getMenuEntry(Long idMenu) {
		return repository.findByMenu(idMenu).map(o -> new MenuProfilEntry(
				((BigInteger) o[MenuEntrySpecification.INDEX_ID]).longValue(),
				(String) o[MenuEntrySpecification.INDEX_CODE],
				o[MenuEntrySpecification.INDEX_ID_function] != null ? ((BigInteger) o[MenuEntrySpecification.INDEX_ID_function]).longValue() : null,
				o[MenuEntrySpecification.INDEX_ID_PARENT] != null ? ((BigInteger) o[MenuEntrySpecification.INDEX_ID_PARENT]).longValue() : null,
				MenuEntryType.valueOf((String) o[MenuEntrySpecification.INDEX_TYPE]),
				(Integer) o[MenuEntrySpecification.INDEX_ORDRE],
				(String) o[MenuEntrySpecification.INDEX_ICON],
				(String) o[MenuEntrySpecification.INDEX_URL],
				(String) o[MenuEntrySpecification.INDEX_URL_function])
		);
	}
}
