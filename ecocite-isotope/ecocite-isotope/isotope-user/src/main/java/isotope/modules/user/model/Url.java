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

package isotope.modules.user.model;

import isotope.commons.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(
    name = "is_url"
)
public class Url extends BaseEntity {
  @Column(
      name = "url",
      nullable = false,
      length = 255
  )
  private String url;

  @Column(
      name = "code_langue",
      length = 2
  )
  private String codeLangue;

  @Column(
      name = "id_function",
      nullable = false
  )
  private Long idFunction;

  @Column(
      name = "date_creation",
      nullable = false
  )
  private LocalDateTime dateCreation;

  public Url() {
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Optional<String> getCodeLangue() {
    return Optional.ofNullable(this.codeLangue);
  }

  public void setCodeLangue(String codeLangue) {
    this.codeLangue = codeLangue;
  }

  public Long getIdFunction() {
    return this.idFunction;
  }

  public void setIdFunction(Long idFunction) {
    this.idFunction = idFunction;
  }

  public LocalDateTime getDateCreation() {
    return this.dateCreation;
  }

  public void setDateCreation(LocalDateTime dateCreation) {
    this.dateCreation = dateCreation;
  }
}
