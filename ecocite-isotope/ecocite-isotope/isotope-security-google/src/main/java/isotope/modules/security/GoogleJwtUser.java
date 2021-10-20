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

import isotope.modules.user.IJwtUser;
import isotope.modules.user.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Locale;

/**
 * Représente le user JWT en cours dans l'application
 * <p>
 * Created by bbauduin on 02/06/2016.
 */
public class GoogleJwtUser extends JwtUser implements IJwtUser {

    private String googleId;
    private Locale locale;

    public GoogleJwtUser(JwtUser user, String googleId, Collection<? extends GrantedAuthority> authorities) {
        this(user.getId(), user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getPassword(), user.getAuthorities(), user.isEnabled(), user.getLocale());
        this.googleId = googleId;
        this.locale = user.getLocale();
    }

    public GoogleJwtUser(Long id, String username, String firstname, String lastname, String email, String password, Collection<? extends GrantedAuthority> authorities, boolean enabled, Locale locale) {
        super(id, username, firstname, lastname, email, password, authorities, enabled, locale);
    }

    public GoogleJwtUser(User user, Locale locale, Collection<? extends GrantedAuthority> authorities) {
        super(user, locale, authorities);
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
