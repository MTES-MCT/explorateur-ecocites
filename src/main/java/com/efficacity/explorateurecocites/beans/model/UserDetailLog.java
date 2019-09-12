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
