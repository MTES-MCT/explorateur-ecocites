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

var escapeHTML = function(unsafe) {
	return unsafe.replace(/[&<>"']/g, function(m) {
		return ({
			'&': '&amp;',
			'<': '&lt;',
			'>': '&gt;',
			'"': '&quot;',
			'\'': '&#039;'
		})[m]
	});
};
var token_csrf = $("meta[name='_csrf']").attr("content");
var header_csrf = $("meta[name='_csrf_header']").attr("content");

(function ($) {
	$.ajaxSetup({
		statusCode: {
			401: function () {
				window.location.replace("/");
			}
		},
		beforeSend: function (xhr) {
			xhr.setRequestHeader('ajaxCall', true);
			xhr.setRequestHeader(header_csrf, token_csrf);
		}
	});


  if (typeof $ === 'undefined') {
    throw new TypeError('Bootstrap\'s JavaScript requires jQuery. jQuery must be included before Bootstrap\'s JavaScript.');
  }

  var version = $.fn.jquery.split(' ')[0].split('.');
  var minMajor = 1;
  var ltMajor = 2;
  var minMinor = 9;
  var minPatch = 1;
  var maxMajor = 4;

  if (version[0] < ltMajor && version[1] < minMinor || version[0] === minMajor && version[1] === minMinor && version[2] < minPatch || version[0] >= maxMajor) {
    throw new Error('Bootstrap\'s JavaScript requires at least jQuery v1.9.1 but less than v4.0.0');
  }
})($);
//# sourceMappingURL=index.js.map


if (!String.prototype.format) {
	String.prototype.format = function() {
		var args = arguments;
		return this.replace(/{(\d+)}/g, function(match, number) {
			return typeof args[number] !== 'undefined' ? args[number] : match;
		});
	};
}

$(document).on('show.bs.modal', '.modal', function () {
	var zIndex = 1040 + (10 * $('.modal.show').length);
	$(this).css('z-index', zIndex).addClass('modal-stack');
	setTimeout(function() {
		$('.modal-backdrop').not('.modal-stack').css('z-index', zIndex - 1).addClass('modal-stack');
	}, 0);
});

$(document).on('hidden.bs.modal', '.modal', function () {
	$('.modal:visible').length && $(document.body).addClass('modal-open');
});

function appliqueDatePicker(){
    $('.datepicker').datepicker({
        autoclose: true,
        orientation: 'auto',
        language: 'fr',
    }).on('changeDate', function (evt) {
        saveAttribut(evt.target, evt.target.value);
    });
}

function appliqueDatePickerSansSaveAuto(){
    $('.datepicker').datepicker({
        autoclose: true,
        orientation: 'auto',
        language: 'fr',
        format: "mm/yyyy",
        startView: "months",
        minViewMode: "months"
    })
}

$(document).ready( function() {
    var $draggable = $('.draggable').draggabilly({
        handle: '.handle',
        grid: [ 20, 20 ],
        containment: true
    });
    //Initialisation des tooltips bootstrap
	$(function () {
		$('[data-toggle="tooltip"]').tooltip()
	});

    appliqueDatePicker();
});

function toFormDataFileWithTile(fileInputElt, titleInputElt) {
	var res = {};
	var data = new FormData();
	var $file_changed=$("#file_changed");
	data.append('fileChanged', $file_changed.val());
	data.append('idFile', $("#idFile").val());
	var $fileInput = $(fileInputElt);
	var files = $fileInput[0].files;
	var $fileError = $fileInput.parent().parent().parent().parent().find("p.text-danger");
	if($file_changed.val() === "1") {
		if (files.length < 0) {
			res.error = true;
			$fileError.text("Champ obligatoire");
			$fileError.show();
		} else if (files[0].size > 5000000) {
			res.error = true;
			$fileError.text("Vous ne pouvez uploader que des fichiers de moins de 5Mo");
			$fileError.show();
		} else {
			$fileError.hide();
			data.append('file', files[0]);
		}
	}
	var $title = $(titleInputElt);
	var title = $title.val();
	var $titleError = $title.parent().find("p.text-danger");
	if (title.trim().length > 0) {
		$titleError.hide();
		data.append('title', title);
	} else {
		res.error = true;
		$titleError.text("Champ obligatoire");
		$titleError.show();
	}
	res.data = data;
	return res;
}

