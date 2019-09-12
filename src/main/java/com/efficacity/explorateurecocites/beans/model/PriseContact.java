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
import java.lang.String;
import java.time.LocalDateTime;
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
    name = "exp_prise_contact"
)
public class PriseContact extends BaseEntity {
  @Column(
      name = "email",
      nullable = false,
      length = 255
  )
  private String email;

  @Column(
      name = "message",
      nullable = false,
      length = 2147483647
  )
  private String message;

  @Column(
      name = "date_redaction"
  )
  private LocalDateTime dateRedaction;

  @Column(
      name = "date_envoie"
  )
  private LocalDateTime dateEnvoie;

  public PriseContact() {
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public LocalDateTime getDateRedaction() {
    return this.dateRedaction;
  }

  public void setDateRedaction(LocalDateTime dateRedaction) {
    this.dateRedaction = dateRedaction;
  }

  public LocalDateTime getDateEnvoie() {
    return this.dateEnvoie;
  }

  public void setDateEnvoie(LocalDateTime dateEnvoie) {
    this.dateEnvoie = dateEnvoie;
  }
}
