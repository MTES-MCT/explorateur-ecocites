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

import com.efficacity.explorateurecocites.beans.biz.AssoIndicateurObjetBean;
import com.efficacity.explorateurecocites.beans.biz.AssoObjetObjectifBean;
import com.efficacity.explorateurecocites.beans.model.AssoObjetObjectif;
import com.efficacity.explorateurecocites.beans.model.EtiquetteFinalite;
import com.efficacity.explorateurecocites.beans.repository.AssoObjetObjectifRepository;
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

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.beans.specification.AssoObjetObjectifSpecifications.*;
import static org.springframework.data.jpa.domain.Specification.where;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 *
 * Date de génération : 19/02/2018
 */
@Service
public class AssoObjetObjectifService extends CrudEntityService<AssoObjetObjectifRepository, AssoObjetObjectif, Long> {

    @Autowired
    AssoIndicateurObjetService assoIndicateurObjetService;

    @Autowired
    MediaModificationService mediaModificationService;

    @Autowired
    ActionService actionService;

    public AssoObjetObjectifService(AssoObjetObjectifRepository repository) {
        super(repository);
    }

    public AssoObjetObjectif findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public List<AssoObjetObjectif> getListByAction(Long idAction) {
        return repository.findAll(where(hasIdObjet(idAction)).and(hasAction()), Sort.by(Sort.Direction.ASC, "poid"));
    }
    public List<AssoObjetObjectif> getListByActionOrderByIdObjectifAsc(Long idAction) {
        return repository.findByIdObjetAndTypeObjetOrderByIdObjectifAsc(idAction, TYPE_OBJET.ACTION.getCode());
    }

    public List<AssoObjetObjectif> getListByEcocite(Long idEcocite) {
        return repository.findAll(where(hasIdObjet(idEcocite)).and(hasEcocite()), Sort.by(Sort.Direction.ASC, "poid"));
    }
    public List<AssoObjetObjectif> getListByEcociteOrderByIdObjectifAsc(Long idEcocite) {
        return repository.findByIdObjetAndTypeObjetOrderByIdObjectifAsc(idEcocite, TYPE_OBJET.ECOCITE.getCode());
    }

    /**
    * Mettre à jour l'association de l'etiquette et l'action
    *
    * @param idObjet id objet
    * @param objectif l'etiquette de la finalité à associer
    * @param form
    * @return
    */
    public AssoObjetObjectifBean maj(Long idObjet, TYPE_OBJET typeObjet, EtiquetteFinalite objectif, EtiquetteFrom form, JwtUser user) {
        AssoObjetObjectif biz;
        if (CustomValidator.isEmpty(form.getIdAsso())
                || (biz = repository.findById(Long.parseLong(form.getIdAsso())).orElse(null)) == null) {
            biz = new AssoObjetObjectif();
        }
        // Mise à jour les association des etiquettes et l'objet
        biz = this.update(biz, idObjet, typeObjet.getCode(), Long.parseLong(form.getIdEtiquette()), form.getPoid());
        mediaModificationService.markModified(typeObjet.getMMOT(), idObjet);
        if (TYPE_OBJET.ACTION.equals(typeObjet)) {
            actionService.markActionModified(idObjet, user);
        }
        return AssoObjetObjectifBean.createFrom(biz.getId(), objectif, idObjet, form.getPoid());
    }

    /**
    * Mettre à jour l'association de l'objectif et de l'objet et le sauvegarder à la BDD
    *
    * @param biz         l'association à sauvegarder
    * @param idObject    id de object
    * @param typeObject  type de l'objet (action ou ecocite)
    * @param idEtiquette id de l'etiquette de la finalité
    * @param poid        poid de l'etiquette (principal, secondaire ou mineure)
    * @return l'association sauvegardée
    */
    @Transactional
    public AssoObjetObjectif update(AssoObjetObjectif biz, Long idObject, String typeObject, Long idEtiquette, Integer poid) {
        biz.setIdObjet(idObject);
        biz.setTypeObjet(typeObject);
        biz.setIdObjectif(idEtiquette);
        biz.setPoid(poid);
        return repository.save(biz);
    }

