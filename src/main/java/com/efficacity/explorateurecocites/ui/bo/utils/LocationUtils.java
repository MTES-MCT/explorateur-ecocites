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

public class LocationUtils {

    private LocationUtils() {}

    public static Double getCoordinateFromDMS(String latitude) {
        String[] parts = latitude.split("(°|'|\"|&quot;)\\s*");
        if (parts.length == 4 && parts[3].length() == 1) {
            Double d = Double.parseDouble(parts[0]);
            Double m = Double.parseDouble(parts[1]);
            Double s = Double.parseDouble(parts[2]);
            switch (parts[3]) {
                case "S":
                case "W":
                    d = 0 - d;
                    m = 0 - m;
                    s = 0 - s;
                    break;
                case "N":
                case "E":
                default:
                    break;
            }
            return (d + (m / 60) + (s / 3600));
        }
        return null;
    }
}
