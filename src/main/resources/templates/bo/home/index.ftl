<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<!DOCTYPE html>
<html lang="fr">
<head>
    <#include "../commun/header.ftl"/>
</head>

<body class="theme-1">

<#include "../commun/navbar.ftl">

<div class="container-fluid">
    <div class="row">

        <#assign currentMenu = "accueil" >
        <#include "../commun/menuGauche.ftl">

        <!-- DASHBOARD WRAPPER -->
        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">

            <div id="contenuBo">

                <#--TODO - Contenu en fonction du profil utilisateur-->
                <#--Porteur d'action -->
                <#include "../actions/accueil.ftl">

            </div>
        </main>
    </div>
</div>

<script type="application/javascript">
    <#if currentMenu??>
        $( ".sidebar .active").removeClass( "active" );
        $( ".sidebar #${currentMenu}" ).addClass( "active" );
    <#else>
        $( ".sidebar #accueil" ).addClass( "active" );
    </#if>
</script>

</body>

</html>
