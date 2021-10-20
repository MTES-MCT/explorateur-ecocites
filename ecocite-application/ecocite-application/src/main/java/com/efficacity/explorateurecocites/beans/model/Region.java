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
