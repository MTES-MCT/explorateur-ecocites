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

import com.efficacity.explorateurecocites.beans.model.Indicateur;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

public class IndicateurForm {

    private String id;

    private Integer type;

    @NotNull
    @Size(min = 1)
    private String nomLong;

    @NotNull
    @Size(min = 1)
    private String definition;

    private String methodeCalcul;

    @NotNull
    @Size(min = 1)
    private String sourceDonnee;

    private List<String> posteCalcul;

    @NotNull
    @Size(min = 1)
    private List<String> unite;

    @NotNull
    @Size(min = 1)
    private String nomCourt;

    @NotNull
    @Size(min = 1)
    private String echelle;

    @NotNull
    @Size(min = 1)
    private String nature;

    @NotNull
    @Size(min = 1)
    private List<String> origine;

    @NotNull
    @Size(min = 1)
    private String typeMesure;

    @NotNull
    @Size(min = 1)
    private String etatValidation;

    @NotNull
    @Size(min = 1)
    private String statutBibliotheque;

    private List<String> domaines;

    private List<String> objectifs;

    public List<String> getDomaines() {
        return domaines;
    }

    public void setDomaines(final List<String> domaines) {
        this.domaines = domaines;
    }

    public List<String> getObjectifs() {
        return objectifs;
    }

    public void setObjectifs(final List<String> objectifs) {
        this.objectifs = objectifs;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(final Integer type) {
        this.type = type;
    }

    public String getTypeMesure() {
        return typeMesure;
    }

    public void setTypeMesure(final String typeMesure) {
        this.typeMesure = typeMesure;
    }

    public String getNomLong() {
        return nomLong;
    }

    public void setNomLong(final String nomLong) {
        this.nomLong = nomLong;
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

    public String getSourceDonnee() {
        return sourceDonnee;
    }

    public void setSourceDonnee(final String sourceDonnee) {
        this.sourceDonnee = sourceDonnee;
    }

    public List<String> getPosteCalcul() {
        return posteCalcul;
    }

    public void setPosteCalcul(final List<String> posteCalcul) {
        this.posteCalcul = posteCalcul;
    }

    public List<String> getUnite() {
        return unite;
    }

    public void setUnite(final List<String> unite) {
        this.unite = unite;
    }

    public String getNomCourt() {
        return nomCourt;
    }

    public void setNomCourt(final String nomCourt) {
        this.nomCourt = nomCourt;
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

    public List<String> getOrigine() {
        return origine;
    }

    public void setOrigine(final List<String> origine) {
        this.origine = origine;
    }

    public String getEtatValidation() {
        return etatValidation;
    }

    public void setEtatValidation(final String etatValidation) {
        this.etatValidation = etatValidation;
    }

    public String getStatutBibliotheque() {
        return statutBibliotheque;
    }

    public void setStatutBibliotheque(final String statutBibliotheque) {
        this.statutBibliotheque = statutBibliotheque;
    }

    public Indicateur getModel() {
        Indicateur indicateur = new Indicateur();
        indicateur.setTypeMesure(this.getTypeMesure());
        indicateur.setNom(this.getNomLong());
        indicateur.setDescription(this.getDefinition());
        indicateur.setMethodeCalcule(this.getMethodeCalcul());
        indicateur.setSourceDonnees(this.getSourceDonnee());
        indicateur.setPosteCalcule(this.getPosteCalcul().stream().collect(Collectors.joining(";")));
        indicateur.setUnite(this.getUnite().stream().collect(Collectors.joining(";")));
        indicateur.setNomCourt(this.getNomCourt());
        indicateur.setEchelle(this.getEchelle());
        indicateur.setNature(this.getNature());
        indicateur.setOrigine(this.getOrigine().stream().collect(Collectors.joining(";")));
        indicateur.setEtatValidation(this.getEtatValidation());
        indicateur.setEtatBibliotheque(this.getStatutBibliotheque());
        return indicateur;
    }
}
