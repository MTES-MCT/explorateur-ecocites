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


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import isotope.modules.security.GoogleJwtUser;
import isotope.modules.security.locale.ILocaleService;
import isotope.modules.user.constante.RoleEnum;
import isotope.modules.user.model.Role;
import isotope.modules.user.model.User;
import isotope.modules.user.repository.UserRepository;
import isotope.modules.user.service.FunctionService;
import isotope.modules.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Service qui permet de récupérer un utilisateur au moment du login
 */
@Service
@Primary
public class GoogleServiceImpl implements UserDetailsService {

    private final String GOOGLE_LASTNAME = "family_name";
    private final String GOOGLE_FIRSTNAME = "given_name";
    private final String PASSWORD = "nonutile";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ILocaleService localeService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private FunctionService functionService;

    /**
     * Retourne un {@link GoogleJwtUser} correspondant au login passé en paramètre.
     * <p>
     * <b>Note :</b> Il semblerait que l'annotation Transactional soit nécessaire, sinon on a l'erreur :
     * <p>
     * failed to lazily initialize a collection of role: User.group, could not initialize proxy - no Session
     *
     * @param username le login de l'utilisateur
     * @return l'utilisateur correspondant au {@code username} sous la forme d'un objet {@code GoogleJwtUser}.
     * @throws UsernameNotFoundException
     */
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // On récupère le user
        User user = userRepository.findOneByLogin(username).orElseThrow(() -> new UsernameNotFoundException(String.format("No user found with username '%s'.", username)));

        // On va récupérer ces droits de l'utilisateur
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getCode().toUpperCase()))
        );
        functionService.getFunctionsWithoutAssoRole().forEach(functionLightBean ->
                authorities.add(new SimpleGrantedAuthority(functionLightBean.getCode()))
        );
        functionService.getFunctions(user.getId()).forEach(functionLightBean ->
                authorities.add(new SimpleGrantedAuthority(functionLightBean.getCode()))
        );
        return new GoogleJwtUser(user, localeService.getUserLocale(user), authorities);
    }

    /**
     * Crée un utilisateur en base à partir des informations du compte Google
     * @return l'objet userDetails
     */
    public UserDetails createUser(GoogleIdToken tokenGoogle) {
        User user = new User();

        user.setLogin(tokenGoogle.getPayload().getEmail());
        user.setEmail(tokenGoogle.getPayload().getEmail());
        user.setLastname((String)tokenGoogle.getPayload().get(GOOGLE_LASTNAME));
        user.setFirstname((String)tokenGoogle.getPayload().get(GOOGLE_FIRSTNAME));
        user.setDisabled(false);

        //Génération du mot de passe
        user.setPasswordHash(PASSWORD);

        //Maj des dates
        LocalDateTime now = LocalDateTime.now();
        user.setDateCreation(now);
        user.setDateModification(now);

        //Je sauvegarde mon utilisateur en base
        user = userRepository.save(user);

        Set<Role> roles = new HashSet<>();
		Role invite = new Role();
        invite.setCode(RoleEnum.INVITE.name().toLowerCase());
        invite.setId(RoleEnum.INVITE.getId());
        roles.add(invite);
        user.setRoles(roles);

        //Je finis par renvoyer le user en rechargeant ses droits et son compte depuis la base
        return loadUserByUsername(user.getLogin());
    }
}
