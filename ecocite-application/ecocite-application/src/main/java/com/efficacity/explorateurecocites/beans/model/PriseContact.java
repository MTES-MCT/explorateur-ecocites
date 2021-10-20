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
