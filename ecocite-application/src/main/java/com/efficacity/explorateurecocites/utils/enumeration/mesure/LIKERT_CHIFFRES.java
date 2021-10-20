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

package com.efficacity.explorateurecocites.utils.enumeration.mesure;

import java.util.Optional;

public enum LIKERT_CHIFFRES {
    TOTAL_DESACCORD(0, "1"),
    DESACCORD(1, "2"),
    NEUTRE(2, "3"),
    ACCORD(3, "4"),
    TOTAL_ACCORD(4, "5");

    private final Integer value;
    private final String message;

    LIKERT_CHIFFRES(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public static LIKERT_CHIFFRES getByValue(Integer value) {
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
        return Optional.ofNullable(getByValue(value)).map(LIKERT_CHIFFRES::getMessage).orElse("Inconnu");
    }
}
