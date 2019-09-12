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
