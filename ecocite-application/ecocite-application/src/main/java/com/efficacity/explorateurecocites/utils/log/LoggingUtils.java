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

package com.efficacity.explorateurecocites.utils.log;

import com.efficacity.explorateurecocites.beans.model.Action;
import com.efficacity.explorateurecocites.beans.model.Axe;
import com.efficacity.explorateurecocites.beans.model.Business;
import com.efficacity.explorateurecocites.beans.model.Contact;
import com.efficacity.explorateurecocites.beans.model.Ecocite;
import com.efficacity.explorateurecocites.beans.model.Indicateur;
import com.efficacity.explorateurecocites.utils.enumeration.FILE_TYPE;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import isotope.modules.security.JwtUser;
import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.function.Supplier;

public class LoggingUtils {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy à HH:mm:ss");
    public static final String INFO_SEPARATOR = "\" | ";

    public enum ActionType {
        CREATION,
        MODIFICATION,
        SUPPRESSION,
        AJOUT,
        SOUMISSION,
        VALIDATION,
        ANNULATION,
        CONSULTATION;
    }

    public enum SecondaryType {
        CARACTERISATION,
        CARACTERISATION_INGENIERIE,
        CARACTERISATION_DOMAINES,
        CARACTERISATION_OBJECTIF,
        CHOIX_INDICATEURS,
        INNOVATION,
        RENSEIGNEMENT_INDICATEURS,
        RENSEIGNEMENT_INDICATEURS_CIBLE,
        RENSEIGNEMENT_INDICATEURS_MESURE,
        QUESTIONNAIRE,
        FICHIER_QUESTIONNAIRE,
        FICHIER_PRESENATION,
        KML_ACTION,
        KML_STRATEGIQUE,
        KML_OPERATIONNEL,
        FICHIER,
        IMAGE_PRINCIPALE,
        IMAGE_SECONDAIRE,
        NONE;
    }

    private static void getLogInfo(StringBuilder builder, ActionType type, JwtUser user) {
        switch (type) {
            case CREATION:
                builder.append("Créé le ");
                break;
            case MODIFICATION:
                builder.append("Modifié le ");
                break;
            case SUPPRESSION:
                builder.append("Supprimé le ");
                break;
            case AJOUT:
                builder.append("Ajouté le ");
                break;
            case SOUMISSION:
                builder.append("Envoyé pour validation le ");
                break;
            case VALIDATION:
                builder.append("Validé le ");
                break;
            case ANNULATION:
                builder.append("Annulé le ");
                break;
            case CONSULTATION:
                builder.append("Consulté le ");
                break;
        }
        builder.append(getCurrentTime());
        builder.append(" par ");
        builder.append(user.getEmail());
        builder.append(" | ");
    }

    private static String getCurrentTime() {
        return LocalDateTime.now().format(dateTimeFormatter);
    }

    public static void logFileEvent(final Logger logger, final ActionType type, FILE_TYPE fileType, Supplier<Action> actionSupplier,
                                    Supplier<Ecocite> ecociteSupplier, final JwtUser user) {
        if (fileType != null && fileType.getTypeObjet() != null) {
            if (Objects.equals(fileType.getTypeObjet(), TYPE_OBJET.ACTION)) {
                Action action = actionSupplier.get();
                if (action != null && user != null) {
                    logAction(logger, type, fileType, action, user);
                }
            } else if (Objects.equals(fileType.getTypeObjet(), TYPE_OBJET.ECOCITE)) {
                Ecocite ecocite = ecociteSupplier.get();
                if (ecocite != null && user != null) {
                    logAction(logger, type, fileType, ecocite, user);
                }
            }
        }
    }

    public static void logFileEvent(final Logger logger, final ActionType type, FILE_TYPE fileType, Supplier<Axe> axeSupplier, final JwtUser user) {
        if (fileType != null && fileType.getTypeObjet() != null && Objects.equals(fileType.getTypeObjet(), TYPE_OBJET.AXE)) {
            Axe axe = axeSupplier.get();
            if (axe != null && user != null) {
                logAction(logger, type, LoggingUtils.SecondaryType.FICHIER, axe, user);
            }
        }
    }

    public static void logAction(final Logger logger, final ActionType type, final Contact contact, final JwtUser user) {
        if (logger.isInfoEnabled() && user != null) {
            StringBuilder builder = new StringBuilder();
            getContactInfo(builder, contact);
            getLogInfo(builder, type, user);
            logger.info(builder.toString());
        }
    }

    public static void logAction(final Logger logger, final ActionType type, final Business business, final JwtUser user) {
        if (logger.isInfoEnabled() && user != null) {
            StringBuilder builder = new StringBuilder();
            getBusinessInfo(builder, business);
            getLogInfo(builder, type, user);
            logger.info(builder.toString());
        }
    }

