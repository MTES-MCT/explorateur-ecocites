/*
 * l'État, ministère chargé du logement
 * Copyright (C) 2019 IpsoSenso
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.efficacity.explorateurecocites.beans.specification;

import com.efficacity.explorateurecocites.beans.model.LibelleFo;
import com.efficacity.explorateurecocites.beans.model.LibelleFo_;
import com.efficacity.explorateurecocites.utils.enumeration.LIBELLE_FO_CODE;
import com.efficacity.explorateurecocites.utils.enumeration.LIBELLE_FO_TYPE;
import org.springframework.data.jpa.domain.Specification;

public class LibelleFoSpecifications {

    // Hide public constructor
    private LibelleFoSpecifications() {}

    public static Specification<LibelleFo> withType(LIBELLE_FO_TYPE type) {
        return (root, query, cb) -> cb.equal(root.get(LibelleFo_.type), type.code);
    }

    public static Specification<LibelleFo> withCode(LIBELLE_FO_CODE code) {
        return (root, query, cb) -> cb.equal(root.get(LibelleFo_.code), code.code);
    }
    public static Specification<LibelleFo> withCode(String code) {
        return (root, query, cb) -> cb.equal(root.get(LibelleFo_.code), code);
    }

}
