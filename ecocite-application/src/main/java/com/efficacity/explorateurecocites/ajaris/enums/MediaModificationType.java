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
