<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<#-- @ftlvariable name="title" type="java.lang.String" -->
<#-- @ftlvariable name="currentMenu" type="java.lang.String" -->
<#-- @ftlvariable name="currentTab" type="java.lang.String" -->
<#-- @ftlvariable name="nbEcocites" type="java.lang.Long" -->
<#-- @ftlvariable name="nbActionsVisible" type="java.lang.Long" -->
<#-- @ftlvariable name="nbActionsDone" type="java.lang.Long" -->
<#-- @ftlvariable name="nbActionsEvaluated" type="java.lang.Long" -->
<#-- @ftlvariable name="titre_section" type="com.efficacity.explorateurecocites.beans.biz.json.LibelleFoBean" -->
<#-- @ftlvariable name="nbEcocitesVisibles" type="com.efficacity.explorateurecocites.beans.biz.json.LibelleFoBean" -->
<#-- @ftlvariable name="description_section" type="com.efficacity.explorateurecocites.beans.biz.json.LibelleFoBean" -->
<#-- @ftlvariable name="montant_pia_vdd" type="com.efficacity.explorateurecocites.beans.biz.json.LibelleFoBean" -->
<#-- @ftlvariable name="nombre_action_visible" type="com.efficacity.explorateurecocites.beans.biz.json.LibelleFoBean" -->
<#-- @ftlvariable name="nombre_action_realise" type="com.efficacity.explorateurecocites.beans.biz.json.LibelleFoBean" -->
<#-- @ftlvariable name="nombre_action_evalue" type="com.efficacity.explorateurecocites.beans.biz.json.LibelleFoBean" -->

<!DOCTYPE html>
<html lang="fr" xmlns="http://www.w3.org/1999/html">
<head>
    <#include "../commun/header.ftl"/>
    <script src="/js/ckeditor/ckeditor.js"></script>
    <script src="/js/bo/wording.js"></script>
</head>
<body class="theme-1">
        <#include "../commun/navbar.ftl">
<div class="container-fluid">
    <div class="row">
        <#include "../commun/menuGauche.ftl">
        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
            <div id="contenuBo">
                <div class="row m-b-20">
                    <h1>${title}</h1>
                </div>
                <div class="row m-0">
                    <div class="col-6 p-20">
                        <div class="paper m-0">
                            <h3 class="text-center">Saisie des valeurs</h3>
                            <div class="row">
                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="exampleInputEmail1">${titre_section.description}</label>
                                        <input id="em_${titre_section.id}" type="text" class="form-control saveable_libelle" value="${titre_section.texte}">
                                        <p id="wording-error-${titre_section.id}" class="text-danger" style="display : none;">Erreur lors de la sauvegarde de ce champs</p>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="exampleInputEmail1">${nbEcocitesVisibles.description}</label>
                                        <input id="em_${nbEcocitesVisibles.id}" type="text" class="form-control saveable_libelle" value="${nbEcocitesVisibles.texte}">
                                        <p id="wording-error-${nbEcocitesVisibles.id}" class="text-danger" style="display : none;">Erreur lors de la sauvegarde de ce champs</p>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="exampleInputEmail1">${description_section.description}</label>
                                        <input id="em_${description_section.id}" type="text" class="form-control saveable_libelle" value="${description_section.texte}">
                                        <p id="wording-error-${description_section.id}" class="text-danger" style="display : none;">Erreur lors de la sauvegarde de ce champs</p>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="exampleInputEmail1">${montant_pia_vdd.description}</label>
                                        <input id="em_${montant_pia_vdd.id}" type="text" class="form-control saveable_libelle" value="${montant_pia_vdd.texte}">
                                        <p id="wording-error-${montant_pia_vdd.id}" class="text-danger" style="display : none;">Erreur lors de la sauvegarde de ce champs</p>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="exampleInputEmail1">${nombre_action_visible.description}</label>
                                        <input id="em_${nombre_action_visible.id}" type="text" class="form-control saveable_libelle" value="${nombre_action_visible.texte}">
                                        <p id="wording-error-${nombre_action_visible.id}" class="text-danger" style="display : none;">Erreur lors de la sauvegarde de ce champs</p>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="exampleInputEmail1">${nombre_action_realise.description}</label>
                                        <input id="em_${nombre_action_realise.id}" type="text" class="form-control saveable_libelle" value="${nombre_action_realise.texte}">
                                        <p id="wording-error-${nombre_action_realise.id}" class="text-danger" style="display : none;">Erreur lors de la sauvegarde de ce champs</p>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-12">
                                    <div class="form-group">
                                        <label for="exampleInputEmail1">${nombre_action_evalue.description}</label>
                                        <input id="em_${nombre_action_evalue.id}" type="text" class="form-control saveable_libelle" value="${nombre_action_evalue.texte}">
                                        <p id="wording-error-${nombre_action_evalue.id}" class="text-danger" style="display : none;">Erreur lors de la sauvegarde de ce champs</p>
                                    </div>
                                </div>
                            </div>
                            <button class="btn btn-block btn-primary" onclick="saveAllWording()">Sauvegarder les wordings</button>
                        </div>
                    </div>
                    <div class="col-6 p-20">
                        <div class="paper m-0" style="height: 100%;">
                            <h3 class="text-center">Aperçu du contenu du bloc</h3>
                            <#include '../../common/blocs/actualite.ftl' />
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>
<script>
	<#if currentMenu??>
        $( ".sidebar .active").removeClass( "active" );
        $( ".sidebar #${currentMenu}" ).addClass( "active" );
        <#if currentTab??>
            $(".sidebar").find("#${currentTab}").addClass( "active" );
        </#if>
    <#else>
        $(".sidebar #accueil").addClass( "active" );
	</#if>
	$(function () {
		var inputs = $(".saveable_libelle");
		inputs.on('change textInput input', function (event) {
			var jelt = $(event.target);
			jelt.addClass("needs-validation");
			jelt.removeClass("is-invalid");
			jelt.parent().find(".text-danger").hide();
		});
	})
</script>
</body>
</html>
