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

@Entity
@Table(
    name = "is_asso_function_role"
)
public class AssoFunctionRole extends BaseEntity {

  @Column(
      name = "id_function",
      nullable = false
  )
  private Long idFunction;

  @Column(
      name = "id_role",
      nullable = false
  )
  private Long idRole;

  public AssoFunctionRole() {
  }


  public Long getIdFunction() {
    return this.idFunction;
  }

  public void setIdFunction(Long idFunction) {
    this.idFunction = idFunction;
  }

  public Long getIdRole() {
    return this.idRole;
  }

  public void setIdRole(Long idRole) {
    this.idRole = idRole;
  }
}
