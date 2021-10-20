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

public enum LIBELLE_FO_CODE {
    TITRE_PRINCIPAL("TITRE_PRINCIPAL"),
    SOUS_TITRE_PRINCIPAL("SOUS_TITRE_PRINCIPAL"),
    TITRE_ECOCITE("TITRE_ECOCITE"),
    SOUS_TITRE_ECOCITE("SOUS_TITRE_ECOCITE"),
    TITRE_ACTION("TITRE_ACTION"),
    SOUS_TITRE_ACTION("SOUS_TITRE_ACTION"),
    COPYRIGHT("COPYRIGHT"),
    TITRE_MINISTERE("TITRE_MINISTERE"),
    JAVASCRIPT_ANALYTICS("JAVASCRIPT_ANALYTICS"),
    EM_TITRE_SECTION("EM_TITRE_SECTION"),
    EM_DESCRIPTION_SECTION("EM_DESCRIPTION_SECTION"),
    EM_MONTANT_PIA_VDD("EM_MONTANT_PIA_VDD"),
    EM_NOMBRE_ACTION_VISIBLE("EM_NOMBRE_ACTION_VISIBLE"),
    EM_NOMBRE_ACTION_REALISE("EM_NOMBRE_ACTION_REALISE"),
    EM_NOMBRE_ECOCITE("EM_NOMBRE_ECOCITE"),
    EM_NOMBRE_ACTION_EVALUE("EM_NOMBRE_ACTION_EVALUE");

    public final String code;

    LIBELLE_FO_CODE(final String code) {
        this.code = code;
    }

    public static LIBELLE_FO_CODE getByCode(String code)
    {
        for(LIBELLE_FO_CODE enu : LIBELLE_FO_CODE.values())
        {
            if(enu.code.equals(code))
                return enu;
        }

        return LIBELLE_FO_CODE.EM_TITRE_SECTION;
    }
}
