CREATE TABLE exp_utilisateur (
  email VARCHAR(255),
  nom VARCHAR(255),
  prenom  VARCHAR(255),
  date_premiere_connexion date,
  date_derniere_connexion date,
  PRIMARY KEY(email)
);

CREATE TABLE exp_axe (
  id bigint,
  libelle VARCHAR(255),
  code_couleur1  VARCHAR(255),
  code_couleur2  VARCHAR(255),
  icone VARCHAR(255),
  user_modification VARCHAR(255),
  date_modification date,
  PRIMARY KEY(id)
);

CREATE TABLE exp_ingenierie (
  id BIGINT NOT NULL PRIMARY KEY,
  libelle VARCHAR(255),
  code_couleur VARCHAR(100)
);

CREATE TABLE exp_finalite (
  id BIGINT NOT NULL PRIMARY KEY,
  libelle VARCHAR(255),
  code_couleur VARCHAR(100)
);

CREATE TABLE exp_ecocite (
  id bigint,
  nom VARCHAR(255),
  PRIMARY KEY(id)
);
CREATE INDEX idx_exp_ecocite_nom ON exp_ecocite (nom);

CREATE TABLE exp_action (
  id bigint,
  id_ecocite bigint REFERENCES exp_ecocite (id),
  id_axe bigint REFERENCES exp_axe (id),
  id_axe_principale bigint REFERENCES exp_axe (id),
  id_ingenierie BIGINT REFERENCES exp_ingenierie (id),
  id_finalite BIGINT REFERENCES exp_finalite (id),
  nom_public VARCHAR(255),
  numero_action VARCHAR(20),
  date_debut date,
  date_fin date,
  latitude DECIMAL(17,3),
  longitude DECIMAL(17,3),
  description TEXT,
  lien VARCHAR(255),
  echelle VARCHAR(255),
  etat_avancement VARCHAR(255),
  type_financement VARCHAR(255),
  etat_publication VARCHAR(255),
  tranche_execution VARCHAR(255),
  PRIMARY KEY (id)
);
CREATE INDEX fk_exp_action_ecocite ON exp_action(id_ecocite);
CREATE INDEX fk_exp_action_axe ON exp_action(id_axe);
CREATE INDEX fk_exp_action_axe_principale ON exp_action(id_axe_principale);
CREATE INDEX fk_exp_action_ingenierie ON exp_action(id_ingenierie);
CREATE INDEX fk_exp_action_finalite ON exp_action(id_finalite);

CREATE TABLE exp_etape (
  id bigint,
  id_action bigint REFERENCES exp_action (id),
  nom VARCHAR(255),
  statut VARCHAR(255),
  commentaire VARCHAR(255),
  PRIMARY KEY (id)
);
CREATE INDEX fk_exp_etape_action ON exp_etape(id_action);
CREATE UNIQUE INDEX unq_exp_etape_action_etape ON exp_etape(id_action, nom);

CREATE TABLE exp_business (
  id bigint,
  id_action bigint REFERENCES exp_action (id),
  nom VARCHAR(255),
  PRIMARY KEY (id)
);
CREATE INDEX fk_exp_business_action ON exp_business(id_action);

CREATE TABLE exp_contact (
  id bigint,
  id_action bigint REFERENCES exp_action (id),
  nom VARCHAR(255),
  prenom VARCHAR(255),
  type_contact VARCHAR(100),
  PRIMARY KEY (id)
);
CREATE INDEX fk_exp_contact_action ON exp_contact(id_action);

CREATE TABLE exp_document (
  id bigint,
  id_action bigint REFERENCES exp_action (id),
  type_document VARCHAR(100),
  PRIMARY KEY (id)
);
CREATE INDEX fk_exp_document_action ON exp_document(id_action);

CREATE TABLE exp_etiquette_ingenierie (
  id BIGINT PRIMARY KEY,
  libelle VARCHAR(255),
  id_ingenierie BIGINT REFERENCES exp_ingenierie (id)
);
CREATE INDEX fk_etiquette_ingenierie_ingenierie ON exp_etiquette_ingenierie(id_ingenierie);

