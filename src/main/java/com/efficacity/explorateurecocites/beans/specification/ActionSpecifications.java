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

import com.efficacity.explorateurecocites.beans.model.*;
import com.efficacity.explorateurecocites.utils.enumeration.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

public class ActionSpecifications {
    public static Specification<Action> findActionLikeQuery(String query) {
        return (Root<Action> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get(Action_.nomPublic)), "%" + query.toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get(Action_.description)), "%" + query.toLowerCase() + "%"));
    }

    public static Specification<Action> hasEcocite(Long id) {
        return (root, query, cb) -> cb.equal(root.get(Action_.idEcocite), id);
    }

    public static Specification<Action> hasNumeroAction(String numero) {
        return (root, query, cb) -> cb.equal(root.get(Action_.numeroAction), numero);
    }

    public static Specification<Action> likeNumeroAction(String numero) {
        return (root, query, cb) -> cb.like(root.get(Action_.numeroAction), "%" + numero.replaceAll("%", "\\\\%").replaceAll("_", "\\\\_") + "%");
    }

    public static Specification<Action> hasNom(String value) {
        return (Root<Action> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(Action_.nomPublic)), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Action> hasAxePrincipale(Long id) {
        return (root, query, cb) -> cb.equal(root.get(Action_.idAxe), id);
    }

    public static Specification<Action> hasId(Long id) {
        return (root, query, cb) -> cb.equal(root.get(Action_.id), id);
    }

    public static Specification<Action> hasIdIn(List<Long> ids) {
        return (root, query, cb) -> root.get(Action_.id).in(ids);
    }

    public static Specification<Action> hasIdEcociteIn(List<Long> ids) {
        return (root, query, cb) -> root.get(Action_.idEcocite).in(ids);
    }

    public static Specification<Action> hasTypeFinancement(String value) {
        return (root, query, cb) -> cb.equal(root.get(Action_.typeFinancement), value);
    }

    public static Specification<Action> hasEtatAvancement(String value) {
        return (root, query, cb) -> cb.equal(root.get(Action_.etatAvancement), value);
    }

    public static Specification<Action> hasEtatAvancement(ETAT_AVANCEMENT value) {
        return (root, query, cb) -> cb.equal(root.get(Action_.etatAvancement), value.getCode());
    }

    public static Specification<Action> hasEtatPublication(String value) {
        return (root, query, cb) -> cb.equal(root.get(Action_.etatPublication), value);
    }

    public static Specification<Action> hasEtatPublication(ETAT_PUBLICATION value) {
        return (root, query, cb) -> cb.equal(root.get(Action_.etatPublication), value.getCode());
    }


    public static Specification<Action> hasCaraterisationStatus(ETAPE_STATUT value) {
        return hasCaraterisationStatus(value.getCode());
    }

    public static Specification<Action> hasCaraterisationStatus(String value) {
        return (root, query, cb) -> {
            Root<Etape> rootEtape = query.from(Etape.class);
            return cb.and(
                    cb.equal(root.get(Ecocite_.id), rootEtape.get(Etape_.idObjet)),
                    cb.equal(rootEtape.get(Etape_.codeEtape), ETAPE_ACTION.CARACTERISATION.getCode()),
                    cb.equal(rootEtape.get(Etape_.typeObjet), TYPE_OBJET.ACTION.getCode()),
                    cb.equal(rootEtape.get(Etape_.statut), value)
            );
        };
    }

    public static Specification<Action> hasIndicateurChoixStatus(ETAPE_STATUT value) {
        return hasIndicateurChoixStatus(value.getCode());
    }

    public static Specification<Action> hasIndicateurChoixStatus(String value) {
        return (root, query, cb) -> {
            Root<Etape> rootEtape = query.from(Etape.class);
            return cb.and(
                    cb.equal(root.get(Ecocite_.id), rootEtape.get(Etape_.idObjet)),
                    cb.equal(rootEtape.get(Etape_.codeEtape), ETAPE_ACTION.INDICATEUR.getCode()),
                    cb.equal(rootEtape.get(Etape_.typeObjet), TYPE_OBJET.ACTION.getCode()),
                    cb.equal(rootEtape.get(Etape_.statut), value)
            );
        };
    }

    public static Specification<Action> hasEvaluationInnovationStatus(ETAPE_STATUT value) {
        return hasEvaluationInnovationStatus(value.getCode());
    }

    public static Specification<Action> hasEvaluationInnovationStatus(String value) {
        return (root, query, cb) -> {
            Root<Etape> rootEtape = query.from(Etape.class);
            return cb.and(
                    cb.equal(root.get(Ecocite_.id), rootEtape.get(Etape_.idObjet)),
                    cb.equal(rootEtape.get(Etape_.codeEtape), ETAPE_ACTION.EVALUATION_INNOVATION.getCode()),
                    cb.equal(rootEtape.get(Etape_.typeObjet), TYPE_OBJET.ACTION.getCode()),
                    cb.equal(rootEtape.get(Etape_.statut), value)
            );
        };
    }

    public static Specification<Action> hasIndicateurMesureStatus(ETAPE_STATUT value) {
        return hasIndicateurMesureStatus(value.getCode());
    }

    public static Specification<Action> hasIndicateurMesureStatus(String value) {
        return (root, query, cb) -> {
            Root<Etape> rootEtape = query.from(Etape.class);
            return cb.and(
                    cb.equal(root.get(Ecocite_.id), rootEtape.get(Etape_.idObjet)),
                    cb.equal(rootEtape.get(Etape_.codeEtape), ETAPE_ACTION.MESURE_INDICATEUR.getCode()),
                    cb.equal(rootEtape.get(Etape_.typeObjet), TYPE_OBJET.ACTION.getCode()),
                    cb.equal(rootEtape.get(Etape_.statut), value)
            );
        };
    }

    public static Specification<Action> hasImpactProgrammeStatus(ETAPE_STATUT value) {
        return hasImpactProgrammeStatus(value.getCode());
    }

    public static Specification<Action> hasImpactProgrammeStatus(String value) {
        return (root, query, cb) -> {
            Root<Etape> rootEtape = query.from(Etape.class);
            return cb.and(
                    cb.equal(root.get(Ecocite_.id), rootEtape.get(Etape_.idObjet)),
                    cb.equal(rootEtape.get(Etape_.codeEtape), ETAPE_ACTION.CONTEXTE_ET_FACTEUR.getCode()),
                    cb.equal(rootEtape.get(Etape_.typeObjet), TYPE_OBJET.ACTION.getCode()),
                    cb.equal(rootEtape.get(Etape_.statut), value)
            );
        };
    }

    public static Specification<Action> hasDomaineAction(Long id) {
        return (root, query, cb) -> {
            Root<AssoActionDomain> rootAssoActionDomain = query.from(AssoActionDomain.class);
            return cb.and(
                    cb.equal(root.get(Action_.id), rootAssoActionDomain.get(AssoActionDomain_.idAction)),
                    cb.equal(rootAssoActionDomain.get(AssoActionDomain_.idDomain), id)
            );
        };
    }

    public static Specification<Action> hasObjectifVille(Long id, TYPE_OBJET typeObjet) {
        return (root, query, cb) -> {
            Root<AssoObjetObjectif> rootAssoActionDomain = query.from(AssoObjetObjectif.class);
            return cb.and(
                    cb.equal(root.get(Action_.id), rootAssoActionDomain.get(AssoObjetObjectif_.idObjet)),
                    cb.equal(rootAssoActionDomain.get(AssoObjetObjectif_.typeObjet), typeObjet.getCode()),
                    cb.equal(rootAssoActionDomain.get(AssoObjetObjectif_.idObjectif), id)
            );
        };
    }

    public static Specification<Action> hasFinalite(Long id) {
        return (root, query, cb) -> {
            Root<AssoObjetObjectif> rootAssoObjetObjectif = query.from(AssoObjetObjectif.class);
            Root<EtiquetteFinalite> rootEtiquetteFinalite = query.from(EtiquetteFinalite.class);
            return cb.and(
                    cb.equal(root.get(Action_.id), rootAssoObjetObjectif.get(AssoObjetObjectif_.idObjet)),
                    cb.equal(rootAssoObjetObjectif.get(AssoObjetObjectif_.typeObjet), TYPE_OBJET.ACTION.getCode()),
                    cb.equal(rootAssoObjetObjectif.get(AssoObjetObjectif_.idObjectif), rootEtiquetteFinalite.get(EtiquetteFinalite_.id)),
                    cb.equal(rootEtiquetteFinalite.get(EtiquetteFinalite_.idFinalite), id)
            );
        };
    }

    public static Specification<Action> hasTypeMission(Long id) {
        return (root, query, cb) -> {
            Root<AssoActionIngenierie> rootAssoActionDomain = query.from(AssoActionIngenierie.class);
            return cb.and(
                    cb.equal(root.get(Action_.id), rootAssoActionDomain.get(AssoActionIngenierie_.idAction)),
                    cb.equal(rootAssoActionDomain.get(AssoActionIngenierie_.idEtiquetteIngenierie), id)
            );
        };
    }

    public static Specification<Action> wasModifiedWithin30Days() {
        return (root, query, cb) -> {
            LocalDateTime timeNow = LocalDateTime.now().minusDays(30);
            return cb.and(cb.greaterThanOrEqualTo(root.get(Action_.dateModification), timeNow));
        };
    }

    public static Specification<Action> hasIdGreaterThan(Long id) {
        return (root, query, cb) -> cb.greaterThan(root.get(Action_.id), id);
    }
}
