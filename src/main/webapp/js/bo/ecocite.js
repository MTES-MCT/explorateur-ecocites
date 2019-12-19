
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

function accepteValidationCategorisationEcocite(typeOnglet, ecociteId) {
	$.ajax({
		url: '/bo/ecocites/' + typeOnglet + '/accepte_validation/' + ecociteId,
		method: 'POST',
		success: function (data) {
			$('#confirmationValidationCategorisationIndicateur').modal('hide');
			$('body').removeClass('modal-open');
			$('.modal-backdrop').remove();
			if(data === true) {
				updateCurrentTab();
				updateTabState();
			} else {
				$('#erreurValidationCategorisationIndicateur').modal('show').find('.alert-danger').html(data);
			}
		},
		error: function (error) {
			console.log("Error ! " + error);
		}
	})
}

function updateTabState() {
	var idEcocite = $("#idEcocite").val();
	$.ajax({
		type: "GET",
		url: "/bo/ecocites/edition/" + idEcocite + "/onglet",
		success: function(data) {
			$("#tab-list").replaceWith(data);
			$("a[href='" + "#" + $(".tab-pane.active").attr("id") + "']").tab("show");
			$('.listOngletEcocite a[data-toggle="tab"]').on('click', function (e) {
				var clickedElt = $(e.target);
				if (!clickedElt.is('a[data-toggle="tab"]')) {
					clickedElt = clickedElt.closest('a[data-toggle="tab"]')
				}
				if (!clickedElt.closest("li.nav-item").hasClass("state-impossible")) {
					var target = clickedElt.attr("href");
					loadOnglet(target.replace("#", ""), idEcocite);
				}
			});
		}
	});
}

function annulationValidationCategorisationEcocite(typeOnglet, ecociteId) {
	$.ajax({
		url: '/bo/ecocites/' + typeOnglet + '/annulation_validation/' + ecociteId,
		method: 'POST',
		success: function (data) {
			$('#confirmationValidationCategorisationIndicateur').modal('hide');
			$('body').removeClass('modal-open');
			$('.modal-backdrop').remove();
			if(data === true) {
				updateCurrentTab();
				updateTabState();
			} else {
				$('#erreurValidationCategorisationIndicateur').modal('show').find('.alert-danger').html(data);
			}
		},
		error: function (error) {
			console.log("Error ! " + error);
		}
	})
}

function showAjoutIndicateurMesure(type){
	$( ".templateAjout"+type ).show();
	$( ".showAjout"+type+"Bouton" ).hide();
	$( ".ajout"+type+"Bouton").show();
}

function cancelIndicateurMesure(type){
	$( ".templateAjout"+type ).hide();
	$( ".showAjout"+type+"Bouton" ).show();
	$( ".ajout"+type+"Bouton").hide();
	$("#value"+type+"Ajout").val("");
	$("#date"+type+"Ajout").val("");
}

function reloadPresentationFun(ecociteId) {
	return function () {
		loadPresentation(ecociteId)
	}
}

