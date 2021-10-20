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
package isotope.modules.mail.repository;

import isotope.modules.mail.model.Mail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Stream;

/**
 * Manage mail persistence
 */
@Repository
public interface MailRepository extends MailDefaultRepository {

    /**
     * Return a stream of mail with specific attributes :
     *
     * @param dateLimite the upper limit (inclusive) to filter mail by creation date
     * @param mailStatus expected status see {@link isotope.modules.mail.model.MAIL_STATUS}
     * @return
     */
    @Query("select m from Mail m where m.creationDate<= :dateLimite and m.status=:mailStatus")
    Stream<Mail> streamAllEntities(@Param("dateLimite") LocalDateTime dateLimite, @Param("mailStatus") String mailStatus);

    /**
     * Deletes all records where creationDate is before limit
     */
    @Transactional
    int deleteByCreationDateBefore(LocalDateTime limit);

}
