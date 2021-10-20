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
