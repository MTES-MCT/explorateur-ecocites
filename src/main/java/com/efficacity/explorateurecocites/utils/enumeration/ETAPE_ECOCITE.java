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
