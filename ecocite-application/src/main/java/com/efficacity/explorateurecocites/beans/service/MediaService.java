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

import com.efficacity.explorateurecocites.ajaris.AjarisClient;
import com.efficacity.explorateurecocites.ajaris.beans.Document;
import com.efficacity.explorateurecocites.ajaris.beans.Rdf;
import com.efficacity.explorateurecocites.ajaris.beans.X4dUpdate;
import com.efficacity.explorateurecocites.beans.biz.ActionBean;
import com.efficacity.explorateurecocites.beans.biz.EcociteBean;
import com.efficacity.explorateurecocites.beans.biz.FileUploadBean;
import com.efficacity.explorateurecocites.beans.biz.MediaBean;
import com.efficacity.explorateurecocites.beans.model.*;
import com.efficacity.explorateurecocites.beans.repository.MediaRepository;
import com.efficacity.explorateurecocites.ui.bo.forms.MediaForm;
import com.efficacity.explorateurecocites.ui.bo.forms.MediaFormFromFile;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.ui.bo.utils.RG;
import com.efficacity.explorateurecocites.utils.enumeration.FILE_TYPE;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import isotope.commons.exceptions.NotFoundException;
import isotope.commons.services.CrudEntityService;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.efficacity.explorateurecocites.beans.model.Media_;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import javax.persistence.EntityNotFoundException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.beans.specification.MediaSpecifications.*;
import static org.springframework.data.jpa.domain.Specification.where;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 * <p>
 * Date de génération : 29/10/2018
 */
@Service
public class MediaService extends CrudEntityService<MediaRepository, Media, Long> {
    public MediaService(MediaRepository repository) {
        super(repository);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(MediaService.class);

    @Autowired
    AjarisClient ajarisClient;

    @Autowired
    MediaModificationService mediaModificationService;

    @Autowired
    RegionService regionService;

    @Autowired
    ActionService actionService;

    @Autowired
    EcociteService ecociteService;

    @Autowired
    AssoActionDomainService assoActionDomainService;

    @Autowired
    AssoObjetObjectifService assoObjetObjectifService;

    @Autowired
    EtiquetteAxeService etiquetteAxeService;

    @Autowired
    EtiquetteFinaliteService etiquetteFinaliteService;

    @Autowired
    FinaliteService finaliteService;

    @Autowired
    AxeService axeService;

    @Autowired
    FileUploadService fileUploadService;

    public MediaForm getMediaFormById(Long id) {
        Media media = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());;
        return this.getMediaFormById(media);
    }

    public MediaForm getMediaFormById(Media media) {
        Rdf response = ajarisClient.getFileByID(media.getIdAjaris());
        if (response == null) {
            return null;
        }
        MediaForm res = new MediaForm();
        res.setIdAjaris(media.getIdAjaris());
        res.setCopyright(response.getDocument().getCopyrightValue());
        res.setTitle(response.getDocument().getTitreValue());
        res.setDatePriseVue(response.getDocument().getDatePriseDeVue());
        res.setLegende(response.getDocument().getLegendeValue());
        res.setDescription(response.getDocument().getDescriptionValue());
        res.setNumerisation(media.getNumero());
        res.setLatitude(response.getDocument().getLatitude());
        res.setLongitude(response.getDocument().getLongitude());
        res.setPreviewUrl(response.getDocument().getPreviewUrl());
        return res;
    }


    public MediaForm createById(final Errors errors, final Locale locale, final MessageSourceService messageSource, Integer idAjaris, final String type, final Integer level, final Long idObject) {
        if (this.canCreateMedia(errors, messageSource, locale, type, idObject, level)) {
            Rdf response = ajarisClient.getFileByID(idAjaris);
            if (response != null) {
                Media media = new Media();
                media.setTypeObject(type);
                media.setIdObject(idObject);
                media.setIdAjaris(idAjaris);
                media.setLevel(level);
                media.setTitle(response.getDocument().getTitreValue());
                Document document = response.getDocument();
                if (Objects.equals(document.getValidation(), Document.VALIDE)) {
                    errors.rejectValue("idAjaris", "error.media.ajaris.owned", messageSource.getMessageSource().getMessage("error.media.ajaris.owned", null, locale));
                } else {
                    document.setValidation(Document.VALIDE);
                    if (!ajarisClient.update(idAjaris, new X4dUpdate(document))) {
                        errors.rejectValue("idAjaris", "error.media.ajaris.own.fail", messageSource.getMessageSource().getMessage("error.media.ajaris.own.fail", null, locale));
                    } else {
                        return this.save(media);
                    }
                }
            } else {
                errors.rejectValue("idAjaris", "error.media.ajaris.notfound", messageSource.getMessageSource().getMessage("error.media.ajaris.notfound", null, locale));
            }
        }
        return null;
    }

