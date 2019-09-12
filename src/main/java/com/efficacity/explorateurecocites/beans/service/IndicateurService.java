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

import com.efficacity.explorateurecocites.beans.Enums;
import com.efficacity.explorateurecocites.beans.biz.IndicateurBean;
import com.efficacity.explorateurecocites.beans.model.AssoIndicateurDomaine;
import com.efficacity.explorateurecocites.beans.model.AssoIndicateurObjectif;
import com.efficacity.explorateurecocites.beans.model.Indicateur;
import com.efficacity.explorateurecocites.beans.repository.IndicateurRepository;
import com.efficacity.explorateurecocites.ui.bo.forms.IndicateurForm;
import com.efficacity.explorateurecocites.ui.bo.service.MessageSourceService;
import com.efficacity.explorateurecocites.utils.CustomValidator;
import com.efficacity.explorateurecocites.utils.enumeration.ECHELLE_INDICATEUR;
import com.efficacity.explorateurecocites.utils.enumeration.ETAT_BIBLIOTHEQUE;
import com.efficacity.explorateurecocites.utils.enumeration.ETAT_VALIDATION;
import isotope.commons.services.CrudEntityService;
import isotope.modules.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.efficacity.explorateurecocites.beans.specification.IndicateurSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Cette classe a été autogénérée. Elle n'est générée qu'une seule fois.
 *
 * Date de génération : 12/02/2018
 */
@Service
public class IndicateurService extends CrudEntityService<IndicateurRepository, Indicateur, Long> {

    @Autowired
    AssoIndicateurObjetService assoIndicateurObjetService;
    @Autowired
    MessageSourceService messageSourceService;
    @Autowired
    AssoIndicateurDomaineService assoIndicateurDomaineService;
    @Autowired
    AssoIndicateurObjectifService assoIndicateurObjectifService;

    public IndicateurService(IndicateurRepository repository) {
        super(repository);
    }

    public Indicateur findOneByIdAndEchelleAndNatureIn(Long Id, String echelle, List<String> natures) {
        return repository.findOneByIdAndEchelleAndNatureIn(Id, echelle, natures);
    }

    public Indicateur findOneByIdAndEchelleAndNatureInValideVisible(Long Id, String echelle, List<String> natures) {
        return repository.findOneByIdAndEchelleAndEtatValidationAndEtatBibliothequeAndNatureIn(Id, echelle, ETAT_VALIDATION.VALIDE.getCode(), ETAT_BIBLIOTHEQUE.VISIBLE.getCode(),  natures );
    }

    public Indicateur findOneByIdVisibleForUser(Long id, String echelle, List<String> natures, JwtUser user) {
        if (natures != null && !natures.isEmpty()) {
            return repository.findOne(where(isValideAndPublieOrCreateur(user.getEmail())).and(hasEchelle(echelle)).and(hasNatureIn(natures)).and(hasId(id)));
        } else {
            return repository.findOne(where(isValideAndPublieOrCreateur(user.getEmail())).and(hasEchelle(echelle)).and(hasId(id)));
        }
    }

    public IndicateurBean findOneIndicateur(Long idIndicateur){
        return toIndicateurBean(repository.findOne(idIndicateur));
    }

    public IndicateurBean findOneIndicateurValideVisible(Long idIndicateur){
        return toIndicateurBean(repository.findOneByIdAndEtatValidationAndEtatBibliotheque(idIndicateur, ETAT_VALIDATION.VALIDE.getCode(), ETAT_BIBLIOTHEQUE.VISIBLE.getCode()));
    }
    public IndicateurBean findOneIndicateurValideVisibleForUser(Long idIndicateur, JwtUser user){
        return toIndicateurBean(repository.findOne(where(isValideAndPublieOrCreateur(user.getEmail())).and(hasId(idIndicateur))));
    }

