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

import com.efficacity.explorateurecocites.beans.model.Etape;
import com.efficacity.explorateurecocites.utils.enumeration.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe qui étent le bean model du générator
 *
 * Date de génération : 06/02/2018
 */

public class EtapeBean {

    private Etape to;
    private String lastMesure;
    private String texteInfoBulle;

    public EtapeBean (Etape etape) {
        this(etape, null);
    }

    public EtapeBean(final Etape etape, final LocalDate lastMesure) {
        this.to = etape;
        if (lastMesure == null) {
            this.lastMesure = "";
        } else {
            this.lastMesure = "Dernière mesure le : " + lastMesure.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        initInfoBulle(etape.getTypeObjet(), etape.getCodeEtape());
    }

    private void initInfoBulle(String typeObjet, String codeEtape){
       if(typeObjet.equals(TYPE_OBJET.ACTION.getCode())){
           if(codeEtape.equals(ETAPE_ACTION.CARACTERISATION.getCode())){
               texteInfoBulle="Pour commencer, vous choisissez des étiquettes pour caractériser votre action (domaine d'action, objectif de la ville durable, type de mission ingénierie le cas échéant).";
           }
           else if(codeEtape.equals(ETAPE_ACTION.INDICATEUR.getCode())){
               texteInfoBulle="Ensuite vous choisissez des indicateurs pour évaluer les retombées de votre action";
           }
           else if(codeEtape.equals(ETAPE_ACTION.EVALUATION_INNOVATION.getCode())){
               texteInfoBulle="Puis vous évaluez les natures et le niveau d'innovation de votre action";
           }
           else if(codeEtape.equals(ETAPE_ACTION.MESURE_INDICATEUR.getCode())){
               texteInfoBulle="Une fois vos indicateurs choisis, vous renseignez périodiquement des mesures et des cibles.";
           }
           else if(codeEtape.equals(ETAPE_ACTION.CONTEXTE_ET_FACTEUR.getCode())){
               texteInfoBulle="Lorsque que votre action est suffisamment avancée (ou terminée), vous remplissez un questionnaire final.";
           }
       }
       else if(typeObjet.equals(TYPE_OBJET.ECOCITE.getCode())){
           if(codeEtape.equals(ETAPE_ECOCITE.CARACTERISATION.getCode())){
               texteInfoBulle="Pour commencer, vous choisissez des étiquettes pour caractériser les objectifs ville durable de votre ÉcoCité.";
           }
           else if(codeEtape.equals(ETAPE_ECOCITE.INDICATEUR.getCode())){
               texteInfoBulle="Ensuite vous choisissez des indicateurs pour évaluer l'état du votre territoire et son évolution.";
           }
           else if(codeEtape.equals(ETAPE_ECOCITE.MESURE_INDICATEUR.getCode())){
               texteInfoBulle="Une fois vos indicateurs choisis, vous renseignez périodiquement des mesures et des cibles.";
           }
           else if(codeEtape.equals(ETAPE_ECOCITE.CONTEXTE_ET_FACTEUR.getCode())){
               texteInfoBulle="Lorsque que les actions de votre ÉcoCité sont suffisamment avancées, vous remplissez un questionnaire final";
           }
       }
    }

    public ETAPE_STATUT getStatutEnum() {
        return ETAPE_STATUT.getByCode(this.to.getStatut());
    }

    public ETAPE_ACTION getEtapeEnumAction() {
        return ETAPE_ACTION.getByCode(this.to.getCodeEtape());
    }

    public ETAPE_ACTION_EDITION getEtapeEnumActionEdition() {
        return ETAPE_ACTION_EDITION.getByCode(this.to.getCodeEtape());
    }

    public ETAPE_ECOCITE getEtapeEnumEcocite() {
        return ETAPE_ECOCITE.getByCode(this.to.getCodeEtape());
    }

    public Long getId() {
        return this.to.getId();
    }

    public void setId(Long id) {
        this.to.setId(id);
    }

    public Long getIdObjet() {
        return this.to.getIdObjet();
    }

    public void setIdObjet(Long idObjet) {
        this.to.setIdObjet(idObjet);
    }

    public String getNom() {
        return this.to.getCodeEtape();
    }

    public void setNom(String nom) {
        this.to.setCodeEtape(nom);
    }

    public String getStatut() {
        return this.to.getStatut();
    }

    public void setStatut(String statut) {
        this.to.setStatut(statut);
    }

    public String getCommentaire() {
        return this.to.getCommentaire();
    }

    public void setCommentaire(String commentaire) {
        this.to.setCommentaire(commentaire);
    }

    public String getTypeObjet() {
        return this.to.getTypeObjet();
    }

    public void setTypeObjet(String typeObjet) {
         this.to.setTypeObjet(typeObjet);
    }

    public Etape getTo() {
        return to;
    }

    public Long getValideePar() { return to.getValideePar(); }

    public LocalDateTime getDateValidation() { return to.getDateValidee(); }

    public void setValideePar(final Long valideePar) {
        this.to.setValideePar(valideePar);
    }

    public void setDateValidee(final LocalDateTime dateValidee) {
        this.to.setDateValidee(dateValidee);
    }

    public void setTo(final Etape to) {
        this.to = to;
    }

    public String getLastMesure() {
        return lastMesure;
    }

    public void setLastMesure(final String lastMesure) {
        this.lastMesure = lastMesure;
    }

    public String getTexteInfoBulle() {
        return this.texteInfoBulle;
    }

    public String getCssClass() {
        ETAPE_STATUT statut = ETAPE_STATUT.getByCode(to.getStatut());
        if (statut != null) {
            switch (statut) {
                case VALIDER:
                    return "state-valid";
                case ENVOYER:
                    return "state-send";
                case IMPOSSIBLE:
                    return "state-impossible";
                case A_RENSEIGNER:
                    return "state-progress";
            }
        }
        return "state-error";
    }

    public String getCssClassLink() {
        ETAPE_STATUT statut = ETAPE_STATUT.getByCode(to.getStatut());
        if (statut != null) {
            switch (statut) {
                case IMPOSSIBLE:
                    return "disabled";
            }
        }
        return "";
    }

    public String getBadgeContentAction(Integer numEtape) {
        ETAPE_STATUT statut = ETAPE_STATUT.getByCode(to.getStatut());
        ETAPE_ACTION etapeAction = ETAPE_ACTION.getByCode(to.getCodeEtape());
        if (statut != null && (statut == ETAPE_STATUT.VALIDER)) {
            return "<i class=\"fa fa-check\"></i>";
        }
        if (etapeAction != null) {
            switch (etapeAction) {
                case CARACTERISATION:
                    return "<span>" + (numEtape == -1 ? 1 : numEtape) + "</span>";
                case INDICATEUR:
                    return "<span>" + (numEtape == -1 ? 2 : numEtape) + "</span>";
                case EVALUATION_INNOVATION:
                    return "<span>" + (numEtape == -1 ? 3 : numEtape) + "</span>";
                case MESURE_INDICATEUR:
                    return "<span>" + (numEtape == -1 ? 4 : numEtape) + "</span>";
                case CONTEXTE_ET_FACTEUR:
                    return "<span>" + (numEtape == -1 ? 5 : numEtape) + "</span>";
            }
        }
        return "<i class=\"fa fa-exclamation\"></i>";
    }

    public String getBadgeContentEcocite() {
        ETAPE_STATUT statut = ETAPE_STATUT.getByCode(to.getStatut());
        ETAPE_ECOCITE etapeEcocite = ETAPE_ECOCITE.getByCode(to.getCodeEtape());
        if (statut != null && (statut == ETAPE_STATUT.VALIDER || statut == ETAPE_STATUT.ENVOYER)) {
            return "<i class=\"fa fa-check\"></i>";
        }
        if (etapeEcocite != null) {
            switch (etapeEcocite) {
                case CARACTERISATION:
                    return "<span>1</span>";
                case INDICATEUR:
                    return "<span>2</span>";
                case MESURE_INDICATEUR:
                    return "<span>3</span>";
                case CONTEXTE_ET_FACTEUR:
                    return "<span>4</span>";
            }
        }
        return "<i class=\"fa fa-exclamation\"></i>";
    }
}
