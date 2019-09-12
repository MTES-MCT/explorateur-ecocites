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

var ecocitesJSON = [];

var token_csrf = $("meta[name='_csrf']").attr("content");
var header_csrf = $("meta[name='_csrf_header']").attr("content");

$.urlParam = function(name, location){
	var url = location || window.location.href;
	name = name.replace(/[\[\]]/g, '\\$&');
	var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
		results = regex.exec(url);
	if (!results) return null;
	if (!results[2]) return '';
	return decodeURIComponent(results[2].replace(/\+/g, ' '));
};

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
})($);

function hideModal() {
	$(".modal").modal('hide');
	$("#popup").empty().hide();
	$(".modal-backdrop").remove();
	$(".cookie-dialog").css("z-index", 3);
	pushState({showModal: false, home: true}, '/', false);
}

function simpleHideModal() {
	$(".modal").modal('hide');
}

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

function openModalShowEcocite(ecociteId, e, shouldCookiePopHigher) {
	if (shouldCookiePopHigher) {
		$(".cookie-dialog").css("zIndex", 20000)
	}
	pushEcociteModalState(ecociteId);
	$.ajax({
		type: "GET",
		url: '/modal/ecocite/' + ecociteId,
		contentType: "application/json",
		async: false,
		success: function (data) {
			$("#popup").html(data).modal('show');
			onModalEcociteLoadSuccess();
		}
	});
	if (e) {
		e.preventDefault();
	}
	return false;
}

window.onload = function() {
	var acceptCookie = getCookie("accept-cookies");
	if(!acceptCookie){
		deleteGACookies();
		$(".cookie-dialog").show();
	} else if (acceptCookie === 'true') {
		startGoogleAnalytics()
	}
};

if (!String.prototype.format) {
	String.prototype.format = function() {
		var args = arguments;
		return this.replace(/{(\d+)}/g, function(match, number) {
			return typeof args[number] !== 'undefined' ? args[number] : match;
		});
	};
}

function openModalEcocite(ecociteId, shouldCookiePopHigher) {
	if (shouldCookiePopHigher) {
		$(".cookie-dialog").css("zIndex", 20000)
	}
	pushEcociteModalState(ecociteId);
	$.ajax({
		type: "GET",
		url: '/modal/ecocite/' + ecociteId,
		contentType: "application/json",
		async: false,
		success: function(data) {
			$("#popup").html(data);
			onModalEcociteLoadSuccess();
		}
	});
}

function onModalEcociteLoadSuccess() {
	$(function () {
		$(document).on('shown.bs.tab', function (e) {
			var target = $(e.target).attr("href");
			var idEcocite= $("#idEcocite").val();
			if (target === "#mapEcociteOnglet") {
				$.ajax({
						type: 'GET',
						url: "modal/ecocite/"+idEcocite+"/mapActionCoordonnee",
						async: false,
						success: function(data) {
								afficheMapEcocite(data);
						}
				})
			}
		});
	});
	$(function () {
		$('[data-toggle="tooltip"]').tooltip()
	});

	$("#contact-form").submit(function () {
		return false;
	});
}

function onModalActionLoadSuccess() {
	$(function () {
		$(document).on('shown.bs.tab', function (e) {
			var target = $(e.target).attr("href");
			if (target === "#mapActionOnglet") {
				afficheMapAction();
			}
		});
	});
	$(function () {
		$('[data-toggle="tooltip"]').tooltip()
	});
	$("#contact-form").submit(function () {
		return false;
	});
}

function filtrageFunction() {
	var data = {
		axePrincipale: $("#filter-axePrincipale").val(),
		ecocite: $("#filter-ecocite").val(),
		etatAvancement: $("#filter-progressStatus").val(),
		objectifVille: $("#filter-objectifVille").val(),
		finalite: $("#filter-finalite").val()
	};
	pushFilterModalState(data, true);
	$.ajax({
		type: 'POST',
		url: "/modal/showAxis/filtrage",
		data: data,
		async: false,
		beforeSend: function (xhr) {
			$('#recherche-results').html("");
			xhr.setRequestHeader(header_csrf, token_csrf);
		},
		success: function(data) {
			$('#recherche-results').html(data);
		}
	})
}

