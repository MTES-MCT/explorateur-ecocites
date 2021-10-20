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

package isotope.modules.cm.valuelist.service;

import isotope.commons.services.CrudEntityService;
import isotope.modules.cm.valuelist.model.ValueList;
import isotope.modules.cm.valuelist.repository.ValueListRepository;
import org.springframework.stereotype.Service;

@Service
public class ValueListService extends CrudEntityService<ValueListRepository, ValueList, Long> {
    /**
     * Base constructor
     *
     * @param repository
     */
    public ValueListService(ValueListRepository repository) {
        super(repository);
    }
}
