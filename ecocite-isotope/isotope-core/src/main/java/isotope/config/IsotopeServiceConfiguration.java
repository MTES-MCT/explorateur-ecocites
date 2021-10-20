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

package isotope.config;


import isotope.modules.cm.menu.repository.MenuRepository;
import isotope.modules.cm.menu.service.MenuEntryService;
import isotope.modules.cm.menu.service.MenuService;
import isotope.modules.cm.menu.service.impl.MenuServiceImpl;
import isotope.modules.user.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by qletel on 19/05/2016.
 */
@Configuration
public class IsotopeServiceConfiguration {

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserService getUserService() {
        return new UserServiceImpl();
    }

    @Bean
    public GroupService getGroupService() {
        return new GroupServiceImpl();
    }

    @Bean
    public MenuService getMenuService(MenuRepository repository, FunctionService functionService, MenuEntryService menuEntryService){
        return new MenuServiceImpl(repository, functionService, menuEntryService);
    }


}
