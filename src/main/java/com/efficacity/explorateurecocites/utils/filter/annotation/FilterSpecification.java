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

package com.efficacity.explorateurecocites.utils.filter.annotation;

import com.efficacity.explorateurecocites.utils.filter.bean.PathAndValue;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;

/**
 * Created by rxion on 16/02/2018.
 */
public class FilterSpecification<T> implements Specification<T> {

  protected final Map<PathOperation, PathAndValue> filteredPaths;

  public FilterSpecification(Map<PathOperation, PathAndValue> filteredPaths) {
	this.filteredPaths = filteredPaths;
  }

  @Override
  public final Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	this.customizeQuery(root, query);
	return this.filteredPaths.entrySet()
			.stream()
			.map(e -> e.getKey().buildPredicate(cb, e.getValue().getPath(root), e.getValue().getValue()))
			.reduce(cb.conjunction(), (p1, p2) -> cb.and(p1, p2));
  }

  protected void customizeQuery(Root<T> root, final CriteriaQuery<?> query) {}
}
