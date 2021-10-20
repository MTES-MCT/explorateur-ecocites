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

package isotope.commons.events;

import com.google.common.base.Preconditions;
import isotope.commons.entities.BaseEntity;

/**
 * Created by oturpin on 16/06/16.
 */
public class BeforeSaveEntityEvent <T extends BaseEntity> {

    private final T entity;

    public BeforeSaveEntityEvent(final T entity) {
        this.entity = entity;
    }

    public final T getEntity() {
        return Preconditions.checkNotNull(entity);
    }


}
