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

package com.efficacity.explorateurecocites.beans.biz;

import com.efficacity.explorateurecocites.beans.model.QuestionQuestionnaireEvaluation;

import java.util.LinkedList;

/**
 * Classe qui étent le bean model du générator
 *
 * Date de génération : 06/02/2018
 */

public class QuestionnaireFacteurBean {

   LinkedList<QuestionQuestionnaireEvaluation> listQuestion;

   public QuestionnaireFacteurBean (LinkedList<QuestionQuestionnaireEvaluation> listQuestion){
      this.listQuestion = listQuestion;
   }
}
