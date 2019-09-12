insert into exp_region (id, nom, shortname) values
  (1, 'Auvergne-Rhône-Alpes', ''),
  (2, 'Bretagne', ''),
  (3, 'Grand Est', ''),
  (4, 'Hauts-de-France', ''),
  (5, 'Ile-de-France', ''),
  (6, 'La Réunion', ''),
  (7, 'Normandie', ''),
  (8, 'Nouvelle Aquitaine', ''),
  (9, 'Occitanie', ''),
  (10, 'Pays de la Loire', ''),
  (11, 'Provence-Alpes-Côte d''Azur', '');

insert into exp_axe (id, libelle, code_couleur1, code_couleur2, icone, user_modification, date_modification) values
  (1, 'Bâtiments et usages', '#dd517d ', '#b72b57', '/img/icons/axe/Batiments et usages.png', null, null),
  (2, 'Conception urbaine et environnement', '#74b44f ', '#7eb830', '/img/icons/axe/Conception urbaine et environnement.png', null, null),
  (3, 'Economie durable et de la connaissance', '#354294', '#2b2f6b', '/img/icons/axe/Economie durable et de la connaissance.png', null, null),
  (4, 'Energie et réseaux', '#e8ca5c', '#ffd218', '/img/icons/axe/Energie et reseaux.png', null, null),
  (5, 'Equipements et services au public', '#74be9b', '#00b188', '/img/icons/axe/Equipements et services au public.png', null, null),
  (6, 'Mobilités', '#e6a04f', '#f27a33', '/img/icons/axe/Mobilites.png', null, null),
  (7, 'Services urbains innovants', '#804989', '#492D51', '/img/icons/axe/Services urbains innovants.png', null, null);

INSERT INTO is_role (id, code, date_creation) VALUES
  (1, 'VISITEUR_PUBLIC', '2018-02-14'),
  (2, 'ACTEUR_ECOCITE_AUTRE', '2018-02-14'),
  (3, 'PORTEUR_ACTION', '2018-02-14'),
  (4, 'REFERENT_ECOCITE', '2018-02-14'),
  (5, 'ACCOMPAGNATEUR', '2018-02-14'),
  (6, 'ADMIN_EVALUATION', '2018-02-14'),
  (7, 'ADMIN_GENERAL', '2018-02-14');

INSERT INTO exp_questions_evaluation(id, titre, description) VALUES
  (1, 'Innovation technique et technologique', 'Avancée significative par rapport à la technologie actuelle, innovation technologique de rupture, amélioration notable d’un procédé existant, utilisation nouvelle d''une technologie déjà existante…'),
  (2, 'Modèle économique innovant', 'Innovation relatives au business model de l''action : modèle économique en open source ou open innovation, billettique intelligente (systèmes flexibles de tarification selon le degré d''utilisation…)'),
  (3, 'Innovation organisationnelle et de gouvernance', 'Innovation en matière de gestion de projet : intégration élargie des parties prenantes (usagers ou autres acteurs de la ville…) et /ou réorganisation des façons de concevoir et de piloter un projet'),
  (4, 'Information et communication urbaines innovantes', 'Innovation facilitant la diffusion de l’information et la communication entre les usagers et les acteurs de la ville (open data, plateformes ou applications web, tableau de bord sur la ville...)'),
  (5, 'Nouvelle forme d’usage et de services', 'Innovation apportant une amélioration significative dans la nature, les fonctions ou dans la manière d’utiliser un produit ou service existant (autopartage, mutualisation d''objets…)'),
  (6, 'Interaction sociale innovante', 'Action où l’innovation vise à former une communauté et / ou une interaction entre acteurs qui, sans le projet, n''auraient pas été mis en contact (application qui met en contact des voisins...)');

insert into exp_etiquette_axe (id, id_axe, libelle, description) values
  (8, 6, 'Transports en commun (dont TCSP)', 'Actions liées aux transports en commun (bus, tram, train, métro...)'),
  (9, 6, 'Services d’aide à la mobilité', 'Système d''aide à l''exploitation et à l''information des voyageurs (billettique multimodale, systèmes d’informations en temps réel…)'),
  (10, 6, 'Pôle intermodal', 'Actions facilitant l’intermodalité (pôles d''échange, centrales de mobilité…)'),
  (14, 6, 'Stationnement', 'Actions concernant le stationnement (parkings mutualisé, mutable, déporté…)'),
  (11, 6, 'Véhicules individuels motorisés', 'Actions liées aux véhicules privés (bornes de recharge électriques, stations d’autopartage, de covoiturage…)'),
  (13, 6, 'Modes actifs', 'Actions liées aux modes actifs (marche, vélo…)'),
  (12, 6, 'Logistique urbaine', 'Transport de marchandises ou de déchets en milieu urbain (gestion du dernier kilomètre, mutualisation des trajets, centres de distribution urbaine…)'),
  (15, 1, 'Construction', 'Comprend toutes les actions de constructions (bâtiment ou îlot démonstrateur)'),
  (17, 1, 'Exploitation et usages dans les bâtiments', 'Accompagnement des habitants et réflexions sur les usages et l''exploitation dans les bâtiments'),
  (18, 1, 'Instrumentation et suivi de performance', 'Mise en place de capteurs ou compteurs intelligents et suivi de la performance à l’échelle du bâtiment'),
  (16, 1, 'Rénovation', 'Rénovation et réhabilitation de bâtiments ou de copropriétés'),
  (20, 2, 'Trame viaire et trame bâtie', 'Actions de modification ou de réflexion sur la trame viaire, la trame bâtie, les paysages'),
  (19, 2, 'Aménagement des espaces publics', 'Actions d’aménagement de l’espace public (y compris le mobilier urbain)'),
  (22, 2, 'Agriculture urbaine', 'Mise en place de jardins ou potagers urbains (toits ou friches cultivés, jardins partagés...) et autres surfaces de production agricole en ville (containers, façades...)'),
  (21, 2, 'Espaces verts, milieux naturels et aquatiques', 'Aménagement des espaces verts et gestion alternative du cycle de l’eau (eaux pluviales, traitement alternatif des eaux usées via phyto-épuration…)'),
  (23, 2, 'Gestion des sols et des déblais', 'Actions de gestion et de traitement des sols'),
  (24, 2, 'Gestion des risques', 'Gestion et surveillance des risques naturels et technologiques'),
  (5, 4, 'Réseaux et smartgrid', 'Réseaux de chaleur ou de froid ou réseaux intelligents d’électricité'),
  (6, 4, 'Production d’énergie', 'Actions liées à la production d’énergie (électricité, chaleur, froid)'),
  (7, 4, 'Stockage de l''énergie', 'Actions liées au stockage de l’énergie (électricité, chaleur, froid)'),
  (3, 7, 'Monitoring, données et modélisation urbaine', 'Monitoring urbain et déploiement de capteurs, open data, modélisation 3D de bâtiments ou de quartiers (BIM, CIM)'),
  (4, 7, 'Eclairage public', 'Actions d’optimisation des systèmes d’éclairage public'),
  (2, 7, 'Collecte, valorisation et traitement des déchets', 'Systèmes de collecte (tri sélectif, etc.), de valorisation (énergie ou matière) et de traitement des déchets'),
  (1, 7, 'Gestion de l’eau et assainissement', 'Actions relatives aux stations, procédés et réseaux d’assainissement, de production et de distribution d’eau potable'),
  (31, 3, 'Economie circulaire', 'Actions de valorisation matière de ressources (réutilisation, recyclage, réemploi), par ex. au sein de recycleries ou via la mise en place d’échanges entre industriels'),
  (32, 3, 'Economie sociale et solidaire', 'Actions portées par ou en soutien direct à des structures d''ESS visant à concilier utilité sociale, solidarité, viabilité économique et gouvernance démocratique'),
  (33, 3, 'Economie collaborative', 'Actions de partage ou d’échange entre particuliers de biens ou de connaissances, avec ou sans échange monétaire (AMAP, coworking, fablabs...)'),
  (34, 3, 'Incubateurs et clusters de R&D', 'Actions de soutien direct à des start-ups (incubateur…) et à la création/développement de structures de R&D (cluster R&D, pôle de compétitivité, institut de recherche…)'),
  (25, 5, 'Education, sensibilisation et formation', 'Actions de renforcement des compétences, de sensibilisation et de formation du grand public ou de professionnels'),
  (26, 5, 'Culture, loisirs et patrimoine', 'Valorisation du patrimoine local (matériel et immatériel) et développement d’une offre culturelle, de loisirs et de découverte du territoire'),
  (27, 5, 'Service de santé et de soins', 'Structures, services et politiques publiques en faveur de la santé et des soins'),
  (28, 5, 'Application numérique et site web', 'Développement d''outils numériques (application mobile, site web…) mis à disposition du public ou des habitants d’un quartier'),
  (29, 5, 'Conciergerie', 'Mise en place d’un espace d’offres multiservices de proximité'),
  (30, 5, 'Espaces de vivre ensemble', 'Actions qui favorisent les espaces de rencontre, d’échanges ou de sociabilité ; éphémères ou pérennes ; physiques (maison de quartier, association…) ou virtuels (réseaux sociaux…)');

