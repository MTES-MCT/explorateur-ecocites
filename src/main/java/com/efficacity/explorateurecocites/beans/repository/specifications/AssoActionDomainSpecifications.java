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

package com.efficacity.explorateurecocites.beans.repository.specifications;

import com.efficacity.explorateurecocites.beans.model.AssoActionDomain;
import com.efficacity.explorateurecocites.beans.model.AssoActionDomain_;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class AssoActionDomainSpecifications {

    public static Specification<AssoActionDomain> hasId(Long id) {
        return (root, query, cb) -> cb.equal(root.get(AssoActionDomain_.id), id);
    }

    public static Specification<AssoActionDomain> hasAction(Long idAction) {
        return (root, query, cb) -> cb.equal(root.get(AssoActionDomain_.idAction), idAction);
    }

    public static Specification<AssoActionDomain> hasActionIn(List<Long> idAction) {
        return (root, query, cb) -> root.get(AssoActionDomain_.idAction).in(idAction);
    }

    public static Specification<AssoActionDomain> hasPoid(Integer poid) {
      return (root, query, cb) -> cb.equal(root.get(AssoActionDomain_.poid), poid);
    }

    public static Specification<AssoActionDomain> hasDomain(Long idEtqAxe) {
        return (root, query, cb) -> cb.equal(root.get(AssoActionDomain_.idDomain), idEtqAxe);
    }
}
