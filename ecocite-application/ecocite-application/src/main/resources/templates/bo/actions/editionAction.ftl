<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0..-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.-->
<#--You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.-->

<#-- @ftlvariable name="action" type="com.efficacity.explorateurecocites.beans.biz.ActionBean" -->
<#-- @ftlvariable name="ongletActif" type="java.lang.String" -->

<!DOCTYPE html>
<html lang="fr">
<head>
    <#include "../commun/header.ftl"/>
    <script src="/js/ckeditor/ckeditor.js"></script>
</head>

<body class="theme-1">

    <#assign currentMenu = "actions" >
    <#include "../commun/navbar.ftl">

    <div class="container-fluid">
        <div class="row">
            <#include "../commun/menuGauche.ftl">
            <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
                <div class="row">
                    <div class="col-lg-12 head my-4">
                        <h2 class=" ft-700">${action.nomPublic!}</h2>
                    </div>
                    <div class="col-lg-12 mb-4">
                        <div class="paper bg-grey-light">
                            <div class="head d-flex mb-4 py-0 px-3 border-0">
                                <div class="row d-block w-100">
                                    <#include "./ongletAction.ftl" />
                                </div>
                            </div>
                            <div class="body">
                                <!-- Tab panes -->
                                <div class="tab-content">
                                    <div role="tabpanel" class="tab-pane fade in table-drag" id="presentation"></div>
                                    <#if action.typeFinancementIngenierie || action.typeFinancementIngenierieEtInvestissement || action.typeFinancementIngenierieEtPriseParticipation>
                                        <div role="tabpanel" class="tab-pane fade in table-drag" id="ingenierie"></div>
                                    </#if>
                                    <div role="tabpanel" class="tab-pane fade in table-drag" id="domaine"></div>
                                    <div role="tabpanel" class="tab-pane fade in table-drag" id="objectif"></div>
                                    <#if action?? && !action.typeFinancementIngenierie>
                                        <div role="tabpanel" class="tab-pane fade in table-drag" id="indicateurOngletRealisation"></div>
                                        <div role="tabpanel" class="tab-pane fade in table-drag" id="indicateurOngletResultat"></div>
                                        <div role="tabpanel" class="tab-pane fade" id="evaluation_innovation"></div>
                                        <div role="tabpanel" class="tab-pane fade in table-drag" id="mesureIndicateurOnglet"></div>
                                    </#if>
                                    <#if action?? && (action.typeFinancementIngenierieEtInvestissement || action.typeFinancementIngenierieEtPriseParticipation || action.typeFinancementIngenierie)>
                                        <div role="tabpanel" class="tab-pane fade in table-drag" id="questionnaire_action_ingenierie">
                                        </div>
                                    </#if>
                                    <#if action?? && (action.typeFinancementIngenierieEtInvestissement || action.typeFinancementIngenierieEtPriseParticipation || action.typeFinancementInvestissementOuPriseParticipation)>
                                        <div role="tabpanel" class="tab-pane fade in table-drag" id="questionnaire_action_investissement">
                                        </div>
                                    </#if>
                                </div>
                            </div>
                            <div class="modal fade" id="modalAjoutDocument" tabindex="-1" role="dialog" aria-labelledby="modalAjoutDocumentTitle" aria-hidden="true">
                            </div>
                            <div class="footer">
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        </div>
        <input type="hidden" id="idAction" value="${action.id!?c}"/>
    </div>

    <#assign message="Vous n'avez pas les droits pour effectuer cette action">
    <#assign idTitre="erreurDroitUtilisateurTitle">
    <#assign displayModalError=true>
    <div class="modal" id="erreurDroitUtilisateur" tabindex="-1" role="dialog" aria-hidden="true">
        <#include "../modal/error_modal.ftl" >
    </div>

    <script src="/js/chart/chart.js"></script>
    <script src="/js/chart/chart-tools.js"></script>
    <script src="/js/bo/action.js"></script>
    <script src="/js/bo/questionnaire.js"></script>
    <script src="/js/bo/indicateurs.js"></script>
    <script type="application/javascript">
        <#if currentMenu??>
            $( ".sidebar .active").removeClass( "active" );
            $( ".sidebar #${currentMenu}" ).addClass( "active" );
        <#else>
            $( ".sidebar #accueil" ).addClass( "active" );
        </#if>
        window.onload = function() {
            loadOnglet("${ongletActif!"presentation"}", '${action.id!?c}');
            $( ".inputSaveAuto" ).blur(
                    function() { saveAttribut(this, this.value);}
            );
            $('.listOngletAction a[data-toggle="tab"]').on('click', function (e) {
            	var clickedElt = $(e.target);
            	if (!clickedElt.is('a[data-toggle="tab"]')) {
            		clickedElt = clickedElt.closest('a[data-toggle="tab"]')
                }
            	if (!clickedElt.closest("li.nav-item").hasClass("state-impossible")) {
                    var target = clickedElt.attr("href");
                    loadOnglet(target.replace("#", ""), '${action.id!?c}');
                }
            });

            $('.loadComment').on('click', function() {
                var href = $(this).attr("href");
                if (href) {
                    loadCommentaire(href, "${action.id!?c}");
                }
            });
            $('body').on('blur', 'textarea.commentaire', function() {
                majCommentaire(this, "${action.id!?c}");
            });
        };
    </script>
</body>

</html>
