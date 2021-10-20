<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0..-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.-->
<#--You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.-->

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
