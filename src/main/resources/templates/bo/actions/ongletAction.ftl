<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<#-- @ftlvariable name="action" type="com.efficacity.explorateurecocites.beans.biz.ActionBean" -->

<#macro etapeCss etape><#if etape?? && etape.cssClass??>${etape.cssClass}</#if></#macro>

<#macro etapeChildren etape><#if etape?? && etape.cssClassLink??>${etape.cssClassLink}</#if></#macro>

<#macro etapeBadge etape number=-1><#if etape?? && etape.getBadgeContentAction(number)??>${etape.getBadgeContentAction(number)?noEsc}</#if></#macro>


<ul id="tab-list" class="nav nav-tabs col-lg-12 listOngletAction" role="tablist">
    <li class="nav-item">
        <a class="nav-link" href="#presentation" role="tab" data-toggle="tab">Présentation</a>
    </li>
    <!-- DROPDOWN -->
    <#if action.etapeCaraterisation??>
    <li class="nav-item dropdown <@etapeCss action.etapeCaraterisation!/>">
        <a id="dLabel" class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
            <div class="m-r-10"><div class="tab-badge"><@etapeBadge action.etapeCaraterisation/></div></div>
            <div>Caractérisation de l'action</div>
        <#--<i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Pour commencer, vous choisissez des étiquettes pour caractériser votre action (domaine d'action, objectif de la ville durable, type de mission ingénierie le cas échéant)."></i>-->
        </a>
        <div class="dropdown-menu categorisation" aria-labelledby="dLabel">
            <#if action.typeFinancementIngenierie || action.typeFinancementIngenierieEtInvestissement || action.typeFinancementIngenierieEtPriseParticipation>
                <a class="dropdown-item loadComment" href="#ingenierie" role="tab" data-toggle="tab">Type de mission d'ingénierie</a>
            </#if>
            <a class="dropdown-item loadComment" href="#domaine" role="tab" data-toggle="tab">Domaines d'action</a>
            <a class="dropdown-item loadComment" href="#objectif" role="tab" data-toggle="tab">Objectifs de la ville durable</a>
        </div>
    </li>
    <#else >
    <li class="nav-item dropdown state-impossible">
        <a id="dLabel" class="nav-link disabled">
            <div class="m-r-10"><div class="tab-badge"><i class="fa fa-exclamation"></i></div></div>
            <div>Problème de chargement</div>
        </a>
    </li>
    </#if>
    <#if action?? && !action.typeFinancementIngenierie>
        <!-- DROPDOWN -->
        <#if action.etapeChoixIndicateur??>
        <li class="nav-item dropdown <@etapeCss action.etapeChoixIndicateur!/>">
            <a id="dLabel" class="nav-link dropdown-toggle d-print-block" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
                <div class="m-r-10"><div class="tab-badge"><@etapeBadge action.etapeChoixIndicateur!/></div></div>
                <div>Choix des indicateurs</div>
            <#--<i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Ensuite vous choisissez des indicateurs pour évaluer les retombées de votre action."></i>-->
            </a>
            <div class="dropdown-menu" aria-labelledby="dLabel">
                <a class="dropdown-item indicateurOngletRealisation" href="#indicateurOngletRealisation" role="tab" data-toggle="tab">Indicateurs de réalisation</a>
                <a class="dropdown-item indicateurOngletResultat" href="#indicateurOngletResultat" role="tab" data-toggle="tab">Indicateurs de résultat et d'impact</a>
            </div>
        </li>
        <#else >
        <li class="nav-item dropdown state-impossible">
            <a id="dLabel" class="nav-link disabled">
                <div class="m-r-10"><div class="tab-badge"><i class="fa fa-exclamation"></i></div></div>
                <div>Problème de chargement</div>
            </a>
        </li>
        </#if>
        <#if action.etapeEvaluationInnovation??>
        <li class="nav-item <@etapeCss action.etapeEvaluationInnovation!/>">
            <a class="nav-link" id="linkTabInnovation" href="#evaluation_innovation" role="tab" data-toggle="tab">
                <div class="m-r-10"><div class="tab-badge"><@etapeBadge action.etapeEvaluationInnovation!/></div></div>
                <div>Evaluation de l'innovation</div>
            <#--<i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Puis vous évaluez les natures et le niveau d'innovation de votre action."></i>-->
            </a>
        </li>
        <li class="nav-item <@etapeCss action.etapeRenseignementIndicateur!/>">
            <#if action?? && action.isEtapeIndicateurDone()>
                <a class="nav-link" id="linkTabMesure" href="#mesureIndicateurOnglet" role="tab" data-toggle="tab">
                    <div class="m-r-10"><div class="tab-badge"><@etapeBadge action.etapeRenseignementIndicateur!/></div></div>
                    <div>Renseignement des indicateurs</div>
                <#--<i class="m-r-10 fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Une fois vos indicateurs choisis, vous renseignez périodiquement des mesures et des cibles."></i>-->
                </a>
            <#else>
                <a class="nav-link disabled " id="linkTabMesure" href="#mesureIndicateurOnglet" role="tab" data-toggle="tab">
                    <div class="m-r-10"><div class="tab-badge"><@etapeBadge action.etapeRenseignementIndicateur!/></div></div>
                    <div>Renseignement des indicateurs</div>
                <#--<i class="m-r-10 fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Une fois vos indicateurs choisis, vous renseignez périodiquement des mesures et des cibles."></i>-->
                </a>
            </#if>
        </li>
        <#else >
        <li class="nav-item dropdown state-impossible">
            <a id="dLabel" class="nav-link disabled">
                <div class="m-r-10"><div class="tab-badge"><i class="fa fa-exclamation"></i></div></div>
                <div>Problème de chargement</div>
            </a>
        </li>
        </#if>
    </#if>
    <#if action.etapeEvaluationFacteur??>
    <#if action?? && (action.typeFinancementIngenierieEtInvestissement || action.typeFinancementIngenierieEtPriseParticipation)>
        <li class="nav-item dropdown <@etapeCss action.etapeEvaluationFacteur!/>">
            <a id="linkTabQuestionnaire" class="nav-link dropdown-toggle <@etapeChildren action.etapeEvaluationFacteur!/>" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
                <div class="m-r-10"><div class="tab-badge"><@etapeBadge action.etapeEvaluationFacteur!/></div></div>
                <div>Évaluation qualitative</div>
            <#--<i class="m-r-10 fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Lorsque que votre action est suffisamment avancée (ou terminée), vous remplissez un questionnaire final."></i>-->
            </a>
            <div class="dropdown-menu" aria-labelledby="dLabel">
                <a class="dropdown-item" href="#questionnaire_action_ingenierie" role="tab" data-toggle="tab">Évaluation de l’apport de l'étude</a>
                <a class="dropdown-item" href="#questionnaire_action_investissement" role="tab" data-toggle="tab">Évaluation des facteurs de succès</a>
            </div>
        </li>
    <#elseIf action?? && action.typeFinancementIngenierie>
        <li class="nav-item <@etapeCss action.etapeEvaluationFacteur!/>">
            <a class="nav-link <@etapeChildren action.etapeEvaluationFacteur!/>" id="linkTabQuestionnaire" href="#questionnaire_action_ingenierie" role="tab" data-toggle="tab">
                <div class="m-r-10"><div class="tab-badge"><@etapeBadge etape=action.etapeEvaluationFacteur! number=2/></div></div>
                <div>Évaluation de l’apport de l'étude</div>
            <#--<i class="m-r-10 fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Lorsque que votre action est suffisamment avancée (ou terminée), vous remplissez un questionnaire final."></i>-->
            </a>
        </li>
    <#elseIf action?? && action.typeFinancementInvestissementOuPriseParticipation>
        <li class="nav-item <@etapeCss action.etapeEvaluationFacteur!/>">
            <a class="nav-link <@etapeChildren action.etapeEvaluationFacteur!/>" id="linkTabQuestionnaire" href="#questionnaire_action_investissement" role="tab" data-toggle="tab">
                <div class="m-r-10"><div class="tab-badge"><@etapeBadge action.etapeEvaluationFacteur!/></div></div>
                <div>Evaluation des facteurs de succès</div>
            <#--<i class="m-r-10 fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Lorsque que votre action est suffisamment avancée (ou terminée), vous remplissez un questionnaire final."></i>-->
            </a>
        </li>
    </#if>
    <#else>
    <li class="nav-item dropdown state-impossible">
        <a id="dLabel" class="nav-link disabled">
            <div class="m-r-10"><div class="tab-badge"><i class="fa fa-exclamation"></i></div></div>
            <div>Problème de chargement</div>
        </a>
    </li>
    </#if>
    <#if action?? && action.typeFinancementIngenierie>
        <li class="nav-item">
            <a class="nav-link disabled" role="tab"></a>
        </li>
        <li class="nav-item">
            <a class="nav-link disabled" role="tab"></a>
        </li>
        <li class="nav-item">
            <a class="nav-link disabled" role="tab"></a>
        </li>
    </#if>
</ul>
