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
