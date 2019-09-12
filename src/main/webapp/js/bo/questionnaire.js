
/*
 * Explorateur Écocités
 * Copyright (C) 2019 l'État, ministère chargé du logement
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

function onChangeAutre(element) {
	var elementTextAutre = $(element).closest(".form-group").find(".fieldTextAutre");
	if(element.checked){
		$(elementTextAutre).attr("disabled", false).css("pointer-events", "");
	} else {
		$(elementTextAutre).val("");
		$(elementTextAutre).attr("disabled","disabled").css("pointer-events", "none");
	}
}


function onChangeExplicatif(element) {
	var elementTextExplicatif = $(element).closest(".form-group").find(".fieldTextExplicatif");
	if($(element).attr("type")==="radio" ){
		if(element.checked && !($(element).val()==="non concerné")){
			$(elementTextExplicatif).show()
		} else {
			$(elementTextExplicatif).val("");
			$(elementTextExplicatif).hide();
		}
	}
}

function onChangeShowFieldTextAssocier(element) {
	var idData = $(element).data("idasso");
	var elementFieldTextAssocier = $(element).closest(".form-group").find(".fieldTextCheckBoxSecondaire"+idData);
	if(element.checked){
		$(elementFieldTextAssocier).attr("disabled", false).css("pointer-events", "");
	} else {
		$(elementFieldTextAssocier).val("");
		$(elementFieldTextAssocier).attr("disabled", "disabled").css("pointer-events", "none");
	}
}

function soumissionFormQuestionnaire(cible, objetType, codeQuestionnaire, objectId){
	$(".text-danger").hide().text("");
	$(".is-invalid").removeClass("is-invalid");
	$("#formulaireError").hide();
	var modalTarget = undefined;
	if (cible === "soumission") {
		modalTarget = "#confirmationSoumissionQuestionnaire";
	} else if (cible === "validation") {
		modalTarget = "#confirmationValidationQuestionnaire";
	}
	if (modalTarget) {
		$.ajax({
			type: "POST",
			url: "/bo/" + objetType + "s/questionnaire/" + cible,
			data: $("#questionnaireForm").serialize(),
			contentType: "application/x-www-form-urlencoded",
			success: function (data) {
				hideModelThen(function () {
					var flag = true;
					if (data && data.globalErrors && data.globalErrors.length > 0) {
						$('html,body').scrollTop(0);
						$("#errorModalMessage").html(data.globalErrors[0].defaultMessage);
						$("#erreurDroitUtilisateur").modal('show');
						flag = false;
					}
					if (data && data.fieldErrors && data.fieldErrors.length > 0) {
						for (var i = 0, l = data.fieldErrors.length; i < l; i++) {
							$("[name^='" + data.fieldErrors[i].field + "']")
								.addClass("is-invalid")
								.closest(".form-group")
								.find(".text-danger").text(data.fieldErrors[i].defaultMessage).show();
						}
						$('html,body').scrollTop(0);
						$("#formulaireError").show();
						flag = false;
					}
					if (flag) {
						loadEvaluationFacteur(codeQuestionnaire, objectId);
						$(".modal-backdrop").remove();
						if (updateTabState) {
							updateTabState();
						}
					}
				}, modalTarget)();
			},
			error: function () {

			}
		});
	}
}

function annulationValidation(objetType, codeQuestionnaire, objectId){
	$(".text-danger").hide().html("");
	$(".is-invalid").removeClass("is-invalid");
	$("#formulaireError").hide();
	$.ajax({
		type: "POST",
		url: "/bo/" + objetType + "s/questionnaire/annulationValidation",
		data : $("#questionnaireForm").serialize(),
		contentType: "application/x-www-form-urlencoded",
		success: function(data) {
			hideModelThen(function () {
				$('html,body').scrollTop(0);
			},"#confirmationAnnulationValidationQuestionnaire")();
			if(data && data.globalErrors && data.globalErrors > 0) {
				$("#erreurDroitUtilisateur").modal('show');
			} else {
				loadEvaluationFacteur(codeQuestionnaire, objectId);
				$(".modal-backdrop").remove();
				if (updateTabState) {
					updateTabState();
				}
			}
		},
		error: function() {

		}
	});
}


