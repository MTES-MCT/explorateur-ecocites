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

package com.efficacity.explorateurecocites.beans.repository;

import com.efficacity.explorateurecocites.beans.model.AssoIndicateurObjet;
import com.efficacity.explorateurecocites.utils.enumeration.TYPE_OBJET;
import org.hibernate.type.ObjectType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 *
 * Date de génération : 20/02/2018
 */
@Repository
public interface AssoIndicateurObjetRepository extends AssoIndicateurObjetDefaultRepository {

    public List<AssoIndicateurObjet> findAllByIdObjetAndTypeObjet(Long IdObjet, String typeObjet);
    public List<AssoIndicateurObjet> findAllByIdObjetAndTypeObjetOrderByIdIndicateurAsc(Long IdObjet, String typeObjet);


    public AssoIndicateurObjet findByIdObjetAndTypeObjetAndIdIndicateurAndUniteAndPosteCalcule(Long idAction, String typeObjet,Long idIndicateur, String unite, String posteCalcule);

    public List<AssoIndicateurObjet> findAllByIdIndicateurAndTypeObjet(Long idIndicateur, String objectType);
    public List<AssoIndicateurObjet> findAllByIdIndicateurAndTypeObjetOrderByIdObjet(Long idIndicateur, String objectType);
}
