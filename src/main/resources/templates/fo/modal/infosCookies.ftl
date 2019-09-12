<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<div id="infosCookies">
<#--<input type="hidden" id="axisModal" value="<#if axis??>true</#if>">-->
<#--<input type="hidden" id="selectAxeModal" value="<#if selectAxe??>true</#if>">-->
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-header-inner">
                    <h4 class="modal-title">INFORMATIONS SUR lES COOKIES</h4>
                    <div class="button-area--modal">
                        <button type="button" class="close" onclick="simpleHideModal()">
                            <span class="sr-only">Close</span>
                        </button>
                    </div>
                </div>
            </div>
            <div class="modal-body" id="modal-body">
                <p><b>Informations sur les Cookies</b><br/>Il faut entrer ici le texte de description des cookies.</p>
                <br/>
            </div>
        </div>
    </div>
</div>
