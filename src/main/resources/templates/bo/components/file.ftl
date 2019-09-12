<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<#-- @ftlvariable name="file" type="com.efficacity.explorateurecocites.beans.biz.FileUploadBean" -->

<#if file?? && file.originalName?hasContent && file.title?hasContent && file.url?? && file.id??>
    <p style="margin: 10px 10px 0;">
        <a class="pull-left" download="${file.originalName}" href="${file.url}">${file.title}</a>
        <i class="fa fa-remove pull-right" onclick="deleteDocumentQuestionnaire('${file.id?c}', '${question.listeReponses[0].id}')"></i>
    </p><br/>
</#if>