function fileInputChangeListener(elt, queryLabel, imgFieldName) {
		$(imgFieldName).show();
    var files = $(elt)[0].files;
		var addBtn = $("#addbtn");
		var delBtn = $("#delbtn");
    if (files.length > 0) {
			$(queryLabel).val(files[0].name);
			readURL(imgFieldName, files[0]);
	    addBtn.hide();
	    delBtn.show();
    } else {
			$(queryLabel).val("");
			readURL(imgFieldName, undefined);
	    addBtn.show();
	    delBtn.hide();
    }
}

function readURL(imgFieldName, file) {
	if (imgFieldName) {
		if (file) {
			var reader = new FileReader();
			reader.onload = function (e) {
				$(imgFieldName).attr('src', e.target.result);
			};
			reader.readAsDataURL(file);
		} else {
			$(imgFieldName).hide();
		}
	}
}

function uploadDocument(url, fileInputElt, titleInputElt, callBack, eltList) {
	var res = toFormDataFileWithTile(fileInputElt, titleInputElt);
	if (!res.error) {
		jQuery.ajax({
			url: url,
			data: res.data,
			cache: false,
			contentType: false,
			processData: false,
			method: 'POST',
			success: function (data) {
				if (callBack !== undefined) {
					callBack(data);
				}
			},
			error: function (errors) {
				console.log(errors)
				var response = errors.responseJSON;
				var $fileError = $(fileInputElt).parent().parent().parent().parent().find("p.text-danger");
				var $titleError = $(titleInputElt).parent().find("p.text-danger");
				$titleError.hide();
				$fileError.hide();
				if (response && response.fieldErrors && response.fieldErrors.length > 0) {
					response.fieldErrors.forEach(function (error) {
						if (error.field === "file") {
							$fileError.text(error.defaultMessage);
							$fileError.show();
						} else if (error.field === "title") {
							$titleError.text(error.defaultMessage);
							$titleError.show();
						}
					})
				} else {
					$fileError.text("Vous ne pouvez uploader que des fichiers de moins de 5Mo");
					$fileError.show();
				}
			}
		});
	}
}

function uploadImage(url, fileInputElt, titleInputElt, callBack, eltList) {
	var res=toFormDataImageInfo(fileInputElt, titleInputElt, eltList);
	if (!res.error) {
		jQuery.ajax({
			url: url,
			data: res.data,
			cache: false,
			contentType: false,
			processData: false,
			method: 'POST',
			success: function (data) {
				if (callBack !== undefined) {
					callBack(data);
				}
			},
			error: function (errors) {
				console.log(errors)
				var response = errors.responseJSON;
				var $fileError = $(fileInputElt).parent().parent().parent().parent().find("p.text-danger");
				var $titleError = $(titleInputElt).parent().find("p.text-danger");
				$titleError.hide();
				$fileError.hide();
				if (response && response.fieldErrors && response.fieldErrors.length > 0) {
					response.fieldErrors.forEach(function (error) {
						if (error.field === "file") {
							$fileError.text(error.defaultMessage);
							$fileError.show();
						} else if (error.field === "title") {
							$titleError.text(error.defaultMessage);
							$titleError.show();
						}
					})
				} else if (errors.status === 502) {
					$fileError.text("Vous ne pouvez uploader que des fichiers de moins de 5Mo");
					$fileError.show();
				}
			}
		});
	}
}

