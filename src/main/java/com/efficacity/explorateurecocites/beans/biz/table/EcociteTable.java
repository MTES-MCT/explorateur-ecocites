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

package com.efficacity.explorateurecocites.beans.biz.table;

import com.efficacity.explorateurecocites.utils.table.TableColumn;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by rxion on 19/02/2018.
 */
public class EcociteTable {
	@JsonSerialize(
			using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
	)
  	private Long id;
	@TableColumn("ecocite.colum.nom")
	private String nom;
  	@TableColumn("ecocite.colum.region")
	private String region;
  	@TableColumn("ecocite.colum.porteurProjet")
	private String porteurProjet;
  	@TableColumn("ecocite.colum.anneAdhesion")
	private String anneAdhesion;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPorteurProjet() {
		return porteurProjet;
	}

	public void setPorteurProjet(String porteurProjet) {
		this.porteurProjet = porteurProjet;
	}

	public String getAnneAdhesion() {
		return anneAdhesion;
	}

	public void setAnneAdhesion(String anneAdhesion) {
		this.anneAdhesion = anneAdhesion;
	}
}
