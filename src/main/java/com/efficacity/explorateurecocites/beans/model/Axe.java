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
import java.lang.Long;
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
    name = "exp_axe"
)
public class Axe extends BaseEntity {
  @Column(
      name = "libelle",
      length = 255
  )
  private String libelle;

  @Column(
      name = "code_couleur1",
      length = 255
  )
  private String codeCouleur1;

  @Column(
      name = "code_couleur2",
      length = 255
  )
  private String codeCouleur2;

  @Column(
      name = "icone",
      length = 255
  )
  private String icone;

  @Column(
      name = "user_modification",
      length = 255
  )
  private String userModification;

  @Column(
      name = "date_modification"
  )
  private LocalDateTime dateModification;

  @Column(
      name = "icon_file"
  )
  private Long iconFile;

  public Axe() {
  }

  public String getLibelle() {
    return this.libelle;
  }

  public void setLibelle(String libelle) {
    this.libelle = libelle;
  }

  public String getCodeCouleur1() {
    return this.codeCouleur1;
  }

  public void setCodeCouleur1(String codeCouleur1) {
    this.codeCouleur1 = codeCouleur1;
  }

  public String getCodeCouleur2() {
    return this.codeCouleur2;
  }

  public void setCodeCouleur2(String codeCouleur2) {
    this.codeCouleur2 = codeCouleur2;
  }

  public String getIcone() {
    return this.icone;
  }

  public void setIcone(String icone) {
    this.icone = icone;
  }

  public String getUserModification() {
    return this.userModification;
  }

  public void setUserModification(String userModification) {
    this.userModification = userModification;
  }

  public LocalDateTime getDateModification() {
    return this.dateModification;
  }

  public void setDateModification(LocalDateTime dateModification) {
    this.dateModification = dateModification;
  }

  public Long getIconFile() {
    return this.iconFile;
  }

  public void setIconFile(Long iconFile) {
    this.iconFile = iconFile;
  }
}