function toFormDataImageInfo(fileInputElt, titleInputElt, eltList) {
	var res = {};
	var data = new FormData();
	var $file_changed=$("#file_changed");
	data.append('fileChanged', $file_changed.val());
	data.append('idFile', $("#idFile").val());
	var $fileInput = $(fileInputElt);
	var files = $fileInput[0].files;
	var $fileError = $fileInput.parent().parent().parent().parent().find("p.text-danger");
	if($file_changed.val()==="1") {
		if ( files.length > 0 ) {
			$fileError.hide();
			data.append('file', files[ 0 ]);
		} else {
			res.error = true;
			$fileError.text("Champ obligatoire");
			$fileError.show();
		}
	}
	var $title = $(titleInputElt);
	var title = $title.val();
	var $titleError = $title.parent().find("p.text-danger");
	if (title.trim().length > 0 && title.trim().length < 80000) {
		$titleError.hide();
		data.append('title', title);
	} else if(title.trim().length > 80000) {
		res.error = true;
		$titleError.text("Champ de 80000 caractères maximum");
		$titleError.show();
	}else {
		res.error = true;
		$titleError.text("Champ obligatoire");
		$titleError.show();
	}
	var $modalImageUpload = $(eltList);
	var $legende = $modalImageUpload.find("#legende");
	var $legendeErr = $legende.parent().find("p.text-danger");
	$legendeErr.hide();
	var legende=$legende.val();
	if (legende.trim().length > 0 && legende.trim().length < 80000) {
		$legendeErr.hide();
		data.append('legende', legende);
	} else if(legende.trim().length > 80000) {
		res.error = true;
		$legendeErr.text("Champ de 80000 caractères maximum");
		$legendeErr.show();
	}else {
		res.error = true;
		$legendeErr.text("Champ obligatoire");
		$legendeErr.show();
	}
	var $lieu = $modalImageUpload.find("#lieu");
	var $lieuErr = $lieu.parent().find("p.text-danger");
	var lieu=$lieu.val();
	if (lieu.trim().length > 0 && lieu.trim().length < 80000) {
		$lieuErr.hide();
		data.append('lieu', lieu);
	} else if(lieu.trim().length > 80000) {
		res.error = true;
		$lieuErr.text("Champ de 80000 caractères maximum");
		$lieuErr.show();
	}else {
		res.error = true;
		$lieuErr.text("Champ obligatoire");
		$lieuErr.show();
	}
	var $desc = $modalImageUpload.find("#description");
	var $descErr = $desc.parent().find("p.text-danger");
	var desc=$desc.val();
	if (desc.trim().length > 0 && desc.trim().length < 80000) {
		$descErr.hide();
		data.append('description', desc);
	}	else if (desc.trim().length > 80000) {
		res.error = true;
		$descErr.text("Champ de 80000 caractères maximum");
		$descErr.show();
	} else{
		res.error = true;
		$descErr.text("Champ obligatoire");
		$descErr.show();
	}
	var $copyright = $modalImageUpload.find("#copyright");
	var $copyrightErr = $copyright.parent().find("p.text-danger");
	var copyright=$copyright.val();
	if (copyright.trim().length > 0) {
		$copyrightErr.hide();
		data.append('copyright', copyright);
	} else {
		res.error = true;
		$copyrightErr.text("Champ obligatoire");
		$copyrightErr.show();
	}
	var checkPresse= $modalImageUpload.find("#presse");
	var checkRevue= $modalImageUpload.find("#revue");
	var checkExpo= $modalImageUpload.find("#expo");
	var checkInternet= $modalImageUpload.find("#internet");
	var checkSiteEE= $modalImageUpload.find("#siteEE");
	var checkSupportMM= $modalImageUpload.find("#supportMM");
	var checkErr=checkPresse.parent().find("p.text-danger");
	var autorisationsDiffusion=[checkPresse,checkRevue,checkExpo,checkInternet,checkSiteEE,checkSupportMM];
	var checkCount=0;
	autorisationsDiffusion.forEach(function(autorisation){
		if(autorisation.is(":checked")){
			checkCount+=1;
		}
	});
	if(checkCount>0){
		checkErr.hide();
		data.append('autorisationPresse', checkPresse.is(":checked"));
		data.append('autorisationRevue', checkRevue.is(":checked"));
		data.append('autorisationExpo', checkExpo.is(":checked"));
		data.append('autorisationInternet', checkInternet.is(":checked"));
		data.append('autorisationSiteEE', checkSiteEE.is(":checked"));
		data.append('autorisationSupportMM', checkSupportMM.is(":checked"));
	}
	else{
		res.error = true;
		checkErr.text("Il faut sélectionner au moins une autorisation.");
		checkErr.show();
	}
	var $numerisation=$modalImageUpload.find("#numerisation");
	var $numerisationErr = $numerisation.parent().find("p.text-danger");
	$numerisationErr.hide();
	if($numerisation.val().trim().length > 0) {
		if(Number.isInteger(parseInt($numerisation.val())) && parseInt($numerisation.val())>=0){
			data.append('numerisation', $modalImageUpload.find("#numerisation").val());
		}
		else{
			res.error = true;
			$numerisationErr.text("Veuillez saisir un nombre entier positif.");
			$numerisationErr.show();
		}
	}
	else{
		data.append('numerisation', "-1");
	}
	res.data = data;
	return res;
}

