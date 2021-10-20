<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the Etalab Open License, version 2.0..-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Etalab Open License for more details.-->
<#--You should have received a copy of the Etalab Open License along with this program. If not, see <https://www.etalab.gouv.fr/wp-content/uploads/2017/04/ETALAB-Licence-Ouverte-v2.0.pdf>.-->

<#-- @ftlvariable name="ecocite" type="com.efficacity.explorateurecocites.beans.biz.EcociteBean" -->
<#-- @ftlvariable name="imagesPrincipales" type="java.util.List<com.efficacity.explorateurecocites.beans.model.Media>" -->
<#-- @ftlvariable name="imagesSecondaires" type="java.util.List<com.efficacity.explorateurecocites.beans.model.Media>" -->
<#-- @ftlvariable name="ajarisStatus" type="java.lang.String" -->
<#-- @ftlvariable name="ajarisLoginUrl" type="java.lang.String" -->

<span style="font-size: 0.9rem; color: #83b93a; padding-left: 2rem;">&nbsp;</span>
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
        <div class="row">
            <div class="col-lg-3">
                <div class="form-group">
                    <label for="nomPublic"><strong>Nom *</strong></label> </br>
                    <input id="nomEcocite" <#if canEdit?? && !canEdit >disabled </#if> type="text" name="nom" value="${ecocite.nom!}" class="inputSaveAuto form-control" data-objectid="${ecocite.id!?c}" data-objectclass="Ecocite"/>
                    <p class="text-danger" style="display : none;"></p>
                </div>
            </div>
            <div class="col-lg-3">
                <div class="form-group">
                    <label for="nomPublic"><strong>Région *</strong></label> </br>
                    <select <#if canEdit?? && !canEdit >disabled </#if> name="idRegion" class="inputSaveAuto form-control" data-objectid="${ecocite.id!?c}" data-objectclass="Ecocite">
                        <option value=""></option>
                        <#list regions?keys as key>
                            <option value="${key}" <#if ecocite.idRegion?? && key==ecocite.idRegion!?c > selected</#if>>${regions[key]}</option>
                        </#list>
                    </select>
                    <p class="text-danger" style="display : none;"></p>
                </div>
            </div>
            <div class="col-lg-3">
                <div class="form-group">
                <label for="anneeAdhesion"><strong>ÉcoCité depuis *</strong></label> </br>
                <input <#if canEdit?? && !canEdit >disabled </#if> type="text" name="anneeAdhesion" value="${ecocite.anneeAdhesion!}" class="inputSaveAuto form-control" data-objectid="${ecocite.id!?c}" data-objectclass="Ecocite"/>
                <p class="text-danger" style="display : none;"></p>
            </div>
            </div>
            <div class="col-lg-3">
                <div class="form-group">
                    <label for="etatPublication"><strong>Etat de publication *</strong></label> </br>
                    <select <#if canEdit?? && !canEdit >disabled </#if> name="etatPublication" class="inputSaveAuto form-control" data-objectid="${ecocite.id!?c}" data-objectclass="Ecocite" <#if !(isAdminAccompaModif??)>disabled </#if>>
                        <option value=""></option>
                        <#if etatsPublication??>
                            <#list etatsPublication as e>
                                <option value="${e.code}" <#if e.code == ecocite.etatPublication!> selected</#if>>${e.libelle}</option>
                            </#list>
                        </#if>
                    </select>
                    <p class="text-danger" style="display : none;"></p>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-6">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="form-group">
                            <label for="siren"><strong>N° SIREN *</strong></label> </br>
                            <input id="siren" <#if canEdit?? && !canEdit >disabled </#if> type="text" name="siren" value="${ecocite.siren!}" class="inputSaveAuto form-control" data-objectid="${ecocite.id!?c}" data-objectclass="Ecocite"/>
                            <p class="text-danger" style="display : none;"></p>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="form-group">
                            <label for="descStrategie"><strong>Description de la stratégie territoriale *</strong></label> <br/>
                            <textarea <#if canEdit?? && !canEdit >disabled </#if> id="descStrategie" name="descStrategie" data-objectid="${ecocite.id!?c}" data-objectclass="Ecocite"></textarea>
                            <p class="text-danger" style="display : none;"></p>
                        </div>
                        <script>
                            var e1= $("#presentation").find("textarea[name=descStrategie]").get(0);
                            var instance1 = CKEDITOR.replace(e1);
                            instance1.setData("${(ecocite.descStrategieNoLineBreak!"")?noEsc}");
                            instance1.on('blur', function() {
                                saveAttribut(e1, this.getData());
                            });
                        </script>
                    </div>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="row">
                    <div class="form-group col-lg-6">
                        <div class="row">
                            <div class="col-lg-10">
                                <label for="contactSecond">
                                    <strong>Contact principal (1 max)</strong>
                                </label>
                            </div>
                            <div class="col-lg-2 pull-right">
                                <#if canEdit?? && canEdit>
                                    <#if contactPrincipal??>
                                    <div class="pull-right">
                                        <i id="addContact1"class="fa fa-plus-circle cursorPointer c-bleu-e" <#if contactPrincipal?size<1><#else> style="display: none" </#if> <#if modalContactUrl??>onclick="ajoutContactEcocite(-1, '${modalContactUrl!}',event)"</#if>></i>
                                    </div>
                                    </#if>
                                </#if>
                            </div>
                        </div>
                        <select <#if canEdit?? && !canEdit >disabled </#if> id="contactPrin" class="form-control select-one" name="contactPrin" style="width: 100%" multiple>
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
                            <div class="col-lg-2 pull-right">
                                <#if canEdit?? && canEdit>
                                    <#if contactSecondaire??>
                                    <div class="pull-right">
                                        <i id="addContact2" class="fa fa-plus-circle cursorPointer c-bleu-e"  <#if contactSecondaire?size<2><#else> style="display: none" </#if> <#if modalContactUrl??>onclick="ajoutContactEcocite(-1, '${modalContactUrl!}',event)"</#if>></i>
                                    </div>
                                    </#if>
                                </#if>
                            </div>
                        </div>
                        <select <#if canEdit?? && !canEdit >disabled </#if> id="contactSecond" class="form-control select-multiple" name="contactSecond" style="width: 100%" multiple>
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
                <div class="row">
                    <div class="form-group col-lg-6">
                        <label for="superficieKm2"><strong>Superficie (en km2) *</strong></label> </br>
                        <input <#if canEdit?? && !canEdit >disabled </#if> type="text" name="superficieKm2" value="${(ecocite.superficieKm2!0)?c}" class="inputSaveAuto form-control" data-objectid="${ecocite.id!?c}" data-objectclass="Ecocite"/>
                        <p class="text-danger" style="display : none;"></p>
                    </div>
                    <div class="form-group col-lg-6">
                        <label for="nbHabitants"><strong>Nombre d'habitants *</strong></label> </br>
                        <input <#if canEdit?? && !canEdit >disabled </#if> type="text" name="nbHabitants" value="${(ecocite.nbHabitants!0)?c}" class="inputSaveAuto form-control" data-objectid="${ecocite.id!?c}" data-objectclass="Ecocite"/>
                        <p class="text-danger" style="display : none;"></p>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-lg-6">
                        <label for="nbCommunes"><strong>Nombre de communes *</strong></label> </br>
                        <input <#if canEdit?? && !canEdit >disabled </#if> type="text" name="nbCommunes" value="${(ecocite.nbCommunes!0)?c}" class="inputSaveAuto form-control" data-objectid="${ecocite.id!?c}" data-objectclass="Ecocite"/>
                        <p class="text-danger" style="display : none;"></p>
                    </div>
                    <div class="form-group col-lg-6">
                        <label for="soutienPiaDetail"><strong>Soutien du PIA</strong></label> </br>
                        <input <#if canEdit?? && !canEdit >disabled </#if> type="text" name="soutienPiaDetail" value="${ecocite.soutienPiaDetail!}" class="inputSaveAuto form-control" data-objectid="${ecocite.id!?c}" data-objectclass="Ecocite"/>
                        <p class="text-danger" style="display : none;"></p>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-6">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="form-group">
                            <label for="descPerimetre"><strong>Description du périmètre de l'ÉcoCité</strong></label> <br/>
                            <textarea <#if canEdit?? && !canEdit >disabled </#if> id="descPerimetre" name="descPerimetre"  data-objectid="${ecocite.id!?c}" data-objectclass="Ecocite"></textarea>
                            <p class="text-danger" style="display : none;"></p>
                        </div>
                        <script>
                            var e2 = $("#presentation").find("textarea[name=descPerimetre]").get(0);
                            var instance2 = CKEDITOR.replace(e2);
                            instance2.setData("${(ecocite.descPerimetreNoLineBreak!"")?noEsc}");
                            instance2.on('blur', function() {
                                saveAttribut(e2, this.getData());
                            });
                        </script>
                    </div>
                    <div class="form-group col-lg-12">
                        <label for="porteur"><strong>Porteur du Projet *</strong></label> <br/>
                        <input <#if canEdit?? && !canEdit >disabled </#if> id="porteur" type="text" name="porteur" value="${ecocite.porteur!}" class="inputSaveAuto form-control" data-objectid="${ecocite.id!?c}" data-objectclass="Ecocite" style="width: 100%"/>
                        <p class="text-danger" style="display : none;"></p>
                    </div>
                </div>
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
                                onclick="getModalAddMedia('#modalAjoutDocument', 'ecocite', '${ecocite.id!?c}', 1)"
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
                                                <a class="m-l-10 btn-wrap-text" href="#" onclick="getModalEditMedia('#modalAjoutDocument', 'ecocite', '${ecocite.id!?c}','${img.id?c}')">
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
                                onclick="getModalAddMedia('#modalAjoutDocument', 'ecocite', '${ecocite.id!?c}', 2)"
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
                                                    <a class="m-l-10 btn-wrap-text" href="#" onclick="getModalEditMedia('#modalAjoutDocument', 'ecocite', '${ecocite.id!?c}','${img.id?c}')">
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
                                onclick="getModalAddFile('#modalAjoutDocument', 4, '${ecocite.id!?c}')"
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
                                        <i class="fa fa-search" onclick="getModalEditFile('#modalAjoutDocument', 4, '${ecocite.id!?c}','${doc.id?c}')"></i>
                                        <i class="fa fa-remove" onclick="deleteDocument('${doc.id?c}', '${ecocite.id?c}')"></i>
                                    </#if>
                                        </div>
                                    </div>
                                </p>
                            </#if>
                        </#list>
                    </#if>
                    </div>
                    <div class="form-group col-lg-6">
                        <label for="lien" style="margin-left: 10px"><strong>Lien site web</strong></label> </br>
                        <input <#if canEdit?? && !canEdit >disabled </#if> type="text" name="lien" value="${ecocite.lien!}"  style="width:96.4%; margin-left: 10px" class="inputSaveAuto form-control" data-objectid="${ecocite.id!?c}" data-objectclass="Ecocite"/>
                        <p class="text-danger" style="display : none;"></p>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-lg-12">
                        <label for="partProjet"><strong>Partenaire(s) du Projet (15 max)</strong></label><br/>
                        <select <#if canEdit?? && !canEdit >disabled </#if> id="partProjet" class="form-control select-tags" name="partenaire" style="width: 100%; display: grid" data-objectid="${ecocite.id!?c}" data-objectclass="Ecocite" multiple>
                        <#list partenairesEnumUsed as mo>
                            <#if mo?? && mo.code?? && mo.libelle??>
                                <option value="${mo.code}" selected>${mo.libelle}</option>
                            </#if>
                        </#list>
                        <#list partenairesEnumNotUsed as mo>
                            <#if mo?? && mo.code?? && mo.libelle??>
                                <option value="${mo.code}">${mo.libelle}</option>
                            </#if>
                        </#list>
                        <#list partenairesUsed as mo>
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
                    <div class="form-group col-lg-6">
                        <#assign hasPerimetreStrategique = perimetreStrategique?? && perimetreStrategique.originalName?hasContent && perimetreStrategique.title?hasContent && perimetreStrategique.url?? && perimetreStrategique.id??>
                        <label for="perimetre" style="text-overflow: hidden"><strong>Périmètre stratégique (1 max)</strong></label>
                        <button id="button-add-img-principale" type="button" name="perimetre"
                                class="btn btn-outline-primary btn-block" style="width:96.4%"
                                onclick="getModalAddFile('#modalAjoutDocument', 7, '${ecocite.id!?c}')"
                                <#if hasPerimetreStrategique || (canEdit?? && !canEdit)>disabled</#if>>
                            Choisissez un fichier KML
                        </button>
                        <#if hasPerimetreStrategique>
                            <p style="margin: 10px 10px 0;">
                                <div class="row">
                                    <div class="col-8">
                                        <a class="m-l-10 btn-wrap-text" download="${perimetreStrategique.originalName}" href="${perimetreStrategique.url}">${perimetreStrategique.title}</a>
                                    </div>
                                    <div class="col-4 col-h-center">
                                        <#if canEdit?? && canEdit >
                                            <i class="fa fa-search" onclick="getModalEditFile('#modalAjoutDocument', 7, '${ecocite.id!?c}','${perimetreStrategique.id?c}')"></i>
                                            <i class="fa fa-remove" onclick="deleteDocument('${perimetreStrategique.id?c}', '${ecocite.id?c}')"></i>
                                        </#if>
                                    </div>
                                </div>
                            </p>
                        </#if>
                    </div>
                    <div class="form-group col-lg-6">
                        <#assign hasPerimetreOperationnel = perimetreOperationnel?? && perimetreOperationnel.originalName?hasContent && perimetreOperationnel.title?hasContent && perimetreOperationnel.url?? && perimetreOperationnel.id??>
                        <label for="perimetre"><strong style="margin-left: 10px">Périmètre opérationnel (1 max)</strong></label>
                        <button id="button-add-img-principale" type="button" name="perimetre"
                                class="btn btn-outline-primary btn-block" style="width:96.4%; margin-left: 10px "
                                onclick="getModalAddFile('#modalAjoutDocument', 8, '${ecocite.id!?c}')"
                                <#if hasPerimetreOperationnel || (canEdit?? && !canEdit)>disabled</#if>>
                            Choisissez un fichier KML
                        </button>
                        <#if hasPerimetreOperationnel>
                            <p style="margin: 10px 10px 0;">
                                 <div class="row">
                                     <div class="col-8">
                                         <a class="m-l-20 btn-wrap-text" download="${perimetreOperationnel.originalName}" href="${perimetreOperationnel.url}">${perimetreOperationnel.title}</a>
                                     </div>
                                     <div class="col-4 col-h-center">
                                        <#if canEdit?? && canEdit >
                                            <i class="fa fa-search m-l-20" onclick="getModalEditFile('#modalAjoutDocument', 8, '${ecocite.id!?c}','${perimetreOperationnel.id?c}')"></i>
                                            <i class="fa fa-remove" onclick="deleteDocument('${perimetreOperationnel.id?c}', '${ecocite.id?c}')"></i>
                                        </#if>
                                     </div>
                                 </div>
                            </p>
                        </#if>
                    </div>
                </div>
                 <div class="form-row">
                    <div class="form-group col-lg-6">
                        <label for="latitude"><strong>Latitude *</strong> <i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Pour obtenir la latitude et la longitude de votre projet, vous pouvez aller sur le site : https://www.geoportail.gouv.fr/carte"></i></label> </br>
                        <input id="latitudeEcocite"  <#if canEdit?? && !canEdit >disabled </#if> type="text" name="latitude" value="${ecocite.latitude!""}"
                               class="inputSaveAuto form-control updateMap" style="width:96.4%" data-objectid="${ecocite.id!?c}" data-objectclass="Ecocite"/>
                        <p class="text-danger" style="display : none;"></p>
                    </div>
                    <div class="form-group col-lg-6">
                        <label for="longitude"><strong style="margin-left: 10px">Longitude *</strong> <i class="fa fa-info-circle" data-toggle="tooltip" data-placement="top" title="Pour obtenir la latitude et la longitude de votre projet, vous pouvez aller sur le site : https://www.geoportail.gouv.fr/carte"></i></label> </br>
                        <input id="longitudeEcocite"  <#if canEdit?? && !canEdit >disabled </#if> type="text" name="longitude" value="${ecocite.longitude!""}"
                               class="inputSaveAuto form-control updateMap" style="width:96.4%; margin-left: 10px " data-objectid="${ecocite.id!?c}" data-objectclass="Ecocite"/>
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
        <#if isAdminModif?? && isAdminModif>
            <div class="row">
                <div class="col-3 offset-9">
                    <br/> <br/>
                    <button class="btn btn-danger btn-block" data-toggle="modal" data-target="#confirmationSuppressionEcocite" >
                        Supprimer
                    </button>
                </div>
            </div>
        </#if>
    </div>
    <input type="hidden" id="hasPerimetreStrategiqueEcocite" value="<#if hasPerimetreStrategique>${"true"}</#if>">
    <input type="hidden" id="hasPerimetreOperationnelEcocite" value="<#if hasPerimetreOperationnel>${"true"}</#if>">
    <input type="hidden" id="nomPublicEcocite" value="${ecocite.nom!""}">
    <input type="hidden" id="perimetreStrategiqueUrlEcocite" value="<#if hasPerimetreStrategique && perimetreStrategique?? && perimetreStrategique.url??>${perimetreStrategique.url!""}</#if>">
    <input type="hidden" id="perimetreOperationnelUrlEcocite" value="<#if hasPerimetreOperationnel && perimetreOperationnel?? && perimetreOperationnel.url??>${perimetreOperationnel.url!""}</#if>">
    <input type="hidden" id="geoportailApiKey" value="${geoportailApiKey!""}">
    <input type="hidden" id="modalContactUrl" value="<#if modalContactUrl??>'${modalContactUrl!}'<#else>#</#if>">
</div>
<div class="footer row">
    <span style="margin-left: 50px">Les champs mentionnés avec un astérisque (*) sont obligatoires</span>
</div>

<#--modal d'ajout de contacts-->
<div class="modal fade" id="modalAjoutContact" role="dialog" aria-labelledby="modalAjoutContactTitle"
     aria-hidden="true">
</div>

<div class="modal fade" id="confirmationSuppressionEcocite" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 40%;">
        <div class="modal-content">
            <div class="modal-header bg-grey-light">
                <h5 class="modal-title" id="">Supprimer l'ÉcoCité</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="">
                    <p>Voulez vous vraiment supprimer cette ÉcoCité ? Vous perdrez définitivement toutes les données associées.</p>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Annuler</button>
                <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="deleteEcocite('${ecocite.id?c!}')">Confirmer</button>
            </div>
        </div>
    </div>
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

<div class="modal fade" id="erreurSuppressionEcocite" tabindex="-1" role="dialog" aria-hidden="true">
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

<div class="modal fade" id="erreurSauvegardPresentation" tabindex="-1" role="dialog" aria-hidden="true">
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