CREATE TABLE exp_etiquette_finalite (
  id BIGINT PRIMARY KEY,
  libelle VARCHAR(255),
  id_finalite BIGINT REFERENCES exp_finalite (id)
);
CREATE INDEX fk_etiquette_finalite_finalite ON exp_etiquette_finalite(id_finalite);

CREATE TABLE exp_etiquette_axe (
  id BIGINT PRIMARY KEY,
  libelle VARCHAR(255),
  id_axe BIGINT REFERENCES exp_axe (id)
);
CREATE INDEX fk_etiquette_axe_axe ON exp_etiquette_axe(id_axe);

CREATE TABLE exp_asso_action_ingenierie (
  id BIGINT PRIMARY KEY,
  id_action BIGINT REFERENCES exp_action,
  id_etiquette_ingenierie BIGINT REFERENCES exp_etiquette_ingenierie (id),
  poid INTEGER NOT NULL DEFAULT 0
);
CREATE INDEX fk_asso_action_ingenierie_action ON exp_asso_action_ingenierie(id_action);
CREATE INDEX fk_asso_action_ingenierie_etiquette_ingenierie ON exp_asso_action_ingenierie(id_etiquette_ingenierie);
CREATE UNIQUE INDEX unq_exp_asso_action_ingenierie_action_ingenierie ON exp_asso_action_ingenierie(id_action, id_etiquette_ingenierie);

CREATE TABLE exp_asso_action_domain (
  id BIGINT PRIMARY KEY,
  id_action BIGINT REFERENCES exp_action,
  id_domain BIGINT REFERENCES exp_etiquette_axe (id),
  poid INTEGER NOT NULL DEFAULT 0
);
CREATE INDEX fk_asso_action_domain_action ON exp_asso_action_domain(id_action);
CREATE INDEX fk_asso_action_domain_domain ON exp_asso_action_domain(id_domain);
CREATE UNIQUE INDEX unq_exp_asso_action_domain_action_domain ON exp_asso_action_domain(id_action, id_domain);

CREATE TABLE exp_asso_action_objectif (
  id BIGINT PRIMARY KEY,
  id_action BIGINT REFERENCES exp_action,
  id_objectif BIGINT REFERENCES exp_etiquette_finalite (id),
  poid INTEGER NOT NULL DEFAULT 0
);
CREATE INDEX fk_exp_asso_action_objectif_action ON exp_asso_action_objectif(id_action);
CREATE INDEX fk_exp_asso_action_objectif_objectif ON exp_asso_action_objectif(id_objectif);
CREATE UNIQUE INDEX unq_exp_asso_action_objectif_action_objectif ON exp_asso_action_objectif(id_action, id_objectif);

CREATE TABLE exp_indicateur (
  id BIGINT PRIMARY KEY,
  nom VARCHAR(255),
  nom_court VARCHAR(255),
  echelle VARCHAR(255),
  nature VARCHAR(255),
  perimetre_geographique VARCHAR(255),
  periodicite VARCHAR(255),
  origine VARCHAR(255),
  type_reponse_attendue VARCHAR(255),
  description TEXT,
  methode_calcule TEXT,
  source_donnees VARCHAR(255),
  poste_calcule TEXT,
  unite TEXT
);

CREATE TABLE exp_asso_indicateur_objectif (
  id BIGINT PRIMARY KEY,
  id_indicateur BIGINT REFERENCES exp_indicateur,
  id_objectif BIGINT REFERENCES exp_etiquette_ingenierie (id)
);

CREATE INDEX fk_exp_asso_indicateur_objectif_indicateur ON exp_asso_indicateur_objectif(id_indicateur);
CREATE INDEX fk_exp_asso_indicateur_objectif_objectif ON exp_asso_indicateur_objectif(id_objectif);

CREATE TABLE exp_asso_indicateur_domaine (
  id BIGINT PRIMARY KEY,
  id_indicateur BIGINT REFERENCES exp_indicateur,
  id_domaine BIGINT REFERENCES exp_etiquette_axe (id)
);
CREATE INDEX fk_exp_asso_indicateur_domaine_indicateur ON exp_asso_indicateur_domaine(id_indicateur);
CREATE INDEX fk_exp_asso_indicateur_domaine_domaine ON exp_asso_indicateur_domaine(id_domaine);