    public static void logAction(final Logger logger, final ActionType type, final Indicateur indicateur, final JwtUser user) {
        if (logger.isInfoEnabled() && user != null) {
            StringBuilder builder = new StringBuilder();
            getIndicateurInfo(builder, indicateur);
            getLogInfo(builder, type, user);
            logger.info(builder.toString());
        }
    }

    public static void logAction(final Logger logger, final ActionType type, final SecondaryType secondaryType, final Action action, final JwtUser user) {
        if (logger.isInfoEnabled() && user != null) {
            StringBuilder builder = new StringBuilder();
            getActionInfo(builder, action);
            getLogSecondaryInfo(builder, secondaryType);
            getLogInfo(builder, type, user);
            logger.info(builder.toString());
        }
    }

    public static void logActionSupplierA(final Logger logger, final ActionType type, final SecondaryType secondaryType, final Supplier<Action> actionSupplier, final JwtUser user) {
        if (logger.isInfoEnabled() && user != null) {
            StringBuilder builder = new StringBuilder();
            Action action = actionSupplier.get();
            getActionInfo(builder, action);
            getLogSecondaryInfo(builder, secondaryType);
            getLogInfo(builder, type, user);
            logger.info(builder.toString());
        }
    }

    public static void logAction(final Logger logger, final ActionType type, final FILE_TYPE fileType, final Action action, final JwtUser user) {
        if (logger.isInfoEnabled() && user != null) {
            StringBuilder builder = new StringBuilder();
            getActionInfo(builder, action);
            manageFileType(builder, fileType);
            getLogInfo(builder, type, user);
            logger.info(builder.toString());
        }
    }

    public static void logAction(final Logger logger, final ActionType type, final SecondaryType secondaryType, final Ecocite ecocite, final JwtUser user) {
        if (logger.isInfoEnabled() && user != null) {
            StringBuilder builder = new StringBuilder();
            getEcociteInfo(builder, ecocite);
            getLogSecondaryInfo(builder, secondaryType);
            getLogInfo(builder, type, user);
            logger.info(builder.toString());
        }
    }

    public static void logActionSupplierE(final Logger logger, final ActionType type, final SecondaryType secondaryType, final Supplier<Ecocite> ecociteSupplier, final JwtUser user) {
        if (logger.isInfoEnabled() && user != null) {
            StringBuilder builder = new StringBuilder();
            Ecocite ecocite = ecociteSupplier.get();
            getEcociteInfo(builder, ecocite);
            getLogSecondaryInfo(builder, secondaryType);
            getLogInfo(builder, type, user);
            logger.info(builder.toString());
        }
    }

    public static void logAction(final Logger logger, final ActionType type, final SecondaryType secondaryType, final Supplier<Action> actionSupplier,
                                 final Supplier<Ecocite> ecociteSupplier, final JwtUser user, final TYPE_OBJET typeObjet) {
        if (Objects.equals(typeObjet, TYPE_OBJET.ACTION)) {
            LoggingUtils.logActionSupplierA(logger, type, secondaryType, actionSupplier, user);
        } else {
            LoggingUtils.logActionSupplierE(logger, type, secondaryType, ecociteSupplier, user);
        }
    }

    public static void logAction(final Logger logger, final ActionType type, final FILE_TYPE fileType, final Ecocite ecocite, final JwtUser user) {
        if (logger.isInfoEnabled() && user != null) {
            StringBuilder builder = new StringBuilder();
            getEcociteInfo(builder, ecocite);
            manageFileType(builder, fileType);
            getLogInfo(builder, type, user);
            logger.info(builder.toString());
        }
    }

    public static void logAction(final Logger logger, final ActionType type, final SecondaryType secondaryType, final Axe axe, final JwtUser user) {
        if (logger.isInfoEnabled() && user != null) {
            StringBuilder builder = new StringBuilder();
            getAxeInfo(builder, axe);
            getLogSecondaryInfo(builder, secondaryType);
            getLogInfo(builder, type, user);
            logger.info(builder.toString());
        }
    }

    private static void getIndicateurInfo(final StringBuilder builder, final Indicateur indicateur) {
        if (indicateur != null) {
            builder.append("Indicateur: \"");
            builder.append(indicateur.getId());
            builder.append(" - ");
            builder.append(indicateur.getNomCourt());
            builder.append(INFO_SEPARATOR);
        }
    }

    private static void getContactInfo(final StringBuilder builder, final Contact contact) {
        if (contact != null) {
            builder.append("Contact: \"");
            builder.append(contact.getId());
            builder.append(" - ");
            builder.append(contact.getEmail());
            builder.append(INFO_SEPARATOR);
        }
    }

    private static void getBusinessInfo(final StringBuilder builder, final Business business) {
        if (business != null) {
            builder.append("Affaire: \"");
            builder.append(business.getId());
            builder.append(" - ");
            builder.append(business.getNumeroAffaire());
            builder.append(INFO_SEPARATOR);
        }
    }

