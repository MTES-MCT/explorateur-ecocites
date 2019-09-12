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

package com.efficacity.explorateurecocites.ui.bo.service.action.onglets;

import com.efficacity.explorateurecocites.beans.biz.ActionBean;
import com.efficacity.explorateurecocites.beans.biz.AssoIndicateurObjetBean;
import com.efficacity.explorateurecocites.beans.model.AssoActionDomain;
import com.efficacity.explorateurecocites.beans.model.AssoActionIngenierie;
import com.efficacity.explorateurecocites.beans.model.AssoObjetObjectif;
import com.efficacity.explorateurecocites.beans.service.*;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.utils.enumeration.NATURE_INDICATEUR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Locale;

import static com.efficacity.explorateurecocites.ui.bo.utils.RG.Action.*;
import static com.efficacity.explorateurecocites.ui.bo.utils.StreamUtils.verifyStreamSizeBetweenBoundaries;

@Service
public class ActionIndicateurService {

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

    public String canRequestIndicateurValidation(@PathVariable Long actionId, Locale locale) {
        ActionBean actionBean = actionService.findOneAction(actionId);
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

        if (actionBean.isTypeFinancementIngenierieEtInvestissement() || actionBean.isTypeFinancementIngenierieEtPriseParticipation()) {
            List<AssoActionIngenierie> assoIngenieries =  assoActionIngenierieService.getListByAction(actionId);
            if (!verifyStreamSizeBetweenBoundaries(assoIngenieries.stream().filter(assObj -> assObj.getPoid() == 1), MIN_INGENIERIE, MAX_INGENIERIE)) {
                return messageSourceService.getMessageSource().getMessage("error.action.ingenierie.missing", null, locale);
            }
        }
        List<AssoIndicateurObjetBean> listAssoIndicateurAction = assoIndicateurActionService.findAllByAction(actionId, null);
        for (NATURE_INDICATEUR nature : NATURE_INDICATEUR.values()) {
            if (listAssoIndicateurAction.stream().noneMatch(assoIndic -> assoIndic.getIndicateur().getNature().equals(nature.getCode()))) {
                return messageSourceService.getMessageSource().getMessage("error.action.indicateurs.missing", null, locale);
            }
        }
        return "";
    }
}
