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
public class InnovationNotificationService {
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

    public void innovationEnvoyerAction(Long idAction) {
        if (idAction != null) {
            ActionBean action = actionService.findOneAction(idAction);
            if (action != null) {
                EcociteBean ecocite = ecociteService.findOneEcocite(action.getIdEcocite());
                if (ecocite != null) {
                    String adresse = serviceConfiguration.getEmailAccompagnateur();
                    String prenomNom = "Accompagnateur";
                    String objet = "Évaluation ÉcoCité Ville de demain – Évaluation de l’innovation à valider";
                    String messageContact = "Bonjour,\n\nL’évaluation de l’innovation pour l’action " + action.getNomPublic() + " de l’ÉcoCité " + ecocite.getNom() + " a été envoyée pour validation.\n\n" +
                            "Vous devez maintenant valider ce choix et ajouter un niveau d’innovation global sur le site Explorateur ÉcoCités.\n\n" +
                            "Bien cordialement, \n\n" +
                            "PS : Ce mail est automatique, merci de ne pas y répondre. Pour toute question sur l’évaluation " +
                            "ÉcoCité Ville de demain, vous pouvez contacter l’accompagnateur national à l’adresse suivante : " +
                            "Support_Evaluation_EcoCite@technopolis-group.com\n" +
                            "Lien vers Explorateur ÉcoCité : https://explorateur.ecocites.logement.gouv.fr/\n";
                    envoieEmailService.envoyerEmailUnique(adresse, prenomNom, null, messageContact, objet);
                }
            }
        }
    }

    public void innovationValiderAction(Long idAction) {
        if (idAction != null) {
            ActionBean action = actionService.findOneAction(idAction);
            if (action != null) {
                EcociteBean ecocite = ecociteService.findOneEcocite(action.getIdEcocite());
                if (ecocite != null) {
                    List<Contact> contactsPrincipalEcocite;
                    List<Contact> contactsPrincipalAction;
                    List<String> adresses = new LinkedList<>();
                    List<String> prenomNom = new LinkedList<>();
                    contactsPrincipalEcocite = assoObjetContactService.findAllContactPrincipaleForEcocite(action.getIdEcocite());
                    contactsPrincipalAction = assoObjetContactService.findAllContactPrincipaleForAction(action.getId());
                    sendEmail(action, ecocite, contactsPrincipalEcocite, contactsPrincipalAction, adresses, prenomNom);
                }
            }
        }

    }

    /**
     * Construction et envoi de l'email.
     *
     * @param action                   action
     * @param ecocite                  ecocitee
     * @param contactsPrincipalEcocite liste de contacts ecocites
     * @param contactsPrincipalAction  liste de contacts actions
     * @param adresses                 Liste d'adresses
     * @param prenomNom                Liste des prénoms et noms
     */
    private void sendEmail(ActionBean action, EcociteBean ecocite, List<Contact> contactsPrincipalEcocite, List<Contact> contactsPrincipalAction, List<String> adresses, List<String> prenomNom) {
        for (Contact contact : contactsPrincipalEcocite) {
            adresses.add(contact.getEmail());
            prenomNom.add(contact.getPrenom() + " " + contact.getNom());
        }
        for (Contact contact : contactsPrincipalAction) {
            adresses.add(contact.getEmail());
            prenomNom.add(contact.getPrenom() + " " + contact.getNom());
        }
        if (!adresses.isEmpty()) {
            String objet = "Évaluation ÉcoCité Ville de demain – Évaluation de l’innovation validée";
            String messageContact = "Bonjour,\n\nL’évaluation de l’innovation pour l’action " + action.getNomPublic() + " de l’ÉcoCité " + ecocite.getNom() + " a été validée.\n\n" +
                    "Vous pouvez consulter cette évaluation sur le site Explorateur ÉcoCités.\n\n" +
                    "Bien cordialement, \n\n" +
                    "PS : Ce mail est automatique, merci de ne pas y répondre. Pour toute question sur l’évaluation " +
                    "ÉcoCité Ville de demain, vous pouvez contacter l’accompagnateur national à l’adresse suivante : " +
                    "Support_Evaluation_EcoCite@technopolis-group.com\n" +
                    "Lien vers Explorateur ÉcoCité : https://explorateur.ecocites.logement.gouv.fr/\n";
            envoieEmailService.envoyerEmail(adresses, prenomNom, null, messageContact, objet);
        }
    }

}
