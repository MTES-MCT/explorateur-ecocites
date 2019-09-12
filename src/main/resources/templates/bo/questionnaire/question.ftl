<#ftl outputFormat="HTML">
<#--Explorateur Écocités-->
<#--Copyright (C) 2019 l'État, ministère chargé du logement-->
<#--This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.-->
<#--This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.-->
<#--You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<#-- @ftlvariable name="question" type="com.efficacity.explorateurecocites.beans.biz.QuestionQuestionnaireEvaluationBean" -->
<#-- @ftlvariable name="objetId" type="java.lang.Long" -->
<#-- @ftlvariable name="objetType" type="java.lang.String" -->
<#-- @ftlvariable name="codeQuestionnaire" type="java.lang.String" -->

<#switch question.typeReponse>
    <#case "file">
        <#if canEditFile?? && canEditFile>
            <div class="row  p-l-30">
                <div class="form-group col-12">
                    <#assign maxCapacity = false>
                    <#if question.listeReponses?? && question.listeReponses?hasContent && question.listeReponses[0]?? && question.listeReponses[0].files??>
                        <#assign files = question.listeReponses[0].files>
                        <#assign maxCapacity = files?? && files?hasContent && 3 <= files?size>
                    </#if>
                    <#if (question.question?? && question.question?length > 0) ><label for="photoSecond"><strong>${question.question!}</strong></label></#if>
                </div>
            </div>
            <div class="row  p-l-30">
                <div class="form-group col-3">
                    <button id="button-create-indic" type="button" name="photoSecond"
                            class="btn btn-outline-secondary btn-block"
                            onclick="getModalAddFile('#modalAjoutDocument', 9, '${objetId!?c}', '${question.id!?c}', '${codeQuestionnaire}')"
                            <#if maxCapacity || etapeDisabled??>disabled</#if>
                            >
                        Choisissez un document
                    </button>
                    <#if files??>
                        <#list files as file>
                            <#if file?? && file.originalName?hasContent && file.title?hasContent && file.url?? && file.id??>
                               <#if etapeDisabled??>
                                <p id="${file.id?c}" style="margin: 10px 10px 0;">
                                    <a class="m-l-10 btn-wrap-text" download="${file.originalName}" href="${file.url}">${file.title}</a>
                                </p>
                                <#else>
                                <div id="${file.id?c}" style="margin: 10px 10px 0;">
                                    <div class="row">
                                        <div class="col-8">
                                            <a class="m-l-10 btn-wrap-text" download="${file.originalName}" href="${file.url}">${file.title}</a>
                                        </div>
                                         <div class="col-4 col-h-center">
                                            <i class="fa fa-search" onclick="getModalEditFile('#modalAjoutDocument', 9,'${objetId!?c}','${file.id!?c}','${question.id!?c}','${codeQuestionnaire!}')"></i>
                                            <i class="fa fa-remove" onclick="deleteDocumentQuestionnaire('${file.id?c}', '${question.listeReponses[0].id?c}')"></i>
                                        </div>
                                    </div>
                                </div>
                                </#if>
                            </#if>
                        </#list>
                    </#if>
                </div>
            </div>
        <#elseIf canConsulte!false>
            <div class="row  p-l-30">
                <div class="form-group col-12">
                    <#if question.listeReponses?? && question.listeReponses?hasContent && question.listeReponses[0]?? && question.listeReponses[0].files??>
                        <#assign files = question.listeReponses[0].files>
                    </#if>
                    <#if (question.question?? && question.question?length > 0) ><label for="photoSecond"><strong>${question.question!}</strong></label></#if>
                </div>
            </div>
            <div class="row  p-l-30">
                <div class="form-group col-3">
                    <button id="button-create-indic" type="button" name="photoSecond"
                            class="btn btn-outline-secondary btn-block"
                            disabled
                            >
                        Choisissez un document
                    </button>
                    <#if files??>
                        <#list files as file>
                            <#if file?? && file.originalName?hasContent && file.title?hasContent && file.url?? && file.id??>
                               <#if etapeDisabled??>
                                <p id="${file.id?c}" style="margin: 10px 10px 0;">
                                    <a class="m-l-10 btn-wrap-text" download="${file.originalName}" href="${file.url}">${file.title}</a>
                                </p>
                                <#else>
                                <div id="${file.id?c}" style="margin: 10px 10px 0;">
                                    <div class="row">
                                        <div class="col-8">
                                            <a class="m-l-10 btn-wrap-text" download="${file.originalName}" href="${file.url}">${file.title}</a>
                                        </div>
                                         <div class="col-4 col-h-center">
                                            <i class="fa fa-search" onclick="getModalEditFile('#modalAjoutDocument', 9,'${objetId!?c}','${file.id!?c}','${question.id!?c}','${codeQuestionnaire!}')"></i>
                                            <i class="fa fa-remove" onclick="deleteDocumentQuestionnaire('${file.id?c}', '${question.listeReponses[0].id?c}')"></i>
                                        </div>
                                    </div>
                                </div>
                                </#if>
                            </#if>
                        </#list>
                    </#if>
                </div>
            </div>
        <#else>
        <div class="form-group p-l-30">
            <p>Vous ne pouvez pas voir les notes de synthèse de cette Action.</p>
        </div>
        </#if>
        <#break>
    <#case "texte_libre">
        <div class="form-group p-l-30" data-idquestion="${question.id?c}" <#if question.idQuestionMere?? && !question.mereHasReponseVoulu> style="display:none;"</#if>>
            <label for="nomPublic"><strong>${question.question!} *</strong></label> <br/>
            <input type="hidden" name="reponses[${question.id?c}].id" value="${question.id?c}"/>
            <div class="p-l-20">
                <textarea class="form-control inputSaveAuto"
                          cols="100" rows="3"
                          name="reponses[${question.id?c}].reponseText"
                          data-referenceid="${question.id?c}"
                          data-objectclass="ReponsesQuestionnaireEvaluation"
                          data-objectid="${objetId?c}"
                          <#if etapeDisabled??>disabled</#if>><#if question.reponseUnique??>${question.reponseUnique.reponsePrincipale}</#if></textarea>
                <p class="text-danger" style="display : none;"></p>
            </div>
        </div>
        <#break>
    <#case "radio_bouton">
            <div class="form-group p-l-30" data-idquestion="${question.id?c}" <#if question.idQuestionMere?? && !question.mereHasReponseVoulu> style="display:none;"</#if>>
                <label for="reponses[${question.id?c}].reponseList"><strong>${question.question!} *</strong></label> <br/>
                <input type="hidden" name="reponses[${question.id?c}].id" value="${question.id?c}"/>
                <div class="p-l-20">
                    <#list question.reponses?split("$$$") as reponsePossible>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio"
                                       name="reponses[${question.id?c}].reponseText"
                                       value="${reponsePossible}"
                                       class="form-check-input inputSaveAuto"
                                       data-referenceid="${question.id?c}"
                                       data-objectclass="ReponsesQuestionnaireEvaluation"
                                       data-objectid="${objetId?c}"
                                        <#if question.getIsReponsePrincipale(reponsePossible)>checked</#if>
                                       <#if etapeDisabled??>disabled</#if>/>
                                ${reponsePossible}
                            </label>
                        </div>
                    </#list>
                    <p class="text-danger" style="display : none;"></p>
                </div>
            </div>
        <#break>
    <#case "radio_bouton_autre">
        <div class="form-group radio p-l-30" data-idquestion="${question.id?c}" <#if question.idQuestionMere?? && !question.mereHasReponseVoulu> style="display:none;"</#if>>
            <label for="reponses[${question.id?c}].reponseList"><strong>${question.question!} *</strong></label> <br/>
            <input type="hidden" name="reponses[${question.id?c}].id" value="${question.id?c}"/>
            <div class="p-l-20">
                <#list question.reponses?split("$$$") as reponsePossible>
                    <div class="form-check">
                        <label>
                            <input type="radio"
                                   name="reponses[${question.id?c}].reponseText"
                                   value="${reponsePossible}"
                                   class="form-check-input inputSaveAuto"
                                   data-referenceid="${question.id?c}"
                                   data-objectclass="ReponsesQuestionnaireEvaluation"
                                   data-objectid="${objetId?c}"
                                   <#if question.getIsReponsePrincipale(reponsePossible)>checked</#if>
                                   <#if etapeDisabled??>disabled</#if>/>
                            ${reponsePossible}
                        </label>
                    </div>
                </#list>
                <div class="form-check">
                    <label>
                        <input type="radio"
                               onchange="onChangeAutre(this);"
                               name="reponses[${question.id?c}].reponseText"
                               value="Autre"
                               class="form-check-input inputSaveAuto"
                               data-referenceid="${question.id?c}"
                               data-objectclass="ReponsesQuestionnaireEvaluation"
                               data-objectid="${objetId?c}"
                               <#if question.getIsReponsePrincipale("Autre")>checked</#if>
                               <#if etapeDisabled??>disabled</#if>/>
                        Autre :
                    </label>
                        <#if !question.getIsReponsePrincipale("Autre")?? || etapeDisabled?? >
                            <span data-toggle="tooltip" data-placement="top" title="<#if question.reponseUnique??>${question.reponseUnique.reponseSecondaire!}</#if>">
                                <input type="text"
                                       name="reponses[${question.id?c}].reponseTextSecondaire"
                                       value="<#if question.reponseUnique??>${question.reponseUnique.reponseSecondaire!}</#if>"
                                       class="form-control fieldTextAutre inputSaveAuto"
                                       data-referenceid="${question.id?c}"
                                       data-objectclass="ReponsesQuestionnaireEvaluation"
                                       data-objectid="${objetId?c}"
                                       style="pointer-events: none;"
                                       disabled />
                            </span>
                        <#else>
                            <input type="text"
                               name="reponses[${question.id?c}].reponseTextSecondaire"
                               value="<#if question.reponseUnique??>${question.reponseUnique.reponseSecondaire!}</#if>"
                               class="form-control fieldTextAutre inputSaveAuto"
                               data-referenceid="${question.id?c}"
                               data-objectclass="ReponsesQuestionnaireEvaluation"
                               data-objectid="${objetId?c}" />
                        </#if>
                </div>
                <p class="text-danger" style="display : none;"></p>
            </div>
        </div>
        <#break>
    <#case "radio_bouton_oui_non_nonconcerne">
        <div class="form-group p-l-30" data-idquestion="${question.id?c}" <#if question.idQuestionMere?? && !question.mereHasReponseVoulu> style="display:none;"</#if>>
            <label for="reponses[${question.id?c}].reponseList"><strong>${question.question!} *</strong></label> <br/>
            <input type="hidden" name="reponses[${question.id?c}].id" value="${question.id?c}"/>
            <div class="p-l-20">
                <#list question.reponses?split("$$$") as reponsePossible>
                    <div class="form-check-inline">
                        <label>
                            <input type="radio"
                                   name="reponses[${question.id?c}].reponseText"
                                   onchange="onChangeExplicatif(this);"
                                   value="${reponsePossible}" class="form-check-input inputSaveAuto"
                                   data-referenceid="${question.id?c}"
                                   data-objectclass="ReponsesQuestionnaireEvaluation"
                                   data-objectid="${objetId?c}"
                                   <#if question.getIsReponsePrincipale(reponsePossible)>
                                       <#assign showAnswer = (reponsePossible != "non concerné") />
                                       checked
                                   </#if>
                                   <#if etapeDisabled??>disabled</#if>
                                   />
                            ${reponsePossible}
                        </label>
                    </div>
                </#list>
                <textarea <#if showAnswer?? && !showAnswer>style="display:none;"</#if>
                          class="form-control fieldTextExplicatif inputSaveAuto"
                          cols="100" rows="3"
                          name="reponses[${question.id?c}].reponseTextSecondaire"
                          data-referenceid="${question.id?c}"
                          data-objectclass="ReponsesQuestionnaireEvaluation"
                          data-objectid="${objetId?c}"
                          <#if etapeDisabled??>disabled</#if>><#if question.reponseUnique??>${question.reponseUnique.reponseSecondaire!}</#if></textarea>
                <p class="text-danger" style="display : none;"></p>
            </div>
        </div>
        <#break>
    <#case "checkbox_autre">
        <div class="form-group p-l-30" data-idquestion="${question.id?c}" <#if question.idQuestionMere?? && !question.mereHasReponseVoulu> style="display:none;"</#if>>
            <label for="reponses[${question.id?c}].reponseList"><strong>${question.question!} *</strong></label> <br/>
            <input type="hidden" name="reponses[${question.id?c}].id" value="${question.id?c}"/>
            <div class="p-l-20">
                <#list question.reponses?split("$$$") as reponsePossible>
                    <div class="form-check">
                        <label>
                            <input type="checkBox"
                                   name="reponses[${question.id?c}].reponseList"
                                   value="${reponsePossible}"
                                   class="form-check-input inputSaveAuto"
                                   data-referenceid="${question.id?c}"
                                   data-objectclass="ReponsesQuestionnaireEvaluation"
                                   data-objectid="${objetId?c}"
                                   <#if question.getReponseByValue(reponsePossible)??>checked</#if>
                                   <#if etapeDisabled??>disabled</#if>/>
                            ${reponsePossible}
                        </label>
                    </div>
                </#list>
                <div class="form-check">
                    <label>
                        <input type="checkBox"
                               onchange="onChangeAutre(this);"
                               name="reponses[${question.id?c}].reponseList"
                               value="Autre"
                               class="form-check-input inputSaveAuto"
                               data-referenceid="${question.id?c}"
                               data-objectclass="ReponsesQuestionnaireEvaluation"
                               data-objectid="${objetId?c}"
                               <#if question.getReponseByValue("Autre")??>checked</#if>
                               <#if etapeDisabled??>disabled</#if>/>
                        Autre :
                    </label>
                    <#if !question.getReponseByValue("Autre")?? || etapeDisabled?? >
                        <span data-toggle="tooltip" data-placement="top" title="<#if question.getReponseByValue('Autre')??>${question.getReponseByValue('Autre').reponseSecondaire!}</#if>">
                            <input type="text"
                                   name="reponses[${question.id?c}].reponseTextSecondaire"
                                   value="<#if question.getReponseByValue('Autre')??>${question.getReponseByValue('Autre').reponseSecondaire!}</#if>"
                                   class="form-control fieldTextAutre inputSaveAuto"
                                   data-referenceid="${question.id?c}"
                                   data-objectclass="ReponsesQuestionnaireEvaluation"
                                   data-objectid="${objetId?c}"
                                   style="pointer-events: none;"
                                   disabled />
                        </span>
                    <#else>
                        <input type="text"
                           name="reponses[${question.id?c}].reponseTextSecondaire"
                           value="<#if question.getReponseByValue('Autre')??>${question.getReponseByValue('Autre').reponseSecondaire!}</#if>"
                           class="form-control fieldTextAutre inputSaveAuto"
                           data-referenceid="${question.id?c}"
                           data-objectclass="ReponsesQuestionnaireEvaluation"
                           data-objectid="${objetId?c}" />
                    </#if>
                </div>
                <p class="text-danger" style="display : none;"></p>
            </div>
        </div>
        <#break>
    <#case "checkbox_texte_libre">
        <div class="form-group p-l-30" data-idquestion="${question.id?c}" <#if question.idQuestionMere?? && !question.mereHasReponseVoulu> style="display:none;"</#if>>
            <label for="reponses[${question.id?c}].reponseList"><strong>${question.question!} *</strong></label> <br/>
            <input type="hidden" name="reponses[${question.id?c}].id" value="${question.id?c}"/>
            <div class="p-l-20">
                <#list question.reponses?split("$$$") as reponsePossible>
                    <div class="form-check m-b-10 p-l-20">
                        <label>
                            <input type="checkBox"
                                   data-idasso="${reponsePossible?index}"
                                   onchange="onChangeShowFieldTextAssocier(this);"
                                   name="reponses[${question.id?c}].reponseList[${reponsePossible?index}]"
                                   value="${reponsePossible}"
                                   class="form-check-input inputSaveAuto"
                                   data-referenceid="${question.id?c}"
                                   data-objectclass="ReponsesQuestionnaireEvaluation"
                                   data-objectid="${objetId?c}"
                                   <#if question.getReponseByValue(reponsePossible)??>checked</#if>
                                   <#if etapeDisabled??>disabled</#if>/>
                            ${reponsePossible}
                        </label>
                        <#if !question.getReponseByValue(reponsePossible)?? || etapeDisabled?? >
                            <span data-toggle="tooltip" data-placement="top" title="<#if question.getReponseByValue(reponsePossible)??>${question.getReponseByValue(reponsePossible).reponseSecondaire!}</#if>">
                                <input type="text"
                                       name="reponses[${question.id?c}].reponseListSecondaire[${reponsePossible?index}]"
                                       value="<#if question.getReponseByValue(reponsePossible)??>${question.getReponseByValue(reponsePossible).reponseSecondaire!}</#if>"
                                       class="form-control inputSaveAuto fieldTextCheckBoxSecondaire${reponsePossible?index}"
                                       data-referenceid="${question.id?c}"
                                       data-objectclass="ReponsesQuestionnaireEvaluation"
                                       data-objectid="${objetId?c}"
                                       style="pointer-events: none;"
                                       disabled />
                            </span>
                        <#else>
                            <input type="text"
                               name="reponses[${question.id?c}].reponseListSecondaire[${reponsePossible?index}]"
                               value="<#if question.getReponseByValue(reponsePossible)??>${question.getReponseByValue(reponsePossible).reponseSecondaire!}</#if>"
                               class="form-control inputSaveAuto fieldTextCheckBoxSecondaire${reponsePossible?index}"
                               data-referenceid="${question.id?c}"
                               data-objectclass="ReponsesQuestionnaireEvaluation"
                               data-objectid="${objetId?c}" />
                        </#if>
                    </div>
                </#list>
                <p class="text-danger" style="display : none;"></p>
            </div>
        </div>
        <#break>
    <#default>

