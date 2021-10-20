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
import com.efficacity.explorateurecocites.beans.model.Action;
import com.efficacity.explorateurecocites.beans.model.AssoActionIngenierie;
import com.efficacity.explorateurecocites.beans.model.EtiquetteIngenierie;
import com.efficacity.explorateurecocites.beans.repository.AssoActionIngenierieRepository;
import com.efficacity.explorateurecocites.ui.bo.forms.EtiquetteFrom;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import isotope.commons.services.CrudEntityService;
import isotope.modules.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.beans.specification.AssoActionIngenierieSpecifications.*;
import static org.springframework.data.jpa.domain.Specification.where;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 * <p>
 * Date de génération : 08/02/2018
 */
@Service
public class AssoActionIngenierieService extends CrudEntityService<AssoActionIngenierieRepository, AssoActionIngenierie, Long> {

    @Autowired
    ActionService actionService;

    public AssoActionIngenierieService(AssoActionIngenierieRepository repository) {
        super(repository);
    }

    public List<AssoActionIngenierie> getListByAction(Long idAction) {
        return repository.findByIdAction(idAction);
    }
    public List<AssoActionIngenierie> getListByActionOrderByIdIngenierieAsc(Long idAction) {
        return repository.findByIdActionOrderByIdEtiquetteIngenierieAsc(idAction);
    }
    /**
     * Mettre à jour l'association de l'etiquette et l'action
     *
     * @param action     l'action ayant son propre Id
     * @param ingenierie l'etiquette d'ingénierie à associer
     * @param form
     * @return
     */
    public AssoActionIngenierieBean maj(Action action, EtiquetteIngenierie ingenierie, EtiquetteFrom form, JwtUser user) {
        AssoActionIngenierie biz;
        if (CustomValidator.isEmpty(form.getIdAsso())
                || (biz = repository.findById(Long.parseLong(form.getIdAsso())).orElse(null)) == null) {
            biz = new AssoActionIngenierie();
        }
        // Mise à jour les association des etiquettes et l'action
        biz = update(biz, action.getId(), Long.parseLong(form.getIdEtiquette()), form.getPoid());
        actionService.markActionModified(action, user);
        return AssoActionIngenierieBean.createFrom(biz.getId(), ingenierie, action.getId(), form.getPoid());

    }

    /**
     * Mettre à jour l'association de l'ingénierie et de l'action et le sauvegarder à la BDD
     *
     * @param biz         l'association à sauvegarder
     * @param idAction    id de l'action
     * @param idEtiquette id de l'etiquette de l'ingénierie
     * @param poid        poid de l'etiquette (principal, secondaire ou mineure)
     * @return l'association sauvegardée
     */
    @Transactional
    public AssoActionIngenierie update(AssoActionIngenierie biz, Long idAction, Long idEtiquette, Integer poid) {
        biz.setIdAction(idAction);
        biz.setIdEtiquetteIngenierie(idEtiquette);
        biz.setPoid(poid);
        return repository.save(biz);
    }

    /**
     * Supprimer une association de l'action et l'ingénierie
     *
     * @param idAction               id de l'action
     * @param idAssoActionIngenierie id de l'association
     */
    @Transactional
    public void delete(Long idAction, Long idAssoActionIngenierie, JwtUser user) {
        findOne(idAssoActionIngenierie).ifPresent(assoBiz -> {
            actionService.markActionModified(idAction, user);
            if (assoBiz.getIdAction().equals(idAction)) {
                repository.delete(assoBiz);
            }
        });
    }

    /**
     * Supprimer toutes les associations entre les ingénierie et l'action
     *
     * @param idAction id de l'action
     */
    @Transactional
    public void delete(Long idAction) {
        List<AssoActionIngenierie> res = getListByAction(idAction);
        if (CustomValidator.isNotEmpty(res)) {
            repository.deleteAll(res);
        }
    }

    public long countEtiquettesPrin(Long idAction) {
        return countEtiquettesByActionAndPoid(idAction, 1);
    }

    private long countEtiquettesByActionAndPoid(Long idAction, Integer poid) {
        return repository.count(where(hasAction(idAction)).and(hasPoid(poid)));
    }

    public boolean etiquettesExistByAction(final Long idAction, final Long idEtiquette) {
        Pageable limit = PageRequest.of(0, 1);
        return repository.findAll(where(hasAction(idAction)).and(hasEtqIngenierie(idEtiquette)), limit).getTotalElements() > 0;
    }

    public Long countByIngenierie(Long id) {
        return repository.count(where(hasEtqIngenierie(id)));
    }

    public void deleteByAction(final Long id) {
        repository.deleteAll(repository.findByIdAction(id));
    }

    public List<AssoActionIngenierie> cloneForAction(final Long idClone, final Long idOriginal) {
        List<AssoActionIngenierie> clones = repository.findByIdAction(idOriginal)
                .stream()
                .map(etq -> {
                    AssoActionIngenierie clone = new AssoActionIngenierie();
                    clone.setIdAction(idClone);
                    clone.setIdEtiquetteIngenierie(etq.getIdEtiquetteIngenierie());
                    clone.setPoid(etq.getPoid());
                    return clone;
                })
                .collect(Collectors.toList());
        return repository.saveAll(clones);
    }
}
