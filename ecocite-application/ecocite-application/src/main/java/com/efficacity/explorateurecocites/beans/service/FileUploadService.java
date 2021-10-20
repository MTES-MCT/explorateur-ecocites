
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
import com.efficacity.explorateurecocites.beans.biz.EcociteBean;
import com.efficacity.explorateurecocites.beans.biz.FileUploadBean;
import com.efficacity.explorateurecocites.beans.biz.ReponsesQuestionnaireEvaluationBean;
import com.efficacity.explorateurecocites.beans.model.Action;
import com.efficacity.explorateurecocites.beans.model.Axe;
import com.efficacity.explorateurecocites.beans.model.Ecocite;
import com.efficacity.explorateurecocites.beans.model.FileUpload;
import com.efficacity.explorateurecocites.beans.repository.FileUploadRepository;
import com.efficacity.explorateurecocites.configuration.ServiceConfiguration;
import com.efficacity.explorateurecocites.ui.bo.forms.FileUploadForm;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.AxeTableForm;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.ui.bo.utils.FileUtils;
import com.efficacity.explorateurecocites.ui.bo.utils.RG;
import com.efficacity.explorateurecocites.utils.ApplicationConstants;
import com.efficacity.explorateurecocites.utils.enumeration.FILE_TYPE;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import com.efficacity.explorateurecocites.utils.log.LoggingUtils;
import isotope.commons.services.CrudEntityService;
import isotope.modules.security.JwtUser;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.beans.specification.FileUploadSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 * <p>
 * Date de génération : 01/03/2018
 */
@Service
public class FileUploadService extends CrudEntityService<FileUploadRepository, FileUpload, Long> {

