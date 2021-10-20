<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0..-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.-->
<#--You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.-->

<#-- @ftlvariable name="actionsContenu" type="java.util.List<com.efficacity.explorateurecocites.beans.biz.ContenuRecherche>" -->
<#-- @ftlvariable name="ecocite" type="com.efficacity.explorateurecocites.beans.biz.EcociteBean" -->
<#-- @ftlvariable name="region" type="java.lang.String" -->
<#-- @ftlvariable name="images" type="java.util.List<com.efficacity.explorateurecocites.beans.biz.MediaBean>" -->


<#assign hasPerimetreStrategique = perimetreStrategique?? && perimetreStrategique.originalName?hasContent && perimetreStrategique.title?hasContent && perimetreStrategique.url?? && perimetreStrategique.id??>
<#assign hasPerimetreOperationnel = perimetreOperationnel?? && perimetreOperationnel.originalName?hasContent && perimetreOperationnel.title?hasContent && perimetreOperationnel.url?? && perimetreOperationnel.id??>

<div class="modal-dialog">
    <div class="modal-content modal-ecocite">
        <div class="modal-header">
            <div class="modal-header-inner">
                <div class="col-md-10 col-xs-8">
                    <h4 class="modal-title">${ecocite.nom!}</h4>
                </div>
                <div class="col-md-2 col-xs-4 d-print-flex">
                    <button type="button" class="close" onclick="hideModal()"><span class="sr-only">Close</span></button>
                    <button id="back-history" class="back" onclick="window.history.back()"></button>
                </div>
            </div>
        </div>

        <div class="modal-sub-header">
            <ul id="panel-navigation" class="nav nav-pills nav-pills--modal nav-pills--modal---ecocite">
                <li class="col-md-1"></li>
                <li class="col-md-2 active"><a href="#presentationOnglet" data-toggle="pill" >Présentation</a></li>
                <li class="col-md-2 "><a href="#mapEcociteOnglet" data-toggle="pill" >Carte</a></li>
                <li class="col-md-2 "><a href="#actionsOnglet" data-toggle="pill" >Actions</a></li>
                <li class="col-md-2 "><a href="#evaluationOnglet" data-toggle="pill" >Evaluation</a></li>
                <li class="col-md-2 "><a href="#contactOnglet" data-toggle="pill" >Contact</a></li>
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
                        <div class="row">
                            <p class="col-md-4 p-t-20" align="center">
                                <span class="ft-s-14" >Nombre de communes : </span>
                                <strong><span class="ft-s-14 ">${ecocite.nbCommunes!}</span></strong><br/>
                            </p>
                            <p class="col-md-5 p-t-20 " align="center">
                                <span class="ft-s-14">Nombre d'habitants : </span>
                                <strong><span class="ft-s-14 ">${ecocite.nbHabitants!}</span></strong><br/>
                            </p>
                            <p class="col-md-3 p-t-20 p-r-0" align="center">
                                <span class="ft-s-14">Superficie : </span>
                                <strong><span class="ft-s-14 ">${ecocite.superficieKm2!}</span> km²</strong><br/>
                            </p>
                        </div>
                        <div class="row">
                            <p class="col-md-12 p-t-20 ">
                                <span class="ft-s-14">Soutien du PIA : </span>
                                <strong><span class="ft-s-14 ">${ecocite.soutienPiaDetail!}</span></strong><br/>
                            </p>
                        </div>
                    </div>
                    <div class="col-md-4 fs-14">
                        <div align="left">
                            <div class="col-md-12">
                                <p>
                                    <span class="ft-s-14">Porteur du projet</span><br/>
                                    <span class="ft-s-14"> <strong>${ecocite.porteur!}</strong></span>
                                </p>
                            </div>
                            <div class="col-md-12">
                                <p>
                                    <span class="ft-s-14">Région</span><br/>
                                    <span class="ft-s-14"> <strong>${region!}</strong></span>
                                </p>
                            </div>
                            <div class="col-md-12">
                                <p>
                                    <span class="ft-s-14">Partenaires</span><br/>
                                <#if partenaires??>
                                    <#list partenaires as partenaire>
                                        <span class="ft-s-14"> <strong>${partenaire!}</strong></span><br/>
                                    </#list>
                                </#if>
                                </p>
                            </div>
                            <div class="col-md-12">
                                <p>
                                    <span class="ft-s-14">ÉcoCité depuis</span><br/>
                                    <span class="ft-s-14"> <strong>${ecocite.anneeAdhesion!}</strong></span>
                                </p>
                            </div>
                            <div class="col-md-12">
                                <p>
                                    <span class="ft-s-14">Contact</span><br/>
                                    <#if contacts??>
                                        <#list contacts as contact>
                                            <strong><span class="ft-s-14">${contact.prenom!} ${contact.nom!}</span></strong><br/>
                                        </#list>
                                    </#if>
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12 p-t-20">
                        <p align="justify">
                            <#if ecocite.descStrategie??>
                                ${ecocite.descStrategie?noEsc}
                            </#if>
                        </p>
                    </div>
                    <br/>
                    <div class="col-md-6">
                        <#if ecocite.documents?? && ecocite.documents?hasContent >
                            <span>Document :</span><br/>
                            <#list ecocite.documents as document>
                                <a class="underline" download="${document.originalName}" href="${document.url}"><strong>${document.title}</strong></a><br/>
                            </#list>
                        </#if>
                    </div>
                    <div class="col-md-6"></div>
                    <div class="col-md-12 p-t-20">
                        <#if ecocite.lien?? && ecocite.lien?hasContent>
                           <span class="m-r-15">Pour en savoir plus :</span><br/>
                             <a class="underline" target="_blank" href="${ecocite.lien!}"><strong>${ecocite.lien}</strong></a>
                        </#if>
                    </div>
                </div>
                <div id="mapEcociteOnglet" class="tab-pane fade">
                    <#if (hasPerimetreStrategique || hasPerimetreOperationnel) || (ecocite.longitude?? && ecocite.latitude?? && ecocite.longitude != "" && ecocite.latitude != "")>
                        <div id="loading large-map" style="height: 100%; width: 100%;">
                            <div id="mapEcocitePlaceHolder" class="map-placeholder"><p>La carte est en chargement...</p></div>
                            <div id="mapEcocite" class="map-component"></div>
                        </div>
                    <#else>
                        <div class="row text-center">
                            <h4>Pas de carte disponible</h4>
                        </div>
                    </#if>
                    <div class="col-md-12 p-t-20">
                        <p align="justify">
                            <#if ecocite.descPerimetre??>
                                ${ecocite.descPerimetre?noEsc}
                            </#if>
                        </p>
                    </div>
                </div>
                <div id="actionsOnglet" class="tab-pane fade">
                    <#if actionsContenu??>
                        <#list actionsContenu as contenu>
                            <div class="axis">
                                <h2 class="text-center">${contenu.titre!}</h2>
                                <ul class="list-action list--modal">
                                    <#if contenu.hasResult()>
                                        <#list contenu.resultatRechercheList as resultatRecherche>
                                            <li class="modal-li">
                                                <a class="modal-li-btn show-content-in-modal" onclick="openModalRechercheAction('${resultatRecherche.href}', event);" href="#">
                                                    <span>${(resultatRecherche.titre!"")}</span>
                                                    ${resultatRecherche.description!}
                                                </a>
                                            </li>
                                        </#list>
                                    </#if>

                                </ul>
                            </div>
                        </#list>
                    </#if>
                </div>
                <div id="evaluationOnglet" class="tab-pane fade p-10">
                    <#if ecocite?? && ecocite.etiquettesList??>
                        <div class="row m-b-20">
                            <div class="col-lg-6">
                                <h4 class="w-black">Objectifs de la ville durable</h4>
                                <#if ecocite.etapes?? && ecocite.etapes.categorisationValide()>
                                    <#if ecocite.etiquettesList.objectifsEtiquettes?? && ecocite.etiquettesList.objectifsEtiquettes?hasContent>
                                        <#if ecocite.etiquettesList.objectifsEtiquettes["1"]?? && ecocite.etiquettesList.objectifsEtiquettes["1"]?hasContent>
                                        <div class="p-l-10 p-r-10">
                                            <h5><u>Majeurs</u></h5>
                                            <#list ecocite.etiquettesList.objectifsEtiquettes["1"] as etiquette>
                                            <div class="p-l-10 p-t-10">
                                                <#if etiquette??>
                                                    <#include "../components/etiquette.ftl" />
                                                </#if>
                                                </div>
                                            </#list>
                                        </div>
                                        </#if>
                                    </#if>
                                    <#if ecocite.etiquettesList.objectifsEtiquettes?? && ecocite.etiquettesList.objectifsEtiquettes?hasContent>
                                        <#if ecocite.etiquettesList.objectifsEtiquettes["2"]?? && ecocite.etiquettesList.objectifsEtiquettes["2"]?hasContent>
                                        <div class="p-l-10 p-r-10">
                                            <h5><u>Modérés</u></h5>
                                            <#list ecocite.etiquettesList.objectifsEtiquettes["2"] as etiquette>
                                            <div class="p-l-10 p-t-10">
                                                <#if etiquette??>
                                                    <#include "../components/etiquette.ftl" />
                                                </#if>
                                            </div>
                                            </#list>
                                        </div>
                                        </#if>
                                    </#if>
                                    <#if ecocite.etiquettesList.objectifsEtiquettes?? && ecocite.etiquettesList.objectifsEtiquettes?hasContent>
                                        <#if ecocite.etiquettesList.objectifsEtiquettes["3"]?? && ecocite.etiquettesList.objectifsEtiquettes["3"]?hasContent>
                                        <div class="p-l-10 p-r-10">
                                            <h5><u>Mineurs</u></h5>
                                            <#list ecocite.etiquettesList.objectifsEtiquettes["3"] as etiquette>
                                            <div class="p-l-10 p-t-10">
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
                    </#if>
                    <div class="row m-b-20">
                        <div class="col-lg-6">
                            <h4 class="w-black">Indicateurs de réalisation</h4>
                            <#if ecocite.etapes?? && ecocite.etapes.indicateurValide()>
                                <#if ecocite.indicateursBean?? && ecocite.indicateursBean.realisationIndicateurs?? && ecocite.indicateursBean.realisationIndicateurs?hasContent>
                                    <ul>
                                        <#list ecocite.indicateursBean.realisationIndicateurs as realisationIndic>
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
                            <#if ecocite.etapes?? && ecocite.etapes.indicateurValide()>
                                <#if ecocite.indicateursBean?? && ecocite.indicateursBean.resultatIndicateurs?? && ecocite.indicateursBean.resultatIndicateurs?hasContent>
                                    <ul>
                                        <#list ecocite.indicateursBean.resultatIndicateurs as realisationIndic>
                                            <li>${realisationIndic.nom!}</li>
                                        </#list>
                                    </ul>
                                </#if>
                                <#if ecocite.indicateursBean?? && ecocite.indicateursBean.impactIndicateurs?? && ecocite.indicateursBean.impactIndicateurs?hasContent>
                                    <ul>
                                        <#list ecocite.indicateursBean.impactIndicateurs as realisationIndic>
                                            <li>${realisationIndic.nom!}</li>
                                        </#list>
                                    </ul>
                                </#if>
                            <#else>
                                <p><i>Evaluation en cours</i></p>
                            </#if>
                        </div>
                    </div>
                </div>
                <div id="contactOnglet" class="tab-pane fade">
                    <form id="contact-form" action="/modal/contact" method="post">
                        <div class="col-md-12 fs-14 p-b-30">
                            <strong><span>Vous avez une question concernant cette ÉcoCité ? Ecrivez la ci-dessous, elle sera transmise au contact principal de l'ÉcoCité :</span></strong>
                        </div>
                        <div class="col-md-12 fs-14">
                            <label class="p-b-10" for="emailContact-string">Votre email *</label>
                            <input type="text" id="emailContact-string" name="emailContact" autocomplete="off" autofocus>
                            <p class="message-danger" style="display : none; margin-bottom: 30px; color : red">Ce champ est obligatoire</p>
                        </div>
                        <div class="col-md-12 fs-14">
                            <label class="p-b-10" for="messageContact-string">Votre message *</label>
                            <textarea id="messageContact-string" name="messageContact" rows="10" cols="50"></textarea>
                            <p class="message-danger" style="display : none; margin-bottom: 30px; color : red">Ce champ est obligatoire</p>
                            <b class="message-success" style="display : none; margin-bottom: 20px; color : green">Le message a été envoyé</b>
                            <b class="message-error" style="display : none; margin-bottom: 20px; color : red">Le message n'a pas été envoyé, vérifiez l'adresse email.</b>
                        </div>
                        <input id="idEcocite" name="idObjet" type="hidden" value="${ecocite.id?c}"/>
                        <input id="typeEcocite" name="typeObjet" type="hidden" value="ecocite"/>
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
        <input type="hidden" id="hasPerimetreStrategiqueEcocite" value="<#if perimetreStrategique?? && perimetreStrategique.url??>${"true"}</#if>">
        <input type="hidden" id="hasPerimetreOperationnelEcocite" value="<#if perimetreOperationnel?? && perimetreOperationnel.url??>${"true"}</#if>">
        <input type="hidden" id="nomPublicEcocite" value="${ecocite.nom!""}">
        <input type="hidden" id="perimetreStrategiqueUrlEcocite" value="<#if perimetreStrategique?? && perimetreStrategique.url??>${perimetreStrategique.url!""}</#if>">
        <input type="hidden" id="perimetreOperationnelUrlEcocite" value="<#if perimetreOperationnel?? && perimetreOperationnel.url??>${perimetreOperationnel.url!""}</#if>">
        <input type="hidden" id="latitudeEcocite" value="${ecocite.latitude!""}">
        <input type="hidden" id="longitudeEcocite" value="${ecocite.longitude!""}">
    </div>
    <div class="modal-footer">
    </div>
</div>
