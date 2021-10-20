<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0..-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.-->
<#--You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.-->

<#-- @ftlvariable name="currentMenu" type="java.lang.String" -->
<#-- @ftlvariable name="currentTab" type="java.lang.String" -->
<#-- @ftlvariable name="objectType" type="java.lang.String" -->
<#-- @ftlvariable name="usesModal" type="java.lang.Boolean" -->
<#-- @ftlvariable name="modalUrl" type="java.lang.String" -->


<!DOCTYPE html>
<html lang="fr">
    <head>
        <#include "commun/header.ftl"/>
        <script src="/js/bo/edition_objet.js"></script>
        <#if objectType == "indicateurs">
            <script src="/js/bo/indicateurs.js"></script>
        </#if>
    </head>
    <body class="theme-1">
        <#include "commun/navbar.ftl">
        <div class="container-fluid">
            <div class="row">
            <#include "commun/menuGauche.ftl">
                <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
                    <div id="contenuBo">
                        <#include "pagination/liste-pagination.ftl"/>
                    </div>
                </main>
            </div>
        </div>
        <script type="application/javascript">
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
                appliqueDataTable();
                <#if filtres??>
                    <#if selectedFiltres??>
                    <#else>
                        reloadFilterFromLocalStorage('${urlFilter!}');
                    </#if>
                    soumissionForm('${urlFilter!}');
                </#if>
                var queryParameters = parse_query_string(location.search);
                if (queryParameters.openId) {
                	$("#tr-" + queryParameters.openId).click()
                }
            });
        </script>
    </body>
</html>
