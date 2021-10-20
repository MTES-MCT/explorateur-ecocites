<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0..-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.-->
<#--You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.-->

<#-- @ftlvariable name="action" type="com.efficacity.explorateurecocites.beans.biz.ActionBean" -->
<#-- @ftlvariable name="perimetre" type="com.efficacity.explorateurecocites.beans.biz.FileUploadBean" -->
<#-- @ftlvariable name="imagesPrincipales" type="java.util.List<com.efficacity.explorateurecocites.beans.model.Media>" -->
<#-- @ftlvariable name="imagesSecondaires" type="java.util.List<com.efficacity.explorateurecocites.beans.model.Media>" -->
<#-- @ftlvariable name="documents" type="java.util.List<com.efficacity.explorateurecocites.beans.biz.FileUploadBean>" -->
<#-- @ftlvariable name="ecocites" type="java.util.Map<java.lang.String, java.lang.String>" -->
<#-- @ftlvariable name="etatsAvancement" type="com.efficacity.explorateurecocites.utils.enumeration.ETAT_AVANCEMENT[]" -->
<#-- @ftlvariable name="tranches" type="com.efficacity.explorateurecocites.utils.enumeration.TRANCHE_EXECUTION[]" -->
<#-- @ftlvariable name="echellesAction" type="com.efficacity.explorateurecocites.utils.enumeration.ECHELLE_ACTION[]" -->
<#-- @ftlvariable name="etatsPublication" type="com.efficacity.explorateurecocites.utils.enumeration.ETAT_PUBLICATION[]" -->
<#-- @ftlvariable name="typesFinancement" type="com.efficacity.explorateurecocites.utils.enumeration.TYPE_FINANCEMENT[]" -->
<#-- @ftlvariable name="axePrin" type="com.efficacity.explorateurecocites.beans.model.Axe" -->
<#-- @ftlvariable name="moUsed" type="java.util.List<com.efficacity.explorateurecocites.utils.enumeration.MAITRISE_OUVRAGE>" -->
<#-- @ftlvariable name="moEnumUsed" type="java.util.List<com.efficacity.explorateurecocites.utils.enumeration.MAITRISE_OUVRAGE>" -->
<#-- @ftlvariable name="moEnumNotUsed" type="java.util.List<com.efficacity.explorateurecocites.utils.enumeration.MAITRISE_OUVRAGE>" -->
<#-- @ftlvariable name="contactPrincipal" type="java.util.List<com.efficacity.explorateurecocites.beans.model.Contact>" -->
<#-- @ftlvariable name="contactPrincipalNotUsed" type="java.util.List<com.efficacity.explorateurecocites.beans.model.Contact>" -->
<#-- @ftlvariable name="contactSecondaire" type="java.util.List<com.efficacity.explorateurecocites.beans.model.Contact>" -->
<#-- @ftlvariable name="contactSecondaireNotUsed" type="java.util.List<com.efficacity.explorateurecocites.beans.model.Contact>" -->
<#-- @ftlvariable name="axes" type="java.util.List<com.efficacity.explorateurecocites.beans.model.Axe>" -->
<#-- @ftlvariable name="ajarisStatus" type="java.lang.String" -->
<#-- @ftlvariable name="ajarisLoginUrl" type="java.lang.String" -->

<#if dateModification?? && userNameModification?? && userProfilModification?? && dateModification?hasContent>
    <span style="font-size: 0.9rem; color: #83b93a; padding-left: 2rem;">
        Dernière modification le ${dateModification!"Date inconnue"} par ${userNameModification!"Inconnu"} (${userProfilModification!"Inconnu"}).
    </span>
<#else>
    <span class="c-bleu-e ajaris-status" style="padding-left: 2rem;">&nbsp;</span>
</#if>
<#if ajarisStatus == "FINISHED">
    <span data-toggle="tooltip" data-placement="top" title="Tous les images associées ont été synchronisées avec les derniers changements" class="pull-right ajaris-status c-vert-e">
        <i class="fa fa-check"> Synchonisé</i>
    </span>
<#elseIf ajarisStatus == "PENDING">
    <span data-toggle="tooltip" data-placement="top" title="Les derniers changements sont en cours de synchronisation avec les images associés" class="pull-right ajaris-status c-bleu-e">
        <i class="fa fa-clock-o"></i> En cours
    </span>
