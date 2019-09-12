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
import java.lang.Long;
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
    name = "exp_asso_objet_contact"
)
public class AssoObjetContact extends BaseEntity {
  @Column(
      name = "id_objet"
  )
  private Long idObjet;

  @Column(
      name = "type_objet",
      length = 255
  )
  private String typeObjet;

  @Column(
      name = "id_contact"
  )
  private Long idContact;

  @Column(
      name = "importance"
  )
  private Integer importance;

  public AssoObjetContact() {
  }

  public Long getIdObjet() {
    return this.idObjet;
  }

  public void setIdObjet(Long idObjet) {
    this.idObjet = idObjet;
  }

  public String getTypeObjet() {
    return this.typeObjet;
  }

  public void setTypeObjet(String typeObjet) {
    this.typeObjet = typeObjet;
  }

  public Long getIdContact() {
    return this.idContact;
  }

  public void setIdContact(Long idContact) {
    this.idContact = idContact;
  }

  public Integer getImportance() {
    return this.importance;
  }

  public void setImportance(Integer importance) {
    this.importance = importance;
  }
}
