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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Cette classe a été autogénérée. Elle sera écrasée à chaque génération.
 *
 * Date de génération : 23/01/2019
 */
@Entity
@Table(
    name = "exp_etape"
)
public class Etape extends BaseEntity {
  @Column(
      name = "id_objet"
  )
  private Long idObjet;

  @Column(
      name = "code_etape",
      length = 255
  )
  private String codeEtape;

  @Column(
      name = "statut",
      length = 255
  )
  private String statut;

  @Column(
      name = "commentaire",
      length = 2147483647
  )
  private String commentaire;

  @Column(
      name = "type_objet",
      length = 50
  )
  private String typeObjet;

  @Column(
      name = "date_validee"
  )
  private LocalDateTime dateValidee;

  @Column(
      name = "validee_par"
  )
  private Long valideePar;

  public Etape() {
  }

  public Long getIdObjet() {
    return this.idObjet;
  }

  public void setIdObjet(Long idObjet) {
    this.idObjet = idObjet;
  }

  public String getCodeEtape() {
    return this.codeEtape;
  }

  public void setCodeEtape(String codeEtape) {
    this.codeEtape = codeEtape;
  }

  public String getStatut() {
    return this.statut;
  }

  public void setStatut(String statut) {
    this.statut = statut;
  }

  public String getCommentaire() {
    return this.commentaire;
  }

  public void setCommentaire(String commentaire) {
    this.commentaire = commentaire;
  }

  public String getTypeObjet() {
    return this.typeObjet;
  }

  public void setTypeObjet(String typeObjet) {
    this.typeObjet = typeObjet;
  }

  public LocalDateTime getDateValidee() {
    return this.dateValidee;
  }

  public void setDateValidee(LocalDateTime dateValidee) {
    this.dateValidee = dateValidee;
  }

  public Long getValideePar() {
    return this.valideePar;
  }

  public void setValideePar(Long valideePar) {
    this.valideePar = valideePar;
  }
}
