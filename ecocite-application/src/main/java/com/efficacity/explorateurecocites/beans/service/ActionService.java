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

import com.efficacity.explorateurecocites.beans.biz.*;
import com.efficacity.explorateurecocites.beans.model.Action;
import com.efficacity.explorateurecocites.beans.model.Action_;
import com.efficacity.explorateurecocites.beans.model.Business;
import com.efficacity.explorateurecocites.beans.repository.ActionRepository;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.ActionCloneForm;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.ActionForm;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import com.efficacity.explorateurecocites.utils.enumeration.*;
import isotope.commons.exceptions.NotFoundException;
import isotope.commons.services.CrudEntityService;
import isotope.modules.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.beans.specification.ActionSpecifications.*;
import static com.efficacity.explorateurecocites.beans.specification.SpecificationHelper.addSpec;
import static org.springframework.data.jpa.domain.Specification.not;
import static org.springframework.data.jpa.domain.Specification.where;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 * <p>
 * Date de génération : 06/02/2018
 */
@Service
public class ActionService extends CrudEntityService<ActionRepository, Action, Long> {

    @Autowired
    AssoObjetContactService assoObjetContactService;

    @Autowired
    EtapeService etapeService;

    @Autowired
    ReponsesEvaluationService reponsesEvaluationService;

    @Autowired
    QuestionsEvaluationService questionsEvaluationService;

    @Autowired
    AssoObjetObjectifService assoObjetObjectifService;

    @Autowired
    AssoActionDomainService assoActionDomainService;

    @Autowired
    AssoActionIngenierieService assoActionIngenierieService;

    @Autowired
    BusinessService businessService;

    @Autowired
    AssoIndicateurObjetService assoIndicateurObjetService;

    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    ReponsesQuestionnaireEvaluationService reponsesQuestionnaireEvaluationService;

    @Autowired
    ContactService contactService;

    @Autowired
    MediaModificationService mediaModificationService;


    public ActionService(ActionRepository repository) {
        super(repository);
    }

    public List<ActionBean> findAllAction() {
        return toActionBeanList(repository.findAll(Sort.by(Sort.Direction.ASC, Action_.nomPublic.getName())));
    }

    public List<ActionBean> findAllByOrderByNomAsc() {
        return toActionBeanList(repository.findAllByOrderByNomPublicAsc());
    }
    public List<Action> findAllByOrderByIdAsc() {
        return repository.findAllByOrderByIdAsc();
    }

    public List<ActionBean> findAllByIdEcociteIn(List<Long> listeIdEcocite) {
        return toActionBeanList(repository.findAllByIdEcociteIn(listeIdEcocite));
    }

    public List<Action> findAllByListeEcocite(List<Long> listeIdEcocite) {
        return repository.findAllByIdEcociteIn(listeIdEcocite);
    }

    public List<ActionBean> findAllByIdIn(List<Long> listeId) {
        return toActionBeanList(repository.findAllByIdIn(listeId));
    }

    public List<Action> findAll(List<Long> ids) {
        if (ids == null  || ids.isEmpty()) {
            return new ArrayList<>();
        }
        return repository.findAll(where(hasIdIn(ids)));
    }

    /**
     * Récupérer la liste des actions en fonction de critères
     *
     * @param spec spécification qui représente des critères de recherche, elle peut être null
     * @return l'ensemble des actions
     */
    public List<Action> getList(Specification<Action> spec) {
        return repository.findAll(spec);
    }

