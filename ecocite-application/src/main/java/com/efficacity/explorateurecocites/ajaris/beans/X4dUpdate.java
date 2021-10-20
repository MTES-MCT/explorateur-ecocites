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


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Base64;

@XmlRootElement(name = "X4DUPDATE")
public class X4dUpdate {
    private static final String EMPTY_UPDATE = "<?xml version='1.0' encoding='UTF-8'?>\n" +
            "<X4DUPDATE>\n" +
            "  <TABLE name=\"Document\">\n" +
            "  </TABLE>\n" +
            "</X4DUPDATE>";

    private static final Logger LOGGER = LoggerFactory.getLogger(X4dUpdate.class);

    private final Document table;

    public X4dUpdate(Document document) {
        table = document;
    }

    @XmlElement(name = "TABLE")
    public Document getTable() {
        return table;
    }

    public String asXml() {
        this.table.updateList();
        XmlMapper xmlMapper = new XmlMapper();
        AnnotationIntrospector introspector = new JaxbAnnotationIntrospector(xmlMapper.getTypeFactory());
        AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
        xmlMapper.setAnnotationIntrospector(introspector);
//        xmlMapper.getFactory().getXMLOutputFactory().setProperty("javax.xml.stream.isRepairingNamespaces", false);
        xmlMapper.setAnnotationIntrospector(AnnotationIntrospector.pair(introspector, secondary));
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true );
        try {
            return xmlMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            LOGGER.error("Erreur pendant la création du JSON", e);
            return EMPTY_UPDATE;
        }
    }

    public String asBase64EncodedXml() {
        return Base64.getEncoder().encodeToString(this.asXml().getBytes());
    }
}
