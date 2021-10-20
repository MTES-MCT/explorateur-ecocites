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

package com.efficacity.explorateurecocites.ui.bo.controllers.administration;

import com.efficacity.explorateurecocites.beans.biz.json.LibelleFoBean;
import com.efficacity.explorateurecocites.beans.model.LibelleFo;
import com.efficacity.explorateurecocites.beans.service.LibelleFoService;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.utils.ApplicationConstants;
import com.efficacity.explorateurecocites.utils.ValidationErrorBuilder;
import com.efficacity.explorateurecocites.utils.enumeration.LIBELLE_FO_TYPE;
import isotope.modules.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Locale;

@Controller
@PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
@RequestMapping("/bo/administration/wording")
public class WordingController {

    public static final String TYPE_STATIC = "wording_static";
    public static final String TYPE_DYNAMIC = "wording_dynamic";

    private LibelleFoService libelleFoService;
    private MessageSourceService messageSourceService;

    @Autowired
    public void setLibelleFoService(final LibelleFoService libelleFoService) {
        this.libelleFoService = libelleFoService;
    }

    @Autowired
    public void setMessageSourceService(final MessageSourceService messageSourceService) {
        this.messageSourceService = messageSourceService;
    }


    @GetMapping("/static")
    public String getStaticList(Model model) {
        model.addAttribute("title", "Gestion des libellés publics statiques");
        model.addAttribute("currentMenu", "administration");
        model.addAttribute("currentTab", TYPE_STATIC);
        model.addAttribute("libelles", libelleFoService.getList(LIBELLE_FO_TYPE.FO_STATIC));
        return "bo/administration/wordings_static";
    }

    @GetMapping("/dynamic")
    public String getStaticDynamic(Model model) {
        model.addAttribute("title", "Gestion des libellés publics dynamique");
        model.addAttribute("currentMenu", "administration");
        model.addAttribute("currentTab", TYPE_DYNAMIC);
        libelleFoService.fillDynamicNews(model);
        return "bo/administration/wordings_dynamic";
    }

    @PostMapping
    public ResponseEntity getModalEdit(@RequestBody LibelleFoBean form, BindingResult result, Model model, Locale locale) {
        JwtUser user = ((JwtUser) model.asMap().getOrDefault("user", null));
        if (user != null && !result.hasErrors()) {
            LibelleFo libelleFo = libelleFoService.save(form, user);
            if (libelleFo != null) {
                return ResponseEntity.ok(new LibelleFoBean(libelleFo));
            }
            result.reject(ApplicationConstants.ERROR_TECHNICAL, messageSourceService.getMessageSource().getMessage(ApplicationConstants.ERROR_TECHNICAL, null, locale));
        }
        return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrorsIgnoreValue(result));
    }

    @PostMapping("multiple")
    public ResponseEntity getModalEdit(@RequestBody List<LibelleFoBean> form, BindingResult result, Model model, Locale locale) {
        JwtUser user = ((JwtUser) model.asMap().getOrDefault("user", null));
        if (user != null && !result.hasErrors()) {
            libelleFoService.saveMultiple(form, user, result, locale);
            if (!result.hasErrors()) {
                return ResponseEntity.ok(true);
            }
        }
        return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrorsIgnoreValue(result));
    }
}
