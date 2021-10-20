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

package isotope.modules.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import isotope.modules.security.exceptions.UnknownAccountException;
import isotope.modules.security.service.CheckGoogleOauth2Service;
import isotope.modules.security.service.JwtProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

/**
 * Ce filtre s'occupe de vérifier que le googleId envoyé par l'utilisateur est valide
 * <p>
 * Created by bbauduin on 02/06/2016.
 */
public class GoogleJwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(GoogleJwtAuthenticationTokenFilter.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private CheckGoogleOauth2Service checkGoogleOauth2Service;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        //Le googleId est stocké dans un header particulier (configurable)
        String authToken = httpRequest.getHeader(jwtProperties.getHeader());

        //Je récupère le username depuis le googleId
        if (SecurityContextHolder.getContext().getAuthentication() == null) {

            try {
                if (authToken != null) {
                    GoogleIdToken compte = checkGoogleOauth2Service.check(authToken);
                    //On vérifie que le user connecté est valide auprès de Google
                    if (compte != null) {
                        GoogleIdToken.Payload payload = compte.getPayload();
                        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(payload.getEmail());

                        //J'ajoute l'info sur le googleId Google
                        GoogleJwtUser userDetails = new GoogleJwtUser(user, authToken, user.getAuthorities());
                        if (userDetails.getLocale() == null) {
                            Locale locale = Locale.forLanguageTag((String) payload.get("locale"));
                            userDetails.setLocale(locale);
                        }

                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (UnknownAccountException e) {
                logger.debug("Le token n'est pas valide : {}", authToken, e);
            }
        }

        chain.doFilter(request, response);
    }
}
