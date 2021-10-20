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

import com.efficacity.explorateurecocites.beans.model.Etape;
import com.efficacity.explorateurecocites.beans.model.Etape_;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import org.springframework.data.jpa.domain.Specification;

public class EtapeSpecifications {
    public static Specification<Etape> hasIdObjet(Long id) {
        return (root, query, cb) -> cb.equal(root.get(Etape_.idObjet), id);
    }

    private static Specification<Etape> hasTypeObject(TYPE_OBJET typeObjet) {
        return (root, query, cb) -> cb.equal(root.get(Etape_.typeObjet), typeObjet.getCode());
    }

    public static Specification<Etape> belongToEcocite() {
        return hasTypeObject(TYPE_OBJET.ECOCITE);
    }

    public static Specification<Etape> belongToAction() {
        return hasTypeObject(TYPE_OBJET.ACTION);
    }
}
