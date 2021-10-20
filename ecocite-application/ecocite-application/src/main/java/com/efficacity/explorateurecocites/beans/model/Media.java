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
import java.lang.Integer;
import java.lang.String;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Cette classe a été autogénérée. Elle sera écrasée à chaque génération.
 *
 * Date de génération : 23/01/2019
 */
@Entity
@Table(
    name = "exp_media"
)
public class Media extends BaseEntity {
  @Column(
      name = "type_object",
      nullable = false,
      length = 32
  )
  private String typeObject;

  @Column(
      name = "id_object",
      nullable = false
  )
  private long idObject;

  @Column(
      name = "title",
      length = 255
  )
  private String title;

  @Column(
      name = "level"
  )
  private Integer level;

  @Column(
      name = "numero"
  )
  private Integer numero;

  @Column(
      name = "id_ajaris"
  )
  private Integer idAjaris;

  public Media() {
  }

  public String getTypeObject() {
    return this.typeObject;
  }

  public void setTypeObject(String typeObject) {
    this.typeObject = typeObject;
  }

  public long getIdObject() {
    return this.idObject;
  }

  public void setIdObject(long idObject) {
    this.idObject = idObject;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getLevel() {
    return this.level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }

  public Integer getNumero() {
    return this.numero;
  }

  public void setNumero(Integer numero) {
    this.numero = numero;
  }

  public Integer getIdAjaris() {
    return this.idAjaris;
  }

  public void setIdAjaris(Integer idAjaris) {
    this.idAjaris = idAjaris;
  }
}
