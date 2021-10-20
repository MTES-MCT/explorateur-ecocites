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

package com.efficacity.explorateurecocites.beans.service;

import com.efficacity.explorateurecocites.beans.model.Region;
import com.efficacity.explorateurecocites.beans.repository.RegionRepository;
import isotope.commons.exceptions.NotFoundException;
import isotope.commons.services.CrudEntityService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.beans.specification.RegionSpecifications.hasSiren;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 * <p>
 * Date de génération : 16/02/2018
 */
@Service
public class RegionService extends CrudEntityService<RegionRepository, Region, Long> {
    public RegionService(RegionRepository repository) {
        super(repository);
    }

    /**
     * Récupérer une région par l'id
     *
     * @param id identifiant
     * @return
     * @throws NotFoundException si aucun est trouvé
     */
    public Region findById(Long id) {
        return findOne(id)
                .orElseThrow(() ->
                        new NotFoundException("Région" + " '" + id + "' n'est pas trouvée")
                );
    }

    /**
     * Récupérer tous les region de la BDD en ordre du nom alphabétique
     *
     * @return un ensemble {@code LinkedHashMap} des region avec l'id et le nom
     * @see #getListOrderByNomAsc()
     */
    public LinkedHashMap<String, String> getMapOrderByNomAsc() {
        return getListOrderByNomAsc()
                .stream()
                .collect(Collectors.toMap(e -> String.valueOf(e.getId()), Region::getNom,
                        (k1, k2) -> k1,
                        LinkedHashMap::new)
                );
    }

    /**
     * Récupérer tous les écocités de la BDD en ordre du nom alphabétique
     *
     * @return une liste des écocité
     */
    public List<Region> getListOrderByNomAsc() {
        return repository.findAllByOrderByNomAsc();
    }

    public Region findBySiren(final String siren) {
        return repository.findOne(hasSiren(siren)).orElse(null);
    }

    public Long countBySiren(final String siren) {
        return repository.count(hasSiren(siren));
    }
}
