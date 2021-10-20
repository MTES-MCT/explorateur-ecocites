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

import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ECHELLE_INDICATEUR {

    SPECIFIQUE("specifique","Spécifique"),
    TERRITORIALE("territoriale","Territorial");

    String code;
    String libelle;

    ECHELLE_INDICATEUR(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public static ECHELLE_INDICATEUR getByCode(String code)
    {
        for(ECHELLE_INDICATEUR enu : ECHELLE_INDICATEUR.values())
        {
            if(enu.code.equals(code))
                return enu;
        }

        return null;
    }

    public static String getLibelleByCode(String code) {
        ECHELLE_INDICATEUR echelle = getByCode(code);
        return echelle != null ? echelle.getLibelle() : code;
    }

    public static LinkedHashMap<String, String> toMap() {
        return Stream.of(values())
                .collect(Collectors.toMap(ECHELLE_INDICATEUR::getCode, ECHELLE_INDICATEUR::getLibelle,
                        (k1, k2) -> k1,
                        LinkedHashMap::new));
    }

    public String getCode() {
        return code;
    }
    public String getLibelle() {
            return libelle;
        }

}
