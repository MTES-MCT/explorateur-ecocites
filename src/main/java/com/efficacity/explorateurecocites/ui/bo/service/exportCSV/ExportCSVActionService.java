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
public class ExportCSVActionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExportCSVActionService.class);

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


    public List<byte[]> actionFilesByteStream(List<Long> listeEcociteId) {
        List<ActionExportCSVBean> actions;
        if(listeEcociteId!=null){
            actions=exportServ.getActionsExportData(listeEcociteId);
        }
        else{
            actions=exportServ.getActionsExportData();
        }
        List<BusinessExportCSVBean> business = new ArrayList<>();
        List<QuestionnaireExportCSVBean> questionnaires = new ArrayList<>();
        List<NiveauExportCSVBean> niveaux = new ArrayList<>();
        List<DomaineExportCSVBean> domaines = new ArrayList<>();
        List<ObjectifExportCSVBean> objectifs = new ArrayList<>();
        List<IngenierieExportCSVBean> ingenieries = new ArrayList<>();
        List<ContactExportCSVBean> contacts = new ArrayList<>();
        List<IndicateurExportCSVBean> indicateurs = new ArrayList<>();

        for (ActionExportCSVBean action : actions) {
            if (action != null) {
                if (!action.getIdAction().equals("")) {
                    Long idAction = Long.parseLong(action.getIdAction());
                    business.addAll(exportServ.getBusinessExportData(idAction));
                    contacts.addAll(exportServ.getContactExportData(idAction, TYPE_OBJET.ACTION));
                    domaines.addAll(exportServ.getDomaineExportData(idAction));
                    objectifs.addAll(exportServ.getObjectifExportData(idAction, TYPE_OBJET.ACTION));
                    ingenieries.addAll(exportServ.getIngenierieExportData(idAction));
                    niveaux.addAll(exportServ.getNiveauExportData(idAction));
                    questionnaires.addAll(exportServ.getQuestionnaireExportData(idAction, TYPE_OBJET.ACTION));
                    indicateurs.addAll(exportServ.getIndicateurExportData(idAction, TYPE_OBJET.ACTION));
                }
            }
        }
        try {
            Writer actionWriter = new StringWriter();
            Writer businessWriter = new StringWriter();
            Writer contactWriter = new StringWriter();
            Writer objectifWriter = new StringWriter();
            Writer domaineWriter = new StringWriter();
            Writer ingenierieWriter = new StringWriter();
            Writer niveauWriter = new StringWriter();
            Writer questionnaireWriter = new StringWriter();
            Writer indicateurWriter = new StringWriter();

            //BEAN TO CSV : On Ecrit les informations contenues dans les objets CSV des listes dans des StringWriters.
            //MAPPING STRATEGY = Ordre des colonnes définit dans les ExportBeans
            ColumnPositionMappingStrategy<ActionExportCSVBean> strategyAction = ActionExportCSVBean.getActionMappingStrategy();
            StatefulBeanToCsvBuilder<ActionExportCSVBean> actionBeanToCsv = new StatefulBeanToCsvBuilder<ActionExportCSVBean>(actionWriter).withMappingStrategy(strategyAction);
            ColumnPositionMappingStrategy<BusinessExportCSVBean> strategyBusiness = BusinessExportCSVBean.getBusinessMappingStrategy();
            StatefulBeanToCsvBuilder<BusinessExportCSVBean> businessBeanToCsv = new StatefulBeanToCsvBuilder<BusinessExportCSVBean>(businessWriter).withMappingStrategy(strategyBusiness);
            ColumnPositionMappingStrategy<ContactExportCSVBean> strategyContact = ContactExportCSVBean.getContactMappingStrategyForAction();
            StatefulBeanToCsvBuilder<ContactExportCSVBean> contactBeanToCsv = new StatefulBeanToCsvBuilder<ContactExportCSVBean>(contactWriter).withMappingStrategy(strategyContact);
            ColumnPositionMappingStrategy<DomaineExportCSVBean> strategyDomaine = DomaineExportCSVBean.getDomaineMappingStrategy();
            StatefulBeanToCsvBuilder<DomaineExportCSVBean> domaineBeanToCsv = new StatefulBeanToCsvBuilder<DomaineExportCSVBean>(domaineWriter).withMappingStrategy(strategyDomaine);
            ColumnPositionMappingStrategy<ObjectifExportCSVBean> strategyObjectif = ObjectifExportCSVBean.getObjectifMappingStrategyForAction();
            StatefulBeanToCsvBuilder<ObjectifExportCSVBean> objectifBeanToCsv = new StatefulBeanToCsvBuilder<ObjectifExportCSVBean>(objectifWriter).withMappingStrategy(strategyObjectif);
            ColumnPositionMappingStrategy<IngenierieExportCSVBean> strategyIngenierie = IngenierieExportCSVBean.getIngenierieMappingStrategy();
            StatefulBeanToCsvBuilder<IngenierieExportCSVBean> ingenierieBeanToCsv = new StatefulBeanToCsvBuilder<IngenierieExportCSVBean>(ingenierieWriter).withMappingStrategy(strategyIngenierie);
            ColumnPositionMappingStrategy<NiveauExportCSVBean> strategyNiveau = NiveauExportCSVBean.getNiveauMappingStrategy();
            StatefulBeanToCsvBuilder<NiveauExportCSVBean> niveauBeanToCsv = new StatefulBeanToCsvBuilder<NiveauExportCSVBean>(niveauWriter).withMappingStrategy(strategyNiveau);
            ColumnPositionMappingStrategy<QuestionnaireExportCSVBean> strategyQuestionnaire = QuestionnaireExportCSVBean.getQuestionnaireMappingStrategyForAction();
            StatefulBeanToCsvBuilder<QuestionnaireExportCSVBean> questionnaireBeanToCsv = new StatefulBeanToCsvBuilder<QuestionnaireExportCSVBean>(questionnaireWriter).withMappingStrategy(strategyQuestionnaire);
            ColumnPositionMappingStrategy<IndicateurExportCSVBean> strategyIndicateur = IndicateurExportCSVBean.getContactMappingStrategyForAction();
            StatefulBeanToCsvBuilder<IndicateurExportCSVBean> indicateurBeanToCsv = new StatefulBeanToCsvBuilder<IndicateurExportCSVBean>(indicateurWriter).withMappingStrategy(strategyIndicateur);

            actionBeanToCsv.build().write(actions);
            businessBeanToCsv.build().write(business);
            contactBeanToCsv.build().write(contacts);
            domaineBeanToCsv.build().write(domaines);
            objectifBeanToCsv.build().write(objectifs);
            ingenierieBeanToCsv.build().write(ingenieries);
            niveauBeanToCsv.build().write(niveaux);
            questionnaireBeanToCsv.build().write(questionnaires);
            indicateurBeanToCsv.build().write(indicateurs);

            actionWriter.close();
            businessWriter.close();
            contactWriter.close();
            domaineWriter.close();
            objectifWriter.close();
            ingenierieWriter.close();
            niveauWriter.close();
            questionnaireWriter.close();
            indicateurWriter.close();

            List<byte[]> contents = new ArrayList<>();
            contents.add(actionWriter.toString().getBytes("Cp1252"));
            contents.add(businessWriter.toString().getBytes("Cp1252"));
            contents.add(contactWriter.toString().getBytes("Cp1252"));
            contents.add(domaineWriter.toString().getBytes("Cp1252"));
            contents.add(objectifWriter.toString().getBytes("Cp1252"));
            contents.add(ingenierieWriter.toString().getBytes("Cp1252"));
            contents.add(niveauWriter.toString().getBytes("Cp1252"));
            contents.add(questionnaireWriter.toString().getBytes("Cp1252"));
            contents.add(indicateurWriter.toString().getBytes("Cp1252"));
            return contents;
        }
        catch (IOException e ){
            LOGGER.error("input/output Exception");
            throw new InternalServerErrorException("input/output Exception");
        }
        catch(CsvRequiredFieldEmptyException e){
            LOGGER.error("CSV Required Empty Field Exception");
            throw new InternalServerErrorException("CSV Required Empty Field Exception");
        }
        catch (CsvDataTypeMismatchException e){
            LOGGER.error("CSV Data mismatch Exception");
            throw new InternalServerErrorException("CSV Data mismatch Exception\"");
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
