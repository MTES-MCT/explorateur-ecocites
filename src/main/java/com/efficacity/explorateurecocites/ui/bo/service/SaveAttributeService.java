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

import com.efficacity.explorateurecocites.ui.bo.exceptions.SaveAttributException;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Created by dylan on 14/02/18.
 */
@Service
public class SaveAttributeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaveAttributeService.class);

    @Autowired
    MessageSourceService messageSourceService;

    /**
     * Méthode générale pour mettre à jour un attribut pour un objet.
     * Pourtant on ne sauvegarde pas l'objet dans cette méthode
     *
     * @param bizBean   l'objet dont on récupéra le bean a sauvegarder en base
     * @param attrNom   le nom de l'attribut à mettre à jour
     * @param attrValue la valeur de l'attribut à mettre à jour
     * @param errors    objet d'erreur à renvoyer s'il y a en
     * @return objet ayant la nouvelle valeur de l'attribut
     * @throws SaveAttributException si l'attribut donné n'est pas trouvé dans cet objet,
     *                               ou la valeur d'entrée viole les contraintes définies dans cet objet, etc
     */
    public Object majAttribut(Object bizBean, String attrNom, String attrValue, Errors errors, Locale locale) {
        //TODO: Droits de modifications des champs par user au lieu de tout autoriser
        int length;
        Class<?> type = null;
        Object biz = null;
        Class clazzBean = bizBean.getClass();
        try {
            Method method = clazzBean.getMethod("getTo");
            biz = method.invoke(bizBean);
        } catch (NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            biz = bizBean;
        }
        if (biz != null) {
            try {
                Class clazz = biz.getClass();
                Field field = clazz.getDeclaredField(attrNom);
                // Permettre d'avoir l'accès aux champs privés
                field.setAccessible(true);
                type = field.getType();

                // Constraint de la BDD NOTNULL
                if (!field.getAnnotation(Column.class).nullable() && attrValue == null) {
                    errors.rejectValue("attributValue", "error.attribut.notNull", messageSourceService.getMessageSource().getMessage("error.attribut.notNull", null, locale));
                    throw new SaveAttributException(errors);
                }

                // Constraint de la longeur de String de la BDD
                if (String.class.equals(type) && attrValue != null && (length = field.getAnnotation(Column.class).length()) < attrValue.length()) {
                    errors.rejectValue("attributValue", "error.attribut.size.max", messageSourceService.getMessageSource().getMessage("error.attribut.size.max", new Object[]{length}, locale));
                    throw new SaveAttributException(errors);
                }

                // Variable à évaluer les format =< si erreur exception catch avec message d'erreur sur le format
                String val = attrValue != null ? attrValue.replaceAll("\\s", "") : "";
                if (CustomValidator.isEmpty(val)) {
                    field.set(biz, null);
                } else if (attrValue != null && type.isAssignableFrom(attrValue.getClass())) {
                    field.set(biz, type.cast(attrValue));
                } else if (type.isAssignableFrom(Long.class) || type.equals(Long.TYPE)) {
                    field.set(biz, Long.valueOf(val));
                } else if (type.isAssignableFrom(Integer.class) || type.equals(Integer.TYPE)) {
                    field.set(biz, Integer.valueOf(val));
                } else if (type.isAssignableFrom(Short.class) || type.equals(Short.TYPE)) {
                    field.set(biz, Short.valueOf(val));
                } else if (type.isAssignableFrom(Boolean.class) || type.equals(Boolean.TYPE)) {
                    field.set(biz, Boolean.valueOf(val));
                } else if (type.isAssignableFrom(Double.class) || type.equals(Double.TYPE)) {
                    field.set(biz, Double.valueOf(val));
                } else if (type.isAssignableFrom(BigDecimal.class)) {
                    field.set(biz, new BigDecimal(val));
                } else if (type.isAssignableFrom(LocalDateTime.class)) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    field.set(biz, LocalDate.parse(val, formatter).atStartOfDay() );
                } else {
                    field.set(biz, attrValue);
                }

                // On va appeler la validation spécifique du bean (logique des date, liste de valeur, ....)
                // Ainsi du not null mais hors celui de la BDD car des insert doit être fait sans certaine de ces valeur lors de intital import
                try {
                    Method method = clazzBean.getMethod("validateChamps", String.class, Object.class, MessageSource.class, Errors.class, Locale.class);
                    method.invoke(bizBean, attrNom, val, messageSourceService.getMessageSource(), errors, locale);
                } catch (NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                    // Y a pas la méthode => sans erreur on continue
                }
            } catch (SaveAttributException e) {
                // Reject erreur déjà rajouté, on continue normalement
            } catch (NoSuchFieldException | IllegalAccessException e) {
                errors.rejectValue("attributId", "error.technical", messageSourceService.getMessageSource().getMessage("error.technical", null, locale));
            } catch (NumberFormatException | DateTimeParseException e) {
                errors.rejectValue("attributValue", "error.attribut.format", messageSourceService.getMessageSource().getMessage("error.attribut.format", null, locale));
            }
        }
        return biz;
    }
}
