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

function pushState(state, url, replace) {
	if (replace) {
		window.history.replaceState(state, '', url);
	} else {
		window.history.pushState(state, '', url);
	}
}

function pushActionModalState(actionId) {
	var url = '/?action=' + actionId;
	var state = {
		type: 'action',
		actionId: actionId,
		showModal: true
	};
	pushState(state, url, location.search === '?action=' + actionId);
}

function pushEcociteModalState(ecociteId) {
	var url = '/?ecocite=' + ecociteId;
	var state = {
		type: 'ecocite',
		ecociteId: ecociteId,
		showModal: true
	};
	pushState(state, url, location.search === '?ecocite=' + ecociteId);
}

function pushSearchTextModal(searchQuery, replace) {
	var url = '/?searchMode=0&searchQuery=' + searchQuery;
	var state = {
		type: 'searchText',
		searchQuery: searchQuery,
		showModal: true
	};
	pushState(state, url, replace);
}

function getUrlModalState(filters) {
	var url = '/?searchMode=1';
	if (filters.axePrincipale) {
		url = url + '&axePrincipale=' + filters.axePrincipale;
	}
	if (filters.ecocite) {
		url = url + '&ecocite=' + filters.ecocite;
	}
	if (filters.etatAvancement) {
		url = url + '&etatAvancement=' + filters.etatAvancement;
	}
	if (filters.objectifVille) {
		url = url + '&objectifVille=' + filters.objectifVille;
	}
	if (filters.finalite) {
		url = url + '&finalite=' + filters.finalite;
	}
	return url;
}

function pushFilterModalState(filters, replace) {
	var url =  getUrlModalState(filters);
	var state = {
		type: 'searchFilter',
		filters: filters,
		showModal: true
	};
	pushState(state, url, replace);
}

window.onpopstate = function (event) {
	console.log("Event state", event.state);
	if (event.state) {
		switch (event.state.type) {
			case 'searchText':
				openModalRechercheWithText(event.state.searchQuery);
				break;
			case 'searchFilter':
				openModalShowAxis(event.state.filters);
				break;
			case 'action':
				openModalShowAction(event.state.actionId);
				break;
			case 'ecocite':
				openModalShowEcocite(event.state.ecociteId);
				break;
			default:
				break;
		}
		if (!event.state.showModal) {
			simpleHideModal()
		}
	} else if (!location.search) {
		simpleHideModal();
	}
};
