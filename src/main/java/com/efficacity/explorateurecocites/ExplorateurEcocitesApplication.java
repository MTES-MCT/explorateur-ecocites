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

package com.efficacity.explorateurecocites;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

/**
 * Created by tfossurier on 10/01/2018.
 * Point de démarrage de l'application
 */
@EnableAutoConfiguration
@EnableSpringDataWebSupport
@EntityScan(basePackages = {"isotope", "com.efficacity.explorateurecocites"})
@Configuration
@EnableJpaRepositories(basePackages = {"isotope", "com.efficacity.explorateurecocites"})
@ComponentScan(basePackages = {"isotope", "com.efficacity.explorateurecocites"})
public class ExplorateurEcocitesApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(ExplorateurEcocitesApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ExplorateurEcocitesApplication.class);
    }
}
