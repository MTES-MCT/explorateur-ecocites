<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0..-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.-->
<#--You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.-->

<#-- @ftlvariable name="typeMesure" type="com.efficacity.explorateurecocites.utils.enumeration.TYPE_MESURE" -->
<#-- @ftlvariable name="selectMap" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="idInput" type="java.lang.String" -->

<#switch typeMesure>
    <#case "NOMBRE_DEC_O">
    <#case "NOMBRE_DEC">
        <input placeholder="Valeur" type="number" id="${idInput}" name="${idInput}" class="form-control"/>
        <#break>
    <#case "NOMBRE_DEC_0_1">
        <input placeholder="Valeur" type="number" step="0.01" id="${idInput}" name="${idInput}" class="form-control"/>
        <#break>
    <#case "OUI_NON">
    <#case "ECHELLE_LICKERT_ACCORD">
    <#case "ECHELLE_LICKERT_BENEFICE">
    <#case "ECHELLE_LICKERT_EXCELLENT">
    <#case "ECHELLE_LICKERT_CHIFFRES_SEULS">
        <#if selectMap?? && selectMap?hasContent>
            <select title="Echelle de likert" id="${idInput}" name="${idInput}" class="form-control">
                <#list selectMap as key, value>
                    <option value="${key}">${value}</option>
                </#list>
            </select>
        <#else>
            <input placeholder="Valeur" type="text" id="${idInput}" name="${idInput}" class="form-control"/>
        </#if>
        <#break>
    <#default>
        <input placeholder="Valeur" type="text" id="${idInput}" name="${idInput}" class="form-control"/>
        <#break>
</#switch>
