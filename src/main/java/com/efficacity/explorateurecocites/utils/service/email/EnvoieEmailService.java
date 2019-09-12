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

package com.efficacity.explorateurecocites.utils.service.email;

import com.efficacity.explorateurecocites.configuration.ServiceConfiguration;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by ktoomey on 19/03/2018.
 */

@Service
public class EnvoieEmailService {

    @Value("${efficacity.explorateurecocites.email.filter:}")
    private String[] filter;

    @Autowired
    ServiceConfiguration serviceConfiguration;

    private static final Logger LOGGER = LoggerFactory.getLogger(EnvoieEmailService.class);

    public void envoyerEmail(List<String> adresses, List<String> prenomNom, String emailExpediteur, String message, String messageSubject) {
        int index = 0;
        for (String adresse : adresses) {
            if (adresse != null) {
                if (adresses.size() == prenomNom.size()) {
                    if (prenomNom.get(index) != null) {
                        envoyerEmailUnique(adresse, prenomNom.get(index), emailExpediteur, message, messageSubject);
                    } else {
                        envoyerEmailUnique(adresse, "", emailExpediteur, message, messageSubject);
                    }
                } else {
                    envoyerEmailUnique(adresse, "", emailExpediteur, message, messageSubject);
                }
                index = +1;
            }
        }
    }

    public void envoyerEmailUnique(String adresse, String prenomNom, String emailExpediteur, String message, String messageSubject) {
        if (adresse != null) {
            try {
                SimpleEmail mailer = new SimpleEmail();
                mailer.setCharset(org.apache.commons.mail.EmailConstants.UTF_8);
                mailer.setHostName(serviceConfiguration.getServerStmp());
                if (emailExpediteur != null && emailExpediteur.length() > 0) {
                    mailer.setFrom(emailExpediteur);
                } else {
                    mailer.setFrom(serviceConfiguration.getEmailExpediteur(), serviceConfiguration.getEmailNameExpediteur());
                }
                mailer.setSubject(Objects.requireNonNullElse(messageSubject, "pas d'objet"));
                mailer.addTo(adresse, Objects.requireNonNullElse(prenomNom, ""));
                mailer.setMsg(Objects.requireNonNullElse(message, "Le message est vide."));
                if (shouldSendEmail(adresse)) {
                    LOGGER.info("Un email a ete envoye a " + adresse + " avec pour objet :  " + messageSubject);
                    mailer.send();
                } else {
                    LOGGER.info("Un email aurait du (filtre) etre envoye a " + adresse + " avec pour objet :  " + messageSubject);
                }
            } catch (EmailException e) {
                LOGGER.error("L'email n'a pas ete envoye !", e);
            }
        }
    }

    private Boolean shouldSendEmail(final String addr) {
        if (filter != null && filter.length > 0) {
            return Arrays.stream(filter).anyMatch(addr::contains);
        }
        return true;
    }
}
