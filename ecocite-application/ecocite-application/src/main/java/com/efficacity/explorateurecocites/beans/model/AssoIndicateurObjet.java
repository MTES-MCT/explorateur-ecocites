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
    name = "exp_asso_indicateur_objet"
)
public class AssoIndicateurObjet extends BaseEntity {
  @Column(
      name = "id_indicateur"
  )
  private Long idIndicateur;

  @Column(
      name = "poste_calcule",
      length = 255
  )
  private String posteCalcule;

  @Column(
      name = "unite",
      length = 255
  )
  private String unite;

  @Column(
      name = "id_objet"
  )
  private Long idObjet;

  @Column(
      name = "commentaire_mesure",
      length = 2147483647
  )
  private String commentaireMesure;

  @Column(
      name = "commentaire_cible",
      length = 2147483647
  )
  private String commentaireCible;

  @Column(
      name = "type_objet",
      length = 50
  )
  private String typeObjet;

  public AssoIndicateurObjet() {
  }

  public Long getIdIndicateur() {
    return this.idIndicateur;
  }

  public void setIdIndicateur(Long idIndicateur) {
    this.idIndicateur = idIndicateur;
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

  public Long getIdObjet() {
    return this.idObjet;
  }

  public void setIdObjet(Long idObjet) {
    this.idObjet = idObjet;
  }

  public String getCommentaireMesure() {
    return this.commentaireMesure;
  }

  public void setCommentaireMesure(String commentaireMesure) {
    this.commentaireMesure = commentaireMesure;
  }

  public String getCommentaireCible() {
    return this.commentaireCible;
  }

  public void setCommentaireCible(String commentaireCible) {
    this.commentaireCible = commentaireCible;
  }

  public String getTypeObjet() {
    return this.typeObjet;
  }

  public void setTypeObjet(String typeObjet) {
    this.typeObjet = typeObjet;
  }
}
