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

package com.efficacity.explorateurecocites.beans.service;

import com.efficacity.explorateurecocites.beans.model.UserObjetRight;
import com.efficacity.explorateurecocites.beans.repository.UserObjetRightRepository;
import isotope.commons.services.CrudEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 *
 * Date de génération : 21/03/2018
 */
@Service
public class UserObjetRightService extends CrudEntityService<UserObjetRightRepository, UserObjetRight, Long> {
    public UserObjetRightService(UserObjetRightRepository repository) {
    super(repository);
  }


    public void save(UserObjetRight userObjetRight) {
        repository.save(userObjetRight);
    }

    public List<UserObjetRight>  findAllByIdUser(Long IdUser) {
        return repository.findAllByIdUser(IdUser);
    }

    @Transactional
    public void deleteByIdUser(Long IdUser) {
        repository.deleteAllByIdUser(IdUser);
    }
}
