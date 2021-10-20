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

package com.efficacity.explorateurecocites.ui.bo.controllers.ecocite;

import com.efficacity.explorateurecocites.beans.Enums;
import com.efficacity.explorateurecocites.beans.biz.table.EcociteTable;
import com.efficacity.explorateurecocites.beans.model.Ecocite;
import com.efficacity.explorateurecocites.beans.model.Region;
import com.efficacity.explorateurecocites.beans.service.EcociteService;
import com.efficacity.explorateurecocites.beans.service.FinaliteService;
import com.efficacity.explorateurecocites.beans.service.RegionService;
import com.efficacity.explorateurecocites.ui.bo.forms.ListeFiltrageForm;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.EcociteForm;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.validators.EcociteFormValidator;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.ui.bo.service.RightUserService;
import com.efficacity.explorateurecocites.ui.bo.service.TablePaginationService;
import com.efficacity.explorateurecocites.utils.ApplicationConstants;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import com.efficacity.explorateurecocites.utils.ExpBeanToLightBeanUtils;
import com.efficacity.explorateurecocites.utils.enumeration.ETAPE_STATUT;
import com.efficacity.explorateurecocites.utils.enumeration.ETAT_PUBLICATION;
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
 * Created by rxion on 20/02/2018.
 */
@Controller
@RequestMapping("/bo/ecocites")
public class EcociteListeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EcociteListeController.class);

    private static final Integer MODE_AFFICHAGE = 0;
    private static final Integer MODE_CREATION = 1;
    private static final Integer MODE_EDITION = 2;

    @Autowired
    EcociteFormValidator ecociteFormValidator;

    @Autowired
    EcociteService ecociteService;

    @Autowired
    RegionService regionService;

    @Autowired
    TablePaginationService tablePaginationService;

    @Autowired
    MessageSourceService messageSourceService;

    @Autowired
    RightUserService rightUserService;

    @Autowired
    FinaliteService finaliteService;

    public static final String TYPE = "ecocites";


    private void fillCommon(Model model, Locale locale) {
        model.addAttribute("title", "Liste des ÉcoCités");
        model.addAttribute("currentMenu", TYPE);
        model.addAttribute("objectType", TYPE);
        model.addAttribute("usesModal", false);
        model.addAttribute("modalUrl", "/bo/" + TYPE + "/edit/");
        model.addAttribute("canExportObject", rightUserService.canExport(model));
        model.addAttribute("hasExportAction", false);
        model.addAttribute("exportUrl", "/bo/rapports/" + TYPE + "/");
        model.addAttribute("date", LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")));
        model.addAttribute("colonnes", tablePaginationService.processColumns(EcociteTable.class, locale));
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
        filtres.put("nom", new LinkedList<>(Arrays.asList("Nom", "text", null)));
        filtres.put("region", new LinkedList<>(Arrays.asList("Région", "select", regionService.getMapOrderByNomAsc())));
        filtres.put("porteur", new LinkedList<>(Arrays.asList("Porteur du projet","text",null)));
        filtres.put("anneeAhdesion", new LinkedList<>(Arrays.asList("ÉcoCité depuis","text",null)));
        filtres.put("publication", new LinkedList<>(Arrays.asList("Etat de publication", "select", ETAT_PUBLICATION.toMap())));
        filtres.put("caracterisation", new LinkedList<>(Arrays.asList("Caractérisation de l'ÉcoCité", "select", ETAPE_STATUT.toMapSansValidation())));
        filtres.put("indicateurChoix", new LinkedList<>(Arrays.asList("Choix des indicateurs", "select", ETAPE_STATUT.toMapSansValidation())));
        filtres.put("indicateurMesure", new LinkedList<>(Arrays.asList("Renseignement des indicateurs", "select", ETAPE_STATUT.toMapSansValidation())));
        filtres.put("impactProgramme", new LinkedList<>(Arrays.asList("Impact du programme", "select", ETAPE_STATUT.toMapSansValidation())));
        filtres.put("finalite", new LinkedList<>(Arrays.asList("Finalités","select", finaliteService.getMap())));
        model.addAttribute("filtres", filtres);
        model.addAttribute("urlFilter", "/bo/" + TYPE + "/filtrer");
    }

    @GetMapping
    public String index(Model model, Locale locale) {
        fillCommon(model, locale);
        fillFiltre(model, locale);

        List<EcociteTable> objs = ecociteService.getList(null)
              .stream()
              .map(ecocite -> {
                  Region region = ecocite.getIdRegion() != null ? regionService.findById(ecocite.getIdRegion()) : null;
                  return ExpBeanToLightBeanUtils.copyFrom(ecocite,region);
              })
              .collect(Collectors.toList());
        model.addAttribute("data", tablePaginationService.processData(objs, EcociteTable.class));
        return "bo/listeEntites";
    }

    @PostMapping(value="/filtrer", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String soumissionFiltrer(ListeFiltrageForm filtres, Model model, BindingResult result, Locale locale) {
        fillCommon(model, locale);
        List<EcociteTable> objs = ecociteService.getListFiltre(filtres.getFiltres())
                .stream()
                .map(ecocite -> {
                    Region region = ecocite.getIdRegion() != null ? regionService.findById(ecocite.getIdRegion()) : null;
                    return ExpBeanToLightBeanUtils.copyFrom(ecocite,region);
                })
                .collect(Collectors.toList());
        model.addAttribute("data", tablePaginationService.processData(objs, EcociteTable.class));
        return "bo/pagination/liste-pagination-tableau";
    }

    @GetMapping({"/new", "/edit/-1"})
    public String getModalCreate(Model model, Locale locale) {
        fillCommon(model, locale);
        EcociteForm ecociteForm = new EcociteForm();
        model.addAttribute("titreModal", "Créer une ÉcoCité");
        model.addAttribute("data", tablePaginationService.processSingleObject(ecociteForm, locale));
        model.addAttribute("typeModal", MODE_CREATION);
        model.addAttribute("createUrl", "/bo/" + TYPE + "/");
        model.addAttribute("redirectAfterCreation", true);
        model.addAttribute("redirectURL", "/bo/" + TYPE + "/edition/");
        return "bo/administration/modal_edition_objet";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(Locale locale, @PathVariable Long id, Model model) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        Map<String, String> mapErreur = new HashMap<>();
        Enums.ProfilsUtilisateur profil = null;
        if (model.containsAttribute("userProfileCode")){
            profil = Enums.ProfilsUtilisateur.getByCode((String) model.asMap().get("userProfileCode"));
        }
        if(Enums.ProfilsUtilisateur.ADMIN.equals(profil)) {
            Ecocite ecocite = ecociteService.findOne(id).orElse(null);
            if(CustomValidator.isEmpty(ecocite)){
                mapErreur.put(ApplicationConstants.GENERAL_ERROR, messageSourceService.getMessageSource().getMessage("error.attribut.unknown", null, locale));
            } else {
                try {
                    String result = ecociteService.delete(id, locale, (JwtUser) model.asMap().get("user"));
                    LoggingUtils.logAction(LOGGER,LoggingUtils.ActionType.SUPPRESSION,LoggingUtils.SecondaryType.NONE,ecocite,user);
                    if(CustomValidator.isNotEmpty(result)){
                        mapErreur.put(ApplicationConstants.GENERAL_ERROR, result);
                    }
                } catch (Exception e) {
                    mapErreur.put(ApplicationConstants.GENERAL_ERROR, messageSourceService.getMessageSource().getMessage(ApplicationConstants.ERROR_TECHNICAL, null, locale));
                }
            }
        } else {
            mapErreur.put(ApplicationConstants.GENERAL_ERROR, messageSourceService.getMessageSource().getMessage("error.user.right", null, locale));
        }
        return ResponseEntity.ok(mapErreur);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid EcociteForm ecociteForm, Model model, Locale locale) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        Enums.ProfilsUtilisateur profil = null;
        if (model.containsAttribute("userProfileCode")){
            profil = Enums.ProfilsUtilisateur.getByCode((String) model.asMap().get("userProfileCode"));
        }
        if(Enums.ProfilsUtilisateur.ADMIN.equals(profil)) {
            Ecocite ecocite = ecociteService.createOne(ecociteForm, (JwtUser) model.asMap().get("user"));
            LoggingUtils.logAction(LOGGER,LoggingUtils.ActionType.CREATION,LoggingUtils.SecondaryType.NONE,ecocite,user);
            if (ecocite.getId() != null) {
                return ResponseEntity.ok(String.valueOf(ecocite.getId()));
            } else {
                return ResponseEntity.badRequest().body("");
            }
        } else {
            return ResponseEntity.badRequest().body(messageSourceService.getMessageSource().getMessage("error.user.right", null, locale));
        }
    }

    @InitBinder("ecociteForm")
    public void bindValidator(WebDataBinder binder) {
        binder.addValidators(ecociteFormValidator);
    }
}
