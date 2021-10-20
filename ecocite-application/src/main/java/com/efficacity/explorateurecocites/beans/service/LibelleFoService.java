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

package com.efficacity.explorateurecocites.beans.service;

import com.efficacity.explorateurecocites.beans.biz.json.LibelleFoBean;
import com.efficacity.explorateurecocites.beans.model.LibelleFo;
import com.efficacity.explorateurecocites.beans.model.LibelleFo_;
import com.efficacity.explorateurecocites.beans.repository.LibelleFoRepository;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.utils.ApplicationConstants;
import com.efficacity.explorateurecocites.utils.enumeration.LIBELLE_FO_CODE;
import com.efficacity.explorateurecocites.utils.enumeration.LIBELLE_FO_TYPE;
import com.hp.hpl.jena.shared.NotFoundException;
import isotope.commons.services.CrudEntityService;
import isotope.modules.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.beans.specification.LibelleFoSpecifications.withCode;
import static com.efficacity.explorateurecocites.beans.specification.LibelleFoSpecifications.withType;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 * <p>
 * Date de génération : 09/04/2018
 */
@Service
public class LibelleFoService extends CrudEntityService<LibelleFoRepository, LibelleFo, Long> {

    private MessageSourceService messageSourceService;
    private ActionService actionService;
    private EcociteService ecociteService;

    @Autowired
    public void setActionService(final ActionService actionService) {
        this.actionService = actionService;
    }

    @Autowired
    public void setEcociteService(final EcociteService ecociteService) {
        this.ecociteService = ecociteService;
    }

    @Autowired
    public void setMessageSourceService(final MessageSourceService messageSourceService) {
        this.messageSourceService = messageSourceService;
    }

    public LibelleFoService(LibelleFoRepository repository) {
        super(repository);
    }

    public LibelleFo findById(Long id) {
        return findOne(id)
                .orElseThrow(() ->
                        new NotFoundException("LibelleFo" + " '" + id + "' n'est pas trouvé")
                );
    }

    public List<LibelleFoBean> getList(LIBELLE_FO_TYPE type) {
        return repository.findAll(withType(type), Sort.by(Sort.Direction.ASC, LibelleFo_.id.getName())).stream().map(LibelleFoBean::new).collect(Collectors.toList());
    }
    public LibelleFoBean findByCode(LIBELLE_FO_CODE code) {
        return new LibelleFoBean(repository.findOne(withCode(code)).orElse(null));
    }

    public LibelleFo save(final LibelleFoBean form, JwtUser user) {
        LibelleFo libelleFo = repository.findById(form.getId()).orElse(null);
        if (libelleFo == null) {
            return null;
        }
        libelleFo.setTexte(form.getTexte());
        libelleFo.setDateModification(LocalDateTime.now());
        libelleFo.setUserModification(user.getEmail());
        return repository.save(libelleFo);
    }

    public void saveMultiple(final List<LibelleFoBean> form, final JwtUser user, final Errors errors, final Locale locale) {
        for (final LibelleFoBean libelle : form) {
            LibelleFo saved = save(libelle, user);
            if (saved == null) {
                errors.reject("em_" + libelle.getId(), messageSourceService.getMessageSource().getMessage(ApplicationConstants.ERROR_TECHNICAL, null, locale));
            }
        }
    }

    public void fillDynamicNews(final Model model) {
        model.addAttribute("titre_section", findByCode(LIBELLE_FO_CODE.EM_TITRE_SECTION));
        model.addAttribute("nbEcocitesVisibles", findByCode(LIBELLE_FO_CODE.EM_NOMBRE_ECOCITE));
        model.addAttribute("description_section", findByCode(LIBELLE_FO_CODE.EM_DESCRIPTION_SECTION));
        model.addAttribute("montant_pia_vdd", findByCode(LIBELLE_FO_CODE.EM_MONTANT_PIA_VDD));
        model.addAttribute("nombre_action_visible", findByCode(LIBELLE_FO_CODE.EM_NOMBRE_ACTION_VISIBLE));
        model.addAttribute("nombre_action_realise", findByCode(LIBELLE_FO_CODE.EM_NOMBRE_ACTION_REALISE));
        model.addAttribute("nombre_action_evalue", findByCode(LIBELLE_FO_CODE.EM_NOMBRE_ACTION_EVALUE));
        model.addAttribute("nbEcocites", ecociteService.countVisibleEcocite());
        model.addAttribute("nbActionsVisible", actionService.countActionVisible());
        model.addAttribute("nbActionsDone", actionService.countActionDone());
        model.addAttribute("nbActionsEvaluated", actionService.countActionEvaluated());
    }
}
