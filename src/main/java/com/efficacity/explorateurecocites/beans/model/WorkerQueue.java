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
 * Date de génération : 29/10/2018
 */
@Entity
@Table(
    name = "exp_worker_queue"
)
public class WorkerQueue extends BaseEntity {
  @Column(
      name = "type",
      nullable = false,
      length = 64
  )
  private String type;

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
      name = "data",
      length = 2147483647
  )
  private String data;

  public WorkerQueue() {
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
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

  public String getData() {
    return this.data;
  }

  public void setData(String data) {
    this.data = data;
  }
}
