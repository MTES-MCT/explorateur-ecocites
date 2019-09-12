<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<#-- @ftlvariable name="lectureSeule" type="java.lang.Boolean" -->
<#-- @ftlvariable name="typeModal" type="java.lang.Integer" -->
<#-- @ftlvariable name="titreModal" type="java.lang.String" -->

<#-- @ftlvariable name="data" type="com.efficacity.explorateurecocites.ui.bo.forms.tables.helpers.ObjectAsMap" -->

<#-- @ftlvariable name="redirectAfterCreation" type="java.lang.Boolean" -->
<#-- @ftlvariable name="redirectURL" type="java.lang.String" -->

<#-- @ftlvariable name="postAsForm" type="java.lang.Boolean" -->
<#-- @ftlvariable name="updateUrl" type="java.lang.String" -->
<#-- @ftlvariable name="deleteUrl" type="java.lang.String" -->
<#-- @ftlvariable name="createUrl" type="java.lang.String" -->

<#if data?? && data.fields??>
    <#assign AFFICHAGE = 0 />
    <#assign CREATION = 1 />
    <#assign EDITION = 2 />
    <#if !typeModal??>
        <#assign typeModal = CREATION>
    </#if>
    <#switch typeModal>
        <#case CREATION>
            <#assign lectureSeule = false />
            <#break>

        <#case EDITION>
            <#assign lectureSeule = false />
            <#break>
        <#case AFFICHAGE>
        <#default>
            <#assign lectureSeule = true />
            <#break>
    </#switch>
    <#-- Cette modal doit être placé dans une div telle que la suivante -->
    <#-- <div class="modal fade" id="modalAjoutIndicateur" tabindex="-1" role="dialog" aria-labelledby="modalAjoutIndicateurTitle" aria-hidden="true"> -->
    <div class="modal-dialog" role="document" style="max-width: 60%;">
        <div class="modal-content">
            <div class="modal-header bg-grey-light">
                <h5 class="modal-title" style="margin-left: 15px" id="modalAjoutIndicateurTitle">${titreModal}</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <#if data?? && data.fields??>
                            <#list data.fields as field>
                                <#switch field.type.code>
                                    <#case "select" >
                                        <div class="form-group col-6">
                                            <label for="title"><strong>${field.label!""}<#if field.required?? && field.required>${" *"}</#if></strong></label>
                                            <br/>
                                            <select <#if field.required?? && field.required>required</#if> <#if lectureSeule>disabled</#if> id='title' type="text" name="${field.name}" class="form-control field-object">
                                                <option value=""></option>
                                                <#if field.values??>
                                                    <#list field.values as opt>
                                                        <option value="${opt.value}" <#if field.value == opt.value>selected</#if>>${opt.nom!""}</option>
                                                    </#list>
                                                </#if>
                                            </select>
                                            <p class="text-danger" style="display : none;"></p>
                                        </div>
                                        <#break />
                                    <#case "file" >
                                            <div class="form-group col-6" id="${field.name}_container">
                                                <label for="fileInput"><strong>Fichier<#if field.required?? && field.required>${" *"}</#if></strong></label><br/>
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <#assign modeAjout = !(field.value??) || !(field.value?hasContent)>
                                                        <div <#if lectureSeule>disabled</#if> id="${field.name}_addbtn" class="btn btn-outline-primary btn-file" <#if !modeAjout>style="display: none" </#if>>
                                                            Choisir un fichier
                                                            <input <#if lectureSeule>disabled</#if> class="field-file" id="${field.name}_file" name="${field.name}_file" type="file" onchange="fileInputChangeListenerModalAdmin(this, '#${field.name}')" accept="image/x-png,image/jpeg">
                                                        </div>
                                                        <button <#if lectureSeule>disabled</#if> id="${field.name}_delbtn" class="btn btn-outline-danger btn-file" <#if modeAjout>style="display: none" </#if> onclick="deleteCurrentFileModalAdmin(this, '#${field.name}')">
                                                            Supprimer le fichier
                                                        </button>
                                                    </div>

                                                    <input id="${field.name}_preview" name="${field.name}_preview" type="text" class="form-control" placeholder="Pas de fichier selectionné" aria-label="" aria-describedby="basic-addon1" disabled <#if !modeAjout>style="display: none" </#if>>
                                                    <div id="${field.name}_lienFileModalObjet" class="lienFileModalObjet form-control" <#if modeAjout>style="display: none" </#if>>
                                                        <a href="${field.value}" target="_blank">${field.value}</a>
                                                    </div>
                                                </div>
                                                <p class="text-danger" style="display : none;"></p>
                                                <input <#if lectureSeule>disabled</#if> name="${field.name}_changed" id="${field.name}_changed" class="field-object" type="hidden" <#if modeAjout> value="1" <#else> value="0" </#if>"/>
                                            </div>
                                        <#break />
                                    <#case "date" >
                                        <div class="form-group col-6">
                                            <label for="title"><strong>${field.label!""}<#if field.required?? && field.required>${" *"}</#if></strong></label><br/>
                                            <input <#if field.required?? && field.required>required</#if> <#if lectureSeule>disabled</#if> placeholder="dd/mm/aaaa" type="text" name="${field.name}" value="${field.value!}" class="form-control datepicker field-object" />
                                            <p class="text-danger" style="display : none;"></p>
                                        </div>
                                        <#break />
                                    <#case "hidden" >
                                        <input <#if lectureSeule>disabled</#if> id='title' type="hidden" name="${field.name}" class="form-control field-object" value="${field.value!""}"/>
                                        <p class="text-danger" style="display : none;"></p>
                                        <#break />
                                    <#case "basic" >
                                    <#default>
                                        <div class="form-group col-6">
                                            <label for="title"><strong>${field.label!""}</strong></label><#if field.required?? && field.required>${" *"}</#if><br/>
                                            <input <#if field.required?? && field.required>required</#if> <#if lectureSeule>disabled</#if> id='title' type="text" name="${field.name}" class="form-control field-object" value="${field.value!""}"/>
                                            <p class="text-danger" style="display : none;"></p>
                                        </div>
                                        <#break />
                                </#switch>
                            </#list>
                        </#if>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <div class="row" style="width: 100%!important;">
                    <div class="col-6">
                        <p>Les champs mentionnés avec un astérisque (*) sont obligatoires</p>
                    </div>
                    <#if typeModal == AFFICHAGE >
                        <div class="col-3"></div>
                    </#if>
                    <div class="col-3">
                        <#if typeModal == EDITION >
                            <button type="button" class="btn btn-block btn-danger" data-toggle="modal" data-target="#confirmationSuppression">Supprimer</button>
                        <#else>
                            <button type="button" class="btn btn-block btn-outline-secondary" data-dismiss="modal">Annuler</button>
                        </#if>
                    </div>
                    <div class="col-3">
                        <#if typeModal == EDITION >
                            <#if postAsForm?? && postAsForm>
                                <button type="button" class="btn btn-block btn-success" onclick="updateObjetForm('${updateUrl!""}')">Sauvegarder</button>
                            <#else>
                                <button type="button" class="btn btn-block btn-success" onclick="updateObjetJson('${updateUrl!""}')">Sauvegarder</button>
                            </#if>
                        <#elseIf typeModal == CREATION >
                            <#assign redirectUrlJsVar = "">
                            <#if redirectAfterCreation?? && redirectAfterCreation && redirectURL??>
                                <#assign redirectUrlJsVar = redirectURL!"">
                            </#if>
                            <#if postAsForm?? && postAsForm>
                                <button type="button" class="btn btn-block btn-success" onclick="createObjetForm('${createUrl!""}', '${redirectUrlJsVar}')">Enregistrer</button>
                            <#else>
                                <button type="button" class="btn btn-block btn-success" onclick="createObjetJson('${createUrl!""}', '${redirectUrlJsVar}')">Enregistrer</button>
                            </#if>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
<#else>
    <#assign message="Aucune donnée n'a été fourni à la modal">
    <#assign idTitre="Erreur">
    <#include "../modal/error_modal.ftl" >
</#if>
