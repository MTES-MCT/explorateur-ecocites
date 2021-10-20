<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0..-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.-->
<#--You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.-->

<#-- @ftlvariable name="file" type="com.efficacity.explorateurecocites.beans.biz.FileUploadBean" -->

<#if file?? && file.originalName?hasContent && file.title?hasContent && file.url?? && file.id??>
    <p style="margin: 10px 10px 0;">
        <a class="pull-left" download="${file.originalName}" href="${file.url}">${file.title}</a>
        <i class="fa fa-remove pull-right" onclick="deleteDocumentQuestionnaire('${file.id?c}', '${question.listeReponses[0].id}')"></i>
    </p><br/>
</#if>
