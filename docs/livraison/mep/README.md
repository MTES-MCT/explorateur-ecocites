# Mise En Prod

## Configuration application.properties

Rajouter les clés suivantes dans **application.properties**:

* `efficacity.explorateurecocites.basepath`
Il prends la valeurs d'un chemin racine dans lequel le serveur stockeras tous ses fichiers.
* `efficacity.explorateurecocites.email.accompagnateur`
C'est l'adresse mail Accompagnateur à laquelle on envoie les mails de notification.
* `efficacity.explorateurecocites.email.expediteur`
C'est l'adresse mail expeditrice des mails de notif et des prises de contact.

## Initialisation de la base de données
Il faut ajouter les fichiers suivant dans l'ordre

### Fichiers de schéma SQL

- `1 - isotope.sql`
- `2 - mep.sql`

### Fichiers de données SQL

- `3 - mep_data.sql`
- `4 - ecocites.sql`
- `5 - actions.sql`
- `6 - affaires.sql`
- `7 - indicateurs.sql`
- `8 - asso_etiquette_indicateur.sql`
- `9 - contacts.sql`
- `10 - etapes_actions.sql`
- `11 - etapes_ecocites.sql`
