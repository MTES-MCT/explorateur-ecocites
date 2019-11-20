<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<#-- @ftlvariable name="typeModal" type="java.lang.String" -->
<#-- @ftlvariable name="idObject" type="java.lang.Integer" -->
<#-- @ftlvariable name="idMedia" type="java.lang.Long" -->
<#-- @ftlvariable name="media" type="com.efficacity.explorateurecocites.ui.bo.forms.MediaForm" -->


<#assign titreModal="Modification d'une image">
<#assign acceptedFormat = ".pdf, .odt, .docx, .doc, .txt, .jpg, .jpeg, .png, .bmp" />
<#assign textBtnValidation="Enregistrer">
<#assign textBtnSuppression="Supprimer">

<#if titreModal?? && acceptedFormat??>
    <#-- <div class="modal fade" id="modalAjoutDocument" tabindex="-1" role="dialog" aria-labelledby="modalAjoutDocumentTitle" aria-hidden="true"> -->
    <div id="modalAjoutImageAjaris" class="modal-dialog modal-dialog-centered" role="document" style="max-width: 70%; min-height: 400px;">
        <div class="modal-content">
            <div class="modal-header bg-grey-light">
                <h5 class="modal-title" style="margin-left: 15px" id="mediaFormTitle">${titreModal}</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div id="form-scratch">
                    <input id='selectMediaFormType' type="hidden" value="update"/>
                    <div class="row">
                        <div class="form-group col-12">
                            <label for="idAjarisTitle"><strong>Id Terra *</strong></label><br/>
                            <input id='idAjarisTitle' type="text" class="form-control" disabled <#if media?? && media.idAjaris??>value="${media.idAjaris?c}"</#if> />
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-12">
                            <label for="mediaFormTitle"><strong>Titre *</strong></label> <br/>
                            <input id='mediaFormTitle' type="text" name="title" class="form-control form-ajaris-field" required data-maxSize="80000" <#if media?? && media.title??>value="${media.title}"</#if> />
                            <p class="text-danger" style="display : none;"></p>
                        </div>
                    </div>
                    <div class="row">
                        <#if media?? && media.previewUrl??>
                            <div class="col-12">
                                <label for="previewMediaForm"><strong>Miniature</strong></label> <br />
                                <img id="previewMediaForm" role="presentation" class="rounded img-fluid" src="${media.previewUrl}" />
                            </div>
                        </#if>
                        <div class="form-group col-6">
                            <label for="datePriseVue"><strong>Date de prise de vue *</strong></label> <br/>
                            <input id="datePriseVue" placeholder="dd/mm/aaaa" type="text" name="datePriseVue" class="form-ajaris-field form-control datepicker" <#if media?? && media.datePriseVue??>value="${media.datePriseVue}"</#if> required/>
                            <p class="text-danger" style="display : none;"></p>
                        </div>
                        <div class="form-group col-6">
                            <label for="legende"><strong>Légende</strong></label> <br />
                            <input id='legende' type="text" name="legende" class="form-control form-ajaris-field" data-maxSize="80000" <#if media?? && media.legende??>value="${media.legende}"</#if> />
                            <p class="text-danger" style="display : none;"></p>
                        </div>
                        <div class="form-group col-6">
                            <label for="description"><strong>Description</strong></label> <br/>
                            <input id='description' type="text" name="description" class="form-control form-ajaris-field" data-maxSize="80000" <#if media?? && media.description??>value="${media.description}"</#if> />
                            <p class="text-danger" style="display : none;"></p>
                        </div>
                        <div class="form-group col-6">
                            <label for="copyright"><strong>Copyright *</strong></label> <br/>
                            <input id='copyright' type="text" name="copyright" class="form-control form-ajaris-field" required <#if media?? && media.copyright??>value="${media.copyright}"</#if> />
                            <p class="text-danger" style="display : none;"></p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-6">
                            <label for="lieu"><strong>Latitude *</strong></label> <br/>
                            <input id='latInput' type="text" name="latitude" class="form-control form-ajaris-field" data-type="float" required <#if media?? && media.latitude??>value="${media.latitude?c}"</#if> />
                            <p class="text-danger" style="display : none;"></p>
                        </div>
                        <div class="form-group col-6">
                            <label for="lieu"><strong>Longitude *</strong></label> <br/>
                            <input id='longInput' type="text" name="longitude" class="form-control form-ajaris-field" data-type="float" required <#if media?? && media.longitude??>value="${media.longitude?c}"</#if> />
                            <p class="text-danger" style="display : none;"></p>
                        </div>
                        <div class="col-12">
                            <div class="media-form-map loading">
                                <div id="mapMediaFormPlaceHolder" class="map-placeholder"><p>La carte est en chargement...</p></div>
                                <div id="mapMediaForm" class="map-component"></div>
                            </div>
                        </div>
                        <div class="col-12">
                            <p class="text-muted">Veuillez cliquer sur la carte pour positionner le marqueur</p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-12">
                            <p id="addMediaGlobalError" class="text-danger" style="display : none;"></p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-6">
                            <label for="numerisation"><strong>Numérisation</strong></label><span class="text-muted"> - Ordre de publication (mettre un chiffre de 1 à 5)</span><br/>
                            <input id='numerisation' type="text" name="numerisation" class="form-control form-ajaris-field" <#if media?? && media.numerisation??>value="${media.numerisation}"</#if> />
                            <p class="text-danger" style="display : none;"></p>
                        </div>
                        <div class="form-group col-12">
                            <label><strong>Autorisation de diffusion</strong></label> <br/>
                            <p>Dans le cadre de la médiatisation de la démarche ÉcoCité, nous autorisons le ministère de la Transition écologique et solidaire (MTES)
                                et le ministère de la Cohésion des territoires et des relations avec les collectivités territoriales (MCTRCT) ainsi que leurs partenaires institutionnels à utiliser et diffuser à titre gratuit
                                et non exclusif les photographies enregistrées au sein de l'Explorateur ÉcoCités. Ces clichés seront exploités à titre exclusif par les
                                ministères dans le cadre de la médiatisation de la démarche ÉcoCité. Les photographies susmentionnées sont susceptibles d’être reproduites
                                sur les supports suivants :
                            </p>
                            <ul>
                                <li>Publication de dossiers de presse</li>
                                <li>Publication dans une revue, ouvrage ou journal dont la publication est coordonnée par les MTES/MCTRCT</li>
                                <li>Présentation au public lors d’expositions dédiées à la ville durable organisées par les ministères et leurs partenaires institutionnels </li>
                                <li>Diffusion sur le site web, internet et extranet des MTES/MCTRCT et ses partenaires institutionnels</li>
                                <li>Diffusion sur le site web « Explorateur ÉcoCités »</li>
                                <li>Diffusion sur tout autre support multimédia</li>
                            </ul>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-12">
                            <p id="editMediaGlobalError" class="text-danger" style="display : none;"></p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <div class="row full-row" style="width: 100%!important;">
                    <div class="col-6 vertical-center-flex "> <p>Les champs mentionnés avec un astérisque (*) sont obligatoires.</p></div>
                    <div class="col-3 vertical-center-flex ">
                        <button type="button" class="btn btn-block btn-wrap-text btn-danger" onclick="deleteMedia('${idMedia?c}', '${idObject?c}')">${textBtnSuppression}</button>
                    </div>
                    <div class="col-3 vertical-center-flex ">
                        <button type="button" class="btn btn-block btn-wrap-text btn-success" onclick="addAjarisImage('${typeModal}', '', '${idObject?c}', '${idMedia?c}')">${textBtnValidation}</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
<#else>
    <#assign message="Un des paramètres de la modale est vide (titre / url / formats acceptés)">
    <#assign idTitre="modalAjoutDocumentTitle">
    <#include "../error_modal.ftl" >
</#if>
