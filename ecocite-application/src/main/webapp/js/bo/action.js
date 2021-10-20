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

var updateCurrentTab = undefined;

function canRequestCategoryValidation(actionId) {
	$.ajax({
		url: '/bo/actions/category/can_request_validation/' + actionId,
		method: 'GET',
		success: function (data) {
			if (data === true) {
				if ($('#button-submit-category').attr('data-target') === "#modalCategorisationIndicateurNonCompleteIng") {
					updateCurrentTab();
					updateTabState();
				}
			} else {
				$('#button-submit-category').attr('data-target', "#modalCategorisationIndicateurNonCompleteIng")
			}
		},
		error: function (error) {
			console.log("Error ! " + error);
		}
	})
}

function onTypeFinancementFocus() {
	var typeFinancement = $("#typeFinancement");
	var typeFinancementOldValue = $("#typeFinancementOldValue");
	typeFinancementOldValue.val(typeFinancement.val());
}

function onTypeFinancementChange() {
	$("#confirmationChangementTypeFinancement").modal('show');
}

function confirmChangementTypeFinancement(doAction) {
	var typeFinancement = $("#typeFinancement");
	var typeFinancementOldValue = $("#typeFinancementOldValue");
	if (doAction) {
		saveAttribut(typeFinancement.get(), typeFinancement.val())
	} else {
		$("#confirmationChangementTypeFinancement").modal('hide');
		typeFinancement.val(typeFinancementOldValue.val());
	}
}

function majCommentaire(domComment, actionId) {
	var jsonObj = {
		id: $(domComment).data("idetape"),
		idObjet: actionId,
		commentaire: $(domComment).val()
	};
	$.ajax({
		url: '/bo/actions/etape/commentaire/majCommentaire',
		method: 'POST',
		data: JSON.stringify(jsonObj),
		contentType: "application/json; charset=utf-8",
		dataType: 'json',
		success: function (data) {
			$(domComment).data("idetape", data.id);
			$(domComment).val(data.commentaire);
		},
		error: function (jqXHR, textStatus, error) {
			console.log("Error ! " + error);
		}
	})
}

function loadEvaluationFacteur(typeFacteurSucces, actionId, scrollBottom) {
	if (typeFacteurSucces === "questionnaire_action_ingenierie" || typeFacteurSucces === "questionnaire_action_investissement") {
		$("#questionnaire_action_ingenierie").html("");
		$("#questionnaire_action_investissement").html("");
	}
	$.ajax({
		method: 'GET',
		url: "/bo/actions/questionnaire/" + actionId + "/" + typeFacteurSucces,
		contentType: "application/json",
		success: function (data) {
			var id = "#" + typeFacteurSucces;
			$(id).html(data);
			$('html,body').scrollTop(0);
			$('[data-toggle="tooltip"]').tooltip();
			$(id + " .inputSaveAuto").blur(
				function () {
					saveAttribut(this, this.value);
				}
			);
			if (scrollBottom) {
				window.scrollTo(0, document.body.scrollHeight);
			}
		}
	});
}

function loadCategory(category, actionId) {
	$("#ingenierie").html("");
	$("#domaine").html("");
	$("#objectif").html("");
	$.ajax({
		method: 'GET',
		url: "/bo/actions/" + category + "/" + actionId,
		contentType: "application/json",
		success: function (data) {
			var id = "#" + category;
			$(id).html(data);
			$(id + " .inputSaveAuto").blur(
				function () {
					saveAttribut(this, this.value);
				}
			);
			canRequestCategoryValidation(actionId);
			triggerSortable(id, actionId);
			loadCommentaire(category, actionId);
			updateCurrentTab = function () {
				loadCategory(category, actionId)
			};
			$(function () {
				$('[data-toggle="tooltip"]').tooltip()
			});
		}
	});
}

function loadEvaluationAction(actionId) {
	$.ajax({
		method: 'GET',
		url: "/bo/actions/evaluation/" + actionId,
		contentType: "application/json",
		success: function (data) {
			$("#evaluation_innovation").html(data)
				.find(".inputSaveAuto").change(
				function () {
					saveAttribut(this, this.value);
				}
			);
			loadCommentaire('#evaluation_innovation', actionId);
			$(function () {
				$('[data-toggle="tooltip"]').tooltip()
			});
			$('document').ready(onCheckBoxChange);
		}
	});
}

function onCheckBoxChange() {
	if (canSubmit()) {
		$("#button-submit-eval").prop("disabled", false);
	} else {
		$("#button-submit-eval").prop("disabled", true);
	}
}

function canSubmit() {
	var o = {};
	$("input:radio").each(function (a, t) {
		o[$(t).attr("name")] = true;
	});
	var flag = true;
	Object.keys(o).forEach(function (a) {
		if (!$("input[name='" + a + "']:checked").val()) {
			flag = false;
		}
	});
	return flag;
}

function loadMesureIndicateur(actionId) {
	$.ajax({
		method: 'GET',
		url: "/bo/actions/indicateur/loadMesureIndicateurs/" + actionId,
		contentType: "application/json",
		success: function (data) {
			var mesureIndicateurElt = $("#mesureIndicateurOnglet");
			mesureIndicateurElt.html(data);
			mesureIndicateurElt.find(".inputSaveAuto").blur(
				function () {
					saveAttribut(this, this.value);
				}
			);
		}
	});
}

