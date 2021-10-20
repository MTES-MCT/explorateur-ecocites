<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0..-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.-->
<#--You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.-->

<style type="text/css">
    <#if ingenieries??>
        <#list ingenieries as ingenierie>
            #btn-outline-ing-${ingenierie.id?c}:hover,
            #btn-outline-ing-${ingenierie.id?c}:focus,
            #btn-outline-ing-${ingenierie.id?c}.selected {
                color: #fff!important;
                background-color: ${ingenierie.codeCouleur}!important;
                border-color: ${ingenierie.codeCouleur}!important;
            }
            .color-etiquette-${ingenierie.id?c} {
                border-color: ${ingenierie.codeCouleur}!important;
                color: ${ingenierie.codeCouleur}!important;
            }
        </#list>
    </#if>
</style>
<div class="row align-items-center">
    <div class="col-lg-3 col-xl-3 col-1">
    </div>
    <div class="col-lg-6 col-xl-6 col-10">
        <div class="head">
            <p class="ft-s-20 ft-700">Types de mission d'ingénierie</p>
            <#if titrePageValidation??>
                <p style="color:#83b93a;">${titrePageValidation!}</p>
            </#if>
            <#if !caracteristiqueLectureSeule>
            <p class="c-bleu-e"> Pour passer à l'étape suivante, vous devez choisir au moins une étiquette</p>
            </#if>
        </div>
    </div>
    <div class="col-lg-3 col-xl-3 col-1">
        <button class="btn btn-bleu-efficacity btn-block btn-wrap-text text-white" <#if action??> onclick="loadOnglet('domaine', '${action.id?c}');"</#if>>
            <div class="row">
                <div class="col-9 d-none d-lg-block">
                    Domaines d'action
                </div>
                <div class="col-2 col-v-center col-h-center">
                    <i class="fa fa-arrow-right" aria-hidden="true"></i>
                </div>
            </div>
        </button>
    </div>
</div>
<div class="body flex-column flex-lg-row">
    <#if actionIngenierie??>
        <#assign etqsIngenierie = actionIngenierie.etiquettes!/>
        <#assign etqsPrimaires = []/>
        <#if actionIngenierie.etiquettesSelected?? && actionIngenierie.etiquettesSelected?hasContent>
            <#list actionIngenierie.etiquettesSelected as etq>
                <#if etq.poid == 1>
                    <#assign etqsPrimaires = etqsPrimaires + [etq]/>
                </#if>
            </#list>
        </#if>
    </#if>
    <#if !caracteristiqueLectureSeule>
        <div class="col-lg-4 col-12" style="position:relative;">
            <div class="title-container">
                <div class="title">
                    A quels types de mission d'ingénierie correspond votre action ?
                </div>
            </div>
            <div class="content">
                <#if ingenieries??>
                    <#list ingenieries as ingenierie>
                    <button id="btn-outline-ing-${ingenierie.id?c}" name="idIngenierie"
                            data-objectId="${action.id!?c}" data-objectClass="Action"
                            class="btn btn-lg btn-block <#if ingenierie?isFirst>selected</#if>"
                            style="white-space:normal !important; word-wrap: break-word; color: ${ingenierie.codeCouleur}; border-color: ${ingenierie.codeCouleur}; background-color: transparent;
                                    background-image: none;"
                            onclick="showListEtiquettesForAction('${ingenierie.id?c}', 'ingenierie', this, '${action.id!?c}')"
                    >
                        ${ingenierie.libelle}
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
                <#if !ingenierieSelec??>
                <div class="info-cat-empty">
                    Sélectionnez une nature de mission afin d'accéder aux types de mission d’ingénierie associés
                </div>
                </#if>
                <!-- sort: true -->
                <div id="etiquettesGroup" class="list-group">
                    <div>
                        <input id="currentCategory" type="hidden" value="ingenierie">
                        <input id="currentCategoryId" type="hidden" value="${ingenierieSelec.id!?c}">
                    </div>
                    <#if etqsIngenierie?? && etqsIngenierie?hasContent>
                        <#list etqsIngenierie as etiquetteAsso>
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
                Type de mission d'ingénierie choisis
            </div>
        </div>
        <div class="content">
            <div class="title">
                Type de mission d'ingénierie choisis
            </div>
            <div class="info-etq-empty ft-s-10" <#if etqsPrimaires?? && etqsPrimaires?hasContent>hidden</#if>>
                Sélectionnez des étiquettes pour caractériser votre action
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
        <div class="content">
            <label for="ingenierieCommentaire">Commentaire <i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Vous pouvez si besoin expliquer votre sélection d’étiquettes ici"></i></label>
            <textarea class="commentaire form-control" id="ingenierieCommentaire" rows="3" <#if caracteristiqueLectureSeule>disabled</#if>></textarea>
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
