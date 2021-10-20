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
        <#include "../commun/header.ftl"/>
</head>
<body class="theme-1">
        <#include "../commun/navbar.ftl">
<div class="container-fluid">
    <div class="row">
            <#include "../commun/menuGauche.ftl">
        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
            <div id="contenuBo">
                <div class="row">
                    <div class="col-6 head my-4">
                        <h2 class="ft-700">Rapport ÉcoCité</h2>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-lg-4">
                        <label for="download_ecocites_filter"><strong>ÉcoCité</strong></label><br/>
                        <select class="form-control" id="download_ecocites_filter">
                        </select>
                    </div>
                    <div class="col-lg-4">
                        <label><strong style="visibility: hidden">Téléchargement</strong></label><br/>
                        <button class="btn btn-success btn-block" id="download_btn">
                            Télécharger le rapport <span class="fa fa-download"></span>
                        </button>
                        <a id="download_link" class="hidden" href="#" download="export.odt" ></a>
                    </div>
                </div>
                <div id="no_download" align="center" style=" display: none">
                    <span style="color: red">Vous ne pouvez pas télécharger de rapport d'ÉcoCité.</span>
                </div>
            </div>
        </main>
    </div>
</div>
<script src="/js/tools.js"></script>
<script src="/js/bo/rapports_ecocites.js"></script>
<script type="application/javascript">
    <#if currentMenu??>
                $( ".sidebar .active").removeClass( "active" );
                $( ".sidebar #export_rapport" ).addClass( "active" );
        <#if currentTab??>
                    $(".sidebar").find("#${currentTab}").addClass( "active" );
        </#if>
    <#else>
                $(".sidebar #accueil").addClass( "active" );
    </#if>
    initRapportFilter('${ecociteJson?jsString?noEsc}')
</script>
</body>
</html>
