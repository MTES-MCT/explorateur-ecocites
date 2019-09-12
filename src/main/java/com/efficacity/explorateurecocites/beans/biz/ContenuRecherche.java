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
