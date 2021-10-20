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

package isotope.modules.cm.valuelist.lightbean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

public class ValueListLightBean {
  @JsonSerialize(
      using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
  )
  private Long id;

  @JsonSerialize(
      using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
  )
  private Long idCreator;

  private String creatorName;

  private LocalDateTime creationDate;

  @JsonSerialize(
      using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
  )
  private Long idModifier;

  private String modifierName;

  private LocalDateTime modificationDate;

  private String code;

  private String description;

  private String name;

  public ValueListLightBean() {
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getIdCreator() {
    return this.idCreator;
  }

  public void setIdCreator(Long idCreator) {
    this.idCreator = idCreator;
  }

  public String getCreatorName() {
    return this.creatorName;
  }

  public void setCreatorName(String creatorName) {
    this.creatorName = creatorName;
  }

  public LocalDateTime getCreationDate() {
    return this.creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public Long getIdModifier() {
    return this.idModifier;
  }

  public void setIdModifier(Long idModifier) {
    this.idModifier = idModifier;
  }

  public String getModifierName() {
    return this.modifierName;
  }

  public void setModifierName(String modifierName) {
    this.modifierName = modifierName;
  }

  public LocalDateTime getModificationDate() {
    return this.modificationDate;
  }

  public void setModificationDate(LocalDateTime modificationDate) {
    this.modificationDate = modificationDate;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
