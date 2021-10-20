<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0..-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.-->
<#--You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.-->

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
