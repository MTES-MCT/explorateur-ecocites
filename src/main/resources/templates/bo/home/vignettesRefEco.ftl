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
    <#--VIGNETTES PRINCIPALES-->
<#if selectedEcocite??>
<div class="row">
    <div class="col-lg-6 mb-4" id="workflow-${selectedEcocite.id!}">
        <div class="paper bg-grey-light" style="min-height: 500px">
            <div class="head bg-light d-flex mb-4 pb-4">
                <div class="row">
                    <div class="col-lg-12 mb-2">
                        <div class="title ft-s-16 ft-700 c-black">${selectedEcocite.nom!}</div>
                    </div>
                </div>
            </div>

            <div class="body">
                <#if selectedEcocite.listEtapesTriees?? && selectedEcocite.listEtapesTriees?hasContent >
                <ul class="timeline">
                    <#list selectedEcocite.listEtapesTriees as etape>
                    <#--une étape sur deux, mettre timeline-inverted dans le li pour changer de côté de la time line-->
                        <#if etape?itemParityCap == 'Even'>
                            <#assign cssClassSide = "timeline-inverted"/>
                        <#else>
                            <#assign cssClassSide = ""/>
                        </#if>

                        <#switch selectedEcocite.getStatusEnumEtape(etape).code>
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
                            <div class="timeline-badge ${cssClassIcone}">${iconeToPrint?noEsc}</div>
                            <#if selectedEcocite.getStatusEnumEtape(etape)?? && selectedEcocite.getStatusEnumEtape(etape).code != "impossible">
                            <a class="timeline-panel" href="/bo/ecocites/edition/${selectedEcocite.id!?c}/onglet/${etape.etapeEnumEcocite.code}">
                            <#else>
                            <a class="timeline-panel">
                            </#if>
                            <div class="timeline-heading">
                                <div class="row">
                                    <div class="col-lg-10">
                                        <h4 class="timeline-title ft-700 ft-s-13"><#if etape?? && etape.etapeEnumEcocite??>${etape.etapeEnumEcocite.libelle!}</#if></h4>
                                    </div>
                                    <div class="col-lg-1">
                                        <i class="m-r-10 fa fa-info-circle"
                                               <#if etape?? && etape.getTexteInfoBulle??>
                                               data-toggle="tooltip" data-placement="top"
                                               title="${(etape.texteInfoBulle!"")}"</#if>
                                        >
                                        </i>
                                    </div>
                                </div>
                                <p><small class="${cssClassFontColor}"><i class="glyphicon glyphicon-time"></i>${selectedEcocite.getStatusEnumEtape(etape).libelle!}</small></p>
                                <p><small class="text-muted">${etape.lastMesure!""}</small></p>
                            </div>
                            </a>
                        </li>
                    </#list>
                </ul>
                <#else >
                    <div align="center" style="height:300px; color:grey" > Le chargement des étapes n'a pas été effectué. </div>
                </#if>
            </div>
            <div class="footer">
                <a class="btn btn-lg btn-success btn-block" href="/bo/ecocites/edition/${selectedEcocite.id!?c}">Voir la fiche de mon ÉcoCité</a>
            </div>
        </div>
     </div>
    <#--VIGNETTE PRINCIPAL - TABLEAU DE BORD-->
    <div class="col-lg-6 mb-4" id="vignettePrincipal" >
        <div class="paper bg-grey-light vignettePrincipale vignettePrincipalAbsolute">
        <#--height:490px-->
            <a id="exportActionsCSV_link" href="/bo/referent/ecocite/exportActionsCSV/${selectedEcocite.id!?c}" class="link-top-left" download="actions.zip">Exporter les données<span class="fa fa-download fa-1x p-l-10"></a>
            <span class="fa fa-lightbulb-o fa-3x icon-top-right c-grey-mid "></span>
            <div class="part1" style="height:65%" align="center" onclick="$('#formFiltreActionsFromEcocite').submit()">
                <div class="horizontalCenter">
                    <span class="elementPrincipal">${nbrActionsFromEcocites}</span>
                    <br/>
                    <span class="elementSecond">Actions dans mon ÉcoCité</span>
                </div>
            </div>
            <div class="part2" align="center" style="height:35%">
                <div class="col-lg-6 part2-1">
                    <div class="horizontalCenter">
                        <span class="elementPrincipal">${actionsIng}*</span>
                        <br/>
                        <span class="elementSecond" >Actions d'ingénierie</span>
                        <br/>
                    <#--<span class="elementThird">*Une action peutêtre à la fois une action d'ingénieirie et d'investissement</span>-->
                    </div>
                    <span class="elementThird">*Une action peut être à la fois une action d'ingénierie et d'investissement</span>
                </div>
                <div class="col-lg-6 part2-2">
                    <div class="horizontalCenter">
                        <span class="elementPrincipal">${actionsInv}*</span>
                        <br/>
                        <span class="elementSecond"> Actions d'investissements ou de prise de participation</span>
                    </div>
                    <span class="elementThird">*Une action peut être à la fois une action d'ingénierie et d'investissement</span>
                </div>
            </div>
        </div>
    </div>
