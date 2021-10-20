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

package isotope.commons.controllers.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by oturpin on 20/06/16.
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheControl {
    /**
     * The <code>cache-control</code> policies to apply to the response.
     *
     * @see CachePolicy
     */
    CachePolicy[] policy() default { CachePolicy.NO_CACHE };

    /**
     *  The maximum amount of time, in seconds, that this content will be considered fresh.
     */
    int maxAge() default 0;

    /**
     * The maximum amount of time, in seconds, that this content will be considered fresh
     * only for shared caches (e.g., proxy) caches.
     */
    int sharedMaxAge() default -1;
}
