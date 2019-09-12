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

import com.efficacity.explorateurecocites.beans.model.UserDetailLog;
import com.efficacity.explorateurecocites.beans.repository.UserDetailLogRepository;
import isotope.commons.services.CrudEntityService;
import org.springframework.stereotype.Service;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 * <p>
 * Date de génération : 30/04/2018
 */
@Service
public class UserDetailLogService extends CrudEntityService<UserDetailLogRepository, UserDetailLog, Long> {
    public UserDetailLogService(UserDetailLogRepository repository) {
        super(repository);
    }

    public UserDetailLog save(final UserDetailLog detailLog) {
        return repository.save(detailLog);
    }
}
