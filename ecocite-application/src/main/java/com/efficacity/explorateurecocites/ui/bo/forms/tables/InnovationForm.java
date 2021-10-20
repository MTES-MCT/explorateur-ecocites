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

package com.efficacity.explorateurecocites.ui.bo.forms.tables;

import com.efficacity.explorateurecocites.beans.model.QuestionsEvaluation;
import com.efficacity.explorateurecocites.utils.table.TableColumn;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.NotBlank;

public class InnovationForm {

    @JsonSerialize(
            using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
    )
    private Long id;

    @NotBlank
    @TableColumn("innovation.colum.nom")
    private String nom;

    @NotBlank
    @TableColumn("innovation.colum.description")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(final String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public QuestionsEvaluation getQuestionEvaluation() {
        QuestionsEvaluation questionsEvaluation = new QuestionsEvaluation();
        questionsEvaluation.setDescription(getDescription());
        questionsEvaluation.setTitre(getNom());
        return questionsEvaluation;
    }
}
