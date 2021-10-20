<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0..-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.-->
<#--You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.-->

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
