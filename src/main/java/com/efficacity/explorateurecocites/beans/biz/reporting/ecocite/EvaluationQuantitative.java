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

package com.efficacity.explorateurecocites.beans.biz.reporting.ecocite;

import com.efficacity.explorateurecocites.beans.biz.reporting.misc.IndicateurMesureReport;
import com.efficacity.explorateurecocites.beans.biz.reporting.misc.IndicateursReport;

import java.util.List;

public class EvaluationQuantitative {
    private IndicateursReport indicateurs;

    public EvaluationQuantitative(final List<IndicateurMesureReport> indicateurReports) {
        this.indicateurs = new IndicateursReport(indicateurReports);
    }

    public IndicateursReport getIndicateurs() {
        return indicateurs;
    }

    public void setIndicateurs(final IndicateursReport indicateurs) {
        this.indicateurs = indicateurs;
    }
}
