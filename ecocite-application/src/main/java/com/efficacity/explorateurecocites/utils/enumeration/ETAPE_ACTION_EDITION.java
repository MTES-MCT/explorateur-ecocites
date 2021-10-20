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

public enum ETAPE_ACTION_EDITION {
    CATEGORIESATION("categorisation","Catégorisation de l'action"),
    CATEGORIESATION_INGENIERIE("ingenierie", "Type de mission d'ingénierie"),
    CATEGORIESATION_DOMAINE("domaine", "Domaines d'action"),
    CATEGORIESATION_OBJECTIF("objectif", "Objectifs de la ville durable"),
    INDICATEUR("choix_indicateur","Choix de mes indicateurs"),
    INDICATEUR_REALISATION("indicateurOngletRealisation","Indicateurs de réalisation"),
    INDICATEUR_RESULTAT_IMPACT("indicateurOngletResultat","Indicateurs de résultat et d'impact"),
    EVALUATION_INNOVATION("evaluation_innovation","Evaluation de l'innovation "),
    MESURE_INDICATEUR("mesure_indicateur","Renseignement de mes indicateurs"),
    CONTEXTE_ET_FACTEUR("contexte_et_facteur","Evaluation des facteurs de succès "),
    CONTEXTE_ET_FACTEUR_INGENIERIE("contexte_et_facteur_ingenierie","Evaluation des facteurs de succès d'ingénierie"),
    CONTEXTE_ET_FACTEUR_INVESTISSEMENT("contexte_et_facteur_investissement","Evaluation des facteurs de succès d'investissement"),
    ;

    String code;
    String libelle;

    ETAPE_ACTION_EDITION(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public static ETAPE_ACTION_EDITION[] getListEtapeActionIngenerie(){
        ETAPE_ACTION_EDITION tab[] = {ETAPE_ACTION_EDITION.CATEGORIESATION,
                ETAPE_ACTION_EDITION.CATEGORIESATION_INGENIERIE,
                ETAPE_ACTION_EDITION.CATEGORIESATION_DOMAINE,
                ETAPE_ACTION_EDITION.CATEGORIESATION_OBJECTIF,
                ETAPE_ACTION_EDITION.CONTEXTE_ET_FACTEUR,
                ETAPE_ACTION_EDITION.CONTEXTE_ET_FACTEUR_INGENIERIE};
        return tab;
    }

    public static ETAPE_ACTION_EDITION[] getListEtapeActionInvestissement(){
        ETAPE_ACTION_EDITION tab[] = {ETAPE_ACTION_EDITION.CATEGORIESATION,
                ETAPE_ACTION_EDITION.CATEGORIESATION_DOMAINE,
                ETAPE_ACTION_EDITION.CATEGORIESATION_OBJECTIF,
                ETAPE_ACTION_EDITION.INDICATEUR,
                ETAPE_ACTION_EDITION.INDICATEUR_REALISATION,
                ETAPE_ACTION_EDITION.INDICATEUR_RESULTAT_IMPACT,
                ETAPE_ACTION_EDITION.EVALUATION_INNOVATION,
                ETAPE_ACTION_EDITION.MESURE_INDICATEUR,
                ETAPE_ACTION_EDITION.CONTEXTE_ET_FACTEUR,
                ETAPE_ACTION_EDITION.CONTEXTE_ET_FACTEUR_INVESTISSEMENT};
        return tab;
    }

    public static ETAPE_ACTION_EDITION[] getListEtapeActionIngenerieEtInvestissement(){
        ETAPE_ACTION_EDITION tab[] = {ETAPE_ACTION_EDITION.CATEGORIESATION,
                ETAPE_ACTION_EDITION.CATEGORIESATION_INGENIERIE,
                ETAPE_ACTION_EDITION.CATEGORIESATION_DOMAINE,
                ETAPE_ACTION_EDITION.CATEGORIESATION_OBJECTIF,
                ETAPE_ACTION_EDITION.INDICATEUR,
                ETAPE_ACTION_EDITION.INDICATEUR_REALISATION,
                ETAPE_ACTION_EDITION.INDICATEUR_RESULTAT_IMPACT,
                ETAPE_ACTION_EDITION.EVALUATION_INNOVATION,
                ETAPE_ACTION_EDITION.MESURE_INDICATEUR,
                ETAPE_ACTION_EDITION.CONTEXTE_ET_FACTEUR,
                ETAPE_ACTION_EDITION.CONTEXTE_ET_FACTEUR_INGENIERIE,
                ETAPE_ACTION_EDITION.CONTEXTE_ET_FACTEUR_INVESTISSEMENT};
        return tab;
    }

    public static ETAPE_ACTION_EDITION getByCode(String code)
    {
        for(ETAPE_ACTION_EDITION enu : ETAPE_ACTION_EDITION.values())
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
