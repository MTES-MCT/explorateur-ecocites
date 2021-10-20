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

package com.efficacity.explorateurecocites.ui.bo.utils;

import org.junit.jupiter.api.Test;

import static com.efficacity.explorateurecocites.ui.bo.utils.FileUtils.getExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FileUtilsTest {

    @Test
    void testGetExtension() {
        assertEquals("", getExtension("C"));
        assertEquals(".ext", getExtension("C.ext"));
        assertEquals(".ext", getExtension("A/B/C.ext"));
        assertEquals("", getExtension("A/B/C.ext/"));
        assertEquals("", getExtension("A/B/C.ext/.."));
        assertEquals(".bin", getExtension("A/B/C.bin"));
        assertEquals(".hidden", getExtension(".hidden"));
        assertEquals(".dsstore", getExtension("/user/home/.dsstore"));
        assertEquals("", getExtension(".strange."));
        assertEquals(".3", getExtension("1.2.3"));
        assertEquals(".exe", getExtension("C:\\Program Files (x86)\\java\\bin\\javaw.exe"));
    }
}
