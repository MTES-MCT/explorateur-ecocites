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

package com.efficacity.explorateurecocites.beans.biz;

import com.efficacity.explorateurecocites.beans.model.EtiquetteAxe;

/**
 * Created by rxion on 11/02/2018.
 */
public class AssoActionDomainBean extends AssoActionEtiquetteBean{

  private EtiquetteAxe etiquette;

  private AssoActionDomainBean(Long idAsso, Long idAction, int poid) {
	super(idAsso, idAction, poid);
  }

  @Override
  public String getCategorie() {
    return "axe";
  }

  public static AssoActionDomainBean createFrom(Long idAsso, EtiquetteAxe etiquette, Long idAction, int poid) {
	AssoActionDomainBean bean = new AssoActionDomainBean(idAsso, idAction, poid);
	bean.setEtiquette(etiquette);
	return bean;
  }

  public EtiquetteAxe getEtiquette() {
	return etiquette;
  }

  public void setEtiquette(EtiquetteAxe etiquette) {
	this.etiquette = etiquette;
  }
}
