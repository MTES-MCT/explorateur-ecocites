<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<div class="row align-items-center">
    <#if type_indicateur?? && type_indicateur == "indicateurOngletRealisation">
        <div class="col-lg-3 col-xl-3 col-1"></div>
    <#else>
        <div class="col-lg-3 col-xl-3 col-1">
            <a class="cursorPointer btn btn-wrap-text btn-lg btn-bleu-efficacity btn-block text-white" onclick='switchTab("${type_indicateur}");'>
                <div class="row">
                    <div class="col-1 col-v-center col-h-center">
                        <i class="fa fa-arrow-left" aria-hidden="true"></i>
                    </div>
                    <div class="col-9 d-none d-lg-block">
                        Indicateurs de réalisation
                    </div>
                </div>
            </a>
        </div>
    </#if>
    <div class="col-lg-6 col-xl-6 col-10">
        <div class="head">
            <#if type_indicateur?? && type_indicateur == "indicateurOngletRealisation">
                <p class="ft-s-20 ft-700">Indicateurs de réalisation</p>
            <#else>
                <p class="ft-s-20 ft-700">Indicateurs de résultat et d'impact</p>
            </#if>
            <#if titrePageValidation??>
                <p style="color:#83b93a;">${titrePageValidation!}</p>
            </#if>
            <#if !indicateurLectureSeule>
                <p class="c-bleu-e" >Pour envoyer vos choix pour validation, vous devez choisir au moins un indicateur de réalisation, un indicateur de résultat et un indicateur d'impact.</p>
            </#if>
        </div>
    </div>
    <#if type_indicateur?? && type_indicateur == "indicateurOngletRealisation">
        <div class="col-lg-3 col-xl-3 col-1">
            <a class="cursorPointer btn btn-wrap-text btn-lg btn-bleu-efficacity btn-block text-white" onclick='switchTab("${type_indicateur}");'>
                <div class="row ">
                    <div class="col-lg-9 d-none d-lg-block">
                        Indicateurs de résultat et d'impact
                    </div>
                    <div class="col-lg-1 col-v-center col-h-center">
                        <i class="fa fa-arrow-right" aria-hidden="true"></i>
                    </div>
                </div>
            </a>
        </div>
    <#else>
        <div class="col-lg-3 col-xl-3 col-1"></div>
    </#if>
</div>
<br/>
<div class="body flex-column row">
    <#if indicateurLectureSeule>
        <div class="container-fluid col-lg-6 col-12 offset-lg-3 bg-grey-light title-container">
            <div class="noBorder title">
                Indicateurs choisis
            </div>
    <#else>
        <div class="container-fluid row bg-grey-light m-r-0 m-l-0">
            <div class="col-lg-3 col-12 noBorder">
                <select name="filtreDomaine" id="filtreDomaine" class="form-control" onchange="onChangeFiltre('${actionId!?c}', '${type_indicateur!}')">
                    <option value="">Choisissez un domaine d'action</option>
                    <#list listFiltreDomaine?keys as key>
                        <option value="${key}" >${listFiltreDomaine[key]}</option>
                    </#list>
                </select>
            </div>
            <#if type_indicateur?? && type_indicateur != "indicateurOngletRealisation">
                <div class="col-lg-3 col-12 noBorder">
                    <select id="filtreObjectif" name="filtreObjectif" class="form-control" onchange="onChangeFiltre('${actionId!?c}', '${type_indicateur!}')">
                        <option value="">Choisissez un objectif</option>
                        <#list listFiltreObjectif?keys as key>
                            <option value="${key}">${listFiltreObjectif[key]}</option>
                        </#list>
                    </select>
                </div>
            </#if>
            <#if type_indicateur?? && type_indicateur == "indicateurOngletRealisation">
                <div class="col-lg-3 col-12 noBorder"></div>
            </#if>
            <div class="col-lg-3 col-12 noBorder">
            </div>
            <div class="col-lg-3 col-12 noBorder">
                <button id="button-create-indic" type="button"
                        class="btn btn-outline-success btn-block"
                        onclick="modalIndicateur(-1);" >
                    Créer un indicateur
                </button>
            </div>
        </#if>
    </div>
