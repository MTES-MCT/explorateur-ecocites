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

import com.efficacity.explorateurecocites.beans.biz.FileUploadBean;
import com.efficacity.explorateurecocites.beans.model.Axe;
import com.efficacity.explorateurecocites.beans.model.Axe_;
import com.efficacity.explorateurecocites.beans.repository.AxeRepository;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.AxeTableForm;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.utils.enumeration.FILE_TYPE;
import isotope.commons.exceptions.NotFoundException;
import isotope.commons.services.CrudEntityService;
import isotope.modules.security.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.beans.specification.AxeSpecifications.hasIdIn;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 * <p>
 * Date de génération : 02/02/2018
 */
@Service
public class AxeService extends CrudEntityService<AxeRepository, Axe, Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AxeService.class);

    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    ActionService actionService;

    @Autowired
    MessageSourceService messageSourceService;

    @Autowired
    EtiquetteAxeService etiquetteAxeService;

    @Autowired
    MediaModificationService mediaModificationService;

    public AxeService(AxeRepository repository) {
        super(repository);
    }

    /**
     * Récupérer toutes les axes de la BDD
     *
     * @return une liste des axes
     */
    public List<Axe> getList() {
        return repository.findAll(new Sort(Axe_.libelle.getName()));
    }

    /**
     * Récupérer toutes les axes de la BDD
     *
     * @return un ensemble {@code LinkedHashMap} des axes avec l'id et le nom
     * @see #getList()
     */
    public LinkedHashMap<String, String> getMap() {
        return getList().stream()
                .collect(Collectors.toMap(e -> String.valueOf(e.getId()), Axe::getLibelle,
                        (k1, k2) -> k1,
                        LinkedHashMap::new)
                );
    }

    /**
     * Récupérer une axe par l'id
     *
     * @param id id de l'axe à rechercher
     * @return resultat non null du type {@code Axe}
     * @throws NotFoundException si aucun n'est trouvé
     */
    public Axe findById(Long id) {
        return findOne(id)
                .orElseThrow(() ->
                        new NotFoundException("Axe" + " '" + id + "' n'est pas trouvé")
                );
    }

    /**
     * Récupérer une axe par l'id
     *
     * @param id id de l'axe à rechercher
     * @return resultat du type {@code Axe} ou null si aucun n'est trouvé
     */
    public Axe getOne(Long id) {
        return repository.findOne(id);
    }


    /**
     * Récupérer une axe par l'action
     *
     * @param idAction id de l'action
     * @return resultat du type {@code Axe} ou null si aucun n'est trouvé
     */
    public Axe getByAction(Long idAction) {
        return actionService.findOne(idAction)
                .map(action ->
                        action.getIdAxe() != null ? repository.findOne(action.getIdAxe()) : null)
                .orElse(null);
    }

    public List<Axe> findAll() {
        return repository.findAll();
    }

    public String delete(final Long id, JwtUser user, Locale locale) {
        // Si l'indicateur est associer à une action ou une écocité => KO on delete pas
        if(actionService.getByAxe(id).size() > 0){
            return messageSourceService.getMessageSource().getMessage("error.objet.suppression.liee", null, locale);
        } else if (etiquetteAxeService.getListByAxe(id).size() > 0) {
            return messageSourceService.getMessageSource().getMessage("error.objet.suppression.liee", null, locale);
        } else {
            repository.delete(id);
            FileUploadBean icon = fileUploadService.getFirstFileAxeOfType(id, FILE_TYPE.AXE_ICON);
            if (icon != null) {
                fileUploadService.deleteFile(icon.getId(), user);
            }
            return "";
        }
    }

    public Axe createOne(final AxeTableForm axeTable, JwtUser user, Errors errors, Locale locale) {
        Axe a = repository.save(axeTable.getAxe());
        if (a != null) {
            FileUploadBean f = fileUploadService.saveNewAxeIcon(axeTable,user, a.getId(), errors, locale);
            if (f != null) {
                a.setIcone(f.getUrl());
                a = repository.save(a);
            }
        } else {
            throw new NotFoundException();
        }
        return a;
    }

    public void save(final Axe axe) {
        repository.save(axe);
    }

    public void update(final Long id, final AxeTableForm axeTableForm,JwtUser user, Errors errors, Locale locale) {
        Axe a = repository.findOne(id);
        if (a != null) {
            FileUploadBean f = fileUploadService.saveNewAxeIcon(axeTableForm,user, a.getId(), errors, locale);
            if (f != null) {
                a.setIcone(f.getUrl());
            }
            a.setLibelle(axeTableForm.getNom());
            a.setDateModification(LocalDateTime.now());
            a.setCodeCouleur1(axeTableForm.getColor_1());
            a.setCodeCouleur2(axeTableForm.getColor_2());
            mediaModificationService.markModified(a);
            repository.save(a);
        } else {
            throw new NotFoundException();
        }
    }

    public List<Axe> getList(final List<Long> ids) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        return repository.findAll(where(hasIdIn(ids)));
    }
}
