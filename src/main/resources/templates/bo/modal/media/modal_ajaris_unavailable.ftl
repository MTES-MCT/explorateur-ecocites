<#ftl outputFormat="HTML">
<#--
Explorateur Écocités
Copyright (C) 2019 l'État, ministère chargé du logement
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
-->

<#-- @ftlvariable name="message" type="java.lang.String" -->
<#-- @ftlvariable name="idTitre" type="java.lang.Long" -->
<#assign idTitre="imageunavailable" />
<#assign message="Cette image ne semble pas être disponible sur Terra." />
<#assign hasButton=true />
<#assign buttonContent="<button type=\"button\" class=\"btn btn-block btn-wrap-text btn-danger\" onclick=\"deleteMedia('${idMedia?c}', '${idObject?c}')\">Supprimer</button>"/>
<#include "../error_modal.ftl" />
