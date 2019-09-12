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

package com.efficacity.explorateurecocites.ui.bo.forms;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by rxion on 11/02/2018.
 */
public class EtiquetteFrom {
  @NotBlank
  private String idObjet;

  @NotBlank
  private String idEtiquette;

  @NotNull
  private Integer poid;

  private String idAsso;

  public String getIdObjet() {
	return idObjet;
  }

  public void setIdObjet(String idObjet) {
	this.idObjet = idObjet;
  }

  public String getIdEtiquette() {
	return idEtiquette;
  }

  public void setIdEtiquette(String idEtiquette) {
	this.idEtiquette = idEtiquette;
  }

  public Integer getPoid() {
	return poid;
  }

  public void setPoid(Integer poid) {
	this.poid = poid;
  }

  public String getIdAsso() {
	return idAsso;
  }

  public void setIdAsso(String idAsso) {
	this.idAsso = idAsso;
  }
}