function submitSearchQuery(searchedString) {
	if (searchedString.length > 3) {
		$('#search-result').html("");
		pushSearchTextModal(searchedString, true);
		$.ajax({
			type: 'GET',
			url: "/modal/recherche/search",
			data: {searchedString: searchedString},
			contentType: "application/json",
			async: false,
			success: function(data) {
				$('#search-result').html(data);
			}
		})
	} else{
		$('#search-result').html("");
	}
}

function onModalRechercheLoadSuccess() {
	var axis = $("#axisModal").val();
	var selectAxe = $("#selectAxeModal").val();
	if (axis) {
		$('.filter--modal').on('change', function () {
			filtrageFunction();
		});
		if (selectAxe) {
			$(function () {
				filtrageFunction();
			});
		}
	} else {
		$('#searched-string').on('keyup', function () {
			submitSearchQuery($(this).val())
		});
	}
}
function afficheMapAction() {
	document.getElementById('mapAction').innerHTML = "";
	var apiKey = $("#geoportailApiKey").val();
	var hasPerimetre = $("#hasPerimetreAction").val();
	var nomPublic = $("#nomPublicAction").val();
	var perimetreUrl = $("#perimetreUrlAction").val();
	var latitude = parseFloat($("#latitudeAction").val().replace(",", "."));
	var longitude = parseFloat($("#longitudeAction").val().replace(",", "."));
	if ((latitude && longitude) || hasPerimetre) {
		Gp.Services.getConfig({
			apiKey: apiKey,
			onSuccess: function () {
				var map = createMap("mapAction", "mapActionPlaceHolder");
				map.initialize(latitude, longitude);
				map.enablePopup();
				if (hasPerimetre) {
					map.addKml(perimetreUrl);
				}
				if (latitude && longitude) {
					map.addMarker(latitude, longitude, nomPublic);
				}
			}
		});
	}
}

function afficheMapEcocite(data) {
	document.getElementById('mapEcocite').innerHTML = "";
	var apiKey = $("#geoportailApiKey").val();
	var nom = $("#nomPublicEcocite").val();
	var hasPerimetreStrategique = $("#hasPerimetreStrategiqueEcocite").val();
	var hasPerimetreOperationnel = $("#hasPerimetreOperationnelEcocite").val();
	var perimetreStrategiqueUrl = $("#perimetreStrategiqueUrlEcocite").val();
	var perimetreOperationnelUrl = $("#perimetreOperationnelUrlEcocite").val();
	var latitude = parseFloat($("#latitudeEcocite").val().replace(",", "."));
	var longitude = parseFloat($("#longitudeEcocite").val().replace(",", "."));
	if ((latitude && longitude) || hasPerimetreOperationnel || hasPerimetreStrategique) {
		Gp.Services.getConfig({
			apiKey: apiKey,
			onSuccess: function () {
				var map = createMap("mapEcocite", "mapEcocitePlaceHolder");
				map.initialize(latitude, longitude);
				map.enablePopup();
				if (hasPerimetreStrategique) {
					map.addKml(perimetreStrategiqueUrl);
				}
				if (hasPerimetreOperationnel) {
					map.addKml(perimetreOperationnelUrl);
				}
				if (latitude && longitude) {
					map.addMarker(latitude, longitude, nom);
				}
				if (data !== undefined) {
					for (var i = 0, l = data.length; i < l; i++) {
						if (data[i].urlPerimetre != null) {
							map.addKml(data[i].urlPerimetre, true);
						}
						if (data[i].longitude != null && data[i].latitude != null) {
							var content = '<a class="cursorPointer" onclick="openModalRechercheAction(\'/modal/action/{0}\', event);"><p>{1}</p></a>'.format(data[i].id, escapeHTML(data[i].name))
							map.addMarkerWithLink(parseFloat(data[i].latitude), parseFloat(data[i].longitude), data[i].name, content);
						}
					}
				}
			}
		});
	}
}


