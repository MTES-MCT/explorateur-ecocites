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

package com.efficacity.explorateurecocites.beans.biz.reporting.action;

import com.efficacity.explorateurecocites.beans.biz.ActionBean;
import com.efficacity.explorateurecocites.beans.biz.EcociteBean;

import java.io.File;

public class ActionReport {

    private File image;
    private String nom;
    private String nomEcocite;
    private FicheIdentite identite;
    private Caracterisation caracterisation;
    private EvaluationQuantitative quantitative;
    private EvaluationQualitative qualitative;

    public ActionReport(final ActionBean actionBean, final EcociteBean ecocite,
                        final FicheIdentite ficheIdentite, final Caracterisation caracterisation,
                        final File image, final EvaluationQuantitative quantitative, final EvaluationQualitative qualitative) {
        nom = actionBean.getNomPublic();
        nomEcocite = ecocite.getNom();
        identite = ficheIdentite;
        this.caracterisation = caracterisation;
        this.quantitative = quantitative;
        this.qualitative = qualitative;
        this.image = image;
    }

    public File getImage() {
        return image;
    }

    public EvaluationQualitative getQualitative() {
        return qualitative;
    }

    public void setQualitative(final EvaluationQualitative qualitative) {
        this.qualitative = qualitative;
    }

    public EvaluationQuantitative getQuantitative() {
        return quantitative;
    }

    public void setQuantitative(final EvaluationQuantitative quantitative) {
        this.quantitative = quantitative;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(final String nom) {
        this.nom = nom;
    }

    public String getNomEcocite() {
        return nomEcocite;
    }

    public void setNomEcocite(final String nomEcocite) {
        this.nomEcocite = nomEcocite;
    }

    public FicheIdentite getIdentite() {
        return identite;
    }

    public void setIdentite(final FicheIdentite identite) {
        this.identite = identite;
    }

    public Caracterisation getCaracterisation() {
        return caracterisation;
    }

    public void setCaracterisation(final Caracterisation caracterisation) {
        this.caracterisation = caracterisation;
    }
}
