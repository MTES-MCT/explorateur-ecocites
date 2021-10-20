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
    name = "exp_utilisateur"
)
public class Utilisateur {
  @Column(
      name = "email",
      nullable = false,
      length = 255
  )
  @Id
  private String email;

  @Column(
      name = "nom",
      length = 255
  )
  private String nom;

  @Column(
      name = "prenom",
      length = 255
  )
  private String prenom;

  @Column(
      name = "date_premiere_connexion"
  )
  private LocalDateTime datePremiereConnexion;

  @Column(
      name = "date_derniere_connexion"
  )
  private LocalDateTime dateDerniereConnexion;

  public Utilisateur() {
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNom() {
    return this.nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getPrenom() {
    return this.prenom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  public LocalDateTime getDatePremiereConnexion() {
    return this.datePremiereConnexion;
  }

  public void setDatePremiereConnexion(LocalDateTime datePremiereConnexion) {
    this.datePremiereConnexion = datePremiereConnexion;
  }

  public LocalDateTime getDateDerniereConnexion() {
    return this.dateDerniereConnexion;
  }

  public void setDateDerniereConnexion(LocalDateTime dateDerniereConnexion) {
    this.dateDerniereConnexion = dateDerniereConnexion;
  }
}
