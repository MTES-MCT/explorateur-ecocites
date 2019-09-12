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

package com.efficacity.explorateurecocites.beans.biz;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by rxion on 12/02/2018.
 */
public abstract class AssoActionEtiquetteBean {
  @JsonSerialize(
		  using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
  )
  private Long idAsso;

  @JsonSerialize(
		  using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
  )
  private Long idAction;

  private int poid;

  public AssoActionEtiquetteBean(Long idAsso, Long idAction, int poid) {
	this.idAsso = idAsso;
	this.idAction = idAction;
	this.poid = poid;
  }

  public Long getIdAsso() {
	return idAsso;
  }

  public void setIdAsso(Long idAsso) {
	this.idAsso = idAsso;
  }

  public Long getIdAction() {
	return idAction;
  }

  public void setIdAction(Long idAction) {
	this.idAction = idAction;
  }

  public int getPoid() {
	return poid;
  }

  public void setPoid(int poid) {
	this.poid = poid;
  }

  /* Abstract method */
  public abstract String getCategorie();
}
