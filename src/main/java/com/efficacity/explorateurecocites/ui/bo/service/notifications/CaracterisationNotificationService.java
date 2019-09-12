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
public class CaracterisationNotificationService {
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

    public void caracterisationEnvoyerAction(String userEmail, Long idAction){
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
                prenomNom.add("Accompagnateur");
                if(!adresses.isEmpty() && ecocite!=null){
                    String messageContact = "Bonjour, \n\n" +
                            "Le choix d’étiquettes pour l’action "+action.getNomPublic()+" de l’ÉcoCité "+ecocite.getNom()+" a été envoyé " +
                            "pour validation.\n\n" +
                            "Vous devez maintenant valider ce choix sur le site Explorateur ÉcoCités."+
                            "Bien cordialement, \n\n" +
                            "PS : Ce mail est automatique, merci de ne pas y répondre. Pour toute question sur l’évaluation ÉcoCité Ville de demain, vous pouvez contacter " +
                            "l’accompagnateur national à l’adresse suivante : Support_Evaluation_EcoCite@technopolis-group.com\n" +
                            "Lien vers Explorateur ÉcoCité : https://explorateur.ecocites.logement.gouv.fr/\n";
                    String objet = "Évaluation ÉcoCité Ville de demain – Choix étiquettes à valider";
                    envoieEmailService.envoyerEmail(adresses, prenomNom, null, messageContact, objet);
                }
            }
        }
    }
    public void caracterisationValiderAction(String userEmail, Long idAction){
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
                prenomNom.add("Accompagnateur");
                if (!adresses.isEmpty() && ecocite!=null) {
                    String messageContact = "Bonjour, \n\n" +
                            "Le choix d'étiquettes pour l’action "+action.getNomPublic() +" de l’ÉcoCité "+ ecocite.getNom() + " a été validé.\n\n" +
                            "Bien cordialement, \n\n" +
                            "PS : Ce mail est automatique, merci de ne pas y répondre. Pour toute question sur l’évaluation ÉcoCité Ville de demain, vous pouvez contacter " +
                            "l’accompagnateur national à l’adresse suivante : Support_Evaluation_EcoCite@technopolis-group.com\n" +
                            "Lien vers Explorateur ÉcoCité : https://explorateur.ecocites.logement.gouv.fr/\n";
                    String objet = "Évaluation ÉcoCité Ville de demain – Choix étiquettes validé";
                    envoieEmailService.envoyerEmail(adresses, prenomNom, null, messageContact, objet);
                }
            }
        }
    }
}