CREATE TABLE exp_asso_indicateur_action (
  id BIGINT PRIMARY KEY,
  id_indicateur BIGINT REFERENCES exp_indicateur,
  poste_calcule VARCHAR(255),
  unite VARCHAR(255),
  id_action BIGINT REFERENCES exp_action (id)
);
CREATE INDEX fk_exp_asso_indicateur_action_indicateur ON exp_asso_indicateur_action(id_indicateur);
CREATE INDEX fk_exp_asso_indicateur_action_action ON exp_asso_indicateur_action(id_action);

CREATE TABLE exp_region (
  id bigint,
  nom VARCHAR(255),
  shortname VARCHAR(255),
  PRIMARY KEY(id)
);

ALTER TABLE exp_ecocite
  ADD shortname VARCHAR(255);
ALTER TABLE exp_ecocite
  ADD id_region BIGINT;

CREATE TABLE exp_mesure_indicateur (
  id bigint,
  id_asso_indicateur_action BIGINT,
  date_saisie date,
  valeur VARCHAR(255),
  valide boolean,
  PRIMARY KEY(id)
);
CREATE INDEX fk_exp_asso_indicateur_action_mesure ON exp_mesure_indicateur(id_asso_indicateur_action);

CREATE TABLE exp_cible_indicateur (
  id bigint,
  id_asso_indicateur_action BIGINT,
  date_saisie date,
  valeur VARCHAR(255),
  valide boolean,
  PRIMARY KEY(id)
);
CREATE INDEX fk_exp_asso_indicateur_action_cible ON exp_cible_indicateur(id_asso_indicateur_action);

CREATE TABLE exp_questions_evaluation (
  id BIGINT PRIMARY KEY,
  titre VARCHAR(255),
  description VARCHAR(1024)
);

CREATE TABLE exp_reponses_evaluation (
  id BIGINT PRIMARY KEY,
  niveau INTEGER,
  id_question BIGINT REFERENCES exp_questions_evaluation,
  id_action BIGINT REFERENCES exp_action,

  unique (id_action, id_question)
);

CREATE INDEX fk_exp_asso_reponses_evaluation_questions_evaluation ON exp_reponses_evaluation(id_question);
CREATE INDEX fk_exp_asso_reponses_evaluation_action ON exp_reponses_evaluation(id_action);

-- Ne pas oublier de remplir le champ par la valeur 0 dans
ALTER TABLE exp_action ADD COLUMN evaluation_niveau_global INT DEFAULT 0;

-- On rajoute les commentaires de l'étape de saisie des mesures des indicateur
ALTER TABLE exp_asso_indicateur_action ADD COLUMN commentaire_mesure TEXT;
ALTER TABLE exp_asso_indicateur_action ADD COLUMN commentaire_cible TEXT;

-- V00.00.17

alter table exp_etiquette_finalite add COLUMN description text;
alter table exp_etiquette_axe add COLUMN description text;
alter table exp_etiquette_ingenierie add COLUMN description text;

-- On clean les ligne de 'exp_etape' car on doit ajouter une colonne necessaire à l'affichage
DELETE FROM exp_etape;
ALTER TABLE exp_etape ADD COLUMN type_objet VARCHAR(50);
ALTER TABLE exp_etape RENAME COLUMN  nom TO code_etape;
ALTER TABLE exp_etape RENAME COLUMN  id_action TO id_objet;
DROP INDEX unq_exp_etape_action_etape RESTRICT;
CREATE UNIQUE INDEX unq_exp_etape_objet_etape ON exp_etape(id_objet, code_etape,type_objet);
ALTER TABLE exp_etape DROP CONSTRAINT exp_etape_id_action_fkey;

