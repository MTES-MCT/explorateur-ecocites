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

import com.efficacity.explorateurecocites.beans.model.Finalite;
import com.efficacity.explorateurecocites.beans.repository.FinaliteRepository;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.FinaliteTableForm;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import isotope.commons.exceptions.NotFoundException;
import isotope.commons.services.CrudEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.beans.specification.FinaliteSpecifications.hasIdIn;
import static org.springframework.data.jpa.domain.Specification.where;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 * <p>
 * Date de génération : 08/02/2018
 */
@Service
public class FinaliteService extends CrudEntityService<FinaliteRepository, Finalite, Long> {
    @Autowired
    ActionService actionService;

    @Autowired
    EcociteService ecociteService;

    @Autowired
    MessageSourceService messageSourceService;

    @Autowired
    EtiquetteFinaliteService etiquetteFinaliteService;

    public FinaliteService(FinaliteRepository repository) {
        super(repository);
    }

    public List<Finalite> getList() {
        return repository.findAll();
    }

    public List<Finalite> getList(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        return repository.findAll(where(hasIdIn(ids)));
    }

    /**
     * Récupérer toutes les axes de la BDD
     *
     * @return un ensemble {@code LinkedHashMap} des axes avec l'id et le nom
     * @see #getList()
     */
    public LinkedHashMap<String, String> getMap() {
        return getList().stream()
                .collect(Collectors.toMap(e -> String.valueOf(e.getId()), Finalite::getLibelle,
                        (k1, k2) -> k1,
                        LinkedHashMap::new)
                );
    }

    public Finalite findById(Long id) {
        return findOne(id)
                .orElseThrow(() ->
                        new NotFoundException("Finalité" + " '" + id + "' n'est pas trouvé")
                );
    }

    public String delete(final Long id, Locale locale) {
        // Si l'indicateur est associer à une action ou une écocité => KO on delete pas
        if (etiquetteFinaliteService.getListByFinalite(id).size() > 0) {
            return messageSourceService.getMessageSource().getMessage("error.objet.suppression.liee", null, locale);
        } else {
            repository.deleteById(id);
            return "";
        }
    }

    public Finalite createOne(final FinaliteTableForm finaliteTableForm) {
        return repository.save(finaliteTableForm.getFinalite());
    }

    public void save(final Finalite finalite) {
        repository.save(finalite);
    }
}
