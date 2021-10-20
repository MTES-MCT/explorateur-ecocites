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

import com.efficacity.explorateurecocites.beans.model.AssoObjetContact;
import com.efficacity.explorateurecocites.beans.model.AssoObjetContact_;
import com.efficacity.explorateurecocites.utils.enumeration.IMPORTANCE_LEVEL;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import org.springframework.data.jpa.domain.Specification;

public class AssoObjetContactSpecifications {

    public static Specification<AssoObjetContact> hasIdObjet(Long id) {
        return (root, query, cb) -> cb.equal(root.get(AssoObjetContact_.idObjet), id);
    }

    public static Specification<AssoObjetContact> hasIdContact(Long id) {
        return (root, query, cb) -> cb.equal(root.get(AssoObjetContact_.idContact), id);
    }

    private static Specification<AssoObjetContact> hasTypeObject(TYPE_OBJET typeObjet) {
        return (root, query, cb) -> cb.equal(root.get(AssoObjetContact_.typeObjet), typeObjet.getCode());
    }

    private static Specification<AssoObjetContact> hasImportanceLevel(Integer level) {
        return (root, query, cb) -> cb.equal(root.get(AssoObjetContact_.importance), level);
    }

    public static Specification<AssoObjetContact> isContactPrincipal() {
        return hasImportanceLevel(IMPORTANCE_LEVEL.PRINCIPAL.getCode());
    }

    public static Specification<AssoObjetContact> isContactSecondaire() {
        return hasImportanceLevel(IMPORTANCE_LEVEL.SECONDAIRE.getCode());
    }

    public static Specification<AssoObjetContact> belongToEcocite() {
        return hasTypeObject(TYPE_OBJET.ECOCITE);
    }

    public static Specification<AssoObjetContact> belongToAction() {
        return hasTypeObject(TYPE_OBJET.ACTION);
    }
}
