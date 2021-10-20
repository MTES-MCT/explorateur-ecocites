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

import javax.validation.constraints.Size;

/**
 * Created by rxion on 14/02/2018.
 */
public class AjoutIndicateurObjetForm {
    @NotBlank
    private String idIndicateur;
    @NotBlank
    private String idObjet;
    @Size(max=255)
    private String unite;
    @Size(max=255)
    private String poste_calcule;

    public String getIdIndicateur() {
        return idIndicateur;
    }

    public void setIdIndicateur(String idIndicateur) {
        this.idIndicateur = idIndicateur;
    }

    public String getIdObjet() {
        return idObjet;
    }

    public void setIdObjet(String idObjet) {
        this.idObjet = idObjet;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public String getPoste_calcule() {
        return poste_calcule;
    }

    public void setPoste_calcule(String poste_calcule) {
        this.poste_calcule = poste_calcule;
    }
}
