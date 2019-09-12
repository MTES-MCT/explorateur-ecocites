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

package com.efficacity.explorateurecocites.ui.fo.utils;

import com.efficacity.explorateurecocites.beans.biz.ActionBean;
import com.efficacity.explorateurecocites.beans.biz.EcociteBean;

public class UrlUtils {

    public static String getUrlAction(ActionBean action) {
        return ConstFO.CONSTANTE_URL_ACTION + action.getId();
    }

    public static String getUrlEcocite(EcociteBean ecocite) {
        return ConstFO.CONSTANTE_URL_ECOCITE + ecocite.getId();
    }

    public static String getUrlIdAction(String IdAction) {
        return ConstFO.CONSTANTE_URL_ACTION + IdAction;
    }

    public static String getUrlIdEcocite(String idEcocite) {
        return ConstFO.CONSTANTE_URL_ECOCITE + idEcocite;
    }

}

