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

package com.efficacity.explorateurecocites.beans.biz.json;

import com.efficacity.explorateurecocites.ui.bo.service.imports.exceptions.ExcelImportException;

import java.util.ArrayList;
import java.util.List;

public class ImportResponse {
    private Boolean success;
    private final List<String> messages;
    private Long linesUpdated;
    private Long linesCreated;
    private Long linesUntouched;

    public ImportResponse() {
        linesUpdated = 0L;
        linesCreated = 0L;
        linesUntouched = 0L;
        this.success = true;
        this.messages = new ArrayList<>();
    }

    public ImportResponse setFail(String reason) {
        success = false;
        messages.add(reason);
        return this;
    }

    public ImportResponse addMessage(String msg) {
        messages.add(msg);
        return this;
    }

    public ImportResponse addMessage(Throwable throwable) {
        messages.add(throwable.getMessage());
        return this;
    }

    public ImportResponse lineCreated() {
        linesCreated++;
        return this;
    }

    public ImportResponse lineUpdated() {
        linesUpdated++;
        return this;
    }

    public ImportResponse lineUntouched() {
        linesUntouched++;
        return this;
    }

    public Boolean successful() {
        return success;
    }

    public Boolean failed() {
        return !success;
    }

    public List<String> getMessages() {
        return messages;
    }

    public Long getLinesUpdated() {
        return linesUpdated;
    }

    public Long getLinesCreated() {
        return linesCreated;
    }

    public Long getLinesUntouched() {
        return linesUntouched;
    }

    public void setFail(final ExcelImportException e) {
        setFail(e.getMessage());
    }
}
