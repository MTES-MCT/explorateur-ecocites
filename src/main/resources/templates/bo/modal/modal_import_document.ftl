<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<#-- @ftlvariable name="importModalTitle" type="java.lang.String" -->
<#-- @ftlvariable name="importFormat" type="java.lang.String" -->

<div class="modal fade" id="modalImportDocument" tabindex="-1" role="dialog" aria-labelledby="modalImportDocumentTitle" aria-hidden="true">
    <div id="modalAjoutDocumentContent" class="modal-dialog modal-dialog-centered" role="document" style="max-width: 40%;">
        <div class="modal-content">
            <div class="modal-header bg-grey-light">
                <h5 class="modal-title" style="margin-left: 15px" id="modalImportDocumentTitle">${importModalTitle!"Import de fichier"}</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="form-group col-12">
                        <label for="fileInput"><strong>Fichier (.xslx)</strong></label><br/>
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div  class="btn btn-outline-primary btn-file">
                                    Choisir un fichier
                                    <input type="file" id="fileInput" onchange="onFileChanged(this, '#fileName')" accept="${importFormat!".xlsx"}">
                                </div >
                            </div>
                            <input id="fileName" type="text" class="form-control" placeholder="Pas de fichier selectionné" aria-label="" aria-describedby="basic-addon1" disabled>
                        </div>
                        <p class="text-danger" style="display : none;"></p>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">Annuler</button>
                <button type="button" class="btn btn-success" onclick="importFile()">Confirmer l'ajout</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modalImportDocumentSuccess" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 40%;">
        <div class="modal-content">
            <div class="modal-header bg-grey-light">
                <h5 class="modal-title">Import réussi</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="success-text alert alert-success">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="location.reload(true)">Retour</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modalImportDocumentError" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 40%;">
        <div class="modal-content">
            <div class="modal-header bg-grey-light">
                <h5 class="modal-title">Erreur pendant l'import</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="alert alert-danger error-text">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" onclick="location.reload(true)">Retour</button>
            </div>
        </div>
    </div>
</div>
