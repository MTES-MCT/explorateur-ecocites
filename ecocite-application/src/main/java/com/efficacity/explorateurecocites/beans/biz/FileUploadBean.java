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

package com.efficacity.explorateurecocites.beans.biz;

import com.efficacity.explorateurecocites.beans.model.FileUpload;
import com.efficacity.explorateurecocites.utils.enumeration.FILE_TYPE;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.Objects;

public class FileUploadBean implements Comparable<FileUploadBean> {
    @JsonIgnore
    private FileUpload to;

    public FileUploadBean(final FileUpload to) {
        this.to = to;
    }

    public String getUrl() {
        return "/files/" + this.to.getName();
    }

    public String getOriginalName() {
        return this.to.getOriginalName();
    }
    public String getLegende() {
        return this.to.getLegende();
    }
    public String getLieu() {
        return this.to.getLieu();
    }
    public String getDescription() {
        return this.to.getDescription();
    }
    public String getCopyright() {
        return this.to.getCopyright();
    }

    public Long getNumerisation() {
        if (this.to.getNumerisation() != null && this.to.getNumerisation() >= 0) {
            return this.to.getNumerisation();
        }
        return Long.parseLong("-1");
    }
    public Boolean getAutorisationpresse() {return this.to.getAutorisationpresse();}
    public Boolean getAutorisationrevue() {
        return this.to.getAutorisationrevue();
    }
    public Boolean getAutorisationexpo() {
        return this.to.getAutorisationexpo();
    }
    public Boolean getAutorisationinternet() {
        return this.to.getAutorisationinternet();
    }
    public Boolean getAutorisationsiteee() {
        return this.to.getAutorisationsiteee();
    }
    public Boolean getAutorisationsupportmm() {
        return this.to.getAutorisationsupportmm();
    }

    public LocalDateTime getUploadDate() {
        return this.to.getDateupload();
    }

    public Long getId() {
        return to.getId();
    }

    public FILE_TYPE getTypeEnum() {
        return FILE_TYPE.getByCode(to.getType());
    }

    public String getName() {
        return to.getName();
    }

    public Long getIdObject() {
        return to.getIdObject();
    }

    public FileUpload getTo() {
        return to;
    }

    public String getTitle() {
        return to.getTitle();
    }


    @Override
    public int compareTo(FileUploadBean o2) {
        if (Objects.equals(this.getTypeEnum(), o2.getTypeEnum())) {
            if(this.getUploadDate()!=null && o2.getUploadDate()!=null ){
                if (this.getNumerisation() != null && o2.getNumerisation() != null && this.getNumerisation() >= 0 && o2.getNumerisation() >= 0) {
                    if (this.getNumerisation() < o2.getNumerisation()) {
                        return 1;
                    } else if (this.getNumerisation() > o2.getNumerisation()) {
                        return -1;
                    } else {
                        if (this.getUploadDate().compareTo(o2.getUploadDate()) > 0) {
                            return -1;
                        } else {
                            return 1;
                        }
                    }
                } else {
                    if (this.getUploadDate().compareTo(o2.getUploadDate()) > 0) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            }
            else{
                return 0;
            }
        } else if (this.getTypeEnum() == FILE_TYPE.ACTION_IMAGE_PRINCIPALE) {
            return 1;
        } else if (this.getTypeEnum() == FILE_TYPE.ECOCITE_IMAGE_PRINCIPALE) {
            return 1;

        } else {
            return -1;
        }
    }

    @Override
    public int hashCode() {
        return to.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof FileUploadBean)) {
            return false;
        }

        FileUploadBean fileUploadBean = (FileUploadBean)obj;

        long primaryKey = fileUploadBean.getId();

        return (getId() == primaryKey);

    }
}
