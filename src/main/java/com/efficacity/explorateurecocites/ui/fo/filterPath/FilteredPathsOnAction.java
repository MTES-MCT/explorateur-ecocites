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

package com.efficacity.explorateurecocites.ui.fo.filterPath;

import com.efficacity.explorateurecocites.beans.model.Action_;
import com.efficacity.explorateurecocites.utils.filter.bean.FilteredPaths;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created by rxion on 16/02/2018.
 */
public class FilteredPathsOnAction<Action> extends FilteredPaths<Action> {
  public FilteredPathsOnAction() {
    filter(Action_.idAxe)
			.with(CriteriaBuilder::equal)
			.filter(Action_.idEcocite)
			.with(CriteriaBuilder::equal)
			.filter(Action_.typeFinancement)
			.with(CriteriaBuilder::equal)
			.filter(Action_.trancheExecution)
			.with(CriteriaBuilder::equal);
  }
}
