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

import com.efficacity.explorateurecocites.beans.biz.EcociteBean;
import com.efficacity.explorateurecocites.beans.biz.EtapeBean;
import com.efficacity.explorateurecocites.beans.model.Ecocite;
import com.efficacity.explorateurecocites.beans.model.Ecocite_;
import com.efficacity.explorateurecocites.beans.repository.EcociteRepository;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.EcociteForm;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import com.efficacity.explorateurecocites.utils.enumeration.ETAT_PUBLICATION;
import isotope.commons.exceptions.NotFoundException;
import isotope.commons.services.CrudEntityService;
import isotope.modules.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.beans.specification.EcociteSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 * <p>
 * Date de génération : 07/02/2018
 */
@Service
public class EcociteService extends CrudEntityService<EcociteRepository, Ecocite, Long> {

    @Autowired
    EtapeService etapeService;

    @Autowired
    ActionService actionService;

    @Autowired
    MessageSourceService messageSourceService;

    @Autowired
    AssoObjetObjectifService assoObjetObjectifService;

    @Autowired
    AssoIndicateurObjetService assoIndicateurObjetService;

    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    ReponsesQuestionnaireEvaluationService reponsesQuestionnaireEvaluationService;

    @Autowired
    ContactService contactService;

    @Autowired
    AssoObjetContactService assoObjetContactService;

    public EcociteService(EcociteRepository repository) {
        super(repository);
    }

    public EcociteBean findOneEcocite(Long idEcocite) {
        return toEcociteBean(repository.findOne(idEcocite));
    }

    public List<EcociteBean> findAllEcocite() {
        return toEcociteBeanList(repository.findAll(new Sort(Sort.Direction.ASC, Ecocite_.nom.getName())));
    }

    public List<EcociteBean> findAllVisibleEcocite() {
        return toEcociteBeanList(this.getListVisible());
    }

    public List<EcociteBean> findAllByOrderByNomAsc() {
        return toEcociteBeanList(repository.findAllByOrderByNomAsc());
    }

    /**
     * Récupérer tous les écocités de la BDD en ordre du nom alphabétique
     *
     * @return une liste des écocité
     */
    public List<Ecocite> getListOrderByNomAsc() {
        return repository.findAllByOrderByNomAsc();
    }

    public List<Ecocite> getListByOrderByIdAsc() {
        return repository.findAllByOrderByIdAsc();
    }

    public LinkedHashMap<String, String> getMapPublishedByNomAsc() {
        return repository.findAll(where(hasEtatPublication(ETAT_PUBLICATION.PUBLIE)), new Sort(Sort.Direction.ASC, "nom"))
                .stream()
                .collect(Collectors.toMap(e -> String.valueOf(e.getId()), Ecocite::getNom,
                        (k1, k2) -> k1,
                        LinkedHashMap::new)
                );
    }

    /**
     * Récupérer tous les écocités de la BDD en ordre du nom alphabétique
     *
     * @return un ensemble {@code LinkedHashMap} des écocités avec l'id et le nom
     * @see #getListOrderByNomAsc()
     */
    public LinkedHashMap<String, String> getMapOrderByNomAsc() {
        return getListOrderByNomAsc()
                .stream()
                .collect(Collectors.toMap(e -> String.valueOf(e.getId()), Ecocite::getNom,
                        (k1, k2) -> k1,
                        LinkedHashMap::new)
                );
    }

    /**
     * Récupérer une ecocite par l'id
     *
     * @param id identifiant
     * @return
     * @throws NotFoundException si aucun est trouvé
     */
    public Ecocite findById(Long id) {
        return findOne(id)
                .orElseThrow(() ->
                        new NotFoundException("Ecocite" + " '" + id + "' n'est pas trouvée")
                );
    }

    public List<EcociteBean> findAllVisibleQuery(String query) {
        return toEcociteBeanList(repository.findAll(where(findEcociteLikeQuery(query)).and(hasEtatPublication(ETAT_PUBLICATION.PUBLIE))));
    }

    public List<EcociteBean> findAllByIdIn(List<Long> listeId) {
        return toEcociteBeanList(repository.findAllByIdInOrderByNomAsc(listeId));
    }

    public List<Ecocite> findAll(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        return repository.findAll(where(hasIdIn(ids)));
    }

    /**
     * Récupérer la liste des ecocite en fonction de critères
     *
     * @param spec spécification qui représente des critères de recherche, elle peut être null
     * @return l'ensemble des actions
     */
    public List<Ecocite> getList(Specification<Ecocite> spec) {
        return repository.findAll(spec);
    }

