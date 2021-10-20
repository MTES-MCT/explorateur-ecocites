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

package com.efficacity.explorateurecocites.ui.bo.service.ecocite.onglets;

import com.efficacity.explorateurecocites.beans.model.AssoObjetObjectif;
import com.efficacity.explorateurecocites.beans.service.AssoIndicateurObjetService;
import com.efficacity.explorateurecocites.beans.service.AssoObjetObjectifService;
import com.efficacity.explorateurecocites.beans.service.EcociteService;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Locale;

import static com.efficacity.explorateurecocites.ui.bo.utils.RG.Ecocite.*;
import static com.efficacity.explorateurecocites.ui.bo.utils.StreamUtils.verifyStreamSizeBetweenBoundaries;

@Service
public class EcociteIndicateurService {

    @Autowired
    AssoIndicateurObjetService assoIndicateurObjetService;
    @Autowired
    AssoObjetObjectifService assoObjetObjectifService;
    @Autowired
    EcociteService ecociteService;
    @Autowired
    MessageSourceService messageSourceService;

    public String canRequestIndicateurValidation(@PathVariable Long ecociteId, Locale locale) {
        List<AssoObjetObjectif> assoObjectif =  assoObjetObjectifService.getListByEcocite(ecociteId);
        if (!verifyStreamSizeBetweenBoundaries(assoObjectif.stream().filter(assObj -> assObj.getPoid() == 1), MIN_PRIMARY_OBJECTIF, MAX_PRIMARY_OBJECTIF)) {
            return messageSourceService.getMessageSource().getMessage("error.ecocite.objectif.majeurs.missing", null, locale);
        }
        if (!verifyStreamSizeBetweenBoundaries(assoObjectif.stream().filter(assObj -> assObj.getPoid() == 2), MIN_SECONDARY_OBJECTIF, MAX_SECONDAY_OBJECTIF)) {
            return messageSourceService.getMessageSource().getMessage("error.ecocite.objectif.moderes.missing", null, locale);
        }
        if (!verifyStreamSizeBetweenBoundaries(assoObjectif.stream().filter(assObj -> assObj.getPoid() == 3), MIN_THIRD_OBJECTIF, MAX_THIRD_OBJECTIF)) {
            return messageSourceService.getMessageSource().getMessage("error.ecocite.objectif.mineurs.missing", null, locale);
        }
        if (assoIndicateurObjetService.countByEcocite(ecociteId) <= 0) {
            return messageSourceService.getMessageSource().getMessage("error.ecocite.indicateurs.missing", null, locale);
        }
        return "";
    }
}
