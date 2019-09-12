<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<#-- @ftlvariable name="message" type="java.lang.String" -->
<#-- @ftlvariable name="idTitre" type="java.lang.Long" -->

<div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 40%; z-index: 1200 !important;">
    <div class="modal-content">
        <div class="modal-header bg-grey-light">
            <#if idTitre??>
                <h5 class="modal-title" id="${idTitre}">Erreur</h5>
            <#else>
                <h5 class="modal-title" id="">Erreur lors de l'affichage de la modal</h5>
            </#if>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="modal-body">
            <div id="errorModalMessage" class="alert alert-danger text-center">
                ${message!}
            </div>
        </div>
        <div class="modal-footer">
            <div class="row full-row" style="width: 100%!important;">
                <#if hasButton??>
                    <div class="col-3 offset-6 vertical-center-flex">
                        ${(buttonContent!"")?noEsc}
                    </div>
                </#if>
                <div class="col-3 vertical-center-flex pull <#if !hasButton??>offset-9</#if>">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Retour</button>
                </div>
            </div>
        </div>
    </div>
</div>
