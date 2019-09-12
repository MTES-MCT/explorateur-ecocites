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

import com.efficacity.explorateurecocites.utils.enumeration.mesure.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public enum TYPE_MESURE {
    NOMBRE_DEC_O("NOMBRE_DEC_O","Nombre décimal >= 0"),
    NOMBRE_DEC("NOMBRE_DEC","Nombre décimal"),
    NOMBRE_DEC_0_1("NOMBRE_DEC_0_1","Nombre décimal entre 0 et 1"),
    OUI_NON("OUI_NON","Oui / Non"),
    ECHELLE_LICKERT_ACCORD("ECHELLE_LICKERT_ACCORD","Echelle qualitative « d’accord/pas d'accord »"),
    ECHELLE_LICKERT_BENEFICE("ECHELLE_LICKERT_BENEFICE","Echelle qualitative « bénéfices »"),
    ECHELLE_LICKERT_EXCELLENT("ECHELLE_LICKERT_EXCELLENT","Echelle qualitative « excellent »"),
    ECHELLE_LICKERT_CHIFFRES_SEULS("ECHELLE_LICKERT_CHIFFRES_SEULS","Echelle qualitative 1 à 5");

    String code;
    String libelle;

    TYPE_MESURE(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public static TYPE_MESURE getByCode(String code)
    {
        for(TYPE_MESURE enu : TYPE_MESURE.values())
        {
            if(enu.code.equals(code))
                return enu;
        }

        return null;
    }

    public String getCode() {
        return code;
    }
    public String getLibelle() {
            return libelle;
        }

    public boolean shouldPrintUnit() {
        return this.equals(NOMBRE_DEC) ||
                this.equals(NOMBRE_DEC_0_1) ||
                this.equals(NOMBRE_DEC_O);
    }

    public static String getLibelleByCode(final String code) {
        TYPE_MESURE typeMesure = getByCode(code);
        return typeMesure != null ? typeMesure.getLibelle() : code;
    }

    public Map<String, String> getMapOptions() {
        switch (this) {
            case OUI_NON:
                return Arrays.stream(NON_OUI.values()).collect(Collectors.toMap(a -> String.valueOf(a.getValue()), NON_OUI::getMessage));
            case ECHELLE_LICKERT_ACCORD:
                return Arrays.stream(LIKERT_ACCORD.values()).collect(Collectors.toMap(a -> String.valueOf(a.getValue()), LIKERT_ACCORD::getMessage));
            case ECHELLE_LICKERT_BENEFICE:
                return Arrays.stream(LIKERT_BENEFICE.values()).collect(Collectors.toMap(a -> String.valueOf(a.getValue()), LIKERT_BENEFICE::getMessage));
            case ECHELLE_LICKERT_EXCELLENT:
                return Arrays.stream(LIKERT_EXCELLENT.values()).collect(Collectors.toMap(a -> String.valueOf(a.getValue()), LIKERT_EXCELLENT::getMessage));
            case ECHELLE_LICKERT_CHIFFRES_SEULS:
                return Arrays.stream(LIKERT_CHIFFRES.values()).collect(Collectors.toMap(a -> String.valueOf(a.getValue()), LIKERT_CHIFFRES::getMessage));
        }
        return new HashMap<>();
    }
}
