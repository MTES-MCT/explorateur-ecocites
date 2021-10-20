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

package com.efficacity.explorateurecocites.ui.bo.utils;

import com.efficacity.explorateurecocites.beans.biz.reporting.action.ActionReport;
import com.efficacity.explorateurecocites.beans.biz.reporting.ecocite.EcociteReport;
import com.efficacity.explorateurecocites.beans.biz.reporting.misc.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.odftoolkit.odfdom.type.Color;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.common.field.Fields;
import org.odftoolkit.simple.common.field.PageNumberField;
import org.odftoolkit.simple.common.navigation.*;
import org.odftoolkit.simple.draw.FrameRectangle;
import org.odftoolkit.simple.style.Font;
import org.odftoolkit.simple.style.NumberFormat;
import org.odftoolkit.simple.style.StyleTypeDefinitions;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;
import org.odftoolkit.simple.text.Paragraph;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public class ReportingHelper {
    private ReportingHelper() {}

    public static final String EMPTY = "Non renseigné";
    private static final String FONT_NAME = "Arial";
    public static final String BULLET_POINT = "\u2022";
    public static final Color EFFICACITY_GREEN = new Color("#579E1C");
    public static final Color EFFICACITY_GREY = new Color("#808080");

    public static final String COMMENTAIRE = "Commentaire :";

    public static final Font REGULAR_FONT = new Font(FONT_NAME, StyleTypeDefinitions.FontStyle.REGULAR, 11);
    public static final Font ITALIC_FONT = new Font(FONT_NAME, StyleTypeDefinitions.FontStyle.ITALIC, 11);
    public static final Font BOLD_FONT = new Font(FONT_NAME, StyleTypeDefinitions.FontStyle.BOLD, 11);
    public static final Font PAGE_HEADER_FONT = new Font(FONT_NAME, StyleTypeDefinitions.FontStyle.BOLD, 16, EFFICACITY_GREEN);
    public static final Font PAGE_SUB_HEADER_FONT = new Font(FONT_NAME, StyleTypeDefinitions.FontStyle.BOLD, 14, EFFICACITY_GREY);
    public static final Font TITLE_FONT = new Font(FONT_NAME, StyleTypeDefinitions.FontStyle.BOLD, 14, EFFICACITY_GREY);
    public static final Font PART_FONT = new Font(FONT_NAME, StyleTypeDefinitions.FontStyle.BOLD, 14, EFFICACITY_GREEN);
    public static final Font SUB_TITLE_FONT = new Font(FONT_NAME, StyleTypeDefinitions.FontStyle.BOLD, 11, EFFICACITY_GREEN);
    public static final Font SUB_SUB_TITLE_FONT = new Font(FONT_NAME, StyleTypeDefinitions.FontStyle.BOLD, 11, EFFICACITY_GREY);

    public static final FrameRectangle PRESENTATION_IMAGE = new FrameRectangle(0, 0, 11, 6, StyleTypeDefinitions.SupportedLinearMeasure.CM);

    public static Cell centerCellContent(final Cell cell) {
        cell.setVerticalAlignment(StyleTypeDefinitions.VerticalAlignmentType.MIDDLE);
        cell.setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
        return cell;
    }

    public static Cell rightAlignCellContent(final Cell cell) {
        cell.setVerticalAlignment(StyleTypeDefinitions.VerticalAlignmentType.MIDDLE);
        cell.setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.RIGHT);
        return cell;
    }

    public static Cell emptyCellContent(final Cell cell) {
        cell.removeContent();
        return cell;
    }

    public static Cell addCellContent(final Cell cell, final CharSequence content) {
        return addCellContent(cell, content, REGULAR_FONT);
    }

    public static Cell addCellContent(final Cell cell, final List<? extends CharSequence> content) {
        return addCellContent(cell, content, REGULAR_FONT);
    }

    public static Cell addCellContent(final Cell cell, final List<? extends CharSequence> content, final Font font) {
        addCellContent(cell, content.stream().collect(Collectors.joining("\n")), font);
        return cell;
    }

    public static Cell addCellContent(final Cell cell, final CharSequence content, final Font font) {
        cell.addParagraph(content.toString()).setFont(font);
        return cell;
    }

    public static Cell addCellContent(final Cell cell, final URI uri, final FrameRectangle rectangle) {
        cell.setImage(uri).setRectangle(rectangle);
        return cell;
    }

    public static TextDocument addTitle(final TextDocument document, final CharSequence content) {
        document.addParagraph(content.toString()).setFont(TITLE_FONT);
        return document;
    }

    public static TextDocument addPart(final TextDocument document, final CharSequence content) {
        document.addParagraph(content.toString()).setFont(PART_FONT);
        return document;
    }

    public static TextDocument addSubTitle(final TextDocument document, final CharSequence content) {
        document.addParagraph(content.toString()).setFont(SUB_TITLE_FONT);
        return document;
    }

    public static TextDocument addPageHeader(final TextDocument document, final ActionReport action, final CharSequence title) {
        addPageHeader(document, action.getNom());
        addLineBreak(addPageSubHeader(document, action.getNomEcocite()));
        addLineBreak(addTitle(document, title));
        return document;
    }

    public static TextDocument addPageHeader(final TextDocument document, final EcociteReport ecocite, final CharSequence title) {
        addPageHeader(document, ecocite.getNom());
        addLineBreak(addPageSubHeader(document, ecocite.getNomRegion()));
        addLineBreak(addTitle(document, title));
        return document;
    }

    public static TextDocument addPageHeader(final TextDocument document, final CharSequence header) {
        Paragraph p = document.addParagraph(header.toString());
        p.setFont(PAGE_HEADER_FONT);
        p.setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
        return document;
    }

    public static TextDocument addPageSubHeader(final TextDocument document, final CharSequence subHeader) {
        Paragraph p = document.addParagraph(subHeader.toString());
        p.setFont(PAGE_SUB_HEADER_FONT);
        p.setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
        return document;
    }

    public static TextDocument addSubSubTitle(final TextDocument document, final CharSequence content) {
        document.addParagraph(content.toString()).setFont(SUB_SUB_TITLE_FONT);
        return document;
    }

    public static TextDocument addContent(final TextDocument document, final List<? extends CharSequence> content, final CharSequence placeHolder) {
        return addContent(document, content, placeHolder, REGULAR_FONT);
    }

    public static TextDocument addContent(final TextDocument document, final List<? extends CharSequence> content, final CharSequence placeHolder, final Font font) {
        if (content == null || content.isEmpty()) {
            return addContent(document, placeHolder, font);
        }
        return addContent(document, content.stream().collect(Collectors.joining("\n")), font);
    }

    public static TextDocument addListWithBulletPoint(final TextDocument document, final List<? extends CharSequence> content, final String placeHolder, final Font font, final Integer indentationLevel, final String bulletPoint) {
        if (content == null || content.isEmpty()) {
            if  (placeHolder == null || placeHolder.isEmpty()) {
                return document;
            }
            return addContent(document, placeHolder, ITALIC_FONT);
        }
        StringBuilder delimiter = new StringBuilder("\n");
        StringBuilder prefix = new StringBuilder("");
        for (int i = 0; i < indentationLevel; i++) {
            delimiter.append("\t");
            prefix.append("\t");
        }
        if (bulletPoint != null && !bulletPoint.isEmpty()) {
            prefix.append(bulletPoint);
            delimiter.append(bulletPoint);
            prefix.append(" ");
            delimiter.append(" ");
        }
        return addContent(document, content.stream().collect(Collectors.joining(delimiter, prefix, "")), font);
    }

    public static TextDocument addContent(final TextDocument document, final CharSequence content) {
        return addContent(document, content, REGULAR_FONT);
    }

    public static TextDocument addContent(final TextDocument document, final CharSequence content, final Font font) {
        document.addParagraph(content.toString()).setFont(font);
        return document;
    }

    public static TextDocument addContent(final TextDocument document, final CharSequence content, final Font font,
                                          final StyleTypeDefinitions.HorizontalAlignmentType align) {
        Paragraph p = document.addParagraph(content.toString());
        p.setFont(font);
        p.setHorizontalAlignment(align);
        return document;
    }

    public static TextDocument addLineBreak(final TextDocument document) {
        document.addParagraph("");
        return document;
    }

    public static TextDocument addPageBreak(final TextDocument document) {
        document.addPageBreak();
        return document;
    }

    public static TextDocument addIndicateurList(final TextDocument document, final CharSequence title,
                                                 final List<IndicateurMesureReport> indicateurs) {
        if (!indicateurs.isEmpty()) {
            addSubTitle(document, title);
            for (IndicateurMesureReport indic : indicateurs) {
                addContent(document, BULLET_POINT + " " + indic.getNomCourt(), BOLD_FONT);
                addContent(document, "Poste de calcul : " + indic.getPosteCalcul(), ITALIC_FONT);
                addLineBreak(addContent(document, "Unité : " + indic.getUnite(), ITALIC_FONT));
                addTableCibleMesure(document, "Cibles :", indic.getCommentaireCible(), "Pas de cibles renseignées", indic.getCibles());
                addTableCibleMesure(document, "Mesures :", indic.getCommentaireMesure(), "Pas de mesures renseignées", indic.getMesures());
            }
        }
        return document;
    }

    public static TextDocument addTableCibleMesure(final TextDocument document, final CharSequence title, final CharSequence comment,
                                                   final CharSequence placeHolder, final List<CibleMesureIndicateurReport> cibleMesureList) {
        addContent(document, title);
        if (cibleMesureList != null && !cibleMesureList.isEmpty()) {
            Table table = document.addTable(cibleMesureList.size(), 2);
            for (int i = 0; i < cibleMesureList.size(); i++) {
                centerCellContent(addCellContent(table.getCellByPosition(0, i), cibleMesureList.get(i).getValue()));
                centerCellContent(addCellContent(table.getCellByPosition(1, i), cibleMesureList.get(i).getDate()));
            }
            table.getColumnByIndex(1).setWidth(50);
            addLineBreak(document);
        } else if (placeHolder != null && placeHolder.length() > 0){
            addLineBreak(addContent(document, placeHolder, ITALIC_FONT));
        } else {
            addLineBreak(document);
        }
        if (comment != null && comment.length() > 0) {
            addContent(document, "Commentaire (dont sources des données utilisées) :", ITALIC_FONT);
            addLineBreak(addContent(document, comment, ITALIC_FONT));
        }
        return document;
    }

    public static TextDocument addTableInnovation(final TextDocument document, final CharSequence placeHolder, final List<QuestionReponseReport> questions) {
        if (!questions.isEmpty()) {
            Table table = document.addTable(questions.size(), 2);
            for (int i = 0; i < questions.size(); i++) {
                addCellContent(table.getCellByPosition(0, i), questions.get(i).getQuestion(), BOLD_FONT);
                centerCellContent(addCellContent(table.getCellByPosition(1, i), questions.get(i).getReponse()));
            }
            table.getColumnByIndex(1).setWidth(50);
            addLineBreak(document);
        } else if (placeHolder != null && placeHolder.length() > 0){
            addLineBreak(addContent(document, placeHolder, ITALIC_FONT));
        }
        return document;
    }

    @SuppressWarnings("Duplicates")
    public static void replaceBaliseWith(TextDocument document, String pattern, TextDocument part) throws InvalidNavigationException {
        TextNavigation navigation = new TextNavigation(pattern, document);
        if (navigation.hasNext()) {
            Selection selection = navigation.nextSelection();
            if (selection.getClass().isAssignableFrom(TextSelection.class)) {
                TextSelection textSelection = (TextSelection) selection;
                textSelection.replaceWith(part);
            } else if (selection.getClass().isAssignableFrom(CellSelection.class)) {
                CellSelection cellSelection = (CellSelection) selection;
                cellSelection.replaceWith(part);
            }
        }
    }

    @SuppressWarnings("Duplicates")
    public static void replaceAllBaliseWith(TextDocument document, String pattern, String part) throws InvalidNavigationException {
        part = part == null ? "" : part;
        TextNavigation navigation = new TextNavigation(pattern, document);
        while (navigation.hasNext()) {
            Selection selection = navigation.nextSelection();
            if (selection.getClass().isAssignableFrom(TextSelection.class)) {
                TextSelection textSelection = (TextSelection) selection;
                textSelection.replaceWith(part);
            } else if (selection.getClass().isAssignableFrom(CellSelection.class)) {
                CellSelection cellSelection = (CellSelection) selection;
                cellSelection.replaceWith(part);
            }
        }
    }

    @SuppressWarnings("Duplicates")
    public static void replaceBaliseWith(TextDocument document, String pattern, String part) throws InvalidNavigationException {
        part = part == null ? "" : part;
        TextNavigation navigation = new TextNavigation(pattern, document);
        if (navigation.hasNext()) {
            Selection selection = navigation.nextSelection();
            if (selection.getClass().isAssignableFrom(TextSelection.class)) {
                TextSelection textSelection = (TextSelection) selection;
                textSelection.replaceWith(part);
            } else if (selection.getClass().isAssignableFrom(CellSelection.class)) {
                CellSelection cellSelection = (CellSelection) selection;
                cellSelection.replaceWith(part);
            }
        }
    }

    @SuppressWarnings("Duplicates")
    public static void addContactTable(TextDocument document, List<ContactReport> contacts) {
        if (contacts == null || contacts.isEmpty()) {
            addContent(document, "Pas de contacts renseignés");
        } else {
            Table table = Table.newTable(document, contacts.size() + 1, 4);
            centerCellContent(addCellContent(table.getCellByPosition(0, 0), "Nom", BOLD_FONT));
            centerCellContent(addCellContent(table.getCellByPosition(1, 0), "Prénom", BOLD_FONT));
            centerCellContent(addCellContent(table.getCellByPosition(2, 0), "Entité", BOLD_FONT));
            centerCellContent(addCellContent(table.getCellByPosition(3, 0), "Mail", BOLD_FONT));
            for (int i = 0; i < contacts.size(); i++) {
                Row row = table.getRowByIndex(i + 1);
                centerCellContent(addCellContent(row.getCellByIndex(0), contacts.get(i).getNom()));
                centerCellContent(addCellContent(row.getCellByIndex(1), contacts.get(i).getPrenom()));
                centerCellContent(addCellContent(row.getCellByIndex(2), contacts.get(i).getEntite()));
                centerCellContent(addCellContent(row.getCellByIndex(3), contacts.get(i).getMail()));
            }
        }
    }

    public static TextDocument addQuestionnaire(TextDocument document, QuestionnaireReport report) {
        addPart(document, report.getName());
        addLineBreak(document);
        if (report.getThematiques() == null || report.getThematiques().isEmpty()) {
            addContent(document, "Aucune réponse n'a été fournie pour ce questionnaire.");
        } else {
            for (ThematiqueReport t : report.getThematiques()) {
                addLineBreak(addContent(document, t.getName(), SUB_TITLE_FONT));
                for (QuestionMultipleReponsesReport q : t.getQuestions()) {
                    addContent(document, "\t\u2022 " + q.getQuestion(), BOLD_FONT);
                    for (String r : q.getReponses()) {
                        addContent(document, r);
                    }
                    addLineBreak(document);
                }
            }
        }
        return document;
    }

    public static String toSimpleTexte(String html) {
        if (html == null)  {
            return "";
        }
        Document doc = Jsoup.parse(html);
        return new HtmlToPlainText().getPlainText(doc);
    }

    public static String getContentOrPlaceHolder(String content) {
        return getContentOrPlaceHolder(content, "");
    }

    public static String getContentOrPlaceHolder(String content, CharSequence suffix) {
        if (content == null || content.trim().isEmpty()) {
            return EMPTY;
        }
        return content + suffix;
    }

    public static String getContentOrPlaceHolder(Number content) {
        return getContentOrPlaceHolder(content, "");
    }

    public static String getContentOrPlaceHolder(Number content, CharSequence suffix) {
        if (content == null) {
            return EMPTY;
        }
        return String.valueOf(content) + suffix;
    }

    public static void addPageCountFooter(final TextDocument document) {
        Cell cell = document.getFooter().addTable(1, 3).getCellByPosition(1, 0);
        Paragraph p = cell.addParagraph("");
        p.setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
        PageNumberField numberField = Fields.createCurrentPageNumberField(
                p.getOdfElement()
        );
        numberField.setNumberFormat(NumberFormat.HINDU_ARABIC_NUMBER);
        numberField.setDisplayPage(PageNumberField.DisplayType.CURRENT_PAGE);
    }
}
