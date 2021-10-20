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

package isotope.modules.cm.valuelistentry.lightbean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ValueListEntryLightBean {
  @JsonSerialize(
      using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
  )
  private Long id;

  @JsonSerialize(
      using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
  )
  private Long idValueList;

  private String code;

  private String label;

  private Integer priority;

  private Boolean disabled;

  private String codeLanguage;

  public ValueListEntryLightBean() {
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getIdValueList() {
    return this.idValueList;
  }

  public void setIdValueList(Long idValueList) {
    this.idValueList = idValueList;
  }

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

  public String getCodeLanguage() {
    return this.codeLanguage;
  }

  public void setCodeLanguage(String codeLanguage) {
    this.codeLanguage = codeLanguage;
  }
}
