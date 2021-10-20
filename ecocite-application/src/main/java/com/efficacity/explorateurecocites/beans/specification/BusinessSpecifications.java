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

import com.efficacity.explorateurecocites.beans.model.Business;
import com.efficacity.explorateurecocites.beans.model.Business_;
import org.springframework.data.jpa.domain.Specification;

public class BusinessSpecifications {
    public static Specification<Business> hasAction(Long id) {
        return (root, query, cb) -> cb.equal(root.get(Business_.idAction), id);
    }

    public static Specification<Business> hasId(Long id) {
        return (root, query, cb) -> cb.equal(root.get(Business_.id), id);
    }

    public static Specification<Business> hasNumeroAffaire(String n) {
        return (root, query, cb) -> cb.equal(root.get(Business_.numeroAffaire), n);
    }

    public static Specification<Business> hasLikeNumeroAffaire(String n) {
        return (root, query, cb) -> cb.like(root.get(Business_.numeroAffaire), "%"+n+"%");
    }

    public static Specification<Business> hasNumeroOperation(String n) {
        return (root, query, cb) -> cb.equal(root.get(Business_.numeroOperation), n);
    }

    public static Specification<Business> hasStatutFinancier(String n) {
        return (root, query, cb) -> cb.equal(root.get(Business_.statutFinancier), n);
    }

    public static Specification<Business> hasStatutFinancierIn(String[] n) {
        return (root, query, cb) -> root.get(Business_.statutFinancier).in(n);
    }

    public static Specification<Business> hasNom(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get(Business_.nom)), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Business> hasEcocite(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get(Business_.ecocite)), "%" + value.toLowerCase() + "%");
    }
}
