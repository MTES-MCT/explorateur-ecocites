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
public enum NON_OUI {
    NON(0, "Non"),
    OUI(1, "Oui");

    private final Integer value;
    private final String message;

    NON_OUI(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public static NON_OUI getByValue(Integer value) {
        switch (value) {
            case 0:
                return NON;
            case 1:
                return OUI;
        }
        return null;
    }

    public static String getLibelleByValue(Integer value) {
        return Optional.ofNullable(getByValue(value)).map(NON_OUI::getMessage).orElse("Inconnu");
    }
}
