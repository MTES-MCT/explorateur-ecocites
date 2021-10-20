<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0..-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.-->
<#--You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.-->

<div class="row">
    <#if actions?? && actions?hasContent>
        <div class="col-lg-12 head">
            <h2 class=" ft-700">Mes actions</h2>
        </div>
        <#list actions as action>
            <div class="col-lg-6 mb-4">
                <div class="paper bg-grey-light">
                    <div class="head bg-light mb-4 pb-4">
                        <div class="row">
                            <div class="col-lg-8 mb-2">
                                <div class="title ft-s-16 ft-700 c-black">${action.nomPublic!}</div>
                            </div>
                            <div class="col-lg-4">
                                <div class="type pull-right text-center">
                                    <div class="ft-s-10 ft-900 text-uppercase mb-2" style="width: max-content">Type de financement</div>
                                    <#if action.typeFinancementEnum??>
                                        <p class="badge badge-primary">${action.typeFinancementEnum.libelle}</p>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="body ">
                        <ul class="timeline">
                            <#list action.listEtapesTriees as etape>
                                <#--une étape sur deux, mettre timeline-inverted dans le li pour changer de côté de la time line-->
                                <#if etape?itemParityCap == 'Even'>
                                    <#assign cssClassSide = "timeline-inverted"/>
                                <#else>
                                    <#assign cssClassSide = ""/>
                                </#if>

                                <#switch action.getStatusEnumEtape(etape).code>
                                    <#--Info à renseigner-->
                                    <#case "vide">
                                        <#assign cssClassIcone="danger"/>
                                        <#assign cssClassBackgroundColor="state-active"/>
                                        <#assign cssClassFontColor=""/>
                                        <#assign iconeToPrint="<span>${etape_index + 1}</span>"/>
                                        <#break>
                                    <#--Impossible de renseigner des infos-->
                                    <#case "impossible">
                                        <#assign cssClassIcone="info"/>
                                        <#assign cssClassBackgroundColor="state-impossible"/>
                                        <#assign cssClassFontColor="text-muted"/>
                                        <#assign iconeToPrint="<span>${etape_index + 1}</span>"/>
                                        <#break>
                                    <#--Envoyé, en attente de validation-->
                                    <#case "envoyer">
                                        <#assign cssClassIcone="warning"/>
                                        <#assign cssClassBackgroundColor="state-send"/>
                                        <#assign cssClassFontColor=""/>
                                        <#assign iconeToPrint="<span>${etape_index + 1}</span>"/>
                                        <#break>
                                    <#--Validé par un référent-->
                                    <#case "valider">
                                        <#assign cssClassIcone="warning"/>
                                        <#assign cssClassBackgroundColor="state-valid"/>
                                        <#assign cssClassFontColor=""/>
                                        <#assign iconeToPrint="<i class='fa fa-check ft-s-16'></i>"/>
                                        <#break>
                                    <#default>
                                        <#assign cssClassIcone="info"/>
                                        <#assign cssClassBackgroundColor="state-impossible"/>
                                        <#assign cssClassFontColor="text-muted"/>
                                        <#assign iconeToPrint="<span>${etape_index + 1}</span>"/>
                                </#switch>
                                <#--permet deremonteter la derniere bulle du workflow pour nu questionnaire d'action Investissement-->
                                <#--<#if nomEtapeQuestionnaire?? && etape.etapeEnumAction.libelle == nomEtapeQuestionnaire && action?? && action.typeFinancementInvestissement>style="margin-top: -80px"</#if>-->
                                <li class="${cssClassSide} ${cssClassBackgroundColor}">
                                    <div class="timeline-badge ${cssClassIcone}">${iconeToPrint?noEsc}</div>
                                    <#if action.getStatusEnumEtape(etape)?? && action.getStatusEnumEtape(etape).code != "impossible">
                                        <a class="timeline-panel" href="/bo/actions/edition/${action.id!?c}/onglet/${etape.etapeEnumAction.code}">
                                    <#else>
                                        <a class="timeline-panel">
                                    </#if>
                                        <div class="timeline-heading">
                                            <div class="row">
                                                <div class="col-lg-9 col-md-9">
                                                 <#if nomEtapeQuestionnaire?? && etape.etapeEnumAction.libelle == nomEtapeQuestionnaire && action?? && action.typeFinancementIngenierie>
                                                    <h4 class="timeline-title ft-700 ft-s-13">Evaluation de l'apport de l'étude</h4>
                                                 <#elseIf nomEtapeQuestionnaire?? && etape.etapeEnumAction.libelle == nomEtapeQuestionnaire && action?? && action.typeFinancementIngenierieEtInvestissement>
                                                    <h4 class="timeline-title ft-700 ft-s-13">Evaluation qualitative</h4>
                                                 <#else>
                                                    <h4 class="timeline-title ft-700 ft-s-13"><#if etape?? && etape.etapeEnumAction??>${etape.etapeEnumAction.libelle!}</#if></h4>
                                                 </#if>
                                                 </div>
                                                <div class="col-lg-3 col-md-3">
                                                    <i class="fa fa-info-circle"
                                                       <#if etape?? && etape.getTexteInfoBulle??>
                                                       data-toggle="tooltip" data-placement="top"
                                                       title="${(etape.texteInfoBulle!"")}"</#if>
                                                        >
                                                    </i>
                                                </div>
                                            </div>
                                            <p><small class="${cssClassFontColor}"><i class="glyphicon glyphicon-time"></i>${action.getStatusEnumEtape(etape).libelle!}</small></p>
                                            <p><small class="text-muted fs-10">${etape.lastMesure!""}</small></p>
                                        </div>
                                    </a>
                                </li>
                            </#list>
                        </ul>
                    </div>
                    <div class="footer">
                        <a class="btn btn-lg btn-success btn-block" href="/bo/actions/edition/${action.id!?c}">Voir la fiche de mon action</a>
                    </div>
                </div>
            </div>
        </#list>
    <#else>
        <div class="col-lg-12 head col-h-center">
            <h2 class=" ft-700">Vous êtes responsable d'aucune action.</h2>
        </div>
    </#if>
</div>
