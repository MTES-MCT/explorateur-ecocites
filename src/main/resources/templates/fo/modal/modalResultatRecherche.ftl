<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<#-- @ftlvariable name="contenuRecherche" type="java.util.List<com.efficacity.explorateurecocites.beans.biz.ContenuRecherche>" -->

<#if contenuRecherche?? && contenuRecherche?hasContent>
    <#list contenuRecherche as contenu>
        <#if contenu.resultatRechercheList?? && contenu.resultatRechercheList?hasContent>
            <div class="axis">
                <h2 class="text-center">${contenu.titre!}</h2>
                <ul class="list--modal">
                    <#list contenu.resultatRechercheList as resultatRecherche>
                        <li class="modal-li">
                            <a class="modal-li-btn show-content-in-modal" onclick="openModalRechercheAction('${resultatRecherche.href!}', event);" href="#">
                                <#if resultatRecherche.titre??>
                                    <span>${resultatRecherche.titre}</span>
                                </#if>
                                <br>
                                <#if resultatRecherche.description??>
                                    ${resultatRecherche.description}
                                </#if>
                            </a>
                        </li>
                    </#list>
                </ul>
            </div>
        </#if>
    </#list>
<#else>
    <div id="search-result"><h3 class="search-process-info">Aucun résultat</h3></div>
</#if>
