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
    name = "exp_contact"
)
public class Contact extends BaseEntity {
  @Column(
      name = "nom",
      nullable = false,
      length = 255
  )
  private String nom;

  @Column(
      name = "prenom",
      nullable = false,
      length = 255
  )
  private String prenom;

  @Column(
      name = "entite",
      nullable = false,
      length = 255
  )
  private String entite;

  @Column(
      name = "id_ecocite"
  )
  private Long idEcocite;

  @Column(
      name = "fonction",
      length = 255
  )
  private String fonction;

  @Column(
      name = "telephone",
      length = 255
  )
  private String telephone;

  @Column(
      name = "email",
      nullable = false,
      length = 255
  )
  private String email;

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
      name = "user_creation",
      length = 255
  )
  private String userCreation;

  @Column(
      name = "date_creation"
  )
  private LocalDateTime dateCreation;

  public Contact() {
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

  public String getEntite() {
    return this.entite;
  }

  public void setEntite(String entite) {
    this.entite = entite;
  }

  public Long getIdEcocite() {
    return this.idEcocite;
  }

  public void setIdEcocite(Long idEcocite) {
    this.idEcocite = idEcocite;
  }

  public String getFonction() {
    return this.fonction;
  }

  public void setFonction(String fonction) {
    this.fonction = fonction;
  }

  public String getTelephone() {
    return this.telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
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

  public String getUserCreation() {
    return this.userCreation;
  }

  public void setUserCreation(String userCreation) {
    this.userCreation = userCreation;
  }

  public LocalDateTime getDateCreation() {
    return this.dateCreation;
  }

  public void setDateCreation(LocalDateTime dateCreation) {
    this.dateCreation = dateCreation;
  }
}
