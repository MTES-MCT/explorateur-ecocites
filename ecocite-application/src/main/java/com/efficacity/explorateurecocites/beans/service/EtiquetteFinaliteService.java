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

import com.efficacity.explorateurecocites.ajaris.enums.MediaModificationOriginType;
import com.efficacity.explorateurecocites.beans.biz.AssoObjetObjectifBean;
import com.efficacity.explorateurecocites.beans.biz.ObjetEtiquettesWrapper;
import com.efficacity.explorateurecocites.beans.model.EtiquetteFinalite;
import com.efficacity.explorateurecocites.beans.repository.EtiquetteFinaliteRepository;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.EtiquetteFinaliteForm;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import isotope.commons.exceptions.NotFoundException;
import isotope.commons.services.CrudEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.beans.specification.EtiquetteFinaliteSpecifications.hasIdIn;
import static org.springframework.data.jpa.domain.Specification.where;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 * <p>
 * Date de génération : 08/02/2018
 */
@Service
public class EtiquetteFinaliteService extends CrudEntityService<EtiquetteFinaliteRepository, EtiquetteFinalite, Long> {
    public EtiquetteFinaliteService(EtiquetteFinaliteRepository repository) {
        super(repository);
    }

    @Autowired
    ActionService actionService;

    @Autowired
    EcociteService ecociteService;

    @Autowired
    AssoObjetObjectifService assoObjetObjectifService;

    @Autowired
    MessageSourceService messageSourceService;

    @Autowired
    AssoIndicateurObjectifService assoIndicateurObjectifService;

    @Autowired
    MediaModificationService mediaModificationService;
    /**
     * Récupérer toues les etiquettes associées à la finalité
     *
     * @param idFinalite Id de la finalité
     * @return Liste des objets du type {@code AssoActionObjectifBean}
     */
    public List<AssoObjetObjectifBean> getListByFinalite(Long idFinalite) {

        return repository.findByIdFinalite(idFinalite)
                .stream()
                .map(etiquette -> AssoObjetObjectifBean.createFrom(null, etiquette, null, 0))
                .collect(Collectors.toList());
    }

    public List<EtiquetteFinalite> findAll() {
        return repository.findAll();
    }

    public List<EtiquetteFinalite> findAll(List<Long> ids) {
        return repository.findAllById(ids);
    }
    /**
     * Récupérer toutes les etiquette axes de la BDD
     *
     * @return un ensemble {@code LinkedHashMap} des axes avec l'id et le nom
     * @see #findAll()
     */
    public LinkedHashMap<String, String> getMap() {
        return findAll().stream()
                .collect(Collectors.toMap(e -> String.valueOf(e.getId()), EtiquetteFinalite::getLibelle,
                        (k1, k2) -> k1,
                        LinkedHashMap::new)
                );
    }


    public EtiquetteFinalite findById(Long id) {
        return findOne(id)
                .orElseThrow(() ->
                        new NotFoundException("Etiquette de finalité" + " '" + id + "' n'est pas trouvé"));
    }

    public List<AssoObjetObjectifBean> getListByFianliteAndNotSelected(Long idFinalite, Set<Long> etiquettesSelected) {
        return repository.findByIdFinaliteAndIdNotIn(idFinalite, etiquettesSelected)
                .stream()
                .map(etiquette -> AssoObjetObjectifBean.createFrom(null, etiquette, null, 0))
                .collect(Collectors.toList());
    }

