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
import com.efficacity.explorateurecocites.beans.model.AssoIndicateurObjet;
import com.efficacity.explorateurecocites.beans.model.CibleIndicateur;
import com.efficacity.explorateurecocites.beans.model.Indicateur;
import com.efficacity.explorateurecocites.beans.model.MesureIndicateur;
import com.efficacity.explorateurecocites.beans.repository.AssoIndicateurObjetRepository;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import isotope.commons.entities.BaseEntity;
import isotope.commons.services.CrudEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.beans.specification.AssoIndicateurObjetSpecifications.*;
import static org.springframework.data.jpa.domain.Specification.where;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 * <p>
 * Date de génération : 20/02/2018
 */
@Service
public class AssoIndicateurObjetService extends CrudEntityService<AssoIndicateurObjetRepository, AssoIndicateurObjet, Long> {

    @Autowired
    protected IndicateurService indicateurService;
    @Autowired
    protected CibleIndicateurService cibleIndicateurService;
    @Autowired
    protected MesureIndicateurService mesureIndicateurService;

    /**
     * Base constructor
     *
     * @param repository
     */
    protected AssoIndicateurObjetService(final AssoIndicateurObjetRepository repository) {
        super(repository);
    }


    public AssoIndicateurObjetBean findOneById(Long idAssoIndicateurObjet) {
        return toAssoIndicateurObjetBean(repository.findById(idAssoIndicateurObjet).orElseThrow(() -> new EntityNotFoundException()), null);
    }

    private List<AssoIndicateurObjetBean> findAllByIdObjetAndTypeObjet(Long idObjet, String typeObjet, List<String> naturesIndicateur) {
        return toAssoIndicateurObjetBeanList(repository.findAllByIdObjetAndTypeObjet(idObjet, typeObjet), naturesIndicateur);
    }

    public AssoIndicateurObjetBean findByIdObjetAndTypeObjetAndIdIndicateurAndUniteAndPosteCalcule(Long idObjet, TYPE_OBJET typeObjet, Long idIndicateur, String unite, String posteCalcule) {
        return toAssoIndicateurObjetBean(repository.findByIdObjetAndTypeObjetAndIdIndicateurAndUniteAndPosteCalcule(idObjet, typeObjet.getCode(), idIndicateur, unite, posteCalcule), null);
    }
    public List<AssoIndicateurObjet> findAllByIdIndicateurAndTypeObjet(Long idIndicateur, String objectType){
        return repository.findAllByIdIndicateurAndTypeObjet(idIndicateur, objectType);
    }
    public List<AssoIndicateurObjet> findAllByIdIndicateurAndTypeObjetOrderByIdObjet(Long idIndicateur, String objectType){
        return repository.findAllByIdIndicateurAndTypeObjetOrderByIdObjet(idIndicateur, objectType);
    }



    public long countByIdIndicateur(Long idIndicateur) {
        return repository.count(where(hasIdIndicateur(idIndicateur)));
    }

    /*
     * Pour récup les association entre un objet et un indicateur via id de l'etiquette objectif
     *
     */
    public List<AssoIndicateurObjetBean> findAllByIdObjetAndTypeObjetAndObjectif(Long idAction, TYPE_OBJET typeObjet, Long idObjectif) {
        return toAssoIndicateurObjetBeanList(repository.findAll(where(hasIndicateurSelectPourObjectif(idAction, typeObjet, idObjectif))), null);
    }

    /*
     * Pour récup les association entre un objet et un indicateur via id de l'etiquette domaine
     *
     */
    public List<AssoIndicateurObjetBean> findAllByIdObjetAndTypeObjetAndDomaine(Long idAction, TYPE_OBJET typeObjet, Long idDomaine) {
        return toAssoIndicateurObjetBeanList(repository.findAll(where(hasIndicateurSelectPourDomaine(idAction, typeObjet, idDomaine))), null);
    }


    /**
     * Save l'association de l'indicateur et de l'action et le sauvegarder à la BDD
     *
     * @return l'association sauvegardée
     */
    @Transactional
    public void save(AssoIndicateurObjetBean assoIndicateurActionBean) {
        repository.save(assoIndicateurActionBean.getTo());
    }

    /**
     * Save l'association de l'indicateur et de l'action et le sauvegarder à la BDD
     *
     * @return l'association sauvegardée
     */
    @Transactional
    public AssoIndicateurObjet save(AssoIndicateurObjet assoIndicateurAction) {
        return repository.save(assoIndicateurAction);
    }

    /**
     * Supprimer une association de l'action et l'indicateur
     *
     * @param idObjet               id de l'action
     * @param idAssoIndicateurObjet id de l'association
     */
    @Transactional
    public void delete(Long idObjet, String typeObjet, Long idAssoIndicateurObjet) {

        /*
        * On doit avant sup les mesure et cible potentiel de cette asso
        */
        List<CibleIndicateur> listeCibleIndicateur = cibleIndicateurService.findAllByIdAssoIndicateurObjetOrderByDateSaisieAsc(idAssoIndicateurObjet);
        for(CibleIndicateur cibleIndicateur : listeCibleIndicateur){
            cibleIndicateurService.delete(cibleIndicateur.getId());
        }
        List<MesureIndicateur> listeMesureIndicateur = mesureIndicateurService.findAllByIdAssoIndicateurObjetOrderByDateSaisieAsc(idAssoIndicateurObjet);
        for(MesureIndicateur mesureIndicateur : listeMesureIndicateur){
            mesureIndicateurService.delete(mesureIndicateur.getId());
        }


        findOne(idAssoIndicateurObjet).ifPresent(assoBiz -> {
            if (assoBiz.getIdObjet().equals(idObjet) && assoBiz.getTypeObjet().equals(typeObjet)) {
                repository.delete(assoBiz);
            }
        });
    }

