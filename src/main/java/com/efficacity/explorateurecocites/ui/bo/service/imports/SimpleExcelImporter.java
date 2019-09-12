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

package com.efficacity.explorateurecocites.ui.bo.service.imports;

import com.efficacity.explorateurecocites.beans.biz.json.ImportResponse;
import com.efficacity.explorateurecocites.ui.bo.service.imports.exceptions.CellException;
import com.efficacity.explorateurecocites.ui.bo.service.imports.exceptions.ExcelImportException;
import com.efficacity.explorateurecocites.ui.bo.service.imports.exceptions.RowException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

public class SimpleExcelImporter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleExcelImporter.class);


    public static final int NOACTION = 0;
    public static final int CREATED = 1;
    public static final int UPDATED = 2;

    /**
     * La fonction appelée pour chaque ligne lue.
     */
    private final LineProcessor processLine;

    /**
     * La fonction exécutée avant l'import.
     */
    private PreUpdate beforeUpdate;

    /**
     * La fonction exécutée après l'import.
     */
    private PreUpdate afterUpdate;

    /**
     * Le nombre de lignes à ignorer lors d'un import.
     */
    private int skipLinesStart = 1;

    /**
     * Le nombre de lignes à ignorer à la fin.
     */
    private int skipLinesEnd = 1;

    public SimpleExcelImporter(final LineProcessor process) {
        this.processLine = process;
    }

    public ImportResponse upload(final Sheet worksheet, final ImportResponse rapport) {
        LOGGER.info("{}", "L'import LAGON a d\u00e9marr\u00e9.");
        if (beforeUpdate != null) {
            try {
                beforeUpdate.execute(worksheet, rapport);
            } catch (Exception e) {
                rapport.addMessage(e);
            }
        }
        for (int i = worksheet.getFirstRowNum() + skipLinesStart, l = worksheet.getLastRowNum() - skipLinesEnd; i <= l; ++i) {
            Row row = worksheet.getRow(i);
            if(row != null){
                try {
                    int result = processLine.execute(row);
                    if (result == CREATED) {
                        rapport.lineCreated();
                    } else if (result == UPDATED) {
                        rapport.lineUpdated();
                    }else if (result == NOACTION) {
                        rapport.lineUntouched();
                    }
                } catch (ExcelImportException e) {
                    rapport.setFail(e);
                    LOGGER.info("Erreur lors de l'import d'un fichier LAGON : {}", e.getMessage());
                } catch (Exception e) {
                    RowException ex = new RowException(i + 1, e);
                    LOGGER.info("Erreur lors de l'import d'un fichier LAGON : {}", ex.getMessage());
                    rapport.setFail(ex);
                }
            }
        }
        if (afterUpdate != null) {
            try {
                afterUpdate.execute(worksheet, rapport);
            } catch (Exception e) {
                rapport.addMessage(e);
            }
        }
        if (rapport.successful()) {
            LOGGER.info("L'import a r\u00e9ussi : {} affaires cr\u00e9es\n{} affaires mises \u00e0 jours\n{} affaires identiques \u00e0 celles en base\n", rapport.getLinesCreated(), rapport.getLinesUpdated(), rapport.getLinesUntouched());
        } else {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("L'import a echou\u00e9 {}", rapport.getMessages().stream().collect(Collectors.joining("\n\t")));
            }
        }
        return rapport;
    }

    @FunctionalInterface
    public interface PreUpdate {
        void execute(final Sheet worksheet, final ImportResponse rapport);
    }

    @FunctionalInterface
    public interface LineProcessor {
        int execute(final Row row) throws CellException;
    }

    public PreUpdate getBeforeUpdate() {
        return beforeUpdate;
    }

    public SimpleExcelImporter setBeforeUpdate(final PreUpdate beforeUpdate) {
        this.beforeUpdate = beforeUpdate;
        return this;
    }

    public PreUpdate getAfterUpdate() {
        return afterUpdate;
    }

    public SimpleExcelImporter setAfterUpdate(final PreUpdate afterUpdate) {
        this.afterUpdate = afterUpdate;
        return this;
    }

    public int getSkipLinesStart() {
        return skipLinesStart;
    }

    public SimpleExcelImporter setSkipLinesStart(final int skipLinesStart) {
        this.skipLinesStart = skipLinesStart;
        return this;
    }

    public int getSkipLinesEnd() {
        return skipLinesEnd;
    }

    public SimpleExcelImporter setSkipLinesEnd(final int skipLinesEnd) {
        this.skipLinesEnd = skipLinesEnd;
        return this;
    }
}
