<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<#-- @ftlvariable name="objectType" type="java.lang.String" -->
<#-- @ftlvariable name="canAddObject" type="java.lang.Boolean" -->
<#-- @ftlvariable name="usesModal" type="java.lang.Boolean" -->
<#-- @ftlvariable name="modalUrl" type="java.lang.String" -->
<#-- @ftlvariable name="canExportObject" type="java.lang.Boolean" -->
<#-- @ftlvariable name="hasUpload" type="java.lang.Boolean" -->
<#-- @ftlvariable name="uploadUrl" type="java.lang.String" -->
<#-- @ftlvariable name="title" type="java.lang.String" -->
<#-- @ftlvariable name="deleteUrl" type="java.lang.String" -->
<#-- @ftlvariable name="filtres" type="java.util.HashMap<java.lang.String, java.util.List<java.lang.Object>>" -->
<#-- @ftlvariable name="selected" type="java.util.Map<java.lang.String, java.lang.String>" -->

<div class="row">
    <#if hasUpload?? && hasUpload && uploadUrl?? && uploadUrl?hasContent>
        <div class="col-4 head my-4">
    <#elseIf !filtres?? && canExportObject?? && !canExportObject >
        <div class="col-10 head my-4">
    <#else >
        <div class="col-6 head my-4">
    </#if>
        <h2 class="ft-700">${title!}</h2>
    </div>
    <#assign mlAutoUsed = false>
    <#if hasUpload?? && hasUpload && uploadUrl?? && uploadUrl?hasContent>
        <#assign mlAutoUsed = true>
        <div class="col-2 my-4 ml-auto">
            <#--<button class="collapsed btn btn-outline-primary btn-wrap-text btn-block" onclick="showImportModal('${uploadUrl!}');">-->
                <#--<div class="row ">-->
                    <#--<div class="col-lg-10 d-none d-lg-block padding-none" >-->
                        <#--<strong>Importer (LAGON)</strong>-->
                    <#--</div>-->
                    <#--<div class="col-lg-2 col-v-center col-h-center padding-none">-->
                        <#--<i class="fa fa-upload mr-1"></i>-->
                    <#--</div>-->
                <#--</div>-->
            <#--</button>-->
            <button class="collapsed btn btn-outline-primary btn-block" onclick="showImportModal('${uploadUrl!}');">
                <strong>Importer (LAGON)</strong>
                <i class="fa fa-upload mr-1"></i>
            </button>
        </div>
    </#if>
    <#if canExportObject?? && canExportObject>
        <#if mlAutoUsed>
            <div class="col-2 my-4">
        <#else>
            <div class="col-2 my-4 ml-auto">
            <#assign mlAutoUsed = true>
        </#if>
    <#--onclick="exportToCSV('${objectType!}');"-->
            <button id="exportCSV_btn" class="collapsed btn btn-outline-primary btn-block" >
                <strong>Exporter</strong>
                <i class="fa fa-download mr-1"></i>
            </button>
            <a id="exportCSV_link" name="${objectType!}" href="#" class="hidden" download="download.zip"></a>
        </div>
    </#if>
    <#if filtres??>
        <#if mlAutoUsed>
            <div class="col-2 my-4">
        <#else>
            <div class="col-2 my-4 ml-auto">
            <#assign mlAutoUsed = true>
        </#if>
            <button class="collapsed btn btn-outline-primary btn-block" data-toggle="collapse"
                    data-target="#listFiltre">
                <strong>Filtrer</strong>
                <i class="fa fa-filter mr-1"></i>
            </button>
        </div>
    </#if>
    <#if canAddObject?? && canAddObject && modalUrl??>
        <#if mlAutoUsed>
            <div class="col-2 my-4">
        <#else>
            <div class="col-2 my-4 ml-auto">
            <#assign mlAutoUsed = true>
        </#if>
            <button class="collapsed btn btn-outline-success btn-block" onclick="openModalObject(-1, '${modalUrl!}')">
                <strong>Ajouter</strong>
                <i class="fa fa-plus mr-1"></i>
            </button>
        </div>
    </#if>
    <div class="col-lg-12 collapse" id="listFiltre" style="float: right;">
        <form id="listFiltreFrom" onkeydown="return event.key !== 'Enter';" onsubmit="return false;">
            <div class="row">
                <#if filtres??>
                    <#list filtres as key, value>
                        <div class="col-lg-3">
                            <div class="form-group">
                                <label for="${key}"><strong> ${value[0]}</strong></label><br/>
                                <#if value[1] == "text">
                                    <input id="${key}" onchange="soumissionForm('${urlFilter!}');" type="text"
                                           name="filtres['${key}']"
                                           value="" class="form-control"/>
                                <#else>
                                    <select id="${key}" onchange="soumissionForm('${urlFilter!}');"
                                            name="filtres['${key}']"
                                            class="form-control">
                                        <option value=""></option>
                                        <#if value[2]??>#
                                            <#list value[2] as keyy, valuee>
                                                <option value="${keyy}"
                                                <#if selectedFiltres??>
                                                    <#if selectedFiltres[key]?? && selectedFiltres[key] == keyy>
                                                        selected
                                                    </#if>
                                                 </#if>
                                                >${valuee}</option>
                                            </#list>
                                        </#if>
                                    </select>
                                </#if>
                            </div>
                        </div>
                    </#list>
                    <div class="col-lg-3">
                        <div class="form-group">
                            <label><strong>Réinitialisation des filtres</strong></label><br/>
                            <button class="form-control btn btn-outline-info" onclick="resetFilters('${urlFilter!}')">Réinitialiser les filtres</button>
                        </div>
                    </div>
                </#if>
            </div>
        </form>
    </div>
</div>
<div class="row">
    <div class="col-lg-12 mb-4" id="liste-pagination-div">
        <#include "liste-pagination-tableau.ftl"/>
    </div>
</div>
<div class="modal fade" id="modalAjoutIndicateur" role="dialog" aria-labelledby="modalAjoutIndicateurTitle"
     aria-hidden="true">
</div>
<div class="modal fade" id="confirmationSuppression" role="dialog" aria-hidden="true"
     style="z-index: 1052 !important;">
    <div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 40%;">
        <div class="modal-content">
            <div class="modal-header bg-grey-light">
                <h5 class="modal-title">Confirmer la suppression</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Êtes-vous sur ce vouloir supprimer cet élément?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Annuler</button>
                <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="deleteObjet('${deleteUrl!""}')">Supprimer</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="erreurSuppressionIndicateur" tabindex="-1" data-backdrop-limit="1" role="dialog"
     aria-hidden="true" style="z-index: 1052 !important;">
    <div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 40%;">
        <div class="modal-content">
            <div class="modal-header bg-grey-light">
                <h5 class="modal-title">Erreur</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="alert alert-danger">

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">Retour</button>
            </div>
        </div>
    </div>
</div>
<input id="locationreloadhelper" type="hidden" value="${urlFilter!}"/>
<#include "../modal/modal_import_document.ftl" />
