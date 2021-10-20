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

package isotope.modules.cm.menu.service.impl;

import isotope.commons.exceptions.NotFoundException;
import isotope.commons.services.CrudEntityService;
import isotope.modules.cm.menu.constant.MenuEntryType;
import isotope.modules.cm.menu.lightbeans.MenuProfilEntry;
import isotope.modules.cm.menu.model.Menu;
import isotope.modules.cm.menu.repository.MenuRepository;
import isotope.modules.cm.menu.service.MenuEntryService;
import isotope.modules.cm.menu.service.MenuService;
import isotope.modules.user.IJwtUser;
import isotope.modules.user.lightbeans.FunctionLightBean;
import isotope.modules.user.service.FunctionService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class MenuServiceImpl extends CrudEntityService<MenuRepository, Menu, Long> implements MenuService{

	private FunctionService functionService;

	private MenuEntryService menuEntryService;

	/**
	 * Base constructor
	 *
	 * @param repository
	 */
	public MenuServiceImpl(MenuRepository repository, FunctionService functionService, MenuEntryService menuEntryService) {
		super(repository);
		this.functionService = functionService;
		this.menuEntryService = menuEntryService;
	}

	/**
	 * Retourne la liste des menus qu'un utilisateur a le droit de voir par rapport à un type
	 *
	 * @param user
	 * @return
	 */
	public Map<Long, MenuProfilEntry> getMenus(IJwtUser user, String code) {
		Menu menu = repository.findByCode(code).orElseThrow(NotFoundException::new);

		// Je récupère toutes les fonctions accessibles au user
		Map<Long, Long> mapFunctions = functionService.getFunctions(user).collect(Collectors.toMap(FunctionLightBean::getId, FunctionLightBean::getId));
		// Et les fonctions qui ne sont associées à aucun rôle
		functionService.getFunctionsWithoutAssoRole()
				.forEach(functionLightBean -> mapFunctions.put(functionLightBean.getId(), functionLightBean.getId()));

		//Je récupère les entrées de menu correspondantes au menu
		Stream<MenuProfilEntry> entreesMenu = menuEntryService.getMenuEntry(menu.getId());

		// Je crée une hashmap générale pour mettre mes éléments au fur et à mesure dedans
		Map<Long, MenuProfilEntry> allEntries = new HashMap<>();


		// Je fais le mapping des deux !
		return entreesMenu
				// Je garde tout ce qui n'est pas fonctionnalité et tout ce qui est bien autorisé
				.filter(o -> o.getType() != MenuEntryType.FUNCTION ? true : mapFunctions.get(o.getIdFunction()) != null)
				// Je trie en mettant les parents en premier pour construire la hashmap
				.sorted((o1, o2) -> {
					// Si l'un des deux éléments n'a pas de parents, il est plus grand
					if (o1.getIdParent() != null && o2.getIdParent() == null) return 1;
					if (o2.getIdParent() != null && o1.getIdParent() == null) return -1;

					// Si l'un est le parent de l'autre, il est plus grand
					if (o1.getIdParent() == o2.getId() )return 1;
					if (o2.getIdParent() == o1.getId() )return -1;

					// Si les deux ont le même parent ou n'en ont pas, on compare l'ordre
					if (o1.getIdParent() == o2.getIdParent()) {
						if (o1.getOrdre() > o2.getOrdre()) return -1;
						if (o1.getOrdre() < o2.getOrdre()) return 1;
					}

					// Sinon, on retourne 0, ils ne sont pas en relation ou de même ordre
					return 0;
				})
				.distinct()
				// Je reconstruis l'arborescence des menus
				.reduce(new HashMap<Long, MenuProfilEntry>(), (o1, o2) -> {
					// Je stocke mon élément dans la map globale pour pouvoir ajouter ses enfants plus tard
					allEntries.put(o2.getId(), o2);

					if (o2.getIdParent() == null) {
						o1.put(o2.getId(), o2);
					} else {
						allEntries.get(o2.getIdParent()).addChildren(o2);
					}
					return o1;
				}, (o, o2) -> {
					o2.keySet().stream().forEach(aLong -> o.put(aLong, o2.get(aLong)));
					return o;
				});
  }
}
