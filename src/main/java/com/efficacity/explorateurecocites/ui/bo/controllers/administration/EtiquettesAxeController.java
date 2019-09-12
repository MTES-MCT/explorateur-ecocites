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

import com.efficacity.explorateurecocites.beans.biz.table.EtiquetteAxeTable;
import com.efficacity.explorateurecocites.beans.model.Axe;
import com.efficacity.explorateurecocites.beans.model.EtiquetteAxe;
import com.efficacity.explorateurecocites.beans.service.AxeService;
import com.efficacity.explorateurecocites.beans.service.EtiquetteAxeService;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.EtiquetteAxeForm;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.helpers.SelectOption;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.ui.bo.service.TablePaginationService;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import com.efficacity.explorateurecocites.utils.ExpBeanToLightBeanUtils;
import isotope.commons.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/bo/administration/domaines")
public class EtiquettesAxeController {

    private static final Integer MODE_AFFICHAGE = 0;
    private static final Integer MODE_CREATION = 1;
    private static final Integer MODE_EDITION = 2;

    public static final String TYPE = "domaines";

    @Autowired
    EtiquetteAxeService etiquetteAxeService;

    @Autowired
    MessageSourceService messageSourceService;

    @Autowired
    TablePaginationService tablePaginationService;

    @Autowired
    AxeService axeService;

    private void fillCommon(Model model, Locale locale) {
        model.addAttribute("title", "Liste des étiquettes domaines d'action");
        model.addAttribute("currentMenu", "administration");
        model.addAttribute("currentTab", TYPE);
        model.addAttribute("objectType", TYPE);
        model.addAttribute("usesModal", true);
        model.addAttribute("modalUrl", "/bo/administration/" + TYPE + "/edit/");
        model.addAttribute("canAddObject", true);
        model.addAttribute("canExportObject", false);
        model.addAttribute("deleteUrl", "/bo/administration/" +  TYPE + "/");
        model.addAttribute("colonnes", tablePaginationService.processColumns(EtiquetteAxeTable.class, locale));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @GetMapping
    public String getList(Model model, Locale locale) {
        fillCommon(model, locale);
        List<Axe> axeList = axeService.getList();
        List<EtiquetteAxeTable> objs = etiquetteAxeService.getList()
                .stream()
                .map(ea -> ExpBeanToLightBeanUtils.copyFrom(ea, axeList.stream().filter(a -> Objects.equals(ea.getIdAxe(), a.getId())).findFirst().orElseThrow(NotFoundException::new)))
                .collect(Collectors.toList());
        model.addAttribute("data", tablePaginationService.processData(objs, EtiquetteAxeTable.class));
        return "bo/listeEntites";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @GetMapping("/edit/{id}")
    public String getModalEdit(Model model, Locale locale, @PathVariable Long id) {
        if (id == -1) {
            return getModalCreate(model, locale);
        }
        fillCommon(model, locale);
        EtiquetteAxe etiquetteAxe = etiquetteAxeService.findById(id);
        model.addAttribute("titreModal", etiquetteAxe.getLibelle());
        List<SelectOption> selectOptions = axeService.getList().stream().map(a -> new SelectOption(String.valueOf(a.getId()), a.getLibelle())).collect(Collectors.toList());
        model.addAttribute("data", tablePaginationService.processSingleObject(ExpBeanToLightBeanUtils.copyFrom(etiquetteAxe, selectOptions), locale));
        model.addAttribute("typeModal", MODE_EDITION);
        model.addAttribute("updateUrl", "/bo/administration/" + TYPE + "/" + id);
        model.addAttribute("deleteUrl", "/bo/administration/" + TYPE + "/" + id);
        return "bo/administration/modal_edition_objet";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @GetMapping("/new")
    public String getModalCreate(Model model, Locale locale) {
        fillCommon(model, locale);
        EtiquetteAxeForm etiquetteAxeTableForm = new EtiquetteAxeForm();
        model.addAttribute("titreModal", "Créer un domaine d'action");
        etiquetteAxeTableForm.setIdAxeDefaults(axeService.getList().stream().map(a -> new SelectOption(String.valueOf(a.getId()), a.getLibelle())).collect(Collectors.toList()));
        model.addAttribute("data", tablePaginationService.processSingleObject(etiquetteAxeTableForm, locale));
        model.addAttribute("typeModal", MODE_CREATION);
        model.addAttribute("createUrl", "/bo/administration/" + TYPE + "/");
        return "bo/administration/modal_edition_objet";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(Locale locale, @PathVariable Long id) {
        Map<String, String> mapErreur = new HashMap<>();
        EtiquetteAxe etiquetteAxe = etiquetteAxeService.findOne(id).orElse(null);
        if(CustomValidator.isEmpty(etiquetteAxe)){
            mapErreur.put("general_error", messageSourceService.getMessageSource().getMessage("error.attribut.unknown", null, locale));
        } else {
            try {
                String result = etiquetteAxeService.delete(id, locale);
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
    @PostMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid EtiquetteAxeForm tableform, @PathVariable Long id) {
        etiquetteAxeService.update(id, tableform);
        return ResponseEntity.ok("");
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAnyAuthority('ROLE_ADMIN_TECH')")
    public ResponseEntity<?> create(@RequestBody @Valid EtiquetteAxeForm tableform) {
        EtiquetteAxe etiquetteAxe = etiquetteAxeService.createOne(tableform);
        if (etiquetteAxe.getId() != null) {
            return ResponseEntity.ok("");
        } else {
            return ResponseEntity.badRequest().body("");
        }
    }
}
