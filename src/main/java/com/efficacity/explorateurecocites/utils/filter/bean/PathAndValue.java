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

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

/**
 * Created by rxion on 16/02/2018.
 */
public class PathAndValue {
  private final List<SingularAttribute<?, ?>> path;
  private final String value;

  public PathAndValue(List<SingularAttribute<?, ?>> path, String value) {
	this.path = path;
	this.value = value;
  }

  public String getValue() {
	return value;
  }

  public Path<?> getPath(final Root<?> root) {
	Path<?> rv = root;
	for (SingularAttribute attribute : path) {
	  rv = rv.get(attribute);
	}
	return rv;
  }
}
