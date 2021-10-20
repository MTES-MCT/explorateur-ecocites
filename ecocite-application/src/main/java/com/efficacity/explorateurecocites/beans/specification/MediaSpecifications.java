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

import com.efficacity.explorateurecocites.beans.biz.ActionBean;
import com.efficacity.explorateurecocites.beans.biz.EcociteBean;
import com.efficacity.explorateurecocites.beans.model.Action;
import com.efficacity.explorateurecocites.beans.model.Ecocite;
import com.efficacity.explorateurecocites.beans.model.Media;
import com.efficacity.explorateurecocites.beans.model.Media_;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

public class MediaSpecifications {

    // Hide public constructor
    private MediaSpecifications() {}

    public static Specification<Media> hasIdAjaris(Integer idAjaris) {
        return (root, query, cb) -> cb.equal(root.get(Media_.idAjaris), idAjaris);
    }

    private static Specification<Media> hasTypeObject(TYPE_OBJET typeObjet) {
        return (root, query, cb) -> cb.equal(root.get(Media_.typeObject), typeObjet.getCode());
    }

    private static Specification<Media> hasTypeObject(String typeObject) {
        return (root, query, cb) -> cb.equal(root.get(Media_.typeObject), typeObject);
    }

    private static Specification<Media> hasIdObject(Long idObject) {
        return (root, query, cb) -> cb.equal(root.get(Media_.idObject), idObject);
    }

    public static Specification<Media> with(String typeObject, Long idObject, Integer level) {
        if (level == 1) {
            return where(hasTypeObject(typeObject)).and(hasIdObject(idObject)).and(isMainMedia());
        }
        return where(hasTypeObject(typeObject)).and(hasIdObject(idObject)).and(isSecondaryMedia());
    }

    public static Specification<Media> isMainMedia() {
        return (root, query, cb) -> cb.equal(root.get(Media_.level), 1);
    }

    public static Specification<Media> isSecondaryMedia() {
        return (root, query, cb) -> cb.notEqual(root.get(Media_.level), 1);
    }

    public static Specification<Media> withId(List<Long>ids) {
        return (root, query, cb) -> root.get(Media_.id).in(ids);
    }

    public static Specification<Media> withAction(Action action) {
        return where(hasTypeObject(TYPE_OBJET.ACTION)).and(hasIdObject(action.getId()));
    }

    public static Specification<Media> withAction(final Long id) {
        return where(hasTypeObject(TYPE_OBJET.ACTION)).and(hasIdObject(id));
    }

    public static Specification<Media> withAction(ActionBean action) {
        return where(hasTypeObject(TYPE_OBJET.ACTION)).and(hasIdObject(action.getId()));
    }

    public static Specification<Media> withEcocite(Ecocite ecocite) {
        return where(hasTypeObject(TYPE_OBJET.ECOCITE)).and(hasIdObject(ecocite.getId()));
    }
    public static Specification<Media> withEcocite(EcociteBean ecocite) {
        return where(hasTypeObject(TYPE_OBJET.ECOCITE)).and(hasIdObject(ecocite.getId()));
    }
}
