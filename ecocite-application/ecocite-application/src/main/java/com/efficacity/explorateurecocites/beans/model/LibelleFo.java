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
    name = "exp_libelle_fo"
)
public class LibelleFo extends BaseEntity {
  @Column(
      name = "code",
      nullable = false,
      length = 255
  )
  private String code;

  @Column(
      name = "texte",
      nullable = false,
      length = 2147483647
  )
  private String texte;

  @Column(
      name = "date_modification",
      nullable = false
  )
  private LocalDateTime dateModification;

  @Column(
      name = "user_modification",
      nullable = false,
      length = 255
  )
  private String userModification;

  @Column(
          name = "description",
          nullable = false,
          length = 255
  )
  private String description;

  @Column(
          name = "type",
          nullable = false,
          length = 255
  )
  private String type;

  public LibelleFo() {
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getTexte() {
    return this.texte;
  }

  public void setTexte(String texte) {
    this.texte = texte;
  }

  public LocalDateTime getDateModification() {
    return this.dateModification;
  }

  public void setDateModification(LocalDateTime dateModification) {
    this.dateModification = dateModification;
  }

  public String getUserModification() {
    return this.userModification;
  }

  public void setUserModification(String userModification) {
    this.userModification = userModification;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
