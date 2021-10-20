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


import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.google.common.base.Charsets;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.Base64;

@XmlRootElement(name = "rdf:RDF")
public class Rdf {
    private Document document;

    @XmlElement(name = "Document")
    public Document getDocument() {
        return document;
    }

    @XmlElement(name = "Document")
    public void setDocument(final Document document) {
        this.document = document;
    }

    public static Rdf fromXml(String xml) {
        XmlMapper xmlMapper = new XmlMapper();
        AnnotationIntrospector introspector = new JaxbAnnotationIntrospector(xmlMapper.getTypeFactory());
        xmlMapper.setAnnotationIntrospector(introspector);
        AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
        xmlMapper.setAnnotationIntrospector(AnnotationIntrospector.pair(introspector, secondary));
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true );
        try {
            return xmlMapper.readValue(xml, Rdf.class);
        } catch (IOException e) {
            return null;
        }
    }

    public static Rdf fromBase64(String encoded) {
        try {
            return Rdf.fromXml(new String(Base64.getDecoder().decode(encoded.getBytes()), Charsets.UTF_8));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
