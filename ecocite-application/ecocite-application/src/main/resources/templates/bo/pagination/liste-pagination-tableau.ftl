<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0..-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.-->
<#--You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.-->

<#-- @ftlvariable name="objectType" type="java.lang.String" -->
<#-- @ftlvariable name="usesModal" type="java.lang.Boolean" -->
<#-- @ftlvariable name="modalUrl" type="java.lang.String" -->
<#-- @ftlvariable name="colonnes" type="java.util.List<java.lang.String>" -->
<#-- @ftlvariable name="data" type="java.util.Map<java.lang.Long, java.util.List<java.lang.String>>" -->
<#-- @ftlvariable name="hasExportAction" type="java.lang.Boolean" -->
<#-- @ftlvariable name="exportUrl" type="java.lang.String" -->
<#-- @ftlvariable name="date" type="java.lang.String" -->

<table id="liste-pagination"
       class="table table-striped table-bordered nowrap"
       data-page-length='25' width="100%" cellspacing="0" >
    <thead>
        <tr>
            <#list colonnes as col>
                <th <#if col?counter == 1>style="width: 35%;"</#if>>${col!}</th>
            </#list>
            <#if hasExportAction?? && hasExportAction && exportUrl??>
                <th class="" style="width: 1px;"></th>
            </#if>
        </tr>
    </thead>
    <tbody>
        <#if !objectType??>
            <div class="alert alert-danger"><strong>Erreur</strong> Le type d'object n'est pas renseigné.</div>
        <#else>
            <#if data??>
                <#list data as key, value>
                    <tr id="tr-${key!?c}"
                        class="cursorPointer"
                        <#if usesModal?? && usesModal>
                            onclick="openModalObject('${key!?c}', '${modalUrl!}');"
                        <#else>
                            onclick="document.location = '/bo/${objectType}/edition/${key!?c}';"
                        </#if>>
                        <#list value as v>
                            <td class="td-limit">
                                ${v!?replace(";",", ")}
                            </td>
                        </#list>
                        <#if hasExportAction?? && hasExportAction && exportUrl??>
                            <td class="td-limit"><a href="${exportUrl}${key!?c}" download="${objectType}-${key!?c}-${date!}.odt"><i class="fa fa-download mr-1"></i></a></td>
                        </#if>
                    </tr>
                </#list>
            </#if>
        </#if>
    </tbody>
</table>
