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
