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

package com.efficacity.explorateurecocites.beans.biz.table;

import com.efficacity.explorateurecocites.utils.table.TableColumn;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by rxion on 19/02/2018.
 */
public class BusinessTable {
    @JsonSerialize(
            using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
    )
    private Long id;

    @TableColumn("business.colum.nom")
    private String nom;

    @TableColumn("business.colum.numero")
    private String numero;

    @TableColumn("business.colum.nomOperation")
    private String nomOperation;

    @TableColumn("business.colum.nomAction")
    private String nomAction;

    @TableColumn("business.colum.tranche")
    private String tranche;

    @TableColumn("business.colum.typeFinancement")
    private String typeFinancement;

    @TableColumn("business.colum.statutFinancier")
    private String statutFinancier;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
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

    public String getNomAction() {
        return nomAction;
    }

    public void setNomAction(final String nomAction) {
        this.nomAction = nomAction;
    }

    public String getTranche() {
        return tranche;
    }

    public void setTranche(final String tranche) {
        this.tranche = tranche;
    }

    public String getTypeFinancement() {
        return typeFinancement;
    }

    public void setTypeFinancement(final String typeFinancement) {
        this.typeFinancement = typeFinancement;
    }

    public String getStatutFinancier() {
        return statutFinancier;
    }

    public void setStatutFinancier(final String statutFinancier) {
        this.statutFinancier = statutFinancier;
    }
}
