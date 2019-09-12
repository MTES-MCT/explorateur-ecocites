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

package com.efficacity.explorateurecocites.beans.service;

import com.efficacity.explorateurecocites.beans.biz.json.ImportResponse;
import com.efficacity.explorateurecocites.beans.model.Business;
import com.efficacity.explorateurecocites.beans.repository.BusinessRepository;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.BusinessForm;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.ui.bo.service.imports.ExcelImportUtils;
import com.efficacity.explorateurecocites.ui.bo.service.imports.SimpleExcelImporter;
import com.efficacity.explorateurecocites.ui.bo.service.imports.exceptions.CellException;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import com.efficacity.explorateurecocites.utils.enumeration.STATUT_FINANCIER_AFFAIRE;
import isotope.commons.exceptions.NotFoundException;
import isotope.commons.services.CrudEntityService;
import isotope.modules.security.JwtUser;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.PersistenceException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.beans.specification.BusinessSpecifications.*;
import static com.efficacity.explorateurecocites.beans.specification.SpecificationHelper.addSpec;
import static com.efficacity.explorateurecocites.ui.bo.service.imports.SimpleExcelImporter.*;
import static org.springframework.data.jpa.domain.Specifications.not;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 * <p>
 * Date de génération : 06/02/2018
 */
@Service
public class BusinessService extends CrudEntityService<BusinessRepository, Business, Long> {

    @Autowired
    MessageSourceService messageSourceService;

    public BusinessService(BusinessRepository repository) {
        super(repository);
    }

    public Business getOne(Long id) {
        return findOne(id)
                .orElseThrow(() ->
                        new NotFoundException("Business" + " '" + id + "' n'est pas trouvé")
                );
    }

    public List<Business> findByIdAction(Long idAction) {
        return repository.findByIdAction(idAction);
    }

    public List<Business> findByIdActionOrderByIdAsc(Long idAction) {
        return repository.findByIdActionOrderByIdAsc(idAction);
    }

    public List<Business> getList() {
        return repository.findAll();
    }

    public List<Business> getListOrderByIdAsc() {
        return repository.findAllByOrderByIdAsc();
    }

    public void delete(final Long id) {
        repository.delete(id);
    }

