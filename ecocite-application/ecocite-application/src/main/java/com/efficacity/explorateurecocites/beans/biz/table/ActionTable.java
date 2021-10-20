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
public class ActionTable {
	@JsonSerialize(
			using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
	)
  	private Long id;
	@TableColumn("action.colum.nomPublic")
	private String nomPublic;
  	@TableColumn("action.colum.ecocite")
	private String ecocite;
  	@TableColumn("action.colum.axePrincial")
	private String axePrincial;
  	@TableColumn("action.colum.typeFinancement")
	private String typeFinancement;
  	@TableColumn("action.colum.etatAvancement")
	private String etatAvancement;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getNomPublic() {
	return nomPublic;
  }

  public void setNomPublic(String nomPublic) {
	this.nomPublic = nomPublic;
  }

  public String getEcocite() {
	return ecocite;
  }

  public void setEcocite(String ecocite) {
	this.ecocite = ecocite;
  }

  public String getAxePrincial() {
	return axePrincial;
  }

  public void setAxePrincial(String axePrincial) {
	this.axePrincial = axePrincial;
  }

  public String getTypeFinancement() {
	return typeFinancement;
  }

  public void setTypeFinancement(String typeFinancement) {
	this.typeFinancement = typeFinancement;
  }

  public String getEtatAvancement() {
	return etatAvancement;
  }

  public void setEtatAvancement(String etatAvancement) {
	this.etatAvancement = etatAvancement;
  }
}
