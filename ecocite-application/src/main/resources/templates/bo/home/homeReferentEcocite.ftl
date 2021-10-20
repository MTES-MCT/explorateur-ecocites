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
            <#if ecocites?? >
            <div class="row">
                <div class="col-lg-6 head">
                    <h2 class="ft-700">Mes ÉcoCités</h2>
                </div>
                <div class="col-lg-2 head"></div>
                <div class="col-lg-4">
                    <form id="formFiltreActionsFromEcocite" method="get" action="/bo/actions">
                        <select id="selectEcocite" onchange="onReferentEcociteSelecterChange();"
                                name="filtres['ecocite']"
                                class="form-control">
                            <#if ecocites?? >
                                <#list ecocites as ecocite>
                                    <option value="${ecocite.id}" >${ecocite.nom!}</option>
                                </#list>
                             </#if>
                        </select>
                    </form>
                </div>
            </div>

            <div id="contenuBo">

<#--VIGNETTES PRINCIPALES + VIGNETTES BLEUES VERTES-->
    <div id="vignettesReferentEcocite">
        <#include "vignettesRefEco.ftl">
    </div>
<#--VIGNETTES GRAPHIQUES-->
                <div class="row">
                    <div class="col-lg-6 mb-4">
                        <div class="paper bg-grey-light vignetteGraphique">
                            <div class="part1" style="height:inherit">
                                <span class="titre">Répartition des actions par axe</span>
                            </div>
                            <div class="part2">
                            <#--<div class="row">-->
                                <div class="col-xs-offset-3 col-xs-9 m-20">
                                    <select id="filtreEcociteAxe" name="filtreEcocite" title="Filtre ecocite pour la répartition" class="form-control" data-objectid="" data-objectclass="Action">
                                    </select>
                                </div>
                            <#--</div>-->
                                <div class=col-lg-12">
                                    <div style="position: relative; height: 100%;">
                                        <canvas id="chartAxe"></canvas>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6 mb-4">
                        <div class="paper bg-grey-light vignetteGraphique">
                            <div class="part1" style="height:inherit" align="center">
                                <span class="titre">Répartition des actions par finalité</span>
                            </div>
                            <div class="part2">
                            <#--<div class="row">-->
                                <div class="col-xs-offset-3 col-xs-9 m-20">
                                    <select id="filtreEcociteFinalite" name="filtreEcocite" title="Filtre ecocite pour la répartition" class="form-control" data-objectid="" data-objectclass="Action">
                                    </select>
                                </div>
                            <#--</div>-->
                                <div class=col-lg-12">
                                    <div style="position: relative; height: 100%;">
                                        <canvas id="chartFinalite"></canvas>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        <#else>
            <div class="col-lg-12 head col-h-center">
                <h2 class="ft-700">Vous êtes responsable d'aucune ÉcoCité.</h2>
            </div>
        </#if>
        </main>
    </div>
</div>

<script src="/js/bo/accueil.js"></script>
<script src="/js/chart/chart.js"></script>
<script src="/js/chart/chart-tools.js"></script>
<script type="application/javascript">
    <#if currentMenu??>
    $( ".sidebar .active").removeClass( "active" );
    $( ".sidebar #${currentMenu}" ).addClass( "active" );
    <#else>
    $( ".sidebar #accueil" ).addClass( "active" );
    </#if>
    <#if ecociteMapJson?? && ecociteAxeGraphMapJson?? && ecociteFinaliteGraphMapJson?? && axeJson?? && finalitesJson??>
    window.onload = function() {
        initializeChartSelect("chartAxe", "filtreEcociteAxe", "${ecociteMapJson?jsString?noEsc}", "${ecociteAxeGraphMapJson?jsString?noEsc}", "${axeJson?jsString?noEsc}", true);
        initializeChartSelect("chartFinalite", "filtreEcociteFinalite", "${ecociteMapJson?jsString?noEsc}", "${ecociteFinaliteGraphMapJson?jsString?noEsc}", "${finalitesJson?jsString?noEsc}", true);
    };
    </#if>
</script>

</body>

</html>