-- On clean les ligne de 'exp_asso_action_objectif' car on doit ajouter une colonne necessaire à l'affichage
DELETE FROM exp_asso_action_objectif;
ALTER TABLE exp_asso_action_objectif RENAME COLUMN  id_action TO id_objet;
ALTER TABLE exp_asso_action_objectif ADD COLUMN type_objet VARCHAR(50);
ALTER TABLE exp_asso_action_objectif RENAME TO exp_asso_objet_objectif;
ALTER TABLE exp_asso_objet_objectif DROP CONSTRAINT exp_asso_action_objectif_id_action_fkey;
CREATE INDEX fk_exp_asso_action_objectif_objet ON exp_asso_objet_objectif(id_objet);
DROP INDEX unq_exp_asso_action_objectif_action_objectif RESTRICT;
DROP INDEX fk_exp_asso_action_objectif_action RESTRICT;
CREATE UNIQUE INDEX unq_exp_asso_objet_objectif_objet_objectif ON exp_asso_objet_objectif(id_objet, id_objectif, type_objet);

-- On clean les ligne de 'exp_asso_indicateur_action' car on doit ajouter une colonne necessaire à l'affichage
DELETE FROM exp_asso_indicateur_action;
ALTER TABLE exp_asso_indicateur_action DROP CONSTRAINT exp_asso_indicateur_action_id_action_fkey;
ALTER TABLE exp_asso_indicateur_action RENAME COLUMN  id_action TO id_objet;
ALTER TABLE exp_asso_indicateur_action ADD COLUMN type_objet VARCHAR(50);
ALTER TABLE exp_asso_indicateur_action RENAME TO exp_asso_indicateur_objet;

ALTER TABLE exp_cible_indicateur RENAME COLUMN  id_asso_indicateur_action TO id_asso_indicateur_objet;
ALTER TABLE exp_mesure_indicateur RENAME COLUMN  id_asso_indicateur_action TO id_asso_indicateur_objet;

-- Debut ajout colonne Ecocite
ALTER TABLE exp_ecocite ADD COLUMN id_finalite BIGINT REFERENCES exp_finalite (id);

ALTER TABLE exp_etape ADD COLUMN date_validee DATE;
ALTER TABLE exp_etape ADD COLUMN validee_par BIGINT;
ALTER TABLE exp_etape ADD CONSTRAINT fk_validee_par FOREIGN KEY (validee_par) REFERENCES "user"(id);

alter table exp_ecocite add COLUMN desc_strategie text;
alter table exp_ecocite add COLUMN desc_perimetre text;
alter table exp_ecocite add COLUMN porteur VARCHAR(255);
alter table exp_ecocite add COLUMN partenaire VARCHAR(255);
alter table exp_ecocite add COLUMN annee_adhesion VARCHAR(4);
alter table exp_ecocite add COLUMN soutien_pia_detail VARCHAR(255);
alter table exp_ecocite add COLUMN soutien_pia DECIMAL(17,3);
alter table exp_ecocite add COLUMN nb_communes INTEGER;
alter table exp_ecocite add COLUMN nb_habitants INTEGER;
alter table exp_ecocite add COLUMN superficie_km2 DECIMAL(17,3);
alter table exp_ecocite add COLUMN lien VARCHAR(255);
alter table exp_ecocite add COLUMN latitude DECIMAL(17,3);
alter table exp_ecocite add COLUMN longitude DECIMAL(17,3);
alter table exp_ecocite add COLUMN etat_publication VARCHAR(255);

alter table exp_ecocite ALTER COLUMN latitude SET DATA TYPE VARCHAR(255);
alter table exp_ecocite ALTER COLUMN longitude SET DATA TYPE VARCHAR(255);
alter table exp_action ALTER COLUMN latitude SET DATA TYPE VARCHAR(255);
alter table exp_action ALTER COLUMN longitude SET DATA TYPE VARCHAR(255);

alter table exp_indicateur RENAME COLUMN  type_reponse_attendue TO type_mesure;
alter table exp_indicateur add COLUMN etat_validation VARCHAR(255);
alter table exp_indicateur add COLUMN contact_createur VARCHAR(255);
alter table exp_indicateur add COLUMN etat_bibliotheque VARCHAR(255);
alter table exp_indicateur DROP COLUMN perimetre_geographique;
alter table exp_indicateur DROP COLUMN periodicite;

