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

import com.efficacity.explorateurecocites.beans.model.AssoIndicateurObjet;
import com.efficacity.explorateurecocites.beans.model.CibleIndicateur;
import com.efficacity.explorateurecocites.beans.repository.CibleIndicateurRepository;
import isotope.commons.services.CrudEntityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.beans.specification.CibleIndicateurSpecifications.hasIdAssoIndicateur;
import static com.efficacity.explorateurecocites.beans.specification.CibleIndicateurSpecifications.isValid;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 * <p>
 * Date de génération : 16/02/2018
 */
@Service
public class CibleIndicateurService extends CrudEntityService<CibleIndicateurRepository, CibleIndicateur, Long> {
    public CibleIndicateurService(CibleIndicateurRepository repository) {
        super(repository);
    }

    public List<CibleIndicateur> findAllByIdAssoIndicateurObjetOrderByDateSaisieAsc(Long IdAssoIndicateurObjet) {
        return repository.findAllByIdAssoIndicateurObjetOrderByDateSaisieAsc(IdAssoIndicateurObjet);
    }

    public void save(CibleIndicateur cibleIndicateur) {
        repository.save(cibleIndicateur);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    public void deleteByAssoIndicateur(final List<AssoIndicateurObjet> assos) {
        List<Long> ids = assos.stream().map(AssoIndicateurObjet::getId).collect(Collectors.toList());
        if (ids.size() > 0) {
            List<CibleIndicateur> cibleIndicateurs = repository.findAll(where(hasIdAssoIndicateur(ids)));
            repository.delete(cibleIndicateurs);
        }
    }

    public boolean hasOneValidForAsso(final Long id) {
        return repository.count(where(hasIdAssoIndicateur(id)).and(isValid())) > 0;
    }
}
