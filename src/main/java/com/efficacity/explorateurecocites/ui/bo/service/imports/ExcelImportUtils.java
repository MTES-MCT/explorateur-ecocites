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

import com.efficacity.explorateurecocites.ui.bo.service.imports.exceptions.CellException;
import com.efficacity.explorateurecocites.utils.enumeration.STATUT_FINANCIER_AFFAIRE;
import com.efficacity.explorateurecocites.utils.enumeration.TRANCHE_EXECUTION;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_FINANCEMENT;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Classe utilitaire regroupant des méthodes pour l'import de fichiers Excel.
 * <p>
 * Created by qletel on 02/03/2017.
 */
public class ExcelImportUtils {

    private ExcelImportUtils() {

    }


    public static String getLocationMessage(final Integer rowIndex, final Integer cellIndex, final String message) {
        return "Ligne : " + (rowIndex + 1) + ", Colonne : " + (cellIndex + 1) + ", Message : " + message;
    }

    /**
     * Méthode utilitaire permettant de créer un générateur de {@link CellException} à partir d'une ligne et d'un
     * index de cellule avançant au gré de la lecture de la ligne.
     *
     * @param row       la ligne du fichier Excel
     * @param cellIndex l'index de la cellule
     * @return un générateur d'exception synchronisé sur l'index de la cellule
     */
    public static Function<String, Supplier<CellException>> createExceptionSupplier(Row row, AtomicInteger cellIndex) {
        return message -> () -> new CellException(getLocationMessage(row.getRowNum(), cellIndex.get() - 1, message));
    }


