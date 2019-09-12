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

package com.efficacity.explorateurecocites.ui.bo.service.exportCSV;

import com.efficacity.explorateurecocites.beans.exportBean.*;
import com.efficacity.explorateurecocites.beans.service.ActionService;
import com.efficacity.explorateurecocites.beans.service.EcociteService;
import com.efficacity.explorateurecocites.ui.bo.service.RightUserService;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import isotope.commons.exceptions.InternalServerErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExportCSVIndicateurService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExportCSVIndicateurService.class);

    @Autowired
    ExportCSVService exportServ;

    @Autowired
    EcociteService ecociteServ;

    @Autowired
    ActionService actionServ;

    @Autowired
    ZipService zipService;

    @Autowired
    RightUserService rightUserService;


    public List<byte[]> indicateurFilesByteStream() {
        List<IndicateurOnlyExportCSVBean> indicateurs = exportServ.getIndicateurOnlyExportData();
        List<IndicateurObjectifExportCSVBean> objectifs = new ArrayList<>();
        List<IndicateurDomaineExportCSVBean> domaines = new ArrayList<>();
        List<IndicateurActionExportCSVBean> actions = new ArrayList<>();
        List<IndicateurEcociteExportCSVBean> ecocites = new ArrayList<>();

        for (IndicateurOnlyExportCSVBean indicateur : indicateurs){
            if(indicateur!=null) {
                if (!indicateur.getIdIndicateur().equals("")) {
                    Long idIndicateur = Long.parseLong(indicateur.getIdIndicateur());
                    objectifs.addAll(exportServ.getIndicateurObjectifExportData(idIndicateur));
                    domaines.addAll(exportServ.getIndicateurDomaineExportData(idIndicateur));
                    actions.addAll(exportServ.getIndicateurActionExportData(idIndicateur));
                    ecocites.addAll(exportServ.getIndicateurEcociteExportData(idIndicateur));
                }
            }
        }
        try {
            Writer indicateurWriter = new StringWriter();
            Writer objectifWriter = new StringWriter();
            Writer domaineWriter = new StringWriter();
            Writer actionWriter = new StringWriter();
            Writer ecociteWriter = new StringWriter();
            //BEAN TO CSV : On Ecrit les informations contenues dans les objets CSV des listes dans des StringWriters.
            //MAPPING STRATEGY = Ordre des colonnes définit dans les ExportBeans

            ColumnPositionMappingStrategy<IndicateurOnlyExportCSVBean> strategyIndicateur = IndicateurOnlyExportCSVBean.getIndicateurOnlyMappingStrategy();
            StatefulBeanToCsvBuilder<IndicateurOnlyExportCSVBean> indicateurBeanToCsv = new StatefulBeanToCsvBuilder<IndicateurOnlyExportCSVBean>(indicateurWriter).withMappingStrategy(strategyIndicateur);
            ColumnPositionMappingStrategy<IndicateurObjectifExportCSVBean> strategyObjectif = IndicateurObjectifExportCSVBean.getIndicateurObjectifMappingStrategy();
            StatefulBeanToCsvBuilder<IndicateurObjectifExportCSVBean> objectifBeanToCsv = new StatefulBeanToCsvBuilder<IndicateurObjectifExportCSVBean>(objectifWriter).withMappingStrategy(strategyObjectif);
            ColumnPositionMappingStrategy<IndicateurDomaineExportCSVBean> strategyDomaine = IndicateurDomaineExportCSVBean.getIndicateurDomaineMappingStrategy();
            StatefulBeanToCsvBuilder<IndicateurDomaineExportCSVBean> domaineBeanToCsv = new StatefulBeanToCsvBuilder<IndicateurDomaineExportCSVBean>(domaineWriter).withMappingStrategy(strategyDomaine);
            ColumnPositionMappingStrategy<IndicateurActionExportCSVBean> strategyAction = IndicateurActionExportCSVBean.getIndicateurActionMappingStrategy();
            StatefulBeanToCsvBuilder<IndicateurActionExportCSVBean> actionBeanToCsv = new StatefulBeanToCsvBuilder<IndicateurActionExportCSVBean>(actionWriter).withMappingStrategy(strategyAction);
            ColumnPositionMappingStrategy<IndicateurEcociteExportCSVBean> strategyEcocite= IndicateurEcociteExportCSVBean.getIndicateurEcociteMappingStrategy();
            StatefulBeanToCsvBuilder<IndicateurEcociteExportCSVBean> ecociteBeanToCsv = new StatefulBeanToCsvBuilder<IndicateurEcociteExportCSVBean>(ecociteWriter).withMappingStrategy(strategyEcocite);

            indicateurBeanToCsv.build().write(indicateurs);
            objectifBeanToCsv.build().write(objectifs);
            domaineBeanToCsv.build().write(domaines);
            actionBeanToCsv.build().write(actions);
            ecociteBeanToCsv.build().write(ecocites);

            indicateurWriter.close();
            objectifWriter.close();
            domaineWriter.close();
            actionWriter.close();
            ecociteWriter.close();
            List<byte[]> contents = new ArrayList<>();
            contents.add(indicateurWriter.toString().getBytes("Cp1252"));
            contents.add(objectifWriter.toString().getBytes("Cp1252"));
            contents.add(domaineWriter.toString().getBytes("Cp1252"));
            contents.add(actionWriter.toString().getBytes("Cp1252"));
            contents.add(ecociteWriter.toString().getBytes("Cp1252"));

            return contents;
        }
        catch (IOException e ){
            LOGGER.error("input/output Exception : "+e.getMessage());
            throw new InternalServerErrorException("input/output Exception");
        }
        catch(CsvRequiredFieldEmptyException e){
            LOGGER.error("CSV Required Empty Field Exception : "+e.getMessage());
            throw new InternalServerErrorException("CSV Required Empty Field Exception");
        }
        catch (CsvDataTypeMismatchException e){
            LOGGER.error("CSV Data mismatch Exception : "+e.getMessage());
            throw new InternalServerErrorException("CSV Data mismatch Exception");
        }
        catch(NullPointerException e){
            LOGGER.error("Null Pointer Exception : "+e.getMessage());
            throw new InternalServerErrorException("Null Pointer Exception");
        }
        catch(Exception e){
            LOGGER.error("Exception : "+e.getMessage());
            throw new InternalServerErrorException("Exception");
        }
    }
}
