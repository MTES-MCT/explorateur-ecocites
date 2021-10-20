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

package com.efficacity.explorateurecocites.ui.bo.service.reporting;

import com.efficacity.explorateurecocites.beans.biz.ActionBean;
import com.efficacity.explorateurecocites.beans.biz.EcociteBean;
import com.efficacity.explorateurecocites.beans.biz.EtapeBean;
import com.efficacity.explorateurecocites.beans.biz.reporting.action.*;
import com.efficacity.explorateurecocites.beans.biz.reporting.misc.EtiquettesReport;
import com.efficacity.explorateurecocites.beans.biz.reporting.misc.EvaluationInnovationReport;
import com.efficacity.explorateurecocites.beans.biz.reporting.misc.IndicateurMesureReport;
import com.efficacity.explorateurecocites.beans.biz.reporting.misc.QuestionnaireReport;
import com.efficacity.explorateurecocites.beans.model.*;
import com.efficacity.explorateurecocites.beans.service.*;
import com.efficacity.explorateurecocites.ui.bo.service.QuestionnaireEvaluationService;
import com.efficacity.explorateurecocites.utils.enumeration.ETAPE_ACTION_EDITION;
import com.efficacity.explorateurecocites.utils.enumeration.QUESTIONNAIRE_CODE;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
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
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.ui.bo.utils.ReportingHelper.*;

