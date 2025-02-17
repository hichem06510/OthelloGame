# Othello Game Project

Ce projet implémente le jeu Othello avec une architecture basée sur Java et Gradle.

## Prérequis

Avant de commencer, assurez-vous d'avoir installé les éléments suivants sur votre machine :

- **Java JDK** (version 22 ou supérieure) :
  - [Télécharger ici](https://www.oracle.com/java/technologies/javase-downloads.html)
- **Gradle** (version 8.0 ou supérieure) :
  - [Télécharger ici](https://gradle.org/releases/)
- **Git** (optionnel, pour cloner le dépôt) :
  - [Télécharger ici](https://git-scm.com/)

## Structure du projet

La structure principale du projet est organisée comme suit :

- `build.gradle` : Fichier de configuration Gradle contenant les dépendances et tâches.
- `settings.gradle` : Fichier de configuration des modules Gradle.
- `src/main/java` : Contient le code source principal, y compris les classes du jeu Othello, les contrôleurs et les vues.
  - `fr/univ_amu/m1info/board_game_library/` :
    - **othello/** : Contient les classes principales liées au jeu Othello (logique, grille, joueurs, etc.).
    - **graphics/** : Gère l'interface graphique (JavaFX).
    - **command/** : Implémente le modèle de commande pour les actions du jeu.
- `src/test/java` : Contient les tests unitaires organisés par module.
- `gradlew` et `gradlew.bat` : Scripts pour exécuter Gradle sans installation locale.
- `gradle/wrapper` : Contient les fichiers du wrapper Gradle (fichier JAR et propriétés).
- `src/main/resources` : Contient les ressources comme les icônes utilisées dans l'interface graphique.

## Installation

1. **Cloner le dépôt (optionnel)** :
   Si vous avez accès au dépôt Git, vous pouvez le cloner directement :
   ```bash
   git clone <URL_DU_DEPOT>
   cd othello-game-34
   ```

2. **Vérifier l'installation de Java et Gradle** :
   Assurez-vous que Java et Gradle sont installés correctement en exécutant les commandes suivantes :
   ```bash
   java -version
   gradle -v
   ```

3. **Construire le projet** :
   Téléchargez les dépendances et compilez le projet avec la commande suivante :
   ```bash
   ./gradlew build
   ```

## Exécution de l'application

Pour lancer le jeu, suivez ces étapes :

1. Naviguez vers le répertoire racine du projet.
2. Exécutez la commande suivante :
   ```bash
   ./gradlew run
   ```

   Sous Windows, utilisez :
   ```cmd
   gradlew.bat run
   ```

3. L'application démarrera et affichera l'interface utilisateur ou la console pour jouer au jeu Othello.

## Exécution des tests

Le projet inclut des tests unitaires pour vérifier les fonctionnalités principales. Pour les exécuter :

1. Lancez les tests en utilisant la commande suivante :
   ```bash
   ./gradlew test
   ```

2. Les rapports de tests sont générés dans le fichier suivant :
   ```
   build/reports/tests/test/index.html
   ```
   Vous pouvez l'ouvrir dans un navigateur pour une vue détaillée.

## Dépannage

Voici quelques solutions aux problèmes courants :

- **Problème de version Java** :
  - Assurez-vous que la variable d'environnement `JAVA_HOME` pointe vers la version correcte de Java.
  - Exemple pour Linux/Mac :
    ```bash
    export JAVA_HOME=/chemin/vers/java
    export PATH=$JAVA_HOME/bin:$PATH
    ```
- **Problème avec Gradle** :
  - Supprimez le cache local Gradle et reconstruisez le projet :
    ```bash
    rm -rf .gradle
    ./gradlew build
    ```
- **Dépendances manquantes** :
  - Vérifiez que votre connexion Internet fonctionne pour permettre à Gradle de télécharger les dépendances.

## Contribution

Pour contribuer à ce projet :

1. **Forkez** le projet sur votre propre compte GitHub.
2. **Créez une branche** pour vos modifications.
   ```bash
   git checkout -b nouvelle-fonctionnalite
   ```
3. Apportez vos modifications et effectuez des commits clairs :
   ```bash
   git commit -m "Ajout d'une nouvelle fonctionnalité"
   ```
4. **Soumettez une Pull Request** pour révision.

## Auteurs

Ce projet a été développé par **Oussema**, **Yassine**, **Issam** et **Hichem**.



---

Merci d'utiliser ce projet. Bon jeu !
