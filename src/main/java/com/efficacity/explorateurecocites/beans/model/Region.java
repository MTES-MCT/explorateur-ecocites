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
    name = "exp_region"
)
public class Region extends BaseEntity {
  @Column(
      name = "nom",
      length = 255
  )
  private String nom;

  @Column(
      name = "shortname",
      length = 255
  )
  private String shortname;

  @Column(
      name = "siren",
      length = 16
  )
  private String siren;

  public Region() {
  }

  public String getNom() {
    return this.nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getShortname() {
    return this.shortname;
  }

  public void setShortname(String shortname) {
    this.shortname = shortname;
  }

  public String getSiren() {
    return this.siren;
  }

  public void setSiren(String siren) {
    this.siren = siren;
  }
}
