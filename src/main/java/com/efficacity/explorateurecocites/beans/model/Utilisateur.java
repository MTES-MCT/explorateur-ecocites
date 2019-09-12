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
