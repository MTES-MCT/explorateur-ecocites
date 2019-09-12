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

package com.efficacity.explorateurecocites.ui.bo.service;

import com.efficacity.explorateurecocites.ui.bo.forms.tables.helpers.*;
import com.efficacity.explorateurecocites.utils.table.InputType;
import com.efficacity.explorateurecocites.utils.table.TableColumn;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by rxion on 19/02/2018.
 */
@Service
public class TablePaginationService {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TablePaginationService.class);

    @Autowired
    MessageSourceService messageSourceService;

    /**
     * Récupérer tous les champs annotés par {@link TableColumn} et générer
     * une liste des noms des colonnes
     *
     * @param clazz  classe qui représente le tableau
     * @param locale La langue dans laquelle écrire
     * @return une liste des noms des colonnes
     */
    public List<String> processColumns(Class<?> clazz, Locale locale) {
        List<String> res = new ArrayList<>();
        final Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            TableColumn column = field.getAnnotation(TableColumn.class);
            if (column != null) {
                res.add(TableColumn.USE_DEFAULT_NAME.equals(column.value())
                        ? field.getName() : messageSourceService.getMessageSource().getMessage(column.value(), null, locale));
            }
        }
        return res;
    }
    /**
     * Générer l'ensemble de valeurs à partir d'une liste des objets
     * Chaque objet en paramètre représente une ligne de donnée au tableau
     *
     * @param objects l'ensembel des objets à traiter, ils doievent tous à la même classe
     * @return un map où chaque {@code entry} représent une ligne
     */
    public <T> Map<Long, List<String>> processData(List<T> objects, Class<T> tClass) {
        if (!(objects != null && !objects.isEmpty())) {
            return new LinkedHashMap<>();
        }
        final Field[] fields = tClass.getDeclaredFields();
        LinkedHashMap<Long, List<String>> res = new LinkedHashMap<>(objects.size());
        long index = 0L;
        for (Object o : objects) {
            processObjectTable(o, res, index, fields);
            index++;
        }
        return res;
    }

    /**
     * Générer l'ensemble de valeurs à partir d'une liste des objets
     * Chaque objet en paramètre représente une ligne de donnée au tableau
     *
     * @param object l'objet à traiter
     * @param res la map dans laquelle ajout le résultat
     * @param suggestedIndex L'index à utiliser si l'objet n'en a pas
     * @param fields
     * @return un map où chaque {@code entry} représent une ligne
     */
    public void processObjectTable(Object object, Map<Long, List<String>> res, Long suggestedIndex, final Field[] fields) {
        List<String> vals = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            TableColumn column = field.getAnnotation(TableColumn.class);
            if (column != null) {
                try {
                    Object val = field.get(object);
                    vals.add("" + (val != null ? val : ""));
                } catch (IllegalAccessException e) {
                    vals.add("");
                }
            }
            if (field.getName().equals("id")) {
                try {
                    // Override de l'index passé en paramètre
                    suggestedIndex = (Long) field.get(object);
                } catch (IllegalAccessException e) {
                    vals.add("");
                }
            }
        }
        res.put(suggestedIndex, vals);
    }

    public ObjectAsMap processSingleObject(final Object obj, Locale locale) {
        ObjectAsMap res = new ObjectAsMap();
        if (obj != null) {
            final Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                TableColumn column = field.getAnnotation(TableColumn.class);
                if (column != null) {
                    try {
                        String fieldName = TableColumn.USE_DEFAULT_NAME.equals(column.value()) ?
                                field.getName() : messageSourceService.getMessageSource().getMessage(column.value(), null, locale);
                        if (column.type().equals(InputType.BASIC)) {
                            Object val = field.get(obj);
                            res.getFields().add(new InputField(field.getName(), fieldName, "" + (val != null ? val : ""), column.required()));
                        } else if (column.type().equals(InputType.SELECT)){
                            String defaultFieldName = field.getName() + "Defaults";
                            Field defaultField = obj.getClass().getDeclaredField(defaultFieldName);
                            defaultField.setAccessible(true);
                            Object defaultValues = defaultField.get(obj);
                            if (defaultValues != null && List.class.isAssignableFrom(defaultValues.getClass())) {
                                Object val = field.get(obj);
                                // Unchecked cast car on ne peut pas vérifier le type exacte d'une classe templaté au runtime (même erasure)
                                // Du coup on vérifie que c'est une liste mais pas que c'est une liste de SelectOption
                                res.getFields().add(new SelectField(field.getName(), fieldName,"" + (val != null ? val : ""), (List<SelectOption>) defaultValues, column.required()));
                            }
                        } else if (column.type().equals(InputType.FILE)) {
                            //Chercher l'id dans les files upload
                            //Ajouter le nom du fichier
                            Object val = field.get(obj);
                            res.getFields().add(new FileField(field.getName(), fieldName, "" + (val != null ? val : ""), column.required()));
                        } else if (column.type().equals(InputType.DATE)) {
                            Object val = field.get(obj);
                            res.getFields().add(new DateField(field.getName(), fieldName, "" + (val != null ? val : ""), column.required()));
                        } else if (column.type().equals(InputType.HIDDEN)) {
                            Object val = field.get(obj);
                            res.getFields().add(new HiddenInputField(field.getName(), fieldName, "" + (val != null ? val : ""), column.required()));
                        } else {
                            LOGGER.warn("Ce type d'input field n'est pas traité correctement: {}", column.type().getCode());
                        }
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        LOGGER.warn("Une erreur est apparue en essayant de process l'object: {}", obj);
                    }
                } else {
                    if (field.getName().startsWith("id")) {
                        try {
                            res.setId("" + field.get(obj));
                        } catch (IllegalAccessException e) {
                            LOGGER.warn("Une erreur est apparue en essayant de process l'object: {}", obj);
                        }
                    }
                }
            }
        }
        return res;
    }
}