    public MediaForm updateImage(final MediaForm form, final Long idMedia) {
        Media media = repository.findById(idMedia).orElseThrow(() -> new EntityNotFoundException());;
        MediaForm res = null;
        media.setTitle(form.getTitle());
        media.setNumero(form.getNumerisation());
        if (TYPE_OBJET.ACTION.getCode().equals(media.getTypeObject())) {
            ActionBean action = actionService.findOneAction(media.getIdObject());
            if (action == null) {
                throw new NotFoundException("L'action avec l'id " + media.getIdObject() + " n'existe pas.");
            } else {
                res = updateImage(media, form, action.getTo());
            }
        } else if (TYPE_OBJET.ECOCITE.getCode().equals(media.getTypeObject())) {
            EcociteBean ecocite = ecociteService.findOneEcocite(media.getIdObject());
            if (ecocite == null) {
                throw new NotFoundException("L'Écocité avec l'id " + media.getIdObject() + " n'existe pas.");
            } else {
                res = updateImage(media, form, ecocite.getTo());
            }
        }
        if (res != null) {
            this.save(media);
        }
        return res;
    }

    private MediaForm updateImage(final Media media, final MediaForm form, final Action action) {
        Document document = fillCommon(form);
        EcociteBean ecocite = ecociteService.findOneEcocite(action.getIdEcocite());
        String region = regionService.findById(ecocite.getIdRegion()).getNom();
        List<AssoActionDomain> assoActionDomains = assoActionDomainService.getListByAction(action.getId());
        List<AssoObjetObjectif> assoObjetObjectifs = assoObjetObjectifService.getListByAction(action.getId());
        List<EtiquetteAxe> etiquetteAxes = etiquetteAxeService.getList(assoActionDomains.stream().map(AssoActionDomain::getIdDomain).collect(Collectors.toList()));
        List<EtiquetteFinalite> etiquetteFinalites = etiquetteFinaliteService.getList(assoObjetObjectifs.stream().map(AssoObjetObjectif::getIdObjectif).collect(Collectors.toList()));
        String domaines = etiquetteAxes.stream().map(EtiquetteAxe::getLibelle).collect(Collectors.joining(";"));
        String objectifs = etiquetteFinalites.stream().map(EtiquetteFinalite::getLibelle).collect(Collectors.joining(";"));
        List<Finalite> finalites = finaliteService.getList(etiquetteFinalites.stream().map(EtiquetteFinalite::getIdFinalite).collect(Collectors.toList()));
        Axe axe = action.getId() != null ? axeService.findById(action.getIdAxe()) : null;
        String axeLibelle = axe != null ? axe.getLibelle() : "";
        document.setEcocite(ecocite.getNom());
        document.setAxe(axeLibelle);
        document.setDomaine(domaines);
        document.setFinalites(finalites.stream().map(Finalite::getLibelle).collect(Collectors.joining(";")));
        document.setObjectif(objectifs);
        document.setLieu(region);
        document.fillWithAction(action);
        if (ajarisClient.update(media.getIdAjaris(), new X4dUpdate(document))) {
            return form;
        } else {
            return null;
        }
    }

    private MediaForm updateImage(final Media media, final MediaForm form, final Ecocite ecocite) {
        String region = regionService.findById(ecocite.getIdRegion()).getNom();
        List<AssoObjetObjectif> assoObjetObjectifs = assoObjetObjectifService.getListByEcocite(ecocite.getId());
        List<EtiquetteFinalite> etiquetteFinalites = etiquetteFinaliteService.getList(assoObjetObjectifs.stream().map(AssoObjetObjectif::getIdObjectif).collect(Collectors.toList()));
        List<Finalite> finalites = finaliteService.getList(etiquetteFinalites.stream().map(EtiquetteFinalite::getIdFinalite).collect(Collectors.toList()));
        String objectifs = etiquetteFinalites.stream().map(EtiquetteFinalite::getLibelle).collect(Collectors.joining(";"));
        Document document = fillCommon(form);
        document.fillWithEcocite(ecocite);
        document.setObjectif(objectifs);
        document.setFinalites(finalites.stream().map(Finalite::getLibelle).collect(Collectors.joining(";")));
        document.setLieu(region);
        if (ajarisClient.update(media.getIdAjaris(), new X4dUpdate(document))) {
            return form;
        } else {
            return null;
        }
    }