function deleteDocument(id, idObject) {
	jQuery.ajax({
		url: '/bo/upload/' + id,
		cache: false,
		method: 'DELETE',
		success: function(){
			loadPresentation(idObject);
		},
		error: function (data) {
			// TODO: Do something
		}
	});
}

function deleteDocumentQuestionnaire(id, idReponse) {
	jQuery.ajax({
		url: '/bo/upload/' + id + '/' + idReponse,
		cache: false,
		method: 'DELETE',
		success: function(){
			var elt = $('#' + id);
			elt.closest('.form-group').children('button').prop('disabled', false);
			elt.remove();
		}
	});
}

function hideModelThen(callback, selector) {
	return function(data) {
		var modal = selector ? $(selector): $('.modal');
		if(modal.length <= 0 || !modal.hasClass("modal")){
            modal = modal.parent();
		}
		if (callback) {
			modal.off("hidden.bs.modal");
			modal.on("hidden.bs.modal", function () {
				callback(data);
			});
		}
		modal.modal('hide');
		$("body").removeClass("modal-open");
	}
}

function getModalAddFile(modalParent, type, id, secondaryId, typeSecondary) {
	var params = {};
	if (secondaryId) {
		params.secondaryId = secondaryId;
	}
	if (typeSecondary) {
		params.typeSecondary = typeSecondary;
	}
	jQuery.ajax({
		url: '/bo/upload/modal/' + type + '/' + id,
		cache: false,
		data: params,
		method: 'GET',
		success: function(data){
			$(modalParent).html(data);
			$(modalParent).modal('show');
		}
	});
}

function getModalEditFile(modalParent, type, id, idUploadFile, secondaryId, typeSecondary) {
	var params = {};
	if (secondaryId && secondaryId!=="") {
		params.secondaryId = secondaryId;
	}
	if (typeSecondary) {
		params.typeSecondary = typeSecondary;
	}
	jQuery.ajax({
		url: '/bo/upload/modal/' + type + '/' + id + '/' + idUploadFile,
		cache: false,
		data: params,
		method: 'GET',
		success: function(data){
			$(modalParent).html(data);
			$(modalParent).modal('show');
		}
	});
}

function lambdaLoadEvaluationFacteur(name, id) {
	return function() {
		loadEvaluationFacteur(name, id, true);
	};
}

function fileInputChangeListenerModalAdmin(input, eltNamePrefix) {
	var filelink = $(eltNamePrefix + '_lienFileModalObjet');
    var filePreview = $(eltNamePrefix + '_preview');
	var touchListener = $(eltNamePrefix + '_changed');
	var addBtn = $(eltNamePrefix + '_addbtn');
	var delBtn = $(eltNamePrefix + '_delbtn');

	touchListener.val(1);
	var files = $(input)[0].files;
    $(filelink).hide();
    $(filePreview).show();
	if (files.length > 0) {
		$(filePreview).val(files[0].name);
		addBtn.hide();
		delBtn.show();
		addBtn.insertAfter(delBtn);
	} else {
		filePreview.val("");
		addBtn.show();
		delBtn.hide();
		delBtn.insertAfter(addBtn);
	}
}

