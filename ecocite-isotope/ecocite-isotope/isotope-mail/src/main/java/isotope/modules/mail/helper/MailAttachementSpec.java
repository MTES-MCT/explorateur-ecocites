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

package isotope.modules.mail.helper;

import javax.activation.DataSource;

/**
 * Created by oturpin on 20/12/16.
 */
public class MailAttachementSpec {

	private final DataSource content;
	private final String attachementName;
	private final String mimeType;
	private final boolean inline;

	MailAttachementSpec(String name, DataSource content, String mimeType, boolean inline) {
		this.attachementName = name;
		this.content = content;
		this.mimeType = mimeType;
		this.inline = inline;
	}

	public DataSource getContent() {
		return content;
	}

	public String getAttachementName() {
		return attachementName;
	}

	public String getMimeType() {
		return mimeType;
	}

	public boolean isInline() {
		return inline;
	}
}
