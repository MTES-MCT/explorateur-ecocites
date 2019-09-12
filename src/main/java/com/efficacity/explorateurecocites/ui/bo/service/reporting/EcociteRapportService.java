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

import com.efficacity.explorateurecocites.beans.biz.EcociteBean;
import com.efficacity.explorateurecocites.beans.biz.EtapeBean;
import com.efficacity.explorateurecocites.beans.biz.reporting.ecocite.*;
import com.efficacity.explorateurecocites.beans.biz.reporting.misc.EtiquettesReport;
import com.efficacity.explorateurecocites.beans.biz.reporting.misc.IndicateurMesureReport;
import com.efficacity.explorateurecocites.beans.biz.reporting.misc.QuestionnaireReport;
import com.efficacity.explorateurecocites.beans.model.AssoObjetObjectif;
import com.efficacity.explorateurecocites.beans.model.Contact;
import com.efficacity.explorateurecocites.beans.model.Region;
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
public class EcociteRapportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EcociteRapportService.class);

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
    private final RegionService regionService;
    private final MediaService mediaService;

    @Value(value = "classpath:templates/bo/odt/ecocites.odt")
    private Resource templateEcocite;

    @Value(value = "classpath:templates/bo/odt/common/questionnaire.odt")
    private Resource templateQuestionnairePart;

    @Autowired
    public EcociteRapportService(final ReportService reportService, final EcociteService ecociteService,
                                 final IndicateurService indicateurService, final FileUploadService fileUploadService,
                                 final AssoActionDomainService assoActionDomainService, final MesureIndicateurService mesureIndicateurService,
                                 final AssoActionIngenierieService assoActionIngenierieService, final AssoObjetObjectifService assoObjetObjectifService,
                                 final EtapeService etapeService, final AssoObjetContactService assoObjetContactService,
                                 final AssoIndicateurObjetService assoIndicateurObjetService, final QuestionnaireEvaluationService questionnaireEvaluationService,
                                 final AxeService axeService, final CibleIndicateurService cibleIndicateurService,
                                 final RegionService regionService, final MediaService mediaService) {
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
        this.regionService = regionService;
        this.mediaService = mediaService;
    }

    public EcociteReport copyFrom(EcociteBean ecocite) {
        final Region region = ecocite.getIdRegion() != null ? regionService.findById(ecocite.getIdRegion()) : null;
        File image = mediaService.getMainMediaForEcociteFile(ecocite);
        List<Contact> contacts = new ArrayList<>();
        contacts.addAll(assoObjetContactService.findAllContactPrincipaleForEcocite(ecocite.getId()));
        contacts.addAll(assoObjetContactService.findAllContactSecondaireForEcocite(ecocite.getId()));
        FicheIdentite ficheIdentite = new FicheIdentite(ecocite, contacts);
        List<AssoObjetObjectif> assoObjetObjectifs = assoObjetObjectifService.getListByEcocite(ecocite.getId());
        List<EtapeBean> etapes = etapeService.getEtapeByIdEcocite(ecocite.getId());
        EtiquettesReport objectif = new EtiquettesReport(reportService.filterObjectifs(assoObjetObjectifs, 1),
                reportService.filterObjectifs(assoObjetObjectifs, 2), reportService.filterObjectifs(assoObjetObjectifs, 3),
                reportService.getCommentaireEtape(ETAPE_ACTION_EDITION.CATEGORIESATION_OBJECTIF, etapes));
        Caracterisation caracterisation = new Caracterisation(objectif);
        List<IndicateurMesureReport> indicateurReports = assoIndicateurObjetService.findAllByEcocite(ecocite.getId())
                .stream()
                .map(i -> new IndicateurMesureReport(i, indicateurService.findOneIndicateur(i.getIdIndicateur()),
                        cibleIndicateurService.findAllByIdAssoIndicateurObjetOrderByDateSaisieAsc(i.getId()),
                        mesureIndicateurService.findAllByIdAssoIndicateurObjetOrderByDateSaisieAsc(i.getId())))
                .collect(Collectors.toList());
        EvaluationQuantitative quantitative = new EvaluationQuantitative(indicateurReports);
        QuestionnaireReport eco = new QuestionnaireReport(QUESTIONNAIRE_CODE.QUESTIONNAIRE_ECOCITE.getLibelle(),
                        questionnaireEvaluationService.genererQuestionnaire(QUESTIONNAIRE_CODE.QUESTIONNAIRE_ECOCITE.getCode(),
                                ecocite.getId(), TYPE_OBJET.ECOCITE.getCode()));
        EvaluationQualitative qualitative = new EvaluationQualitative(eco);
        return new EcociteReport(ecocite, region, ficheIdentite, caracterisation, image, quantitative, qualitative);
    }

    private void generatePresentation(TextDocument document, EcociteReport ecocite) throws Exception {
        TextDocument presentation = TextDocument.loadDocument(templateEcocite.getInputStream());
        replaceAllBaliseWith(presentation, "\\[NomEcocite\\]", ecocite.getNom());
        replaceAllBaliseWith(presentation, "\\[NomRegion\\]", ecocite.getNomRegion());
        replaceBaliseWith(presentation, "\\[NombreCommunes\\]", ecocite.getIdentite().getNombreCommunes());
        replaceBaliseWith(presentation, "\\[NombreHabitants\\]", ecocite.getIdentite().getNombreHabitants());
        replaceBaliseWith(presentation, "\\[Superficie\\]", ecocite.getIdentite().getSuperficie());
        replaceBaliseWith(presentation, "\\[EcociteDepuis\\]", ecocite.getIdentite().getEcociteDepuis());
        //Ajout de l'image si elle existe, sinon on laisse le placeholder
        if (ecocite.getImage() != null &&
                presentation.getTableList().stream().anyMatch(o -> o.getTableName().equals("presentation"))) {
            Table presentationTable = presentation.getTableByName("presentation");
            presentationTable.getRowByIndex(0).setUseOptimalHeight(true);
            centerCellContent(addCellContent(presentationTable.getCellByPosition(1, 0), ecocite.getImage().toURI(), PRESENTATION_IMAGE));
        }
        replaceBaliseWith(presentation, "\\[PorteurProjet\\]", ecocite.getIdentite().getPorteurProjet());
        replaceBaliseWith(presentation, "\\[SoutienPIA\\]", ecocite.getIdentite().getSoutientPIA());
        if(ecocite.getIdentite().getPartenaires().isEmpty()) {
            replaceBaliseWith(presentation, "\\[Partenaires\\]", "Pas de maîtrises d'ouvrages renseignées");
        } else {
            replaceBaliseWith(presentation, "\\[Partenaires\\]", ecocite.getIdentite().getPartenaires().replaceAll(";", "\n"));
        }
        replaceBaliseWith(presentation, "\\[DescriptionEcociteStrat\\]", toSimpleTexte(ecocite.getIdentite().getDescriptionStrategie()));
        replaceBaliseWith(presentation, "\\[DescriptionEcocitePeri\\]", toSimpleTexte(ecocite.getIdentite().getDescriptionPerimetre()));
        replaceBaliseWith(presentation, "\\[Lien\\]", ecocite.getIdentite().getLien());
        document.insertContentFromDocumentBefore(presentation, document.getParagraphByIndex(0, false), true);
        addContactTable(document, ecocite.getIdentite().getContacts());
        addPageBreak(document);
    }

    private void generateCategorisation(TextDocument document, EcociteReport ecocite) {
        addPageHeader(document, ecocite, "Caractérisation de l’ÉcoCité");
        addSubTitle(document, "Objectif(s) ville durable de l’ÉcoCité");
        addSubSubTitle(document, "Majeurs");
        addLineBreak(addListWithBulletPoint(document, ecocite.getCaracterisation().getObjectif().getMajeures(), EMPTY, REGULAR_FONT, 1, BULLET_POINT));
        addSubSubTitle(document, "Modérés");
        addLineBreak(addListWithBulletPoint(document, ecocite.getCaracterisation().getObjectif().getModeres(), EMPTY, REGULAR_FONT, 1, BULLET_POINT));
        addSubSubTitle(document, "Mineurs");
        addLineBreak(addListWithBulletPoint(document, ecocite.getCaracterisation().getObjectif().getMineures(), EMPTY, REGULAR_FONT, 1, BULLET_POINT));
        if (ecocite.getCaracterisation().getObjectif().getCommentaire() != null &&
                !ecocite.getCaracterisation().getObjectif().getCommentaire().isEmpty()) {
            addContent(document, COMMENTAIRE, ITALIC_FONT);
            addLineBreak(addContent(document, ecocite.getCaracterisation().getObjectif().getCommentaire(), ITALIC_FONT));
        }
        addPageBreak(document);
    }

    private void generateEvaluationQuantitative(TextDocument document, EcociteReport ecocite) {
        addPageHeader(document, ecocite, "Évaluation quantitative de l’ÉcoCité");
        addSubTitle(document, "Liste des indicateur(s) choisi(s)");
        List<String> indics = new ArrayList<>();
        ecocite.getQuantitative().getIndicateurs().getRealisations().stream().map(IndicateurMesureReport::getNomCourt).forEach(indics::add);
        ecocite.getQuantitative().getIndicateurs().getImpacts().stream().map(IndicateurMesureReport::getNomCourt).forEach(indics::add);
        ecocite.getQuantitative().getIndicateurs().getResultats().stream().map(IndicateurMesureReport::getNomCourt).forEach(indics::add);
        addLineBreak(addListWithBulletPoint(document, indics, EMPTY, REGULAR_FONT, 1, BULLET_POINT));
        addIndicateurList(document, "Indicateur(s) de réalisation", ecocite.getQuantitative().getIndicateurs().getRealisations());
        addIndicateurList(document, "Indicateur(s) de résultat", ecocite.getQuantitative().getIndicateurs().getResultats());
        addIndicateurList(document, "Indicateur(s) d’impact", ecocite.getQuantitative().getIndicateurs().getImpacts());
        addPageBreak(document);
    }

    private void generateEvaluationQualitative(TextDocument document, EcociteReport ecocite) {
        addPageHeader(document, ecocite, "Évaluation qualitative de l’ÉcoCité");
        addQuestionnaire(document, ecocite.getQualitative().getQuestionnaire());
    }

    public byte[] generateEcociteReport(EcociteBean ecocite) {
        EcociteReport ecociteReport = copyFrom(ecocite);
        try (TextDocument document = TextDocument.newTextDocument()) {
            addPageCountFooter(document);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            generatePresentation(document, ecociteReport);
            generateCategorisation(document, ecociteReport);
            generateEvaluationQuantitative(document, ecociteReport);
            generateEvaluationQualitative(document, ecociteReport);
            document.save(out);
            return out.toByteArray();
        } catch (Exception e) {
            LOGGER.error("Erreur pendant la génération du rapport d'ecocite " + (ecocite != null ? String.valueOf(ecocite.getId()) : "-1"), e);
            throw new InternalServerErrorException();
        }
    }
}
