<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0..-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.-->
<#--You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.-->

<nav id="navbar-top" class="navbar navbar-light sticky-top bg-light flex-md-nowrap">
    <div class="d-inline-flex">
        <img class="logo-gouvernement" src="/img/backgrounds/bloc_marque_ministere.svg">
        <div class="d-block text-parent">

            <h3 class="text-enfant"><a href="/"><span class="couleur-eco">Éco</span>Cité</a></h3>

            <ul class="login navbar-nav px-3 flex-row" style="padding-left: 0px !important; font-size: 15px;">
                <li class="nav-item text-nowrap" style="padding-left: 0px;">
                    <span>Evaluation du programme</span> <span style="color: #83b93a;">Ville de demain</span>
                </li>
            </ul>
        </div>

    </div>

    <#--<ul class="login navbar-nav px-3 flex-row">-->
    <#--<li class="nav-item text-nowrap">-->
    <#--<span>Evaluation du programme ville de</span> <span style="color: #83b93a;">demain</span>-->
    <#--</li>-->
    <#--</ul>-->

    <ul class="login navbar-nav px-3 flex-row">
        <li class="nav-item text-nowrap">
            <#if user?? >
                <i class="fa fa-user-circle-o mr-1"></i>
                <span><strong>${user.firstname} ${user.lastname} </strong><#if userProfile?? ><em class="ft-s-11">(${userProfile})</em></#if></span>
            </#if>
        </li>
        <#if modeBouchonActive?? && modeBouchonActive>
            <li class="nav-item">
                <a class="c-dark" href="/bo/reauthentification/do" >
                    <span data-feather="bar-chart-2"></span>
                    <strong>Changer d'utilisateur</strong>
                </a>
            </li>
        </#if>
        <li class="nav-item">
            <a class="c-dark" href="/bo/deconnexion" >
                <span data-feather="bar-chart-2"></span>
                <strong>Déconnexion</strong>
            </a>
        </li>
    </ul>
</nav>
