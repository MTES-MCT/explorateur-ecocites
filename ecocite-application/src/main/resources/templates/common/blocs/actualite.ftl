<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0..-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.-->
<#--You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.-->

<#-- @ftlvariable name="nbEcocites" type="java.lang.Long" -->
<#-- @ftlvariable name="nbActionsVisible" type="java.lang.Long" -->
<#-- @ftlvariable name="nbActionsDone" type="java.lang.Long" -->
<#-- @ftlvariable name="nbActionsEvaluated" type="java.lang.Long" -->
<#-- @ftlvariable name="nbEcocitesVisibles" type="com.efficacity.explorateurecocites.beans.biz.json.LibelleFoBean" -->
<#-- @ftlvariable name="titre_section" type="com.efficacity.explorateurecocites.beans.biz.json.LibelleFoBean" -->
<#-- @ftlvariable name="description_section" type="com.efficacity.explorateurecocites.beans.biz.json.LibelleFoBean" -->
<#-- @ftlvariable name="montant_pia_vdd" type="com.efficacity.explorateurecocites.beans.biz.json.LibelleFoBean" -->
<#-- @ftlvariable name="nombre_action_visible" type="com.efficacity.explorateurecocites.beans.biz.json.LibelleFoBean" -->
<#-- @ftlvariable name="nombre_action_realise" type="com.efficacity.explorateurecocites.beans.biz.json.LibelleFoBean" -->
<#-- @ftlvariable name="nombre_action_evalue" type="com.efficacity.explorateurecocites.beans.biz.json.LibelleFoBean" -->

<#if titre_section.texte?? && titre_section.texte?hasContent>
    <p class="titre-actualites" type="text"><b>${titre_section.texte}</b></p>
</#if>
<#if nbEcocitesVisibles.texte?? && nbEcocitesVisibles.texte?hasContent>
    <p type="text"><b>${nbEcocitesVisibles.texte}</b></p>
<#else>
    <p type="text"><b>${nbEcocites!0} Écocités</b></p>
</#if>
<#if description_section.texte?? && description_section.texte?hasContent>
    <p type="text"><b>${description_section.texte}</b></p>
</#if>
<#if montant_pia_vdd.texte?? && montant_pia_vdd.texte?hasContent>
    <p type="text"><b>${montant_pia_vdd.texte}</b></p>
</#if>
<#if nombre_action_visible.texte?? && nombre_action_visible.texte?hasContent>
    <p type="text">${nombre_action_visible.texte}</p>
<#else>
    <p type="text">${nbActionsVisible!0} innovations au total</p>
</#if>
<#if nombre_action_realise.texte?? && nombre_action_realise.texte?hasContent>
    <p type="text">${nombre_action_realise.texte}</p>
<#else>
    <p type="text">${nbActionsDone!0} innovations réalisées</p>
</#if>
<#if nombre_action_evalue.texte?? && nombre_action_evalue.texte?hasContent>
    <p type="text">${nombre_action_evalue.texte}</p>
<#else>
    <p type="text">${nbActionsEvaluated!0} innovations évaluées</p>
</#if>
