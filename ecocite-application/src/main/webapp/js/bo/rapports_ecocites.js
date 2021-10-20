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

var mapEcocitesAction;
var actionFilter;
var ecociteFilter;
var downloadLink;
var downloadBtn;

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

function initRapportFilter(mapEcocites) {
	ecociteFilter = $("#download_ecocites_filter");
	downloadLink = $("#download_link");
	downloadBtn = $("#download_btn");
	ecociteFilter.change(onEcociteChange);
	downloadBtn.click(download).prop("disabled", true);
	updateFilters(mapEcocites);
}

function updateFilters(mapEcocites) {
	fillEcocite(mapEcocites);
}

function fillEcocite(mapEcocite) {
	var tmpEcocite = JSON.parse(mapEcocite);
	ecociteFilter.html("");
	ecociteFilter.append("<option value='{0}'>{1}</option>".format("-1", ""));
	tmpEcocite.forEach(function (ecocite) {
		if (ecocite.name.length > 50) {
			ecociteFilter.append("<option value='{0}'>{1}...</option>".format(ecocite.id, escapeHTML(ecocite.name.substring(0, 47))));
		} else {
			ecociteFilter.append("<option value='{0}'>{1}</option>".format(ecocite.id, escapeHTML(ecocite.name)));
		}
	});
	if(tmpEcocite.length<=0){
		$("#no_download").show();
	}
}

function onEcociteChange() {
	var idEcocite = ecociteFilter.val();
	if (!idEcocite || idEcocite === "-1") {
		downloadBtn.prop("disabled", true);
	} else {
		downloadBtn.prop("disabled", false);
	}
}

function download() {
	var idEcocite = ecociteFilter.val();
	if (idEcocite && idEcocite !== "-1") {
		downloadLink.prop("href", "/bo/rapports/ecocites/" + idEcocite);
		downloadLink.prop("download", "ecocite-" + idEcocite + "-" + dateNowToString() + ".odt");
		downloadLink.get(0).click();
	}
}

function dateNowToString() {
	var now = new Date(Date.now());
	return "{0}-{1}-{2}-{3}-{4}-{5}-{6}".format(now.getFullYear(), now.getMonth(), now.getDay(), now.getHours(), now.getMinutes(), now.getSeconds(), now.getMilliseconds());
}
