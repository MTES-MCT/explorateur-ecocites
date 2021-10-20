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

import isotope.commons.entities.BaseEntity;

import javax.persistence.*;

@Entity
@Table(
		name = "is_mail_attachement"
)
public class MailAttachement extends BaseEntity {
	/**
	 * Id of the mail
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "id_mail",
			nullable = false,
			referencedColumnName = "id"
	)
	private Mail mail;

	/**
	 * Name of the attachement
	 */
	@Column(
			name = "attachement_name",
			nullable = false,
			length = 255
	)
	private String attachementName;

	/**
	 * Content of the mail (Render already don)
	 */
	@Column(
			name = "content",
			nullable = false
	)
	private byte[] content;

	/**
	 * Mime type for this attachement
	 */
	@Column(
			name = "mime_type",
			nullable = false,
			length = 45
	)
	private String mimeType;

	@Column(
			name = "inline",
			nullable = false
	)
	private boolean inline;

	public MailAttachement() {
	}

	public Mail getMail() {
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public String getAttachementName() {
		return this.attachementName;
	}

	public void setAttachementName(String attachementName) {
		this.attachementName = attachementName;
	}

	public byte[] getContent() {
		return this.content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getMimeType() {
		return this.mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public boolean isInline() {
		return inline;
	}

	public void setInline(boolean inline) {
		this.inline = inline;
	}
}
