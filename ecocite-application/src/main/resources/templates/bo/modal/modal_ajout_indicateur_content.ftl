<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0..-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.-->
<#--You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.-->

<#-- @ftlvariable name="lectureSeule" type="java.lang.Boolean" -->
<#-- @ftlvariable name="typeModal" type="java.lang.Integer" -->
<#-- @ftlvariable name="indicateur" type="com.efficacity.explorateurecocites.beans.biz.IndicateurBean" -->
<#-- @ftlvariable name="natures" type="java.util.ArrayList<com.efficacity.explorateurecocites.utils.enumeration.NATURE_INDICATEUR>" -->
<#-- @ftlvariable name="echelles" type="java.util.ArrayList<com.efficacity.explorateurecocites.utils.enumeration.ECHELLE_INDICATEUR>" -->
<#-- @ftlvariable name="etatsIndicateur" type="java.util.ArrayList<com.efficacity.explorateurecocites.utils.enumeration.ETAT_VALIDATION>" -->
<#-- @ftlvariable name="etatsBibliotheque" type="java.util.ArrayList<com.efficacity.explorateurecocites.utils.enumeration.ETAT_BIBLIOTHEQUE>" -->
<#-- @ftlvariable name="typesMesure" type="java.util.ArrayList<com.efficacity.explorateurecocites.utils.enumeration.TYPE_MESURE>" -->
<#-- @ftlvariable name="user" type="isotope.modules.user.model.User" -->
<#-- @ftlvariable name="objectifs" type="java.util.List<com.efficacity.explorateurecocites.beans.model.EtiquetteFinalite>" -->
<#-- @ftlvariable name="domaines" type="java.util.List<com.efficacity.explorateurecocites.beans.model.EtiquetteAxe>" -->
<#-- @ftlvariable name="domainesJson" type="java.lang.String" -->
<#-- @ftlvariable name="selectedDomainsJson" type="java.lang.String" -->
<#-- @ftlvariable name="objectifsJson" type="java.lang.String" -->
<#-- @ftlvariable name="selectedObjectifsJson" type="java.lang.String" -->
<#-- @ftlvariable name="originsChoices" type="com.efficacity.explorateurecocites.utils.enumeration.ORIGINE_INDICATEUR[]" -->
<#-- @ftlvariable name="enums" type="freemarker.template.TemplateHashModel" -->


<#if indicateur??>
    <#assign nomLong = indicateur.nom! />
    <#assign definition = indicateur.description! />
    <#assign methodeCalcul = indicateur.methodeCalcule! />
    <#assign sourceDonnees = indicateur.sourceDonnees! />
    <#assign posteCalcul = indicateur.posteCalcule! />
    <#assign unite = indicateur.unite! />
    <#assign nomCourt = indicateur.nomCourt! />
    <#assign echelle = indicateur.echelle! />
    <#assign nature = indicateur.nature! />
    <#assign etatValidation = indicateur.etatValidation! />
    <#assign contactCreateur = indicateur.userCreation!"" />
    <#assign etatBibliotheque = indicateur.etatBibliotheque! />
    <#assign origine = indicateur.origineEnumLibelle! />
    <#assign typeMesure = indicateur.typeMesure />
    <#if indicateur.id??>
        <#assign idIndicateur = indicateur.id?c />
    <#else>
        <#assign idIndicateur = "" />
    </#if>
<#else>
    <#assign nomLong = "" />
    <#assign definition = "" />
    <#assign methodeCalcul = "" />
    <#assign sourceDonnees = "" />
    <#assign posteCalcul = "" />
    <#assign unite = "" />
    <#assign nomCourt = "" />
    <#assign echelle = "" />
    <#assign nature = "" />
    <#assign etatValidation = "" />
    <#assign contactCreateur = "" />
    <#assign etatBibliotheque = "" />
    <#assign origine = "" />
    <#assign typeMesure = "" />
    <#assign idIndicateur = "" />
</#if>
<#if !domaines??>
    <#assign domaines = [] />
</#if>
<#if !objectifs??>
    <#assign objectifs = [] />
</#if>

<#assign NATURE_INDICATEUR_REALISATIONS = "realisation">
<#assign NATURE_INDICATEUR_NAME = "com.efficacity.explorateurecocites.utils.enumeration.NATURE_INDICATEUR" />
<#if enums?? && enums[NATURE_INDICATEUR_NAME]??>
    <#if enums[NATURE_INDICATEUR_NAME].REALISATIONS?? && enums[NATURE_INDICATEUR_NAME].REALISATIONS.getCode()??>
        <#assign NATURE_INDICATEUR_REALISATIONS = enums[NATURE_INDICATEUR_NAME].REALISATIONS.getCode()>
    </#if>
