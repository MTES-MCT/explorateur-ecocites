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

package com.efficacity.explorateurecocites.ui.bo.controllers.contact;

import com.efficacity.explorateurecocites.beans.Enums;
import com.efficacity.explorateurecocites.beans.biz.table.ContactTable;
import com.efficacity.explorateurecocites.beans.model.Contact;
import com.efficacity.explorateurecocites.beans.service.ActionService;
import com.efficacity.explorateurecocites.beans.service.AssoObjetContactService;
import com.efficacity.explorateurecocites.beans.service.ContactService;
import com.efficacity.explorateurecocites.beans.service.EcociteService;
import com.efficacity.explorateurecocites.ui.bo.forms.ListeFiltrageForm;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.ContactForm;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.helpers.SelectOption;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.validators.ContactFormValidator;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.ui.bo.service.RightUserService;
import com.efficacity.explorateurecocites.ui.bo.service.TablePaginationService;
import com.efficacity.explorateurecocites.utils.ExpBeanToLightBeanUtils;
import com.efficacity.explorateurecocites.utils.enumeration.CODE_FUNCTION_PROFILE;
import com.efficacity.explorateurecocites.utils.log.LoggingUtils;
import isotope.commons.exceptions.ForbiddenException;
import isotope.modules.security.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.utils.log.LoggingUtils.ActionType.*;