<#elseIf ajarisStatus == "ERROR">
    <span data-toggle="tooltip" data-placement="top" title="La dernière synchronisation à échoué, elle sera retenté dans quelques instants" class="pull-right ajaris-status c-bleu-e">
        <i class="fa fa-times"></i> En cours
    </span>
</#if>

<div class="head ft-s-20 ft-700">

</div>
<div class="body flex-column flex-lg-row">
    <div class="container-fluid no-border">
        <div class="row ">
            <div class="col-lg-6">
                <div class="form-group">
                    <label for="nomPublic"><strong>Nom public *</strong></label> <br/>
                    <input <#if canEdit?? && !canEdit >disabled </#if> type="text" id="nomPublic" name="nomPublic" value="${action.nomPublic!}" class="inputSaveAuto form-control" data-objectid="${action.id!?c}" data-objectclass="Action"/>
                    <p class="text-danger" style="display : none;"></p>
                </div>
            </div>
            <div class="col-lg-3">
                <div class="form-group">
                    <label for="idEcocite"><strong>ÉcoCité *</strong></label> <br/>
                    <select id="idEcocite" name="idEcocite" class="inputSaveAuto form-control" data-objectid="${action.id!?c}" data-objectclass="Action" <#if !(isAdminAccompaModif??) || (canEdit?? && !canEdit) >disabled </#if>>
                        <option value=""></option>
                        <#list ecocites?keys as key>
                            <option value="${key}" <#if action.idEcocite?? && key==action.idEcocite!?c > selected</#if>>${ecocites[key]}</option>
                        </#list>
                    </select>
                    <p class="text-danger" style="display : none;"></p>
                </div>
            </div>
            <div class="col-lg-3">
                <div class="form-group">
                    <label for="numeroAction"><strong>Numéro d'action *</strong></label> <br/>
                    <input type="text" id="numeroAction" name="numeroAction" value="<#if !action.numeroAction??>${action.numeroAction!}<#else>${action.numeroAction!}</#if>"
                           class="inputSaveAuto form-control" data-objectid="${action.id!?c}" data-objectclass="Action" <#if !(isAdminAccompaModif??) || (canEdit?? && !canEdit) >disabled </#if>/>
                    <p class="text-danger" style="display : none;"></p>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-6">
                <div class="form-group">
                    <label for="affaire"><strong>Numéro des affaires associées</strong></label> <br/>
                    <input type="text" id="affaire" name="affaire" value="${affaires!""}" class="form-control" disabled/>
                    <p class="text-danger" style="display : none;"></p>
                </div>
            </div>
            <div class="col-lg-3">
                <div class="form-group">
                    <label for="typeFinancement"><strong>Type de financement *</strong></label> <br/>
                    <select id="typeFinancement" name="typeFinancement" class="form-control" data-objectid="${action.id!?c}" data-objectclass="Action" <#if !(isAdminAccompaModif??) || (canEdit?? && !canEdit)>disabled</#if> onchange="onTypeFinancementChange()" onfocus="onTypeFinancementFocus()">
                        <#if typesFinancement??>
                            <#list typesFinancement as e>
                                <option value="${e.code}" <#if e.code == action.typeFinancement!> selected</#if>>${e.libelle}</option>
                            </#list>
                        </#if>
                    </select>
                    <input id="typeFinancementOldValue" type="hidden">
                    <p class="text-danger" style="display : none;"></p>
                </div>
            </div>
            <div class="col-lg-3">
                <div class="form-group">
                    <label for="axePrincipale"><strong>Axe principal </strong><i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="L'axe principal est déterminé par le domaine d'action principal de l'action, une fois validé"></i></label><br/>
                    <select type="text" id="idAxe" name="idAxe" data-objectid="${action.id!?c}" data-objectclass="Action"
                           <#if !(isAdminAccompaModif??) || (canEdit?? && !canEdit) >disabled </#if> class="inputSaveAuto form-control">
                        <option value=""></option>
                        <#if axes?? && axes?hasContent>
                            <#list axes as axe>
                                <option value="${axe.id?c}" <#if axePrin?? && axePrin == axe.id>selected</#if>>${axe.libelle}</option>
                            </#list>
                        </#if>
                    </select>
                    <span></span>
                    <p class="text-danger" style="display : none;"></p>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-3">
                <div class="form-group">
                    <label for="trancheExecution"><strong>Tranche d'exécution *</strong></label> <br/>
                    <select <#if (canEdit?? && !canEdit)|| (isPorteurAction??) >disabled </#if> id="trancheExecution" name="trancheExecution" class="inputSaveAuto form-control" data-objectid="${action.id!?c}" data-objectclass="Action">
                        <option value=""></option>
                    <#if tranches??>
                        <#list tranches as e>
                            <option value="${e.code}" <#if e.code == action.trancheExecution!> selected</#if>>${e.libelle}</option>
                        </#list>
                    </#if>
                    </select>
                    <p class="text-danger" style="display : none;"></p>
                </div>
            </div>
            <div class="col-lg-3">
                <div class="form-group">
                    <label for="etatAvancement"><strong>Etat d'avancement *</strong></label> <br/>
                    <select <#if canEdit?? && !canEdit >disabled </#if> name="etatAvancement" id="etatAvancement" class="inputSaveAuto form-control" data-objectid="${action.id!?c}" data-objectclass="Action">
                        <option value=""></option>
                    <#if etatsAvancement??>
                        <#list etatsAvancement as e>
                            <option value="${e.code}" <#if e.code == action.etatAvancement!> selected</#if>>${e.libelle}</option>
                        </#list>
                    </#if>
                    </select>
                    <p class="text-danger" style="display : none;"></p>
                </div>
            </div>
            <div class="col-lg-3">
                <div class="form-group">
                    <label for="dateDebut"><strong>Date de début</strong> <i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="La date de début de l’action correspond à la date de contractualisation avec la CDC"></i></label><br/>
                    <input <#if canEdit?? && !canEdit >disabled </#if> placeholder="dd/mm/aaaa" type="text" name="dateDebut" value="<#if action.dateDebut??>${action.dateDebut[8..9]+"/"+action.dateDebut[5..6]+"/"+action.dateDebut[0..3]}</#if>"
                           class="form-control datepicker inputSaveAuto" data-objectid="${action.id!?c}" data-objectclass="Action"/>
                    <p class="text-danger" style="display : none;"></p>
                </div>
            </div>
            <div class="col-lg-3">
                <div class="form-group">
                    <label for="dateFin"><strong>Date de fin</strong> <i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="La date de fin correspond à la date de livraison, de mise en service ou d’achèvement des travaux en fonction du type d’action."></i></label><br/>
                    <input <#if canEdit?? && !canEdit >disabled </#if> placeholder="dd/mm/aaaa" type="text" name="dateFin" value="<#if action.dateFin??>${action.dateFin[8..9]+"/"+action.dateFin[5..6]+"/"+action.dateFin[0..3]}</#if>"
                           class="form-control datepicker inputSaveAuto" data-objectid="${action.id!?c}" data-objectclass="Action"/>
                    <p class="text-danger" style="display : none;"></p>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-6">
                <div class="form-group">
                    <label for="description"><strong>Description *</strong></label> <br/>
                    <textarea <#if canEdit?? && !canEdit >disabled </#if> id="description" name="description" data-objectid="${action.id!?c}" data-objectclass="Action"></textarea>
                    <p class="text-danger" style="display : none;"></p>
                </div>
                <script>
                    var e = $("#presentation").find("textarea[name=description]").get(0);
                    var instance = CKEDITOR.replace(e);
                    instance.setData("${(action.descriptionNoLineBreak!"")?noEsc}");
                    instance.on('blur', function() {
                        saveAttribut(e, this.getData());
                    });
                </script>
            </div>
            <div class="col-lg-6">
                <div class="form-row">
                    <div class="form-group col-lg-6">
                        <label for="photoPrin"><strong>Accès à la médiathèque ÉcoCité</strong></label>
                        <#if ajarisLoginUrl?? && ajarisLoginUrl?hasContent >
                            <a
                               href="${ajarisLoginUrl}"
                               target="_blank" rel="noopener noreferrer"
                               class="btn btn-outline-primary btn-block" style="width:96.4%"
                            >
                                Accédez
                            </a>
                        <#else>
                            <button class="btn btn-outline-primary btn-block disabled" style="width:96.4%" disabled>
                                Accédez
                            </button>
                        </#if>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-lg-6">
                        <#assign hasImagePrincipale = imagesPrincipales?? && imagesPrincipales?hasContent>
                        <label for="photoPrin"><strong>Photo principale (1 max)</strong></label>
                        <button id="button-add-img-principale" type="button" name="photoPrin"
                                class="btn btn-outline-primary btn-block" style="width:96.4%"
                                onclick="getModalAddMedia('#modalAjoutDocument', 'action', '${action.id!?c}', 1)"
                                <#if hasImagePrincipale || (canEdit?? && !canEdit)>disabled</#if>>
                            Choisissez une image
                        </button>
                        <#if hasImagePrincipale>
                            <p style="margin: 10px 10px 0;">
                                <#list imagesPrincipales as img>
                                    <#if img?? && img.title?? && img.id?? && img.title?hasContent >
                                        <div class="row">
                                            <div class="col-12">
                                                <#if canEdit?? && canEdit>
                                                    <a class="m-l-10 btn-wrap-text" href="#" onclick="getModalEditMedia('#modalAjoutDocument', 'action', '${action.id!?c}','${img.id?c}')">
                                                        <#if img.numero??>${img.numero?c} - </#if>${img.title}
                                                    </a>
                                                </#if>
                                            </div>
                                        </div>
                                    </#if>
                                </#list>
                            </p>
                        </#if>
                    </div>
                    <div class="form-group col-lg-6">
                        <#assign hasImageSecondaire = imagesSecondaires?? && imagesSecondaires?hasContent>
                        <#assign hasMaxImageSecondaire = imagesSecondaires?? && imagesSecondaires?hasContent && 5 <= imagesSecondaires?size>
                        <label style="width:96.4%; margin-left: 10px" for="photoSecond"><strong>Photo(s) secondaires(s) (5 max)</strong></label>
                        <button id="button-create-indic" type="button" name="photoSecond"
                                class="btn btn-outline-primary btn-block " style="width:96.4%; margin-left: 10px"
                                onclick="getModalAddMedia('#modalAjoutDocument', 'action', '${action.id!?c}', 2)"
                                <#if hasMaxImageSecondaire || (canEdit?? && !canEdit)>disabled</#if>>
                            Choisissez une image
                        </button>
                        <#if hasImageSecondaire>
                            <p style="margin: 10px 10px 0;">
                                <#list imagesSecondaires as img>
                                    <#if img?? && img.title?? && img.id?? && img.title?hasContent >
                                        <div class="row">
                                            <div class="col-12">
                                                <#if canEdit?? && canEdit>
                                                    <a class="m-l-10 btn-wrap-text" href="#" onclick="getModalEditMedia('#modalAjoutDocument', 'action', '${action.id!?c}','${img.id?c}')">
                                                        <#if img.numero??>${img.numero?c} - </#if>${img.title}
                                                    </a>
                                                </#if>
                                            </div>
                                        </div>
                                    </#if>
                                </#list>
                            </p>
                        </#if>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-lg-6">
                        <#assign maxCapacity = documents?? && documents?hasContent && 3 <= documents?size>
                        <label for="photoSecond"><strong>Documents (3 max)</strong><i class="fa fa-info-circle m-l-5" data-toggle="tooltip" data-placement="top" title="Ces documents seront visibles sur le site public. Vous pouvez ajouter par exemple un dossier de presse, le dossier de candidature s'il n'est pas confidentiel, etc."></i></label>
                        <button id="button-create-indic" type="button" name="photoSecond"
                                class="btn btn-outline-primary btn-block" style="width:96.4%"
                                onclick="getModalAddFile('#modalAjoutDocument', 0, '${action.id!?c}')"
                                <#if maxCapacity || (canEdit?? && !canEdit)>disabled</#if>>
                            Choisissez un fichier
                        </button>
                        <#if documents?? && documents?hasContent>
                            <#list documents as doc>
                                <#if doc?? && doc.originalName?hasContent && doc.title?hasContent && doc.url?? && doc.id??>
                                    <p style="margin: 10px 10px 0;">
                                        <div class="row">
                                            <div class="col-8">
                                                <a class="m-l-10 btn-wrap-text" download="${doc.originalName}" href="${doc.url}">${doc.title}</a>
                                            </div>
                                            <div class="col-4 col-h-center">
                                                <#if canEdit?? && canEdit >
                                                    <i class="fa fa-search " onclick="getModalEditFile('#modalAjoutDocument', 0, '${action.id!?c}','${doc.id?c}')"></i>
                                                    <i class="fa fa-remove" onclick="deleteDocument('${doc.id?c}', '${action.id?c}')"></i>
                                                </#if>
                                            </div>
                                        </div>
                                    </p>
                                </#if>
                            </#list>
                        </#if>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-lg-6">
                        <div class="row">
                            <div class="col-lg-10">
                                <label for="contactPrin">
                                    <strong>Contact principal (1 max)</strong>
                                </label>
                            </div>
                            <#if canEdit?? && canEdit>
                            <div class="col-lg-2 pull-right">
                                <#if contactPrincipal??>
                                <div class="pull-right">
                                    <i id="addContact1" class="fa fa-plus-circle cursorPointer c-bleu-e" <#if contactPrincipal?size<1><#else> style="display: none" </#if> <#if modalContactUrl??>onclick="ajoutContactAction(-1, '${modalContactUrl!}',event)"</#if>></i>
                                </div>
                                </#if>
                            </div>
                            </#if>
                        </div>
                        <select <#if canEdit?? && !canEdit>disabled </#if> id="contactPrin" class="form-control select-one" name="contactPrin" style="width: 100%" multiple>
                            <#if contactPrincipal?? && contactPrincipal?hasContent>
                                <#list contactPrincipal as contact>
                                    <#if contact?? && contact.id?? && contact.prenom?? && contact.nom??>
                                        <option value="${contact.id?c}" selected>${contact.nom} ${contact.prenom}</option>
                                    </#if>
                                </#list>
                            </#if>
                            <#if contactPrincipalNotUsed?? && contactPrincipalNotUsed?hasContent>
                                <#list contactPrincipalNotUsed as contact>
                                    <#if contact?? && contact.id?? && contact.prenom?? && contact.nom??>
                                        <option value="${contact.id?c}">${contact.nom} ${contact.prenom}</option>
                                    </#if>
                                </#list>
                            </#if>
                        </select>
                        <p class="text-danger" style="display : none;"></p>
                    </div>
                    <div class="form-group col-lg-6">
                        <div class="row">
                            <div class="col-lg-10">
                                <label for="contactSecond">
                                    <strong>Contact(s) secondaire(s) (2 max)</strong>
                                </label>
                            </div>
                            <#if canEdit?? && canEdit>
                            <div class="col-lg-2">
                                <#if contactSecondaire??>
                                <div class="pull-right">
                                    <i id="addContact2" class="fa fa-plus-circle cursorPointer c-bleu-e"  <#if contactSecondaire?size<2><#else> style="display: none" </#if> <#if modalContactUrl??>onclick="ajoutContactAction(-1, '${modalContactUrl!}',event)"</#if>></i>
                                 </div>
                                </#if>
                            </div>
                            </#if>
                        </div>
                        <select <#if canEdit?? && !canEdit>disabled </#if> id="contactSecond" class="form-control select-multiple" name="contactSecond" style="width: 100%" multiple>
                            <#if contactSecondaire?? && contactSecondaire?hasContent>
                                <#list contactSecondaire as contact>
                                    <#if contact?? && contact.id?? && contact.prenom?? && contact.nom??>
                                        <option value="${contact.id?c}" selected>${contact.nom} ${contact.prenom}</option>
                                    </#if>
                                </#list>
                            </#if>
                            <#if contactSecondaireNotUsed?? && contactSecondaireNotUsed?hasContent>
                                <#list contactSecondaireNotUsed as contact>
                                    <#if contact?? && contact.id?? && contact.prenom?? && contact.nom??>
                                        <option value="${contact.id?c}">${contact.nom} ${contact.prenom}</option>
                                    </#if>
                                </#list>
                            </#if>
                        </select>
                        <p class="text-danger" style="display : none;"></p>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-6">
                <div class="form-group">
                    <label for="lien"><strong>Lien site web</strong></label><br/>
                    <input <#if canEdit?? && !canEdit>disabled </#if> type="text" id="lien" name="lien" value="${action.lien!}" class="inputSaveAuto form-control" data-objectid="${action.id!?c}" data-objectclass="Action"/>
                    <p class="text-danger" style="display : none;"></p>
                </div>
            </div>
            <div class="col-lg-3">
                <div class="form-group">
                    <label for="echelle"><strong>Echelle</strong></label><br/>
                    <select <#if canEdit?? && !canEdit>disabled </#if> id="echelle" name="echelle" class="inputSaveAuto form-control" data-objectid="${action.id!?c}" data-objectclass="Action">
                        <option value=""></option>
                        <#if echellesAction??>
                            <#list echellesAction as e>
                                <option value="${e.code}" <#if e.code == action.echelle!> selected</#if>>${e.libelle}</option>
                            </#list>
                        </#if>
                    </select>
                    <p class="text-danger" style="display : none;"></p>
                </div>
            </div>
            <div class="col-lg-3">
                <div class="form-group">
                    <label for="etatPublication"><strong>Etat de publication *</strong></label><br/>
                    <select id="etatPublication" name="etatPublication" class="inputSaveAuto form-control" data-objectid="${action.id!?c}" data-objectclass="Action" <#if !(isAdminAccompaModif??) || (canEdit?? && !canEdit)>disabled </#if>>
                        <option value=""></option>
                    <#if etatsPublication??>
                        <#list etatsPublication as e>
                            <option value="${e.code}" <#if e.code == action.etatPublication!> selected</#if>>${e.libelle}</option>
                        </#list>
                    </#if>
                    </select>
                    <p class="text-danger" style="display : none;"></p>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-6">
                <div class="form-row">
                    <div class="form-group col-lg-12">
                        <label for="selectMO"><strong>Maîtrise d'ouvrage (5 max)</strong></label><br/>
                        <select <#if canEdit?? && !canEdit>disabled </#if> id="selectMO" class="form-control select-tags" name="maitriseOuvrage" style="width: 100%" data-objectid="${action.id!?c}" data-objectclass="Action" multiple>
                            <#list moEnumUsed as mo>
                                <#if mo?? && mo.code?? && mo.libelle??>
                                    <option value="${mo.code}" selected>${mo.libelle}</option>
                                </#if>
                            </#list>
                            <#list moEnumNotUsed as mo>
                                <#if mo?? && mo.code?? && mo.libelle??>
                                    <option value="${mo.code}">${mo.libelle}</option>
                                </#if>
                            </#list>
                            <#list moUsed as mo>
                                <#if mo?hasContent>
                                    <option selected>${mo}</option>
                                </#if>
                            </#list>
                        </select>
                        <p class="text-danger" style="display : none;"></p>
                    </div>
                </div>
            </div>
        </div>
        <br/>
        <br/>
        <label style="font-size: 20px; text-underline: "><strong><u>Localisation géographique</u></strong></label>
        <br/>
        <br/>
        <div class="row">
            <div class="col-lg-6">
                <div class="form-row">
                    <div class="form-group col-lg-12">
                        <#assign hasPerimetre = perimetre?? && perimetre.originalName?hasContent && perimetre.title?hasContent && perimetre.url?? && perimetre.id??>
                        <label for="perimetre"><strong>Périmètre (1 max)</strong></label>
                        <button id="button-add-img-principale" type="button" name="perimetre"
                                class="btn btn-outline-primary btn-block"
                                onclick="getModalAddFile('#modalAjoutDocument', 3, '${action.id!?c}')"
                                <#if hasPerimetre || (canEdit?? && !canEdit)>disabled</#if>>
                            Choisissez un fichier KML
                        </button>
                        <#if hasPerimetre>
                            <p style="margin: 10px 10px 0;">
                                <div class="row">
                                    <div class="col-10">
                                        <a class="m-l-10 btn-wrap-text" download="${perimetre.originalName}" href="${perimetre.url}">${perimetre.title}</a>
                                    </div>
                                    <div class="col-2 col-h-center">
                                        <#if canEdit?? && canEdit >
                                            <i class="fa fa-search" onclick="getModalEditFile('#modalAjoutDocument', 3, '${action.id!?c}','${perimetre.id?c}')"></i>
                                            <i class="fa fa-remove" onclick="deleteDocument('${perimetre.id?c}', '${action.id?c}')"></i>
                                        </#if>
                                    </div>
                                </div>
                            </p>
                        </#if>
                    </div>
                    <div class="form-group col-lg-6">
                        <label for="latitude"><strong>Latitude</strong> <i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Pour obtenir la latitude et la longitude de votre projet, vous pouvez aller sur le site : https://www.geoportail.gouv.fr/carte"></i></label><br/>
                        <input id="latitudeAction" <#if canEdit?? && !canEdit>disabled </#if> type="text" name="latitude" value="${action.latitude!""}"
                               class="inputSaveAuto form-control updateMap" style="width:96.4%"
                               data-objectid="${action.id!?c}" data-objectclass="Action"/>
                        <p class="text-danger" style="display : none;"></p>
                    </div>
                    <div class="form-group col-lg-6">
                        <label for="longitude" style="margin-left: 10px "><strong>Longitude</strong> <i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Pour obtenir la latitude et la longitude de votre projet, vous pouvez aller sur le site : https://www.geoportail.gouv.fr/carte"></i></label><br/>
                        <input id="longitudeAction" <#if canEdit?? && !canEdit>disabled </#if> type="text" name="longitude" value="${action.longitude!""}"
                               class="inputSaveAuto form-control updateMap" style="width:96.4%; margin-left: 10px "
                               data-objectid="${action.id!?c}" data-objectclass="Action"/>
                        <p class="text-danger" style="display : none; margin-left: 10px"></p>
                    </div>
                </div>
            </div>
            <div class="form-group col-lg-6">
                <div class="large-map loading">
                    <div id="mapPlaceHolder" class="map-placeholder"><p>La carte est en chargement...</p></div>
                    <div id="map" class="map-component"></div>
                </div>
            </div>
        </div>
        <br/>
        <#if isAdminModif?? && isAdminModif>
            <div class="row">
                <div class="col-3 offset-6">
                    <button class="btn btn-success btn-block" data-toggle="modal" data-target="#confirmationClonageAction" >
                        Dupliquer
                    </button>
                </div>
                <div class="col-3">
                    <button class="btn btn-danger btn-block" data-toggle="modal" data-target="#confirmationSuppressionAction" >
                        Supprimer
                    </button>
                </div>
            </div>
        </#if>
    </div>
    <input type="hidden" id="hasPerimetreAction" value="<#if hasPerimetre>${"true"}</#if>">
    <input type="hidden" id="nomPublicAction" value="${action.nomPublic!""}">
    <input type="hidden" id="perimetreUrlAction" value="<#if hasPerimetre && perimetre?? && perimetre.url??>${perimetre.url!}</#if>">
    <input type="hidden" id="geoportailApiKey" value="${geoportailApiKey!""}">
    <input type="hidden" id="modalContactUrl" value="<#if modalContactUrl??>'${modalContactUrl!}'<#else>#</#if>">
