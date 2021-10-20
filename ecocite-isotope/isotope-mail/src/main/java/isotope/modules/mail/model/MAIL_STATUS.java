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
package isotope.modules.mail.model;

/**
 * Possible status for an email stored in the database
 * Created by oturpin on 03/01/17.
 */
public enum MAIL_STATUS {
    // Waiting to be processed
    WAITING,
    // being sent
    SENDING,
    // processed without error
    SENT,
    // processed but recoverable error occured (can be sent again later)
    ERROR,
    // processed but irrecoverable error occured (mustn't be sent again)
    IRRECOVERABLE_ERROR
}
