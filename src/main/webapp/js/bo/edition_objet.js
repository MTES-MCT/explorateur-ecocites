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

function appliqueDataTable(){
	$('#liste-pagination').DataTable({
		searching: false,
		select: false,
		bLengthChange: false,
		language: {
			responsive: true,
			emptyTable: "Aucune données disponibles dans le tableau",
			info: "Afficher _START_ à _END_ de _TOTAL_ entités",
			infoEmpty: "Afficher 0 to 0 de 0 entités",
			paginate: {
				first: "Premier",
				last: "Dernier",
				next: "Suivant",
				previous: "Précédent"
			}
		}
	});
	initExportCSV();
}

function reloadFilterFromLocalStorage(urlFilter) {
	var form = $("#listFiltreFrom");
	var filtres = JSON.parse(window.localStorage.getItem('filtres' + urlFilter));
	var hasFiltres = false;
	if (filtres && filtres.length > 0) {
		filtres.forEach(function (filtre) {
			if (filtre.value) {
				hasFiltres = true;
				form.find("[name=\"" + filtre.name + "\"]").val(filtre.value);
			}
		});
	}
	if (hasFiltres) {
		$("#listFiltre").collapse("show");
	}
}

function resetFilters(urlFilter) {
	var form = $("#listFiltreFrom");
	form.find("input, select, textarea").val("");
	$("#listFiltre").collapse("hide");
	soumissionForm(urlFilter);
}

function soumissionForm(urlFilter) {
	var form = $("#listFiltreFrom");
	window.localStorage.setItem('filtres' + urlFilter, JSON.stringify(form.serializeArray()));
	$.ajax({
		type: "POST",
		url: urlFilter,
		data: form.serialize(),
		contentType: "application/x-www-form-urlencoded",
		success: function (data) {
			$("#liste-pagination-div").html(data);
			appliqueDataTable();
		},
		error: function () {
		}
	});
}

function openModalObject(value, modalUrl) {
	$.ajax({
		method: 'GET',
		url: modalUrl + value,
		contentType: "application/json",
		success: function (data) {
			$("#modalAjoutIndicateur").html(data);
            $("#modalAjoutIndicateur").modal('show');
			modalEditionObjetReady(value);
		}
	});
}

function initExportCSV() {
	downloadLink = $("#exportCSV_link");
	downloadBtn = $("#exportCSV_btn");
	downloadBtn.off("click");
	downloadBtn.click(exportCSV)
}

function exportCSV() {
	var objectToExport = downloadLink.prop("name");
	downloadLink.prop("href", "/bo/exportCSV/"+objectToExport);
	if(objectToExport==="business"){
		downloadLink.prop("download", "affaires.zip");
	}
	else{
		downloadLink.prop("download", objectToExport+ ".zip");
	}

	downloadLink.get(0).click();
}

var importUrl = undefined;
function showImportModal(url) {
	importUrl = url;
	$("#modalImportDocument").modal("show");
}

function importFile() {
	if (importUrl) {
		var res = toFormDataImport();
		if (!res.error) {
			jQuery.ajax({
				url: importUrl,
				data: res.data,
				cache: false,
				contentType: false,
				processData: false,
				method: 'POST',
				success: showModalImportSuccessful,
				error: showModalImportError
			});
		}
	}
}

function toFormDataImport() {
	var res = {};
	var data = new FormData();
	var $fileInput = $("#fileInput");
	var files = $fileInput[0].files;
	var $fileError = $fileInput.parent().parent().parent().parent().find("p.text-danger");
	if (files.length > 0) {
		$fileError.hide();
		data.append('file', files[0]);
	} else {
		res.error = true;
		$fileError.text("Champ obligatoire");
		$fileError.show();
	}
	res.data = data;
	return res;
}

function onFileChanged(elt, queryLabel) {
	var files = $(elt)[0].files;
	if (files.length > 0) {
		$(queryLabel).val(files[0].name);
	} else {
		$(queryLabel).val("");
	}
}

function showModalImportError(response) {
	$("#modalImportDocument").modal("hide");
	var msg = "<ul class='m-0'>";
	var data = response.responseJSON;
	if (data && data.messages && data.messages.length) {
		data.messages.forEach(function (row) {
			msg = msg + "<li>" + row + "</li>"
		});
	} else {
		msg = msg + "<li>Une erreur inconnue est survenue</li>"
	}
	msg = msg + "</ul>";
	$("#modalImportDocumentError").find(".error-text").html(msg);
    $("#modalImportDocumentError").modal("show");
}

function showModalImportSuccessful(data) {
	$("#modalImportDocument").modal("hide");
	$("#modalImportDocumentSuccess").find(".success-text")
		.html("<ul class='m-0'>"
				+ "<li>" + data.linesUntouched + " affaire(s) identique(s) à celle(s) en base</li>"
				+ "<li>" + data.linesUpdated + " affaire(s) mises à jour</li>"
				+ "<li>" + data.linesCreated + " affaire(s) créées</li></ul>"
			+ "</ul>");
    $("#modalImportDocumentSuccess").modal("show");
}

var deleteObjet = undefined;
function createDeleteFunction(id) {
	return function (url) {
		if (url && url !== "") {
			$.ajax({
				method: 'DELETE',
				url: url + id,
				contentType: "application/json",
				dataType: 'json',
				success: function (data) {
					if (data && data.general_error) {
						var elt = $('#erreurSuppressionIndicateur');
						elt.find('.alert-danger').html(data.general_error);
						elt.modal('show');
					} else {
						location.reload();
					}
				}
			});
		}
	}
}

