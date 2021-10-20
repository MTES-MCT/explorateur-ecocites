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

public enum ECHELLE_ACTION {

    BATIEMENT("batiment","Bâtiment"),
    ILOT("ilot","Îlot"),
    QUARTIER("quartier","Quartier"),
    AGGLOMERATION("agglomeration","Agglomération");

    String code;
    String libelle;

    private ECHELLE_ACTION(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public static ECHELLE_ACTION getByCode(String code)
    {
        for(ECHELLE_ACTION enu : ECHELLE_ACTION.values())
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
