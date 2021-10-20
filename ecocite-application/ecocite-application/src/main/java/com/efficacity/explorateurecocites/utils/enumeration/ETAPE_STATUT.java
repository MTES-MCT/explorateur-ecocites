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

import java.util.LinkedHashMap;

public enum ETAPE_STATUT {

    VALIDER("valider", "Validé ","glyphicon-check"),
    ENVOYER("envoyer", "Envoyé pour validation","glyphicon-warning"),
    IMPOSSIBLE("impossible", "Non renseignable actuellement","glyphicon-warning"),
    A_RENSEIGNER("vide", "Informations à renseigner","glyphicon-danger");

    String code;
    String libelle;
    String classCss;

    private ETAPE_STATUT(String code, String libelle, String classCss) {
        this.code = code;
        this.libelle = libelle;
        this.classCss = classCss;
    }

    public static ETAPE_STATUT getByCode(String code)
    {
        for(ETAPE_STATUT enu : ETAPE_STATUT.values())
        {
            if(enu.code.equals(code))
                return enu;
        }

        return null;
    }

    public static LinkedHashMap<String, String> toMapSansValidation() {
        LinkedHashMap<String, String> mapAvecValidation = new LinkedHashMap<>();
        mapAvecValidation.put(ETAPE_STATUT.A_RENSEIGNER.getCode(),ETAPE_STATUT.A_RENSEIGNER.getLibelle());
        mapAvecValidation.put(ETAPE_STATUT.VALIDER.getCode(),ETAPE_STATUT.VALIDER.getLibelle());
        return mapAvecValidation;
    }

    public static LinkedHashMap<String, String> toMapAvecValidation() {
        LinkedHashMap<String, String> mapSansValidation = new LinkedHashMap<>();
        mapSansValidation.put(ETAPE_STATUT.A_RENSEIGNER.getCode(),ETAPE_STATUT.A_RENSEIGNER.getLibelle());
        mapSansValidation.put(ETAPE_STATUT.ENVOYER.getCode(),ETAPE_STATUT.ENVOYER.getLibelle());
        mapSansValidation.put(ETAPE_STATUT.VALIDER.getCode(),ETAPE_STATUT.VALIDER.getLibelle());
        return mapSansValidation;
    }

    public String getCode() {
        return code;
    }

    public String getClassCss() {
        return classCss;
    }

    public String getLibelle() {
            return libelle;
        }
}