function deleteCurrentFileModalAdmin(input, eltNamePrefix) {
    var filelink = $(eltNamePrefix + '_lienFileModalObjet');
	var filePreview = $(eltNamePrefix + '_preview');
	var file = $(eltNamePrefix + '_file');
	var touchListener = $(eltNamePrefix + '_changed');
	var addBtn = $(eltNamePrefix + '_addbtn');
	var delBtn = $(eltNamePrefix + '_delbtn');

    $(filelink).hide();
    $(filePreview).show();
	touchListener.val(1);
	file.val(undefined);
	filePreview.val("");
	addBtn.show();
	delBtn.hide();
	delBtn.insertAfter(addBtn);
}

function deleteCurrentFile(input) {
	var filePreview = $('#preview');
	var file = $('#fileInput');
	var fileName = $('#fileName');
	var addBtn = $('#addbtn');
	var delBtn = $('#delbtn');

	$("#file_changed").val(1);
	file.val(undefined);
	filePreview.val("");
	fileName.val("");
	addBtn.show();
	delBtn.hide();
}

function deleteEcocite(id) {
	jQuery.ajax({
		url: '/bo/ecocites/' + id,
		cache: false,
		method: 'DELETE',
		success: function(data){
			if(data && data.general_error){
				var elt = $('#erreurSuppressionEcocite');
				elt.find('.alert-danger').html(data.general_error);
				elt.modal('show');
			} else {
				location.replace('/bo/ecocites')
			}
		}
	});
}
function deleteAction(id) {
	jQuery.ajax({
		url: '/bo/actions/' + id,
		cache: false,
		method: 'DELETE',
		success: function(data){
			if(data && data.general_error){
				var elt = $('#erreurSuppressionAction');
				elt.find('.alert-danger').html(data.general_error);
				elt.modal('show');
			} else {
				location.replace('/bo/actions');
			}
		}
	});
}

function submit() {
	var res ={};
	res.id1 = $('#id1').val();
	res.id2 = $('#id2').val();
	jQuery.ajax({
		url: '/bo/ecocites/' + id,
		cache: false,
		method: 'POST',
		data: JSON.stringify(res),
		success: function(){
			$('#id1').closest('contact-form').children('.error-msg')
				.addClass("alert-danger").removeClass("alert-success").text("YOUPI").show();
		},
		error: function(){
			$('#id1').closest('form-group').children('.error-msg')
				.addClass("alert-danger").removeClass("alert-success").text("FAIL").show();
		}
	});
}


function createAssoContactObject(idContact, idObject, typeObject, poids, onSuccess, onError) {
	jQuery.ajax({
		url: '/bo/contacts/' + idContact + '/' + typeObject + '/' + idObject + '/' + poids,
		cache: false,
		method: 'POST',
		success: onSuccess,
		error: onError
	});
}

function deleteAssoContactObjet(idContact, idObject, typeObject, onSuccess, onError) {
	jQuery.ajax({
		url: '/bo/contacts/' + idContact + '/' + typeObject + '/' + idObject,
		cache: false,
		method: 'DELETE',
		success: onSuccess,
		error: onError
	});
}

// Gestion des médias

function initDateInputMediaForm() {
	$('.form-ajaris-field.datepicker').datepicker({
		autoclose: true,
		orientation: 'auto',
		language: 'fr',
		endDate: '0d'
	});
}

