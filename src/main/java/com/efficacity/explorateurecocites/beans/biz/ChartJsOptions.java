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