function soumissionContactForm() {
	$("#emailContact-string").css({'margin-bottom': '0'}).closest('div').find('.message-danger').text("");
	$("#messageContact-string").css({'margin-bottom': '0'}).closest('div').find('.message-danger').text("");
	$('.message-success').hide();
	$('.message-error').hide();
	$.ajax({
		type: "POST",
		url: "/modal/contact",
		data: $("#contact-form").serialize(),
		contentType: "application/x-www-form-urlencoded",
		success: function () {
			$('.message-error').hide();
			$('.message-success').show();
			setTimeout(function () {
				location.reload();
			}, 1000);
		},
		error: function (data) {
			var parsedResponse = JSON.parse(data.responseText);
			parsedResponse.fieldErrors.forEach(function (fError) {
				$("#" + fError.field + "-string").css({'margin-bottom': '30'})
					.closest('div').find('.message-danger').text(fError.defaultMessage).show();
			});
			$('.message-success').hide();
			$('.message-error').show();
		}
	});
}

function afficheReunion() {
	afficheCarte(-21.138889, 55.507500, 10);
}

function afficheFrance() {
	afficheCarte(46.817621, 3.009651, 6);
}

function hideNavbar() {
	$("#bs-example-navbar-collapse-1").collapse('hide');
}

function scrollToSection(s){
	hideNavbar();
	$("html, body").animate({
		scrollTop: $(s).position().top - $('#navbar-header-top-home').height()
	});
}

function openModalShowAxis(filters, e) {
	var pushInHistory = $.urlParam("searchMode", location.href) === "1";
	pushFilterModalState(filters, pushInHistory);
	$.ajax({
		type: "GET",
		url: "/modal/showAxis",
		data : filters,
		contentType: "application/json",
		success: function (data) {
			$("#popup").html(data).modal('show');
			onModalRechercheLoadSuccess();
		},
		error: function (error) {
			console.log("Error ! " + error);
		}
	});
	if (e) {
		e.preventDefault();
	}
	return false;
}

function opshowenModalShowEcocite(ecociteId, e, shouldCookiePopHigher) {
	if (shouldCookiePopHigher) {
		$(".cookie-dialog").css("zIndex", 20000)
	}
	pushEcociteModalState(ecociteId);
	$.ajax({
		type: "GET",
		url: '/modal/ecocite/' + ecociteId,
		contentType: "application/json",
		async: false,
		success: function (data) {
			$("#popup").html(data);
			$("#popup").modal('show');
			onModalEcociteLoadSuccess();
		}
	});
	if (e) {
		e.preventDefault();
	}
	return false;
}

function openModalShowAction(actionId, e, shouldCookiePopHigher) {
	if (shouldCookiePopHigher) {
		$(".cookie-dialog").css("zIndex", 20000)
	}
	$.ajax({
		type: "GET",
		url: '/modal/action/' + actionId,
		contentType: "application/json",
		async: false,
		success: function (data) {
			$("#popup").html(data).modal('show');
			pushActionModalState(actionId);
			onModalActionLoadSuccess();
		}
	});
	if (e) {
		e.preventDefault();
	}
	return false;
}

function openModalRecherche(e) {
	hideNavbar();
	pushSearchTextModal('');
	$.ajax({
		type: "GET",
		url: "/modal/recherche",
		contentType: "application/json",
		async: false,
		success: function (data) {
			var modal = $("#popup");
			modal.html(data).on('shown.bs.modal', function () {
				$('#searched-string').focus()
			});
			modal.modal('show');
			onModalRechercheLoadSuccess(data);
		}
	});
	if (e !== undefined) {
		e.preventDefault();
	}
	return false;
}

function openModalRechercheWithText(text) {
	$.ajax({
		type: "GET",
		url: "/modal/recherche",
		contentType: "application/json",
		async: false,
		success: function (data) {
			$("#popup").html(data).modal('show');
			searchInput = $('#searched-string');
			searchInput.focus();
			if (text) {
				searchInput.val(text);
				submitSearchQuery(text)
			}
			onModalRechercheLoadSuccess(data);
		}
	});
}

function openModalMentionsLegales() {
  $("#popupmention").modal('show');
	return false;
}

function openModalInfosCookies() {
	var modal = $("#popupmention");
	modal.off("shown.bs.modal");
	modal.on("shown.bs.modal", function () {
		modal.animate({
			scrollTop: $('#cookie-section').offset().top
		});
		modal.off("shown.bs.modal");
	});
	modal.modal('show');
	return false;
}