    private static void getAxeInfo(final StringBuilder builder, final Axe axe) {
        if (axe != null) {
            builder.append("Axe: \"");
            builder.append(axe.getId());
            builder.append(" - ");
            builder.append(axe.getLibelle());
            builder.append(INFO_SEPARATOR);
        }
    }

    private static void getActionInfo(final StringBuilder builder, final Action action) {
        if (action != null) {
            builder.append("Action: \"");
            builder.append(action.getId());
            builder.append(" - ");
            builder.append(action.getNomPublic());
            builder.append(INFO_SEPARATOR);
        }
    }

    private static void getEcociteInfo(final StringBuilder builder, final Ecocite ecocite) {
        if (ecocite != null) {
            builder.append("Ecocite: \"");
            builder.append(ecocite.getId());
            builder.append(" - ");
            builder.append(ecocite.getNom());
            builder.append(INFO_SEPARATOR);
        }
    }

    private static void getLogSecondaryInfo(StringBuilder builder, SecondaryType type) {
        if (type != null) {
            switch (type) {
                case CARACTERISATION:
                    builder.append("Etape : 'caractérisation de l'action' | ");
                    break;
                case CARACTERISATION_INGENIERIE:
                    builder.append("Etape : 'types de mission ingenierie' | ");
                    break;
                case CARACTERISATION_DOMAINES:
                    builder.append("Etape : 'domaines d'action' | ");
                    break;
                case CARACTERISATION_OBJECTIF:
                    builder.append("Etape : 'objectifs de la ville durable' | ");
                    break;
                case CHOIX_INDICATEURS:
                    builder.append("Etape : 'choix des Indicateurs' | ");
                    break;
                case INNOVATION:
                    builder.append("Etape : 'evaluation de l'innovation' | ");
                    break;
                case RENSEIGNEMENT_INDICATEURS:
                    builder.append("Etape : 'renseignement des Indicateurs' | ");
                    break;
                case RENSEIGNEMENT_INDICATEURS_CIBLE:
                    builder.append("Etape : 'renseignement des Indicateurs ' | Cible ");
                    break;
                case RENSEIGNEMENT_INDICATEURS_MESURE:
                    builder.append("Etape : 'renseignement des Indicateurs' | Mesure ");
                    break;
                case QUESTIONNAIRE:
                    builder.append("Etape : 'evaluation qualitative' | ");
                    break;
                case FICHIER_QUESTIONNAIRE:
                    builder.append("Fichier : 'fiche de synthèse' | ");
                    break;
                case FICHIER_PRESENATION:
                    builder.append("Fichier : 'documents' | ");
                    break;
                case IMAGE_PRINCIPALE:
                    builder.append("Fichier : 'image principale' | ");
                    break;
                case IMAGE_SECONDAIRE:
                    builder.append("Fichier : 'images secondaires' | ");
                    break;
                case KML_ACTION:
                    builder.append("Fichier : 'périmètre' | ");
                    break;
                case KML_STRATEGIQUE:
                    builder.append("Fichier : 'périmètre stratégique' | ");
                    break;
                case KML_OPERATIONNEL:
                    builder.append("Fichier : 'périmètre opérationnel' | ");
                    break;
                case FICHIER:
                    builder.append("Fichier | ");
                    break;
                case NONE:
                    break;
            }
        }
    }

    private static void manageFileType(final StringBuilder builder, final FILE_TYPE fileType) {
        switch (fileType) {
            case ACTION_DOCUMENT:
            case ECOCITE_DOCUMENT:
                getLogSecondaryInfo(builder, SecondaryType.FICHIER_PRESENATION);
                break;
            case ECOCITE_IMAGE_PRINCIPALE:
            case ACTION_IMAGE_PRINCIPALE:
                getLogSecondaryInfo(builder, SecondaryType.IMAGE_PRINCIPALE);
                break;
            case ECOCITE_IMAGE_SECONDAIRE:
            case ACTION_IMAGE_SECONDAIRE:
                getLogSecondaryInfo(builder, SecondaryType.IMAGE_SECONDAIRE);
                break;
            case ACTION_PERIMETRE:
                getLogSecondaryInfo(builder, SecondaryType.KML_ACTION);
                break;
            case ECOCITE_PERIMETRE_STRATEGIQUE:
                getLogSecondaryInfo(builder, SecondaryType.KML_STRATEGIQUE);
                break;
            case ECOCITE_PERIMETRE_OPERATIONNEL:
                getLogSecondaryInfo(builder, SecondaryType.KML_OPERATIONNEL);
                break;
            case QUESTIONNAIRE_ACTION_SYNTHESE:
            case QUESTIONNAIRE_ECOCITE_SYNTHESE:
                getLogSecondaryInfo(builder, SecondaryType.FICHIER_QUESTIONNAIRE);
                break;
            case AXE_ICON:
                break;
            default:
                break;
        }
    }
}
