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

import com.efficacity.explorateurecocites.beans.model.Contact;
import com.efficacity.explorateurecocites.beans.repository.ContactRepository;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.ContactForm;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import isotope.commons.services.CrudEntityService;
import isotope.modules.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.beans.specification.ContactSpecifications.hasEcocite;
import static com.efficacity.explorateurecocites.beans.specification.ContactSpecifications.hasEntite;
import static com.efficacity.explorateurecocites.beans.specification.ContactSpecifications.hasFonction;
import static com.efficacity.explorateurecocites.beans.specification.ContactSpecifications.hasNom;
import static com.efficacity.explorateurecocites.beans.specification.ContactSpecifications.hasPrenom;
import static com.efficacity.explorateurecocites.beans.specification.SpecificationHelper.addSpec;
import static org.springframework.data.jpa.domain.Specification.where;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 * <p>
 * Date de génération : 06/02/2018
 */
@Service
public class ContactService extends CrudEntityService<ContactRepository, Contact, Long> {

    @Autowired
    AssoObjetContactService assoObjetContactService;

    public ContactService(ContactRepository repository) {
        super(repository);
    }

    public List<Contact> getList() {
        return repository.findAll();
    }

    public List<Contact> getListByOrderByIdAsc() {
        return repository.getListByOrderByIdAsc();
    }

    /**
     * Récupérer la liste des action en fonction de critères de filtrage sur le Type de Financement
     *
     * @return l'ensemble des actions
     */
    public List<Contact> getListFiltreIdEcocite(List<String> idEcocites) {
        Specification<Contact> spec = null;
        if (idEcocites != null && !idEcocites.isEmpty()) {
            for (String id : idEcocites) {
                if (CustomValidator.isNotEmpty(id) && !id.equals("")) {
                    if (spec == null) {
                        spec = where(hasEcocite(Long.parseLong(id)));
                    } else {
                        spec = spec.or(hasEcocite(Long.parseLong(id)));
                    }
                }
            }
        } else {
            spec = null;
        }
        return repository.findAll(spec);
    }

    public void delete(final Long id) {
        Contact contact = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        assoObjetContactService.deleteByContact(contact.getId());
        repository.delete(contact);
    }

    public Contact update(final Long id, final ContactForm tableform, JwtUser user) {
        Contact a = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        ;
        a.setNom(tableform.getNom());
        a.setTelephone(tableform.getTelephone());
        a.setPrenom(tableform.getPrenom());
        a.setFonction(tableform.getFonction());
        a.setEntite(tableform.getEntite());
        a.setIdEcocite(tableform.getIdEcocite());
        a.setEmail(tableform.getEmail());
        return save(a, user);
    }

    private Contact save(final Contact contact, JwtUser user) {
        contact.setUserModification(user.getEmail());
        contact.setDateModification(LocalDateTime.now());
        return repository.save(contact);
    }

    private Contact create(final Contact contact, JwtUser user) {
        contact.setUserCreation(user.getEmail());
        contact.setDateCreation(LocalDateTime.now());
        contact.setUserModification(user.getEmail());
        contact.setDateModification(LocalDateTime.now());
        return repository.save(contact);
    }

    public Contact createOne(final ContactForm tableform, JwtUser user) {
        return create(tableform.getContact(), user);
    }

    public void removeEcociteReference(final Long id, JwtUser user) {
        final LocalDateTime now = LocalDateTime.now();
        final String email = user.getEmail();
        repository.saveAll(repository.findAll(where(hasEcocite(id)))
                .stream()
                .peek(c -> {
                    c.setIdEcocite(null);
                    c.setUserCreation(email);
                    c.setDateModification(now);
                })
                .collect(Collectors.toList()));
    }

    public List<Contact> findAll(List<Long> ids) {
        return repository.findAllById(ids);
    }

    public List<Contact> getListFiltre(final Map<String, String> filtres) {
        return repository.findAll(getFiltre(filtres));
    }

    private Specification<Contact> getFiltre(Map<String, String> filtres) {
        Specification<Contact> spec = null;
        if (filtres != null && !filtres.isEmpty()) {
            for (String champs : filtres.keySet()) {
                if (CustomValidator.isNotEmpty(filtres.get(champs))) {
                    switch (champs) {
                        case "nom":
                            spec = addSpec(spec, hasNom(filtres.get(champs)));
                            break;
                        case "prenom":
                            spec = addSpec(spec, hasPrenom(filtres.get(champs)));
                            break;
                        case "fonction":
                            spec = addSpec(spec, hasFonction(filtres.get(champs)));
                            break;
                        case "entite":
                            spec = addSpec(spec, hasEntite(filtres.get(champs)));
                            break;
                        case "ecocite":
                            spec = addSpec(spec, hasEcocite(Long.valueOf(filtres.get(champs))));
                            break;
                    }
                }
            }
        }
        return spec;
    }
}
