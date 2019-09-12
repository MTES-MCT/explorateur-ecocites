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