@Service
public class ActionRapportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActionRapportService.class);

    private final ReportService reportService;
    private final EcociteService ecociteService;
    private final IndicateurService indicateurService;
    private final FileUploadService fileUploadService;
    private final AssoObjetContactService assoObjetContactService;
    private final AxeService axeService;
    private final AssoActionDomainService assoActionDomainService;
    private final AssoActionIngenierieService assoActionIngenierieService;
    private final AssoObjetObjectifService assoObjetObjectifService;
    private final AssoIndicateurObjetService assoIndicateurObjetService;
    private final EtapeService etapeService;
    private final MesureIndicateurService mesureIndicateurService;
    private final CibleIndicateurService cibleIndicateurService;
    private final QuestionnaireEvaluationService questionnaireEvaluationService;
    private final MediaService mediaService;

    @Value(value = "classpath:templates/bo/odt/actions.odt")
    private Resource templateAction;

    @Value(value = "classpath:templates/bo/odt/common/questionnaire.odt")
    private Resource templateQuestionnairePart;



    @Autowired
    public ActionRapportService(final ReportService reportService, final EcociteService ecociteService,
                                final IndicateurService indicateurService, final FileUploadService fileUploadService,
                                final AssoActionDomainService assoActionDomainService, final MesureIndicateurService mesureIndicateurService,
                                final AssoActionIngenierieService assoActionIngenierieService, final AssoObjetObjectifService assoObjetObjectifService,
                                final EtapeService etapeService, final AssoObjetContactService assoObjetContactService,
                                final AssoIndicateurObjetService assoIndicateurObjetService, final QuestionnaireEvaluationService questionnaireEvaluationService,
                                final AxeService axeService, final CibleIndicateurService cibleIndicateurService, final MediaService mediaService) {
        this.reportService = reportService;
        this.ecociteService = ecociteService;
        this.indicateurService = indicateurService;
        this.fileUploadService = fileUploadService;
        this.assoActionDomainService = assoActionDomainService;
        this.mesureIndicateurService = mesureIndicateurService;
        this.assoActionIngenierieService = assoActionIngenierieService;
        this.assoObjetObjectifService = assoObjetObjectifService;
        this.etapeService = etapeService;
        this.assoObjetContactService = assoObjetContactService;
        this.assoIndicateurObjetService = assoIndicateurObjetService;
        this.questionnaireEvaluationService = questionnaireEvaluationService;
        this.axeService = axeService;
        this.cibleIndicateurService = cibleIndicateurService;
        this.mediaService = mediaService;
    }

    public ActionReport copyFrom(ActionBean action) {
        EcociteBean ecocite = ecociteService.findOneEcocite(action.getIdEcocite());
        Axe axe = action.getIdAxe() == null ? null : axeService.findById(action.getIdAxe());
        List<Contact> contacts = new ArrayList<>();
        contacts.addAll(assoObjetContactService.findAllContactPrincipaleForAction(action.getId()));
        contacts.addAll(assoObjetContactService.findAllContactSecondaireForAction(action.getId()));

        File image = mediaService.getMainMediaForActionFile(action);
        FicheIdentite ficheIdentite = new FicheIdentite(action, axe, contacts);
        List<AssoActionDomain> assoActionDomains = assoActionDomainService.getListByAction(action.getId());
        List<AssoObjetObjectif> assoObjetObjectifs = assoObjetObjectifService.getListByAction(action.getId());
        List<AssoActionIngenierie> assoActionIngenieries = assoActionIngenierieService.getListByAction(action.getId());
        List<EtapeBean> etapes = etapeService.getEtapeByIdAction(action.getId());
        EtiquettesReport domaine = new EtiquettesReport(reportService.filterDomain(assoActionDomains, 1),
                reportService.filterDomain(assoActionDomains, 2), reportService.filterDomain(assoActionDomains, 3),
                reportService.getCommentaireEtape(ETAPE_ACTION_EDITION.CATEGORIESATION_DOMAINE, etapes));
        EtiquettesReport objectif = new EtiquettesReport(reportService.filterObjectifs(assoObjetObjectifs, 1),
                reportService.filterObjectifs(assoObjetObjectifs, 2), reportService.filterObjectifs(assoObjetObjectifs, 3),
                reportService.getCommentaireEtape(ETAPE_ACTION_EDITION.CATEGORIESATION_OBJECTIF, etapes));
        EtiquettesReport ingenierie = new EtiquettesReport(reportService.filterIngenierie(assoActionIngenieries, 1),
                reportService.filterIngenierie(assoActionIngenieries, 2), reportService.filterIngenierie(assoActionIngenieries, 3),
                reportService.getCommentaireEtape(ETAPE_ACTION_EDITION.CATEGORIESATION_INGENIERIE, etapes));
        Caracterisation caracterisation = new Caracterisation(action, ingenierie, domaine, objectif);
        List<IndicateurMesureReport> indicateurReports = assoIndicateurObjetService.findAllByAction(action.getId())
                .stream()
                .map(i -> new IndicateurMesureReport(i, indicateurService.findOneIndicateur(i.getIdIndicateur()),
                        cibleIndicateurService.findAllByIdAssoIndicateurObjetOrderByDateSaisieAsc(i.getId()),
                        mesureIndicateurService.findAllByIdAssoIndicateurObjetOrderByDateSaisieAsc(i.getId())))
                .collect(Collectors.toList());
        EvaluationQuantitative quantitative = new EvaluationQuantitative(indicateurReports);
        EvaluationInnovationReport innovation = new EvaluationInnovationReport(action, reportService.getCommentaireEtape(ETAPE_ACTION_EDITION.EVALUATION_INNOVATION, etapes));
        QuestionnaireReport ing = null;
        QuestionnaireReport inv = null;
        switch (action.getTypeFinancementEnum()) {
            case INGENIERIE:
                ing = new QuestionnaireReport(QUESTIONNAIRE_CODE.QUESTIONNAIRE_ACTION_INGENIERIE.getLibelle(),
                        questionnaireEvaluationService.genererQuestionnaire(QUESTIONNAIRE_CODE.QUESTIONNAIRE_ACTION_INGENIERIE.getCode(),
                                action.getId(), TYPE_OBJET.ACTION.getCode()));
                break;
            case INVESTISSEMENT:
            case PRISE_PARTICIPATION:
                inv = new QuestionnaireReport(QUESTIONNAIRE_CODE.QUESTIONNAIRE_ACTION_INVESTISSEMENT.getLibelle(),
                        questionnaireEvaluationService.genererQuestionnaire(QUESTIONNAIRE_CODE.QUESTIONNAIRE_ACTION_INVESTISSEMENT.getCode(),
                                action.getId(), TYPE_OBJET.ACTION.getCode()));
                break;
            case INGENIERIE_INVESTISSEMENT:
            case INGENIERIE_PRISE_PARTICIPATION:
                inv = new QuestionnaireReport(QUESTIONNAIRE_CODE.QUESTIONNAIRE_ACTION_INVESTISSEMENT.getLibelle(),
                        questionnaireEvaluationService.genererQuestionnaire(QUESTIONNAIRE_CODE.QUESTIONNAIRE_ACTION_INVESTISSEMENT.getCode(),
                                action.getId(), TYPE_OBJET.ACTION.getCode()));
                ing = new QuestionnaireReport(QUESTIONNAIRE_CODE.QUESTIONNAIRE_ACTION_INGENIERIE.getLibelle(),
                        questionnaireEvaluationService.genererQuestionnaire(QUESTIONNAIRE_CODE.QUESTIONNAIRE_ACTION_INGENIERIE.getCode(),
                                action.getId(), TYPE_OBJET.ACTION.getCode()));
                break;
        }
        EvaluationQualitative qualitative = new EvaluationQualitative(innovation, ing, inv);
        return new ActionReport(action, ecocite, ficheIdentite, caracterisation, image, quantitative, qualitative);
    }

    private void generatePresentation(TextDocument document, ActionReport action) throws Exception {
        TextDocument presentation = TextDocument.loadDocument(templateAction.getInputStream());
        replaceAllBaliseWith(presentation, "\\[NomAction\\]", action.getNom());
        replaceAllBaliseWith(presentation, "\\[NomEcoCite\\]", action.getNomEcocite());
        replaceBaliseWith(presentation, "\\[Type de financement\\]", action.getIdentite().getTypeFinancement());
        replaceBaliseWith(presentation, "\\[Etat avancement\\]", action.getIdentite().getEtatAvancement());
        replaceBaliseWith(presentation, "\\[Axe\\]", action.getIdentite().getAxePrincipal());
        replaceBaliseWith(presentation, "\\[Tranche\\]", action.getIdentite().getTranche());
        replaceBaliseWith(presentation, "\\[Echelle\\]", action.getIdentite().getEchelle());
        //Ajout de l'image si elle existe, sinon on laisse le placeholder
        if (action.getImage() != null &&
                presentation.getTableList().stream().anyMatch(o -> o.getTableName().equals("presentation"))) {
            Table presentationTable = presentation.getTableByName("presentation");
            presentationTable.getRowByIndex(0).setUseOptimalHeight(true);
            centerCellContent(addCellContent(presentationTable.getCellByPosition(1, 0), action.getImage().toURI(), PRESENTATION_IMAGE));
        }
        if(action.getIdentite().getMaitriseOuvrages().isEmpty()) {
            replaceBaliseWith(presentation, "\\[MaitriseOuvrage\\]", "Pas de maîtrises d'ouvrages renseignées");
        } else {
            replaceBaliseWith(presentation, "\\[MaitriseOuvrage\\]", action.getIdentite().getMaitriseOuvrages().stream().collect(Collectors.joining("\n")));
        }
        replaceBaliseWith(presentation, "\\[DescriptionAction\\]", toSimpleTexte(action.getIdentite().getDescription()));
        document.insertContentFromDocumentBefore(presentation, document.getParagraphByIndex(0, false), true);
        addContactTable(document, action.getIdentite().getContacts());
        addPageBreak(document);
    }

    private void generateCategorisation(TextDocument document, ActionReport action) {
        addPageHeader(document, action, "Caractérisation de l’action");
        if (action.getCaracterisation().isTypeIngenierie()) {
            addSubTitle(document, "Type(s) de mission d’ingénierie");
            addLineBreak(addListWithBulletPoint(document, action.getCaracterisation().getIngenierie().getMajeures(), EMPTY, REGULAR_FONT, 1, BULLET_POINT));
            if (action.getCaracterisation().getIngenierie().getCommentaire() != null &&
                    !action.getCaracterisation().getIngenierie().getCommentaire().isEmpty()) {
                addContent(document, COMMENTAIRE, ITALIC_FONT);
                addLineBreak(addContent(document, action.getCaracterisation().getIngenierie().getCommentaire(), ITALIC_FONT));
            }
        }
        addSubTitle(document, "Domaine(s) d’action");
        addSubSubTitle(document, "Principal");
        addLineBreak(addListWithBulletPoint(document, action.getCaracterisation().getDomaine().getMajeures(), EMPTY, REGULAR_FONT, 1, BULLET_POINT));
        addSubSubTitle(document, "Secondaires");
        addLineBreak(addListWithBulletPoint(document, action.getCaracterisation().getDomaine().getModeres(), EMPTY, REGULAR_FONT, 1, BULLET_POINT));
        if (action.getCaracterisation().getDomaine().getCommentaire() != null &&
                !action.getCaracterisation().getDomaine().getCommentaire().isEmpty()) {
            addContent(document, COMMENTAIRE, ITALIC_FONT);
            addLineBreak(addContent(document, action.getCaracterisation().getDomaine().getCommentaire(), ITALIC_FONT));
        }
        addSubTitle(document, "Objectif(s) ville durable de l’action");
        addSubSubTitle(document, "Majeurs");
        addLineBreak(addListWithBulletPoint(document, action.getCaracterisation().getObjectif().getMajeures(), EMPTY, REGULAR_FONT, 1, BULLET_POINT));
        addSubSubTitle(document, "Modérés");
        addLineBreak(addListWithBulletPoint(document, action.getCaracterisation().getObjectif().getModeres(), EMPTY, REGULAR_FONT, 1, BULLET_POINT));
        addSubSubTitle(document, "Mineurs");
        addLineBreak(addListWithBulletPoint(document, action.getCaracterisation().getObjectif().getMineures(), EMPTY, REGULAR_FONT, 1, BULLET_POINT));
        if (action.getCaracterisation().getObjectif().getCommentaire() != null &&
                !action.getCaracterisation().getObjectif().getCommentaire().isEmpty()) {
            addContent(document, COMMENTAIRE, ITALIC_FONT);
            addLineBreak(addContent(document, action.getCaracterisation().getObjectif().getCommentaire(), ITALIC_FONT));
        }
        addPageBreak(document);
    }

    private void generateEvaluationQuantitative(TextDocument document, ActionReport action) {
        if (action.getCaracterisation().isTypeFinancementOuInvestissement()) {
            addPageHeader(document, action, "Évaluation quantitative de l’action");
            addSubTitle(document, "Liste des indicateur(s) choisi(s)");
            List<String> indics = new ArrayList<>();
            action.getQuantitative().getIndicateurs().getRealisations().stream().map(IndicateurMesureReport::getNomCourt).forEach(indics::add);
            action.getQuantitative().getIndicateurs().getImpacts().stream().map(IndicateurMesureReport::getNomCourt).forEach(indics::add);
            action.getQuantitative().getIndicateurs().getResultats().stream().map(IndicateurMesureReport::getNomCourt).forEach(indics::add);
            addLineBreak(addListWithBulletPoint(document, indics, EMPTY, REGULAR_FONT, 1, BULLET_POINT));
            addIndicateurList(document, "Indicateur(s) de réalisation", action.getQuantitative().getIndicateurs().getRealisations());
            addIndicateurList(document, "Indicateur(s) de résultat", action.getQuantitative().getIndicateurs().getResultats());
            addIndicateurList(document, "Indicateur(s) d’impact", action.getQuantitative().getIndicateurs().getImpacts());
            addPageBreak(document);
        }
    }

    private void generateEvaluationQualitative(TextDocument document, ActionReport action) {
        addPageHeader(document, action, "Évaluation qualitative de l’action");
        Boolean needPageBreak = false;
        if (action.getCaracterisation().isTypeFinancementOuInvestissement()) {
            generateEvaluationInnovation(document, action.getQualitative().getInnovation());
            needPageBreak = true;
        }
        if (action.getQualitative().getQuestionnaireInvestissement() != null) {
            if (needPageBreak) {
                addPageBreak(document);
            }
            addQuestionnaire(document, action.getQualitative().getQuestionnaireInvestissement());
            needPageBreak = true;
        }
        if (action.getQualitative().getQuestionnaireIngenierie() != null) {
            if (needPageBreak) {
                addPageBreak(document);
            }
            addQuestionnaire(document, action.getQualitative().getQuestionnaireIngenierie());
        }
    }

    private void generateEvaluationInnovation(TextDocument document, EvaluationInnovationReport innovationReport) {
        addPart(document, "Évaluation de l’innovation");
        addSubTitle(document, "Nature d’innovation de l’action");
        addTableInnovation(document, "Aucune réponses à afficher", innovationReport.getQuestions());
        addSubTitle(document, "Niveau d’innovation global :");
        addLineBreak(addContent(document, innovationReport.getNiveauGlobal(), BOLD_FONT));
        addContent(document, "Légende :", ITALIC_FONT);
        addContent(document, "Niveau 3 :", BOLD_FONT);
        addContent(document, "Réplication d’une innovation déjà répandue, mais dans un contexte différent");
        addContent(document, "Niveau 2 :", BOLD_FONT);
        addContent(document, "Réplication d’une innovation peu répandue ou somme de petites innovations encore jamais combinées ensemble");
        addContent(document, "Niveau 1 :", BOLD_FONT);
        addContent(document, "Première nationale");
        if (innovationReport.getComment() != null && innovationReport.getComment().length() > 0) {
            addLineBreak(document);
            addContent(document, "Commentaire (dont sources des données utilisées) :", ITALIC_FONT);
            addLineBreak(addContent(document, innovationReport.getComment(), ITALIC_FONT));
        }
    }

    public byte[] generateActionReport(ActionBean action) {
        ActionReport actionReport = copyFrom(action);
        try (TextDocument document = TextDocument.newTextDocument()) {
            addPageCountFooter(document);
            generatePresentation(document, actionReport);
            generateCategorisation(document, actionReport);
            generateEvaluationQuantitative(document, actionReport);
            generateEvaluationQualitative(document, actionReport);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            return out.toByteArray();
        } catch (Exception e) {
            LOGGER.error("Erreur pendant la génération du rapport d'action {}", (action != null ? String.valueOf(action.getId()) : "-1"), e);
            throw new InternalServerErrorException();
        }
    }
}
