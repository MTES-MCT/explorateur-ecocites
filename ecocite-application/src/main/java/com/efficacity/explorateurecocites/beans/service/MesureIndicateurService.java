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

import com.efficacity.explorateurecocites.beans.model.AssoIndicateurObjet;
import com.efficacity.explorateurecocites.beans.model.MesureIndicateur;
import com.efficacity.explorateurecocites.beans.model.MesureIndicateur_;
import com.efficacity.explorateurecocites.beans.repository.MesureIndicateurRepository;
import isotope.commons.services.CrudEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.beans.specification.MesureIndicateurSpecifications.hasIdAssoIndicateur;
import static org.springframework.data.jpa.domain.Specification.where;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 * <p>
 * Date de génération : 16/02/2018
 */
@Service
public class MesureIndicateurService extends CrudEntityService<MesureIndicateurRepository, MesureIndicateur, Long> {
    public MesureIndicateurService(MesureIndicateurRepository repository) {
        super(repository);
    }

    public List<MesureIndicateur> findAllByIdAssoIndicateurObjetOrderByDateSaisieAsc(Long IdAssoIndicateurObjet) {
        return repository.findAllByIdAssoIndicateurObjetOrderByDateSaisieAsc(IdAssoIndicateurObjet);
    }

    public MesureIndicateur create(MesureIndicateur mesureIndicateur) {
        mesureIndicateur.setDateCreation(LocalDateTime.now());
        return repository.save(mesureIndicateur);
    }

    public void save(MesureIndicateur mesureIndicateur) {
        repository.save(mesureIndicateur);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public void deleteByAssoIndicateur(final List<AssoIndicateurObjet> assos) {
        List<Long> ids = assos.stream().map(AssoIndicateurObjet::getId).collect(Collectors.toList());
        if (ids.size() > 0) {
            List<MesureIndicateur> cibleIndicateurs = repository.findAll(where(hasIdAssoIndicateur(ids)));
            repository.deleteAll(cibleIndicateurs);
        }
    }

    public boolean hasOneValidForAsso(final Long id) {
        return repository.count(where(hasIdAssoIndicateur(id))) > 0;
    }

    public MesureIndicateur getLastMesure(final List<Long> ids) {
        Page<MesureIndicateur> mesureIndicateurs = repository.findAll(where(hasIdAssoIndicateur(ids)),
                PageRequest.of(0, 1, Sort.Direction.DESC, MesureIndicateur_.dateCreation.getName()));
        if (mesureIndicateurs != null && mesureIndicateurs.getContent() != null &&
                !mesureIndicateurs.getContent().isEmpty()) {
            return mesureIndicateurs.getContent().get(0);
        }
        return null;
    }
}
