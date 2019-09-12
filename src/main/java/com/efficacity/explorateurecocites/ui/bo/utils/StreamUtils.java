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
