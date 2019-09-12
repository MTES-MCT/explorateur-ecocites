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

package com.efficacity.explorateurecocites.utils;

import isotope.constante.Const;
import isotope.modules.security.config.SecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Created by qletel on 12/02/2017.
 */
@Profile(Const.DEV)
@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

	public static final String LOGIN_PARAMATER = "login";

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("api2")
				.select()
				//.apis(RequestHandlerSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("com.efficacity"))
				.paths(input -> !input.startsWith("/api/demo"))
				.build()

				.globalOperationParameters(
						Arrays.asList(
								new ParameterBuilder()
										.name("TestNameHeader")
										.description("La version de l'API")
										.modelRef(new ModelRef("string"))
										.parameterType("header")
										.build(),
								new ParameterBuilder()
										.name(LOGIN_PARAMATER)
										.description("Le login de l'utilisateur (dev)")
										.modelRef(new ModelRef("string"))
										.parameterType("query")
										.build()
						)
				)

				.genericModelSubstitutes(ResponseEntity.class)
				.directModelSubstitute(LocalDate.class, Date.class)
				.directModelSubstitute(LocalDateTime.class, java.util.Date.class)
				;
	}

	@Bean
	UiConfiguration uiConfig() {
		return new UiConfiguration(
				null,// url
				"none",       // docExpansion          => none | list
				"alpha",      // apiSorter             => alpha
				"schema",     // defaultModelRendering => schema
				UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
				true,        // enableJsonEditor      => true | false
				true,         // showRequestHeaders    => true | false
				15000L);      // requestTimeout => in milliseconds, defaults to null (uses jquery xh timeout)
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {

//            @Override
//            public void addViewControllers(ViewControllerRegistry registry) {
//                registry.addRedirectViewController("/swagger/v2/api-docs", "/v2/api-docs");
//                registry.addRedirectViewController("/swagger/configuration/ui", "/configuration/ui");
//                registry.addRedirectViewController("/swagger/configuration/security", "/configuration/security");
//                registry.addRedirectViewController("/swagger/swagger-resources", "/swagger-resources");
//                registry.addRedirectViewController("/swagger", "/documentation/swagger-ui.html");
//                registry.addRedirectViewController("/swagger/", "/documentation/swagger-ui.html");
//                super.addViewControllers(registry);
//            }

			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
				// surcharge de swagger-ui.html
//				registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/public/swagger/swagger-ui.html");
				registry
						.addResourceHandler("/swagger/**").addResourceLocations("classpath:/META-INF/resources/");
			}
		};
	}

	/**
	 * TODO à supprimer au profit de IsotopeSwaggerSecurityConfiguration
	 */
	@Order(Ordered.HIGHEST_PRECEDENCE)
	@Configuration
	class SwaggerSecurityConfiguration extends SecurityConfig {

		protected void configure(HttpSecurity httpSecurity) throws Exception {
			httpSecurity
					.authorizeRequests()
					// swagger
					.antMatchers("/v2/**").permitAll()
					.antMatchers("/webjars/**").permitAll()
					.antMatchers("/configuration/**").permitAll()
					.antMatchers("/swagger-resources/**").permitAll()
					.antMatchers("/swagger-ui.html").permitAll()
			;

//			httpSecurity.addFilterBefore(new DevAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

			super.configure(httpSecurity);
		}

	}

}
