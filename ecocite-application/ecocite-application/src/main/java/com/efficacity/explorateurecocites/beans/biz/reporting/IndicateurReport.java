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

package com.efficacity.explorateurecocites.beans.biz.reporting;

import com.efficacity.explorateurecocites.beans.biz.IndicateurBean;
import com.efficacity.explorateurecocites.utils.enumeration.ORIGINE_INDICATEUR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IndicateurReport {

    private String nomCourt;
    private String nom;
    private String echelle;
    private String nature;
    private String definition;
    private String methodeCalcul;
    private List<String> unites;
    private List<String> postesCalcul;
    private String sourceDonnees;
    private List<String> origines;
    private List<String> objectifs;

    public IndicateurReport(IndicateurBean bean, List<String> objectifs) {
        nom = bean.getNom();
        nomCourt = bean.getNomCourt();
        echelle = bean.getEchelleEnum() != null ? bean.getEchelleEnum().getLibelle() : "";
        nature = bean.getNatureEnum() != null ? bean.getNatureEnum().getLibelle() : "";
        definition = bean.getDescription();
        methodeCalcul = bean.getMethodeCalcule();
        sourceDonnees = bean.getSourceDonnees();
        unites = (bean.getUnite() != null && !bean.getUnite().trim().isEmpty()) ? Arrays.asList(bean.getUnite().split(";")) : new ArrayList<>();
        postesCalcul = (bean.getPosteCalcule() != null && !bean.getPosteCalcule().trim().isEmpty()) ? Arrays.asList(bean.getPosteCalcule().split(";")) : new ArrayList<>();
        origines = (bean.getOrigine() != null && !bean.getOrigine().trim().isEmpty()) ?
                Arrays.stream(bean.getOrigine()
                        .split(";"))
                        .map(ORIGINE_INDICATEUR::getLibelleForCode)
                        .collect(Collectors.toList())
                : new ArrayList<>();
        bean.getOrigine();
        this.objectifs = objectifs;
    }

    public String getNomCourt() {
        return nomCourt;
    }

    public void setNomCourt(final String nomCourt) {
        this.nomCourt = nomCourt;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(final String nom) {
        this.nom = nom;
    }

    public String getEchelle() {
        return echelle;
    }

    public void setEchelle(final String echelle) {
        this.echelle = echelle;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(final String nature) {
        this.nature = nature;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(final String definition) {
        this.definition = definition;
    }

    public String getMethodeCalcul() {
        return methodeCalcul;
    }

    public void setMethodeCalcul(final String methodeCalcul) {
        this.methodeCalcul = methodeCalcul;
    }

    public List<String> getUnites() {
        return unites;
    }

    public void setUnites(final List<String> unites) {
        this.unites = unites;
    }

    public List<String> getPostesCalcul() {
        return postesCalcul;
    }

    public void setPostesCalcul(final List<String> postesCalcul) {
        this.postesCalcul = postesCalcul;
    }

    public String getSourceDonnees() {
        return sourceDonnees;
    }

    public void setSourceDonnees(final String sourceDonnees) {
        this.sourceDonnees = sourceDonnees;
    }

    public List<String> getOrigines() {
        return origines;
    }

    public void setOrigines(final List<String> origines) {
        this.origines = origines;
    }

    public List<String> getObjectifs() {
        return objectifs;
    }

    public void setObjectifs(final List<String> objectifs) {
        this.objectifs = objectifs;
    }
}
