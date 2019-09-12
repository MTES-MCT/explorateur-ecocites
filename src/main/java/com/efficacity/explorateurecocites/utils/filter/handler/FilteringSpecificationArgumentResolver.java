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
