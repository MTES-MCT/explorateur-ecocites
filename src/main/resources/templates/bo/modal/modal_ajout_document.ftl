<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<#-- @ftlvariable name="typeModal" type="java.lang.Integer" -->
<#-- @ftlvariable name="idObject" type="java.lang.String" -->
<#-- @ftlvariable name="idSecondary" type="java.lang.Long" -->
<#-- @ftlvariable name="typeSecondary" type="java.lang.String" -->

<#assign ACTION_DOCUMENT = 0>
<#assign ACTION_IMAGE_PRINCIPALE = 1>
<#assign ACTION_IMAGE_SECONDAIRE = 2>
<#assign ACTION_PERIMETRE = 3>
<#assign ECOCITE_DOCUMENT = 4>
<#assign ECOCITE_IMAGE_PRINCIPALE = 5>
<#assign ECOCITE_IMAGE_SECONDAIRE = 6>
<#assign ECOCITE_PERIMETRE_STRATEGIQUE = 7>
<#assign ECOCITE_PERIMETRE_OPERATIONNEL = 8>
<#assign QUESTIONNAIRE_REPONSE_SYNTHESE = 9>

<#if fileUpload??>
    <#assign AFFICHAGE="EDITION">
    <#assign textBtnValidation="Enregistrer">
<#else>
    <#assign AFFICHAGE="CREATION">
    <#assign textBtnValidation="Confirmer l'ajout">
</#if>

<#if !typeModal??>
    <#assign typeModal = ACTION_IMAGE_PRINCIPALE>
