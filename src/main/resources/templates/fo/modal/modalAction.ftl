<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<#-- @ftlvariable name="action" type="com.efficacity.explorateurecocites.beans.biz.ActionBean" -->
<#-- @ftlvariable name="axe" type="com.efficacity.explorateurecocites.beans.model.Axe" -->
<#-- @ftlvariable name="ecocite" type="com.efficacity.explorateurecocites.beans.biz.EcociteBean" -->
<#-- @ftlvariable name="images" type="java.util.List<com.efficacity.explorateurecocites.beans.biz.MediaBean>" -->

<#assign hasPerimetre = perimetre?? && perimetre.originalName?hasContent && perimetre.title?hasContent && perimetre.url?? && perimetre.id??>


<div class="modal-dialog">
    <div class="modal-content modal-action">
        <div class="modal-header">
            <div class="modal-header-inner row">
                <div class="col-md-10 col-xs-8">
                    <h4 class="modal-title">${action.nomPublic!}</h4>
                </div>
                <div class="col-md-2 col-xs-4 d-print-flex">
                    <button type="button" class="close" onclick="hideModal()"><span class="sr-only">Close</span></button>
                    <button id="back-history" class="back" onclick="window.history.back()"></button>
                </div>
            </div>
        </div>

        <div class="modal-sub-header">
            <ul id="panel-navigation" class="nav nav-pills nav-pills--modal nav-pills--modal---ecocite">
                <li class="col-md-3 active"><a href="#presentationOnglet" data-toggle="pill" >Présentation</a></li>
                <li class="col-md-3 "><a href="#mapActionOnglet" data-toggle="pill" >Carte</a></li>
                <li class="col-md-3 "><a href="#evaluationOnglet" data-toggle="pill" >Evaluation</a></li>
                <li class="col-md-3 "><a href="#contactOnglet" data-toggle="pill" >Contact</a></li>
            </ul>
        </div>

        <div class="modal-body" id="modal-body">
            <div class="tab-content">
                <div id="presentationOnglet" class="tab-pane fade in active">
                    <div class="col-md-8">
                    <#if images?? && images?hasContent>
                        <div id="carousel-action" class="carousel-ecocite carousel slide" data-ride="carousel">
                            <ol class="carousel-indicators">
                                <#list images as image>
                                    <li data-target="#carousel-action" data-slide-to="${image?index}" <#if image?isFirst>class="active"</#if>></li>
                                </#list>
                            </ol>
                            <div class="carousel-inner" role="listbox">
                                <#list images as image>
                                    <#--<#if image.isAutorisationSiteEE()?? && !image.isAutorisationSiteEE()>-->
                                    <div class="item  <#if image?isFirst>active</#if>">
                                        <img src="${image.href!}" alt="${image.title!}" class="img-align-center">
                                        <#if image.libelle?? >
                                            <p align="center" class="img-legend-action-fo">${image.title!}</p>
                                        </#if>
                                    </div>
                                    <#--</#if>-->
                                </#list>
                            </div>

                            <a class="left carousel-control" href="#carousel-action" role="button" data-slide="prev">
                                <span class="glyphicon carousel-left-arrow" aria-hidden="true"></span>
                                <span class="sr-only">Previous</span>
                            </a>
                            <a class="right carousel-control" href="#carousel-action" role="button" data-slide="next">
                                <span class="glyphicon carousel-right-arrow" aria-hidden="true"></span>
                                <span class="sr-only">Next</span>
                            </a>
                        </div>
                    <#else>
                        <div id="carousel-action">
                            <div class="carousel-inner" >
                                <div class="item active">
                                    <img src="/img/pas_dimage_disponible.jpg" alt="Pas d'image disponible" class="img-align-center thumbnail">
                                </div>
                            </div>
                        </div>
                        <#--<img src="/img/pas_dimage_disponible.jpg" alt="Pas d'image disponible" class="img-thumbnail">-->
                    </#if>
                    </div>
                    <div class="col-md-4 fs-14">
                        <div align="left">
                            <div class="col-md-12">
                                <p>
                                    <span class="fs-14">ÉcoCité</span><br/>
                                    <#if ecocite??>
                                        <span class="fs-14">
                                            <strong>${ecocite.nom!}</strong>
                                            <i onclick="openModalEcocite('${ecocite.id!?c}')" class="glass"></i>
                                        </span>
                                    </#if>
                                </p>
                            </div>
                            <div class="col-md-12">
                                <p>
                                    <span class="fs-14">Axe d'intervention principal</span><br/>
                                    <#if axe??>
                                        <span class="fs-14"> <strong>${axe.libelle}</strong><i onclick="openModalShowAxis({ axePrincipale: '${axe.id!?c}'})" class="glass"></i></span>
                                    </#if>
                                </p>
                            </div>
                            <div class="col-md-12">
                                <p>
                                    <span class="fs-14">Maîtrise d'ouvrage</span><br/>
                                <#if maitriseOuvrage??>
                                    <#list maitriseOuvrage as e>
                                        <strong><span class="fs-14">${e!}</span></strong>
                                    </#list>
                                </#if>
                                </p>
                            </div>
                            <div class="col-md-12">
                                <p>
                                    <span class="fs-14">Type de financement</span><br/>
                                    <#if action.typeFinancement??>
                                        <span class="fs-14"> <strong>${action.typeFinancementEnum.libelle!}</strong></span>
                                    </#if>
                                </p>
                            </div>
                            <div class="col-md-12">
                                <p>
                                    <span class="fs-14">Etat d'avancement</span><br/>
                                    <#if action.etatAvancementEnum??>
                                        <span class="fs-14"> <strong>${action.etatAvancementEnum.libelle!}</strong></span>
                                    </#if>
                                </p>
                            </div>
                            <div class="col-md-12">
                                <p>
                                    <span class="fs-14">Contact</span><br/>
                                    <#if contacts??>
                                        <#list contacts as e>
                                            <strong><span class="fs-14">${e.prenom!} ${e.nom!}</span></strong>
                                        </#list>
                                    </#if>
                                </p>
                            </div>
                            <div class="col-md-12">
                                <p>
                                    <span class="fs-14">Date de dernière modification</span><br/>
                                    <strong><span class="fs-14">${action.dateModificationAffichable}</span></strong>
                                </p>
                            </div>
                         </div>
                    </div>
                    <div class="col-md-12 p-t-20">
                        <div align="justify" class="fs-14">
                            <#if action.description??>
                                ${action.description?noEsc}
                            </#if>
                        </div>
                    </div>
                    <br/>
                    <div class="col-md-6 p-t-20">
                        <#if action.documents?? && action.documents?hasContent >
                            <span>Document :</span><br/>
                            <#list action.documents as document>
                                <a class="underline" download="${document.originalName}" href="${document.url}"><strong>${document.title}</strong></a><br/>
                            </#list>
                        </#if>
                    </div>
                    <br/>
                    <br/>
                    <div class="col-md-6"></div>
                    <div class="col-md-12 p-t-20">
                        <#if action.lien?? && action.lien?hasContent>
                            <span>Pourr en savoir plus :</span><br/>
                            <a target="_blank" class="underline" href="${action.lien}"><strong>${action.lien}</strong></a>
                        </#if>
                    </div>
                </div>
                <div id="mapActionOnglet" class="tab-pane fade">
                    <#if hasPerimetre || (action.latitude?? && action.latitude != "" && action.longitude?? && action.longitude != "")>
                        <div id="loading large-map" style="height: 100%; width: 100%;">
                            <div id="mapActionPlaceHolder" class="map-placeholder"><p>La carte est en chargement...</p></div>
                            <div id="mapAction" class="map-component"></div>
                        </div>
                    <#else>
                        <div class="row text-center">
                            <h4>Pas de carte disponible</h4>
                        </div>
                    </#if>
                </div>
                <div id="evaluationOnglet" class="tab-pane fade p-10">
                    <#if action?? && action.etiquettesList??>
                        <div class="row m-b-20">
                            <div class="col-lg-6">
                                <h4 class="w-black">Domaines d'action</h4>
                                <#if action.etapes?? && action.etapes.categorisationValide()>
                                    <#if action.etiquettesList.domainesEtiquettes?? && action.etiquettesList.domainesEtiquettes?hasContent>
                                        <#if action.etiquettesList.domainesEtiquettes["1"]?? && action.etiquettesList.domainesEtiquettes["1"]?hasContent>
                                            <div class="p-l-10 p-r-10">
                                                <h5><u>Principal</u></h5>
                                                <#list action.etiquettesList.domainesEtiquettes["1"] as etiquette>
                                                    <div class="p-l-10">
                                                        <#if etiquette??>
                                                            <#include "../components/etiquette.ftl" />
                                                        </#if>
                                                    </div>
                                                </#list>
                                            </div>
                                        </#if>
                                    </#if>
                                    <#if action.etiquettesList.domainesEtiquettes?? && action.etiquettesList.domainesEtiquettes?hasContent>
                                        <#if action.etiquettesList.domainesEtiquettes["2"]?? && action.etiquettesList.domainesEtiquettes["2"]?hasContent>
                                            <div class="p-l-10 p-r-10">
                                                <h5><u>Secondaires</u></h5>
                                                <#list action.etiquettesList.domainesEtiquettes["2"] as etiquette>
                                                    <div class="p-l-10 p-t-10"">
                                                    <#if etiquette??>
                                                        <#include "../components/etiquette.ftl" />
                                                    </#if>
                                                    </div>
                                                </#list>
                                            </div>
                                        </#if>
                                    </#if>
                                <#else>
                                    <p><i>Evaluation en cours</i></p>
                                </#if>
                            </div>
                            <div class="col-lg-6">
                                <h4 class="w-black">Objectifs de la ville durable</h4>
                                <#if action.etapes?? && action.etapes.categorisationValide()>
                                    <#if action.etiquettesList.objectifsEtiquettes?? && action.etiquettesList.objectifsEtiquettes?hasContent>
                                        <#if action.etiquettesList.objectifsEtiquettes["1"]?? && action.etiquettesList.objectifsEtiquettes["1"]?hasContent>
                                        <div class="p-l-10 p-r-10">
                                            <h5><u>Majeurs</u></h5>
                                            <#list action.etiquettesList.objectifsEtiquettes["1"] as etiquette>
                                            <div class="p-l-10 p-t-10">
                                                <#if etiquette??>
                                                    <#include "../components/etiquette.ftl" />
                                                </#if>
                                            </div>
                                            </#list>
                                        </div>
                                        </#if>
                                    </#if>
                                    <#if action.etiquettesList.objectifsEtiquettes?? && action.etiquettesList.objectifsEtiquettes?hasContent>
                                        <#if action.etiquettesList.objectifsEtiquettes["2"]?? && action.etiquettesList.objectifsEtiquettes["2"]?hasContent>
                                        <div class="p-l-10 p-r-10">
                                            <h5><u>Modérés</u></h5>
                                            <#list action.etiquettesList.objectifsEtiquettes["2"] as etiquette>
                                            <div class="p-l-10 p-t-10">
                                                <#if etiquette??>
                                                    <#include "../components/etiquette.ftl" />
                                                </#if>
                                            </div>
                                            </#list>
                                        </div>
                                        </#if>
                                    </#if>
                                    <#if action.etiquettesList.objectifsEtiquettes?? && action.etiquettesList.objectifsEtiquettes?hasContent>
                                        <#if action.etiquettesList.objectifsEtiquettes["3"]?? && action.etiquettesList.objectifsEtiquettes["3"]?hasContent>
                                        <div class="p-l-10 p-r-10">
                                            <h5><u>Mineurs</u></h5>
                                            <#list action.etiquettesList.objectifsEtiquettes["3"] as etiquette>
                                            <div class="p-l-10">
                                                <#if etiquette??>
                                                    <#include "../components/etiquette.ftl" />
                                                </#if>
                                            </div>
                                            </#list>
                                        </div>
                                        </#if>
                                    </#if>
                                <#else>
                                    <p><i>Evaluation en cours</i></p>
                                </#if>
                            </div>
                        </div>
                        <#if action?? && (action.typeFinancementIngenierie || action.typeFinancementIngenierieEtInvestissement || action.typeFinancementIngenierieEtPriseParticipation)>
                        <div class="row">
                            <div class="col-lg-6">
                                <h4 class="w-black">Type de mission d'ingénierie</h4>
                                <#if action.etapes?? && action.etapes.categorisationValide()>
                                    <#if action.etiquettesList.ingenieriesEtiquettes?? && action.etiquettesList.ingenieriesEtiquettes?hasContent>
                                        <#if action.etiquettesList.ingenieriesEtiquettes["1"]?? && action.etiquettesList.ingenieriesEtiquettes["1"]?hasContent>
                                            <#list action.etiquettesList.ingenieriesEtiquettes["1"] as etiquette>
                                                <#if etiquette??>
                                                    <#include "../components/etiquette.ftl" />
                                                </#if>
                                            </#list>
                                        </#if>
                                    </#if>
                                <#else>
                                    <p><i>Evaluation en cours</i></p>
                                </#if>
                            </div>
                        </div>
                        <br/>
                        </#if>
                    </#if>
                    <#if action?? && !action.typeFinancementIngenierie>
                    <div class="row m-b-20">
                        <div class="col-lg-6">
                            <h4 class="w-black">Indicateurs de réalisation</h4>
                            <#if action.etapes?? && action.etapes.indicateurValide()>
                                <#if action.indicateursBean?? && action.indicateursBean.realisationIndicateurs?? && action.indicateursBean.realisationIndicateurs?hasContent>
                                    <ul>
                                        <#list action.indicateursBean.realisationIndicateurs as realisationIndic>
                                            <li>${realisationIndic.nom!}</li>
                                        </#list>
                                    </ul>
                                </#if>
                            <#else>
                                <p><i>Evaluation en cours</i></p>
                            </#if>
                        </div>
                        <div class="col-lg-6">
                            <h4 class="w-black">Indicateurs de résultat et d'impact</h4>
                            <#if action.etapes?? && action.etapes.indicateurValide()>
                                <#if action.indicateursBean?? && action.indicateursBean.resultatIndicateurs?? && action.indicateursBean.resultatIndicateurs?hasContent>
                                    <ul>
                                        <#list action.indicateursBean.resultatIndicateurs as realisationIndic>
                                            <li>${realisationIndic.nom!}</li>
                                        </#list>
                                    </ul>
                                </#if>
                                <#if action.indicateursBean?? && action.indicateursBean.impactIndicateurs?? && action.indicateursBean.impactIndicateurs?hasContent>
                                    <ul>
                                        <#list action.indicateursBean.impactIndicateurs as realisationIndic>
                                            <li>${realisationIndic.nom!}</li>
                                        </#list>
                                    </ul>
                                </#if>
                            <#else>
                                <p><i>Evaluation en cours</i></p>
                            </#if>
                        </div>
                    </div>
                    <div class="row m-b-20">
                        <div class="col-lg-6">
                            <h4 class="w-black">Nature et niveau d'innovation</h4>
                            <#if action.etapes?? && action.etapes.evaluationValide()>
                                <#if action.questions?? && action.questions?hasContent>
                                    <ul>
                                        <#list action.questions as question>
                                            <li>
                                                <p>
                                                    ${question.question.titre}:
                                                    <#if question.reponse?? && question.reponse.niveauEnum??>
                                                        ${question.reponse.niveauEnum.title}
                                                        <#if question.reponse.niveauEnum.description??>
                                                            <span class="glyphicon glyphicon-info-sign" data-toggle="tooltip" data-placement="top" title="${question.reponse.niveauEnum.description}"></span>
                                                        </#if>
                                                    </#if>
                                                </p>
                                            </li>
                                        </#list>
                                    </ul>
                                </#if>
                            <#else>
                                <p><i>Evaluation en cours</i></p>
                            </#if>
                        </div>
                        <div class="col-lg-6">
                            <h4 class="w-black">Niveau d'innovation global</h4>

                            <#if action.etapes?? && action.etapes.evaluationValide()>
                                <#if action.evaluationNiveauGlobalEnum?? && action.evaluationNiveauGlobalEnum?hasContent>
                                    <ul>
                                        <li>
                                            <p>
                                                ${action.evaluationNiveauGlobalEnum.content}
                                            </p>
                                        </li>
                                    </ul>
                                </#if>
                            <#else>
                                <p><i>Evaluation en cours</i></p>
                            </#if>
                        </div>
                    </div>
                    </#if>
                </div>
                <div id="contactOnglet" class="tab-pane fade">
                    <form id="contact-form" action="/modal/contact" method="post">
                        <div class="col-md-12 fs-14 p-b-30">
                                <strong><span>Vous avez une question concernant cette action ? Ecrivez la ci-dessous, elle sera transmise au contact principal de l'action :</span></strong>
                        </div>
                        <div class="col-md-12 fs-14">
                            <label for="emailContact-string" class="p-b-10">Votre email *</label>
                            <input type="text" id="emailContact-string" name="emailContact" autocomplete="off" autofocus>
                            <p class="message-danger" style="display : none; margin-bottom: 30px; color:red"></p>
                        </div>
                        <div class="col-md-12 fs-14">
                            <label class="p-b-10" for="messageContact-string">Votre message *</label>
                            <textarea id="messageContact-string" name="messageContact" rows="10" cols="50"></textarea>
                            <p class="message-danger" style="display : none; margin-bottom: 30px; color : red"></p>
                            <b class="message-success" style="display : none; margin-bottom: 20px; color : green">Le message a été envoyé</b>
                            <b class="message-error" style="display : none; margin-bottom: 20px; color : red">Le message n'a pas été envoyé, vérifiez l'adresse email.</b>
                        </div>
                        <input id="idAction" name="idObjet" type="hidden" value="${action.id}">
                        <input id="typeAction" name="typeObjet" type="hidden" value="action">
                        <div class="col-md-12">
                            <em>Les champs mentionnés avec un astérisque (*) sont obligatoires</em>
                        </div>
                        <div class="col-md-12 t-a-right">
                            <button type="submit" class="bt bt-green" value="Envoyer" onclick="soumissionContactForm();">Envoyer</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <input type="hidden" id="hasPerimetreAction" value="<#if perimetre?? && perimetre.url??>${"true"}</#if>">
        <input type="hidden" id="nomPublicAction" value="${action.nomPublic!""}">
        <input type="hidden" id="perimetreUrlAction" value="<#if perimetre?? && perimetre.url??>${perimetre.url!""}</#if>">
        <input type="hidden" id="latitudeAction" value="${action.latitude!""}">
        <input type="hidden" id="longitudeAction" value="${action.longitude!""}">
        <div class="modal-footer"><br/>
        </div>
    </div>
</div>