function reloadPresentationFun(actionId) {
	return function () {
		loadPresentation(actionId)
	}
}

function formatStateContact(state) {
	if ((!state.id) || state.text === "Choisissez un contact") {
		return escapeHTML(state.text);
	}
	var modalContactUrl = $("#modalContactUrl").val();
	return $(
		'<span>' + escapeHTML(state.text) + '<i class="fa fa-search cursorPointer m-l-10" onclick="viewContactAction(\'' + state.id + '\',' + modalContactUrl + ',event);"></i>' + '</span>'
	);
}

function loadPresentation(actionId) {
	$.ajax({
		method: 'GET',
		url: "/bo/actions/presentation/" + actionId,
		contentType: "application/json",
		success: function (data) {
			var presentationElt = $("#presentation");
			presentationElt.html(data);
			presentationElt.find(".inputSaveAuto").blur(
				function () {
					saveAttribut(this, this.value);
				}
			);
			$(function () {
				presentationElt.find('[data-toggle="tooltip"]').tooltip()
			});
			appliqueDatePicker();
			var selectTags = presentationElt.find(".select-tags");
			selectTags
				.select2({
					tags: true,
					maximumSelectionLength: 5
				}).on('change.select2', function (e) {
				saveAttribut(e.currentTarget, $(e.currentTarget).val().join(';'));
			});
			var contactPrincipal = $("#contactPrin");
			contactPrincipal
				.select2({
					templateSelection: formatStateContact,
					maximumSelectionLength: 1
				})
				.on('select2:selecting', function (e) {
					var oldVal = contactPrincipal.val();
					createAssoContactObject(e.params.args.data.id, actionId, 'actions', '1',
						function () {
							contactPrincipal.closest('.form-group')
								.find('p.text-danger')
								.hide();
							$("#addContact1").hide();
						},
						function () {
							contactPrincipal.val(oldVal)
								.change()
								.closest('.form-group')
								.find('p.text-danger')
								.text("Vous ne pouvez pas choisir ce contact")
								.show();
						});
				})
				.on('select2:unselecting', function (e) {
					var oldVal = contactPrincipal.val();
					deleteAssoContactObjet(e.params.args.data.id, actionId, 'actions',
						function () {
							contactPrincipal.closest('.form-group')
								.find('p.text-danger')
								.hide();
							$("#addContact1").show();
						},
						function () {
							contactPrincipal.val(oldVal)
								.change()
								.closest('.form-group')
								.find('p.text-danger')
								.text("Vous ne pouvez pas choisir ce contact")
								.show();
						});
				});
			var contactSecondaire = $("#contactSecond");
			contactSecondaire.select2({
				templateSelection: formatStateContact,
				maximumSelectionLength: 2
			})
				.on('select2:selecting', function (e) {
					var oldVal = contactSecondaire.val();
					createAssoContactObject(e.params.args.data.id, actionId, 'actions', '2',
						function () {
							contactSecondaire.closest('.form-group')
								.find('p.text-danger')
								.hide();
							if (oldVal.length > 0) {
								$("#addContact2").hide();
							}
						},
						function () {
							contactSecondaire.val(oldVal)
								.change()
								.closest('.form-group')
								.find('p.text-danger')
								.text("Vous ne pouvez pas choisir ce contact")
								.show();
						});
				})
				.on('select2:unselecting', function (e) {
					var oldVal = contactSecondaire.val();
					deleteAssoContactObjet(e.params.args.data.id, actionId, 'actions',
						function () {
							contactSecondaire.closest('.form-group')
								.find('p.text-danger')
								.hide();
							$("#addContact2").show();
						},
						function () {
							contactSecondaire.val(oldVal)
								.change()
								.closest('.form-group')
								.find('p.text-danger')
								.text("Vous ne pouvez pas choisir ce contact")
								.show();
						});
				});
			afficheCarteOnLoad();
		}
	});
}

function loadIndicateur(typeIndicateur, actionId) {
	$("#indicateurOngletRealisation").html("");
	$("#indicateurOngletResultat").html("");
	$.ajax({
		method: 'GET',
		url: "/bo/actions/indicateur/loadIndicateurs/" + actionId + "/" + typeIndicateur,
		contentType: "application/json",
		success: function (data) {
			var id = "#" + typeIndicateur;
			$(id).html(data);
			$(id + " .inputSaveAuto").blur(
				function () {
					saveAttribut(this, this.value);
				}
			);
			$(function () {
				$(id).find('[data-toggle="tooltip"]').tooltip()
			});
			loadCommentaire(typeIndicateur, actionId);
			$('li .prev_link').click(function () {
				previous();
				return false;
			});
			$('li .next_link').click(function () {
				next();
				return false;
			});
			updateCurrentTab = function () {
				loadIndicateur($("#typeIndicateur").val(), $("#actionId").val());
			}
		}
	});
}

function deleteEtiquette(domElem, actionId) {
	$.ajax({
		url: '/bo/actions/' + $(domElem).data("cat") + '/etiquette/' + actionId + '/' + $(domElem).data("idasso"),
		method: 'DELETE',
		success: function () {
			canRequestCategoryValidation(actionId);
			reloadPage(actionId);
		},
		error: function (jqXHR, textStatus, error) {
			console.log("Error ! " + error);
		}
	})
}

