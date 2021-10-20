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

package com.efficacity.explorateurecocites.utils.enumeration;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum EVALUATION_NIVEAU_INNOVATION_GLOBAL {

    VIDE(0, ""),
    NIVEAU_UN(1, "Niveau 1"),
    NIVEAU_TROIS(3, "Niveau 3"),
    NIVEAU_DEUX(2, "Niveau 2");

    Integer value;
    String content;

    EVALUATION_NIVEAU_INNOVATION_GLOBAL(final Integer value, final String content) {
        this.value = value;
        this.content = content;
    }

    public static EVALUATION_NIVEAU_INNOVATION_GLOBAL getByValue(Integer code)
    {
        return Arrays.stream(EVALUATION_NIVEAU_INNOVATION_GLOBAL.values())
                .filter(enu -> enu.value.equals(code))
                .findFirst()
                .orElse(null);
    }

    public static Map<Integer, String> getValueContentMap()
    {
        return Arrays.stream(EVALUATION_NIVEAU_INNOVATION_GLOBAL.values())
                .collect(Collectors.toMap(EVALUATION_NIVEAU_INNOVATION_GLOBAL::getValue, EVALUATION_NIVEAU_INNOVATION_GLOBAL::getContent));
    }

    public int getValue() {
        return value;
    }

    public String getContent() {
        return content;
    }
}
