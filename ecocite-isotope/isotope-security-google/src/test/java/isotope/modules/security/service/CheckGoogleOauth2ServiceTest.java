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

package isotope.modules.security.service;

import isotope.modules.security.exceptions.UnknownAccountException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


/** Tests liés au service de contrôle GoogleOAuth2 */
public class CheckGoogleOauth2ServiceTest {


    @Test
    public void checkUsername(){

        GoogleOAuth2Properties properties = new GoogleOAuth2Properties();
        properties.setDomainRestrictions(Arrays.asList("ipsosenso.com"));

        CheckGoogleOauth2Service service = new CheckGoogleOauth2Service(properties);

        final String username = "john@doe.com";
        assertThatThrownBy(() -> service.checkUsername(username)).isInstanceOf(UnknownAccountException.class);

        final String validUsername = "billybob@ipsosenso.com";
        assertThatCode(()-> service.checkUsername(validUsername)).doesNotThrowAnyException();

        // Attention il n'y a pas le @ dans le mail ;)
        final String invalidUsername2 = "billybobipsosenso.com";
        assertThatCode(()-> service.checkUsername(invalidUsername2)).isInstanceOf(UnknownAccountException.class);

        //final String invalidUsername3 = null;
        //assertThatCode(()-> service.checkUsername(invalidUsername3)).isInstanceOf(UnknownAccountException.class);
    }


    @Test
    public void checkDomainAuthorized(){

        GoogleOAuth2Properties properties = new GoogleOAuth2Properties();
        properties.setDomainRestrictions(Arrays.asList("ipsosenso.com"));

        CheckGoogleOauth2Service service = new CheckGoogleOauth2Service(properties);

        boolean mailVerified = true;
        boolean mailNotVerified = false;

        assertThat(service.isDomainAuthorized(mailVerified, "doe.com")).isFalse();
        assertThat(service.isDomainAuthorized(mailNotVerified, "doe.com")).isFalse();

        // Le domaine est valide, seule le critère de validité du mail doit être validé
        assertThat(service.isDomainAuthorized(mailVerified, "ipsosenso.com")).isTrue();
        assertThat(service.isDomainAuthorized(mailNotVerified, "ipsosenso.com")).isFalse();
    }
}
