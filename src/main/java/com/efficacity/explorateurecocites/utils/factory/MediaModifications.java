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

package com.efficacity.explorateurecocites.utils.factory;

import com.efficacity.explorateurecocites.ajaris.enums.MediaModificationOriginType;
import com.efficacity.explorateurecocites.ajaris.enums.MediaModificationType;
import com.efficacity.explorateurecocites.beans.model.Action;
import com.efficacity.explorateurecocites.beans.model.Axe;
import com.efficacity.explorateurecocites.beans.model.Ecocite;
import com.efficacity.explorateurecocites.beans.model.MediaModification;
import com.efficacity.explorateurecocites.ajaris.enums.JobStatus;

import java.time.LocalDateTime;

public final class MediaModifications {
    private MediaModifications() {}

    public static MediaModification of(final MediaModificationOriginType originType, final Long id) {
        MediaModification res = new MediaModification();
        res.setIdObject(id);
        res.setNumberTry(0);
        res.setLastModified(LocalDateTime.now());
        res.setStatus(JobStatus.PENDING.code);
        res.setTypeObject(originType.code);
        res.setTypeModification(MediaModificationType.UPDATE.code);
        return res;
    }

    public static MediaModification of(Action action) {
        return of(MediaModificationOriginType.ACTION, action.getId());
    }

    public static MediaModification of(Ecocite ecocite) {
        return of(MediaModificationOriginType.ECOCITE, ecocite.getId());
    }

    public static MediaModification of(final Axe axe) {
        return of(MediaModificationOriginType.AXE, axe.getId());
    }

    public static MediaModification reset(MediaModification modification) {
        modification.setLastModified(LocalDateTime.now());
        modification.setStatus(JobStatus.PENDING.code);
        return modification;
    }

    public static MediaModification complete(MediaModification modification) {
        modification.setLastModified(LocalDateTime.now());
        modification.setStatus(JobStatus.FINISHED.code);
        modification.setNumberTry(0);
        return modification;
    }

    public static MediaModification fail(MediaModification modification) {
        modification.setLastModified(LocalDateTime.now());
        modification.setStatus(JobStatus.ERROR.code);
        modification.setNumberTry(modification.getNumberTry() + 1);
        return modification;
    }
}