</#if>
<#if idObject??>
    <#switch typeModal>
        <#case ACTION_DOCUMENT>
            <#assign titreModal = "Ajout d'un document" />
            <#if AFFICHAGE="EDITION" >
                <#assign url = "/bo/upload/documents/edition" />
            <#else >
                <#assign url = "/bo/upload/actions/${idObject}/documents" />
            </#if>
            <#assign acceptedFormat = ".pdf, .odt, .docx, .doc, .txt, .jpg, .jpeg, .png, .bmp" />
            <#break>
        <#case ACTION_IMAGE_PRINCIPALE>
            <#assign titreModal = "Ajout d'une image principale" />
            <#if AFFICHAGE="EDITION" >
                <#assign url = "/bo/upload/documents/edition" />
            <#else >
                <#assign url = "/bo/upload/actions/${idObject}/images" />
            </#if>
            <#assign acceptedFormat = ".jpg, .png, .jpeg" />
            <#break>
        <#case ACTION_IMAGE_SECONDAIRE>
            <#assign titreModal = "Ajout d'une image secondaire" />
            <#if AFFICHAGE="EDITION" >
                <#assign url =  "/bo/upload/documents/edition" />
            <#else >
                <#assign url = "/bo/upload/actions/${idObject}/images/secondaires" />
            </#if>
            <#assign acceptedFormat = ".jpg, .png, .jpeg" />
            <#break>
        <#case ACTION_PERIMETRE>
            <#assign titreModal = "Ajout d'un perimètre d'action" />
            <#if AFFICHAGE="EDITION" >
                <#assign url =  "/bo/upload/documents/edition"/>
            <#else >
                <#assign url = "/bo/upload/actions/${idObject}/perimetres" />
            </#if>
            <#assign acceptedFormat = ".kml" />
            <#break>
        <#case ECOCITE_DOCUMENT>
            <#assign titreModal = "Ajout d'un document" />
            <#if AFFICHAGE="EDITION" >
                <#assign url =  "/bo/upload/documents/edition" />
            <#else >
                <#assign url = "/bo/upload/ecocites/${idObject}/documents" />
            </#if>
            <#assign acceptedFormat = ".pdf, .odt, .docx, .doc, .txt, .jpg, .jpeg, .png, .bmp" />
            <#break>
        <#case ECOCITE_IMAGE_PRINCIPALE>
            <#assign titreModal = "Ajout d'une image principale" />
            <#if AFFICHAGE="EDITION" >
                <#assign url =  "/bo/upload/documents/edition"/>
            <#else >
                <#assign url = "/bo/upload/ecocites/${idObject}/images" />
            </#if>
            <#assign acceptedFormat = ".jpg, .png, .jpeg" />
            <#break>
        <#case ECOCITE_IMAGE_SECONDAIRE>
            <#assign titreModal = "Ajout d'une image secondaire" />
            <#if AFFICHAGE="EDITION" >
                <#assign url =  "/bo/upload/documents/edition" />
            <#else >
                <#assign url = "/bo/upload/ecocites/${idObject}/images/secondaires" />
            </#if>
            <#assign acceptedFormat = ".jpg, .png, .jpeg" />
            <#break>
        <#case ECOCITE_PERIMETRE_OPERATIONNEL>
            <#assign titreModal = "Ajout d'un perimètre d'action" />
            <#if AFFICHAGE="EDITION" >
                <#assign url =  "/bo/upload/documents/edition" />
            <#else >
                <#assign url = "/bo/upload/ecocites/${idObject}/perimetres/operationnel" />
            </#if>
            <#assign acceptedFormat = ".kml" />
            <#break>
        <#case ECOCITE_PERIMETRE_STRATEGIQUE>
            <#assign titreModal = "Ajout d'un perimètre opérationnel d'ÉcoCité" />
            <#if AFFICHAGE="EDITION" >
                <#assign url =  "/bo/upload/documents/edition" />
            <#else >
                <#assign url = "/bo/upload/ecocites/${idObject}/perimetres/strategique" />
            </#if>
            <#assign acceptedFormat = ".kml" />
            <#break>
        <#case QUESTIONNAIRE_REPONSE_SYNTHESE>
            <#assign titreModal = "Ajout d'une note de synthèse" />
            <#if idSecondary?? && typeSecondary??>
                <#if AFFICHAGE="EDITION" >
                    <#assign url =  "/bo/upload/documents/edition"/>
                <#else >
                    <#assign url = "/bo/upload/questionnaire/${idObject}/${idSecondary}/${typeSecondary}" />
                </#if>
            </#if>
            <#assign acceptedFormat = ".pdf, .odt, .docx, .doc, .txt, .jpg, .jpeg, .png, .bmp" />
            <#break>
    </#switch>
    <#if typeModal == ACTION_IMAGE_PRINCIPALE || typeModal == ACTION_IMAGE_SECONDAIRE || typeModal == ECOCITE_IMAGE_PRINCIPALE || typeModal == ECOCITE_IMAGE_SECONDAIRE>
        <#assign maxWidth = "70%"/>
    <#else >
        <#assign maxWidth = "50%" />
    </#if>
            <#if titreModal?? && url?? && acceptedFormat??>
        <#-- <div class="modal fade" id="modalAjoutDocument" tabindex="-1" role="dialog" aria-labelledby="modalAjoutDocumentTitle" aria-hidden="true"> -->
        <div id="modalAjoutDocumentContent" class="modal-dialog modal-dialog-centered" role="document" style="max-width: ${maxWidth};">
            <div class="modal-content">
                <div class="modal-header bg-grey-light">
                    <h5 class="modal-title" style="margin-left: 15px" id="modalAjoutDocumentTitle">${titreModal}</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="form-group col-12">
                            <label for="title"><strong>Titre du document *</strong></label> </br>
                            <input id='title' type="text" name="title" class="form-control" <#if AFFICHAGE=="EDITION" && fileUpload.title??>value="${fileUpload.title!}"</#if>/>
                            <p class="text-danger" style="display : none;"></p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-12">
                            <label for="fileInput"><strong>Fichier *</strong></label> </br>
                            <div class="input-group">
                                <div class="input-group-prepend">
                                    <div id="addbtn" <#if AFFICHAGE=="EDITION" >style="display: none" </#if> class="btn btn-outline-primary btn-file">
                                        Choisir un fichier
                                        <#if typeModal == ACTION_IMAGE_PRINCIPALE || typeModal == ACTION_IMAGE_SECONDAIRE || typeModal == ECOCITE_IMAGE_PRINCIPALE || typeModal == ECOCITE_IMAGE_SECONDAIRE>
                                            <input type="file" id="fileInput" onchange="fileInputChangeListener(this, '#fileName', '#preview')" accept="${acceptedFormat}">
                                        <#else>
                                            <input type="file" id="fileInput" onchange="fileInputChangeListener(this, '#fileName')" accept="${acceptedFormat}">
                                        </#if>
                                    </div>
                                    <button id="delbtn" class="btn btn-outline-danger btn-file" <#if AFFICHAGE=="CREATION" >style="display: none" </#if> onclick="deleteCurrentFile(this)">
                                        Supprimer le fichier
                                    </button>
                                </div>
                                <input id="fileName" type="text" class="form-control" placeholder="Pas de fichier selectionné" aria-label="" aria-describedby="basic-addon1" <#if AFFICHAGE=="EDITION" && fileUpload.originalName??>value="${fileUpload.originalName!}"</#if> disabled>
                                <input name="file_changed" id="file_changed" class="field-object" type="hidden" <#if AFFICHAGE="CREATION"> value="1" <#else> value="0" </#if>/>
                                <input name="idFile" id="idFile" class="field-object" type="hidden"  <#if fileUpload?? && AFFICHAGE=="EDITION">value="${fileUpload.id!?c}"</#if>/>
                            </div>
                            <p class="text-danger" style="display : none;"></p>
                        </div>
                    </div>
                    <#if typeModal == ACTION_IMAGE_PRINCIPALE || typeModal == ACTION_IMAGE_SECONDAIRE || typeModal == ECOCITE_IMAGE_PRINCIPALE || typeModal == ECOCITE_IMAGE_SECONDAIRE>
                        <div class="row">
                            <div class="form-group col-6">
                                <label for="legende"><strong>Légende *</strong></label> </br>
                                <input id='legende' type="text" name="legende" class="form-control" <#if AFFICHAGE=="EDITION" && fileUpload.legende??>value="${fileUpload.legende!}"</#if>/>
                                <p class="text-danger" style="display : none;"></p>
                            </div>
                            <div class="form-group col-6">
                                <label for="lieu"><strong>Lieu *</strong></label> </br>
                                <input id='lieu' type="text" name="lieu" class="form-control"  <#if AFFICHAGE=="EDITION" && fileUpload.lieu??>value="${fileUpload.lieu!}"</#if>/>
                                <p class="text-danger" style="display : none;"></p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-6">
                                <label for="description"><strong>Description *</strong></label> </br>
                                <input id='description' type="text" name="description" class="form-control"  <#if AFFICHAGE=="EDITION" && fileUpload.description??>value="${fileUpload.description!}"</#if>/>
                                <p class="text-danger" style="display : none;"></p>
                            </div>
                            <div class="form-group col-6">
                                <label for="copyright"><strong>Copyright *</strong></label> </br>
                                <input id='copyright' type="text" name="copyright" class="form-control"  <#if AFFICHAGE=="EDITION" && fileUpload.copyright??>value="${fileUpload.copyright!}"</#if>/>
                                <p class="text-danger" style="display : none;"></p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <img id="preview"  <#if AFFICHAGE=="EDITION" && fileUpload.url??>src="${fileUpload.url!}"</#if> role="presentation" class="rounded img-fluid">
                            </div>
                        </div>
                        <br/>
                        <div class="row">
                            <div class="form-group col-12">
                                <label for="uniteIndicateur"><strong>Autorisation de diffusion</strong></label> </br>
                                <p>Dans le cadre de la médiatisation de la démarche ÉcoCité, nous autorisons le ministère de la Transition écologique et solidaire (MTES)
                                    et le ministère de la Cohésion des territoires (MCT) ainsi que leurs partenaires institutionnels à utiliser et diffuser à titre gratuit
                                    et non exclusif les photographies enregistrées au sein de l'Explorateur ÉcoCités. Ces clichés seront exploités à titre exclusif par les
                                    ministères dans le cadre de la médiatisation de la démarche ÉcoCité. Les photographies susmentionnées sont susceptibles d’être reproduites
                                    sur les supports suivants :
                                </p>
                                <input id="presse" type="checkbox" <#if AFFICHAGE=="EDITION" && fileUpload.autorisationpresse?? && fileUpload.autorisationpresse>checked<#elseIf AFFICHAGE=="CREATION" >checked</#if>>
                                <label for="presse"> Publication de dossiers de presse</label><br/>
                                <input id="revue" type="checkbox" <#if AFFICHAGE=="EDITION" && fileUpload.autorisationrevue?? && fileUpload.autorisationrevue>checked</#if>
                                <label for="revue"> Publication dans une revue, ouvrage ou journal dont la publication est coordonnée par les MTES/MCT</label><br/>
                                <input id="expo" type="checkbox" <#if AFFICHAGE=="EDITION" && fileUpload.autorisationexpo?? && fileUpload.autorisationexpo>checked</#if>>
                                <label for="expo"> Présentation au public lors d’expositions dédiées à la ville durable organisées par les ministères et leurs partenaires institutionnels </label><br/>
                                <input id="internet" type="checkbox" <#if AFFICHAGE=="EDITION" && fileUpload.autorisationinternet?? && fileUpload.autorisationinternet>checked</#if>>
                                <label for ="internet">  Diffusion sur le site web, internet et extranet des MTES/MCT et ses partenaires institutionnels</label><br/>
                                <input id="siteEE" type="checkbox" <#if AFFICHAGE=="EDITION" && fileUpload.autorisationsiteee?? && fileUpload.autorisationsiteee>checked</#if>>
                                <label for="siteEE"> Diffusion sur le site web « Explorateur ÉcoCités »</label><br/>
                                <input id="supportMM" type="checkbox" <#if AFFICHAGE=="EDITION" && fileUpload.autorisationsupportmm?? && fileUpload.autorisationsupportmm>checked</#if>>
                                <label for ="supportMM"> Diffusion sur tout autre support multimédia</label><br/>
                                <p class="text-danger" style="display : none;"></p>
                            </div>
                            <div class="form-group col-3">
                                <label for="numerisation"><strong>Numérisation</strong></label> </br>
                                <input id='numerisation' type="text" name="numerisation" class="form-control"  <#if AFFICHAGE=="EDITION" && "${fileUpload.numerisation!}"!="-1">value="${fileUpload.numerisation!}"</#if>/>
                                <p class="text-danger" style="display : none;"></p>
                            </div>
                        </div>
                     </#if>
                </div>
                <div class="modal-footer">
                    <div class="row full-row" style="width: 100%!important;">
                        <div class="col-9 vertical-center-flex "> <p>Les champs mentionnés avec un astérisque (*) sont obligatoires.</p></div>
                        <div class="col-3 vertical-center-flex ">
                            <#switch typeModal>
                                <#case QUESTIONNAIRE_REPONSE_SYNTHESE>
                                    <button type="button" class="btn btn-block btn-wrap-text btn-success" onclick="uploadDocument('${url}', '#fileInput', '#title', hideModelThen(lambdaLoadEvaluationFacteur('${typeSecondary!}', '${idObject}')), '#modalAjoutDocumentContent')">${textBtnValidation}</button>
                                    <#break>
                                <#case ACTION_IMAGE_PRINCIPALE>
                                    <button type="button" class="btn btn-block btn-wrap-text btn-success" onclick="uploadImage('${url}', '#fileInput', '#title', hideModelThen(reloadPresentationFun('${idObject}')), '#modalAjoutDocumentContent')">${textBtnValidation}</button>
                                    <#break>
                                <#case ACTION_IMAGE_SECONDAIRE>
                                    <button type="button" class="btn btn-block btn-wrap-text btn-success" onclick="uploadImage('${url}', '#fileInput', '#title', hideModelThen(reloadPresentationFun('${idObject}')), '#modalAjoutDocumentContent')">${textBtnValidation}</button>
                                    <#break>
                                <#case ECOCITE_IMAGE_PRINCIPALE>
                                    <button type="button" class="btn btn-block btn-wrap-text btn-success" onclick="uploadImage('${url}', '#fileInput', '#title', hideModelThen(reloadPresentationFun('${idObject}')), '#modalAjoutDocumentContent')">${textBtnValidation}</button>
                                    <#break>
                                 <#case ECOCITE_IMAGE_SECONDAIRE>
                                    <button type="button" class="btn btn-block btn-wrap-text btn-success" onclick="uploadImage('${url}', '#fileInput', '#title', hideModelThen(reloadPresentationFun('${idObject}')), '#modalAjoutDocumentContent')">${textBtnValidation}</button>
                                    <#break>
                                <#default>
                                    <button type="button" class="btn btn-block btn-wrap-text btn-success" onclick="uploadDocument('${url}', '#fileInput', '#title', hideModelThen(reloadPresentationFun('${idObject}'), '#modalAjoutDocumentContent'))">${textBtnValidation}</button>
                                    <#break>
                            </#switch>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    <#else>
        <#assign message="Un des paramètres de la modale est vide (titre / url / formats acceptés)">
        <#assign idTitre="modalAjoutDocumentTitle">
        <#include "./error_modal.ftl" >
    </#if>
<#else>
    <#assign message="Aucun ID n'as été fourni à la modale">
    <#assign idTitre="modalAjoutDocumentTitle">
    <#include "./error_modal.ftl" >
</#if>
