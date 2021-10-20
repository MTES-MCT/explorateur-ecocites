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

package com.efficacity.explorateurecocites.ui.fo.form;

import com.efficacity.explorateurecocites.beans.model.PriseContact;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Created by ktoomey on 16/03/2018.
 */
public class PriseContactForm {
    @JsonSerialize(
            using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
    )
    private Long id;

    @NotNull
    private Long idObjet;

    @NotBlank()
    private String typeObjet;

    @NotBlank(message="Veuillez saisir une adresse email.")
    @Email(message="Veuillez entrer une addresse email valide.")
    private String emailContact;

    @NotBlank(message="Veuillez saisir un message.")
    private String messageContact;

    private LocalDateTime dateRedaction= LocalDateTime.now();

    private LocalDateTime dateEnvoie;

    public String getEmailContact() {
        return emailContact;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }

    public String getMessageContact() {
        return messageContact;
    }

    public void setMessageContact(String messageContact) {
        this.messageContact = messageContact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateRedaction() {
        return dateRedaction;
    }

    public void setDateRedaction(LocalDateTime dateRedaction) {
        this.dateRedaction = dateRedaction;
    }

    public LocalDateTime getDateEnvoie() {
        return dateEnvoie;
    }

    public void setDateEnvoie(LocalDateTime dateEnvoie) {
        this.dateEnvoie = dateEnvoie;
    }

    public Long getIdObjet() {
        return idObjet;
    }

    public void setIdObjet(Long idObjet) {
        this.idObjet = idObjet;
    }

    public String getTypeObjet() {
        return typeObjet;
    }

    public void setTypeObjet(String typeObjet) {
        this.typeObjet = typeObjet;
    }

    public PriseContact getPriseContact() {
        PriseContact priseContact = new PriseContact();
        priseContact.setEmail(getEmailContact());
        priseContact.setMessage(getMessageContact());
        priseContact.setId(getId());
        try {
            priseContact.setDateRedaction(getDateRedaction());
        } catch (DateTimeParseException e) {
            // Worst case we don't set it but the validator should have caught it
        }
        try{
            priseContact.setDateEnvoie(LocalDateTime.now());
        } catch (DateTimeParseException e) {
            // Worst case we don't set it but the validator should have caught it
        }
        return priseContact;
    }

}
