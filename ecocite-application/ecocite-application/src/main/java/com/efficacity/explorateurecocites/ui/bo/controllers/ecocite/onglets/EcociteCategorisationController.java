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

package com.efficacity.explorateurecocites.ui.bo.controllers.ecocite.onglets;

import com.efficacity.explorateurecocites.beans.biz.EcociteBean;
import com.efficacity.explorateurecocites.beans.biz.EtapeBean;
import com.efficacity.explorateurecocites.beans.model.Ecocite;
import com.efficacity.explorateurecocites.beans.model.EtiquetteFinalite;
import com.efficacity.explorateurecocites.beans.model.Finalite;
import com.efficacity.explorateurecocites.beans.service.*;
import com.efficacity.explorateurecocites.ui.bo.forms.EtiquetteFrom;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.ui.bo.service.RightUserService;
import com.efficacity.explorateurecocites.utils.enumeration.CODE_FUNCTION_PROFILE;
import com.efficacity.explorateurecocites.utils.enumeration.ETAPE_ECOCITE;
import com.efficacity.explorateurecocites.utils.enumeration.ETAPE_STATUT;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import com.efficacity.explorateurecocites.utils.log.LoggingUtils;
import isotope.commons.exceptions.ForbiddenException;
import isotope.modules.security.JwtUser;
import isotope.modules.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by rxion on 12/02/2018.
 */
