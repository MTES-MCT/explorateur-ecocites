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

/**
 * Created by oturpin on 20/06/16.
 */
public enum CachePolicy {

    /**
     * Forces caches to submit the request to the origin server for validation before
     * releasing a cached copy.  Useful to assure that authentication is respected
     * (in combination with public), or to maintain rigid freshness, without sacrificing
     * all of the benefits of caching.
     */
    NO_CACHE("no-cache"),

    /**
     * Instructs caches not to keep a copy of the representation under any conditions.
     */
    NO_STORE("no-store"),

    /**
     * Tells caches that they must obey any freshness information you give them.
     * HTTP allows caches to serve stale representations under special conditions;
     * by specifying this policy, you're telling the cache that you want it to
     * strictly follow your rules.
     */
    MUST_REVALIDATE("must-revalidate"),

    /**
     * Similar to {@link CachePolicy#MUST_REVALIDATE}, except that it only applies to proxy caches.
     */
    PROXY_REVALIDATE("proxy-revalidate"),

    /**
     * Allows caches that are specific to one user (e.g., in a browser) to store
     * the response; shared caches (e.g., in a proxy) may not.
     */
    PRIVATE("private"),

    /**
     * Marks authenticated responses as cacheable.  Normally, if HTTP authentication
     * is required, responses are automatically private.
     */
    PUBLIC("public");

    private final String policy;

    /**
     * Creates a new, empty policy.
     */
    CachePolicy() {
        this.policy = null;
    }

    /**
     * Creates a new cache policy with the given Cache-Control header value.
     *
     * @param policy the Cache-Control header value for this policy
     */
    CachePolicy(final String policy) {
        this.policy = policy;
    }

    /**
     * Returns the HTTP <code>cache-control</code> header value for this policy.
     */
    public String policy() {
        return this.policy;
    }
}
