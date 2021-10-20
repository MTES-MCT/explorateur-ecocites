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

package com.efficacity.explorateurecocites.utils;

import com.efficacity.explorateurecocites.beans.biz.*;
import com.efficacity.explorateurecocites.beans.biz.table.*;
import com.efficacity.explorateurecocites.beans.model.*;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.*;
import com.efficacity.explorateurecocites.ui.bo.forms.tables.helpers.SelectOption;
import com.efficacity.explorateurecocites.utils.enumeration.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Created by rxion on 12/02/2018.
 */
public class ExpBeanToLightBeanUtils {

    public static ResultatRecherche copyFromAE(Action action, Ecocite ecocite) {
        return new ResultatRecherche(action, ecocite);
    }

    public static ResultatRecherche copyFrom(Action action, EcociteBean ecocite) {
        return new ResultatRecherche(action, ecocite);
    }

    public static ActionBean copyFromAC(Action action, final List<FileUploadBean> fileAction, final EtiquettesBean etiquettesList, final AdapteurIndicateursBean indicateursBean, final List<QuestionsAvecReponseBean> questions, final AdaptateurEtapesBean etapes, final List<Business> business) {
        return new ActionBean(action, fileAction, etiquettesList, indicateursBean, questions, etapes, business);
    }

    public static ActionTable copyFrom(Action action, Ecocite ecocite, Axe axe) {
        ActionTable bean = new ActionTable();
        bean.setId(action.getId());
        bean.setNomPublic(action.getNomPublic());
        if (axe != null) {
            bean.setAxePrincial(axe.getLibelle());
        }
        if (ecocite != null) {
            bean.setEcocite(ecocite.getNom());
        }
        ETAT_AVANCEMENT etatAvancement = ETAT_AVANCEMENT.getByCode(action.getEtatAvancement());
        bean.setEtatAvancement(etatAvancement != null ? etatAvancement.getLibelle() : "");
        bean.setTypeFinancement(Optional.ofNullable(TYPE_FINANCEMENT.getByCode(action.getTypeFinancement())).map(TYPE_FINANCEMENT::getLibelle).orElse(""));
        return bean;
    }

    public static AxeTableForm copyFrom(Axe axe) {
        AxeTableForm bean = new AxeTableForm();
        bean.setId(axe.getId());
        bean.setNom(axe.getLibelle());
        bean.setColor_1(axe.getCodeCouleur1());
        bean.setColor_2(axe.getCodeCouleur2());
        bean.setIcon(axe.getIcone());
        return bean;
    }

    public static EcociteTable copyFrom(Ecocite ecocite, Region region) {
        EcociteTable bean = new EcociteTable();
        bean.setId(ecocite.getId());
        bean.setNom(ecocite.getNom());
        if (region != null) {
            bean.setRegion(region.getNom());
        } else {
            bean.setRegion("");
        }
        bean.setAnneAdhesion(ecocite.getAnneeAdhesion());
        bean.setPorteurProjet(ecocite.getPorteur());
        return bean;
    }

    public static IndicateursTable copyFrom(Indicateur indicateur) {
        IndicateursTable bean = new IndicateursTable();
        bean.setId(indicateur.getId());
//        bean.setNom(indicateur.getNomCourt());
        bean.setNomLong(indicateur.getNom());
        bean.setNature(NATURE_INDICATEUR.getLibelleByCode(indicateur.getNature()));
        bean.setEchelle(ECHELLE_INDICATEUR.getLibelleByCode(indicateur.getEchelle()));
        bean.setOrigine(ORIGINE_INDICATEUR.getLibelleForCode(indicateur.getOrigine()));
        return bean;
    }

    public static EcociteBean copyFrom(Ecocite ecocite, final List<FileUploadBean> fileAction, final EtiquettesBean etiquettesList, final AdapteurIndicateursBean indicateursBean, final AdaptateurEtapesBean etapes) {
        return new EcociteBean(ecocite, fileAction, etiquettesList, indicateursBean, etapes);
    }

    public static FinaliteTableForm copyFrom(final Finalite finalite) {
        FinaliteTableForm bean = new FinaliteTableForm();
        bean.setId(finalite.getId());
        bean.setNom(finalite.getLibelle());
        bean.setColor(finalite.getCodeCouleur());
        return bean;
    }

    public static IngenierieTableForm copyFrom(final Ingenierie ingenierie) {
        IngenierieTableForm bean = new IngenierieTableForm();
        bean.setId(ingenierie.getId());
        bean.setNom(ingenierie.getLibelle());
        bean.setColor(ingenierie.getCodeCouleur());
        return bean;
    }

    public static EtiquetteAxeForm copyFrom(final EtiquetteAxe etiquetteAxe, final List<SelectOption> axes) {
        EtiquetteAxeForm bean = new EtiquetteAxeForm();
        bean.setId(etiquetteAxe.getId());
        bean.setDescription(etiquetteAxe.getDescription());
        bean.setNom(etiquetteAxe.getLibelle());
        bean.setIdAxe(etiquetteAxe.getIdAxe());
        bean.setIdAxeDefaults(axes);
        return bean;
    }

