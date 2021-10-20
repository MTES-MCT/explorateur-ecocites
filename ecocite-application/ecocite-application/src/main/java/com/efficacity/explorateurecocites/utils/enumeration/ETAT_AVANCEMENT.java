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

public enum ETAT_AVANCEMENT {

    PREVU("prevu","Prévu"),
    EN_COURS("en_cours","En cours"),
    REALISE("realise","Réalisé"),
    ABANDONNE("abandonne","Abandonné");

    String code;
    String libelle;

    private ETAT_AVANCEMENT(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public static ETAT_AVANCEMENT getByCode(String code)
    {
        for(ETAT_AVANCEMENT enu : ETAT_AVANCEMENT.values())
        {
            if(enu.code.equals(code)){
                return enu;
            }
        }

        return null;
    }

    public static LinkedHashMap<String, String> toMap() {
        return Stream.of(values())
                .collect(Collectors.toMap(ETAT_AVANCEMENT::getCode, ETAT_AVANCEMENT::getLibelle,
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
