<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0..-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.-->
<#--You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.-->

<style type="text/css">
    <#if finalites??>
        <#list finalites as finalite>
            #btn-outline-final-${finalite.id?c}:hover,
            #btn-outline-final-${finalite.id?c}:focus,
            #btn-outline-final-${finalite.id?c}.selected{
                color: #fff!important;
                background-color: ${finalite.codeCouleur}!important;
                border-color: ${finalite.codeCouleur}!important;
            }
            .color-etiquette-${finalite.id?c} {
                border-color: ${finalite.codeCouleur}!important;
                color: ${finalite.codeCouleur}!important;
            }
        </#list>
    </#if>
</style>

<div class="head">
    <#if titrePageValidation??>
        <p style="color:#83b93a;">${titrePageValidation!}</p>
    </#if>
    <#if caracteristiqueLectureSeule?? && !caracteristiqueLectureSeule>
        <p class="c-bleu-e" >Pour passer à l'étape suivante, vous devez choisir au moins une étiquette "objectif de la ville durable".</p>
    </#if>
</div>
<div class="body flex-column flex-lg-row">
    <#if ecociteObjectif??>
        <#assign objectifs = ecociteObjectif.etiquettes!/>
        <#assign etqsPrimaires = []/>
        <#assign etqsSecondaires = []/>
        <#assign etqsTrois = []/>
        <#if ecociteObjectif.etiquettesSelected?? && ecociteObjectif.etiquettesSelected?hasContent>
            <#list ecociteObjectif.etiquettesSelected as etq>
                <#if etq.poid == 1>
                    <#assign etqsPrimaires = etqsPrimaires + [etq]/>
                <#elseIf etq.poid == 2>
                    <#assign etqsSecondaires = etqsSecondaires + [etq]/>
                <#elseIf etq.poid == 3>
                    <#assign etqsTrois = etqsTrois + [etq]/>
                </#if>
            </#list>
        </#if>
    </#if>
    <#if caracteristiqueLectureSeule?? && !caracteristiqueLectureSeule>
        <div class="col-lg-4 col-12" style="position:relative;">
            <div class="title-container">
                <div class="title">
                    A quelles finalités de la ville durable répond votre ÉcoCité ?
                </div>
            </div>
            <div class="content">
                <#if finalites??>
                    <#list finalites as finalite>
                    <button id="btn-outline-final-${finalite.id?c}" name="idFinalite"
                            style="white-space:normal !important; word-wrap: break-word; color: ${finalite.codeCouleur}; border-color: ${finalite.codeCouleur}; background-color: transparent;
                                    background-image: none;" data-objectId="${ecocite.id!?c}" data-objectClass="Ecocite"
                            class="btn btn-lg btn-block <#if finalite?isFirst>selected</#if>"
                            onclick="showListEtiquettesForEcocite('${finalite.id?c}', 'finalite', this, '${ecocite.id!?c}')"
                    >
                        ${finalite.libelle}
                    </button>
                    </#list>
                </#if>
            </div>
            <div class="row placeholder-h-100"></div>
            <img src="/img/brand/logo_iso.jpg" width="66px" height="auto" style="position:absolute; bottom: 0;">
        </div>
        <div class="col-lg-4 col-12" style="position:relative;">
            <div class="title-container">
                <div class="title">
                    Choisissez vos étiquettes
                </div>
            </div>
            <div class="content">
                <#if !finaliteSelec??>
                    <div id="etiquettesGroup" class="list-group">
                        <div class="info-cat-empty">
                            Sélectionnez une finalité afin d'accéder aux objectifs de la ville durable associés
                        </div>
                    </div>
                <#else>
                    <div id="etiquettesGroup" class="list-group">
                        <div>
                            <input id="currentCategory" type="hidden" value="finalite">

                            <input id="currentCategoryId" type="hidden" value="${finaliteSelec.id!?c}">
                        </div>
                        <#if objectifs?? && objectifs?hasContent>
                            <#list objectifs as etiquetteAsso>
                                <#assign lectureSeule = true>
                                <#include "../../components/etiquette.ftl">
                            </#list>
                        </#if>
                    </div>
                </#if>
            </div>
            <div class="row placeholder-h-100"></div>
            <img src="/img/brand/Logo_RFSC.jpg" width="100px" height="auto" style="position:absolute; bottom: 0;">
        </div>
    </#if>

    <div <#if caracteristiqueLectureSeule?? && !caracteristiqueLectureSeule> class="col-lg-4 col-12" <#else> class="col-lg-6 col-6 offset-lg-3 offset-3" </#if>>
        <div class="title-container bg-grey-light">
            <div class="title">
                Objectifs de la ville durable choisis
            </div>
        </div>
        <div class="content">
            <div class="title">
                Objectifs majeurs
            </div>
            <div class="info-etq-empty ft-s-10" <#if etqsPrimaires?? && etqsPrimaires?hasContent>hidden</#if>>
                Sélectionnez des étiquettes pour caractériser votre ÉcoCité
            </div>

            <!-- sort: false -->
            <div id="etiquettePrincipal" class="list-group drag-and-drop-area etq-list">
                <#if etqsPrimaires?? && etqsPrimaires?hasContent>
                    <#list etqsPrimaires as etiquetteAsso>
                        <#assign lectureSeule = caracteristiqueLectureSeule>
                        <#include "../../components/etiquette.ftl">
                    </#list>
                </#if>
            </div>
        </div>
        <hr/>
        <div class="content">
            <div class="title">
                Objectifs modérés
            </div>
            <div class="info-etq-empty ft-s-10" <#if etqsSecondaires?? && etqsSecondaires?hasContent || titrePageValidation??>hidden</#if>>
                Sélectionnez une ou plusieurs étiquette(s) secondaire(s)
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
        <hr/>
        <div class="content">
            <div class="title">
                Objectifs mineurs
            </div>
            <div class="info-etq-empty ft-s-10" <#if etqsTrois?? && etqsTrois?hasContent || titrePageValidation??>hidden</#if>>
                Sélectionnez une ou plusieurs étiquette(s) mineure(s)
            </div>

            <!-- sort: false -->
            <div id="etiquetteTrois" class="list-group drag-and-drop-area etq-list">
                <#if etqsTrois?? && etqsTrois?hasContent>
                    <#list etqsTrois as etiquetteAsso>
                        <#assign lectureSeule = caracteristiqueLectureSeule>
                        <#include "../../components/etiquette.ftl">
                    </#list>
                </#if>
            </div>
        </div>
        <div class="content">
            <label for="finaliteCommentaire">Commentaire <i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Vous pouvez si besoin expliquer votre sélection d’étiquettes ici"></i></label>
            <textarea class="commentaire form-control" id="finaliteCommentaire" rows="3" <#if caracteristiqueLectureSeule?? && caracteristiqueLectureSeule>disabled</#if>></textarea>
        </div>
    </div>
</div>
<div class="footer"></div>

<#assign typeOnglet = "category"/>
<#include "validationCategorisationIndicateurEcocite.ftl"/>
