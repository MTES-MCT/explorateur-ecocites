<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<!DOCTYPE html>
<html lang="fr">
<head>
    <#include "common/header.ftl"/>
    <style type="text/css">
        .footer-menu-link {
            height: 30px;
        }

        .footer-col {
            padding-top: 10px;
        }
    </style>

    <#if _csrf??>
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
    </#if>
</head>

<div class=" container-fluid cookie-dialog" style="display: none">
    <div class="row">
        <div class="center-bloc-cookie col-sm-8 ">
            Ce site utilise des cookies pour personnaliser et optimiser votre navigation en ligne et réaliser des
            statistiques de visites.
            Pour plus d'information, cliquez <a class="cursorPointer" onclick="openModalInfosCookies()"><u>ici</u></a>.
        </div>
        <div class="col-sm-2">
            <button class="btn btn-lg btn-block" style="align-content: flex-end"
                    onclick="$('.cookie-dialog').hide();manageCookies('true');"> Accepter
            </button>
        </div>
        <div class="col-sm-2 ">
            <button class="btn btn-lg btn-block" style="align-content: flex-end"
                    onclick="$('.cookie-dialog').hide();manageCookies('false');"> Refuser
            </button>
        </div>
    </div>
</div>

<body>
<meta name=title content="Explorateur ÉcoCité">
<meta name=description content="Avec la démarche ÉcoCité, l’État accompagne la transition écologique des grandes villes françaises.
 Les territoires ÉcoCité mettent en œuvre de grands projets d’aménagement durable pour répondre aux enjeux du changement climatique avec
 le soutien financier du programme d'investissements d'avenir Ville de demain géré par la Caisse des Dépôts. L’État encourage les stratégies
 territoriales ambitieuses et l’innovation pour soutenir la croissance et l’attractivité des villes en assurant le bien être des habitants.">
<meta name=abstract content="Les ÉcoCités : 31 territoires bénéficient aujourd'hui de l'accompagnement de l'État et du soutien financier
du programme d'investissements d'avenir Ville de demain géré par la Caisse des Dépôts.">

<nav id="navbar-top-home" class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div id="navbar-header-top-home" class="navbar-header">
        <h1 id="logo-header" class="navbar-brand ecocite-logo ecocite-logo--navbar">Explorateur ÉcoCité</h1>
        <button type="button" id="navbar-toggle-button" class="navbar-toggle collapsed" data-toggle="collapse"
                data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1" style="float: right;">
        <ul class="nav navbar-nav">
            <li class="navbar-learn-more">
                <a href="http://www.ecocites.logement.gouv.fr/" class="link-with-icon--navbar navbar-learn-more" target="_blank"
                   onclick="hideNavbar()" id="">
                    <i class="navbar-learn-more">Pour aller plus loin<br><em>Site web ÉcoCité</em></i>
                </a>
            </li>
            <li class="">
                <a onclick="scrollToSection('#map-section');" href="#map-section" class="link-with-icon--navbar navbar-button"
                   id="scroll-map">
                    <i class="icon-link--navbar icon-link--navbar---map"></i>
                    <i class="">LA CARTE</i>
                </a>
            </li>
            <li class="">
                <a onclick="scrollToSection('#axises-section');" href="#axises-section" class="link-with-icon--navbar"
                   id="scroll-axises">
                    <i class="icon-link--navbar icon-link--navbar---action"></i>
                    <i class="">LES ACTIONS</i>
                </a>
            </li>
            <li class="">
                <a onclick="openModalRecherche();" href="#" id="open-search-modal" class="link-with-icon--navbar">
                    <i class="icon-link--navbar icon-link--navbar---search"></i>
                    <i class="">RECHERCHER</em></i>
                </a>
            </li>
            <li class="">
                <a href="/bo/login" class="link-with-icon--navbar" style="color: #7fb72f;">
                    <i class="icon-link--navbar icon-link--navbar---login"></i>
                    <i class="navbar-login">SE CONNECTER</em></i>
                </a>
            </li>
        </ul>
    </div>
</nav>


