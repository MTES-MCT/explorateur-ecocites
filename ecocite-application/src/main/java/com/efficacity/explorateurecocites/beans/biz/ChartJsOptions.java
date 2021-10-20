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
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ChartJsOptions {
    public static final String EMPTY_MODEL_JSON_STRING = "{\"dataSets\":[], \"labelX\":[]}";

    private List<GraphDataSet> datasets;
    private Map<Integer, String> labelY;
    private List<String> labelX;

    public ChartJsOptions(final List<GraphDataSet> dataSets, final List<String> labelX, final Map<String, String> labelY) {
        this.datasets = dataSets;
        this.labelY = labelY.entrySet()
                .stream()
                .collect(Collectors.toMap(a -> Integer.parseInt(a.getKey()), Map.Entry::getValue, (a, b) -> b, TreeMap::new));
        this.labelX = labelX;
    }

    public ChartJsOptions(final List<GraphDataSet> dataSets, final List<String> labelX) {
        this.datasets = dataSets;
        this.labelY = new HashMap<>();
        labelY.put(5, "test");
        labelY.put(10, "more test");
        this.labelX = labelX;
    }

    public List<GraphDataSet> getDatasets() {
        return datasets;
    }

    public void setDatasets(final List<GraphDataSet> datasets) {
        this.datasets = datasets;
    }

    public Map<Integer, String> getLabelY() {
        return labelY;
    }

    public void setLabelY(final Map<Integer, String> labelY) {
        this.labelY = labelY;
    }

    public List<String> getLabelX() {
        return labelX;
    }

    public void setLabelX(final List<String> labelX) {
        this.labelX = labelX;
    }
}