    public String delete(Long idIndicateur, Locale locale) {
        // Si l'indicateur est associer à une action ou une écocité => KO on delete pas
        if(assoIndicateurObjetService.countByIdIndicateur(idIndicateur) > 0){
            return messageSourceService.getMessageSource().getMessage("error.indicateur.suppression.lieeAction", null, locale);
        } else {
            // On supprime les associations vers les étiquettes
            List<AssoIndicateurDomaine> listeAssoIndicateurDomaine =  assoIndicateurDomaineService.getListByIndicateur(idIndicateur);
            for(AssoIndicateurDomaine assoIndicateurDomaine : listeAssoIndicateurDomaine){
                assoIndicateurDomaineService.delete(assoIndicateurDomaine.getId());
            }

            List<AssoIndicateurObjectif> listeAssoIndicateurObjectif =  assoIndicateurObjectifService.getListByIndicateur(idIndicateur);
            for(AssoIndicateurObjectif assoIndicateurObjectif : listeAssoIndicateurObjectif){
                assoIndicateurObjectifService.delete(assoIndicateurObjectif.getId());
            }

            repository.delete(idIndicateur);
            return "";
        }
    }

    public List<IndicateurBean> toIndicateurBeanList (List<Indicateur> indicateurs){
        if(indicateurs != null) {
            List<IndicateurBean> actionsBean = new ArrayList<IndicateurBean>();
            for (Indicateur indicateur : indicateurs) {
                actionsBean.add(toIndicateurBean(indicateur));
            }
            return actionsBean;
        } else {
            return null;
        }
    }

    public IndicateurBean toIndicateurBean (Indicateur indicateur){
        if(indicateur != null) {
            return new IndicateurBean(indicateur);
        } else {
            return null;
        }
    }

    /**
    * Récupérer la liste des indicateurs en fonction de critères de filtrage
    * @return l'ensemble des actions
    */
    public List<Indicateur> getListFiltre(Map<String,String> filtres, JwtUser user) {
        Specifications<Indicateur> spec = null;
        if(filtres != null){
            for(String champs : filtres.keySet()){
                if(CustomValidator.isNotEmpty(filtres.get(champs))) {
                    switch (champs) {
                        case "nomCourt":
                            if (spec == null) {
                                spec = where(hasNomCourt(filtres.get(champs)));
                            } else {
                                spec = spec.and(hasNomCourt(filtres.get(champs)));
                            }
                            break;
                        case "nomLong":
                            if (spec == null) {
                                spec = where(hasNomLong(filtres.get(champs)));
                            } else {
                                spec = spec.and(hasNomLong(filtres.get(champs)));
                            }
                            break;
                        case "nature":
                            if (spec == null) {
                                spec = where(hasNature(filtres.get(champs)));
                            } else {
                                spec = spec.and(hasNature(filtres.get(champs)));
                            }
                            break;
                        case "echelle":
                            if (spec == null) {
                                spec = where(hasEchelle(filtres.get(champs)));
                            } else {
                                spec = spec.and(hasEchelle(filtres.get(champs)));
                            }
                            break;
                        case "origine":
                            if (spec == null) {
                                spec = where(hasOrigine(filtres.get(champs)));
                            } else {
                                spec = spec.and(hasOrigine(filtres.get(champs)));
                            }
                            break;
                        case "objectifs":
                            if (spec == null) {
                                spec = where(hasObjectifs(Long.valueOf(filtres.get(champs))));
                            } else {
                                spec = spec.and(hasObjectifs(Long.valueOf(filtres.get(champs))));
                            }
                            break;
                        case "domaines":
                            if (spec == null) {
                                spec = where(hasDomaines(Long.valueOf(filtres.get(champs))));
                            } else {
                                spec = spec.and(hasDomaines(Long.valueOf(filtres.get(champs))));
                            }
                            break;
                    }
                }
            }
            if(user == null){
                if(filtres.containsKey("etatValidation") && CustomValidator.isNotEmpty(filtres.get("etatValidation"))){
                    if (spec == null) {
                        spec = where(hasEtatValidation(filtres.get("etatValidation")));
                    } else {
                        spec = spec.and(hasEtatValidation(filtres.get("etatValidation")));
                    }
                }
                if(filtres.containsKey("etatBibliotheque") && CustomValidator.isNotEmpty(filtres.get("etatBibliotheque"))){
                    if (spec == null) {
                        spec = where(hasEtatBibliotheque(filtres.get("etatBibliotheque")));
                    } else {
                        spec = spec.and(hasEtatBibliotheque(filtres.get("etatBibliotheque")));
                    }
                }
            } else {
                if (!user.getAuthorities().contains(new SimpleGrantedAuthority(Enums.ProfilsUtilisateur.ADMIN.getCode())) &&
                        !user.getAuthorities().contains(new SimpleGrantedAuthority(Enums.ProfilsUtilisateur.ACCOMPAGNATEUR.getCode()))) {
                    if (spec == null) {
                        spec = where(isValideAndPublieOrCreateur(user.getEmail()));
                    } else {
                        spec = spec.and(isValideAndPublieOrCreateur(user.getEmail()));
                    }
                }
            }
        } else if (user != null) {
            if (!user.getAuthorities().contains(new SimpleGrantedAuthority(Enums.ProfilsUtilisateur.ADMIN.getCode())) &&
                    !user.getAuthorities().contains(new SimpleGrantedAuthority(Enums.ProfilsUtilisateur.ACCOMPAGNATEUR.getCode()))) {
                spec = where(isValideAndPublieOrCreateur(user.getEmail()));
            }
        }
        return repository.findAll(spec);
    }

