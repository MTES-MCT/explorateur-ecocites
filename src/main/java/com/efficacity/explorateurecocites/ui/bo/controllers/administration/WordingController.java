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

import com.efficacity.explorateurecocites.beans.biz.json.LibelleFoBean;
import com.efficacity.explorateurecocites.beans.model.LibelleFo;
import com.efficacity.explorateurecocites.beans.service.LibelleFoService;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
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
            result.reject("error.technical", messageSourceService.getMessageSource().getMessage("error.technical", null, locale));
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
