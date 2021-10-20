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

import com.efficacity.explorateurecocites.beans.Enums.ProfilsUtilisateur;
import com.efficacity.explorateurecocites.beans.model.Action;
import com.efficacity.explorateurecocites.beans.model.UserDetailLog;
import com.efficacity.explorateurecocites.beans.model.UserObjetRight;
import com.efficacity.explorateurecocites.beans.service.ActionService;
import com.efficacity.explorateurecocites.beans.service.EcociteService;
import com.efficacity.explorateurecocites.beans.service.UserDetailLogService;
import com.efficacity.explorateurecocites.beans.service.UserObjetRightService;
import com.efficacity.explorateurecocites.beans.model.*;
import com.efficacity.explorateurecocites.beans.service.*;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import i2.application.cerbere.commun.Profil;
import i2.application.cerbere.commun.Utilisateur;
import isotope.modules.security.JwtUser;
import isotope.modules.security.locale.ILocaleService;
import isotope.modules.user.model.Role;
import isotope.modules.user.model.User;
import isotope.modules.user.repository.RoleRepository;
import isotope.modules.user.repository.UserRepository;
import isotope.modules.user.service.FunctionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.*;

/**
 * Created by tfossurier on 12/02/2018.
 */
@Service
@Primary
public class ExplorateurUserServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExplorateurUserServiceImpl.class);

    private final String FAKE_HASH = "nonutile";

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EcociteService ecociteService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private ActionService actionService;
    @Autowired
    private UserDetailLogService userDetailLogService;
    @Autowired
    private ILocaleService localeService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private FunctionService functionService;
    @Autowired
    private UserObjetRightService userObjetRightService;

    public ExplorateurUserServiceImpl() {
    }

    @Transactional(
            readOnly = true
    )
    public UserDetails loadUserByUsername(String username) {
        User user = this.userRepository.findOneByLogin(username).orElseThrow(() -> new UsernameNotFoundException(String.format("No user found with username '%s'.", username)));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getCode().toUpperCase())));
        this.functionService.getFunctions(user.getId()).forEach(functionLightBean -> authorities.add(new SimpleGrantedAuthority(functionLightBean.getCode())));

        // On va charger la liste des action/ecocite auquel il a droit depuis la base et pas depuis la réponse cerbere
        this.userObjetRightService.findAllByIdUser(user.getId()).forEach(userObjetRight -> authorities.add(new SimpleGrantedAuthority(userObjetRight.getCodeRole())));

        return new JwtUser(user, this.localeService.getUserLocale(user), authorities);
    }


    /**
     * Crée un utilisateur en base à partir des informations reçues de CERBERE
     *
     * @return l'objet userDetails
     */
    public UserDetails createUser(Utilisateur cerbereUser) {
        User user = new User();
        user.setLogin(cerbereUser.getMel());
        user.setEmail(cerbereUser.getMel());
        user.setLastname(cerbereUser.getNom());
        user.setFirstname(cerbereUser.getPrenom());
        user.setDisabled(false);
        user.setPasswordHash(FAKE_HASH);

        // Maj des dates
        LocalDateTime now = LocalDateTime.now();
        user.setDateCreation(now);
        user.setDateModification(now);

        creationUserObjetRight(user, cerbereUser);

        // Je finis par renvoyer le user en rechargeant ses droits et son compte depuis la base
        return loadUserByUsername(user.getLogin());
    }

    /**
     * Mise à jour d'un utilisateur en base à partir des informations reçues de CERBERE
     *
     * @return l'objet userDetails
     */
    public UserDetails majUser(User user, Utilisateur cerbereUser){
        user.setDateModification(LocalDateTime.now());
        user.setLastname(cerbereUser.getNom());
        user.setFirstname(cerbereUser.getPrenom());
        // On delete pour update

        userObjetRightService.deleteByIdUser(user.getId());
        // On recreer le tout

        creationUserObjetRight(user, cerbereUser);
        // Je finis par renvoyer le user en rechargeant ses droits et son compte depuis la base
        return loadUserByUsername(user.getLogin());
    }


    private void creationUserObjetRight(final User user, final Utilisateur cerbereUser){
        ProfilsUtilisateur profil = ProfilsUtilisateur.VISITEUR_PUBLIC;
        for (Profil p : cerbereUser.getHabilitation().getTousProfils()) {
            ProfilsUtilisateur pu = ProfilsUtilisateur.getByCode(p.getNom().toUpperCase());
            if (pu != null && pu.getOrder() > profil.getOrder()) {
                profil = pu;
            }
        }

        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setCode(profil.name().toLowerCase());
        role.setId(profil.getId());
        roles.add(role);
        // Save du user
        user.setRoles(roles);
        userRepository.save(user);

        if (profil == ProfilsUtilisateur.ADMIN ||
                profil == ProfilsUtilisateur.ADMIN_TECH ||
                profil == ProfilsUtilisateur.ACCOMPAGNATEUR) {
            creationUserObjetRightAdmin(user);
        } else {
            // On a besoin de l'id de l'utilisateur si il est nouveau.
            for (Profil p : cerbereUser.getHabilitation().getTousProfils()) {
                String restrictions = p.getRestriction();
                if (restrictions != null) {
                    creationUserObjetRight(user, restrictions);
                }
            }
        }
        logCerbereResponse(user, cerbereUser);
    }


    private void creationUserObjetRightAdmin(final User user) {
        UserObjetRight userObjetRight = new UserObjetRight();
        userObjetRight.setIdUser(user.getId());
        userObjetRight.setIdObjet(-1L);
        userObjetRight.setTypeObjet(TYPE_OBJET.ACTION.getCode());
        userObjetRight.setCodeRole(TYPE_OBJET.ACTION.getCode() + "_ALL");
        userObjetRightService.save(userObjetRight);
        userObjetRight = new UserObjetRight();
        userObjetRight.setIdUser(user.getId());
        userObjetRight.setIdObjet(-1L);
        userObjetRight.setTypeObjet(TYPE_OBJET.ECOCITE.getCode());
        userObjetRight.setCodeRole(TYPE_OBJET.ECOCITE.getCode() + "_ALL");
        userObjetRightService.save(userObjetRight);
    }


    private void creationUserObjetRight(final User user, final String restrictions) {
        String[] listDroitObjet = restrictions.split(",");
        for (String droitSurObjet : listDroitObjet) {
            droitSurObjet = droitSurObjet.trim();
            if (droitSurObjet.startsWith("A_")) {
                createRightAction(user, droitSurObjet.substring(2));
            } else if (droitSurObjet.startsWith("E_")) {
                createRightEcocite(user, droitSurObjet.substring(2));
            } else if (droitSurObjet.startsWith("R_")) {
                Region region = regionService.findBySiren(droitSurObjet.substring(2));
                ecociteService.findByIdRegion(region.getId())
                        .forEach(e -> createRightEcocite(user, e.getId()));
            }
        }
    }

    private void createRightAction(User user, String numeroAction) {
        UserObjetRight userObjetRight = new UserObjetRight();
        Action action = actionService.findByNumero(numeroAction);
        if (action == null) {
            return;
        }
        userObjetRight.setIdUser(user.getId());
        userObjetRight.setIdObjet(action.getId());
        userObjetRight.setTypeObjet(TYPE_OBJET.ACTION.getCode());
        userObjetRight.setCodeRole(TYPE_OBJET.ACTION.getCode() + "_" + action.getId());
        userObjetRightService.save(userObjetRight);
    }

    private void createRightEcocite(User user, String objectSiren) {
        Ecocite ecocite = ecociteService.findBySiren(objectSiren);
        if (ecocite != null) {
            createRightEcocite(user, ecocite.getId());
        }
    }

    private void createRightEcocite(User user, Long idEcocite) {
        UserObjetRight userObjetRight = new UserObjetRight();
        userObjetRight.setIdUser(user.getId());
        userObjetRight.setIdObjet(idEcocite);
        userObjetRight.setTypeObjet(TYPE_OBJET.ECOCITE.getCode());
        userObjetRight.setCodeRole(TYPE_OBJET.ECOCITE.getCode() + "_" + idEcocite);
        userObjetRightService.save(userObjetRight);
        List<Action> actionsFromEcocite = actionService.findAllByListeEcocite(Collections.singletonList(idEcocite));
        for(Action action : actionsFromEcocite){
            createRightAction(user, action.getNumeroAction());
        }
    }

    private void logCerbereResponse(User user, Utilisateur cerbereUser) {
        UserDetailLog detailLog = new UserDetailLog();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            detailLog.setResponsecerbere(ow.writeValueAsString(cerbereUser));
        } catch (JsonProcessingException e) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(String.format("Cerbere: Une erreur est survenue en tentant de sauvegarder la réponse cerbere en base: %s", cerbereUser.toString()));
            }
            detailLog.setResponsecerbere("Erreur lors de la conversion JSON");
        } finally {
            detailLog.setDateCreation(LocalDateTime.now());
            detailLog.setIdUser(user.getId());
        }
        userDetailLogService.save(detailLog);
    }
}