    /**
    * Supprimer une association de l'objet et l'objectif
    *
    * @param idObjet             id de l'objet
    * @param typeObjet           type de objet
    * @param idAssoObjetObjectif id de l'association
    */
    @Transactional
    public void delete(Long idObjet, TYPE_OBJET typeObjet, Long idAssoObjetObjectif, JwtUser user) {
        // On va sup les possibles associations indicateur/Objet saisie sur cette étiquette avant de supprimer celle de l'étiquette et de l'objet
        AssoObjetObjectif assoObjetObjectif = findById(idAssoObjetObjectif);
        Long idObjectif = assoObjetObjectif.getIdObjectif();
        mediaModificationService.markModified(typeObjet.getMMOT(), idObjet);
        List<AssoIndicateurObjetBean> listAssocIndicateur = assoIndicateurObjetService.findAllByIdObjetAndTypeObjetAndObjectif(idObjet, typeObjet, idObjectif);
        for(AssoIndicateurObjetBean assocIndicateur : listAssocIndicateur){
            // On sup l'asso
            assoIndicateurObjetService.delete(idObjet, typeObjet.getCode(), assocIndicateur.getId());
        }
        // On fini par sup l'asso
        findOne(idAssoObjetObjectif).ifPresent(assoBiz -> {
            if (assoBiz.getIdObjet().equals(idObjet) && assoBiz.getTypeObjet().equals(typeObjet.getCode())) {
                if (TYPE_OBJET.ACTION.equals(typeObjet)) {
                    actionService.markActionModified(idObjet, user);
                }
                repository.delete(assoBiz);
            }
        });
    }

    public long countEtiquettesPrinByAction(Long idObjet) {
        return countEtiquettesByActionAndPoid(idObjet, 1);
    }

    public long countEtiquettesPrinByEcocite(Long idObjet) {
        return countEtiquettesByEcociteAndPoid(idObjet, 1);
    }

    public long countEtiquettesSecondByEcocite(Long idObjet ) {
        return countEtiquettesByEcociteAndPoid(idObjet, 2);
    }

    public long countEtiquettesSecondByAction(Long idObjet ) {
        return countEtiquettesByActionAndPoid(idObjet, 2);
    }

    public long countEtiquettesThirdByAction(Long idObjet) {
        return countEtiquettesByActionAndPoid(idObjet, 3);
    }

    public long countEtiquettesThirdByEcocite(Long idObjet) {
        return countEtiquettesByEcociteAndPoid(idObjet, 3);
    }

    private long countEtiquettesByActionAndPoid(Long idObjet, Integer poid) {
        return repository.count(where(hasIdObjet(idObjet)).and(hasAction()).and(hasPoid(poid)));
    }
    private long countEtiquettesByEcociteAndPoid(Long idObjet, Integer poid) {
        return repository.count(where(hasIdObjet(idObjet)).and(hasEcocite()).and(hasPoid(poid)));
    }

    public boolean etiquettesExistByAction(Long idObjet, Long idEtiquette) {
        Pageable limit = PageRequest.of(0,1);
        return repository.findAll(where(hasIdObjet(idObjet)).and(hasAction()).and(hasObjectif(idEtiquette)), limit).getTotalElements() > 0;
    }

    public boolean etiquettesExistByEcocite(Long idObjet, Long idEtiquette) {
        Pageable limit = PageRequest.of(0,1);
        return repository.findAll(where(hasIdObjet(idObjet)).and(hasEcocite()).and(hasObjectif(idEtiquette)), limit).getTotalElements() > 0;
    }

    public List<AssoObjetObjectif> getListByFinalite(final Long id) {
        return repository.findAll(where(hasObjectif(id)));
    }

    public Long countByEtiquetteFinalite(final Long id) {
        return repository.count(where(hasObjectif(id)));
    }

    public void deleteByEcocite(final Long id) {
        repository.deleteAll(repository.findByIdObjetAndTypeObjet(id, TYPE_OBJET.ECOCITE.getCode()));
    }

    public void deleteByAction(final Long id) {
        repository.deleteAll(repository.findByIdObjetAndTypeObjet(id, TYPE_OBJET.ACTION.getCode()));
    }

    public List<AssoObjetObjectif> findAll(Specification<AssoObjetObjectif> specs) {
        return repository.findAll(specs);
    }

    public List<AssoObjetObjectif> cloneForAction(final Long idClone, final Long idOriginal, TYPE_OBJET typeObjet) {
        List<AssoObjetObjectif> clones = repository.findByIdObjetAndTypeObjet(idOriginal, typeObjet.getCode())
                .stream()
                .map(etq -> {
                    AssoObjetObjectif clone = new AssoObjetObjectif();
                    clone.setIdObjet(idClone);
                    clone.setIdObjectif(etq.getIdObjectif());
                    clone.setPoid(etq.getPoid());
                    clone.setTypeObjet(typeObjet.getCode());
                    return clone;
                })
                .collect(Collectors.toList());
        return repository.saveAll(clones);
    }
}
