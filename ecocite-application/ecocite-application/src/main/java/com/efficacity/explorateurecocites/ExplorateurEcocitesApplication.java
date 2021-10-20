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

package com.efficacity.explorateurecocites;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
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
