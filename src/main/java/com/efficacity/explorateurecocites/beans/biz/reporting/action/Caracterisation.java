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
