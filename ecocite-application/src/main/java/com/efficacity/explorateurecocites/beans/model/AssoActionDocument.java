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

package com.efficacity.explorateurecocites.beans.model;

import isotope.commons.entities.BaseEntity;
import java.lang.Long;
import java.lang.String;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Cette classe a été autogénérée. Elle sera écrasée à chaque génération.
 *
 * Date de génération : 16/02/2018
 */
@Entity
@Table(
    name = "exp_asso_action_document"
)
public class AssoActionDocument extends BaseEntity {
  @Column(
      name = "id_action"
  )
  private Long idAction;

  @Column(
      name = "id_document"
  )
  private Long idDocument;

  @Column(
      name = "type_document",
      length = 100
  )
  private String typeDocument;

  public AssoActionDocument() {
  }

  public Long getIdAction() {
    return this.idAction;
  }

  public void setIdAction(Long idAction) {
    this.idAction = idAction;
  }

  public Long getIdDocument() {
    return this.idDocument;
  }

  public void setIdDocument(Long idDocument) {
    this.idDocument = idDocument;
  }

  public String getTypeDocument() {
    return this.typeDocument;
  }

  public void setTypeDocument(String typeDocument) {
    this.typeDocument = typeDocument;
  }
}
