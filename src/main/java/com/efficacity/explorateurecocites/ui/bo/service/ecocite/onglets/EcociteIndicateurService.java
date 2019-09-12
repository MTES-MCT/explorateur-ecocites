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