alter table exp_ecocite ALTER COLUMN superficie_km2 SET DATA TYPE INTEGER;

drop TABLE if EXISTS exp_business;
CREATE TABLE exp_business (
  id bigint,
  nom VARCHAR(255),
  numero_affaire VARCHAR(10),
  numero_operation VARCHAR(10),
  nom_operation VARCHAR(255),
  id_action bigint REFERENCES exp_action (id),
  tranche VARCHAR(255),
  ecocite VARCHAR(255),
  axe VARCHAR(255),
  type_financement VARCHAR(255),
  statut_financier VARCHAR(255),
  statut_abandon BOOLEAN,
  date_debut date,
  date_fin date,
  PRIMARY KEY (id)
);
CREATE INDEX fk_exp_business_action ON exp_business(id_action);

-- Ajout des questions pour les deux questionnaires d'évaluation des ecococites et des actions
CREATE TABLE exp_question_questionnaire_evaluation(
  id bigint PRIMARY KEY,
  code_questionnaire varchar(255),
  code_categorie varchar(255),
  ordre integer,
  question text,
  type_reponse VARCHAR(255),
  reponses text,
  si_oui BOOLEAN,
  libelle_si_oui text
);
-- FIN - Ajout des questions pour les deux questionnaires d'évaluation des ecococites et des actions

alter table exp_question_questionnaire_evaluation DROP COLUMN si_oui;
alter table exp_question_questionnaire_evaluation DROP COLUMN libelle_si_oui;
alter table exp_question_questionnaire_evaluation ADD COLUMN id_question_mere bigint;
alter table exp_question_questionnaire_evaluation ADD COLUMN reponse_attendu VARCHAR(255);


-- Fix une foreign key qui pointe sur la mauvaise table
DROP INDEX fk_exp_asso_indicateur_objectif_objectif;
ALTER TABLE exp_asso_indicateur_objectif DROP CONSTRAINT exp_asso_indicateur_objectif_id_objectif_fkey;
ALTER TABLE exp_asso_indicateur_objectif ADD CONSTRAINT exp_asso_indicateur_objectif_id_objectif_fkey FOREIGN KEY (id_objectif) REFERENCES exp_etiquette_finalite(id);
CREATE INDEX fk_exp_asso_indicateur_objectif_objectif ON exp_asso_indicateur_objectif(id_objectif);

CREATE TABLE exp_reponses_questionnaire_evaluation(
  id bigint PRIMARY KEY,
  id_question bigint REFERENCES exp_question_questionnaire_evaluation (id),
  id_objet bigint,
  type_objet VARCHAR(50),
  reponse_principale text,
  reponse_secondaire text
);

-- Ajout de la colonne Maitrise d'ouvrage sur action

ALTER TABLE exp_action ADD COLUMN maitrise_ouvrage VARCHAR(255);


-- Gestion de l'upload de fichier, document est une ancienne table dont nous n'avons plus besoin

DROP TABLE exp_document;

CREATE TABLE exp_file_upload (
  id BIGINT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  type VARCHAR(255) NOT NULL,
  id_object BIGINT,
  type_object VARCHAR(255)
);

ALTER TABLE exp_file_upload ADD COLUMN original_name VARCHAR(255);
ALTER TABLE exp_file_upload ADD COLUMN title VARCHAR(255);
ALTER TABLE exp_file_upload ADD CONSTRAINT uniq_exp_file_upload_name UNIQUE (name);

ALTER TABLE exp_indicateur ALTER COLUMN source_donnees SET DATA TYPE Text;


ALTER TABLE exp_ecocite ALTER COLUMN partenaire SET DATA TYPE Text;

-- Ajout des fichiers sur les axes
ALTER TABLE exp_axe ADD COLUMN icon_file BIGINT;
ALTER TABLE exp_axe ADD CONSTRAINT exp_axe_id_icon_file_fkey FOREIGN KEY (icon_file) REFERENCES exp_axe(id);

