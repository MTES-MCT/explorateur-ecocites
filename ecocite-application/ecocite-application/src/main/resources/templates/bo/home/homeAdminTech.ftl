<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0..-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.-->
<#--You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.-->

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