</div>
<div class="footer">
    <span style="margin-left: 50px">Les champs mentionnés avec un astérisque (*) sont obligatoires</span>
</div>

<#--modal d'ajout de contacts-->
<div class="modal fade" id="modalAjoutContact" role="dialog" aria-labelledby="modalAjoutContactTitle"
     aria-hidden="true">
</div>

<div class="modal fade" id="confirmationSuppressionAction" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 40%;">
        <div class="modal-content">
            <div class="modal-header bg-grey-light">
                <h5 class="modal-title" id="">Supprimer l'action</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="">
                    <p>Voulez vous vraiment supprimer cette action ? Vous perdrez définitivement toutes les données associées.</p>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Annuler</button>
                <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="deleteAction('${action.id?c!}')">Confirmer</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="confirmationClonageAction" tabindex="-1" role="dialog" aria-hidden="true">
    <#include '../../modal/modal_clone_action.ftl'/>
</div>

<div class="modal fade" id="confirmationSuppression" role="dialog" aria-hidden="true"
     style="z-index: 1052 !important;">
    <div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 40%;">
        <div class="modal-content">
            <div class="modal-header bg-grey-light">
                <h5 class="modal-title">Confirmer la suppression</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Êtes-vous sur ce vouloir supprimer cet élément?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Annuler</button>
                <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="deleteContact('${deleteUrl!""}')">Supprimer</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="erreurSuppressionAction" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 40%;">
        <div class="modal-content">
            <div class="modal-header bg-grey-light">
                <h5 class="modal-title">Erreur</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="alert alert-danger">

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">Retour</button>
            </div>
        </div>
    </div>
</div>


    <div class="modal fade" id="confirmationChangementTypeFinancement" role="dialog" aria-hidden="true"
     style="z-index: 1052 !important;">
    <div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 40%;">
        <div class="modal-content">
            <div class="modal-header bg-grey-light">
                <h5 class="modal-title">Confirmer le changement de type de financement</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p>Êtes-vous sûr de vouloir changer le type de financement de cette action ?<br/>Attention : ce changement peut avoir des impacts sur les étapes d'évaluation à compléter pour l'action concernée.</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" onclick="confirmChangementTypeFinancement(false)">Annuler</button>
                <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="confirmChangementTypeFinancement(true)">Confirmer</button>
            </div>
        </div>
    </div>
</div>
