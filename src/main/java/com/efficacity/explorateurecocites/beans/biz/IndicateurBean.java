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

import com.efficacity.explorateurecocites.beans.model.Indicateur;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import com.efficacity.explorateurecocites.utils.enumeration.*;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Classe qui étent le bean model du générator
 *
 * Date de génération : 06/02/2018
 */

public class IndicateurBean {

    private Indicateur to;

    public IndicateurBean(Indicateur indicateur) {
        super();
        this.to=indicateur;
    }

    public ECHELLE_INDICATEUR getEchelleEnum() {
        return ECHELLE_INDICATEUR.getByCode(this.to.getEchelle());
    }

    public NATURE_INDICATEUR getNatureEnum() {
        return NATURE_INDICATEUR.getByCode(this.to.getNature());
    }

    public String getNatureLibelle() {
        return NATURE_INDICATEUR.getLibelleByCode(this.to.getNature());
    }

    public ORIGINE_INDICATEUR getOrigineEnum() {
        return ORIGINE_INDICATEUR.getByCode(this.to.getOrigine());
    }

    public String getOrigineEnumLibelle() {
        return ORIGINE_INDICATEUR.getLibelleForCode(this.to.getOrigine());
    }

    public String getOrigineListEnumLibelle() {
        return Arrays.stream(this.to.getOrigine().split(";"))
                .map(ORIGINE_INDICATEUR::getLibelleForCode)
                .collect(Collectors.joining(";"));
    }

    public TYPE_MESURE getTypeMesureEnum() {
        return TYPE_MESURE.getByCode(this.to.getTypeMesure());
    }

    public Long getId() {
        return this.to.getId();
    }

    public void setId(Long id) {
        this.to.setId(id);
    }

    public String getNom() {
        return this.to.getNom();
    }

    public void setNom(String nom) {
        this.to.setNom(nom);
    }

    public String getNomCourt() {
        return this.to.getNomCourt();
    }

    public void setNomCourt(String nomCourt) {
        this.to.setNomCourt(nomCourt);
    }

    public String getEchelle() {
        return this.to.getEchelle();
    }

    public void setEchelle(String echelle) {
        this.to.setEchelle(echelle);
    }

    public String getNature() {
        return this.to.getNature();
    }

    public void setNature(String nature) {
        this.to.setNature(nature);
    }

    public String getOrigine() {
        return this.to.getOrigine();
    }

    public void setOrigine(String origine) {
        this.to.setOrigine(origine);
    }

    public String getTypeMesure() {
        return this.to.getTypeMesure();
    }

    public void setTypeMesure(String typeMesure) {
        this.to.setTypeMesure(typeMesure);
    }

    public String getDescription() {
        return this.to.getDescription();
    }

    public void setDescription(String description) {
        this.to.setDescription(description);
    }

    public String getMethodeCalcule() {
        return this.to.getMethodeCalcule();
    }

    public void setMethodeCalcule(String methodeCalcule) {
        this.to.setMethodeCalcule(methodeCalcule);
    }

    public String getSourceDonnees() {
        return this.to.getSourceDonnees();
    }

