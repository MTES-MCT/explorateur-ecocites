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


import com.efficacity.explorateurecocites.beans.biz.AssoIndicateurObjetBean;
import com.efficacity.explorateurecocites.beans.biz.ChartJsOptions;
import com.efficacity.explorateurecocites.beans.biz.IndicateurBean;
import com.efficacity.explorateurecocites.beans.model.*;
import com.efficacity.explorateurecocites.beans.service.*;
import com.efficacity.explorateurecocites.ui.bo.forms.AjoutIndicateurMesureForm;
import com.efficacity.explorateurecocites.ui.bo.forms.validator.AjoutIndicateurMesureFormValidator;
import com.efficacity.explorateurecocites.ui.bo.service.ChoixIndicateursService;
import com.efficacity.explorateurecocites.ui.bo.service.GrapheService;
import com.efficacity.explorateurecocites.ui.bo.service.RightUserService;
import com.efficacity.explorateurecocites.ui.bo.service.notifications.EmailNotificationService;
import com.efficacity.explorateurecocites.utils.enumeration.CODE_FUNCTION_PROFILE;
import com.efficacity.explorateurecocites.utils.enumeration.ETAPE_ECOCITE;
import com.efficacity.explorateurecocites.utils.enumeration.NATURE_INDICATEUR;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import com.efficacity.explorateurecocites.utils.log.LoggingUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import isotope.commons.exceptions.NotFoundException;
import isotope.modules.security.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by tfossurier on 10/01/2018.
 */
