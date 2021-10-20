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

package com.efficacity.explorateurecocites.ui.bo.forms;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by rxion on 14/02/2018.
 */
public class EtapeForm {
  @NotBlank
  private String id;
  @NotBlank
  private String idObjet;
  @Size(max=2147483647)
  private String commentaire;

  public String getId() {
	return id;
  }

  public void setId(String id) {
	this.id = id;
  }

  public String getIdObjet() {
	return idObjet;
  }

  public void setIdObjet(String idObjet) {
	this.idObjet = idObjet;
  }

  public String getCommentaire() {
	return commentaire;
  }

  public void setCommentaire(String commentaire) {
	this.commentaire = commentaire;
  }
}
