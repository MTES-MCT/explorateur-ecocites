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

import com.efficacity.explorateurecocites.beans.Enums;
import com.efficacity.explorateurecocites.beans.biz.ActionBean;
import com.efficacity.explorateurecocites.beans.biz.EcociteBean;
import com.efficacity.explorateurecocites.beans.biz.IndicateurBean;
import com.efficacity.explorateurecocites.beans.service.*;
import com.efficacity.explorateurecocites.configuration.ServiceConfiguration;
import com.efficacity.explorateurecocites.ui.bo.service.notifications.ChoixIndicateursNotificationService;
import com.efficacity.explorateurecocites.ui.bo.service.notifications.CreationIndicateurNotificationService;
import com.efficacity.explorateurecocites.utils.enumeration.*;
import com.efficacity.explorateurecocites.utils.service.email.EnvoieEmailService;
import isotope.commons.exceptions.ForbiddenException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import isotope.modules.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Service
public class EmailNotificationService {
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
    ChoixIndicateursNotificationService choixIndicateursNotificationService;
    @Autowired
    CreationIndicateurNotificationService creationIndicateurNotificationService;
    @Autowired
    InnovationNotificationService innovationNotificationService;
    @Autowired
    QuestionnaireNotificationService questionnaireNotificationService;
    @Autowired
    MesureCibleNotificationService mesureCibleNotificationService;
    @Autowired
    CaracterisationNotificationService caracterisationNotificationService;

    public void creationIndicateurNotification(Model model,  Long idIndicateur){
        IndicateurBean indicateur = indicateurService.findOneIndicateur(idIndicateur);
        JwtUser user = (JwtUser) model.asMap().getOrDefault("user", null);
        Enums.ProfilsUtilisateur profil = Enums.ProfilsUtilisateur.getByCode((String) model.asMap().getOrDefault("userProfileCode", null));
        if (user == null || profil == null) {
            throw new ForbiddenException();
        }
        if(Objects.equals(indicateur.getEtatValidation(), ETAT_VALIDATION.NON_VALIDE.getCode())) {
            if (Enums.ProfilsUtilisateur.PORTEUR_ACTION.equals(profil) || Enums.ProfilsUtilisateur.REFERENT_ECOCITE.equals(profil)) {
                creationIndicateurNotificationService.creationIndicateurEnvoyer(model, idIndicateur);
            }
        }
        else if(Objects.equals(indicateur.getEtatValidation(), ETAT_VALIDATION.VALIDE.getCode())) {
            if (Enums.ProfilsUtilisateur.ACCOMPAGNATEUR.equals(profil)) {
                creationIndicateurNotificationService.creationIndicateurValider(model, idIndicateur);
            }
        }
    }

