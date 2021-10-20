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

package isotope.modules.cm.menu.model;

import isotope.commons.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(
    name = "is_asso_menu_menu_entry"
)
public class AssoMenuMenuEntry extends BaseEntity {
  @Column(
      name = "id_menu",
      nullable = false
  )
  private Long idMenu;

  @Column(
      name = "id_menu_entry",
      nullable = false
  )
  private Long idMenuEntry;

  @Column(
      name = "ordre",
      nullable = false
  )
  private Integer ordre;

  public AssoMenuMenuEntry() {
  }

  public Long getIdMenu() {
    return this.idMenu;
  }

  public void setIdMenu(Long idMenu) {
    this.idMenu = idMenu;
  }

  public Long getIdMenuEntry() {
    return this.idMenuEntry;
  }

  public void setIdMenuEntry(Long idMenuEntry) {
    this.idMenuEntry = idMenuEntry;
  }

  public Integer getOrdre() {
    return this.ordre;
  }

  public void setOrdre(Integer ordre) {
    this.ordre = ordre;
  }
}
