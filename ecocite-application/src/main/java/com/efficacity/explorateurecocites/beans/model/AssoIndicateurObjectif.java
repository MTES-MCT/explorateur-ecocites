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
    name = "exp_asso_indicateur_objectif"
)
public class AssoIndicateurObjectif extends BaseEntity {
  @Column(
      name = "id_indicateur"
  )
  private Long idIndicateur;

  @Column(
      name = "id_objectif"
  )
  private Long idObjectif;

  public AssoIndicateurObjectif() {
  }

  public Long getIdIndicateur() {
    return this.idIndicateur;
  }

  public void setIdIndicateur(Long idIndicateur) {
    this.idIndicateur = idIndicateur;
  }

  public Long getIdObjectif() {
    return this.idObjectif;
  }

  public void setIdObjectif(Long idObjectif) {
    this.idObjectif = idObjectif;
  }
}
