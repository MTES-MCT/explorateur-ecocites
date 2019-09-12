<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<nav id="navbar-top" class="navbar navbar-light sticky-top bg-light flex-md-nowrap">
    <div class="navbar-brand col-sm-3 col-md-2 mr-0 logo-company">

        <span>
            <a href="/">
                <img src="/img/brand/logo_explorateur.png" width="200px" style="margin-bottom: 5px; margin-right: 20px"  />
            </a>
            <#--<span>evaluation du programme ville de</span> <span style="color: #83b93a;">demain</span>-->
        </span>

        <ul class="login navbar-nav px-3 flex-row" style="padding-left: 0px !important; font-size: 15px;">
            <li class="nav-item text-nowrap" style="padding-left: 5px;">
                <span>Evaluation du programme</span> <span style="color: #83b93a;">Ville de demain</span>
            </li>
        </ul>

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
