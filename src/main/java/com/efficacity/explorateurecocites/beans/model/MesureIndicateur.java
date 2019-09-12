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
import java.lang.Boolean;
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
    name = "exp_mesure_indicateur"
)
public class MesureIndicateur extends BaseEntity {
  @Column(
      name = "id_asso_indicateur_objet"
  )
  private Long idAssoIndicateurObjet;

  @Column(
      name = "date_saisie"
  )
  private LocalDateTime dateSaisie;

  @Column(
      name = "valeur",
      length = 255
  )
  private String valeur;

  @Column(
      name = "valide"
  )
  private Boolean valide;

  @Column(
      name = "date_creation",
      nullable = false
  )
  private LocalDateTime dateCreation;

  public MesureIndicateur() {
  }

  public Long getIdAssoIndicateurObjet() {
    return this.idAssoIndicateurObjet;
  }

  public void setIdAssoIndicateurObjet(Long idAssoIndicateurObjet) {
    this.idAssoIndicateurObjet = idAssoIndicateurObjet;
  }

  public LocalDateTime getDateSaisie() {
    return this.dateSaisie;
  }

  public void setDateSaisie(LocalDateTime dateSaisie) {
    this.dateSaisie = dateSaisie;
  }

  public String getValeur() {
    return this.valeur;
  }

  public void setValeur(String valeur) {
    this.valeur = valeur;
  }

  public Boolean getValide() {
    return this.valide;
  }

  public void setValide(Boolean valide) {
    this.valide = valide;
  }

  public LocalDateTime getDateCreation() {
    return this.dateCreation;
  }

  public void setDateCreation(LocalDateTime dateCreation) {
    this.dateCreation = dateCreation;
  }
}
