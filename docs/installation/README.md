# Installation

## Configuration maven

S'assurer d'avoir ajouter le serveur artifactory d'ipsosenso dans sa configuration Maven.

Par défault le fichier settings est disponible aux emplacements suivants : 
- **Windows** : `C:\Users\[USERNAME]\.m2\settings.xml`
- **Linux** : `$HOME\.m2\settings.xml`

S'assurer que Intellij utilise bien ce fichier de configuration:

Aller dans le menu `Settings > Build, Execution, Deployement > Build Tools > Maven`, puis vérifier que le champ `User settings file` a le bon chemin comme valeur, et que la case `Override` en fin de ligne est coché

## Génération du fichier WAR de production

Le fichier WAR de production embarque la vraie version du filtre CERBERE, par conséquent il faut embarquer une dépendance différente de lorsque l'on build le projet normalement.

Pour se faire nous avons défini dans le pom.xml deux profils:
- **dev** : Ce profil est activé par défault, il contient une dépendance sur la version bouchon de cerbere-filtre.
- **production** : Ce profil est désactivé par défault, il contient une dépendance sur la vraie version de cerbere-filtre.

Pour générer le WAR de production nous utilisons donc la commande suivante :

`mvn package -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -P production,!dev`

Afin de faciliter la génération du WAR, il est conseillé de rajouter une configuration de lancement maven avec les paramètres suivants:
- **Command line** : `package -Dmaven.test.skip=true -Dmaven.javadoc.skip=true`
- **Profiles (separated with spaces)** : `production !dev`