    /**
     * Récupérer la liste des ecocite en fonction de critères de filtrage
     *
     * @return l'ensemble des actions
     */
    public List<Ecocite> getListFiltre(Map<String, String> filtres) {
        Specifications<Ecocite> spec = null;
        if (filtres != null && !filtres.isEmpty()) {
            for (String champs : filtres.keySet()) {
                if (CustomValidator.isNotEmpty(filtres.get(champs))) {
                    switch (champs) {
                        case "nom":
                            if (spec == null) {
                                spec = where(hasNom(filtres.get(champs)));
                            } else {
                                spec = spec.and(hasNom(filtres.get(champs)));
                            }
                            break;
                        case "region":
                            if (spec == null) {
                                spec = where(hasRegion(Long.valueOf(filtres.get(champs))));
                            } else {
                                spec = spec.and(hasRegion(Long.valueOf(filtres.get(champs))));
                            }
                            break;
                        case "porteur":
                            if (spec == null) {
                                spec = where(hasPorteur(filtres.get(champs)));
                            } else {
                                spec = spec.and(hasPorteur(filtres.get(champs)));
                            }
                            break;
                        case "anneeAhdesion":
                            if (spec == null) {
                                spec = where(hasAnnee(filtres.get(champs)));
                            } else {
                                spec = spec.and(hasAnnee(filtres.get(champs)));
                            }
                            break;
                        case "publication":
                            if (spec == null) {
                                spec = where(hasEtatPublication(filtres.get(champs)));
                            } else {
                                spec = spec.and(hasEtatPublication(filtres.get(champs)));
                            }
                            break;
                        case "caracterisation":
                            if (spec == null) {
                                spec = where(hasCaraterisationStatus(filtres.get(champs)));
                            } else {
                                spec = spec.and(hasCaraterisationStatus(filtres.get(champs)));
                            }
                            break;
                        case "indicateurChoix":
                            if (spec == null) {
                                spec = where(hasChoixIndicateurStatus(filtres.get(champs)));
                            } else {
                                spec = spec.and(hasChoixIndicateurStatus(filtres.get(champs)));
                            }
                            break;
                        case "indicateurMesure":
                            if (spec == null) {
                                spec = where(hasMesureIndicateurStatus(filtres.get(champs)));
                            } else {
                                spec = spec.and(hasMesureIndicateurStatus(filtres.get(champs)));
                            }
                            break;
                        case "impactProgramme":
                            if (spec == null) {
                                spec = where(hasImpactProgrammeStatus(filtres.get(champs)));
                            } else {
                                spec = spec.and(hasImpactProgrammeStatus(filtres.get(champs)));
                            }
                            break;
                        case "finalite":
                            if (spec == null) {
                                spec = where(hasFinalite(Long.valueOf(filtres.get(champs))));
                            } else {
                                spec = spec.and(hasFinalite(Long.valueOf(filtres.get(champs))));
                            }
                            break;
                    }
                }
            }
        } else {
            spec = null;
        }
        return repository.findAll(spec);
    }

    /**
     * Récupérer une ecocite par l'id
     *
     * @param id identifiant
     * @return un objet {@code Ecocite} ou null si aucun n'est trouvé
     */
    public Ecocite getOne(Long id) {
        return repository.findOne(id);
    }

    public void save(EcociteBean ecocite, JwtUser user) {
        // sauvegarde de l'action
        save(ecocite.getTo(), user);
    }

    public Ecocite save(Ecocite ecocite, JwtUser user) {
        ecocite.setUserModification(user.getEmail());
        ecocite.setDateModification(LocalDateTime.now());
        return repository.save(ecocite);
    }


    private List<EcociteBean> toEcociteBeanList(List<Ecocite> ecocites) {
        if (ecocites != null) {
            List<EcociteBean> ecocitesBean = new ArrayList<EcociteBean>();
            for (Ecocite ecocite : ecocites) {
                ecocitesBean.add(toEcociteBean(ecocite));
            }
            return ecocitesBean;
        } else {
            return null;
        }
    }

    private EcociteBean toEcociteBean(Ecocite ecocite) {
        if (ecocite != null) {
            List<EtapeBean> etapes = etapeService.getEtapeByIdEcocite(ecocite.getId());
            return new EcociteBean(ecocite, etapes);
        } else {
            return null;
        }
    }

    @Transactional
    public String delete(final Long id, final Locale locale, final JwtUser user) {
        if (actionService.countByEcocite(id) > 0) {
            return messageSourceService.getMessageSource().getMessage("error.objet.suppression.liee", null, locale);
        } else {
            Ecocite ecocite = repository.findOne(id);
            assoObjetObjectifService.deleteByEcocite(id);
            assoIndicateurObjetService.deleteByEcocite(id);
            etapeService.deleteByEcocite(id);
            fileUploadService.deleteByEcocite(id);
            contactService.removeEcociteReference(id, user);
            reponsesQuestionnaireEvaluationService.deleteByEcocite(id);
            assoObjetContactService.deleteByEcocite(id);
            repository.delete(ecocite);
            return "";
        }
    }

    public Ecocite createOne(final EcociteForm tableform, final JwtUser user) {
        Ecocite ecocite = tableform.getEcocite();
        ecocite.setDateCreation(LocalDateTime.now());
        ecocite.setUserCreation(user.getEmail());
        ecocite.setUserModification(user.getEmail());
        ecocite.setDateModification(LocalDateTime.now());
        ecocite = repository.save(ecocite);
        etapeService.createEtapeEcocite(ecocite);
        return ecocite;
    }

    public boolean existWithId(final Long idEcocite) {
        return repository.count(where(hasId(idEcocite))) > 0;
    }

    public List<Ecocite> getListVisible() {
        return repository.findAll(hasEtatPublication(ETAT_PUBLICATION.PUBLIE), new Sort(Sort.Direction.ASC, Ecocite_.nom.getName()));
    }

    public List<Ecocite> findByIdRegion(final Long objectId) {
        return repository.findAll(hasRegion(objectId));
    }

    public Ecocite findBySiren(final String siren) {
        return repository.findOne(where(hasSiren(siren)));
    }

    public Long countBySiren(final String siren) {
        return repository.count(hasSiren(siren));
    }

    public Long countVisibleEcocite() {
        return repository.count(where(hasEtatPublication(ETAT_PUBLICATION.PUBLIE)));
    }
}

