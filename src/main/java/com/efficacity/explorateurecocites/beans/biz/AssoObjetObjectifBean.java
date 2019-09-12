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

package com.efficacity.explorateurecocites.beans.biz;

import com.efficacity.explorateurecocites.beans.model.EtiquetteFinalite;

/**
 * Created by rxion on 11/02/2018.
 */
public class AssoObjetObjectifBean extends AssoActionEtiquetteBean{


  private EtiquetteFinalite etiquette;

  private AssoObjetObjectifBean(Long idAsso, Long idAction, int poid) {
	super(idAsso, idAction, poid);
  }

  @Override
  public String getCategorie() {
    return "finalite";
  }

  public static AssoObjetObjectifBean createFrom(Long idAsso, EtiquetteFinalite etiquette, Long idAction, int poid) {
    AssoObjetObjectifBean bean = new AssoObjetObjectifBean(idAsso, idAction, poid);
    bean.setEtiquette(etiquette);
    return bean;
  }

  public EtiquetteFinalite getEtiquette() {
	return etiquette;
  }

  public void setEtiquette(EtiquetteFinalite etiquette) {
	this.etiquette = etiquette;
  }
}