    public static EtiquetteAxeTable copyFrom(final EtiquetteAxe ea, final Axe axe) {
        EtiquetteAxeTable bean = new EtiquetteAxeTable();
        bean.setDescription(ea.getDescription());
        bean.setId(ea.getId());
        bean.setAxe(axe.getLibelle());
        bean.setNom(ea.getLibelle());
        return bean;
    }

    public static EtiquetteFinaliteForm copyFrom(final EtiquetteFinalite etq, final List<SelectOption> finalites) {
        EtiquetteFinaliteForm bean = new EtiquetteFinaliteForm();
        bean.setId(etq.getId());
        bean.setDescription(etq.getDescription());
        bean.setNom(etq.getLibelle());
        bean.setIdFinalite(etq.getIdFinalite());
        bean.setIdFinaliteDefaults(finalites);
        return bean;
    }

    public static EtiquetteFinaliteTable copyFrom(final EtiquetteFinalite etq, final Finalite finalite) {
        EtiquetteFinaliteTable bean = new EtiquetteFinaliteTable();
        bean.setId(etq.getId());
        bean.setDescription(etq.getDescription());
        bean.setNom(etq.getLibelle());
        bean.setFinalite(finalite.getLibelle());
        return bean;
    }

    public static EtiquetteIngenierieForm copyFrom(final EtiquetteIngenierie etq, final List<SelectOption> ingenierie) {
        EtiquetteIngenierieForm bean = new EtiquetteIngenierieForm();
        bean.setId(etq.getId());
        bean.setDescription(etq.getDescription());
        bean.setNom(etq.getLibelle());
        bean.setIdIngenierie(etq.getIdIngenierie());
        bean.setIdIngenierieDefaults(ingenierie);
        return bean;
    }

    public static EtiquetteIngenierieTable copyFrom(final EtiquetteIngenierie etq, final Ingenierie ingenierie) {
        EtiquetteIngenierieTable bean = new EtiquetteIngenierieTable();
        bean.setId(etq.getId());
        bean.setDescription(etq.getDescription());
        bean.setNom(etq.getLibelle());
        bean.setNature(ingenierie.getLibelle());
        return bean;
    }

    public static InnovationForm copyFrom(final QuestionsEvaluation questionsEvaluation) {
        InnovationForm bean = new InnovationForm();
        bean.setId(questionsEvaluation.getId());
        bean.setDescription(questionsEvaluation.getDescription());
        bean.setNom(questionsEvaluation.getTitre());
        return bean;
    }

    public static BusinessForm copyFrom(final Business business, List<SelectOption> actions) {
        BusinessForm bean = new BusinessForm();
        bean.setId(business.getId());
        bean.setEcocite(business.getEcocite());
        bean.setIdActionDefaults(actions);
        bean.setNom(business.getNom());
        bean.setIdAction(business.getIdAction());
        bean.setNomOperation(business.getNomOperation());
        bean.setNumeroOperation(business.getNumeroOperation());
        bean.setNumero(business.getNumeroAffaire());
        bean.setStatutFinancier(business.getStatutFinancier());
        bean.setTranche(business.getTranche());
        bean.setTypeFinancement(business.getTypeFinancement());
        if (business.getDateDebut() != null) {
            bean.setDateDebut(business.getDateDebut().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        if (business.getDateFin() != null) {
            bean.setDateFin(business.getDateFin().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        return bean;
    }

    public static BusinessTable copyFrom(final Business business, Action action) {
        BusinessTable bean = new BusinessTable();
        bean.setId(business.getId());
        bean.setNom(business.getNom());
        if (action == null) {
            bean.setNomAction("");
        } else {
            bean.setNomAction(action.getNomPublic());
        }
        bean.setNomOperation(business.getNomOperation());
        bean.setNumero(business.getNumeroAffaire());
        bean.setStatutFinancier(STATUT_FINANCIER_AFFAIRE.getLibelleForCode(business.getStatutFinancier()));
        bean.setTranche(TRANCHE_EXECUTION.getLibelleForCode(business.getTranche()));
        bean.setTypeFinancement(TYPE_FINANCEMENT.getLibelleForCode(business.getTypeFinancement()));
        return bean;
    }

    public static ContactTable copyFrom(final Contact contact, final Ecocite ecocite) {
        ContactTable bean = new ContactTable();
        bean.setNom(contact.getNom());
        bean.setPrenom(contact.getPrenom());
        bean.setEcocite(ecocite != null ? ecocite.getNom() : "");
        bean.setEntite(contact.getEntite());
        bean.setFonction(contact.getFonction());
        bean.setTelephone(contact.getTelephone());
        bean.setEmail(contact.getEmail());
        bean.setId(contact.getId());
        return bean;
    }

    public static ContactForm copyFrom(final Contact contact, final List<SelectOption> ecocites) {
        ContactForm bean = new ContactForm();
        bean.setNom(contact.getNom());
        bean.setPrenom(contact.getPrenom());
        bean.setIdEcocite(contact.getIdEcocite());
        bean.setIdEcociteDefaults(ecocites);
        bean.setEntite(contact.getEntite());
        bean.setFonction(contact.getFonction());
        bean.setTelephone(contact.getTelephone());
        bean.setEmail(contact.getEmail());
        bean.setId(contact.getId());
        return bean;
    }
}
