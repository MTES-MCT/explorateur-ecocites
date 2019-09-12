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
