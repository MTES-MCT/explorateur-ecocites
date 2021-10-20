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
import com.efficacity.explorateurecocites.utils.enumeration.ETAT_BIBLIOTHEQUE;
import com.efficacity.explorateurecocites.utils.enumeration.ETAT_VALIDATION;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class IndicateurSpecifications {

    public static Specification<Indicateur> hasNomCourt(String value) {
        return (Root<Indicateur> root,  CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(Indicateur_.nomCourt)), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Indicateur> hasNomLong(String value) {
        return (Root<Indicateur> root,  CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(Indicateur_.nom)), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Indicateur> hasNature(String value) {
        return (root, query, cb) -> cb.equal(root.get(Indicateur_.nature), value);
    }

    public static Specification<Indicateur> hasNatureIn(List<String> values) {
        return (root, query, cb) -> root.get(Indicateur_.nature).in(values);
    }

    public static Specification<Indicateur> hasEchelle(String value) {
        return (root, query, cb) -> cb.equal(root.get(Indicateur_.echelle), value);
    }

    public static Specification<Indicateur> hasId(Long value) {
        return (root, query, cb) -> cb.equal(root.get(Indicateur_.id), value);
    }

    public static Specification<Indicateur> hasIdIn(List<Long> values) {
        return (root, query, cb) -> root.get(Indicateur_.id).in(values);
    }

    public static Specification<Indicateur> isValide() {
        return (root, query, cb) -> cb.equal(root.get(Indicateur_.etatValidation), ETAT_VALIDATION.VALIDE.getCode());
    }

    public static Specification<Indicateur> hasOrigine (String value) {
        return (root, query, cb) -> cb.equal(root.get(Indicateur_.origine), value);
    }

    public static Specification<Indicateur> hasEtatValidation (String value) {
        return (root, query, cb) -> cb.equal(root.get(Indicateur_.etatValidation), value);
    }

    public static Specification<Indicateur> hasEtatBibliotheque (String value) {
        return (root, query, cb) -> cb.equal(root.get(Indicateur_.etatBibliotheque), value);
    }
    public static Specification<Indicateur> isValideAndPublieOrCreateur (String createur) {
        return (root, query, cb) -> cb.or(
                cb.and(cb.equal(root.get(Indicateur_.etatBibliotheque), ETAT_BIBLIOTHEQUE.VISIBLE.getCode()),
                        cb.equal(root.get(Indicateur_.etatValidation), ETAT_VALIDATION.VALIDE.getCode()))
                , cb.equal(root.get(Indicateur_.userCreation),createur));
    }

    public static Specification<Indicateur> hasObjectifs(Long id) {
        return (root, query, cb) -> {
            Root<AssoIndicateurObjectif> rootAssoIndicateurObjectif = query.from(AssoIndicateurObjectif.class);
            return cb.and(
                    cb.equal(root.get(Indicateur_.id),rootAssoIndicateurObjectif.get(AssoIndicateurObjectif_.idIndicateur)),
                    cb.equal(rootAssoIndicateurObjectif.get(AssoIndicateurObjectif_.idObjectif), id)
            );
        };
    }

    public static Specification<Indicateur> hasDomaines(Long id) {
        return (root, query, cb) -> {
            Root<AssoIndicateurDomaine> rootAssoIndicateurDomaine = query.from(AssoIndicateurDomaine.class);
            return cb.and(
                    cb.equal(root.get(Indicateur_.id),rootAssoIndicateurDomaine.get(AssoIndicateurDomaine_.idIndicateur)),
                    cb.equal(rootAssoIndicateurDomaine.get(AssoIndicateurDomaine_.idDomaine), id)
            );
        };
    }
}
