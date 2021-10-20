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

package com.efficacity.explorateurecocites.beans.biz;

import com.efficacity.explorateurecocites.beans.model.Action;
import com.efficacity.explorateurecocites.beans.model.Business;
import com.efficacity.explorateurecocites.ui.bo.utils.DateUtils;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import com.efficacity.explorateurecocites.utils.enumeration.*;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import  com.efficacity.explorateurecocites.utils.ApplicationConstants;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe qui étent le bean model du générator
 *
 * Date de génération : 06/02/2018
 */
public class ActionBean {

    private Action to;
    private List<QuestionsAvecReponseBean> questionsAvecReponseBeanList;

    private List<FileUploadBean> documents;
    private List<AdapteurImagesBeans> imagesBeans;
    private EtiquettesBean etiquettesList;
    private AdapteurIndicateursBean indicateursBean;
    private AdaptateurEtapesBean etapes;
    private List<Business> business;

    public Action getTo() {
        return to;
    }

    public ActionBean(final Action action, final List<EtapeBean> etapes, final List<QuestionsAvecReponseBean> questions, final List<Business> business) {
        super();
        this.to = action;
        this.etapes = new AdaptateurEtapesBean(TYPE_OBJET.ACTION, etapes);
        this.questionsAvecReponseBeanList = questions;
        this.business = business;
    }

    public ActionBean(final Action action, final List<FileUploadBean> fileAction, final EtiquettesBean etiquettesList, final AdapteurIndicateursBean indicateursBean, final List<QuestionsAvecReponseBean> questions, final AdaptateurEtapesBean etapes, final List<Business> business) {
        super();
        this.to = action;
        imagesBeans = new ArrayList<>();
        documents = new ArrayList<>();
        fileAction.stream()
                .filter(f -> f.getTypeEnum() == FILE_TYPE.ACTION_IMAGE_PRINCIPALE ||f.getTypeEnum() == FILE_TYPE.ACTION_IMAGE_SECONDAIRE)
                .sorted(Comparator.reverseOrder())
                .forEach(f -> imagesBeans.add(new AdapteurImagesBeans(f)));
        fileAction.stream()
                .filter(f -> f.getTypeEnum() == FILE_TYPE.ACTION_DOCUMENT)
                .forEach(f-> documents.add(new FileUploadBean(f.getTo())));
        this.etiquettesList = etiquettesList;
        this.indicateursBean = indicateursBean;
        this.questionsAvecReponseBeanList = questions;
        this.etapes = etapes;
        this.business = business;
    }

    public boolean isEtapeIndicateurDone() {
        return etapes.getEtapeList().stream().anyMatch(etape -> etape.getEtapeEnumAction() == ETAPE_ACTION.INDICATEUR && etape.getStatutEnum() == ETAPE_STATUT.VALIDER);
    }

    public EtapeBean getEtapeCaraterisation() {
        if (etapes == null || etapes.getEtapeList() == null) {
            return null;
        }
        return etapes.getEtapeList().stream().filter(e -> ETAPE_ACTION.CARACTERISATION.getCode().equals(e.getTo().getCodeEtape())).findFirst().orElse(null);
    }

    public EtapeBean getEtapeChoixIndicateur() {
        if (etapes == null || etapes.getEtapeList() == null) {
            return null;
        }
        return etapes.getEtapeList().stream().filter(e -> ETAPE_ACTION.INDICATEUR.getCode().equals(e.getTo().getCodeEtape())).findFirst().orElse(null);
    }

    public EtapeBean getEtapeEvaluationInnovation() {
        if (etapes == null || etapes.getEtapeList() == null) {
            return null;
        }
        return etapes.getEtapeList().stream().filter(e -> ETAPE_ACTION.EVALUATION_INNOVATION.getCode().equals(e.getTo().getCodeEtape())).findFirst().orElse(null);
    }

    public EtapeBean getEtapeRenseignementIndicateur() {
        if (etapes == null || etapes.getEtapeList() == null) {
            return null;
        }
        EtapeBean etapeBean = etapes.getEtapeList().stream()
                .filter(e -> ETAPE_ACTION.MESURE_INDICATEUR.getCode().equals(e.getTo().getCodeEtape()))
                .findFirst()
                .orElse(null);
        if (etapeBean != null &&
                etapes.getEtapeList().stream()
                        .noneMatch(etape -> etape.getEtapeEnumAction() == ETAPE_ACTION.INDICATEUR && etape.getStatutEnum() == ETAPE_STATUT.VALIDER)) {
            etapeBean.setStatut(ETAPE_STATUT.IMPOSSIBLE.getCode());
        }
        return etapeBean;
    }

