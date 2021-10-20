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

package isotope.modules.user.repository;

import isotope.modules.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Ce repository nous permet de récupérer les infos de la base utilisateurs
 *
 * Created by qletel on 18/05/2016.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Récupère les utilisateurs non supprimés
     */
    Collection<User> findByDisabledFalseOrderByEmail();

    /**
     * Recupère tous les utilisateurs
     */
    Collection<User> findAllByOrderByEmail();

    /**
     * Permet de récupérer un utilisateur par son mail
     */
    Optional<User> findOneByEmail(String email);

    /**
     * Permet de récupérer un utilisateur par son email, en excluant l'utilisateur d'ID indiqué
     */
    Optional<User> findOneByIdNotAndEmail(long idUser, String email);

    /**
     * Récupère un utilisateur par son login
     */
    Optional<User> findOneByLogin(String login);

    /**
     * Permet de récupérer un utilisateur par son login, en excluant l'utilisateur d'ID indiqué
     */
    Optional<User> findOneByIdNotAndLogin(long idUser, String login);

    /**
     * Récupère la liste complète des utilisateurs
     */
    Page<User> findAll(Pageable pageable);

    /**
     * Récupère la liste de tous les utilisateurs actifs.
     * @param sort    critères de tri
     * @return la liste de tous les utilisateurs actifs
     */
    List<User> findAllByDisabledFalse(Sort sort);

}
