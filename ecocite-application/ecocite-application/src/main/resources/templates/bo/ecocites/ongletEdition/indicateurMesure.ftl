<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0..-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.-->
<#--You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.-->

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
            <#if (listAssoIndicateurEcocite?? && listAssoIndicateurEcocite?size >0)>
                <#list listAssoIndicateurEcocite as assoIndicateurObjetBean>
                    <div class="row assoIndicateurEcocite cursorPointer" data-objectid="${assoIndicateurObjetBean.id?c}" onclick="loadIndicateurSaisieMesure(this);">
                        <div class="col-2">
                            <div class="iconeIndicateur ${assoIndicateurObjetBean.indicateur.nature}"><div></div></div>
                        </div>
                        <div class="col-8">
                            <span class='nomIndicateurCour'>${assoIndicateurObjetBean.indicateur.nomCourt}</span>
                            <span class='nomIndicateurLong'>Unité : ${assoIndicateurObjetBean.unite}</span>
                            <#if assoIndicateurObjetBean.posteCalcule?? >
                                <span class='nomIndicateurLong'>Poste de calcul : ${assoIndicateurObjetBean.posteCalcule}</span>
                            </#if>
                        </div>
                        <div class="col-2">
                            <a class="c-dark cursorPointer" onclick="modalIndicateur('${assoIndicateurObjetBean.idIndicateur?c}');" ><i class="fa fas fa-search"></i></a>
                        </div>
                    </div>
                </#list>
            </#if>
        </div>
    </div>
    <div id="ajoutMesure" class="col-lg-8 col-12">

    </div>
</div>
<div class="footer"></div>
<#include "../../modal/pop_up_validation_mesure.ftl" />
<div class="modal fade" id="modalAjoutIndicateur" tabindex="-1" role="dialog" aria-labelledby="modalAjoutIndicateurTitle" aria-hidden="true">
</div>


