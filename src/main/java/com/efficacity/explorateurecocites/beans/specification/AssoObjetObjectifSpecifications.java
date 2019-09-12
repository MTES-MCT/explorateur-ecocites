/*
 * Explorateur Écocités
 * Copyright (C) 2019 l'État, ministère chargé du logement
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
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
