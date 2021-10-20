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

package com.efficacity.explorateurecocites.ajaris.beans;

import com.efficacity.explorateurecocites.beans.model.Action;
import com.efficacity.explorateurecocites.beans.model.Ecocite;
import com.efficacity.explorateurecocites.ui.bo.utils.LocationUtils;
import com.efficacity.explorateurecocites.utils.enumeration.ETAT_AVANCEMENT;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.beans.Transient;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@JacksonXmlRootElement(localName = "TABLE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Document {
    private static final Pattern titlePrefixPatern = Pattern.compile("^[0-9]+\\s*-\\s*VDD\\s*-\\s*");
    public static final String VALIDE = "1";
    public static final String NON_VALIDE = "0";
    private static final Double LATITUDE_DEFAULT = 46.76306;
    private static final Double LONGITUDE_DEFAULT = 2.42472;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/YYYY");
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));

    @XmlAttribute
    private final String name = "Document";

    private final AttributeTest<String> titre = new AttributeTest<>("Soustitre");
    private final AttributeTest<String> datePriseDeVue = new AttributeTest<>("Date3");
    private final AttributeTest<String> ecocite = new AttributeTest<>("Critere1");
    private final AttributeTest<String> numeroAction = new AttributeTest<>("Critere2");
    private final AttributeTest<String> etatAvancement = new AttributeTest<>("Critere3");
    private final AttributeTest<String> nomAction = new AttributeTest<>("Critere4");
    private final AttributeTest<String> copyright = new AttributeTest<>("Critere5");
    private final AttributeTest<String> typeDroit = new AttributeTest<>("Critere6");
    private final AttributeTest<String> motscles = new AttributeTest<>("Motsclesimage1");
    private final AttributeTest<String> axe = new AttributeTest<>("Motsclesimage2");
    private final AttributeTest<String> domaine = new AttributeTest<>("Motsclesimage3");
    private final AttributeTest<String> lieu = new AttributeTest<>("Motsclesimage4");
    private final AttributeTest<String> objectif = new AttributeTest<>("Motsclesimage5");
    private final AttributeTest<String> finalites = new AttributeTest<>("Motsclesimage6");
    private final AttributeTest<String> legende = new AttributeTest<>("Legende1");
    private final AttributeTest<String> description = new AttributeTest<>("Legende2");
    private final AttributeTest<String> validationDocument = new AttributeTest<>("Validation");
    private final AttributeTest<String> fileName = new AttributeTest<>("FileName");

    private Double realLatitude = null;
    private Double realLongitude = null;
    private final AttributeTest<String> latitude = new AttributeTest<>("Latitude");
    private final AttributeTest<String> longitude = new AttributeTest<>("Longitude");
    private String previewUrl =  null;
    private String mainUrl =  null;

    @XmlElement(name = "FIELD")
    private final List<AttributeTest> fields = new ArrayList<>();

    public Document() {}

    public Document(String titre, String copyright, String legende, String description, Double latitude, Double longitude) {
        this();
        this.titre.setValue(titre);
        this.copyright.setValue(copyright);
        this.legende.setValue(legende);
        this.description.setValue(description);
        this.realLatitude = latitude;
        this.realLongitude = longitude;
        this.setLatitudeFromExplorateurFormat(latitude);
        this.setLongitudeFromExplorateurFormat(longitude);
        this.validationDocument.setValue(Document.VALIDE);
    }

    public Document(String titre, String copyright, String legende, String description) {
        this(titre, copyright, legende, description, LATITUDE_DEFAULT, LONGITUDE_DEFAULT);
    }

    public void updateList() {
        fields.clear();
        addToField(titre);
        addToField(ecocite);
        addToField(numeroAction);
        addToField(etatAvancement);
        addToField(copyright);
        addToField(typeDroit);
        addToField(motscles);
        addToField(axe);
        addToField(domaine);
        addToField(objectif);
        addToField(lieu);
        addToField(legende);
        addToField(description);
        addToField(latitude);
        addToField(longitude);
        addToField(datePriseDeVue);
        addToField(validationDocument);
        if (numeroAction.getValue() == null || numeroAction.getValue().isEmpty()) {
            addToField(nomAction);
        } else {
            AttributeTest<String> nomActionCritere = new AttributeTest<>("Critere4");
            nomActionCritere.setValue(numeroAction.getValue() + " - VDD - " + this.nomAction.getValue());
            addToField(nomActionCritere);
        }
        addToField(finalites);
    }

    private <T> void addToField(AttributeTest<T> field) {
        if (field.getValue() != null) {
            this.fields.add(field);
        }
    }

    @XmlTransient
    public String getTitreValue() {
        return titre.getValue();
    }

    @XmlTransient
    public String getEcociteValue() {
        return ecocite.getValue();
    }

    @XmlTransient
    public String getNumeroActionValue() {
        return numeroAction.getValue();
    }

    @XmlTransient
    public String getNomActionValue() {
        return nomAction.getValue();
    }

    @XmlTransient
    public String getEtatAvancementValue() {
        return etatAvancement.getValue();
    }

    @XmlTransient
    public String getCopyrightValue() {
        return copyright.getValue();
    }

    @XmlTransient
    public String getTypeDroitValue() {
        return typeDroit.getValue();
    }

    @XmlTransient
    public String getMotsclesValue() {
        return motscles.getValue();
    }

    @XmlTransient
    public String getAxeValue() {
        return axe.getValue();
    }

    @XmlTransient
    public String getDomaineValue() {
        return domaine.getValue();
    }

    @XmlTransient
    public String getObjectifValue() {
        return objectif.getValue();
    }

    @XmlTransient
    public String getFinalitesValue() {
        return finalites.getValue();
    }

    @XmlTransient
    public String getLieuValue() {
        return lieu.getValue();
    }

    @XmlTransient
    public String getLegendeValue() {
        return legende.getValue();
    }

    @XmlTransient
    public String getDescriptionValue() {
        return description.getValue();
    }

    @XmlTransient
    public Double getLatitude() {
        return this.realLatitude;
    }

    @XmlTransient
    public Double getLongitude() {
        return this.realLongitude;
    }

    @XmlTransient
    public String getDatePriseDeVue() {
        return this.datePriseDeVue.getValue();
    }

    @XmlElement(name = "SousTitre")
    public void setTitre(final String titre) {
        this.titre.setValue(titre);
    }

    @XmlElement(name = "Date3")
    public void setDatePriseDeVueFromAjarisFormat(final String datePriseDeVue) {
        try {
            this.datePriseDeVue.setValue(LocalDateTime.parse(datePriseDeVue).format(DATE_TIME_FORMATTER));
        } catch (DateTimeParseException e) {
            this.datePriseDeVue.setValue(null);
        }
    }

    @XmlTransient
    public void setDatePriseDeVueFromExplorateurFormat(final String datePriseDeVue) {
        this.datePriseDeVue.setValue(datePriseDeVue);
    }

    @XmlElement(name = "Critere1")
    public void setEcocite(final String ecocite) {
        this.ecocite.setValue(ecocite);
    }

    @XmlElement(name = "Validation")
    public void setValidationFromAjaris(final String validation) {
        switch (validation) {
            case "Validé":
                this.validationDocument.setValue(VALIDE);
                break;
            default:
            case "Non validé":
                this.validationDocument.setValue(NON_VALIDE);
                break;
        }
    }

    public void setValidation(final String validation) {
        this.validationDocument.setValue(validation);
    }

    @XmlElement(name = "FileName")
    public void setFileName(final String fileName) {
        this.fileName.setValue(fileName);
    }

    @XmlElement(name = "Critere2")
    public void setNumeroAction(final String numeroAction) {
        this.numeroAction.setValue(numeroAction);
    }

    @XmlElement(name = "Critere3")
    public void setEtatAvancement(final String etatAvancement) {
        this.etatAvancement.setValue(etatAvancement);
    }

    @XmlElement(name = "Critere4")
    public void setNomAction(final String nomAction) {
        if (nomAction != null) {
            Matcher matcher = titlePrefixPatern.matcher(nomAction);
            this.nomAction.setValue(matcher.replaceAll(""));
            return;
        }
        this.nomAction.setValue("");
    }

    @XmlElement(name = "Critere5")
    public void setCopyright(final String copyright) {
        this.copyright.setValue(copyright);
    }

    @XmlElement(name = "Critere6")
    public void setTypeDroit(final String typeDroit) {
        this.typeDroit.setValue(typeDroit);
    }

    @XmlElement(name = "Mot1")
    public void setMotscles(final String motscles) {
        this.motscles.setValue(motscles);
    }

    @XmlElement(name = "Mot2")
    public void setAxe(final String axe) {
        this.axe.setValue(axe);
    }

    @XmlElement(name = "Mot3")
    public void setDomaine(final String domaine) {
        this.domaine.setValue(domaine);
    }

    @XmlElement(name = "Mot4")
    public void setLieu(final String lieu) {
        this.lieu.setValue(lieu);
    }

    @XmlElement(name = "Mot5")
    public void setObjectif(final String objectif) {
        this.objectif.setValue(objectif);
    }

    @XmlElement(name = "Mot6")
    public void setFinalites(final String finalites) {
        this.finalites.setValue(finalites);
    }

    @XmlElement(name = "Legende1")
    public void setLegende(final String legende) {
        this.legende.setValue(legende);
    }

    @XmlElement(name = "Legende2")
    public void setDescription(final String description) {
        this.description.setValue(description);
    }

    @XmlElement(name = "Latitude")
    public void setLatitudeFromAjarisFormat(final String latitude) {
        this.realLatitude = LocationUtils.getCoordinateFromDMS(latitude);
        this.setLongitudeFromExplorateurFormat(this.realLatitude);
    }

    @XmlTransient
    public void setLatitudeFromExplorateurFormat(final Double latitude) {
        this.realLatitude = latitude;
        this.latitude.setValue(DECIMAL_FORMAT.format(latitude * 360000));
    }

    @XmlElement(name = "Longitude")
    public void setLongitudeFromAjarisFormat(final String longitude) {
        this.realLongitude = LocationUtils.getCoordinateFromDMS(longitude);
        this.setLongitudeFromExplorateurFormat(this.realLongitude);
    }

    @XmlTransient
    public void setLongitudeFromExplorateurFormat(final Double longitude) {
        this.realLongitude = longitude;
        this.longitude.setValue(DECIMAL_FORMAT.format(longitude * 360000));
    }

    @XmlTransient
    public String getValidation() {
        return this.validationDocument.getValue();
    }

    @Transient
    public String getPreviewUrl() {
        // Server sends URL like this : http:///4DCGI/WS/GetPreview/252273/
        if (previewUrl != null && previewUrl.contains("http:///")) {
            return previewUrl.replace("http://", "http://mineco-ws.ajaris.com");
        }
        return previewUrl;
    }

    @Transient
    public String getMainUrl() {
        // Server sends URL like this : http:///4DCGI/WS/GetPreview/252273/
        if (mainUrl != null && mainUrl.contains("http:///")) {
            return mainUrl.replace("http://", "http://mineco-ws.ajaris.com");
        }
        return mainUrl;
    }

    @XmlElement(name = "GetPreview")
    public void setPreviewUrl(final String previewUrl) {
        this.previewUrl = previewUrl;
    }

    @XmlElement(name = "GetPrincipal")
    public void setMainUrl(final String mainUrl) {
        this.mainUrl = mainUrl;
    }

    @Transient
    public void fillWithEcocite(Ecocite ecocite) {
        this.setEcocite(ecocite.getNom());
    }

    @Transient
    public void fillWithAction(final Action action) {
        this.setNumeroAction(action.getNumeroAction());
        this.setNomAction(action.getNomPublic());
        ETAT_AVANCEMENT avancement = ETAT_AVANCEMENT.getByCode(action.getEtatAvancement());
        if (avancement != null) {
            this.setEtatAvancement(avancement.getLibelle());
        }
    }

    @XmlTransient
    public String getFileName() {
        return this.fileName.getValue();
    }
}