</div>
<#--VIGNETTES BLEUES ET VERTES-->
<div class="row">
    <div class="col-lg-6 mb-4 vignetteBleuVert ">
        <div class=" paper head d-flex">
            <div class="row">
                <div class="col-lg-12 mb-2">
                    <div class="title ft-s-18 ft-700 c-black">Caractérisation des actions*</div>
                    <span class="m-l-10 fs-10">*Cette étape concerne toutes les actions</span>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-6 mb-4">
                <div class="paper vignetteSecond" style="background-color: #4688f1" align="center" onclick="$('#formFiltreCategorisationAValider').submit()">
                    <a href="#"><span class="fa fa-pencil fa-3x icon-top-right icon-pencil-blue"></span></a>
                    <div class="horizontalCenter">
                        <span class="elementPrincipal">${actionAValider}</span>
                        <br/>
                        <span class="fs-18">A Valider</span>
                        <form id="formFiltreCategorisationAValider" method="get" action="/bo/actions">
                            <input type="hidden" name="filtres['caracterisation']" value="envoyer" class="form-control"/>
                            <input type="hidden" name="filtres['ecocite']" value="${selectedEcocite.id!}" class="form-control"/>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-lg-6 mb-4 ">
                <div class="paper vignetteSecond" style="background-color: #83b93a" align="center"  onclick="$('#formFiltreCategorisationValide').submit()">
                    <a href="#"><span class="fa fa-check-circle-o fa-3x icon-top-right icon-check-green "></span></a>
                    <div class="horizontalCenter">
                        <span class="elementPrincipal">${actionValide}</span>
                        <br/>
                        <span class="fs-18">Validées</span>
                        <form id="formFiltreCategorisationValide" method="get" action="/bo/actions">
                            <input type="hidden" name="filtres['caracterisation']" value="valider" class="form-control"/>
                            <input type="hidden" name="filtres['ecocite']" value="${selectedEcocite.id!}" class="form-control"/>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-lg-6 mb-4 vignetteBleuVert " >
        <div class=" paper head d-flex">
            <div class="row">
                <div class="col-lg-12 mb-2">
                    <div class="title ft-s-18 ft-700 c-black">Choix des Indicateurs*</div>
                    <span class="m-l-10 fs-10">*Cette étape ne concerne ques les actions d'investissement</span>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-6 mb-4">
                <div class="paper vignetteSecond " style="background-color: #4688f1" align="center" onclick="$('#formFiltreChoixIndicateurAValider').submit()">
                    <a href="#"><span class="fa fa-pencil fa-3x icon-top-right icon-pencil-blue"></span></a>
                    <div class="horizontalCenter">
                        <span class="elementPrincipal">${indicAValider}</span>
                        <br/>
                        <span class="fs-18">A Valider</span>
                    </div>
                    <form id="formFiltreChoixIndicateurAValider" method="get" action="/bo/actions">
                        <input type="hidden" name="filtres['indicateurChoix']" value="envoyer" class="form-control"/>
                        <input type="hidden" name="filtres['ecocite']" value="${selectedEcocite.id!}" class="form-control"/>
                    </form>
                </div>
            </div>
            <div class="col-lg-6 mb-4">
                <div class="paper vignetteSecond" style="background-color: #83b93a" align="center" onclick="$('#formFiltreChoixIndicateurValide').submit()">
                    <a href="#"><span class="fa fa-check-circle-o fa-3x icon-top-right icon-check-green "></span></a>
                    <div class="horizontalCenter">
                        <span class="elementPrincipal">${indicValide}</span>
                        <br/>
                        <span class="fs-18">Validées</span>
                    </div>
                    <form id="formFiltreChoixIndicateurValide" method="get" action="/bo/actions">
                        <input type="hidden" name="filtres['indicateurChoix']" value="valider" class="form-control"/>
                        <input type="hidden" name="filtres['ecocite']" value="${selectedEcocite.id!}" class="form-control"/>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</#if>
</html>