insert into exp_finalite (id, code_couleur, libelle) values
  (1, '#17396C', 'Attractivité'),
  (2, '#477ec0', 'Bien-être'),
  (3, '#008ecf', 'Cohésion sociale'),
  (4, '#3aaa35', 'Résilience'),
  (5, '#129a7e', 'Préservation de l’environnement'),
  (6, '#95c11f', 'Usage responsable des ressources');

insert into exp_etiquette_finalite (id, id_finalite, libelle, description) values
  (1, 1, 'Renforcer l’emploi et l’économie locale', 'Action qui participe au développement économique local, à l’émergence ou la consolidation de filières locales, à la création d’emplois locaux et pérennes (notamment dans le domaine de la R&D, de la transition énergétique et écologique)'),
  (2, 1, 'Assurer la connectivité et l’accès aux aménités sur le territoire', 'Action qui renforce la connectivité, la diversité des fonctions et l’accès aux différents services du territoire (y compris le lien avec les autres territoires)'),
  (3, 2, 'Promouvoir la qualité des aménités, des espaces publics et du cadre de vie', 'Action qui participe à l’amélioration du cadre de vie (architectural, urbanistique, paysager…) et à la qualité des services et des équipements qui sont proposés aux habitants et usagers du territoire'),
  (4, 2, 'Favoriser la santé et le bien-être', 'Action qui participe à la préservation de la santé et de la tranquillité publiques (réduction des nuisances sensorielles...)'),
  (5, 3, 'Garantir l’équité sociale et inter-générationnelle', 'Action qui favorise l’accès pour tous aux équipements et services (personnes âgées, handicapées, minorités socioculturelles, égalité homme-femme…)'),
  (6, 3, 'Garantir l’intégration socio-économique', 'Action qui prend en compte le niveau de revenus des habitants et usagers ou propose des mesures particulières pour certains publics plus précaires'),
  (7, 4, 'Encourager la résilience et s’adapter aux effets du changement climatique', 'Action qui intègre une démarche de réduction et/ou d’adaptation face aux risques naturels, technologiques et industriels'),
  (8, 4, 'Assurer la résilience et l’efficience économique du projet', 'Action qui intègre une démarche d’efficience économique (approche en coût global, etc.), notamment en vue d’optimiser les coûts du projet en phase de fonctionnement et pour la maintenance'),
  (10, 5, 'Réduire la pollution', 'Action qui contribue à la prévention et à la réduction des sources de pollution (qualité de l’air, de l’eau, des sols…)'),
  (9, 5, 'Réduire les émissions de gaz à effet de serre et économiser l’énergie', 'Action qui favorise l’efficience énergétique du projet (maîtrise et efficacité énergétiques, recours aux énergies renouvelables et de récupération...) et la réduction des émissions de gaz à effet de serre (GES)'),
  (11, 5, 'Protéger, restaurer et valoriser la biodiversité et les écosystèmes', 'Action qui participe à la préservation, la restauration et la valorisation de la biodiversité et des écosystèmes'),
  (12, 6, 'Maîtriser les ressources foncières', 'Action qui préserve les ressources foncières et participe à limiter l’étalement urbain'),
  (13, 6, 'Gérer les ressources naturelles de façon durable et diminuer la production de déchets', 'Action qui contribue à la préservation des matières premières (en particulier les ressources rares et non renouvelables) et à la réduction et valorisation des déchets (recyclage des déchets, valorisation énergie - matière…)'),
  (14, 6, 'Protéger, préserver et gérer les ressources en eau ', 'Action qui contribue à l’optimisation et à la préservation des ressources en eau (optimisation des consommations, utilisation de sources alternatives à l’eau potable…)');

insert into exp_ingenierie (id, code_couleur, libelle) values
  (1, '#2b2f6b', 'Etudes amonts '),
  (2, '#2b2f6b', 'Maitrise d’œuvre (MOE) '),
  (3, '#2b2f6b', 'Assistance à maitrise d''ouvrage (AMO)'),
  (4, '#2b2f6b', 'Autres missions');

