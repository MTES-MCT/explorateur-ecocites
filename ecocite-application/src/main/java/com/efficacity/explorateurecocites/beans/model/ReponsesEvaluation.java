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
    name = "exp_reponses_evaluation"
)
public class ReponsesEvaluation extends BaseEntity {
  @Column(
      name = "niveau"
  )
  private Integer niveau;

  @Column(
      name = "id_question"
  )
  private Long idQuestion;

  @Column(
      name = "id_action"
  )
  private Long idAction;

  public ReponsesEvaluation() {
  }

  public Integer getNiveau() {
    return this.niveau;
  }

  public void setNiveau(Integer niveau) {
    this.niveau = niveau;
  }

  public Long getIdQuestion() {
    return this.idQuestion;
  }

  public void setIdQuestion(Long idQuestion) {
    this.idQuestion = idQuestion;
  }

  public Long getIdAction() {
    return this.idAction;
  }

  public void setIdAction(Long idAction) {
    this.idAction = idAction;
  }
}
