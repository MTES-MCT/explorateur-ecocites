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

package com.efficacity.explorateurecocites.beans.specification;

import com.efficacity.explorateurecocites.ajaris.enums.MediaModificationType;
import com.efficacity.explorateurecocites.beans.model.MediaModification;
import com.efficacity.explorateurecocites.beans.model.MediaModification_;
import com.efficacity.explorateurecocites.ajaris.enums.MediaModificationOriginType;
import com.efficacity.explorateurecocites.ajaris.enums.JobStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

import static org.springframework.data.jpa.domain.Specifications.where;

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