insert into exp_etiquette_ingenierie (id, id_ingenierie, libelle, description) values
  (1, 1, 'Etude de diagnostic', 'Réalisation d’un diagnostic ou d’un audit, qu’il soit multi-thématique ou spécifique'),
  (2, 1, 'Etude prospective et stratégique', 'Réalisation d’une étude prospective et l’élaboration d’une stratégie à échelle élargie (quartier, territoire)'),
  (3, 1, 'Etude de faisabilité', 'Réalisation d’une étude permettant de vérifier la faisabilité technique, la viabilité économique et juridique d’un projet'),
  (4, 2, 'Etude de programmation, définition et conception', 'Réalisation d’une étude permettant de définir un programme (idées directrices, moyens), de construire une proposition de concept, de dessiner de premières orientations d''un projet'),
  (5, 2, 'Etude de développement et dimensionnement', 'Réalisation d’une étude de développement d’un concept dont la faisabilité est avérée et/ou sur la création d’un outil ou système correspondant'),
  (6, 2, 'Etude de modélisation et simulation', 'Réalisation d’une modélisation numérique et/ou de simulations permettant la quantification d’impacts et/ou la comparaison de différents scénarios'),
  (7, 2, 'Mission globale de conception-réalisation-exploitation', 'Réalisation d’une mission globale allant de la conception à la maintenance d’un projet'),
  (8, 3, 'Mission d’AMO ÉcoCité', 'Recours à un Assistant à Maîtrise d’Ouvrage afin que celui-ci apporte à la structure porteuse de l’ÉcoCité une assistance technique pour le suivi de la démarche ÉcoCité'),
  (9, 3, 'Mission d’AMO Développement Durable', 'Recours à un Assistant à Maîtrise d’Ouvrage sur la globalité d’un sujet thématique (AMO Energie, AMO Mobilité, AMO Foncier...)'),
  (10, 3, 'Mission d’AMO spécialisée', 'Recours à un Assistant à Maîtrise d’Ouvrage sur la globalité d’un sujet thématique (AMO Energie, AMO Mobilité, AMO Foncier...)'),
  (11, 4, 'Mission de suivi-évaluation', 'Mission de suivi ou de monitoring des performances, d’évaluation des impacts d’un projet, de traitement de données collectées, de formulation de préconisations ex-post'),
  (12, 4, 'Mission de concertation, d’accompagnement et d’animation', 'Mission d’accompagnement d’un public cible (habitants, techniciens...), mise en place d’un dispositif de concertation particulier, mission d’animation de groupes de travail ou d''ateliers');

