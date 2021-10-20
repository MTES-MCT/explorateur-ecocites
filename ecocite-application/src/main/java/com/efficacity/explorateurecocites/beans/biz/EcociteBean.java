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

import com.efficacity.explorateurecocites.beans.model.Ecocite;
import com.efficacity.explorateurecocites.utils.ApplicationConstants;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import com.efficacity.explorateurecocites.utils.enumeration.*;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe qui étent le bean model du générator
 * <p>
 * Date de génération : 06/02/2018
 */

public class EcociteBean {

    private Ecocite to;

    private List<FileUploadBean> documents;
    private List<AdapteurImagesBeans> imagesBeans;
    private EtiquettesBean etiquettesList;
    private AdapteurIndicateursBean indicateursBean;
    private AdaptateurEtapesBean etapes;

    public EcociteBean(Ecocite ecocite, final List<EtapeBean> etapes) {
        super();
        this.to = ecocite;
        this.etapes = new AdaptateurEtapesBean(TYPE_OBJET.ECOCITE, etapes);
    }

    public EcociteBean(final Ecocite ecocite, final List<FileUploadBean> fileAction, final EtiquettesBean etiquettesList, final AdapteurIndicateursBean indicateursBean, final AdaptateurEtapesBean etapes) {
        super();
        this.to = ecocite;
        imagesBeans = new ArrayList<>();
        documents = new ArrayList<>();
        fileAction.stream()
                .filter(f -> f.getTypeEnum() == FILE_TYPE.ECOCITE_IMAGE_PRINCIPALE || f.getTypeEnum() == FILE_TYPE.ECOCITE_IMAGE_SECONDAIRE)
                .sorted(Comparator.reverseOrder())
                .forEach(f -> imagesBeans.add(new AdapteurImagesBeans(f)));
        fileAction.stream()
                .filter(f -> f.getTypeEnum() == FILE_TYPE.ECOCITE_DOCUMENT)
                .forEach(f -> documents.add(new FileUploadBean(f.getTo())));
        this.etiquettesList = etiquettesList;
        this.indicateursBean = indicateursBean;
        this.etapes = etapes;
    }

    public List<EtapeBean> getListEtapesTriees() {
        List<EtapeBean> list = getEtapesList();
        if (list != null) {
            list.removeIf(item -> item.getEtapeEnumEcocite() == null);
            if (!list.isEmpty()) {
                list.sort(Comparator.comparing(o -> o.getEtapeEnumEcocite().getOrder()));
            }
        }
        return list;
    }

    public boolean isEtapeIndicateurDone() {
        return etapes.getEtapeList().stream().anyMatch(etape -> etape.getEtapeEnumEcocite() == ETAPE_ECOCITE.INDICATEUR && etape.getStatutEnum() == ETAPE_STATUT.VALIDER);
    }

    public EtapeBean getEtapeCaraterisation() {
        if (etapes == null || etapes.getEtapeList() == null) {
            return null;
        }
        return etapes.getEtapeList().stream().filter(e -> ETAPE_ECOCITE.CARACTERISATION.getCode().equals(e.getTo().getCodeEtape())).findFirst().orElse(null);
    }

    public EtapeBean getEtapeChoixIndicateur() {
        if (etapes == null || etapes.getEtapeList() == null) {
            return null;
        }
        return etapes.getEtapeList().stream().filter(e -> ETAPE_ECOCITE.INDICATEUR.getCode().equals(e.getTo().getCodeEtape())).findFirst().orElse(null);
    }
    public EtapeBean getEtapeRenseignementIndicateur() {
        if (etapes == null || etapes.getEtapeList() == null) {
            return null;
        }
        EtapeBean etapeBean = etapes.getEtapeList().stream()
                .filter(e -> ETAPE_ECOCITE.MESURE_INDICATEUR.getCode().
                        equals(e.getTo().getCodeEtape()))
                .findFirst()
                .orElse(null);
        if (etapeBean != null &&
                etapes.getEtapeList().stream()
                        .noneMatch(etape -> etape.getEtapeEnumEcocite() == ETAPE_ECOCITE.INDICATEUR && etape.getStatutEnum() == ETAPE_STATUT.VALIDER)) {
            etapeBean.setStatut(ETAPE_STATUT.IMPOSSIBLE.getCode());
        }
        return etapeBean;
    }

    public EtapeBean getEtapeEvaluationFacteur() {
        if (etapes == null || etapes.getEtapeList() == null) {
            return null;
        }
        return etapes.getEtapeList().stream()
                .filter(e -> ETAPE_ECOCITE.CONTEXTE_ET_FACTEUR.getCode().equals(e.getTo().getCodeEtape()))
                .findFirst().orElse(null);
    }

