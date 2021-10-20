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

package isotope.modules.mail.service;

import isotope.modules.mail.config.MailModuleConfiguration;
import isotope.modules.mail.helper.EmailSpec;
import isotope.modules.mail.helper.EmailSpecBuilder;
import isotope.modules.mail.helper.MailRecipientSpec;
import isotope.modules.mail.model.MailRecipient;
import isotope.modules.mail.repository.MailRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by olivier on 03/01/2017.
 */
public class MailServiceTest {

    private MailModuleConfiguration configuration;
    private MailService mailService;
    private MailRepository repository;
    private ITemplateService templateService;
    private JavaMailSender javaMailSender;


    private static final String MAIL_TEST_IPSOSENSO = "spam+to@ipsosenso.com";
    private static final String MAIL_ANOTHER_DOMAIN = "spam+to@another.com";
    private static final String BODY_FILLED_BY_TEMPLATE = "BodyTemplate";



    @BeforeAll
    public void prepare() throws IOException {
        configuration = new MailModuleConfiguration();
        repository = mock(MailRepository.class);
        templateService = mock(ITemplateService.class);
        javaMailSender = mock(JavaMailSender.class);

        // intercepter l'appel au mergeTemplate
        when(templateService.mergeTemplateIntoString(eq("mail/FakeTemplate"), any(), any())).thenReturn(BODY_FILLED_BY_TEMPLATE);

        mailService = new MailService(repository, templateService, javaMailSender, configuration);
    }



    @Test
    public void given_list_if_filterNotProvided_then_success() throws Exception {
        String nullFilter = null;
        Set<MailRecipient> recipients = new HashSet<>();
        MailRecipient mailRecipientTo = new MailRecipient();
        mailRecipientTo.setMailTo(MAIL_TEST_IPSOSENSO);
        mailRecipientTo.setType(MailRecipientSpec.TYPE.TO.name());
        recipients.add(mailRecipientTo);

        MailRecipient mailRecipientCC = new MailRecipient();
        mailRecipientCC.setMailTo(MAIL_ANOTHER_DOMAIN);
        mailRecipientCC.setType(MailRecipientSpec.TYPE.CC.name());
        recipients.add(mailRecipientCC);

        Assertions.assertEquals(mailService.filterMails(recipients, nullFilter).size(), 2);
    }

    @Test
    public void given_list_if_filterDontMatch_then_returnEmptyList() throws Exception {

        String domaine = "nomatchfilter.com";
        Set<MailRecipient> recipients = new HashSet<>();

        MailRecipient mailRecipientTo = new MailRecipient();
        mailRecipientTo.setMailTo(MAIL_TEST_IPSOSENSO);
        mailRecipientTo.setType(MailRecipientSpec.TYPE.TO.name());
        recipients.add(mailRecipientTo);

        MailRecipient mailRecipientCC = new MailRecipient();
        mailRecipientCC.setMailTo(MAIL_ANOTHER_DOMAIN);
        mailRecipientCC.setType(MailRecipientSpec.TYPE.CC.name());
        recipients.add(mailRecipientCC);

        Assertions.assertEquals(mailService.filterMails(recipients, domaine).size(), 0);
    }

    @Test
    public void given_list_if_multipleFilterDontMatch_then_ReturnEmptyList() throws Exception {

        String domaine = "nomatchfilter.com,wontmatcheithher.com";
        Set<MailRecipient> recipients = new HashSet<>();

        MailRecipient mailRecipientTo = new MailRecipient();
        mailRecipientTo.setMailTo(MAIL_TEST_IPSOSENSO);
        mailRecipientTo.setType(MailRecipientSpec.TYPE.TO.name());
        recipients.add(mailRecipientTo);

        MailRecipient mailRecipientCC = new MailRecipient();
        mailRecipientCC.setMailTo(MAIL_ANOTHER_DOMAIN);
        mailRecipientCC.setType(MailRecipientSpec.TYPE.CC.name());
        recipients.add(mailRecipientCC);

        Assertions.assertEquals(mailService.filterMails(recipients, domaine).size(), 0);
    }




    @Test
    public void given_list_if_filterMatchToRecipient_then_success() throws Exception {

        String domaine = "ipsosenso.com";
        Set<MailRecipient> recipients = new HashSet<>();

        MailRecipient mailRecipientTo = new MailRecipient();
        mailRecipientTo.setMailTo(MAIL_TEST_IPSOSENSO);
        mailRecipientTo.setType(MailRecipientSpec.TYPE.TO.name());
        recipients.add(mailRecipientTo);

        Assertions.assertEquals(mailService.filterMails(recipients, domaine).size(), 1);
    }

    @Test
    public void given_emptyList_then_shouldNotSend() throws Exception {

        Set<MailRecipient> recipients = new HashSet<>();
        Assertions.assertEquals(mailService.mailShouldBeSent(recipients), false);
        recipients = null;
        Assertions.assertEquals(mailService.mailShouldBeSent(recipients), false);
    }


    @Test
    public void given_listWithOnlyCCValid_then_shouldNotSend() throws Exception {

        String domaine = "ipsosenso.com";
        Set<MailRecipient> recipients = new HashSet<>();

        MailRecipient mailRecipientTo = new MailRecipient();
        mailRecipientTo.setMailTo(MAIL_ANOTHER_DOMAIN);
        mailRecipientTo.setType(MailRecipientSpec.TYPE.TO.name());
        recipients.add(mailRecipientTo);

        MailRecipient mailRecipientCc = new MailRecipient();
        mailRecipientCc.setMailTo(MAIL_TEST_IPSOSENSO);
        mailRecipientCc.setType(MailRecipientSpec.TYPE.CC.name());
        recipients.add(mailRecipientCc);

        Set<MailRecipient> filtered = mailService.filterMails(recipients, domaine);
        Assertions.assertEquals(filtered.size(), 1);
        Assertions.assertEquals(mailService.mailShouldBeSent(filtered), false);
    }


    @Test
    public void given_templateFilledAfterBody_should_ignoreBody() throws IOException {

        String body = "My body";
        EmailSpecBuilder emailSpecBuilder = mailService.createBuilder();
        emailSpecBuilder.body(body);
        emailSpecBuilder.withTemplate("mail/FakeTemplate");
        EmailSpec emailSpec = emailSpecBuilder.build();


        // je devrais avoir surchargé le contenu de body avec le template
        Assertions.assertEquals(BODY_FILLED_BY_TEMPLATE, emailSpec.getBody());
    }

    @Test
    public void given_bodyFilledAfterTemplate_should_ignoreTemplate() throws IOException {

        String body = "My body";

        EmailSpecBuilder emailSpecBuilder = mailService.createBuilder();
        emailSpecBuilder.withTemplate("mail/FakeTemplate");
        emailSpecBuilder.body(body);
        EmailSpec emailSpec = emailSpecBuilder.build();

        // je devrais avoir surchargé le contenu de body avec le template
        Assertions.assertEquals(body, emailSpec.getBody());
    }
}