    public MediaForm uploadImage(final Errors errors, final MessageSourceService messageSource, final Locale locale, MediaFormFromFile form, final String typeObject, final Integer level, final Long idObject) {
        if (this.canCreateMedia(errors, messageSource, locale, typeObject, idObject, level)) {
            if (TYPE_OBJET.ACTION.getCode().equals(typeObject)) {
                ActionBean action = actionService.findOneAction(idObject);
                if (action == null) {
                    throw new NotFoundException("L'action avec l'id " + idObject + " n'existe pas.");
                } else {
                    return uploadImage(form, level, action);
                }
            } else if (TYPE_OBJET.ECOCITE.getCode().equals(typeObject)) {
                EcociteBean ecocite = ecociteService.findOneEcocite(idObject);
                if (ecocite == null) {
                    throw new NotFoundException("L'Écocité avec l'id " + idObject + " n'existe pas.");
                } else {
                    return uploadImage(form, level, ecocite);
                }
            }
            return null;
        }
        return null;
    }

    private Boolean canCreateMedia(final Errors errors, final MessageSourceService messageSource, final Locale locale, final String typeObject, final Long idObject, final Integer level) {
        Long count = this.repository.count(with(typeObject, idObject, level));
        if (level == 1) {
            if (count >= RG.Action.Fichier.MAX_IMAGE_PRINCIPALE) {
                errors.reject("error.media.main.max", messageSource.getMessageSource().getMessage("error.media.main.max", null, locale));
                return false;
            }
            return true;
        } else {
            if (count > RG.Action.Fichier.MAX_IMAGES_SECONDAIRES) {
                errors.reject("error.media.secondary.max", messageSource.getMessageSource().getMessage("error.media.secondary.max", null, locale));
                return false;
            }
            return true;
        }
    }

    private MediaForm uploadImage(final MediaFormFromFile form, final Integer level, final ActionBean action) {
        EcociteBean ecocite = ecociteService.findOneEcocite(action.getIdEcocite());
        String region = regionService.findById(ecocite.getIdRegion()).getNom();
        List<AssoActionDomain> assoActionDomains = assoActionDomainService.getListByAction(action.getId());
        List<AssoObjetObjectif> assoObjetObjectifs = assoObjetObjectifService.getListByAction(action.getId());
        List<EtiquetteAxe> etiquetteAxes = etiquetteAxeService.getList(assoActionDomains.stream().map(AssoActionDomain::getIdDomain).collect(Collectors.toList()));
        List<EtiquetteFinalite> etiquetteFinalites = etiquetteFinaliteService.getList(assoObjetObjectifs.stream().map(AssoObjetObjectif::getIdObjectif).collect(Collectors.toList()));
        String domaines = etiquetteAxes.stream().map(EtiquetteAxe::getLibelle).collect(Collectors.joining(";"));
        String objectifs = etiquetteFinalites.stream().map(EtiquetteFinalite::getLibelle).collect(Collectors.joining(";"));
        Axe axe = action.getIdAxe() != null ? axeService.getOne(action.getIdAxe()) : null;
        String axeLibelle = axe != null ? axe.getLibelle() : "";
        List<Finalite> finalites = finaliteService.getList(etiquetteFinalites.stream().map(EtiquetteFinalite::getIdFinalite).collect(Collectors.toList()));
        Document document = fillCommon(form);
        document.fillWithAction(action.getTo());
        document.setEcocite(ecocite.getNom());
        document.setAxe(axeLibelle);
        document.setDomaine(domaines);
        document.setObjectif(objectifs);
        document.setFinalites(finalites.stream().map(Finalite::getLibelle).collect(Collectors.joining(";")));
        document.setLieu(region);
        Integer idAjaris = ajarisClient.upload(form.getFile(), new X4dUpdate(document));
        if (idAjaris != -1) {
            return this.createFromAjaris(idAjaris, form, level, action);
        } else {
            return null;
        }
    }

    private MediaForm createFromAjaris(final Integer idAjaris, final MediaFormFromFile form, final Integer level, final ActionBean actionBean) {
        Media media = new Media();
        media.setIdObject(actionBean.getId());
        media.setIdAjaris(idAjaris);
        media.setLevel(level);
        media.setTypeObject(TYPE_OBJET.ACTION.getCode());
        media.setTitle(form.getTitle());
        media.setNumero(form.getNumerisation());
        return this.save(media);
    }

