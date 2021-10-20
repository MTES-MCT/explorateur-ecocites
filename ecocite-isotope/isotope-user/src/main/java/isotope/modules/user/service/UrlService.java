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

import isotope.commons.exceptions.NotFoundException;
import isotope.commons.services.CrudEntityService;
import isotope.modules.user.lightbeans.UrlLightBean;
import isotope.modules.user.model.Url;
import isotope.modules.user.repository.UrlRepository;
import isotope.modules.user.utils.LightBeanToBeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UrlService extends CrudEntityService<UrlRepository, Url, Long> {

	/**
	 * Base constructor
	 *
	 * @param repository
	 */
	public UrlService(UrlRepository repository) {
		super(repository);
	}

	/**
	 * Retourne un url light bean pour une url donnée (attention, on ne gère pas la langue ici)
	 * @param url
	 * @return
	 */
	public UrlLightBean findByUrl(String url) {
		return LightBeanToBeanUtils.copyFrom(repository.findByUrl(url).orElseThrow(NotFoundException::new));
	}

}
