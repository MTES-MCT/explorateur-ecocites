
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

var chartNumber = -1;
var selectOptArray = [];
var labelsArray = [];
var datasetsArray = [];
var chartArray = [];
var parent = undefined;


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

function textLabel(labels) {
	return function (value) {
		return labels[value];
	}
}

function noDecimalLabel(value) {
	if (Math.floor(value) === value) {
		return value;
	}
}

function decimalLabel(value) {
	return value;
}

var printDecimal = false;
function initializeEmptyChartLine(chartDivCible, printDecimalParams) {
	printDecimal = printDecimalParams;
	var ctx = document.getElementById(chartDivCible).getContext('2d');
	parent = $("#" + chartDivCible).closest(".graphContainer");
	parent.hide();
	return new Chart(ctx, {
		type: 'line',
		data: {labels: [], datasets: []},
		options: {
			legend: {
				position: "bottom"
			},
			responsive: true,
			scales: {yAxes: [{ticks: {beginAtZero: true}}]}, elements: {line: {tension: 0}},
			//La suite permet d'afficher les pourcentages Mes/Cible sur le graphe
			events: false,
			tooltips: {
				enabled: false
			},
			hover: {
				animationDuration: 0
			},
			animation: {
				duration: 1,
				onComplete: function () {
					if (!this.data.datasets || !this.data.datasets.length) {
						return;
					}
					var chartInstance = this.chart;
						ctx = chartInstance.ctx;
					ctx.font = Chart.helpers.fontString(Chart.defaults.global.defaultFontSize, Chart.defaults.global.defaultFontStyle, Chart.defaults.global.defaultFontFamily);
					ctx.textAlign = 'center';
					ctx.textBaseline = 'bottom';
					var data=this.data;
					var datasets=this.data.datasets;
					var percentsOutput=[];
					var datasetMes=datasets.find(function(element) {
						return element.label==="Mesures";
					});
					var datasetCible=datasets.find(function(element) {
						return element.label==="Cibles";
					});
					var metaMes = chartInstance.controller.getDatasetMeta(0);
					metaMes.data.forEach(function (line, index) {
						var valuePercent=0;
						var dataMes = datasetMes.data[index];
						var dataCible = datasetCible.data[index];
						if(dataCible!=null){
							valuePercent=(dataMes/dataCible)*100;
							percentsOutput.push(Math.floor(valuePercent))
						}
						else {
							var metaCible = chartInstance.controller.getDatasetMeta(1);
							var hasCible = false;
							var indexUtil=0;
							metaCible.data.forEach(function (line, index2) {
								if ( datasetCible.data[ index2 ] != null ) {
									hasCible = true;
									indexUtil=index2;
								}
							});
							if ( hasCible===true) {
								var dateMes = data.labels[ index ];
								var diffTemps = new Date(data.labels[indexUtil]) - new Date(dateMes);
								metaCible.data.forEach(function (line, index2) {
									var dataCible = datasetCible.data[ index2 ];
									if(dataCible!=null){
										var newDiffTemp = new Date(data.labels[index2]) - new Date(dateMes);
										if(diffTemps>newDiffTemp && newDiffTemp>0){
											diffTemps=newDiffTemp;
											indexUtil=index2;
										}
									}
								});
								if(diffTemps<0){
									percentsOutput.push(undefined);
								}
								else {
									valuePercent = (dataMes / datasetCible.data[ indexUtil ]) * 100;
									percentsOutput.push(Math.floor(valuePercent));
								}
							}
							else{
								percentsOutput=undefined;
							}
						}
					});
					if(percentsOutput!== undefined){
						metaMes.data.forEach(function (bar, index) {
							if(percentsOutput[index]!== undefined) {
								ctx.fillText(percentsOutput[ index ] + "%", bar._model.x + 10, bar._model.y + 20);
							}
							else{
								ctx.fillText("", bar._model.x + 10, bar._model.y + 20);
							}
						});
					}
				}
			}
		}
	});
}

function initializeChartLine(chartDivCible, optionsJson, printDecimal) {
	var options = JSON.parse(optionsJson);
	var callBackLabelY = printDecimal ? decimalLabel : noDecimalLabel;
	if (options.labelY && !$.isEmptyObject(options.labelY)) {
		callBackLabelY = textLabel(options.labelY);
	}
	var ctx = document.getElementById(chartDivCible).getContext('2d');
	return new Chart(ctx, {
		type: 'line',
		data: {
			labels: options.labelX,
			datasets: options.datasets
		},
		options: {
			legend: {
				position: "bottom"
			}, responsive: true,
			scales: {
				yAxes: [{
					ticks: {
						beginAtZero: true,
						callback: callBackLabelY
					}
				}]
			}, elements: {
				line: {
					tension: 0 // disables bezier curves
				}
			}
		}
	});
}

function updateChart(chart, options) {
	var callBackLabelY = printDecimal ? decimalLabel : noDecimalLabel;
	if (options.labelY && !$.isEmptyObject(options.labelY)) {
		callBackLabelY = textLabel(options.labelY);
	}
	chart.data.labels =  options.labelX;
	chart.data.datasets = options.datasets;
	chart.options.scales.yAxes = [{
		ticks: {
			beginAtZero: true,
			callback: callBackLabelY
		}
	}];
	if (parent && options.datasets) {
		var flag = false;
		options.datasets.forEach(function (value) {
			if (value.data && value.data.length > 0) {
				flag = true;
			}
		});
		if (flag) {
			parent.show();
		} else {
			parent.hide()
		}
	}
	chart.update(0);
}


function updateData(chart, label, datasets) {
	chart.data.labels = [label];
	chart.data.datasets = datasets;
	chart.update(0);
}


function initializeChartSelect(chartDivCible, selectDivCible, selectsJson, datasetsJson, labelsJson, maintainAspectRatio) {
	chartNumber = chartNumber + 1;
	selectOptArray[chartNumber] = JSON.parse(selectsJson);
	labelsArray[chartNumber] = JSON.parse(labelsJson);
	datasetsArray[chartNumber] = JSON.parse(datasetsJson);
	var selectFiltre = $('#' + selectDivCible);
	Object.keys(selectOptArray[chartNumber]).forEach(function (key) {
		selectFiltre.append("<option value="+ key + ">" + escapeHTML(selectOptArray[chartNumber][key]) + "</option>");
	});
	selectFiltre.val("all");
	var ctx = document.getElementById(chartDivCible).getContext('2d');
	chartArray[chartNumber] = new Chart(ctx, {
		type: 'bar',
		data: {
			labels: [],
			datasets: datasetsArray[chartNumber]["all"]
		},
		options: {
			legend: {
				position: "bottom"
			},
			responsive: true,
			maintainAspectRatio: maintainAspectRatio,
			scales: {
				yAxes: [{
					ticks: {
						beginAtZero:true,
						callback: noDecimalLabel
					}
				}]
			}
		}
	});
	var currentChart = chartNumber;
	selectFiltre.change(function(e) {
		var selected = $(e.target).val();
		updateData(chartArray[currentChart], [], datasetsArray[currentChart][selected])
	});
}
