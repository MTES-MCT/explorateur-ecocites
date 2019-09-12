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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum STATUT_FINANCIER_AFFAIRE {

    EN_COURS_PAIEMENT("en_cours_paiement","En cours de paiement"),
    SOLDE(new String[] { "solde", "soldee" },"Soldé"),
    ENGAGE(new String[] { "engage", "engagee" },"Engagé"),
    CLOTURE(new String[] { "cloture", "cloturee" },"Clôturé"),
    CONTRACTUALISE(new String[] { "contractualise", "contractualisee" },"Contractualisée"),
    ;

    String[] code;
    String libelle;

    private STATUT_FINANCIER_AFFAIRE(String code, String libelle) {
        this.code = new String[] { code };
        this.libelle = libelle;
    }

    private STATUT_FINANCIER_AFFAIRE(String[] code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public static STATUT_FINANCIER_AFFAIRE getByCode(String code)
    {
        for(STATUT_FINANCIER_AFFAIRE enu : STATUT_FINANCIER_AFFAIRE.values())
        {
            for (String c : enu.code) {
                if(c.equals(code)) {
                    return enu;
                }
            }
        }

        return null;
    }

    public static Map<String, String> toMap() {
        return Stream.of(values())
                .collect(Collectors.toMap(STATUT_FINANCIER_AFFAIRE::getFirstCode, STATUT_FINANCIER_AFFAIRE::getLibelle,
                (k1, k2) -> k1,
                LinkedHashMap::new));
    }

    public String[] getCode() {
        return code;
    }

    public String getFirstCode() {
        if (code.length > 0) {
            return code[0];
        }
        return "";
    }

    public String getLibelle() {
            return libelle;
        }

    public static String getLibelleForCode(String code) {
        return Optional.ofNullable(getByCode(code)).map(STATUT_FINANCIER_AFFAIRE::getLibelle).orElse(code);
    }
}
