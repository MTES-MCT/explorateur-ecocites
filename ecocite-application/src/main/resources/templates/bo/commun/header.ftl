<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0..-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.-->
<#--You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.-->

<!-- META TAG -->
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no,shrink-to-fit=no">
<meta name="keywords" content="Entrer, des, mots, clés">
<meta name="description" content="Entrer une description">
<meta name="geo.placename" content="Paris, IDF, France">
<meta name="twitter:site" content="@compte">
<#if _csrf??>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
</#if>
<!-- TITLE PAGE -->
<title>Explorateur ÉcoCité</title>

<!-- CSS -->
<link href="/css/bo/bootstrap.css" rel="stylesheet">
<link href="/css/bo/bootstrap-grid.css" rel="stylesheet">
<link href="/css/bo/bootstrap-reboot.css" rel="stylesheet">
<link href="/css/bo/font-awesome.min.css" rel="stylesheet">
<link href="/css/bo/bootstrap-datepicker3.standalone.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/css/datatables.min.css"/>
<link href="/css/bo/style.css" rel="stylesheet">
<link href="/css/bo/select2.min.css" rel="stylesheet">
<link href="/css/bo/select2-bootstrap.css" rel="stylesheet">

<!-- Favicons -->
<link rel="shortcut icon" href="/img/favicon.ico" type="image/vnd.microsoft.icon" />

<!-- @FONT -->
<link href="/css/font.googleapis.com.lato.css" rel="stylesheet">
<link href="/css/font.googleapis.com.roboto.css" rel="stylesheet">
<!-- JS -->
<script src="/js/jquery-3.2.1.min.js"></script>
<script src="/js/bootstrap-datepicker.min.js"></script>
<script src="/js/bootstrap-datepicker.fr.js"></script>
<script src="/js/popper.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/sortable.min.js"></script>
<#--<script src="/geoportail/GpOl3.js"></script>-->

<script src="/js/openlayer/ol.js"></script>
<script src="/js/openlayer/GpPluginOpenLayers.js"></script>
<script src="/js/mapCommon.js"></script>


<script src="/js/tools.js"></script>
<script src="/js/select2.min.js"></script>
<script src='/js/bo/draggabilly.pkgd.js'></script>
<script src="/js/bo/jquery.dataTables.min.js"></script>



<#--GeoPortail-->
<#--<link rel="stylesheet" href="/geoportail/GpOl3.css" />-->
<link rel="stylesheet" href="/css/openlayer/ol.css" />
<link rel="stylesheet" href="/css/openlayer/GpPluginOpenLayers.css" />
<#--FIN - GeoPortail-->
