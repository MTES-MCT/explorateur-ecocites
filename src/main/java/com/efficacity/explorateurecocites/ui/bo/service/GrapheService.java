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

package com.efficacity.explorateurecocites.ui.bo.service;

import com.efficacity.explorateurecocites.beans.biz.ChartJsOptions;
import com.efficacity.explorateurecocites.beans.biz.EcociteBean;
import com.efficacity.explorateurecocites.beans.biz.GraphDataSet;
import com.efficacity.explorateurecocites.beans.biz.IndicateurBean;
import com.efficacity.explorateurecocites.beans.model.*;
import com.efficacity.explorateurecocites.beans.service.*;
import com.efficacity.explorateurecocites.utils.Pair;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.beans.repository.specifications.AssoActionDomainSpecifications.hasActionIn;
import static com.efficacity.explorateurecocites.beans.specification.AssoObjetObjectifSpecifications.hasAction;
import static com.efficacity.explorateurecocites.beans.specification.AssoObjetObjectifSpecifications.hasIdObjetIn;
import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class GrapheService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GrapheService.class);

    @Autowired
    EtiquetteAxeService etiquetteAxeService;
    @Autowired
    AxeService axeService;
    @Autowired
    EtiquetteFinaliteService etiquetteFinaliteService;
    @Autowired
    FinaliteService finaliteService;
    @Autowired
    ActionService actionService;
    @Autowired
    AssoObjetObjectifService assoObjetObjectifService;
    @Autowired
    AssoActionDomainService assoActionDomainService;

    public Map<String, Collection<GraphDataSet>> getGraphDataSetObjectif(final List<EcociteBean> ecocites) {
        Map<Long, EtiquetteFinalite> etqs = etiquetteFinaliteService
                .findAll()
                .stream()
                .collect(Collectors.toMap(EtiquetteFinalite::getId, a -> a, (e1, e2) -> e2));
        Map<Long, Finalite> finalites = finaliteService
                .getList()
                .stream()
                .collect(Collectors.toMap(Finalite::getId, a -> a, (e1, e2) -> e2));
        return ecocites.stream()
                .collect(Collectors.toMap(e -> String.valueOf(e.getId()), e -> getGraphDataSetObjectif(e, etqs, finalites), (g1, g2) -> g1));
    }

    private Collection<GraphDataSet> getGraphDataSetObjectif(final EcociteBean e, final Map<Long, EtiquetteFinalite> etqs, final Map<Long, Finalite> finalites) {
        List<Action> actionBeans = actionService.findAllActionForEcocite(e);
        if (actionBeans != null && actionBeans.size() > 0) {
            List<AssoObjetObjectif> assoObjetObjectifs = assoObjetObjectifService.findAll(
                    where(hasIdObjetIn(actionBeans.stream().map(Action::getId).collect(Collectors.toList())))
                            .and(hasAction()));
            Map<String, AssoObjetObjectif> objectifMap = assoObjetObjectifs.stream()
                    .collect(Collectors.toMap(a -> (a.getIdObjet() + ":" + etqs.get(a.getIdObjectif()).getIdFinalite()), Function.identity(), (a1, a2) -> a1));
            return objectifMap.values()
                    .stream()
                    .map(a -> {
                        EtiquetteFinalite etq = etqs.get(a.getIdObjectif());
                        Finalite finalite = finalites.get(etq.getIdFinalite());
                        return new GraphDataSet(finalite.getLibelle(), 1.0f, finalite.getCodeCouleur());
                    })
                    .collect(Collectors.toMap(GraphDataSet::getLabel, Function.identity(), GraphDataSet::merge))
                    .values();
        }
        return new ArrayList<>();
    }

    public Collection<GraphDataSet> getGraphDataSetAllObjectif() {
        Map<Long, EtiquetteFinalite> etqs = etiquetteFinaliteService
                .findAll()
                .stream()
                .collect(Collectors.toMap(EtiquetteFinalite::getId, a -> a, (e1, e2) -> e2));
        Map<Long, Finalite> finalites = finaliteService
                .getList()
                .stream()
                .collect(Collectors.toMap(Finalite::getId, a -> a, (e1, e2) -> e2));
        List<AssoObjetObjectif> assoObjetObjectifs = assoObjetObjectifService.findAll(hasAction());
        Map<String, AssoObjetObjectif> objectifMap = assoObjetObjectifs.stream()
                .collect(Collectors.toMap(a -> (a.getIdObjet() + ":" + etqs.get(a.getIdObjectif()).getIdFinalite()), Function.identity(), (a1, a2) -> a1));
        return objectifMap.values()
                .stream()
                .map(a -> {
                    EtiquetteFinalite etq = etqs.get(a.getIdObjectif());
                    Finalite finalite = finalites.get(etq.getIdFinalite());
                    return new GraphDataSet(finalite.getLibelle(), 1.0f, finalites.get(etq.getIdFinalite()).getCodeCouleur());
                })
                .collect(Collectors.toMap(GraphDataSet::getLabel, Function.identity(), GraphDataSet::merge))
                .values();
    }

    public Map<String, Collection<GraphDataSet>> getGraphDataSetAxe(final List<EcociteBean> ecocites) {
        Map<Long, EtiquetteAxe> etqs = etiquetteAxeService
                .findAll()
                .stream()
                .collect(Collectors.toMap(EtiquetteAxe::getId, a -> a, (e1, e2) -> e2));
        Map<Long, Axe> finalites = axeService
                .getList()
                .stream()
                .collect(Collectors.toMap(Axe::getId, a -> a, (e1, e2) -> e2));
        return ecocites.stream()
                .collect(Collectors.toMap(e -> String.valueOf(e.getId()), e -> getGraphDataSetAxe(e, etqs, finalites), (g1, g2) -> g1));
    }

    private Collection<GraphDataSet> getGraphDataSetAxe(final EcociteBean e, final Map<Long, EtiquetteAxe> etqs, final Map<Long, Axe> finalites) {
        List<Action> actionBeans = actionService.findAllActionForEcocite(e);
        if (actionBeans != null && actionBeans.size() > 0) {
            List<AssoActionDomain> assoObjetObjectifs = assoActionDomainService.findAll(
                    where(hasActionIn(actionBeans.stream().map(Action::getId).collect(Collectors.toList()))));
            Map<String, AssoActionDomain> objectifMap = assoObjetObjectifs.stream()
                    .collect(Collectors.toMap(a -> (a.getIdAction() + ":" + etqs.get(a.getIdDomain()).getIdAxe()), Function.identity(), (a1, a2) -> a1));
            return objectifMap.values()
                    .stream()
                    .map(a -> {
                        EtiquetteAxe etq = etqs.get(a.getIdDomain());
                        Axe axe = finalites.get(etq.getIdAxe());
                        return new GraphDataSet(axe.getLibelle(), 1.0f, axe.getCodeCouleur1());
                    })
                    .collect(Collectors.toMap(GraphDataSet::getLabel, Function.identity(), GraphDataSet::merge))
                    .values();
        }
        return new ArrayList<>();
    }

    public Collection<GraphDataSet> getGraphDataSetAllAxe() {
        Map<Long, EtiquetteAxe> etqs = etiquetteAxeService
                .findAll()
                .stream()
                .collect(Collectors.toMap(EtiquetteAxe::getId, a -> a, (e1, e2) -> e2));
        Map<Long, Axe> finalites = axeService
                .getList()
                .stream()
                .collect(Collectors.toMap(Axe::getId, a -> a, (e1, e2) -> e2));
        List<AssoActionDomain> assoObjetObjectifs = assoActionDomainService.findAll();
        Map<String, AssoActionDomain> objectifMap = assoObjetObjectifs.stream()
                .collect(Collectors.toMap(a -> (a.getIdAction() + ":" + etqs.get(a.getIdDomain()).getIdAxe()), Function.identity(), (a1, a2) -> a1));
        return objectifMap.values()
                .stream()
                .map(a -> {
                    EtiquetteAxe etq = etqs.get(a.getIdDomain());
                    Axe axe = finalites.get(etq.getIdAxe());
                    return new GraphDataSet(axe.getLibelle(), 1.0f, axe.getCodeCouleur1());
                })
                .collect(Collectors.toMap(GraphDataSet::getLabel, Function.identity(), GraphDataSet::merge))
                .values();
    }

    public ChartJsOptions getIndicateurCibleMesure(final List<CibleIndicateur> listCible, final List<MesureIndicateur> listMesure, final IndicateurBean indicateur) {
        Map<LocalDateTime, Pair<String, String>> mapValue = new TreeMap<>();
        listCible.forEach(a -> mapValue.put(a.getDateSaisie(), new Pair<>(a.getValeur(), "")));
        listMesure.forEach(a -> {
            if (mapValue.containsKey(a.getDateSaisie())) {
                mapValue.put(a.getDateSaisie(), new Pair<>(mapValue.get(a.getDateSaisie()).getKey(), a.getValeur()));
            } else {
                mapValue.put(a.getDateSaisie(), new Pair<>("", a.getValeur()));
            }
        });
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<String> labels = mapValue.keySet().stream().map(a -> a.format(formatter)).collect(Collectors.toList());
        List<GraphDataSet> res = new ArrayList<>();
        res.add(new GraphDataSet("Mesures", mapValue.values().stream().map(Pair::getValue).collect(Collectors.toList()), new Color(70, 136, 241)));
        res.add(new GraphDataSet("Cibles", mapValue.values().stream().map(Pair::getKey).collect(Collectors.toList()), new Color(131, 185, 58)));
        return new ChartJsOptions(res, labels, indicateur.getTypeMesureEnum().getMapOptions());
    }

    public void fillGraphModel(Model model, List<EcociteBean> ecociteBeans) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {

            Map<String, String> ecociteMap = ecociteBeans.stream().collect(Collectors.toMap(e -> String.valueOf(e.getId()), EcociteBean::getNom));
            ecociteMap.put("all", "Toutes les écocités");
            String ecociteMapJson = ow.writeValueAsString(ecociteMap);
            model.addAttribute("ecociteMapJson", ecociteMapJson);

            List<String> etqsFinalite = finaliteService.getList().stream().map(Finalite::getLibelle).collect(Collectors.toList());
            String finalitesJson = ow.writeValueAsString(etqsFinalite);
            model.addAttribute("finalitesJson", finalitesJson);

            List<String> etqsDomaines = axeService.getList().stream().map(Axe::getLibelle).collect(Collectors.toList());
            String axeJson = ow.writeValueAsString(etqsDomaines);
            model.addAttribute("axeJson", axeJson);

            Map<String, Collection<GraphDataSet>> ecociteFinaliteGraphMap = this.getGraphDataSetObjectif(ecociteBeans);
            ecociteFinaliteGraphMap.put("all", this.getGraphDataSetAllObjectif());
            String ecociteFinaliteGraphMapJson = ow.writeValueAsString(ecociteFinaliteGraphMap);
            model.addAttribute("ecociteFinaliteGraphMapJson", ecociteFinaliteGraphMapJson);

            Map<String, Collection<GraphDataSet>> ecociteAxeGraphMap = this.getGraphDataSetAxe(ecociteBeans);
            ecociteAxeGraphMap.put("all", this.getGraphDataSetAllAxe());
            String ecociteAxeGraphMapJson = ow.writeValueAsString(ecociteAxeGraphMap);
            model.addAttribute("ecociteAxeGraphMapJson", ecociteAxeGraphMapJson);
        } catch (JsonProcessingException e) {
            //Ne fais rien
            LOGGER.warn("{}", "Json Processing exception");
        }
    }
}