    private MediaForm uploadImage(final MediaFormFromFile form, final Integer level, final EcociteBean ecocite) {
        Document document = fillCommon(form);
        String region = regionService.findById(ecocite.getIdRegion()).getNom();
        List<AssoObjetObjectif> assoObjetObjectifs = assoObjetObjectifService.getListByEcocite(ecocite.getId());
        List<EtiquetteFinalite> etiquetteFinalites = etiquetteFinaliteService.getList(assoObjetObjectifs.stream().map(AssoObjetObjectif::getIdObjectif).collect(Collectors.toList()));
        List<Finalite> finalites = finaliteService.getList(etiquetteFinalites.stream().map(EtiquetteFinalite::getIdFinalite).collect(Collectors.toList()));
        String objectifs = etiquetteFinalites.stream().map(EtiquetteFinalite::getLibelle).collect(Collectors.joining(";"));
        document.fillWithEcocite(ecocite.getTo());
        document.setObjectif(objectifs);
        document.setFinalites(finalites.stream().map(Finalite::getLibelle).collect(Collectors.joining(";")));
        document.setLieu(region);
        Integer idAjaris = ajarisClient.upload(form.getFile(), new X4dUpdate(document));
        if (idAjaris != -1) {
            return this.createFromAjaris(idAjaris, form, level, ecocite);
        } else {
            return null;
        }
    }


    private MediaForm createFromAjaris(final Integer idAjaris, final MediaFormFromFile form, final Integer level, final EcociteBean ecociteBean) {
        Media media = new Media();
        media.setIdObject(ecociteBean.getId());
        media.setIdAjaris(idAjaris);
        media.setTypeObject(TYPE_OBJET.ECOCITE.getCode());
        media.setTitle(form.getTitle());
        media.setNumero(form.getNumerisation());
        media.setLevel(level);
        return this.save(media);
    }

    private Document fillCommon(final MediaFormFromFile form) {
        Document document = new Document(form.getTitle(), form.getCopyright(), form.getLegende(), form.getDescription(), form.getLatitude(), form.getLongitude());
        document.setTitre(form.getTitle());
        document.setDatePriseDeVueFromExplorateurFormat(form.getDatePriseVue());
        document.setLegende(form.getLegende());
        document.setDescription(form.getDescription());
        document.setCopyright(form.getCopyright());
        return document;
    }

    private Document fillCommon(final MediaForm form) {
        Document document = new Document(form.getTitle(), form.getCopyright(), form.getLegende(), form.getDescription(), form.getLatitude(), form.getLongitude());
        document.setTitre(form.getTitle());
        document.setDatePriseDeVueFromExplorateurFormat(form.getDatePriseVue());
        document.setDescription(form.getDescription());
        document.setLegende(form.getLegende());
        document.setCopyright(form.getCopyright());
        return document;
    }

    public List<MediaBean> getAllMediaForAction(final ActionBean action) {
        return getAllMediaForAction(action.getTo());
    }

    public List<MediaBean> getAllMediaForAction(final Action action) {
        Sort sort = Sort.by(Sort.Direction.ASC, Media_.level.getName(), Media_.numero.getName());
        List<Media> results = repository.findAll(withAction(action), sort);
        return results.stream().map(MediaBean::new).collect(Collectors.toList());
    }

    public List<MediaBean> getAllMediaForAction(final Long id) {
        List<Media> results = repository.findAll(withAction(id));
        return results.stream().map(MediaBean::new).collect(Collectors.toList());
    }

    public List<MediaBean> getAllMediaForEcocite(final EcociteBean ecocite) {
        return getAllMediaForEcocite(ecocite.getTo());
    }

    public List<MediaBean> getAllMediaForEcocite(final Ecocite ecocite) {
        Sort sort = Sort.by(Sort.Direction.ASC, Media_.level.getName(), Media_.numero.getName());
        List<Media> results = repository.findAll(withEcocite(ecocite), sort);
        return results.stream().map(MediaBean::new).collect(Collectors.toList());
    }