-- Ajout des questonnaires
insert into exp_question_questionnaire_evaluation (id,code_questionnaire,code_categorie,ordre,question,type_reponse,reponses,id_question_mere,reponse_attendu) values
  (1,'questionnaire_action_ingenierie','1.1.1',0,'Quel est le besoin qui a motivé la réalisation de l’étude ?','texte_libre',null,null,null),
  (2,'questionnaire_action_ingenierie','1.1.1',10,'Quels étaient les principaux objectifs de l’étude ?','texte_libre',null,null,null),
  (3,'questionnaire_action_ingenierie','1.1.1',20,'Quelle a été la méthodologie de travail mise en place (étapes suivies, expertises mobilisées et acteurs impliqués) ?','texte_libre',null,null,null),
  (4,'questionnaire_action_ingenierie','1.1.2',0,'A quels livrables l’étude a-t- elle permis d’aboutir ? (Rapport écrit, cartographie, base de données, liste de contacts d’acteurs clés, etc.)','texte_libre',null,null,null),
  (5,'questionnaire_action_ingenierie','1.1.2',10,'Quelle a été la plus-value de l’étude : quels éléments n’auraient pas été disponibles sans l’étude (en matière de connaissances techniques, de gouvernance, de dynamique de travail, d’engagement, de perspectives, etc.) ?','texte_libre',null,null,null),
  (6,'questionnaire_action_ingenierie','1.1.2',20,'Quels ont été les résultats et apports les plus innovants de l’étude selon vous ?','texte_libre',null,null,null),
  (7,'questionnaire_action_ingenierie','1.1.3',0,'Lors du lancement ou au cours de la réalisation de l’étude, des difficultés particulières ont-elles été rencontrées en matière de :','checkbox_autre','Gouvernance, mise en œuvre de partenariats, arbitrage et portage politique$$$Accès aux données (données manquantes ou difficiles à obtenir$$$Méthodologie de travail$$$Choix scientifiques et techniques et mise en œuvre de ces choix$$$Calendrier et contraintes temporelles des différents acteurs$$$Relations de confiance et dialogue avec les acteurs$$$Spécificités réglementaires$$$Acceptation et adhésion, notamment citoyennes$$$Modèle économique et financier$$$Montage juridique$$$Spécificités liées au contexte local$$$Incertitudes quant à l’évolution des usages',null,null),
  (8,'questionnaire_action_ingenierie','1.1.3',10,'Comment avez-vous surmonté ces difficultés ?','texte_libre',null,null,null),
  (9,'questionnaire_action_ingenierie','1.1.4',0,'Quels enseignements peuvent être tirés de l’étude sur les méthodes de travail ? Notamment concernant :','checkbox_texte_libre','Les délais de réalisation et les coûts associés :$$$Le besoin d’associer certains acteurs dans une démarche partenariale :$$$Les contraintes des gestionnaires ou des acteurs concernés à prendre en compte :$$$Les possibilités de déroger à la réglementation :',null,null),
  (10,'questionnaire_action_ingenierie','1.1.4',10,'Quels besoins l’étude a-t-elle révélés ? (ex : besoin d’approfondir certains sujets techniques dans le cadre d’une étude similaire, besoins en termes de suivi-évaluation, etc.)','texte_libre',null,null,null ),
  (11,'questionnaire_action_ingenierie','1.1.5',0,'Quelles sont les conditions indispensables pour la reproduction d’une étude sur un autre projet du même type ? Y-a- t-il certains éléments méthodologiques qui dépendent trop fortement du contexte ?','texte_libre',null,null,null),
  (12,'questionnaire_action_ingenierie','1.1.5',10,'Certains éléments méthodologiques sont-ils pertinents pour des projets de nature différente (logiciels, bases de données…) ?','texte_libre',null,null,null),
  (13,'questionnaire_action_ingenierie','1.1.5',20,'Les résultats de l’étude sont-ils applicables ou utilisables dans un autre contexte ou pour un projet différent ?','radio_bouton_oui_non_nonconcerne','Oui$$$Non$$$non concerné',null,null),
  (14,'questionnaire_action_ingenierie','1.1.6',0,'Selon vous, l’étude a-t- elle permis d’atteindre les objectifs visés ?','radio_bouton','1 - Tout à fait d''accord : L’étude a atteint tous les objectifs visés.$$$2 - D''accord : L’étude a atteint presque tous les objectifs visés.$$$3 - Partiellement d''accord : L’étude a atteint une majeure partie des objectifs visés.$$$4 - Pas d''accord : L’étude n’a atteint qu’une partie des objectifs visés.$$$5 - Pas du tout d''accord : L’étude n’a pas atteint les objectifs visés.',null,null),
  (15,'questionnaire_action_ingenierie','1.1.6',10,'Au regard des éléments apportés par l’étude, est ce que les coûts engagés pour la réalisation de l’étude étaient raisonnables selon vous ?','texte_libre',null,null,null),
  (16,'questionnaire_action_ingenierie','1.1.6',15,'Le travail mené à travers cette étude se poursuit-il ?','checkbox_autre','Oui, via une autre action d’ingénierie financée par le PIA VDD : préciser les études dans la zone de texte ci après$$$Oui, via une action d’investissement financée par le PIA VDD : préciser l’action dans la zone de texte ci après$$$Oui, via d’autres études d’approfondissement non financées par la PIA VDD : préciser les études dans la zone de texte ci après$$$Oui, via une mise en œuvre concrète non financée par le PIA VDD : préciser l’action de mise en œuvre dans la zone de texte ci après$$$Non, le projet est abandonné car : préciser les raisons de son arrêt dans la zone de texte ci-après',null,null),
  (100,'questionnaire_action_ingenierie','1.1.6',20,'Ajouter une note de synthèse (facultatif)','file',null,null,null),
  (17,'questionnaire_action_investissement','2.1.1',0,'Le projet vous a-t- il conduit à collaborer avec différents types d''acteurs ?','radio_bouton','Oui$$$Non',null,null),
  (18,'questionnaire_action_investissement','2.1.1',10,'Si oui, quels ont été les partenaires du projet ?','checkbox_texte_libre','Acteurs publics :$$$Acteurs privés :$$$Recherche :$$$Société civile :$$$Autre :',17,'Oui'),
  (19,'questionnaire_action_investissement','2.1.1',20,'Quels ont été les apports du/des partenariat(s) ?','texte_libre',null,null,null),
  (20,'questionnaire_action_investissement','2.1.1',30,'Quelles ont été les éventuelles difficultés rencontrées du fait du/des partenariat(s) lors du montage ou du pilotage du projet (cultures professionnelles ou objectifs différents des partenaires,…) ?','texte_libre',null,17,'Oui'),
  (21,'questionnaire_action_investissement','2.1.1',40,'Est-ce qu’il y a eu des évolutions du partenariat durant le projet (départ ou arrivée de nouveaux partenaires…) ?','radio_bouton_oui_non_nonconcerne','Oui$$$Non$$$non concerné',17,'Oui'),
  (22,'questionnaire_action_investissement','2.1.1',50,'Si oui, pour quelles raisons ?','texte_libre',null,21,'Oui'),
  (23,'questionnaire_action_investissement','2.1.1',60,'Globalement, les partenariats ont-ils été déterminants à la réussite du projet ?','radio_bouton_oui_non_nonconcerne','Oui$$$Non$$$non concerné',null,null),
  (24,'questionnaire_action_investissement','2.1.2',0,'Avez-vous mis en place une démarche de concertation ou de participation citoyenne ?','radio_bouton','Oui$$$Non',null,null),
  (25,'questionnaire_action_investissement','2.1.2',10,'Si oui, de quelle nature ?','checkbox_autre','Réunions de quartier$$$Site web$$$Journée porte ouverte$$$Maison du projet$$$Ligne téléphonique dédiée$$$Enquête de satisfaction',24,'Oui'),
  (26,'questionnaire_action_investissement','2.1.2',20,'D’autres mesures ont-elles été mises en place pour favoriser l''adhésion de tous ?','radio_bouton','Oui$$$Non',null,null),
  (27,'questionnaire_action_investissement','2.1.2',25,'Si oui, de quelle nature ?','checkbox_autre','Prévention$$$Compensation',26,'Oui'),
  (28,'questionnaire_action_investissement','2.1.2',30,'Est-ce que ces mesures en vue de la satisfaction du public ont conduit à une évolution du projet ?','radio_bouton_oui_non_nonconcerne','Oui$$$Non$$$non concerné',null,null),
  (29,'questionnaire_action_investissement','2.1.2',40,'Si oui,sur quel(s) aspect(s) ?','texte_libre',null,28,'Oui'),
  (64,'questionnaire_action_investissement','2.1.2',41,'Si non, pour quelle(s) raison(s) ?','texte_libre',null,28,'Non'),
  (30,'questionnaire_action_investissement','2.1.2',50,'Est-ce que ces mesures étaient essentielles à la réussite de l’action ?','radio_bouton_oui_non_nonconcerne','Oui$$$Non$$$non concerné',null,null),
  (31,'questionnaire_action_investissement','2.1.2',60,'Si oui, lesquelles ?','texte_libre',null,30,'Oui'),
  (32,'questionnaire_action_investissement','2.1.3',0,'La réussite du projet a-t- elle été selon vous favorisée par des particularités du contexte local ?','radio_bouton','Oui$$$Non',null,null),
  (33,'questionnaire_action_investissement','2.1.3',10,'Si oui, de quelle nature ?','checkbox_autre','Climat$$$Topographie$$$Ressources naturelles$$$Spécificités des filières économiques et industrielles locales$$$Tissu social particulier$$$Contexte culturel$$$Caractéristiques locales de gouvernance$$$Infrastructures locales$$$Plan stratégique et règlement locaux',32,'Oui'),
  (34,'questionnaire_action_investissement','2.1.3',20,'Est-ce que le projet aurait pu se faire sans certaines de ces particularités ?','radio_bouton','Oui$$$Non$$$non concerné',null,null),
  (35,'questionnaire_action_investissement','2.1.3',30,'Si non, lesquelles ?','texte_libre',null,34,'Non'),
  (36,'questionnaire_action_investissement','2.1.4',0,'L''action a-t- elle généré ou accompagné une évolution des pratiques de certains usagers ?','radio_bouton','Oui$$$Non',null,null),
  (37,'questionnaire_action_investissement','2.1.4',10,'Quels sont ces usagers ?','texte_libre',null,36,'Oui'),
  (38,'questionnaire_action_investissement','2.1.4',20,'Si oui, quelles ont été les évolutions ?','texte_libre',null,36,'Oui'),
  (39,'questionnaire_action_investissement','2.1.4',30,'Ces évolutions des usages étaient-elles nécessaires pour la réussite de l’action ?','radio_bouton_oui_non_nonconcerne','Oui$$$Non$$$non concerné',null,null),
  (40,'questionnaire_action_investissement','2.1.5',0,'La mise en œuvre de l’action a-t- elle été confrontée à des difficultés réglementaires ?','radio_bouton','Oui$$$Non',null,null),
  (41,'questionnaire_action_investissement','2.1.5',10,'Si oui, de quelle nature ?','texte_libre',null,40,'Oui'),
  (42,'questionnaire_action_investissement','2.1.5',20,'Comment ont-elles été résolues ?','checkbox_autre','Par une modification du projet (pour contourner les obstacles réglementaires)$$$Par des dérogations$$$Par une évolution du cadre réglementaire',40,'Oui'),
  (43,'questionnaire_action_investissement','2.1.5',30,'Si l’action a généré une évolution du cadre réglementaire, de quelle nature est cette évolution ?','checkbox_autre','Simplification de la procédure existante$$$Création d’un précédent',42,'Par une évolution du cadre réglementaire'),
  (44,'questionnaire_action_investissement','2.1.5',40,'Dans quel domaine du droit ?','checkbox_autre','Code de l''environnement$$$Code de l''urbanisme$$$Code du travail$$$Code de la santé',40,'Oui'),
  (45,'questionnaire_action_investissement','2.1.6',0,'Le PIA a-t- il permis de trouver de nouveaux cofinanceurs ?','radio_bouton','Oui$$$Non',null,null),
  (46,'questionnaire_action_investissement','2.1.6',10,'Si oui, lesquels ?','texte_libre',null,45,'Oui'),
  (65,'questionnaire_action_investissement','2.1.6',20,'Quelle est la part du financement du PIA dans le financement total de l’action?','texte_libre',null,null,null),
  (47,'questionnaire_action_investissement','2.1.6',30,'Quelle est la part du financement privé dans le financement total de l’action?','texte_libre',null,null,null),
  (48,'questionnaire_action_investissement','2.1.6',40,'Quelles sont les perspectives de recettes actuelles ?','texte_libre',null,null,null),
  (49,'questionnaire_action_investissement','2.1.6',50,'Quel serait le modèle économique de la reproduction de cette action / de ce type d’action ?','checkbox_autre','Modèle économique classique (économie de marché$$$Modèle économique de service public (subventionné)$$$Modèle économique de contrat de partenariat',null,null),
  (50,'questionnaire_action_investissement','2.1.6',60,'Quels sont les gains à attendre de l’industrialisation du démonstrateur ?','texte_libre',null,null,null),
  (51,'questionnaire_action_investissement','2.1.7',0,'Votre projet a-t- il permis de lever des verrous scientifiques et/ou techniques ?','radio_bouton','Oui$$$Non',null,null),
  (52,'questionnaire_action_investissement','2.1.7',10,'Si oui, lesquels?','texte_libre','Oui$$$Non',51,'Oui'),
  (53,'questionnaire_action_investissement','2.1.7',20,'Votre projet a-t-il fait l’objet d’une valorisation scientifique et technique ?','radio_bouton','Oui$$$Non',null,null),
  (54,'questionnaire_action_investissement','2.1.7',30,'Si oui, sous quelle forme ?','checkbox_autre','Articles scientifiques$$$Dépôt de brevets',53,'Oui'),
  (101,'questionnaire_action_investissement','2.1.7',40,'Ajouter une note de synthèse (facultatif)','file',null,null,null),
  (55,'questionnaire_ecocite','3.1.1',0,'Pour le programme d’actions de votre ÉcoCité, l’accent a-t-il été plutôt mis sur le gain de performance (amélioration des résultats bien au-delà des pratiques courantes) ou sur la recherche d’innovation (émergence de solutions inédites) ?','radio_bouton_autre','Gagner en performance$$$Rechercher l’innovation',null,null),
  (56,'questionnaire_ecocite','3.1.1',10,'Est-ce que votre positionnement serait similaire dans le cadre d’un prochain programme ?','texte_libre',null,null,null),
  (57,'questionnaire_ecocite','3.1.2',20,'Est-ce que la démarche ÉcoCité et le programme Ville de demain vous ont aidé à faire évoluer votre stratégie territoriale ? (Ex : modification des documents d’urbanisme, du PCAET, etc.)','texte_libre',null,null,null),
  (58,'questionnaire_ecocite','3.1.2',30,'La démarche ÉcoCité et le programme Ville de demain vous ont ils permis de faciliter la mise en œuvre de vos stratégies de territoires ?','texte_libre',null,null,null),
  (59,'questionnaire_ecocite','3.1.3',40,'Avez-vous observé la réplication de certaines actions Ville de demain ou l’émergence de nouveaux projets directement inspirés de ces actions ?','texte_libre',null,null,null),
  (60,'questionnaire_ecocite','3.1.3',50,'Identifiez-vous certaines actions Ville de demain qui sont en cours de réplication ou qui pourraient être répliquées à court terme (d’ici 1 à 2 ans maximum) ?','texte_libre',null,null,null),
  (61,'questionnaire_ecocite','3.1.4',0,'Est-ce que la démarche ÉcoCité vous a permis d’améliorer votre fonctionnement interne (décloisonnement et transversalité entre les services, meilleure communication, etc.) ?','texte_libre',null,null,null),
  (62,'questionnaire_ecocite','3.1.4',10,'Est-ce que la démarche ÉcoCité vous a permis d’améliorer votre coopération ou de mettre en place des partenariats avec des services et acteurs externes (publics ou privés) ?','texte_libre',null,null,null),
  (63,'questionnaire_ecocite','3.1.5',20,'Est-ce que vos référents ÉcoCités ou des porteurs d’actions sont intervenus à propos du programme Ville de demain dans des évènements en dehors de ceux liés au réseau ÉcoCité (forums, colloques, conférences, tables rondes, etc.) ?','texte_libre',null,null,null),
  (102,'questionnaire_ecocite','3.1.5',30,'Ajouter une note de synthèse (facultatif)','file',null,null,null);

