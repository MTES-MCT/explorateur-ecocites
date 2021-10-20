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

import com.efficacity.explorateurecocites.beans.model.AssoObjetObjectif;
import com.efficacity.explorateurecocites.beans.model.AssoObjetObjectif_;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class AssoObjetObjectifSpecifications {

    public static Specification<AssoObjetObjectif> hasId(Long id) {
        return (root, query, cb) -> cb.equal(root.get(AssoObjetObjectif_.id), id);
    }

    public static Specification<AssoObjetObjectif> hasIdObjet(Long idAction) {
        return (root, query, cb) -> cb.equal(root.get(AssoObjetObjectif_.idObjet), idAction);
    }

    public static Specification<AssoObjetObjectif> hasIdObjetIn(List<Long> idActions) {
        return (root, query, cb) -> root.get(AssoObjetObjectif_.idObjet).in(idActions);
    }

    public static Specification<AssoObjetObjectif> hasAction() {
        return (root, query, cb) -> cb.equal(root.get(AssoObjetObjectif_.typeObjet), TYPE_OBJET.ACTION.getCode());
    }

    public static Specification<AssoObjetObjectif> hasEcocite() {
        return (root, query, cb) -> cb.equal(root.get(AssoObjetObjectif_.typeObjet), TYPE_OBJET.ECOCITE.getCode());
    }

    public static Specification<AssoObjetObjectif> hasPoid(Integer poid) {
      return (root, query, cb) -> cb.equal(root.get(AssoObjetObjectif_.poid), poid);
    }

    public static Specification<AssoObjetObjectif> hasObjectif(Long idEtqFinalite) {
        return (root, query, cb) -> cb.equal(root.get(AssoObjetObjectif_.idObjectif), idEtqFinalite);
    }
}