</#if>

<#assign ECHELLE_INDICATEUR_SPECIFIQUE = "specifique">
<#assign ECHELLE_INDICATEUR_NAME = "com.efficacity.explorateurecocites.utils.enumeration.ECHELLE_INDICATEUR" />
<#if enums?? && enums[ECHELLE_INDICATEUR_NAME]??>
    <#if enums[ECHELLE_INDICATEUR_NAME].SPECIFIQUE?? && enums[ECHELLE_INDICATEUR_NAME].SPECIFIQUE.getCode()??>
        <#assign ECHELLE_INDICATEUR_SPECIFIQUE = enums[ECHELLE_INDICATEUR_NAME].SPECIFIQUE.getCode()>
    </#if>
</#if>

<#if nomLong?? && definition?? && methodeCalcul??
        && sourceDonnees?? && posteCalcul?? && unite??
        && nomCourt?? && echelle?? && nature?? && etatValidation??
        && contactCreateur?? && etatBibliotheque?? && origine??
        && typeMesure?? && echelles?? && natures?? && etatsIndicateur??
        && typeMesure?? && etatsBibliotheque?? && domaines??
        && objectifs??
>
    <#assign CREATION = 1 />
    <#assign CREATION_ACTION = 2 />
    <#assign CREATION_ECOCITE = 3 />
    <#assign EDITION = 4 />
    <#assign AFFICHAGE = 0 />
    <#if !typeModal??>
        <#assign typeModal = CREATION_ACTION>
    </#if>
    <#switch typeModal>
        <#case CREATION>
        <#-- La modal est en mode creation -->
            <#assign titreModal = "Créer un indicateur" />

            <#assign lectureSeule = false />
            <#assign echelleLectureSeule = false />
            <#assign etatValidation = "non_valide" />
            <#assign etatBibliotheque = "non_visible" />
            <#assign contactCreateur = user.email />
            <#break>
        <#case CREATION_ACTION>
        <#-- La modal est en mode creation pour une action -->
            <#assign titreModal = "Créer un indicateur (Action)" />

            <#assign lectureSeule = false />
            <#assign echelleLectureSeule = true />
            <#assign echelle = "specifique" />
            <#assign etatValidation = "non_valide" />
            <#assign etatBibliotheque = "non_visible" />
            <#assign contactCreateur = user.email />
            <#break>
        <#case CREATION_ECOCITE>
        <#-- La modal est en mode creation pour une ecocite -->
            <#assign titreModal = "Créer un indicateur (ÉcoCité)" />

            <#assign lectureSeule = false />
            <#assign echelleLectureSeule = true />
            <#assign echelle = "territoriale" />
            <#assign etatValidation = "non_valide" />
            <#assign etatBibliotheque = "non_visible" />
            <#assign contactCreateur = user.email />
            <#break>
        <#case EDITION>
        <#-- La modal est en mode edition pour un indicateur précis -->
            <#assign titreModal = nomLong />

            <#assign lectureSeule = false />
            <#assign echelleLectureSeule = false />
            <#break>
        <#case AFFICHAGE>
        <#default>
        <#-- La modal est en mode affichage -->
            <#assign titreModal = nomLong />

            <#assign lectureSeule = true />
            <#break>
    </#switch>
    <#-- Cette modal doit être placé dans une div telle que la suivante -->
    <#-- <div class="modal fade" id="modalAjoutIndicateur" tabindex="-1" role="dialog" aria-labelledby="modalAjoutIndicateurTitle" aria-hidden="true"> -->
    <div class="modal-dialog" role="document" style="max-width: 80%;">
        <div class="modal-content">
            <div class="modal-header bg-grey-light">
                <h5 class="modal-title" style="margin-left: 15px" id="modalAjoutIndicateurTitle">${titreModal}</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="head d-flex mb-4 py-0 px-3 border-0">
                    <div class="row d-block w-100">
                        <ul class="nav nav-tabs col-lg-12 listOngletAction" role="tablist">
                            <li class="nav-item">
                                <a class="nav-link active" href="#modalAjoutPresentationTab" role="tab" data-toggle="tab">Présentation</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#modalAjoutEtiquettesTab" role="tab" data-toggle="tab">Etiquettes</a>
                            </li>
                            <li class="nav-item"></li>
                            <li class="nav-item"></li>
                        </ul>
                    </div>
                </div>
                <!-- Tab panes -->
                <div class="tab-content container-fluid">
                    <div role="tabpanel" class="tab-pane fade in active show table-drag" id="modalAjoutPresentationTab">
                        <div class="row">
                            <div class="col-8">
                                <input type="hidden" name="id" value="${idIndicateur}"/>
                                <input type="hidden" name="type" value="${typeModal}"/>
                                <div class="form-group">
                                    <label for="nomLong-input"><strong>Nom Long *</strong> <i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Intitulé plus détaillé et opérationnel qui précise ce qui va être évalué (va-t-on calculer un rendement, des surfaces cumulées, un niveau de satisfaction...)"></i></label> <br/>
                                    <input id="nomLong-input" type="text" name="nomLong" class="form-control" <#if lectureSeule> disabled</#if> value="${nomLong}" required/>
                                    <p class="text-danger" style="display : none;"></p>
                                </div>
                                <div class="form-group">
                                    <label for="definition-input"><strong>Définition *</strong> <i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Description qui précise la finalité et l’utilité de l’indicateur (« l’indicateur vise à… , permet de… ») et qui définit si nécessaire les termes de l’indicateur"></i></label> <br/>
                                    <textarea name="definition" id="definition-input" class="form-control" rows="3" <#if lectureSeule> disabled</#if> required>${definition}</textarea>
                                    <p class="text-danger" style="display : none;"></p>
                                </div>
                                <div class="form-group">
                                    <label for="methodeCalcul-input"><strong>Méthode de calcul</strong> <i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Pour les indicateurs quantitatifs : la méthodologie de calcul, les hypothèses et le cas échéant la formule ; Pour les indicateurs qualitatifs : la méthodologie d’évaluation et si pertinent une échelle d’évaluation."></i></label> <br/>
                                    <textarea id="methodeCalcul-input" name="methodeCalcul" class="form-control" rows="3" <#if lectureSeule> disabled</#if>>${methodeCalcul}</textarea>
                                    <p class="text-danger" style="display : none;"></p>
                                </div>
                                <div class="form-group">
                                    <label for="sourceDonnee-input"><strong>Source de données *</strong> <i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Identification des sources de données à mobiliser pour renseigner l’indicateur (base de données, rapport d’un gestionnaire de service, Permis de Construire, site internet, enquête auprès des habitants...)"></i></label> <br/>
                                    <textarea id="sourceDonnee-input" name="sourceDonnee" class="form-control" rows="3" <#if lectureSeule> disabled</#if> required>${sourceDonnees}</textarea>
                                    <p class="text-danger" style="display : none;"></p>
                                </div>
                                <div class="row">
                                    <div class="col-6">
                                        <div class="form-group">
                                            <label for="selectPosteCalcul"><strong>Poste de calcul</strong> <i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Détail des différents « postes » qui peuvent être pris en compte dans le calcul. Il s’agit d’autant d’éléments qui peuvent être évalués séparément les uns des autres, tout en renseignant un seul et même indicateur."></i></label> <br/>
                                            <select id="selectPosteCalcul" class="form-control select-tags" name="posteCalcul" multiple="multiple" style="width: 100%" <#if lectureSeule> disabled</#if>>
                                                <#assign postesCalculs = posteCalcul?split(";")/>
                                                <#if postesCalculs?? && postesCalculs?hasContent>
                                                    <#list postesCalculs as p>
                                                        <#if p?trim?hasContent>
                                                            <option selected>${p?trim}</option>
                                                        </#if>
                                                    </#list>
                                                </#if>
                                            </select>
                                            <p class="text-danger" style="display : none;"></p>
                                        </div>
                                    </div>
                                    <div class="col-6">
                                        <div class="form-group">
                                            <label for="selectUnite"><strong>Unite *</strong> <i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Détail des unités de l’indicateur (correspondants aux valeurs relatives ou absolues possibles)"></i></label><br/>
                                            <select id="selectUnite" class="form-control select-tags" required name="unite" multiple="multiple" style="width: 100%" <#if lectureSeule> disabled</#if>>
                                                <#assign unites = unite?split(";")/>
                                                <#if unites?? && unites?hasContent>
                                                    <#list unites as u>
                                                        <#if u?trim?hasContent>
                                                            <option selected>${u?trim}</option>
                                                        </#if>
                                                    </#list>
                                                </#if>
                                            </select>
                                            <p class="text-danger" style="display : none;"></p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-4">
                                <div class="form-group">
                                    <label for="nomCourt-input"><strong>Nom court *</strong> <i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Intitulé court indiquant le sujet abordé en quelques mots clés"></i></label> <br/>
                                    <input type="text" id="nomCourt-input" name="nomCourt" class="form-control" <#if lectureSeule> disabled</#if> value="${nomCourt}" required/>
                                    <p class="text-danger" style="display : none;"></p>
                                </div>
                                <div class="form-group">
                                    <label for="echelle-input"><strong>Echelle *</strong> <i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Indicateur spécifique (à l'échelle de l'action ) ou territorial (à l'échelle du territoire)"></i></label> <br/>
                                    <select id="echelle-input" name="echelle" class="form-control" <#if lectureSeule || echelleLectureSeule>disabled</#if> required>
                                        <option value=""></option>
                                        <#list echelles as e>
                                            <option value="${e.code}" <#if e.code == echelle>selected</#if>>${e.libelle}</option>
                                        </#list>
                                    </select>
                                    <p class="text-danger" style="display : none;"></p>
                                </div>
                                <div class="form-group">

                                    <label for="nature-input"><strong>Nature *</strong> <i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Indicateur de réalisation (suivi de l'avancement), de résultat (suivi des effets immédiats du projet sur les destinataires directs) ou d'impact (évaluation des retombées du projet au-delà de ses effets immédiats)"></i></label> <br/>
                                    <select id="nature-input" name="nature" class="form-control" <#if lectureSeule> disabled</#if> required>
                                        <option value=""></option>
                                        <#list natures as n>
                                            <option value="${n.code}" <#if n.code == nature>selected</#if>>${n.libelle}</option>
                                        </#list>
                                    </select>
                                    <p class="text-danger" style="display : none;"></p>
                                </div>
                                <div class="form-group">
                                    <label for="selectOrigin"><strong>Origine *</strong> <i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Détail de l'origine de l’indicateur dans un souci de traçabilité (RFSC, CITYkeys, AEU2, PCAET, proposé par une ÉcoCité...)"></i></label> <br/>
                                    <select id="selectOrigin" class="form-control select-tags" <#if lectureSeule> disabled</#if> name="origine" multiple="multiple" style="width: 100%" required>
                                        <#if indicateur??>
                                            <#assign origines = indicateur.origineListEnumLibelle/>
                                            <#assign origineslist = origines?split(";")/>
                                            <#if origineslist?? && origineslist?hasContent>
                                                <#list origineslist as origine>
                                                    <#if origine!="">
                                                        <option value="${origine!}" selected>${origine!}</option>
                                                    </#if>
                                                </#list>
                                            </#if>
                                        </#if>
                                        <#if origineEcociteAuto??>
                                            <#if origineEcociteAuto!="">
                                                    <option value="${origineEcociteAuto!}" selected>${origineEcociteAuto!}</option>
                                            </#if>
                                        </#if>
                                        <#if originsChoices??>
                                            <#list originsChoices as originChoice>
                                                <#if originChoice?? && originChoice.code?? && originChoice.libelle??>
                                                <option value="${originChoice.code}">${originChoice.libelle}</option>
                                                </#if>
                                            </#list>
                                        </#if>
                                    </select>
                                    <p class="text-danger" style="display : none;"></p>
                                </div>
                                <div class="form-group">
                                    <label for="typeMesure-input"><strong>Type de mesure *</strong> <i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Nombre, texte ou liste de valeur pré-remplies"></i></label> <br/>
                                    <select id="typeMesure-input" name="typeMesure" class="form-control" <#if lectureSeule> disabled</#if> required>
                                        <option value=""></option>
                                        <#list typesMesure as t>
                                            <option value="${t.code}" <#if t.code == typeMesure>selected</#if>>${t.libelle}</option>
                                        </#list>
                                    </select>
                                    <p class="text-danger" style="display : none;"></p>
                                </div>
                                <div class="form-group">
                                    <label for="etatValidation-input"><strong>Etat de validation *</strong></label> <br/>
                                    <select id="etatValidation-input" name="etatValidation" class="form-control" <#if lectureSeule || !userIsAdmin> disabled</#if> required>
                                        <option value=""></option>
                                        <#list etatsIndicateur as e>
                                            <option value="${e.code}" <#if e.code == etatValidation>selected</#if>>${e.libelle}</option>
                                        </#list>
                                    </select>
                                    <p class="text-danger" style="display : none;"></p>
                                </div>
                                <div class="form-group">
                                    <label for="statutBibliotheque-input"><strong>Etat de publication *</strong></label> <br/>
                                    <select id="statutBibliotheque-input" name="statutBibliotheque" class="form-control" <#if lectureSeule || !userIsAdmin> disabled</#if> required>
                                        <option value=""></option>
                                        <#list etatsBibliotheque as e>
                                            <option value="${e.code}" <#if e.code == etatBibliotheque>selected</#if>>${e.libelle}</option>
                                        </#list>
                                    </select>
                                    <p class="text-danger" style="display : none;"></p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div role="tabpanel" class="tab-pane fade in show table-drag" id="modalAjoutEtiquettesTab">
                        <div class="form-group">
                            <label for="select2DA"><strong>Domaines d'action *</strong></label> <br/>
                            <select id="select2DA" class="js-example-basic-multiple" name="domaines" multiple="multiple" <#if lectureSeule> disabled</#if> style="width: 80%;" required></select>
                            <button id="addAllDA" class="btn btn-primary">Tout ajouter</button>
                            <p class="text-danger" style="display : none;"></p>
                        </div>
                        <div class="form-group">
                            <label for="select2O"><strong>Objectifs de la ville durable *</strong></label> <br/>
                            <select id="select2O" class="js-example-basic-multiple" name="objectifs" multiple="multiple" <#if lectureSeule> disabled</#if> style="width: 80%;" required></select>
                            <button id="addAllO" class="btn btn-primary">Tout ajouter</button>
                            <p class="text-danger" style="display : none;"></p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <div class="row" style="width: 100%!important;">
                    <div class="col-6">
                        <p>Les champs mentionnés avec un astérisque (*) sont obligatoires</p>
                    </div>
                    <#if typeModal == EDITION >
                        <div class="col-3">
                            <button type="button" class="btn btn-block btn-danger" data-toggle="modal" data-target="#confirmationSuppression" >Supprimer</button>
                        </div>
                        <div class="col-3">
                            <#if indicateur?? && indicateur.id??>
                                <button type="button" class="btn btn-block btn-success" onclick="updateObject('${indicateur.id?c}')">Sauvegarder</button>
                            <#else>
                                <button type="button" class="btn btn-block btn-success">Sauvegarder</button>
                            </#if>
                        </div>
                    <#elseIf typeModal == AFFICHAGE >
                        <div class="col-3">
                        </div>
                        <div class="col-3">
                            <button type="button" class="btn btn-block btn-success" data-dismiss="modal">Ok</button>
                        </div>
                    <#else>
                        <div class="col-3">
                            <button type="button" class="btn btn-block btn-outline-secondary" data-dismiss="modal">Annuler</button>
                        </div>
                        <div class="col-3">
                            <#if typeModal == CREATION_ACTION>
                                <button type="button" class="btn btn-block btn-success" onclick="createObjet('/bo/indicateurs/ajoutIndicateurs/action')">Enregistrer</button>
                            <#else>
                                <#if typeModal == CREATION_ECOCITE>
                                    <button type="button" class="btn btn-block btn-success" onclick="createObjet('/bo/indicateurs/ajoutIndicateurs/ecocite')">Enregistrer</button>
                                <#else>
                                    <button type="button" class="btn btn-block btn-success" onclick="createObjet('/bo/indicateurs/ajoutIndicateurs/any')">Enregistrer</button>
                                </#if>
                            </#if>
                        </div>
                    </#if>
                </div>
            </div>
        </div>
    </div>
    <#-- </div> -->
    <script>
        $(document).ready(function() {
            modalEditionObjetReadyIndicateur(${(domainesJson!'undefined')?noEsc}, ${(selectedDomainsJson!'undefined')?noEsc},
                ${(objectifsJson!'undefined')?noEsc}, ${(selectedObjectifsJson!'undefined')?noEsc})
        });

        function arrayFieldValidDomain(value, elt) {
        	if ($("#echelle-input").val() !== "${ECHELLE_INDICATEUR_SPECIFIQUE}") {
                if ($("#nature-input").val() !== "${NATURE_INDICATEUR_REALISATIONS}") {
                    return true;
                }
            }
            return !(elt.prop("required") && (!value || value.length < 1))
        }

        function arrayFieldValidObjectifs(value, elt) {
            if ($("#nature-input").val() !== "${NATURE_INDICATEUR_REALISATIONS}") {
                return !(elt.prop("required") && (!value || value.length < 1))
            }
            return true;
        }
    </script>
</#if>
