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

package isotope.modules.cm.menu.repository;

import isotope.modules.cm.menu.model.MenuEntry;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface MenuEntryRepository extends MenuEntryDefaultRepository, JpaSpecificationExecutor<MenuEntry> {
	Optional<MenuEntry> findByCode(String code);

	@Query(value = "select distinct " +
			"is_menu_entry.id," +
			"is_menu_entry.code," +
			"is_menu_entry.id_function," +
			"is_menu_entry.id_parent," +
			"is_menu_entry.type," +
			"is_menu_entry.icon," +
			"is_asso_menu_menu_entry.ordre," +
			"is_menu_entry.url," +
			"is_url.url as urlfunction " +
			"from is_menu_entry " +
			"join is_asso_menu_menu_entry " +
			"left join is_url on is_url.id_function=is_menu_entry.id_function " +
			"where is_asso_menu_menu_entry.id_menu=:code " +
			"and is_asso_menu_menu_entry.id_menu_entry=is_menu_entry.id", nativeQuery = true)
	Stream<Object[]> findByMenu(@Param("code") Long code);
}
