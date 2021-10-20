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
import com.efficacity.explorateurecocites.utils.enumeration.ETAPE_ECOCITE;
import com.efficacity.explorateurecocites.utils.enumeration.ETAT_PUBLICATION;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class EcociteSpecifications {
    public static Specification<Ecocite> findEcociteLikeQuery(String query) {
        return (Root<Ecocite> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get(Ecocite_.nom)), "%" + query.toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get(Ecocite_.descPerimetre)), "%" + query.toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get(Ecocite_.descStrategie)), "%" + query.toLowerCase() + "%"));
    }

    public static Specification<Ecocite> hasNom(String value) {
        return (Root<Ecocite> root,  CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(Ecocite_.nom)), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Ecocite> hasId(Long id) {
        return (root, query, cb) -> cb.equal(root.get(Ecocite_.id), id);
    }

    public static Specification<Ecocite> hasIdIn(List<Long> ids) {
        return (root, query, cb) -> root.get(Ecocite_.id).in(ids);
    }

    public static Specification<Ecocite> hasRegion(Long value) {
        return (root, query, cb) -> cb.equal(root.get(Ecocite_.idRegion), value);
    }

    public static Specification<Ecocite> hasSiren(String value) {
        return (root, query, cb) -> cb.equal(root.get(Ecocite_.siren), value);
    }

    public static Specification<Ecocite> hasPorteur(String value) {
        return (Root<Ecocite> root,  CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(Ecocite_.porteur)), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Ecocite> hasAnnee(String value) {
        return (Root<Ecocite> root,  CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(Ecocite_.anneeAdhesion)), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Ecocite> hasEtatPublication(String value) {
        return (root, query, cb) -> cb.equal(root.get(Ecocite_.etatPublication), value);
    }

    public static Specification<Ecocite> hasEtatPublication(ETAT_PUBLICATION value) {
        return (root, query, cb) -> cb.equal(root.get(Ecocite_.etatPublication), value.getCode());
    }

    public static Specification<Ecocite> hasCaraterisationStatus(String value) {
        return (root, query, cb) -> {
            Root<Etape> rootEtape = query.from(Etape.class);
            return cb.and(
                    cb.equal(root.get(Ecocite_.id),rootEtape.get(Etape_.idObjet)),
                    cb.equal(rootEtape.get(Etape_.codeEtape), ETAPE_ECOCITE.CARACTERISATION.getCode()),
                    cb.equal(rootEtape.get(Etape_.typeObjet), TYPE_OBJET.ECOCITE.getCode()),
                    cb.equal(rootEtape.get(Etape_.statut),value)
            );
        };
    }

    public static Specification<Ecocite> hasChoixIndicateurStatus(String value) {
        return (root, query, cb) -> {
            Root<Etape> rootEtape = query.from(Etape.class);
            return cb.and(
                    cb.equal(root.get(Ecocite_.id),rootEtape.get(Etape_.idObjet)),
                    cb.equal(rootEtape.get(Etape_.codeEtape), ETAPE_ECOCITE.INDICATEUR.getCode()),
                    cb.equal(rootEtape.get(Etape_.typeObjet), TYPE_OBJET.ECOCITE.getCode()),
                    cb.equal(rootEtape.get(Etape_.statut),value)
            );
        };
    }

    public static Specification<Ecocite> hasMesureIndicateurStatus(String value) {
        return (root, query, cb) -> {
            Root<Etape> rootEtape = query.from(Etape.class);
            return cb.and(
                    cb.equal(root.get(Ecocite_.id),rootEtape.get(Etape_.idObjet)),
                    cb.equal(rootEtape.get(Etape_.codeEtape), ETAPE_ECOCITE.MESURE_INDICATEUR.getCode()),
                    cb.equal(rootEtape.get(Etape_.typeObjet), TYPE_OBJET.ECOCITE.getCode()),
                    cb.equal(rootEtape.get(Etape_.statut),value)
            );
        };
    }

    public static Specification<Ecocite> hasImpactProgrammeStatus(String value) {
        return (root, query, cb) -> {
            Root<Etape> rootEtape = query.from(Etape.class);
            return cb.and(
                    cb.equal(root.get(Ecocite_.id),rootEtape.get(Etape_.idObjet)),
                    cb.equal(rootEtape.get(Etape_.codeEtape), ETAPE_ECOCITE.CONTEXTE_ET_FACTEUR.getCode()),
                    cb.equal(rootEtape.get(Etape_.typeObjet), TYPE_OBJET.ECOCITE.getCode()),
                    cb.equal(rootEtape.get(Etape_.statut),value)
            );
        };
    }

    public static Specification<Ecocite> hasFinalite(Long id) {
        return (root, query, cb) -> {
            Root<AssoObjetObjectif> rootAssoObjetObjectif = query.from(AssoObjetObjectif.class);
            Root<EtiquetteFinalite> rootEtiquetteFinalite = query.from(EtiquetteFinalite.class);
            return cb.and(
                    cb.equal(root.get(Ecocite_.id), rootAssoObjetObjectif.get(AssoObjetObjectif_.idObjet)),
                    cb.equal(rootAssoObjetObjectif.get(AssoObjetObjectif_.typeObjet), TYPE_OBJET.ECOCITE.getCode()),
                    cb.equal(rootAssoObjetObjectif.get(AssoObjetObjectif_.idObjectif), rootEtiquetteFinalite.get(EtiquetteFinalite_.id)),
                    cb.equal(rootEtiquetteFinalite.get(EtiquetteFinalite_.idFinalite), id)
            );
        };
    }
}
