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

public enum LIBELLE_FO_TYPE {
    FO_STATIC("fo_static"),
    FO_DYNAMIC("fo_dynamic");

    public final String code;

    LIBELLE_FO_TYPE(final String code) {
        this.code = code;
    }

    public static LIBELLE_FO_TYPE getByCode(String code)
    {
        for(LIBELLE_FO_TYPE enu : LIBELLE_FO_TYPE.values())
        {
            if(enu.code.equals(code))
                return enu;
        }

        return LIBELLE_FO_TYPE.FO_STATIC;
    }
}
