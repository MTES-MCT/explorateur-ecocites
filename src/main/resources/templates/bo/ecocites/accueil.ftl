<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<div class="row">
    <div class="col-lg-12 head">
        <h2 class=" ft-700">Mes ÉcoCités</h2>
    </div>
    <#if ecocites?? >
        <#list ecocites as ecocite>
            <div class="col-lg-6 mb-4">
                <div class="paper bg-grey-light">
                    <div class="head bg-light d-flex mb-4 pb-4">
                        <div class="row">
                            <div class="col-lg-12 mb-2">
                                <div class="title ft-s-16 ft-700 c-black">${ecocite.nom!}</div>
                            </div>
                        </div>
                    </div>

                    <div class="body ">
                        <ul class="timeline">
                            <#list ecocite.listEtapesTriees as etape>
                                <#--une étape sur deux, mettre timeline-inverted dans le li pour changer de côté de la time line-->
                                <#if etape?itemParityCap == 'Even'>
                                    <#assign cssClassSide = "timeline-inverted"/>
                                <#else>
                                    <#assign cssClassSide = ""/>
                                </#if>

                                <#switch ecocite.getStatusEnumEtape(etape).code>
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
                                        <#assign cssClassFontColor="text-muted"/>
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

                                <li class="${cssClassSide} ${cssClassBackgroundColor}">
                                    <div class="timeline-badge ${cssClassIcone}">${iconeToPrint}</div>
                                    <#if ecocite.getStatusEnumEtape(etape)?? && ecocite.getStatusEnumEtape(etape).code != "impossible">
                                        <a class="timeline-panel" href="/bo/ecocites/edition/${ecocite.id!?c}/onglet/${etape.etapeEnumEcocite.code}">
                                    <#else>
                                        <a class="timeline-panel">
                                    </#if>
                                        <div class="timeline-heading">
                                            <h4 class="timeline-title ft-700 ft-s-13">${etape.etapeEnumEcocite.libelle}</h4>
                                            <p><small class="${cssClassFontColor}"><i class="glyphicon glyphicon-time"></i>${ecocite.getStatusEnumEtape(etape).libelle}</small></p>
                                            <p><small class="text-muted">${etape.lastMesure}</small></p>
                                        </div>
                                    </a>
                                </li>
                            </#list>
                        </ul>
                    </div>
                    <div class="footer">
                        <a class="btn btn-lg btn-success btn-block" href="/bo/ecocites/edition/${ecocite.id!?c}">Voir la fiche de mon ÉcoCité</a>
                    </div>
                </div>
            </div>
        </#list>
    </#if>
</div>
