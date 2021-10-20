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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Cette classe a été autogénérée. Elle sera écrasée à chaque génération.
 *
 * Date de génération : 23/01/2019
 */
@Entity
@Table(
    name = "exp_media_modification"
)
public class MediaModification extends BaseEntity {
  @Column(
      name = "status",
      nullable = false,
      length = 64
  )
  private String status;

  @Column(
      name = "last_modified",
      nullable = false
  )
  private LocalDateTime lastModified;

  @Column(
      name = "type_object",
      nullable = false,
      length = 32
  )
  private String typeObject;

  @Column(
          name = "number_try",
          nullable = false
  )
  private Integer numberTry;

  @Column(
          name = "id_object",
          nullable = false
  )
  private long idObject;

  @Column(
      name = "type_modification",
      nullable = false,
      length = 255
  )
  private String typeModification;

  @Column(
      name = "target_id"
  )
  private Long targetId;

  @Column(
      name = "target_type",
      length = 255
  )
  private String targetType;

  public MediaModification() {
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public LocalDateTime getLastModified() {
    return this.lastModified;
  }

  public void setLastModified(LocalDateTime lastModified) {
    this.lastModified = lastModified;
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

  public String getTypeModification() {
    return this.typeModification;
  }

  public void setTypeModification(String typeModification) {
    this.typeModification = typeModification;
  }

  public Long getTargetId() {
    return this.targetId;
  }

  public void setTargetId(Long targetId) {
    this.targetId = targetId;
  }

  public String getTargetType() {
    return this.targetType;
  }

  public void setTargetType(String targetType) {
    this.targetType = targetType;
  }

  public int getNumberTry() {
    return numberTry;
  }

  public void setNumberTry(final int numberTry) {
    this.numberTry = numberTry;
  }
}
