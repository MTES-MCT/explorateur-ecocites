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

package com.efficacity.explorateurecocites.ui.bo.controllers.ecocite.onglets;

import com.efficacity.explorateurecocites.beans.Enums;
import com.efficacity.explorateurecocites.beans.biz.EcociteBean;
import com.efficacity.explorateurecocites.beans.model.Contact;
import com.efficacity.explorateurecocites.beans.service.*;
import com.efficacity.explorateurecocites.configuration.ServiceConfiguration;
import com.efficacity.explorateurecocites.ui.bo.service.RightUserService;
import com.efficacity.explorateurecocites.utils.enumeration.*;
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
@RequestMapping("/bo/ecocites/presentation")
public class EcocitePresentationController {

    @Autowired
    EcociteService ecociteService;

    @Autowired
    RegionService regionService;

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
    MediaService mediaService;

    @Autowired
    MediaModificationService mediaModificationService;

    @GetMapping("/{ecociteId}")
    public String loadEcocitePresentationTab(Model model, @PathVariable Long ecociteId) {
        Enums.ProfilsUtilisateur profil = null;
        if (model.containsAttribute("userProfileCode")){
            profil = Enums.ProfilsUtilisateur.getByCode((String) model.asMap().get("userProfileCode"));
        }
        if(Enums.ProfilsUtilisateur.ACCOMPAGNATEUR.equals(profil) || Enums.ProfilsUtilisateur.ADMIN.equals(profil)) {
            model.addAttribute("isAdminAccompaModif", true);
        }

        if(Enums.ProfilsUtilisateur.ADMIN.equals(profil)) {
            model.addAttribute("isAdminModif", true);
        }

        if(rightUserService.canModifObjet( model, ecociteId, CODE_FUNCTION_PROFILE.EDIT_PRESENTATION_ECOCITE.getCode(), TYPE_OBJET.ECOCITE.getCode())){
            model.addAttribute("canEdit", true);
        } else {
            model.addAttribute("canEdit", false);
        }

        EcociteBean ecocite = ecociteService.findOneEcocite(ecociteId);
        model.addAttribute("ecocite", ecocite);
        model.addAttribute("perimetreOperationnel", fileUploadService.getFirstFileEcociteOfType(ecociteId, FILE_TYPE.ECOCITE_PERIMETRE_OPERATIONNEL));
        model.addAttribute("perimetreStrategique", fileUploadService.getFirstFileEcociteOfType(ecociteId, FILE_TYPE.ECOCITE_PERIMETRE_STRATEGIQUE));


        model.addAttribute("imagesPrincipales", mediaService.getMainMediaForEcocite(ecocite));
        model.addAttribute("imagesSecondaires", mediaService.getOtherMediaForEcocite(ecocite));

        model.addAttribute("documents", fileUploadService.getFileEcociteOfType(ecociteId, FILE_TYPE.ECOCITE_DOCUMENT));

        model.addAttribute("porteurProjet", ecocite.getPorteur());

        model.addAttribute("partenairesUsed", ecocite.getPartenaireNotFromEnum());
        List<MAITRISE_OUVRAGE> maitriseOuvrages = ecocite.getPartenaireFromEnum();
        model.addAttribute("partenairesEnumUsed", maitriseOuvrages);
        model.addAttribute("partenairesEnumNotUsed", Arrays.stream(MAITRISE_OUVRAGE.values()).filter(o -> !maitriseOuvrages.contains(o)).collect(Collectors.toList()));

        // On va pas envoye toute les bean région, c'est trop gros. Juste une map ID / nom

        model.addAttribute("regions", regionService.getMapOrderByNomAsc());
        model.addAttribute("etatsPublication", ETAT_PUBLICATION.values());
        List<Contact> contactPrincipalSelected = assoObjetContactService.findAllContactPrincipaleForEcocite(ecociteId);
        List<Contact> contactSecondaireSelected = assoObjetContactService.findAllContactSecondaireForEcocite(ecociteId);
        List<Contact> contacts = contactService.getList();
        model.addAttribute("contactPrincipal", contactPrincipalSelected);
        model.addAttribute("contactPrincipalNotUsed", contacts.stream().filter(c -> !contactPrincipalSelected.contains(c)).collect(Collectors.toList()));
        model.addAttribute("contactSecondaire", contactSecondaireSelected);
        model.addAttribute("contactSecondaireNotUsed", contacts.stream().filter(c -> !contactSecondaireSelected.contains(c)).collect(Collectors.toList()));
        model.addAttribute("ajarisLoginUrl", serviceConfiguration.getAjarisLoginUrl());
        model.addAttribute("geoportailApiKey", serviceConfiguration.getGeoportailApiKey());
        model.addAttribute("modalContactUrl","/bo/contacts/edit/");
        model.addAttribute("deleteUrl", "/bo/contacts/");

        model.addAttribute("ajarisStatus", mediaModificationService.getStatus(ecocite.getTo()));
        return "bo/ecocites/ongletEdition/presentationEcocite";
    }
}
