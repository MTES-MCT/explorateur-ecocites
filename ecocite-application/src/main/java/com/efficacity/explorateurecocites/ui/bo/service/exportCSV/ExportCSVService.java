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

package com.efficacity.explorateurecocites.ui.bo.service.exportCSV;

import com.efficacity.explorateurecocites.beans.biz.*;
import com.efficacity.explorateurecocites.beans.exportBean.*;
import com.efficacity.explorateurecocites.beans.model.*;
import com.efficacity.explorateurecocites.beans.service.*;
import com.efficacity.explorateurecocites.utils.enumeration.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ktoomey on 05/03/2018.
 */
@Service
public class ExportCSVService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExportCSVService.class);

    @Autowired
    ActionService actionServ;
    @Autowired
    EcociteService ecociteServ;
    @Autowired
    BusinessService businessServ;
    @Autowired
    ContactService contactServ;
    @Autowired
    ReponsesQuestionnaireEvaluationService reponsesQuestionnaireEvaluationServ;
    @Autowired
    QuestionQuestionnaireEvaluationService questionQuestionnaireEvaluationServ;
    @Autowired
    ReponsesEvaluationService reponsesEvaluationServ;
    @Autowired
    QuestionsEvaluationService questionsEvaluationServ;
    @Autowired
    AssoIndicateurDomaineService assoIndicateurDomaineServ;
    @Autowired
    AssoIndicateurObjectifService assoIndicateurObjectifServ;
    @Autowired
    AssoIndicateurObjetService assoIndicateurObjetServ;
    @Autowired
    IndicateurService indicateurServ;
    @Autowired
    MesureIndicateurService mesureIndicateurServ;
    @Autowired
    CibleIndicateurService cibleIndicateurServ;
    @Autowired
    AssoActionDomainService assoActionDomainServ;
    @Autowired
    EtiquetteAxeService etiquetteAxeServ;
    @Autowired
    AssoObjetObjectifService assoObjetObjectifServ;
    @Autowired
    EtiquetteFinaliteService etiquetteFinaliteServ;
    @Autowired
    FinaliteService finaliteServ;
    @Autowired
    AssoActionIngenierieService assoActionIngenierieService;
    @Autowired
    EtiquetteIngenierieService etiquetteIngenierieServ;
    @Autowired
    IngenierieService ingenierieServ;
    @Autowired
    AxeService axeServ;
    @Autowired
    EtapeService etapeServ;
    @Autowired
    RegionService regionServ;
    @Autowired
    AssoObjetContactService assoObjetContactServ;

    //EXPORT ACTIONS & ECOCITES
    public List<ActionExportCSVBean> getActionsExportData(List<Long> listeEcociteId) {
        //On créé la liste des lignes avec les information liées au modèle Action du REF ECO
        List<ActionExportCSVBean> listeActions = actionServ.findAllByListeEcocite(listeEcociteId)
                .stream()
                .sorted(Comparator.comparing(Action::getId))
                .map(ActionExportCSVBean::new)
                .collect(Collectors.toList());
        return getListeActionsToExport(listeActions);
    }

    public List<ActionExportCSVBean> getActionsExportData() {
        //On créé la liste des lignes avec les information liées au modèle Action
        List<ActionExportCSVBean> listeActions = actionServ.findAllByOrderByIdAsc()
                .stream()
                .sorted(Comparator.comparing(Action::getId))
                .map(ActionExportCSVBean::new)
                .collect(Collectors.toList());
        return getListeActionsToExport(listeActions);
    }

    private List<ActionExportCSVBean> getListeActionsToExport(List<ActionExportCSVBean> listeActions) {
        //On ajoute ensuite les informations liées aux autres services
        for (ActionExportCSVBean action : listeActions) {
            if (action != null && !action.getIdEcocite().equals("")) {
                Optional<Ecocite> optEcocite = ecociteServ.findOne(Long.parseLong(action.getIdEcocite()));
                optEcocite.ifPresent(ecocite -> action.setEcocite(ecocite.getNom()));
            }
            if (action != null && !action.getIdAction().equals("")) {
                ActionBean a = actionServ.findOneAction(Long.parseLong(action.getIdAction()));
                if (a != null) {
                    if (a.joinAllLibelleMaitriseOuvrage() != null) {
                        action.setMaitriseOuvrage(a.joinAllLibelleMaitriseOuvrage().replaceAll("[\n\r]", " "));
                    }
                    if (a.getIdAxe() != null) {
                        if (axeServ.findById(a.getIdAxe()) != null) {
                            Axe axe = axeServ.findById(a.getIdAxe());
                            if (axe != null && axe.getLibelle() != null) {
                                action.setAxePrincipal(axe.getLibelle().replaceAll("[\n\r]", " "));
                            }
                        }
                    }
                }
                List<EtapeBean> etapesAction = etapeServ.getEtapeByIdAction(Long.parseLong(action.getIdAction()));
                if (etapesAction != null) {
                    for (EtapeBean etape : etapesAction) {
                        if (etape != null) {
                            if (Objects.equals(ETAPE_ACTION.CARACTERISATION.getCode(), etape.getNom())) {
                                action.setEtapeCaracterisation(etape.getStatutEnum().getLibelle());
                            } else if (Objects.equals(ETAPE_ACTION.INDICATEUR.getCode(), etape.getNom())) {
                                action.setEtapeIndicateur(etape.getStatutEnum().getLibelle());
                            } else if (Objects.equals(ETAPE_ACTION.EVALUATION_INNOVATION.getCode(), etape.getNom())) {
                                action.setEtapeEvaluation(etape.getStatutEnum().getLibelle());
                            } else if (Objects.equals(ETAPE_ACTION.MESURE_INDICATEUR.getCode(), etape.getNom())) {
                                action.setEtapeMesure(etape.getStatutEnum().getLibelle());
                            } else if (Objects.equals(ETAPE_ACTION.CONTEXTE_ET_FACTEUR.getCode(), etape.getNom())) {
                                action.setEtapeContexte(etape.getStatutEnum().getLibelle());
                            }
                        }
                    }
                }
            }
        }
        return listeActions;
    }

    public List<EcociteExportCSVBean> getEcocitesExportData() {
        List<EcociteExportCSVBean> listeEcocites = ecociteServ.getListByOrderByIdAsc()
                .stream()
                .map(EcociteExportCSVBean::new)
                .collect(Collectors.toList());
        for (EcociteExportCSVBean ecocite : listeEcocites) {
            if (ecocite != null && !ecocite.getIdEcocite().equals("")) {
                Ecocite e = ecociteServ.findById(Long.parseLong(ecocite.getIdEcocite()));
                if (e != null && e.getIdRegion() != null) {
                    Region region = regionServ.findById(e.getIdRegion());
                    if (region != null) {
                        ecocite.setRegion(region.getNom());
                    }
                }
                List<EtapeBean> etapesEcocite = etapeServ.getEtapeByIdEcocite(Long.parseLong(ecocite.getIdEcocite()));
                if (etapesEcocite != null) {
                    for (EtapeBean etape : etapesEcocite) {
                        if (etape != null) {
                            if (Objects.equals(etape.getNom(), ETAPE_ECOCITE.CARACTERISATION.getCode())) {
                                ecocite.setEtapeCaracterisation(etape.getStatutEnum().getLibelle());
                            } else if (Objects.equals(etape.getNom(), ETAPE_ECOCITE.INDICATEUR.getCode())) {
                                ecocite.setEtapeIndicateur(etape.getStatutEnum().getLibelle());
                            } else if (Objects.equals(etape.getNom(), ETAPE_ECOCITE.MESURE_INDICATEUR.getCode())) {
                                ecocite.setEtapeMesure(etape.getStatutEnum().getLibelle());
                            } else if (Objects.equals(etape.getNom(), ETAPE_ECOCITE.CONTEXTE_ET_FACTEUR.getCode())) {
                                ecocite.setEtapeContexte(etape.getStatutEnum().getLibelle());
                            }
                        }
                    }
                }
            }
        }
        return listeEcocites;
    }

    public List<BusinessExportCSVBean> getBusinessExportData(Long idAction) {
        List<BusinessExportCSVBean> listeBusiness = businessServ.findByIdActionOrderByIdAsc(idAction)
                .stream()
                .map(BusinessExportCSVBean::new)
                .collect(Collectors.toList());
        for (BusinessExportCSVBean business : listeBusiness) {
            if (business != null) {
                ActionBean action = actionServ.findOneAction(idAction);
                if (action != null) {
                    business.setNomAction(action.getNomPublic());
                    business.setNumeroAction(action.getNumeroAction());
                }
            }
        }
        return listeBusiness;
    }

    public List<QuestionnaireExportCSVBean> getQuestionnaireExportData(Long idObjet, TYPE_OBJET objectType) {
        List<QuestionnaireExportCSVBean> listeQuestionnaires;
        switch (objectType.getCode()) {
            case "action":
                listeQuestionnaires = reponsesQuestionnaireEvaluationServ.findAllByIdObjetAndTypeObjetOrderByIdQuestionAsc(idObjet, TYPE_OBJET.ACTION.getCode())
                        .stream()
                        .map(QuestionnaireExportCSVBean::new)
                        .collect(Collectors.toList());
                break;
            case "ecocite":
                listeQuestionnaires = reponsesQuestionnaireEvaluationServ.findAllByIdObjetAndTypeObjetOrderByIdQuestionAsc(idObjet, TYPE_OBJET.ECOCITE.getCode())
                        .stream()
                        .map(QuestionnaireExportCSVBean::new)
                        .collect(Collectors.toList());
                break;
            default:
                LOGGER.info("getQuestionnaireExportData has a problem concerning action/ecocite");
                return new ArrayList<>();
        }
        for (QuestionnaireExportCSVBean questionnaire : listeQuestionnaires) {
            if (questionnaire != null) {
                if (!questionnaire.getIdQuestion().equals("")) {
                    QuestionQuestionnaireEvaluationBean questionQuestionnaireEvaluation =
                            questionQuestionnaireEvaluationServ.findById(Long.parseLong(questionnaire.getIdQuestion()));
                    if (questionQuestionnaireEvaluation != null && questionQuestionnaireEvaluation.getQuestion() != null) {
                        questionnaire.setQuestion(questionQuestionnaireEvaluation.getQuestion().replaceAll("\n", " ").replaceAll("\r", ""));
                        questionnaire.setCodeQuestionnaire(questionQuestionnaireEvaluation.getCodeQuestionnaire());
                    }
                }
                if (objectType.getCode().equals("action")) {
                    Action action = actionServ.findById(idObjet);
                    if (action != null) {
                        questionnaire.setNomObjet(action.getNomPublic());
                    }
                } else if (objectType.getCode().equals("ecocite")) {
                    Ecocite ecocite = ecociteServ.findById(idObjet);
                    if (ecocite != null) {
                        questionnaire.setNomObjet(ecocite.getNom());
                    }
                }
            }
        }
        return listeQuestionnaires;
    }

    public List<NiveauExportCSVBean> getNiveauExportData(Long idAction) {
        List<NiveauExportCSVBean> listeNiveaux = reponsesEvaluationServ.findByIdActionOrderByIdQuestionAsc(idAction)
                .stream()
                .map(NiveauExportCSVBean::new)
                .collect(Collectors.toList());
        for (NiveauExportCSVBean niveau : listeNiveaux) {
            if (niveau != null) {
                if (!Objects.equals(niveau.getIdQuestion(), "")) {
                    if (questionsEvaluationServ.findById(Long.parseLong(niveau.getIdQuestion())) != null) {
                        QuestionsEvaluationBean questionsEvaluation =
                                questionsEvaluationServ.findById(Long.parseLong(niveau.getIdQuestion()));
                        niveau.setQuestion(questionsEvaluation.getTitre());
                    }
                }
                Etape etapeInnovation = etapeServ.getEtapeByActionAndCode(idAction, ETAPE_ACTION.EVALUATION_INNOVATION.getCode());
                if (etapeInnovation != null && etapeInnovation.getCommentaire() != null) {
                    niveau.setCommentaire(etapeInnovation.getCommentaire().replaceAll("[\n\r]", " "));
                }
                niveau.setNomAction(actionServ.findById(idAction).getNomPublic());
            }
        }
        return listeNiveaux;
    }

    public List<ContactExportCSVBean> getContactExportData(Long idObjet, TYPE_OBJET objectType) {
        List<ContactExportCSVBean> listeContacts = new LinkedList<>();
        if (objectType.getCode().equals("action")) {
            listeContacts = (assoObjetContactServ.findAllAssoForAction(idObjet)
                    .stream()
                    .map(ContactExportCSVBean::new)
                    .collect(Collectors.toList()));
        } else if (objectType.getCode().equals("ecocite")) {
            listeContacts = assoObjetContactServ.findAllAssoForEcocite(idObjet)
                    .stream()
                    .map(ContactExportCSVBean::new)
                    .collect(Collectors.toList());
        }
        for (ContactExportCSVBean contactBean : listeContacts) {
            if (contactBean != null) {
                if (!contactBean.getIdContact().equals("")) {
                    if (!contactBean.getIdContact().equals("")) {
                        Optional<Contact> optContact = contactServ.findOne(Long.parseLong(contactBean.getIdContact()));
                        if (optContact.isPresent()) {
                            Contact contact = optContact.get();
                            contactBean.setNomContact(contact.getNom());
                            contactBean.setPrenomContact(contact.getPrenom());
                            contactBean.setEmail(contact.getEmail());
                        }
                    }
                }
                if (objectType.getCode().equals("action")) {
                    Action action = actionServ.findById(idObjet);
                    if (action != null) {
                        contactBean.setNomObjet(action.getNomPublic());
                    }
                } else if (objectType.getCode().equals("ecocite")) {
                    Ecocite ecocite = ecociteServ.findById(idObjet);
                    if (ecocite != null) {
                        contactBean.setNomObjet(ecocite.getNom());
                    }
                }
            }
        }
        return listeContacts;
    }

    public List<IndicateurExportCSVBean> getIndicateurExportData(Long idObjet, TYPE_OBJET typeObject) {
        List<AssoIndicateurObjet> listeAssoIndicateur;
        String etapeIndicateurRealisation;
        String etapeIndicateurResultatImpact;
        switch (typeObject) {
            case ACTION:
                listeAssoIndicateur = assoIndicateurObjetServ.findAllByActionOrderByIdIndicateurAsc(idObjet);
                etapeIndicateurRealisation = ETAPE_ACTION_EDITION.INDICATEUR_REALISATION.getCode();
                etapeIndicateurResultatImpact = ETAPE_ACTION_EDITION.INDICATEUR_RESULTAT_IMPACT.getCode();
                break;
            case ECOCITE:
                listeAssoIndicateur = assoIndicateurObjetServ.findAllByEcociteOrderByIdIndicateurAsc(idObjet);
                etapeIndicateurRealisation = ETAPE_ECOCITE_EDITION.INDICATEUR_REALISATION.getCode();
                etapeIndicateurResultatImpact = ETAPE_ECOCITE_EDITION.INDICATEUR_RESULTAT_IMPACT.getCode();
                break;
            default:
                LOGGER.info("getIndicateurExportData has a problem concerning action/ecocite");
                return new ArrayList<>();
        }
        Etape etapeRea = etapeServ.getEtapeByIdTypeAndCode(idObjet, typeObject, etapeIndicateurRealisation);
        Etape etapeRes = etapeServ.getEtapeByIdTypeAndCode(idObjet, typeObject, etapeIndicateurResultatImpact);
        Optional<Action> action = Optional.empty();
        Optional<Ecocite> ecocite = Optional.empty();
        switch (typeObject) {
            case ACTION:
                action = actionServ.findOne(idObjet);
                break;
            case ECOCITE:
                ecocite = ecociteServ.findOne(idObjet);
                break;
        }
        final String objectName = action.map(Action::getNomPublic).orElse(ecocite.map(Ecocite::getNom).orElse(""));
        return listeAssoIndicateur.stream().map(asso -> {
            IndicateurExportCSVBean indicateurBean = new IndicateurExportCSVBean(asso);
            indicateurBean.setNomObjet(objectName);
            if (etapeRea != null && etapeRea.getCommentaire() != null) {
                indicateurBean.setCommentaireOngletRealisation(etapeRea.getCommentaire().replaceAll("[\n\r]", " "));
            }
            if (etapeRes != null && etapeRes.getCommentaire() != null) {
                indicateurBean.setCommentaireOngletResultat(etapeRes.getCommentaire().replaceAll("[\n\r]", " "));
            }
            List<MesureIndicateur> listeMesures = mesureIndicateurServ.findAllByIdAssoIndicateurObjetOrderByDateSaisieAsc(asso.getId());
            List<CibleIndicateur> listeCibles = cibleIndicateurServ.findAllByIdAssoIndicateurObjetOrderByDateSaisieAsc(asso.getId());
            Optional<Indicateur> optIndicateur = indicateurServ.findOne(Long.parseLong(indicateurBean.getIdIndicateur()));
            optIndicateur.ifPresent(i -> {
                indicateurBean.setNatureIndicateur(i.getNature());
                indicateurBean.setEtatValidation(i.getEtatValidation());
                indicateurBean.setNomCourt(i.getNomCourt());
            });
            if (listeMesures != null && !listeMesures.isEmpty()) {
                StringBuilder mesures = new StringBuilder();
                StringBuilder datesMesures = new StringBuilder();
                for (MesureIndicateur mesure : listeMesures) {
                    mesures.append(mesure.getValeur()).append(";");
                    if (mesure.getDateSaisie() != null) {
                        datesMesures.append(mesure.getDateSaisie().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
                    }
                    datesMesures.append(";");
                }
                indicateurBean.setMesure(mesures.toString());
                indicateurBean.setDatesMesures(datesMesures.toString());
            }
            if (listeCibles != null && !listeCibles.isEmpty()) {
                StringBuilder cibles = new StringBuilder();
                StringBuilder datesCibles = new StringBuilder();
                for (CibleIndicateur cible : listeCibles) {
                    cibles.append(cible.getValeur()).append(";");
                    if (cible.getDateSaisie() != null) {
                        datesCibles.append(cible.getDateSaisie().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
                    }
                    datesCibles.append(";");
                }
                indicateurBean.setCible(cibles.toString());
                indicateurBean.setDatesCibles(datesCibles.toString());
            }
            indicateurBean.setPosteCalcul(indicateurBean.getPosteCalcul().replaceAll("[\n\r]", " "));
            return indicateurBean;
        }).collect(Collectors.toList());
    }

    public List<DomaineExportCSVBean> getDomaineExportData(Long idAction) {
        List<DomaineExportCSVBean> listeDomaines = assoActionDomainServ.getListByActionOrderByIdDomain(idAction)
                .stream()
                .map(DomaineExportCSVBean::new)
                .collect(Collectors.toList());
        for (DomaineExportCSVBean domaine : listeDomaines) {
            if (domaine != null) {
                if (!domaine.getIdEtiquetteDomaine().equals("")) {
                    if (etiquetteAxeServ.findById(Long.parseLong(domaine.getIdEtiquetteDomaine())) != null) {
                        EtiquetteAxe etiquetteAxe = etiquetteAxeServ.findById(Long.parseLong(domaine.getIdEtiquetteDomaine()));
                        if (etiquetteAxe != null) {
                            if (etiquetteAxe.getIdAxe() != null) {
                                domaine.setIdDomaine(etiquetteAxe.getIdAxe().toString());
                            }
                            domaine.setEtiquetteDomaine(etiquetteAxe.getLibelle());
                            Axe axe = axeServ.findById(etiquetteAxe.getIdAxe());
                            if (axe != null) {
                                domaine.setDomaine(axe.getLibelle());
                            }
                        }
                    }
                }
                Etape etape = etapeServ.getEtapeByActionAndCode(idAction, ETAPE_ACTION_EDITION.CATEGORIESATION_DOMAINE.getCode());
                if (etape != null && etape.getCommentaire() != null) {
                    domaine.setCommentaire(etape.getCommentaire().replaceAll("[\n\r]", " "));
                }
                Action action = actionServ.findById(idAction);
                if (action != null) {
                    domaine.setNomAction(action.getNomPublic());
                }
            }
        }
        return listeDomaines;
    }

    public List<ObjectifExportCSVBean> getObjectifExportData(Long idObject, TYPE_OBJET objectType) {
        List<ObjectifExportCSVBean> listeobjectifs;
        if (objectType.getCode().equals("action")) {
            listeobjectifs = assoObjetObjectifServ.getListByActionOrderByIdObjectifAsc(idObject)
                    .stream()
                    .map(ObjectifExportCSVBean::new)
                    .collect(Collectors.toList());
        } else if (objectType.getCode().equals(("ecocite"))) {
            listeobjectifs = assoObjetObjectifServ.getListByEcociteOrderByIdObjectifAsc(idObject)
                    .stream()
                    .map(ObjectifExportCSVBean::new)
                    .collect(Collectors.toList());
        } else {
            LOGGER.info("getObjectifExportData has a problem concerning action/ecocite");
            return new ArrayList<>();
        }
        for (ObjectifExportCSVBean objectif : listeobjectifs) {
            if (objectif != null) {
                if (!objectif.getIdEtiquetteObjectif().equals("")) {
                    EtiquetteFinalite etiquetteFinalite = etiquetteFinaliteServ.findById(Long.parseLong(objectif.getIdEtiquetteObjectif()));
                    if (etiquetteFinalite != null) {
                        if (etiquetteFinalite.getIdFinalite() != null) {
                            objectif.setIdObjectif(etiquetteFinalite.getIdFinalite().toString());
                        }
                        objectif.setEtiquetteObjectif(etiquetteFinalite.getLibelle());
                        Finalite finalite = finaliteServ.findById(etiquetteFinalite.getIdFinalite());
                        if (finalite != null) {
                            objectif.setObjectif(finalite.getLibelle());
                        }
                    }
                }
                Etape etape = new Etape();
                if (objectType.getCode().equals("action")) {
                    etape = etapeServ.getEtapeByActionAndCode(idObject, ETAPE_ACTION_EDITION.CATEGORIESATION_OBJECTIF.getCode());
                    Action action = actionServ.findById(idObject);
                    if (action != null) {
                        objectif.setNomObjet(action.getNomPublic());
                    }
                } else if (objectType.getCode().equals(("ecocite"))) {
                    etape = etapeServ.getEtapeByEcociteAndCode(idObject, ETAPE_ECOCITE_EDITION.CATEGORIESATION.getCode());
                    Ecocite ecocite = ecociteServ.findById(idObject);
                    if (ecocite != null) {
                        objectif.setNomObjet(ecocite.getNom());
                    }
                }
                if (etape != null && etape.getCommentaire() != null) {
                    objectif.setCommentaire(etape.getCommentaire().replaceAll("[\n\r]", " "));
                }
            }
        }
        return listeobjectifs;
    }

    public List<IngenierieExportCSVBean> getIngenierieExportData(Long idAction) {
        List<IngenierieExportCSVBean> listeIngenieries = assoActionIngenierieService.getListByActionOrderByIdIngenierieAsc(idAction)
                .stream()
                .map(IngenierieExportCSVBean::new)
                .collect(Collectors.toList());
        for (IngenierieExportCSVBean ingenierieBean : listeIngenieries) {
            if (ingenierieBean != null) {
                if (idAction != null) {
                    Action action = actionServ.findById(idAction);
                    if (action != null) {
                        ingenierieBean.setNomAction(action.getNomPublic());
                    }
                }
                if (!ingenierieBean.getIdEtiquetteIngenierie().equals("")) {
                    EtiquetteIngenierie etiquetteIngenierie = etiquetteIngenierieServ.findById(Long.parseLong(ingenierieBean.getIdEtiquetteIngenierie()));
                    if (etiquetteIngenierie != null) {
                        if (etiquetteIngenierie.getIdIngenierie() != null) {
                            ingenierieBean.setIdIngenierie(etiquetteIngenierie.getIdIngenierie().toString());
                        }
                        ingenierieBean.setEtiquetteIngenierie(etiquetteIngenierie.getLibelle());
                        Ingenierie ingenierie = ingenierieServ.findById(etiquetteIngenierie.getIdIngenierie());
                        if (ingenierie != null) {
                            ingenierieBean.setIngenierie(ingenierie.getLibelle());
                        }
                    }
                }
                Etape etape = etapeServ.getEtapeByActionAndCode(idAction, ETAPE_ACTION_EDITION.CATEGORIESATION_INGENIERIE.getCode());
                if (etape != null && etape.getCommentaire() != null) {
                    ingenierieBean.setCommentaire(etape.getCommentaire().replaceAll("[\n\r]", " "));
                }
            }
        }
        return listeIngenieries;
    }

    // EXPORT INDICATEURS
    public List<IndicateurDomaineExportCSVBean> getIndicateurDomaineExportData(Long idIndicateur) {
        List<IndicateurDomaineExportCSVBean> listeDomaines = assoIndicateurDomaineServ.getListByIndicateurOrderByIdDomaine(idIndicateur)
                .stream()
                .map(IndicateurDomaineExportCSVBean::new)
                .collect(Collectors.toList());
        for (IndicateurDomaineExportCSVBean domaine : listeDomaines) {
            if (domaine != null) {
                if (!domaine.getIdEtiquetteDomaine().equals("")) {
                    EtiquetteAxe etiquetteAxe = etiquetteAxeServ.findById(Long.parseLong(domaine.getIdEtiquetteDomaine()));
                    if (etiquetteAxe != null) {
                        if (etiquetteAxe.getIdAxe() != null) {
                            domaine.setIdDomaine(etiquetteAxe.getIdAxe().toString());
                        }
                        domaine.setEtiquetteDomaine(etiquetteAxe.getLibelle());
                        Axe axe = axeServ.findById(etiquetteAxe.getIdAxe());
                        if (axe != null) {
                            domaine.setDomaine(axe.getLibelle());
                        }
                    }
                }
                Optional<Indicateur> optIndicateur = indicateurServ.findOne(idIndicateur);
                optIndicateur.ifPresent(indicateur -> domaine.setNomCourt(indicateur.getNomCourt()));
            }
        }
        return listeDomaines;
    }

    public List<IndicateurObjectifExportCSVBean> getIndicateurObjectifExportData(Long idIndicateur) {
        List<IndicateurObjectifExportCSVBean> listeObjectifs = assoIndicateurObjectifServ.getListByIndicateurOrderByIdObjectifAsc(idIndicateur)
                .stream()
                .map(IndicateurObjectifExportCSVBean::new)
                .collect(Collectors.toList());
        for (IndicateurObjectifExportCSVBean objectif : listeObjectifs) {
            if (objectif != null) {
                if (!objectif.getIdEtiquetteObjectif().equals("")) {
                    EtiquetteFinalite etiquetteFinalite = etiquetteFinaliteServ.findById(Long.parseLong(objectif.getIdEtiquetteObjectif()));
                    if (etiquetteFinalite != null) {
                        if (etiquetteFinalite.getIdFinalite() != null) {
                            objectif.setIdObjectif(etiquetteFinalite.getIdFinalite().toString());
                        }
                        objectif.setEtiquetteObjectif(etiquetteFinalite.getLibelle());
                        Finalite finalite = finaliteServ.findById(etiquetteFinalite.getIdFinalite());
                        if (finalite != null) {
                            objectif.setObjectif(finalite.getLibelle());
                        }
                    }
                }
                Optional<Indicateur> optIndicateur = indicateurServ.findOne(idIndicateur);
                optIndicateur.ifPresent(indicateur -> objectif.setNomCourt(indicateur.getNomCourt()));
            }
        }
        return listeObjectifs;
    }

    public List<IndicateurActionExportCSVBean> getIndicateurActionExportData(Long idIndicateur) {
        List<IndicateurActionExportCSVBean> listeActions = assoIndicateurObjetServ.findAllByIdIndicateurAndTypeObjetOrderByIdObjet(idIndicateur, TYPE_OBJET.ACTION.getCode())
                .stream()
                .map(IndicateurActionExportCSVBean::new)
                .collect(Collectors.toList());
        for (IndicateurActionExportCSVBean actionBean : listeActions) {
            if (actionBean != null) {
                if (!actionBean.getIdAction().equals("")) {
                    Action action = actionServ.findById(Long.parseLong(actionBean.getIdAction()));
                    if (action != null) {
                        actionBean.setNomAction(action.getNomPublic());
                    }
                }
                Optional<Indicateur> optIndicateur = indicateurServ.findOne(idIndicateur);
                if (optIndicateur.isPresent()) {
                    Indicateur indicateur = optIndicateur.get();
                    actionBean.setNomCourt(indicateur.getNomCourt());
                }
            }
        }
        return listeActions;
    }

    public List<IndicateurEcociteExportCSVBean> getIndicateurEcociteExportData(Long idIndicateur) {
        List<IndicateurEcociteExportCSVBean> listeEcocites = assoIndicateurObjetServ.findAllByIdIndicateurAndTypeObjetOrderByIdObjet(idIndicateur, TYPE_OBJET.ECOCITE.getCode())
                .stream()
                .map(IndicateurEcociteExportCSVBean::new)
                .collect(Collectors.toList());
        for (IndicateurEcociteExportCSVBean ecociteBean : listeEcocites) {
            if (ecociteBean != null) {
                if (!ecociteBean.getIdEcocite().equals("")) {
                    Ecocite ecocite = ecociteServ.findById(Long.parseLong(ecociteBean.getIdEcocite()));
                    if (ecocite != null) {
                        ecociteBean.setNomEcocite(ecocite.getNom());
                    }
                }
                Optional<Indicateur> optIndicateur = indicateurServ.findOne(idIndicateur);
                if (optIndicateur.isPresent()) {
                    Indicateur indicateur = optIndicateur.get();
                    ecociteBean.setNomCourt(indicateur.getNomCourt());
                }
            }
        }
        return listeEcocites;
    }

    public List<IndicateurOnlyExportCSVBean> getIndicateurOnlyExportData() {
        return indicateurServ.getListByOrderByIdAsc()
                .stream()
                .map(IndicateurOnlyExportCSVBean::new)
                .collect(Collectors.toList());
    }

    // EXPORT CONTACTS UNIQUEMENT
    public List<ContactOnlyExportCSVBean> getContactOnlyExportData() {
        List<ContactOnlyExportCSVBean> listesContacts = contactServ.getListByOrderByIdAsc()
                .stream()
                .map(ContactOnlyExportCSVBean::new)
                .collect(Collectors.toList());
        for (ContactOnlyExportCSVBean contact : listesContacts) {
            if (contact != null) {
                if (!contact.getIdEcocite().equals("")) {
                    Ecocite ecocite = ecociteServ.findById(Long.parseLong(contact.getIdEcocite()));
                    if (ecocite != null) {
                        contact.setEcocite(ecocite.getNom());
                    }
                }
            }
        }
        return listesContacts;
    }

    // EXPORT BUSINESS UNIQUEMENT
    public List<BusinessOnlyExportCSVBean> getBusinessOnlyExportData() {
        List<BusinessOnlyExportCSVBean> listesBusiness = businessServ.getListOrderByIdAsc()
                .stream()
                .map(BusinessOnlyExportCSVBean::new)
                .collect(Collectors.toList());
        for (BusinessOnlyExportCSVBean businessBean : listesBusiness) {
            if (businessBean != null) {
                if (!businessBean.getIdAction().equals("")) {
                    Action action = actionServ.findById(Long.parseLong(businessBean.getIdAction()));
                    if (action != null) {
                        businessBean.setNumeroAction(action.getNumeroAction());
                        if (action.getIdEcocite() != null) {
                            businessBean.setIdEcocite(action.getIdEcocite().toString());
                        }
                    }
                }
            }
        }
        return listesBusiness;
    }

}
