<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<#-- @ftlvariable name="indicateur" type="com.efficacity.explorateurecocites.beans.model.Indicateur" -->


<div class="title-container">
    <div class="title">
        ${indicateur.nom}
    </div>
</div>
<div class="content">
    <div class="row">
        <label for="indicateur.description"><strong>Définition</strong></label> </br>
        <textarea  name="indicateur.description" class="form-control" disabled style="height: 125px">${indicateur.description!}</textarea>
    </div>
    <div class="row">
        <label for="indicateur.methodeCalcule"><strong>Méthode de calcul</strong></label> </br>
        <textarea  name="indicateur.description" class="form-control" disabled style="height: 125px">${indicateur.methodeCalcule!}</textarea>
    </div>
    <div class="row">
        <div class="col-6 p-l-0">
            <label for="indicateur.origine"><strong>Origine</strong></label> </br>
            <input type="text" name="indicateur.description" value="${indicateur.origineEnumLibelle!}" class="form-control" disabled/>
        </div>
        <div class="col-6 p-r-0">
            <label for="indicateur.typeReponseAttendue"><strong>Type de mesure</strong></label> </br>
            <input type="text" name="indicateur.typeReponseAttendue" value="${indicateur.typeMesureEnum.libelle!}" class="form-control" disabled/>
        </div>
    </div>
    <div class="row m-t-10">
        <label for="uniteIndicateur"><strong>Unité</strong></label>
        <i class="fa fa-info-circle m-l-10" data-toggle="tooltip" data-placement="top" title="Si l’unité voulue n’est pas proposée, vous pouvez contacter l’accompagnateur pour qu’elle soit ajoutée."></i>
        <br/>
        <select id="uniteIndicateur" name="uniteIndicateur" class="form-control">
            <option value="">Choisissez</option>
            <#list indicateur.unite?split(";") as unite>
                <option value="${unite}">${unite}</option>
            </#list>
        </select>
        <p id="unite-error" class="message-danger" style="display : none; color:red">Veuillez choisir une unité.</p></br>
    </div>
    <div class="row m-t-10">
        <#if  (indicateur.posteCalcule?? && indicateur.posteCalcule?length > 0)>
            <label for="calculeIndicateur"><strong>Poste de calcul</strong></label>
            <i class="fa fa-info-circle m-l-10" data-toggle="tooltip" data-placement="top" title="Si le poste de calcul voulu n’est pas proposé, vous pouvez contacter l’accompagnateur pour qu’il soit ajouté."></i>
            </br>
            <select id="calculeIndicateur" name="calculeIndicateur" class="form-control">
                <option value="">Choisissez</option>
                <#list indicateur.posteCalcule?split(";") as calcul>
                    <option value="${calcul}">${calcul}</option>
                </#list>
            </select>
            <p id="calcule-error" class="message-danger" style="display : none; color:red">Veuillez choisir un poste de calcul.</p></br>
        </#if>
    </div>
    <div class="row m-t-10">
        <a class="btn btn-lg btn-success btn-block ft-s-16 " data-objectid="${indicateur.id?c}" onclick="ajoutIndicateurInfo(this);">Ajouter l'indicateur</a>
    </div>
</div>


