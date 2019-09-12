<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<#-- @ftlvariable name="etiquette" type="com.efficacity.explorateurecocites.beans.biz.EtiquetteCommonBean" -->

<#if etiquette??>
    <#assign idEtiquette = etiquette.id!?c>
    <#assign libelle = etiquette.text!>
    <#assign description = etiquette.description!>
    <#assign color = etiquette.color!>
    <#if etiquette?? && idEtiquette?? && libelle?? && description?? && color??>
    <div class="etiquette-round vertical-center-etiquette" style="color: ${color}; border-color: ${color}">
        <div class="row" style="width: 100%; display: flex; vertical-align: middle">
            <div class="col-xs-10 text-center">
                ${libelle}<br/>
            </div>
            <div class="col-xs-2 vertical-center-etiquette">
                <span class="glyphicon glyphicon-info-sign" data-toggle="tooltip" data-placement="bottom" data-container="body" title="${description}"></span>
            </div>
        </div>
    </div>
    </#if>
</#if>
