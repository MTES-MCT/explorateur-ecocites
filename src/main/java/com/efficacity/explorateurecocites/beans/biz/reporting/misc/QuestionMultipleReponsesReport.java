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

package com.efficacity.explorateurecocites.beans.biz.reporting.misc;

import java.util.List;

public class QuestionMultipleReponsesReport {

    private String question;

    private List<String> reponses;

    public QuestionMultipleReponsesReport(final String question, final List<String> reponses) {
        this.question = question;
        this.reponses = reponses;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(final String question) {
        this.question = question;
    }

    public List<String> getReponses() {
        return reponses;
    }

    public void setReponses(final List<String> reponses) {
        this.reponses = reponses;
    }
}