    private static final Tika tika = new Tika();

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadService.class);
    public static final String FILE_NOT_DELETED_INFO_LOG = "Un fichier n'as pas pu être supprimé: {}";

    @Autowired
    ReponsesQuestionnaireEvaluationService reponsesQuestionnaireEvaluationService;

    @Autowired
    private ServiceConfiguration serviceConfiguration;

    @Autowired
    private ActionService actionService;

    @Autowired
    private AxeService axeService;

    @Autowired
    private EcociteService ecociteService;

    @Autowired
    private MessageSourceService messageSourceService;

    public FileUploadService(FileUploadRepository repository) {
        super(repository);
    }

    private void rejectValue(String attr, String code, Errors bindingResult, Locale locale) {
        bindingResult.rejectValue(attr, code, messageSourceService.getMessageSource().getMessage(code, null, locale));
    }

    public FileUploadBean uploadFile(final MultipartFile file, final JwtUser user, final Long idObject, final FILE_TYPE fileType, String title, Errors bindingResult, Locale locale) {
        if (!validRequest(file, idObject, fileType, title, bindingResult, locale)) {
            return null;
        }
        String name = UUID.randomUUID().toString() + FileUtils.getExtension(file.getOriginalFilename());
        try {
            Path path = getPath(idObject, fileType, name);
            Files.createDirectories(path.getParent());
            File f = path.toFile();
            file.transferTo(f);
            Supplier<Action> findActionById = () -> actionService.findById(idObject);
            Supplier<Ecocite> findEcociteById = () -> ecociteService.findById(idObject);
            LoggingUtils.logFileEvent(LOGGER, LoggingUtils.ActionType.AJOUT, fileType, findActionById, findEcociteById, user);
            if (TYPE_OBJET.ACTION.equals(fileType.getTypeObjet())) {
                actionService.markActionModified(idObject, user);
            }
            return saveNewFile(name, idObject, fileType, file.getOriginalFilename(), title);
        } catch (IOException e) {
            bindingResult.rejectValue("file", ApplicationConstants.ERROR_TECHNICAL, messageSourceService.getMessageSource().getMessage(ApplicationConstants.ERROR_TECHNICAL, null, locale));
            LOGGER.error("Une exceptione est survenue à la sauvegarde du fichier: {}, d'ID: {}", file.getOriginalFilename(), name, e);
            return null;
        }
    }

    public FileUploadBean uploadImage(FileUploadForm form, JwtUser user, final Long idObject, final FILE_TYPE fileType, Errors bindingResult, Locale locale) {
        if (!validRequest(form.getFile(), idObject, fileType, form.getTitle(), bindingResult, locale)) {
            return null;
        }
        MultipartFile file = form.getFile();
        String name = UUID.randomUUID().toString() + FileUtils.getExtension(file.getOriginalFilename());
        try {
            Path path = getPath(idObject, fileType, name);
            Files.createDirectories(path.getParent());
            File f = path.toFile();
            file.transferTo(f);
            Supplier<Action> findActionById = () -> actionService.findById(idObject);
            Supplier<Ecocite> findEcociteById = () -> ecociteService.findById(idObject);
            LoggingUtils.logFileEvent(LOGGER, LoggingUtils.ActionType.AJOUT, fileType, findActionById, findEcociteById, user);
            return saveNewImage(name, idObject, fileType, file.getOriginalFilename(), form);
        } catch (IOException e) {
            bindingResult.rejectValue("file", ApplicationConstants.ERROR_TECHNICAL, messageSourceService.getMessageSource().getMessage(ApplicationConstants.ERROR_TECHNICAL, null, locale));
            LOGGER.error("Une exceptione est survenue à la sauvegarde du fichier: {}, d'ID: {}", file.getOriginalFilename(), name, e);
            return null;
        }
    }

    private FileUploadBean saveNewImage(final String name, final Long id, final FILE_TYPE fileType, String originalName, FileUploadForm form) {
        FileUpload fileUpload = new FileUpload();
        fileUpload.setName(name);
        fileUpload.setOriginalName(originalName);
        fileUpload.setIdObject(id);
        fileUpload.setTitle(form.getTitle());
        fileUpload.setLegende(form.getLegende());
        fileUpload.setLieu(form.getLieu());
        fileUpload.setDescription(form.getDescription());
        fileUpload.setCopyright(form.getCopyright());
        fileUpload.setAutorisationpresse(form.isAutorisationPresse());
        fileUpload.setAutorisationrevue(form.isAutorisationRevue());
        fileUpload.setAutorisationexpo(form.isAutorisationExpo());
        fileUpload.setAutorisationinternet(form.isAutorisationInternet());
        fileUpload.setAutorisationsiteee(form.isAutorisationSiteEE());
        fileUpload.setAutorisationsupportmm(form.isAutorisationSupportMM());
        fileUpload.setCopyright(form.getCopyright());
        fileUpload.setNumerisation(form.getNumerisation());
        fileUpload.setTypeObject(fileType.getTypeObjet().getCode());
        fileUpload.setType(fileType.getCode());
        fileUpload.setDateupload(LocalDateTime.now());
        return toBean(repository.save(fileUpload));
    }

    public FileUploadBean saveDocumentEdit(final FileUploadForm form, final JwtUser user) {
        FileUploadBean fileBean;
        fileBean = findFileById(form.getIdFile());
        if (form.isFileChanged()) {
            try {
                deleteFile(fileBean);
                MultipartFile file = form.getFile();
                String name = UUID.randomUUID().toString() + FileUtils.getExtension(file.getOriginalFilename());
                if (fileBean.getTo() != null) {
                    Path path = getPath(fileBean.getIdObject(), fileBean.getTypeEnum(), name);
                    Files.createDirectories(path.getParent());
                    File f = path.toFile();
                    file.transferTo(f);
                    fileBean.getTo().setName(name);
                    fileBean.getTo().setOriginalName(file.getOriginalFilename());
                }
            } catch (NumberFormatException e) {
                LOGGER.warn("Lors du changement d'icone d'un axe, l'ancien fichier ne correspondait pas à un ID");
            } catch (IOException e) {
                LOGGER.error("Une exceptione est survenue à la sauvegarde du fichier");
                return null;
            } catch (NullPointerException e) {
                LOGGER.error("nullPointerEcxcepion while updating document");
            }
        }

        setFileProperties(form, user, fileBean);

        return fileBean;
    }

    private void deleteFile(FileUploadBean fileBean) {
        Path oldPath = getPath(fileBean.getIdObject(), fileBean.getTypeEnum(), fileBean.getName());
        if (oldPath.toFile().exists()) {
            try {
                Files.delete(oldPath);
            } catch (IOException e) {
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info(FILE_NOT_DELETED_INFO_LOG, oldPath.toString());
                }
            }
        }
    }

    private void setFileProperties(FileUploadForm form, JwtUser user, FileUploadBean fileBean) {

        if (fileBean != null) {

            fileBean.getTo().setTitle(form.getTitle());
            if (fileBean.getTo().getType().equals(FILE_TYPE.ACTION_IMAGE_PRINCIPALE.getCode()) || fileBean.getTo().getType().equals(FILE_TYPE.ACTION_IMAGE_SECONDAIRE.getCode()) ||
                    fileBean.getTo().getType().equals(FILE_TYPE.ECOCITE_IMAGE_PRINCIPALE.getCode()) || fileBean.getTo().getType().equals(FILE_TYPE.ECOCITE_IMAGE_SECONDAIRE.getCode())) {
                fileBean.getTo().setLegende(form.getLegende());
                fileBean.getTo().setLieu(form.getLieu());
                fileBean.getTo().setDescription(form.getDescription());
                fileBean.getTo().setCopyright(form.getCopyright());
                fileBean.getTo().setAutorisationpresse(form.isAutorisationPresse());
                fileBean.getTo().setAutorisationrevue(form.isAutorisationRevue());
                fileBean.getTo().setAutorisationexpo(form.isAutorisationExpo());
                fileBean.getTo().setAutorisationinternet(form.isAutorisationInternet());
                fileBean.getTo().setAutorisationsiteee(form.isAutorisationSiteEE());
                fileBean.getTo().setAutorisationsupportmm(form.isAutorisationSupportMM());
                fileBean.getTo().setCopyright(form.getCopyright());
                fileBean.getTo().setNumerisation(form.getNumerisation());
            }
            toBean(repository.save(fileBean.getTo()));
            if (fileBean.getTo() != null) {
                Supplier<Action> findActionById = () -> actionService.findById(fileBean.getIdObject());
                Supplier<Ecocite> findEcociteById = () -> ecociteService.findById(fileBean.getIdObject());
                LoggingUtils.logFileEvent(LOGGER, LoggingUtils.ActionType.MODIFICATION, fileBean.getTypeEnum(), findActionById, findEcociteById, user);
            }
        }
    }

    private FileUploadBean saveNewFile(final String name, final Long id, final FILE_TYPE fileType, String originalName, String title) {
        FileUpload fileUpload = new FileUpload();
        fileUpload.setName(name);
        fileUpload.setOriginalName(originalName);
        fileUpload.setIdObject(id);
        fileUpload.setTitle(title);
        fileUpload.setTypeObject(fileType.getTypeObjet().getCode());
        fileUpload.setType(fileType.getCode());
        fileUpload.setDateupload(LocalDateTime.now());
        return toBean(repository.save(fileUpload));
    }

    private boolean validRequest(final MultipartFile file, final Long id, final FILE_TYPE fileType, final String title, final Errors bindingResult, final Locale locale) {
        if (title == null || title.trim().isEmpty()) {
            rejectValue("title", "error.attribut.notNull", bindingResult, locale);
            return false;
        }
        switch (fileType.getTypeObjet()) {
            case ACTION:
                return validAction(file, id, fileType, bindingResult, locale);
            case ECOCITE:
                return validEcocite(file, id, fileType, bindingResult, locale);
            case AXE:
                return validAxe(file, id, fileType, bindingResult, locale);
            default:
                return false;
        }
    }

    private boolean validateFileField(String attr, MultipartFile file, List<FileUploadBean> files, List<String> formatAutorise, List<String> mimeTypeAutorise, Integer maxFileOfType, Errors bindingResult, Locale locale) {
        if (!FileUtils.hasCorrectExtention(file.getOriginalFilename(), formatAutorise)) {
            rejectValue(attr, ApplicationConstants.ERROR_ATTRIBUT_FORMAT, bindingResult, locale);
            return false;
        }
        if (!FileUtils.hasCorrectMimeType(file, mimeTypeAutorise)) {
            rejectValue(attr, ApplicationConstants.ERROR_ATTRIBUT_FORMAT, bindingResult, locale);
            return false;
        }
        if (files.size() >= maxFileOfType) {
            rejectValue(attr, "error.attribut.capacity", bindingResult, locale);
            return false;
        }
        return true;
    }

    private boolean validAxe(final MultipartFile file, final Long axeId, final FILE_TYPE fileType, final Errors bindingResult, final Locale locale) {
        Axe axe = axeId != null ? axeService.getOne(axeId) : null;
        if (axe == null) {
            return false;
        }
        if (fileType == FILE_TYPE.AXE_ICON) {
            return validateFileField("icon_file", file, getFileEcociteOfType(axeId, FILE_TYPE.AXE_ICON),
                    RG.Fichier.FormatAutorise.IMAGE, RG.Fichier.FormatAutorise.IMAGE_MIME_TYPES, RG.Axe.Fichier.MAX_ICON, bindingResult, locale);
        } else {
            return false;
        }
    }

    private boolean validEcocite(final MultipartFile file, final Long ecociteId, final FILE_TYPE fileType, final Errors bindingResult, final Locale locale) {
        EcociteBean ecocite = ecociteService.findOneEcocite(ecociteId);
        if (ecocite == null) {
            return false;
        }
        switch (fileType) {
            case ECOCITE_DOCUMENT:
                return validateFileField("file", file, getFileEcociteOfType(ecociteId, FILE_TYPE.ECOCITE_DOCUMENT),
                        RG.Fichier.FormatAutorise.DOCUMENTS, RG.Fichier.FormatAutorise.DOCUMENTS_MIME_TYPES, RG.Ecocite.Fichier.MAX_DOCUMENTS, bindingResult, locale);
            case ECOCITE_IMAGE_PRINCIPALE:
                return validateFileField("file", file, getFileEcociteOfType(ecociteId, FILE_TYPE.ECOCITE_IMAGE_PRINCIPALE),
                        RG.Fichier.FormatAutorise.IMAGE, RG.Fichier.FormatAutorise.IMAGE_MIME_TYPES, RG.Ecocite.Fichier.MAX_IMAGE_PRINCIPALE, bindingResult, locale);
            case ECOCITE_IMAGE_SECONDAIRE:
                return validateFileField("file", file, getFileEcociteOfType(ecociteId, FILE_TYPE.ECOCITE_IMAGE_SECONDAIRE),
                        RG.Fichier.FormatAutorise.IMAGE, RG.Fichier.FormatAutorise.IMAGE_MIME_TYPES, RG.Ecocite.Fichier.MAX_IMAGES_SECONDAIRES, bindingResult, locale);
            case ECOCITE_PERIMETRE_STRATEGIQUE:
                return validateFileField("file", file, getFileEcociteOfType(ecociteId, FILE_TYPE.ECOCITE_PERIMETRE_STRATEGIQUE),
                        RG.Fichier.FormatAutorise.PERIMETRE, RG.Fichier.FormatAutorise.PERIMETRE_MIME_TYPES, RG.Ecocite.Fichier.MAX_PERIMETRES_STRATEGIQUES, bindingResult, locale);
            case ECOCITE_PERIMETRE_OPERATIONNEL:
                return validateFileField("file", file, getFileEcociteOfType(ecociteId, FILE_TYPE.ECOCITE_PERIMETRE_OPERATIONNEL),
                        RG.Fichier.FormatAutorise.PERIMETRE, RG.Fichier.FormatAutorise.PERIMETRE_MIME_TYPES, RG.Ecocite.Fichier.MAX_PERIMETRES_OPERATIONNELS, bindingResult, locale);
            case QUESTIONNAIRE_ECOCITE_SYNTHESE:
                return validateFileField("file", file, getFileActionOfType(ecociteId, FILE_TYPE.QUESTIONNAIRE_ECOCITE_SYNTHESE),
                        RG.Fichier.FormatAutorise.NOTE_SYNTHESE, RG.Fichier.FormatAutorise.NOTE_SYNTHESE_MIME_TYPES, RG.Ecocite.Fichier.MAX_SYNTHESE, bindingResult, locale);
            default:
                return false;
        }
    }

    private boolean validAction(final MultipartFile file, final Long actionId, final FILE_TYPE fileType, final Errors bindingResult, final Locale locale) {
        ActionBean action = actionService.findOneAction(actionId);
        if (action == null) {
            return false;
        }
        switch (fileType) {
            case ACTION_DOCUMENT:
                return validateFileField("file", file, getFileActionOfType(actionId, FILE_TYPE.ACTION_DOCUMENT),
                        RG.Fichier.FormatAutorise.DOCUMENTS, RG.Fichier.FormatAutorise.DOCUMENTS_MIME_TYPES, RG.Action.Fichier.MAX_DOCUMENTS, bindingResult, locale);
            case ACTION_IMAGE_PRINCIPALE:
                return validateFileField("file", file, getFileActionOfType(actionId, FILE_TYPE.ACTION_IMAGE_PRINCIPALE),
                        RG.Fichier.FormatAutorise.IMAGE, RG.Fichier.FormatAutorise.IMAGE_MIME_TYPES, RG.Action.Fichier.MAX_IMAGE_PRINCIPALE, bindingResult, locale);
            case ACTION_IMAGE_SECONDAIRE:
                return validateFileField("file", file, getFileActionOfType(actionId, FILE_TYPE.ACTION_IMAGE_SECONDAIRE),
                        RG.Fichier.FormatAutorise.IMAGE, RG.Fichier.FormatAutorise.IMAGE_MIME_TYPES, RG.Action.Fichier.MAX_IMAGES_SECONDAIRES, bindingResult, locale);
            case ACTION_PERIMETRE:
                return validateFileField("file", file, getFileActionOfType(actionId, FILE_TYPE.ACTION_PERIMETRE),
                        RG.Fichier.FormatAutorise.PERIMETRE, RG.Fichier.FormatAutorise.PERIMETRE_MIME_TYPES, RG.Action.Fichier.MAX_PERIMETRES, bindingResult, locale);
            case QUESTIONNAIRE_ACTION_SYNTHESE:
                return validateFileField("file", file, getFileActionOfType(actionId, FILE_TYPE.QUESTIONNAIRE_ACTION_SYNTHESE),
                        RG.Fichier.FormatAutorise.NOTE_SYNTHESE, RG.Fichier.FormatAutorise.NOTE_SYNTHESE_MIME_TYPES, RG.Action.Fichier.MAX_SYNTHESE, bindingResult, locale);
            default:
                return false;
        }
    }

    public Path getPath(FileUploadBean fileUpload) {
        return this.getPath(fileUpload.getIdObject(), fileUpload.getTypeEnum(), fileUpload.getName());
    }

    public Path getPath(Long id, FILE_TYPE fileType, String name) {
        return Paths.get(serviceConfiguration.getBasePath(), fileType.getFolderPath(), id.toString(), fileType.getSubFolderPath(), name);
    }

    public Path getPathDirectory(Long id, TYPE_OBJET typeObjet) {
        return Paths.get(serviceConfiguration.getBasePath(), typeObjet.getFolderPath(), String.valueOf(id));
    }

    public String getPathUrlFile(final Long Id, FILE_TYPE fileType) {
        FileUploadBean file = repository.findAll(
                where(hasIdObject(Id))
                        .and(belongToAction())
                        .and(hasType(fileType)), PageRequest.of(0, 1))
                .getContent()
                .stream()
                .findFirst()
                .map(this::toBean)
                .orElse(null);
        if (file != null) {
            return file.getUrl();
        }
        return null;
    }

    public List<FileUploadBean> getFileAction(final Long actionId) {
        return toBeanList(repository.findAll(where(hasIdObject(actionId)).and(belongToAction())));
    }

    public List<FileUploadBean> getFileActionOfType(final Long actionId, FILE_TYPE fileType) {
        return toBeanList(repository.findAll(where(hasIdObject(actionId)).and(belongToAction()).and(hasType(fileType))));
    }

    public List<FileUploadBean> getFileEcociteOfType(final Long ecociteId, FILE_TYPE fileType) {
        return toBeanList(repository.findAll(where(hasIdObject(ecociteId)).and(belongToEcocite()).and(hasType(fileType))));
    }

    public FileUploadBean getFirstFileActionOfType(final Long actionId, FILE_TYPE fileType) {
        return repository.findAll(
                where(hasIdObject(actionId))
                        .and(belongToAction())
                        .and(hasType(fileType)), PageRequest.of(0, 1))
                .getContent()
                .stream()
                .findFirst()
                .map(this::toBean)
                .orElse(null);
    }

    public FileUploadBean getFirstFileEcociteOfType(final Long ecociteId, FILE_TYPE fileType) {
        return repository.findAll(
                where(hasIdObject(ecociteId))
                        .and(belongToEcocite())
                        .and(hasType(fileType)), PageRequest.of(0, 1))
                .getContent()
                .stream()
                .findFirst()
                .map(this::toBean)
                .orElse(null);
    }

    public File getFirstFileEcociteOfTypeAsFile(final Long ecociteId, FILE_TYPE fileType) {
        return asFile(getFirstFileEcociteOfType(ecociteId, fileType));
    }

    public File getFirstFileActionOfTypeAsFile(final Long actionId, FILE_TYPE fileType) {
        return asFile(getFirstFileActionOfType(actionId, fileType));
    }

    public File asFile(FileUploadBean fileUploadBean) {
        if (fileUploadBean != null) {
            Path path = getPath(fileUploadBean.getIdObject(), fileUploadBean.getTypeEnum(), fileUploadBean.getName());
            File file = path.toFile();
            if (file.exists()) {
                return file;
            }
        }
        return null;
    }

    public List<FileUploadBean> getFileEcocite(final Long ecociteId) {
        return toBeanList(repository.findAll(where(hasIdObject(ecociteId)).and(belongToEcocite())));
    }

    public List<FileUploadBean> getFileAxe(final Long axeId) {
        return toBeanList(repository.findAll(where(hasIdObject(axeId)).and(belongToAxe())));
    }

    public FileUploadBean getFirstFileAxeOfType(final Long axeId, FILE_TYPE fileType) {
        return repository.findAll(
                where(hasIdObject(axeId))
                        .and(belongToAxe())
                        .and(hasType(fileType)), PageRequest.of(0, 1))
                .getContent()
                .stream()
                .findFirst()
                .map(this::toBean)
                .orElse(null);
    }

    private FileUploadBean toBean(FileUpload fileUpload) {
        return new FileUploadBean(fileUpload);
    }

    private List<FileUploadBean> toBeanList(List<FileUpload> fileUploads) {
        return fileUploads.stream().map(this::toBean).collect(Collectors.toList());
    }

    public FileUploadBean findFileById(final Long fileId) {
        return repository.findAll(where(hasId(fileId)), PageRequest.of(0, 1))
                .getContent()
                .stream()
                .findFirst()
                .map(this::toBean)
                .orElse(null);
    }

    public FileUploadBean findFileByName(final String fileName) {
        return repository.findAll(where(hasName(fileName)), PageRequest.of(0, 1))
                .getContent()
                .stream()
                .findFirst()
                .map(this::toBean)
                .orElse(null);
    }

    public void deleteFile(final Long fileId, JwtUser user) {
        FileUploadBean fileUploadBean = findFileById(fileId);
        deleteFile(fileUploadBean, user);
    }

    public void deleteFile(final FileUploadBean fileUploadBean, JwtUser user) {
        if (fileUploadBean != null) {
            if (TYPE_OBJET.ACTION.equals(fileUploadBean.getTypeEnum().getTypeObjet())) {
                actionService.markActionModified(fileUploadBean.getIdObject(), user);
            }
            deleteFile(fileUploadBean);

            if (fileUploadBean.getTo() != null) {
                if (Objects.equals(fileUploadBean.getTo().getTypeObject(), TYPE_OBJET.AXE.getCode())) {
                    Supplier<Axe> findAxeById = () -> axeService.findById(fileUploadBean.getIdObject());
                    LoggingUtils.logFileEvent(LOGGER, LoggingUtils.ActionType.SUPPRESSION, fileUploadBean.getTypeEnum(),
                            findAxeById, user);
                } else {
                    Supplier<Action> findActionById = () -> actionService.findById(fileUploadBean.getIdObject());
                    Supplier<Ecocite> findEcociteById = () -> ecociteService.findById(fileUploadBean.getIdObject());
                    LoggingUtils.logFileEvent(LOGGER, LoggingUtils.ActionType.SUPPRESSION, fileUploadBean.getTypeEnum(),
                            findActionById, findEcociteById, user);
                }
            }


            repository.deleteById(fileUploadBean.getId());
        }
    }

    public List<ReponsesQuestionnaireEvaluationBean> associateFileToResponse(final List<ReponsesQuestionnaireEvaluationBean> reponseQuestions) {
        for (final ReponsesQuestionnaireEvaluationBean reponseQuestion : reponseQuestions) {
            if (reponseQuestion.getReponsePrincipale() != null && !reponseQuestion.getReponsePrincipale().isEmpty()) {
                List<Long> ids = Arrays.stream(reponseQuestion.getReponsePrincipale().split(";")).map(r -> {
                    try {
                        return Long.parseLong(r);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }).filter(Objects::nonNull)
                        .collect(Collectors.toList());
                reponseQuestion.setFiles(repository.findAllById(ids).stream().map(FileUploadBean::new).collect(Collectors.toList()));
            }
        }
        return reponseQuestions;
    }

    public void deleteFileQuestionnaire(final FileUploadBean file, JwtUser user) {
        deleteFile(file, user);
    }

    public FileUploadBean saveNewAxeIcon(final AxeTableForm axeTableForm, JwtUser user, final Long id, final Errors errors, final Locale locale) {
        FileUploadBean f = null;
        if (Boolean.TRUE.equals(axeTableForm.hasIconChanged())) {
            try {
                f = getFirstFileAxeOfType(id, FILE_TYPE.AXE_ICON);
                if (f != null) {
                    deleteFile(f.getId(), user);
                }
            } catch (NumberFormatException e) {
                LOGGER.warn("Lors du changement d'icone d'un axe, l'ancien fichier ne correspondait pas à un ID");
            } finally {
                f = uploadFile(axeTableForm.getIcon_file(), user, id, FILE_TYPE.AXE_ICON, axeTableForm.getIcon_file().getOriginalFilename(), errors, locale);
            }
        }
        return f;
    }

    public void deleteByEcocite(final Long id) {
        repository.deleteAll(repository.findAll(where(hasIdObject(id)).and(belongToEcocite())));
        deleteDirectory(TYPE_OBJET.ECOCITE, id);
    }

    public void deleteByAction(final Long id) {
        repository.deleteAll(repository.findAll(where(hasIdObject(id)).and(belongToAction())));
        deleteDirectory(TYPE_OBJET.ACTION, id);
    }

    private void deleteDirectory(final TYPE_OBJET typeObjet, final Long id) {
        Path path = getPathDirectory(id, typeObjet);
        FileUtils.deleteDirectory(path.toFile());
    }

    public List<FileUploadBean> getFileVisibleForEcocite(Long ecociteId) {
        return toBeanList(repository.findAll(where(hasIdObject(ecociteId)).and(belongToEcocite()).and(isVisible())));
    }

    public List<FileUploadBean> getFileVisibleForAction(Long actionId) {
        return toBeanList(repository.findAll(where(hasIdObject(actionId)).and(belongToAction()).and(isVisible())));
    }

    public List<FileUploadBean> findAllUploadToMigrate() {
        List<FILE_TYPE> fileTypes = new ArrayList<>();
        fileTypes.add(FILE_TYPE.ACTION_IMAGE_PRINCIPALE);
        fileTypes.add(FILE_TYPE.ECOCITE_IMAGE_PRINCIPALE);
        fileTypes.add(FILE_TYPE.ACTION_IMAGE_SECONDAIRE);
        fileTypes.add(FILE_TYPE.ECOCITE_IMAGE_SECONDAIRE);
        return toBeanList(this.repository.findAll(where(hasTypeIn(fileTypes))));
    }

    public Boolean deleteFileNoUser(final FileUploadBean old) {
        if (old != null) {
            deleteFile(old);
            repository.deleteById(old.getId());
            return true;
        }
        return false;
    }

    public List<FileUpload> cloneForAction(final Long idClone, final Long idOriginal) {
        Map<Long, FileUpload> cloneDocumentReferenceMap = new HashMap<>();
        Map<Long, FileUpload> originalDocumentReferenceMap = new HashMap<>();
        List<FileUpload> clones = repository.findAll(where(hasIdObject(idOriginal)).and(belongToAction()))
                .stream()
                .map(original -> {
                    FileUpload clone = new FileUpload();
                    clone.setAutorisationexpo(original.getAutorisationexpo());
                    clone.setAutorisationinternet(original.getAutorisationinternet());
                    clone.setAutorisationpresse(original.getAutorisationpresse());
                    clone.setAutorisationrevue(original.getAutorisationrevue());
                    clone.setAutorisationsiteee(original.getAutorisationsiteee());
                    clone.setAutorisationsupportmm(original.getAutorisationsupportmm());
                    clone.setCopyright(original.getCopyright());
                    clone.setDateupload(original.getDateupload());
                    clone.setDescription(original.getDescription());
                    clone.setLegende(original.getLegende());
                    clone.setLieu(original.getLieu());
                    clone.setNumerisation(original.getNumerisation());
                    clone.setOriginalName(original.getOriginalName());
                    clone.setTitle(original.getTitle());
                    clone.setType(original.getType());
                    clone.setTypeObject(original.getTypeObject());
                    clone.setIdObject(idClone);
                    clone.setName(UUID.randomUUID().toString() + FileUtils.getExtension(original.getOriginalName()));
                    cloneDocumentReferenceMap.put(original.getId(), clone);
                    originalDocumentReferenceMap.put(original.getId(), original);
                    return clone;
                })
                .collect(Collectors.toList());
        clones = repository.saveAll(clones);
        originalDocumentReferenceMap.forEach((key, original) -> {
            FILE_TYPE fileType = FILE_TYPE.getByCode(original.getType());
            if (cloneDocumentReferenceMap.containsKey(key) && fileType != null) {
                FileUpload clone = cloneDocumentReferenceMap.get(key);
                try {
                    Path originalPath = getPath(idOriginal, fileType, original.getName());
                    Path clonePath = getPath(idClone, fileType, clone.getName());
                    Files.createDirectories(clonePath.getParent());
                    Files.copy(originalPath, clonePath);
                } catch (IOException e) {
                    // Do something
                }
            }
        });
        reponsesQuestionnaireEvaluationService.fixCloneLink(idClone, cloneDocumentReferenceMap);
        return clones;
    }
}
