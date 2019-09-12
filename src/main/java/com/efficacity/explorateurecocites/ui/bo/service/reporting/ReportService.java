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

package com.efficacity.explorateurecocites.ui.bo.service.reporting;

import com.efficacity.explorateurecocites.beans.Enums;
import com.efficacity.explorateurecocites.beans.biz.ActionBean;
import com.efficacity.explorateurecocites.beans.biz.EcociteBean;
import com.efficacity.explorateurecocites.beans.biz.EtapeBean;
import com.efficacity.explorateurecocites.beans.biz.json.SimpleAction;
import com.efficacity.explorateurecocites.beans.biz.json.SimpleEcocite;
import com.efficacity.explorateurecocites.beans.model.AssoActionDomain;
import com.efficacity.explorateurecocites.beans.model.AssoActionIngenierie;
import com.efficacity.explorateurecocites.beans.model.AssoObjetObjectif;
import com.efficacity.explorateurecocites.beans.service.*;
import com.efficacity.explorateurecocites.utils.enumeration.ETAPE_ACTION_EDITION;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import isotope.modules.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ActionService actionService;
    private final EcociteService ecociteService;
    private final EtiquetteAxeService etiquetteAxeService;
    private final EtiquetteFinaliteService etiquetteFinaliteService;
    private final EtiquetteIngenierieService etiquetteIngenierieService;


    @Autowired
    public ReportService(final ActionService actionService, final EcociteService ecociteService, final EtiquetteAxeService etiquetteAxeService,
                         final EtiquetteFinaliteService etiquetteFinaliteService, final EtiquetteIngenierieService etiquetteIngenierieService) {
        this.actionService = actionService;
        this.ecociteService = ecociteService;
        this.etiquetteAxeService = etiquetteAxeService;
        this.etiquetteFinaliteService = etiquetteFinaliteService;
        this.etiquetteIngenierieService = etiquetteIngenierieService;
    }

    private <A> List<String> filterListEtq(final List<A> actionDomains,
                                                  final Integer poids, final Function<A, Integer> poidsMapper,
                                                  final Function<A, String> beanMapper) {
        return actionDomains.stream()
                .filter(f -> Objects.equals(poidsMapper.apply(f), poids))
                .map(beanMapper)
                .collect(Collectors.toList());
    }

    public List<String> filterDomain(final List<AssoActionDomain> actionDomains, final Integer poids) {
        return filterListEtq(actionDomains, poids, AssoActionDomain::getPoid,
                a -> etiquetteAxeService.findById(a.getIdDomain()).getLibelle());
    }

    public List<String> filterObjectifs(final List<AssoObjetObjectif> objetObjectifs, final Integer poids) {
        return filterListEtq(objetObjectifs, poids, AssoObjetObjectif::getPoid,
                a -> etiquetteFinaliteService.findById(a.getIdObjectif()).getLibelle());
    }

    public List<String> filterIngenierie(final List<AssoActionIngenierie> actionIngenieries, final Integer poids) {
        return filterListEtq(actionIngenieries, poids, AssoActionIngenierie::getPoid,
                a -> etiquetteIngenierieService.findById(a.getIdEtiquetteIngenierie()).getLibelle());
    }

    public String getCommentaireEtape(final ETAPE_ACTION_EDITION etape, final List<EtapeBean> etapes) {
        return etapes.stream()
                .filter(e -> e.getEtapeEnumActionEdition() == etape)
                .findFirst()
                .map(EtapeBean::getCommentaire)
                .orElse("");
    }

    public List<SimpleEcocite> getMapAutorizedEcocite(Model model) {
        JwtUser user = ((JwtUser) model.asMap().getOrDefault("user", null));
        List<EcociteBean> ecocites = new ArrayList<>();
        if (user != null) {
            Enums.ProfilsUtilisateur profil = null;
            if (model.containsAttribute("userProfileCode")) {
                profil = Enums.ProfilsUtilisateur.getByCode((String) model.asMap().get("userProfileCode"));
            }
            if (Enums.ProfilsUtilisateur.REFERENT_ECOCITE.equals(profil)
                    || Enums.ProfilsUtilisateur.ACTEUR_ECOCITE_AUTRE.equals(profil)) {
                ecocites = getEcociteForReferentEcocite(user);
            } else if (Enums.ProfilsUtilisateur.PORTEUR_ACTION.equals(profil)) {
                return getEcociteForReferentAction(user);
            } else {
                ecocites = ecociteService.findAllByOrderByNomAsc();
            }
        }
        return ecocites.stream()
                .map(e -> new SimpleEcocite(String.valueOf(e.getId()), e.getNom(),
                        actionService.findAllActionForEcocite(e)
                                .stream()
                                .map(a -> new SimpleAction(String.valueOf(a.getId()), a.getNomPublic()))
                                .sorted(Comparator.comparing(SimpleAction::getName))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    private List<EcociteBean> getEcociteForReferentEcocite(JwtUser user) {
        if (user.getAuthorities().contains(new SimpleGrantedAuthority(TYPE_OBJET.ECOCITE.getCode() + "_ALL"))) {
            return ecociteService.findAllByOrderByNomAsc();
        } else {
            final String ecociteCode = TYPE_OBJET.ECOCITE.getCode() + "_";
            List<Long> listId = user.getAuthorities().stream()
                    .filter(auth -> auth.getAuthority().startsWith(ecociteCode))
                    .map(auth -> Long.valueOf(auth.getAuthority().substring(8)))
                    .collect(Collectors.toList());

            return ecociteService.findAllByIdIn(listId);
        }
    }

    private List<SimpleEcocite> getEcociteForReferentAction(JwtUser user) {
        List<ActionBean> actions;
        if (user.getAuthorities().contains(new SimpleGrantedAuthority(TYPE_OBJET.ACTION.getCode() + "_ALL"))) {
            actions = actionService.findAllAction();
        } else {
            final String actionCode = TYPE_OBJET.ACTION.getCode() + "_";
            List<Long> listId = user.getAuthorities().stream()
                    .filter(auth -> auth.getAuthority().startsWith(actionCode))
                    .map(auth -> Long.valueOf(auth.getAuthority().substring(7)))
                    .collect(Collectors.toList());
            actions = actionService.findAllByIdIn(listId);
        }
        Map<Long, List<ActionBean>> actionMap = actions.stream()
                .filter(a -> a.getIdEcocite() != null)
                .collect(Collectors.groupingBy(ActionBean::getIdEcocite));
        return ecociteService
                .findAllByIdIn(actions.stream()
                        .filter(a -> a.getIdEcocite() != null)
                        .map(ActionBean::getIdEcocite)
                        .collect(Collectors.toList()))
                .stream()
                .map(e -> new SimpleEcocite(String.valueOf(e.getId()), e.getNom(),
                        actionMap.getOrDefault(e.getId(), new ArrayList<>()).stream()
                                .map(a -> new SimpleAction(String.valueOf(a.getId()), a.getNomPublic()))
                                .sorted(Comparator.comparing(SimpleAction::getName))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }
}
