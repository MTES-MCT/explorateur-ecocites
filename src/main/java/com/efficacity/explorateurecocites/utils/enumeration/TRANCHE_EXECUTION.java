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

import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum TRANCHE_EXECUTION {

    TRANCHE_1("tranche_1","Tranche 1"),
    TRANCHE_2("tranche_2","Tranche 2"),
    TRANCHE_1_et_2("tranche_1_2","Tranche 1 et 2");

    String code;
    String libelle;

    private TRANCHE_EXECUTION(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public static TRANCHE_EXECUTION getByCode(String code)
    {
        for(TRANCHE_EXECUTION enu : TRANCHE_EXECUTION.values())
        {
            if(enu.code.equals(code))
                return enu;
        }

        return null;
    }

    public static LinkedHashMap<String, String> toMap() {
        return Stream.of(values())
                .collect(Collectors.toMap(TRANCHE_EXECUTION::getCode, TRANCHE_EXECUTION::getLibelle,
                        (k1, k2) -> k1,
                        LinkedHashMap::new));
    }

    public String getCode() {
        return code;
    }
    public String getLibelle() {
        return libelle;
    }

    public static String getLibelleForCode(String code) {
        return Optional.ofNullable(getByCode(code)).map(TRANCHE_EXECUTION::getLibelle).orElse(code);
    }
}