function updateObjetForm(url) {
	if (url && url !== "") {
		var res = getAllModalAsFormData();
		if (!res.error) {
			$.ajax({
				method: 'POST',
				url: url,
				data: res.data,
				contentType: false,
				processData: false,
				cache: false,
				success: onSuccessEditionObjet,
				error: onErrorEditionObjet
			});
		}
	}
}

function updateObjetJson(url) {
	if (url && url !== "") {
		var res = getAllModalValue();
		if (!res.error) {
			$.ajax({
				method: 'POST',
				url: url,
				data: JSON.stringify(res.data),
				contentType: "application/json",
				processData: true,
				cache: false,
				success: onSuccessEditionObjet,
				error: onErrorEditionObjet
			});
		}
	}
}


function createObjetForm(url, redirectUrl) {
	var res = getAllModalAsFormData();
	if (!res.error) {
		$.ajax({
			method: 'POST',
			url: url,
			data: res.data,
			contentType: false,
			processData: false,
			cache: false,
			success: createOnSuccessCreationObject(redirectUrl),
			error: onErrorEditionObjet
		});
	}
}
function createObjetJson(url, redirectUrl) {
	var res = getAllModalValue();
	if (!res.error) {
		$.ajax({
			method: 'POST',
			url: url,
			data: JSON.stringify(res.data),
			contentType: "application/json",
			processData: undefined,
			cache: false,
			success: createOnSuccessCreationObject(redirectUrl),
			error: onErrorEditionObjet
		});
	}
}

function onErrorEditionObjet(data) {
	var rep = JSON.parse(data.responseText);
	if (rep.fieldErrors) {
		rep.fieldErrors.forEach(function (fe) {
			$('[name=' + fe.field + ']').closest('.form-group').find('p.text-danger').text(fe.defaultMessage).show();
		})
	}
}

function onSuccessEditionObjet() {
	reloadWithFilter();
}

function createOnSuccessCreationObject(redirectUrl) {
	return function (id) {
		if (redirectUrl && redirectUrl !== "") {
			location.replace(redirectUrl + id);
		} else {
			location.reload()
		}
	}
}

function modalEditionObjetReady(id) {
	$('[data-toggle="tooltip"]').tooltip();
	$('.datepicker').datepicker({
		autoclose: true,
		orientation: 'auto',
		language: 'fr'
	});
	deleteObjet = createDeleteFunction(id);
}

updateCurrentTab = function() { location.reload() };

function getAllModalValue() {
	var res = {};
	var data = {};

	$(':input.field-object').each(function (i, el) {
		var elt = $(el);
		var val = elt.val();
		if (elt.prop('required')) {
			if (val === undefined || val.trim() === "") {
				elt.closest('.form-group').find('p.text-danger').text("Champ obligatoire").show();
				res.error = true;
			} else {
				elt.closest('.form-group').find('p.text-danger').hide();
			}
		} else {
			elt.closest('.form-group').find('p.text-danger').hide();
		}
		data[elt.attr('name')] = elt.val();
	});
	res.data = data;
	return res;
}

function getAllModalAsFormData() {
	var res = {};
	res.error = false;
	var data = new FormData();

	$(':input.field-object').each(function (i, el) {
		var elt = $(el);
		var val = elt.val();
		if (elt.prop('required')) {
			if (val === undefined || val.trim() === "") {
				elt.closest('.form-group').find('p.text-danger').text("Champ obligatoire").show();
				res.error = true;
			} else {
				elt.closest('.form-group').find('p.text-danger').hide();
			}
		} else {
			elt.closest('.form-group').find('p.text-danger').hide();
		}
		data.append(elt.attr('name'), elt.val());
	});
	$(':input.field-file').each(function (i, el) {
		var fileInput = $(el);
		var files = fileInput[0].files;
		if (files.length > 0) {
			fileInput.closest('.form-group').find('p.text-danger').hide();
			data.append(fileInput.attr('name'), files[0]);
		} else {
			if (fileInput.prop('required')) {
				res.error = true;
				fileInput.closest('.form-group').find('p.text-danger').text('Champ obligatoire').show();
			} else {
				fileInput.closest('.form-group').find('p.text-danger').hide();
			}
		}
	});
	res.data = data;
	return res;
}

function hideModal() {
	$('.modal').modal('hide');
}


function reloadWithFilter() {
	var locationUrl = $("#locationreloadhelper").val();
	if (locationUrl) {
		soumissionForm(locationUrl)
		hideModal()
	} else {
		location.reload()
	}
}

function parse_query_string(query) {
	var vars = query.split(/[&?]/);
	var query_string = {};
	for (var i = 0; i < vars.length; i++) {
		var pair = vars[i].split("=");
		var key = decodeURIComponent(pair[0]);
		var value = decodeURIComponent(pair[1]);
		if (key && value) {
			// If first entry with this name
			if (typeof query_string[key] === "undefined") {
				query_string[key] = decodeURIComponent(value);
				// If second entry with this name
			} else if (typeof query_string[key] === "string") {
				var arr = [query_string[key], decodeURIComponent(value)];
				query_string[key] = arr;
				// If third or later entry with this name
			} else {
				query_string[key].push(decodeURIComponent(value));
			}
		}
	}
	return query_string;
}
