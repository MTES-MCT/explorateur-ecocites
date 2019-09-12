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

package com.efficacity.explorateurecocites.beans.biz;

import com.efficacity.explorateurecocites.beans.model.Indicateur;
import com.efficacity.explorateurecocites.utils.enumeration.NATURE_INDICATEUR;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapteurIndicateursBean {
    private Map<String, List<Indicateur>> mapIndicateurs;

    public AdapteurIndicateursBean() {
        mapIndicateurs = new HashMap<>();
        mapIndicateurs.put(NATURE_INDICATEUR.IMPACTS.getCode(), new ArrayList<>());
        mapIndicateurs.put(NATURE_INDICATEUR.REALISATIONS.getCode(), new ArrayList<>());
        mapIndicateurs.put(NATURE_INDICATEUR.RESULTATS.getCode(), new ArrayList<>());
    }

    public Map<String, List<Indicateur>> getMapIndicateurs() {
        return mapIndicateurs;
    }

    public void setMapIndicateurs(final Map<String, List<Indicateur>> mapIndicateurs) {
        this.mapIndicateurs = mapIndicateurs;
    }

    public List<Indicateur> getImpactIndicateurs() {
        return mapIndicateurs.get(NATURE_INDICATEUR.IMPACTS.getCode());
    }

    public List<Indicateur> getRealisationIndicateurs() {
        return mapIndicateurs.get(NATURE_INDICATEUR.REALISATIONS.getCode());
    }

    public List<Indicateur> getResultatIndicateurs() {
        return mapIndicateurs.get(NATURE_INDICATEUR.RESULTATS.getCode());
    }

    public void put(String key, Indicateur indic) {
        mapIndicateurs.get(key).add(indic);
    }
}
