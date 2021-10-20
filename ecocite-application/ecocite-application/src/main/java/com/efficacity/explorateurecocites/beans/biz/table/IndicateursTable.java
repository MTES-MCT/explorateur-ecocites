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

package com.efficacity.explorateurecocites.beans.biz.table;

import com.efficacity.explorateurecocites.utils.table.TableColumn;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by rxion on 19/02/2018.
 */
public class IndicateursTable {
	@JsonSerialize(
			using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
	)
  	private Long id;
//	@TableColumn("indicateur.colum.nom")
//	private String nom;
  	@TableColumn("indicateur.colum.nomLong")
	private String nomLong;
  	@TableColumn("indicateur.colum.nature")
	private String nature;
  	@TableColumn("indicateur.colum.echelle")
	private String echelle;
	@TableColumn("indicateur.colum.origine")
	private String origine;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

//	public String getNom() {
//		return nom;
//	}
//
//	public void setNom(String nom) {
//		this.nom = nom;
//	}

	public String getNomLong() {
		return nomLong;
	}

	public void setNomLong(String nomLong) {
		this.nomLong = nomLong;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getEchelle() {
		return echelle;
	}

	public void setEchelle(String echelle) {
		this.echelle = echelle;
	}

	public String getOrigine() {
		return origine;
	}

	public void setOrigine(String origine) {
		this.origine = origine;
	}
}
