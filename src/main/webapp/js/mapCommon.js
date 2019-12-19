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

var DEFAULTS = {
	LATITUDE: 46.817621,
	LONGITUDE: 3.009651
};

function createMap(target, placeholder, overlay) {
	var mapInstance = {
		target: target
	};
	if (placeholder) {
		mapInstance.placeholder = $("#" + placeholder).parent();
		mapInstance.placeholder.addClass("loading");
		mapInstance.placeholder.removeClass("loaded");
	}
	if (overlay) {
		mapInstance.overlay = overlay;
		mapInstance.overlayContent = overlay.find(".overlay-content");
		mapInstance.overlayCloser = overlay.find(".overlay-closer");
	}
	return {
		mapInstance: mapInstance,
		initialize: function initMap(latitude, longitude, zoom) {
			this.destroy();
			var center = undefined;
			var vectorSource = new ol.source.Vector({
				features: []
			});
			var vectorLayer = new ol.layer.Vector({
				source: vectorSource,
				style: new ol.style.Style({
					image: new ol.style.Icon({
						src: '/img/icons/ecocite_popup_map_spot_ecocite.png',
						anchor: [25, 38],
						anchorXUnits: "pixels",
						anchorYUnits: "pixels"
					})
				})
			});
			var vectorSource2 = new ol.source.Vector({
				features: []
			});
			var vectorLayer2 = new ol.layer.Vector({
				source: vectorSource2,
				style: new ol.style.Style({
					image: new ol.style.Icon({
						src: '/img/icons/ecocite_popup_map_spot_action.png',
						anchor: [25, 38],
						anchorXUnits: "pixels",
						anchorYUnits: "pixels"
					})
				})
			});
			mapInstance.markerVectorSource = vectorSource;
			mapInstance.markerVectorSource2 = vectorSource2;
			mapInstance.markersLayer = vectorLayer;
			mapInstance.markersLayer2 = vectorLayer2;
			mapInstance.kmlLayers = [];

			mapInstance.optMap = {
				target: target,
				layers: [
					new ol.layer.GeoportalWMTS({
						layer: "GEOGRAPHICALGRIDSYSTEMS.PLANIGN"
					}),
					vectorLayer,
					vectorLayer2
				]
			};
			if (latitude && longitude) {
				center = ol.proj.transform([longitude, latitude], 'EPSG:4326', 'EPSG:3857');
				mapInstance.optMap.view = new ol.View({
					center: center,
					zoom: zoom || 12,
					minZoom: 2,
					maxZoom: 18
				})
			} else {
				mapInstance.optMap.view = new ol.View({
					center: ol.proj.transform([DEFAULTS.LONGITUDE, DEFAULTS.LATITUDE], 'EPSG:4326', 'EPSG:3857'),
					zoom: zoom || 5,
					minZoom: 2,
					maxZoom: 18
				})
			}
			if (mapInstance.overlay) {
				var overlay = new ol.Overlay({
					element: container,
					autoPan: true,
					autoPanAnimation: {
						duration: 250
					}
				});
				mapInstance.optMap.overlays = [ overlay ]
			}
			mapInstance.olMap = new ol.Map(mapInstance.optMap);

			if (mapInstance.placeholder) {
				mapInstance.olMap.on('postrender', function() {
					if (mapInstance.placeholder) {
						mapInstance.placeholder.removeClass("loading");
						mapInstance.placeholder.addClass("loaded");
					}
				});
			}
		},

		enablePopup: function enablePopup() {
			var popup = new Popup();
			mapInstance.olMap.addOverlay(popup);
			mapInstance.popUp = popup;

			mapInstance.olMap.on("click", function(e) {
				var clickHandled = false;
				mapInstance.olMap.forEachFeatureAtPixel(e.pixel, function (feature, layer) {
					if (!clickHandled) {
						clickHandled = true;
						var name = feature.get('description');
						if (feature.get('clickable')) {
							var content = feature.get('content');
							popup.show(feature.getGeometry().getFirstCoordinate(), content);
						} else {
							popup.show(feature.getGeometry().getFirstCoordinate(), '<p>{0}</p>'.format(escapeHTML(name)));
						}
					}
				});
				if (!clickHandled) {
					popup.hide();
				}
			});
		},

		addMarker: function addMarker(latitude, longitude, name) {
			if (mapInstance.olMap) {
				var coordinate = ol.proj.transform([longitude, latitude], 'EPSG:4326', 'EPSG:3857');
				var feature = new ol.Feature({
					name: 'click',
					description: name,
					clickable: false,
					geometry: new ol.geom.Point(coordinate)
				});
				mapInstance.markerVectorSource.addFeature(feature);
			}
		},

		addMarkerWithLink: function addMarkerWithLink(latitude, longitude, name, content) {
			if (mapInstance.olMap) {
				var coordinate = ol.proj.transform([longitude, latitude], 'EPSG:4326', 'EPSG:3857');
				var feature = new ol.Feature({
					name: 'click',
					description: name,
					clickable: true,
					content: content,
					geometry: new ol.geom.Point(coordinate)
				});
				mapInstance.markerVectorSource2.addFeature(feature);
			}
		},

		replaceMarker: function replaceMarker(latitude, longitude, content, center) {
			if (mapInstance.olMap) {
				mapInstance.markerVectorSource.clear();
				this.addMarker(latitude, longitude, content);
				if (center) {
					this.center(latitude, longitude)
				}
			}
		},

		removeMarker: function removeMarker(center) {
			if (mapInstance.olMap) {
				mapInstance.markerVectorSource.clear();
				if (center) {
					this.center()
				}
			}
		},

		center: function center(latitude, longitude) {
			if (mapInstance.olMap) {
				if (mapInstance.kmlLayers.length <= 0) {
					var mapView = mapInstance.olMap.getView();
					if (latitude && longitude) {
						mapView.setCenter(ol.proj.transform([longitude, latitude], 'EPSG:4326', 'EPSG:3857'));
						mapView.setZoom(12);
					} else {
						mapView.setCenter(ol.proj.transform([DEFAULTS.LONGITUDE, DEFAULTS.LATITUDE], 'EPSG:4326', 'EPSG:3857'));
						mapView.setZoom(5);
					}
				}
			}
		},

		addKml: function addKMLLayer(url, doNotCenter) {
			if (mapInstance.olMap) {
				var vectorSource = new ol.source.Vector({
						url: url,
						format: new ol.format.KML(),
						extractStyles: true
					});
				var kmlLayer = new ol.layer.Vector({
					source: vectorSource
				});
				if (!doNotCenter) {
					vectorSource.once('change', function () {
						if (vectorSource.getState() === 'ready') {
							var extent = vectorSource.getExtent();
							mapInstance.olMap.getView().fit(extent, mapInstance.olMap.getSize());
						}
					});
				}
				mapInstance.olMap.addLayer(kmlLayer);
				mapInstance.kmlLayers.push(kmlLayer);
				return kmlLayer
			}
		},

		destroy: function destroy() {
			$("#" + mapInstance.target).empty()
		},

		addOnClickListener: function (callback) {
			if (mapInstance.olMap) {
				mapInstance.olMap.on('singleclick', function (evt) {
					var coordinates = ol.proj.transform(evt.coordinate, 'EPSG:3857', 'EPSG:4326');
					callback(coordinates[1], coordinates[0]);
				});
			}
		}
	}
}
