<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<#-- @ftlvariable name="title" type="java.lang.String" -->
<#-- @ftlvariable name="currentMenu" type="java.lang.String" -->
<#-- @ftlvariable name="currentTab" type="java.lang.String" -->
<#-- @ftlvariable name="libelles" type="java.util.List<com.efficacity.explorateurecocites.beans.biz.json.LibelleFoBean>" -->

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
                <#if libelles?? && libelles?hasContent>
                    <#list libelles as libelle>
                        <div class="row">
                            <div class="col-6">
                                <h3>${libelle.description} *</h3>
                            </div>
                            <div class="col-6">
                                <#if libelle??>
                                    <span class="pull-right" style="font-size: 0.9rem; color: #83b93a; padding-left: 2rem;">
                                        Dernière modification le ${libelle.date!"Date inconnu"} par ${libelle.user!"Inconnu"}).
                                    </span>
                                </#if>
                            </div>
                            <div class="col-10 offset-1">
                                <div class="row">
                                    <div class="col-12">
                                        <div class="form-group">
                                            <textarea id="wording-input-${libelle.id}"
                                                      class="wording-input"
                                                      <#if libelle.javascript>
                                                          data-use-js="true"
                                                      </#if>
                                                      data-id="${libelle.id}"
                                                      title="${libelle.description}"
                                            >
                                                ${(libelle.texte!"")?noEsc}
                                            </textarea>
                                            <p id="wording-error-${libelle.id}" class="text-danger" style="display : none;"></p>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-4 offset-8">
                                        <button id="wording-btn-${libelle.id}" class="btn btn-block btn-primary">Sauvegarder le wording</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <hr style="margin: 30px 0; border: 1px solid;"/>
                    </#list>
                <#else>
                    <div class="row">
                        <p>Il y a pas de libellés à modifier.</p>
                    </div>
                </#if>
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
    $(function (){
        $(".wording-input").each(function (_, element) {
            var id = element.getAttribute("data-id");
            var instance = CKEDITOR.replace(element);
            instance.on('change', function () {
                onChangeWording(id)
            });
            $("#wording-btn-" + id).click(function () {
            	var isJavascript = element.getAttribute("data-use-js");
            	var text = isJavascript ? instance.document.getBody().getText() : instance.getData();
                saveWording(id, text);
            })

        })
    })
</script>
</body>
</html>
