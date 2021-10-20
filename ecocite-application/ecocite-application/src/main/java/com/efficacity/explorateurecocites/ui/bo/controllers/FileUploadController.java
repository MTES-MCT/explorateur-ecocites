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

package com.efficacity.explorateurecocites.ui.bo.controllers;

import com.efficacity.explorateurecocites.beans.biz.FileUploadBean;
import com.efficacity.explorateurecocites.beans.service.FileUploadService;
import com.efficacity.explorateurecocites.beans.service.ReponsesQuestionnaireEvaluationService;
import com.efficacity.explorateurecocites.ui.bo.forms.FileUploadForm;
import com.efficacity.explorateurecocites.ui.bo.service.RightUserService;
import com.efficacity.explorateurecocites.utils.enumeration.FILE_TYPE;
import com.efficacity.explorateurecocites.utils.enumeration.QUESTIONNAIRE_CODE;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import isotope.modules.security.JwtUser;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

import static com.efficacity.explorateurecocites.ui.bo.service.RightUserService.checkAutority;

@Controller
@RequestMapping("/bo/upload")
public class FileUploadController {

    private FileUploadService fileUploadService;
    private ReponsesQuestionnaireEvaluationService reponsesQuestionnaireEvaluationService;

    public FileUploadController(final FileUploadService fileUploadService, final ReponsesQuestionnaireEvaluationService reponsesQuestionnaireEvaluationService) {
        this.fileUploadService = fileUploadService;
        this.reponsesQuestionnaireEvaluationService = reponsesQuestionnaireEvaluationService;
    }

    public ResponseEntity responseWithError(FileUploadBean bean, BindingResult bindingResult) {
        if (bean == null || bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(com.efficacity.explorateurecocites.utils.ValidationErrorBuilder.fromBindingErrorsIgnoreValue(bindingResult));
        } else {
            return ResponseEntity.ok(bean);
        }
    }

    @PostMapping(value = "/documents/edition", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadImageEdition(FileUploadForm form,Model model, BindingResult bindingResult, Locale locale) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        return responseWithError(fileUploadService.saveDocumentEdit(form,user), bindingResult);
    }

    @PostMapping(value = "/actions/{actionId}/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadDocumentAction(FileUploadForm form,Model model, BindingResult bindingResult, @PathVariable Long actionId, Locale locale) {
        checkAutority(model, TYPE_OBJET.ACTION, actionId);
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        return responseWithError(fileUploadService.uploadFile(form.getFile(),user, actionId, FILE_TYPE.ACTION_DOCUMENT, form.getTitle(), bindingResult, locale), bindingResult);
    }

    @PostMapping(value = "/actions/{actionId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadPrimaryImageAction(FileUploadForm form,Model model, BindingResult bindingResult, @PathVariable Long actionId, Locale locale) {
        checkAutority(model, TYPE_OBJET.ACTION, actionId);
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        return responseWithError(fileUploadService.uploadImage(form,user, actionId, FILE_TYPE.ACTION_IMAGE_PRINCIPALE, bindingResult, locale), bindingResult);
    }

