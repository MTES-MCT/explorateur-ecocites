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

package isotope.modules.cm.valuelistentry.model;

import isotope.commons.entities.BaseEntity;
import isotope.modules.cm.valuelist.model.ValueList;

import javax.persistence.*;

@Entity
@Table(
		name = "is_cm_value_list_entry"
)
public class ValueListEntry extends BaseEntity {
	@Column(
			name = "code",
			nullable = false,
			length = 255
	)
	private String code;

	@Column(
			name = "label",
			nullable = false,
			length = 255
	)
	private String label;

	@Column(
			name = "priority",
			nullable = false
	)
	private Integer priority;

	@Column(
			name = "disabled",
			nullable = false
	)
	private Boolean disabled;

	@ManyToOne
	@JoinColumn(name = "value_list_id")
	private ValueList valueList;

	public ValueListEntry() {
	}

	public ValueList getValueList() {
		return valueList;
	}

	public void setValueList(ValueList valueList) {
		this.valueList = valueList;
	}

	// Getters & Setters

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Boolean getDisabled() {
		return this.disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
}
