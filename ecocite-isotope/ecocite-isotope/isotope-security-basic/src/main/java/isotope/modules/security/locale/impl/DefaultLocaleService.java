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

package isotope.modules.security.locale.impl;

import isotope.modules.user.model.User;
import isotope.modules.security.locale.ILocaleService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Created by mperalta on 29/11/2016.
 */
@ConditionalOnMissingClass("LocaleService")
@Service
public class DefaultLocaleService implements ILocaleService {
    @Override
    public Locale getUserLocale(User user) {
        return Locale.getDefault();
    }
}
