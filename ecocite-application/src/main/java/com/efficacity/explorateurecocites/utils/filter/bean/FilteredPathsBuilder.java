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
import java.util.*;

/**
 * Created by rxion on 16/02/2018.
 */
public class FilteredPathsBuilder<T> {
  private final Map<List<SingularAttribute<?, ?>>, PathOperation<T>> filterablePaths = new HashMap<>();

  private List<SingularAttribute<?, ?>> lastPath;
  private PathOperation<T> lastOp;

  public FilteredPathsBuilder<T> filter(SingularAttribute<?, ?>... path) {
	if(lastPath != null && lastOp != null) {
	  filterablePaths.put(lastPath, lastOp);
	}
	this.lastPath = Collections.unmodifiableList(Arrays.asList(path));
	this.lastOp = null;
	return this;
  }

  public FilteredPathsBuilder<T> with(PathOperation<T> op) {
	this.lastOp = op;
	return this;
  }

  public Map<List<SingularAttribute<?, ?>>, PathOperation<T>> getFilterablePaths() {
	if(lastPath != null && lastOp != null) {
	  filterablePaths.put(lastPath, lastOp);
	}
	this.lastPath = null;
	this.lastOp = null;
	return Collections.unmodifiableMap(filterablePaths);
  }
}
