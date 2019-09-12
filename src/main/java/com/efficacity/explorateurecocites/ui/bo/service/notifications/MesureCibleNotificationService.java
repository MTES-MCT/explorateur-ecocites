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
import com.efficacity.explorateurecocites.beans.model.Ecocite;
import com.efficacity.explorateurecocites.beans.service.*;
import com.efficacity.explorateurecocites.configuration.ServiceConfiguration;
import com.efficacity.explorateurecocites.utils.service.email.EnvoieEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MesureCibleNotificationService {
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

    public void ajoutMesureCibleAction(String userEmail, Long idAction) {
        if(idAction!=null) {
            ActionBean action = actionService.findOneAction(idAction);
            if (action != null) {
                EcociteBean ecocite = ecociteService.findOneEcocite(action.getIdEcocite());
                if (ecocite != null) {
                    String adresse = serviceConfiguration.getEmailAccompagnateur();
                    String prenomNom = "Accompagnateur";
                    String objet = "Évaluation ÉcoCité Ville de demain – Ajout Mesure ou Cible";
                    String messageContact = "Bonjour, \n\n" +
                            "Une mesure ou une cible a été ajoutée pour l’action " + action.getNomPublic() + " de l’ÉcoCité "+ecocite.getNom()+".\n" +
                            "\n" +
                            "Vous pouvez consulter cette valeur sur le site Explorateur ÉcoCités\n" +
                            "\n" +
                            "Bien cordialement, \n" +
                            "\n" +
                            "PS : Ce mail est automatique, merci de ne pas y répondre. Pour toute question sur l’évaluation ÉcoCité Ville de demain, vous pouvez " +
                            "contacter l’accompagnateur national à l’adresse suivante : Support_Evaluation_EcoCite@technopolis-group.com\n" +
                            "Lien vers Explorateur ÉcoCité : https://explorateur.ecocites.logement.gouv.fr/\n";
                    envoieEmailService.envoyerEmailUnique(adresse, prenomNom, null, messageContact, objet);
                }
            }
        }
    }
    public void ajoutMesureCibleEcocite(String userEmail, Long idEcocite) {
        if(idEcocite!=null) {
            EcociteBean ecocite = ecociteService.findOneEcocite(idEcocite);
            if (ecocite != null) {
                String adresse = serviceConfiguration.getEmailAccompagnateur();
                String prenomNom = "Accompagnateur";
                String objet = "Évaluation ÉcoCité Ville de demain – Ajout Mesure ou Cible";
                String messageContact = "Bonjour, \n\n" +
                        "Une mesure ou une cible a été ajoutée à l’ÉcoCité "+ecocite.getNom()+".\n" +
                        "\n" +
                        "Vous pouvez consulter cette valeur sur le site Explorateur ÉcoCités\n" +
                        "\n" +
                        "Bien cordialement, \n" +
                        "\n" +
                        "PS : Ce mail est automatique, merci de ne pas y répondre. Pour toute question sur l’évaluation ÉcoCité Ville de demain, vous pouvez " +
                        "contacter l’accompagnateur national à l’adresse suivante : Support_Évaluation_EcoCite@technopolis-group.com\n" +
                        "Lien vers Explorateur ÉcoCité : https://explorateur.ecocites.logement.gouv.fr/\n";
                envoieEmailService.envoyerEmailUnique(adresse, prenomNom, null, messageContact, objet);
            }
        }
    }
}