    public void deleteMedia(final Long idMedia) {
        Media media = this.repository.findById(idMedia).orElseThrow(() -> new EntityNotFoundException());;
        Rdf response = ajarisClient.getFileByID(media.getIdAjaris());
        Document document = response.getDocument();
        document.setValidation(Document.NON_VALIDE);
        if (!ajarisClient.update(media.getIdAjaris(), new X4dUpdate(document))) {
            LOGGER.error("Deletion from Ajaris failed for image {}", media.getIdAjaris());
        }
        repository.deleteById(idMedia);
    }

    public MediaForm save(Media media) {
        if (media == null) {
            return null;
        }
        return new MediaForm(this.repository.save(media));
    }

    public List<Media> getMainMediaForAction(final ActionBean action) {
        return repository.findAll(where(isMainMedia()).and(withAction(action)));
    }

    public List<Media> getOtherMediaForAction(final ActionBean action) {
        Sort sort = Sort.by(Sort.Direction.ASC, Media_.level.getName(), Media_.numero.getName());
        return repository.findAll(where(isSecondaryMedia()).and(withAction(action)), sort);
    }

    public List<Media> getMainMediaForEcocite(final EcociteBean ecocite) {
        return repository.findAll(where(isMainMedia()).and(withEcocite(ecocite)));
    }

    public List<Media> getOtherMediaForEcocite(final EcociteBean ecocite) {
        Sort sort = Sort.by(Sort.Direction.ASC, Media_.level.getName(), Media_.numero.getName());
        return repository.findAll(where(isSecondaryMedia()).and(withEcocite(ecocite)), sort);
    }

    public String getUrlForIdMedia(final Long idMedia) {
        Media media = this.repository.findById(idMedia).orElseThrow(() -> new EntityNotFoundException());;
        Rdf response = ajarisClient.getFileByID(media.getIdAjaris());
        if (response != null && response.getDocument() != null && response.getDocument().getMainUrl() != null) {
            return response.getDocument().getMainUrl();
        } else {
            return null;
        }
    }

    public Boolean updateAllImageFor(final Axe axe) {
        List<Action> actions = actionService.getByAxe(axe.getId());
        Boolean res = true;
        for (Action action : actions) {
            res = res && updateAllImageFor(action);
        }
        return res;
    }

    public Boolean updateAllImageFor(final Ecocite ecocite) {
        String region = regionService.findById(ecocite.getIdRegion()).getNom();
        List<AssoObjetObjectif> assoObjetObjectifs = assoObjetObjectifService.getListByEcocite(ecocite.getId());
        List<EtiquetteFinalite> etiquetteFinalites = etiquetteFinaliteService.getList(assoObjetObjectifs.stream().map(AssoObjetObjectif::getIdObjectif).collect(Collectors.toList()));
        String objectifs = etiquetteFinalites.stream().map(EtiquetteFinalite::getLibelle).collect(Collectors.joining(";"));
        List<Finalite> finalites = finaliteService.getList(etiquetteFinalites.stream().map(EtiquetteFinalite::getIdFinalite).collect(Collectors.toList()));
        List<Media> medias = this.repository.findAll(withEcocite(ecocite));
        for (Media media : medias) {
            Rdf response = ajarisClient.getFileByID(media.getIdAjaris());
            Document document = response.getDocument();
            document.fillWithEcocite(ecocite);
            document.setObjectif(objectifs);
            document.setFinalites(finalites.stream().map(Finalite::getLibelle).collect(Collectors.joining(";")));
            document.setLieu(region);
            if (!ajarisClient.update(media.getIdAjaris(), new X4dUpdate(document))) {
                return false;
            }
        }
        return true;
    }

    public Boolean updateAllImageFor(final EtiquetteAxe etiquetteAxe) {
        List<AssoActionDomain> assoActionDomains = assoActionDomainService.getListByAxe(etiquetteAxe.getIdAxe());
        List<Long> idActions = new ArrayList<>();
        for(AssoActionDomain asso : assoActionDomains) {
            idActions.add(asso.getIdAction());
        }
        List<Action> actions = actionService.findAll(idActions);
        Boolean res = true;
        for (Action action : actions) {
            res = res && updateAllImageFor(action);
        }
        return res;
    }

