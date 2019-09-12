<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<div class="body flex-column flex-lg-row" id="indicateurListe">
    <div class="col-lg-4 col-12">
        <div class="title-container">
            <div class="title">
                Indicateurs choisis
            </div>
            <#if titrePageValidation??>
                <p style="color:#83b93a;">${titrePageValidation!}</p>
            </#if>
        </div>
        <div class="content">
            <#if (listAssoIndicateurAction?? && listAssoIndicateurAction?size >0)>
                <#list listAssoIndicateurAction as assoIndicateurActionBean>
                    <div class="row assoIndicateurAction cursorPointer" data-objectid="${assoIndicateurActionBean.id?c}" onclick="loadIndicateurSaisieMesure(this);">
                        <div class="col-2">
                            <div class="iconeIndicateur ${assoIndicateurActionBean.indicateur.nature}"><div></div></div>
                        </div>
                        <div class="col-8">
                            <span class='nomIndicateurCour'>${assoIndicateurActionBean.indicateur.nomCourt}</span>
                            <span class='nomIndicateurLong'>Unité : ${assoIndicateurActionBean.unite}</span>
                            <#if assoIndicateurActionBean.posteCalcule?? >
                                <span class='nomIndicateurLong'>Poste de calcul : ${assoIndicateurActionBean.posteCalcule}</span>
                            </#if>
                        </div>
                        <div class="col-2">
                            <a class="c-dark cursorPointer" onclick="modalIndicateur('${assoIndicateurActionBean.idIndicateur?c}');" ><i class="fa fas fa-search"></i></a>
                        </div>
                    </div>
                </#list>
            </#if>
        </div>
    </div>
    <div id="ajoutMesure" class="col-lg-8 col-12"></div>
</div>
<div class="footer"></div>
<#include "../../modal/pop_up_validation_mesure.ftl" />
<div class="modal fade" id="modalAjoutIndicateur" tabindex="-1" role="dialog" aria-labelledby="modalAjoutIndicateurTitle" aria-hidden="true">
</div>



