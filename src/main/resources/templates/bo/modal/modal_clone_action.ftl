<#ftl output_format="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<#-- @ftlvariable name="action" type="com.efficacity.explorateurecocites.beans.biz.ActionBean" -->

<div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 60%;">
    <div class="modal-content">
        <div class="modal-header bg-grey-light">
            <h5 class="modal-title" id="">Dupliquer l'action : ${action.nomPublic} (${action.id?c!""})</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="modal-body">
            <div class="row">
                <div class="col-6">
                    <div class="form-group">
                        <label for="idOriginal"><strong>ID de l'Action à dupliquer *</strong></label><br/>
                        <input id="idOriginal" type="text" name="idOriginal" class="form-control clone-action" value="${action.id?c!""}" required disabled/>
                        <p class="text-danger" style="display : none;"></p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-6">
                    <div class="form-group">
                        <label for="nom"><strong>Nom Long *</strong></label><br/>
                        <input id="nom" type="text" name="nom" class="form-control clone-action" value="${action.nomPublic!"Clone"} - Copie" required/>
                        <p class="text-danger" style="display : none;"></p>
                    </div>
                </div>
                <div class="col-6">
                    <div class="form-group">
                        <label for="numero"><strong>Numéro d'action *</strong></label><br/>
                        <input id="numero" type="text" name="numero" class="form-control clone-action" value="${action.numeroAction!""}" required/>
                        <p class="text-danger" style="display : none;"></p>
                    </div>
                </div>
            </div>
            <div class="row">
                <p class="text-warning m-auto">Vous devrez publier l'action vous-même une fois créée.</p>
            </div>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Annuler</button>
            <button type="button" class="btn btn-success" onclick="cloneAction('')">Confirmer</button>
        </div>
    </div>
</div>