-- Fusion des deux role admin
UPDATE is_role SET code='ADMIN' WHERE code='ADMIN_EVALUATION';
DELETE FROM is_role  WHERE code='ADMIN_GENERAL';

INSERT INTO is_function (id, code, date_creation, type) VALUES
  (1, 'edit_categorisation_action', '2018-03-21 10:02:38.426000', null),
  (2, 'edit_choix_indi_action', '2018-03-21 10:02:42.327000', null),
  (3, 'edit_evaluation_action', '2018-03-21 10:02:43.502000', null),
  (4, 'edit_mesure_action', '2018-03-21 10:02:49.426000', null),
  (5, 'edit_contexte_action', '2018-03-21 10:02:50.950000', null),
  (6, 'edit_categorisation_ecocite', '2018-03-21 10:02:51.839000', null),
  (7, 'edit_choix_indi_ecocite', '2018-03-21 10:02:52.497000', null),
  (8, 'edit_mesure_ecocite', '2018-03-21 10:02:53.315000', null),
  (9, 'edit_impact_ecocite', '2018-03-21 10:02:53.937000', null),
  (10, 'val_categorisation_action', '2018-03-21 10:02:54.502000', null),
  (11, 'val_choix_indi_action', '2018-03-21 10:02:55.702000', null),
  (12, 'val_evaluation_action', '2018-03-21 10:03:06.660000', null),
  (13, 'val_mesure_action', '2018-03-21 10:03:10.944000', null),
  (14, 'val_contexte_action', '2018-03-21 10:03:13.878000', null),
  (15, 'val_categorisation_ecocite', '2018-03-21 10:03:16.473000', null),
  (16, 'val_choix_indi_ecocite', '2018-03-21 10:03:17.250000', null),
  (17, 'val_mesure_ecocite', '2018-03-21 10:03:18.005000', null),
  (18, 'val_impact_ecocite', '2018-03-21 10:03:18.557000', null),
  (19, 'modif_categorisation_action', '2018-03-21 10:03:19.069000', null),
  (20, 'modif_choix_indi_action', '2018-03-21 10:03:19.670000', null),
  (21, 'modif_evaluation_action', '2018-03-21 10:03:20.317000', null),
  (22, 'modif_mesure_action', '2018-03-21 10:03:20.941000', null),
  (23, 'modif_contexte_action', '2018-03-21 10:03:21.575000', null),
  (24, 'modif_categorisation_ecocite', '2018-03-21 10:03:22.131000', null),
  (25, 'modif_choix_indi_ecocite', '2018-03-21 10:03:22.658000', null),
  (26, 'modif_mesure_ecocite', '2018-03-21 10:03:23.182000', null),
  (27, 'modif_impact_ecocite', '2018-03-21 10:03:23.763000', null),
  (28, 'edit_indicateur', '2018-03-21 10:03:23.763000', null),
  (29, 'creation_indicateur', '2018-03-21 10:03:23.763000', null),
  (30, 'suppression_indicateur', '2018-03-21 10:03:23.763000', null),
  (31, 'edit_contact', '2018-03-21 10:03:23.763000', null),
  (32, 'creation_contact', '2018-03-21 10:03:23.763000', null),
  (33, 'suppression_contact', '2018-03-21 10:03:23.763000', null);

