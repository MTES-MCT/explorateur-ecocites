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

import java.util.ArrayList;
import java.util.List;

public class ObjectAsMap {
    private String id;

    private List<AbstractField> fields;

    public ObjectAsMap() {
        id = "";
        fields = new ArrayList<>();
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public List<AbstractField> getFields() {
        return fields;
    }

    public void setFields(final List<AbstractField> fields) {
        this.fields = fields;
    }
}
