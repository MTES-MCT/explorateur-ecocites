<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<#if categorie?? >
    <#if questionnaire[categorie]??>
        <#list questionnaire[categorie] as question>
            <#if question?isFirst && question.codeCategorieEnum??>
                <h3 class="p-t-30 p-b-10 ft-700">${question.codeCategorieEnum.libelle}</h3>
                <#if question.codeCategorieEnum.description??>
                    <p>${question.codeCategorieEnum.description}</p>
                </#if>
            </#if>
            <#include "question.ftl"/>
        </#list>
    </#if>
</#if>