    public Business update(final Long id, final BusinessForm tableform, final JwtUser user) {
        Business a = repository.findOne(id);
        a.setNom(tableform.getNom());
        a.setNomOperation(tableform.getNomOperation());
        a.setNumeroAffaire(tableform.getNumero());
        a.setNumeroOperation(tableform.getNumeroOperation());
        a.setIdAction(tableform.getIdAction());
        a.setStatutFinancier(tableform.getStatutFinancier());
        a.setTranche(tableform.getTranche());
        a.setTypeFinancement(tableform.getTypeFinancement());
        try {
            a.setDateDebut(LocalDateTime.parse(tableform.getDateDebut(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } catch (DateTimeParseException e) {
            // Worst case we don't set it but the validator should have caught it
        }
        try {
            a.setDateFin(LocalDateTime.parse(tableform.getDateFin(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } catch (DateTimeParseException e) {
            // Worst case we don't set it but the validator should have caught it
        }
        return save(a, user);
    }

    private Business save(Business business, JwtUser user) {
        business.setDateModification(LocalDateTime.now());
        business.setUserModification(user.getEmail());
        return repository.save(business);
    }

    private Business create(Business business, JwtUser user) {
        business.setDateCreation(LocalDateTime.now());
        business.setUserCreation(user.getEmail());
        business.setDateModification(LocalDateTime.now());
        business.setUserModification(user.getEmail());
        return repository.save(business);
    }

    public Business createOne(final BusinessForm tableform, final JwtUser user) {
        return create(tableform.getBusiness(), user);
    }

    public void removeActionReference(final Long id) {
        repository.save(repository.findAll(where(hasAction(id))).stream().peek(b -> b.setIdAction(null)).collect(Collectors.toList()));
    }

    public Long countByNumeroAffaire(final String numero, final Long selfId) {
        return repository.count(where(hasNumeroAffaire(numero)).and(not(hasId(selfId))));
    }

    public Long countByNumeroOperation(final String numeroOperation, final Long selfId) {
        return repository.count(where(hasNumeroOperation(numeroOperation)).and(not(hasId(selfId))));
    }

    public List<Business> getListFiltre(final Map<String, String> filtres) {
        return repository.findAll(getFiltre(filtres));
    }

    private Specifications<Business> getFiltre(Map<String, String> filtres) {
        Specifications<Business> spec = null;
        if (filtres != null && !filtres.isEmpty()) {
            for (String champs : filtres.keySet()) {
                if (CustomValidator.isNotEmpty(filtres.get(champs))) {
                    switch (champs) {
                        case "nom":
                            spec = addSpec(spec, hasNom(filtres.get(champs)));
                            break;
                        case "ecocite":
                            spec = addSpec(spec, hasEcocite(filtres.get(champs)));
                            break;
                        case "statutFinancier":
                            spec = addSpec(spec, hasStatutFinancierIn(STATUT_FINANCIER_AFFAIRE.getByCode(filtres.get(champs)).getCode()));
                            break;
                        case "numeroAffaire":
                            spec = addSpec(spec, hasLikeNumeroAffaire(filtres.get(champs)));
                            break;
                    }
                }
            }
        }
        return spec;
    }

    public ImportResponse uploadFiles(final MultipartFile file, final BindingResult bindingResult, final Locale locale) {
        ImportResponse response = new ImportResponse();
        if (file == null) {
            return response.setFail("Veuillez fournir un fichier à télécharger");
        }
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheet("opérations et affaires");
            if (sheet == null) {
                return response.setFail("Le document ne contient pas d'onglet avec le bon nom");
            }
            new SimpleExcelImporter(this::processLine).setSkipLinesStart(2).upload(sheet, response);
        } catch (IOException | InvalidFormatException e) {
            return response.setFail("Erreur technique pendant l'import du fichier, vérifier le format du fichier");
        }
        return response;
    }

    private int processLine(Row row) throws CellException {
        try {
            Business contrat = readFromRow(row);
            List<Business> parent = repository.findAll(where(hasNumeroAffaire(contrat.getNumeroAffaire())));
            int result;
            if (parent == null || parent.isEmpty()) {
                result = CREATED;
                repository.save(contrat);
            } else {
                Business oldSelf = parent.get(0);
                if (areBusinessEquals(oldSelf, contrat)) {
                    result = NOACTION;
                } else {
                    result = UPDATED;
                    contrat.setIdAction(oldSelf.getIdAction());
                    contrat.setId(oldSelf.getId());
                    repository.save(contrat);
                }
            }
            return result;
        } catch (PersistenceException e) {
            throw e;
        }
    }

    public Business readFromRow(Row row) throws CellException {
        final AtomicInteger cellIndex = new AtomicInteger(2);
        Business affaire = new Business();
        Function<String, Supplier<CellException>> exceptionSupplier = ExcelImportUtils.createExceptionSupplier(row, cellIndex);
        String numeroOperation = ExcelImportUtils.getStringTroncateIfNumber(row, cellIndex.getAndIncrement())
                .orElseThrow(exceptionSupplier.apply("Numero d'opération obligatoire"));
        affaire.setNumeroOperation(numeroOperation);
        String nomOperation = ExcelImportUtils.getString(row, cellIndex.getAndIncrement())
                .orElseThrow(exceptionSupplier.apply("Nom d'opération obligatoire"));
        affaire.setNomOperation(nomOperation);
        String ecocite = ExcelImportUtils.getString(row, cellIndex.getAndIncrement())
                .orElse("");
        affaire.setEcocite(ecocite);
        String tranche = ExcelImportUtils.getTranche(row, cellIndex.getAndIncrement())
                .orElseThrow(exceptionSupplier.apply("Tranche obligatoire"));
        affaire.setTranche(tranche);
        //SKIP Column
        cellIndex.incrementAndGet();
        String typeFinancement = ExcelImportUtils.getTypeFinancement(row, cellIndex.getAndIncrement())
                .orElseThrow(exceptionSupplier.apply("TypeFinancement obligatoire"));
        affaire.setTypeFinancement(typeFinancement);
        //SKIP two Column
        cellIndex.incrementAndGet();
        cellIndex.incrementAndGet();
        String numeroAffaire = ExcelImportUtils.getStringTroncateIfNumber(row, cellIndex.getAndIncrement())
                .orElseThrow(exceptionSupplier.apply("Numéro d'affaire obligatoire"));
        affaire.setNumeroAffaire(numeroAffaire);
        String nom = ExcelImportUtils.getString(row, cellIndex.getAndIncrement())
                .orElseThrow(exceptionSupplier.apply("Nom d'affaire obligatoire"));
        affaire.setNom(nom);
        String statutFinancier = ExcelImportUtils.getStatutFinancier(row, cellIndex.getAndIncrement())
                .orElseThrow(exceptionSupplier.apply("Statut d'affaire obligatoire"));
        affaire.setStatutFinancier(statutFinancier);
        //SKIP two Column
        cellIndex.incrementAndGet();
        cellIndex.incrementAndGet();

        try {
            LocalDateTime dateDebut = ExcelImportUtils.getLocalDateTime(row, cellIndex.getAndIncrement())
                    .orElse(null);
            affaire.setDateDebut(dateDebut);
        } catch (CellException e) {
            System.out.println(e);
        }
        return affaire;
    }

    private static Boolean areBusinessEquals(Business b1, Business b2) {
        if (b1 == b2) {
            return true;
        }
        if (b1 == null || b2 == null) {
            return false;
        }
        return Objects.equals(b1.getNumeroOperation(), b2.getNumeroOperation())
                && Objects.equals(b1.getTranche(), b2.getTranche())
                && Objects.equals(b1.getTypeFinancement(), b2.getTypeFinancement())
                && Objects.equals(b1.getNumeroAffaire(), b2.getNumeroAffaire())
                && Objects.equals(b1.getNom(), b2.getNom())
                && Objects.equals(b1.getStatutFinancier(), b2.getStatutFinancier())
                && Objects.equals(b1.getDateDebut(), b2.getDateDebut())
                && Objects.equals(b1.getEcocite(), b2.getEcocite());
    }
}
