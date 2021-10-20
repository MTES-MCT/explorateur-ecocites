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
