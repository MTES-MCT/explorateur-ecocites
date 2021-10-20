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

package com.efficacity.explorateurecocites.ui.bo.controllers.action.onglets;

import com.efficacity.explorateurecocites.beans.Enums;
import com.efficacity.explorateurecocites.beans.biz.ActionBean;
import com.efficacity.explorateurecocites.beans.model.Business;
import com.efficacity.explorateurecocites.beans.model.Contact;
import com.efficacity.explorateurecocites.beans.service.*;
import com.efficacity.explorateurecocites.configuration.ServiceConfiguration;
import com.efficacity.explorateurecocites.ui.bo.service.RightUserService;
import com.efficacity.explorateurecocites.utils.enumeration.*;
import isotope.modules.user.lightbeans.RoleLightBean;
import isotope.modules.user.lightbeans.UserLightbean;
import isotope.modules.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/bo/actions/presentation")
public class ActionPresentationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActionPresentationController.class);

    @Autowired
    ActionService actionService;

    @Autowired
    BusinessService businessService;

    @Autowired
    EcociteService ecociteService;

    @Autowired
    AxeService axeService;

    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    AssoObjetContactService assoObjetContactService;

    @Autowired
    ContactService contactService;

    @Autowired
    ServiceConfiguration serviceConfiguration;

    @Autowired
    RightUserService rightUserService;

    @Autowired
    UserService userService;

    @Autowired
    MediaModificationService mediaModificationService;

    @Autowired
    MediaService mediaService;

    @GetMapping("/{actionId}")
    public String loadActionPresentationTab(Model model, @PathVariable Long actionId) {
        Enums.ProfilsUtilisateur profil = null;
        if (model.containsAttribute("userProfileCode")) {
            profil = Enums.ProfilsUtilisateur.getByCode((String) model.asMap().get("userProfileCode"));
        }
        if (Enums.ProfilsUtilisateur.ACCOMPAGNATEUR.equals(profil) || Enums.ProfilsUtilisateur.ADMIN.equals(profil)) {
            model.addAttribute("isAdminAccompaModif", true);
        }

        if (Enums.ProfilsUtilisateur.ADMIN.equals(profil)) {
            model.addAttribute("isAdminModif", true);
        }
        if (Enums.ProfilsUtilisateur.PORTEUR_ACTION.equals(profil)) {
            model.addAttribute("isPorteurAction", true);
        }

        if (rightUserService.canModifObjet(model, actionId, CODE_FUNCTION_PROFILE.EDIT_PRESENTATION_ACTION.getCode(), TYPE_OBJET.ACTION.getCode())) {
            model.addAttribute("canEdit", true);
        } else {
            model.addAttribute("canEdit", false);
        }

        ActionBean action = actionService.findOneAction(actionId);
        model.addAttribute("action", action);
        model.addAttribute("perimetre", fileUploadService.getFirstFileActionOfType(actionId, FILE_TYPE.ACTION_PERIMETRE));
        model.addAttribute("documents", fileUploadService.getFileActionOfType(actionId, FILE_TYPE.ACTION_DOCUMENT));
        // On va pas envoye toute les bean ecocite, c'est trop gros. Juste une map ID / nom
        model.addAttribute("ecocites", ecociteService.getMapOrderByNomAsc());

        model.addAttribute("imagesPrincipales", mediaService.getMainMediaForAction(action));
        model.addAttribute("imagesSecondaires", mediaService.getOtherMediaForAction(action));

        // On va pas envoye toute les bean ecocite, c'est trop gros. Juste une map ID / nom
        model.addAttribute("etatsAvancement", ETAT_AVANCEMENT.values());
        model.addAttribute("tranches", TRANCHE_EXECUTION.values());
        model.addAttribute("echellesAction", ECHELLE_ACTION.values());
        model.addAttribute("etatsPublication", ETAT_PUBLICATION.values());
        model.addAttribute("typesFinancement", TYPE_FINANCEMENT.values());
        model.addAttribute("axePrin", action.getIdAxe());
        model.addAttribute("axes", axeService.getList());
        List<Business> business = businessService.findByIdAction(actionId);
        model.addAttribute("affaires", business.stream().map(Business::getNumeroAffaire).collect(Collectors.joining(";")));
        action.setBusiness(business);
        model.addAttribute("moUsed", action.getMaitriseNotFromEnum());
        List<MAITRISE_OUVRAGE> maitriseOuvrages = action.getMaitriseFromEnum();
        model.addAttribute("moEnumUsed", maitriseOuvrages);
        model.addAttribute("moEnumNotUsed", Arrays.stream(MAITRISE_OUVRAGE.values()).filter(o -> !maitriseOuvrages.contains(o)).collect(Collectors.toList()));
        List<Contact> contactPrincipalSelected = assoObjetContactService.findAllContactPrincipaleForAction(actionId);
        List<Contact> contactSecondaireSelected = assoObjetContactService.findAllContactSecondaireForAction(actionId);
        List<Contact> contacts = contactService.getList();
        model.addAttribute("contactPrincipal", contactPrincipalSelected);
        model.addAttribute("contactSecondaire", contactSecondaireSelected);
        model.addAttribute("contactPrincipalNotUsed", contacts.stream().filter(c -> !contactPrincipalSelected.contains(c)).collect(Collectors.toList()));
        model.addAttribute("contactSecondaireNotUsed", contacts.stream().filter(c -> !contactSecondaireSelected.contains(c)).collect(Collectors.toList()));
        model.addAttribute("geoportailApiKey", serviceConfiguration.getGeoportailApiKey());
        model.addAttribute("ajarisLoginUrl", serviceConfiguration.getAjarisLoginUrl());
        model.addAttribute("modalContactUrl", "/bo/contacts/edit/");
        model.addAttribute("deleteUrl", "/bo/contacts/");

        UserLightbean userModification = userService.getUserByEmail(action.getEmailUserModification()).orElse(null);
        if (userModification != null) {
            model.addAttribute("userNameModification", userModification.getFirstname() + " " + userModification.getLastname());
            String profilLibelle = null;
            if (userModification.getRoles() != null && !userModification.getRoles().isEmpty() && userModification.getRoles().toArray()[0] != null &&
                    Enums.ProfilsUtilisateur.getByCode(((RoleLightBean) userModification.getRoles().toArray()[0]).getCode()) != null) {
                profilLibelle = Enums.ProfilsUtilisateur.getByCode(((RoleLightBean) userModification.getRoles().toArray()[0]).getCode()).getLibelle();
            }
            model.addAttribute("userProfilModification", profilLibelle != null ? profilLibelle : "inconnu");
            model.addAttribute("dateModification", action.getHeureModificationAffichable());
        } else {
            model.addAttribute("userNameModification", "Inconnu");
        }

        model.addAttribute("ajarisStatus", mediaModificationService.getStatus(action.getTo()));
        return "bo/actions/ongletEdition/presentationAction";
    }
}