@Controller
@RequestMapping("/bo/contacts")
public class ContactController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactController.class);

    private static final Integer MODE_AFFICHAGE = 0;
    private static final Integer MODE_CREATION = 1;
    private static final Integer MODE_EDITION = 2;

    public static final String TYPE = "contacts";

    @Autowired
    ContactFormValidator contactFormValidator;

    @Autowired
    ContactService contactService;

    @Autowired
    AssoObjetContactService assoObjetContactService;

    @Autowired
    EcociteService ecociteService;

    @Autowired
    ActionService actionService;

    @Autowired
    MessageSourceService messageSourceService;

    @Autowired
    TablePaginationService tablePaginationService;

    @Autowired
    RightUserService rightUserService;

    private void fillFiltre(Model model, Locale locale) {
        HashMap<String, List<Object>> filtres = new LinkedHashMap<>();
        filtres.put("prenom", new LinkedList<>(Arrays.asList("Prénom", "text", null)));
        filtres.put("nom", new LinkedList<>(Arrays.asList("Nom", "text", null)));
        filtres.put("entite", new LinkedList<>(Arrays.asList("Entité", "text", null)));
        filtres.put("fonction", new LinkedList<>(Arrays.asList("Fonction", "text", null)));
        filtres.put("ecocite", new LinkedList<>(Arrays.asList("ÉcoCité", "select", ecociteService.getMapOrderByNomAsc())));
        model.addAttribute("filtres", filtres);
        model.addAttribute("urlFilter", "/bo/" + TYPE + "/filtrer");
    }


    private void fillCommon(Model model, Locale locale) {
        Enums.ProfilsUtilisateur profil = null;
        if (model.containsAttribute("userProfileCode")){
            profil = Enums.ProfilsUtilisateur.getByCode((String) model.asMap().get("userProfileCode"));
        }

        model.addAttribute("title", "Liste des contacts");
        model.addAttribute("currentMenu", TYPE);
        model.addAttribute("objectType", TYPE);
        model.addAttribute("usesModal", true);
        model.addAttribute("modalUrl", "/bo/" + TYPE + "/edit/");
        if(Enums.ProfilsUtilisateur.ACTEUR_ECOCITE_AUTRE.equals(profil)) {
            model.addAttribute("canAddObject", false);
        } else {
            model.addAttribute("canAddObject", true);
        }
        model.addAttribute("canExportObject", rightUserService.canExport(model));
        model.addAttribute("deleteUrl", "/bo/" + TYPE + "/");
        model.addAttribute("colonnes", tablePaginationService.processColumns(ContactTable.class, locale));
    }

    @GetMapping
    public String getList(Model model, Locale locale) {
        fillCommon(model, locale);
        fillFiltre(model, locale);
        List<ContactTable> objs = contactService.getList()
                .stream()
                .map(b -> ExpBeanToLightBeanUtils.copyFrom(b, b.getIdEcocite() == null ? null : ecociteService.findById(b.getIdEcocite())))
                .collect(Collectors.toList());
        model.addAttribute("data", tablePaginationService.processData(objs, ContactTable.class));
        return "bo/listeEntites";
    }

    @PostMapping(value="/filtrer", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String soumissionFiltrer(ListeFiltrageForm filtres, Model model,
                                    BindingResult result, Locale locale) {
        fillCommon(model, locale);
        fillFiltre(model, locale);
        model.addAttribute("selectedFiltres", filtres.getFiltres());
        List<ContactTable> objs = contactService.getListFiltre(filtres.getFiltres())
                .stream()
                .map(b -> ExpBeanToLightBeanUtils.copyFrom(b, b.getIdEcocite() == null ? null : ecociteService.findById(b.getIdEcocite())))
                .collect(Collectors.toList());
        model.addAttribute("data", tablePaginationService.processData(objs, ContactTable.class));

        return "bo/pagination/liste-pagination-tableau";
    }

    @GetMapping("/edit/{id}")
    public String getModalEdit(Model model, Locale locale, @PathVariable Long id) {

        if (id == -1) {
            return getModalCreate(model, locale);
        }
        JwtUser user = (JwtUser) model.asMap().getOrDefault("user", null);
        if (user == null) {
            throw new ForbiddenException();
        }
        fillCommon(model, locale);
        Optional<Contact> contact = contactService.findOne(id);
        if (contact.isPresent()) {
            model.addAttribute("titreModal", contact.get().getPrenom() + " " + contact.get().getNom());
            List<SelectOption> ecocites = ecociteService.findAllEcocite().stream().map(a -> new SelectOption("" + a.getId(), a.getNom())).collect(Collectors.toList());
            model.addAttribute("data", tablePaginationService.processSingleObject(ExpBeanToLightBeanUtils.copyFrom(contact.get(), ecocites), locale));
            if(user.getAuthorities().contains(new SimpleGrantedAuthority(CODE_FUNCTION_PROFILE.EDIT_CONTACT.getCode()))) {
                model.addAttribute("typeModal", MODE_EDITION);
            } else {
                model.addAttribute("typeModal", MODE_AFFICHAGE);
            }
            model.addAttribute("updateUrl", "/bo/" + TYPE + "/" + id);
            model.addAttribute("deleteUrl", "/bo/" + TYPE + "/" + id);
            return "bo/administration/modal_edition_objet";
        } else {
            model.addAttribute("message", "L'objet demandé n'existe pas");
            model.addAttribute("idTitre", "Erreur lors de l'affichage de la modal");
            return "bo/modal/error_modal";
        }
    }

    @GetMapping("/view/{id}")
    public String getModalView(Model model, Locale locale, @PathVariable Long id) {
        if (id == -1) {
            return getModalCreate(model, locale);
        }
        JwtUser user = (JwtUser) model.asMap().getOrDefault("user", null);
        if (user == null) {
            throw new ForbiddenException();
        }
        fillCommon(model, locale);
        Optional<Contact> contact = contactService.findOne(id);
        if (contact.isPresent()) {
            model.addAttribute("titreModal", contact.get().getPrenom() + " " + contact.get().getNom());
            List<SelectOption> ecocites = ecociteService.findAllEcocite().stream().map(a -> new SelectOption("" + a.getId(), a.getNom())).collect(Collectors.toList());
            model.addAttribute("data", tablePaginationService.processSingleObject(ExpBeanToLightBeanUtils.copyFrom(contact.get(), ecocites), locale));
            model.addAttribute("typeModal", MODE_AFFICHAGE);
            model.addAttribute("updateUrl", "/bo/" + TYPE + "/" + id);
            model.addAttribute("deleteUrl", "/bo/" + TYPE + "/" + id);
            return "bo/administration/modal_edition_objet";
        } else {
            model.addAttribute("message", "L'objet demandé n'existe pas");
            model.addAttribute("idTitre", "Erreur lors de l'affichage de la modal");
            return "bo/modal/error_modal";
        }
    }

    @GetMapping("/new")
    public String getModalCreate(Model model, Locale locale) {
        JwtUser user = (JwtUser) model.asMap().getOrDefault("user", null);
        if (user == null) {
            throw new ForbiddenException();
        }
        if(user.getAuthorities().contains(new SimpleGrantedAuthority(CODE_FUNCTION_PROFILE.CREATION_CONTACT.getCode()))) {
            fillCommon(model, locale);
            ContactForm contactForm = new ContactForm();
            model.addAttribute("titreModal", "Créer un contact");
            contactForm.setIdEcociteDefaults(ecociteService.findAllEcocite().stream().map(a -> new SelectOption("" + a.getId(), a.getNom())).collect(Collectors.toList()));
            model.addAttribute("data", tablePaginationService.processSingleObject(contactForm, locale));
            model.addAttribute("typeModal", MODE_CREATION);
            model.addAttribute("createUrl", "/bo/" + TYPE + "/");
            return "bo/administration/modal_edition_objet";
            }
        model.addAttribute("message",  messageSourceService.getMessageSource().getMessage("error.user.right", null, locale));
        model.addAttribute("idTitre", "ErreurRight");
        return "bo/modal/error_modal";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(Model model, Locale locale, @PathVariable Long id) {
        Map<String, String> mapErreur = new HashMap<>();
        JwtUser user = (JwtUser) model.asMap().getOrDefault("user", null);
        if (user == null) {
            throw new ForbiddenException();
        }
        if(user.getAuthorities().contains(new SimpleGrantedAuthority(CODE_FUNCTION_PROFILE.SUPPRESSION_CONTACT.getCode()))) {
            Contact contact = contactService.findOne(id).orElse(null);
            if (contact == null) {
                mapErreur.put("general_error", messageSourceService.getMessageSource().getMessage("error.attribut.unknown", null, locale));
            } else {
                try {
                    contactService.delete(id);
                    LoggingUtils.logAction(LOGGER, SUPPRESSION, contact, user);
                } catch (Exception e) {
                    mapErreur.put("general_error", messageSourceService.getMessageSource().getMessage("error.technical", null, locale));
                }
            }
        } else {
            mapErreur.put("general_error", messageSourceService.getMessageSource().getMessage("error.user.right", null, locale));
        }
        return ResponseEntity.ok(mapErreur);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(Model model, @RequestBody @Valid ContactForm tableform, @PathVariable Long id) {
        JwtUser user = (JwtUser) model.asMap().getOrDefault("user", null);
        if (user == null) {
            throw new ForbiddenException();
        }
        if(user.getAuthorities().contains(new SimpleGrantedAuthority(CODE_FUNCTION_PROFILE.EDIT_CONTACT.getCode()))) {
            Contact contact = contactService.update(id, tableform, user);
            LoggingUtils.logAction(LOGGER, MODIFICATION, contact, user);
            return ResponseEntity.ok("");
        }
        return ResponseEntity.badRequest().body("");
    }

    @PostMapping
    public ResponseEntity<?> create(Model model, @RequestBody @Valid ContactForm tableform) {
        JwtUser user = (JwtUser) model.asMap().getOrDefault("user", null);
        if (user == null) {
            throw new ForbiddenException();
        }
        if(user.getAuthorities().contains(new SimpleGrantedAuthority(CODE_FUNCTION_PROFILE.CREATION_CONTACT.getCode()))) {
            Contact contact = contactService.createOne(tableform, user);
            if (contact != null && contact.getId() != null) {
                LoggingUtils.logAction(LOGGER, CREATION, contact, user);
                return new ResponseEntity<>(contact.getId().toString(),HttpStatus.OK);
            } else {
                return ResponseEntity.badRequest().body("");
            }
        }
        return ResponseEntity.badRequest().body("");
    }

    @InitBinder("contactForm")
    public void bindValidator(WebDataBinder binder) {
        binder.addValidators(contactFormValidator);
    }

    @PostMapping("{idContact}/actions/{idAction}/{poids}")
    public ResponseEntity<?> createAssoActionContact(@PathVariable("idContact") Long idContact, @PathVariable("idAction") Long idAction, @PathVariable("poids") Integer poids) {
        if (idContact == -1) {
            assoObjetContactService.deleteAssoActionContactPrincipal(idAction);
            return ResponseEntity.ok("");
        }
        Optional<Contact> contact = contactService.findOne(idContact);
        if (contact.isPresent() &&
                !assoObjetContactService.findAllContactPrincipaleForAction(idAction).contains(contact.get()) &&
                !assoObjetContactService.findAllContactSecondaireForAction(idAction).contains(contact.get())){
                    return ResponseEntity.ok(assoObjetContactService.createOneContactForAction(idContact, idAction, poids).getId());
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("{idContact}/ecocites/{idEcocite}/{poids}")
    public ResponseEntity<?> createAssoEcociteContact(@PathVariable("idContact") Long idContact, @PathVariable("idEcocite") Long idEcocite, @PathVariable("poids") Integer poids) {
        if (idContact == -1) {
            assoObjetContactService.deleteAssoEcociteContactPrincipal(idEcocite);
            return ResponseEntity.ok("");
        }
        Optional<Contact> contact = contactService.findOne(idContact);
        if (contact.isPresent() &&
                !assoObjetContactService.findAllContactPrincipaleForEcocite(idEcocite).contains(contact.get()) &&
                !assoObjetContactService.findAllContactSecondaireForEcocite(idEcocite).contains(contact.get())){
                    return ResponseEntity.ok(assoObjetContactService.createOneContactForEcocite(idContact, idEcocite, poids).getId());
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("{idContact}/actions/{idAction}")
    public ResponseEntity<?> deleteAssoActionContact(@PathVariable("idContact") Long idContact, @PathVariable("idAction") Long idAction) {
        assoObjetContactService.deleteAssoActionContact(idContact, idAction);
        return ResponseEntity.ok("");
    }

    @DeleteMapping("{idContact}/ecocites/{idEcocite}")
    public ResponseEntity<?> deleteAssoEcociteContact(@PathVariable("idContact") Long idContact, @PathVariable("idEcocite") Long idEcocite) {
        assoObjetContactService.deleteAssoEcociteContact(idContact, idEcocite);
        return ResponseEntity.ok("");
    }
}