-- Gestions des contacts
DROP TABLE IF EXISTS exp_contact;
CREATE TABLE exp_contact (
  id BIGINT PRIMARY KEY,
  nom VARCHAR(255) NOT NULL,
  prenom VARCHAR(255) NOT NULL,
  entite VARCHAR(255) NOT NULL,
  id_ecocite BIGINT REFERENCES exp_ecocite(id),
  fonction VARCHAR(255),
  telephone VARCHAR(255),
  email VARCHAR(255) NOT NULL
);

CREATE TABLE exp_asso_objet_contact (
  id BIGINT PRIMARY KEY,
  id_objet BIGINT,
  type_objet VARCHAR(255),
  id_contact BIGINT REFERENCES exp_contact (id),
  importance INTEGER,
  unique (id_objet, type_objet, id_contact)
);

-- Fin de la v00.00.39

ALTER TABLE exp_action ADD COLUMN user_modification VARCHAR(255);
ALTER TABLE exp_action ADD COLUMN date_modification TIMESTAMP;
ALTER TABLE exp_action ADD COLUMN user_creation VARCHAR(255);
ALTER TABLE exp_action ADD COLUMN date_creation TIMESTAMP;

ALTER TABLE exp_ecocite ADD COLUMN user_modification VARCHAR(255);
ALTER TABLE exp_ecocite ADD COLUMN date_modification TIMESTAMP;
ALTER TABLE exp_ecocite ADD COLUMN user_creation VARCHAR(255);
ALTER TABLE exp_ecocite ADD COLUMN date_creation TIMESTAMP;

ALTER TABLE exp_indicateur DROP COLUMN contact_createur;
ALTER TABLE exp_indicateur ADD COLUMN user_modification VARCHAR(255);
ALTER TABLE exp_indicateur ADD COLUMN date_modification TIMESTAMP;
ALTER TABLE exp_indicateur ADD COLUMN user_creation VARCHAR(255);
ALTER TABLE exp_indicateur ADD COLUMN date_creation TIMESTAMP;

ALTER TABLE exp_business ADD COLUMN user_modification VARCHAR(255);
ALTER TABLE exp_business ADD COLUMN date_modification TIMESTAMP;
ALTER TABLE exp_business ADD COLUMN user_creation VARCHAR(255);
ALTER TABLE exp_business ADD COLUMN date_creation TIMESTAMP;

ALTER TABLE exp_contact ADD COLUMN user_modification VARCHAR(255);
ALTER TABLE exp_contact ADD COLUMN date_modification TIMESTAMP;
ALTER TABLE exp_contact ADD COLUMN user_creation VARCHAR(255);
ALTER TABLE exp_contact ADD COLUMN date_creation TIMESTAMP;


-- Ajout de la table qui stockera les objet (action et ecocité) accécible a un user
CREATE TABLE exp_user_objet_right (
  id BIGINT PRIMARY KEY,
  id_user BIGINT,
  id_objet BIGINT,
  type_objet VARCHAR(50),
  code_role VARCHAR(50)
);
CREATE INDEX exp_user_objet_right_id_objet_index ON exp_user_objet_right(id_objet);
ALTER TABLE exp_user_objet_right ADD CONSTRAINT exp_user_objet_right_user_fkey FOREIGN KEY (id_user) REFERENCES "user" (id);


-- Gestions des prises de contacts
DROP TABLE IF EXISTS exp_prise_contact;
CREATE TABLE exp_prise_contact (
  id BIGINT PRIMARY KEY,
  email VARCHAR(255) NOT NULL,
  message VARCHAR(255) NOT NULL,
  date_redaction TIMESTAMP,
  date_envoie TIMESTAMP
);

-- FIN de v00.00.41


ALTER TABLE exp_action DROP COLUMN id_axe_principale;
ALTER TABLE exp_action DROP COLUMN id_ingenierie;
ALTER TABLE exp_action DROP COLUMN id_finalite;
ALTER TABLE exp_ecocite DROP COLUMN id_finalite;

-- #########################################################
-- #########################################################
--    Livraison Initiale - 04/04/2018
-- #########################################################
-- #########################################################

