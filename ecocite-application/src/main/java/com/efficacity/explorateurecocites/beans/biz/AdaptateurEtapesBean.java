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

package com.efficacity.explorateurecocites.beans.biz;

import com.efficacity.explorateurecocites.utils.enumeration.ETAPE_ACTION;
import com.efficacity.explorateurecocites.utils.enumeration.ETAPE_ECOCITE;
import com.efficacity.explorateurecocites.utils.enumeration.ETAPE_STATUT;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;

import java.util.*;

public class AdaptateurEtapesBean {
    private Map<String, ETAPE_STATUT> mapEtapes;
    private List<EtapeBean> etapeList;
    private TYPE_OBJET typeObjet;

    public AdaptateurEtapesBean(final TYPE_OBJET typeObjet, final List<EtapeBean> etapeBeans) {
        mapEtapes = new HashMap<>();
        this.typeObjet = typeObjet;
        switch (typeObjet) {
            case ACTION:
                Arrays.asList(ETAPE_ACTION.values()).forEach(e -> mapEtapes.put(e.getCode(), ETAPE_STATUT.IMPOSSIBLE));
                if (etapeBeans != null) {
                    etapeBeans.stream()
                            .filter(e -> e.getEtapeEnumAction() != null)
                            .forEach(e -> mapEtapes.replace(e.getEtapeEnumAction().getCode(), e.getStatutEnum()));
                }
                break;
            case ECOCITE:
                Arrays.asList(ETAPE_ECOCITE.values()).forEach(e -> mapEtapes.put(e.getCode(), ETAPE_STATUT.IMPOSSIBLE));
                if (etapeBeans != null) {
                    etapeBeans.stream()
                            .filter(e -> e.getEtapeEnumEcocite() != null)
                            .forEach(e -> mapEtapes.replace(e.getEtapeEnumEcocite().getCode(), e.getStatutEnum()));
                }
                break;
        }
        etapeList = etapeBeans;
    }

    public List<EtapeBean> getEtapeList() {
        return etapeList;
    }

    public boolean categorisationValide() {
        switch (typeObjet) {
            case ACTION:
                return Objects.equals(mapEtapes.get(ETAPE_ACTION.CARACTERISATION.getCode()), ETAPE_STATUT.VALIDER);
            case ECOCITE:
                return Objects.equals(mapEtapes.get(ETAPE_ECOCITE.CARACTERISATION.getCode()), ETAPE_STATUT.VALIDER);
            default:
                return false;
        }
    }

    public boolean indicateurValide() {
        switch (typeObjet) {
            case ACTION:
                return Objects.equals(mapEtapes.get(ETAPE_ACTION.INDICATEUR.getCode()), ETAPE_STATUT.VALIDER);
            case ECOCITE:
                return Objects.equals(mapEtapes.get(ETAPE_ECOCITE.INDICATEUR.getCode()), ETAPE_STATUT.VALIDER);
            default:
                return false;
        }
    }

    public boolean evaluationValide() {
        switch (typeObjet) {
            case ACTION:
                return Objects.equals(mapEtapes.get(ETAPE_ACTION.EVALUATION_INNOVATION.getCode()), ETAPE_STATUT.VALIDER);
            case ECOCITE:
            default:
                return false;
        }
    }

    public Map<String, ETAPE_STATUT> getMapEtapes() {
        return mapEtapes;
    }

    public void setMapEtapes(final Map<String, ETAPE_STATUT> mapEtapes) {
        this.mapEtapes = mapEtapes;
    }
}
