<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<style type="text/css">
    <#if axes??>
        <#list axes as axe>
            #btn-outline-axe-${axe.id?c}:hover,
            #btn-outline-axe-${axe.id?c}:focus,
            #btn-outline-axe-${axe.id?c}.selected{
                color: #fff!important;
                background-color: ${axe.codeCouleur1}!important;
                border-color: ${axe.codeCouleur1}!important;
            }
            .color-etiquette-${axe.id?c} {
                border-color: ${axe.codeCouleur1}!important;
                color: ${axe.codeCouleur1}!important;
            }
        </#list>
    </#if>
</style>
<div class="row align-items-center">
    <div class="col-lg-3 col-xl-3 col-1">
        <#if action?? && action.typeFinancementAuMoinsIngenierie>
        <button class="btn btn-bleu-efficacity btn-block btn-wrap-text text-white" <#if action??> onclick="loadOnglet('ingenierie', '${action.id?c}');"</#if>>
            <div class="row ">
                <div class="col-1 col-v-center col-h-center">
                    <i class="fa fa-arrow-left" aria-hidden="true"></i>
                </div>
                <div class="col-9 d-none d-lg-block">
                    Types de mission
                </div>
            </div>
        </button>
         </#if>
    </div>
    <div class="col-lg-6 col-xl-6 col-10">
        <div class="head">
            <p class="ft-s-20 ft-700">Domaine d'action</p>
            <#if titrePageValidation??>
                <p style="color:#83b93a;">${titrePageValidation!}</p>
            </#if>
            <#if !caracteristiqueLectureSeule>
                <p class="c-bleu-e" >Pour passer à l'étape suivante, vous devez choisir une étiquette "domaine d'action principal".<br/>Vous pouvez si vous le souhaitez ajouter jusqu'à cinq étiquettes "domaines d'action secondaires".</p>
            </#if>
        </div>
    </div>
    <div class="col-lg-3 col-xl-3 col-1">
        <button class="btn btn-bleu-efficacity btn-block btn-wrap-text text-white" <#if action??> onclick="loadOnglet('objectif', '${action.id?c}');"</#if>>
            <div class="row">
                <div class="col-9 d-none d-lg-block">
                    Objectifs
                </div>
                <div class="col-1 col-v-center col-h-center">
                    <i class="fa fa-arrow-right" aria-hidden="true"></i>
                </div>
            </div>
        </button>
    </div>
