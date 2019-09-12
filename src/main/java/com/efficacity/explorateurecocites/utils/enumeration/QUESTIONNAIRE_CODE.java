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
