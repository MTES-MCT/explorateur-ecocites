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

package com.efficacity.explorateurecocites.beans.specification;

import com.efficacity.explorateurecocites.beans.model.AssoActionIngenierie;
import com.efficacity.explorateurecocites.beans.model.AssoActionIngenierie_;
import org.springframework.data.jpa.domain.Specification;

public class AssoActionIngenierieSpecifications {

    public static Specification<AssoActionIngenierie> hasId(Long id) {
        return (root, query, cb) -> cb.equal(root.get(AssoActionIngenierie_.id), id);
    }

    public static Specification<AssoActionIngenierie> hasAction(Long idAction) {
        return (root, query, cb) -> cb.equal(root.get(AssoActionIngenierie_.idAction), idAction);
    }

    public static Specification<AssoActionIngenierie> hasPoid(Integer poid) {
      return (root, query, cb) -> cb.equal(root.get(AssoActionIngenierie_.poid), poid);
    }

    public static Specification<AssoActionIngenierie> hasEtqIngenierie(Long idEtqIng) {
        return (root, query, cb) -> cb.equal(root.get(AssoActionIngenierie_.idEtiquetteIngenierie), idEtqIng);
    }
}
