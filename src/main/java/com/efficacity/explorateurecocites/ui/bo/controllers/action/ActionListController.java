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

package com.efficacity.explorateurecocites.ui.bo.controllers.action;


import com.efficacity.explorateurecocites.beans.Enums;
import com.efficacity.explorateurecocites.beans.biz.table.ActionTable;
import com.efficacity.explorateurecocites.beans.model.Action;
import com.efficacity.explorateurecocites.beans.model.Axe;
import com.efficacity.explorateurecocites.beans.model.Ecocite;
import com.efficacity.explorateurecocites.beans.service.*;
import com.efficacity.explorateurecocites.ui.bo.forms.ListeFiltrageForm;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.ActionCloneForm;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.ActionForm;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.helpers.SelectOption;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.validators.ActionCloneFormValidator;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.validators.ActionFormValidator;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.ui.bo.service.RightUserService;
import com.efficacity.explorateurecocites.ui.bo.service.TablePaginationService;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import com.efficacity.explorateurecocites.utils.ExpBeanToLightBeanUtils;
import com.efficacity.explorateurecocites.utils.enumeration.ETAPE_STATUT;
import com.efficacity.explorateurecocites.utils.enumeration.ETAT_AVANCEMENT;
import com.efficacity.explorateurecocites.utils.enumeration.ETAT_PUBLICATION;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_FINANCEMENT;
import com.efficacity.explorateurecocites.utils.log.LoggingUtils;
import isotope.modules.security.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by tfossurier on 10/01/2018.
 */
