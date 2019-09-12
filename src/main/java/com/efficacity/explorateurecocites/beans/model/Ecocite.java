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
import java.lang.Integer;
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
    name = "exp_ecocite"
)
public class Ecocite extends BaseEntity {
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
      name = "id_region"
  )
  private Long idRegion;

  @Column(
      name = "desc_strategie",
      length = 2147483647
  )
  private String descStrategie;

  @Column(
      name = "desc_perimetre",
      length = 2147483647
  )
  private String descPerimetre;

  @Column(
      name = "porteur",
      length = 255
  )
  private String porteur;

  @Column(
      name = "partenaire",
      length = 2147483647
  )
  private String partenaire;

  @Column(
      name = "annee_adhesion",
      length = 4
  )
  private String anneeAdhesion;

  @Column(
      name = "soutien_pia_detail",
      length = 255
  )
  private String soutienPiaDetail;

  @Column(
      name = "nb_communes"
  )
  private Integer nbCommunes;

  @Column(
      name = "nb_habitants"
  )
  private Integer nbHabitants;

  @Column(
      name = "superficie_km2"
  )
  private Integer superficieKm2;

  @Column(
      name = "lien",
      length = 255
  )
  private String lien;

  @Column(
      name = "latitude",
      length = 255
  )
  private String latitude;

  @Column(
      name = "longitude",
      length = 255
  )
  private String longitude;

  @Column(
      name = "etat_publication",
      length = 255
  )
  private String etatPublication;

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

  @Column(
      name = "siren",
      length = 16
  )
  private String siren;

  public Ecocite() {
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

  public Long getIdRegion() {
    return this.idRegion;
  }

  public void setIdRegion(Long idRegion) {
    this.idRegion = idRegion;
  }

  public String getDescStrategie() {
    return this.descStrategie;
  }

  public void setDescStrategie(String descStrategie) {
    this.descStrategie = descStrategie;
  }

  public String getDescPerimetre() {
    return this.descPerimetre;
  }

  public void setDescPerimetre(String descPerimetre) {
    this.descPerimetre = descPerimetre;
  }

  public String getPorteur() {
    return this.porteur;
  }

  public void setPorteur(String porteur) {
    this.porteur = porteur;
  }

  public String getPartenaire() {
    return this.partenaire;
  }

  public void setPartenaire(String partenaire) {
    this.partenaire = partenaire;
  }

  public String getAnneeAdhesion() {
    return this.anneeAdhesion;
  }

  public void setAnneeAdhesion(String anneeAdhesion) {
    this.anneeAdhesion = anneeAdhesion;
  }

  public String getSoutienPiaDetail() {
    return this.soutienPiaDetail;
  }

  public void setSoutienPiaDetail(String soutienPiaDetail) {
    this.soutienPiaDetail = soutienPiaDetail;
  }

  public Integer getNbCommunes() {
    return this.nbCommunes;
  }

  public void setNbCommunes(Integer nbCommunes) {
    this.nbCommunes = nbCommunes;
  }

  public Integer getNbHabitants() {
    return this.nbHabitants;
  }

  public void setNbHabitants(Integer nbHabitants) {
    this.nbHabitants = nbHabitants;
  }

  public Integer getSuperficieKm2() {
    return this.superficieKm2;
  }

  public void setSuperficieKm2(Integer superficieKm2) {
    this.superficieKm2 = superficieKm2;
  }

  public String getLien() {
    return this.lien;
  }

  public void setLien(String lien) {
    this.lien = lien;
  }

  public String getLatitude() {
    return this.latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return this.longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getEtatPublication() {
    return this.etatPublication;
  }

  public void setEtatPublication(String etatPublication) {
    this.etatPublication = etatPublication;
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

  public String getSiren() {
    return this.siren;
  }

  public void setSiren(String siren) {
    this.siren = siren;
  }
}