INSERT INTO is_asso_function_role (id, id_function, id_role) VALUES
  (51, 1, 6),
  (52, 2, 6),
  (53, 3, 6),
  (54, 4, 6),
  (55, 5, 6),
  (56, 6, 6),
  (57, 7, 6),
  (58, 8, 6),
  (59, 9, 6),
  (60, 10, 6),
  (61, 11, 6),
  (62, 12, 6),
  (63, 13, 6),
  (64, 14, 6),
  (65, 15, 6),
  (66, 16, 6),
  (67, 17, 6),
  (68, 18, 6),
  (69, 19, 6),
  (70, 20, 6),
  (71, 21, 6),
  (72, 22, 6),
  (73, 23, 6),
  (74, 24, 6),
  (75, 25, 6),
  (76, 26, 6),
  (77, 27, 6),
  (78, 28, 5),
  (79, 29, 5),
  (80, 30, 5),
  (81, 28, 6),
  (82, 29, 6),
  (1, 1, 3),
  (2, 2, 3),
  (3, 3, 3),
  (4, 4, 3),
  (5, 5, 3),
  (6, 1, 4),
  (7, 2, 4),
  (8, 3, 4),
  (9, 4, 4),
  (10, 5, 4),
  (11, 6, 4),
  (12, 7, 4),
  (13, 8, 4),
  (14, 9, 4),
  (15, 10, 4),
  (16, 11, 4),
  (17, 12, 4),
  (19, 14, 4),
  (20, 15, 4),
  (21, 16, 4),
  (22, 17, 4),
  (23, 18, 4),
  (24, 1, 5),
  (25, 2, 5),
  (26, 3, 5),
  (27, 4, 5),
  (28, 5, 5),
  (29, 6, 5),
  (30, 7, 5),
  (31, 8, 5),
  (32, 9, 5),
  (33, 10, 5),
  (34, 11, 5),
  (35, 12, 5),
  (36, 13, 5),
  (37, 14, 5),
  (38, 15, 5),
  (39, 16, 5),
  (40, 17, 5),
  (41, 18, 5),
  (42, 19, 5),
  (43, 20, 5),
  (44, 21, 5),
  (45, 22, 5),
  (46, 23, 5),
  (47, 24, 5),
  (48, 25, 5),
  (49, 26, 5),
  (50, 27, 5),
  (83, 30, 6),
  (84, 31, 5),
  (85, 32, 5),
  (86, 33, 5),
  (87, 31, 6),
  (88, 32, 6),
  (89, 33, 6),
  (90, 32, 2),
  (91, 32, 3),
  (92, 32, 4),
  (93, 28, 3),
  (94, 28, 4),
  (95, 29, 3),
  (96, 19, 4),
  (97, 20, 4),
  (98, 21, 4),
  (99, 22, 4),
  (100, 23, 4),
  (101, 24, 4),
  (102, 25, 4),
  (103, 26, 4),
  (104, 27, 4);


-- Correction git 04/04/2018
INSERT INTO is_function (id, code, date_creation, type) VALUES
  (34, 'edit_presentation_action', '2018-03-21 10:03:23.763000', null),
  (35, 'edit_presentation_ecocite', '2018-03-21 10:03:23.763000', null);

INSERT INTO is_asso_function_role (id, id_function, id_role) VALUES
  (105, 34, 3),
  (106, 34, 4),
  (107, 35, 4),
  (108, 34, 5),
  (109, 35, 5),
  (110, 34, 6),
  (111, 35, 6);
DELETE FROM is_asso_function_role  WHERE id='22';

UPDATE exp_question_questionnaire_evaluation SET type_reponse='checkbox_texte_libre' WHERE id='16';

-- Correction git 05/04/2018
-- un referent ne peut pas valider innovation des actions
DELETE FROM is_asso_function_role  WHERE id='17';

