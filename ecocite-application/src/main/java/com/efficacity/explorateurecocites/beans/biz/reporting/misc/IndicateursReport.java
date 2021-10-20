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

package com.efficacity.explorateurecocites.beans.biz.reporting.misc;

import java.util.ArrayList;
import java.util.List;

public class IndicateursReport {
    private List<IndicateurMesureReport> realisations;
    private List<IndicateurMesureReport> resultats;
    private List<IndicateurMesureReport> impacts;

    public IndicateursReport(final List<IndicateurMesureReport> indicateurReports) {
        realisations = new ArrayList<>();
        resultats = new ArrayList<>();
        impacts = new ArrayList<>();
        for (IndicateurMesureReport i : indicateurReports) {
            switch (i.getNatureEnum()) {
                case REALISATIONS:
                    realisations.add(i);
                    break;
                case RESULTATS:
                    resultats.add(i);
                    break;
                case IMPACTS:
                    impacts.add(i);
                    break;
            }
        }

    }

    public List<IndicateurMesureReport> getRealisations() {
        return realisations;
    }

    public void setRealisations(final List<IndicateurMesureReport> realisations) {
        this.realisations = realisations;
    }

    public List<IndicateurMesureReport> getImpacts() {
        return impacts;
    }

    public void setImpacts(final List<IndicateurMesureReport> impacts) {
        this.impacts = impacts;
    }

    public List<IndicateurMesureReport> getResultats() {
        return resultats;
    }

    public void setResultats(final List<IndicateurMesureReport> resultats) {
        this.resultats = resultats;
    }
}
