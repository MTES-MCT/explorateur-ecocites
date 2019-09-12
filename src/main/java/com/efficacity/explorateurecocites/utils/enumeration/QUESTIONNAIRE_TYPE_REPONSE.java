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

public enum QUESTIONNAIRE_TYPE_REPONSE {

    TEXTE_LIBRE("texte_libre"),
    RADIO_BOUTON("radio_bouton"),
    RADIO_BOUTON_AUTRE("radio_bouton_autre"),
    RADIO_BOUTON_OUI_NON_NONCONCERNE("radio_bouton_oui_non_nonconcerne"),
    CHECKBOX_AUTRE("checkbox_autre"),
    CHECKBOX_TEXTE_LIBRE("checkbox_texte_libre"),
    FILE("file");

    String code;

    QUESTIONNAIRE_TYPE_REPONSE(String code) {
        this.code = code;
    }

    public static QUESTIONNAIRE_TYPE_REPONSE getByCode(String code)
    {
        for(QUESTIONNAIRE_TYPE_REPONSE enu : QUESTIONNAIRE_TYPE_REPONSE.values())
        {
            if(enu.code.equals(code))
                return enu;
        }

        return null;
    }

    public String getCode() {
        return code;
    }

}