<div id="main-container" class="main container-fluid">
    <section id="home" class="row ecocite-hero--home" style="background-position: right 80px;">
        <div class="home-background">
            <header class="ecocite-logo-wrapper--hero header-background">
                <div class="row">
                    <div class="col-md-1 col-sm-2 col-xs-2">
                        <div class="ecocite-logo ecocite-logo--hero m-t-20"
                             title="Ministère de la transition écologique et solidaire - Ministère de la cohésion des territoires"></div>
                    </div>
                    <div class="col-md-1 col-md-push-10 col-sm-1 col-sm-push-9 col-sm-offset-0 col-xs-1 col-xs-offset-9 contraste p-l-0">
                        <div class="row div-contraste">
                            <div class="col-lg-6 col-md-6 col-sm-12 div-contraste-add t-a-center">
                                <span style="color: black;" onclick="addContrast();"><i class="material-icons grey800">invert_colors</i></span>
                            </div>
                            <div class="col-lg-6 col-md-6 col-sm-12 div-contraste-remove t-a-center">
                                <span style="color: black;" onclick="removeContrast();"><i
                                            class="material-icons grey800">invert_colors_off</i></span>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-10 col-md-pull-1 col-sm-9 col-sm-pull-1 col-xs-12 text-center">
                        <div class="hero-title">
                            <#if titrePrincipal??>
                                ${titrePrincipal.texte?noEsc}
                            </#if>
                        </div>
                        <div class="hero-description">
                            <#if sousTitrePrincipal??>
                                ${sousTitrePrincipal.texte?noEsc}
                            </#if>
                        </div>
                    </div>
                </div>
            </header>


            <div class="col-md-3 col-md-offset-3 col-sm-12 bloc-actualites text-left">
                <#include '../common/blocs/actualite.ftl' />
            </div>
            <div class="col-md-4 col-sm-12 logo-bottom-left">
                <div class="row">
                    <img src="/img/backgrounds/investir_l'avenir.png" style="margin-left: 7px" alt="investir l'avenir">
                </div>
                <div class="row">
                    <img src="/img/backgrounds/groupe_cdd.svg" alt="Caisse des Dépôts">
                </div>
            </div>

            <div class="col-sm-12 scroll-down-btn-wrapper--hero">
                <a id="scroll-down-btn--map" class="scroll-down-btn" onclick="scrollToSection('#map-section');"
                   href="#map-section">Scroll down</a>
            </div>
            <div style="clear: both;"></div>
        </div>
    </section>

    <section id="map-section" class="row">
        <div class="modal-header">
            <div class="modal-header-inner">
                <h2 class="modal-title"><#if titreEcocite??>${titreEcocite.texte?noEsc}</#if></h2>
                <h5 class="modal-subtitle"><#if sousTitreEcocite??>${sousTitreEcocite.texte?noEsc}</#if></h5>
            </div>
        </div>
        <div class="col-xs-12 map">
            <div class="row">
                <div class="col-xs-12 col-md-9 largeMap">
                    <div id="large-map loading" class="loading" style="height: 100%; width: 100%; background-color: #dbe1ed;">
                        <div id="mapPlaceHolder" class="map-placeholder"><p>La carte est en chargement...</p></div>
                        <div id="map" class="map-component"></div>
                    </div>
                </div>
                <div class="col-md-3 colonneMap">
                    <div class="row">
                        <div class="col-xs-6 col-md-12 image-reunion-bg" title="La Réunion" onclick="afficheReunion();">
                            <div style="position: absolute; bottom: 5px; right: 10px; background-color: white; color: black; width: 150px; text-align: center; margin: 2px;">
                                La Réunion
                            </div>
                        </div>
                        <div class="col-xs-6 col-md-12 image-metropole-bg" title="Métropole" onclick="afficheFrance();">
                            <div style="position: absolute; bottom: 5px; right: 10px; background-color: white; color: black; width: 150px; text-align: center; margin: 2px;">
                                Métropole
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <section id="axises-section" class="row">
        <div id="axises-header" class="modal-header">
            <div class="modal-header-inner">
                <h2 class="modal-title"><#if titreAction??>${titreAction.texte?noEsc}</#if></h2>
                <h5 class="modal-subtitle"><#if sousTitreAction??>${sousTitreAction.texte?noEsc}</#if></h5>
            </div>
        </div>
        <div class="blocs-chiffres-action col-lg-12">
            <div class="row mt-20 vignette-info-action-row">
                <div class="col-md-4 col-xs-12 vignette-info-action-cell">
                    <div class="paper vignette-info-action">
                        <div class="info-action text-center bg-light-grey">
                            <p class="m-auto text-center" type="text"><b>
                                <#if nombre_action_visible.texte?? && nombre_action_visible.texte?hasContent>
                                    ${nombre_action_visible.texte}
                                <#else>
                                    ${nbActionsVisible!0} Innovations au total
                                </#if>
                            </b></p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4 col-xs-12 vignette-info-action-cell">
                    <div class="paper vignette-info-action">
                        <div class="info-action text-center bg-light-grey">
                            <p class="m-auto text-center" type="text"><b>
                                <#if nombre_action_realise.texte?? && nombre_action_realise.texte?hasContent>
                                    ${nombre_action_realise.texte}
                                <#else>
                                    ${nbActionsDone!0} Innovations réalisées
                                </#if>
                            </b></p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4 col-xs-12 vignette-info-action-cell">
                    <div class="paper vignette-info-action">
                        <div class="info-action text-center bg-light-grey">
                            <p class="m-auto text-center" type="text"><b>
                                <#if nombre_action_evalue.texte?? && nombre_action_evalue.texte?hasContent>
                                    ${nombre_action_evalue.texte}
                                <#else>
                                    ${nbActionsEvaluated!0} Innovations évaluées
                                </#if>
                            </b></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="axises-wrapper col-lg-12">
            <ul class="axises-list row">
                <li class="col-lg-3 col-md-6 col-sm-6 col-xs-12 axis-0 axis-button">
                    <a onclick="openModalShowAxis({})" href="#" class="axis-link-btn show-content-in-modal"
                       data-nature="axis" data-slug="all"
                       style="box-shadow: 0 0 0; background-size: auto 100%; background: url(/img/backgrounds/selectionnez_un_axe.png) no-repeat center;"
                       title="Sélectionnez une thématique">
                        <span class="initial axis-icon--0">
                            <#--<img src="/img/backgrounds/selectionnez_un_axe.png">-->
                        </span>
                    </a>
                </li>

                <#if axis?? >
                    <#list axis as axe>
                        <li class="col-lg-3 col-md-6 col-sm-6 col-xs-12 axis-2 axis-button">
                            <#--                        <li class="axis-link axis-2">-->
                            <a onclick="openModalShowAxis({ axePrincipale: '${axe.id!?c}'}, event)" href="#"
                               style="color: white; text-align: center; background-color: ${axe.codeCouleur1};"
                               class="axis-link-btn show-content-in-modal" data-nature="axis"
                               data-slug="Bâtiments et usages">
                                <#if axe.icone??>
                                <span class="initial axis-icon--btiments-et-usages"
                                      style="background-image: url('${axe.icone}')" title="${axe.libelle}">
                                <#else>
                                    <span class="initial axis-icon--btiments-et-usages"
                                          style="background: ${axe.codeCouleur2!"#FFA500"}" title="${axe.libelle}">
                                </#if>
                                        <#if axe?? >
                                            ${axe.libelle!""}
                                        </#if>
                                </span>
                            </a>
                        </li>
                    </#list>
                </#if>
            </ul>
        </div>
        <div class="col-lg-12 graph-container-fo">
            <div class="row mt-20">
                <div class="col-lg-6">
                    <div class="paper vignetteGraphique row mb-20">
                        <div class="part1 text-center bg-light-grey">
                            <span class="titre">Répartition des actions par axe</span>
                        </div>
                        <div class="part2">
                            <div class="col-lg-8 col-lg-offset-2 p-20">
                                <select id="filtreEcociteAxe" name="filtreEcocite"
                                        title="Filtre ecocite pour la répartition" class="form-control" data-objectid=""
                                        data-objectclass="Action">
                                </select>
                            </div>
                            <div class="col-lg-12">
                                <div style="position: relative; height: 100%; min-height: 300px">
                                    <canvas id="chartAxe"></canvas>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-6 mb-4">
                    <div class="paper vignetteGraphique row mb-20">
                        <div class="part1 text-center bg-light-grey">
                            <span class="titre">Répartition des actions par finalité</span>
                        </div>
                        <div class="part2">
                            <div class="col-lg-8 col-lg-offset-2 p-20">
                                <select id="filtreEcociteFinalite" name="filtreEcocite"
                                        title="Filtre ecocite pour la répartition" class="form-control" data-objectid=""
                                        data-objectclass="Action">
                                </select>
                            </div>
                            <div class="col-lg-12">
                                <div style="position: relative; height: 100%; min-height: 300px">
                                    <canvas id="chartFinalite"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <input type="hidden" id="geoportailApiKey" value="${geoportailApiKey!""}">
