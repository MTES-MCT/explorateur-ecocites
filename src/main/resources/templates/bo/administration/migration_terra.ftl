<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<#-- @ftlvariable name="hasMigration" type="java.lang.Boolean" -->
<#-- @ftlvariable name="numberImages" type="java.lang.Integer" -->

<!DOCTYPE html>
<html lang="fr" xmlns="http://www.w3.org/1999/html">
<head>
    <#include "../commun/header.ftl"/>
</head>
<body class="theme-1">
        <#include "../commun/navbar.ftl">
<div class="container-fluid">
    <div class="row">
        <#include "../commun/menuGauche.ftl">
        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
            <div id="contenuBo">
                <div class="row">
                    <h1>Migration Terra</h1>
                </div>
                <#if numberImages?? && 0 < numberImages>
                    <div class="row">
                        <p>Il y a ${numberImages} images à migrer.</p>
                    </div>
                    <div class="row">
                        <form id="form" action="/bo/administration/terra/migration/one" method="post">
                            <#if _csrf??>
                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                <input type="hidden" name="_csrf_header" value="${_csrf.headerName}"/>
                            </#if>
                            <input id="submit" type="submit" class="btn btn-warning" value="Faire la migration de une image">
                        </form>
                    </div>
                    <br/>
                    <div class="row">
                        <form id="form" action="/bo/administration/terra/migration" method="post">
                            <#if _csrf??>
                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                <input type="hidden" name="_csrf_header" value="${_csrf.headerName}"/>
                            </#if>
                            <input id="submit" type="submit" class="btn btn-danger" value="Faire la migration">
                        </form>
                    </div>
                <#else>
                    <div class="row"
                        <p>Toutes les images ont déjà été migrés</p>
                    </div>
                </#if>
            </div>
        </main>
    </div>
</div>
</body>
</html>
