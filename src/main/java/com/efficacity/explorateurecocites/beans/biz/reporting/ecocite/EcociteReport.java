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

package com.efficacity.explorateurecocites.beans.biz.reporting.ecocite;

import com.efficacity.explorateurecocites.beans.biz.EcociteBean;
import com.efficacity.explorateurecocites.beans.model.Region;

import java.io.File;

public class EcociteReport {

    private File image;
    private String nom;
    private String nomRegion;
    private FicheIdentite identite;
    private Caracterisation caracterisation;
    private EvaluationQuantitative quantitative;
    private EvaluationQualitative qualitative;

    public EcociteReport() {}

    public EcociteReport(final EcociteBean ecociteBean, final Region region,
                         final FicheIdentite ficheIdentite, final Caracterisation caracterisation,
                         final File image, final EvaluationQuantitative quantitative, final EvaluationQualitative qualitative) {
        nom = ecociteBean.getNom();
        nomRegion = region != null ? region.getNom() : "";
        identite = ficheIdentite;
        this.caracterisation = caracterisation;
        this.quantitative = quantitative;
        this.qualitative = qualitative;
        this.image = image;
    }

    public File getImage() {
        return image;
    }

    public void setImage(final File image) {
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(final String nom) {
        this.nom = nom;
    }

    public String getNomRegion() {
        return nomRegion;
    }

    public void setNomRegion(final String nomRegion) {
        this.nomRegion = nomRegion;
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

    public EvaluationQuantitative getQuantitative() {
        return quantitative;
    }

    public void setQuantitative(final EvaluationQuantitative quantitative) {
        this.quantitative = quantitative;
    }

    public EvaluationQualitative getQualitative() {
        return qualitative;
    }

    public void setQualitative(final EvaluationQualitative qualitative) {
        this.qualitative = qualitative;
    }
}
