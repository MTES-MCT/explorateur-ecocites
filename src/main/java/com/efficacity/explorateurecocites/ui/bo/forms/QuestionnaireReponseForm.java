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
