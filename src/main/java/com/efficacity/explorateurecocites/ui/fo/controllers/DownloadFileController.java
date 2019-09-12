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

package com.efficacity.explorateurecocites.ui.fo.controllers;

import com.efficacity.explorateurecocites.beans.biz.FileUploadBean;
import com.efficacity.explorateurecocites.beans.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/files")
public class DownloadFileController {

    @Value("${efficacity.explorateurecocites.path.logs}")
    private String logsPath;
    @Value("${efficacity.explorateurecocites.path.dump}")
    private String dumpPath;

    private FileUploadService fileUploadService;

    @Autowired
    public DownloadFileController(final FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    /**
     * On a besoin du <code>:.+</code> à la fin de la variable de Path car sinon l'extension du fichier est tronqué
     * @param fileName Le nom du fichier a recuperer
     * @return
     */
    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        FileUploadBean fileUpload = fileUploadService.findFileByName(fileName);
        if (fileUpload != null) {
            Path path = fileUploadService.getPath(fileUpload.getIdObject(), fileUpload.getTypeEnum(), fileUpload.getName());
            try {
                File file = path.toFile();
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                return ResponseEntity.ok()
                        .contentLength(file.length())
                        .contentType(MediaType.parseMediaType("application/octet-stream"))
                        .header("content-disposition", "attachment; filename=\"" + fileUpload.getOriginalName() + "\"")
                        .body(resource);
            } catch (IOException e) {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Permet de récupérer le fichier dans le repo des logs par son nom
     * @param fileName
     * @return
     */
    @GetMapping("/log/{fileName:.+}")
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAuthority('ROLE_ADMIN_TECH')")
    public ResponseEntity<Resource> getFileLog(@PathVariable String fileName) {
        if (fileName != null) {
            Path path = Paths.get(logsPath, fileName);
            try {
                File file = path.toFile();
                if(file != null) {
                    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                    return ResponseEntity.ok()
                            .contentType(MediaType.parseMediaType("application/octet-stream"))
                            .contentLength(file.length())
                            .body(resource);
                }
            } catch (IOException e) {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Permet de récupérer le fichier dans le repo des logs par son nom
     * @param fileName
     * @return
     */
    @GetMapping("/dump/{fileName:.+}")
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR') OR hasAuthority('ROLE_ADMIN_TECH')")
    public ResponseEntity<Resource> getFileDump(@PathVariable String fileName) {
        if (fileName != null) {
            Path path = Paths.get(dumpPath, fileName);
            try {
                File file = path.toFile();
                if(file != null) {
                    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                    return ResponseEntity.ok()
                            .contentLength(file.length())
                            .contentType(MediaType.parseMediaType("application/octet-stream"))
                            .body(resource);
                }
            } catch (IOException e) {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

}
