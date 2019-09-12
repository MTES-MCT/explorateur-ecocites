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

package com.efficacity.explorateurecocites.ajaris.enums;

public enum MediaModificationType {
    UPDATE("UPDATE", "Mise à jour"),
    CLONE("CLONE", "Duplication"),
    DELETE("DELETE", "Suppression"),
    UNKNOWN("UNKNOWN", "Inconnue");

    public final String code;
    public final String libelle;

    MediaModificationType(final String code, final String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public static MediaModificationType getByCode(String code) {
        if (code == null) {
            return UNKNOWN;
        }
        switch (code) {
            case "UPDATE":
                return UPDATE;
            case "CLONE":
                return CLONE;
            case "DELETE":
                return DELETE;
            case "UNKNOWN":
            default:
                return UNKNOWN;
        }
    }
}
