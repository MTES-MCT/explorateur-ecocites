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

public enum QUESTIONNAIRE_CATEGORIE_CODE {

    CAT_1_1_1("1.1.1","Rappel du besoin et des objectifs de l'étude",null),
    CAT_1_1_2("1.1.2","Résultats principaux de l’étude",null),
    CAT_1_1_3("1.1.3","Difficultés rencontrées dans la mise en œuvre de l’étude",null),
    CAT_1_1_4("1.1.4","Enseignements sur les méthodes de travail",null),
    CAT_1_1_5("1.1.5","Eléments reproductibles de l’action d’ingénierie",null),
    CAT_1_1_6("1.1.6","Apport et suites données à l’action d’ingénierie",null),
    CAT_1_1_7("1.1.7","Importer une note de synthèse","Vous pouvez importer ici une note de synthèse ou tout autre document disponible (rapport d'évaluation, rapport d'audit...) pour compléter votre évaluation qualitative. Ce document ne sera téléchargeable que par vous, votre référent ÉcoCité ou les acteurs institutionnels de l'ÉcoCité (CDC, DREAL...) et les accompagnateurs et administrateurs."),

    CAT_2_1_1("2.1.1","Qualité des partenariats & gouvernance",null),
    CAT_2_1_2("2.1.2","Satisfaction du public",null),
    CAT_2_1_3("2.1.3","Rôle du contexte local",null),
    CAT_2_1_4("2.1.4","Evolution des usages et des pratiques",null),
    CAT_2_1_5("2.1.5","Spécificités règlementaires",null),
    CAT_2_1_6("2.1.6","Modèle économique et financier",null),
    CAT_2_1_7("2.1.7","Qualité scientifique et technique",null),
    CAT_2_1_8("2.1.8","Importer une note de synthèse","Vous pouvez importer ici une note de synthèse ou tout autre document disponible (rapport d'évaluation, rapport d'audit...) pour compléter votre évaluation qualitative. Ce document ne sera téléchargeable que par vous, votre référent ÉcoCité ou les acteurs institutionnels de l'ÉcoCité (CDC, DREAL...) et les accompagnateurs et administrateurs."),

    CAT_3_1_1("3.1.1","Positionnement de votre ÉcoCité dans le programme Ville de Demain","Les actions financées par le PIA Ville de demain ont été sélectionnées pour leur haut niveau d’innovation ou de performance."),
    CAT_3_1_2("3.1.2","Evolution de votre stratégie territoriale","La démarche ÉcoCité a pour but d’encourager une approche intégrée du développement urbain durable."),
    CAT_3_1_3("3.1.3","Généralisation et réplication d’actions Ville de demain","Le programme Ville de demain a aidé à financer des actions à fort potentiel d’innovation ou à forte performance, des démonstrateurs, qui idéalement pourraient être répliqués (déployées ailleurs) et massifiés."),
    CAT_3_1_4("3.1.4","Evolution de votre gouvernance","La démarche ÉcoCité, de par son ambition d’encourager des projets urbains intégrés, a souvent incité différents services des Collectivités à dialoguer et à coopérer."),
    CAT_3_1_5("3.1.5","Rayonnement des programmes ÉcoCité - Ville de demain",null),
    CAT_3_1_6("3.1.6","Importer une note de synthèse","Vous pouvez importer ici une note de synthèse ou tout autre document disponible (rapport d'évaluation, rapport d'audit...) pour compléter votre évaluation qualitative. Ce document ne sera téléchargeable que par vous, votre référent ÉcoCité ou les acteurs institutionnels de l'ÉcoCité (CDC, DREAL...) et les accompagnateurs et administrateurs."),
    ;

    String code;
    String libelle;
    String description;

    private QUESTIONNAIRE_CATEGORIE_CODE(String code, String libelle, String description) {
        this.code = code;
        this.libelle = libelle;
        this.description = description;
    }

    public static QUESTIONNAIRE_CATEGORIE_CODE getByCode(String code)
    {
        for(QUESTIONNAIRE_CATEGORIE_CODE enu : QUESTIONNAIRE_CATEGORIE_CODE.values())
        {
            if(enu.code.equals(code))
                return enu;
        }

        return null;
    }

    public static QUESTIONNAIRE_CATEGORIE_CODE[] valuesByCodeQuestionnaire(String codeQuestionnaire){

        switch (QUESTIONNAIRE_CODE.getByCode(codeQuestionnaire)) {
            case QUESTIONNAIRE_ACTION_INGENIERIE:
                return valuesIngenierie();
            case QUESTIONNAIRE_ACTION_INVESTISSEMENT:
                return valuesInvestissementOuPriseParticipation();
            case QUESTIONNAIRE_ECOCITE:
                return valuesEcocite();
        }

        return null;
    }

    public static QUESTIONNAIRE_CATEGORIE_CODE[] valuesIngenierie(){
        QUESTIONNAIRE_CATEGORIE_CODE tab[] = {QUESTIONNAIRE_CATEGORIE_CODE.CAT_1_1_1,
                QUESTIONNAIRE_CATEGORIE_CODE.CAT_1_1_2,
                QUESTIONNAIRE_CATEGORIE_CODE.CAT_1_1_3,
                QUESTIONNAIRE_CATEGORIE_CODE.CAT_1_1_4,
                QUESTIONNAIRE_CATEGORIE_CODE.CAT_1_1_5,
                QUESTIONNAIRE_CATEGORIE_CODE.CAT_1_1_6,
                QUESTIONNAIRE_CATEGORIE_CODE.CAT_1_1_7};
        return tab;
    }

    public static QUESTIONNAIRE_CATEGORIE_CODE[] valuesInvestissementOuPriseParticipation(){
        QUESTIONNAIRE_CATEGORIE_CODE tab[] = {QUESTIONNAIRE_CATEGORIE_CODE.CAT_2_1_1,
                QUESTIONNAIRE_CATEGORIE_CODE.CAT_2_1_2,
                QUESTIONNAIRE_CATEGORIE_CODE.CAT_2_1_3,
                QUESTIONNAIRE_CATEGORIE_CODE.CAT_2_1_4,
                QUESTIONNAIRE_CATEGORIE_CODE.CAT_2_1_5,
                QUESTIONNAIRE_CATEGORIE_CODE.CAT_2_1_6,
                QUESTIONNAIRE_CATEGORIE_CODE.CAT_2_1_7,
                QUESTIONNAIRE_CATEGORIE_CODE.CAT_2_1_8};
        return tab;
    }

    public static QUESTIONNAIRE_CATEGORIE_CODE[] valuesEcocite(){
        QUESTIONNAIRE_CATEGORIE_CODE tab[] = {QUESTIONNAIRE_CATEGORIE_CODE.CAT_3_1_1,
                QUESTIONNAIRE_CATEGORIE_CODE.CAT_3_1_2,
                QUESTIONNAIRE_CATEGORIE_CODE.CAT_3_1_3,
                QUESTIONNAIRE_CATEGORIE_CODE.CAT_3_1_4,
                QUESTIONNAIRE_CATEGORIE_CODE.CAT_3_1_5,
                QUESTIONNAIRE_CATEGORIE_CODE.CAT_3_1_6};
        return tab;
    }

    public String getCode() {
        return code;
    }
    public String getLibelle() {
            return libelle;
        }

}