function majEtiquette(domElem, poid, actionId) {
	var jsonObj = {
		idEtiquette: $(domElem).data("idetiquette"),
		idAsso: $(domElem).data("idasso"),
		idObjet: actionId,
		poid: poid
	};
	$.ajax({
		url: '/bo/actions/' + $(domElem).data("cat") + '/majEtiquette',
		method: 'POST',
		data: JSON.stringify(jsonObj),
		contentType: "application/json; charset=utf-8",
		dataType: 'json',
		success: function (data) {
			$(domElem).data("idasso", data.idAsso);
			$(domElem).data("idetiquette", data.etiquette.id);
			canRequestCategoryValidation(actionId);
		},
		error: function () {
			var parent = $(domElem).parent();
			if (parent.children('.etiquette-round').length < 2) {
				parent.closest('.content').children('.info-etq-empty').attr("hidden", false);
			} else {
				parent.closest('.content').children('.info-etq-empty').attr("hidden", true);
			}
			$(domElem).remove();
		}
	})
}

function loadCommentaire(activeEtape, actionId) {
	var codeEtape = activeEtape.replace("#", "");
	$.ajax({
		url: '/bo/actions/etape/' + actionId + '/' + codeEtape,
		method: 'GET',
		success: function (res) {
			if (res) {
				$(".tab-content > div#" + codeEtape + " textarea.commentaire").val(res.commentaire).data("idetape", res.id);
			}
		},
		error: function (jqXHR, textStatus, error) {
			console.log("Error ! " + error);
		}
	});
}

function deleteCurrentEtiquette(evt, actionId) {
	var etiquette = $(evt).parent().parent().parent();
	var parent = etiquette.parent();
	deleteEtiquette(etiquette, actionId);
	if (parent.children('.etiquette-round').length < 2) {
		parent.closest('.content').children('.info-etq-empty').attr("hidden", false);
	} else {
		parent.closest('.content').children('.info-etq-empty').attr("hidden", true);
	}
	etiquette.remove();
}

function showListEtiquettesForAction(idCategorie, cat, etqDomElem, actionId) {
	$.ajax({
		url: '/bo/actions/' + cat + '/etiquettes/' + idCategorie + '/' + actionId,
		method: 'GET'
	}).done(function (data) {
		// activer le bouton en cours mais désacitver les autres
		if (etqDomElem) {
			$(etqDomElem).addClass("selected");
			$(etqDomElem).siblings().removeClass("selected");
		}
		// Clear les etiquettes associées
		// Enlever l'info
		$('.tab-content > .active #etiquettesGroup .info-cat-empty').attr("hidden", true);
		// Mettre à jour la liste des etiquettes
		$('.tab-content > .active #etiquettesGroup > div').remove();
		$('.tab-content > .active #etiquettesGroup > script').remove();
		$('.tab-content > .active #etiquettesGroup').prepend(data);
		$(function () {
			$('[data-toggle="tooltip"]').tooltip()
		});
	}).fail(function (ex) {
		console.log("Error ! " + ex);
	});
}

function reloadPage(actionId) {
	var category = $("#currentCategory").val();
	var categoryId = $("#currentCategoryId").val();
	showListEtiquettesForAction(categoryId, category, undefined, actionId);
}

function updateTabState() {
	var idAction = $("#idAction").val();
	$.ajax({
		type: "GET",
		url: "/bo/actions/edition/" + idAction + "/onglet",
		success: function (data) {
			$("#tab-list").replaceWith(data);
			$("a[href='" + "#" + $(".tab-pane.active").attr("id") + "']").tab("show");
			$('.listOngletAction a[data-toggle="tab"]').on('click', function (e) {
				var clickedElt = $(e.target);
				if (!clickedElt.is('a[data-toggle="tab"]')) {
					clickedElt = clickedElt.closest('a[data-toggle="tab"]')
				}
				if (!clickedElt.closest("li.nav-item").hasClass("state-impossible")) {
					var target = clickedElt.attr("href");
					loadOnglet(target.replace("#", ""), idAction);
				}
			});
		}
	});
}

function saveAttribut(domEle, value, callback) {
	$(domEle).parent().find(".text-danger").hide();
	$(domEle).removeClass("is-invalid");
	var objectId = $(domEle).data("objectid");
	var referenceId = $(domEle).data("referenceid");
	var referenceTypeObjet = "action";
	var objectClass = $(domEle).data("objectclass");
	var idAttribut = $(domEle).attr("name");
	var checkBoxValue = "";
	var checked = $(domEle).is(":checked");
	if ($(domEle).attr("type") === "text" && $(domEle).closest("div").find("input.form-check-input")) {
		checkBoxValue = $(domEle).closest("div").find("input.form-check-input").val();
	}
	var bean = JSON.stringify({
		objectId: objectId,
		checkBoxValue: checkBoxValue,
		referenceId: referenceId,
		referenceTypeObjet: referenceTypeObjet,
		objectClass: objectClass,
		attributId: idAttribut,
		attributValue: value,
		checked: checked
	});
	$.ajax({
		type: "POST",
		url: "/bo/saveAttribut",
		data: bean,
		contentType: "application/json; charset=utf-8",
		dataType: 'json',
		success: function (data) {
			if (data && data.fieldErrors && data.fieldErrors[0]) {
				$(domEle).addClass("is-invalid");
				$(domEle).parent().find(".text-danger").html(data.fieldErrors[0].defaultMessage).show();
			} else {
				if (objectClass === "Action") {
					if (idAttribut === "etatAvancement") {
						updateTabState();
					} else if (idAttribut === "typeFinancement") {
						window.location.href = "/bo/actions/edition/" + objectId;
					}
				}
				if (callback) {
					callback();
				}
			}
		},
		error: function () {
			$(domEle).parent().find($("filed_error")).html("Erreur technique");
		}
	});
}

