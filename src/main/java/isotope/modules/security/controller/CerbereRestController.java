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

package isotope.modules.security.controller;

import com.efficacity.explorateurecocites.configuration.ServiceConfiguration;
import i2.application.cerbere.commun.Cerbere;
import i2.application.cerbere.commun.CerbereConnexionException;
import i2.application.cerbere.commun.Utilisateur;
import isotope.commons.exceptions.NotFoundException;
import isotope.modules.security.JwtUser;
import isotope.modules.security.service.ExplorateurUserServiceImpl;
import isotope.modules.user.model.User;
import isotope.modules.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by tfossurier on 12/02/2018.
 */
@Controller
public class CerbereRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CerbereRestController.class);

    @Autowired
    private ServiceConfiguration serviceConfiguration;

    @Autowired
    private ExplorateurUserServiceImpl explorateurUserServiceImpl;

    @Autowired
    private UserRepository userRepository;

    /**
     * Récupère un refresh token et un username et renvoie un token JWT isotope (google access token)
     *
     * @throws AuthenticationException si l'utilisateur ne peut pas être authentifié
     */
    @GetMapping("/bo/login")
    public String createAuthenticationToken(HttpSession session, HttpServletRequest request) {
        try {
            createSession(session, request);
            return "redirect:/bo";
        } catch (CerbereConnexionException e) {
            LOGGER.warn("Erreur lors de la connection", e);
            return "redirect:/";
        }
    }

    /**
     * Récupère un refresh token et un username et renvoie un token JWT isotope (google access token)
     *
     * @throws AuthenticationException si l'utilisateur ne peut pas être authentifié
     */
    @GetMapping("/bo/reauthentification/do")
    public void reauthentification(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        if (serviceConfiguration.getLoginBouchon()) {
            try {
                Cerbere.creation().reAuthentification(request, response, "/bo/reauthentification/back");
                session.invalidate();
            } catch (CerbereConnexionException e) {
                LOGGER.warn("Erreur lors de la connection", e);
            }
        } else {
            throw new NotFoundException();
        }
    }

    /**
     * Récupère un refresh token et un username et renvoie un token JWT isotope (google access token)
     *
     * @throws AuthenticationException si l'utilisateur ne peut pas être authentifié
     */
    @GetMapping("/bo/reauthentification/back")
    public String reauthentification(HttpSession session, HttpServletRequest request) {
        if (serviceConfiguration.getLoginBouchon()) {
            return createAuthenticationToken(session, request);
        } else {
            throw new NotFoundException();
        }
    }

    public void createSession(HttpSession session, HttpServletRequest request) throws CerbereConnexionException {
        Cerbere cerbere = Cerbere.creation();
        Utilisateur cerbereUser = cerbere.getUtilisateur();
        try {
            if (cerbereUser != null) {
                JwtUser user = null;
                try {
                    User userBdd = userRepository.findOneByLogin(cerbereUser.getMel()).orElse(null);
                    if (userBdd != null) {
                        user = (JwtUser) explorateurUserServiceImpl.majUser(userBdd, cerbereUser);
                    } else {
                        user = (JwtUser) explorateurUserServiceImpl.createUser(cerbereUser);
                    }
                } catch (UsernameNotFoundException e) {
                    user = (JwtUser) explorateurUserServiceImpl.createUser(cerbereUser);
                }
                if(session != null){
                    session.invalidate();
                }
                request.getSession().setAttribute("cle", user.getUsername());
            }
        } catch (Exception e) {
            LOGGER.error("Erreur lors du login d'un user", e);
        }
    }
}
