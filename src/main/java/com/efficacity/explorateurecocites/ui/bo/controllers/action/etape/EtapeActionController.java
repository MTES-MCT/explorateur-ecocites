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

package com.efficacity.explorateurecocites.ui.bo.controllers.action.etape;

import com.efficacity.explorateurecocites.beans.model.Etape;
import com.efficacity.explorateurecocites.beans.service.EtapeService;
import com.efficacity.explorateurecocites.ui.bo.forms.EtapeForm;
import com.efficacity.explorateurecocites.ui.bo.forms.EtapeFormReponse;
import com.efficacity.explorateurecocites.utils.enumeration.ETAPE_STATUT;
import isotope.commons.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by rxion on 14/02/2018.
 */
@Controller
@RequestMapping("bo/actions/etape")
public class EtapeActionController {

  @Autowired
  EtapeService etapeServ;

  @GetMapping("{idAction}/{codeEtape}")
  @ResponseBody
  public EtapeFormReponse getEtape(@PathVariable("idAction") Long idAction, @PathVariable("codeEtape") String codeEtape) {
    Etape etape = etapeServ.getEtapeByActionAndCode(idAction, codeEtape);
    if(etape!=null) {
      return new EtapeFormReponse(String.valueOf(etape.getId()), etape.getCommentaire());
    }
    else{
      throw new BadRequestException("The request is not valid");
    }
  }

  @PostMapping("commentaire/majCommentaire")
  @ResponseBody
  public  EtapeFormReponse majCommentaire(@RequestBody @Valid EtapeForm etapeForm) {
    Etape etape = etapeServ.findByIdEtapeAndAction(Long.parseLong(etapeForm.getId()), Long.parseLong(etapeForm.getIdObjet()));
    if (etape != null && (ETAPE_STATUT.A_RENSEIGNER.getCode().equals(etape.getStatut()) ||
            ETAPE_STATUT.ENVOYER.getCode().equals(etape.getStatut()))) {
      etapeServ.majCommentaire(etape, etapeForm.getCommentaire());
      return new EtapeFormReponse(String.valueOf(etape.getId()), etape.getCommentaire());
    }
    return new EtapeFormReponse(null, null);
  }
}
