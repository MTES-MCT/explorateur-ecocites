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

function onChangeWording(id) {
	var errorField = $("#wording-error-" + id);
	var btn = $("#wording-btn-" + id);
	errorField.hide();
	btn.removeClass("btn-primary").addClass("btn-warning");
}

function onSaveWordingSuccess(response, id) {
	var errorField = $("#wording-error-" + id);
	var btn = $("#wording-btn-" + id);
	errorField.hide();
	btn.removeClass("btn-warning").addClass("btn-primary");

}

function onSaveWordingFail(response, id) {
	var errorField = $("#wording-error-" + id);
	errorField.text(JSON.stringify(response)).show();
	console.log(response)
}

function saveWording(id, text) {
	var res = {
		texte: text,
		id: id
	};
	$.ajax({
		url: "/bo/administration/wording",
		method: 'POST',
		data: JSON.stringify(res),
		contentType: "application/json",
		success: function (response) {
			onSaveWordingSuccess(response, id)
		},
		error: function (response) {
			onSaveWordingFail(response, id)
		}
	});
}

function saveAllWording() {
	var results = [];
	var inputs = $(".saveable_libelle");
	inputs.removeClass("is-invalid");
	inputs.parent().find(".text-danger").hide();
	inputs.each(function (index, elt) {
		var jelt = $(elt);
		results.push({
			id: jelt.attr('id').replace('em_', ''),
			texte: jelt.val()
		})
	});
	$.ajax({
		url: "/bo/administration/wording/multiple",
		method: 'POST',
		data: JSON.stringify(results),
		contentType: "application/json",
		success: function () {
			document.location.reload()
		},
		error: function (response) {
			var globalErrors = response && response.responseJSON && response.responseJSON.globalErrors
			if (globalErrors) {
				globalErrors.forEach(function (error) {
					var jelt = $("#" + error.code);
					jelt.addClass('is-invalid');
					jelt.removeClass('needs-validation');
					jelt.parent().find(".text-danger").show()
				})
			}
		}
	});
}
