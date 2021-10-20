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

import java.lang.Long;
import java.lang.String;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Cette classe a été autogénérée. Elle sera écrasée à chaque génération.
 *
 * Date de génération : 23/01/2019
 */
@Entity
@Table(
    name = "exp_user_detail_log"
)
public class UserDetailLog {
  @Column(
      name = "id_user",
      nullable = false
  )
  @Id
  private Long idUser;

  @Column(
      name = "responsecerbere",
      length = 2147483647
  )
  private String responsecerbere;

  @Column(
      name = "date_creation"
  )
  private LocalDateTime dateCreation;

  public UserDetailLog() {
  }

  public Long getIdUser() {
    return this.idUser;
  }

  public void setIdUser(Long idUser) {
    this.idUser = idUser;
  }

  public String getResponsecerbere() {
    return this.responsecerbere;
  }

  public void setResponsecerbere(String responsecerbere) {
    this.responsecerbere = responsecerbere;
  }

  public LocalDateTime getDateCreation() {
    return this.dateCreation;
  }

  public void setDateCreation(LocalDateTime dateCreation) {
    this.dateCreation = dateCreation;
  }
}
