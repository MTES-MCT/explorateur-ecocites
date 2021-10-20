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

import com.efficacity.explorateurecocites.beans.model.AssoIndicateurObjectif;
import com.efficacity.explorateurecocites.beans.model.Indicateur;
import com.efficacity.explorateurecocites.beans.repository.AssoIndicateurObjectifRepository;
import isotope.commons.services.CrudEntityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.beans.specification.AssoIndicateurObjectifSpecifications.hasIdObjectif;
import static org.springframework.data.jpa.domain.Specification.where;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 * <p>
 * Date de génération : 12/02/2018
 */
@Service
public class AssoIndicateurObjectifService extends CrudEntityService<AssoIndicateurObjectifRepository, AssoIndicateurObjectif, Long> {
    public AssoIndicateurObjectifService(AssoIndicateurObjectifRepository repository) {
        super(repository);
    }

    public List<AssoIndicateurObjectif> getListByObjectif(Long idObjectif) {
        return repository.findByIdObjectif(idObjectif);
    }

    public List<AssoIndicateurObjectif> getListByIndicateur(Long idIndicateur) {
        return repository.findByIdIndicateur(idIndicateur);
    }
    public List<AssoIndicateurObjectif> getListByIndicateurOrderByIdObjectifAsc(Long idIndicateur) {
        return repository.findByIdIndicateurOrderByIdObjectifAsc(idIndicateur);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }


    public void createSeveral(final Indicateur indic, final List<String> objectifs) {
        objectifs.forEach(o -> createOne(indic, o));
    }

    public void createOne(final Indicateur indic, final String objectif) {
        AssoIndicateurObjectif indicateurObjectif = new AssoIndicateurObjectif();
        indicateurObjectif.setIdIndicateur(indic.getId());
        indicateurObjectif.setIdObjectif(Long.valueOf(objectif));
        repository.save(indicateurObjectif);
    }

    public Long countByObjectif(final Long id) {
        return repository.count(where(hasIdObjectif(id)));
    }

    public void updateSeveral(final Indicateur indicateur, final List<String> objectifs) {
        List<AssoIndicateurObjectif> list = getListByIndicateur(indicateur.getId());
        List<String> newAssos = objectifs.stream()
                .filter(o -> list.stream()
                        .noneMatch(a -> String.valueOf(a.getIdObjectif()).equals(o)))
                .collect(Collectors.toList());
        List<AssoIndicateurObjectif> toDelete = list.stream()
                .filter(a -> !objectifs.contains(String.valueOf(a.getIdObjectif())))
                .collect(Collectors.toList());
        repository.deleteAll(toDelete);
        createSeveral(indicateur, newAssos);
    }
}
