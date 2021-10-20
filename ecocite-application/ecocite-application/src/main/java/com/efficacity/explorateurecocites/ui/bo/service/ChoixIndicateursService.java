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

package com.efficacity.explorateurecocites.ui.bo.service;

import com.efficacity.explorateurecocites.beans.biz.ActionBean;
import com.efficacity.explorateurecocites.beans.biz.AssoIndicateurObjetBean;
import com.efficacity.explorateurecocites.beans.biz.EcociteBean;
import com.efficacity.explorateurecocites.beans.model.Action;
import com.efficacity.explorateurecocites.beans.model.Ecocite;
import com.efficacity.explorateurecocites.beans.service.*;
import com.efficacity.explorateurecocites.utils.enumeration.ETAPE_ACTION;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import com.efficacity.explorateurecocites.utils.log.LoggingUtils;
import isotope.modules.security.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.function.Supplier;

@Service
public class ChoixIndicateursService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChoixIndicateursService.class);

    private final EtapeService etapeService;
    private final AssoIndicateurObjetService assoIndicateurObjetService;
    private final MesureIndicateurService mesureIndicateurService;
    private final ActionService actionService;
    private final EcociteService ecociteService;

    @Autowired
    public ChoixIndicateursService(final EtapeService etapeService,
                                   final AssoIndicateurObjetService assoIndicateurObjetService,
                                   final MesureIndicateurService mesureIndicateurService,
                                   final ActionService actionService, final EcociteService ecociteService) {
        this.etapeService = etapeService;
        this.assoIndicateurObjetService = assoIndicateurObjetService;
        this.mesureIndicateurService = mesureIndicateurService;
        this.actionService = actionService;
        this.ecociteService = ecociteService;
    }


    public void updateEtapeForAsso(final Long id, JwtUser user) {
        Boolean flagValid = false;
        if (mesureIndicateurService.hasOneValidForAsso(id)) {
            flagValid = true;
        }
        AssoIndicateurObjetBean me = assoIndicateurObjetService.findOneById(id);
        TYPE_OBJET type = TYPE_OBJET.getByCode(me.getTypeObjet());
        if (type != null) {
            switch (type) {
                case ACTION:
                    ActionBean action = actionService.findOneAction(me.getIdObjet());
                    Supplier<Action> findActionById = () -> actionService.findById(action.getId());
                    if (flagValid) {
                        if (etapeService.setEtapeActionValidationValider(action.getId(), ETAPE_ACTION.MESURE_INDICATEUR.getCode())) {
                            LoggingUtils.logActionSupplierA(LOGGER, LoggingUtils.ActionType.VALIDATION, LoggingUtils.SecondaryType.RENSEIGNEMENT_INDICATEURS, findActionById, user);
                        }
                    } else {
                        if (etapeService.setEtapeActionValidationARenseigner(action.getId(), ETAPE_ACTION.MESURE_INDICATEUR.getCode())) {
                            LoggingUtils.logActionSupplierA(LOGGER, LoggingUtils.ActionType.ANNULATION, LoggingUtils.SecondaryType.RENSEIGNEMENT_INDICATEURS, findActionById, user);
                        }
                    }
                    break;
                case ECOCITE:
                    EcociteBean ecocite = ecociteService.findOneEcocite(me.getIdObjet());
                    Supplier<Ecocite> findEcociteById = () -> ecociteService.findById(ecocite.getId());
                    if (flagValid) {
                        if (etapeService.setEtapeEcociteValidationValider(ecocite.getId(), ETAPE_ACTION.MESURE_INDICATEUR.getCode())) {
                            LoggingUtils.logActionSupplierE(LOGGER, LoggingUtils.ActionType.VALIDATION, LoggingUtils.SecondaryType.RENSEIGNEMENT_INDICATEURS, findEcociteById, user);
                        }
                    } else {
                        if (etapeService.setEtapeEcociteValidationRenseigner(ecocite.getId(), ETAPE_ACTION.MESURE_INDICATEUR.getCode())) {
                            LoggingUtils.logActionSupplierE(LOGGER, LoggingUtils.ActionType.ANNULATION, LoggingUtils.SecondaryType.RENSEIGNEMENT_INDICATEURS, findEcociteById, user);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
