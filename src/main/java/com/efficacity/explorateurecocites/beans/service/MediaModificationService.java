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

import com.efficacity.explorateurecocites.ajaris.AjarisClient;
import com.efficacity.explorateurecocites.ajaris.enums.JobStatus;
import com.efficacity.explorateurecocites.ajaris.enums.MediaModificationOriginType;
import com.efficacity.explorateurecocites.ajaris.enums.MediaModificationType;
import com.efficacity.explorateurecocites.beans.biz.ActionBean;
import com.efficacity.explorateurecocites.beans.biz.EcociteBean;
import com.efficacity.explorateurecocites.beans.biz.MediaBean;
import com.efficacity.explorateurecocites.beans.biz.MediaModificationBean;
import com.efficacity.explorateurecocites.beans.model.*;
import com.efficacity.explorateurecocites.beans.repository.MediaModificationRepository;
import com.efficacity.explorateurecocites.configuration.ServiceConfiguration;
import com.efficacity.explorateurecocites.utils.factory.MediaModifications;
import com.google.common.base.Objects;
import isotope.commons.services.CrudEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.beans.specification.MediaModificationSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.not;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 * <p>
 * Date de génération : 29/10/2018
 */
@Service
public class MediaModificationService extends CrudEntityService<MediaModificationRepository, MediaModification, Long> {
    public MediaModificationService(MediaModificationRepository repository) {
        super(repository);
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(MediaModificationService.class);

    @Autowired
    AjarisClient ajarisClient;

    @Autowired
    MediaService mediaService;

    @Autowired
    EcociteService ecociteService;

    @Autowired
    ActionService actionService;

    @Autowired
    AxeService axeService;

    @Autowired
    EtiquetteFinaliteService etiquetteFinaliteService;

    @Autowired
    EtiquetteAxeService etiquetteAxeService;

    @Autowired
    ServiceConfiguration serviceConfiguration;

    public List<MediaModification> getUnfinishedJob() {
        return repository.findAll(
                where(notFinished())
                        .and(olderThan(LocalDateTime.now()))
                        .and(numberTryLessThan(serviceConfiguration.getMaxRetryNumber())),
                new Sort(Sort.Direction.ASC, MediaModification_.lastModified.getName()));
    }

    public boolean hasPendingModification(Action action) {
        PageRequest pageRequest = new PageRequest(0, 1);
        return repository.findAll(
                where(hasOriginModification(MediaModificationOriginType.ACTION))
                        .and(hasId(action.getId()))
                        .and(isPending()), pageRequest).getTotalElements() > 0;
    }

    public MediaModification complete(MediaModification job) {
        MediaModifications.complete(job);
        return repository.save(job);
    }


    public MediaModification fail(MediaModification job) {
        MediaModifications.fail(job);
        return repository.save(job);
    }

    public void create(final MediaModification modification) {
        repository.save(modification);
    }

    public void markModified(final Action action) {
        markModified(MediaModificationOriginType.ACTION, action.getId());
    }

    public void markModified(final Ecocite ecocite) {
        markModified(MediaModificationOriginType.ECOCITE, ecocite.getId());
    }

    public void markModified(final EtiquetteAxe domaine) {
        markModified(MediaModificationOriginType.DOMAINE, domaine.getId());
    }

    public void markModified(final EtiquetteFinalite a) {
        markModified(MediaModificationOriginType.OBJECTIF, a.getId());
    }

    public void markModified(final MediaModificationOriginType originType, final Long id) {
        MediaModification mediaModification = repository.findOne(where(hasOriginModification(originType)).and(hasId(id)).and(hasTypeModification(MediaModificationType.UPDATE)));
        if (mediaModification != null) {
            MediaModifications.reset(mediaModification);
        } else {
            mediaModification = MediaModifications.of(originType, id);
        }
        repository.save(mediaModification);
    }

    public void markModified(final Axe axe) {
        markModified(MediaModificationOriginType.AXE, axe.getId());
    }

    public JobStatus getStatus(final Action action) {
        List<MediaModification> mediaModification = repository.findAll(where(hasOriginModification(MediaModificationOriginType.ACTION)).and(hasId(action.getId())).and(not(hasStatus(JobStatus.FINISHED))), new Sort(MediaModification_.lastModified.getName()));
        if (mediaModification.isEmpty()) {
            return JobStatus.FINISHED;
        } else {
            return JobStatus.getByCode(mediaModification.get(0).getStatus());
        }
    }

    public JobStatus getStatus(final Ecocite ecocite) {
        List<MediaModification> mediaModification = repository.findAll(where(hasOriginModification(MediaModificationOriginType.ECOCITE)).and(hasId(ecocite.getId())).and(not(hasStatus(JobStatus.FINISHED))), new Sort(MediaModification_.lastModified.getName()));
        if (mediaModification.isEmpty()) {
            return JobStatus.FINISHED;
        } else {
            return JobStatus.getByCode(mediaModification.get(0).getStatus());
        }
    }

    public void doPendingJobs() {
        List<MediaModification> pending = this.getUnfinishedJob();
        if (!pending.isEmpty()) {
            if (ajarisClient.isConnected()) {
                LOGGER.info("Updating all images - start");
                for (MediaModification job : pending) {
                    LOGGER.info("Updating all images - job(" + job.getTypeObject() + "-" + job.getIdObject() + ")" + " - start");
                    try {
                        if (this.startJob(job)) {
                            this.complete(job);
                            LOGGER.info("Updating all images - job(" + job.getTypeObject() + "-" + job.getIdObject() + ")" + " - done");
                        } else {
                            this.fail(job);
                            LOGGER.info("Updating all images - job(" + job.getTypeObject() + "-" + job.getIdObject() + ")" + " - fail");
                        }
                    } catch (Exception e) {
                        this.fail(job);
                        LOGGER.info("Updating all images - job(" + job.getTypeObject() + "-" + job.getIdObject() + ")" + " - fail");
                        LOGGER.info("Updating all images - Exception", e);
                    }
                }
                LOGGER.info("Updating all images - done");
            } else {
                LOGGER.info("Updating all images - keep alive failed - skipping");
            }
        } else {
            LOGGER.info("No images to update");
        }
    }


    public Boolean cloneAllImageFor(final MediaModification job) {
        if (Objects.equal(job.getTypeObject(), MediaModificationOriginType.ACTION.code)) {
            Action clone = actionService.findOne(job.getIdObject()).orElse(null);
            Media original = mediaService.findOne(job.getTargetId()).orElse(null);
            if (clone != null && original != null) {
                return mediaService.cloneAllImageFor(clone, original);
            }
        }
        return false;
    }

    public Boolean updateAllImageFor(final MediaModification job) {
        if (Objects.equal(job.getTypeObject(), MediaModificationOriginType.ACTION.code)) {
            Action action = actionService.findOne(job.getIdObject()).orElse(null);
            if (action != null) {
                return mediaService.updateAllImageFor(action);
            }
        } else if (Objects.equal(job.getTypeObject(), MediaModificationOriginType.ECOCITE.code)) {
            Ecocite ecocite = ecociteService.findOne(job.getIdObject()).orElse(null);
            if (ecocite != null) {
                return mediaService.updateAllImageFor(ecocite);
            }
        } else if (Objects.equal(job.getTypeObject(), MediaModificationOriginType.AXE.code)) {
            Axe axe = axeService.findOne(job.getIdObject()).orElse(null);
            if (axe != null) {
                return mediaService.updateAllImageFor(axe);
            }
        } else if (Objects.equal(job.getTypeObject(), MediaModificationOriginType.DOMAINE.code)) {
            EtiquetteAxe etiquetteAxe = etiquetteAxeService.findOne(job.getIdObject()).orElse(null);
            if (etiquetteAxe != null) {
                return mediaService.updateAllImageFor(etiquetteAxe);
            }
            return false;
        } else if (Objects.equal(job.getTypeObject(), MediaModificationOriginType.OBJECTIF.code)) {
            EtiquetteFinalite etiquetteFinalite = etiquetteFinaliteService.findOne(job.getIdObject()).orElse(null);
            if (etiquetteFinalite != null) {
                return mediaService.updateAllImageFor(etiquetteFinalite);
            }
        }
        return false;
    }

    public Boolean startJob(final MediaModification job) {
        switch (MediaModificationType.getByCode(job.getTypeModification())) {
            case UPDATE:
                return this.updateAllImageFor(job);
            case CLONE:
                return this.cloneAllImageFor(job);
            case DELETE:
                break;
            case UNKNOWN:
            default:
                break;
        }
        return false;
    }

    public void startJob(final Long id) {
        startJob(this.repository.findOne(id));
    }

    public void deleteJob(final Long id) {
        this.repository.delete(id);
    }

    public List<MediaModificationBean> findAllJobs() {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, MediaModification_.lastModified.getName()));
        return this.repository.findAll(sort).stream().map(job -> {
            String name = job.getTypeObject() + " " + job.getIdObject();
            switch (MediaModificationOriginType.getByCode(job.getTypeObject())) {
                case ACTION:
                    ActionBean action = actionService.findOneAction(job.getIdObject());
                    if (action != null) {
                        name = action.getNomPublic();
                    }
                    break;
                case ECOCITE:
                    EcociteBean ecocite = ecociteService.findOneEcocite(job.getIdObject());
                    if (ecocite != null) {
                        name = ecocite.getNom();
                    }
                    break;
                case AXE:
                    Axe axe = axeService.findById(job.getIdObject());
                    if (axe != null) {
                        name = axe.getLibelle();
                    }
                    break;
                case DOMAINE:
                    EtiquetteAxe etqAxe = etiquetteAxeService.findById(job.getIdObject());
                    if (etqAxe != null) {
                        name = etqAxe.getLibelle();
                    }
                    break;
                case OBJECTIF:
                    EtiquetteFinalite etqFinalite = etiquetteFinaliteService.findById(job.getIdObject());
                    if (etqFinalite != null) {
                        name = etqFinalite.getLibelle();
                    }
                    break;
            }
            return new MediaModificationBean(job, name);
        }).collect(Collectors.toList());
    }

    public void cloneForAction(final Long idClone, final Long idOriginal) {
        List<MediaBean> mediasToClone = mediaService.getAllMediaForAction(idOriginal);
        List<MediaModification> cloneJobs = mediasToClone
                .stream()
                .map(mediaToClone -> {
                    MediaModification cloneJob = new MediaModification();
                    cloneJob.setTypeObject(MediaModificationOriginType.ACTION.code);
                    cloneJob.setIdObject(idClone);
                    cloneJob.setTargetId(mediaToClone.getId());
                    cloneJob.setTargetType(MediaModificationOriginType.ACTION.code);
                    cloneJob.setStatus(JobStatus.PENDING.code);
                    cloneJob.setTypeModification(MediaModificationType.CLONE.code);
                    cloneJob.setLastModified(LocalDateTime.now());
                    return cloneJob;
                })
                .collect(Collectors.toList());
        this.repository.save(cloneJobs);
    }

    public void delete(final Long id, final MediaModificationOriginType originType) {
        MediaModification cloneJob = new MediaModification();
        cloneJob.setIdObject(id);
        cloneJob.setTypeObject(originType.code);
        cloneJob.setStatus(JobStatus.PENDING.code);
        cloneJob.setTypeModification(MediaModificationType.DELETE.code);
        cloneJob.setLastModified(LocalDateTime.now());
        this.repository.save(cloneJob);
    }
}
