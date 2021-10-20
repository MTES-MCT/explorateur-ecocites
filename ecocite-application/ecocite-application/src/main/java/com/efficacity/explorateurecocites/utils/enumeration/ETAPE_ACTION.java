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

public enum ETAPE_ACTION {
    CARACTERISATION("categorisation","Caractérisation de l'action", 1),
    INDICATEUR("choix_indicateur","Choix des indicateurs", 2),
    EVALUATION_INNOVATION("evaluation_innovation","Evaluation de l'innovation", 3),
    MESURE_INDICATEUR("mesure_indicateur","Renseignement des indicateurs", 4),
    CONTEXTE_ET_FACTEUR("contexte_et_facteur","Evaluation des facteurs de succès", 5);

    String code;
    String libelle;
    private Integer order;

    private ETAPE_ACTION(String code, String libelle, Integer order) {
        this.code = code;
        this.libelle = libelle;
        this.order = order;
    }

    public static ETAPE_ACTION getByCode(String code)
    {
        for(ETAPE_ACTION enu : ETAPE_ACTION.values())
        {
            if(enu.code.equals(code)) {
                return enu;
            }
        }

        return null;
    }

    public static ETAPE_ACTION[] getListEtapeActionIngenerie(){
        return new ETAPE_ACTION[]{CARACTERISATION, CONTEXTE_ET_FACTEUR};
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
