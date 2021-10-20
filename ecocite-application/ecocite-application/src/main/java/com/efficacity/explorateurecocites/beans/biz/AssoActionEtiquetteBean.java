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
