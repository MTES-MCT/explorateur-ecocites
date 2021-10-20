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

import isotope.modules.security.filters.TicketFilter;
import isotope.modules.security.service.JwtTokenUtil;
import isotope.modules.security.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration spécifique à la partie filtre avec ticket
 *
 * Created by bbauduin on 24/04/2017.
 */
@Configuration
@Order(80)
@ConditionalOnProperty("security.ticket.urlliste")
@PropertySource("classpath:/application-isotope-security.properties")
public class TicketSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private TicketService ticketService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Value("${security.ticket.urlliste:}")
	private String urlsTicketListe;

	@Value("${security.ticket.parameterkey:ticket}")
	private String ticketKeyParameter;

	/**
	 * Configuration de spring security
	 */
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// Custom ticket filter
		if (!urlsTicketListe.isEmpty()) {
			String[] urls = urlsTicketListe.split(",");
			httpSecurity
					.requestMatchers()
					.antMatchers(urls).and()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
					.authorizeRequests()
					.antMatchers(urls).permitAll().and()
					.addFilterBefore(new TicketFilter(ticketKeyParameter, ticketService, userDetailsService, jwtTokenUtil), UsernamePasswordAuthenticationFilter.class);
		}
	}

}
