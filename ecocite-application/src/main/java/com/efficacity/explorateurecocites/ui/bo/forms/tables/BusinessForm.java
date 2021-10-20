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

package com.efficacity.explorateurecocites.ui.bo.forms.tables;

import com.efficacity.explorateurecocites.beans.model.Business;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.helpers.SelectOption;
import com.efficacity.explorateurecocites.utils.enumeration.STATUT_FINANCIER_AFFAIRE;
import com.efficacity.explorateurecocites.utils.enumeration.TRANCHE_EXECUTION;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_FINANCEMENT;
import com.efficacity.explorateurecocites.utils.table.InputType;
import com.efficacity.explorateurecocites.utils.table.TableColumn;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BusinessForm {

    @NotNull
    @TableColumn(value = "business.colum.id", type = InputType.HIDDEN)
    private Long id;

    @NotBlank
    @TableColumn("business.colum.nom")
    private String nom;

    @TableColumn(value = "business.colum.ecocite", required = false)
    private String ecocite;

    @NotBlank
    @Size(max = 10, message = "La taille de ce champ ne peut dépasser 10 caractères")
    @TableColumn("business.colum.numero")
    private String numero;

    @NotBlank
    @Size(max = 10, message = "La taille de ce champ ne peut dépasser 10 caractères")
    @TableColumn("business.colum.numeroOperation")
    private String numeroOperation;

    @NotBlank
    @TableColumn("business.colum.nomOperation")
    private String nomOperation;

    @TableColumn(value = "business.colum.nomAction", type = InputType.SELECT, required = false)
    private Long idAction;
    private List<SelectOption> idActionDefaults;

    @NotBlank
    @TableColumn(value = "business.colum.tranche", type = InputType.SELECT)
    private String tranche;
    private List<SelectOption> trancheDefaults;

    @NotBlank
    @TableColumn(value = "business.colum.typeFinancement", type = InputType.SELECT)
    private String typeFinancement;
    private List<SelectOption> typeFinancementDefaults;

    @NotBlank
    @TableColumn(value = "business.colum.statutFinancier", type = InputType.SELECT)
    private String statutFinancier;
    private List<SelectOption> statutFinancierDefaults;

    @TableColumn(value = "business.colum.datedebut", type = InputType.DATE, required = false)
    private String dateDebut;

    @TableColumn(value = "business.colum.datefin", type = InputType.DATE, required = false)
    private String dateFin;

    public BusinessForm() {
        id = -1L;
        idActionDefaults = new ArrayList<>();
        trancheDefaults = Arrays.stream(TRANCHE_EXECUTION.values()).map(a -> new SelectOption(a.getCode(), a.getLibelle())).collect(Collectors.toList());
        typeFinancementDefaults = Arrays.stream(TYPE_FINANCEMENT.values()).map(a -> new SelectOption(a.getCode(), a.getLibelle())).collect(Collectors.toList());
        statutFinancierDefaults = Arrays.stream(STATUT_FINANCIER_AFFAIRE.values()).map(a -> new SelectOption(a.getFirstCode(), a.getLibelle())).collect(Collectors.toList());
    }

    public List<SelectOption> getIdActionDefaults() {
        return idActionDefaults;
    }

    public void setIdActionDefaults(final List<SelectOption> idActionDefaults) {
        this.idActionDefaults = idActionDefaults;
    }

    public String getNumeroOperation() {
        return numeroOperation;
    }

    public void setNumeroOperation(final String numeroOperation) {
        this.numeroOperation = numeroOperation;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(final String nom) {
        this.nom = nom;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(final String numero) {
        this.numero = numero;
    }

    public String getNomOperation() {
        return nomOperation;
    }

    public void setNomOperation(final String nomOperation) {
        this.nomOperation = nomOperation;
    }

    public Long getIdAction() {
        return idAction;
    }

    public void setIdAction(final Long idAction) {
        this.idAction = idAction;
    }

    public String getTranche() {
        return tranche;
    }

    public void setTranche(final String tranche) {
        this.tranche = tranche;
    }

    public List<SelectOption> getTrancheDefaults() {
        return trancheDefaults;
    }

    public void setTrancheDefaults(final List<SelectOption> trancheDefaults) {
        this.trancheDefaults = trancheDefaults;
    }

    public String getTypeFinancement() {
        return typeFinancement;
    }

    public void setTypeFinancement(final String typeFinancement) {
        this.typeFinancement = typeFinancement;
    }

    public List<SelectOption> getTypeFinancementDefaults() {
        return typeFinancementDefaults;
    }

    public void setTypeFinancementDefaults(final List<SelectOption> typeFinancementDefaults) {
        this.typeFinancementDefaults = typeFinancementDefaults;
    }

    public String getStatutFinancier() {
        return statutFinancier;
    }

    public void setStatutFinancier(final String statutFinancier) {
        this.statutFinancier = statutFinancier;
    }

    public List<SelectOption> getStatutFinancierDefaults() {
        return statutFinancierDefaults;
    }

    public void setStatutFinancierDefaults(final List<SelectOption> statutFinancierDefaults) {
        this.statutFinancierDefaults = statutFinancierDefaults;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(final String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(final String dateFin) {
        this.dateFin = dateFin;
    }

    public Business getBusiness() {
        Business business = new Business();
        business.setNom(getNom());
        business.setNumeroAffaire(getNumero());
        business.setNomOperation(getNomOperation());
        business.setNumeroOperation(getNumeroOperation());
        business.setIdAction(getIdAction());
        business.setTranche(getTranche());
        business.setTypeFinancement(getTypeFinancement());
        business.setStatutFinancier(getStatutFinancier());
        business.setEcocite(getEcocite());
        try {
            business.setDateDebut(LocalDate.parse(getDateDebut(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay());
        } catch (DateTimeParseException e) {
            // Worst case we don't set it but the validator should have caught it
        }
        try{
               business.setDateFin(LocalDate.parse(getDateFin(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay());
        } catch (DateTimeParseException e) {
            // Worst case we don't set it but the validator should have caught it
        }
        return business;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getEcocite() {
        return ecocite;
    }

    public void setEcocite(final String ecocite) {
        this.ecocite = ecocite;
    }
}
