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
    name = "exp_action"
)
public class Action extends BaseEntity {
  @Column(
      name = "id_ecocite"
  )
  private Long idEcocite;

  @Column(
      name = "id_axe"
  )
  private Long idAxe;

  @Column(
      name = "nom_public",
      length = 255
  )
  private String nomPublic;

  @Column(
      name = "numero_action",
      length = 20
  )
  private String numeroAction;

  @Column(
      name = "date_debut"
  )
  private LocalDateTime dateDebut;

  @Column(
      name = "date_fin"
  )
  private LocalDateTime dateFin;

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
      name = "description",
      length = 2147483647
  )
  private String description;

  @Column(
      name = "lien",
      length = 255
  )
  private String lien;

  @Column(
      name = "echelle",
      length = 255
  )
  private String echelle;

  @Column(
      name = "etat_avancement",
      length = 255
  )
  private String etatAvancement;

  @Column(
      name = "type_financement",
      length = 255
  )
  private String typeFinancement;

  @Column(
      name = "etat_publication",
      length = 255
  )
  private String etatPublication;

  @Column(
      name = "tranche_execution",
      length = 255
  )
  private String trancheExecution;

  @Column(
      name = "evaluation_niveau_global"
  )
  private Integer evaluationNiveauGlobal;

  @Column(
      name = "maitrise_ouvrage",
      length = 2147483647
  )
  private String maitriseOuvrage;

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
      name = "date_modification_fo",
      nullable = false
  )
  private LocalDateTime dateModificationFo;

  @Column(
      name = "user_modification_fo",
      nullable = false,
      length = 255
  )
  private String userModificationFo;

  public Action() {
  }

  public Long getIdEcocite() {
    return this.idEcocite;
  }

  public void setIdEcocite(Long idEcocite) {
    this.idEcocite = idEcocite;
  }

  public Long getIdAxe() {
    return this.idAxe;
  }

  public void setIdAxe(Long idAxe) {
    this.idAxe = idAxe;
  }

  public String getNomPublic() {
    return this.nomPublic;
  }

  public void setNomPublic(String nomPublic) {
    this.nomPublic = nomPublic;
  }

  public String getNumeroAction() {
    return this.numeroAction;
  }

  public void setNumeroAction(String numeroAction) {
    this.numeroAction = numeroAction;
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

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLien() {
    return this.lien;
  }

  public void setLien(String lien) {
    this.lien = lien;
  }

  public String getEchelle() {
    return this.echelle;
  }

  public void setEchelle(String echelle) {
    this.echelle = echelle;
  }

  public String getEtatAvancement() {
    return this.etatAvancement;
  }

  public void setEtatAvancement(String etatAvancement) {
    this.etatAvancement = etatAvancement;
  }

  public String getTypeFinancement() {
    return this.typeFinancement;
  }

  public void setTypeFinancement(String typeFinancement) {
    this.typeFinancement = typeFinancement;
  }

  public String getEtatPublication() {
    return this.etatPublication;
  }

  public void setEtatPublication(String etatPublication) {
    this.etatPublication = etatPublication;
  }

  public String getTrancheExecution() {
    return this.trancheExecution;
  }

  public void setTrancheExecution(String trancheExecution) {
    this.trancheExecution = trancheExecution;
  }

  public Integer getEvaluationNiveauGlobal() {
    return this.evaluationNiveauGlobal;
  }

  public void setEvaluationNiveauGlobal(Integer evaluationNiveauGlobal) {
    this.evaluationNiveauGlobal = evaluationNiveauGlobal;
  }

  public String getMaitriseOuvrage() {
    return this.maitriseOuvrage;
  }

  public void setMaitriseOuvrage(String maitriseOuvrage) {
    this.maitriseOuvrage = maitriseOuvrage;
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

  public LocalDateTime getDateModificationFo() {
    return this.dateModificationFo;
  }

  public void setDateModificationFo(LocalDateTime dateModificationFo) {
    this.dateModificationFo = dateModificationFo;
  }

  public String getUserModificationFo() {
    return this.userModificationFo;
  }

  public void setUserModificationFo(String userModificationFo) {
    this.userModificationFo = userModificationFo;
  }
}
