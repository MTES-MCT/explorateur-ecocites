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

public enum ETAPE_ECOCITE_EDITION {
    CATEGORIESATION("categorisation","Catégorisation de l'ÉcoCité"),
    INDICATEUR("choix_indicateur","Choix de mes indicateurs "),
    INDICATEUR_REALISATION("indicateurOngletRealisation","Indicateurs de réalisation"),
    INDICATEUR_RESULTAT_IMPACT("indicateurOngletResultat","Indicateurs de résultat et d'impact"),
    MESURE_INDICATEUR("mesure_indicateur","Renseignement de mes indicateurs"),
    CONTEXTE_ET_FACTEUR("impact_programme","Evaluation des facteurs de succès");

    String code;
    String libelle;

    private ETAPE_ECOCITE_EDITION(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public static ETAPE_ECOCITE_EDITION getByCode(String code)
    {
        for(ETAPE_ECOCITE_EDITION enu : ETAPE_ECOCITE_EDITION.values())
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
}
