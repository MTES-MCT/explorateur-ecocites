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

package com.efficacity.explorateurecocites.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    /***
     * Le chemin vers le dossier de base ou seront stockés tous les fichiers du serveur.
     */
    @Value("${efficacity.explorateurecocites.basepath}")
    private String basePath;

    @Value("${efficacity.explorateurecocites.email.server.smtp}")
    private String smtpHostName;

    @Value("${efficacity.explorateurecocites.email.expediteur}")
    private String emailExpediteur;

    @Value("${efficacity.explorateurecocites.email.expediteur.name}")
    private String emailNameExpediteur;

    @Value("${efficacity.explorateurecocites.email.accompagnateur}")
    private String emailAccopagnateur;

    @Value("${efficacity.explorateurecocites.geoportail.apikey}")
    private String geoportailApiKey;

    @Value("${efficacity.ajaris.login.url:}")
    private String ajarisLoginUrl;

    @Value("${efficacity.explorateurecocites.login.bouchon:false}")
    private boolean loginBouchon;

    @Value("${efficacity.explorateurecocites.url.public:/}")
    private String publicUrl;

    @Value("${efficacity.media.update.maxretry:5}")
    private Integer maxRetryNumber;

    public boolean getLoginBouchon() {
        return loginBouchon;
    }

    public void setLoginBouchon(final boolean loginBouchon) {
        this.loginBouchon = loginBouchon;
    }

    public String getBasePath() {
        return basePath;
    }

    public String getServerStmp(){
        return smtpHostName;
    }

    public String getEmailExpediteur(){ return emailExpediteur; }

    public String getEmailNameExpediteur(){ return emailNameExpediteur; }

    public String getEmailAccompagnateur(){ return emailAccopagnateur; }

    public String getGeoportailApiKey() {
        return geoportailApiKey;
    }

    public String getPublicUrl() {
        return publicUrl;
    }

    public Integer getMaxRetryNumber() {
        return maxRetryNumber;
    }

    public String getAjarisLoginUrl() {
        return ajarisLoginUrl;
    }
}
