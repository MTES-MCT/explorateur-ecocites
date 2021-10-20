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

package isotope.modules.user.repository.specifications;

import isotope.modules.user.model.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Contient toutes les spécifications (au sens JPA) permettant d'interagir avec les fonctions
 *
 * Created by bbauduin on 21/12/2016.
 */
public class FunctionSpecification {

    /**
     * Retourne les fonctions autorisées pour un utilisateur donné
     * @param idUser
     * @return
     */
    public static Specification<Function> findByUser(long idUser) {
        return (Root<Function> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // On récupère l'utilisateur en cours
            Root<User> userLink = criteriaQuery.from(User.class);
            predicates.add(criteriaBuilder.equal(userLink.get(User_.id), idUser));

            // On relie à la table des rôles
            final Join<User, Role> roles = userLink.join(User_.roles);

            // On relie les fonctions associées à un rôle
            Root<AssoFunctionRole> assoFunctionRoleLink = criteriaQuery.from(AssoFunctionRole.class);
            predicates.add(criteriaBuilder.equal(assoFunctionRoleLink.get(AssoFunctionRole_.idRole), roles.get(Role_.id)));

            // On relie le tout à la table des fonctions
            predicates.add(criteriaBuilder.equal(root.get(Function_.id), assoFunctionRoleLink.get(AssoFunctionRole_.idFunction)));

            criteriaQuery.distinct(true);

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    /**
     * Retourne toutes les fonctions qui ne sont pas associées à un rôle
     * @return
     */
    public static Specification<Function> findNotInList(List<Long> ids) {
        return (Root<Function> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            criteriaQuery.distinct(true);
            // On veut les Functions qui ne sont pas associées à un rôle
            return criteriaBuilder.not(root.get(Function_.id).in(ids));
        };
    }

    /**
     * Retourne la spécification dont l'id est passé en paramètre
     * @param idFunction
     * @return
     */
    public static Specification<Function> findById(long idFunction) {
        return (Root<Function> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(Function_.id), idFunction);
        };
    }

}