</div>

<footer class="footer">
    <div class="row">
        <div class="col-xs-12 col-md-6 footer-col">
            <div class="text-center">
                <ul class="footer-links">
                    <li>
                        <#if titreMinistere??>${titreMinistere.texte?noEsc}</#if>
                    </li>
                    <li><#if copyright??>${copyright.texte?noEsc}</#if></li>
                    <li><a onclick="openModalMentionsLegales();" href="#">Mentions légales</a></li>
                </ul>
            </div>
        </div>

        <div class="col-xs-12 col-md-6 footer-col">
            <div class="text-center">
                <div class="row">
                    <div class="col-xs-4 col-md-3 col-md-offset-3 text-right">
                        <u>Plan du site :</u>
                    </div>
                    <div class="col-xs-8 col-md-6">
                        <div class="row">
                            <div class="col-xs-6 col-md-6 footer-menu-link">
                                <a onclick="scrollToSection('#map-section');" href="#map-section">La carte</a>
                            </div>
                            <div class="col-xs-6 col-md-6 footer-menu-link">
                                <a onclick="scrollToSection('#axises-section');" href="#map-section">Les actions</a>
                            </div>
                            <div class="col-xs-6 col-md-6 footer-menu-link">
                                <a onclick="openModalRecherche();" href="#">Rechercher</a>
                            </div>
                            <div class="col-xs-6 col-md-6 footer-menu-link">
                                <a href="/bo/login">Se connecter</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</footer>

