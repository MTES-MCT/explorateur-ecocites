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

import com.efficacity.explorateurecocites.beans.biz.ActionBean;
import com.efficacity.explorateurecocites.beans.biz.EtapeBean;
import com.efficacity.explorateurecocites.beans.model.Action;
import com.efficacity.explorateurecocites.beans.model.Ecocite;
import com.efficacity.explorateurecocites.beans.model.Etape;
import com.efficacity.explorateurecocites.beans.repository.EtapeRepository;
import com.efficacity.explorateurecocites.beans.repository.specifications.EtapeSpecifications;
import com.efficacity.explorateurecocites.utils.enumeration.*;
import isotope.commons.exceptions.NotFoundException;
import isotope.commons.services.CrudEntityService;
import isotope.modules.user.lightbeans.UserLightbean;
import isotope.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.beans.repository.specifications.EtapeSpecifications.*;
import static com.efficacity.explorateurecocites.beans.specification.EtapeSpecifications.*;
import static org.springframework.data.jpa.domain.Specification.where;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 * <p>
 * Date de génération : 06/02/2018
 */
@Service
public class EtapeService extends CrudEntityService<EtapeRepository, Etape, Long> {

    @Autowired
    UserService userService;

    @Autowired
    AssoIndicateurObjetService assoIndicateurObjetService;

    public EtapeService(EtapeRepository repository) {
        super(repository);
    }

    public List<EtapeBean> getEtapeByIdAction(Long idAction) {
        return toEtapeBeanList(repository.findByIdObjetAndTypeObjet(idAction, TYPE_OBJET.ACTION.getCode()));
    }

    public List<EtapeBean> getEtapeByIdEcocite(Long idEcocite) {
        return toEtapeBeanList(repository.findByIdObjetAndTypeObjet(idEcocite, TYPE_OBJET.ECOCITE.getCode()));
    }