    public ETAPE_STATUT getStatusEnumEtape(EtapeBean etapeBean) {
        if (etapeBean.getEtapeEnumAction() != null && etapeBean.getEtapeEnumEcocite() == ETAPE_ECOCITE.MESURE_INDICATEUR) {
            return etapes.getEtapeList()
                    .stream()
                    .anyMatch(etape -> etape.getEtapeEnumEcocite() == ETAPE_ECOCITE.INDICATEUR && etape.getStatutEnum() == ETAPE_STATUT.VALIDER)
                    ? etapeBean.getStatutEnum() : ETAPE_STATUT.IMPOSSIBLE;
        } else {
            return etapeBean.getStatutEnum();
        }
    }

    public ETAT_PUBLICATION getEtatPublicationEnum() {
        return ETAT_PUBLICATION.getByCode(this.to.getEtatPublication());
    }

    public List<EtapeBean> getEtapesList() {
        return this.etapes.getEtapeList();
    }

    public Long getId() {
        return this.to.getId();
    }

    public void setId(Long id) {
        this.to.setId(id);
    }

    public String getNom() {
        return this.to.getNom();
    }

    public String getShortname() {
        return this.to.getShortname();
    }

    public void setShortname(String shortname) {
        this.to.setShortname(shortname);
    }

    public void setNom(String nom) {
        this.to.setNom(nom);
    }

    public Long getIdRegion() {
        return this.to.getIdRegion();
    }

    public void setIdRegion(Long idRegion) {
        this.to.setIdRegion(idRegion);
    }

    public String getDescStrategie() {
        return this.to.getDescStrategie();
    }

    public String getDescStrategieNoLineBreak() {
        if (to.getDescStrategie() != null) {
            return this.to.getDescStrategie().replaceAll("\r?\n", "<br/>").replaceAll("\"", "\\\\\"");
        }
        return "";
    }

    public void setDescStrategie(String descStrategie) {
        this.to.setDescStrategie(descStrategie);
    }

    public String getDescPerimetre() {
        return this.to.getDescPerimetre();
    }

    public String getDescPerimetreNoLineBreak() {
        if (to.getDescPerimetre() != null) {
            return this.to.getDescPerimetre().replaceAll("\r?\n", "<br/>").replaceAll("\"", "\\\\\"");
        }
        return "";
    }

    public void setDescPerimetre(String descPerimetre) {
        this.to.setDescPerimetre(descPerimetre);
    }

    public String getPorteur() {
        return this.to.getPorteur();
    }

    public void setPorteur(String porteur) {
        this.to.setPorteur(porteur);
    }

    public String getPartenaire() {
        return this.to.getPartenaire();
    }

    public void setPartenaire(String partenaire) {
        this.to.setPartenaire(partenaire);
    }

    public String getAnneeAdhesion() {
        return this.to.getAnneeAdhesion();
    }

    public void setAnneeAdhesion(String anneeAdhesion) {
        this.to.setAnneeAdhesion(anneeAdhesion);
    }

    public String getSoutienPiaDetail() {
        return this.to.getSoutienPiaDetail();
    }

    public void setSoutienPiaDetail(String soutienPiaDetail) {
        this.to.setSoutienPiaDetail(soutienPiaDetail);
    }

    public Integer getNbCommunes() {
        return this.to.getNbCommunes();
    }

    public void setNbCommunes(Integer nbCommunes) {
        this.to.setNbCommunes(nbCommunes);
    }

    public Integer getNbHabitants() {
        return this.to.getNbHabitants();
    }

    public void setNbHabitants(Integer nbHabitants) {
        this.to.setNbHabitants(nbHabitants);
    }

    public Integer getSuperficieKm2() {
        return this.to.getSuperficieKm2();
    }

    public void setSuperficieKm2(Integer superficieKm2) {
        this.to.setSuperficieKm2(superficieKm2);
    }

    public String getLien() {
        return this.to.getLien();
    }

