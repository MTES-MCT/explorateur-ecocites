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

import java.util.Collections;
import java.util.List;

import static com.efficacity.explorateurecocites.ui.bo.utils.FileUtils.*;

public class RG {
    public static class Fichier {
        private Fichier() {}
        public static class FormatAutorise {
            private FormatAutorise() {}
            public static final List<String> DOCUMENTS = Collections.unmodifiableList(List.of(PDF_EXTENSION, ODT_EXTENSION, DOCX_EXTENSION, DOC_EXTENSION, TXT_EXTENSION, JPG_EXTENSION, JPEG_EXTENSION, PNG_EXTENSION, BMP_EXTENSION));
            public static final List<String> DOCUMENTS_MIME_TYPES = Collections.unmodifiableList(List.of(PDF_MIME_TYPE, ODT_MIME_TYPE, DOCX_MIME_TYPE, TIKA_DOCX_MIME_TYPE, DOC_MIME_TYPE, TIKA_DOC_MIME_TYPE, TXT_MIME_TYPE, JPG_MIME_TYPE, PNG_MIME_TYPE, BMP_MIME_TYPE));
            public static final List<String> NOTE_SYNTHESE = Collections.unmodifiableList(List.of(PDF_EXTENSION, ODT_EXTENSION, DOCX_EXTENSION, DOC_EXTENSION, TXT_EXTENSION, JPG_EXTENSION, JPEG_EXTENSION, PNG_EXTENSION, BMP_EXTENSION));
            public static final List<String> NOTE_SYNTHESE_MIME_TYPES = Collections.unmodifiableList(List.of(PDF_MIME_TYPE, ODT_MIME_TYPE, DOCX_MIME_TYPE, TIKA_DOCX_MIME_TYPE, DOC_MIME_TYPE, TIKA_DOC_MIME_TYPE, TXT_MIME_TYPE, JPG_MIME_TYPE, PNG_MIME_TYPE, BMP_MIME_TYPE));
            public static final List<String> IMAGE = Collections.unmodifiableList(List.of(PNG_EXTENSION, JPG_EXTENSION, JPEG_EXTENSION));
            public static final List<String> IMAGE_MIME_TYPES = Collections.unmodifiableList(List.of(PNG_MIME_TYPE, JPG_MIME_TYPE, BMP_MIME_TYPE));
            public static final List<String> PERIMETRE = Collections.unmodifiableList(Collections.singletonList(KML_EXTENSION));
            public static final List<String> PERIMETRE_MIME_TYPES = Collections.unmodifiableList(Collections.singletonList(KML_MIME_TYPE));
        }
    }
    public static class Action {
        private Action() {}
        public static class Fichier {
            private Fichier() {}
            public static final int MAX_SYNTHESE = 3;
            public static final int MAX_DOCUMENTS = 3;
            public static final int MAX_IMAGES_SECONDAIRES = 5;
            public static final int MAX_IMAGE_PRINCIPALE = 1;
            public static final int MAX_PERIMETRES = 1;
        }
        public static final int MIN_PRIMARY_DOMAIN = 1;
        public static final int MAX_PRIMARY_DOMAIN = 1;
        public static final int MIN_SECONDARY_DOMAIN = 0;
        public static final int MAX_SECONDAY_DOMAIN = 5;

        public static final int MIN_INGENIERIE = 1;
        public static final int MAX_INGENIERIE = Integer.MAX_VALUE;

        public static final int MIN_PRIMARY_OBJECTIF = 1;
        public static final int MAX_PRIMARY_OBJECTIF = Integer.MAX_VALUE;
        public static final int MIN_SECONDARY_OBJECTIF = 0;
        public static final int MAX_SECONDAY_OBJECTIF = 5;
        public static final int MIN_THIRD_OBJECTIF = 0;
        public static final int MAX_THIRD_OBJECTIF = 5;
    }
    public class Ecocite {
        private Ecocite() {}
        public class Fichier {
            private Fichier() {}
            public static final int MAX_SYNTHESE = 3;
            public static final int MAX_DOCUMENTS = 3;
            public static final int MAX_IMAGES_SECONDAIRES = 5;
            public static final int MAX_IMAGE_PRINCIPALE = 1;
            public static final int MAX_PERIMETRES_STRATEGIQUES = 1;
            public static final int MAX_PERIMETRES_OPERATIONNELS = 1;
        }
        public static final int MIN_PRIMARY_OBJECTIF = 1;
        public static final int MAX_PRIMARY_OBJECTIF = Integer.MAX_VALUE;
        public static final int MIN_SECONDARY_OBJECTIF = 0;
        public static final int MAX_SECONDAY_OBJECTIF = 5;
        public static final int MIN_THIRD_OBJECTIF = 0;
        public static final int MAX_THIRD_OBJECTIF = 5;
    }
    public class Axe {
        private Axe() {}
        public class Fichier {
            private Fichier() {}
            public static final int MAX_ICON = 1;
        }
    }
}
