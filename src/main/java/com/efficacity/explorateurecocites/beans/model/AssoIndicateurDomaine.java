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

package com.efficacity.explorateurecocites.beans.model;

import isotope.commons.entities.BaseEntity;
import java.lang.Long;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Cette classe a été autogénérée. Elle sera écrasée à chaque génération.
 *
 * Date de génération : 23/01/2019
 */
@Entity
@Table(
    name = "exp_asso_indicateur_domaine"
)
public class AssoIndicateurDomaine extends BaseEntity {
  @Column(
      name = "id_indicateur"
  )
  private Long idIndicateur;

  @Column(
      name = "id_domaine"
  )
  private Long idDomaine;

  public AssoIndicateurDomaine() {
  }

  public Long getIdIndicateur() {
    return this.idIndicateur;
  }

  public void setIdIndicateur(Long idIndicateur) {
    this.idIndicateur = idIndicateur;
  }

  public Long getIdDomaine() {
    return this.idDomaine;
  }

  public void setIdDomaine(Long idDomaine) {
    this.idDomaine = idDomaine;
  }
}
