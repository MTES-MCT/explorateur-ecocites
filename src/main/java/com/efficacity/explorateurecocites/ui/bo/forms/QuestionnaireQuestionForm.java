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

import java.util.List;

/**
 * Created by acaillard on 28/02/2018.
 */
public class QuestionnaireQuestionForm {

  @NotBlank
  private String id;
  private String reponseText;
  private String reponseTextSecondaire;
  private List<String> reponseList;
  private List<String> reponseListSecondaire;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getReponseText() {
    return reponseText;
  }

  public void setReponseText(String reponseText) {
    this.reponseText = reponseText;
  }

  public String getReponseTextSecondaire() {
    return reponseTextSecondaire;
  }

  public void setReponseTextSecondaire(String reponseTextSecondaire) {
    this.reponseTextSecondaire = reponseTextSecondaire;
  }

  public List<String> getReponseList() {
    return reponseList;
  }

  public void setReponseList(List<String> reponseList) {
    this.reponseList = reponseList;
  }

  public List<String> getReponseListSecondaire() {
    return reponseListSecondaire;
  }

  public void setReponseListSecondaire(List<String> reponseListSecondaire) {
    this.reponseListSecondaire = reponseListSecondaire;
  }
}
