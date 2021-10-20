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

import com.efficacity.explorateurecocites.ajaris.enums.MediaModificationType;
import com.efficacity.explorateurecocites.beans.model.MediaModification;
import com.efficacity.explorateurecocites.beans.model.MediaModification_;
import com.efficacity.explorateurecocites.ajaris.enums.MediaModificationOriginType;
import com.efficacity.explorateurecocites.ajaris.enums.JobStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

import static org.springframework.data.jpa.domain.Specification.where;

public class MediaModificationSpecifications {

    // Hide public constructor
    private MediaModificationSpecifications() {}

    public static Specification<MediaModification> notFinished() {
        return where(isErrored()).or(isPending());
    }

    public static Specification<MediaModification> numberTryLessThan(Integer numberTry) {
//        return (root, query, cb) -> cb.lessThan(root.get("numberTry"), numberTry);
        return (root, query, cb) -> cb.lessThan(root.get(MediaModification_.numberTry), numberTry);
    }

    public static Specification<MediaModification> isPending() {
        return (root, query, cb) -> cb.equal(root.get(MediaModification_.status), JobStatus.PENDING.code);
    }

    public static Specification<MediaModification> isErrored() {
        return (root, query, cb) -> cb.equal(root.get(MediaModification_.status), JobStatus.ERROR.code);
    }

    public static Specification<MediaModification> hasStatus(JobStatus jobStatus) {
        return (root, query, cb) -> cb.equal(root.get(MediaModification_.status), jobStatus.code);
    }

    public static Specification<MediaModification> olderThan(LocalDateTime time) {
        return (root, query, cb) -> cb.lessThan(root.get(MediaModification_.lastModified), time);
    }

    public static Specification<MediaModification> hasOriginModification(MediaModificationOriginType typeObject) {
        return (root, query, cb) -> cb.equal(root.get(MediaModification_.typeObject), typeObject.code);
    }

    public static Specification<MediaModification> hasId(Long idObject) {
        return (root, query, cb) -> cb.equal(root.get(MediaModification_.idObject), idObject);
    }

    public static Specification<MediaModification> hasTypeModification(MediaModificationType type) {
        return (root, query, cb) -> cb.equal(root.get(MediaModification_.typeModification), type.code);
    }
}
