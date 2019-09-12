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

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class GraphDataSet {
    private String label;
    private List<Float> data;
    private List<String> backgroundColor;
    private List<String> borderColor;
    private Integer borderWidth;
    private Boolean spanGaps;
    private Boolean fill;

    public GraphDataSet(final String label, final Float value, final String colorCode) {
        this.label = label;
        this.data = new ArrayList<>();
        data.add(value);
        Color color;
        try {
            color = Color.decode(colorCode.trim());
        } catch (NumberFormatException | NullPointerException e) {
            color = Color.BLACK;
        }
        this.backgroundColor = new ArrayList<>();
        this.backgroundColor.add(String.format(Locale.US, "rgba(%d, %d, %d, %.1f)", color.getRed(), color.getGreen(), color.getBlue(), 1f));
        this.borderColor = new ArrayList<>();
        this.borderColor.add(String.format(Locale.US, "rgba(%d, %d, %d, %d)", color.getRed(), color.getGreen(), color.getBlue(), 1));
        this.borderWidth = 1;
        this.spanGaps = true;
        this.fill = true;
    }

    public GraphDataSet(final String label, final List<String> values, final Color color) {
        this.label = label;
        this.data = values.stream().map(a -> {
            try {
                return Float.parseFloat(a);
            } catch (NumberFormatException e) {
                return null;
            }
        }).collect(Collectors.toList());
        this.backgroundColor = new ArrayList<>();
        this.backgroundColor.add(String.format(Locale.US, "rgba(%d, %d, %d, %.1f)", color.getRed(), color.getGreen(), color.getBlue(), 1f));
        this.borderColor = new ArrayList<>();
        this.borderColor.add(String.format(Locale.US, "rgba(%d, %d, %d, %d)", color.getRed(), color.getGreen(), color.getBlue(), 1));
        this.borderWidth = 2;
        this.spanGaps = true;
        this.fill = false;
    }

    public <R> GraphDataSet(final String cibles, final R collect, final Color color) {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public List<Float> getData() {
        return data;
    }

    public void setData(final List<Float> data) {
        this.data = data;
    }

    public List<String> getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(final List<String> backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public List<String> getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(final List<String> borderColor) {
        this.borderColor = borderColor;
    }

    public GraphDataSet merge(GraphDataSet g) {
        if (data != null && data.size() > 0) {
            if (g.data != null && g.data.size() > 0) {
                data.set(0, g.data.get(0) + data.get(0));
            }
        } else {
            data = g.data;
        }
        return this;
    }

    public Integer getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(final Integer borderWidth) {
        this.borderWidth = borderWidth;
    }

    public Boolean getSpanGaps() {
        return spanGaps;
    }

    public void setSpanGaps(final Boolean spanGaps) {
        this.spanGaps = spanGaps;
    }

    public Boolean getFill() {
        return fill;
    }

    public void setFill(final Boolean fill) {
        this.fill = fill;
    }
}
