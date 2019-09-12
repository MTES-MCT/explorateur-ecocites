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

public class EtiquettesReport {
    private List<String> majeures;
    private List<String> moderes;
    private List<String> mineures;
    private String commentaire;

    public EtiquettesReport(final List<String> majeures, final List<String> moderes, final List<String> mineures, final String commentaire) {
        this.majeures = majeures;
        this.moderes = moderes;
        this.mineures = mineures;
        if (commentaire == null || commentaire.trim().isEmpty()) {
            this.commentaire = null;
        } else {
            this.commentaire = commentaire;
        }
    }

    public List<String> getMajeures() {
        return majeures;
    }

    public void setMajeures(final List<String> majeures) {
        this.majeures = majeures;
    }

    public List<String> getModeres() {
        return moderes;
    }

    public void setModeres(final List<String> moderes) {
        this.moderes = moderes;
    }

    public List<String> getMineures() {
        return mineures;
    }

    public void setMineures(final List<String> mineures) {
        this.mineures = mineures;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(final String commentaire) {
        this.commentaire = commentaire;
    }
}
