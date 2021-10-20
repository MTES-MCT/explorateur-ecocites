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
import com.efficacity.explorateurecocites.beans.biz.AssoActionDomainBean;
import com.efficacity.explorateurecocites.beans.biz.ObjetEtiquettesWrapper;
import com.efficacity.explorateurecocites.beans.model.EtiquetteAxe;
import com.efficacity.explorateurecocites.beans.repository.EtiquetteAxeRepository;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.EtiquetteAxeForm;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import isotope.commons.exceptions.NotFoundException;
import isotope.commons.services.CrudEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.beans.specification.EtiquetteAxeSpecifications.hasIdIn;
import static org.springframework.data.jpa.domain.Specification.where;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 * <p>
 * Date de génération : 08/02/2018
 */
@Service
public class EtiquetteAxeService extends CrudEntityService<EtiquetteAxeRepository, EtiquetteAxe, Long> {
    public EtiquetteAxeService(EtiquetteAxeRepository repository) {
        super(repository);
    }

    @Autowired
    ActionService actionService;

    @Autowired
    MessageSourceService messageSourceService;

    @Autowired
    AssoActionDomainService actionDomainService;

    @Autowired
    AssoIndicateurDomaineService assoIndicateurDomaineService;

    @Autowired
    MediaModificationService mediaModificationService;

    /**
     * Récupérer toues les etiquettes associées à l'axe
     *
     * @param idAxe Id de l'axe
     * @return Liste des objets du type {@code AssoActionDomainBean}
     */
    public List<AssoActionDomainBean> getListByAxe(Long idAxe) {

        return repository.findByIdAxe(idAxe)
                .stream()
                .map(etiquetteAxe -> AssoActionDomainBean.createFrom(null, etiquetteAxe, null, 0))
                .collect(Collectors.toList());
    }

    public List<EtiquetteAxe> findAll() {
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
                .collect(Collectors.toMap(e -> String.valueOf(e.getId()), EtiquetteAxe::getLibelle,
                        (k1, k2) -> k1,
                        LinkedHashMap::new)
                );
    }


    public EtiquetteAxe findById(Long id) {
        return findOne(id)
                .orElseThrow(() ->
                        new NotFoundException("Etiquette d'axe" + " '" + id + "' n'est pas trouvé"));
    }

    public List<AssoActionDomainBean> getListByAxeAndNotSelected(Long idAxe, Set<Long> etiquettesSelected) {
        return repository.findByIdAxeAndIdNotIn(idAxe, etiquettesSelected)
                .stream()
                .map(etiquetteAxe -> AssoActionDomainBean.createFrom(null, etiquetteAxe, null, 0))
                .collect(Collectors.toList());
    }

    /**
     * Récupérer la liste des etiquettes de l'axe disponibles
     * et celles qui ont été choisies
     *
     * @param idAction Id de l'action
     * @return objet du type {@code ActionDomainBean}
     */
    public ObjetEtiquettesWrapper getListSelectedByAction(Long idAction, Long idAxe) {
        Set<Long> idsDomainsSeletcted = new HashSet<>();
        ObjetEtiquettesWrapper res = new ObjetEtiquettesWrapper();
        actionService.findOne(idAction).ifPresent(action -> {
            // Rechercher à la BDD les etiquettes de l'axe associées à l'action
            List<AssoActionDomainBean> domainsSelected = actionDomainService.getListByAction(idAction)
                    .stream()
                    .map(actionDomain -> {
                        EtiquetteAxe etqAxe = repository.findById(actionDomain.getIdDomain()).orElseThrow(() -> new EntityNotFoundException());;
                        // Ajouter l'id à la liste pour le filtrage de plus tard
                        idsDomainsSeletcted.add(etqAxe.getId());
                        return AssoActionDomainBean.createFrom(actionDomain.getId(), etqAxe, action.getId(), actionDomain.getPoid());
                    }).collect(Collectors.toList());
            // On recherche celles qui n'ont pas encore été sélectionées
            List<AssoActionDomainBean> etqs = CustomValidator.isEmpty(idsDomainsSeletcted) ?
                    getListByAxe(idAxe)
                    : getListByAxeAndNotSelected(idAxe, idsDomainsSeletcted);
            res.setEtiquettes(etqs);
            res.setEtiquettesSelected(domainsSelected);
        });
        return res;
    }

    public List<EtiquetteAxe> getList() {
        return repository.findAll();
    }

    public String delete(final Long id, final Locale locale) {
        if(actionDomainService.getListByAxe(id).size() > 0){
            return messageSourceService.getMessageSource().getMessage("error.objet.suppression.liee", null, locale);
        } else if (assoIndicateurDomaineService.getListByDomaine(id).size() > 0) {
            return messageSourceService.getMessageSource().getMessage("error.objet.suppression.liee", null, locale);
        } else {
            mediaModificationService.markModified(MediaModificationOriginType.DOMAINE, id);
            repository.deleteById(id);
            return "";
        }
    }

    public void update(final Long id, final EtiquetteAxeForm tableform) {
        EtiquetteAxe a = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());;
        a.setIdAxe(tableform.getIdAxe());
        a.setLibelle(tableform.getNom());
        a.setDescription(tableform.getDescription());
        mediaModificationService.markModified(a);
        repository.save(a);
    }

    public EtiquetteAxe createOne(final EtiquetteAxeForm tableform) {
        return repository.save(tableform.getEtiquetteAxe());
    }

    public List<EtiquetteAxe> getList(final List<Long> ids) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        return repository.findAll(where(hasIdIn(ids)));
    }
}
