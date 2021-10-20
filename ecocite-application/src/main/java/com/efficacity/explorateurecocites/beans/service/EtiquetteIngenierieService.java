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

package com.efficacity.explorateurecocites.beans.service;

import com.efficacity.explorateurecocites.beans.biz.AssoActionIngenierieBean;
import com.efficacity.explorateurecocites.beans.biz.ObjetEtiquettesWrapper;
import com.efficacity.explorateurecocites.beans.model.EtiquetteIngenierie;
import com.efficacity.explorateurecocites.beans.repository.EtiquetteIngenierieRepository;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.EtiquetteIngenierieForm;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import isotope.commons.exceptions.NotFoundException;
import isotope.commons.services.CrudEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 * <p>
 * Date de génération : 08/02/2018
 */
@Service
public class EtiquetteIngenierieService extends CrudEntityService<EtiquetteIngenierieRepository, EtiquetteIngenierie, Long> {
    public EtiquetteIngenierieService(EtiquetteIngenierieRepository repository) {
        super(repository);
    }

    @Autowired
    ActionService actionService;

    @Autowired
    AssoActionIngenierieService assoActionIngenierieService;

    @Autowired
    MessageSourceService messageSourceService;

    /**
     * Récupérer toues les etiquettes associées à l'ingénieire
     *
     * @param idIng Id de l'ingénieire
     * @return Liste des objets du type {@code AssoActionIngenierieBean}
     */
    public List<AssoActionIngenierieBean> getListByIngenierie(Long idIng) {

        return repository.findByIdIngenierie(idIng)
                .stream()
                .map(etiquette -> AssoActionIngenierieBean.createFrom(null, etiquette, null, 0))
                .collect(Collectors.toList());
    }


    public EtiquetteIngenierie findById(Long id) {
        return findOne(id)
                .orElseThrow(() ->
                        new NotFoundException("Etiquette d'ingénierie" + " '" + id + "' n'est pas trouvé"));
    }

    public List<AssoActionIngenierieBean> getListByIngenierieAndNotSelected(Long idIng, Set<Long> etiquettesSelected) {
        return repository.findByIdIngenierieAndIdNotIn(idIng, etiquettesSelected)
                .stream()
                .map(etiquette -> AssoActionIngenierieBean.createFrom(null, etiquette, null, 0))
                .collect(Collectors.toList());
    }

    public List<EtiquetteIngenierie> findAll() {
        return repository.findAll();
    }

    /**
     * Récupérer toutes les etiquette axes de la BDD
     *
     * @return un ensemble {@code LinkedHashMap} des axes avec l'id et le nom
     * @see #findAll()
     */
    public LinkedHashMap<String, String> getMap() {
        return findAll().stream()
                .collect(Collectors.toMap(e -> String.valueOf(e.getId()), EtiquetteIngenierie::getLibelle,
                        (k1, k2) -> k1,
                        LinkedHashMap::new)
                );
    }

    /**
     * Récupérer la liste des etiquettes de l'ingénierie disponibles
     * et celles qui ont été choisies
     *
     * @param idAction Id de l'action
     * @return objet du type {@code ActionEtiquettesWrapper}
     */
    public ObjetEtiquettesWrapper getListSelectedByAction(Long idAction, Long idIngenierie) {
        Set<Long> idsSeletcted = new HashSet<>();
        ObjetEtiquettesWrapper res = new ObjetEtiquettesWrapper();
        actionService.findOne(idAction).ifPresent(action -> {
            // Rechercher à la BDD les etiquettes de l'ingénierie associées à l'action
            List<AssoActionIngenierieBean> etqsSelected = assoActionIngenierieService.getListByAction(idAction)
                    .stream()
                    .map(actionIng -> {
                        EtiquetteIngenierie etq = repository.findById(actionIng.getIdEtiquetteIngenierie()).orElseThrow(() -> new EntityNotFoundException());;
                        // Ajouter l'id à la liste pour le filtrage de plus tard
                        idsSeletcted.add(etq.getId());
                        return AssoActionIngenierieBean.createFrom(actionIng.getId(), etq, action.getId(), actionIng.getPoid());
                    }).collect(Collectors.toList());
            // On recherche celles qui n'ont pas encore été sélectionées
            List<AssoActionIngenierieBean> etqs = CustomValidator.isEmpty(idsSeletcted) ?
                    getListByIngenierie(idIngenierie)
                    : getListByIngenierieAndNotSelected(idIngenierie, idsSeletcted);
            res.setEtiquettes(etqs);
            res.setEtiquettesSelected(etqsSelected);
        });
        return res;
    }

    public List<EtiquetteIngenierie> getList() {
        return repository.findAll();
    }

    public String delete(final Long id, final Locale locale) {
        if(assoActionIngenierieService.countByIngenierie(id) > 0){
            return messageSourceService.getMessageSource().getMessage("error.objet.suppression.liee", null, locale);
        } else {
            repository.deleteById(id);
            return "";
        }
    }

    public void update(final Long id, final EtiquetteIngenierieForm tableform) {
        EtiquetteIngenierie a = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());;
        a.setIdIngenierie(tableform.getIdIngenierie());
        a.setLibelle(tableform.getNom());
        a.setDescription(tableform.getDescription());
        repository.save(a);
    }

    public EtiquetteIngenierie createOne(final EtiquetteIngenierieForm tableform) {
        return repository.save(tableform.getEtiquetteIngenierie());
    }
}
