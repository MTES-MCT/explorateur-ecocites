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
    name = "exp_business"
)
public class Business extends BaseEntity {
  @Column(
      name = "nom",
      length = 255
  )
  private String nom;

  @Column(
      name = "numero_affaire",
      length = 10
  )
  private String numeroAffaire;

  @Column(
      name = "numero_operation",
      length = 10
  )
  private String numeroOperation;

  @Column(
      name = "nom_operation",
      length = 255
  )
  private String nomOperation;

  @Column(
      name = "id_action"
  )
  private Long idAction;

  @Column(
      name = "tranche",
      length = 255
  )
  private String tranche;

  @Column(
      name = "ecocite",
      length = 255
  )
  private String ecocite;

  @Column(
      name = "axe",
      length = 255
  )
  private String axe;

  @Column(
      name = "type_financement",
      length = 255
  )
  private String typeFinancement;

  @Column(
      name = "statut_financier",
      length = 255
  )
  private String statutFinancier;

  @Column(
      name = "statut_abandon"
  )
  private Boolean statutAbandon;

  @Column(
      name = "date_debut"
  )
  private LocalDateTime dateDebut;

  @Column(
      name = "date_fin"
  )
  private LocalDateTime dateFin;

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

  public Business() {
  }

  public String getNom() {
    return this.nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getNumeroAffaire() {
    return this.numeroAffaire;
  }

  public void setNumeroAffaire(String numeroAffaire) {
    this.numeroAffaire = numeroAffaire;
  }

  public String getNumeroOperation() {
    return this.numeroOperation;
  }

  public void setNumeroOperation(String numeroOperation) {
    this.numeroOperation = numeroOperation;
  }

  public String getNomOperation() {
    return this.nomOperation;
  }

  public void setNomOperation(String nomOperation) {
    this.nomOperation = nomOperation;
  }

  public Long getIdAction() {
    return this.idAction;
  }

  public void setIdAction(Long idAction) {
    this.idAction = idAction;
  }

  public String getTranche() {
    return this.tranche;
  }

  public void setTranche(String tranche) {
    this.tranche = tranche;
  }

  public String getEcocite() {
    return this.ecocite;
  }

  public void setEcocite(String ecocite) {
    this.ecocite = ecocite;
  }

  public String getAxe() {
    return this.axe;
  }

  public void setAxe(String axe) {
    this.axe = axe;
  }

  public String getTypeFinancement() {
    return this.typeFinancement;
  }

  public void setTypeFinancement(String typeFinancement) {
    this.typeFinancement = typeFinancement;
  }

  public String getStatutFinancier() {
    return this.statutFinancier;
  }

  public void setStatutFinancier(String statutFinancier) {
    this.statutFinancier = statutFinancier;
  }

  public Boolean getStatutAbandon() {
    return this.statutAbandon;
  }

  public void setStatutAbandon(Boolean statutAbandon) {
    this.statutAbandon = statutAbandon;
  }

  public LocalDateTime getDateDebut() {
    return this.dateDebut;
  }

  public void setDateDebut(LocalDateTime dateDebut) {
    this.dateDebut = dateDebut;
  }

  public LocalDateTime getDateFin() {
    return this.dateFin;
  }

  public void setDateFin(LocalDateTime dateFin) {
    this.dateFin = dateFin;
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
