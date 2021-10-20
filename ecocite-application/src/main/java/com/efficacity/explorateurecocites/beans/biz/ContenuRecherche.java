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

import com.efficacity.explorateurecocites.utils.CustomValidator;

import java.util.List;

public class ContenuRecherche {

    private String titre;
    private List<ResultatRecherche> resultatRechercheList;

    public String getTitre() {return titre; }

    public void setTitre(String titre) { this.titre = titre; }

    public List<ResultatRecherche> getResultatRechercheList() { return resultatRechercheList; }

    public boolean hasResult() { return CustomValidator.isNotEmpty(resultatRechercheList); }

    public void setResultatRechercheList(List<ResultatRecherche> resultatRechercheList) { this.resultatRechercheList = resultatRechercheList; }
}
