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

package com.efficacity.explorateurecocites.beans.biz.reporting.action;

import com.efficacity.explorateurecocites.beans.biz.ActionBean;
import com.efficacity.explorateurecocites.beans.biz.reporting.misc.EtiquettesReport;

public class Caracterisation {
    private Boolean isTypeIngenierie;
    private Boolean isTypeFinancementOuInvestissement;

    private EtiquettesReport ingenierie;
    private EtiquettesReport domaine;
    private EtiquettesReport objectif;

    public Caracterisation(final ActionBean action, final EtiquettesReport ingenierie, final EtiquettesReport domaine, final EtiquettesReport objectif) {
        this.ingenierie = ingenierie;
        this.domaine = domaine;
        this.objectif = objectif;
        isTypeIngenierie = !action.isTypeFinancementInvestissementOuPriseParticipation();
        isTypeFinancementOuInvestissement = !action.isTypeFinancementIngenierie();
    }

    public Boolean isTypeIngenierie() {
        return isTypeIngenierie;
    }

    public void setTypeIngenierie(final Boolean typeIngenierie) {
        isTypeIngenierie = typeIngenierie;
    }

    public EtiquettesReport getIngenierie() {
        return ingenierie;
    }

    public void setIngenierie(final EtiquettesReport ingenierie) {
        this.ingenierie = ingenierie;
    }

    public EtiquettesReport getDomaine() {
        return domaine;
    }

    public void setDomaine(final EtiquettesReport domaine) {
        this.domaine = domaine;
    }

    public EtiquettesReport getObjectif() {
        return objectif;
    }

    public void setObjectif(final EtiquettesReport objectif) {
        this.objectif = objectif;
    }

    public Boolean isTypeFinancementOuInvestissement() {
        return isTypeFinancementOuInvestissement;
    }

    public void setTypeFinancementOuInvestissement(final Boolean typeFinancementOuInvestissement) {
        isTypeFinancementOuInvestissement = typeFinancementOuInvestissement;
    }
}
