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

public class BeanSaveAttribut {

    public BeanSaveAttribut() {
    }

    private String objectId;
    private String objectClass;
    private String attributId;
    private String attributValue;
    private String referenceId;
    private String referenceTypeObjet;

    // Car particulier des questionnaire avec checkBox et texte libre, on a besoin de la valeur de la checkBox pour retrouver la réponse a update si on change la donnée secondaire de celle-ci
    private String checkBoxValue;
    private Boolean checked;



    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }

    public String getAttributId() {
        return attributId;
    }

    public void setAttributId(String attributId) {
        this.attributId = attributId;
    }

    public String getAttributValue() {
        return attributValue;
    }

    public void setAttributValue(String attributValue) {
        this.attributValue = attributValue;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(final String referenceId) {
        this.referenceId = referenceId;
    }

    public String getReferenceTypeObjet() {
        return referenceTypeObjet;
    }

    public void setReferenceTypeObjet(String referenceTypeObjet) {
        this.referenceTypeObjet = referenceTypeObjet;
    }

    public String getCheckBoxValue() {
        return checkBoxValue;
    }

    public void setCheckBoxValue(String checkBoxValue) {
        this.checkBoxValue = checkBoxValue;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(final Boolean checked) {
        this.checked = checked;
    }
}
