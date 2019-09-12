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
