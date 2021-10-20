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

import com.efficacity.explorateurecocites.beans.model.Action;
import com.efficacity.explorateurecocites.beans.model.AssoObjetContact;
import com.efficacity.explorateurecocites.beans.model.Contact;
import com.efficacity.explorateurecocites.beans.model.Ecocite;
import com.efficacity.explorateurecocites.beans.repository.AssoObjetContactRepository;
import com.efficacity.explorateurecocites.utils.enumeration.IMPORTANCE_LEVEL;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import isotope.commons.exceptions.BadRequestException;
import isotope.commons.exceptions.NotFoundException;
import isotope.commons.services.CrudEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.beans.specification.AssoObjetContactSpecifications.*;
import static org.springframework.data.jpa.domain.Specification.where;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 * <p>
 * Date de génération : 16/03/2018
 */
@Service
public class AssoObjetContactService extends CrudEntityService<AssoObjetContactRepository, AssoObjetContact, Long> {

    @Autowired
    ContactService contactService;

    @Autowired
    ActionService actionService;

    @Autowired
    EcociteService ecociteService;

    public AssoObjetContactService(AssoObjetContactRepository repository) {
        super(repository);
    }

    public List<AssoObjetContact> findAllAssoForAction(final Long actionId) {
//        return repository.findAll(where(hasIdObjet(actionId)).and(belongToAction()));
        return repository.findAllByTypeObjetAndIdObjetOrderByIdContactAsc(TYPE_OBJET.ACTION.getCode(),actionId);
    }
    public List<AssoObjetContact> findAllAssoForEcocite(final Long ecociteId) {
//        return repository.findAll(where(hasIdObjet(ecociteId)).and(belongToEcocite()));
        return repository.findAllByTypeObjetAndIdObjetOrderByIdContactAsc(TYPE_OBJET.ECOCITE.getCode(),ecociteId);
    }

    private List<AssoObjetContact> findAllAssoPrincipaleForAction(final Long actionId) {
        return repository.findAll(where(hasIdObjet(actionId)).and(belongToAction()).and(isContactPrincipal()));
    }

    private Long countAssoPrincipaleForAction(final Long actionId) {
        return repository.count(where(hasIdObjet(actionId)).and(belongToAction()).and(isContactPrincipal()));
    }

    public List<Contact> findAllContactPrincipaleForAction(final Long actionId) {
        return contactService.findAll(findAllAssoPrincipaleForAction(actionId).stream().map(AssoObjetContact::getIdContact).collect(Collectors.toList()));
    }

    private List<AssoObjetContact> findAllAssoSecondaireForAction(final Long actionId) {
        return repository.findAll(where(hasIdObjet(actionId)).and(belongToAction()).and(isContactSecondaire()));
    }

    private Long countAssoSecondaireForAction(final Long actionId) {
        return repository.count(where(hasIdObjet(actionId)).and(belongToAction()).and(isContactSecondaire()));
    }

    public List<Contact> findAllContactSecondaireForAction(final Long actionId) {
        return contactService.findAll(findAllAssoSecondaireForAction(actionId).stream().map(AssoObjetContact::getIdContact).collect(Collectors.toList()));
    }

    private List<AssoObjetContact> findAllAssoPrincipaleForEcocite(final Long ecociteId) {
        return repository.findAll(where(hasIdObjet(ecociteId)).and(belongToEcocite()).and(isContactPrincipal()));
    }

    private Long countAssoPrincipaleForEcocite(final Long ecociteId) {
        return repository.count(where(hasIdObjet(ecociteId)).and(belongToEcocite()).and(isContactPrincipal()));
    }

    public List<Contact> findAllContactPrincipaleForEcocite(final Long ecociteId) {
        return contactService.findAll(findAllAssoPrincipaleForEcocite(ecociteId).stream().map(AssoObjetContact::getIdContact).collect(Collectors.toList()));
    }

    private List<AssoObjetContact> findAllAssoSecondaireForEcocite(final Long ecociteId) {
        return repository.findAll(where(hasIdObjet(ecociteId)).and(belongToEcocite()).and(isContactSecondaire()));
    }

    private Long countAssoSecondaireForEcocite(final Long ecociteId) {
        return repository.count(where(hasIdObjet(ecociteId)).and(belongToEcocite()).and(isContactSecondaire()));
    }

    public List<Contact> findAllContactSecondaireForEcocite(final Long ecociteId) {
        return contactService.findAll(findAllAssoSecondaireForEcocite(ecociteId).stream().map(AssoObjetContact::getIdContact).collect(Collectors.toList()));
    }

    public AssoObjetContact createOneContactForAction(final Long idContact, final Long idAction, final Integer poids) {
        Contact contact = contactService.findOne(idContact).orElseThrow(NotFoundException::new);
        Action action = actionService.findOne(idAction).orElseThrow(NotFoundException::new);
        if (poids == 1) {
            return createOnePrimaryContactForAction(contact, action);
        } else {
            return createOneSecondaryContactForAction(contact, action);
        }
    }

