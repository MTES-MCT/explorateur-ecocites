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

import com.efficacity.explorateurecocites.beans.model.Ingenierie;
import com.efficacity.explorateurecocites.beans.repository.IngenierieRepository;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.IngenierieTableForm;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import isotope.commons.services.CrudEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Locale;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 * <p>
 * Date de génération : 08/02/2018
 */
@Service
public class IngenierieService extends CrudEntityService<IngenierieRepository, Ingenierie, Long> {

    @Autowired
    ActionService actionService;

    @Autowired
    EtiquetteIngenierieService etiquetteIngenierieService;

    @Autowired
    MessageSourceService messageSourceService;

    public IngenierieService(IngenierieRepository repository) {
        super(repository);
    }

    public List<Ingenierie> getList() {
        return repository.findAll();
    }

    public Ingenierie findById(final Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public String delete(final Long id, Locale locale) {
        // Si l'indicateur est associer à une action ou une écocité => KO on delete pas
        if (etiquetteIngenierieService.getListByIngenierie(id).size() > 0) {
            return messageSourceService.getMessageSource().getMessage("error.objet.suppression.liee", null, locale);
        } else {
            repository.deleteById(id);
            return "";
        }
    }

    public Ingenierie createOne(final IngenierieTableForm ingenierieTableForm) {
        return repository.save(ingenierieTableForm.getIngenierie());
    }

    public void save(final Ingenierie ingenierie) {
        repository.save(ingenierie);
    }
}