function deleteIndicateurMesure(elt, type){
	var idElement = $("#mesureToDelete").val();
	$.ajax({
		type: "DELETE",
		url: "/bo/ecocites/indicateur/" + type + "/" + idElement,
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

function loadIndicateurSaisieMesure(domEle){
	var idAssoObjetIndicateur = $(domEle).data("objectid");
	$(".assoIndicateurEcocite ").removeClass("bg-grey-light");
	$(domEle).addClass("bg-grey-light");
	$.ajax({
		method: 'GET',
		url: "/bo/ecocites/indicateur/loadEditMesureIndicateurs/"+idAssoObjetIndicateur,
		contentType: "application/json",
		success: function (data) {
			var ajoutMesure = $("#ajoutMesure");
			ajoutMesure.html(data);
			ajoutMesure.find("input.inputSaveAuto").change(
				function() { saveAttribut(this, $(this).is(":checked"), updateTabState); }
			);
			ajoutMesure.find("textarea.inputSaveAuto").change(
				function() { saveAttribut(this, $(this).val())}
			);
			$(function () {
				$('[data-toggle="tooltip"]').tooltip()
			});
			appliqueDatePickerSansSaveAuto();
			var printDecimal = $("#printDecimal").val();
			var assoIndicateurEcociteId = $("#assoIndicateurEcociteId").val();
			chart = initializeEmptyChartLine("chartMesureIndicateur", printDecimal === "NOMBRE_DEC_0_1");
			doUpdateChart = doUpdateChartFunCreator(assoIndicateurEcociteId, chart);
			doUpdateChart();
		}
	});
}

function loadOnglet(name, ecociteId) {
	$("#" + name).addClass("active show").siblings().removeClass("active show");
	var me = $('a[href="#' + name + '"]').addClass("active");
	$(".body > .tab-content > .tab-pane").html("");
	me.siblings().removeClass("active show");
	var parent = me.closest('li.nav-item');
	parent.children('a.nav-link').addClass("active");
	parent.siblings().children("a").removeClass("active show");
	$(".tab-content").children().html("");
	if (name === "presentation") {
		loadPresentation(ecociteId);
	} else if (name === "indicateurOngletRealisation" || name === "indicateurOngletResultat") {
		loadIndicateur(name, ecociteId);
	} else if(name === "mesureIndicateurOnglet"){
		loadMesureIndicateur(ecociteId);
	} else if (name === "categorisation") {
		loadCategory(ecociteId);
	} else if (name === "questionnaire_ecocite") {
		loadEvaluationFacteur(name, ecociteId);
	}
}

function formatStateContact (state) {
	if ((!state.id) || state.text==="Choisissez un contact") { return escapeHTML(state.text); }
	var modalContactUrl=$("#modalContactUrl").val();
	return $(
		'<span>' + escapeHTML(state.text) + '<i class="fa fa-search cursorPointer m-l-10" onclick="viewContactEcocite(\''+state.id+'\','+modalContactUrl+', event);"></i>' + '</span>'
	);
}

function loadPresentation(ecociteId) {
	$.ajax({
		method: 'GET',
		url: "/bo/ecocites/presentation/" + ecociteId,
		contentType: "application/json",
		success: function (data) {
			$("#presentation").html(data).find(".inputSaveAuto").blur(
				function () {
					saveAttribut(this, this.value);
				}
			);
			$(function () {
				$('[data-toggle="tooltip"]').tooltip()
			});
			var selectTags = $(".select-tags");
			selectTags
				.select2({
					tags: true,
					maximumSelectionLength: 15
				}).on('change.select2', function (e) {
				saveAttribut(e.currentTarget, $(e.currentTarget).val().join(';'));
			});
			appliqueDatePicker();
			var contactPrincipal = $("#contactPrin");
			contactPrincipal.select2({
				templateSelection: formatStateContact,
				maximumSelectionLength: 1
			})
				.on('select2:selecting', function (e) {
					var oldVal = contactPrincipal.val();
					createAssoContactObject(e.params.args.data.id, ecociteId, 'ecocites', '1',
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
					deleteAssoContactObjet(e.params.args.data.id, ecociteId, 'ecocites',
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
					createAssoContactObject(e.params.args.data.id, ecociteId, 'ecocites', '2',
						function () {
							contactSecondaire.closest('.form-group')
								.find('p.text-danger')
								.hide();
							if(oldVal.length>0){
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
					deleteAssoContactObjet(e.params.args.data.id, ecociteId, 'ecocites',
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
			afficheOnloadCarte();
		}
	});
	// $(function () {
	// 	$('#partProjet').siblings('.select2-container').find('.select2-selection--multiple .select2-selection__rendered').css("display", "grid");
	// });
}

function loadCategory(ecociteId){
	$.ajax({
		method: 'GET',
		url: "/bo/ecocites/finalite/" + ecociteId,
		contentType: "application/json",
		success: function (data) {
			$("#categorisation").html(data).find(".inputSaveAuto" ).blur(
				function() { saveAttribut(this, this.value);}
			);
			triggerSortable("#categorisation", ecociteId);
            loadCommentaire("categorisation", ecociteId);
			updateCurrentTab = function () { loadCategory(ecociteId) };
			$(function () {
				$('[data-toggle="tooltip"]').tooltip()
			});
		}
	});
}
function saveAttribut(domEle, value, callback){
	$(domEle).parent().find(".text-danger").hide();
	$(domEle).removeClass("is-invalid");
	var objectId = $(domEle).data("objectid");
	var referenceId = $(domEle).data("referenceid");
	var referenceTypeObjet = "ecocite";
	var objectClass = $(domEle).data("objectclass");
	var idAttribut = $(domEle).attr("name");
	var checkBoxValue="";
	var checked = $(domEle).is(":checked");
	if($(domEle).attr("type") === "text"&& $(domEle).closest("div").find("input.form-check-input")){
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
		data : bean,
		contentType: "application/json; charset=utf-8",
		dataType: 'json',
		success: function(data) {
			if(data && data.fieldErrors && data.fieldErrors[0]) {
				$(domEle).addClass("is-invalid");
				$(domEle).parent().find(".text-danger").html(data.fieldErrors[0].defaultMessage).show();
			} else if(data && data.fieldErrors && data.globalErrors[0]) {
                $('#erreurSauvegardPresentation').modal('show').find('.alert-danger').html(data.globalErrors[0].defaultMessage);
			}
			if (callback) {
				callback();
			}
		},
		error: function() {
			$(domEle).parent().find($("filed_error")).html("Erreur technique");
		}
	});
}

function reloadEtiquetteCategory(ecociteId) {
	var category = $("#currentCategory").val();
	var categoryId = $("#currentCategoryId").val();
	showListEtiquettesForEcocite(categoryId, category, undefined, ecociteId);
}


function showListEtiquettesForEcocite(idCategorie, cat, etqDomElem, ecociteId) {
	$.ajax({
		url: '/bo/ecocites/' + cat + '/etiquettes/' + idCategorie + '/' + ecociteId,
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

function triggerSortable(tabPanelId, ecociteId) {
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
			onAdd: function(evt) {
				majEtiquette(evt.item, 1, ecociteId);
				$(evt.item).children("div").children(".col-2").append("<i class=\"fa fa-close delete-etiquette\" onclick=\"deleteCurrentEtiquette(this," + "'" + ecociteId + "'" + ")\" style=\"margin-left: 5px\"></i>")
			},
			onSort: function(evt) {
				$(evt.to).prepend(evt.item);
				if (e1.children('.etiquette-round').length < 1) {
					e1.closest('.content').children('.info-etq-empty').attr("hidden", false);
				}else {
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
			onAdd: function(evt) {
				majEtiquette(evt.item, 1, ecociteId);
				$(evt.item).children("div").children(".col-2").append("<i class=\"fa fa-close delete-etiquette\" onclick=\"deleteCurrentEtiquette(this," + "'" + ecociteId + "'" + ")\" style=\"margin-left: 5px\"></i>");
			},
			onSort: function(evt) {
				$(evt.to).prepend(evt.item);
				if (e1s.children('.etiquette-round').length < 1) {
					e1s.closest('.content').children('.info-etq-empty').attr("hidden", false);
				}else {
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
			onAdd: function(evt) {
				majEtiquette(evt.item, 2, ecociteId);
				$(evt.item).children("div").children(".col-2").append("<i class=\"fa fa-close delete-etiquette\" onclick=\"deleteCurrentEtiquette(this," + "'" + ecociteId + "'" + ")\" style=\"margin-left: 5px\"></i>")
			},
			onSort: function(evt) {
				$(evt.to).prepend(evt.item);
				if (e2.children('.etiquette-round').length <1) {
					e2.closest('.content').children('.info-etq-empty').attr("hidden", false);
				}else {
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
			onAdd: function(evt) {
				majEtiquette(evt.item, 3, ecociteId);
				$(evt.item).children("div").children(".col-2").append("<i class=\"fa fa-close delete-etiquette\" onclick=\"deleteCurrentEtiquette(this," + "'" + ecociteId + "'" + ")\" style=\"margin-left: 5px\"></i>")
			},
			onSort: function(evt) {
				$(evt.to).prepend(evt.item);
				if (e3.children('.etiquette-round').length <1) {
					e3.closest('.content').children('.info-etq-empty').attr("hidden", false);
				}else {
					e3.closest('.content').children('.info-etq-empty').attr("hidden", true);
				}
			}
		});
	}
}

function loadCommentaire(activeEtape, ecociteId) {
	var codeEtape = activeEtape.replace("#","");
	$.ajax({
		url: '/bo/ecocites/etape/' + ecociteId + '/' + codeEtape,
		method: 'GET',
		success: function(res) {
			if (res) {
				$(".tab-content > div#" + codeEtape + " textarea.commentaire")
					.val(res.commentaire)
					.data("idetape", res.id);
			}
		},
		error: function(jqXHR, textStatus, error) {
			console.log("Error ! " + error);
		}
	});
}

function majEtiquette(domElem, poid, ecociteId) {
	var jsonObj = {
		idEtiquette: $(domElem).data("idetiquette"),
		idAsso: $(domElem).data("idasso"),
		idObjet: ecociteId,
		poid: poid
	};
	$.ajax({
		url: '/bo/ecocites/' + $(domElem).data("cat") + '/majEtiquette',
		method: 'POST',
		data: JSON.stringify(jsonObj),
		contentType: "application/json; charset=utf-8",
		dataType: 'json',
		success: function(data) {
			$(domElem).data("idasso", data.idAsso);
			$(domElem).data("idetiquette", data.etiquette.id);
		},
		error: function() {
			var parent = $(domElem).parent();
			if (parent.children('.etiquette-round').length < 2) {
				parent.closest('.content').children('.info-etq-empty').attr("hidden", false);
			}else {
				parent.closest('.content').children('.info-etq-empty').attr("hidden", true);
			}
			$(domElem).remove();
		}
	})
}

function deleteCurrentEtiquette(evt, ecociteId) {
	var etiquette = $(evt).parent().parent().parent();
	var parent = etiquette.parent();
	deleteEtiquette(etiquette, ecociteId);
	if (parent.children('.etiquette-round').length < 2) {
		parent.closest('.content').children('.info-etq-empty').attr("hidden", false);
	}else {
		parent.closest('.content').children('.info-etq-empty').attr("hidden", true);
	}
	etiquette.remove();
}

function deleteEtiquette(domElem, ecociteId) {
	$.ajax({
		url: '/bo/ecocites/' + $(domElem).data("cat") + '/etiquette/' + ecociteId + '/' + $(domElem).data("idasso"),
		method: 'DELETE',
		success: function() {
			reloadEtiquetteCategory(ecociteId);
		},
		error: function(jqXHR, textStatus, error) {
			console.log("Error ! " + error);
		}
	})
}

function loadIndicateur(typeIndicateur, ecociteId){
	$("#indicateurOngletRealisation").html("");
	$("#indicateurOngletResultat").html("");
	$.ajax({
		method: 'GET',
		url: "/bo/ecocites/indicateur/loadIndicateurs/" + ecociteId + "/" +typeIndicateur,
		contentType: "application/json",
		success: function (data) {
			var id = "#" + typeIndicateur;
			$(id).html(data);
			$(function () {
				$('[data-toggle="tooltip"]').tooltip()
			});
			$(id + " .inputSaveAuto" ).blur(
				function() { saveAttribut(this, this.value);}
			);
			loadCommentaire(typeIndicateur, ecociteId);
			$('li .prev_link').click(function(){
				previous();
				return false;
			});
			$('li .next_link').click(function(){
				next();
				return false;
			});
			updateCurrentTab = function () {loadIndicateur($("#typeIndicateur").val(), $("#ecociteId").val());};
		}
	});
}

function loadMesureIndicateur(ecociteId){
	$.ajax({
		method: 'GET',
		url: "/bo/ecocites/indicateur/loadMesureIndicateurs/" + ecociteId,
		contentType: "application/json",
		success: function (data) {
			$("#mesureIndicateurOnglet").html(data);
		}
	});
}

function loadEvaluationFacteur(typeFacteurSucces, ecociteId, scrollBottom){
	$.ajax({
		method: 'GET',
		url: "/bo/ecocites/questionnaire/" + ecociteId + "/" + typeFacteurSucces,
		contentType: "application/json",
		success: function (data) {
			var id = "#" + typeFacteurSucces;
			$(id).html(data);
			$('html,body').scrollTop(0);
			$('[data-toggle="tooltip"]').tooltip();
			$( id+" .inputSaveAuto" ).blur(
				function() { saveAttribut(this, this.value);}
			);
			if(scrollBottom){
					window.scrollTo(0,document.body.scrollHeight);
			}
		}
	});
}

function majCommentaire(domComment, ecociteId) {
	var jsonObj = {
		id: $(domComment).data("idetape"),
		idObjet: ecociteId,
		commentaire: $(domComment).val()
	};
	$.ajax({
		url: '/bo/ecocites/etape/commentaire/majCommentaire',
		method: 'POST',
		data: JSON.stringify(jsonObj),
		contentType: "application/json; charset=utf-8",
		dataType: 'json',
		success: function(data) {
			$(domComment).data("idetape", data.id);
			$(domComment).val(data.commentaire);
		},
		error: function(jqXHR, textStatus, error) {
			console.log("Error ! " + error);
		}
	})
}

function onChangeFiltre(ecociteId, typeIndicateur) {
	var params = {};
	var idDomaine = $("#filtreDomaine").val();
	if (idDomaine !== "") {
		params.idDomaine = idDomaine;
	}
	var idObjectif =$("#filtreObjectif").val();
	if (idObjectif !== "") {
		params.idObjectif = idObjectif;
	}
	getIndicateursFiltres(params, ecociteId, typeIndicateur)
}

function getIndicateursFiltres(params, ecociteId, typeIndicateur, idIndicateurToSelect) {
	$.ajax({
		method: 'GET',
		url: "/bo/ecocites/indicateur/filtreIndicateurs/" + ecociteId + "/" + typeIndicateur,
		data: params,
		contentType: "application/json",
		success: function (data) {
			$("#listeIndicateurFiltreeEcocite").html(data);
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

function loadIndicateurInfo(domEle){
	var idIndicateur = $(domEle).data("objectid");
	$(".indicateurRecommande ").removeClass("bg-grey-light");
	$(domEle).addClass("bg-grey-light");
	loadIndicateurInfoId(idIndicateur)
}

function loadIndicateurInfoId(idIndicateur){
	$.ajax({
		method: 'GET',
		url: "/bo/ecocites/indicateur/loadInfoIndicateur/" + idIndicateur,
		contentType: "application/json",
		success: function (data) {
			$("#indicateurDescription").html(data);
			$('[data-toggle="tooltip"]').tooltip();
		}
	});
}

function ajoutIndicateurInfo(domEle){
	var idIndicateur = $(domEle).data("objectid");
	var ecociteId = $("#ecociteId").val();
	var posteCalculValide = true;
	var uniteValide = true;
	var inputPosteCalcul = $("#calculeIndicateur");
	var inputUnite = $("#uniteIndicateur");
	if (inputPosteCalcul.length > 0) {
		var poste_calcule = inputPosteCalcul.val();
		if(poste_calcule===""){
			$("#calcule-error").show();
			posteCalculValide=false;
		}
		else{
			$("#calcule-error").hide();
			posteCalculValide=true;
		}
	}
	var unite = inputUnite.val();
	if(unite===""){
		$("#unite-error").show();
		uniteValide=false;
	}
	else{
		$("#unite-error").hide();
		uniteValide=true;
	}
	if(uniteValide && posteCalculValide) {
		var bean = JSON.stringify({
			idIndicateur: idIndicateur,
			idObjet: ecociteId,
			unite: unite,
			poste_calcule: poste_calcule
		});
		$.ajax({
			type: "POST",
			url: "/bo/ecocites/indicateur/ajoutToEcocite",
			data: bean,
			contentType: "application/json",
			success: function (data) {
				$(".errorAjoutInfo").remove();
				$("#assoIndicateurEcocite").append(data);
				resetIndicateurSelected();
				$.ajax({
					type: "GET",
					url: "/bo/ecocites/indicateur/can_request_validation/" + ecociteId,
					success: function (data) {
						if ( data === true ) {
							$('#button-submit-indic').attr('data-target', "#confirmationValidationCategorisationIndicateur")
						} else {
							$('#button-submit-indic').attr('data-target', "#modalCategorisationIndicateurNonComplete")
						}
					}
				});
			}
		});
	}
}

function deleteIndicateurInfo(domEle){
	var idIndicateurAsso = $(domEle).data("objectid");
	var ecociteId = $("#ecociteId").val();
	$.ajax({
		method: 'DELETE',
		url: "/bo/ecocites/indicateur/supprimeToEcocite/" + ecociteId + "/" + idIndicateurAsso,
		contentType: "application/json",
		dataType: 'json',
		success: function () {
			$(".assoIndicateurEcocite" + idIndicateurAsso).remove();
			$.ajax({
				type: "GET",
				url: "/bo/ecocites/indicateur/can_request_validation/" + ecociteId,
				success: function (data) {
					if ( data === true ) {
						$('#button-submit-indic').attr('data-target', "#confirmationValidationCategorisationIndicateur")
					} else {
						$('#button-submit-indic').attr('data-target', "#modalCategorisationIndicateurNonComplete")
					}
				}
			});
		}
	});
}

function switchTab(value){
	if("indicateurOngletRealisation" === value){
		$(".indicateurOngletResultat").trigger('click').addClass("active").addClass('show');
		$(".indicateurOngletRealisation").removeClass("active").removeClass('show');
	} else {
		$(".indicateurOngletRealisation").trigger('click').addClass("active").addClass('show');
		$(".indicateurOngletResultat").removeClass("active").removeClass('show');
	}
}

var currentPage=0;

function previous(){
	goTo(currentPage - 1);
}

function next(){
	goTo(currentPage + 1);
}

function goTo(page){
	var listIndicateurSize = Number($("#listIndicateurSize").val());
	if(page>=0 && page<= Number($("#maxPage").val())){
		currentPage = page;
		$(".pageIndicateur").hide();
		$(".pageIndicateur.pageIndicateur" + page).show();

		if((currentPage + 1) * 10 < listIndicateurSize){
			$('.indicateurCompteur span').html(currentPage * 10 +
				"-" + (currentPage + 1) * 10 + " sur " + listIndicateurSize);
		} else {
			$('.indicateurCompteur span').html(currentPage * 10 +
				"- " + listIndicateurSize + " sur " + listIndicateurSize);
		}
	}
}

function modalIndicateur(value){
	$.ajax({
		method: 'GET',
		url: "/bo/indicateurs/edition/ecocite/" + value,
		contentType: "application/json",
		data: {idObjet: $("#idEcocite").val()},
		success: function (data) {
			$("#modalAjoutIndicateur").html(data);
            $("#modalAjoutIndicateur").modal("show");
			modalEditionObjetReady(value);
		}
	});
}

function afficheOnloadCarte() {
	var nom = $("#nomPublicEcocite").val();
	var hasPerimetreStrategique = $("#hasPerimetreStrategiqueEcocite").val();
	var hasPerimetreOperationnel= $("#hasPerimetreOperationnelEcocite").val();
	var perimetreStrategiqueUrl = $("#perimetreStrategiqueUrlEcocite").val();
	var perimetreOperationnelUrl = $("#perimetreOperationnelUrlEcocite").val();
	var latitudeElt = $("#latitudeEcocite");
	var longitudeElt = $("#longitudeEcocite");
	var latitude = parseFloat(latitudeElt.val().replace(",", "."));
	var longitude = parseFloat(longitudeElt.val().replace(",", "."));
	var apiKey = $("#geoportailApiKey").val();
	Gp.Services.getConfig({
		apiKey: apiKey,
		onSuccess: function () {
			var map = createMap("map", "mapPlaceHolder");
			map.initialize(latitude, longitude);
			if (hasPerimetreStrategique) {
				map.addKml(perimetreStrategiqueUrl);
			}
			if (hasPerimetreOperationnel) {
				map.addKml(perimetreOperationnelUrl);
			}
			if (latitude && longitude) {
				map.addMarker(latitude, longitude, nom);
			}
			$(".updateMap").blur(
				function () {
					var lat = latitudeElt.val().replace(',', '.');
					var long = longitudeElt.val().replace(',', '.');
					if (lat !== '' && long !== '' && $.isNumeric(lat) && $.isNumeric(long)) {
						map.replaceMarker(parseFloat(lat), parseFloat(long), nom, true)
					} else if (lat === '' || long === '') {
						map.removeMarker()
					}
				}
			);
			map.addOnClickListener(function (latitude, longitude) {
				map.replaceMarker(latitude, longitude, nom, false);
				latitudeElt.val(latitude);
				longitudeElt.val(longitude);
				saveAttribut(latitudeElt, latitude, function() {
					saveAttribut(longitudeElt, longitude);
				});
			});
		}
	});
}

var contactImp=undefined;
var deleteContact = undefined;
function ajoutIndicateurMesure(type) {
	var idAssoObjetIndicateur = $("#assoIndicateurEcociteId").val();
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
		url: "/bo/ecocites/indicateur/ajoutMesure",
		data: bean,
		contentType: "application/json",
		success: function (data) {
			$(".templateAjout" + type).before(data).hide();
			$(".showAjout" + type + "Bouton").show();
			$(".ajout" + type + "Bouton").hide();
			$("#value" + type + "Ajout").val("");
			$("#date" + type + "Ajout").val("");
			if (doUpdateChart) {
				doUpdateChart();
				updateTabState();
			}
			$("#ajoutMesure").find("input.inputSaveAuto" ).off().change(
				function() { saveAttribut(this, $(this).is(":checked")); }
			);
		}, error: showErrorMesure
	});
}

function ajoutContactEcocite(value, modalUrl,e){
	contactImp=e.target.id;
	contactImp=contactImp.substr(contactImp.length - 1);
	var idEcocite= $('#idEcocite').val();
	$.ajax({
		method: 'GET',
		url: modalUrl + value,
		contentType: "application/json",
		success: function (data) {
			$("#modalAjoutContact").html(data).modal('show');
			$('select[name=idEcocite]').val(idEcocite)
		}
	});
}

function viewContactEcocite(id, modalUrl, e){
	e.stopPropagation();
	$.ajax({
		method: 'GET',
		url: modalUrl + id,
		contentType: "application/json",
		success: function (data) {
			$("#modalAjoutContact").html(data).modal('show');
			modalEditionContactReady(id)
		}
	});
}

function modalEditionContactReady(id) {
	$('[data-toggle="tooltip"]').tooltip();
	deleteContact = createDeleteContactFunction(id);
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

function createObjetJson(url,redirectUrl) {
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
				if (contactImp !==undefined) {
					var idEcocite=$('#idEcocite').val();
					if(contactImp==="1") {
						if($('#contactPrin').val().length<1) {
							createAssoContactObject(idContact, idEcocite, 'ecocites', contactImp,
								function () {
								});
						}
					}
					if(contactImp==="2") {
						if($('#contactSecond').val().length<2) {
							createAssoContactObject(idContact, idEcocite, 'ecocites', contactImp,
								function () {
								});
						}
					}
				}
				location.reload();
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
