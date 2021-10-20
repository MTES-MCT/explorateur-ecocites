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

public enum QUESTIONNAIRE_CODE {

    QUESTIONNAIRE_ACTION_INGENIERIE("questionnaire_action_ingenierie", "Évaluation des apports de l'étude"),
    QUESTIONNAIRE_ACTION_INVESTISSEMENT("questionnaire_action_investissement", "Évaluation des facteurs de succès"),
    QUESTIONNAIRE_ECOCITE("questionnaire_ecocite", "Évaluation de l'impact du programme Ville de demain sur l'ÉcoCité"),;

    String code;
    String libelle;

    QUESTIONNAIRE_CODE(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public static QUESTIONNAIRE_CODE getByCode(String code) {
        for (QUESTIONNAIRE_CODE enu : QUESTIONNAIRE_CODE.values()) {
            if (enu.code.equals(code))
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

    public static QUESTIONNAIRE_CODE[] getQuestionnaireActions(TYPE_FINANCEMENT typeFinancement) {
        if (typeFinancement == null) {
            return new QUESTIONNAIRE_CODE[0];
        }
        switch (typeFinancement) {

            case INGENIERIE:
                return new QUESTIONNAIRE_CODE[]{QUESTIONNAIRE_ACTION_INGENIERIE};
            case INVESTISSEMENT:
            case PRISE_PARTICIPATION:
                return new QUESTIONNAIRE_CODE[]{QUESTIONNAIRE_ACTION_INVESTISSEMENT};
            case INGENIERIE_INVESTISSEMENT:
            case INGENIERIE_PRISE_PARTICIPATION:
                return new QUESTIONNAIRE_CODE[]{QUESTIONNAIRE_ACTION_INGENIERIE, QUESTIONNAIRE_ACTION_INVESTISSEMENT};
        }
        return new QUESTIONNAIRE_CODE[0];
    }
}
