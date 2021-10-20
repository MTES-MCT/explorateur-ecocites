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

package com.efficacity.explorateurecocites.ui.bo.controllers;

import com.efficacity.explorateurecocites.beans.Enums;
import com.efficacity.explorateurecocites.configuration.ServiceConfiguration;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.TemplateHashModel;
import isotope.modules.user.IJwtUser;
import isotope.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Created by tfossurier on 14/02/2018.
 *
 * ControllerAdvice permettant de mettre dans le model toutes les informations utiles
 * dans tous les controllers du basePackages
 * Exemple venu de https://www.concretepage.com/spring/spring-mvc/spring-mvc-controlleradvice-annotation-example
 */
@ControllerAdvice(basePackages = {"com.efficacity.explorateurecocites.ui.bo.controllers"} )
public class BoControllerAdvice {
    private static BeansWrapper wrapper = new BeansWrapperBuilder(Configuration.VERSION_2_3_21).build();

    @Autowired
    UserRepository userRepository;

    @Autowired
    ServiceConfiguration serviceConfiguration;


    @ModelAttribute
    public void globalAttributes(Model model, @AuthenticationPrincipal IJwtUser jwtUser) {
        Enums.ProfilsUtilisateur profil;
        if (jwtUser != null) {
            model.addAttribute("user", jwtUser);
            for(GrantedAuthority autho : jwtUser.getAuthorities()) {
                if (autho != null && autho.getAuthority().startsWith("ROLE_")) {
                    profil = Enums.ProfilsUtilisateur.getByCode(autho.getAuthority().substring(5));
                    if (profil != null) {
                        model.addAttribute("userProfileCode", profil.getCode());
                        model.addAttribute("userProfile", profil.getLibelle());
                        break;
                    }
                }
            }
        }
        model.addAttribute("modeBouchonActive", serviceConfiguration.getLoginBouchon());
        TemplateHashModel enumModels = wrapper.getEnumModels();
        model.addAttribute("enums", enumModels);
    }
}
