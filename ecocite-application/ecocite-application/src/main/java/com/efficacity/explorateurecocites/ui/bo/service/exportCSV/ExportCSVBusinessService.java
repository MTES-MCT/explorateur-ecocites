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

package com.efficacity.explorateurecocites.ui.bo.service.exportCSV;

import com.efficacity.explorateurecocites.beans.exportBean.BusinessOnlyExportCSVBean;
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
import java.util.LinkedList;
import java.util.List;

@Service
public class ExportCSVBusinessService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExportCSVBusinessService.class);

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


    public List<byte[]> businessFilesByteStream() {
        List<BusinessOnlyExportCSVBean> business = exportServ.getBusinessOnlyExportData();
        try {
            Writer businessWriter = new StringWriter();
            //BEAN TO CSV : On Ecrit les informations contenues dans les objets CSV des listes dans des StringWriters.
            //MAPPING STRATEGY = Ordre des colonnes définit dans les ExportBeans
            ColumnPositionMappingStrategy<BusinessOnlyExportCSVBean> strategyBusiness = BusinessOnlyExportCSVBean.getBusinessOnlyMappingStrategy();
            StatefulBeanToCsvBuilder<BusinessOnlyExportCSVBean> businessBeanToCsv = new StatefulBeanToCsvBuilder<BusinessOnlyExportCSVBean>(businessWriter).withMappingStrategy(strategyBusiness);
            businessBeanToCsv.build().write(business);
            businessWriter.close();
            List<byte[]> contents = new LinkedList<>();
            contents.add(businessWriter.toString().getBytes("Cp1252"));
            return contents;
        }
        catch (IOException e ){
            LOGGER.error("input/output Exception : {}", e.getMessage());
            throw new InternalServerErrorException("input/output Exception");
        }
        catch(CsvRequiredFieldEmptyException e){
            LOGGER.error("CSV Required Empty Field Exception : {}", e.getMessage());
            throw new InternalServerErrorException("CSV Required Empty Field Exception");
        }
        catch (CsvDataTypeMismatchException e){
            LOGGER.error("CSV Data mismatch Exception : {}", e.getMessage());
            throw new InternalServerErrorException("CSV Data mismatch Exception");
        }
        catch(NullPointerException e){
            LOGGER.error("Null Pointer Exception : {}", e.getMessage());
            throw new InternalServerErrorException("Null Pointer Exception");
        }
        catch(Exception e){
            LOGGER.error("Exception : {}", e.getMessage());
            throw new InternalServerErrorException("Exception");
        }
    }
}
