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
    name = "exp_cible_indicateur"
)
public class CibleIndicateur extends BaseEntity {
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

  public CibleIndicateur() {
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
}