    public void setSourceDonnees(String sourceDonnees) {
        this.to.setSourceDonnees(sourceDonnees);
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
    public String getEtatValidation() {
        return this.to.getEtatValidation();
    }

    public void setEtatValidation(String etatValidation) {
        this.to.setEtatValidation(etatValidation);
    }

    public String getUserCreation() {
        return this.to.getUserCreation();
    }

    public void setUserCreation(String contactCreateur) {
        this.to.setUserCreation(contactCreateur);
    }

    public String getEtatBibliotheque() {
        return this.to.getEtatBibliotheque();
    }

    public void setEtatBibliotheque(String etatBibliotheque) {
        this.to.setEtatBibliotheque(etatBibliotheque);
    }

    public void validateChamps(String idChamps, Object value, MessageSource messages, Errors errors, Locale locale){

        Map<String, String> mapErreur = new HashMap<String, String>();

        switch (idChamps) {
            case "id":
                // On fait, rien au pire exception lors du save sur l'unicité
                break;
            case "nom":
                if(CustomValidator.isEmpty(value)){
                    errors.rejectValue("attributValue","error.attribut.notNull", messages.getMessage("error.attribut.notNull", null, locale));
                }
                break;
            case "nomCourt":
                if(CustomValidator.isEmpty(value)){
                    errors.rejectValue("attributValue","error.attribut.notNull", messages.getMessage("error.attribut.notNull", null, locale));
                }
                break;
            case "description":
                if(CustomValidator.isEmpty(value)){
                    errors.rejectValue("attributValue","error.attribut.notNull", messages.getMessage("error.attribut.notNull", null, locale));
                }
                break;
            case "sourceDonnees":
                if(CustomValidator.isEmpty(value)){
                    errors.rejectValue("attributValue","error.attribut.notNull", messages.getMessage("error.attribut.notNull", null, locale));
                }
                break;
            case "unite":
                if(CustomValidator.isEmpty(value)){
                    errors.rejectValue("attributValue","error.attribut.notNull", messages.getMessage("error.attribut.notNull", null, locale));
                }
                break;
            case "echelle":
                if(CustomValidator.isEmpty(value)){
                    errors.rejectValue("attributValue","error.attribut.notNull", messages.getMessage("error.attribut.notNull", null, locale));
                } else if(CustomValidator.isNotEmpty(ECHELLE_INDICATEUR.getByCode((String)value))){
                    errors.rejectValue("error.attribut.unknown","error.attribut.unknown", messages.getMessage("error.attribut.unknown", null, locale));
                }
                break;
            case "nature":
                if(CustomValidator.isEmpty(value)){
                    errors.rejectValue("attributValue","error.attribut.notNull", messages.getMessage("error.attribut.notNull", null, locale));
                } else if(CustomValidator.isNotEmpty(NATURE_INDICATEUR.getByCode((String)value))){
                    errors.rejectValue("error.attribut.unknown","error.attribut.unknown", messages.getMessage("error.attribut.unknown", null, locale));
                }
                break;
            case "origine":
                if(CustomValidator.isEmpty(value)){
                    errors.rejectValue("attributValue","error.attribut.notNull", messages.getMessage("error.attribut.notNull", null, locale));
                }
                break;
            case "typeMesure":
                if(CustomValidator.isEmpty(value)){
                    errors.rejectValue("attributValue","error.attribut.notNull", messages.getMessage("error.attribut.notNull", null, locale));
                } else if(CustomValidator.isNotEmpty(TYPE_MESURE.getByCode((String)value))){
                    errors.rejectValue("error.attribut.unknown","error.attribut.unknown", messages.getMessage("error.attribut.unknown", null, locale));
                }
                break;
            case "etatValidation":
                if(CustomValidator.isEmpty(value)){
                    errors.rejectValue("attributValue","error.attribut.notNull", messages.getMessage("error.attribut.notNull", null, locale));
                } else if(CustomValidator.isNotEmpty(ETAT_VALIDATION.getByCode((String)value))){
                    errors.rejectValue("error.attribut.unknown","error.attribut.unknown", messages.getMessage("error.attribut.unknown", null, locale));
                }
                break;
            case "contactCreateur":
                if(CustomValidator.isEmpty(value)){
                    errors.rejectValue("attributValue","error.attribut.notNull", messages.getMessage("error.attribut.notNull", null, locale));
                }
                break;
            case "etatBibliotheque":
                if(CustomValidator.isEmpty(value)){
                    errors.rejectValue("attributValue","error.attribut.notNull", messages.getMessage("error.attribut.notNull", null, locale));
                } else if(CustomValidator.isNotEmpty(ETAT_BIBLIOTHEQUE.getByCode((String)value))){
                    errors.rejectValue("error.attribut.unknown","error.attribut.unknown", messages.getMessage("error.attribut.unknown", null, locale));
                }
                break;
        }
    }

    public Indicateur getTo() {
        return to;
    }

    public String getTypeMesureLibelle() {
        return TYPE_MESURE.getLibelleByCode(this.getTo().getTypeMesure());
    }
}
