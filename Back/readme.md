# Installation

Installer Maven et Java dans les variable d'environnement :  
Java :  
https://docs.oracle.com/javase/10/install/installation-jdk-and-jre-microsoft-windows-platforms.htm#JSJIG-GUID-A7E27B90-A28D-4237-9383-A58B416071CA  

Maven :  
https://docs.wso2.com/display/IS323/Installing+Apache+Maven+on+Windows  

Sonar :
mvn sonar:sonar -Dsonar.host.url=URL_SONAR

Vous pouvez lancer un serveur sonar avec docker : 
`docker run -d --name sonarqube -p 9000:9000 sonarqube`


Dans intelliJ, définir les variable d'environnement du projet :
DATABASE_HOST=192.168.99.100
DATABASE_PORT=5432
DATABASE_NAME=compareTout
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=compareTout@postgres

Dans intelliJ, installer Lombok sur IntelliJ :
	File > Settings > Plugins
Chercher Lombok et l'installer


Télécharger et installer docker en version .exe:
https://github.com/docker/toolbox/releases

docker ligne de commande :
Aller dans la racine du projet  


    docker-compose up -d


http://localhost:8080/users
Pour tester si ca fonctionne correctement



Pour programmer avec Hibernate :  
--------------------------------

Vous pouvez utiliser DBeaver afin de visualiser les tables en temps réel  :
DBeaver   
--------
Télécharger et installer DBeaver:  
https://dbeaver.io/download/  

Créer une connexion postgreSQL et indiquer les élements suivants :  
Host : 192.168.99.100 (à utiliser avec docker en service)  
Port : 5432   
Database : compareTout  
User : postgres  
Password : compareTout@postgres


Exécuter le Back dans intelliJ.
  - S'il y a des erreurs lors de la compilation  :
   - Aller dans le dossier src/main/resources depuis l'ide.
   - Ouvrir le fichier application.properties et remplacer le ou les champs en "create" par "update".
   - Recompiler et c'est bon.

Dans DBeaver, les tables se trouvent à :  
 Schemas > public > Tables
