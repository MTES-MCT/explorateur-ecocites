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
