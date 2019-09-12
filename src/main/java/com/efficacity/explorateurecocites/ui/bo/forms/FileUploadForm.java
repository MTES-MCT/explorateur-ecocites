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

package com.efficacity.explorateurecocites.ui.bo.forms;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadForm {
    private MultipartFile file;
    private String title;
    private String legende;
    private String lieu;
    private String description;
    private String copyright;
    private boolean autorisationPresse;
    private boolean autorisationRevue;
    private boolean autorisationExpo;
    private boolean autorisationInternet;
    private boolean autorisationSiteEE;
    private boolean autorisationSupportMM;
    private Long numerisation;
    private boolean fileChanged;
    private Long idFile;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(final MultipartFile file) {
        this.file = file;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getLegende() {
        return legende;
    }

    public void setLegende(String legende) {
        this.legende = legende;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public boolean isAutorisationPresse() {
        return autorisationPresse;
    }

    public void setAutorisationPresse(boolean autorisationPresse) {
        this.autorisationPresse = autorisationPresse;
    }

    public boolean isAutorisationRevue() {
        return autorisationRevue;
    }

    public void setAutorisationRevue(boolean autorisationRevue) {
        this.autorisationRevue = autorisationRevue;
    }

    public boolean isAutorisationExpo() {
        return autorisationExpo;
    }

    public void setAutorisationExpo(boolean autorisationExpo) {
        this.autorisationExpo = autorisationExpo;
    }

    public boolean isAutorisationInternet() {
        return autorisationInternet;
    }

    public void setAutorisationInternet(boolean autorisationInternet) {
        this.autorisationInternet = autorisationInternet;
    }

    public boolean isAutorisationSiteEE() {
        return autorisationSiteEE;
    }

    public void setAutorisationSiteEE(boolean autorisationSiteEE) {
        this.autorisationSiteEE = autorisationSiteEE;
    }

    public boolean isAutorisationSupportMM() {
        return autorisationSupportMM;
    }

    public void setAutorisationSupportMM(boolean autorisationSupportMM) {
        this.autorisationSupportMM = autorisationSupportMM;
    }

    public Long getNumerisation() {
        return numerisation;
    }

    public void setNumerisation(Long numerisation) {
        this.numerisation = numerisation;
    }

    public boolean isFileChanged() {
        return fileChanged;
    }

    public void setFileChanged(boolean fileChanged) {
        this.fileChanged = fileChanged;
    }

    public Long getIdFile() {
        return idFile;
    }

    public void setIdFile(Long idFile) {
        this.idFile = idFile;
    }
}
