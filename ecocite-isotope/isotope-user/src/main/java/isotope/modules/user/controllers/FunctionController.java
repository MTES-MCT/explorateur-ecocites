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

package isotope.modules.user.controllers;

import isotope.modules.user.IJwtUser;
import isotope.modules.user.controllers.beans.Path;
import isotope.modules.user.lightbeans.FunctionLightBean;
import isotope.modules.user.service.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Ce controller permet d'intéragir avec les fonctions de l'application
 *
 * Created by bbauduin on 21/12/2016.
 */
@RestController
@RequestMapping("/api/admin/function")
public class FunctionController {

    @Autowired
    FunctionService functionService;

    /**
     * Indique si un path donné est autorisé (s'il correspond à une fonction connue)
     */
    @RequestMapping(method = RequestMethod.POST)
    public boolean isAuthorized(@AuthenticationPrincipal IJwtUser user, @RequestBody Path path) {
        return functionService.isAuthorized(user, path.getPath());
    }

    /**
     * @param user
     * @return la liste de toutes les fonctions connues du service
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<FunctionLightBean> getFunctions(@AuthenticationPrincipal IJwtUser user) {
        return functionService.getFunctions();
    }

}
