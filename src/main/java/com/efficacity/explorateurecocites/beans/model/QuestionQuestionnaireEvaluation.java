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
    name = "exp_question_questionnaire_evaluation"
)
public class QuestionQuestionnaireEvaluation extends BaseEntity {
  @Column(
      name = "code_questionnaire",
      length = 255
  )
  private String codeQuestionnaire;

  @Column(
      name = "code_categorie",
      length = 255
  )
  private String codeCategorie;

  @Column(
      name = "ordre"
  )
  private Integer ordre;

  @Column(
      name = "question",
      length = 2147483647
  )
  private String question;

  @Column(
      name = "type_reponse",
      length = 255
  )
  private String typeReponse;

  @Column(
      name = "reponses",
      length = 2147483647
  )
  private String reponses;

  @Column(
      name = "id_question_mere"
  )
  private Long idQuestionMere;

  @Column(
      name = "reponse_attendu",
      length = 255
  )
  private String reponseAttendu;

  public QuestionQuestionnaireEvaluation() {
  }

  public String getCodeQuestionnaire() {
    return this.codeQuestionnaire;
  }

  public void setCodeQuestionnaire(String codeQuestionnaire) {
    this.codeQuestionnaire = codeQuestionnaire;
  }

  public String getCodeCategorie() {
    return this.codeCategorie;
  }

  public void setCodeCategorie(String codeCategorie) {
    this.codeCategorie = codeCategorie;
  }

  public Integer getOrdre() {
    return this.ordre;
  }

  public void setOrdre(Integer ordre) {
    this.ordre = ordre;
  }

  public String getQuestion() {
    return this.question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public String getTypeReponse() {
    return this.typeReponse;
  }

  public void setTypeReponse(String typeReponse) {
    this.typeReponse = typeReponse;
  }

  public String getReponses() {
    return this.reponses;
  }

  public void setReponses(String reponses) {
    this.reponses = reponses;
  }

  public Long getIdQuestionMere() {
    return this.idQuestionMere;
  }

  public void setIdQuestionMere(Long idQuestionMere) {
    this.idQuestionMere = idQuestionMere;
  }

  public String getReponseAttendu() {
    return this.reponseAttendu;
  }

  public void setReponseAttendu(String reponseAttendu) {
    this.reponseAttendu = reponseAttendu;
  }
}
