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

                <#--VIGNETTES PRINCIPALES-->
                <div class="row">
                    <div class="col-lg-6 mb-4">
                        <div class="paper bg-grey-light vignettePrincipale">
                            <a id="exportActionsCSV_link" href="/bo/referent/exportAdminCSV" class="link-top-left" download="explorateur_ecocite.zip">Exporter les données<span class="fa fa-download fa-1x p-l-10"></a>
                            <span class="fa fa-building fa-3x icon-top-right c-grey-mid "></span>
                            <div class="part1" align="center">
                                <div class="horizontalCenter">
                                    <span class="elementPrincipal">${ecocitesProgramme}</span>
                                    <br/>
                                    <span class="elementSecond">ÉcoCités dans le programme</span>
                                </div>
                            </div>
                            <div class="part2" align="center" onclick="$('#formActions30Jours').submit()">
                                <div class="horizontalCenter">
                                    <span class="elementPrincipal">${actionsMaj30Jours}</span>
                                    <br/>
                                    <span class="elementSecond">Actions mises à jour dans les 30 derniers jours</span>
                                </div>
                                <form id="formActions30Jours" method="get" action="/bo/actions">
                                    <input type="hidden" name="filtres['30jours']" value="empty"  class="form-control"/>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6 mb-4">
                        <div class="paper bg-grey-light vignettePrincipale">
                            <span class="fa fa-lightbulb-o fa-3x icon-top-right c-grey-mid "></span>
                            <div class="part1" align="center" onclick="$('#formActions').submit()">
                                <div class="horizontalCenter">
                                    <span class="elementPrincipal">${actionsProgramme}</span>
                                    <br/>
                                    <span class="elementSecond">Actions dans le programme</span>
                                </div>
                                <form id="formActions" method="get" action="/bo/actions">
                                    <input type="hidden" name="action" class="form-control"/>
                                </form>
                            </div>
                            <div class="part2" align="center">
                                <div class="col-lg-6 part2-1">
                                    <div class="horizontalCenter">
                                        <span class="elementPrincipal">${actionsIng}*</span>
                                        <br/>
                                        <span class="elementSecond">Actions d'ingénierie</span>
                                        <br/>
                                        <#--<span class="elementThird">*Une action peutêtre à la fois une action d'ingénieirie et d'investissement</span>-->
                                    </div>
                                    <span class="elementThird">*Une action peut être à la fois une action d'ingénierie et d'investissement</span>
                                </div>
                                <div class="col-lg-6 part2-2">
                                    <div class="horizontalCenter">
                                        <span class="elementPrincipal">${actionsInv}*</span>
                                        <br/>
                                        <span class="elementSecond">Actions d'investissements ou de prise de participation</span>
                                    </div>
                                    <span class="elementThird">*Une action peut être à la fois une action d'ingénierie et d'investissement</span>
                                    <#--<div class="col-lg-12">-->
                                        <#--test-->
                                    <#--</div>-->
                                    <#--<div class="col-lg-12">-->
                                        <#--test-->
                                    <#--</div>-->
                                    <#--<div class="col-lg-12">-->
                                        <#--test-->
                                    <#--</div>-->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


                <#--VIGNETTES BLEUES-->
                <div class="row">
                    <div class="col-lg-6 mb-4">
                        <div class="paper vignetteSecond" style="background-color: #4688f1" align="center" onclick="$('#formFiltreIndicateurAValider').submit();">
                            <a href="#"><span class="fa fa-pencil fa-3x icon-top-right icon-pencil-blue"></span></a>
                            <div class="horizontalCenter">
                                <span class="elementPrincipal">${nouveauIndicAValider}</span>
                                <br/>
                                <span class="elementSecond">Nouveaux indicateurs à valider</span>
                            </div>
                            <form id="formFiltreIndicateurAValider" method="get" action="/bo/indicateurs">
                                <input type="hidden" name="filtres['etatValidation']" value="non_valide" class="form-control"/>
                            </form>
                        </div>
                    </div>
                    <div class="col-lg-6 mb-4">
                        <div class="paper vignetteSecond" style="background-color: #4688f1" align="center" onclick="$('#formFiltreEvaluationInnovationValide').submit()">
                            <a href="#"><span class="fa fa-pencil fa-3x icon-top-right icon-pencil-blue"></span></a>
                            <div class="horizontalCenter">
                                <span class="elementPrincipal">${evalAValider}</span>
                                <br/>
                                <span class="elementSecond">Evaluations de l'innovation d'actions à valider</span>
                            </div>
                            <form id="formFiltreEvaluationInnovationValide" method="get" action="/bo/actions">
                                <input type="hidden" name="filtres['evaluationInnovation']" value="envoyer" class="form-control"/>
                            </form>
                        </div>
                    </div>
                </div>


                <#--VIGNETTES GRAPHIQUES-->
                <div class="row">
                    <div class="col-lg-6 mb-4">
                        <div class="paper bg-grey-light vignetteGraphique">
                            <div class="part1">
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
                            <div class="part1" align="center">
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
        </main>
    </div>
</div>

<script src="/js/chart/chart.js"></script>
<script src="/js/chart/chart-tools.js"></script>
<script type="application/javascript">
    <#if currentMenu??>
    $( ".sidebar .active").removeClass( "active" );
    $( ".sidebar #${currentMenu}" ).addClass( "active" );
    <#else>
    $( ".sidebar #accueil" ).addClass( "active" );
    </#if>

    window.onload = function() {
        initializeChartSelect("chartAxe", "filtreEcociteAxe", "${ecociteMapJson?jsString?noEsc}", "${ecociteAxeGraphMapJson?jsString?noEsc}", "${axeJson?jsString?noEsc}", true);
        initializeChartSelect("chartFinalite", "filtreEcociteFinalite", "${ecociteMapJson?jsString?noEsc}", "${ecociteFinaliteGraphMapJson?jsString?noEsc}", "${finalitesJson?jsString?noEsc}", true);
    };

</script>

</body>

</html>
