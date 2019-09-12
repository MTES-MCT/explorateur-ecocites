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

import com.efficacity.explorateurecocites.beans.model.Contact;
import com.efficacity.explorateurecocites.beans.model.Contact_;
import org.springframework.data.jpa.domain.Specification;

public class ContactSpecifications {
    public static Specification<Contact> hasEcocite(Long id) {
        return (root, query, cb) -> cb.equal(root.get(Contact_.idEcocite), id);
    }

    public static Specification<Contact> hasNom(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get(Contact_.nom)), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Contact> hasPrenom(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get(Contact_.prenom)), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Contact> hasEntite(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get(Contact_.entite)), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Contact> hasFonction(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get(Contact_.fonction)), "%" + value.toLowerCase() + "%");
    }
}
