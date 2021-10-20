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

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum TYPE_FINANCEMENT{

    INGENIERIE("ingenierie","Ingénierie"),
    INVESTISSEMENT("investissement","Investissement"),
    PRISE_PARTICIPATION("prise_participation","Prise de participation"),
    INGENIERIE_INVESTISSEMENT("ingenierie_investissement","Ingénierie et Investissement"),
    INGENIERIE_PRISE_PARTICIPATION("ingenierie_prise_participation","Ingénierie et Prise de participation"),;

    String code;
    String libelle;

    TYPE_FINANCEMENT(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public static TYPE_FINANCEMENT getByCode(String code)
    {
        for(TYPE_FINANCEMENT enu : TYPE_FINANCEMENT.values())
        {
            if(enu.code.equals(code))
                return enu;
        }

        return null;
    }

    public static LinkedHashMap<String, String> toMap() {
        return Stream.of(values())
                .collect(Collectors.toMap(TYPE_FINANCEMENT::getCode, TYPE_FINANCEMENT::getLibelle,
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
        return Optional.ofNullable(getByCode(code)).map(TYPE_FINANCEMENT::getLibelle).orElse(code);
    }


    public static final List<String> ingenierieTypes = Arrays.asList(INGENIERIE.getCode(), TYPE_FINANCEMENT.INGENIERIE_INVESTISSEMENT.getCode(), TYPE_FINANCEMENT.INGENIERIE_PRISE_PARTICIPATION.getCode());
    public static final List<String> investissementTypes  = Arrays.asList(INVESTISSEMENT.getCode(), TYPE_FINANCEMENT.INGENIERIE_INVESTISSEMENT.getCode(), TYPE_FINANCEMENT.PRISE_PARTICIPATION.getCode(), TYPE_FINANCEMENT.INGENIERIE_PRISE_PARTICIPATION.getCode());
}
