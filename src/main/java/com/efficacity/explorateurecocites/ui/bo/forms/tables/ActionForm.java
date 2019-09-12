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

package com.efficacity.explorateurecocites.ui.bo.forms.tables;

import com.efficacity.explorateurecocites.beans.model.Action;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.helpers.SelectOption;
import com.efficacity.explorateurecocites.utils.enumeration.ETAT_PUBLICATION;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_FINANCEMENT;
import com.efficacity.explorateurecocites.utils.table.InputType;
import com.efficacity.explorateurecocites.utils.table.TableColumn;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rxion on 19/02/2018.
 */
public class ActionForm {
    @JsonSerialize(
            using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
    )
    private Long id;

    @NotBlank
    @TableColumn("action.colum.nomPublic")
    private String nomPublic;

    @NotNull
    @TableColumn(value = "action.colum.ecocite", type = InputType.SELECT)
    private Long idEcocite;
    private List<SelectOption> idEcociteDefaults;

    @NotBlank
    @TableColumn(value = "action.colum.typeFinancement", type = InputType.SELECT)
    private String typeFinancement;
    private List<SelectOption> typeFinancementDefaults;

    @NotBlank
    @Size(max = 20, message = "La taille de ce champ ne peut dépasser 10 caractères")
    @TableColumn("action.colum.numero")
    private String numero;

    public ActionForm() {
        idEcocite = -1L;
        typeFinancementDefaults = Arrays.stream(TYPE_FINANCEMENT.values()).map(a -> new SelectOption(a.getCode(), a.getLibelle())).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getNomPublic() {
        return nomPublic;
    }

    public void setNomPublic(final String nomPublic) {
        this.nomPublic = nomPublic;
    }

    public Long getIdEcocite() {
        return idEcocite;
    }

    public void setIdEcocite(final Long idEcocite) {
        this.idEcocite = idEcocite;
    }

    public List<SelectOption> getIdEcociteDefaults() {
        return idEcociteDefaults;
    }

    public void setIdEcociteDefaults(final List<SelectOption> idEcociteDefaults) {
        this.idEcociteDefaults = idEcociteDefaults;
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(final String numero) {
        this.numero = numero;
    }

    public Action getAction() {
        Action action = new Action();
        action.setNomPublic(getNomPublic());
        action.setIdEcocite(getIdEcocite());
        action.setNumeroAction(getNumero());
        action.setEtatPublication(ETAT_PUBLICATION.NON_PUBLIE.getCode());
        action.setTypeFinancement(getTypeFinancement());
        return action;
    }
}
