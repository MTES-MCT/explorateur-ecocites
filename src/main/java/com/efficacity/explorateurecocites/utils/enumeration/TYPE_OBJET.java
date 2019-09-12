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

import com.efficacity.explorateurecocites.ajaris.enums.MediaModificationOriginType;

public enum TYPE_OBJET {

    ACTION("action","Action"),
    ECOCITE("ecocite","ÉcoCité"),
    INDICATEUR("indicateur","Indicateur"),
    AXE("axe","Axe");

    String code;
    String libelle;

    TYPE_OBJET(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public static TYPE_OBJET getByCode(String code)
    {
        for(TYPE_OBJET enu : TYPE_OBJET.values())
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

    public String getFolderPath() {
        switch (this) {
            case ACTION:
                return "actions";
            case ECOCITE:
                return "ecocites";
            case AXE:
                return "axes";
            default:
                return "unknown";
        }
    }

    public MediaModificationOriginType getMMOT() {
        switch (this) {
            case ACTION:
                return MediaModificationOriginType.ACTION;
            case ECOCITE:
                return MediaModificationOriginType.ECOCITE;
            case AXE:
                return MediaModificationOriginType.AXE;
            default:
                return MediaModificationOriginType.OTHER;
        }
    }
}
