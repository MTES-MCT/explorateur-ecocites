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

import com.efficacity.explorateurecocites.beans.exportBean.*;
import com.efficacity.explorateurecocites.beans.service.ActionService;
import com.efficacity.explorateurecocites.beans.service.EcociteService;
import com.efficacity.explorateurecocites.ui.bo.service.RightUserService;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
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
public class ExportCSVEcociteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExportCSVEcociteService.class);
    public static final String CP_1252 = "Cp1252";

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


    public List<byte[]> ecociteFilesByteStream() {
        List<EcociteExportCSVBean> ecocites = exportServ.getEcocitesExportData();
        List<QuestionnaireExportCSVBean> questionnaires= new ArrayList<>();
        List<ObjectifExportCSVBean> objectifs= new ArrayList<>();
        List<IndicateurExportCSVBean> indicateurs= new ArrayList<>();
        List<ContactExportCSVBean> contacts= new ArrayList<>();
        for (EcociteExportCSVBean ecocite : ecocites) {
            if (ecocite != null && !ecocite.getIdEcocite().equals("")) {
                Long idEcocite = Long.parseLong(ecocite.getIdEcocite());
                objectifs.addAll(exportServ.getObjectifExportData(idEcocite, TYPE_OBJET.ECOCITE));
                questionnaires.addAll(exportServ.getQuestionnaireExportData(idEcocite, TYPE_OBJET.ECOCITE));
                indicateurs.addAll(exportServ.getIndicateurExportData(idEcocite, TYPE_OBJET.ECOCITE));
                contacts.addAll(exportServ.getContactExportData(idEcocite, TYPE_OBJET.ECOCITE));
            }
        }
        try {
            Writer ecociteWriter = new StringWriter();
            Writer objectifWriter = new StringWriter();
            Writer questionnaireWriter = new StringWriter();
            Writer indicateurWriter = new StringWriter();
            Writer contactWriter = new StringWriter();
            //BEAN TO CSV : On Ecrit les informations contenues dans les objets CSV des listes dans des StringWriters.
            //MAPPING STRATEGY = Ordre des colonnes définit dans les ExportBeans
            ColumnPositionMappingStrategy<EcociteExportCSVBean> strategyEcocite = EcociteExportCSVBean.getEcociteMappingStrategy();
            StatefulBeanToCsvBuilder<EcociteExportCSVBean> ecociteBeanToCsv = new StatefulBeanToCsvBuilder<EcociteExportCSVBean>(ecociteWriter).withMappingStrategy(strategyEcocite);
            ColumnPositionMappingStrategy<ObjectifExportCSVBean> strategyObjectif = ObjectifExportCSVBean.getObjectifMappingStrategyForEcocite();
            StatefulBeanToCsvBuilder<ObjectifExportCSVBean> objectifBeanToCsv = new StatefulBeanToCsvBuilder<ObjectifExportCSVBean>(objectifWriter).withMappingStrategy(strategyObjectif);
            ColumnPositionMappingStrategy<QuestionnaireExportCSVBean> strategyQuestionnaire = QuestionnaireExportCSVBean.getQuestionnaireMappingStrategyForEcocite();
            StatefulBeanToCsvBuilder<QuestionnaireExportCSVBean> questionnaireBeanToCsv = new StatefulBeanToCsvBuilder<QuestionnaireExportCSVBean>(questionnaireWriter).withMappingStrategy(strategyQuestionnaire);
            ColumnPositionMappingStrategy<IndicateurExportCSVBean> strategyIndicateur = IndicateurExportCSVBean.getContactMappingStrategyForEcocite();
            StatefulBeanToCsvBuilder<IndicateurExportCSVBean> indicateurBeanToCsv = new StatefulBeanToCsvBuilder<IndicateurExportCSVBean>(indicateurWriter).withMappingStrategy(strategyIndicateur);
            ColumnPositionMappingStrategy<ContactExportCSVBean> strategyContact = ContactExportCSVBean.getContactMappingStrategyForEcocite();
            StatefulBeanToCsvBuilder<ContactExportCSVBean> contactBeanToCsv = new StatefulBeanToCsvBuilder<ContactExportCSVBean>(contactWriter).withMappingStrategy(strategyContact);

            ecociteBeanToCsv.build().write(ecocites);
            objectifBeanToCsv.build().write(objectifs);
            questionnaireBeanToCsv.build().write(questionnaires);
            indicateurBeanToCsv.build().write(indicateurs);
            contactBeanToCsv.build().write(contacts);

            ecociteWriter.close();
            objectifWriter.close();
            questionnaireWriter.close();
            indicateurWriter.close();
            contactWriter.close();

            List<byte[]> contents = new ArrayList<>();
            contents.add(ecociteWriter.toString().getBytes(CP_1252));
            contents.add(objectifWriter.toString().getBytes(CP_1252));
            contents.add(contactWriter.toString().getBytes(CP_1252));
            contents.add(questionnaireWriter.toString().getBytes(CP_1252));
            contents.add(indicateurWriter.toString().getBytes(CP_1252));
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
