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

public enum ETAPE_ECOCITE {
    CARACTERISATION("categorisation","Caractérisation de l'ÉcoCité", 1),
    INDICATEUR("choix_indicateur","Choix des indicateurs", 2),
    MESURE_INDICATEUR("mesure_indicateur","Renseignement des indicateurs", 3),
    CONTEXTE_ET_FACTEUR("impact_programme","Evaluation des facteurs de succès", 4);

    String code;
    String libelle;
    private Integer order;

    private ETAPE_ECOCITE(String code, String libelle, Integer order) {
        this.code = code;
        this.libelle = libelle;
        this.order = order;
    }

    public static ETAPE_ECOCITE getByCode(String code)
    {
        for(ETAPE_ECOCITE enu : ETAPE_ECOCITE.values())
        {
            if(enu.code.equals(code))
                return enu;
        }

        return null;
    }

    public static ETAPE_ECOCITE[] getListEtapeActionIngenerie(){
        return new ETAPE_ECOCITE[]{CARACTERISATION, INDICATEUR};
    }

    public String getCode() {
        return code;
    }
    public String getLibelle() {
        return libelle;
    }

    public Integer getOrder() {
        return order;
    }
}
