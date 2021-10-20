<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0..-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.-->
<#--You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.-->

<#-- @ftlvariable name="etiquetteAsso" type="com.efficacity.explorateurecocites.beans.biz.AssoObjetObjectifBean" -->
<#-- @ftlvariable name="lectureSeule" type="java.lang.Boolean" -->
<#if etiquetteAsso?? && etiquetteAsso.etiquette??>
    <#if etiquetteAsso.idAsso??>
        <#assign idAsso = etiquetteAsso.idAsso!?c>
    <#else>
        <#assign idAsso = etiquetteAsso.idAsso!>
    </#if>
    <#assign categorie = etiquetteAsso.categorie!>
    <#assign etiquette = etiquetteAsso.etiquette!>
    <#assign idEtiquette = etiquette.id!?c>
    <#assign libelle = etiquette.libelle!>
    <#assign description = etiquette.description!>
    <#if etiquette.idFinalite??>
        <#assign classColor = "color-etiquette-${etiquette.idFinalite?c}">
    <#elseIf etiquette.idIngenierie??>
        <#assign classColor = "color-etiquette-${etiquette.idIngenierie?c}">
    <#elseIf etiquette.idAxe??>
        <#assign classColor = "color-etiquette-${etiquette.idAxe?c}">
    <#else>
        <#assign classColor = "">
    </#if>
    <#if idAsso?? && categorie?? && etiquette?? && idEtiquette?? && libelle?? && description?? && classColor??>
    <div class="etiquette-round vertical-center-etiquette ${classColor}" data-idEtiquette="${idEtiquette}" data-idAsso="${idAsso}" data-cat="${categorie}">
        <div class="row" style="width: 100%;">
            <div class="col-9 text-center">
                ${libelle}<br/>
            </div>
            <div class="col-2 vertical-center-etiquette">
                <i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="${description}"></i>
                <#if lectureSeule?? && !lectureSeule>
                    <#if action?? && action.id??>
                        <i class="fa fa-close delete-etiquette" onclick="deleteCurrentEtiquette(this, '${action.id?c}')" style="margin-left: 5px"></i>
                    <#elseIf ecocite?? && ecocite.id??>
                        <i class="fa fa-close delete-etiquette" onclick="deleteCurrentEtiquette(this, '${ecocite.id?c}')" style="margin-left: 5px"></i>
                    </#if>
                </#if>
            </div>
        </div>
    </div>
    </#if>
</#if>
