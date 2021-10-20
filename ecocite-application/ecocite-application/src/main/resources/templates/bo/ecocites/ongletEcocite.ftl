<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0..-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.-->
<#--You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.-->

<#-- @ftlvariable name="ecocite" type="com.efficacity.explorateurecocites.beans.biz.EcociteBean" -->

<#macro etapeCss etape><#if etape?? && etape.cssClass??>${etape.cssClass}</#if></#macro>

<#macro etapeChildren etape><#if etape?? && etape.cssClassLink??>${etape.cssClassLink}</#if></#macro>

<#macro etapeBadge etape><#if etape?? && etape.badgeContentEcocite??>${etape.badgeContentEcocite?noEsc}</#if></#macro>

<ul id="tab-list" class="nav nav-tabs col-lg-12 listOngletEcocite" role="tablist">
    <li class="nav-item">
        <a class="nav-link active" href="#presentation" role="tab" data-toggle="tab">Présentation</a>
    </li>
    <#if ecocite.etapeCaraterisation??>
    <li class="nav-item  <@etapeCss ecocite.etapeCaraterisation!/>">
        <a class="nav-link" href="#categorisation" role="tab" data-toggle="tab">
            <div class="m-r-10"><div class="tab-badge"><@etapeBadge  ecocite.etapeCaraterisation!/></div></div>
            <div>Caractérisation de l'ÉcoCité</div>
            <#--<i class="m-r-10 fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Pour commencer, vous choisissez des étiquettes pour caractériser les objectifs ville durable de votre ÉcoCité."></i>-->
        </a>
    </li>
    <#else>
    <li class="nav-item dropdown state-impossible">
        <a id="dLabel" class="nav-link disabled">
            <div class="m-r-10"><div class="tab-badge"><i class="fa fa-exclamation"></i></div></div>
            <div>Problème de chargement</div>
        </a>
    </li>
    </#if>
    <#if ecocite.etapeChoixIndicateur??>
    <li class="nav-item dropdown <@etapeCss ecocite.etapeChoixIndicateur!/>">
        <a id="dLabel" class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
            <div class="m-r-10"><div class="tab-badge"><@etapeBadge  ecocite.etapeChoixIndicateur!/></div></div>
            <div>Choix des indicateurs</div>
            <#--<i class=" m-l-10 m-r-10 fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Ensuite vous choisissez des indicateurs pour évaluer l'état du votre territoire et son évolution."></i>-->
        </a>
        <div class="dropdown-menu" aria-labelledby="dLabel">
            <a class="dropdown-item indicateurOngletRealisation" href="#indicateurOngletRealisation" role="tab" data-toggle="tab">Indicateurs de réalisation</a>
            <a class="dropdown-item indicateurOngletResultat" href="#indicateurOngletResultat" role="tab" data-toggle="tab">Indicateurs de résultat et d'impact</a>
        </div>
    </li>
    <#else>
    <li class="nav-item dropdown state-impossible">
        <a id="dLabel" class="nav-link disabled">
            <div class="m-r-10"><div class="tab-badge"><i class="fa fa-exclamation"></i></div></div>
            <div>Problème de chargement</div>
        </a>
    </li>
    </#if>
    <#if ecocite.etapeRenseignementIndicateur??>
    <li class="nav-item <@etapeCss ecocite.etapeRenseignementIndicateur!/>">
        <#if ecocite?? && ecocite.isEtapeIndicateurDone()>
            <a class="nav-link" id="linkTabMesure" href="#mesureIndicateurOnglet" role="tab" data-toggle="tab">
                <div class="m-r-10"><div class="tab-badge"><@etapeBadge  ecocite.etapeRenseignementIndicateur!/></div></div>
                <div>Renseignement des indicateurs</div>
                <#--<i class="m-r-10 fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Une fois vos indicateurs choisis, vous renseignez périodiquement des mesures et des cibles."></i>-->
            </a>
        <#else>
            <a class="nav-link disabled" id="linkTabMesure" href="#mesureIndicateurOnglet" role="tab" data-toggle="tab">
                <div class="m-r-10"><div class="tab-badge"><@etapeBadge  ecocite.etapeRenseignementIndicateur!/></div></div>
                <div>Renseignement des indicateurs</div>
                <#--<i class="m-r-10 fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Une fois vos indicateurs choisis, vous renseignez périodiquement des mesures et des cibles."></i>-->
            </a>
        </#if>
    </li>
    <#else>
     <li class="nav-item dropdown state-impossible">
         <a id="dLabel" class="nav-link disabled">
             <div class="m-r-10"><div class="tab-badge"><i class="fa fa-exclamation"></i></div></div>
             <div>Problème de chargement</div>
         </a>
     </li>
    </#if>
    <li class="nav-item <@etapeCss ecocite.etapeEvaluationFacteur!/>">
        <a class="nav-link" href="#questionnaire_ecocite" role="tab" data-toggle="tab">
            <div class="m-r-10"><div class="tab-badge"><@etapeBadge  ecocite.etapeEvaluationFacteur!/></div></div>
            <div>Impact du programme</div>
            <#--<i class="m-l-10 m-r-10 fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Lorsque que les actions de votre ÉcoCité sont suffisamment avancées, vous remplissez un questionnaire final."></i>-->
        </a>
    </li>
</ul>
