<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<#-- @ftlvariable name="listIndicateur" type="java.util.List<com.efficacity.explorateurecocites.beans.model.Indicateur>" -->

<#assign cptPage= -1>
<#list listIndicateur as indicateur>
    <#if (indicateur?index % 10) == 0>
        <#assign cptPage=(cptPage+1)>
    </#if>
    <div class="indicateurRecommande row pageIndicateur pageIndicateur${cptPage}" data-objectid="${indicateur.id?c}" onclick="loadIndicateurInfo(this);" <#if (cptPage>0)>style="display : none;"</#if>>
        <div class="col-2">
            <div class="iconeIndicateur ${indicateur.nature}"><div></div></div>
        </div>
        <div class="col-10">
            <span class='nomIndicateurCour'>${indicateur.nomCourt}</span>
            <span class='nomIndicateurLong'>${indicateur.nom}</span>
        </div>
    </div>
</#list>
<#if (cptPage > 0)>
    <div class="row pageNavigator">
        <div class="col-6">Eléments par page : 10</div>
        <div class="col-6">
            <ul class="pagination justify-content-end">
                <li class="indicateurCompteur m-r-10"><span>0-10 sur ${listIndicateur?size}</span></li>
                <li class="page-item ft-s-16"><a href="#" class="prev_link">&#60;</a></li>
                <li class="page-item ft-s-16"><a href="#" class="next_link">&#62;</a></li>
            </ul>
        </div>
    </div>
</#if>
<input type="hidden" id="maxPage" value="${cptPage}">
