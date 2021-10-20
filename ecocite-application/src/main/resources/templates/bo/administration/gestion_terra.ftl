<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0..-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.-->
<#--You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.-->

<#-- @ftlvariable name="currentMenu" type="java.lang.String" -->
<#-- @ftlvariable name="currentTab" type="java.lang.String" -->
<#-- @ftlvariable name="jobQueue" type="java.util.List<com.efficacity.explorateurecocites.beans.biz.MediaModificationBean>" -->

<!DOCTYPE html>
<html lang="fr">
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
                    <h1 class="ft-700">Liste de mise à jour Terra</h1>
                    <div class="col-3 my-4 ml-auto">
                        <form method="post" action="/bo/administration/terra/refreshAll">
                            <#if _csrf??>
                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                <input type="hidden" name="_csrf_header" value="${_csrf.headerName}"/>
                            </#if>
                            <button type="submit" class="collapsed btn btn-outline-danger btn-block" >
                                <strong>Mettre à jour toutes les images</strong>
                                <i class="fa fa-refresh mr-1"></i>
                            </button>
                        </form>
                    </div>
                </div>
                <div class="row">
                    <#if jobQueue?? && jobQueue?hasContent>
                        <div class="col-lg-12 mb-4" id="liste-pagination-div">
                            <table id="liste-pagination"
                                   class="table table-striped table-bordered nowrap"
                                   data-page-length='25' width="100%" cellspacing="0">
                                <thead>
                                    <tr>
                                        <th>Type d'objet</th>
                                        <th style="width: 35%">Objet modifié</th>
                                        <th>Dernière modification</th>
                                        <th>Statut (nombre d'essai)</th>
                                        <th colspan="2"></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <#list jobQueue as job>
                                    <tr class="cursorPointer">
                                        <td class="td-limit">${job.typeObject}</td>
                                        <td class="td-limit"><a href="${job.url}">${job.relatedObject}</a></td>
                                        <td class="td-limit">${job.lastModified}</td>
                                        <td class="td-limit ${job.statusCSSClass}">${job.status} (${job.numberTry!0})</td>
                                        <td class="td-limit">
                                            <form action="/bo/administration/terra/job/${job.id?c}" method="post">
                                                <#if _csrf??>
                                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                                    <input type="hidden" name="_csrf_header" value="${_csrf.headerName}"/>
                                                </#if>
                                                <button class="collapsed btn btn-outline-primary btn-block">
                                                    <i class="fa fa-refresh"></i>
                                                </button>
                                            </form>
                                        </td>
                                        <td class="td-limit">
                                            <form action="/bo/administration/terra/job/delete/${job.id?c}" method="post">
                                                <#if _csrf??>
                                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                                    <input type="hidden" name="_csrf_header" value="${_csrf.headerName}"/>
                                                </#if>
                                                <button class="collapsed btn btn-outline-primary btn-block">
                                                    <i class="fa fa-trash-o"></i>
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                    <#else>
                        <p>Toutes les images sont à jour avec Terra</p>
                    </#if>
                </div>
            </div>
        </main>
    </div>
</div>
<script type="application/javascript">
    <#if currentMenu??>
        $(".sidebar .active").removeClass("active");
        $(".sidebar #${currentMenu}").addClass("active");
        <#if currentTab??>
            $(".sidebar").find("#${currentTab}").addClass("active");
        </#if>
    <#else>
    $(".sidebar #accueil").addClass("active");
    </#if>
    $(function () {
        appliqueDataTable();
    });
</script>
</body>
</html>