    public List<AssoIndicateurObjetBean> toAssoIndicateurObjetBeanList(List<AssoIndicateurObjet> assoIndicateurObjets, List<String> naturesIndicateur) {
        if (assoIndicateurObjets != null) {
            List<AssoIndicateurObjetBean> assoIndicateurActionBeans = new ArrayList<>();
            for (AssoIndicateurObjet assoIndicateurObjet : assoIndicateurObjets) {
                assoIndicateurActionBeans.add(toAssoIndicateurObjetBean(assoIndicateurObjet, naturesIndicateur));
            }
            return assoIndicateurActionBeans;
        } else {
            return null;
        }
    }

    public AssoIndicateurObjetBean toAssoIndicateurObjetBean(AssoIndicateurObjet assoIndicateurObjet, List<String> naturesIndicateur) {
        if (assoIndicateurObjet != null) {
            Indicateur indicateur = indicateurService.findOne(assoIndicateurObjet.getIdIndicateur()).orElse(null);
            if (indicateur == null) {
                return null;
            }
            if ((CustomValidator.isNotEmpty(naturesIndicateur) && naturesIndicateur.contains(indicateur.getNature())) || CustomValidator.isEmpty(naturesIndicateur)) {
                return new AssoIndicateurObjetBean(assoIndicateurObjet, indicateur);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public List<AssoIndicateurObjetBean> findAllByAction(final Long actionId, final List<String> natures) {
        return findAllByIdObjetAndTypeObjet(actionId, TYPE_OBJET.ACTION.getCode(), natures);
    }

    public List<AssoIndicateurObjet> findAllByAction(final Long actionId) {
        return repository.findAllByIdObjetAndTypeObjet(actionId, TYPE_OBJET.ACTION.getCode());
    }
    public List<AssoIndicateurObjet> findAllByActionOrderByIdIndicateurAsc(final Long actionId) {
        return repository.findAllByIdObjetAndTypeObjetOrderByIdIndicateurAsc(actionId, TYPE_OBJET.ACTION.getCode());
    }

    public List<AssoIndicateurObjetBean> findAllByEcocite(final Long ecociteId, final List<String> natures) {
        return findAllByIdObjetAndTypeObjet(ecociteId, TYPE_OBJET.ECOCITE.getCode(), natures);
    }

    public List<AssoIndicateurObjet> findAllByEcocite(final Long ecociteId) {
        return repository.findAllByIdObjetAndTypeObjet(ecociteId, TYPE_OBJET.ECOCITE.getCode());
    }
    public List<AssoIndicateurObjet> findAllByEcociteOrderByIdIndicateurAsc(final Long ecociteId) {
        return repository.findAllByIdObjetAndTypeObjetOrderByIdIndicateurAsc(ecociteId, TYPE_OBJET.ECOCITE.getCode());
    }

    public void deleteByEcocite(final Long id) {
        List<AssoIndicateurObjet> assos = repository.findAllByIdObjetAndTypeObjet(id, TYPE_OBJET.ECOCITE.getCode());
        cibleIndicateurService.deleteByAssoIndicateur(assos);
        mesureIndicateurService.deleteByAssoIndicateur(assos);
        repository.deleteAll(assos);
    }

    public void deleteByAction(final Long id) {
        List<AssoIndicateurObjet> assos = repository.findAllByIdObjetAndTypeObjet(id, TYPE_OBJET.ACTION.getCode());
        cibleIndicateurService.deleteByAssoIndicateur(assos);
        mesureIndicateurService.deleteByAssoIndicateur(assos);
        repository.deleteAll(assos);
    }

    public LocalDate getLastMesureFor(final String typeObjet, final Long id) {
        List<Long> ids = repository.findAllByIdObjetAndTypeObjet(id, typeObjet).stream().map(BaseEntity::getId).collect(Collectors.toList());
        if (ids == null || ids.isEmpty()) {
            return null;
        }
        MesureIndicateur lastMesure = mesureIndicateurService.getLastMesure(ids);
        if(lastMesure != null && lastMesure.getDateSaisie() != null){
            return lastMesure.getDateCreation().toLocalDate();
        }
        return null;
    }

    public Long countByEcocite(final Long ecociteId) {
        return repository.count(where(hasIdObject(ecociteId)).and(belongToEcocite()));
    }

    public List<AssoIndicateurObjet> cloneForAction(final Long idClone, final Long idOriginal) {
        List<AssoIndicateurObjet> clones = repository.findAllByIdObjetAndTypeObjet(idOriginal, TYPE_OBJET.ACTION.getCode())
                .stream()
                .map(original -> {
                    AssoIndicateurObjet clone = new AssoIndicateurObjet();
                    clone.setIdIndicateur(original.getIdIndicateur());
                    clone.setIdObjet(idClone);
                    clone.setTypeObjet(TYPE_OBJET.ACTION.getCode());
                    clone.setPosteCalcule(original.getPosteCalcule());
                    clone.setUnite(original.getUnite());
                    clone.setCommentaireCible(original.getCommentaireCible());
                    clone.setCommentaireMesure(original.getCommentaireMesure());
                    return clone;
                })
                .collect(Collectors.toList());
        return repository.saveAll(clones);
    }
}
