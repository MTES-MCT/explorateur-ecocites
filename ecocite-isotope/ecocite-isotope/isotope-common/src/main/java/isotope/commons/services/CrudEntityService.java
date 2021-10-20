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

package isotope.commons.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;
import java.util.Optional;

/**
 * Expérimental : classe que les services s'appuyant principalement sur un repo peuvent étendre
 * afin de bénéficier du repo embarqué et d'un panel de méthodes déjà implémentées, dans le respect
 * des conventions décourageant l'utilisation des repository en dehors de la couche de service.
 * <p>
 * Created by qletel on 25/09/2016.
 */
public abstract class CrudEntityService<R extends PagingAndSortingRepository<T, ID>, T, ID extends Serializable> {

    @Autowired
    protected R repository;


    /**
     * Base constructor
     * @param repository
     */
    protected CrudEntityService(R repository){
        this.repository = repository;
    }

    /**
     * {@link org.springframework.data.repository.CrudRepository#findOne(Serializable)}}
     * <p>
     * Permet également de favoriser l'utilisation de la classe {@code Optional}.
     */
    public Optional<T> findOne(ID id) {
        return repository.findById(id);
    }

    /**
     * {@link org.springframework.data.repository.CrudRepository#findAll()}
     */
    public Iterable<T> findAll() {
        return repository.findAll();
    }

    /**
     * {@link org.springframework.data.repository.PagingAndSortingRepository#findAll(Sort)}
     */
    public Iterable<T> findAll(Sort sort) {
        return repository.findAll(sort);
    }


}