    public Boolean updateAllImageFor(final EtiquetteFinalite etiquetteFinalite) {
        List<AssoObjetObjectif> assoObjetObjectifs = assoObjetObjectifService.getListByFinalite(etiquetteFinalite.getIdFinalite());
        List<Long> idActions = new ArrayList<>();
        List<Long> idEcocites = new ArrayList<>();
        for(AssoObjetObjectif asso : assoObjetObjectifs) {
            if (Objects.equals(asso.getTypeObjet(), TYPE_OBJET.ACTION.getCode())) {
                idActions.add(asso.getIdObjet());
            } else if (Objects.equals(asso.getTypeObjet(), TYPE_OBJET.ECOCITE.getCode())) {
                idEcocites.add(asso.getIdObjet());
            }
        }
        List<Action> actions = actionService.findAll(idActions);
        List<Ecocite> ecocites = ecociteService.findAll(idEcocites);
        Boolean res = true;
        for (Action action : actions) {
            res = res && updateAllImageFor(action);
        }
        for (Ecocite ecocite : ecocites) {
            res = res && updateAllImageFor(ecocite);
        }
        return res;
    }

    public Boolean cloneAllImageFor(final Action clone, final Media original) {
        Media media = repository.findById(original.getId()).orElseThrow(() -> new EntityNotFoundException());;
        Rdf response = ajarisClient.getFileByID(media.getIdAjaris());
        Document document = response.getDocument();
        document.setNomAction(clone.getNomPublic());
        document.setNumeroAction(clone.getNumeroAction());
        try {
            URL url = new URL(response.getDocument().getMainUrl());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() >= 300 || conn.getResponseCode() < 200) {
                return false;
            }
            conn.connect();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy(conn.getInputStream(), baos);
            byte[] file = baos.toByteArray();
            media.setId(null);
            media.setIdObject(clone.getId());
            media.setTypeObject(TYPE_OBJET.ACTION.getCode());
            media.setIdAjaris(ajarisClient.upload(file, response.getDocument().getFileName(), new X4dUpdate(document)));
            if (media.getIdAjaris() != -1) {
                this.repository.save(media);
                return true;
            }
        } catch (IOException e) {
            LOGGER.info("IOException was thrown when creating stream");
            LOGGER.info("Exception: ", e);
        }
        return false;
    }

    public Boolean updateAllImageFor(final Action action) {
        EcociteBean ecocite = ecociteService.findOneEcocite(action.getIdEcocite());
        String region = regionService.findById(ecocite.getIdRegion()).getNom();
        List<AssoActionDomain> assoActionDomains = assoActionDomainService.getListByAction(action.getId());
        List<AssoObjetObjectif> assoObjetObjectifs = assoObjetObjectifService.getListByAction(action.getId());
        List<EtiquetteAxe> etiquetteAxes = etiquetteAxeService.getList(assoActionDomains.stream().map(AssoActionDomain::getIdDomain).collect(Collectors.toList()));
        List<EtiquetteFinalite> etiquetteFinalites = etiquetteFinaliteService.getList(assoObjetObjectifs.stream().map(AssoObjetObjectif::getIdObjectif).collect(Collectors.toList()));
        String domaines = etiquetteAxes.stream().map(EtiquetteAxe::getLibelle).collect(Collectors.joining(";"));
        String objectifs = etiquetteFinalites.stream().map(EtiquetteFinalite::getLibelle).collect(Collectors.joining(";"));
        Axe axe = action.getIdAxe() != null ? axeService.getOne(action.getIdAxe()) : null;
        String axeLibelle = axe != null ? axe.getLibelle() : "";
        List<Finalite> finalites = finaliteService.getList(etiquetteFinalites.stream().map(EtiquetteFinalite::getIdFinalite).collect(Collectors.toList()));

        List<Media> medias = this.repository.findAll(withAction(action));
        for (Media media : medias) {
            Rdf response = ajarisClient.getFileByID(media.getIdAjaris());
            Document document = response.getDocument();
            document.fillWithAction(action);
            document.setEcocite(ecocite.getNom());
            document.setAxe(axeLibelle);
            document.setDomaine(domaines);
            document.setFinalites(finalites.stream().map(Finalite::getLibelle).collect(Collectors.joining(";")));
            document.setObjectif(objectifs);
            document.setLieu(region);
            if (!ajarisClient.update(media.getIdAjaris(), new X4dUpdate(document))) {
                return false;
            }
        }
        return true;
    }

    public File getTmpFileForMedia(final Media media) {
        Rdf response = ajarisClient.getFileByID(media.getIdAjaris());
        String urlImgAjaris = response.getDocument().getMainUrl();
        try {
            URL url = new URL(response.getDocument().getMainUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() < 300 && connection.getResponseCode() >= 200) {
                File f = File.createTempFile(UUID.randomUUID().toString(), "");
                f.deleteOnExit();
                InputStream input = connection.getInputStream();
                OutputStream os = new FileOutputStream(f);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = input.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
                os.flush();
                return f;
            }
        } catch (MalformedURLException e) {
            String errorMsg = "Problem with the URL of the image on Ajaris : " + urlImgAjaris;
            LOGGER.info(errorMsg);
            LOGGER.info("Exception: ", e);
        } catch (FileNotFoundException e) {
            String errorMsg = "Could not write to tmp file when downloading : " + urlImgAjaris;
            LOGGER.info(errorMsg);
            LOGGER.info("Exception: ", e);
        }
        catch (IOException e) {
            String errorMsg = "IOException was thrown when downloading : " + urlImgAjaris;
            LOGGER.info(errorMsg);
            LOGGER.info("Exception: ", e);
        }
        return null;
    }

    public File getMainMediaForActionFile(final ActionBean action) {
        List<Media> list = getMainMediaForAction(action);
        if (!list.isEmpty()) {
            return getTmpFileForMedia(list.get(0));
        }
        return null;
    }

    public File getMainMediaForEcociteFile(final EcociteBean ecocite) {
        List<Media> list = getMainMediaForEcocite(ecocite);
        if (!list.isEmpty()) {
            return getTmpFileForMedia(list.get(0));
        }
        return null;
    }

    // Migration of old images to Ajaris plateform

    private Document fillCommon(final FileUploadBean form, String latitude, String longitude) {
        try {
            Double latitudeDouble = Double.parseDouble(latitude);
            Double longitudeDouble = Double.parseDouble(longitude);
            return new Document(form.getTitle(), form.getCopyright(), form.getLegende(), form.getDescription(), latitudeDouble, longitudeDouble);
        } catch (Exception e) {
            return new Document(form.getTitle(), form.getCopyright(), form.getLegende(), form.getDescription());
        }
    }

    public Media migrate(Document doc, FileUploadBean old, FILE_TYPE primaryType, FILE_TYPE secondaryType) {
        Path path = fileUploadService.getPath(old);
        try {
            Integer ajarisId = ajarisClient.upload(Files.readAllBytes(path), old.getOriginalName(), new X4dUpdate(doc));
            if (ajarisId == -1) {
                return null;
            } else {
                Media media = new Media();
                try {
                    media.setNumero(Math.toIntExact(old.getNumerisation()));
                } catch (ArithmeticException e) {
                    media.setNumero(Integer.MAX_VALUE);
                }
                if (primaryType.equals(old.getTypeEnum())) {
                    media.setLevel(1);
                } else if (secondaryType.equals(old.getTypeEnum())) {
                    media.setLevel(2);
                } else {
                    return null;
                }
                media.setTitle(old.getTitle());
                media.setIdAjaris(ajarisId);
                media.setTypeObject(old.getTypeEnum().getTypeObjet().getCode());
                media.setIdObject(old.getIdObject());
                media = this.repository.save(media);
                fileUploadService.deleteFileNoUser(old);
                return media;
            }
        } catch (IOException e) {
            return null;
        }
    }

    public Media migrateAction(FileUploadBean old) {
        ActionBean action = actionService.findOneAction(old.getIdObject());
        EcociteBean ecocite = ecociteService.findOneEcocite(action.getIdEcocite());
        String region = regionService.findById(ecocite.getIdRegion()).getNom();
        List<AssoObjetObjectif> assoObjetObjectifs = assoObjetObjectifService.getListByAction(action.getId());
        List<AssoActionDomain> assoActionDomains = assoActionDomainService.getListByAction(action.getId());
        List<EtiquetteAxe> etiquetteAxes = etiquetteAxeService.getList(assoActionDomains.stream().map(AssoActionDomain::getIdDomain).collect(Collectors.toList()));
        List<EtiquetteFinalite> etiquetteFinalites = etiquetteFinaliteService.getList(assoObjetObjectifs.stream().map(AssoObjetObjectif::getIdObjectif).collect(Collectors.toList()));
        List<Finalite> finalites = finaliteService.getList(etiquetteFinalites.stream().map(EtiquetteFinalite::getIdFinalite).collect(Collectors.toList()));
        String domaines = etiquetteAxes.stream().map(EtiquetteAxe::getLibelle).collect(Collectors.joining(";"));
        String objectifs = etiquetteFinalites.stream().map(EtiquetteFinalite::getLibelle).collect(Collectors.joining(";"));
        String axe = axeService.findById(action.getIdAxe()).getLibelle();
        String latitude = action.getLatitude();
        String longitude = action.getLongitude();
        Document doc = fillCommon(old, latitude, longitude);
        doc.fillWithAction(action.getTo());
        doc.setEcocite(ecocite.getNom());
        doc.setAxe(axe);
        doc.setObjectif(objectifs);
        doc.setDomaine(domaines);
        doc.setFinalites(finalites.stream().map(Finalite::getLibelle).collect(Collectors.joining(";")));
        doc.setLieu(region);
        return this.migrate(doc, old, FILE_TYPE.ACTION_IMAGE_PRINCIPALE, FILE_TYPE.ACTION_IMAGE_SECONDAIRE);
    }

    public Media migrateEcocite(FileUploadBean old) {
        EcociteBean ecocite = ecociteService.findOneEcocite(old.getIdObject());
        String latitude = ecocite.getLatitude();
        List<AssoObjetObjectif> assoObjetObjectifs = assoObjetObjectifService.getListByEcocite(ecocite.getId());
        String longitude = ecocite.getLongitude();
        List<EtiquetteFinalite> etiquetteFinalites = etiquetteFinaliteService.getList(assoObjetObjectifs.stream().map(AssoObjetObjectif::getIdObjectif).collect(Collectors.toList()));
        List<Finalite> finalites = finaliteService.getList(etiquetteFinalites.stream().map(EtiquetteFinalite::getIdFinalite).collect(Collectors.toList()));
        Document doc = fillCommon(old, latitude, longitude);
        String region = regionService.findById(ecocite.getIdRegion()).getNom();
        doc.fillWithEcocite(ecocite.getTo());
        String objectifs = etiquetteFinalites.stream().map(EtiquetteFinalite::getLibelle).collect(Collectors.joining(";"));
        doc.setLieu(region);
        doc.setObjectif(objectifs);
        doc.setFinalites(finalites.stream().map(Finalite::getLibelle).collect(Collectors.joining(";")));
        return this.migrate(doc, old, FILE_TYPE.ECOCITE_IMAGE_PRINCIPALE, FILE_TYPE.ECOCITE_IMAGE_SECONDAIRE);
    }

    public Media migrate(FileUploadBean old) {
        switch (old.getTypeEnum().getTypeObjet()) {
            case ACTION:
                return this.migrateAction(old);
            case ECOCITE:
                return this.migrateEcocite(old);
            default:
                return null;
        }
    }

    public Boolean migrate() {
        LOGGER.info("MIGRATION - Initialization");
        List<FileUploadBean> oldUploads = fileUploadService.findAllUploadToMigrate();
        if (!oldUploads.isEmpty()) {
            LOGGER.info("MIGRATION - Found {} old images", oldUploads.size());
            for (FileUploadBean oldUpload : oldUploads) {
                migrateOne(oldUpload);
            }
        } else {
            LOGGER.info("MIGRATION - Nothing to do");
        }
        LOGGER.info("MIGRATION - End");
        return true;
    }

    public Boolean migrateOne() {
        LOGGER.info("TEST MIGRATION - Initialization");
        List<FileUploadBean> oldUploads = fileUploadService.findAllUploadToMigrate();
        if (!oldUploads.isEmpty()) {
            LOGGER.info("TEST MIGRATION - Found {} old images", oldUploads.size());
            migrateOne(oldUploads.get(1));
        } else {
            LOGGER.info("TEST MIGRATION - Nothing to do");
        }
        LOGGER.info("TEST MIGRATION - End");
        return true;
    }

    private void migrateOne(FileUploadBean oldUpload) {
        LOGGER.info("MIGRATION - Migrating fileUpload {}", oldUpload.getId());
        if (oldUpload.getTypeEnum() != null) {
            try {
                Media media = this.migrate(oldUpload);
                if (media != null) {
                    LOGGER.info("MIGRATION - Done migrating {} new ID is {}", oldUpload.getId(), media.getIdAjaris());
                } else {
                    LOGGER.info("MIGRATION - Failed to migrate {}", oldUpload.getId());
                }
            } catch (Exception e) {
                LOGGER.info("MIGRATION - Failed to migrate {} with exception ", oldUpload.getId(), e);
            }
        } else {
            LOGGER.info("MIGRATION - Failed {}", oldUpload.getId());
        }
    }

    public List<FileUploadBean> imagesToMigrate() {
        return this.fileUploadService.findAllUploadToMigrate();
    }

    public Boolean isEnabled() {
        return ajarisClient.isEnabled();
    }
}