function openModalRechercheAction(url, e, shouldCookiePopHigher) {
	if (shouldCookiePopHigher) {
		$(".cookie-dialog").css("zIndex", 20000)
	}
	$.ajax({
		type: "GET",
		url: url,
		contentType: "application/json",
		async: false,
		success: function (data) {
			$("#popup").html(data);
			if (url.includes('action')) {
				onModalActionLoadSuccess();
				pushActionModalState(url.split(/[\/]+/).pop());
			} else {
				onModalEcociteLoadSuccess();
				pushEcociteModalState(url.split(/[\/]+/).pop());
			}
		}
	});
	if (e) {
		e.preventDefault();
	}
	return false;
}

function addContrast(){
	$("body").addClass("inv-c");
	$("#popup").addClass("inv-c");
}

function removeContrast(){
	$("body").removeClass("inv-c");
	$("#popup").removeClass("inv-c");
}

function afficheCarte(latitude, longitude, zoom){
	document.getElementById('map').innerHTML = "";
	var apiKey = $("#geoportailApiKey").val();
		Gp.Services.getConfig({
			apiKey: apiKey,
			onSuccess: function () {
				var map = createMap("map", "mapPlaceHolder");
				map.initialize(latitude, longitude, zoom);
				map.enablePopup();
				for (var i = 0; i < ecocitesJSON.length; i++) {
					var ecocite = ecocitesJSON[i];
					var content = '<a class="cursorPointer" onclick="openModalShowEcocite(\'{0}\', event);"><p>{1}</p></a>'.format(ecocite.id, escapeHTML(ecocite.nom));
					if (ecocite.latitude && ecocite.longitude) {
						map.addMarkerWithLink(parseFloat(ecocite.latitude), parseFloat(ecocite.longitude), ecocite.nom, content);
					}
				}
			}
		});
}

function manageCookies(accept){
	var date = new Date();
	date.setTime(date.getTime() + (365 * 86400000)); //24 * 60 * 60 * 1000
	window.document.cookie = "accept-cookies="+accept+"; expires=" + date.toUTCString() + "; path=/";
	if(accept==="true"){
		startGoogleAnalytics();
	}
	else{
		rejectCookies();
	}
}

function getCookie(name) {
	var value = "; " + document.cookie;
	var parts = value.split("; " + name + "=");
	if (parts.length === 2) return parts.pop().split(";").shift();
}

// var UA_code_GA = 'UA-80421845-4';
function startGoogleAnalytics() {
	$.ajax({
		type: "GET",
		url: "/analytics",
		success: function (data) {
			$('body').append(data)
		}
	})
}
// function startGoogleAnalytics() {
// 	(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
// 		(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
// 		m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
// 	})(window,document,'script','https://www.google-analytics.com/analytics.js','ga');
//
// 	window.document.cookie = 'ga-disable-'+UA_code_GA+'=; Expires=Thu, 01 Jan 1970 00:00:00 GMT; Path=/';
//
// 	ga('create', UA_code_GA, 'auto');
// 	ga('send', 'pageview');
// }

function rejectCookies() {
	var date = new Date();
	date.setTime(date.getTime()  + (365 * 86400000)); //24 * 60 * 60 * 1000
	// création du cookie spécifique empêchant Google Analytics de démarrer
	window.document.cookie = 'ga-disable-' + UA_code_GA + '=true; Expires=' + date.toUTCString() + '; Path=/';
	// insertion de cette valeur dans l'objet window
	window[ 'ga-disable-' + UA_code_GA ] = true;
	deleteGACookies();
}

function deleteGACookies() {
	// suppression de tous les cookies précédemment créés par Google Analytics
	var GA_COOKIE_NAMES = ['__utma', '__utmb', '__utmc', '__utmz', '_ga', '_gat','_gid'];
	var i=0;
	for(i;i<GA_COOKIE_NAMES.length;i++){
		document.cookie = GA_COOKIE_NAMES[i]+'=; Domain=.ipsosenso.com; Expires=Thu, 01 Jan 1970 00:00:00 GMT; Path=/';
		// $.cookie(GA_COOKIE_NAMES[i], null, { path: '/' });
	}
}
