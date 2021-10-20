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

import com.efficacity.explorateurecocites.beans.biz.ActionBean;
import com.efficacity.explorateurecocites.beans.biz.EcociteBean;
import com.efficacity.explorateurecocites.beans.model.Contact;
import com.efficacity.explorateurecocites.beans.service.*;
import com.efficacity.explorateurecocites.configuration.ServiceConfiguration;
import com.efficacity.explorateurecocites.utils.service.email.EnvoieEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ChoixIndicateursNotificationService {
    public static final String ACCOMPAGNATEUR = "Accompagnateur";
    public static final String MESSAGE_BONJOUR = "Bonjour, \n\n";
    public static final String MESSAGE_BIEN_CORDIALEMENT = "Bien cordialement, \n\n";
    public static final String MESSAGE_PS = "PS : Ce mail est automatique, merci de ne pas y répondre. Pour toute question sur l’évaluation ÉcoCité Ville de demain, vous pouvez contacter ";
    public static final String MESSAGE_PS_SUITE = "l’accompagnateur national à l’adresse suivante : Support_Evaluation_EcoCite@technopolis-group.com\n";
    public static final String MESSAGE_PS_LIEN = "Lien vers Explorateur ÉcoCité : https://explorateur.ecocites.logement.gouv.fr/\n";
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

    public void choixIndicateurEnvoyerAction(String userEmail, Long idAction){
        ActionBean action;
        EcociteBean ecocite;
        if(idAction!=null) {
            action = actionService.findOneAction(idAction);
            if (action != null && action.getIdEcocite() != null) {
                ecocite = ecociteService.findOneEcocite(action.getIdEcocite());
                List<Contact> contactsPrincipal;
                List<String> adresses = new LinkedList<>();
                List<String> prenomNom = new LinkedList<>();
                contactsPrincipal = assoObjetContactService.findAllContactPrincipaleForEcocite(action.getIdEcocite());
                for (Contact contact : contactsPrincipal) {
                    adresses.add(contact.getEmail());
                    prenomNom.add(contact.getPrenom() + " " + contact.getNom());
                }
                adresses.add(serviceConfiguration.getEmailAccompagnateur());
                prenomNom.add(ACCOMPAGNATEUR);
                if(!adresses.isEmpty() && ecocite!=null){
                    String messageContact = MESSAGE_BONJOUR +
                            "Le choix d’étiquettes et d’indicateurs pour l’action "+action.getNomPublic()+" de l’ÉcoCité "+ecocite.getNom()+" a été envoyé " +
                            "pour validation.\n\n" +
                            "Vous devez maintenant valider ce choix sur le site Explorateur ÉcoCités afin que les " +
                            "indicateurs de cette action puissent être renseignés.\n\n" +
                            MESSAGE_BIEN_CORDIALEMENT +
                            MESSAGE_PS +
                            MESSAGE_PS_SUITE +
                            MESSAGE_PS_LIEN;
                    String objet = "Évaluation ÉcoCité Ville de demain – Choix étiquettes et indicateurs à valider";
                    envoieEmailService.envoyerEmail(adresses, prenomNom, null, messageContact, objet);
                }
            }
        }
    }
    public void choixIndicateurValiderAction(String userEmail, Long idAction){
        ActionBean action;
        EcociteBean ecocite;
        if(idAction!=null) {
            action = actionService.findOneAction(idAction);
            if (action != null && action.getIdEcocite() != null) {
                ecocite = ecociteService.findOneEcocite(action.getIdEcocite());
                List<Contact> contactsPrincipal;
                List<String> adresses = new LinkedList<>();
                List<String> prenomNom = new LinkedList<>();
                contactsPrincipal = assoObjetContactService.findAllContactPrincipaleForAction(action.getId());
                for (Contact contact : contactsPrincipal) {
                    adresses.add(contact.getEmail());
                    prenomNom.add(contact.getPrenom() + " " + contact.getNom());
                }
                adresses.add(serviceConfiguration.getEmailAccompagnateur());
                prenomNom.add(ACCOMPAGNATEUR);
                if (!adresses.isEmpty() && ecocite!=null) {
                    String messageContact = MESSAGE_BONJOUR +
                            "Le choix d’étiquettes et d’indicateurs pour l’action "+action.getNomPublic() +" de l’ÉcoCité "+ ecocite.getNom() + " a été validé.\n\n" +
                            "Vous pouvez maintenant renseigner des cibles et des mesures pour cette action sur le site Explorateur ÉcoCités.\n\n" +
                            MESSAGE_BIEN_CORDIALEMENT +
                            MESSAGE_PS +
                            MESSAGE_PS_SUITE +
                            MESSAGE_PS_LIEN;
                    String objet = "Évaluation ÉcoCité Ville de demain – Choix étiquettes et indicateurs validé";
                    envoieEmailService.envoyerEmail(adresses, prenomNom, null, messageContact, objet);
                }
            }
        }
    }
    public void choixIndicateurValiderEcocite(String userEmail, Long idEcocite){
        if(idEcocite!=null) {
            EcociteBean ecocite = ecociteService.findOneEcocite(idEcocite);
            if(ecocite!=null) {
                String adresse = serviceConfiguration.getEmailAccompagnateur();
                String prenomNom = ACCOMPAGNATEUR;
                String messageContact = MESSAGE_BONJOUR +
                        "Le choix d’étiquettes et d’indicateurs de l’ÉcoCité " + ecocite.getNom() + " a été validé.\n\n" +
                        "Vous pouvez maintenant renseigner des cibles et des mesures pour cette ÉcoCité sur le site Explorateur ÉcoCités.\n\n" +
                        MESSAGE_BIEN_CORDIALEMENT +
                        MESSAGE_PS +
                        MESSAGE_PS_SUITE +
                        MESSAGE_PS_LIEN;
                String objet = "Évaluation ÉcoCité Ville de demain – Choix étiquettes et indicateurs validé";
                envoieEmailService.envoyerEmailUnique(adresse, prenomNom, null, messageContact, objet);
            }
        }
    }
}