function getModalAddMedia(modalParent, type, id, level) {
	jQuery.ajax({
		url: '/bo/ajaris/modal/create/' + type + '/' + level + '/' + id,
		method: 'GET',
		success: function(data){
			var modal = $(modalParent);
			modal.html(data);
			mediaModalInitialisation();
			initDateInputMediaForm();
			modal.on('shown.bs.modal', function () {
				modal.off('shown.bs.modal');
			});
			$(modalParent).modal('show');
		}
	});
}

function getModalEditMedia(modalParent, type, id, idMedia) {
	jQuery.ajax({
		url: '/bo/ajaris/modal/edit/' + type + '/' + id + '/' + idMedia,
		cache: false,
		method: 'GET',
		success: function(data){
			var modal = $(modalParent);
			modal.html(data);
			initDateInputMediaForm();
			modal.on('shown.bs.modal', function () {
				modal.off('shown.bs.modal');
				initMapMediaForm();
			});
			modal.modal('show');
		}
	});
}

function onChangeMediaFormType() {
	var select = $("#selectMediaFormType");
	var currentType = select.val() || "form-empty";
	if (currentType) {
		Object.keys(mediaFormTypes).forEach(function showOrHide(formId) {
			if (mediaFormTypes.hasOwnProperty(formId)) {
				if (formId === "form-" + currentType) {
					mediaFormTypes[formId].show();
					if (formId === "form-scratch") {
						initMapMediaForm();
					}
				} else {
					mediaFormTypes[formId].hide()
				}
			}
		})
	}
}

var mediaFormTypes = {};
function mediaModalInitialisation() {
	var select = $("#selectMediaFormType");
	mediaFormTypes = {
		"form-empty": $("#form-empty"),
		"form-ajaris": $("#form-ajaris"),
		"form-scratch": $("#form-scratch")
	};
	if (select) {
		select.change(onChangeMediaFormType);
	}
	onChangeMediaFormType()
}

function addAjarisImage(typeModal, level, idObject, idMedia) {
	var select = $("#selectMediaFormType");
	switch (select.val()) {
		case 'empty':
			break;
		case 'ajaris':
			submitMediaFormAjaris(idObject, typeModal, level);
			break;
		case 'scratch':
			submitMediaFormFile(idObject, typeModal, level, "#mediaFormFileInput", "#form-scratch");
			break;
		case 'update':
			submitMediaForm(idObject, typeModal, idMedia, "#form-scratch");
			break;
	}
}

function submitMediaFormAjaris(idObject, typeModal, level) {
	var url = '/bo/ajaris/createId/' + typeModal + '/' + level + '/' + idObject;
	var inputIdAjaris = $("#idAjaris");
	var globalErrorField = $("#addMediaGlobalError");
	var idAjaris = inputIdAjaris.val();
	var fieldIdAjarisError = inputIdAjaris.parent().find("p.text-danger");
	if (!idAjaris) {
		fieldIdAjarisError.text("Champ obligatoire");
		fieldIdAjarisError.show();
	} else {
		fieldIdAjarisError.hide();
		globalErrorField.hide();
		jQuery.ajax({
			url: url,
			data: JSON.stringify({ idAjaris: idAjaris }),
			contentType: "application/json; charset=utf-8",
			cache: false,
			method: 'POST',
			success: function (data) {
				reloadPresentationFun(idObject)();
				var modal = $("#modalAjoutDocument");
				modal.on('hidden.bs.modal', function () {
					modal.off('hidden.bs.modal');
					getModalEditMedia("#modalAjoutDocument", typeModal, idObject, data.id);
				});
				modal.modal('hide');
			},
			error: function (errors) {
				var response = errors.responseJSON;
				if (response && response.fieldErrors && response.fieldErrors.length > 0) {
					response.fieldErrors.forEach(function (error) {
						if (error.field === "idAjaris") {
							fieldIdAjarisError.text(error.defaultMessage);
							fieldIdAjarisError.show();
						}
					})
				} else if (response && response.globalErrors && response.globalErrors.length > 0) {
					response.globalErrors.forEach(function (error) {
						globalErrorField.text(error.defaultMessage);
						globalErrorField.show();
					})
				}
			}
		});
	}
}

