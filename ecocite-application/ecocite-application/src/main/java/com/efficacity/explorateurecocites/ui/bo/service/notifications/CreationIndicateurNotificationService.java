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

package com.efficacity.explorateurecocites.ui.bo.service.notifications;


import com.efficacity.explorateurecocites.beans.Enums;
import com.efficacity.explorateurecocites.beans.biz.IndicateurBean;
import com.efficacity.explorateurecocites.beans.service.ActionService;
import com.efficacity.explorateurecocites.beans.service.AssoObjetContactService;
import com.efficacity.explorateurecocites.beans.service.ContactService;
import com.efficacity.explorateurecocites.beans.service.EcociteService;
import com.efficacity.explorateurecocites.beans.service.IndicateurService;
import com.efficacity.explorateurecocites.configuration.ServiceConfiguration;
import com.efficacity.explorateurecocites.utils.service.email.EnvoieEmailService;
import isotope.commons.exceptions.ForbiddenException;
import isotope.modules.security.JwtUser;
import isotope.modules.user.lightbeans.RoleLightBean;
import isotope.modules.user.lightbeans.UserLightbean;
import isotope.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Objects;
import java.util.Set;

@Service
public class CreationIndicateurNotificationService {
    @Autowired
    ServiceConfiguration serviceConfiguration;
    @Autowired
    AssoObjetContactService assoObjetContactService;
    @Autowired
    ContactService contactService;
    @Autowired
    EnvoieEmailService envoieEmailService;
    @Autowired
    EcociteService ecociteService;
    @Autowired
    ActionService actionService;
    @Autowired
    IndicateurService indicateurService;
    @Autowired
    UserService userService;

    public void creationIndicateurEnvoyer(Model model, Long idIndicateur) {
        if (idIndicateur != null) {
            IndicateurBean indicateur = indicateurService.findOneIndicateur(idIndicateur);
            JwtUser user = (JwtUser) model.asMap().getOrDefault("user", null);
            Enums.ProfilsUtilisateur profil = Enums.ProfilsUtilisateur.getByCode((String) model.asMap().getOrDefault("userProfileCode", null));
            if (user == null || profil == null) {
                throw new ForbiddenException();
            }
            if (indicateur != null) {
                String adresse = serviceConfiguration.getEmailAccompagnateur();
                String prenomNom = "Accompagnateur";
                String messageContact = "Bonjour, \n\n"
                        + user.getFirstname() + " " + user.getLastname() + " a créé un nouvel indicateur intitulé " + indicateur.getNomCourt() + ".\n\n" +
                        "Vous devez le valider sur le site Explorateur ÉcoCités afin qu’il soit ajouté à la bibliothèque d’indicateurs.\n\n" +
                        "Bien cordialement,\n\n" +
                        "PS : Ce mail est automatique, merci de ne pas y répondre. Pour toute question sur l’évaluation " +
                        "ÉcoCité Ville de demain, vous pouvez contacter l’accompagnateur national à l’adresse suivante : " +
                        "Support_Evaluation_EcoCite@technopolis-group.com\n" +
                        "Lien vers Explorateur ÉcoCité : https://explorateur.ecocites.logement.gouv.fr/\n";
                String objet = "Évaluation ÉcoCité Ville de demain – Indicateur créé";
                envoieEmailService.envoyerEmailUnique(adresse, prenomNom, null, messageContact, objet);
            }
        }
    }

    public void creationIndicateurValider(Model model, Long idIndicateur) {
        if (idIndicateur != null) {
            IndicateurBean indicateur = indicateurService.findOneIndicateur(idIndicateur);
            JwtUser user = (JwtUser) model.asMap().getOrDefault("user", null);
            Enums.ProfilsUtilisateur profil = Enums.ProfilsUtilisateur.getByCode((String) model.asMap().getOrDefault("userProfileCode", null));
            if (user == null || profil == null) {
                throw new ForbiddenException();
            }
            if (indicateur != null) {
                String adresse = indicateur.getUserCreation();
                if (adresse != null) {
                    UserLightbean userCreator = userService.getUserByEmail(adresse).orElse(null);
                    if (userCreator != null) {
                        boolean sendEmail = isSendEmail(userCreator);
                        sendMail(sendEmail, indicateur, adresse, userCreator);
                    }
                }
            }
        }
    }

    /**
     * Test si un mail doit être envoyé.
     *
     * @param userCreator
     * @return true si un mail doit être envoyé
     */
    private boolean isSendEmail(UserLightbean userCreator) {

        Set<RoleLightBean> roles = userCreator.getRoles();
        boolean sendEmail = false;
        for (RoleLightBean role : roles) {
            Enums.ProfilsUtilisateur profilCreator = Enums.ProfilsUtilisateur.getByCode(role.getCode());
            if (Objects.equals(profilCreator, Enums.ProfilsUtilisateur.PORTEUR_ACTION) || Objects.equals(profilCreator, Enums.ProfilsUtilisateur.REFERENT_ECOCITE)) {
                sendEmail = true;
            }
        }
        return sendEmail;
    }

    /**
     * Envoi de l'email.
     *
     * @param sendEmail   true si le mail doit être envoyé
     * @param indicateur  indicateur
     * @param adresse     adresse
     * @param userCreator user createur
     */
    private void sendMail(Boolean sendEmail, IndicateurBean indicateur, String adresse, UserLightbean userCreator) {

        if (Boolean.TRUE.equals(sendEmail)) {
            String prenomNom = userCreator.getFirstname() + " " + userCreator.getLastname();
            String messageContact = "Bonjour,\n\n" +
                    "L'indicateur intitulé " + indicateur.getNomCourt() + " a été validé. " +
                    "Il a été ajouté à la bibliothèque d’indicateurs du site Explorateur ÉcoCités.\n\n" +
                    "Bien cordialement,\n\n" +
                    "PS : Ce mail est automatique, merci de ne pas y répondre. Pour toute question sur l’évaluation " +
                    "ÉcoCité Ville de demain, vous pouvez contacter l’accompagnateur national à l’adresse suivante : " +
                    "Support_Evaluation_EcoCite@technopolis-group.com\n" +
                    "Lien vers Explorateur ÉcoCité : https://explorateur.ecocites.logement.gouv.fr/\n";
            String objet = "Évaluation ÉcoCité Ville de demain – Indicateur validé";
            envoieEmailService.envoyerEmailUnique(adresse, prenomNom, null, messageContact, objet);
        }
    }
}