    /**
     * Récupérer la liste des indicateur en fonction de critères
     *
     * @param spec spécification qui représente des critères de recherche, elle peut être null
     * @return l'ensemble des actions
     */
    public List<Indicateur> getList(Specification<Indicateur> spec) {
        return repository.findAll(spec);
    }

    public List<Indicateur> getListByOrderByIdAsc() {
        return repository.findAllByOrderByIdAsc();
    }

    public Indicateur createOneActionIndicateur(final IndicateurForm indicateurForm, JwtUser user) {
        // on recupère les différents champs depuis le form et on en override certain par les vraies valeurs
        Indicateur indicateur = indicateurForm.getModel();
        indicateur.setEchelle(ECHELLE_INDICATEUR.SPECIFIQUE.getCode());
        indicateur.setEtatValidation(ETAT_VALIDATION.NON_VALIDE.getCode());
        indicateur.setEtatBibliotheque(ETAT_BIBLIOTHEQUE.NON_VISIBLE.getCode());
        return create(indicateur, user);
    }

    public Indicateur createOneEcociteIndicateur(final IndicateurForm indicateurForm, final JwtUser user) {
        // on recupère les différents champs depuis le form et on en override certain par les vraies valeurs
        Indicateur indicateur = indicateurForm.getModel();
        indicateur.setEchelle(ECHELLE_INDICATEUR.TERRITORIALE.getCode());
        indicateur.setEtatValidation(ETAT_VALIDATION.NON_VALIDE.getCode());
        indicateur.setEtatBibliotheque(ETAT_BIBLIOTHEQUE.NON_VISIBLE.getCode());
        return create(indicateur, user);
    }

    public Indicateur createOneIndicateur(final IndicateurForm indicateurForm, final JwtUser user) {
        // on recupère les différents champs depuis le form et on en override certain par les vraies valeurs
        Indicateur indicateur = indicateurForm.getModel();
        indicateur.setEtatValidation(ETAT_VALIDATION.NON_VALIDE.getCode());
        indicateur.setEtatBibliotheque(ETAT_BIBLIOTHEQUE.NON_VISIBLE.getCode());
        return create(indicateur, user);
    }

    public Indicateur createOneIndicateurAdmin(final IndicateurForm indicateurForm, final JwtUser user) {
        // on recupère les différents champs depuis le form et on fait confiance
        return create(indicateurForm.getModel(), user);
    }

    private Indicateur save(final Indicateur indicateur, JwtUser user) {
        indicateur.setUserModification(user.getEmail());
        indicateur.setDateModification(LocalDateTime.now());
        return repository.save(indicateur);
    }

