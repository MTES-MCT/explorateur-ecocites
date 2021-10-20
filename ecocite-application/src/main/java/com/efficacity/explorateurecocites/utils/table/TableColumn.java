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
