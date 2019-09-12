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
 * Date de génération : 23/01/2019
 */
@Entity
@Table(
    name = "exp_indicateur"
)
public class Indicateur extends BaseEntity {
  @Column(
      name = "nom",
      length = 255
  )
  private String nom;

  @Column(
      name = "nom_court",
      length = 255
  )
  private String nomCourt;

  @Column(
      name = "echelle",
      length = 255
  )
  private String echelle;

  @Column(
      name = "nature",
      length = 255
  )
  private String nature;

  @Column(
      name = "origine",
      length = 255
  )
  private String origine;

  @Column(
      name = "type_mesure",
      length = 255
  )
  private String typeMesure;

  @Column(
      name = "description",
      length = 2147483647
  )
  private String description;

  @Column(
      name = "methode_calcule",
      length = 2147483647
  )
  private String methodeCalcule;

  @Column(
      name = "source_donnees",
      length = 2147483647
  )
  private String sourceDonnees;

  @Column(
      name = "poste_calcule",
      length = 2147483647
  )
  private String posteCalcule;

  @Column(
      name = "unite",
      length = 2147483647
  )
  private String unite;

  @Column(
      name = "etat_validation",
      length = 255
  )
  private String etatValidation;

  @Column(
      name = "etat_bibliotheque",
      length = 255
  )
  private String etatBibliotheque;

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

  public Indicateur() {
  }

  public String getNom() {
    return this.nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getNomCourt() {
    return this.nomCourt;
  }

  public void setNomCourt(String nomCourt) {
    this.nomCourt = nomCourt;
  }

  public String getEchelle() {
    return this.echelle;
  }

  public void setEchelle(String echelle) {
    this.echelle = echelle;
  }

  public String getNature() {
    return this.nature;
  }

  public void setNature(String nature) {
    this.nature = nature;
  }

  public String getOrigine() {
    return this.origine;
  }

  public void setOrigine(String origine) {
    this.origine = origine;
  }

  public String getTypeMesure() {
    return this.typeMesure;
  }

  public void setTypeMesure(String typeMesure) {
    this.typeMesure = typeMesure;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getMethodeCalcule() {
    return this.methodeCalcule;
  }

  public void setMethodeCalcule(String methodeCalcule) {
    this.methodeCalcule = methodeCalcule;
  }

  public String getSourceDonnees() {
    return this.sourceDonnees;
  }

  public void setSourceDonnees(String sourceDonnees) {
    this.sourceDonnees = sourceDonnees;
  }

  public String getPosteCalcule() {
    return this.posteCalcule;
  }

  public void setPosteCalcule(String posteCalcule) {
    this.posteCalcule = posteCalcule;
  }

  public String getUnite() {
    return this.unite;
  }

  public void setUnite(String unite) {
    this.unite = unite;
  }

  public String getEtatValidation() {
    return this.etatValidation;
  }

  public void setEtatValidation(String etatValidation) {
    this.etatValidation = etatValidation;
  }

  public String getEtatBibliotheque() {
    return this.etatBibliotheque;
  }

  public void setEtatBibliotheque(String etatBibliotheque) {
    this.etatBibliotheque = etatBibliotheque;
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
