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

import com.efficacity.explorateurecocites.beans.model.AssoIndicateurObjet;
import com.efficacity.explorateurecocites.beans.model.Indicateur;

/**
 * Created by rxion on 11/02/2018.
 */
public class AssoIndicateurObjetBean {

  private AssoIndicateurObjet to;
  private Indicateur indicateur;

    public AssoIndicateurObjetBean (AssoIndicateurObjet assoIndicateurObjet, Indicateur indicateur ) {
        super();
        this.indicateur = indicateur;
        this.to=assoIndicateurObjet;
    }

    public AssoIndicateurObjet getTo() {
        return to;
    }

    public Indicateur getIndicateur() {
        return indicateur;
    }

    public void setIndicateur(Indicateur indicateur) {
        this.indicateur = indicateur;
    }

    public Long getId() {
        return this.to.getId();
    }

    public void setId(Long id) {
        this.to.setId(id);
    }

    public Long getIdIndicateur() {
        return this.to.getIdIndicateur();
    }

    public void setIdIndicateur(Long idIndicateur) {
        this.to.setIdIndicateur(idIndicateur);
    }

    public String getPosteCalcule() {
        return this.to.getPosteCalcule();
    }

    public void setPosteCalcule(String posteCalcule) {
        this.to.setPosteCalcule(posteCalcule);
    }

    public String getUnite() {
        return this.to.getUnite();
    }

    public void setUnite(String unite) {
        this.to.setUnite(unite);
    }

    public Long getIdObjet() {
        return this.to.getIdObjet();
    }

    public void setIdObjet(Long idObjet) {
        this.to.setIdObjet(idObjet);
    }

    public String getCommentaireMesure() {
        return this.to.getCommentaireMesure();
    }

    public void setCommentaireMesure(String commentaireMesure) {
        this.to.setCommentaireMesure(commentaireMesure);
    }

    public String getCommentaireCible() {
        return this.to.getCommentaireCible();
    }

    public void setCommentaireCible(String commentaireCible) {
        this.to.setCommentaireCible(commentaireCible);
    }

    public String getTypeObjet() {
        return this.to.getTypeObjet();
    }

    public void setTypeObjet(String typeObjet) {
        this.to.setTypeObjet(typeObjet);
    }
}
