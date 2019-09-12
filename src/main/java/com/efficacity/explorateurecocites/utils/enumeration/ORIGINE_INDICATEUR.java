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

package com.efficacity.explorateurecocites.utils.enumeration;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ORIGINE_INDICATEUR {
    ADEME_AEU2("[origin_prefix_code]_ADEME_AEU2", "ADEME AEU2"),
    ADEME_BILAN_CARBONE("[origin_prefix_code]_ADEME_BILAN_CARBONE", "ADEME Bilan Carbone"),
    AMF_ET_IGD("[origin_prefix_code]_AMF_ET_IGD", "AMF et IGD"),
    AURA_MONTPELLIER("[origin_prefix_code]_AURA_MONTPELLIER", "AURA Montpellier"),
    CEREMA_EVALUATION_A_POSTERIORI_DES_TCSP_2015("[origin_prefix_code]_CEREMA_EVALUATION_A_POSTERIORI_DES_TCSP_2015", "CEREMA, Évaluation a posteriori des TCSP, 2015"),
    CITYKEYS("[origin_prefix_code]_CITYKEYS", "CITYkeys"),
    CSTB_ET_IMBE_2011("[origin_prefix_code]_CSTB_ET_IMBE_2011", "CSTB et IMBE 2011"),
    DISPOSITIF_REGLEMENTAIRE("[origin_prefix_code]_DISPOSITIF_REGLEMENTAIRE", "Dispositif réglementaire"),
    DOSSIER_LABELLISATION_ECO_QUARTIER_2012("[origin_prefix_code]_DOSSIER_LABELLISATION_ECO_QUARTIER_2012", "Dossier labellisation EcoQuartier 2012"),
    EFFICACITY_2017("[origin_prefix_code]_EFFICACITY_2017", "Efficacity 2017"),
    EN_15316_4_5("[origin_prefix_code]_EN_15316_4_5", "EN 15316-4-5"),
    ISO37120("[origin_prefix_code]_ISO37120", "ISO 37120"),
    ISO37122("[origin_prefix_code]_ISO37122", "ISO 37122"),
    ISO_15686_5("[origin_prefix_code]_ISO_15686_5", "ISO 15686-5"),
    ISO_TS37151("[origin_prefix_code]_ISO_TS37151", "ISO TS 37151"),
    LABEL_E("[origin_prefix_code]_LABEL_E", "Label E+C-"),
    OBSERVATOIRE_DE_LA_QUALITE_DE_L_AIR_INTERIEUR("[origin_prefix_code]_OBSERVATOIRE_DE_LA_QUALITE_DE_L_AIR_INTERIEUR", "Observatoire de la qualité de l’air intérieur"),
    OBSERVATOIRE_NATIONAL_DES_SERVICES_D_EAU_ET_D_ASSAINISSEMENT("[origin_prefix_code]_OBSERVATOIRE_NATIONAL_DES_SERVICES_D_EAU_ET_D_ASSAINISSEMENT", "Observatoire national des services d'eau et d'assainissement"),
    PCAET("[origin_prefix_code]_PCAET", "PCAET"),
    PEER("[origin_prefix_code]_PEER", "PEER"),
    PLUI_DE_BORDEAUX_METROPOLE("[origin_prefix_code]_PLUI_DE_BORDEAUX_METROPOLE", "PLUi de Bordeaux Métropole"),
    REFERENTIEL_ECO_QUARTIER_2017("[origin_prefix_code]_REFERENTIEL_ECO_QUARTIER_2017", "Référentiel EcoQuartier 2017"),
    REGLEMENTATION_THERMIQUE("[origin_prefix_code]_REGLEMENTATION_THERMIQUE", "Réglementation thermique"),
    RFSC("[origin_prefix_code]_RFSC", "RFSC"),
    SCOT_DE_NANTES_SAINT_NAZAIRE("[origin_prefix_code]_SCOT_DE_NANTES_SAINT_NAZAIRE", "SCOT de Nantes Saint Nazaire"),
    SITE_DU_CEREMA("[origin_prefix_code]_SITE_DU_CEREMA", "Site du CEREMA"),
    UN_AMENAGEMENT_DURABLE_POUR_PARIS_2010("[origin_prefix_code]_UN_AMENAGEMENT_DURABLE_POUR_PARIS_2010", "Un aménagement durable pour Paris 2010"),
    VILLE_DE_DEMAIN("[origin_prefix_code]_VILLE_DE_DEMAIN", "Ville de demain"),
    ECO_CITE_ARDOINES_SEINE_AMONT("[origin_prefix_code]_ardoines_seine_amont", "ÉcoCité Ardoines - Seine Amont"),
    ECO_CITE_BREST_METROPOLE("[origin_prefix_code]_brest_metropole", "ÉcoCité Brest Métropole"),
    ECO_CITE_BORDEAUX_PLAINE_DE_GARONNE("[origin_prefix_code]_ECO_CITE_BORDEAUX_PLAINE_DE_GARONNE", "ÉcoCité Bordeaux Plaine de Garonne"),
    ECO_CITE_CLERMONT_METROPOLE("[origin_prefix_code]_ECO_CITE_CLERMONT_METROPOLE", "ÉcoCité Clermont Métropole"),
    ECO_CITE_EURO_MEDITERRANEE_MARSEILLE("[origin_prefix_code]_ECO_CITE_EURO_MEDITERRANEE_MARSEILLE", "ÉcoCité EuroMéditerranée Marseille"),
    ECO_CITE_GRAND_LYON("[origin_prefix_code]_grand_lyon", "ÉcoCité Grand Lyon"),
    ECO_CITE_GRAND_ROISSY("[origin_prefix_code]_grand_roissy", "ÉcoCité Grand Roissy"),
    ECO_CITE_GRENOBLE_ALPES_METROPOLE("[origin_prefix_code]_grenoble_alpes_metropole", "ÉcoCité Grenoble-Alpes Métropole"),
    ECO_CITE_LA_DEFENSE_SEINE_ARCHE("[origin_prefix_code]_la_defense_seine_arche", "ÉcoCité La Défense Seine Arche"),
    ECO_CITE_MARNE_LA_VALLEE("[origin_prefix_code]_marne_la_vallee", "ÉcoCité Marne-la-Vallée"),
    ECO_CITE_METROPOLE_EUROPEENNE_DE_LILLE("[origin_prefix_code]_metropole_europeenne_de_lille", "ÉcoCité Métropole Européenne de Lille"),
    ECO_CITE_METROPOLE_ROUEN_NORMANDIE("[origin_prefix_code]_metropole_rouen_normandie", "ÉcoCité Métropole Rouen – Normandie"),
    ECO_CITE_METZ_METROPOLE("[origin_prefix_code]_metz_metropole", "ÉcoCité Metz Métropole"),
    ECO_CITE_MONT_VALERIEN("[origin_prefix_code]_mont_valerien", "ÉcoCité Mont-Valérien"),
    ECO_CITE_MONTPELLIER_MEDITERRANEE_METROPOLE("[origin_prefix_code]_montpellier_mediterranee_metropole", "ÉcoCité Montpellier Méditerranée Métropole"),
    ECO_CITE_NANTES_SAINT_NAZAIRE("[origin_prefix_code]_nantes_saint_nazaire", "ÉcoCité Nantes Saint-Nazaire"),
    ECO_CITE_NICE_COTE_D_AZUR_PLAINE_DU_VAR("[origin_prefix_code]_nice_cote_d_azur_plaine_du_var", "ÉcoCité Nice Côte d’Azur Plaine du Var"),
    ECO_CITE_PARIS_ARC_DE_L_INNOVATION("[origin_prefix_code]_paris_arc_de_l_innovation", "ÉcoCité Paris -Arc de l'innovation"),
    ECO_CITE_PARIS_SACLAY("[origin_prefix_code]_paris_saclay", "ÉcoCité Paris - Saclay"),
    ECO_CITE_PAYS_HAUT_VAL_D_ALZETTE("[origin_prefix_code]_pays_haut_val_d_alzette", "ÉcoCité Pays Haut Val d'Alzette"),
    ECO_CITE_PLAINE_COMMUNE("[origin_prefix_code]_plaine_commune", "ÉcoCité Plaine Commune"),
    ECO_CITE_PLAINE_DE_L_OURQ_EST_ENSEMBLE("[origin_prefix_code]_plaine_de_l_ourq_est_ensemble", "ÉcoCité Plaine de l'Ourq - Est Ensemble"),
    ECO_CITE_QUARTIER_UNIVERSITAIRE_INTERNATIONAL_DU_GRAND_PARIS("[origin_prefix_code]_quartier_universitaire_international_du_grand_paris", "ÉcoCité Quartier Universitaire International du Grand Paris (QUIGP)"),
    ECO_CITE_RENNES_METROPOLE("[origin_prefix_code]_rennes_metropole", "ÉcoCité Rennes Métropole"),
    ECO_CITE_SEINE_AVAL("[origin_prefix_code]_seine_aval", "ÉcoCité Seine Aval"),
    ECO_CITE_SENART("[origin_prefix_code]_senart", "ÉcoCité Sénart"),
    ECO_CITE_SOCIETE_DU_GRAND_PARIS("[origin_prefix_code]_societe_du_grand_paris", "ÉcoCité Société du Grand Paris"),
    ECO_CITE_STRASBOURG_METROPOLE_DES_DEUX_RIVES("[origin_prefix_code]_strasbourg_metropole_des_deux_rives", "ÉcoCité Strasbourg Métropole des Deux-Rives"),
    ECO_CITE_TERRITOIRE_DE_LA_COTE_OUEST("[origin_prefix_code]_territoire_de_la_cote_ouest", "ÉcoCité Territoire de la Côte Ouest"),
    ECO_CITE_TOULON_PROVENCE_METROPOLE("[origin_prefix_code]_toulon_provence_metropole", "ÉcoCité Toulon Provence Métropole"),
    ECO_CITE_TOULOUSE_METROPOLE("[origin_prefix_code]_toulouse_metropole", "ÉcoCité Toulouse Métropole");

    static final String PREFIX = "[origin_prefix_code]_";

    String code;
    String libelle;

    ORIGINE_INDICATEUR(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public static ORIGINE_INDICATEUR getByCode(String code)
    {
        if (code == null || !code.startsWith(PREFIX)) {
            return null;
        }
        for(ORIGINE_INDICATEUR enu : values())
        {
            if(enu.code.equals(code))
                return enu;
        }

        return null;
    }

    public static LinkedHashMap<String, String> toMap() {
        return Stream.of(values())
                .collect(Collectors.toMap(ORIGINE_INDICATEUR::getCode, ORIGINE_INDICATEUR::getLibelle,
                        (k1, k2) -> k1,
                        LinkedHashMap::new));
    }

    public static Optional<ORIGINE_INDICATEUR> getByCodeIfExist(String code)
    {
        return Optional.ofNullable(getByCode(code));
    }

    public static boolean isDefined(String code) {
        return code.startsWith(PREFIX) && getByCode(code) != null;
    }

    public static String getLibelleForCode(String code) {
        return getByCodeIfExist(code).map(ORIGINE_INDICATEUR::getLibelle).orElse(code);
    }

    public String getCode() {
        return code;
    }
    public String getLibelle() {
            return libelle;
        }
}
