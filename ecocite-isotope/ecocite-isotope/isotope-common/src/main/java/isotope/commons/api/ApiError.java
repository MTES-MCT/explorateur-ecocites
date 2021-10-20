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

package isotope.commons.api;

/**
 * Created by oturpin on 16/06/16.
 */
public class ApiError {

	/**
	 * Code erreur "métier"
	 */
	private String error;

	/**
	 * Message explicite
	 */
	private String error_details;

	/* GETTERS & SETTERS */
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getError_details() {
		return error_details;
	}

	public void setError_details(String error_details) {
		this.error_details = error_details;
	}
}
