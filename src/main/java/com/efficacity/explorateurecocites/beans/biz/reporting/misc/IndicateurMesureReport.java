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

import com.efficacity.explorateurecocites.beans.biz.IndicateurBean;
import com.efficacity.explorateurecocites.beans.model.AssoIndicateurObjet;
import com.efficacity.explorateurecocites.beans.model.CibleIndicateur;
import com.efficacity.explorateurecocites.beans.model.MesureIndicateur;
import com.efficacity.explorateurecocites.utils.enumeration.NATURE_INDICATEUR;

import java.util.List;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.ui.bo.utils.ReportingHelper.EMPTY;

public class IndicateurMesureReport {
    private String nature;
    private NATURE_INDICATEUR natureEnum;
    private String typeMesure;
    private String nomCourt;
    private String posteCalcul;
    private String unite;
    private List<CibleMesureIndicateurReport> mesures;
    private List<CibleMesureIndicateurReport> cibles;
    private String commentaireMesure;
    private String commentaireCible;

    public IndicateurMesureReport(final AssoIndicateurObjet ai, final IndicateurBean i, List<CibleIndicateur> cibles, List<MesureIndicateur> mesures) {
        nature = i.getNatureLibelle();
        natureEnum = i.getNatureEnum();
        typeMesure = i.getTypeMesureLibelle();
        nomCourt = i.getNomCourt();
        posteCalcul = ai.getPosteCalcule() != null ? ai.getPosteCalcule() : EMPTY;
        unite = ai.getUnite();
        if (i.getTypeMesureEnum() == null || i.getTypeMesureEnum().shouldPrintUnit()) {
            this.mesures = mesures.stream().map(m -> new CibleMesureIndicateurReport(m, ai.getUnite())).collect(Collectors.toList());
            this.cibles = cibles.stream().map(c -> new CibleMesureIndicateurReport(c, ai.getUnite())).collect(Collectors.toList());
        } else {
            this.mesures = mesures.stream().map(CibleMesureIndicateurReport::new).collect(Collectors.toList());
            this.cibles = cibles.stream().map(CibleMesureIndicateurReport::new).collect(Collectors.toList());
        }
        commentaireCible = ai.getCommentaireCible();
        commentaireMesure = ai.getCommentaireMesure();
    }

    public NATURE_INDICATEUR getNatureEnum() {
        return natureEnum;
    }

    public void setNatureEnum(final NATURE_INDICATEUR natureEnum) {
        this.natureEnum = natureEnum;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(final String nature) {
        this.nature = nature;
    }

    public String getTypeMesure() {
        return typeMesure;
    }

    public void setTypeMesure(final String typeMesure) {
        this.typeMesure = typeMesure;
    }

    public String getNomCourt() {
        return nomCourt;
    }

    public void setNomCourt(final String nomCourt) {
        this.nomCourt = nomCourt;
    }

    public String getPosteCalcul() {
        return posteCalcul;
    }

    public void setPosteCalcul(final String posteCalcul) {
        this.posteCalcul = posteCalcul;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(final String unite) {
        this.unite = unite;
    }

    public List<CibleMesureIndicateurReport> getMesures() {
        return mesures;
    }

    public void setMesures(final List<CibleMesureIndicateurReport> mesures) {
        this.mesures = mesures;
    }

    public List<CibleMesureIndicateurReport> getCibles() {
        return cibles;
    }

    public void setCibles(final List<CibleMesureIndicateurReport> cibles) {
        this.cibles = cibles;
    }

    public String getCommentaireMesure() {
        return commentaireMesure;
    }

    public void setCommentaireMesure(final String commentaireMesure) {
        this.commentaireMesure = commentaireMesure;
    }

    public String getCommentaireCible() {
        return commentaireCible;
    }

    public void setCommentaireCible(final String commentaireCible) {
        this.commentaireCible = commentaireCible;
    }
}
