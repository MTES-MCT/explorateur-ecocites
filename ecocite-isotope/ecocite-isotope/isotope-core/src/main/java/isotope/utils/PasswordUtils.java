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

package isotope.utils;

import org.springframework.stereotype.Component;

/**
 * Created by qletel on 19/05/2016.
 */
@Component
public class PasswordUtils {

    public boolean checkPassword(String encryptedPassword, String plainPassword) {
        if ((encryptedPassword == null) || (plainPassword == null) || (encryptedPassword.length() < 4) || (encryptedPassword.charAt(0) != '{')) {
            // bizarrerie. On décide que ca ne passe pas.
            throw new IllegalArgumentException("Le contenu d'un des mots de passe n'est pas correct. clair='" + plainPassword + "', crypté='" + encryptedPassword + "'");
        }

        // Récupérer l'algorithme
        int braceEnd = encryptedPassword.indexOf('}');
        if (braceEnd == -1) {
            // Pas d'accolade fermante. Erreur ...
            return false;
        }

        boolean pass = false;
        String algorithm = encryptedPassword.substring(1, braceEnd);
        /*
        if (ALGO_BCRYPT.equals(algorithm)) {
            pass = BCrypt.checkpw(plainPassword, encryptedPassword.substring(braceEnd + 1, encryptedPassword.length()));
        } else {
            pass = encryptedPassword.equals(crypt(plainPassword, algorithm));
        }
        */

        return pass;
    }

}
