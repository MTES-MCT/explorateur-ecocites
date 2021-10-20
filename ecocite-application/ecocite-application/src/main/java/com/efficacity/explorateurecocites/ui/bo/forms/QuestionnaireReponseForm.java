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

package com.efficacity.explorateurecocites.ui.bo.forms;

import org.hibernate.validator.constraints.NotBlank;

import java.util.HashMap;

/**
 * Created by acaillard on 28/02/2018.
 */
public class QuestionnaireReponseForm {

  @NotBlank
  private HashMap<String,QuestionnaireQuestionForm> reponses;
  @NotBlank
  private String objetId;
  @NotBlank
  private String codeQuestionnaire;

  public HashMap<String,QuestionnaireQuestionForm> getReponses() {
    return reponses;
  }

  public void setReponses(HashMap<String,QuestionnaireQuestionForm> reponses) {
    this.reponses = reponses;
  }

  public String getObjetId() {
    return objetId;
  }

  public void setObjetId(String objetId) {
    this.objetId = objetId;
  }

  public String getCodeQuestionnaire() {
    return codeQuestionnaire;
  }

  public void setCodeQuestionnaire(String codeQuestionnaire) {
    this.codeQuestionnaire = codeQuestionnaire;
  }
}
