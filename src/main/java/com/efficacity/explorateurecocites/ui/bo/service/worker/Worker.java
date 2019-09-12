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

package com.efficacity.explorateurecocites.ui.bo.service.worker;

import com.efficacity.explorateurecocites.ajaris.AjarisClient;
import com.efficacity.explorateurecocites.beans.service.MediaModificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class Worker {

    @Autowired
    MediaModificationService mediaModificationService;

    @Autowired
    AjarisClient ajarisClient;

    @Scheduled(fixedRateString = "${efficacity.media.update.delay:300000}")
    public void handleJobQueue() {
        if (ajarisClient.isEnabled()) {
            mediaModificationService.doPendingJobs();
        }
    }

    /**
     * On redemande le token de façon régulière de façon à ne pas perdre de temps lors du chargement simultanées de plusieurs images.
     * Exemple:
     * - Un utilisateur charge sur la page d'accueil la page de présentation d'une Écocité
     * - Son navigateur essaie de charger toutes les images
     * - Si nous n'avons pas de tokens valide, toutes les images vont être en attente du token
     */
    @Scheduled(fixedRateString = "${efficacity.media.keepalive.delay:120000}")
    public void keepAlive() {
        if (ajarisClient.isEnabled()) {
            ajarisClient.keepAlive();
        }
    }
}
