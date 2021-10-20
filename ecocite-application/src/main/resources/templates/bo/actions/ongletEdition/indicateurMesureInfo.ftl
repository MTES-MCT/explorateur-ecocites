<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0..-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.-->
<#--You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.-->

<#-- @ftlvariable name="isAdmin" type="java.lang.Boolean" -->
<#-- @ftlvariable name="displayWithMap" type="java.lang.Boolean" -->
<#-- @ftlvariable name="selectMap" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="canValide" type="java.lang.Boolean" -->
<#-- @ftlvariable name="class" type="java.lang.String" -->
<#-- @ftlvariable name="type" type="java.lang.String" -->

<#if object?? && object.dateSaisie?? && object.id??>
    <tr id="row-${type}-${object.id?c}">
        <td class="center-table-cell ft-s-11">${object.dateSaisie?datetime("yyyy-MM-dd")?string["MM/yyyy"]}</td>
        <#if displayWithMap?? && displayWithMap && selectMap?? && object.valeur??>
            <td class="center-table-cell ft-s-11">${selectMap[object.valeur]!""}</td>
        <#else>
            <td class="center-table-cell ft-s-11">${object.valeur!""}</td>
        </#if>
        <td class="center-table-cell ft-s-11">${assoIndicateurAction.unite}</td>
        <#--TODO save auto -->
        <td class="center-table-cell"><input class="m-auto inputSaveAuto" type="checkbox" <#if object.valide>checked</#if> <#if !(canValide??) || !canValide>disabled</#if> data-objectid="${object.id?c}" data-objectclass="${class!""}" name="valide"></td>
        <#if canValide?? && canValide>
            <td class="center-table-cell"><span class="fa fa-trash" onclick="showModalConfirmationSuppression('${type}', '${object.id?c}')"></span></td>
        </#if>
    </tr>
</#if>
