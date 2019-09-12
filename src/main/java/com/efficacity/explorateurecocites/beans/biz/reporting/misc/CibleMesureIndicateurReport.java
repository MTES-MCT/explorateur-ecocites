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

package com.efficacity.explorateurecocites.beans.biz.reporting.misc;

import com.efficacity.explorateurecocites.beans.model.CibleIndicateur;
import com.efficacity.explorateurecocites.beans.model.MesureIndicateur;

import java.time.format.DateTimeFormatter;

public class CibleMesureIndicateurReport {

    private String value;
    private String date;

    public CibleMesureIndicateurReport(final MesureIndicateur mesure, String unite) {
        if (unite != null && !unite.trim().isEmpty()) {
            value = mesure.getValeur() + " " + unite;
        } else {
            value = mesure.getValeur();
        }
        date = mesure.getDateSaisie().format(DateTimeFormatter.ofPattern("dd/MM/YYYY"));
    }

    public CibleMesureIndicateurReport(final MesureIndicateur mesure) {
        this(mesure, null);
    }

    public CibleMesureIndicateurReport(final CibleIndicateur cible, final String unite) {
        if (unite != null && !unite.trim().isEmpty()) {
            value = cible.getValeur() + " " + unite;
        } else {
            value = cible.getValeur();
        }
        date = cible.getDateSaisie().format(DateTimeFormatter.ofPattern("dd/MM/YYYY"));
    }

    public CibleMesureIndicateurReport(final CibleIndicateur cible) {
        this(cible, null);
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }
}
