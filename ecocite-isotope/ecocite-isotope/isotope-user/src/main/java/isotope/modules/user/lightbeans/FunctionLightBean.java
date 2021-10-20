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

package isotope.modules.user.lightbeans;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.lang.Long;
import java.lang.String;
import java.time.LocalDateTime;

public class FunctionLightBean {
  @JsonSerialize(
      using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
  )
  private Long id;

  private String code;

  private LocalDateTime dateCreation;

  private String type;

  public FunctionLightBean() {
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public LocalDateTime getDateCreation() {
    return this.dateCreation;
  }

  public void setDateCreation(LocalDateTime dateCreation) {
    this.dateCreation = dateCreation;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
