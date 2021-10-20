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
