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

package com.efficacity.explorateurecocites.ui.bo.service.reporting;

import com.efficacity.explorateurecocites.beans.biz.IndicateurBean;
import com.efficacity.explorateurecocites.beans.biz.reporting.IndicateurReport;
import com.efficacity.explorateurecocites.beans.model.AssoIndicateurObjectif;
import com.efficacity.explorateurecocites.beans.model.EtiquetteFinalite;
import com.efficacity.explorateurecocites.beans.service.AssoIndicateurObjectifService;
import com.efficacity.explorateurecocites.beans.service.EtiquetteFinaliteService;
import isotope.commons.exceptions.InternalServerErrorException;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.ui.bo.utils.ReportingHelper.*;

@Service
public class IndicateurRapportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndicateurRapportService.class);


    private final EtiquetteFinaliteService etiquetteFinaliteService;
    private final AssoIndicateurObjectifService assoIndicateurObjectifService;


    @Value(value = "classpath:templates/bo/odt/indicateurs.odt")
    private Resource templateIndicateur;

    @Autowired
    public IndicateurRapportService(final EtiquetteFinaliteService etiquetteFinaliteService, final AssoIndicateurObjectifService assoIndicateurObjectifService) {
        this.etiquetteFinaliteService = etiquetteFinaliteService;
        this.assoIndicateurObjectifService = assoIndicateurObjectifService;
    }

    public IndicateurReport copyFrom(IndicateurBean bean) {
        List<EtiquetteFinalite> etq = etiquetteFinaliteService.findAll(assoIndicateurObjectifService.getListByIndicateur(bean.getId())
                .stream()
                .map(AssoIndicateurObjectif::getIdObjectif).collect(Collectors.toList()));
        return new IndicateurReport(bean, etq.stream().map(EtiquetteFinalite::getLibelle).collect(Collectors.toList()));
    }

    public byte[] generateIndicateurReport(final IndicateurBean indicateur) {
        try (TextDocument document = TextDocument.loadDocument(templateIndicateur.getInputStream())) {
            IndicateurReport indicateurReport = copyFrom(indicateur);
            List<Table> tables = document.getTableList();
            if (!tables.isEmpty()) {
                Table table = tables.get(0);
                addCellContent(emptyCellContent(table.getCellByPosition(1, 0)), indicateurReport.getNomCourt(), PART_FONT);
                addCellContent(emptyCellContent(table.getCellByPosition(1, 1)), indicateurReport.getNom());
                addCellContent(emptyCellContent(table.getCellByPosition(1, 2)), indicateurReport.getNature());
                addCellContent(table.getCellByPosition(1, 2), indicateurReport.getEchelle());
                addCellContent(emptyCellContent(table.getCellByPosition(1, 3)), indicateurReport.getDefinition());
                addCellContent(emptyCellContent(table.getCellByPosition(1, 4)), indicateurReport.getMethodeCalcul());
                addCellContent(emptyCellContent(table.getCellByPosition(1, 5)), indicateurReport.getUnites());
                addCellContent(emptyCellContent(table.getCellByPosition(1, 6)), indicateurReport.getPostesCalcul());
                addCellContent(emptyCellContent(table.getCellByPosition(1, 7)), indicateurReport.getSourceDonnees());
                addCellContent(emptyCellContent(table.getCellByPosition(1, 8)), indicateurReport.getObjectifs());
                addCellContent(emptyCellContent(table.getCellByPosition(1, 9)), indicateurReport.getOrigines());
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            return out.toByteArray();
        } catch (Exception e) {
            LOGGER.error("Erreur pendant la génération du rapport d'indicateur " + (indicateur != null ? String.valueOf(indicateur.getId()) : "-1"), e);
            throw new InternalServerErrorException();
        }
    }
}