function loadOnglet(name, actionId) {
	$("#" + name).addClass("active show").siblings().removeClass("active show");
	var me = $('a[href="#' + name + '"]').addClass("active");
	me.siblings().removeClass("active");
	$(".body > .tab-content > .tab-panel").html("");
	var parent = me.closest('li.nav-item');
	parent.children('a.nav-link').addClass("active");
	parent.siblings().find("a").removeClass("active show");
	$(".tab-content").children().html("");
	loadSelectedOnglet(name, actionId)
}

function loadSelectedOnglet(name, actionId) {
	if (name === "presentation") {
		loadPresentation(actionId);
	} else if (name === "indicateurOngletRealisation" || name === "indicateurOngletResultat") {
		loadIndicateur(name, actionId);
	} else if (name === "mesureIndicateurOnglet") {
		loadMesureIndicateur(actionId);
	} else if (name === "evaluation_innovation") {
		loadEvaluationAction(actionId);
	} else if (name === "ingenierie" || name === "domaine" || name === "objectif") {
		loadCategory(name, actionId);
	} else if (name === "questionnaire_action_ingenierie" || name === "questionnaire_action_investissement") {
		loadEvaluationFacteur(name, actionId);
	}
}

function triggerSortable(tabPanelId, actionId) {
	var e0 = $(tabPanelId).find('#etiquettesGroup');
	if (e0.length <= 0) {
		return;
	}
	var e1 = $(tabPanelId).find('#etiquettePrincipal');
	var e1s = $(tabPanelId).find('#etiquettePrincipalSolo');
	var e2 = $(tabPanelId).find('#etiquetteSecondaire');
	var e3 = $(tabPanelId).find('#etiquetteTrois');

	if (e0.length > 0) {
		// sort: true
		Sortable.create(e0.get(0), {
			group: {
				name: "sorting",
				pull: true,
				put: false
			}
		});
	}

	if (e1.length > 0) {
		// sort: false
		Sortable.create(e1.get(0), {
			group: {
				name: "sorting",
				put: true,
				pull: false
			},
			sort: false,
			onAdd: function (evt) {
				majEtiquette(evt.item, 1, actionId);
				$(evt.item).children("div").children(".col-2").append("<i class=\"fa fa-close delete-etiquette\" onclick=\"deleteCurrentEtiquette(this," + "'" + actionId + "'" + ")\" style=\"margin-left: 5px\"></i>")
			},
			onSort: function (evt) {
				$(evt.to).prepend(evt.item);
				if (e1.children('.etiquette-round').length < 1) {
					e1.closest('.content').children('.info-etq-empty').attr("hidden", false);
				} else {
					e1.closest('.content').children('.info-etq-empty').attr("hidden", true);
				}
			}
		});
	}
	if (e1s.length > 0) {
		// sort: false
		Sortable.create(e1s.get(0), {
			group: {
				name: "sorting",
				put: function (to) {
					return to.el.children.length < 1;
				},
				pull: false
			},
			sort: false,
			onAdd: function (evt) {
				majEtiquette(evt.item, 1, actionId);
				$(evt.item).children("div").children(".col-2").append("<i class=\"fa fa-close delete-etiquette\" onclick=\"deleteCurrentEtiquette(this," + "'" + actionId + "'" + ")\" style=\"margin-left: 5px\"></i>")
			},
			onSort: function (evt) {
				$(evt.to).prepend(evt.item);
				if (e1s.children('.etiquette-round').length < 1) {
					e1s.closest('.content').children('.info-etq-empty').attr("hidden", false);
				} else {
					e1s.closest('.content').children('.info-etq-empty').attr("hidden", true);
				}
			}
		});
	}
	if (e2.length > 0) {
		// sort: false
		Sortable.create(e2.get(0), {
			group: {
				name: "sorting",
				put: function (to) {
					return to.el.children.length < 5;
				},
				pull: false
			},
			sort: false,
			onAdd: function (evt) {
				majEtiquette(evt.item, 2, actionId);
				$(evt.item).children("div").children(".col-2").append("<i class=\"fa fa-close delete-etiquette\" onclick=\"deleteCurrentEtiquette(this," + "'" + actionId + "'" + ")\" style=\"margin-left: 5px\"></i>")
			},
			onSort: function (evt) {
				$(evt.to).prepend(evt.item);
				if (e2.children('.etiquette-round').length < 1) {
					e2.closest('.content').children('.info-etq-empty').attr("hidden", false);
				} else {
					e2.closest('.content').children('.info-etq-empty').attr("hidden", true);
				}
			}
		});
	}
	if (e3.length > 0) {
		// sort: false
		Sortable.create(e3.get(0), {
			group: {
				name: "sorting",
				put: function (to) {
					return to.el.children.length < 5;
				},
				pull: false
			},
			sort: false,
			onAdd: function (evt) {
				majEtiquette(evt.item, 3, actionId);
				$(evt.item).children("div").children(".col-2").append("<i class=\"fa fa-close delete-etiquette\" onclick=\"deleteCurrentEtiquette(this," + "'" + actionId + "'" + ")\" style=\"margin-left: 5px\"></i>")
			},
			onSort: function (evt) {
				$(evt.to).prepend(evt.item);
				if (e3.children('.etiquette-round').length < 1) {
					e3.closest('.content').children('.info-etq-empty').attr("hidden", false);
				} else {
					e3.closest('.content').children('.info-etq-empty').attr("hidden", true);
				}
			}
		});
	}
}