-- Libelles du FO Administrables
DROP TABLE IF EXISTS exp_libelle_fo;
CREATE TABLE exp_libelle_fo (
  id BIGINT PRIMARY KEY,
  code VARCHAR(255) NOT NULL,
  texte TEXT NOT NULL
);

-- Modification contenu des maitrise d'ouvrage/partenaire
-- 10/04//2018

alter table exp_ecocite ALTER COLUMN partenaire SET DATA TYPE TEXT;
alter table exp_action ALTER COLUMN maitrise_ouvrage SET DATA TYPE TEXT;
-- from date to TIMESTAMP
ALTER TABLE exp_utilisateur ALTER COLUMN date_premiere_connexion TYPE TIMESTAMP;
ALTER TABLE exp_utilisateur ALTER COLUMN date_derniere_connexion TYPE TIMESTAMP;
ALTER TABLE exp_axe ALTER COLUMN date_modification TYPE TIMESTAMP;
ALTER TABLE exp_action ALTER COLUMN date_debut TYPE TIMESTAMP;
ALTER TABLE exp_action ALTER COLUMN date_fin TYPE TIMESTAMP;
ALTER TABLE exp_mesure_indicateur ALTER COLUMN date_saisie TYPE TIMESTAMP;
ALTER TABLE exp_cible_indicateur ALTER COLUMN date_saisie TYPE TIMESTAMP;
ALTER TABLE exp_etape ALTER COLUMN date_validee TYPE TIMESTAMP;
ALTER TABLE exp_business ALTER COLUMN date_debut TYPE TIMESTAMP;
ALTER TABLE exp_business ALTER COLUMN date_fin TYPE TIMESTAMP;

-- Fin de v00.02.07

-- 11/04/2018
ALTER TABLE exp_ecocite DROP COLUMN soutien_pia;

-- 18/04/2018
ALTER TABLE exp_mesure_indicateur ADD COLUMN date_creation TIMESTAMP DEFAULT NOW() NOT NULL;

-- Fin de v00.02.12


-- 30/04/2018
CREATE TABLE exp_user_detail_log (
  id_user BIGINT PRIMARY KEY REFERENCES "user" (id),
  responseCerbere TEXT,
  --   date_modification TIMESTAMP DEFAULT NOW(),
  date_creation TIMESTAMP DEFAULT NOW()
);

-- Les logins cerbère peuvent être plus long que des login usuel
ALTER TABLE "user" ALTER COLUMN login TYPE VARCHAR(255);
ALTER TABLE "user" ALTER COLUMN login SET NOT NULL;

ALTER TABLE exp_region ADD COLUMN siren VARCHAR(16);
ALTER TABLE exp_ecocite ADD COLUMN siren VARCHAR(16);
ALTER TABLE exp_action ADD COLUMN siren VARCHAR(16);



-- #########################################################
-- #########################################################
--    Livraison CERBERE - 30/04/2018
-- #########################################################
-- #########################################################

ALTER TABLE exp_region ADD CONSTRAINT exp_region_unique_siren UNIQUE (siren);
ALTER TABLE exp_ecocite ADD CONSTRAINT exp_ecocite_unique_siren UNIQUE (siren);

-- Retirer les SIRENs dans les actions qui sont identifiées par leurs numéros
ALTER TABLE exp_action DROP COLUMN siren;

-- #########################################################
-- #########################################################
--    Livraison CERBERE - Bouchon et prod
-- #########################################################
-- #########################################################

-- Ajout d'infomatation concernant l'upload d'image
ALTER TABLE exp_file_upload ADD COLUMN legende TEXT;
ALTER TABLE exp_file_upload ADD COLUMN lieu TEXT;
ALTER TABLE exp_file_upload ADD COLUMN description TEXT;
ALTER TABLE exp_file_upload ADD COLUMN copyright TEXT;
ALTER TABLE exp_file_upload ADD COLUMN autorisationPresse boolean;
ALTER TABLE exp_file_upload ADD COLUMN autorisationRevue boolean;
ALTER TABLE exp_file_upload ADD COLUMN autorisationExpo boolean;
ALTER TABLE exp_file_upload ADD COLUMN autorisationInternet boolean;
ALTER TABLE exp_file_upload ADD COLUMN autorisationSiteEE boolean;
ALTER TABLE exp_file_upload ADD COLUMN autorisationSupportMM boolean;
ALTER TABLE exp_file_upload ADD COLUMN numerisation bigint DEFAULT -1;
ALTER TABLE exp_file_upload ADD COLUMN dateUpload TIMESTAMP;

