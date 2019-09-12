<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<#-- @ftlvariable name="ecocite" type="java.util.HashMap<java.lang.String, java.lang.String>" -->
<#-- @ftlvariable name="axis" type="java.util.HashMap<java.lang.String, java.lang.String>" -->
<#-- @ftlvariable name="finalite" type="java.util.HashMap<java.lang.String, java.lang.String>" -->
<#-- @ftlvariable name="etiquetteFinalite" type="java.util.HashMap<java.lang.String, java.lang.String>" -->
<#-- @ftlvariable name="progress" type="java.util.HashMap<java.lang.String, java.lang.String>" -->


<input type="hidden" id="axisModal" value="<#if axis??>true</#if>">
<input type="hidden" id="selectAxeModal" value="<#if selectedAxePrincipale??>true</#if>">
<div class="modal-dialog">
    <div class="modal-content m-t-0">
        <div class="modal-header">
            <div class="modal-header-inner row">
                <div class="col-md-10 col-xs-8">
                    <h4 class="modal-title">RECHERCHE <#if axis?? >D'ACTIONS</#if></h4>
                </div>
                <div class="col-md-2 col-xs-4 d-print-flex">
                    <button type="button" class="close" onclick="hideModal()"><span class="sr-only">Close</span></button>
                    <button id="back-history" class="back" onclick="window.history.back()"></button>
                </div>
            </div>
        </div>

    <#--<div class="modal-header">-->
    <#--<div class="modal-header-inner">-->

    <#--</div>-->
    <#--</div>-->
        <div class="modal-sub-header">
            <#if axis??>
                <div class="row">
                    <div class="col-md-3 m-b-5">
                        <select id="filter-ecocite" class="form-control filter--modal p-b-5">
                            <option class="option-0" value data-slug="all">ÉcoCité</option>
                            <#list ecocite?keys as key>
                                <option value="${key}" data-slug="${key}" <#if selectedEcocite?? && key == selectedEcocite> selected </#if> >${ecocite[key]}</option>
                            </#list>
                        </select>
                    </div>
                    <div class="col-md-3 m-b-5">
                        <select id="filter-axePrincipale" class="form-control filter--modal">
                            <option class="option-0" value data-slug="all">Axe d'intervention</option>
                            <#list axis?keys as key>
                                <option value="${key}" data-slug="${key}" <#if selectedAxePrincipale?? && key == selectedAxePrincipale> selected </#if> >${axis[key]}</option>
                            </#list>
                        </select>
                    </div>
                    <div class="col-md-3 m-b-5">
                        <select id="filter-finalite" class="form-control filter--modal">
                            <option class="option-0" value data-slug="all">Finalités</option>
                            <#list finalite?keys as key>
                                <option value="${key}" data-slug="${key}" <#if selectedFinalite?? && key == selectedFinalite> selected </#if> >${finalite[key]}</option>
                            </#list>
                        </select>
                    </div>
                    <div class="col-md-3 m-b-5">
                        <select id="filter-objectifVille" class="form-control filter--modal">
                            <option class="option-0" value data-slug="all">Objectifs</option>
                            <#list etiquetteFinalite?keys as key>
                                <option value="${key}" data-slug="${key}" <#if selectedObjectifVille?? && key == selectedObjectifVille> selected </#if> >${etiquetteFinalite[key]}</option>
                            </#list>
                        </select>
                    </div>
                    <div class="col-md-3 m-b-5">
                        <select id="filter-progressStatus" class="form-control filter--modal">
                            <option class="option-0" value data-slug="all">État d'avancement</option>
                            <#list progress?keys as key>
                                <option value="${key}" data-slug="${key}" <#if selectedEtatAvancement?? && key == selectedEtatAvancement> selected </#if> >${progress[key]}</option>
                            </#list>
                        </select>
                    </div>
                </div>
            <#else>
                <div id="search-form">
                    <div class="search-input-wrapper">
                        <input type="text" id="searched-string" name="searchedString" autocomplete="off" autofocus title="Search query">
                        <i class="glass"></i>
                    </div>
                </div>
            </#if>
        </div>

        <div class="modal-body" id="modal-body">
            <#if axis?? >
                <div id="recherche-results">
                    <#include './modalResultatRecherche.ftl'/>
                </div>
            <#else>
                <div id="search-result"><h3 class="search-process-info">Veuillez saisir votre recherche par mots clés dans la barre de recherche...</h3></div>
            </#if>
        </div>

        <div class="modal-footer">

        </div>
    </div>
</div>