    private Indicateur create(final Indicateur indicateur, JwtUser user) {
        indicateur.setUserModification(user.getEmail());
        indicateur.setDateModification(LocalDateTime.now());
        indicateur.setUserCreation(user.getEmail());
        indicateur.setDateCreation(LocalDateTime.now());
        return repository.save(indicateur);
    }

    public List<Indicateur> getAllDomaineIndicateurVisibleByUser(ECHELLE_INDICATEUR echelleIndicateur, List<String> natures, JwtUser user) {
        List <Long> ids = assoIndicateurDomaineService.getList()
                .stream()
                .map(AssoIndicateurDomaine::getIdIndicateur)
                .collect(Collectors.toList());
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        if (natures != null && !natures.isEmpty()) {
            return repository.findAll(where(isValideAndPublieOrCreateur(user.getEmail())).and(hasEchelle(echelleIndicateur.getCode())).and(hasNatureIn(natures)).and(hasIdIn(ids)));
        } else {
            return repository.findAll(where(isValideAndPublieOrCreateur(user.getEmail())).and(hasEchelle(echelleIndicateur.getCode())).and(hasIdIn(ids)));
        }
    }

    public List<Indicateur> getAllDomaineIndicateurVisibleByUser(final Long idDomaine, ECHELLE_INDICATEUR echelleIndicateur, List<String> natures, JwtUser user) {
        List <Long> ids = assoIndicateurDomaineService.getListByDomaine(idDomaine)
                .stream()
                .map(AssoIndicateurDomaine::getIdIndicateur)
                .collect(Collectors.toList());
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        if (natures != null && !natures.isEmpty()) {
            return repository.findAll(where(isValideAndPublieOrCreateur(user.getEmail())).and(hasEchelle(echelleIndicateur.getCode())).and(hasNatureIn(natures)).and(hasIdIn(ids)));
        } else {
            return repository.findAll(where(isValideAndPublieOrCreateur(user.getEmail())).and(hasEchelle(echelleIndicateur.getCode())).and(hasIdIn(ids)));
        }
    }

    public static List<Indicateur> mergeList(List<Indicateur> listIndicateurDomain, List<Indicateur> listIndicateurObj) {
        return listIndicateurObj
                .stream()
                .filter(listIndicateurDomain::contains)
                .collect(Collectors.toList());
    }

    public List<Indicateur> findAll(List<Long> ids) {
        return repository.findAll(ids);
    }

    public Indicateur updateIndicateur(final Long id, final IndicateurForm indicateurForm, final JwtUser user) {
        Indicateur bean = repository.findOne(id);
        bean.setTypeMesure(indicateurForm.getTypeMesure());
        bean.setNom(indicateurForm.getNomLong());
        bean.setDescription(indicateurForm.getDefinition());
        bean.setMethodeCalcule(indicateurForm.getMethodeCalcul());
        bean.setSourceDonnees(indicateurForm.getSourceDonnee());
        bean.setPosteCalcule(indicateurForm.getPosteCalcul().stream().collect(Collectors.joining(";")));
        bean.setUnite(indicateurForm.getUnite().stream().collect(Collectors.joining(";")));
        bean.setNomCourt(indicateurForm.getNomCourt());
        bean.setEchelle(indicateurForm.getEchelle());
        bean.setNature(indicateurForm.getNature());
        bean.setOrigine(indicateurForm.getOrigine().stream().collect(Collectors.joining(";")));
        bean.setEtatValidation(indicateurForm.getEtatValidation());
        bean.setEtatBibliotheque(indicateurForm.getStatutBibliotheque());
        assoIndicateurDomaineService.updateSeveral(bean, indicateurForm.getDomaines());
        assoIndicateurObjectifService.updateSeveral(bean, indicateurForm.getObjectifs());
        return save(bean, user);
    }

    public Long countNonValide() {
        return repository.count(hasEtatValidation(ETAT_VALIDATION.NON_VALIDE.getCode()));
    }

    public IndicateurBean findOneVisibleIndicateur(final Long idIndicateur) {
        try {
            return toIndicateurBean(repository.findOne(where(hasId(idIndicateur)).and(isValide())));
        } catch (Exception e) {
            return null;
        }
    }
}
