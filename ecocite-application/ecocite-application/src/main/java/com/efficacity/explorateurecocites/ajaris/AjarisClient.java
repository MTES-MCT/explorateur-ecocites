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

package com.efficacity.explorateurecocites.ajaris;

import com.efficacity.explorateurecocites.ajaris.beans.Rdf;
import com.efficacity.explorateurecocites.ajaris.beans.X4dUpdate;
import com.efficacity.explorateurecocites.ajaris.generated.Orkis_WebServiceLocator;
import com.efficacity.explorateurecocites.ajaris.generated.Orkis_WebServiceRPC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.holders.ByteArrayHolder;
import javax.xml.rpc.holders.IntHolder;
import javax.xml.rpc.holders.StringHolder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;


@Service
public class AjarisClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AjarisClient.class);

    private static final String DOCUMENT_TABLE_NAME = "Document";

    @Value("${efficacity.ajaris.enabled:true}")
    private Boolean enabled;

    @Value("${efficacity.ajaris.credentials.username}")
    private String user;

    @Value("${efficacity.ajaris.credentials.password}")
    private String password;

    private Orkis_WebServiceRPC service;

    private String token;

    public AjarisClient() {
        final Orkis_WebServiceLocator locator = new Orkis_WebServiceLocator();
        try {
            service = locator.getOrkis_WebServicePort();
        } catch (ServiceException e) {
            service = null;
        }
    }

    public Boolean connect() {
        if (Boolean.FALSE.equals(this.enabled)) {
            return false;
        }
        final StringHolder tokenHolder = new StringHolder();
        final StringHolder errorMessageHolder = new StringHolder();
        final IntHolder errorCodeHolder = new IntHolder();
        final StringHolder concessions = new StringHolder();
        final StringHolder marquesEtModeles = new StringHolder();
        final StringHolder detailsParMarque = new StringHolder();
        final StringHolder typeClient = new StringHolder();
        try {
            LOGGER.info("Ajaris - Connect - Starting");
            service.WS_Connect(user, password, 1, tokenHolder, errorCodeHolder, errorMessageHolder, concessions, marquesEtModeles, detailsParMarque, typeClient);
            if (errorCodeHolder.value == 0) {
                LOGGER.info("Ajaris - Connect - Connected");
                this.token = tokenHolder.value;
                return true;
            }
            String err = "Ajaris - Connect - Error(" + errorCodeHolder.value + ") : " + errorMessageHolder.value;
            LOGGER.info(err);
            return false;
        } catch (RemoteException e) {
            LOGGER.info("Ajaris - Connect - Error :", e);
            return false;
        }
    }

    public Boolean update(Integer idAjaris, X4dUpdate payload) {
        return update(idAjaris, payload, true);
    }

    private Boolean update(Integer idAjaris, X4dUpdate payload, Boolean retry) {
        if (Boolean.FALSE.equals(this.enabled)) {
            return false;
        }
        StringHolder jetonOut = new StringHolder();
        StringHolder libelleErreur = new StringHolder();
        IntHolder numErreur = new IntHolder();
        try {
            LOGGER.info("Ajaris - Update - Starting");
            service.WS_Update(token, user, password, 1, DOCUMENT_TABLE_NAME, idAjaris, payload.asBase64EncodedXml().getBytes(StandardCharsets.UTF_8), jetonOut, numErreur, libelleErreur);
            if (numErreur.value == 0) {
                LOGGER.info("Ajaris - Update - Success");
                this.token = jetonOut.value;
                return true;
            } else if (Boolean.TRUE.equals(retry) && (numErreur.value == -5 || numErreur.value == 14) && Boolean.TRUE.equals(connect())) {
                String err = "Ajaris - Update - Failed(" + numErreur.value + ") : " + libelleErreur.value;
                LOGGER.info(err);
                LOGGER.info("Ajaris - Update - Failed - Retrying");
                return update(idAjaris, payload, false);
            }
            String err = "Ajaris - Update - Failed(" + numErreur.value + ") : " + libelleErreur.value;
            LOGGER.info(err);
            return false;
        } catch (IOException e) {
            LOGGER.info("Ajaris - Update - Error", e);
            return false;
        }
    }

    public Integer upload(MultipartFile file, X4dUpdate payload) {
        try {
            return upload(file.getBytes(), file.getOriginalFilename(), payload);
        } catch (IOException e) {
            return -1;
        }
    }

    public Integer upload(byte[] file, String filename, X4dUpdate payload) {
        return upload(file, filename, payload, true);
    }

    private Integer upload(byte[] file, String filename, X4dUpdate payload, Boolean retry) {
        if (Boolean.FALSE.equals(this.enabled)) {
            return -1;
        }
        StringHolder jetonOut = new StringHolder();
        StringHolder libelleErreur = new StringHolder();
        IntHolder idDocument = new IntHolder();
        IntHolder numErreur = new IntHolder();
        try {
            LOGGER.info("Ajaris - Upload - Starting");
            service.WS_Upload(token, user, password, 1, file, filename, payload.asBase64EncodedXml().getBytes(StandardCharsets.UTF_8), jetonOut, numErreur, libelleErreur, idDocument);
            if (numErreur.value == 0) {
                LOGGER.info("Ajaris - Upload - Success");
                this.token = jetonOut.value;
                return idDocument.value;
            } else if (Boolean.TRUE.equals(retry) && (numErreur.value == -5 || numErreur.value == 14) && Boolean.TRUE.equals(connect())) {
                String err = "Ajaris - Upload - Failed(" + numErreur.value + ") : " + libelleErreur.value;
                LOGGER.info(err);
                LOGGER.info("Ajaris - Upload - Failed - Retrying");
                return upload(file, filename, payload, false);
            }
            String err = "Ajaris - Upload - Failed(" + numErreur.value + ") : " + libelleErreur.value;
            LOGGER.info(err);
            return -1;
        } catch (IOException e) {
            LOGGER.info("Ajaris - Upload - Error", e);
            return -1;
        }
    }

    public Rdf getFileByID(Integer id) {
        if (Boolean.FALSE.equals(this.enabled)) {
            return null;
        }
        return getFileByID(id, true);
    }

    private Rdf getFileByID(Integer id, Boolean retry) {
        if (Boolean.FALSE.equals(this.enabled)) {
            return null;
        }
        StringHolder jetonOut = new StringHolder();
        StringHolder libelleErreur = new StringHolder();
        IntHolder nbEnregs = new IntHolder();
        IntHolder numErreur = new IntHolder();
        ByteArrayHolder records = new ByteArrayHolder();
        try {
            LOGGER.info("Ajaris - Select - Starting");
            String rechercheById = "idDocument;OE;" + id + ";ET";
            service.WS_Select(token, user, password, 1, 2, "SelTotale", "", "", "", 1, 1, "*", "", new String[]{rechercheById}, nbEnregs, jetonOut, numErreur, libelleErreur, records);
            if (numErreur.value == 0) {
                this.token = jetonOut.value;
                if (nbEnregs.value > 0) {
                    LOGGER.info("Ajaris - Select - Success");
                    return Rdf.fromXml(new String(records.value, StandardCharsets.UTF_8));
                } else {
                    String err = "Ajaris - Select - No document found with id : " + id;
                    LOGGER.info(err);
                    return null;
                }
            } else if (Boolean.TRUE.equals(retry) && (numErreur.value == -5 || numErreur.value == 14) && Boolean.TRUE.equals(connect())) {
                String err = "Ajaris - Select - Failed(" + numErreur.value + ") : " + libelleErreur.value;
                LOGGER.info(err);
                LOGGER.info("Ajaris - Select - Failed - Retrying");
                return getFileByID(id, false);
            }
            String err = "Ajaris - Select - Failed(" + numErreur.value + ") : " + libelleErreur.value;
            LOGGER.info(err);
            return null;
        } catch (RemoteException e) {
            LOGGER.info("Ajaris - Select - Error During Keep Alive", e);
            return null;
        }
    }

    public Boolean keepAlive() {
        if (Boolean.FALSE.equals(this.enabled)) {
            return false;
        }
        LOGGER.info("Ajaris - KeepAlive - Starting");
        StringHolder libelleErreur = new StringHolder();
        IntHolder numErreur = new IntHolder();
        if (this.token == null || this.token.isEmpty()) {
            LOGGER.info("Ajaris - KeepAlive - Not connected");
            return connect();
        } else {
            try {
                LOGGER.info("Ajaris - KeepAlive - Refreshing token");
                service.WS_KeepAlive(token, numErreur, libelleErreur);
                if (numErreur.value == 0) {
                    LOGGER.info("Ajaris - KeepAlive - Token refreshed");
                    return true;
                } else {
                    String err = "Ajaris - KeepAlive - Fail to refresh token (" + numErreur.value + ") : " + libelleErreur.value;
                    LOGGER.info(err);
                    LOGGER.info("Ajaris - KeepAlive - Connecting again");
                    return connect();
                }
            } catch (RemoteException e) {
                LOGGER.info("Ajaris - KeepAlive - Error : ", e);
            }
        }
        LOGGER.info("Ajaris - KeepAlive - End");
        return false;
    }

    public Boolean isConnected() {
        return this.enabled && this.keepAlive();
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}
