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

import com.efficacity.explorateurecocites.beans.model.*;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Root;

public class AssoIndicateurObjetSpecifications {

    public static Specification<AssoIndicateurObjet> hasIndicateurSelectPourObjectif(Long idObjet, TYPE_OBJET typeObjet, Long idObjectif) {
        return (root, query, cb) -> {
            Root<AssoIndicateurObjectif> rootAssoIndicateurObjectif = query.from(AssoIndicateurObjectif.class);
            return cb.and(
                    cb.equal(root.get(AssoIndicateurObjet_.idIndicateur), rootAssoIndicateurObjectif.get(AssoIndicateurObjectif_.idIndicateur)),
                    cb.equal(root.get(AssoIndicateurObjet_.idObjet), idObjet),
                    cb.equal(root.get(AssoIndicateurObjet_.typeObjet), typeObjet.getCode()),
                    cb.equal(rootAssoIndicateurObjectif.get(AssoIndicateurObjectif_.idObjectif), idObjectif)
            );
        };
    }

    public static Specification<AssoIndicateurObjet> hasIndicateurSelectPourDomaine(Long idObjet, TYPE_OBJET typeObjet, Long idDomaine) {
        return (root, query, cb) -> {
            Root<AssoIndicateurDomaine> rootAssoIndicateurDomaine = query.from(AssoIndicateurDomaine.class);
            return cb.and(
                    cb.equal(root.get(AssoIndicateurObjet_.idIndicateur), rootAssoIndicateurDomaine.get(AssoIndicateurDomaine_.idIndicateur)),
                    cb.equal(root.get(AssoIndicateurObjet_.idObjet), idObjet),
                    cb.equal(root.get(AssoIndicateurObjet_.typeObjet), typeObjet.getCode()),
                    cb.equal(rootAssoIndicateurDomaine.get(AssoIndicateurDomaine_.idDomaine), idDomaine)
            );
        };
    }

    public static Specification<AssoIndicateurObjet> hasIdIndicateur(Long id) {
        return (root, query, cb) -> cb.equal(root.get(AssoIndicateurObjet_.idIndicateur), id);
    }

    public static Specification<AssoIndicateurObjet> hasIdObject(Long id) {
        return (root, query, cb) -> cb.equal(root.get(AssoIndicateurObjet_.idObjet), id);
    }

    public static Specification<AssoIndicateurObjet> belongToEcocite() {
        return (root, query, cb) -> cb.equal(root.get(AssoIndicateurObjet_.typeObjet), TYPE_OBJET.ECOCITE.getCode());
    }
}
