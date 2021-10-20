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

package com.efficacity.explorateurecocites.ui.bo.exceptions.handlers;

import com.efficacity.explorateurecocites.ui.bo.exceptions.SaveAttributException;
import com.efficacity.explorateurecocites.ui.bo.utils.ErrorAdaptor;
import com.efficacity.explorateurecocites.ui.bo.utils.FieldErrorAdaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by rxion on 15/02/2018.
 */

/**
 * Un controller qui permet de gérer l'exception {@code SaveAttributException}
 * pour harmoniser la sorite des messages d'erreur au front
 */
@ControllerAdvice
public class SaveAttributExceptionHandler {
  @Autowired
  private MessageSource messageSource;

  @ExceptionHandler({SaveAttributException.class})
  public ResponseEntity<?> handlerInvalidSaveAttributRequest(RuntimeException e, Locale locale) {
	SaveAttributException sae = (SaveAttributException) e;
	List<FieldErrorAdaptor> feas = new ArrayList<>();

	for (FieldError fe : sae.getErrors().getFieldErrors()) {
	  FieldErrorAdaptor fea = new FieldErrorAdaptor();
	  fea.setResource(fe.getObjectName());
	  fea.setField(fe.getField());
	  fea.setCode(fe.getCode());
	  fea.setMessage(messageSource.getMessage(fe, locale));
	  feas.add(fea);
	}

	ErrorAdaptor error = new ErrorAdaptor("InvalidRequest");
	error.setFieldErrors(feas);

	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);

	return new ResponseEntity(error, headers, HttpStatus.BAD_REQUEST);
  }
}