function showAjoutIndicateurMesure(type) {
	$(".templateAjout" + type).show();
	$(".showAjout" + type + "Bouton").hide();
	$(".ajout" + type + "Bouton").show();
}

function cancelIndicateurMesure(type) {
	$(".templateAjout" + type).hide();
	$(".showAjout" + type + "Bouton").show();
	$(".ajout" + type + "Bouton").hide();
	$("#value" + type + "Ajout").val("");
	$("#date" + type + "Ajout").val("");
}

function deleteIndicateurMesure(elt, type) {
	var idElement = $("#mesureToDelete").val();
	$.ajax({
		type: "DELETE",
		url: "/bo/actions/indicateur/" + type + "/" + idElement,
		contentType: "application/json",
		success: function () {
			$("#row-" + type + "-" + idElement).remove();
			if (doUpdateChart) {
				doUpdateChart();
				updateTabState();
			}
		}, error: showErrorMesure
	});
}

function showErrorMesure(data) {
	var error = $("#erreurMesureCible").modal("show").find("p.error-text").text("");
	error.append("<ul>");
	if (data.responseJSON.fieldErrors && data.responseJSON.fieldErrors.length > 0) {
		data.responseJSON.fieldErrors.forEach(function (line) {
			error.append("<li>" + line.defaultMessage + '</li>')
		})
	}
	error.append("</ul>");
	error.removeClass("d-none");

}

function showModalConfirmationSuppression(type, id) {
	if (type === "cible") {
		$("#mesureToDelete").val(id);
		$("#comfirmationSuppressionCible").modal("show");
	} else if (type === "mesure") {
		$("#mesureToDelete").val(id);
		$("#comfirmationSuppressionMesure").modal("show");
	}
}

function showModalConfirmationAjoutCible() {
	$("#comfirmationAjoutCible").modal("show");
}

function showModalConfirmationAjoutMesure() {
	$("#comfirmationAjoutMesure").modal("show");
}

var chart = undefined;
var doUpdateChart = undefined;

function doUpdateChartFunCreator(idAssoObjetIndicateur, chart) {
	return function () {
		$.ajax({
			type: "GET",
			url: "/bo/actions/indicateur/graph/" + idAssoObjetIndicateur,
			contentType: "application/json",
			success: function (data) {
				updateChart(chart, data);
			}
		});
	};
}

function soumissionValidation(actionId) {
	if (canSubmit()) {
		$.ajax({
			url: '/bo/actions/evaluation/request_validation/' + actionId,
			method: 'POST',
			success: function (data) {
				$('#confirmationValidationCategorisationIndicateur').modal('hide');
				$('body').removeClass('modal-open');
				$('.modal-backdrop').remove();
				if (data === true) {
					loadEvaluationAction(actionId);
					updateTabState();
				} else {
					$('#erreurValidationEvaluation').find('.alert-danger').html(data).modal('show');
				}
			},
			error: function (error) {
				console.log("Error ! " + error);
			}
		})
	}
}

function accepteValidation(actionId) {
	$.ajax({
		url: '/bo/actions/evaluation/accepte_validation/' + actionId,
		method: 'POST',
		success: function (data) {
			$('#confirmationValidationCategorisationIndicateur').modal('hide');
			$('body').removeClass('modal-open');
			$('.modal-backdrop').remove();
			if (data === true) {
				loadEvaluationAction(actionId);
				updateTabState();
			} else {
				$('#erreurValidationEvaluation').find('.alert-danger').html(data);
				$('#erreurValidationEvaluation').modal('show');
			}
		},
		error: function (error) {
			console.log("Error ! " + error);
		}
	})
}

function annulationValidationInnovation(actionId) {
	$.ajax({
		url: '/bo/actions/evaluation/annulation_validation/' + actionId,
		method: 'POST',
		success: function (data) {
			$('#confirmationValidationCategorisationIndicateur').modal('hide');
			$('body').removeClass('modal-open');
			$('.modal-backdrop').remove();
			if (data === true) {
				loadEvaluationAction(actionId);
				updateTabState();
			} else {
				$('#erreurValidationEvaluation').find('.alert-danger').html(data);
				$('#erreurValidationEvaluation').modal('show');
			}
		},
		error: function (error) {
			console.log("Error ! " + error);
		}
	})
}

function soumissionValidationCategorisationAction(typeOnglet, actionId) {
	$.ajax({
		url: '/bo/actions/' + typeOnglet + '/request_validation/' + actionId,
		method: 'POST',
		success: function (data) {
			$('#confirmationValidationCategorisationIndicateur').modal('hide');
			$('body').removeClass('modal-open');
			$('.modal-backdrop').remove();
			if (data === true) {
				updateCurrentTab();
				updateTabState();
			} else {
				$('#erreurValidationCategorisationIndicateur').find('.alert-danger').html(data);
				$('#erreurValidationCategorisationIndicateur').modal('show');
			}
		},
		error: function (error) {
			console.log("Error ! " + error);
		}
	})
}

