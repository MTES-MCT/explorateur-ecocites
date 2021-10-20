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
