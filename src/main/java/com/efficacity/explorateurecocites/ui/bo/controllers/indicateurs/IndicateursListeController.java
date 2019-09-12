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

package com.efficacity.explorateurecocites.ui.bo.controllers.indicateurs;

import com.efficacity.explorateurecocites.beans.Enums;
import com.efficacity.explorateurecocites.beans.biz.table.IndicateursTable;
import com.efficacity.explorateurecocites.beans.service.AxeService;
import com.efficacity.explorateurecocites.beans.service.EtiquetteAxeService;
import com.efficacity.explorateurecocites.beans.service.EtiquetteFinaliteService;
import com.efficacity.explorateurecocites.beans.service.IndicateurService;
import com.efficacity.explorateurecocites.ui.bo.forms.ListeFiltrageForm;
import com.efficacity.explorateurecocites.ui.bo.service.RightUserService;
import com.efficacity.explorateurecocites.ui.bo.service.TablePaginationService;
import com.efficacity.explorateurecocites.utils.ExpBeanToLightBeanUtils;
import com.efficacity.explorateurecocites.utils.enumeration.*;
import isotope.modules.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by rxion on 20/02/2018.
 */
@Controller
@RequestMapping("/bo/indicateurs")
public class IndicateursListeController {

    @Autowired
    IndicateurService indicateurService;
    @Autowired
    TablePaginationService tablePaginationService;
    @Autowired
    AxeService axeServ;
    @Autowired
    EtiquetteAxeService etiquetteAxeService;
    @Autowired
    EtiquetteFinaliteService etiquetteFinaliteService ;
    @Autowired
    RightUserService rightUserService;

    private void fillFiltre(Model model, Locale locale) {
        Enums.ProfilsUtilisateur profil = null;
        if (model.containsAttribute("userProfileCode")){
            profil = Enums.ProfilsUtilisateur.getByCode((String) model.asMap().get("userProfileCode"));
        }
        JwtUser user = null;
        if(Enums.ProfilsUtilisateur.ACTEUR_ECOCITE_AUTRE.equals(profil)) {
            model.addAttribute("canAddObject", false);
        } else {
            model.addAttribute("canAddObject", true);
        }
        HashMap<String, List<Object>> filtres = new LinkedHashMap<>();
        filtres.put("nomLong", new LinkedList<>(Arrays.asList("Nom long", "text", null)));
        filtres.put("nomCourt", new LinkedList<>(Arrays.asList("Nom court", "text", null)));
        filtres.put("nature", new LinkedList<>(Arrays.asList("Nature", "select", NATURE_INDICATEUR.toMap())));
        filtres.put("echelle", new LinkedList<>(Arrays.asList("Echelle", "select", ECHELLE_INDICATEUR.toMap())));
        filtres.put("origine", new LinkedList<>(Arrays.asList("Origine", "text", ORIGINE_INDICATEUR.toMap())));
        filtres.put("objectifs", new LinkedList<>(Arrays.asList("Objectifs", "select", etiquetteFinaliteService.getMap())));
        filtres.put("domaines", new LinkedList<>(Arrays.asList("Domaine d'action", "select", etiquetteAxeService.getMap())));
        filtres.put("etatValidation", new LinkedList<>(Arrays.asList("Etat de validation", "select", ETAT_VALIDATION.toMap())));
        filtres.put("etatBibliotheque", new LinkedList<>(Arrays.asList("Etat de publication", "select", ETAT_BIBLIOTHEQUE.toMap())));
        model.addAttribute("filtres", filtres);
        model.addAttribute("urlFilter", "/bo/indicateurs/filtrer");
    }

    @GetMapping
    public String index(ListeFiltrageForm filtres, Model model, BindingResult result, Locale locale) {
        Enums.ProfilsUtilisateur profil = null;
        if (model.containsAttribute("userProfileCode")){
            profil = Enums.ProfilsUtilisateur.getByCode((String) model.asMap().get("userProfileCode"));
        }
        JwtUser user = null;
        fillFiltre(model,locale);
        // Par defaut on met etat validé pour les non admin et non accompagnateur
        if(!(Enums.ProfilsUtilisateur.ACCOMPAGNATEUR.equals(profil) || Enums.ProfilsUtilisateur.ADMIN.equals(profil))){
            user = ((JwtUser) model.asMap().getOrDefault("user", null));
        }
        model.addAttribute("title", "Liste des indicateurs");
        model.addAttribute("currentMenu", "indicateurs");
        model.addAttribute("objectType", "indicateurs");
        model.addAttribute("usesModal", true);
        model.addAttribute("modalUrl", "/bo/indicateurs/edition/liste/");
        model.addAttribute("canAddObject", true);
        model.addAttribute("canExportObject", rightUserService.canExport(model));
        model.addAttribute("colonnes", tablePaginationService.processColumns(IndicateursTable.class, locale));
        List<IndicateursTable> objs = indicateurService.getListFiltre(filtres.getFiltres(), user)
                .stream()
                .map(ExpBeanToLightBeanUtils::copyFrom).collect(Collectors.toList());
        model.addAttribute("data", tablePaginationService.processData(objs, IndicateursTable.class));
        model.addAttribute("modalOpen", true);
        model.addAttribute("hasExportAction", true);
        model.addAttribute("date", LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")));
        model.addAttribute("exportUrl", "/bo/rapports/" + "indicateurs" + "/");
        model.addAttribute("selectedFiltres", filtres.getFiltres());
        return "bo/listeEntites";
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String soumissionFiltreredIndex(ListeFiltrageForm filtres, Model model, BindingResult result, Locale locale) {
        return index(filtres, model, result, locale);
    }

    @PostMapping(value="filtrer", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String soumissionFiltrer(ListeFiltrageForm filtres, Model model,
                                          BindingResult result, Locale locale) {

        Enums.ProfilsUtilisateur profil = null;
        if (model.containsAttribute("userProfileCode")){
            profil = Enums.ProfilsUtilisateur.getByCode((String) model.asMap().get("userProfileCode"));
        }
        JwtUser user = null;

        // Par defaut on met etat validé pour les non admin et non accompagnateur
        if(!(Enums.ProfilsUtilisateur.ACCOMPAGNATEUR.equals(profil) || Enums.ProfilsUtilisateur.ADMIN.equals(profil))){
            user = ((JwtUser) model.asMap().getOrDefault("user", null));
        }
        model.addAttribute("currentMenu", "indicateurs");
        model.addAttribute("objectType", "indicateurs");
        model.addAttribute("usesModal", true);
        model.addAttribute("modalUrl", "/bo/indicateurs/edition/liste/");
        model.addAttribute("canAddObject", true);
        model.addAttribute("colonnes", tablePaginationService.processColumns(IndicateursTable.class, locale));
        List<IndicateursTable> objs = indicateurService.getListFiltre(filtres.getFiltres(), user)
                .stream()
                .map(ExpBeanToLightBeanUtils::copyFrom).collect(Collectors.toList());
        model.addAttribute("data", tablePaginationService.processData(objs, IndicateursTable.class));
        model.addAttribute("modalOpen", true);
        model.addAttribute("hasExportAction", true);
        model.addAttribute("date", LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")));
        model.addAttribute("exportUrl", "/bo/rapports/" + "indicateurs" + "/");

        return "bo/pagination/liste-pagination-tableau";
    }
}