    public EtapeBean getEtapeEvaluationFacteur() {
        if (etapes == null || etapes.getEtapeList() == null) {
            return null;
        }
        EtapeBean etapeBean = etapes.getEtapeList().stream()
                .filter(e -> ETAPE_ACTION.CONTEXTE_ET_FACTEUR.getCode().equals(e.getTo().getCodeEtape()))
                .findFirst().orElse(null);
        if (Objects.equals(this.getEtatAvancement(), ETAT_AVANCEMENT.PREVU.getCode()) && etapeBean != null) {
            etapeBean.setStatut(ETAPE_STATUT.IMPOSSIBLE.getCode());
        }
        return etapeBean;
    }

    public List<EtapeBean> getListEtapesTriees() {
        List<EtapeBean> list = getEtapesList();
        if (list != null && !list.isEmpty()) {
            list.removeIf(item -> item.getEtapeEnumAction() == null);
            list.sort(Comparator.comparing(o -> o.getEtapeEnumAction().getOrder()));
        }
        return list;
    }

    public ETAPE_STATUT getStatusEnumEtape(EtapeBean etapeBean) {
        if (etapeBean.getEtapeEnumAction() != null && etapeBean.getEtapeEnumAction() == ETAPE_ACTION.MESURE_INDICATEUR) {
            return etapes.getEtapeList()
                    .stream()
                    .anyMatch(etape -> etape.getEtapeEnumAction() == ETAPE_ACTION.INDICATEUR && etape.getStatutEnum() == ETAPE_STATUT.VALIDER)
                    ? etapeBean.getStatutEnum() : ETAPE_STATUT.IMPOSSIBLE;
        } else if (etapeBean.getEtapeEnumAction() != null && etapeBean.getEtapeEnumAction() == ETAPE_ACTION.CONTEXTE_ET_FACTEUR) {
            return Objects.equals(ETAT_AVANCEMENT.PREVU.getCode(), this.getEtatAvancement()) ? ETAPE_STATUT.IMPOSSIBLE : etapeBean.getStatutEnum() ;
        } else {
            return etapeBean.getStatutEnum();
        }
    }

    public boolean isTypeFinancementIngenierie() {
        return TYPE_FINANCEMENT.INGENIERIE.equals(getTypeFinancementEnum());
    }
    public boolean isTypeFinancementAuMoinsIngenierie() {
        return (TYPE_FINANCEMENT.INGENIERIE.equals(getTypeFinancementEnum()) ||
                TYPE_FINANCEMENT.INGENIERIE_PRISE_PARTICIPATION.equals(getTypeFinancementEnum()) ||
                TYPE_FINANCEMENT.INGENIERIE_INVESTISSEMENT.equals(getTypeFinancementEnum()));
    }
    public boolean isTypeFinancementInvestissement() {
        return TYPE_FINANCEMENT.INVESTISSEMENT.equals(getTypeFinancementEnum());
    }
    public boolean isTypeFinancementInvestissementOuPriseParticipation() {
        return TYPE_FINANCEMENT.INVESTISSEMENT.equals(getTypeFinancementEnum()) || TYPE_FINANCEMENT.PRISE_PARTICIPATION.equals(getTypeFinancementEnum());
    }

    public boolean isTypeFinancementIngenierieEtInvestissement() {
        return TYPE_FINANCEMENT.INGENIERIE_INVESTISSEMENT.equals(getTypeFinancementEnum());
    }

    public boolean isTypeFinancementIngenierieEtPriseParticipation() {
        return TYPE_FINANCEMENT.INGENIERIE_PRISE_PARTICIPATION.equals(getTypeFinancementEnum());
    }

    public TYPE_FINANCEMENT getTypeFinancementEnum() {
        return TYPE_FINANCEMENT.getByCode(this.to.getTypeFinancement());
    }

