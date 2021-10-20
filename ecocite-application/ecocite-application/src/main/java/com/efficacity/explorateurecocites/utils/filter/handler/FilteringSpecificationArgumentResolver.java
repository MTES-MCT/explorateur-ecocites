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

package com.efficacity.explorateurecocites.utils.filter.handler;

import com.efficacity.explorateurecocites.utils.filter.annotation.FilterDefinition;
import com.efficacity.explorateurecocites.utils.filter.annotation.PathOperation;
import com.efficacity.explorateurecocites.utils.filter.bean.FilteredPaths;
import com.efficacity.explorateurecocites.utils.filter.bean.PathAndValue;
import org.springframework.core.MethodParameter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.persistence.metamodel.SingularAttribute;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by rxion on 16/02/2018.
 */
public class FilteringSpecificationArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
	return parameter.hasParameterAnnotation(FilterDefinition.class)
			&& parameter.getParameterType() == Specification.class;
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
								NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

    final FilterDefinition filterDefinition = parameter.getParameterAnnotation(FilterDefinition.class);
	final FilteredPaths<?> paths = filterDefinition.paths().newInstance();
	final Map<PathOperation<?>, PathAndValue> filterValues = new HashMap<>();

	paths.getValue().entrySet().forEach(entry -> {
	  final String param = entry.getKey()
			  .stream()
			  .map(SingularAttribute::getName)
			  .collect(Collectors.joining("."));

	  final String paramValue = Optional.ofNullable(webRequest.getParameter(param)).orElse("");
	  if (!paramValue.isEmpty()) {
		filterValues.put(entry.getValue(), new PathAndValue(entry.getKey(), paramValue));
	  }
	});

	return filterDefinition.implementation().getConstructor(Map.class).newInstance(filterValues);
  }
}