    /**
     * Récupérer les critères de filtrage
     *
     * @return l'ensemble des actions
     */
    private Specification<Action> getFiltre(Map<String, String> filtres) {
        Specification<Action> spec = null;
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
                        case "ecocite":
                            if (spec == null) {
                                spec = where(hasEcocite(Long.valueOf(filtres.get(champs))));
                            } else {
                                spec = spec.and(hasEcocite(Long.valueOf(filtres.get(champs))));
                            }
                            break;
                        case "axePrincipale":
                            if (spec == null) {
                                spec = where(hasAxePrincipale(Long.valueOf(filtres.get(champs))));
                            } else {
                                spec = spec.and(hasAxePrincipale(Long.valueOf(filtres.get(champs))));
                            }
                            break;
                        case "domaineAction":
                            if (spec == null) {
                                spec = where(hasDomaineAction(Long.valueOf(filtres.get(champs))));
                            } else {
                                spec = spec.and(hasDomaineAction(Long.valueOf(filtres.get(champs))));
                            }
                            break;
                        case "objectifVille":
                            if (spec == null) {
                                spec = where(hasObjectifVille(Long.valueOf(filtres.get(champs)), TYPE_OBJET.ACTION));
                            } else {
                                spec = spec.and(hasObjectifVille(Long.valueOf(filtres.get(champs)), TYPE_OBJET.ACTION));
                            }
                            break;
                        case "finalite":
                            if (spec == null) {
                                spec = where(hasFinalite(Long.valueOf(filtres.get(champs))));
                            } else {
                                spec = spec.and(hasFinalite(Long.valueOf(filtres.get(champs))));
                            }
                            break;
                        case "typeMission":
                            if (spec == null) {
                                spec = where(hasTypeMission(Long.valueOf(filtres.get(champs))));
                            } else {
                                spec = spec.and(hasTypeMission(Long.valueOf(filtres.get(champs))));
                            }
                            break;
                        case "typeFinancement":
                            if (spec == null) {
                                spec = where(hasTypeFinancement(filtres.get(champs)));
                            } else {
                                spec = spec.and(hasTypeFinancement(filtres.get(champs)));
                            }
                            break;
                        case "etatAvancement":
                            if (spec == null) {
                                spec = where(hasEtatAvancement(filtres.get(champs)));
                            } else {
                                spec = spec.and(hasEtatAvancement(filtres.get(champs)));
                            }
                            break;
                        case "numeroAction":
                            if (spec == null) {
                                spec = where(likeNumeroAction(filtres.get(champs)));
                            } else {
                                spec = spec.and(likeNumeroAction(filtres.get(champs)));
                            }
                            break;
                        case "etatPublication":
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
                                spec = where(hasIndicateurChoixStatus(filtres.get(champs)));
                            } else {
                                spec = spec.and(hasIndicateurChoixStatus(filtres.get(champs)));
                            }
                            break;
                        case "evaluationInnovation":
                            if (spec == null) {
                                spec = where(hasEvaluationInnovationStatus(filtres.get(champs)));
                            } else {
                                spec = spec.and(hasEvaluationInnovationStatus(filtres.get(champs)));
                            }
                            break;
                        case "indicateurMesure":
                            if (spec == null) {
                                spec = where(hasIndicateurMesureStatus(filtres.get(champs)));
                            } else {
                                spec = spec.and(hasIndicateurMesureStatus(filtres.get(champs)));
                            }
                            break;
                        case "impactProgramme":
                            if (spec == null) {
                                spec = where(hasImpactProgrammeStatus(filtres.get(champs)));
                            } else {
                                spec = spec.and(hasImpactProgrammeStatus(filtres.get(champs)));
                            }
                            break;
                        case "30jours":
                            if (spec == null) {
                                spec = where(wasModifiedWithin30Days());
                            } else {
                                spec = spec.and(wasModifiedWithin30Days());
                            }
                            break;
                    }
                }
            }
        } else {
            spec = null;
        }
        return spec;
    }

    /**
     * Récupérer la liste des action en fonction de critères de filtrage
     *
     * @return l'ensemble des actions
     */
    public List<Action> getListFiltre(Map<String, String> filtres) {
        return repository.findAll(getFiltre(filtres));
    }

    /**
     * Récupérer la liste des action en fonction de critères de filtrage sur le Type de Financement
     *
     * @return l'ensemble des actions
     */
    public List<Action> getListFiltreTypeFinancement(List<String> types) {
        Specification<Action> spec = null;
        if (types != null && !types.isEmpty()) {
            for (String type : types) {
                if (CustomValidator.isNotEmpty(type)) {
                    if (spec == null) {
                        spec = where(hasTypeFinancement(type));
                    } else {
                        spec = spec.or(hasTypeFinancement(type));
                    }
                }
            }
        }
        return repository.findAll(spec);
    }


    private Specification<Action> getSpecType(List<String> types) {
        Specification<Action> spec = null;
        if (types != null && !types.isEmpty()) {
            for (String type : types) {
                if (CustomValidator.isNotEmpty(type)) {
                    if (spec != null) {
                        spec = spec.or(hasTypeFinancement(type));
                    } else {
                        spec = where(hasTypeFinancement(type));
                    }
                }
            }
        }
        return spec;
    }

    private Specification<Action> getSpecEcocite(List<String> listeIds) {
        Specification<Action> spec = null;
        if (listeIds != null && !listeIds.isEmpty()) {
            for (String id : listeIds) {
                if(!id.equals("")) {
                    if (CustomValidator.isNotEmpty(Long.parseLong(id))) {
                        if (spec == null) {
                            spec = where(hasEcocite(Long.parseLong(id)));
                        } else {
                            spec = spec.or(hasEcocite(Long.parseLong(id)));
                        }
                    }
                }
            }
        }
        return spec;
    }

    /**
     * Récupérer la liste des action en fonction de critères de filtrage sur le Type de Financement
     *
     * @return l'ensemble des actions
     */
    public Long getCountFiltreTypeFinancement(List<String> types) {
        return repository.count(getSpecType(types));
    }

    public Long getCountFiltreTypeFinancement(List<String> types, List<String> listeIds) {
        return repository.count(where(getSpecType(types)).and(getSpecEcocite(listeIds)));
    }

    /**
     * Récupérer la liste des action en fonction de critères de filtrage sur l' idEcocite
     *
     * @return l'ensemble des actions
     */
    public List<Action> getListFiltreIdEcocite(List<String> listeIds) {
        return repository.findAll(getSpecEcocite(listeIds));
    }

    /**
     * Récupérer la liste des actions groupées par l'axe en fonction de filtre
     *
     * @param spec filtre de recherche
     * @return map avec la clé de l'{@code idAxe} et la valeur de la liste des actions associées
     */
    public Map<Long, List<Action>> getMapGroupeByAxe(Specification<Action> spec) {
        return repository.findAll(spec, sortByAxeAsc())
                .stream()
                .peek(a -> a.setIdAxe(a.getIdAxe() != null ? a.getIdAxe() : -1))
                .collect(Collectors.groupingBy(Action::getIdAxe)
                );
    }

    /**
     * Récupérer la liste des actions groupées par l'axe en fonction de filtre
     *
     * @param filtre
     * @return map avec la clé de l'{@code idAxe} et la valeur de la liste des actions associées
     */
    public Map<Long, List<Action>> getMapGroupeByAxe(Map<String, String> filtre) {
        return repository.findAll(
                    addSpec(getFiltre(filtre), hasEtatPublication(ETAT_PUBLICATION.PUBLIE)), sortByAxeAsc()
        ).stream()
                .peek(a -> a.setIdAxe(a.getIdAxe() != null ? a.getIdAxe() : -1))
                .collect(Collectors.groupingBy(Action::getIdAxe));
    }


    /**
     * Récupérer la liste des actions groupées par l'axe et associées à l'écocité
     *
     * @param idEcocite id de l'écocité associée
     * @return map avec la clé de l'{@code idAxe} et la valeur de la liste des actions associées
     * @see #getMapGroupeByAxe(Specification)
     */
    public Map<Long, List<Action>> getMapGroupeByAxe(Long idEcocite) {
        return getMapGroupeByAxe(where(hasEcocite(idEcocite)).and(hasEtatPublication(ETAT_PUBLICATION.PUBLIE)));
    }

    public List<Action> getByAxe(Long idAxe) {
        return this.repository.findAll(hasAxePrincipale(idAxe));
    }

    public ActionBean findOneAction(Long idAction) {
        return toActionBean(repository.findById(idAction).orElseThrow(() -> new EntityNotFoundException()));
    }

    /**
     * Récupérer une action par l'id
     *
     * @param id identifiant
     * @return
     * @throws NotFoundException si aucun est trouvé
     */
    public Action findById(Long id) {
        return findOne(id)
                .orElseThrow(() ->
                        new NotFoundException("Action" + " '" + id + "' n'est pas trouvée")
                );
    }

    public List<ActionBean> findAllVisibleQuery(String query) {
        return toActionBeanList(repository.findAll(where(findActionLikeQuery(query)).and(hasEtatPublication(ETAT_PUBLICATION.PUBLIE))));
    }

    public Long countActionVisible() {
        return repository.count(hasEtatPublication(ETAT_PUBLICATION.PUBLIE));
    }

    public Long countActionDone() {
        return repository.count(where(hasEtatPublication(ETAT_PUBLICATION.PUBLIE)).and(hasEtatAvancement(ETAT_AVANCEMENT.REALISE)));
    }

    public Long countActionEvaluated() {
        return repository.count(
            where(hasCaraterisationStatus(ETAPE_STATUT.VALIDER))
                .and(hasIndicateurChoixStatus(ETAPE_STATUT.VALIDER))
                .and(hasEvaluationInnovationStatus(ETAPE_STATUT.VALIDER))
                .and(hasIndicateurMesureStatus(ETAPE_STATUT.VALIDER))
                .and(hasImpactProgrammeStatus(ETAPE_STATUT.VALIDER))
                .and(hasEtatPublication(ETAT_PUBLICATION.PUBLIE))
        );
    }

    public void save(ActionBean action, JwtUser user) {
        save(action.getTo(), user);
    }

    public Action save(Action action, JwtUser user) {
        action.setUserModification(user.getEmail());
        action.setUserModificationFo(user.getEmail());
        action.setDateModification(LocalDateTime.now());
        action.setDateModificationFo(LocalDateTime.now());
        return repository.save(action);
    }


    public List<ActionBean> toActionBeanList(List<Action> actions) {
        if (actions != null) {
            List<ActionBean> actionsBean = new ArrayList<ActionBean>();
            for (Action action : actions) {
                actionsBean.add(toActionBean(action));
            }
            return actionsBean;
        } else {
            return null;
        }
    }

    public ActionBean toActionBean(Action action) {
        if (action != null) {
            List<EtapeBean> etapes = etapeService.getEtapeByIdAction(action.getId());
            List<QuestionsAvecReponseBean> questions = QuestionsEvaluationBean.createListFromData(reponsesEvaluationService.getReponseByIdAction(action.getId()), questionsEvaluationService.getAllQuestions());
            List<Business> businessList = businessService.findByIdAction(action.getId());
            return new ActionBean(action, etapes, questions, businessList);
        } else {
            return null;
        }
    }

    private Sort sortByAxeAsc() {
        return Sort.by(Sort.Direction.ASC, "idAxe");
    }

    public Long countByEcocite(final Long id) {
        return repository.count(where(hasEcocite(id)));
    }

    public Long countByNumero(final String numero) {
        return repository.count(where(hasNumeroAction(numero)));
    }

    public Long countByNumeroDifferentFromAction(final String numero, final Long id) {
        return repository.count(where(hasNumeroAction(numero)).and(not(hasId(id))));
    }

    public String delete(final Long id) {
        Action ac = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());;
        assoObjetObjectifService.deleteByAction(id);
        assoIndicateurObjetService.deleteByAction(id);
        assoActionDomainService.deleteByAction(id);
        assoActionIngenierieService.deleteByAction(id);
        businessService.removeActionReference(id);
        etapeService.deleteByAction(id);
        fileUploadService.deleteByAction(id);
        reponsesQuestionnaireEvaluationService.deleteByAction(id);
        reponsesEvaluationService.deleteByAction(id);
        assoObjetContactService.deleteByAction(id);
        repository.delete(ac);
        return "";
    }

    public Action markActionModified(final Long actionId, JwtUser user) {
        return markActionModified(repository.findById(actionId).orElseThrow(() -> new EntityNotFoundException()), user);
    }

    public Action markActionModified(final Action action, JwtUser user) {
        action.setUserModificationFo(user.getEmail());
        action.setDateModificationFo(LocalDateTime.now());
        return repository.save(action);
    }

    public Action createOne(final ActionForm tableform, JwtUser user) {
        Action action = tableform.getAction();
        action.setUserCreation(user.getEmail());
        action.setDateCreation(LocalDateTime.now());
        action.setUserModification(user.getEmail());
        action.setUserModificationFo(user.getEmail());
        action.setDateModification(LocalDateTime.now());
        action.setDateModificationFo(LocalDateTime.now());
        repository.save(action);
        etapeService.createEtapeAction(action);
        return action;
    }

    private Action cloneAction(final Action action) {
        Action clone = new Action();
        clone.setIdEcocite(action.getIdEcocite());
        clone.setIdAxe(action.getIdAxe());
        clone.setNomPublic(action.getNomPublic());
        clone.setNumeroAction(action.getNumeroAction());
        clone.setDateDebut(action.getDateDebut());
        clone.setDateFin(action.getDateFin());
        clone.setLatitude(action.getLatitude());
        clone.setLongitude(action.getLongitude());
        clone.setDescription(action.getDescription());
        clone.setLien(action.getLien());
        clone.setEchelle(action.getEchelle());
        clone.setEtatAvancement(action.getEtatAvancement());
        clone.setTypeFinancement(action.getTypeFinancement());
        clone.setTrancheExecution(action.getTrancheExecution());
        clone.setEvaluationNiveauGlobal(action.getEvaluationNiveauGlobal());
        clone.setMaitriseOuvrage(action.getMaitriseOuvrage());
        clone.setUserModification(action.getUserModification());
        clone.setDateModification(action.getDateModification());
        clone.setUserCreation(action.getUserCreation());
        clone.setDateCreation(action.getDateCreation());
        clone.setDateModificationFo(action.getDateModificationFo());
        clone.setUserModificationFo(action.getUserModificationFo());
        clone.setEtatPublication(action.getEtatPublication());
        return clone;
    }

    public Action clone(final ActionCloneForm tableform, JwtUser user) {
        try {
            Action clone = cloneAction(repository.findById(tableform.getIdOriginal()).orElseThrow(() -> new EntityNotFoundException()));
            clone.setNomPublic(tableform.getNom());
            clone.setNumeroAction(tableform.getNumero());
            clone.setDateModificationFo(LocalDateTime.now());
            clone.setDateModification(LocalDateTime.now());
            clone.setDateCreation(LocalDateTime.now());
            clone.setUserModificationFo(user.getEmail());
            clone.setUserModification(user.getEmail());
            clone.setUserCreation(user.getEmail());
            clone.setEtatPublication(ETAT_PUBLICATION.NON_PUBLIE.getCode());
            clone = repository.save(clone);
            Long idClone = clone.getId();
            try {
                Long idOriginal = tableform.getIdOriginal();
                assoObjetObjectifService.cloneForAction(idClone, idOriginal, TYPE_OBJET.ACTION);
                assoActionDomainService.cloneForAction(idClone, idOriginal);
                assoActionIngenierieService.cloneForAction(idClone, idOriginal);
                assoIndicateurObjetService.cloneForAction(idClone, idOriginal);
                reponsesQuestionnaireEvaluationService.cloneForAction(idClone, idOriginal);
                reponsesEvaluationService.cloneForAction(idClone, idOriginal);
                fileUploadService.cloneForAction(idClone, idOriginal);
                assoObjetContactService.cloneForAction(idClone, idOriginal);
                etapeService.cloneForAction(idClone, idOriginal);
                mediaModificationService.cloneForAction(idClone, idOriginal);
            } catch (Exception e) {
                delete(idClone);
                return null;
            }
            return clone;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Action> findAllActionForEcocite(final EcociteBean e) {
        return repository.findAll(where(hasEcocite(e.getId())));
    }

    public List<Action> findAllActionOrderedByName() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, Action_.nomPublic.getName()));
    }

    public List<Action> getListByIdsEcocite(final List<Long> listeEcociteId) {
        return repository.findAll(where(hasIdEcociteIn(listeEcociteId)));
    }

    public List<Action> getListPublishedByEcocite(final Long idEcocite) {
        return repository.findAll(where(hasEcocite(idEcocite)).and(hasEtatPublication(ETAT_PUBLICATION.PUBLIE)));
    }

    public Action findByNumero(final String numero) {
        return repository.findOne(where(hasNumeroAction(numero))).orElse(null);
    }

    @Transactional
    public void changeActionTypeFinancement(final Long id, final String typeFinancement, JwtUser user) {
        ActionBean actionBean = findOneAction(id);
        Action action = actionBean.getTo();
        TYPE_FINANCEMENT oldTypeFinancement = actionBean.getTypeFinancementEnum();
        if (!action.getTypeFinancement().equals(typeFinancement)) {
            action.setTypeFinancement(typeFinancement);
            TYPE_FINANCEMENT nextTypeFinancement = actionBean.getTypeFinancementEnum();
            switch (nextTypeFinancement) {
                case INGENIERIE:
                    changeActionToIngenierie(oldTypeFinancement, actionBean);
                    break;
                case INVESTISSEMENT:
                case PRISE_PARTICIPATION:
                    changeActionToInvestissement(oldTypeFinancement, actionBean);
                    break;
                case INGENIERIE_INVESTISSEMENT:
                case INGENIERIE_PRISE_PARTICIPATION:
                    changeActionToIngenierieAndInvestissement(oldTypeFinancement, actionBean);
                    break;
            }
            this.save(action, user);
        }
    }

    private void changeActionToInvestissement(final TYPE_FINANCEMENT oldTypeFinancement, final ActionBean action) {
        switch (oldTypeFinancement) {
            case INGENIERIE:
            case INGENIERIE_INVESTISSEMENT:
            case INGENIERIE_PRISE_PARTICIPATION:
                // Supprimer les type de missions associés
                assoActionIngenierieService.deleteByAction(action.getId());
                // Supprimer le questionnaire ingenierie
                reponsesQuestionnaireEvaluationService.deleteByActionAndQuestionnaireCode(action.getId(), QUESTIONNAIRE_CODE.QUESTIONNAIRE_ACTION_INGENIERIE);
                // Mettre à jour les étapes
                etapeService.createEtapeActionExisting(action, true);
                break;
            default:
                etapeService.createEtapeActionExisting(action, false);
                break;
        }
    }

    private void changeActionToIngenierie(final TYPE_FINANCEMENT oldTypeFinancement, final ActionBean action) {
        switch (oldTypeFinancement) {
            case INVESTISSEMENT:
            case PRISE_PARTICIPATION:
            case INGENIERIE_INVESTISSEMENT:
            case INGENIERIE_PRISE_PARTICIPATION:
                // Supprimer le questionnaire investissement
                reponsesQuestionnaireEvaluationService.deleteByActionAndQuestionnaireCode(action.getId(), QUESTIONNAIRE_CODE.QUESTIONNAIRE_ACTION_INVESTISSEMENT);
                // Suppression des indicateurs associés + des cibles/mesures de ces derniers
                assoIndicateurObjetService.deleteByAction(action.getId());
                // Suppression de la partie évaluation de l'innovation
                reponsesEvaluationService.deleteByAction(action.getId());
                // Mettre à jour les étapes
                etapeService.createEtapeActionExisting(action, true);
                break;
            default:
                etapeService.createEtapeActionExisting(action, false);
                break;
        }
    }

    private void changeActionToIngenierieAndInvestissement(final TYPE_FINANCEMENT oldTypeFinancement, final ActionBean action) {
        switch (oldTypeFinancement) {
            case INGENIERIE_INVESTISSEMENT:
            case INGENIERIE_PRISE_PARTICIPATION:
                etapeService.createEtapeActionExisting(action, false);
                break;
            default:
                etapeService.createEtapeActionExisting(action, true);
                break;
        }
    }

    public void createEtapeActionMissing() {
        List<Action> actions = repository.findAll(where(hasIdGreaterThan(544L)));
        etapeService.createBatchEtapeAction(actions);
    }
}
