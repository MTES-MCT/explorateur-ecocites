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

package isotope.modules.mail.service.impl;


import isotope.modules.mail.service.ITemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 * Implémentation du templateService via Thymeleaf
 * Permet d'utiliser un template "Thymeleaf" comme corps d'un mail
 *
 * A noter les templates sont par défaut chargé depuis le répertoire ressources
 *
 * Created by oturpin on 02/01/17.
 */
public class ThymleafTemplateService implements ITemplateService{

    @Autowired
    private SpringTemplateEngine thymeleafEngine;

    /**  Suffixe des templates tymleaf, par défaut l'extension html est utilisée */
    @Value("${spring.thymeleaf.suffix:.html}")
    private String thymeleafSuffix;


    /**
     * Fusionne le template avec le contexte passé en paramètre
     *
     * @param templateReference le nom du template à utiliser exemple "mail/monTemplate" pour un template déposé dans le
     *                          répertoire resources/mail/monTemplate.html
     * @param model map contenant un ensemble de clés/valeurs utilisées pour populer le template (see
     * @param locale Locale utilisée pour internationliser le contenu du template
     * @return
     * @throws IOException
     */
    @Override
    public String mergeTemplateIntoString(final String templateReference, final Map<String, Object> model, Locale locale)
            throws IOException {
        final Context context = new Context();
        context.setVariables(model);
        return thymeleafEngine.process(templateReference, context);
    }
}