</#switch>
<#if question.idQuestionMere?? >
<script>
    $(".form-group[data-idquestion='${question.idQuestionMere}'] input").on('change', function(){
        if($(this).attr("type")==="checkBox" || $(this).attr("type")==="radio") {
            if (this.checked === true && this.value === "${question.reponseAttendu}") {
                $(".form-group[data-idquestion='${question.id?c}']").show();
            } else {
                $(".form-group[data-idquestion='${question.id?c}']").hide();
                $(".form-group[data-idquestion='${question.id?c}'] input[type='text']").val("");
                $(".form-group[data-idquestion='${question.id?c}'] textarea").val("");
                $(".form-group[data-idquestion='${question.id?c}'] input[type='checkBox']").attr("checked", false);
                $(".form-group[data-idquestion='${question.id?c}'] input[type='radio']").attr("checked", false);
            }
        }
    });
    $(function() {
    	var input = $(".form-group[data-idquestion='${question.idQuestionMere}'] input");
        if(input.attr("type")==="checkBox" || input.attr("type")==="radio") {
            if (input.attr("checked") === true && input.val() === "${question.reponseAttendu}") {
                $(".form-group[data-idquestion='${question.id?c}']").show();
            } else {
                $(".form-group[data-idquestion='${question.id?c}']").hide();
                $(".form-group[data-idquestion='${question.id?c}'] input[type='text']").val("");
                $(".form-group[data-idquestion='${question.id?c}'] textarea").val("");
                $(".form-group[data-idquestion='${question.id?c}'] input[type='checkBox']").attr("checked", false);
                $(".form-group[data-idquestion='${question.id?c}'] input[type='radio']").attr("checked", false);
            }
        }
    })
</script>
</#if>