-- 09/04/2018
-- Titres Libellés de la page d'accueil en fo
INSERT INTO exp_libelle_fo (id, texte, code) VALUES
  (1,'Avec la démarche ÉcoCité, l’Etat accompagne la transition écologique des grandes villes françaises','TITRE_PRINCIPAL'),
  (2,'Les ÉcoCités mettent en oeuvre de grands projets d’aménagement durable pour répondre aux enjeux du changement climatique. L’Etat encourage les stratégies territoriales ambitieuses et l’innovation pour soutenir la croissance et l’attractivité des villes en assurant le bien être des habitants.','SOUS_TITRE_PRINCIPAL'),
  (3,'LES ÉCOCITÉS','TITRE_ECOCITE'),
  (4,'31 territoires bénéficient aujourd''hui de l''accompagnement de l''État et du soutien financier du programme<br/>d''investissements d''avenir Ville de demain géré par la Caisse des Dépôts.','SOUS_TITRE_ECOCITE'),
  (5,'LES ACTIONS','TITRE_ACTION'),
  (6,'Les programmes d’actions portés par les ÉcoCités s’inscrivent dans une stratégie territoriale intégrée<br/>qui articule les thématiques du développement durable.','SOUS_TITRE_ACTION'),
  (7,'Ministère de la Cohésion des territoires','TITRE_MINISTERE'),
  (8,'© Toutes les images du site et notamment les icônes et autres visuels sont protégés par des copyrights','COPYRIGHT'),
  (9,'http://www.cohesion-territoires.gouv.fr/ecocites-et-ville-de-demain-31-territoires-soutenus-par-l-etat','LIEN_MINISTERE');

-- 11/04/2018
-- Un référent ÉcoCité peut ajouter un Indicateur
INSERT INTO is_asso_function_role (id, id_function, id_role) VALUES (112, 29, 4);

--20/04/2018
--Un porteur d'action peut valider son questionnaire.
INSERT INTO is_asso_function_role (id, id_function, id_role) VALUES (113, 14, 2);

--27/04/2018
--revu de la note de synthése
UPDATE exp_question_questionnaire_evaluation SET code_categorie='1.1.7', question = '' WHERE id=100;
UPDATE exp_question_questionnaire_evaluation SET code_categorie='2.1.8', question = '' WHERE id=101;
UPDATE exp_question_questionnaire_evaluation SET code_categorie='3.1.6', question = '' WHERE id=102;


UPDATE exp_question_questionnaire_evaluation
SET
  question = 'Vous pouvez importer ici une note de synthèse ou tout autre document disponible (rapport d''évaluation, rapport d''audit...) pour compléter votre évaluation qualitative. Ce document ne sera téléchargeable que par vous, votre référent EcoCité ou les acteurs institutionnels de l''EcoCité (CDC, DREAL...) et les accompagnateurs et administrateurs.'
WHERE id IN (100, 101, 102);


-- 03/05/2018
-- Fix du nom de role administrateur pour correspondre à cerbere
UPDATE is_role SET code='ADMINISTRATEUR' WHERE code='ADMIN';


-- 24/05/2018
-- Set des sirens des régions
UPDATE exp_region SET siren = '200053767' WHERE id = 1;
UPDATE exp_region SET siren = '233500016' WHERE id = 2;
UPDATE exp_region SET siren = '200052264' WHERE id = 3;
UPDATE exp_region SET siren = '200053742' WHERE id = 4;
UPDATE exp_region SET siren = '237500079' WHERE id = 5;
UPDATE exp_region SET siren = '239740012' WHERE id = 6;
UPDATE exp_region SET siren = '200053403' WHERE id = 7;
UPDATE exp_region SET siren = '200053759' WHERE id = 8;
UPDATE exp_region SET siren = '200053791' WHERE id = 9;
UPDATE exp_region SET siren = '234400034' WHERE id = 10;
UPDATE exp_region SET siren = '231300021' WHERE id = 11;

INSERT into exp_region(id, nom, shortname, siren) VALUES
  (12, 'Bourgogne-Franche-Comté', '', '200053726'),
  (13, 'Centre-Val-de-Loire', '', '234500023'),
  (14, 'Corse', '', '232000018'),
  (15, 'Guadeloupe', '', '239710015'),
  (16, 'Guyane', '', '239730013'),
  (17, 'Martinique', '', '200055507'),
  (18, 'Mayotte', '', '229850003');


UPDATE exp_question_questionnaire_evaluation SET type_reponse = 'radio_bouton', reponses = 'Oui$$$Non$$$non concerné' WHERE ID = 30;
UPDATE exp_question_questionnaire_evaluation SET type_reponse = 'radio_bouton', reponses = 'Oui$$$Non$$$non concerné' WHERE ID = 28;
insert into exp_question_questionnaire_evaluation (id,code_questionnaire,code_categorie,ordre,question,type_reponse,reponses,id_question_mere,reponse_attendu) values
  (103,'questionnaire_action_investissement','2.1.2',60,'Si non, lesquelles ?','texte_libre',null,30,'Non');

update exp_axe set libelle = 'Energies et réseaux' where id = 4;

UPDATE exp_action set etat_avancement='realise' WHERE etat_avancement='termine';

UPDATE exp_question_questionnaire_evaluation SET reponses = 'Oui, via une autre action d’ingénierie financée par le PIA VDD : préciser les études dans la zone de texte ci après$$$Oui, via une action d’investissement financée par le PIA VDD : préciser l’action dans la zone de texte ci après$$$Oui, via d’autres études d’approfondissement non financées par la PIA VDD : préciser les études dans la zone de texte ci après$$$Oui, via une mise en œuvre concrète non financée par le PIA VDD : préciser l’action de mise en œuvre dans la zone de texte ci après$$$Non, le projet n''a pas donné lieu, pour l''instant, à des approfondissements ou à une mise en œuvre concrète. Préciser les raisons dans la zone de texte ci-après' WHERE id = 16;

UPDATE exp_question_questionnaire_evaluation
SET
  question = 'Vous pouvez importer ici une note de synthèse ou tout autre document disponible (rapport d''évaluation, rapport d''audit...) pour compléter votre évaluation qualitative. Ce document ne sera téléchargeable que par vous, votre référent ÉcoCité ou les acteurs institutionnels de l''ÉcoCité (CDC, DREAL...) et les accompagnateurs et administrateurs.'
WHERE id IN (100, 101, 102);

