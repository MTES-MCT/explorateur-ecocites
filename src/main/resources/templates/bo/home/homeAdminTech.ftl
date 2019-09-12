<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<#-- @ftlvariable name="ecociteMapJson" type="java.lang.String" -->
<#-- @ftlvariable name="ecociteFinaliteGraphMapJson" type="java.lang.String" -->
<#-- @ftlvariable name="finalitesJson" type="java.lang.String" -->
<#-- @ftlvariable name="ecociteAxeGraphMapJson" type="java.lang.String" -->
<#-- @ftlvariable name="axeJson" type="java.lang.String" -->

<!DOCTYPE html>
<html lang="fr">
<head>
<#include "../commun/header.ftl"/>
</head>

<body class="theme-1">

<style type="text/css">

</style>

<#include "../commun/navbar.ftl">

<div class="container-fluid">
    <div class="row">

    <#assign currentMenu = "accueil" >
    <#include "../commun/menuGauche.ftl">

        <!-- DASHBOARD WRAPPER -->
        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">

            <div id="contenuBo">

                <h2 class="ft-700">Fichiers Serveur</h2>

                <#--VIGNETTES PRINCIPALES-->
                <div class="row">
                    <div class="col-lg-6">
                        <div class="paper bg-grey-light vignettePrincipale">
                            <h5 class="p-l-10 p-t-10">Fichiers de logs <span class="ft-s-8">(${logsPath})</span></h5>
                            <#if filesLog??>
                                <ul class="p-t-10">
                                    <#list filesLog as file>
                                        <li>
                                            <#if file?? && file.file??>
                                                <a href="/files/log/${file.file.name}">${file.file.name}</a><span class="ft-s-8"> (${file.dateCreationAffichable})</span>
                                            <#else>
                                                inconnu
                                            </#if>
                                        </li>
                                    </#list>
                                </ul>
                            </#if>
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <div class="paper bg-grey-light vignettePrincipale">
                            <h5 class="p-l-10 p-t-10">Fichiers de Bdd <span class="ft-s-8">(${dumpPath})</span></h5>
                            <#if filesDump??>
                                <ul class="p-t-10">
                                    <#list filesDump as file>
                                        <li>
                                            <#if file?? && file.file??>
                                                <a href="/files/dump/${file.file.name}">${file.file.name}</a><span class="ft-s-8"> (${file.dateCreationAffichable})</span>
                                            <#else>
                                                inconnu
                                            </#if>
                                        </li>
                                    </#list>
                                </ul>
                            </#if>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>

</body>

</html>
