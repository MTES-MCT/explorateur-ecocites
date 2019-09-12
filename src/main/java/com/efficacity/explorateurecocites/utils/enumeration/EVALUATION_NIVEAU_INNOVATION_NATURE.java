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

package com.efficacity.explorateurecocites.utils.enumeration;

import java.util.*;
import java.util.stream.Collectors;

public enum EVALUATION_NIVEAU_INNOVATION_NATURE {

    NON_CONCERNE(0, "Non concerné", null, 1),
    NIVEAU_TROIS(3, "Niveau 3", "Réplication d’une innovation déjà répandue, mais dans un contexte différent.", 2),
    NIVEAU_DEUX(2, "Niveau 2", "Réplication d’une innovation peu répandue ou somme de petites innovations encore jamais combinées ensemble.", 3),
    NIVEAU_UN(1, "Niveau 1", "Première nationale.", 4);

    private final Integer value;
    private final String title;
    private final String description;
    private final Integer order;

    EVALUATION_NIVEAU_INNOVATION_NATURE(final Integer value, final String title, final String description, final Integer order) {
        this.value = value;
        this.title = title;
        this.description = description;
        this.order = order;
    }

    public static EVALUATION_NIVEAU_INNOVATION_NATURE getByValue(Integer code)
    {
        return Arrays.stream(EVALUATION_NIVEAU_INNOVATION_NATURE.values())
                .filter(enu -> enu.value.equals(code))
                .findFirst()
                .orElse(null);
    }

    public static Map<Integer, List<String>> getValueContentMap()
    {
        return Arrays.stream(EVALUATION_NIVEAU_INNOVATION_NATURE.values())
                .sorted(Comparator.comparing(o -> o.order))
                .collect(Collectors.toMap(EVALUATION_NIVEAU_INNOVATION_NATURE::getValue,
                        enu -> {
                            List<String> list = new ArrayList<>();
                            list.add(enu.getTitle());
                            list.add(enu.getDescription());
                            return list;
                        },
                        (u,v) -> { throw new IllegalStateException(String.format("Duplicate key %s", u)); },
                        LinkedHashMap::new));
    }

    public int getValue() {
        return value;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