@Controller
@RequestMapping("/bo/ecocites/indicateur")
public class EcociteIndicateurMesureController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EcociteIndicateurMesureController.class);

    @Autowired
    EmailNotificationService emailNotificationService;
    @Autowired
    IndicateurService indicateurService;
    @Autowired
    EcociteService ecociteService;
    @Autowired
    ChoixIndicateursService choixIndicateursService;

    private AssoIndicateurObjetService assoIndicateurObjetService;
    private CibleIndicateurService cibleIndicateurService;
    private MesureIndicateurService mesureIndicateurService;
    private RightUserService rightUserService;
    private GrapheService grapheService;
    private AjoutIndicateurMesureFormValidator ajoutIndicateurMesureFormValidator;

    public EcociteIndicateurMesureController(final AssoIndicateurObjetService assoIndicateurObjetService, final CibleIndicateurService cibleIndicateurService,
                                             final MesureIndicateurService mesureIndicateurService, final RightUserService rightUserService,
                                             final GrapheService grapheService, final AjoutIndicateurMesureFormValidator ajoutIndicateurMesureFormValidator) {
        this.assoIndicateurObjetService = assoIndicateurObjetService;
        this.cibleIndicateurService = cibleIndicateurService;
        this.mesureIndicateurService = mesureIndicateurService;
        this.rightUserService = rightUserService;
        this.grapheService = grapheService;
        this.ajoutIndicateurMesureFormValidator = ajoutIndicateurMesureFormValidator;
    }

    private static final String MESURE = "mesure";
    private static final String CIBLE = "cible";

    @GetMapping("loadMesureIndicateurs/{IdEcocite}")
    public String loadMesureIndicateurs(Model model, @PathVariable Long IdEcocite) {

        gestionDroit(model, IdEcocite);

        if(model.containsAttribute("canConsulte") && !(boolean) model.asMap().get("canConsulte")) {
            return "bo/components/errorAccess";
        }

        List<AssoIndicateurObjetBean> listAssoIndicateurEcocite = assoIndicateurObjetService.findAllByEcocite(IdEcocite, null);
        listAssoIndicateurEcocite.sort(new Comparator<AssoIndicateurObjetBean>(){
            @Override
            public int compare(AssoIndicateurObjetBean asso1, AssoIndicateurObjetBean asso2){
                int comp=0;
                if(asso1.getIdIndicateur()!=null && asso2.getIdIndicateur()!=null) {
                    IndicateurBean indicateur1 = indicateurService.findOneIndicateur(asso1.getIdIndicateur());
                    IndicateurBean indicateur2 = indicateurService.findOneIndicateur(asso2.getIdIndicateur());
                    if(indicateur1!=null && indicateur2!=null){
                        if(indicateur1.getNature().equals(NATURE_INDICATEUR.REALISATIONS.getCode())){
                            if(indicateur2.getNature().equals(NATURE_INDICATEUR.RESULTATS.getCode())){comp=-1;}
                            if(indicateur2.getNature().equals(NATURE_INDICATEUR.IMPACTS.getCode())){comp=-1;}
                        }
                        else if(indicateur1.getNature().equals(NATURE_INDICATEUR.RESULTATS.getCode())){
                            if(indicateur2.getNature().equals(NATURE_INDICATEUR.REALISATIONS.getCode())){comp=1;}
                            if(indicateur2.getNature().equals(NATURE_INDICATEUR.IMPACTS.getCode())){comp=-1;}
                        }
                        else if(indicateur1.getNature().equals(NATURE_INDICATEUR.IMPACTS.getCode())){
                            if(indicateur2.getNature().equals(NATURE_INDICATEUR.REALISATIONS.getCode())){comp=1;}
                            if(indicateur2.getNature().equals(NATURE_INDICATEUR.RESULTATS.getCode())){comp=1;}
                        }
                    }
                }
                return comp;
            }
        });
        model.addAttribute("listAssoIndicateurEcocite", listAssoIndicateurEcocite);

        return "bo/ecocites/ongletEdition/indicateurMesure";
    }

    @GetMapping("loadEditMesureIndicateurs/{idAssoEcociteIndicateur}")
    public String loadEditMesureIndicateurs(Model model,@PathVariable Long idAssoEcociteIndicateur) {
        AssoIndicateurObjetBean assoIndicateurEcocite = assoIndicateurObjetService.findOneById(idAssoEcociteIndicateur);
        gestionDroit(model, assoIndicateurEcocite.getIdObjet());
        if(!(boolean) model.asMap().getOrDefault("canConsulte", false)){
            return "bo/components/errorAccess";
        }
        model.addAttribute("assoIndicateurEcocite", assoIndicateurEcocite);
        IndicateurBean indicateur =  new IndicateurBean(assoIndicateurEcocite.getIndicateur());
        Map<String, String> selectMap = indicateur.getTypeMesureEnum().getMapOptions();
        model.addAttribute("selectMap", selectMap);
        model.addAttribute("displayWithMap", selectMap != null && !selectMap.isEmpty());
        // On recherche les mesures
        List<MesureIndicateur> listMesure = mesureIndicateurService.findAllByIdAssoIndicateurObjetOrderByDateSaisieAsc(idAssoEcociteIndicateur);
        model.addAttribute("listMesure", listMesure);

        // On recherche les cible
        List<CibleIndicateur> listCible = cibleIndicateurService.findAllByIdAssoIndicateurObjetOrderByDateSaisieAsc(idAssoEcociteIndicateur);
        model.addAttribute("listCible", listCible);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            model.addAttribute("chartJsOptionsJson", ow.writeValueAsString(grapheService.getIndicateurCibleMesure(listCible, listMesure, indicateur)));
        } catch (JsonProcessingException e) {
            model.addAttribute("chartJsOptionsJson", ChartJsOptions.EMPTY_MODEL_JSON_STRING);
        }
        return "bo/ecocites/ongletEdition/indicateurMesureSaisie";
    }

    @GetMapping("graph/{idAssoActionIndicateur}")
    public ResponseEntity loadGraph(@PathVariable Long idAssoActionIndicateur) {
        AssoIndicateurObjetBean assoIndicateurAction = assoIndicateurObjetService.findOneById(idAssoActionIndicateur);
        IndicateurBean indicateur =  new IndicateurBean(assoIndicateurAction.getIndicateur());
        List<MesureIndicateur> listMesure = mesureIndicateurService.findAllByIdAssoIndicateurObjetOrderByDateSaisieAsc(idAssoActionIndicateur);
        List<CibleIndicateur> listCible = cibleIndicateurService.findAllByIdAssoIndicateurObjetOrderByDateSaisieAsc(idAssoActionIndicateur);
        return ResponseEntity.ok(grapheService.getIndicateurCibleMesure(listCible, listMesure, indicateur));
    }

    @DeleteMapping("{type}/{idAsso}")
    public ResponseEntity deleteAsso(Model model,@PathVariable String type, @PathVariable Long idAsso) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        //TODO gestion droits
        switch (type){
            case MESURE:
                Optional<MesureIndicateur> optAssoMesure=mesureIndicateurService.findOne(idAsso);
                mesureIndicateurService.delete(idAsso);
                if(optAssoMesure.isPresent()){
                    Long idAssoIndicateurObjet=optAssoMesure.get().getIdAssoIndicateurObjet();
                    Optional<AssoIndicateurObjet> optAssoIndicateurObjet=assoIndicateurObjetService.findOne(idAssoIndicateurObjet);
                    if(optAssoIndicateurObjet.isPresent()){
                        Supplier<Ecocite> findEcociteById = () -> ecociteService.findById(optAssoIndicateurObjet.get().getIdObjet());
                        LoggingUtils.logActionSupplierE(LOGGER, LoggingUtils.ActionType.SUPPRESSION, LoggingUtils.SecondaryType.RENSEIGNEMENT_INDICATEURS_MESURE, findEcociteById, user);
                        choixIndicateursService.updateEtapeForAsso(optAssoMesure.get().getIdAssoIndicateurObjet(),user);
                    }
                }
                break;
            case CIBLE:
                Optional<CibleIndicateur> optAssoCible=cibleIndicateurService.findOne(idAsso);
                cibleIndicateurService.delete(idAsso);
                if(optAssoCible.isPresent()){
                    Long idAssoIndicateurObjet=optAssoCible.get().getIdAssoIndicateurObjet();
                    Optional<AssoIndicateurObjet> optAssoIndicateurObjet=assoIndicateurObjetService.findOne(idAssoIndicateurObjet);
                    if(optAssoIndicateurObjet.isPresent()){
                        Supplier<Ecocite> findEcociteById = () -> ecociteService.findById(optAssoIndicateurObjet.get().getIdObjet());
                        LoggingUtils.logActionSupplierE(LOGGER, LoggingUtils.ActionType.SUPPRESSION, LoggingUtils.SecondaryType.RENSEIGNEMENT_INDICATEURS_CIBLE, findEcociteById, user);
                        choixIndicateursService.updateEtapeForAsso(optAssoCible.get().getIdAssoIndicateurObjet(),user);
                    }
                }
                break;
            default:
                throw new NotFoundException("Ce type d'association n'existe pas");
        }
        return ResponseEntity.ok(true);
    }

    @PostMapping("ajoutMesure")
    public String ajoutToEcocites(Model model,@RequestBody @Valid AjoutIndicateurMesureForm ajoutIndicateurMesureForm) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        AssoIndicateurObjetBean assoIndicateurEcocite = assoIndicateurObjetService.findOneById(Long.valueOf(ajoutIndicateurMesureForm.getIdAssoObjetIndicateur()));
        gestionDroit(model, assoIndicateurEcocite.getIdObjet());
        if((boolean) model.asMap().getOrDefault("canEdit", false)) {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.parseDefaulting(ChronoField.DAY_OF_MONTH, 1);
            builder.append(DateTimeFormatter.ofPattern("MM/yyyy"));
            DateTimeFormatter formatter = builder.toFormatter();
            model.addAttribute("type", ajoutIndicateurMesureForm.getType());
            if (MESURE.equals(ajoutIndicateurMesureForm.getType())) {
                MesureIndicateur mesureIndicateur = new MesureIndicateur();
                mesureIndicateur.setDateSaisie(LocalDate.parse(ajoutIndicateurMesureForm.getDate(), formatter).atStartOfDay());
                mesureIndicateur.setIdAssoIndicateurObjet(Long.valueOf(ajoutIndicateurMesureForm.getIdAssoObjetIndicateur()));
                mesureIndicateur.setValide(false);
                mesureIndicateur.setValeur(ajoutIndicateurMesureForm.getValue());
                model.addAttribute("class", "MesureIndicateur");
                MesureIndicateur mesure = mesureIndicateurService.create(mesureIndicateur);
                choixIndicateursService.updateEtapeForAsso(mesure.getIdAssoIndicateurObjet(), user);
                Supplier<Ecocite> findEcociteById = () -> ecociteService.findById(assoIndicateurEcocite.getIdObjet());
                LoggingUtils.logActionSupplierE(LOGGER, LoggingUtils.ActionType.AJOUT, LoggingUtils.SecondaryType.RENSEIGNEMENT_INDICATEURS_MESURE, findEcociteById, user);
                model.addAttribute("object", mesureIndicateur);
            } else if (CIBLE.equals(ajoutIndicateurMesureForm.getType())) {
                CibleIndicateur cibleIndicateur = new CibleIndicateur();
                cibleIndicateur.setDateSaisie(LocalDate.parse(ajoutIndicateurMesureForm.getDate(), formatter).atStartOfDay());
                cibleIndicateur.setIdAssoIndicateurObjet(Long.valueOf(ajoutIndicateurMesureForm.getIdAssoObjetIndicateur()));
                cibleIndicateur.setValeur(ajoutIndicateurMesureForm.getValue());
                cibleIndicateur.setValide(false);
                cibleIndicateurService.save(cibleIndicateur);
                Supplier<Ecocite> findEcociteById = () -> ecociteService.findById(assoIndicateurEcocite.getIdObjet());
                LoggingUtils.logActionSupplierE(LOGGER, LoggingUtils.ActionType.AJOUT, LoggingUtils.SecondaryType.RENSEIGNEMENT_INDICATEURS_CIBLE, findEcociteById, user);
                model.addAttribute("class", "CibleIndicateur");
                model.addAttribute("object", cibleIndicateur);
            }
            emailNotificationService.sendNotificationEmailEtapeEcocite(model,ETAPE_ECOCITE.MESURE_INDICATEUR,assoIndicateurEcocite.getIdObjet());

        }
        model.addAttribute("assoIndicateurEcocite", assoIndicateurEcocite);
        IndicateurBean indicateur =  new IndicateurBean(assoIndicateurEcocite.getIndicateur());
        Map<String, String> selectMap = indicateur.getTypeMesureEnum().getMapOptions();
        model.addAttribute("selectMap", selectMap);
        model.addAttribute("displayWithMap", selectMap != null && !selectMap.isEmpty());
        return "bo/ecocites/ongletEdition/indicateurMesureInfo";
    }

    private void gestionDroit(Model model, Long ecociteId){
        if(rightUserService.canModifObjet( model, ecociteId, CODE_FUNCTION_PROFILE.EDIT_MESURE_INDICATEUR_ECOCITE.getCode(), TYPE_OBJET.ECOCITE.getCode())) {
            model.addAttribute("canConsulte", true);
            model.addAttribute("canEdit", true);
            if(rightUserService.canModifObjet( model, ecociteId, CODE_FUNCTION_PROFILE.VALIDATE_MESURE_INDICATEUR_ECOCITE.getCode(), TYPE_OBJET.ECOCITE.getCode())){
                model.addAttribute("canValide", true);
            }  else {
                model.addAttribute("canValide", false);
            }
        } else {
            if(rightUserService.isHisObject(model, ecociteId, TYPE_OBJET.ECOCITE.getCode())){
                model.addAttribute("canConsulte", true);
            } else {
                model.addAttribute("canConsulte", false);
            }
        }
    }

    @InitBinder("ajoutIndicateurMesureForm")
    public void bindValidator(WebDataBinder binder) {
        binder.addValidators(ajoutIndicateurMesureFormValidator);
    }
}
