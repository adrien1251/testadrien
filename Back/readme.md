# Installation

Maven & Java :
==============
Installer Maven et Java dans les variable d'environnement


IntelliJ :
========

Dans intelliJ, définir les variable d'environnement du projet. Pour se faire il faut :  
- Cliquer sur "Edit Configurations" qui se trouve dans le menu déroulant en haut à droite de la fenêtre.
- Puis dans l'onglet "Configuration", ouvrir "Environnement" et ajouter les variables ci-dessous :  
  * DATABASE_HOST = 192.168.99.100  
 * DATABASE_PORT = 5432  
 * DATABASE_NAME = compareTout  
 * DATABASE_USERNAME = postgres  
 * DATABASE_PASSWORD = compareTout@postgres

Dans intelliJ, installer Lombok sur IntelliJ (outil générant automatiquement les getter et setter lors de la compilation afin d'alleger le code):  
 - File > Settings > Plugins  
 - Chercher Lombok et l'installer, il vous sera demandé de redémarrer l'ide.  


Docker :  
======

Télécharger et installer docker en version .exe:  
https://github.com/docker/toolbox/releases

Une fois installer, lancer le "Docker Quickstart Terminal" que vous venez d'installer.   
Laissez le s'installer entièrement puis aller dans le répertoire à la racine du projet depuis ce même terminal. Puis éxecuter la commande si-dessous.

    docker-compose up -d  

Il est possible de vérifier que Docker se soit correctement lancer en éxecutant la commande ci-dessous. On peut voir depuis combien de temps docker est lancé.  

    docker ps


Vérification Installation :  
---------------------------

Pour cela, il faut executer l'application depuis intelliJ via le triangle vert qui se trouve en haut à droite de la fenetre (il est nécessaire que Docker soit en cours d'éxecution). Puis rendez-vous à l'adresse suivante :  
http://localhost:8080/users