DELETE FROM exp_libelle_fo WHERE code = 'LIEN_MINISTERE';
UPDATE exp_libelle_fo SET description = 'Titre principal' WHERE code = 'TITRE_PRINCIPAL';
UPDATE exp_libelle_fo SET texte = '<p>Avec la d&eacute;marche &Eacute;coCit&eacute;, l&rsquo;Etat accompagne la transition &eacute;cologique des grandes villes fran&ccedil;aises</p>' WHERE code = 'TITRE_PRINCIPAL';
UPDATE exp_libelle_fo SET description = 'Sous titre' WHERE code = 'SOUS_TITRE_PRINCIPAL';
UPDATE exp_libelle_fo SET texte = '<p>Les &Eacute;coCit&eacute;s mettent en oeuvre de grands projets d&rsquo;am&eacute;nagement durable pour r&eacute;pondre aux enjeux du changement climatique. L&rsquo;Etat encourage les strat&eacute;gies territoriales ambitieuses et l&rsquo;innovation pour soutenir la croissance et l&rsquo;attractivit&eacute; des villes en assurant le bien &ecirc;tre des habitants.</p>' WHERE code = 'SOUS_TITRE_PRINCIPAL';
UPDATE exp_libelle_fo SET description = 'Titre partie carte' WHERE code = 'TITRE_ECOCITE';
UPDATE exp_libelle_fo SET texte = '<p>LES &Eacute;COCIT&Eacute;S</p>' WHERE code = 'TITRE_ECOCITE';
UPDATE exp_libelle_fo SET description = 'Sous titre partie carte' WHERE code = 'SOUS_TITRE_ECOCITE';
UPDATE exp_libelle_fo SET texte = '<p>31 territoires b&eacute;n&eacute;ficient aujourd&#39;hui de l&#39;accompagnement de l&#39;&Eacute;tat et du soutien financier du programme<br />d&#39;investissements d&#39;avenir Ville de demain g&eacute;r&eacute; par la Caisse des D&eacute;p&ocirc;ts.</p>' WHERE code = 'SOUS_TITRE_ECOCITE';
UPDATE exp_libelle_fo SET description = 'Titre partie Action' WHERE code = 'TITRE_ACTION';
UPDATE exp_libelle_fo SET texte = '<p>LES ACTIONS</p>' WHERE code = 'TITRE_ACTION';
UPDATE exp_libelle_fo SET description = 'Sous titre partie Action' WHERE code = 'SOUS_TITRE_ACTION';
UPDATE exp_libelle_fo SET texte = '<p>Les programmes d&rsquo;actions port&eacute;s par les &Eacute;coCit&eacute;s s&rsquo;inscrivent dans une strat&eacute;gie territoriale int&eacute;gr&eacute;e<br />qui articule les th&eacute;matiques du d&eacute;veloppement durable.</p>' WHERE code = 'SOUS_TITRE_ACTION';
UPDATE exp_libelle_fo SET description = 'Lien vers le site du ministère' WHERE code = 'TITRE_MINISTERE';
UPDATE exp_libelle_fo SET texte = '<p><a href="Ministère de la Cohésion des territoires">http://www.cohesion-territoires.gouv.fr/ecocites-et-ville-de-demain-31-territoires-soutenus-par-l-etat</a></p>' WHERE code = 'TITRE_MINISTERE';
UPDATE exp_libelle_fo SET description = 'Copyright' WHERE code = 'COPYRIGHT';
UPDATE exp_libelle_fo SET texte = '<p>&copy; Toutes les images du site et notamment les ic&ocirc;nes et autres visuels sont prot&eacute;g&eacute;s par des copyrights</p>' WHERE code = 'COPYRIGHT';
UPDATE exp_libelle_fo set texte = '<a href="Minist&egrave;re de la Coh&eacute;sion des territoires">http://www.cohesion-territoires.gouv.fr/ecocites-et-ville-de-demain-31-territoires-soutenus-par-l-etat</a>' WHERE code = 'TITRE_MINISTERE';


INSERT INTO exp_libelle_fo(id, code, texte, description, type) VALUES
(16, 'JAVASCRIPT_ANALYTICS', '(function (i, s, o, g, r, a, m) {
		i[''GoogleAnalyticsObject''] = r;
		i[r] = i[r] || function () {
			(i[r].q = i[r].q || []).push(arguments)
		}, i[r].l = 1 * new Date();
		a = s.createElement(o),
			m = s.getElementsByTagName(o)[0];
		a.async = 1;
		a.src = g;
		m.parentNode.insertBefore(a, m)
	})(window, document, ''script'', ''//www.google-analytics.com/analytics.js'', ''ga'');
	ga(''create'', ''UA-50447765-1'', ''explorateur-ecocite.fr'');
	ga(''send'', ''pageview'');', 'Tracking Google Analytics', 'fo_static');


INSERT INTO exp_libelle_fo(id, code, texte, description, type) VALUES
(10, 'EM_TITRE_SECTION', '', 'Le titre de la section Écocité du mois (non affiché si vide)', 'fo_dynamic'),
(11, 'EM_DESCRIPTION_SECTION', '', 'Le nom de l''Écocité du mois et sa description (si vide on affichera la valeur calculée)', 'fo_dynamic'),
(12, 'EM_MONTANT_PIA_VDD', '', 'Le montant financier du PIA VDD (non affiché si vide)', 'fo_dynamic'),
(13, 'EM_NOMBRE_ACTION_VISIBLE', '', 'Le nombre d''action/innovation (si vide on affichera la valeur calculée)', 'fo_dynamic'),
(14, 'EM_NOMBRE_ACTION_REALISE', '', 'Le nombre d''action réalisé (si vide on affichera la valeur calculée)', 'fo_dynamic'),
(15, 'EM_NOMBRE_ACTION_EVALUE', '', 'Le nombre d''action évalué (si vide on affichera la valeur calculée)', 'fo_dynamic');

INSERT INTO exp_libelle_fo(id, code, texte, description, type) VALUES
(16, 'JAVASCRIPT_ANALYTICS', '(function (i, s, o, g, r, a, m) {
		i[''GoogleAnalyticsObject''] = r;
		i[r] = i[r] || function () {
			(i[r].q = i[r].q || []).push(arguments)
		}, i[r].l = 1 * new Date();
		a = s.createElement(o),
			m = s.getElementsByTagName(o)[0];
		a.async = 1;
		a.src = g;
		m.parentNode.insertBefore(a, m)
	})(window, document, ''script'', ''//www.google-analytics.com/analytics.js'', ''ga'');
	ga(''create'', ''UA-50447765-1'', ''explorateur-ecocite.fr'');
	ga(''send'', ''pageview'');', 'Tracking Google Analytics', 'fo_static')

INSERT INTO exp_libelle_fo(id, code, texte, description, type) VALUES
(17, 'EM_NOMBRE_ECOCITE', '', 'Le nombre d''Écocité visibles (si vide on affichera la valeur calculée)', 'fo_dynamic');