    private List<EtapeBean> toEtapeBeanList(List<Etape> etapes) {
        if (etapes != null) {
            return etapes.stream().map(this::toEtapeBean).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    public EtapeBean toEtapeBean(Etape etape) {
        if (etape != null) {
            if (Objects.equals(etape.getCodeEtape(), ETAPE_ACTION.MESURE_INDICATEUR.getCode()) ||
                    Objects.equals(etape.getCodeEtape(), ETAPE_ECOCITE.MESURE_INDICATEUR.getCode())) {
                return new EtapeBean(etape, assoIndicateurObjetService.getLastMesureFor(etape.getTypeObjet(), etape.getIdObjet()));
            }
            return new EtapeBean(etape);
        } else {
            return null;
        }
    }

    /**
     * Récupérer une etape par l'id
     *
     * @param id identifiant
     * @return
     * @throws NotFoundException si aucun est trouvé
     */
    public Etape findById(Long id) {
        return findOne(id)
                .orElseThrow(() ->
                        new NotFoundException("Etape" + " '" + id + "' n'est pas trouvée")
                );
    }

    /**
     * Récupérer une etape par l'id et l'action
     *
     * @param id       id de l'etape
     * @param idAction id de l'action
     * @return etape de la BDD ou {@code null} si aucun n'est trouvé
     */
    public Etape findByIdEtapeAndAction(Long id, Long idAction) {
        return repository.findOne(where(EtapeSpecifications.hasId(id))
                .and(hasIdObject(idAction)).and(EtapeSpecifications.hasAction())).orElse(null);
    }

    /**
     * Récupérer une etape par l'id et l'action
     *
     * @param id        id de l'etape
     * @param idEcocite id de l'action
     * @return etape de la BDD ou {@code null} si aucun n'est trouvé
     */
    public Etape findByIdEtapeAndEcocite(Long id, Long idEcocite) {
        return repository.findOne(where(EtapeSpecifications.hasId(id))
                .and(hasIdObject(idEcocite)).and(EtapeSpecifications.hasEcocite())).orElse(null);
    }

    /**
     * Récupérer l'etape par l'action et son nom
     *
     * @param idObject id de l'objet
     * @param typeObjet type de l'objet
     * @param etapeCode code de l'etape
     * @return commentaire de l'etape
     */
    public Etape getEtapeByIdTypeAndCode(Long idObject, TYPE_OBJET typeObjet, String etapeCode) {
        return repository.findOne(where(hasIdObject(idObject)).and(EtapeSpecifications.hasTypeObject(typeObjet))
                .and(hasCodeEtape(etapeCode))).orElse(null);
    }

    /**
     * Récupérer l'etape par l'action et son nom
     *
     * @param idAction  id de l'actiom
     * @param etapeCode code de l'etape
     * @return commentaire de l'etape
     */
    public Etape getEtapeByActionAndCode(Long idAction, String etapeCode) {
        return repository.findOne(where(hasIdObject(idAction)).and(EtapeSpecifications.hasAction()).and(hasCodeEtape(etapeCode))).orElse(null);
    }

    /**
     * Récupérer l'etape par l'action et son nom
     *
     * @param idEcocite id de l'ecocite
     * @param etapeCode code de l'etape
     * @return commentaire de l'etape
     */
    public Etape getEtapeByEcociteAndCode(Long idEcocite, String etapeCode) {
        return repository.findOne(where(hasIdObject(idEcocite)).and(EtapeSpecifications.hasEcocite()).and(hasCodeEtape(etapeCode))).orElse(null);
    }


    /**
     * Mettre à jour le commentaire de l'etape et le sauvegarder à la BDD
     *
     * @param etape       l'etape en cours
     * @param commentaire nouveau commentaire à enregistrer
     * @return Etape mise à jour
     */
    public Etape majCommentaire(Etape etape, String commentaire) {
        etape.setCommentaire(commentaire);
        return repository.save(etape);
    }

    public void updateStatusEtape(final EtapeBean etape, final ETAPE_STATUT statut, Long userId) {
        etape.setStatut(statut.getCode());
        etape.setValideePar(userId);
        etape.setDateValidee(LocalDateTime.now());
        repository.save(etape.getTo());
    }

    public String getTitrePage(final EtapeBean etapeBean) {
        String titrePage = etapeBean.getStatutEnum().getLibelle();
        if (etapeBean.getStatutEnum() == ETAPE_STATUT.VALIDER) {
            if (etapeBean.getValideePar() != null) {
                UserLightbean user = userService.getUserById(etapeBean.getValideePar()).orElse(null);
                if (user != null) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    titrePage = titrePage + " par " + user.getFirstname() + " " + user.getLastname() + " le " + etapeBean.getDateValidation().format(formatter);
                }
            }
        }
        return titrePage;
    }

    public void deleteByEcocite(final Long id) {
        repository.deleteAll(repository.findByIdObjetAndTypeObjet(id, TYPE_OBJET.ECOCITE.getCode()));
    }

    public void createEtapeEcocite(Ecocite ecocite) {
        if(ecocite != null){
            if (repository.count(where(belongToEcocite()).and(hasIdObjet(ecocite.getId()))) == 0) {
                ETAPE_ECOCITE_EDITION listToCreate[] = ETAPE_ECOCITE_EDITION.values();
                for (ETAPE_ECOCITE_EDITION etapeEcocite : listToCreate) {
                    Etape etape = new Etape();
                    etape.setIdObjet(ecocite.getId());
                    etape.setTypeObjet(TYPE_OBJET.ECOCITE.getCode());
                    etape.setCodeEtape(etapeEcocite.getCode());
                    etape.setStatut(ETAPE_STATUT.A_RENSEIGNER.getCode());
                    repository.save(etape);
                }
            }
        }
    }

    public void createBatchEtapeEcocite(List<Ecocite> ecocites) {
        List<Etape> etapes = new ArrayList<>();
        for (Ecocite ecocite : ecocites) {
            if (ecocite != null) {
                ETAPE_ECOCITE_EDITION listToCreate[] = ETAPE_ECOCITE_EDITION.values();
                for (ETAPE_ECOCITE_EDITION etapeEcocite : listToCreate) {
                    Etape etape = new Etape();
                    etape.setIdObjet(ecocite.getId());
                    etape.setTypeObjet(TYPE_OBJET.ECOCITE.getCode());
                    etape.setCodeEtape(etapeEcocite.getCode());
                    etape.setStatut(ETAPE_STATUT.A_RENSEIGNER.getCode());
                    etapes.add(etape);
                }
            }
        }
        repository.saveAll(etapes);
    }

    public void createEtapeAction(Action action) {
        if (action != null) {
            if (repository.count(where(belongToAction()).and(hasIdObjet(action.getId()))) == 0) {
                ActionBean actionBean = new ActionBean(action, null, null, null);
                ETAPE_ACTION_EDITION listToCreate[] = getEtapeForAction(actionBean);
                for (ETAPE_ACTION_EDITION etapeAction : listToCreate) {
                    Etape etape = new Etape();
                    etape.setIdObjet(actionBean.getId());
                    etape.setTypeObjet(TYPE_OBJET.ACTION.getCode());
                    etape.setCodeEtape(etapeAction.getCode());
                    etape.setStatut(ETAPE_STATUT.A_RENSEIGNER.getCode());
                    repository.save(etape);
                }
            }
        }
    }

    public void createBatchEtapeAction(List<Action> actions) {
        List<Etape> etapes = new ArrayList<>();
        for (Action action : actions) {
            ActionBean actionBean = new ActionBean(action, null, null, null);
            ETAPE_ACTION_EDITION listToCreate[] = getEtapeForAction(actionBean);
            for (ETAPE_ACTION_EDITION etapeAction : listToCreate) {
                Etape etape = new Etape();
                etape.setIdObjet(actionBean.getId());
                etape.setTypeObjet(TYPE_OBJET.ACTION.getCode());
                etape.setCodeEtape(etapeAction.getCode());
                etape.setStatut(ETAPE_STATUT.A_RENSEIGNER.getCode());
                etapes.add(etape);
            }
        }
        repository.saveAll(etapes);
    }

    private ETAPE_ACTION_EDITION[] getEtapeForAction(final ActionBean actionBean) {
        final ETAPE_ACTION_EDITION[] listToCreate;
        if (actionBean.isTypeFinancementIngenierie()) {
            listToCreate = ETAPE_ACTION_EDITION.getListEtapeActionIngenerie();
        } else if (actionBean.isTypeFinancementInvestissementOuPriseParticipation()) {
            listToCreate = ETAPE_ACTION_EDITION.getListEtapeActionInvestissement();
        } else {
            listToCreate = ETAPE_ACTION_EDITION.getListEtapeActionIngenerieEtInvestissement();
        }
        return listToCreate;
    }

    public void deleteByAction(final Long id) {
        repository.deleteAll(repository.findByIdObjetAndTypeObjet(id, TYPE_OBJET.ACTION.getCode()));
    }

    public boolean setEtapeActionValidationValider(final Long id, final String code) {
        Etape etape = getEtapeByActionAndCode(id, code);
        if (Objects.equals(etape.getStatut(), ETAPE_STATUT.VALIDER.getCode())) {
            return false;
        }
        etape.setStatut(ETAPE_STATUT.VALIDER.getCode());
        repository.save(etape);
        return true;
    }

    public boolean setEtapeActionValidationARenseigner(final Long id, final String code) {
        Etape etape = getEtapeByActionAndCode(id, code);
        if (Objects.equals(etape.getStatut(), ETAPE_STATUT.A_RENSEIGNER.getCode())) {
            return false;
        }
        etape.setStatut(ETAPE_STATUT.A_RENSEIGNER.getCode());
        repository.save(etape);
        return true;
    }

    public boolean setEtapeEcociteValidationValider(final Long id, final String code) {
        Etape etape = getEtapeByEcociteAndCode(id, code);
        if (Objects.equals(etape.getStatut(), ETAPE_STATUT.VALIDER.getCode())) {
            return false;
        }
        etape.setStatut(ETAPE_STATUT.VALIDER.getCode());
        repository.save(etape);
        return true;
    }
    public boolean setEtapeEcociteValidationRenseigner(final Long id, final String code) {
        Etape etape = getEtapeByEcociteAndCode(id, code);
        if (Objects.equals(etape.getStatut(), ETAPE_STATUT.A_RENSEIGNER.getCode())) {
            return false;
        }
        etape.setStatut(ETAPE_STATUT.A_RENSEIGNER.getCode());
        repository.save(etape);
        return true;
    }

    public Long countEtapeByActionCodeAndStatus(final List<Long> id, final ETAPE_ACTION etapeCode, final ETAPE_STATUT status) {
        if (id == null || id.isEmpty()) {
            return 0L;
        }
        return repository.count(where(hasIdObjectIn(id))
                .and(belongToAction())
                .and(hasCodeEtape(etapeCode.getCode()))
                .and(hasStatus(status.getCode())));
    }

    public void createEtapeActionExisting(final ActionBean action, final Boolean forceStatusToEmpty) {
        ETAPE_ACTION_EDITION[] listEtapeForTypeOfAction = getEtapeForAction(action);
        List<Etape> listToDelete = new ArrayList<>();
        List<Etape> listToSave = new ArrayList<>();
        for (EtapeBean etape : action.getEtapesList()) {
            if (Arrays.stream(listEtapeForTypeOfAction).noneMatch(e -> Objects.equals(e.getCode(), etape.getTo().getCodeEtape()))) {
                listToDelete.add(etape.getTo());
            } else {
                listToSave.add(etape.getTo());
            }
        }
        if (forceStatusToEmpty) {
            listToSave.forEach(e -> e.setStatut(ETAPE_STATUT.A_RENSEIGNER.getCode()));
        }
        for (ETAPE_ACTION_EDITION etapeAction : listEtapeForTypeOfAction) {
            if (action.getEtapesList().stream().noneMatch(e -> Objects.equals(e.getTo().getCodeEtape(), etapeAction.getCode()))) {
                Etape etape = new Etape();
                etape.setTypeObjet(TYPE_OBJET.ACTION.getCode());
                etape.setIdObjet(action.getId());
                etape.setCodeEtape(etapeAction.getCode());
                etape.setStatut(ETAPE_STATUT.A_RENSEIGNER.getCode());
                listToSave.add(etape);
            }
        }
        repository.deleteAll(listToDelete);
        repository.saveAll(listToSave);
    }

    public List<Etape> cloneForAction(final Long idClone, final Long idOriginal) {
        List<Etape> clones = repository.findByIdObjetAndTypeObjet(idOriginal, TYPE_OBJET.ACTION.getCode())
                .stream()
                .map(original -> {
                    Etape clone = new Etape();
                    clone.setIdObjet(idClone);
                    clone.setTypeObjet(TYPE_OBJET.ACTION.getCode());
                    clone.setStatut(ETAPE_STATUT.A_RENSEIGNER.getCode());
                    clone.setCodeEtape(original.getCodeEtape());
                    clone.setCommentaire(original.getCommentaire());
                    clone.setDateValidee(original.getDateValidee());
                    clone.setValideePar(original.getValideePar());
                    return clone;
                })
                .collect(Collectors.toList());
        return repository.saveAll(clones);
    }
}
