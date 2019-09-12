<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<#-- @ftlvariable name="ecocite" type="com.efficacity.explorateurecocites.beans.biz.EcociteBean" -->
<#-- @ftlvariable name="ongletActif" type="java.lang.String" -->

<!DOCTYPE html>
<html lang="fr">
<head>
    <#include "../commun/header.ftl"/>
    <script src="/js/ckeditor/ckeditor.js"></script>
</head>

<body class="theme-1">

    <#assign currentMenu = "ecocites" >
    <#include "../commun/navbar.ftl">

    <div class="container-fluid">
        <div class="row">
            <#include "../commun/menuGauche.ftl">
            <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
                <div class="row">
                    <div class="col-lg-12 head my-4">
                        <h2 class=" ft-700">${ecocite.nom!}</h2>
                    </div>
                    <div class="col-lg-12 mb-4">
                        <div class="paper bg-grey-light">
                            <div class="head d-flex mb-4 py-0 px-3 border-0">
                                <div class="row d-block w-100">
                                    <#include "ongletEcocite.ftl" />
                                </div>
                            </div>
                            <div class="body">
                                <!-- Tab panes -->
                                <div class="tab-content">
                                    <div role="tabpanel" class="tab-pane fade in active show table-drag" id="presentation">
                                    </div>
                                    <div role="tabpanel" class="tab-pane fade in table-drag" id="categorisation">
                                    </div>
                                    <div role="tabpanel" class="tab-pane fade in table-drag" id="indicateurOngletRealisation">
                                    </div>
                                    <div role="tabpanel" class="tab-pane fade in table-drag" id="indicateurOngletResultat">
                                    </div>
                                    <div role="tabpanel" class="tab-pane fade in table-drag" id="mesureIndicateurOnglet">
                                    </div>
                                    <div role="tabpanel" class="tab-pane fade in table-drag" id="questionnaire_ecocite">
                                    </div>
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
        <input type="hidden" id="idEcocite" value="${ecocite.id!?c}"/>
    </div>
    <script src="/js/chart/chart.js"></script>
    <script src="/js/chart/chart-tools.js"></script>
    <script src="/js/bo/ecocite.js"></script>
    <script src="/js/bo/questionnaire.js"></script>
    <script src="/js/bo/indicateurs.js"></script>
    <!-- JS -->
    <script type="application/javascript">
        <#if currentMenu??>
                $(".sidebar .active").removeClass("active");
                $(".sidebar #${currentMenu}").addClass("active");
        <#else>
                $( ".sidebar #accueil" ).addClass("active");
        </#if>
        window.onload = function() {
            loadOnglet("${ongletActif!"presentation"}", "${ecocite.id!?c}");
            $( ".inputSaveAuto" ).blur(
                    function() { saveAttribut(this, this.value);}
            );

            $('.listOngletEcocite a[data-toggle="tab"]').on('click', function (e) {
                var clickedElt = $(e.target);
                if (!clickedElt.is('a[data-toggle="tab"]')) {
                    clickedElt = clickedElt.closest('a[data-toggle="tab"]')
                }
                if (!clickedElt.closest("li.nav-item").hasClass("state-impossible")) {
                    var target = clickedElt.attr("href");
                    loadOnglet(target.replace("#", ""), '${ecocite.id!?c}');
                }
            });

            $('.loadComment').on('click', function() {
                var href = $(this).attr("href");
                if (href) {
                    loadCommentaire(href, "${ecocite.id!?c}");
                }
            });
            $('body').on('blur', 'textarea.commentaire', function() {
                majCommentaire(this, "${ecocite.id!?c}");
            });
        };
    </script>
</body>

</html>
