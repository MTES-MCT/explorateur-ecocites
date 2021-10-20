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

package com.efficacity.explorateurecocites.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ModelAndView  handleError(HttpServletRequest req, Exception ex) {
        LOGGER.error("url: {}", req.getRequestURL());
        LOGGER.error("Request:", ex);

        ModelAndView map = new ModelAndView();
        map.addObject("timestamp", String.valueOf(System.currentTimeMillis()));
        map.addObject("status", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        map.addObject("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        map.addObject("message", "Une erreur est survenue");
        map.addObject("path", req.getRequestURL().toString());
        map.setViewName("error");
        if (req.getHeader("Accept").equals("application/json")) {
            map.setView(new MappingJackson2JsonView());
        }
        return map;
    }
}
