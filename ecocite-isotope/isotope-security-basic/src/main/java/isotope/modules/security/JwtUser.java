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

import com.fasterxml.jackson.annotation.JsonIgnore;
import isotope.modules.user.IJwtUser;
import isotope.modules.user.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Locale;

/**
 * Représente le user JWT en cours dans l'application
 *
 * Created by bbauduin on 02/06/2016.
 */
public class JwtUser implements IJwtUser {

    private final Long id;
    private final String username;
    private final String firstname;
    private final String lastname;
    private final String password;
    private final String email;
    private final boolean enabled;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Locale locale;

    public JwtUser(
            Long id,
            String username,
            String firstname,
            String lastname,
            String email,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            boolean enabled,
            Locale locale) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.locale = locale;
    }

    public JwtUser(User user, Locale locale, Collection<? extends GrantedAuthority> authorities) {
        this(
            user.getId(),
            user.getLogin(),
            user.getFirstname(),
            user.getLastname(),
            user.getEmail(),
            user.getPasswordHash(),
            authorities,
            !user.isDisabled(),
                locale);
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    public String getEmail() {
        return email;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