    public AssoObjetContact createOneContactForEcocite(final Long idContact, final Long idAction, final Integer poids) {
        Contact contact = contactService.findOne(idContact).orElseThrow(NotFoundException::new);
        Ecocite ecocite = ecociteService.findOne(idAction).orElseThrow(NotFoundException::new);
        IMPORTANCE_LEVEL importanceLevel = IMPORTANCE_LEVEL.getByCode(poids);
        switch (importanceLevel) {
            case PRINCIPAL:
                return createOnePrimaryContactForEcocite(contact, ecocite);
            case SECONDAIRE:
            default:
                return createOneSecondaryContactForEcocite(contact, ecocite);
        }
    }

    private AssoObjetContact createOnePrimaryContactForEcocite(final Contact contact, final Ecocite ecocite) {
        repository.deleteAll(repository.findAll(where(hasIdObjet(ecocite.getId())).and(belongToEcocite()).and(hasIdContact(contact.getIdEcocite())).and(isContactPrincipal())));
        AssoObjetContact bean = new AssoObjetContact();
        bean.setIdContact(contact.getId());
        bean.setIdObjet(ecocite.getId());
        bean.setTypeObjet(TYPE_OBJET.ECOCITE.getCode());
        bean.setImportance(IMPORTANCE_LEVEL.PRINCIPAL.getCode());
        return repository.save(bean);
    }

    private AssoObjetContact createOneSecondaryContactForEcocite(final Contact contact, final Ecocite ecocite) {
        if (countAssoSecondaireForEcocite(ecocite.getId()) >= 2) {
            throw new BadRequestException("");
        }
        AssoObjetContact bean = new AssoObjetContact();
        bean.setIdContact(contact.getId());
        bean.setIdObjet(ecocite.getId());
        bean.setTypeObjet(TYPE_OBJET.ECOCITE.getCode());
        bean.setImportance(IMPORTANCE_LEVEL.SECONDAIRE.getCode());
        return repository.save(bean);
    }

    private AssoObjetContact createOnePrimaryContactForAction(final Contact contact, final Action action) {
        repository.deleteAll(repository.findAll(where(hasIdObjet(action.getId())).and(belongToAction()).and(hasIdContact(contact.getIdEcocite())).and(isContactPrincipal())));
        AssoObjetContact bean = new AssoObjetContact();
        bean.setIdContact(contact.getId());
        bean.setIdObjet(action.getId());
        bean.setTypeObjet(TYPE_OBJET.ACTION.getCode());
        bean.setImportance(IMPORTANCE_LEVEL.PRINCIPAL.getCode());
        return repository.save(bean);
    }

    private AssoObjetContact createOneSecondaryContactForAction(final Contact contact, final Action action) {
        if (countAssoSecondaireForAction(action.getId()) >= 2) {
            throw new BadRequestException("");
        }
        AssoObjetContact bean = new AssoObjetContact();
        bean.setIdContact(contact.getId());
        bean.setIdObjet(action.getId());
        bean.setTypeObjet(TYPE_OBJET.ACTION.getCode());
        bean.setImportance(IMPORTANCE_LEVEL.SECONDAIRE.getCode());
        return repository.save(bean);
    }

    public void deleteAssoActionContact(final Long idContact, final Long idAction) {
        repository.deleteAll(repository.findAll(where(hasIdObjet(idAction)).and(belongToAction()).and(hasIdContact(idContact))));
    }

    public void deleteAssoEcociteContact(final Long idContact, final Long idEcocite) {
        repository.deleteAll(repository.findAll(where(hasIdObjet(idEcocite)).and(belongToEcocite()).and(hasIdContact(idContact))));
    }

    public void deleteByAction(final Long idAction) {
        repository.deleteAll(repository.findAll(where(hasIdObjet(idAction)).and(belongToAction())));
    }

    public void deleteByEcocite(final Long idEcocite) {
        repository.deleteAll(repository.findAll(where(hasIdObjet(idEcocite)).and(belongToEcocite())));
    }

    public void deleteByContact(final Long idContact) {
        repository.deleteAll(repository.findAll(hasIdContact(idContact)));
    }

    public void deleteAssoEcociteContactPrincipal(final Long idEcocite) {
        repository.deleteAll(repository.findAll(where(hasIdObjet(idEcocite)).and(belongToEcocite()).and(isContactPrincipal())));
    }

    public void deleteAssoActionContactPrincipal(final Long idAction) {
        repository.deleteAll(repository.findAll(where(hasIdObjet(idAction)).and(belongToAction()).and(isContactPrincipal())));
    }

    public List<AssoObjetContact> cloneForAction(final Long idClone, final Long idOriginal) {
        List<AssoObjetContact> clones = repository.findAllByTypeObjetAndIdObjetOrderByIdContactAsc(TYPE_OBJET.ACTION.getCode(), idOriginal)
                .stream()
                .map(original -> {
                    AssoObjetContact clone = new AssoObjetContact();
                    clone.setIdContact(original.getIdContact());
                    clone.setImportance(original.getImportance());
                    clone.setTypeObjet(TYPE_OBJET.ACTION.getCode());
                    clone.setIdObjet(idClone);
                    return clone;
                })
                .collect(Collectors.toList());
        return repository.saveAll(clones);
    }
}
