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

package com.efficacity.explorateurecocites.beans.service;

import com.efficacity.explorateurecocites.ajaris.enums.MediaModificationOriginType;
import com.efficacity.explorateurecocites.beans.biz.AssoActionDomainBean;
import com.efficacity.explorateurecocites.beans.biz.AssoIndicateurObjetBean;
import com.efficacity.explorateurecocites.beans.model.Action;
import com.efficacity.explorateurecocites.beans.model.AssoActionDomain;
import com.efficacity.explorateurecocites.beans.model.EtiquetteAxe;
import com.efficacity.explorateurecocites.beans.repository.AssoActionDomainRepository;
import com.efficacity.explorateurecocites.ui.bo.forms.EtiquetteFrom;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import isotope.commons.services.CrudEntityService;
import isotope.modules.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.beans.repository.specifications.AssoActionDomainSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 *
 * Date de génération : 08/02/2018
 */
@Service
public class AssoActionDomainService extends CrudEntityService<AssoActionDomainRepository, AssoActionDomain, Long> {

    @Autowired
    AssoIndicateurObjetService assoIndicateurObjetService;

    @Autowired
    MediaModificationService mediaModificationService;

    @Autowired
    ActionService actionService;

    public AssoActionDomainService(AssoActionDomainRepository repository) {
        super(repository);
    }


    public List<AssoActionDomain> getListByAction(Long idAction) {
        return repository.findAll(hasAction(idAction), new Sort(Sort.Direction.ASC, "poid"));
    }
    public List<AssoActionDomain> getListByActionOrderByIdDomain(Long idAction) {
        return repository.findByIdActionOrderByIdDomainAsc(idAction);
    }

    public AssoActionDomain findById(Long id) {
        return repository.findById(id);
    }

    /**
    * Mettre à jour l'association de l'etiquette et l'action
    * @param action l'action ayant son propre Id
    * @param domain l'etiquette d"axe à associer
    * @param form
    * @return
    */
    public AssoActionDomainBean maj(Action action, EtiquetteAxe domain, EtiquetteFrom form, JwtUser user) {
        AssoActionDomain biz;
        if (CustomValidator.isEmpty(form.getIdAsso())
            || (biz = repository.findOne(Long.parseLong(form.getIdAsso()))) == null) {
            biz = new AssoActionDomain();
        }
        // Mise à jour les association des etiquettes et l'action
        biz = update(biz, action.getId(), Long.parseLong(form.getIdEtiquette()), form.getPoid());
        mediaModificationService.markModified(action);
        actionService.markActionModified(action, user);
        return AssoActionDomainBean.createFrom(biz.getId(), domain, action.getId(), form.getPoid());
    }

    /**
    * Mettre à jour l'association de le domain et de l'action et le sauvegarder à la BDD
    * @param biz l'association à sauvegarder
    * @param idAction id de l'action
    * @param idEtiquette id de l'etiquette de l'axe
    * @param poid poid de l'etiquette (principal, secondaire ou mineure)
    * @return l'association sauvegardée
    */
    @Transactional
    public AssoActionDomain update(AssoActionDomain biz, Long idAction, Long idEtiquette, Integer poid) {
        biz.setIdAction(idAction);
        biz.setIdDomain(idEtiquette);
        biz.setPoid(poid);
        return repository.save(biz);
    }


    /**
    * Supprimer une association de l'action et le domain
    * @param idAction id de l'action
    * @param idAssoActionDomain id de l'association
    */
    @Transactional
    public void delete(Long idAction, Long idAssoActionDomain, JwtUser user) {
        // On va sup les possibles associations indicateur/Action saisie sur cette étiquette avant de supprimer celle de l'étiquette et de l'action
        AssoActionDomain assoActionDomain = findById(idAssoActionDomain);
        Long idDomain = assoActionDomain.getIdDomain();
        actionService.markActionModified(idAction, user);
        mediaModificationService.markModified(MediaModificationOriginType.ACTION, idAction);

        List<AssoIndicateurObjetBean> listAssocIndicateur = assoIndicateurObjetService.findAllByIdObjetAndTypeObjetAndDomaine(idAction, TYPE_OBJET.ACTION, idDomain);
        for(AssoIndicateurObjetBean assocIndicateur : listAssocIndicateur){
            // On sup l'asso
            assoIndicateurObjetService.delete(idAction, TYPE_OBJET.ACTION.getCode(), assocIndicateur.getId());
        }

        // On fini par sup l'asso

        findOne(idAssoActionDomain).ifPresent(assoBiz -> {
            if (assoBiz.getIdAction().equals(idAction)) {
                repository.delete(assoActionDomain);
            }
        });
    }

    /**
    * Supprimer toutes les associations entre les domaines et l'action
    * @param idAction id de l'action
    */
    @Transactional
    public void delete(Long idAction) {
        List<AssoActionDomain> res = getListByAction(idAction);
        if (CustomValidator.isNotEmpty(res)) {
            repository.delete(res);
        }
    }

    public long countEtiquettesPrinByAction(Long idAction) {
        return countEtiquettesByActionAndPoid(idAction, 1);
    }

    public long countEtiquettesSecondByAction(Long idAction) {
        return countEtiquettesByActionAndPoid(idAction, 2);
    }

    private long countEtiquettesByActionAndPoid(Long idAction, Integer poid) {
        return repository.count(where(hasAction(idAction)).and(hasPoid(poid)));
    }

    public boolean etiquettesExistByAction(final Long idAction, final Long idEtiquette) {
        Pageable limit = new PageRequest(0,1);
        return repository.findAll(where(hasAction(idAction)).and(hasDomain(idEtiquette)), limit).getTotalElements() > 0;
    }

    public List<AssoActionDomain> getListByAxe(final Long id) {
        return repository.findByIdDomain(id);

    }

    public void deleteByAction(final Long id) {
        repository.delete(repository.findByIdAction(id));
    }

    public List<AssoActionDomain> findAll(Specification<AssoActionDomain> specification) {
        return repository.findAll(specification);
    }
    public List<AssoActionDomain> findAll() {
        return repository.findAll();
    }

    public List<AssoActionDomain> cloneForAction(final Long idClone, final Long idOriginal) {
        List<AssoActionDomain> clones = repository.findByIdAction(idOriginal)
                .stream()
                .map(etq -> {
                    AssoActionDomain clone = new AssoActionDomain();
                    clone.setIdAction(idClone);
                    clone.setIdDomain(etq.getIdDomain());
                    clone.setPoid(etq.getPoid());
                    return clone;
                })
                .collect(Collectors.toList());
        return repository.save(clones);
    }
}
