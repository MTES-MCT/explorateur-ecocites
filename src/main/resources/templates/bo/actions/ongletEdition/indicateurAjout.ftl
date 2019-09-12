<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<#-- @ftlvariable name="assoIndicateurAction" type="com.efficacity.explorateurecocites.beans.biz.AssoIndicateurObjetBean" -->


<#if assoIndicateurAction??>
    <div class="row assoIndicateurAction${assoIndicateurAction.id?c} m-b-10">
        <div class="col-2">
            <div class="iconeIndicateur ${assoIndicateurAction.indicateur.nature}"><div></div></div>
        </div>
        <div class="col-8">
            <span class='nomIndicateurCour'>${assoIndicateurAction.indicateur.nomCourt}</span>
            <span class='nomIndicateurLong'>Unité : ${assoIndicateurAction.unite}</span>
            <#if assoIndicateurAction.posteCalcule?? >
                <span class='nomIndicateurLong'>Poste de calcul : ${assoIndicateurAction.posteCalcule}</span>
            </#if>
            <#if assoIndicateurAction.indicateur.etatValidation != "valide">
                <i class="nomIndicateurLong">Indicateur non validé</i>
            </#if>
        </div>
        <div class="col-2">
            <#if !indicateurLectureSeule>
                <a class="c-dark cursorPointer" onclick="deleteIndicateurInfo(this);" data-objectid="${assoIndicateurAction.id?c}"><i class="fa fas fa-times"></i></a>
            </#if>
            <a class="c-dark cursorPointer" onclick="modalIndicateur('${assoIndicateurAction.idIndicateur?c}');"><i class="fa fas fa-search"></i></a>
        </div>
    </div>
</#if>
