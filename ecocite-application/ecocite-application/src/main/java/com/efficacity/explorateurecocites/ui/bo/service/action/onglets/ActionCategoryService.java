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

package com.efficacity.explorateurecocites.ui.bo.service.action.onglets;

import com.efficacity.explorateurecocites.beans.biz.ActionBean;
import com.efficacity.explorateurecocites.beans.model.AssoActionDomain;
import com.efficacity.explorateurecocites.beans.model.AssoActionIngenierie;
import com.efficacity.explorateurecocites.beans.model.AssoObjetObjectif;
import com.efficacity.explorateurecocites.beans.service.*;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.utils.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Locale;

import static com.efficacity.explorateurecocites.ui.bo.utils.RG.Action.*;
import static com.efficacity.explorateurecocites.ui.bo.utils.StreamUtils.verifyStreamSizeBetweenBoundaries;

@Service
public class ActionCategoryService {

    @Autowired
    AssoIndicateurObjetService assoIndicateurActionService;
    @Autowired
    AssoActionIngenierieService assoActionIngenierieService;
    @Autowired
    AssoActionDomainService assoActionDomainService;
    @Autowired
    AssoObjetObjectifService assoActionObjectifService;
    @Autowired
    ActionService actionService;
    @Autowired
    MessageSourceService messageSourceService;

    public String canRequestCategorieValidation(@PathVariable Long actionId, Locale locale) {
        ActionBean actionBean = actionService.findOneAction(actionId);
        if (!actionBean.isTypeFinancementIngenierie()) {
            return messageSourceService.getMessageSource().getMessage(ApplicationConstants.ERROR_TECHNICAL, null, locale);
        }
        List<AssoActionDomain> assoDomaine =  assoActionDomainService.getListByAction(actionId);
        if (!verifyStreamSizeBetweenBoundaries(assoDomaine.stream().filter(assoDom -> assoDom.getPoid() == 1), MIN_PRIMARY_DOMAIN, MAX_PRIMARY_DOMAIN)) {
            return messageSourceService.getMessageSource().getMessage("error.action.domain.majeurs.missing", null, locale);
        }
        if (!verifyStreamSizeBetweenBoundaries(assoDomaine.stream().filter(assoDom -> assoDom.getPoid() != 1), MIN_SECONDARY_DOMAIN, MAX_SECONDAY_DOMAIN)) {
            return messageSourceService.getMessageSource().getMessage("error.action.domain.moderes.missing", null, locale);
        }
        List<AssoObjetObjectif> assoObjectif =  assoActionObjectifService.getListByAction(actionId);
        if (!verifyStreamSizeBetweenBoundaries(assoObjectif.stream().filter(assObj -> assObj.getPoid() == 1), MIN_PRIMARY_OBJECTIF, MAX_PRIMARY_OBJECTIF)) {
            return messageSourceService.getMessageSource().getMessage("error.action.objectif.majeurs.missing", null, locale);
        }
        if (!verifyStreamSizeBetweenBoundaries(assoObjectif.stream().filter(assObj -> assObj.getPoid() == 2), MIN_SECONDARY_OBJECTIF, MAX_SECONDAY_OBJECTIF)) {
            return messageSourceService.getMessageSource().getMessage("error.action.objectif.moderes.missing", null, locale);
        }
        if (!verifyStreamSizeBetweenBoundaries(assoObjectif.stream().filter(assObj -> assObj.getPoid() == 3), MIN_THIRD_OBJECTIF, MAX_THIRD_OBJECTIF)) {
            return messageSourceService.getMessageSource().getMessage("error.action.objectif.mineurs.missing", null, locale);
        }
        List<AssoActionIngenierie> assoIngenieries =  assoActionIngenierieService.getListByAction(actionId);
        if (!verifyStreamSizeBetweenBoundaries(assoIngenieries.stream().filter(assObj -> assObj.getPoid() == 1), MIN_INGENIERIE, MAX_INGENIERIE)) {
            return messageSourceService.getMessageSource().getMessage("error.action.ingenierie.missing", null, locale);
        }
        return "";
    }
}
