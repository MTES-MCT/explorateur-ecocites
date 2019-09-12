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

package com.efficacity.explorateurecocites.ui.bo.controllers.administration;

import com.efficacity.explorateurecocites.beans.model.Axe;
import com.efficacity.explorateurecocites.beans.service.AxeService;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.AxeTableForm;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.validators.AxeTableFormValidator;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.ui.bo.service.TablePaginationService;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import com.efficacity.explorateurecocites.utils.ExpBeanToLightBeanUtils;
import com.efficacity.explorateurecocites.utils.ValidationErrorBuilder;
import isotope.modules.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/bo/administration/axes")
public class AxesController {

    private static final Integer MODE_AFFICHAGE = 0;
    private static final Integer MODE_CREATION = 1;
    private static final Integer MODE_EDITION = 2;

    @Autowired
    AxeTableFormValidator axeTableFormValidator;

    @Autowired
    AxeService axeService;

    @Autowired
    MessageSourceService messageSourceService;

    @Autowired
    TablePaginationService tablePaginationService;

    private void fillCommon(Model model, Locale locale) {
        model.addAttribute("title", "Liste des axes d'intervention");
        model.addAttribute("currentMenu", "administration");
        model.addAttribute("currentTab", "axes");
        model.addAttribute("objectType", "axes");
        model.addAttribute("usesModal", true);
        model.addAttribute("modalUrl", "/bo/administration/axes/edit/");
        model.addAttribute("canAddObject", true);
        model.addAttribute("canExportObject", false);
        model.addAttribute("postAsForm", true);
        model.addAttribute("deleteUrl", "/bo/administration/axes/");
        model.addAttribute("colonnes", tablePaginationService.processColumns(AxeTableForm.class, locale));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @GetMapping
    public String getList(Model model, Locale locale) {
        fillCommon(model, locale);
        List<AxeTableForm> objs = axeService.getList()
                .stream()
                .map(ExpBeanToLightBeanUtils::copyFrom)
                .collect(Collectors.toList());
        model.addAttribute("data", tablePaginationService.processData(objs, AxeTableForm.class));
        return "bo/listeEntites";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @GetMapping("/edit/{id}")
    public String getModalEdit(Model model, Locale locale, @PathVariable Long id) {
        if (id == -1) {
            return getModalCreate(model, locale);
        }
        fillCommon(model, locale);
        Axe axe = axeService.findById(id);
        model.addAttribute("titreModal", axe.getLibelle());
        model.addAttribute("data", tablePaginationService.processSingleObject(ExpBeanToLightBeanUtils.copyFrom(axe), locale));
        model.addAttribute("typeModal", MODE_EDITION);
        model.addAttribute("updateUrl", "/bo/administration/axes/" + id);
        model.addAttribute("deleteUrl", "/bo/administration/axes/" + id);
        return "bo/administration/modal_edition_objet";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @GetMapping("/new")
    public String getModalCreate(Model model, Locale locale) {
        fillCommon(model, locale);
        model.addAttribute("titreModal", "Créer un axe");
        model.addAttribute("data", tablePaginationService.processSingleObject(new AxeTableForm(), locale));
        model.addAttribute("typeModal", MODE_CREATION);
        model.addAttribute("createUrl", "/bo/administration/axes/");
        return "bo/administration/modal_edition_objet";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAxe(Locale locale,Model model, @PathVariable Long id) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        Map<String, String> mapErreur = new HashMap<>();
        Axe axe = axeService.findOne(id).orElse(null);
        if(CustomValidator.isEmpty(axe)){
            mapErreur.put("general_error", messageSourceService.getMessageSource().getMessage("error.attribut.unknown", null, locale));
        } else {
            try {
                String result = axeService.delete(id,user, locale);
                if(CustomValidator.isNotEmpty(result)){
                    mapErreur.put("general_error", result);
                }
            } catch (Exception e) {
                mapErreur.put("general_error", messageSourceService.getMessageSource().getMessage("error.technical", null, locale));
            }
        }
        return ResponseEntity.ok(mapErreur);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @PostMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE })
    public ResponseEntity<?> updateAxe(@PathVariable Long id, @Valid AxeTableForm axeTable,Model model, BindingResult bindingResult, Locale locale) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrorsIgnoreValue(bindingResult));
        }
        axeService.update(id, axeTable, user, bindingResult, locale);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrorsIgnoreValue(bindingResult));
        }
        return ResponseEntity.ok("");
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE })
    public ResponseEntity<?> createAxe(@Valid AxeTableForm axeTable,Model model, BindingResult bindingResult, Locale locale) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrorsIgnoreValue(bindingResult));
        }
        Axe axe = axeService.createOne(axeTable,user, bindingResult, locale);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrorsIgnoreValue(bindingResult));
        }
        return ResponseEntity.ok("");
    }

    @InitBinder("axeTableForm")
    public void bindValidator(WebDataBinder binder) {
        binder.addValidators(axeTableFormValidator);
    }
}