    /**
     * Récupérer la liste des etiquettes de la finalité disponibles
     * et celles qui ont été choisies
     *
     * @param idAction Id de l'action
     * @return objet du type {@code ActionEtiquettesWrapper}
     */
    public ObjetEtiquettesWrapper getListSelectedByAction(Long idAction, Long idFinalite) {
        Set<Long> idsSeletcted = new HashSet<>();
        ObjetEtiquettesWrapper res = new ObjetEtiquettesWrapper();
        actionService.findOne(idAction).ifPresent(action -> {
            // Rechercher à la BDD les etiquettes de l'finalité associées à l'action
            List<AssoObjetObjectifBean> etqsSelected = assoObjetObjectifService.getListByAction(idAction)
                    .stream()
                    .map(actionObj -> {
                        EtiquetteFinalite etq = repository.findById(actionObj.getIdObjectif()).orElseThrow(() -> new EntityNotFoundException());;
                        // Ajouter l'id à la liste pour le filtrage de plus tard
                        idsSeletcted.add(etq.getId());
                        return AssoObjetObjectifBean.createFrom(actionObj.getId(), etq, action.getId(), actionObj.getPoid());
                    }).collect(Collectors.toList());
            // On recherche celles qui n'ont pas encore été sélectionées
            List<AssoObjetObjectifBean> etqs = CustomValidator.isEmpty(idsSeletcted) ?
                    getListByFinalite(idFinalite)
                    : getListByFianliteAndNotSelected(idFinalite, idsSeletcted);

            res.setEtiquettes(etqs);
            res.setEtiquettesSelected(etqsSelected);
        });
        return res;
    }

    /**
     * Récupérer la liste des etiquettes de la finalité disponibles
     * et celles qui ont été choisies
     *
     * @param idEcocite Id de l'ecocite
     * @return objet du type {@code ActionEtiquettesWrapper}
     */
    public ObjetEtiquettesWrapper getListSelectedByEcocite(Long idEcocite, Long idFinalite) {
        Set<Long> idsSeletcted = new HashSet<>();
        ObjetEtiquettesWrapper res = new ObjetEtiquettesWrapper();
        ecociteService.findOne(idEcocite).ifPresent(ecocite -> {
            // Rechercher à la BDD les etiquettes de l'finalité associées à l'action
            List<AssoObjetObjectifBean> etqsSelected = assoObjetObjectifService.getListByEcocite(idEcocite)
                    .stream()
                    .map(actionObj -> {
                        EtiquetteFinalite etq = repository.findById(actionObj.getIdObjectif()).orElseThrow(() -> new EntityNotFoundException());;
                        idsSeletcted.add(etq.getId());
                        return AssoObjetObjectifBean.createFrom(actionObj.getId(), etq, ecocite.getId(), actionObj.getPoid());
                    }).collect(Collectors.toList());
            // On recherche celles qui n'ont pas encore été sélectionées
            List<AssoObjetObjectifBean> etqs = CustomValidator.isEmpty(idsSeletcted) ?
                    getListByFinalite(idFinalite)
                    : getListByFianliteAndNotSelected(idFinalite, idsSeletcted);
            res.setEtiquettes(etqs);
            res.setEtiquettesSelected(etqsSelected);
        });
        return res;
    }

    public List<EtiquetteFinalite> getList() {
        return repository.findAll();
    }

    public EtiquetteFinalite createOne(final EtiquetteFinaliteForm tableform) {
        return repository.save(tableform.getEtiquetteFinalite());
    }

    public void update(final Long id, final EtiquetteFinaliteForm tableform) {
        EtiquetteFinalite a = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());;
        a.setIdFinalite(tableform.getIdFinalite());
        a.setLibelle(tableform.getNom());
        a.setDescription(tableform.getDescription());
        mediaModificationService.markModified(a);
        repository.save(a);

    }

    public String delete(final Long id, final Locale locale) {
        if(assoObjetObjectifService.countByEtiquetteFinalite(id) > 0){
            return messageSourceService.getMessageSource().getMessage("error.objet.suppression.liee", null, locale);
        } else if (assoIndicateurObjectifService.countByObjectif(id) > 0) {
            return messageSourceService.getMessageSource().getMessage("error.objet.suppression.liee", null, locale);
        } else {
            mediaModificationService.markModified(MediaModificationOriginType.OBJECTIF, id);
            repository.deleteById(id);
            return "";
        }
    }

    public List<EtiquetteFinalite> getList(final List<Long> ids) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        return repository.findAll(where(hasIdIn(ids)));
    }
}
