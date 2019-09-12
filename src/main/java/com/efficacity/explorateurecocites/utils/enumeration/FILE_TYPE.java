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

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

public enum FILE_TYPE {
    ACTION_DOCUMENT("action_document", TYPE_OBJET.ACTION, "documents"),
    ACTION_IMAGE_PRINCIPALE("action_image_principale", TYPE_OBJET.ACTION, "img/principale"),
    ACTION_IMAGE_SECONDAIRE("action_image_secondaire", TYPE_OBJET.ACTION, "img/secondaires"),
    ACTION_PERIMETRE("action_perimetre", TYPE_OBJET.ACTION, "perimetre"),
    ECOCITE_DOCUMENT("ecocite_document", TYPE_OBJET.ECOCITE, "documents"),
    ECOCITE_IMAGE_PRINCIPALE("ecocite_image_principale", TYPE_OBJET.ECOCITE, "img/principale"),
    ECOCITE_IMAGE_SECONDAIRE("ecocite_image_secondaire", TYPE_OBJET.ECOCITE, "img/secondaires"),
    ECOCITE_PERIMETRE_STRATEGIQUE("ecocite_perimetre_strategique", TYPE_OBJET.ECOCITE, "perimetre/strategique"),
    ECOCITE_PERIMETRE_OPERATIONNEL("ecocite_perimetre_operationnel", TYPE_OBJET.ECOCITE, "perimetre/operationnel"),
    QUESTIONNAIRE_ACTION_SYNTHESE("questionnaire_action_synthese", TYPE_OBJET.ACTION, "questionnaire/syntheses"),
    QUESTIONNAIRE_ECOCITE_SYNTHESE("questionnaire_ecocite_synthese", TYPE_OBJET.ECOCITE, "questionnaire/syntheses"),
    AXE_ICON("axe_icon", TYPE_OBJET.AXE, "icon");

    private String code;
    private TYPE_OBJET typeObjet;
    private String subFolderPath;

    FILE_TYPE(final String code, final TYPE_OBJET typeObjet, final String subFolderPath) {
        this.code = code;
        this.typeObjet = typeObjet;
        String[] folders = subFolderPath.split("/");
        if (folders.length > 0) {
            this.subFolderPath = Paths.get(folders[0], Arrays.copyOfRange(folders, 1, folders.length)).toString();
        }
    }

    public String getSubFolderPath() {
        return subFolderPath;
    }

    public String getFolderPath() {
        if (typeObjet == null) {
            return "unknown";
        }
        return typeObjet.getFolderPath();
    }

    public String getCode() {
        return code;
    }

    public static FILE_TYPE getByCode(String code) {
        for (FILE_TYPE type : values()) {
            if (Objects.equals(type.code, code)) {
                return type;
            }
        }
        return null;
    }

    public TYPE_OBJET getTypeObjet() {
        return typeObjet;
    }
}
