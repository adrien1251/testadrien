
# Git

Afin d'avoir une norme commune pour la gestion de git, il faut suivre les quelques règles suivantes:

## Avant de travailler

Avant chaque utilisation de git, il faut pull votre branche `git pull --rebase`


Si conflit il y a, il faudra alors le régler, une fois réglé faire `git rebase --continue` et ensuite vous pouvez coder.

## Pour travailler  

Pour travailler sur une feature nommé `X`:

- Il faut créer une nouvelle branche `git branch feature/X`

Pour travailler sur un gros bug nommé `X`:

- Il faut créer une nouvelle branche `git branch fix/X`

## Après avoir travailler

Une fois la feature/fix `X` fini:

- Il faut merge la branche `[feature/fix]/X` sur la branche `develop`  
```
git checkout develop
git merge feature/X
git push
```

## Le sprint est finit?

Une fois le sprint finit et validé par le client, il faudra alors merge la branche `develop` sur la branche `master` et résoudre les conflits
```
git checkout master
git merge develop
```

## La norme pour tout nos commit

-   **feat**  Travail sur une feature 
-   **fix**  Résolution d'un bug
-   **docs**  Changement sur la documentation
-   **style**  Un changement dans le code qui n'influence en aucun cas le run de celui-ci
-   **refactor**  Refactoring du code
-   **test**  Ajout de test au code
-   **chore** Toutes les tâches fastidieuse qui ne change pas le run du code