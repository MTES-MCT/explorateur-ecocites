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

public enum CODE_FUNCTION_PROFILE {

    // Peut editer
    EDIT_PRESENTATION_ACTION("edit_presentation_action"),
    EDIT_CARACTERISATION_ACTION("edit_categorisation_action"),
    EDIT_INDICATEUR_ACTION("edit_choix_indi_action"),
    EDIT_EVALUATION_INNOVATION_ACTION("edit_evaluation_action"),
    EDIT_MESURE_INDICATEUR_ACTION("edit_mesure_action"),
    EDIT_CONTEXTE_ET_FACTEUR_ACTION("edit_contexte_action"),
    EDIT_PRESENTATION_ECOCITE("edit_presentation_ecocite"),
    EDIT_CARACTERISATION_ECOCITE("edit_categorisation_ecocite"),
    EDIT_INDICATEUR_ECOCITE("edit_choix_indi_ecocite"),
    EDIT_MESURE_INDICATEUR_ECOCITE("edit_mesure_ecocite"),
    EDIT_CONTEXTE_ET_FACTEUR_ECOCITE("edit_impact_ecocite"),

    // Peut valider
    VALIDATE_CARACTERISATION_ACTION("val_categorisation_action"),
    VALIDATE_INDICATEUR_ACTION("val_choix_indi_action"),
    VALIDATE_EVALUATION_INNOVATION_ACTION("val_evaluation_action"),
    VALIDATE_MESURE_INDICATEUR_ACTION("val_mesure_action"),
    VALIDATE_CONTEXTE_ET_FACTEUR_ACTION("val_contexte_action"),
    VALIDATE_CARACTERISATION_ECOCITE("val_categorisation_ecocite"),
    VALIDATE_INDICATEUR_ECOCITE("val_choix_indi_ecocite"),
    VALIDATE_MESURE_INDICATEUR_ECOCITE("val_mesure_ecocite"),
    VALIDATE_CONTEXTE_ET_FACTEUR_ECOCITE("val_impact_ecocite"),

    // Peut annuler une validation
    MODIF_CARACTERISATION_ACTION("modif_categorisation_action"),
    MODIF_INDICATEUR_ACTION("modif_choix_indi_action"),
    MODIF_EVALUATION_INNOVATION_ACTION("modif_evaluation_action"),
    MODIF_MESURE_INDICATEUR_ACTION("modif_mesure_action"),
    MODIF_CONTEXTE_ET_FACTEUR_ACTION("modif_contexte_action"),
    MODIF_CARACTERISATION_ECOCITE("modif_categorisation_ecocite"),
    MODIF_INDICATEUR_ECOCITE("modif_choix_indi_ecocite"),
    MODIF_MESURE_INDICATEUR_ECOCITE("modif_mesure_ecocite"),
    MODIF_CONTEXTE_ET_FACTEUR_ECOCITE("modif_impact_ecocite"),


    EDIT_INDICATEUR("edit_indicateur"),
    CREATION_INDICATEUR("creation_indicateur"),
    SUPPRESSION_INDICATEUR("suppression_indicateur"),

    EDIT_CONTACT("edit_contact"),
    CREATION_CONTACT("creation_contact"),
    SUPPRESSION_CONTACT("suppression_contact"),
    ;

    String code;
    String libelle;

    private CODE_FUNCTION_PROFILE(String code) {
        this.code = code;
    }

    public static CODE_FUNCTION_PROFILE getByCode(String code)
    {
        for(CODE_FUNCTION_PROFILE enu : CODE_FUNCTION_PROFILE.values())
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
