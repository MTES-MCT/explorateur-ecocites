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

package com.efficacity.explorateurecocites.utils.enumeration.mesure;

import java.util.Optional;

public enum LIKERT_ACCORD {
    TOTAL_DESACCORD(0, "Pas du tout d'accord"),
    DESACCORD(1, "Pas d'accord"),
    NEUTRE(2, "Ni en désaccord ni d'accord"),
    ACCORD(3, "D'accord"),
    TOTAL_ACCORD(4, "Tout à fait d'accord");

    private final Integer value;
    private final String message;

    LIKERT_ACCORD(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public static LIKERT_ACCORD getByValue(Integer value) {
        switch (value) {
            case 0:
                return TOTAL_DESACCORD;
            case 1:
                return DESACCORD;
            case 2:
                return NEUTRE;
            case 3:
                return ACCORD;
            case 4:
                return TOTAL_ACCORD;
        }
        return null;
    }

    public static String getLibelleByValue(Integer value) {
        return Optional.ofNullable(getByValue(value)).map(LIKERT_ACCORD::getMessage).orElse("Inconnu");
    }
}
