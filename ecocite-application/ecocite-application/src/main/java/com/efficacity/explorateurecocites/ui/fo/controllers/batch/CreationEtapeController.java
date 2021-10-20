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

package com.efficacity.explorateurecocites.ui.fo.controllers.batch;

import com.efficacity.explorateurecocites.beans.model.Action;
import com.efficacity.explorateurecocites.beans.model.Ecocite;
import com.efficacity.explorateurecocites.beans.repository.ActionRepository;
import com.efficacity.explorateurecocites.beans.repository.EcociteRepository;
import com.efficacity.explorateurecocites.beans.service.EtapeService;
import com.efficacity.explorateurecocites.beans.service.LibelleFoService;
import org.springframework.ui.Model;

import java.util.List;

/**
 * Created by tfossurier on 16/02/2018.
 */
//@Controller
public class CreationEtapeController {
//    @Autowired
    ActionRepository actionRepository;

//    @Autowired
    EcociteRepository ecociteRepository;

//    @Autowired
    EtapeService etapeService;

//    @Autowired
    LibelleFoService libelleFoServ;

//    @GetMapping("/batch/creationEtapeAction")
    public String creationAction(Model model) {
        List<Action> actions = actionRepository.findAll();

        if(actions != null && !actions.isEmpty()){
            // Plus rapide (~20s vs 10+min) que l'autre methode qui crée les actions une par une
            // mais ne vérifie pas si les étapes ont déjà été crée.
            etapeService.createBatchEtapeAction(actions);
        }
        return "redirect:/";
    }

//    @GetMapping("/batch/creationEtapeEcocite")
    public String creationEcocite(Model model) {
        List<Ecocite> ecocites = ecociteRepository.findAll();
        if(ecocites != null && !ecocites.isEmpty()){
            etapeService.createBatchEtapeEcocite(ecocites);
        }
        return "redirect:/";
    }
}
