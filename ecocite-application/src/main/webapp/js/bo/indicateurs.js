/*
 * Explorateur Écocités
 * Copyright (C) 2019 l'État, ministère chargé du logement
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.
 *
 * You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.
 */

function modalEditionObjetReadyIndicateur(domains, selectedDomains, objectifs, selectedObjectifs) {
	var selectDomain = $('#select2DA');
	var addAddDomain = $('#addAllDA');
	selectDomain.select2({
		templateSelection: formatState,
		templateResult: formatResult,
		escapeMarkup: function(m) {
			return m;
		},
		data: domains
	});
	if (selectedDomains) {
		selectDomain.val(selectedDomains);
		selectDomain.trigger('change');
	}

	selectDomain.on('change.select2', function () {
		$('[data-toggle="tooltip"]').tooltip()
	});
	addAddDomain.click(function () {
		selectDomain.val(domains.map(function (domain) {
			return domain.id;
		}));
		selectDomain.trigger('change')
	});

	var addAddObjectif = $('#addAllO');
	var selectObjectif = $('#select2O');
	selectObjectif.select2({
		templateSelection: formatState,
		templateResult: formatResult,
		escapeMarkup: function(m) {
			return m;
		},
		data: objectifs
	});
	if (selectedObjectifs) {
		selectObjectif.val(selectedObjectifs);
		selectObjectif.trigger('change');
	}
	selectObjectif.on('change.select2', function () {
		$('[data-toggle="tooltip"]').tooltip()
	});
	addAddObjectif.click(function () {
		selectObjectif.val(objectifs.map(function (objectif) {
			return objectif.id;
		}));
		selectObjectif.trigger('change')
	});

	var selectTags = $(".select-tags");
	selectTags.select2({
		tags: true,
		dropdownParent: $("#modalAjoutIndicateur")
	});
	$('[data-toggle="tooltip"]').tooltip()
}

function modalEditionObjetReady(id) {
	$('[data-toggle="tooltip"]').tooltip();
	deleteObjet = createDeleteFunction(id);
}

function formatState (state) {
	if (!state.id) {
		return escapeHTML(state.text);
	}
	return $(
		'<span>' + escapeHTML(state.text) + ' <i class="fa fa-info-circle" style="color: ' + state.color + '" title="' + escapeHTML(state.description) + '" data-toggle="tooltip" data-placement="top"></i>' + '</span>'
	);
}

function formatResult (state) {
	if (!state.id) {
		return escapeHTML(state.text);
	}
	return $(
		'<span style="color: ' + state.id + '">' + escapeHTML(state.text) + '</span>'
	);
}

function hideModal() {
	$('.modal').modal('hide');
}

function deleteObjetIndicateur(indicateurId) {
	$.ajax({
		method: 'DELETE',
		url: "/bo/indicateurs/supprimeIndicateur/" + indicateurId,
		contentType: "application/json",
		dataType: 'json',
		success: function (data) {
			if(data && data.general_error){
				$('#erreurSuppressionIndicateur .alert-danger').html(data.general_error);
				$('#erreurSuppressionIndicateur').modal('show');
			} else {;
				$('.modal').modal('hide');
				soumissionForm("indicateurs/filtrer");
			}
		},
		error: function (data) {
		}
	});
}

function createDeleteFunction(id) {
	return function () {
		deleteObjetIndicateur(id);
	}
}

function reloadOngletIndicateur() {
	location.reload();
}

function getFieldValue(elt, validFun, res) {
	var name = elt.attr("name");
	if (name) {
		var value = elt.val();
		if (validFun(value, elt)) {
			elt.parent().find(".text-danger").hide();
			res[name] = value
		} else {
			elt.parent().find(".text-danger").html("Ce champ est obligatoire");
			elt.parent().find(".text-danger").show();
			if (!res.errors) {
				var parentPanel = elt.closest("[role=tabpanel");
				parentPanel.addClass("active show").siblings().removeClass("active show");
				$("a[href=\"#" + parentPanel.attr("id")+"\"]").addClass("active show").parent().siblings().children("a").removeClass("active show")
			}
			res.errors = true;
		}
	}
}

function normalFieldValid(value, elt) {
	return !(elt.prop("required") && value === "")
}

function arrayFieldValid(value, elt) {
	return !(elt.prop("required") && (!value || value.length < 1))
}

function getAllModalValueIndicateur() {
	var res = {errors: false};
	$("#modalAjoutPresentationTab").find(":input").map(function (i, el) {
		getFieldValue($(el), normalFieldValid, res);
	});
	getFieldValue($("#select2DA"), arrayFieldValidDomain, res);
	getFieldValue($("#select2O"), arrayFieldValidObjectifs, res);
	getFieldValue($("#selectOrigin"), arrayFieldValid, res);
	getFieldValue($("#selectUnite"), arrayFieldValid, res);
	getFieldValue($("#selectPosteCalcul"), arrayFieldValid, res);

	return res;
}

function updateObject(indicateurId) {
	var res = getAllModalValueIndicateur();
	if (!res.errors) {
		$.ajax({
			method: 'POST',
			data: JSON.stringify(res),
			url: "/bo/indicateurs/modifIndicateur/" + indicateurId,
			contentType: "application/json",
			success: function () {
				var locationUrl = $("#locationreloadhelper").val();
				if (locationUrl) {
					soumissionForm(locationUrl)
					$('.modal').modal('hide');
				} else {
					location.reload()
				}
			},
			error: function (data) {
			}
		});
	}
}

function createObjet(url) {
	var res = getAllModalValueIndicateur();
	if (!res.errors) {
		$.ajax({
			method: 'POST',
			data: JSON.stringify(res),
			url: url,
			contentType: "application/json",
			success: function (data) {
				hideModelThen(function () {updateLeftAndMiddleTab(data)})()
			},
			error: function (data) {
			}
		});
	}
}

function updateLeftAndMiddleTab(idToSelect) {
	var idObject = $("#objectId").val();
	var typeIndicateur = $("#typeIndicateur").val();
	getIndicateursFiltres({}, idObject, typeIndicateur, idToSelect)
}
