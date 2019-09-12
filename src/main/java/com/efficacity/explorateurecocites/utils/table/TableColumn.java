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

package com.efficacity.explorateurecocites.utils.table;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Created by rxion on 19/02/2018.
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface TableColumn {
    /**
     * Indique qu'il faut utiliser le nom par défaut de la colonne
     */
    String USE_DEFAULT_NAME = "use_default_name";

    /**
     * @return Le nom de la colonne dans le tableau, si vide alors le nom est celui de chmaps
     */
    String value() default USE_DEFAULT_NAME;
    InputType type() default InputType.BASIC;
    boolean required() default true;
}
