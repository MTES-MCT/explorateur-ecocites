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

package com.efficacity.explorateurecocites.ui.bo.utils;

import java.util.stream.Stream;

public class StreamUtils {

    public static boolean verifyStreamSizeBetweenBoundaries(Stream<?> stream, Integer min, Integer max) {
        if (min > max) {
            int tmp = min;
            min = max;
            max = tmp;
        }
        if (max == Integer.MAX_VALUE) {
            switch (min) {
                case 0:
                    return true;
                case 1:
                    return stream.findFirst().isPresent();
                default:
                    return stream.limit(min).count() >= min;
            }
        } else {
            switch (min) {
                case 0:
                    return stream.limit(max + 1L).count() <= max;
                default:
                    Long nb = stream.limit(max + 1L).count();
                    return nb >= min && nb <= max;
            }
        }
    }
}
