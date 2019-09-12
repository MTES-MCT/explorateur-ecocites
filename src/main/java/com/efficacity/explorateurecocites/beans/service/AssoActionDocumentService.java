/*
 * Explorateur Écocités
 * Copyright (C) 2019 l'État, ministère chargé du logement
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.efficacity.explorateurecocites.beans.service;

import com.efficacity.explorateurecocites.beans.model.AssoActionDocument;
import com.efficacity.explorateurecocites.beans.repository.AssoActionDocumentRepository;
import isotope.commons.services.CrudEntityService;
import java.lang.Long;
import org.springframework.stereotype.Service;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 *
 * Date de génération : 15/02/2018
 */
@Service
public class AssoActionDocumentService extends CrudEntityService<AssoActionDocumentRepository, AssoActionDocument, Long> {
  public AssoActionDocumentService(AssoActionDocumentRepository repository) {
    super(repository);
  }
}