    /**
     * Méthode permettant de récupérer le contenu string d'une cellule
     * Dans le cas d'un copier/coller, on peut se retrouver avec une cellule au format number, on le récupère en string
     *
     * @param row
     * @param index
     * @return
     */
    public static Optional<String> getString(Row row, int index) throws CellException {
        try {
            return Optional.ofNullable(row.getCell(index, MissingCellPolicy.RETURN_BLANK_AS_NULL))
                    .map(cell -> {
                        if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                            return String.valueOf(cell.getNumericCellValue());
                        }
                        return cell.getStringCellValue();
                    });
        } catch (IllegalStateException e) {
            throw new CellException(getLocationMessage(row.getRowNum(), index, "La valeur attendue est un TEXTE"));
        }
    }

    /**
     * Méthode permettant de récupérer le contenu string d'une cellule
     * Dans le cas d'un copier/coller, on peut se retrouver avec une cellule au format number, on le récupère en string
     *
     * @param row
     * @param index
     * @return
     */
    public static Optional<String> getStringTroncateIfNumber(Row row, int index) throws CellException {
        try {
            return Optional.ofNullable(row.getCell(index, MissingCellPolicy.RETURN_BLANK_AS_NULL))
                    .map(cell -> {
                        if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                            return String.valueOf(((Double) cell.getNumericCellValue()).intValue());
                        }
                        return cell.getStringCellValue();
                    });
        } catch (IllegalStateException e) {
            throw new CellException(getLocationMessage(row.getRowNum(), index, "La valeur attendue est un TEXTE"));
        }
    }

    public static boolean getBoolean(Row row, int index) throws CellException {
        try {
            return row.getCell(index).getBooleanCellValue();
        } catch (IllegalStateException e) {
            throw new CellException(getLocationMessage(row.getRowNum(), index, "La valeur attendue est un booléen (VRAI ou FAUX)"));
        }
    }

    public static Optional<Instant> getInstant(Row row, int index) throws CellException {
        try {
            return Optional.ofNullable(row.getCell(index, MissingCellPolicy.RETURN_BLANK_AS_NULL))
                    .map(Cell::getDateCellValue)
                    .map(Date::toInstant);
        } catch (IllegalStateException e) {
            throw new CellException(getLocationMessage(row.getRowNum(), index, e.getMessage()));
        }
    }

    public static Optional<Double> getDouble(Row row, int index) throws CellException {
        try {
            return Optional.of(row.getCell(index).getNumericCellValue());
        } catch (IllegalStateException e) {
            throw new CellException(getLocationMessage(row.getRowNum(), index, e.getMessage()));
        }
    }

    public static Optional<Double> getDoubleOrNull(Row row, int index) throws CellException {
        try {
            return Optional.ofNullable(row.getCell(index, MissingCellPolicy.RETURN_BLANK_AS_NULL))
                    .map(Cell::getNumericCellValue);
        } catch (IllegalStateException e) {
            throw new CellException(getLocationMessage(row.getRowNum(), index, e.getMessage()));
        }
    }

    public static Optional<Integer> getInteger(Row row, int index) throws CellException {
        try {
            return row.getCell(index) != null && !row.getCell(index).getCellTypeEnum().equals(CellType.BLANK) ?
                    Optional.of(row.getCell(index).getNumericCellValue())
                            .map(Double::intValue)
                    : Optional.empty();
        } catch (IllegalStateException | NumberFormatException e) {
            throw new CellException(getLocationMessage(row.getRowNum(), index, e.getMessage()));
        }
    }

    public static Optional<LocalDateTime> getLocalDateTime(final Row row, final int index) throws CellException {
        try {
            return Optional.ofNullable(row.getCell(index, MissingCellPolicy.RETURN_BLANK_AS_NULL))
                    .map(cell -> {
                        if (cell.getCellTypeEnum() == CellType.STRING) {
                            return LocalDate.parse(cell.getStringCellValue(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay();
                        }
                        Date date = cell.getDateCellValue();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        return calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    });
        } catch (IllegalStateException e) {
            throw new CellException(getLocationMessage(row.getRowNum(), index, e.getMessage()));
        }
    }

    public static Optional<String> getTranche(final Row row, final int index) throws CellException {
        String trancheText = getString(row, index).orElse("");
        if (trancheText.length() >= 12) {
            trancheText = trancheText.substring(trancheText.length() - 12, trancheText.length());
        }
        switch (trancheText) {
            case "1ère tranche":
                return Optional.of(TRANCHE_EXECUTION.TRANCHE_1.getCode());
            case "2ème tranche":
                return Optional.of(TRANCHE_EXECUTION.TRANCHE_2.getCode());
            default:
                return Optional.of("");
//                throw new CellException(getLocationMessage(row.getRowNum(), index, "La valeur attendue est une tranche (\"1ère tranche\" ou \"2ème tranche\")"));
        }
    }

    public static Optional<String> getTypeFinancement(final Row row, final int index) throws CellException {
        String financementText = getString(row, index).orElse("");
        switch (financementText) {
            case "Volet 1 : TCSP":
            case "Investissement":
                return Optional.of(TYPE_FINANCEMENT.INVESTISSEMENT.getCode());
            case "Capitaux propres":
                return Optional.of(TYPE_FINANCEMENT.PRISE_PARTICIPATION.getCode());
            case "Ingénierie":
            case "Ing préalable T2 & instrument bat":
            case "Protocoles 2010-2011":
                return Optional.of(TYPE_FINANCEMENT.INGENIERIE.getCode());
            default:
                return Optional.of("");
//                throw new CellException(getLocationMessage(row.getRowNum(), index, "La valeur attendue est une tranche de financement " +
//                "(\"Volet 1 : TCSP\", \"Investissement\", \"Capitaux propres\", \"Ingénierie\", \"Ing préalable T2 & instrument bat\", ou \"Protocoles 2010-2011\")"));
        }
    }

    public static Optional<String> getStatutFinancier(final Row row, final int index) throws CellException {
        String financementText = getString(row, index).orElse("");
        switch (financementText) {
            case "Engagée":
                return Optional.of(STATUT_FINANCIER_AFFAIRE.ENGAGE.getFirstCode());
            case "Contractualisée":
            return Optional.of(STATUT_FINANCIER_AFFAIRE.CONTRACTUALISE.getFirstCode());
            case "Soldée":
            return Optional.of(STATUT_FINANCIER_AFFAIRE.SOLDE.getFirstCode());
            case "Clôturée":
            return Optional.of(STATUT_FINANCIER_AFFAIRE.CLOTURE.getFirstCode());
            case "En cours de paiement":
            return Optional.of(STATUT_FINANCIER_AFFAIRE.EN_COURS_PAIEMENT.getFirstCode());
            default:
                return Optional.of("");
//                throw new CellException(getLocationMessage(row.getRowNum(), index, "La valeur attendue est un statut financier " +
//                "(\"Engagée\", \"Contractualisée\", \"Soldée\", \"Clôturée\", ou \"En cours de paiement\")"));
        }
    }
}