function accepteValidationCategorisationAction(typeOnglet, actionId) {
	$.ajax({
		url: '/bo/actions/' + typeOnglet + '/accepte_validation/' + actionId,
		method: 'POST',
		success: function (data) {
			$('#confirmationValidationCategorisationIndicateur').modal('hide');
			$('body').removeClass('modal-open');
			$('.modal-backdrop').remove();
			if (data === true) {
				updateCurrentTab();
				updateTabState();
			} else {
				$('#erreurValidationCategorisationIndicateur').find('.alert-danger').html(data);
				$('#erreurValidationCategorisationIndicateur').modal('show');
			}
		},
		error: function (error) {
			console.log("Error ! " + error);
		}
	})
}

function annulationValidationCategorisationAction(typeOnglet, actionId) {
	$.ajax({
		url: '/bo/actions/' + typeOnglet + '/annulation_validation/' + actionId,
		method: 'POST',
		success: function (data) {
			$('#confirmationValidationCategorisationIndicateur').modal('hide');
			$('body').removeClass('modal-open');
			$('.modal-backdrop').remove();
			if (data === true) {
				updateCurrentTab();
				updateTabState();
			} else {
				$('#erreurValidationCategorisationIndicateur').find('.alert-danger').html(data);
				$('#erreurValidationCategorisationIndicateur').modal('show');
			}
		},
		error: function (error) {
			console.log("Error ! " + error);
		}
	})
}


function onChangeFiltre(actionId, typeIndicateur) {
	var params = {};
	var idDomaine = $("#filtreDomaine").val();
	if (idDomaine !== "") {
		params.idDomaine = idDomaine;
	}
	var idObjectif = $("#filtreObjectif").val();
	if (idObjectif !== "") {
		params.idObjectif = idObjectif;
	}
	getIndicateursFiltres(params, actionId, typeIndicateur)
}

function getIndicateursFiltres(params, actionId, typeIndicateur, idIndicateurToSelect) {
	$.ajax({
		method: 'GET',
		url: "/bo/actions/indicateur/filtreIndicateurs/" + actionId + "/" + typeIndicateur,
		data: params,
		contentType: "application/json",
		success: function (data) {
			$("#listeIndicateurFiltree").html(data);
			if (idIndicateurToSelect) {
				loadIndicateurInfoId(idIndicateurToSelect);
			} else {
				resetIndicateurSelected();
			}
		}
	});
}

function resetIndicateurSelected() {
	$("#listeIndicateurFiltree").children().removeClass("bg-grey-light");
	$("#indicateurDescription").html("<div class=\"title-container\"><div class=\"title\">Fiche Indicateur</div></div><div class=\"content\"><p>Sélectionnez un indicateur dans la liste des indicateurs recommandés</p></div>");
}

function loadIndicateurInfo(domEle) {
	var idIndicateur = $(domEle).data("objectid");
	$(".indicateurRecommande ").removeClass("bg-grey-light");
	$(domEle).addClass("bg-grey-light");
	loadIndicateurInfoId(idIndicateur)
}

function loadIndicateurInfoId(idIndicateur) {
	$.ajax({
		method: 'GET',
		url: "/bo/actions/indicateur/loadInfoIndicateur/" + idIndicateur,
		contentType: "application/json",
		success: function (data) {
			$("#indicateurDescription").html(data);
			$('[data-toggle="tooltip"]').tooltip();
		}
	});
}

function ajoutIndicateurInfo(domEle) {
	var idIndicateur = $(domEle).data("objectid");
	var actionId = $("#actionId").val();
	var posteCalculValide = true;
	var uniteValide = true;
	var inputPosteCalcul = $("#calculeIndicateur");
	var inputUnite = $("#uniteIndicateur");
	if (inputPosteCalcul.length > 0) {
		var poste_calcule = inputPosteCalcul.val();
		if (poste_calcule === "") {
			$("#calcule-error").show();
			posteCalculValide = false;
		}
		else {
			$("#calcule-error").hide();
			posteCalculValide = true;
		}
	}
	var unite = inputUnite.val();
	if (unite === "") {
		$("#unite-error").show();
		uniteValide = false;
	}
	else {
		$("#unite-error").hide();
		uniteValide = true;
	}
	if (uniteValide && posteCalculValide) {
		var bean = JSON.stringify({
			idIndicateur: idIndicateur,
			idObjet: actionId,
			unite: unite,
			poste_calcule: poste_calcule
		});
		$.ajax({
			type: "POST",
			url: "/bo/actions/indicateur/ajoutToAction",
			data: bean,
			contentType: "application/json",
			success: function (data) {
				$(".errorAjoutInfo").remove();
				$("#assoIndicateurAction").append(data);
				resetIndicateurSelected();
				$.ajax({
					type: "GET",
					url: "/bo/actions/indicateur/can_request_validation/" + actionId,
					success: function (data) {
						if (data === true) {
							$('#button-submit-indic').attr('data-target', "#confirmationRequesteValidationCategorisationIndicateur")
							updateCurrentTab();
							updateTabState();
						} else {
							if ($("#financemnetIngEtAutre").val() === "true") {
								$('#button-submit-indic').attr('data-target', "#modalCategorisationIndicateurNonCompleteIngInv");
							}
							else {
								$('#button-submit-indic').attr('data-target', "#modalCategorisationIndicateurNonCompleteInv");
							}
							updateCurrentTab();
							updateTabState();
						}
					}
				});
			},
			error: function (err) {
				$(".errorAjoutInfo").text(err.responseJSON.message);
				resetIndicateurSelected();
			}
		});
	}
}

