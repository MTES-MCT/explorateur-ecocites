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

package com.efficacity.explorateurecocites.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NameNotFoundException;
import javax.naming.ServiceUnavailableException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomValidator {

    private static Logger logger = LoggerFactory.getLogger(CustomValidator.class);

    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        }
        if (o instanceof String) {
            return ((String) o).trim().replaceAll("\\u00A0", "").length() == 0;
        }
        if (o.getClass().isArray()) {
            return ((Object[]) o).length == 0;
        }
        if (o instanceof Collection) {
            return ((Collection<?>) o).isEmpty();
        }
        if (o instanceof Map) {
            return ((Map<?, ?>) o).isEmpty();
        }

        return false;
    }

    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    private static final Pattern RGB_PATTERN = Pattern.compile("^([a-fA-F0-9]){6}$");

    /**
     * Vérifie que la valeur est au format RRGGBB (0-9,A-F,a-f pour chaque)
     * @param value La valeur à tester
     * @return true si la valeur est au format RGB
     */
    public static boolean isRGB(String value)
    {
        Matcher matcher = RGB_PATTERN.matcher(value);
        return matcher.matches();
    }

    /**
     * Vérifie que tous les caracteres de cette chaine sont bien des chiffres
     */
    public static boolean isNumero(String champ) {
        boolean ok = champ!=null;
        if (champ != null) {
            for (int i=0; ok && i < champ.length(); i++) {
                ok = champ.charAt(i)>='0' && champ.charAt(i)<='9';
            }
        }
        return ok;
    } // isEntier

    /**
     * Vérifie qu'une valeur entière est comprise entre 2 bornes
     * @param value Valeur à vérifier
     * @param min Borne inférieure (incluse)
     * @param max Borne supérieure (incluse)
     * @return true si la valeur est comprise dans l'interval
     */
    public static boolean checkRange(String value, double min, double max)
    {
        if(!value.equals("")) {
            double numericValue = Double.parseDouble(value);
            return (numericValue >= min && numericValue <= max);
        }
        else{
            return false;
        }
    }

    /**
     * Verifie qu'une chaîne ne dépasse pas une taille donnée.
     * On considère que null ne dépasse aucune taille.
     * @param value Chaîne de caractères à tester
     * @param max Longueur maximale (incluse)
     * @return true si la chaîne est null ou si elle sa
     * longueur est inférieure ou égale au maximum indiqué
     */
    public static boolean checkMaxSize(String value, int max)
    {
        return value==null || value.length()<=max;
    }

    /**
     * Verifie qu'il y ai "totaldigits" chiffres significatifs maximum dont "fractiondigits" chiffres maximum apres la virgule.
     * Verifie egalement que la chaine est bien numerique.
     * @param value
     * @param totalDigits
     * @param fractionDigits
     */
    public static boolean checkDecimal(String value, int totalDigits, int fractionDigits)
    {
        if (value==null || totalDigits<0 || fractionDigits<0)
        {
            return false;
        }

        boolean avantVirgule = true;
        boolean apresAutreQueZero = false;
        int nbAvant=0;
        int nbApres=0;
        for (int i=0; i<value.length(); i++) {
            char c = value.charAt(i);
            if (i==0 && c=='-') {
                continue;
            }
            if (avantVirgule && (c=='.' || c==',') ) {
                avantVirgule=false;
                continue;
            }
            if (c<'0' || c>'9') {
                return false;
            }
            if (avantVirgule && (nbAvant>0 || c!='0')) {
                nbAvant++;
            } else
            if (!avantVirgule) {
                nbApres++;
                apresAutreQueZero=apresAutreQueZero || c!='0';
            }
        }

        if (!apresAutreQueZero) {
            nbApres=0;
        }

        return (nbAvant+nbApres<=totalDigits && nbApres<=fractionDigits);
    }
    /**
     * Verifie que la chaine soit numérique
     * et qu'elle ne contienne qu'un maximum de <code>fractionDigits</code> elements après la virgule.
     * @param value
     * @param fractionDigits
     */
    public static boolean checkDecimalWithZero(String value, int fractionDigits)
    {
        if (value==null || fractionDigits<0)
        {
            return false;
        }
        boolean avantVirgule = true;
        int nbApres=0;
        for (int i=0; i<value.length(); i++) {
            char c = value.charAt(i);
            if (i==0 && (c=='-' || c=='+')) {
                continue;
            }
            if (avantVirgule && (c=='.' || c==',') ) {
                avantVirgule=false;
                continue;
            }
            if (c<'0' || c>'9') {
                return false;
            }
            if (!avantVirgule) {
                nbApres++;
            }
        }

        return nbApres <= fractionDigits;
    }


    /**
     * Vérifier qu'une chaine fait bien entre min et max caracteres
     */
    static boolean checkSize(String numero, int min, int max) {
        return numero!=null && numero.length()>=min && numero.length()<=max;
    }

    public static boolean isNumeric(String value) {
        if (isEmpty(value)) {
            return false;
        }
        boolean pointOk = false;
        for (int i=0; i<value.length(); i++) {
            char c = value.charAt(i);
            if (c<'0' || c>'9') {
                if (c=='.' || c==',') {
                    if (pointOk) {
                        return false;
                    }
                    pointOk=true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean isSignedNumeric(String value) {
        if (isEmpty(value)) {
            return false;
        }
        boolean pointOk = false;
        for (int i=0; i<value.length(); i++) {
            char c = value.charAt(i);
            if (c<'0' || c>'9') {
                if (c=='.' || c==',') {
                    if (pointOk) {
                        return false;
                    }
                    pointOk=true;
                } else if (c=='-' && i==0)
                {

                } else
                {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * validation de valeurs numériques signées et non décimales
     * @param value la valeur à tester
     * @return true si la valeur une numérique et non décimale
     */
    public static boolean isSignedNonDecimalNumeric(String value) {
        if (isEmpty(value)) {
            return false;
        }
        for (int i=0; i<value.length(); i++) {
            char c = value.charAt(i);
            if (c<'0' || c>'9') {
                if (c=='-' && i==0)
                {

                } else
                {
                    return false;
                }
            }
        }
        return true;
    }

    public static String formatMontant(int montant) {
        return formatNumeric(montant, 2);
    }

    public static String formatNumeric(int montant, int nbCentimes) {
        String s = Integer.toString(montant);
        if (nbCentimes==0) {
            return s;
        }
        while (s.length()<nbCentimes+1) {
            s="0"+s;
        }
        s=s.substring(0, s.length()-nbCentimes)+"."+s.substring(s.length()-nbCentimes);
        return s;
    }

    public static int parseMontant(String s) {
        return parseNumeric(s, 2);
    }

    public static int parseNumeric(String str, int nbCentimes) {
        String s = str;
        int dotindex = s.indexOf(".");
        if (dotindex==-1) {
            dotindex=s.indexOf(",");
        }
        if (dotindex==-1) {
            return Integer.parseInt(s)* (int)Math.pow(10, nbCentimes);
        }

        if (s.length()>dotindex+1+nbCentimes) {
            s=s.substring(0, dotindex+nbCentimes+1);
        } else {
            while (s.length()<dotindex+nbCentimes+1) {
                s+="0";
            }
        }
        s=s.substring(0, dotindex)+s.substring(dotindex+1);

        return Integer.parseInt(s);
    }

    /**
     * Cette méthode permet de controler la validite d'un email.
     * On fait d'abord une vérification sur la syntaxe puis un lookup dns est fait via jndi pour
     * vérifier que le domaine existe et posséde bien un MX.
     */
    private static Pattern emailTest = Pattern.compile("^([a-zA-Z0-9_-]+\\.)*[a-zA-Z0-9_-]+@([a-zA-Z0-9-]{2,}\\.)+[a-zA-Z]{2,10}$");
    private static Set<String> mxCacheOks = new HashSet<String>();
    private static DirContext dirContext = null;
    public static boolean isEmailValid(String email){

        boolean valid = isEmailValidSimple(email);
        if (!valid) {
            return false;
        }

        int arobas = email.indexOf("@");
        String domain = email.substring(arobas+1);

        if (mxCacheOks.contains(domain)) {
            return true;
        }
        domain=domain.trim().toLowerCase();
        try {
            if (dirContext==null) {
                Hashtable<String, String> env = new Hashtable<String, String>();
                env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
                env.put("com.sun.jndi.dns.timeout.initial", "2000");
                env.put("com.sun.jndi.dns.timeout.retries", "3");
                dirContext = new InitialDirContext(env);
            }

            Attributes attrs = dirContext.getAttributes(domain, new String[] {"MX"});

            if (attrs.get("MX")==null) {
                attrs = dirContext.getAttributes(domain, new String[] {"A"});
                if (attrs.get("A")==null) {
                    return false;
                }
            }
        } catch (NameNotFoundException nnfe) {
            return false;
        } catch (ServiceUnavailableException sue) {
            return false;
        }catch (Exception t) {
            logger.error("", t);
        }

        mxCacheOks.add(domain);

        return true;
    }

    /**
     * Validation de l'email sans controle DNS
     * @param email Email a tester
     */
    public static boolean isEmailValidSimple(String email){

        return email!=null && emailTest.matcher(email).matches();
    }

    public static boolean isSiren(final String siren) {
        if (siren == null || siren.length() != 9) {
            return false;
        }
        for (int i=0; i < siren.length(); i++) {
            if (siren.charAt(i) < '0' || siren.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }
}

