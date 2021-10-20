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

package com.efficacity.explorateurecocites.utils.filter.bean;

import com.efficacity.explorateurecocites.utils.filter.annotation.PathOperation;

import javax.persistence.metamodel.SingularAttribute;
import java.util.List;
import java.util.Map;

/**
 * Created by rxion on 16/02/2018.
 */
public abstract class FilteredPaths<T> {
  protected final FilteredPathsBuilder<T> builder = new FilteredPathsBuilder<>();

  public final Map<List<SingularAttribute<?, ?>>, PathOperation<T>> getValue() {
	return this.builder.getFilterablePaths();
  }

  protected FilteredPathsBuilder<T> filter(SingularAttribute<?, ?>... path) {
	return builder.filter(path);
  }
}
