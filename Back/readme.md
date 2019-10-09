# Installation 

Installer Maven et Java dans les variable d'environnement :  
Java :  
https://docs.oracle.com/javase/10/install/installation-jdk-and-jre-microsoft-windows-platforms.htm#JSJIG-GUID-A7E27B90-A28D-4237-9383-A58B416071CA  

Maven :  
https://docs.wso2.com/display/IS323/Installing+Apache+Maven+on+Windows  

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