function deleteIndicateurInfo(domEle) {
	var idIndicateurAsso = $(domEle).data("objectid");
	var actionId = $("#actionId").val();
	$.ajax({
		method: 'DELETE',
		url: "/bo/actions/indicateur/supprimeToAction/" + actionId + "/" + idIndicateurAsso,
		contentType: "application/json",
		dataType: 'json',
		success: function () {
			$(".assoIndicateurAction" + idIndicateurAsso).remove();
			$.ajax({
				type: "GET",
				url: "/bo/actions/indicateur/can_request_validation/" + actionId,
				success: function (data) {
					if (data === true) {
						$('#button-submit-indic').attr('data-target', "#confirmationRequesteValidationCategorisationIndicateur");
						updateCurrentTab();
						updateTabState();
					} else {
						if ($("#financemnetIngEtAutre").val() === "true") {
							$('#button-submit-indic').attr('data-target', "#modalCategorisationIndicateurNonCompleteIngInv");
						}
						else {
							$('#button-submit-indic').attr('data-target', "#modalCategorisationIndicateurNonCompleteInv");
						}
						updateCurrentTab();
						updateTabState();
					}
				}
			});
		}
	});
}

function switchTab(value) {
	if ("indicateurOngletRealisation" === value) {
		$(".indicateurOngletResultat").trigger('click').addClass("active").addClass('show');
		$(".indicateurOngletRealisation").removeClass("active").removeClass('show');
	} else {
		$(".indicateurOngletRealisation").trigger('click').addClass("active").addClass('show');
		$(".indicateurOngletResultat").removeClass("active").removeClass('show');
	}
}

var currentPage = 0;

function previous() {
	goTo(currentPage - 1);
}

function next() {
	goTo(currentPage + 1);
}

function goTo(page) {
	var listIndicateurSize = Number($("#listIndicateurSize").val());
	if (page >= 0 && page <= Number($("#maxPage").val())) {
		currentPage = page;
		$(".pageIndicateur").hide();
		$(".pageIndicateur.pageIndicateur" + page).show();

		if ((currentPage + 1) * 10 < listIndicateurSize) {
			$('.indicateurCompteur span').html(currentPage * 10 +
				"-" + (currentPage + 1) * 10 + " sur " + listIndicateurSize);
		} else {
			$('.indicateurCompteur span').html(currentPage * 10 +
				"- " + listIndicateurSize + " sur " + listIndicateurSize);
		}
	}
}

function modalIndicateur(value) {
	$.ajax({
		method: 'GET',
		url: "/bo/indicateurs/edition/action/" + value,
		contentType: "application/json",
		data: {idObjet: $("#idAction").val()},
		success: function (data) {
			$("#modalAjoutIndicateur").html(data);
			$("#modalAjoutIndicateur").modal("show");
			modalEditionObjetReady(value);
		}
	});
}

function loadIndicateurSaisieMesure(domEle) {
	var idAssoActionIndicateur = $(domEle).data("objectid");
	$(".assoIndicateurAction ").removeClass("bg-grey-light");
	$(domEle).addClass("bg-grey-light");
	$.ajax({
		method: 'GET',
		url: "/bo/actions/indicateur/loadEditMesureIndicateurs/" + idAssoActionIndicateur,
		contentType: "application/json",
		success: function (data) {
			var ajoutMesure = $("#ajoutMesure");
			ajoutMesure.html(data);
			ajoutMesure.find("input.inputSaveAuto").change(
				function () {
					saveAttribut(this, $(this).is(":checked"), updateTabState);
				}
			);
			ajoutMesure.find("textarea.inputSaveAuto").change(
				function () {
					saveAttribut(this, $(this).val())
				}
			);
			$(function () {
				$('[data-toggle="tooltip"]').tooltip()
			});
			appliqueDatePickerSansSaveAuto();
			var printDecimal = $("#printDecimal").val();
			var assoIndicateurActionId = $("#assoIndicateurActionId").val();
			chart = initializeEmptyChartLine("chartMesureIndicateur", printDecimal === "NOMBRE_DEC_0_1");
			doUpdateChart = doUpdateChartFunCreator(assoIndicateurActionId, chart);
			doUpdateChart();
		}
	});
}

function ajoutIndicateurMesure(type) {
	var idAssoObjetIndicateur = $("#assoIndicateurActionId").val();
	var value = $("#value" + type + "Ajout").val();
	var date = $("#date" + type + "Ajout").val();
	var bean = JSON.stringify({
		idAssoObjetIndicateur: idAssoObjetIndicateur,
		value: value,
		date: date,
		type: type
	});
	$.ajax({
		type: "POST",
		url: "/bo/actions/indicateur/ajoutMesure",
		data: bean,
		contentType: "application/json",
		success: function (data) {
			$(".templateAjout" + type).before(data).hide();
			$(".showAjout" + type + "Bouton").show();
			$(".ajout" + type + "Bouton").hide();
			$("#value" + type + "Ajout").val("");
			$("#date" + type + "Ajout").val("");
			$('#erreur' + type).addClass("d-none");
			if (doUpdateChart) {
				doUpdateChart();
				updateTabState();
			}
			$("#ajoutMesure").find("input.inputSaveAuto").off().change(
				function () {
					saveAttribut(this, $(this).is(":checked"));
				}
			);
		}, error: showErrorMesure
	});
}