</div>
<div class="body flex-column flex-lg-row" id="indicateurListe">
    <#assign cptPage= -1>
    <#if !indicateurLectureSeule>
        <div class="col-lg-4 col-12">
            <div class="title-container">
                <div class="title">
                    Liste des indicateurs recommandés
                </div>
            </div>
            <div class="content" id="listeIndicateurFiltree">
                <#include "../../components/listeIndicateurs.ftl" />
            </div>
        </div>
        <div class="col-lg-4 col-12" id="indicateurDescription">
            <div class="title-container">
                <div class="title">
                    Fiche Indicateur
                </div>
            </div>
            <div class="content">
                <p>Sélectionnez un indicateur dans la liste des indicateurs recommandés</p>
            </div>
        </div>
        <div class="col-lg-4 col-12 no-border">
    <#else>
        <div class="col-lg-6 col-12 offset-lg-3 no-border">
    </#if>
        <#if !indicateurLectureSeule>
            <div class="title-container">
                <div class="title">
                        <#if type_indicateur?? && type_indicateur == "indicateurOngletRealisation">
                            Indicateurs de réalisation choisis
                        <#else>
                            Indicateurs de résultat et d'impact choisis
                        </#if>
                </div>
            </div>
        </#if>
        <div id="assoIndicateurAction" class="content">
            <#if (listAssoIndicateurAction?? && listAssoIndicateurAction?size >0)>
                 <#list listAssoIndicateurAction as assoIndicateurActionBean>
                    <#if assoIndicateurActionBean??>
                        <#assign assoIndicateurAction = assoIndicateurActionBean >
                        <#include "indicateurAjout.ftl">
                    </#if>
                </#list>
                <div class="row errorAjoutInfo"></div>
            </#if>
        </div>
        <div id="assoIndicateurActionCommentaire" class="content">
            <label for="domainIndicateur">Commentaire <i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Vous pouvez si besoin expliquer votre sélection d’indicateurs ici"></i></label>
            <textarea class="form-control commentaire" id="indicateurCommentaire" rows="3" <#if indicateurLectureSeule>disabled</#if>></textarea>
            <#--<div class="row m-t-10">-->
                <#--<a class="cursorPointer btn btn-lg btn-success btn-block" onclick='switchTab("${type_indicateur}");'>-->
                    <#--<#if type_indicateur?? && type_indicateur == "indicateurOngletRealisation">-->
                       <#--Indicateurs de résultat et d'impact-->
                    <#--<#else>-->
                        <#--Indicateurs de réalisation-->
                    <#--</#if>-->
                <#--</a>-->
            <#--</div>-->
            <div class="row m-t-10">
                <#if canEdit?? && canEdit && !indicateurLectureSeule>
                    <button id="button-submit-indic" type="button" class="btn btn-lg btn-primary btn-block" data-toggle="modal"
                            <#if canSubmitIndicateurs?? && canSubmitIndicateurs>data-target="#confirmationRequesteValidationCategorisationIndicateur"
                            <#else>
                                <#if action?? && (action.typeFinancementIngenierieEtInvestissement || action.typeFinancementIngenierieEtPriseParticipation)>
                                    data-target="#modalCategorisationIndicateurNonCompleteIngInv"
                                <#else >
                                    data-target="#modalCategorisationIndicateurNonCompleteInv"
                                </#if>
                            </#if>>
                        Envoyer pour validation <i class="fa fas fa-envelope-o"></i>
                    </button>
                <#elseIf canValide?? && canValide >
                    <button id="button-submit-category" type="button" class="btn btn-lg btn-primary btn-block m-t-10" data-toggle="modal"
                            <#if canSubmitIndicateurs?? && canSubmitIndicateurs>data-target="#confirmationValidationCategorisationIndicateur"
                            <#else>
                                <#if action?? && (action.typeFinancementIngenierieEtInvestissement || action.typeFinancementIngenierieEtPriseParticipation)>
                                    data-target="#modalCategorisationIndicateurNonCompleteIngInv"
                                <#else >
                                    data-target="#modalCategorisationIndicateurNonCompleteInv"
                                </#if>
                            </#if>>
                        Valider la soumission <i class="fa fas fa-envelope-o"></i>
                    </button>
                    <div class="footer">
                    </div>
                <#elseIf canEdit?? && canEdit >
                    <button id="button-submit-category" type="button" class="btn btn-lg btn-primary btn-block m-t-10" data-toggle="modal" data-target="#confirmationAnnulationValidationCategorisationIndicateur">
                        Annuler la validation
                    </button>
                </#if>
            </div>
        </div>
    </div>
    <input type="hidden" value="${actionId?c}" id="actionId">
    <input type="hidden" value="${actionId?c}" id="objectId">
    <input type="hidden" <#if action?? >value="${(action.typeFinancementIngenierieEtInvestissement || action.typeFinancementIngenierieEtPriseParticipation)?string}" <#else> value="" </#if> id="financemnetIngEtAutre">
    <input type="hidden" value="${type_indicateur}" id="typeIndicateur">
    <input type="hidden" value="${listIndicateur?size}" id="listIndicateurSize">
</div>
<div class="modal fade" id="modalAjoutIndicateur" tabindex="-1" role="dialog" aria-labelledby="modalAjoutIndicateurTitle" aria-hidden="true"></div>
<div class="footer"></div>
<#assign typeOnglet = "indicateur"/>
<#include "validationCategorisationIndicateurAction.ftl"/>