function submitMediaForm(idObject, typeModal, idMedia, eltList) {
	var url = '/bo/ajaris/edit/' + idMedia;
	var globalErrorField = $("#editMediaGlobalError");
	globalErrorField.hide();
	var res = mediaFormToFormData(eltList);
	if (!res.error) {
		globalErrorField.hide();
		jQuery.ajax({
			url: url,
			data: JSON.stringify(res.data),
			contentType: "application/json; charset=utf-8",
			cache: false,
			processData: false,
			method: 'POST',
			success: function () {
				hideModelThen(reloadPresentationFun(idObject), "#modalAjoutDocument")();
			},
			error: function (errors) {
				console.log(errors);
				var response = errors.responseJSON;
				if (response && response.fieldErrors && response.fieldErrors.length > 0) {
					response.fieldErrors.forEach(function (error) {
						if (error.field === "title") {
							var errorField = $(eltList).find('input[name="' + error.field + '"').parent().find("p.text-danger");
							errorField.text(error.defaultMessage);
							errorField.show();
						}
					})
				} else if (response && response.globalErrors && response.globalErrors.length > 0) {
					response.globalErrors.forEach(function (error) {
						globalErrorField.text(error.defaultMessage);
						globalErrorField.show();
					})
				}
			}
		});
	}
}

function submitMediaFormFile(idObject, typeModal, level, fileInputElt, eltList) {
	var url = '/bo/ajaris/createFile/' + typeModal + '/' + level + '/' + idObject;
	var globalErrorField = $("#editMediaGlobalError");
	globalErrorField.hide();
	var res = mediaFormFileToFormData(fileInputElt, eltList);
	if (!res.error) {
		jQuery.ajax({
			url: url,
			data: res.data,
			cache: false,
			contentType: false,
			processData: false,
			method: 'POST',
			success: function () {
				hideModelThen(reloadPresentationFun(idObject), "#modalAjoutDocument")();
			},
			error: function (errors) {
				console.log(errors);
				var response = errors.responseJSON;
				if (response && response.fieldErrors && response.fieldErrors.length > 0) {
					response.fieldErrors.forEach(function (error) {
						if (error.field === "file") {
							var $fileError = $(fileInputElt).parent().parent().parent().parent().find("p.text-danger");
							$fileError.text(error.defaultMessage);
							$fileError.show();
						} else if (error.field === "title") {
							var errorField = $(eltList).find('input[name="' + error.field + '"').parent().find("p.text-danger");
							errorField.text(error.defaultMessage);
							errorField.show();
						}
					})
				} else if (response && response.globalErrors && response.globalErrors.length > 0) {
					response.globalErrors.forEach(function (error) {
						globalErrorField.text(error.defaultMessage);
						globalErrorField.show();
					})
				} else if (errors.status === 502) {
					$fileError.text("Vous ne pouvez uploader que des fichiers de moins de 5Mo");
					$fileError.show();
				}
			}
		});
	}
}


function mediaFormToFormData(eltList) {
	var res = {};
	var data = {};
	var formFields = $(eltList).find(".form-ajaris-field");
	formFields.each(function(index, field) {
		var inputField = $(field);
		var errorField = inputField.parent().find("p.text-danger");
		var fieldValue = inputField.val().trim();
		if (inputField.attr("required")) {
			if (fieldValue === "") {
				res.error = true;
				errorField.text("Champ obligatoire");
				errorField.show();
			}
		}
		if (inputField.attr("data-maxSize")) {
			var maxSize = parseInt(inputField.attr("data-maxSize"));
			if (fieldValue.length > maxSize) {
				res.error = true;
				errorField.text("Champ de " + inputField.attr("data-maxSize") + " caractères maximum");
				errorField.show();
			}
		}
		var typeData = inputField.attr("data-type");
		var val = inputField.val();
		checkTypeData(val,  typeData, res, errorField);
		if (!res.error) {
			data[inputField.attr("name")] = val;
			errorField.hide();
		}
	});
	res.data = data;
	return res;
}

