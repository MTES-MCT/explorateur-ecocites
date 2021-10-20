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