    @PostMapping(value = "/actions/{actionId}/images/secondaires", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadSecondaryImageAction(FileUploadForm form,Model model, BindingResult bindingResult, @PathVariable Long actionId, Locale locale) {
        checkAutority(model, TYPE_OBJET.ACTION, actionId);
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        return responseWithError(fileUploadService.uploadImage(form,user, actionId, FILE_TYPE.ACTION_IMAGE_SECONDAIRE, bindingResult, locale), bindingResult);
    }

    @PostMapping(value = "/actions/{actionId}/perimetres", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadPerimetreAction(FileUploadForm form,Model model, BindingResult bindingResult, @PathVariable Long actionId, Locale locale) {
        checkAutority(model, TYPE_OBJET.ACTION, actionId);
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        return responseWithError(fileUploadService.uploadFile(form.getFile(),user, actionId, FILE_TYPE.ACTION_PERIMETRE, form.getTitle(), bindingResult, locale), bindingResult);
    }

    @PostMapping(value = "/ecocites/{actionId}/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadDocumentEcocite(FileUploadForm form,Model model, BindingResult bindingResult, @PathVariable Long actionId, Locale locale) {
        checkAutority(model, TYPE_OBJET.ECOCITE, actionId);
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        return responseWithError(fileUploadService.uploadFile(form.getFile(),user, actionId, FILE_TYPE.ECOCITE_DOCUMENT, form.getTitle(), bindingResult, locale), bindingResult);
    }

    @PostMapping(value = "/ecocites/{actionId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadPrimaryImageEcocite(FileUploadForm form,Model model, BindingResult bindingResult, @PathVariable Long actionId, Locale locale) {
        checkAutority(model, TYPE_OBJET.ECOCITE, actionId);
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        return responseWithError(fileUploadService.uploadImage(form,user,  actionId, FILE_TYPE.ECOCITE_IMAGE_PRINCIPALE, bindingResult, locale), bindingResult);
    }

    @PostMapping(value = "/ecocites/{actionId}/images/secondaires", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadSecondaryImageEcocite(FileUploadForm form,Model model, BindingResult bindingResult, @PathVariable Long actionId, Locale locale) {
        checkAutority(model, TYPE_OBJET.ECOCITE, actionId);
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        return responseWithError(fileUploadService.uploadImage(form,user, actionId, FILE_TYPE.ECOCITE_IMAGE_SECONDAIRE, bindingResult, locale), bindingResult);
    }

    @PostMapping(value = "/ecocites/{actionId}/perimetres/operationnel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadPerimetreEcociteOperationnel(FileUploadForm form,Model model, BindingResult bindingResult, @PathVariable Long actionId, Locale locale) {
        checkAutority(model, TYPE_OBJET.ECOCITE, actionId);
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        return responseWithError(fileUploadService.uploadFile(form.getFile(),user, actionId, FILE_TYPE.ECOCITE_PERIMETRE_OPERATIONNEL, form.getTitle(), bindingResult, locale), bindingResult);
    }

    @PostMapping(value = "/ecocites/{actionId}/perimetres/strategique", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadPerimetreEcociteStrategique(FileUploadForm form,Model model, BindingResult bindingResult, @PathVariable Long actionId, Locale locale) {
        checkAutority(model, TYPE_OBJET.ECOCITE, actionId);
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        return responseWithError(fileUploadService.uploadFile(form.getFile(),user, actionId, FILE_TYPE.ECOCITE_PERIMETRE_STRATEGIQUE, form.getTitle(), bindingResult, locale), bindingResult);
    }

    @PostMapping(value = "/questionnaire/{objectId}/{idSecondary}/{typeQuestionnaire}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadFileQuestionnaire(FileUploadForm form,Model model, BindingResult bindingResult, @PathVariable Long objectId, @PathVariable Long idSecondary, @PathVariable String typeQuestionnaire, Locale locale) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        QUESTIONNAIRE_CODE questionnaireCode = QUESTIONNAIRE_CODE.getByCode(typeQuestionnaire);
        FileUploadBean fileUploadBean = null;
        if (questionnaireCode != null) {
            switch (questionnaireCode) {
                case QUESTIONNAIRE_ACTION_INGENIERIE:
                case QUESTIONNAIRE_ACTION_INVESTISSEMENT:
                    checkAutority(model, TYPE_OBJET.ACTION, objectId);
                    fileUploadBean = fileUploadService.uploadFile(form.getFile(),user, objectId, FILE_TYPE.QUESTIONNAIRE_ACTION_SYNTHESE, form.getTitle(), bindingResult, locale);
                    if (fileUploadBean!=null && !reponsesQuestionnaireEvaluationService.saveFileToObject(idSecondary, objectId, TYPE_OBJET.ACTION, fileUploadBean.getId())) {
                        fileUploadService.deleteFile(fileUploadBean.getId(),user);
                        bindingResult.rejectValue("file", "error.attribut.capacity");
                    }
                    break;
                case QUESTIONNAIRE_ECOCITE:
                    checkAutority(model, TYPE_OBJET.ECOCITE, objectId);
                    fileUploadBean = fileUploadService.uploadFile(form.getFile(),user, objectId, FILE_TYPE.QUESTIONNAIRE_ECOCITE_SYNTHESE, form.getTitle(), bindingResult, locale);
                    if (fileUploadBean!=null && !reponsesQuestionnaireEvaluationService.saveFileToObject(idSecondary, objectId, TYPE_OBJET.ECOCITE, fileUploadBean.getId())) {
                        fileUploadService.deleteFile(fileUploadBean.getId(),user);
                        bindingResult.rejectValue("file", "error.attribut.capacity");
                    }
                    break;
            }
        } else {
            bindingResult.reject("error.attribut.unknown");
        }
        return responseWithError(fileUploadBean, bindingResult);
    }

    @GetMapping("/modal/{type}/{idObject}")
    public String getModalAddFile(@PathVariable Integer type, @PathVariable String idObject, Model model, @RequestParam(required = false) String typeSecondary, @RequestParam(required = false) Long secondaryId) {
        model.addAttribute("typeModal", type);
        model.addAttribute("idObject", idObject);
        model.addAttribute("idSecondary", secondaryId);
        model.addAttribute("typeSecondary", typeSecondary);
        return "/bo/modal/modal_ajout_document";
    }

    @GetMapping("/modal/{type}/{idObject}/{idFile}")
    public String getModalEditFile(@PathVariable Integer type, @PathVariable String idObject, @PathVariable Long idFile, Model model, @RequestParam(required = false) String typeSecondary, @RequestParam(required = false) Long secondaryId) {
        model.addAttribute("typeModal", type);
        model.addAttribute("idObject", idObject);
        model.addAttribute("idSecondary", secondaryId);
        model.addAttribute("typeSecondary", typeSecondary);
        if(idFile!=null){
            FileUploadBean fileUpload= fileUploadService.findFileById(idFile);
            if(fileUpload!=null){
                model.addAttribute("fileUpload", fileUpload);
            }
        }
        return "/bo/modal/modal_ajout_document";
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity deleteFile(Model model,@PathVariable Long fileId) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        FileUploadBean file = fileUploadService.findFileById(fileId);
        RightUserService.checkAutority(model, file.getTypeEnum().getTypeObjet(), file.getIdObject());
        fileUploadService.deleteFile(file, user);
        return ResponseEntity.ok("");
    }

    @DeleteMapping("/{fileId}/{idReponse}")
    public ResponseEntity deleteFile(Model model,@PathVariable Long fileId, @PathVariable Long idReponse) {
        JwtUser user = null;
        if (model.containsAttribute("user")) {
            user = ((JwtUser) model.asMap().get("user"));
        }
        FileUploadBean file = fileUploadService.findFileById(fileId);
        RightUserService.checkAutority(model, file.getTypeEnum().getTypeObjet(), file.getIdObject());
        reponsesQuestionnaireEvaluationService.deleteFile(fileId, idReponse);
        fileUploadService.deleteFileQuestionnaire(file, user);
        return ResponseEntity.ok("");
    }
}
