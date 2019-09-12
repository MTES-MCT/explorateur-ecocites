<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<div style="text-align: center">
    <#if titrePageValidation??>
        <p style="color:#83b93a;">${titrePageValidation!} </p>
    </#if>
    <#if !evaluationLectureSeule>
        <p class="c-bleu-e">Pour envoyer vos choix pour validation, vous devez évaluer chacune des natures d'innovation pour votre action.</p>
    </#if>
</div>
<div class="card">
    <div class="card-header d-flex">
        <h5 class="align-middle">Evaluation de l'innovation</h5>
    </div>
    <div class="card-body">
        <table class="table">
            <thead>
            <tr>
                <th style="width: 40%" scope="col">
                    Nature d'innovation
                </th>
            <#if valeurs_evaluation_niveau_nature??>
                <#list valeurs_evaluation_niveau_nature as key, value>
                    <th align="center" style="text-align:center; width: 15%" scope="col">
                        <#if value??>
                        ${value[0]!}
                            <#if value[1]??>
                                <i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="${value[1]}"></i>
                            </#if>
                        </#if>
                    </th>
                </#list>
            </#if>
            </tr>
            </thead>
            <tbody>
            <#if action?? && action.questionsAvecReponseBeanList ??>
                <#list action.questionsAvecReponseBeanList as value>
                    <#if value.question??>
                    <tr>
                        <td style="width: 40%">
                            <b>${value.question.titre}</b><br/>
                            <p style="font-size:12px">${value.question.description}</p>
                        </td>
                        <#if valeurs_evaluation_niveau_nature??>
                            <#list valeurs_evaluation_niveau_nature as key, valeur_evaluation>
                                <td class="center-innovation">
                                    <input name="natureNiveau${value.question.id!?c}"
                                           class="inputSaveAuto"
                                           data-objectid="${action.id!?c}"
                                           onchange="onCheckBoxChange()"
                                           data-referenceid="${value.question.id!?c}"
                                           data-objectclass="ReponseEvaluation"
                                           type="radio"
                                           value="${key}"
                                            <#if evaluationLectureSeule>
                                               disabled
                                            </#if>
                                            <#if value.reponse?? && value.reponse.niveau?? && value.reponse.niveau == key>
                                               checked
                                            </#if>
                                           aria-label="..."
                                    >
                                </td>
                            </#list>
                        </#if>
                    </tr>
                    </#if>
                </#list>
            </#if>
            <tr>
                <td align="center" style="display: table-cell;vertical-align: middle;text-align: left; width: 40%" colspan="1">
                    <b class="text-uppercase">Niveau d'innovation global</b>
                </td>
                <td align="center" style="display: table-cell;vertical-align: middle;text-align: center; width: 30%" colspan="2">
                    <div class="form-group">
                        <select
                                name="evaluationNiveauGlobal"
                                class="inputSaveAuto form-control"
                                data-objectid="${action.id!?c}"
                                data-objectclass="Action"
                                <#if !(isAdminAccompaModif?? && isAdminAccompaModif) || etapeValide?? && etapeValide>
                                        disabled
                                </#if>
                        >
                        <#if valeurs_evaluation_niveau_global??>
                            <#list valeurs_evaluation_niveau_global as key, value>
                                <option value="${key}"
                                    <#if action.evaluationNiveauGlobal?? && key == action.evaluationNiveauGlobal >
                                        selected
                                    </#if>
                                >
                                ${value}
                                </option>
                            </#list>
                        </#if>
                        </select>
                    </div>
                </td>
                <td colspan="2" style="display: table-cell;vertical-align: middle;text-align: center; width: 30%">
                </td>
            </tr>
                <tr>
                    <td align="center" style="display: table-cell;vertical-align: middle;text-align: center; width: 100%" colspan="5">
                        <div class="evaluation_innovation">
                            <label class="pull-left" for="evaluation_innovation_commentaire">Commentaire <i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Vous pouvez si besoin expliquer votre sélection des niveaux d'innovation ici"></i></label>
                            <textarea class="form-control commentaire" id="evaluation_innovation_commentaire" rows="3"  <#if evaluationLectureSeule?? &&  evaluationLectureSeule>disabled</#if>></textarea>
                        </div>
                        <br/>
                        <#if canEdit?? && canEdit && !evaluationLectureSeule>
                            <button disabled id="button-submit-eval" type="button" class="btn btn-primary" data-toggle="modal" data-target="#confirmationRequesteValidationEvaluation">
                                Envoyer pour validation <i class="fa fas fa-envelope-o"></i>
                            </button>
                        <#elseIf canValide?? && canValide >
                            <button disabled id="button-submit-eval" type="button" class="btn btn-primary" data-toggle="modal" data-target="#confirmationValidationEvaluation">
                                Valider la soumission <i class="fa fas fa-envelope-o"></i>
                            </button>
                            <div class="footer">
                            </div>
                        <#elseIf canEdit?? && canEdit >
                            <button disabled id="button-submit-eval" type="button" class="btn btn-primary" data-toggle="modal" data-target="#confirmationAnnulationValidationEvaluation">
                                Annuler la validation
                            </button>
                        </#if>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <input type="hidden" value="evaluation_innovation" id="typeOngletInnovation">
</div>

<div class="modal fade" id="erreurValidationEvaluation" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 40%;">
        <div class="modal-content">
            <div class="modal-header bg-grey-light">
                <h5 class="modal-title">Erreur</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="alert alert-danger">

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">Retour</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="confirmationRequesteValidationEvaluation" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 40%;">
        <div class="modal-content">
            <div class="modal-header bg-grey-light">
                <h5 class="modal-title" id="">Envoyer pour validation</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="">
                    <p>Êtes-vous sûr de vouloir envoyer vos choix pour validation. Après cet envoi, vous ne pourrez plus modifier votre sélection.</p>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Annuler</button>
                <button type="button" class="btn btn-success" data-dismiss="modal" onclick="soumissionValidation('${actionId!?c}')">Confirmer</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="confirmationValidationEvaluation" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 40%;">
        <div class="modal-content">
            <div class="modal-header bg-grey-light">
                <h5 class="modal-title" id="">Validation de l'étape</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="">
                    <p>Êtes-vous sûr de vouloir valider cette étape d'évaluation?</p>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Annuler</button>
                <button type="button" class="btn btn-success" data-dismiss="modal" onclick="accepteValidation('${actionId!?c}')">Confirmer</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="confirmationAnnulationValidationEvaluation" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 40%;">
        <div class="modal-content">
            <div class="modal-header bg-grey-light">
                <h5 class="modal-title" id="">Annulation validation</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="">
                    <p>Etes-vous sûr de vouloir annuler la validation ?</p>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Annuler</button>
                <button type="button" class="btn btn-success" data-dismiss="modal" onclick="annulationValidationInnovation('${actionId!?c}')">Confirmer</button>
            </div>
        </div>
    </div>
</div>

