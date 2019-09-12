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

package com.efficacity.explorateurecocites.beans;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by tfossurier on 13/02/2018.
 */
public class Enums {
    public enum ProfilsUtilisateur {
        VISITEUR_PUBLIC ("VISITEUR_PUBLIC", "Visiteur tout public", 1L, 1L),
        PORTEUR_ACTION ("PORTEUR_ACTION", "Porteur d'action", 3L, 10L),
        REFERENT_ECOCITE ("REFERENT_ECOCITE", "Référent ÉcoCité", 4L, 100L),
        ACTEUR_ECOCITE_AUTRE ("ACTEUR_ECOCITE_AUTRE", "Acteur ÉcoCité autre", 2L, 1000L),
        ACCOMPAGNATEUR ("ACCOMPAGNATEUR", "Accompagnateur", 5L, 10000L),
        ADMIN ("ADMINISTRATEUR", "Administrateur", 6L, 100000L),
        ADMIN_TECH ("ADMIN_TECH", "Administrateur Technique", 8L, 1000000L)
        ;

        private String code;
        private String libelle;
        private Long id;
        private Long order;

        ProfilsUtilisateur(String code, String libelle, Long id, Long order) {
            this.code = code;
            this.libelle = libelle;
            this.id = id;
            this.order = order;
        }

        public String getLibelle() {
            return libelle;
        }
        public String getCode() {
            return code;
        }
        public Long getId() {
            return id;
        }

        public static ProfilsUtilisateur getById(Long id) {
            for(ProfilsUtilisateur e : values()) {
                if(e.id.equals(id)){
                    return e;
                }
            }
            return VISITEUR_PUBLIC;
        }

        public static ProfilsUtilisateur getByCode(String code) {
            for(ProfilsUtilisateur e : values()) {
                if(e.code.equals(code)){
                    return e;
                }
            }
            return VISITEUR_PUBLIC;
        }

        public static boolean isAdmin (String code){
           if(ADMIN.code.equals(code)){
               return true;
           }
            return false;
        }

        public Long getOrder() {
            return order;
        }
    }

    /**
     * L'ensemble des entités qu'on peut mettre à jour par la méthode générale {@code saveAttribut}
     * @see com.efficacity.explorateurecocites.ui.bo.controllers.SaveAttributController
     */
    public enum MODEL_SAVE_ATTRIBUTE {
        ACTION("Action"),
        REPONSE_EVALUATION("ReponseEvaluation"),
        ECOCITE("Ecocite"),
        ASSO_INDICATEUR_OBJET("AssoIndicateurObjet");

        private String libelle;

        MODEL_SAVE_ATTRIBUTE(String libelle) {
            this.libelle = libelle;
        }

        public static Optional<MODEL_SAVE_ATTRIBUTE> getByLibelleIgnoreCase(String libelle) {
            return Stream.of(values()).filter(e -> e.libelle.equalsIgnoreCase(libelle)).findFirst();
        }
    }
}
