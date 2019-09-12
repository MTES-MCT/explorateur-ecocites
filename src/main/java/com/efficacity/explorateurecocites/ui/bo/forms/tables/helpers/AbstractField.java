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

package com.efficacity.explorateurecocites.ui.bo.forms.tables.helpers;

import com.efficacity.explorateurecocites.utils.table.InputType;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractField {
    protected String name;
    protected String label;
    protected String value;
    protected Boolean required;
    protected List<SelectOption> values;

    public AbstractField(final String name, final String label, final String value, final Boolean required) {
        this.name = name;
        this.label = label;
        this.value = value;
        this.values = new ArrayList<>();
        this.required = required;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public List<SelectOption> getValues() {
        return values;
    }

    public void setValues(final List<SelectOption> values) {
        this.values = values;
    }

    public abstract InputType getType();

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(final Boolean required) {
        this.required = required;
    }
}
