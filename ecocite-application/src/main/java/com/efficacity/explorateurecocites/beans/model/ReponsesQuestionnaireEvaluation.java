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
    name = "exp_reponses_questionnaire_evaluation"
)
public class ReponsesQuestionnaireEvaluation extends BaseEntity {
  @Column(
      name = "id_question"
  )
  private Long idQuestion;

  @Column(
      name = "id_objet"
  )
  private Long idObjet;

  @Column(
      name = "type_objet",
      length = 50
  )
  private String typeObjet;

  @Column(
      name = "reponse_principale",
      length = 2147483647
  )
  private String reponsePrincipale;

  @Column(
      name = "reponse_secondaire",
      length = 2147483647
  )
  private String reponseSecondaire;

  public ReponsesQuestionnaireEvaluation() {
  }

  public Long getIdQuestion() {
    return this.idQuestion;
  }

  public void setIdQuestion(Long idQuestion) {
    this.idQuestion = idQuestion;
  }

  public Long getIdObjet() {
    return this.idObjet;
  }

  public void setIdObjet(Long idObjet) {
    this.idObjet = idObjet;
  }

  public String getTypeObjet() {
    return this.typeObjet;
  }

  public void setTypeObjet(String typeObjet) {
    this.typeObjet = typeObjet;
  }

  public String getReponsePrincipale() {
    return this.reponsePrincipale;
  }

  public void setReponsePrincipale(String reponsePrincipale) {
    this.reponsePrincipale = reponsePrincipale;
  }

  public String getReponseSecondaire() {
    return this.reponseSecondaire;
  }

  public void setReponseSecondaire(String reponseSecondaire) {
    this.reponseSecondaire = reponseSecondaire;
  }
}