</div>
<div class="body flex-column flex-lg-row">
    <#if actionDomain??>
        <#assign domains = actionDomain.etiquettes/>
        <#assign etqsSecondaires = []/>
        <#if actionDomain.etiquettesSelected?? && actionDomain.etiquettesSelected?hasContent>
            <#list actionDomain.etiquettesSelected as etq>
                <#if etq.poid == 1>
                    <#assign etqPrincipal = etq/>
                <#elseIf etq.poid == 2>
                    <#assign etqsSecondaires = etqsSecondaires + [etq]/>
                </#if>
            </#list>
        </#if>
    </#if>
    <#if !caracteristiqueLectureSeule>
        <div class="col-lg-4 col-12" style="position:relative;">
            <div class="title-container">
                <div class="title">
                    A quels axes d’intervention correspond votre action ?
                </div>
            </div>
            <div class="content">
                <#if axes??>
                    <#list axes as axe>
                        <button id="btn-outline-axe-${axe.id?c}" name="idAxe"
                                data-objectId="${action.id!?c}" data-objectClass="Action"
                                style="white-space:normal !important; word-wrap: break-word; color: ${axe.codeCouleur1}; border-color: ${axe.codeCouleur1}; background-color: transparent;
                                        background-image: none;" class="btn btn-lg btn-block <#if axe?isFirst>selected</#if>"
                                onclick="showListEtiquettesForAction('${axe.id?c}', 'axe', this, '${action.id!?c}')"
                        >
                            ${axe.libelle}
                        </button>
                    </#list>
                </#if>
            </div>
        </div>
        <div class="col-lg-4 col-12" style="position:relative;">
            <div class="title-container">
                <div class="title">
                    Choisissez vos étiquettes
                </div>
            </div>
            <div class="content">
                <#if !axeSelec??>
                    <div class="info-cat-empty">
                        Sélectionnez un axe afin d'accéder aux domaines d'action associés
                    </div>
                </#if>
                <!-- sort: true -->
                <div id="etiquettesGroup" class="list-group">
                    <div>
                        <input id="currentCategory" type="hidden" value="axe">
                        <input id="currentCategoryId" type="hidden" value="${axeSelec.id!?c}">
                    </div>
                    <#if domains?? && domains?hasContent>
                        <#list domains as etiquetteAsso>
                            <#assign lectureSeule = true>
                            <#include "../../components/etiquette.ftl">
                        </#list>
                    </#if>
                </div>
            </div>
        </div>
    </#if>

    <div <#if !caracteristiqueLectureSeule> class="col-lg-4 col-12" <#else> class="col-lg-6 col-6 offset-lg-3 offset-3" </#if>>
        <div class="title-container bg-grey-light">
            <div class="title">
                Domaines d'action choisis
            </div>
        </div>
        <div class="content">
            <div class="title">
                Domaine d'action principal
            </div>

            <div class="info-etq-empty ft-s-10" <#if etqPrincipal??>hidden</#if>>
                Sélectionnez des étiquettes pour caractériser votre action
            </div>

            <!-- sort: false -->
            <div id="etiquettePrincipalSolo" class="list-group drag-and-drop-area etq-list">
            <#--<#if !etqPrincipal??>Sélectionnez une étiquette principale pour catégoriser votre action</#if>-->
                <#if etqPrincipal??>
                    <#assign etiquetteAsso = etqPrincipal>
                    <#assign lectureSeule = caracteristiqueLectureSeule>
                    <#include "../../components/etiquette.ftl">
                </#if>
            </div>
        </div>
        <hr/>
        <div class="content">
            <div class="title">
                Domaines d'action secondaires (5 max)
            </div>
            <div class="info-etq-empty ft-s-10" <#if etqsSecondaires?? && etqsSecondaires?hasContent || titrePageValidation??>hidden</#if>>
                Sélectionnez également une ou plusieurs étiquette(s) secondaire(s)
            </div>

            <!-- sort: false -->
            <div id="etiquetteSecondaire" class="list-group drag-and-drop-area etq-list">
                <#if etqsSecondaires?? && etqsSecondaires?hasContent>
                    <#list etqsSecondaires as etiquetteAsso>
                        <#assign lectureSeule = caracteristiqueLectureSeule>
                        <#include "../../components/etiquette.ftl">
                    </#list>
                </#if>
            </div>
        </div>
        <div class="content">
            <label for="domainCommentaire">Commentaire <i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Vous pouvez si besoin expliquer votre sélection d’étiquettes ici"></i></label>
            <textarea class="form-control commentaire" id="domainCommentaire" rows="3" <#if caracteristiqueLectureSeule>disabled</#if>></textarea>
            <#if isIngenierie>
                <#if canEdit?? && canEdit && !caracteristiqueLectureSeule>
                    <button id="button-submit-category" type="button" class="btn btn-lg btn-primary btn-block m-t-10" data-toggle="modal" data-target="#confirmationRequesteValidationCategorisationIndicateur">
                        Envoyer pour validation <i class="fa fas fa-envelope-o"></i>
                    </button>
                <#elseIf canValide?? && canValide >
                    <button id="button-submit-category" type="button" class="btn btn-lg btn-primary btn-block m-t-10" data-toggle="modal" data-target="#confirmationValidationCategorisationIndicateur">
                        Valider la soumission <i class="fa fas fa-envelope-o"></i>
                    </button>
                    <div class="footer">
                    </div>
                <#elseIf canEdit?? && canEdit >
                    <button id="button-submit-category" type="button" class="btn btn-lg btn-primary btn-block m-t-10" data-toggle="modal" data-target="#confirmationAnnulationValidationCategorisationIndicateur">
                        Annuler la validation
                    </button>
                </#if>
            </#if>
        </div>
    </div>
</div>
<div class="footer"></div>

<#assign typeOnglet = "category"/>
<#include "validationCategorisationIndicateurAction.ftl"/>
