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

public enum JobStatus {
    PENDING("PENDING", "En attente"),
    RUNNING("RUNNING", "En cours"),
    FINISHED("FINISHED", "Terminé"),
    ERROR("ERROR", "En erreur");

    public final String code;
    public final String libelle;

    JobStatus(final String code, final String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public static JobStatus getByCode(String code) {
        if (code == null) {
            return ERROR;
        }
        switch (code) {
            case "PENDING":
                return PENDING;
            case "RUNNING":
                return RUNNING;
            case "FINISHED":
                return FINISHED;
            case "ERROR":
            default:
                return ERROR;
        }
    }
}
