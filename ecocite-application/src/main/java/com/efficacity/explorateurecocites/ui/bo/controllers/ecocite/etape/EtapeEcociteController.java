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

package com.efficacity.explorateurecocites.ui.bo.controllers.ecocite.etape;

import com.efficacity.explorateurecocites.beans.model.Etape;
import com.efficacity.explorateurecocites.beans.service.EtapeService;
import com.efficacity.explorateurecocites.ui.bo.forms.EtapeForm;
import com.efficacity.explorateurecocites.ui.bo.forms.EtapeFormReponse;
import com.efficacity.explorateurecocites.utils.enumeration.ETAPE_STATUT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by rxion on 14/02/2018.
 */
@Controller
@RequestMapping("bo/ecocites/etape")
public class EtapeEcociteController {

  @Autowired
  EtapeService etapeServ;

  @GetMapping("{idEcocite}/{codeEtape}")
  @ResponseBody
  public EtapeFormReponse getEtape(@PathVariable("idEcocite") Long idEcocite, @PathVariable("codeEtape") String codeEtape) {
    Etape etape = etapeServ.getEtapeByEcociteAndCode(idEcocite, codeEtape);
    if(etape!=null) {
      return new EtapeFormReponse(String.valueOf(etape.getId()), etape.getCommentaire());
    }
    else{
      return new EtapeFormReponse(null, null);
    }
  }

  @PostMapping("commentaire/majCommentaire")
  @ResponseBody
  public EtapeFormReponse majCommentaire(@RequestBody @Valid EtapeForm etapeForm) {
    Long etapeId = Long.parseLong(etapeForm.getId());
    Long etapeIdObjet = Long.parseLong(etapeForm.getIdObjet());
    Etape etape = etapeServ.findByIdEtapeAndEcocite(etapeId, etapeIdObjet);
    if (etape != null && (ETAPE_STATUT.ENVOYER.getCode().equals(etape.getStatut())
            || ETAPE_STATUT.A_RENSEIGNER.getCode().equals(etape.getStatut()))) {
      etapeServ.majCommentaire(etape, etapeForm.getCommentaire());
      return new EtapeFormReponse(String.valueOf(etape.getId()), etape.getCommentaire());
    }
    return new EtapeFormReponse(null, null);
  }
}
