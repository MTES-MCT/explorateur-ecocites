<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0..-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.-->
<#--You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.-->

<div class="modal fade" id="erreurValidationCategorisationIndicateur" tabindex="-1" role="dialog" aria-hidden="true">
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

<div class="modal fade" id="confirmationRequesteValidationCategorisationIndicateur" tabindex="-1" role="dialog" aria-hidden="true">
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
                    <p>Êtes-vous sûr de vouloir envoyer vos choix pour validation. Après cet envoi, vous ne pourrez plus modifier votre sélection. </p>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Annuler</button>
                <button type="button" class="btn btn-success" data-dismiss="modal" onclick="soumissionValidationCategorisationAction('${typeOnglet}', '${actionId!?c}')">Confirmer</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modalCategorisationIndicateurNonCompleteInv" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 40%;">
        <div class="modal-content">
            <div class="modal-header bg-grey-light">
                <h5 class="modal-title" id="">Veuillez remplir toutes les informations nécessaires</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="">
                    <p>
                        Pour pouvoir envoyer vos choix pour validation, vous devez choisir au minimum :<br/>
                        <blockquote>- Une étiquette Domaine d'action principal</blockquote>
                        <blockquote>- Une étiquette Objectif de la ville durable majeur</blockquote>
                        <blockquote>- Un indicateur de réalisation</blockquote>
                        <blockquote>- Un indicateur de résultat</blockquote>
                        <blockquote>- Un indicateur d'impact.</blockquote>
                    </p>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal" >OK</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modalCategorisationIndicateurNonCompleteIng" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 40%;">
        <div class="modal-content">
            <div class="modal-header bg-grey-light">
                <h5 class="modal-title" id="">Veuillez remplir toutes les informations nécessaires</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="">
                    <p>
                        Pour pouvoir envoyer vos choix pour validation, vous devez choisir au minimum :<br/>
                        <blockquote>- Une étiquette Type de mission d'ingénierie</blockquote>
                        <blockquote>- Une étiquette Domaine d'action principal</blockquote>
                        <blockquote>- Une étiquette Objectif de la ville durable majeur </blockquote>
                    </p>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal" >OK</button>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="modalCategorisationIndicateurNonCompleteIngInv" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 40%;">
        <div class="modal-content">
            <div class="modal-header bg-grey-light">
                <h5 class="modal-title" id="">Veuillez remplir toutes les informations nécessaires</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="">
                    <p>
                        Pour pouvoir envoyer vos choix pour validation, vous devez choisir au minimum :<br/>
                        <blockquote>- Une étiquette Type de mission d'ingénierie</blockquote>
                        <blockquote>- Une étiquette Domaine d'action principal</blockquote>
                        <blockquote>- Une étiquette Objectif de la ville durable majeur </blockquote>
                        <blockquote>- Un indicateur de réalisation</blockquote>
                        <blockquote>- Un indicateur de résultat</blockquote>
                        <blockquote>- Un indicateur d'impact.</blockquote>
                    </p>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal" >OK</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="confirmationValidationCategorisationIndicateur" tabindex="-1" role="dialog" aria-hidden="true">
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
                <button type="button" class="btn btn-success" data-dismiss="modal" onclick="accepteValidationCategorisationAction('${typeOnglet}', '${actionId!?c}')">Confirmer</button>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="confirmationAnnulationValidationCategorisationIndicateur" tabindex="-1" role="dialog" aria-hidden="true">
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
                <button type="button" class="btn btn-success" data-dismiss="modal" onclick="annulationValidationCategorisationAction('${typeOnglet}', '${actionId!?c}')">Confirmer</button>
            </div>
        </div>
    </div>
</div>
