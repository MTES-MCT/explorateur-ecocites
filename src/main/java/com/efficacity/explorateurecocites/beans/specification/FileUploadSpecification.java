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

import com.efficacity.explorateurecocites.beans.model.FileUpload;
import com.efficacity.explorateurecocites.beans.model.FileUpload_;
import com.efficacity.explorateurecocites.utils.enumeration.FILE_TYPE;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.stream.Collectors;

public class FileUploadSpecification {

    public static Specification<FileUpload> hasId(Long id) {
        return (root, query, cb) -> cb.equal(root.get(FileUpload_.id), id);
    }

    public static Specification<FileUpload> hasName(String fileName) {
        return (root, query, cb) -> cb.equal(root.get(FileUpload_.name), fileName);
    }

    public static Specification<FileUpload> hasIdObject(Long idObject) {
        return (root, query, cb) -> cb.equal(root.get(FileUpload_.idObject), idObject);
    }

    public static Specification<FileUpload> belongToAction() {
        return (root, query, cb) -> cb.equal(root.get(FileUpload_.typeObject), TYPE_OBJET.ACTION.getCode());
    }

    public static Specification<FileUpload> belongToEcocite() {
        return (root, query, cb) -> cb.equal(root.get(FileUpload_.typeObject), TYPE_OBJET.ECOCITE.getCode());
    }

    public static Specification<FileUpload> belongToAxe() {
        return (root, query, cb) -> cb.equal(root.get(FileUpload_.typeObject), TYPE_OBJET.AXE.getCode());
    }

    public static Specification<FileUpload> hasType(FILE_TYPE fileType) {
        return (root, query, cb) -> cb.equal(root.get(FileUpload_.type), fileType.getCode());
    }

    public static Specification<FileUpload> hasTypeIn(List<FILE_TYPE> fileTypes) {
        return (root, query, cb) -> root.get(FileUpload_.type).in(fileTypes.stream().map(FILE_TYPE::getCode).collect(Collectors.toList()));
    }

    public static Specification<FileUpload> isVisible() {
        return (root, query, cb) -> cb.or(cb.equal(root.get(FileUpload_.autorisationsiteee), true), cb.isNull(root.get(FileUpload_.autorisationsiteee)));
    }
}
