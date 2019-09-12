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
