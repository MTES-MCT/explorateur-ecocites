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

package isotope.modules.security.config;


import isotope.modules.security.GoogleJwtAuthenticationEntryPoint;
import isotope.modules.security.GoogleJwtAuthenticationTokenFilter;
import isotope.modules.security.service.GoogleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Cette classe permet de configurer toute la partie authentification de l'application
 * <p>
 * Created by qletel on 20/05/2016.
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@Order(100)
@PropertySource("classpath:/application-isotope-security-google.properties")
public class GoogleSecurityConfig extends SecurityConfig {

    @Autowired
    protected GoogleJwtAuthenticationEntryPoint unauthorizedHandler;

    /**
     * Permet d'ajouter le filtre JEE de Spring qui nous permet l'authentification par googleId JWT
     *
     */
    @Bean
    @Override
    public UsernamePasswordAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        GoogleJwtAuthenticationTokenFilter authenticationTokenFilter = new GoogleJwtAuthenticationTokenFilter();
        authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
        return authenticationTokenFilter;
    }

    @Bean
    public UserDetailsService getUserDetailsService() {
        return new GoogleServiceImpl();
    }

}