    public List<MAITRISE_OUVRAGE> getMaitriseFromEnum() {
        if (this.to.getMaitriseOuvrage() == null) {
            return new ArrayList<>();
        }
        return Arrays.stream(this.to.getMaitriseOuvrage().split(";"))
                .map(MAITRISE_OUVRAGE::getByCode)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<String> getAllMaitriseOuvrage() {
        if (this.to.getMaitriseOuvrage() == null) {
            return new ArrayList<>();
        }
        return Arrays.stream(this.to.getMaitriseOuvrage().split(";"))
                .map(MAITRISE_OUVRAGE::getLibelleForCode)
                .collect(Collectors.toList());
    }
    public String joinAllLibelleMaitriseOuvrage() {
        if (this.to.getMaitriseOuvrage() == null) {
            return "";
        }
        return Arrays.stream(this.to.getMaitriseOuvrage().split(";"))
                .map(MAITRISE_OUVRAGE::getLibelleForCode)
                .collect(Collectors.joining(";"));
    }

    public List<String> getMaitriseNotFromEnum() {
        if (this.to.getMaitriseOuvrage() == null) {
            return new ArrayList<>();
        }
        return Arrays.stream(this.to.getMaitriseOuvrage().split(";"))
                .filter(o -> !MAITRISE_OUVRAGE.isDefined(o))
                .map(MAITRISE_OUVRAGE::getLibelleForCode)
                .collect(Collectors.toList());
    }

    public String getTypeFinancementLibelle(){
        if (getTypeFinancementEnum().getLibelle()!=null) {
            return getTypeFinancementEnum().getLibelle();
        }
        else{
            return "";
        }
    }

    public  ETAT_AVANCEMENT getEtatAvancementEnum() {
        return ETAT_AVANCEMENT.getByCode(this.to.getEtatAvancement());
    }

    public ECHELLE_ACTION getEchelleEnum() {
        return ECHELLE_ACTION.getByCode(this.to.getEchelle());
    }

    public ETAT_PUBLICATION getEtatPublicationEnum() {
        return ETAT_PUBLICATION.getByCode(this.to.getEtatPublication());
    }

    public List<EtapeBean> getEtapesList(){
        return this.etapes.getEtapeList();
    }

    public List<QuestionsAvecReponseBean> getQuestionsAvecReponseBeanList() {
        return questionsAvecReponseBeanList;
    }
    public List<QuestionsAvecReponseBean> getQuestions() {
        return questionsAvecReponseBeanList;
    }

    public Long getId() {
        return this.to.getId();
    }

    public void setId(Long id) {
        this.to.setId(id);
    }

    public Long getIdEcocite() {
        return this.to.getIdEcocite();
    }

    public void setIdEcocite(Long idEcocite) {
        this.to.setIdEcocite(idEcocite);
    }

    public Long getIdAxe() {
        return this.to.getIdAxe();
    }

    public void setIdAxe(Long idAxe) {
        this.to.setIdAxe(idAxe);
    }

    public Integer getEvaluationNiveauGlobal() {
        return this.to.getEvaluationNiveauGlobal();
    }

    public EVALUATION_NIVEAU_INNOVATION_GLOBAL getEvaluationNiveauGlobalEnum() {
        return EVALUATION_NIVEAU_INNOVATION_GLOBAL.getByValue(this.to.getEvaluationNiveauGlobal());
    }

    public void setEvaluationNiveauGlobal(Integer evaluationNiveauGlobal) {
        this.to.setEvaluationNiveauGlobal(evaluationNiveauGlobal);
    }

    public String getNomPublic() {
        return this.to.getNomPublic();
    }

    public void setNomPublic(String nomPublic) {
        this.to.setNomPublic(nomPublic);
    }

    public String getNumeroAction() {
        return this.to.getNumeroAction();
    }

    public void setNumeroAction(String numeroAction) {
        this.to.setNumeroAction(numeroAction);
    }

    public LocalDateTime getDateDebut() {
        return this.to.getDateDebut();
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.to.setDateDebut(dateDebut);
    }

    public LocalDateTime getDateFin() {
        return this.to.getDateFin();
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.to.setDateFin(dateFin);
    }

    public String getLatitude() {
        return this.to.getLatitude();
    }

    public void setLatitude(String latitude) {
        this.to.setLatitude(latitude);
    }

    public String getLongitude() {
        return this.to.getLongitude();
    }

    public void setLongitude(String longitude) {
        this.to.setLongitude(longitude);
    }

    public String getDescription() {
        return this.to.getDescription();
    }

    public String getDescriptionNoLineBreak() {
        if (to.getDescription() != null) {
            return this.to.getDescription().replaceAll("\r?\n", "<br/>").replaceAll("\"", "\\\\\"");
        }
        return "";
    }

    public void setDescription(String description) {
        this.to.setDescription(description);
    }

    public String getLien() {
        return this.to.getLien();
    }

    public void setLien(String lien) {
        this.to.setLien(lien);
    }

    public String getEchelle() {
        return this.to.getEchelle();
    }

    public void setEchelle(String echelle) {
        this.to.setEchelle(echelle);
    }

    public String getEtatAvancement() {
        return this.to.getEtatAvancement();
    }

    public void setEtatAvancement(String etatAvancement) {
        this.to.setEtatAvancement(etatAvancement);
    }

    public String getTypeFinancement() {
        return this.to.getTypeFinancement();
    }

    public void setTypeFinancement(String typeFinancement) {
        this.to.setTypeFinancement(typeFinancement);
    }

    public String getEtatPublication() {
        return this.to.getEtatPublication();
    }

    public void setEtatPublication(String etatPublication) {
        this.to.setEtatPublication(etatPublication);
    }

    public String getTrancheExecution() {
        if(CustomValidator.isEmpty(this.to.getTrancheExecution()) && business != null){
            // La tranche d'exécution est déterminée par le(s) affaire(s) associée(s)
            for(Business business : getBusiness()){
                if(CustomValidator.isEmpty(this.to.getTrancheExecution())){
                    // On set le premier
                    setTrancheExecution(business.getTranche());
                } else {
                    if(getTrancheExecution().equals(TRANCHE_EXECUTION.TRANCHE_1_et_2.getCode())){
                        // Si déjà 1 et 2 on break
                        break;
                    }
                    if((getTrancheExecution().equals(TRANCHE_EXECUTION.TRANCHE_1.getCode()) && business.getTranche().equals(TRANCHE_EXECUTION.TRANCHE_2.getCode()))
                            || (getTrancheExecution().equals(TRANCHE_EXECUTION.TRANCHE_2.getCode()) && business.getTranche().equals(TRANCHE_EXECUTION.TRANCHE_1.getCode()))){
                        // SI 1 et y a 2 ou inversement ont set le double et break
                        setTrancheExecution(TRANCHE_EXECUTION.TRANCHE_1_et_2.getCode());
                        break;
                    }
                }
            }
        }
        return this.to.getTrancheExecution();
    }

    public TRANCHE_EXECUTION getTrancheExecutionEnum() {
        return TRANCHE_EXECUTION.getByCode(to.getTrancheExecution());
    }

    public void setTrancheExecution(String trancheExecution) {
        this.to.setTrancheExecution(trancheExecution);
    }

    public EtapeBean getEtapeByStatus(final ETAPE_ACTION etapeAction) {
        return etapes.getEtapeList().stream().filter(etape -> etape.getEtapeEnumAction() == etapeAction).findFirst().orElse(null);
    }


    public void setQuestionsAvecReponseBeanList(final List<QuestionsAvecReponseBean> questionsAvecReponseBeanList) {
        this.questionsAvecReponseBeanList = questionsAvecReponseBeanList;
    }

    public List<FileUploadBean> getDocuments() {
        return documents;
    }

    public void setDocuments(final List<FileUploadBean> documents) {
        this.documents = documents;
    }

    public List<AdapteurImagesBeans> getImagesBeans() {
        return imagesBeans;
    }

    public void setImagesBeans(final List<AdapteurImagesBeans> imagesBeans) {
        this.imagesBeans = imagesBeans;
    }

    public EtiquettesBean getEtiquettesList() {
        return etiquettesList;
    }

    public void setEtiquettesList(final EtiquettesBean etiquettesList) {
        this.etiquettesList = etiquettesList;
    }

    public AdapteurIndicateursBean getIndicateursBean() {
        return indicateursBean;
    }

    public void setIndicateursBean(final AdapteurIndicateursBean indicateursBean) {
        this.indicateursBean = indicateursBean;
    }

    public String getEmailUserModification(){
        return this.to.getUserModification();
    }

    public String getHeureModificationAffichable(){
        if(this.to.getDateModificationFo() != null){
            return DateUtils.getHeureAffichable(this.to.getDateModificationFo());
        }
        return "";
    }

    public String getDateModificationAffichable(){
        if(this.to.getDateModificationFo() != null){
            return DateUtils.getDateAffichable(this.to.getDateModificationFo());
        }
        return "";
    }

    public List<Business> getBusiness() {
        return business;
    }

    public void setBusiness(List<Business> business) {
        this.business = business;
    }

    public AdaptateurEtapesBean getEtapes() {
        return etapes;
    }

    public void setEtapes(final AdaptateurEtapesBean etapes) {
        this.etapes = etapes;
    }

    public void validateChamps(String idChamps, Object value, MessageSource messages, Errors errors, Locale locale){
        switch (idChamps) {
            case "id":
                // On fait, rien au pire exception lors du save sur l'unicité
                break;
            case "idEcocite":
                if(CustomValidator.isEmpty(value)){
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE,ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, null, locale));
                }
                break;
            case "evalNiveau":
                if(CustomValidator.isEmpty(value)){
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE,ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, null, locale));
                }
                break;
            case "nomPublic":
                if(CustomValidator.isEmpty(value)){
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE,ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, null, locale));
                }
                break;
            case "numeroAction":
                if(CustomValidator.isEmpty(value)){
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE,ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, null, locale));
                }
                break;
            case "maitriseOuvrage":
                if(value.toString().split(";").length > 5){
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE,ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, null, locale));
                }
                break;
            case "dateDebut":
                if(CustomValidator.isEmpty(value)){
                    break;
                } else if(CustomValidator.isNotEmpty(this.to.getDateFin())&&(this.to.getDateDebut().isAfter(this.to.getDateFin()))){
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE,"error.attribut.dateDebut.after", messages.getMessage("error.attribut.dateDebut.after", null, locale));
                }
                break;
            case "dateFin":
                if(CustomValidator.isEmpty(value)){
                   break;
                } else if(CustomValidator.isNotEmpty(this.to.getDateDebut())&&(this.to.getDateFin()).isBefore(this.to.getDateDebut()) ){
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE, "error.attribut.dateFin.before", messages.getMessage("error.attribut.dateFin.before", null, locale));
                }
                break;
            case "latitude":
                if(CustomValidator.isEmpty(value)){
                    break;
                } else if (!CustomValidator.isSignedNumeric((String)value)) {
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE, ApplicationConstants.ERROR_ATTRIBUT_FORMAT, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_FORMAT, null, locale));
                } else if (!CustomValidator.checkRange((String)value, -90D, 90D)){
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE, ApplicationConstants.ERROR_ATTRIBUT_RANGE, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_RANGE, new Object[]{"La latitude ", -90, 90}, locale));
                }
                break;
            case "longitude":
                if(CustomValidator.isEmpty(value)){
                    break;
                } else if (!CustomValidator.isSignedNumeric((String)value)) {
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE, ApplicationConstants.ERROR_ATTRIBUT_FORMAT, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_FORMAT, null, locale));
                } else if (!CustomValidator.checkRange((String)value, -180D, 180D)){
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE, ApplicationConstants.ERROR_ATTRIBUT_RANGE, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_RANGE, new Object[]{"La longitude ", -180, 180}, locale));
                }
                break;
            case "description":
                if(CustomValidator.isEmpty(value)){
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE,ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, null, locale));
                } else if (value.toString().contains("<script")) {
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE,ApplicationConstants.ERROR_TECHNICAL, messages.getMessage(ApplicationConstants.ERROR_TECHNICAL, null, locale));
                }
                break;
            case "etatAvancement":
                if(CustomValidator.isEmpty(value)){
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE,ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, null, locale));
                } else if(CustomValidator.isEmpty(ETAT_AVANCEMENT.getByCode((String)value))){
                    errors.rejectValue("error.attribut.unknown","error.attribut.unknown", messages.getMessage("error.attribut.unknown", null, locale));
                }
                break;
            case "typeFinancement":
                if(CustomValidator.isEmpty(value)){
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE,ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, null, locale));
                } else if(CustomValidator.isEmpty(TYPE_FINANCEMENT.getByCode((String)value))){
                    errors.rejectValue("error.attribut.unknown","error.attribut.unknown", messages.getMessage("error.attribut.unknown", null, locale));
                }
                break;
            case "trancheExecution":
                if(CustomValidator.isEmpty(value)){
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE,ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, null, locale));
                } else if(CustomValidator.isEmpty(TRANCHE_EXECUTION.getByCode((String)value))){
                    errors.rejectValue("error.attribut.unknown","error.attribut.unknown", messages.getMessage("error.attribut.unknown", null, locale));
                }
                break;
            case "etatPublication":
                if(CustomValidator.isEmpty(value)){
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE,ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, null, locale));
                    break;
                } else if(CustomValidator.isEmpty(ETAT_PUBLICATION.getByCode((String)value))){
                    errors.rejectValue("error.attribut.unknown","error.attribut.unknown", messages.getMessage("error.attribut.unknown", null, locale));
                }
                break;
            case "echelle":
                if(CustomValidator.isEmpty(value)){
                    break;
                } else if(CustomValidator.isEmpty(ECHELLE_ACTION.getByCode((String)value))){
                    errors.rejectValue("error.attribut.unknown","error.attribut.unknown", messages.getMessage("error.attribut.unknown", null, locale));
                }
                break;
            default:
                // Champs sans validation spécifique
                 break;
        }
    }
}