@Controller
@RequestMapping("bo/ecocites")
public class EcociteCategorisationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EcociteCategorisationController.class);
    private static final String PATH_LIST_MODAL = "bo/modal/listEtiquettes";

    @Autowired
    EcociteService ecociteService;

    @Autowired
    EtapeService etapeService;
    @Autowired
    EtiquetteAxeService etiquetteAxeServ;
    @Autowired
    EtiquetteIngenierieService etiquetteIngenierieServ;
    @Autowired
    EtiquetteFinaliteService etiquetteFinaliteServ;
    @Autowired
    AxeService axeService;
    @Autowired
    IngenierieService ingenierieService;
    @Autowired
    FinaliteService finaliteService;
    @Autowired
    AssoObjetObjectifService objetObjectifServ;
    @Autowired
    UserService userService;
    @Autowired
    RightUserService rightUserService;
    @Autowired
    MessageSourceService messageSourceService;

    @GetMapping("finalite/etiquettes/{idCat}/{idEcocite}")
    public String getListEtiquettesModalView(@PathVariable("idCat") Long idCat, @PathVariable("idEcocite") Long idEcocite, Model model) {
        model.addAttribute("currentCategory", "finalite");
        model.addAttribute("currentCategoryId", idCat);
        model.addAttribute("etiquettes", etiquetteFinaliteServ.getListSelectedByEcocite(idEcocite, idCat).getEtiquettes());
        return PATH_LIST_MODAL;
    }

    private void remplirAttributCommun(Model model, EcociteBean ecocite) {
        EtapeBean etapeBean = ecocite.getEtapeByStatus(ETAPE_ECOCITE.CARACTERISATION);
        model.addAttribute("ecocite", ecocite);
    }

    @GetMapping("finalite/{ecociteId}")
    public String getCategoryFinaliteTab(Model model, @PathVariable Long ecociteId) {
        gestionDroit(model,ecociteId);

        if(model.containsAttribute("canConsulte") && !(boolean) model.asMap().get("canConsulte")){
            return "bo/components/errorAccess";
        }

        EcociteBean ecocite = ecociteService.findOneEcocite(ecociteId);
        remplirAttributCommun(model, ecocite);
        List<Finalite> finalites = finaliteService.getList();
        model.addAttribute("finalites", finalites);
        Finalite finalite = finalites.get(0);
        model.addAttribute("finaliteSelec", finalite);
        model.addAttribute("ecociteObjectif", etiquetteFinaliteServ.getListSelectedByEcocite(ecociteId, finalites.get(0).getId()));
        return "bo/ecocites/ongletEdition/objectifEcocite";
    }

    @PostMapping("finalite/majEtiquette")
    @ResponseBody
    public ResponseEntity majAssoEcociteObjectif(Model model, @RequestBody @Valid EtiquetteFrom etiquetteFrom) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        Long idEcocite = Long.parseLong(etiquetteFrom.getIdObjet());
        Long idEtiquette = Long.parseLong(etiquetteFrom.getIdEtiquette());

        gestionDroit(model,idEcocite);
        if(model.containsAttribute("caracteristiqueLectureSeule") && !(boolean) model.asMap().get("caracteristiqueLectureSeule")) {
            if (objetObjectifServ.etiquettesExistByEcocite(idEcocite, idEtiquette)) {
                return ResponseEntity.badRequest().body("");
            }
            if (etiquetteFrom.getPoid() == 1
                    || etiquetteFrom.getPoid() == 2
                    && objetObjectifServ.countEtiquettesSecondByEcocite(idEcocite) < 5L
                    || etiquetteFrom.getPoid() == 3
                    && objetObjectifServ.countEtiquettesThirdByEcocite(idEcocite) < 5L) {
                EtiquetteFinalite objectif = etiquetteFinaliteServ.findById(Long.parseLong(etiquetteFrom.getIdEtiquette()));
                Supplier<Ecocite> findEcociteById = () -> ecociteService.findById(idEcocite);
                LoggingUtils.logActionSupplierE(LOGGER,LoggingUtils.ActionType.MODIFICATION,LoggingUtils.SecondaryType.CARACTERISATION_OBJECTIF,findEcociteById,user);
                return ResponseEntity.ok(objetObjectifServ.maj(idEcocite, TYPE_OBJET.ECOCITE, objectif, etiquetteFrom, user));
            }
        } else {
            throw new ForbiddenException("Vous n'avez pas les droits pour effectuer cette action");
        }
        return ResponseEntity.badRequest().body("");
    }

    @DeleteMapping("finalite/etiquette/{idEcocite}/{idAssoEcociteEtiquette}")
    @ResponseBody
    public void deleteAssoEcociteObjectif(Model model, @PathVariable("idEcocite") Long idEcocite,
                       @PathVariable("idAssoEcociteEtiquette") Long idAssoEcociteEtiquette) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        gestionDroit(model,idEcocite);

        if(model.containsAttribute("caracteristiqueLectureSeule") && !(boolean) model.asMap().get("caracteristiqueLectureSeule")){
            objetObjectifServ.delete(idEcocite, TYPE_OBJET.ECOCITE, idAssoEcociteEtiquette, user);
            Supplier<Ecocite> findEcociteById = () -> ecociteService.findById(idEcocite);
            LoggingUtils.logActionSupplierE(LOGGER,LoggingUtils.ActionType.MODIFICATION,LoggingUtils.SecondaryType.CARACTERISATION_OBJECTIF,findEcociteById,user);
        } else {
            throw new ForbiddenException("Vous n'avez pas les droits pour effectuer cette action");
        }
    }

    private void gestionDroit(Model model, Long ecociteId){
        EtapeBean etape = etapeService.toEtapeBean(etapeService.getEtapeByEcociteAndCode(ecociteId, ETAPE_ECOCITE.CARACTERISATION.getCode()));
        if(etape!=null) {
            if (ETAPE_STATUT.VALIDER.getCode().equals(etape.getStatut())) {
                model.addAttribute("titrePageValidation", etapeService.getTitrePage(etape));
                model.addAttribute("caracteristiqueLectureSeule", true);
                // On test si il peuvent modifier aprés validation pour afficher le bouton
                if (rightUserService.canModifObjet(model, ecociteId, CODE_FUNCTION_PROFILE.MODIF_CARACTERISATION_ECOCITE.getCode(), TYPE_OBJET.ECOCITE.getCode())) {
                    model.addAttribute("canEdit", true);
                } else {
                    model.addAttribute("canEdit", false);
                }
            } else {
                if (ETAPE_STATUT.ENVOYER.getCode().equals(etape.getStatut())) {
                    model.addAttribute("titrePageValidation", etapeService.getTitrePage(etape));
                    // On test si il peuvent valider une étape pour afficher le bouton
                    if (rightUserService.canModifObjet(model, ecociteId, CODE_FUNCTION_PROFILE.VALIDATE_CARACTERISATION_ECOCITE.getCode(), TYPE_OBJET.ECOCITE.getCode())) {
                        model.addAttribute("canValide", true);
                        model.addAttribute("caracteristiqueLectureSeule", false);
                    } else {
                        model.addAttribute("caracteristiqueLectureSeule", true);
                    }
                } else {
                    if (rightUserService.canModifObjet(model, ecociteId, CODE_FUNCTION_PROFILE.EDIT_CARACTERISATION_ECOCITE.getCode(), TYPE_OBJET.ECOCITE.getCode())) {
                        model.addAttribute("canEdit", true);
                        model.addAttribute("caracteristiqueLectureSeule", false);
                    } else {
                        model.addAttribute("caracteristiqueLectureSeule", true);
                        if (rightUserService.isHisObject(model, ecociteId, TYPE_OBJET.ECOCITE.getCode())) {
                            model.addAttribute("canConsulte", true);
                        } else {
                            // On vérifie que étape est validé, sinon il a pas le droit de voir.
                            if (ETAPE_STATUT.VALIDER.getCode().equals(etape.getStatut())) {
                                model.addAttribute("canConsulte", true);
                            } else {
                                model.addAttribute("canConsulte", false);
                            }
                        }
                    }
                }
            }
        }
    }
}
