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

import com.efficacity.explorateurecocites.beans.model.Etape;
import com.efficacity.explorateurecocites.beans.model.Etape_;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class EtapeSpecifications {

    public static Specification<Etape> hasId(Long id) {
        return (root, query, cb) -> cb.equal(root.get(Etape_.id), id);
    }

    public static Specification<Etape> hasIdObject(Long idObject) {
        return (root, query, cb) -> cb.equal(root.get(Etape_.idObjet), idObject);
    }

    public static Specification<Etape> hasIdObjectIn(List<Long> ids) {
        return (root, query, cb) -> root.get(Etape_.idObjet).in(ids);
    }

    public static Specification<Etape> hasAction() {
        return (root, query, cb) -> cb.equal(root.get(Etape_.typeObjet), (TYPE_OBJET.ACTION.getCode()));
    }

    public static Specification<Etape> hasTypeObject(TYPE_OBJET typeObjet) {
        return (root, query, cb) -> cb.equal(root.get(Etape_.typeObjet), typeObjet.getCode());
    }

    public static Specification<Etape> hasEcocite() {
        return (root, query, cb) -> cb.equal(root.get(Etape_.typeObjet), TYPE_OBJET.ECOCITE.getCode());
    }

    public static Specification<Etape> hasCodeEtape(String codeEtape) {
        return (root, query, cb) -> cb.equal(root.get(Etape_.codeEtape), codeEtape);
    }

    public static Specification<Etape> hasStatus(String status) {
        return (root, query, cb) -> cb.equal(root.get(Etape_.statut), status);
    }
}