function mediaFormFileToFormData(fileInputElt, eltList) {
	var res = {};
	var data = new FormData();
	var $fileInput = $(fileInputElt);
	var files = $fileInput[0].files;
	var $fileError = $fileInput.parent().parent().parent().parent().find("p.text-danger");
	if ( files.length > 0 ) {
		$fileError.hide();
		data.append('file', files[ 0 ]);
	} else {
		res.error = true;
		$fileError.text("Champ obligatoire");
		$fileError.show();
	}
	var formFields = $(eltList).find(".form-ajaris-field");
	formFields.each(function(index, field) {
		var inputField = $(field);
		var errorField = inputField.parent().find("p.text-danger");
		errorField.hide();
		var fieldValue = inputField.val().trim();
		if (inputField.attr("required")) {
			if (fieldValue === "") {
				res.error = true;
				errorField.text("Champ obligatoire");
				errorField.show();
			}
		}
		if (inputField.attr("data-maxSize")) {
			var maxSize = parseInt(inputField.attr("data-maxSize"));
			if (fieldValue.length > maxSize) {
				res.error = true;
				errorField.text("Champ de " + inputField.attr("data-maxSize") + " caractères maximum");
				errorField.show();
			}
		}
		var typeData = inputField.attr("data-type");
		var val = inputField.val();
		checkTypeData(val,  typeData, res, errorField);
		if (!res.error) {
			data.append(inputField.attr("name"), val);
		}
	});
	res.data = data;
	return res;
}

function checkTypeData(val, typeData, res, errorField) {
	if (typeData) {
		switch (typeData) {
			case "integer":
				var intVal = parseInt(val);
				if (isNaN(intVal) && val == intVal) {
					res.error = true;
					errorField.text("Ce champ doit être un entier entier");
					errorField.show();
				}
				break;
			case "float":
				var floatVal = parseFloat(val);
				if (isNaN(floatVal) && val == floatVal) {
					res.error = true;
					errorField.text("Ce champ doit être un nombre décimal");
					errorField.show();
				}
				break;
			default:
				break;
		}
	}
}


function deleteMedia(id, idObject) {
	jQuery.ajax({
		url: '/bo/ajaris/edit/' + id,
		cache: false,
		method: 'DELETE',
		success: function(){
			hideModelThen(reloadPresentationFun(idObject), "#modalAjoutDocument")();
		},
		error: function () {
			$("#editMediaGlobalError").text("La suppression de l'image sur Terra à échoué").show()
		}
	});
}

function updateMarkerMapMediaForm(map, latInput, longInput) {
	var lat = latInput.val();
	var long = longInput.val();
	if (lat !== '' && long !== '' && $.isNumeric(lat) && $.isNumeric(long)) {
		map.replaceMarker(parseFloat(lat), parseFloat(long), undefined, true)
	} else {
		map.removeMarker(true)
	}
}

function initMapMediaForm(latitude, longitude) {
	var apiKey = $("#geoportailApiKey").val();
	var latInput = $("#latInput");
	if (!latitude) {
		latitude = parseFloat(latInput.val());
	}
	var longInput = $("#longInput");
	if (!longitude) {
		longitude = parseFloat(longInput.val());
	}
	Gp.Services.getConfig({
		apiKey: apiKey,
		onSuccess: function () {
			var map = createMap("mapMediaForm", "mapMediaFormPlaceHolder");
			map.initialize(latitude, longitude);
			map.addOnClickListener(function (latitude, longitude) {
				map.replaceMarker(latitude, longitude);
				latInput.val(latitude);
				longInput.val(longitude);
			});
			if (latitude && longitude) {
				map.addMarker(latitude, longitude);
			}
			latInput.on('blur', function() {
				updateMarkerMapMediaForm(map, latInput, longInput);
			});
			longInput.on('blur', function () {
				updateMarkerMapMediaForm(map, latInput, longInput);
			});
		}
	});
}