@Controller
@RequestMapping("/bo/actions")
public class ActionListController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActionListController.class);

    private static final Integer MODE_AFFICHAGE = 0;
    private static final Integer MODE_CREATION = 1;
    private static final Integer MODE_EDITION = 2;

    @Autowired
    ActionFormValidator actionFormValidator;

    @Autowired
    ActionCloneFormValidator actionCloneFormValidator;

    @Autowired
    ActionService actionService;

    @Autowired
    EcociteService ecociteService;

    @Autowired
    AxeService axeService;

    @Autowired
    TablePaginationService tablePaginationService;

    @Autowired
    EtiquetteAxeService etiquetteAxeService;

    @Autowired
    FinaliteService finaliteService;

    @Autowired
    EtiquetteFinaliteService etiquetteFinaliteService;

    @Autowired
    EtiquetteIngenierieService etiquetteIngenierieService;

    @Autowired
    MessageSourceService messageSourceService;

    @Autowired
    RightUserService rightUserService;

    public static final String TYPE = "actions";

    private void fillCommon(Model model, Locale locale) {
        model.addAttribute("title", "Liste des actions");
        model.addAttribute("currentMenu", "actions");
        model.addAttribute("objectType", "actions");
        model.addAttribute("usesModal", false);
        model.addAttribute("modalUrl", "/bo/" + TYPE + "/edit/");
        model.addAttribute("canExportObject", rightUserService.canExport(model));
        model.addAttribute("hasExportAction", false);
        model.addAttribute("exportUrl", "/bo/rapports/" + TYPE + "/");
        model.addAttribute("date", LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")));
        model.addAttribute("colonnes", tablePaginationService.processColumns(ActionTable.class, locale));
    }

    private void fillFiltre(Model model, Locale locale) {

        Enums.ProfilsUtilisateur profil = null;
        if (model.containsAttribute("userProfileCode")){
            profil = Enums.ProfilsUtilisateur.getByCode((String) model.asMap().get("userProfileCode"));
        }

        if(Enums.ProfilsUtilisateur.ADMIN.equals(profil)) {
            model.addAttribute("canAddObject", true);
        } else {
            model.addAttribute("canAddObject", false);
        }

        HashMap<String, List<Object>> filtres = new LinkedHashMap<>();
        filtres.put("nom", new LinkedList<>(Arrays.asList("Nom public","text",null)));
        filtres.put("ecocite", new LinkedList<>(Arrays.asList("ÉcoCité","select", ecociteService.getMapOrderByNomAsc())));
        filtres.put("numeroAction", new LinkedList<>(Arrays.asList("Numéro d'action", "text", null)));
        filtres.put("axePrincipale", new LinkedList<>(Arrays.asList("Axe principal","select", axeService.getMap())));
        filtres.put("domaineAction", new LinkedList<>(Arrays.asList("Domaine d'action","select", etiquetteAxeService.getMap())));
        filtres.put("finalite", new LinkedList<>(Arrays.asList("Finalités","select", finaliteService.getMap())));
        filtres.put("objectifVille", new LinkedList<>(Arrays.asList("Objectifs de la ville durable","select", etiquetteFinaliteService.getMap())));
        filtres.put("typeMission", new LinkedList<>(Arrays.asList("Type de mission","select", etiquetteIngenierieService.getMap())));
        filtres.put("typeFinancement", new LinkedList<>(Arrays.asList("Type de financement","select",TYPE_FINANCEMENT.toMap())));
        filtres.put("etatAvancement", new LinkedList<>(Arrays.asList("Etat d'avancement","select",ETAT_AVANCEMENT.toMap())));
        filtres.put("etatPublication", new LinkedList<>(Arrays.asList("Etat de publication", "select", ETAT_PUBLICATION.toMap())));
        filtres.put("caracterisation", new LinkedList<>(Arrays.asList("Caractérisation de l'action", "select", ETAPE_STATUT.toMapAvecValidation())));
        filtres.put("indicateurChoix", new LinkedList<>(Arrays.asList("Choix des indicateurs", "select", ETAPE_STATUT.toMapAvecValidation())));
        filtres.put("evaluationInnovation", new LinkedList<>(Arrays.asList("Evaluation de l'innovation", "select", ETAPE_STATUT.toMapAvecValidation())));
        filtres.put("indicateurMesure", new LinkedList<>(Arrays.asList("Renseignement des indicateurs", "select", ETAPE_STATUT.toMapSansValidation())));
        filtres.put("impactProgramme", new LinkedList<>(Arrays.asList("Evaluation qualitative", "select", ETAPE_STATUT.toMapSansValidation())));
        model.addAttribute("filtres", filtres);
        model.addAttribute("urlFilter", "/bo/" + TYPE + "/filtrer");
    }

    @GetMapping
    public String index(ListeFiltrageForm filtres, Model model, BindingResult result, Locale locale) {
        fillCommon(model, locale);
        fillFiltre(model, locale);
        model.addAttribute("selectedFiltres", filtres.getFiltres());
        List<ActionTable> objs = actionService.getListFiltre(filtres.getFiltres())
                .stream()
                .map(action -> {
                    Ecocite ecocite = action.getIdEcocite() != null ? ecociteService.getOne(action.getIdEcocite()) : null;
                    Axe axe = action.getIdAxe() != null ? axeService.getOne(action.getIdAxe()) : null;
                    return ExpBeanToLightBeanUtils.copyFrom(action, ecocite, axe);
                })
                .collect(Collectors.toList());
        model.addAttribute("data", tablePaginationService.processData(objs, ActionTable.class));

        return "bo/listeEntites";
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String soumissionFiltreredIndex(ListeFiltrageForm filtres, Model model, BindingResult result, Locale locale) {
        return index(filtres, model, result, locale);
    }

    @PostMapping(value="/filtrer", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String soumissionFiltrer(ListeFiltrageForm filtres, Model model,
                                    BindingResult result, Locale locale) {
        fillCommon(model, locale);
        fillFiltre(model, locale);
        model.addAttribute("selectedFiltres", filtres.getFiltres());
        List<ActionTable> objs = actionService.getListFiltre(filtres.getFiltres())
                .stream()
                .map(action -> {
                    Ecocite ecocite = action.getIdEcocite() != null ? ecociteService.getOne(action.getIdEcocite()) : null;
                    Axe axe = action.getIdAxe() != null ? axeService.getOne(action.getIdAxe()) : null;
                    return ExpBeanToLightBeanUtils.copyFrom(action, ecocite, axe);
                })
                .collect(Collectors.toList());
        model.addAttribute("data", tablePaginationService.processData(objs, ActionTable.class));

        return "bo/pagination/liste-pagination-tableau";
    }

    @GetMapping({"/new", "/edit/-1"})
    public String getModalCreate(Model model, Locale locale) {
        fillCommon(model, locale);
        ActionForm actionForm = new ActionForm();
        actionForm.setIdEcociteDefaults(ecociteService.findAllEcocite().stream().map(e -> new SelectOption(String.valueOf(e.getId()), e.getNom())).collect(Collectors.toList()));
        model.addAttribute("titreModal", "Créer une action");
        model.addAttribute("data", tablePaginationService.processSingleObject(actionForm, locale));
        model.addAttribute("typeModal", MODE_CREATION);
        model.addAttribute("createUrl", "/bo/" + TYPE + "/");
        model.addAttribute("redirectAfterCreation", true);
        model.addAttribute("redirectURL", "/bo/" + TYPE + "/edition/");
        return "bo/administration/modal_edition_objet";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(Locale locale, Model model, @PathVariable Long id) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        Enums.ProfilsUtilisateur profil = null;
        if (model.containsAttribute("userProfileCode")){
            profil = Enums.ProfilsUtilisateur.getByCode((String) model.asMap().get("userProfileCode"));
        }
        Map<String, String> mapErreur = new HashMap<>();
        if(Enums.ProfilsUtilisateur.ADMIN.equals(profil)) {
            Action action = actionService.findOne(id).orElse(null);
            if (CustomValidator.isEmpty(action)) {
                mapErreur.put("general_error", messageSourceService.getMessageSource().getMessage("error.attribut.unknown", null, locale));
            } else {
                try {
                    String result = actionService.delete(id);
                    LoggingUtils.logAction(LOGGER,LoggingUtils.ActionType.SUPPRESSION,LoggingUtils.SecondaryType.NONE,action,user);
                    if (CustomValidator.isNotEmpty(result)) {
                        mapErreur.put("general_error", result);
                    }
                } catch (Exception e) {
                    mapErreur.put("general_error", messageSourceService.getMessageSource().getMessage("error.technical", null, locale));
                }
            }
        } else {
            mapErreur.put("general_error", messageSourceService.getMessageSource().getMessage("error.user.right", null, locale));
        }
        return ResponseEntity.ok(mapErreur);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ActionForm actionForm, Model model, Locale locale) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        Enums.ProfilsUtilisateur profil = null;
        if (model.containsAttribute("userProfileCode")){
            profil = Enums.ProfilsUtilisateur.getByCode((String) model.asMap().get("userProfileCode"));
        }
        if(Enums.ProfilsUtilisateur.ADMIN.equals(profil)) {
            Action action = actionService.createOne(actionForm, (JwtUser) model.asMap().get("user"));
            LoggingUtils.logAction(LOGGER,LoggingUtils.ActionType.CREATION,LoggingUtils.SecondaryType.NONE,action,user);
            if (action.getId() != null) {
                return ResponseEntity.ok(String.valueOf(action.getId()));
            } else {
                return ResponseEntity.badRequest().body("");
            }
        } else {
            return ResponseEntity.badRequest().body(messageSourceService.getMessageSource().getMessage("error.user.right", null, locale));
        }
    }

    @PostMapping("/clone")
    public ResponseEntity<?> clone(@RequestBody @Valid ActionCloneForm actionCloneForm, Model model, Locale locale) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        Enums.ProfilsUtilisateur profil = null;
        if (model.containsAttribute("userProfileCode")){
            profil = Enums.ProfilsUtilisateur.getByCode((String) model.asMap().get("userProfileCode"));
        }
        if(Enums.ProfilsUtilisateur.ADMIN.equals(profil)) {
            Action action = actionService.clone(actionCloneForm, (JwtUser) model.asMap().get("user"));
            LoggingUtils.logAction(LOGGER,LoggingUtils.ActionType.CREATION,LoggingUtils.SecondaryType.NONE,action,user);
            if (action != null && action.getId() != null) {
                return ResponseEntity.ok(String.valueOf(action.getId()));
            } else {
                return ResponseEntity.badRequest().body("");
            }
        } else {
            return ResponseEntity.badRequest().body(messageSourceService.getMessageSource().getMessage("error.user.right", null, locale));
        }
    }

    @InitBinder("actionForm")
    public void bindValidator(WebDataBinder binder) {
        binder.addValidators(actionFormValidator);
    }

    @InitBinder("actionCloneForm")
    public void bindCloneValidator(WebDataBinder binder) {
        binder.addValidators(actionCloneFormValidator);
    }
}