    public void sendNotificationEmailEtapeAction(Model model, ETAPE_ACTION etape, Long idAction){
            ActionBean action = actionService.findOneAction(idAction);
            JwtUser user = (JwtUser) model.asMap().getOrDefault("user", null);
            Enums.ProfilsUtilisateur profil = Enums.ProfilsUtilisateur.getByCode((String) model.asMap().getOrDefault("userProfileCode", null));
            if (user == null || profil == null) {
                throw new ForbiddenException();
            }
            String userEmail = user.getEmail();
            if(ETAPE_ACTION.CARACTERISATION.equals(etape)) {
                if(Objects.equals(action.getEtapeByStatus(ETAPE_ACTION.CARACTERISATION).getStatut(), ETAPE_STATUT.ENVOYER.getCode())) {
                    if (Enums.ProfilsUtilisateur.PORTEUR_ACTION.equals(profil)) {
                        caracterisationNotificationService.caracterisationEnvoyerAction(userEmail, idAction);
                    }
                }
                else if(Objects.equals(action.getEtapeByStatus(ETAPE_ACTION.CARACTERISATION).getStatut(), ETAPE_STATUT.VALIDER.getCode())) {
                        caracterisationNotificationService.caracterisationValiderAction(userEmail, idAction);
                }
            }
            if(ETAPE_ACTION.INDICATEUR.equals(etape)) {
                if(Objects.equals(action.getEtapeByStatus(ETAPE_ACTION.INDICATEUR).getStatut(), ETAPE_STATUT.ENVOYER.getCode())) {
                    if (Enums.ProfilsUtilisateur.PORTEUR_ACTION.equals(profil)) {
                        choixIndicateursNotificationService.choixIndicateurEnvoyerAction(userEmail, idAction);
                    }
                }
                else if(Objects.equals(action.getEtapeByStatus(ETAPE_ACTION.INDICATEUR).getStatut(), ETAPE_STATUT.VALIDER.getCode())) {
                    choixIndicateursNotificationService.choixIndicateurValiderAction(userEmail, idAction);
                }
            }
            else if(ETAPE_ACTION.EVALUATION_INNOVATION.equals(etape)) {
                if(Objects.equals(action.getEtapeByStatus(ETAPE_ACTION.EVALUATION_INNOVATION).getStatut(), ETAPE_STATUT.ENVOYER.getCode())) {
                    if (Enums.ProfilsUtilisateur.PORTEUR_ACTION.equals(profil)) {
                        innovationNotificationService.innovationEnvoyerAction(userEmail, idAction);
                    }
                }
                else if(Objects.equals(action.getEtapeByStatus(ETAPE_ACTION.EVALUATION_INNOVATION).getStatut(), ETAPE_STATUT.VALIDER.getCode())) {
                    if (Enums.ProfilsUtilisateur.ACCOMPAGNATEUR.equals(profil)) {
                        innovationNotificationService.innovationValiderAction(userEmail, idAction);
                    }
                }
            }
            else if(ETAPE_ACTION.MESURE_INDICATEUR.equals(etape)) {
                    mesureCibleNotificationService.ajoutMesureCibleAction(userEmail,idAction);
            }
            else if(ETAPE_ACTION.CONTEXTE_ET_FACTEUR.equals(etape)) {
                if(Objects.equals(action.getEtapeByStatus(ETAPE_ACTION.CONTEXTE_ET_FACTEUR).getStatut(), ETAPE_STATUT.VALIDER.getCode())) {
                    if (Enums.ProfilsUtilisateur.PORTEUR_ACTION.equals(profil)) {
                        questionnaireNotificationService.questionnaireEnvoyerAction(userEmail, idAction);
                    }
                    else if (Enums.ProfilsUtilisateur.REFERENT_ECOCITE.equals(profil)) {
                        questionnaireNotificationService.questionnaireEnvoyerAction(userEmail, idAction);
                    }
                }
            }
    }
    public void sendNotificationEmailEtapeEcocite(Model model, ETAPE_ECOCITE etape, Long idEcocite){
        EcociteBean ecocite = ecociteService.findOneEcocite(idEcocite);
        JwtUser user = (JwtUser) model.asMap().getOrDefault("user", null);
        Enums.ProfilsUtilisateur profil = Enums.ProfilsUtilisateur.getByCode((String) model.asMap().getOrDefault("userProfileCode", null));
        if (user == null || profil == null) {
            throw new ForbiddenException();
        }
        String userEmail = user.getEmail();
        if(ETAPE_ECOCITE.INDICATEUR.equals(etape)) {
            if(ecocite.getEtapeByStatus(ETAPE_ECOCITE.INDICATEUR).getStatut().equals(ETAPE_STATUT.VALIDER.getCode())) {
                if (Enums.ProfilsUtilisateur.REFERENT_ECOCITE.equals(profil)) {
                    choixIndicateursNotificationService.choixIndicateurValiderEcocite(userEmail, idEcocite);
                }
            }
        }
        else if(ETAPE_ECOCITE.MESURE_INDICATEUR.equals(etape)) {
            mesureCibleNotificationService.ajoutMesureCibleEcocite(userEmail,idEcocite);
        }
        else if(ETAPE_ECOCITE.CONTEXTE_ET_FACTEUR.equals(etape)) {
            if(ecocite.getEtapeByStatus(ETAPE_ECOCITE.CONTEXTE_ET_FACTEUR).getStatut().equals(ETAPE_STATUT.VALIDER.getCode())) {
                if (Enums.ProfilsUtilisateur.REFERENT_ECOCITE.equals(profil)) {
                    questionnaireNotificationService.questionnaireEnvoyerEcocite(userEmail, idEcocite);
                }
            }
        }

    }

}
