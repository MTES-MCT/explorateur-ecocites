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

package com.efficacity.explorateurecocites.ui.bo.service;

import com.efficacity.explorateurecocites.beans.Enums;
import com.efficacity.explorateurecocites.beans.biz.EcociteBean;
import com.efficacity.explorateurecocites.beans.service.EcociteService;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import isotope.commons.exceptions.NotFoundException;
import isotope.modules.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by acaillard on 21/03/18.
 */
@Service
public class RightUserService {
    private final EcociteService ecociteService;

    @Autowired
    public RightUserService(final EcociteService ecociteService) {
        this.ecociteService = ecociteService;
    }


    /*
     * Peut modifier l'objet selon la fonction passé en paramatére
     */
    public boolean canModifObjet(final Model model, Long idObjet, String function, String typeObjet) {
        JwtUser user = ((JwtUser) model.asMap().getOrDefault("user", null));
        return user != null &&
                user.getAuthorities().contains(new SimpleGrantedAuthority(function)) &&
                (user.getAuthorities().contains(new SimpleGrantedAuthority(typeObjet + "_ALL")) ||
                        user.getAuthorities().contains(new SimpleGrantedAuthority(typeObjet + "_" + idObjet)));
    }

    public boolean isHisObject(final Model model, Long idObjet, String typeObjet) {
        JwtUser user = ((JwtUser) model.asMap().getOrDefault("user", null));
        return user != null &&
                (user.getAuthorities().contains(new SimpleGrantedAuthority(typeObjet + "_ALL")) ||
                        user.getAuthorities().contains(new SimpleGrantedAuthority(typeObjet + "_" + idObjet)));
    }

    public List<Long> getUserRightListeEcociteID(final Model model) {
        List<Long> listeEcociteId = new ArrayList<>();
        JwtUser user = ((JwtUser) model.asMap().getOrDefault("user", null));
        Enums.ProfilsUtilisateur profil = Enums.ProfilsUtilisateur.getByCode((String) model.asMap().getOrDefault("userProfileCode", null));
        if (user == null || profil == null) {
            return listeEcociteId;
        }
        if (Enums.ProfilsUtilisateur.REFERENT_ECOCITE.equals(profil) || Enums.ProfilsUtilisateur.ACTEUR_ECOCITE_AUTRE.equals(profil)) {
            if (user.getAuthorities().contains(new SimpleGrantedAuthority(TYPE_OBJET.ECOCITE.getCode() + "_ALL"))) {
                listeEcociteId = ecociteService.findAllByOrderByNomAsc().stream().map(EcociteBean::getId).collect(Collectors.toList());
            } else {
                final String code = TYPE_OBJET.ECOCITE.getCode() + "_";
                listeEcociteId = user.getAuthorities().stream()
                        .filter(a -> a.getAuthority().startsWith(code))
                        .map(a -> Long.valueOf(a.getAuthority().substring(8)))
                        .collect(Collectors.toList());
            }
        } else if (Enums.ProfilsUtilisateur.ACCOMPAGNATEUR.equals(profil) || Enums.ProfilsUtilisateur.ADMIN.equals(profil)) {
            //Pour les Acompagnateurs et Administrateurs
            for (EcociteBean ecocite : ecociteService.findAllByOrderByNomAsc()) {
                listeEcociteId.add(ecocite.getId());
            }
        }
        return listeEcociteId;
    }

    public boolean canExport(final Model model) {
        Enums.ProfilsUtilisateur profil = null;
        if (model.containsAttribute("userProfileCode")) {
            profil = Enums.ProfilsUtilisateur.getByCode((String) model.asMap().get("userProfileCode"));
        }
        // boolean autorisant l'export des donnes en csv depuis bo
        return (Enums.ProfilsUtilisateur.ACCOMPAGNATEUR.equals(profil)
                || Enums.ProfilsUtilisateur.ADMIN.equals(profil));
    }

    public boolean isAdmin(final Model model) {
        Enums.ProfilsUtilisateur profil = null;
        if (model.containsAttribute("userProfileCode")) {
            profil = Enums.ProfilsUtilisateur.getByCode((String) model.asMap().get("userProfileCode"));
        }
        // boolean identifiant l'administrateur
        return (Enums.ProfilsUtilisateur.ADMIN.equals(profil));
    }

    public static void checkAutority(Model model, TYPE_OBJET typeObjet, Long id) {
        String profil = (String) model.asMap().getOrDefault("userProfileCode", null);
        JwtUser user = ((JwtUser) model.asMap().getOrDefault("user", null));
        if (profil == null || user == null) {
            throw new NotFoundException("Cette ressource n'existe pas");
        }
        if (!profil.equals(Enums.ProfilsUtilisateur.ADMIN.getCode()) && !profil.equals(Enums.ProfilsUtilisateur.ACCOMPAGNATEUR.getCode())) {
            SimpleGrantedAuthority allOfType = new SimpleGrantedAuthority(typeObjet.getCode() + "_ALL");
            SimpleGrantedAuthority currentObject = new SimpleGrantedAuthority(typeObjet.getCode() + "_" + id.toString());
            if (user.getAuthorities().stream().noneMatch(autority -> Objects.equals(autority, currentObject) || Objects.equals(autority, allOfType))) {
                throw new NotFoundException("Cette ressource n'existe pas");
            }
        }
    }
    public static void checkAutority(Model model) {
        String profil = (String) model.asMap().getOrDefault("userProfileCode", null);
        JwtUser user = ((JwtUser) model.asMap().getOrDefault("user", null));
        if (profil == null || user == null) {
            throw new NotFoundException("Cette ressource n'existe pas");
        }
        if (!profil.equals(Enums.ProfilsUtilisateur.ADMIN.getCode()) && !profil.equals(Enums.ProfilsUtilisateur.ACCOMPAGNATEUR.getCode())) {
            throw new NotFoundException("Cette ressource n'existe pas");
        }
    }
}
