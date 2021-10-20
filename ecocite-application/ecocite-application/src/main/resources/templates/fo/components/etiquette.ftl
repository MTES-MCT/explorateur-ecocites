<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0..-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.-->
<#--You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.-->

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
