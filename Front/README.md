1.Installation NodeJS
--------------------
(version 10.9.0 ou plus)
https://nodejs.org/en/


2 Installation Angular CLI
--------------------
(permet de créer des composants de l'application très facilement et rapidement) :
	Saisir dans un terminal :
<pre>npm install -g @angular/cli</pre>


3 Démarrer l'application
--------------------
se rendre dans le dossier Front/compare-tout

de là, taper dans un terminal :
<pre>npm install</pre>

Cela permet d'installer tous les modules et dépendances nécessaire.
Si cette commande retourne des erreurs, il est possible qu'il faille redémarrer le PC.
Si après le redémarrage, la commande renvoie encore des erreurs, saisir la commande :
<pre>npm login</pre>
Puis relancer <pre>npm install</pre>

Puis une fois terminée, saisir <pre>ng serve</pre> ou <pre>npm start</pre>
Cela va lancer l'appli sur le port 4200

Il suffit ensuite de se rendre sur un navigateur et saisir l'adresse localhost:4200 pour voir l'application

4 Test de l'application 
-----------------------
Afin de vérifier que l'application tourne bien et communique bien avec le back,
Vous pouvez vous rendre à l'URL localhost:4200/test
Vous devriez voir les noms suivants s'afficher : 

Adrien - Deblock
Lukas - Ramus

5 Valider le build
------------------
Pour vérifier le build de l'application, Saisir : 
<pre>npm run validation</pre>
Cela lancera la commande ng lint (qui vérifie la syntaxe du code), puis la commande ng build

Présentation d'Angular
======================