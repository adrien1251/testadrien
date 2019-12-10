# compareTout

Le projet ce compose en deux parties, 
la partie Front qui va gérer toute l'affichage du projet,
la partie Back qui gère toutes la partie filtrage.

## Le projet 

Ce projet est entièrement dockerizer et peu donc être lancer à l'aide de la commande `docker-compose up -d`

## Back

Le back ce situe dans le dossier Back du projet. 

C'est un back spring boot qui ce lance grâce à la commande : `mvn spring-boot:run`.

Plusieurs variables d'environnements doivent être définit pour le lancer : 
```
DATABASE_HOST
DATABASE_PORT
DATABASE_NAME
DATABASE_USERNAME
DATABASE_PASSWORD
```

Celui-ci contient un Sonar qui peu être lancer avec la commande : `mvn sonar:sonar -Dsonar.host.url=URL_SONAR`

Un Dockerfile est disponible car nous l'avons dockerizer

## Front

Le front ce situe dans le dossier Front/compare-tout

C'est un front Angular qui peu être lancer à l'aide des commandes `npm install && npm start`.