function afficheCarteOnLoad() {
	document.getElementById('map').innerHTML = "";
	var hasPerimetre = $("#hasPerimetreAction").val();
	var latitudeElt = $("#latitudeAction");
	var longitudeElt = $("#longitudeAction");
	var latitude = parseFloat(latitudeElt.val().replace(",", "."));
	var longitude = parseFloat(longitudeElt.val().replace(",", "."));
	var perimetreUrl = $("#perimetreUrlAction").val();
	var nomPublic = $("#nomPublicAction").val();
	var apiKey = $("#geoportailApiKey").val();
	Gp.Services.getConfig({
		apiKey: apiKey,
		onSuccess: function () {
			var map = createMap("map", "mapPlaceHolder");
			map.initialize(latitude, longitude);
			if (hasPerimetre) {
				map.addKml(perimetreUrl);
			}
			if (latitude && longitude) {
				map.addMarker(latitude, longitude, nomPublic);
			}
			$(".updateMap").blur(
				function () {
					var lat = latitudeElt.val().replace(',', '.');
					var long = longitudeElt.val().replace(',', '.');
					if (lat !== '' && long !== '' && $.isNumeric(lat) && $.isNumeric(long)) {
						map.replaceMarker(parseFloat(lat), parseFloat(long), nomPublic, true)
					} else if (lat === '' || long === '') {
						map.removeMarker(true);
					}
				}
			);
			map.addOnClickListener(function (latitude, longitude) {
				map.replaceMarker(latitude, longitude, nomPublic, false);
				latitudeElt.val(latitude);
				longitudeElt.val(longitude);
				saveAttribut(latitudeElt, latitude, function() {
					saveAttribut(longitudeElt, longitude);
				});
			});
		}
	});
}

var contactImp = undefined;
var deleteContact = undefined;

function ajoutContactAction(value, modalUrl, e) {
	contactImp = e.target.id;
	contactImp = contactImp.substr(contactImp.length - 1);
	var idEcocite = $('#idEcocite').val();
	$.ajax({
		method: 'GET',
		url: modalUrl + value,
		contentType: "application/json",
		success: function (data) {
			$("#modalAjoutContact").html(data).modal('show');
			$('select[name=idEcocite]').val(idEcocite);
		}
	});
}

function viewContactAction(idContact, modalUrl, e) {
	e.stopPropagation();
	$.ajax({
		method: 'GET',
		url: modalUrl + idContact,
		contentType: "application/json",
		success: function (data) {
			modalEditionContactReady(idContact);
			$("#modalAjoutContact").html(data);
			$("#modalAjoutContact").modal('show');
		}
	});
}

function modalEditionContactReady(id) {
	$('[data-toggle="tooltip"]').tooltip();
	deleteContact = createDeleteContactFunction(id);
}

function cloneAction() {
	var idInput = $("#idOriginal.clone-action");
	var nomImput = $("#nom.clone-action");
	var numeroInput = $("#numero.clone-action");
	idInput.parent().find('p.text-danger').hide();
	nomImput.parent().find('p.text-danger').hide();
	numeroInput.parent().find('p.text-danger').hide();
	var data = {
		idOriginal: idInput.val(),
		nom: nomImput.val(),
		numero: numeroInput.val()
	};
	jQuery.ajax({
		url: '/bo/actions/clone',
		cache: false,
		contentType: "application/json",
		processData: true,
		data: JSON.stringify(data),
		method: 'POST',
		success: function(data) {
			window.location = "/bo/actions/edition/" + data;
		},
		error: function(data) {
			if (data && data.responseJSON && data.responseJSON.fieldErrors && data.responseJSON.fieldErrors.length > 0) {
				var errors = data.responseJSON.fieldErrors
				errors.forEach(function (error) {
					$('#' + error.field + '.clone-action').parent().find('p.text-danger').text(error.defaultMessage).show()
				})
			}
		}
	});
}

function createDeleteContactFunction(id) {
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
			success: function (idContact) {
				if (contactImp !== undefined) {
					var idAction = $('#idAction').val();
					if (contactImp == "1") {
						if ($('#contactPrin').val().length < 1) {
							createAssoContactObject(idContact, idAction, 'actions', contactImp, function () {
								location.reload();
							});
						}
					}
					if (contactImp == "2") {
						if ($('#contactSecond').val().length < 2) {
							createAssoContactObject(idContact, idAction, 'actions', contactImp, function () {
								location.reload();
							});
						}
					}
				}
			},
			error: onErrorEditionObjet
		});
	}
}

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

function onSuccessEditionObjet() {
	location.reload();
}

function onErrorEditionObjet(data) {
	var rep = JSON.parse(data.responseText);
	if (rep.fieldErrors) {
		rep.fieldErrors.forEach(function (fe) {
			$('[name=' + fe.field + ']').closest('.form-group').find('p.text-danger').text(fe.defaultMessage).show();
		})
	}
}
