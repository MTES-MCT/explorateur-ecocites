<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0..-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.-->
<#--You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.-->

<#if questionnaire?? >
    <form id="questionnaireForm">
        <p id="formulaireError" style="display: none;">Le formulaire comporte des erreurs, veuillez vérifier votre saisie</p>
        <p id="formulaireErrorGenerique" style="display: none;"></p>
        <input type="hidden" name="objetId" value="${objetId?c}"/>
        <input type="hidden" name="codeQuestionnaire" value="${codeQuestionnaire}"/>
        <#if titrePageValidation??>
            <div style="color:#83b93a;" class="head">
                ${titrePageValidation!}
            </div>
        <#else>
            <div class="head c-bleu-e">
                Les réponses apportées aux questions sont sauvegardées automatiquement au fur et à mesure du remplissage du questionnaire (même si vous quittez le questionnaire sans valider).<br/>
                Pour pouvoir valider cette étape, vous devez répondre à l'ensemble des questions obligatoires.
            </div>
        </#if>
        <#list questionnaire?keys as categorie>
            <#include "categorie.ftl"/>
        </#list>
        <#if canValide?? && canValide >
            <div class="" style="max-width: 280px; margin: 0 auto; display: block">
                <button id="button-submit-indic"  type="button" class="btn btn-primary " data-toggle="modal" data-target="#confirmationValidationQuestionnaire">
                    Valider le questionnaire <i class="fa fas fa-envelope-o"></i>
                </button>
            </div>
        <#elseIf (canEdit?? && canEdit)>
            <div class="" style="max-width: 280px; margin: 0 auto; display: block">
                <button id="button-submit-indic" type="button" class="btn btn-primary " data-toggle="modal" data-target="#confirmationAnnulationValidationQuestionnaire">
                    Annuler la validation
                </button>
            </div>
        </#if>
    </form>

<div class="modal fade" id="confirmationSoumissionQuestionnaire" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 40%;">
        <div class="modal-content">
            <div class="modal-header bg-grey"><h5 class="modal-title" id="">Envoyer pour validation</h5>
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
                <button type="button" class="btn btn-success" data-dismiss="modal" onclick="soumissionFormQuestionnaire('soumission', '${objetType}', '${codeQuestionnaire}', '${objetId?c}')">Confirmer</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade modaleSoumissionQuestionnaire" id="confirmationValidationQuestionnaire" tabindex="-1" role="dialog" aria-hidden="true">
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
                <button type="button" class="btn btn-success" data-dismiss="modal" onclick="soumissionFormQuestionnaire('validation', '${objetType}', '${codeQuestionnaire}', '${objetId?c}')">Confirmer</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade modaleSoumissionQuestionnaire" id="confirmationAnnulationValidationQuestionnaire" tabindex="-1" role="dialog" aria-hidden="true">
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
                <button type="button" class="btn btn-success" data-dismiss="modal" onclick="annulationValidation('${objetType}', '${codeQuestionnaire}', '${objetId?c}')">Confirmer</button>
            </div>
        </div>
    </div>
</div>

</#if>
