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

package com.efficacity.explorateurecocites.ui.bo.utils;

import java.util.List;


public class ErrorAdaptor {
	private String code;
    private List<FieldErrorAdaptor> fieldErrors;

    public ErrorAdaptor(String code) {
        this.code = code;
    }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public List<FieldErrorAdaptor> getFieldErrors() { return fieldErrors; }

    public void setFieldErrors(List<FieldErrorAdaptor> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
