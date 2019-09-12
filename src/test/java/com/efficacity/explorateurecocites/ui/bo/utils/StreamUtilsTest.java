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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class StreamUtilsTest {
    private static ArrayList<String> strings;

    @BeforeAll
    static void setUp() {
        strings = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            strings.add("test");
        }
    }

    @Test
    void checkNoLimit() {
        Assertions.assertTrue(StreamUtils.verifyStreamSizeBetweenBoundaries(strings.stream(), 0, Integer.MAX_VALUE), "La taille du stream est bien comprise entre 0 et l'infini, cela devrait renvoyer true");
    }

    @Test
    void checkWithLimitMin() {
        Assertions.assertTrue(StreamUtils.verifyStreamSizeBetweenBoundaries(strings.stream(), 1, Integer.MAX_VALUE), "La taille du stream est bien comprise entre 1 et l'infini, cela devrait renvoyer true");
        Assertions.assertTrue(StreamUtils.verifyStreamSizeBetweenBoundaries(strings.stream(), 3, Integer.MAX_VALUE), "La taille du stream est bien comprise entre 3 et l'infini, cela devrait renvoyer true");
        Assertions.assertFalse(StreamUtils.verifyStreamSizeBetweenBoundaries(strings.stream(), 4, Integer.MAX_VALUE), "La taille du stream n'est pas comprise entre 4 et l'infini, cela devrait renvoyer false");
    }
    @Test
    void checkWithLimitMax() {
        Assertions.assertTrue(StreamUtils.verifyStreamSizeBetweenBoundaries(strings.stream(), 0, 4), "La taille du stream est bien comprise entre 0 et 4, cela devrait renvoyer true");
        Assertions.assertTrue(StreamUtils.verifyStreamSizeBetweenBoundaries(strings.stream(), 0, 3), "La taille du stream est bien compris entre 0 et 3, cela devrait renvoyer true");
        Assertions.assertFalse(StreamUtils.verifyStreamSizeBetweenBoundaries(strings.stream(), 0, 1), "La taille du stream n'est bien compris entre 0 et 1, cela devrait renvoyer false");
    }
}
