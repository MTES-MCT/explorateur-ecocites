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

package com.efficacity.explorateurecocites.beans.service;

import com.efficacity.explorateurecocites.beans.model.AssoIndicateurDomaine;
import com.efficacity.explorateurecocites.beans.model.Indicateur;
import com.efficacity.explorateurecocites.beans.repository.AssoIndicateurDomaineRepository;
import isotope.commons.services.CrudEntityService;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 * <p>
 * Date de génération : 12/02/2018
 */
@Service
public class AssoIndicateurDomaineService extends CrudEntityService<AssoIndicateurDomaineRepository, AssoIndicateurDomaine, Long> {
    public AssoIndicateurDomaineService(AssoIndicateurDomaineRepository repository) {
        super(repository);
    }

    public List<AssoIndicateurDomaine> getListByDomaine(Long idDomaine) {
        return repository.findByIdDomaine(idDomaine);
    }

    public List<AssoIndicateurDomaine> getListByIndicateur(Long idIndicateur) {
        return repository.findByIdIndicateur(idIndicateur);
    }
    public List<AssoIndicateurDomaine> getListByIndicateurOrderByIdDomaine(Long idIndicateur) {
        return repository.findByIdIndicateurOrderByIdDomaine(idIndicateur);
    }

    public void delete(Long id){
        repository.delete(id);
    }

    public void createOne(final Indicateur indic, final String domaine) {
        AssoIndicateurDomaine indicateurDomaine = new AssoIndicateurDomaine();
        indicateurDomaine.setIdDomaine(Long.valueOf(domaine));
        indicateurDomaine.setIdIndicateur(indic.getId());
        repository.save(indicateurDomaine);
    }

    public void createSeveral(final Indicateur indic, final List<String> domaines) {
        domaines.forEach(d -> createOne(indic, d));
    }

    public List<AssoIndicateurDomaine> getList() {
        return repository.findAll();
    }

    public void updateSeveral(final Indicateur indicateur, final List<String> domaines) {
        List<AssoIndicateurDomaine> list = getListByIndicateur(indicateur.getId());
        List<String> newAssos = domaines.stream()
                .filter(o -> list.stream()
                        .noneMatch(a -> String.valueOf(a.getIdDomaine()).equals(o)))
                .collect(Collectors.toList());
        List<AssoIndicateurDomaine> toDelete = list.stream()
                .filter(a -> !domaines.contains(String.valueOf(a.getIdDomaine())))
                .collect(Collectors.toList());
        repository.delete(toDelete);
        createSeveral(indicateur, newAssos);
    }
}
