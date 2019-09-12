/*
 * l'État, ministère chargé du logement
 * Copyright (C) 2019 IpsoSenso
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
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
