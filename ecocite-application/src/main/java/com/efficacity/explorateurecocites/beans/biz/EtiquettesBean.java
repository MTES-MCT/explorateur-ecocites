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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EtiquettesBean {
    private Map<String, List<EtiquetteCommonBean>> domainesEtiquettes;
    private Map<String, List<EtiquetteCommonBean>> objectifsEtiquettes;
    private Map<String, List<EtiquetteCommonBean>> ingenieriesEtiquettes;

    public EtiquettesBean(final Map<String, List<EtiquetteCommonBean>> sousCategoriesDomaines, final Map<String, List<EtiquetteCommonBean>> sousCategoriesObjectif, final Map<String, List<EtiquetteCommonBean>> sousCategoriesIngenierie) {
        this.domainesEtiquettes = sousCategoriesDomaines;
        this.objectifsEtiquettes = sousCategoriesObjectif;
        this.ingenieriesEtiquettes = sousCategoriesIngenierie;
    }

    public EtiquettesBean(final Map<String, List<EtiquetteCommonBean>> sousCategoriesObjectif) {
        this(new HashMap<>(), sousCategoriesObjectif, new HashMap<>());
    }

    public Map<String, List<EtiquetteCommonBean>> getDomainesEtiquettes() {
        return domainesEtiquettes;
    }

    public void setDomainesEtiquettes(final Map<String, List<EtiquetteCommonBean>> domainesEtiquettes) {
        this.domainesEtiquettes = domainesEtiquettes;
    }

    public Map<String, List<EtiquetteCommonBean>> getObjectifsEtiquettes() {
        return objectifsEtiquettes;
    }

    public void setObjectifsEtiquettes(final Map<String, List<EtiquetteCommonBean>> objectifsEtiquettes) {
        this.objectifsEtiquettes = objectifsEtiquettes;
    }

    public Map<String, List<EtiquetteCommonBean>> getIngenieriesEtiquettes() {
        return ingenieriesEtiquettes;
    }

    public void setIngenieriesEtiquettes(final Map<String, List<EtiquetteCommonBean>> ingenieriesEtiquettes) {
        this.ingenieriesEtiquettes = ingenieriesEtiquettes;
    }
}