    public void setLien(String lien) {
        this.to.setLien(lien);
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

    public String getSiren() {
        return this.to.getSiren();
    }

    public void setLongitude(String longitude) {
        this.to.setLongitude(longitude);
    }

    public String getEtatPublication() {
        return this.to.getEtatPublication();
    }

    public void setEtatPublication(String etatPublication) {
        this.to.setEtatPublication(etatPublication);
    }

    public Ecocite getTo() {
        return to;
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

    public AdaptateurEtapesBean getEtapes() {
        return etapes;
    }

    public void setEtapes(final AdaptateurEtapesBean etapes) {
        this.etapes = etapes;
    }

    public void validateChamps(String idChamps, Object value, MessageSource messages, Errors errors, Locale locale) {
        switch (idChamps) {
            case "id":
                // On fait, rien au pire exception lors du save sur l'unicité
                break;
            case "nom":
                if (CustomValidator.isEmpty(value)) {
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE, ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, null, locale));
                }
                break;
            case "siren":
                if (CustomValidator.isEmpty(value)) {
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE, ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, null, locale));
                } else if (!CustomValidator.isSiren((String) value)) {
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE, ApplicationConstants.ERROR_ATTRIBUT_FORMAT, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, null, locale));
                }
                break;
            case "idRegion":
                if (CustomValidator.isEmpty(value)) {
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE, ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, null, locale));
                }
                break;
            case "anneeAdhesion":
                if (CustomValidator.isEmpty(value) || !CustomValidator.isNumeric((String) value)) {
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE, "error.attribut.notNull.annee", messages.getMessage("error.attribut.notNull.annee", null, locale));
                }
                break;
            case "etatPublication":
                if (CustomValidator.isEmpty(value)) {
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE, ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, null, locale));
                } else if (CustomValidator.isEmpty(ETAT_PUBLICATION.getByCode((String) value))) {
                    errors.rejectValue("error.attribut.unknown", "error.attribut.unknown", messages.getMessage("error.attribut.unknown", null, locale));
                }
                break;
            case "descStrategie":
                if (CustomValidator.isEmpty(value)) {
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE, ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, null, locale));
                } else if (value.toString().contains("<script")) {
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE,ApplicationConstants.ERROR_TECHNICAL, messages.getMessage(ApplicationConstants.ERROR_TECHNICAL, null, locale));
                }
                break;
            case "descPerimetre":
                if (CustomValidator.isEmpty(value)) {
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE, ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, null, locale));
                } else if (value.toString().contains("<script")) {
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE,ApplicationConstants.ERROR_TECHNICAL, messages.getMessage(ApplicationConstants.ERROR_TECHNICAL, null, locale));
                }
                break;
            case "porteur":
                if (CustomValidator.isEmpty(value)) {
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE, ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, null, locale));
                }
                break;
            case "nbCommunes":
                if (CustomValidator.isEmpty(value)) {
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE, ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, null, locale));
                }
                break;
            case "nbHabitants":
                if (CustomValidator.isEmpty(value)) {
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE, ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, null, locale));
                }
                break;
            case "superficieKm2":
                if (CustomValidator.isEmpty(value)) {
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE, ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, null, locale));
                }
                break;
            case "latitude":
                if (CustomValidator.isEmpty(value)) {
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE, ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, null, locale));
                } else if (!CustomValidator.isSignedNumeric((String) value)) {
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE, ApplicationConstants.ERROR_ATTRIBUT_FORMAT, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_FORMAT, null, locale));
                } else if (!CustomValidator.checkRange((String) value, -90D, 90D)) {
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE, ApplicationConstants.ERROR_ATTRIBUT_RANGE, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_RANGE, new Object[]{"La latitude ", -90, 90}, locale));
                }
                break;
            case "longitude":
                if (CustomValidator.isEmpty(value)) {
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE, ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_NOT_NULL, null, locale));
                } else if (!CustomValidator.isSignedNumeric((String) value)) {
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE, ApplicationConstants.ERROR_ATTRIBUT_FORMAT, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_FORMAT, null, locale));
                } else if (!CustomValidator.checkRange((String) value, -180D, 180D)) {
                    errors.rejectValue(ApplicationConstants.ATTRIBUT_VALUE, ApplicationConstants.ERROR_ATTRIBUT_RANGE, messages.getMessage(ApplicationConstants.ERROR_ATTRIBUT_RANGE, new Object[]{"La longitude ", -180, 180}, locale));
                }
                break;
        }
    }

    public EtapeBean getEtapeByStatus(final ETAPE_ECOCITE etapeEcocite) {
        return etapes.getEtapeList().stream().filter(etape -> etape.getEtapeEnumEcocite() == etapeEcocite).findFirst().orElse(null);
    }

    public List<String> getPartenaireNotFromEnum() {
        if (this.to.getPartenaire() == null) {
            return new ArrayList<>();
        }
        return Arrays.stream(this.to.getPartenaire().split(";"))
                .filter(o -> !MAITRISE_OUVRAGE.isDefined(o))
                .map(MAITRISE_OUVRAGE::getLibelleForCode)
                .collect(Collectors.toList());
    }

    public List<MAITRISE_OUVRAGE> getPartenaireFromEnum() {
        if (this.to.getPartenaire() == null) {
            return new ArrayList<>();
        }
        return Arrays.stream(this.to.getPartenaire().split(";"))
                .map(MAITRISE_OUVRAGE::getByCode)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public String getAllPartenaires() {
        if (this.to.getPartenaire() == null) {
            return "";
        }
        return Arrays.stream(this.to.getPartenaire().split(";"))
                .map(MAITRISE_OUVRAGE::getLibelleForCode)
                .collect(Collectors.joining(";"));
    }
}
