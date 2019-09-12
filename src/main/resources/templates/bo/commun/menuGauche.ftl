<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->


<!-- ASIDE NAV -->
<aside class="col-md-3 col-lg-2 d-none d-md-block bg-dark sidebar position-fixed" style="z-index: 1039">
    <div class="sidebar-sticky">
        <#if userProfileCode?? && (userProfileCode=="ADMIN_TECH")>
            <ul class="nav flex-column">
                <li class="nav-item">
                    <a id="accueil" class="nav-link" href="/bo/admin/tech"><i class="fa fa-tachometer" style="font-size: 1.3em; min-width: 25px;"></i><span class="m-l-10">Accueil</span><span class="sr-only">(current)</span></a>
                </li>
            </ul>
        <#else>
                <ul class="nav flex-column sidebar-menu">
                <li class="nav-item">
                    <a id="accueil" class="nav-link" href="/bo"><i class="fa fa-tachometer" style="font-size: 1.3em; min-width: 25px;"></i><span class="m-l-10">Accueil</span><span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a id="actions" class="nav-link" href="/bo/actions"><span class="fa fa-lightbulb-o" style="font-size: 1.3em; min-width: 25px;"></span><span class="m-l-10">Actions</span></a>
                </li>
                <li class="nav-item">
                    <a id="ecocites" class="nav-link" href="/bo/ecocites"><span class="fa fa-building" style="font-size: 1.3em; min-width: 25px;"></span><span class="m-l-10">EcoCités</span></a>
                </li>
                <li class="nav-item">
                    <a id="indicateurs" class="nav-link" href="/bo/indicateurs"><span class="fa fa-bar-chart" style="font-size: 1.3em; min-width: 25px;"></span><span class="m-l-10">Indicateurs</span></a>
                </li>
                <li class="nav-item">
                    <a id="contacts" class="nav-link" href="/bo/contacts"><span class="fa fa-user" style="font-size: 1.3em; min-width: 25px;"></span><span class="m-l-10">Contacts</span></a>
                </li>
                <li class="nav-item dropdown">
                    <a id="export_rapport" class="nav-link dropdown-toggle vertical-center-etiquette" data-toggle="dropdown"
                       href="#" role="button" aria-haspopup="true" aria-expanded="false">
                        <span class="fa fa-file-text-o" style="font-size: 1.3em; min-width: 25px;"></span>
                        <span class="m-l-10">Rapports</span>
                    </a>
                    <div class="dropdown-menu">
                        <#if userProfileCode?? && !(userProfileCode=="PORTEUR_ACTION")>
                            <a id="ecocites" class="dropdown-item" href="/bo/rapports/ecocites">EcoCités</a>
                        </#if>
                        <a id="actions" class="dropdown-item" href="/bo/rapports/actions">Action</a>
                    </div>
                </li>
                <#if userProfileCode?? && (userProfileCode=="ADMINISTRATEUR" || userProfileCode=="ACCOMPAGNATEUR")>
                    <li class="nav-item dropdown">
                        <a id="administration" class="nav-link dropdown-toggle vertical-center-etiquette" data-toggle="dropdown"
                           href="#" role="button" aria-haspopup="true" aria-expanded="false">
                            <span class="fa fa-cog" style="font-size: 1.3em; min-width: 25px;"></span>
                            <span class="m-l-10">Administration</span>
                        </a>
                        <div class="dropdown-menu">
                            <a id="business" class="dropdown-item" href="/bo/administration/business">Affaires</a>
                            <#if userProfileCode=="ADMINISTRATEUR">
                                <a id="axes" class="dropdown-item" href="/bo/administration/axes" >Axes d'intervention</a>
                                <a id="domaines" class="dropdown-item" href="/bo/administration/domaines">Domaines d'action</a>
                                <a id="finalites" class="dropdown-item" href="/bo/administration/finalites" >Finalités</a>
                                <a id="objectifs" class="dropdown-item" href="/bo/administration/objectifs">Objectifs de la ville durable</a>
                                <a id="ingenieries" class="dropdown-item" href="/bo/administration/ingenieries" >Natures d'ingénierie</a>
                                <a id="missions" class="dropdown-item" href="/bo/administration/missions">Types de mission</a>
                                <a id="innovations" class="dropdown-item" href="/bo/administration/innovations">Natures d'innovation</a>
                                <a id="wording_static" class="dropdown-item" href="/bo/administration/wording/static">Gestion des libellés publics statiques</a>
                                <a id="wording_dynamic" class="dropdown-item" href="/bo/administration/wording/dynamic">Gestion des libellés publics dynamiques</a>
                                <a id="ajaris" class="dropdown-item" href="/bo/administration/terra">Synchronisation des images Terra</a>
                                <#--<div class="dropdown-divider"></div>-->
                            </#if>
                        </div>
                    </li>
                </#if>
            </ul>
        </#if>

        <footer class="c-white t-align-center m-b-10">
            <div class="fs-13"><b>Si vous avez besoin d'aide :</b></div>
            <div class="fs-10">
                <a class="c-white " href="mailto:Support_Evaluation_Ecocité@technopolis-group.com">
                    <b><u>Support_Evaluation_Ecocité@ technopolis-group.com</u></b>
                </a>
            </div>
            <div class="fs-13 m-b-10"><b>Pour en savoir plus sur la méthodologie :</b></div>
            <div class="row">
                <div class="col-8" style="height: 16vh">
                    <div class="row" style="height: 50%">
                        <a href="https://www.efficacity.com/realisation/elaboration-de-la-methodologie-devaluation-ex-post-du-pia-ville-de-demain-demarche-ecocite/"
                                class="d-block logo-sidebar logo-cdc" ></a>
                    </div>
                    <div class="row" style="height: 50%">
                        <div class="col-8" style="height: 100%">
                            <div class="row" style="height: 100%">
                                <a href="http://www.efficacity.com/efficacity-et-la-caisse-des-depots-signent-un-partenariat-pluriannuel/"
                                   class="d-block logo-sidebar logo-efficacity" ></a>
                            </div>
                        </div>
                        <div class="col-4" style="height: 100%">
                            <div class="row" style="height: 100%">
                                <a href="http://www.efficacity.com/efficacity-et-la-caisse-des-depots-signent-un-partenariat-pluriannuel/"
                                   class="d-block logo-sidebar logo-investir-avenir" ></a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-4" style="height: 16vh">
                    <div class="row" style="height: 100%">
                        <a href="http://www.efficacity.com/efficacity-et-la-caisse-des-depots-signent-un-partenariat-pluriannuel/"
                           class="d-block logo-sidebar logo-mtes" ></a>
                    </div>
                </div>
            </div>
        </footer>
    </div>
</aside>
