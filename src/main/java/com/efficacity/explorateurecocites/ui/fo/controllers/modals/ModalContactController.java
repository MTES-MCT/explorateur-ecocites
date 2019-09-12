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

package com.efficacity.explorateurecocites.ui.fo.controllers.modals;

import com.efficacity.explorateurecocites.beans.biz.ActionBean;
import com.efficacity.explorateurecocites.beans.biz.EcociteBean;
import com.efficacity.explorateurecocites.beans.model.Action;
import com.efficacity.explorateurecocites.beans.model.Contact;
import com.efficacity.explorateurecocites.beans.model.Ecocite;
import com.efficacity.explorateurecocites.beans.service.*;
import com.efficacity.explorateurecocites.configuration.ServiceConfiguration;
import com.efficacity.explorateurecocites.ui.fo.form.PriseContactForm;
import com.efficacity.explorateurecocites.utils.ValidationErrorBuilder;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import com.efficacity.explorateurecocites.utils.service.email.EnvoieEmailService;
import org.hibernate.type.ObjectType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Created by tfossurier on 29/01/2018.
 */
@Controller
@RequestMapping("/")
public class ModalContactController {


    @Autowired
    PriseContactService priseContactServ;
    @Autowired
    ServiceConfiguration serviceConfiguration;
    @Autowired
    ContactService contactServ;
    @Autowired
    AssoObjetContactService assoObjetContactserv;
    @Autowired
    EnvoieEmailService envoieEmailService;
    @Autowired
    EcociteService ecociteService;
    @Autowired
    ActionService actionService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ModalContactController.class);

    @PostMapping(value = "modal/contact", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public ResponseEntity<?> modalContact(@Valid PriseContactForm priseContact, Model model,BindingResult bindingResult, BindingResult result) {

        String NameObjet="Action/ÉcoCité";
        if(priseContact.getTypeObjet().equals(TYPE_OBJET.ACTION.getCode()) && priseContact.getIdObjet()!=null){
            ActionBean action= actionService.findOneAction(priseContact.getIdObjet());
            if(action!=null) {
                NameObjet ="Action "+action.getNomPublic();
            }
        }
        else if(priseContact.getTypeObjet().equals(TYPE_OBJET.ECOCITE.getCode()) && priseContact.getIdObjet()!=null){
            EcociteBean ecocite= ecociteService.findOneEcocite(priseContact.getIdObjet());
            if(ecocite!=null){
                NameObjet="ÉcoCité " +ecocite.getNom();
            }
        }
        String messageContact = priseContact.getMessageContact();
        String objet = "Explorateur ÉcoCités – Prise de contact";
        messageContact = "Une demande de prise de contact vous est adressée par " +priseContact.getEmailContact()+
                " concernant l’"+NameObjet+".\n\n\n" +messageContact+
                "\n\n\nLien vers Explorateur ÉcoCité :\n" +"https://explorateur.ecocites.logement.gouv.fr/";
        List<String> adresses = new ArrayList<>();
        List<String> prenomNom = new ArrayList<>();
        List<Contact> contacts = new ArrayList<>();

        if (priseContact.getTypeObjet().equals("action")) {
            contacts.clear();
            contacts.addAll(assoObjetContactserv.findAllContactPrincipaleForAction(priseContact.getIdObjet()));
            contacts.addAll(assoObjetContactserv.findAllContactSecondaireForAction(priseContact.getIdObjet()));
        } else if (priseContact.getTypeObjet().equals("ecocite")) {
            contacts.clear();
            contacts.addAll(assoObjetContactserv.findAllContactPrincipaleForEcocite(priseContact.getIdObjet()));
            contacts.addAll(assoObjetContactserv.findAllContactSecondaireForEcocite(priseContact.getIdObjet()));
        }

        for (Contact contactEach : contacts) {
            Optional<Contact> contactOpt = contactServ.findOne(contactEach.getId());
            if (contactOpt.isPresent()) {
                Contact contact = contactOpt.get();
                adresses.add(contact.getEmail());
                prenomNom.add(contact.getPrenom() + " " + contact.getNom());
            }
        }
        if (!adresses.isEmpty()) {
            envoieEmailService.envoyerEmail(adresses, prenomNom, null, messageContact, objet);
            priseContactServ.createOne(priseContact);
        } else {
            LOGGER.warn("Il n'y a pas de contact a qui envoyer le message pour l'objet {} : {}", priseContact.getTypeObjet(), priseContact.getIdObjet());
            // decommenter pour gérer le cas ou il n'y a pas de contact principal.
//            if (priseContact.getTypeObjet().equals("action")) {
//                ActionBean action = actionService.findOneAction(priseContact.getIdObjet());
//                EcociteBean ecocite = ecociteService.findOneEcocite(action.getIdEcocite());
//                contacts.addAll(assoObjetContactserv.findAllContactPrincipaleForEcocite(ecocite.getId()));
//                for (Contact contactEach : contacts) {
//                    Optional<Contact> contactOpt = contactServ.findOne(contactEach.getId());
//                    if (contactOpt.isPresent()) {
//                        Contact contact = contactOpt.get();
//                        adresses.add(contact.getEmail());
//                        prenomNom.add(contact.getPrenom() + " " + contact.getNom());
//                    }
//                }
//                if (!adresses.isEmpty()) {
//                    envoieEmailService.envoyerEmail(adresses, prenomNom, null, messageContact, "Prise de Contact");
//                    priseContactServ.createOne(priseContact);
//                } else {
//                    envoieEmailService.envoyerEmailUnique(serviceConfiguration.getEmailAccompagnateur(),"Accompagnateur",null,messageContact,"Prise de Contact");
//                }
//            }else if (priseContact.getTypeObjet().equals("ecocite")) {
//                envoieEmailService.envoyerEmailUnique(serviceConfiguration.getEmailAccompagnateur(),"Accompagnateur",null,messageContact,"Prise de Contact");
//            }
        }
        //copie du mail à l'adresse mail de la la prise de contact.
        envoieEmailService.envoyerEmailUnique(priseContact.getEmailContact(),"Utilisateur",null,messageContact,objet);
    if(bindingResult.hasErrors()){
        return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrorsIgnoreValue(bindingResult));
    }
    else{
        return ResponseEntity.ok(true);
    }
    }
}
