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

import com.efficacity.explorateurecocites.beans.model.ReponsesQuestionnaireEvaluation;
import org.springframework.context.MessageSource;

import java.util.List;

/**
 * Classe qui étent le bean model du générator
 *
 * Date de génération : 06/02/2018
 */

public class ReponsesQuestionnaireEvaluationBean {

    private ReponsesQuestionnaireEvaluation to;
    private List<FileUploadBean> files;

    public ReponsesQuestionnaireEvaluationBean(ReponsesQuestionnaireEvaluation reponsesQuestionnaireEvaluation) {
        super();
        this.to = reponsesQuestionnaireEvaluation;
    }

    public Long getId() {
        return this.to.getId();
    }

    public void setId(Long id) {
        this.to.setId(id);
    }

    public Long getIdQuestion() {
        return this.to.getIdQuestion();
    }

    public void setIdQuestion(Long idQuestion) {
        this.to.setIdQuestion(idQuestion);
    }

    public Long getIdObjet() {
        return this.to.getIdObjet();
    }

    public void setIdObjet(Long idObjet) {
        this.to.setIdObjet(idObjet);
    }

    public String getTypeObjet() {
        return this.to.getTypeObjet();
    }

    public void setTypeObjet(String typeObjet) {
        this.to.setTypeObjet(typeObjet);
    }

    public String getReponsePrincipale() {
        return this.to.getReponsePrincipale();
    }

    public void setReponsePrincipale(String reponsePrincipale) {
        this.to.setReponsePrincipale(reponsePrincipale);
    }

    public String getReponseSecondaire() {
        return this.to.getReponseSecondaire();
    }

    public void setReponseSecondaire(String reponseSecondaire) {
        this.to.setReponseSecondaire(reponseSecondaire);
    }

    public ReponsesQuestionnaireEvaluation getTo() {
        return to;
    }

    private String REPONSE_TEXT = "reponseText";
    private String REPONSE_TEXT_SECONDAIRE = "reponseTextSecondaire";
    private String REPONSE_LIST = "reponseList";
    private String REPONSE_LIST_SECONDAIRE = "reponseListSecondaire";
    private String NON_CONCERNE = "non concerné";

    public void validateChamps(String idChamps, Object value, MessageSource messages){
        if(value instanceof String){
            // Si type text ou type radio on sauvegare juste la saisie ou la valeur check
            if((idChamps.contains(REPONSE_TEXT) && !idChamps.contains(REPONSE_TEXT_SECONDAIRE))
                    || idChamps.contains(REPONSE_LIST) && !idChamps.contains(REPONSE_LIST_SECONDAIRE)){
                setReponsePrincipale((String)value);
                if(NON_CONCERNE.equals((String)value)){
                    setReponseSecondaire(null);
                }
            }
            // Si valeur secondaire => cas de radio autre et texte explicatif on fait sauvegarde direct
            else if (idChamps.contains(REPONSE_TEXT_SECONDAIRE)){
                setReponseSecondaire((String)value);
            }
            // Cas de checkBox avec text libre
            else if (idChamps.contains(REPONSE_LIST_SECONDAIRE)){
                setReponseSecondaire((String)value);
            }
        }
    }

    public void setFiles(final List<FileUploadBean> files) {
        this.files = files;
    }

    public List<FileUploadBean> getFiles() {
        return files;
    }
}