<#--POPUP - MODAL-->
<div id="popup" class="modal fade" role="dialog" tabindex="-1" aria-hidden="true"></div>
<#--FIN - POPUP - MODAL-->

<#--Contenu de la popup des mentions légales-->

<div id="popupmention" class="modal fade" role="dialog" tabindex="-1" aria-hidden="true">
    <#include "modal/modalMentionsLegales.ftl"/>
</div>
<#--FIN - Contenu de la popup des mentions légales-->

<#--Contenu de la popup des Cookies-->
<div id="popupcookie" class="modal fade" role="dialog" tabindex="-1" aria-hidden="true">
    <#include "modal/infosCookies.ftl"/>
</div>
<#--FIN - Contenu de la popup des Cookie-->

<script src="/js/fo/scripts.js"></script>
<script src="/js/fo/history.js"></script>
<script src="/js/chart/chart.js"></script>
<script src="/js/chart/chart-tools.js"></script>
<script type="application/javascript">
	<#if searchMode??>
	<#if searchMode == '0'>
	openModalRechercheWithText('${searchQuery!''}');
	<#elseIf searchMode == '1'>
	// Maybe nothing
	var data = {
		axePrincipale: "${axePrincipale!""}",
		ecocite: "${ecocite!""}",
		etatAvancement: "${etatAvancement!""}",
		objectifVille: "${objectifVille!""}",
		finalite: "${finalite!""}"
	};
	openModalShowAxis(data);
	</#if>
	<#else>
	<#if paramEcocite??>
	openModalShowEcocite('${paramEcocite}', undefined, true);
	<#elseIf paramAction??>
	openModalShowAction('${paramAction}', undefined, true);
	</#if>
	</#if>
	ecocitesJSON = JSON.parse("${ecocitesJson?jsString?noEsc}");
	initializeChartSelect("chartAxe", "filtreEcociteAxe", "${ecociteMapJson?jsString?noEsc}", "${ecociteAxeGraphMapJson?jsString?noEsc}", "${axeJson?jsString?noEsc}", false);
	initializeChartSelect("chartFinalite", "filtreEcociteFinalite", "${ecociteMapJson?jsString?noEsc}", "${ecociteFinaliteGraphMapJson?jsString?noEsc}", "${finalitesJson?jsString?noEsc}", false);
	afficheFrance()
</script>
</body>
</html>
