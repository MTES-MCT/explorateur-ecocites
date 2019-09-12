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