-- Modification du type du message dans la table de prise de contact (14/05)
ALTER TABLE exp_prise_contact ALTER COLUMN message TYPE TEXT;

-- FIN - Tag V00.02.16

-- Ajout de la bibliothèque AJARIS

DROP TABLE IF EXISTS exp_media_modification;
CREATE TABLE exp_media_modification (
  id BIGINT PRIMARY KEY,
  status VARCHAR(64) NOT NULL,
  last_modified TIMESTAMP NOT NULL,
  type_object VARCHAR(32) NOT NULL,
  id_object BIGINT NOT NULL
);

CREATE UNIQUE INDEX unq_exp_media_modification ON exp_media_modification(type_object, id_object);

DROP TABLE IF EXISTS exp_media;
CREATE TABLE exp_media (
  id BIGINT PRIMARY KEY,
  type_object VARCHAR(32) NOT NULL,
  id_object BIGINT NOT NULL,
  title VARCHAR(255),
  level INTEGER,
  numero INTEGER,
  id_ajaris INTEGER
);

CREATE UNIQUE INDEX unq_exp_media ON exp_media(id_ajaris);

-- #########################################################
-- #########################################################
--    TERRA
-- #########################################################
-- #########################################################
INSERT INTO is_role (id, code, date_creation) VALUES (8, 'ADMIN_TECH', '2018-10-30');

-- #########################################################
--    EXP_295
-- #########################################################

-- Suppression de la limite de taille des commentaires
ALTER TABLE exp_etape ALTER COLUMN commentaire TYPE text;


-- #########################################################
--    AMELS 01/2019
-- #########################################################

-- Gestion de la modification des labels labels.
ALTER TABLE exp_libelle_fo ADD COLUMN date_modification TIMESTAMP NOT NULL DEFAULT NOW();
ALTER TABLE exp_libelle_fo ADD COLUMN user_modification VARCHAR(255) NOT NULL DEFAULT 'inconnu';
ALTER TABLE exp_libelle_fo ADD COLUMN description VARCHAR(255) NOT NULL default '';
UPDATE exp_libelle_fo SET description = code;

-- Gestion des dates de modifications de tous les objets.
ALTER TABLE exp_action ADD COLUMN  date_modification_fo TIMESTAMP NOT NULL DEFAULT NOW();
ALTER TABLE exp_action ADD COLUMN  user_modification_fo VARCHAR(255)  NOT NULL DEFAULT 'inconnu';
UPDATE exp_action SET date_modification_fo = date_modification WHERE date_modification IS NOT NULL;
UPDATE exp_action SET user_modification_fo = user_modification WHERE user_modification IS NOT NULL;

-- Gestion de la suppression/clonages des images Ajaris des actions
ALTER TABLE exp_media_modification ADD COLUMN type_modification VARCHAR(255) NOT NULL DEFAULT 'UPDATE';
ALTER TABLE exp_media_modification ADD COLUMN target_id BIGINT;
ALTER TABLE exp_media_modification ADD COLUMN target_type VARCHAR(255);

-- Suppresion de la contrainte de exp_media_modification afin de permetttre au clone d'action de fonctionner
ALTER TABLE exp_media_modification DROP CONSTRAINT exp_media_modification_pkey;
DROP INDEX unq_exp_media_modification;

-- Limit the number of retry on failed Ajaris synchronisation
ALTER TABLE exp_media_modification ADD COLUMN number_try INTEGER DEFAULT 0;

ALTER TABLE exp_libelle_fo ADD COLUMN type VARCHAR(255) DEFAULT 'fo_static' NOT NULL;
