# 🃏 Jest - Jeu de Cartes en Java

<div align="center">

![Java](https://img.shields.io/badge/Java-21-orange?style=flat&logo=java)
![Maven](https://img.shields.io/badge/Maven-3.8+-blue?style=flat&logo=apachemaven)
![Last Commit](https://img.shields.io/github/last-commit/Bleumms/Game-Jest-in-Java-LO02-Project-?style=flat&logo=git)

*Un jeu de cartes stratégique développé dans le cadre du projet LO02*

[Caractéristiques](#-caractéristiques) • [Installation](#-installation) • [Utilisation](#-utilisation) • [Architecture](#-architecture) • [Règles du Jeu](#-règles-du-jeu)

</div>

---

## Table des Matières

- [À Propos](#-à-propos)
- [Caractéristiques](#-caractéristiques)
- [Prérequis](#-prérequis)
- [Installation](#-installation)
- [Utilisation](#-utilisation)
- [Règles du Jeu](#-règles-du-jeu)
- [Architecture](#-architecture)
- [Patterns de Conception](#-patterns-de-conception)
- [Contributeurs](#-contributeurs)

---

## À Propos

**Jest** est un jeu de cartes stratégique développé en Java dans le cadre du projet LO02. Le jeu oppose 3 ou 4 joueurs (humains ou virtuels) dans une bataille tactique où chaque carte a une valeur qui dépend de règles complexes et interdépendantes.

Le projet met en œuvre plusieurs concepts avancés de programmation orientée objet :
- Héritage et polymorphisme
- Patterns de conception (Visitor, Strategy)
- Sérialisation et persistance
- Architecture modulaire et extensible
- Interface graphique (Swing)

---

## Caractéristiques

### 🎯 Gameplay
- **3-4 joueurs** : Parties multijoueurs avec joueurs humains et virtuels
- **Système de points dynamique** : 5 règles qui modifient la valeur des cartes
- **Cartes trophées** : Objectifs variés à accomplir pour gagner
- **Stratégies joueurs virtuels** : Deux niveaux de difficulté (Random et Intelligent)

### 🔧 Fonctionnalités Techniques
- **Sauvegarde/Chargement** : Reprenez vos parties à tout moment
- **Interface console** : Interaction claire et intuitive
- **Interface graphique** : Swing avec pattern MVC
- **Architecture modulaire** : Ajout facile de nouvelles règles et stratégies
- **Jeux personnalisables** : Mode "MINI" (9 cartes) et "TOUT" (17 cartes), susceptible d'y avoir des ajouts

### 🧩 Composants du Jeu
- **17 cartes** : 4 couleurs (♠️ Pique, ♣️ Trèfle, ♦️ Carreau, ♥️ Cœur) × 4 valeurs (1-4) + 1 Jocker
- **5 règles de score** : Cœur, Carreau, As, Double Noir, Jocker
- **Conditions de victoire variées** : Score maximum, plus de cartes d'une valeur, min/max d'une couleur

---

## 🔧 Prérequis

| Composant | Version minimale |
|-----------|-----------------|
| Java JDK  | 11+            |
| Maven     | 3.6+           |
| OS        | Windows/Linux/macOS |

---

## Installation

### 1️⃣ Cloner le dépôt

```bash
git clone https://github.com/Bleumms/Game-Jest-in-Java-LO02-Project-.git
cd Game-Jest-in-Java-LO02-Project-
```

### 2️⃣ Compiler le projet

```bash
mvn clean install
```

### 3️⃣ Générer la documentation Javadoc

```bash
mvn javadoc:javadoc
# La documentation sera dans target/site/apidocs/
```

---

## Utilisation

### Lancer le jeu

```bash
mvn exec:java -Dexec.mainClass="Jest.Main"
```

Ou avec Java directement :

```bash
java -cp target/classes Jest.Main
```

### Déroulement d'une partie

1. **Choix du mode** : Nouvelle partie ou reprendre une sauvegarde
2. **Configuration** :
   - Sélection du jeu (variantes disponibles)
   - Ajout ou non d'extention au jeu
   - Nombre de joueurs (3 ou 4)
   - Type de joueurs (Réel ou Virtuel + stratégie)
3. **Phase de jeu** :
   - Distribution de 2 cartes par joueur
   - Chaque joueur choisit une carte visible et une cachée
   - Tour par tour, les joueurs volent des cartes aux autres
   - Le joueur avec la carte visible la plus haute joue en premier
   - La carte récupéré est ajouté à la collection du joueur
   - Les cartes non prises retournent à la pioche
   - Un nouveau tour commence jusqu'à épuisement de la pioche
4. **Fin de partie** :
   - Attribution des trophées selon leurs conditions
   - Calcul des scores finaux
   - Annonce du vainqueur

---

## Règles du Jeu

### Valeur de Base
Chaque carte a une valeur égale à son numéro.

### Les 5 Règles Modificatrices

#### ♥️ Règle Cœur
- **Sans Jocker** : Les Cœurs valent 0 point
- **Avec Jocker (pas tous les Cœurs)** : Les Cœurs valent leur valeur négative
- **Avec Jocker + tous les Cœurs** : Les Cœurs valent leur valeur positive

#### ♦️ Règle Carreau
- Tous les Carreaux valent leur valeur négative

#### 🃏 Règle As (carte numéro 1)
- **As seul de sa couleur** : Vaut 5× sa valeur (soit 5 points)
- **As avec d'autres cartes de sa couleur** : Vaut 1 point

#### ♠️♣️ Règle Double Noir
- Si vous avez un Pique ET un Trèfle du même numéro : +1 bonus pour chaque carte

#### 🤡 Règle Jocker
- **Sans Cœur** : Le Jocker vaut 4 points
- **Avec au moins un Cœur** : Le Jocker vaut 0 point

### Conditions de Victoire des Trophées

Exemples de conditions :
- Joueur avec le score maximum (avec/sans Jocker)
- Joueur avec le plus de cartes d'une valeur donnée
- Joueur avec la carte la plus haute/basse d'une couleur
- Joueur possédant le Jocker

---

## Architecture

### Structure du Projet

```
src/Jest/
├── 📁 Model/        
│   ├── Carte.java         
│   ├── CarteClassique.java
│   ├── Jocker.java        
│   │
│   ├── Joueur.java        
│   ├── JoueurPhysique.java
│   ├── JoueurVirtuel.java  
│   │
│   ├── Strategie.java   
│   ├── StrategieRandom.java
│   ├── StrategieIntelligent.java
│   │
│   ├── Regle.java         
│   ├── RegleCoeur.java 
│   ├── RegleCarreau.java  
│   ├── RegleAs.java      
│   ├── RegleDoubleNoir.java 
│   ├── RegleJocker.java  
│   │
│   ├── ConditionVictoire.java
│   ├── ConditionMaxScore.java
│   ├── ConditionMaxMinSymbole.java
│   ├── ConditonPlusCarteValeur.java
│   ├── ConditionJocker.java
│   │
│   ├── Jeu.java   
│   ├── Partie.java     
│   ├── Reference.java   
│   ├── CalculateurScore.java 
│   ├── ValeurParCarte.java  
│   ├── Menu.java   
│   │
│   ├── Visitor.java     
│   ├── Visitable.java
│   │
│   ├── Symbole.java
│   ├── EtatJoueur.java
│   ├── EtatPartie.java
│   └── EtatMenu.java
│
├── 📁 Vue/    
│   ├── MenuPrincipal.java
│   ├── MenuCreerPartie.java
│   ├── MenuAjoutJoueurs.java
│   ├── TestPartie.java
│   ├── FaireUneOffre.java
│   └── AffichageTour.java
│
└── 📁 Controler/   
    ├── MenuDebutControler.java
    ├── MenuCreerControler.java
    ├── MenuJoueursControler.java
    ├── PartieControler.java
    ├── JoueurControler.java
    └── ChoisiCarteControler.java
```

### Diagrammes de Classes Simplifiés

```
┌─────────────┐
│   Partie    │
├─────────────┤
│ - joueurs   │◇────────┐
│ - jeu       │         │
│ - trophe    │         │
└─────────────┘         │
                        │
        ┌───────────────┴───────────┐
        │                           │
    ┌───▼────┐               ┌──────▼──────┐
    │  Jeu   │               │   Joueur    │
    ├────────┤               ├─────────────┤
    │-cartes │               │-collection  │
    │-ref    │───────┐       │-score       │
    └────────┘       │       └──────┬──────┘
                     │              │
              ┌──────▼──────┐       │
              │  Reference  │   ┌───▼────────────┐
              ├─────────────┤   │                │
              │  -regles[]  │   │  ┌──────────┐  │
              └─────────────┘   │  │Physique  │  │
                                │  └──────────┘  │
                                │  ┌──────────┐  │
                                │  │ Virtuel  │  │
                                │  │+strategie│  │
                                │  └──────────┘  │
                                └────────────────┘
```
┌─────────────────────────────────────────────┐
│                   VUE                       │
│  (MenuPrincipal, TestPartie, FaireUneOffre) │
│              Observer Pattern               │
└──────────────────┬──────────────────────────┘
                   │ update()
                   │
      ┌────────────▼───────────────┐
      │      CONTRÔLEUR            │
      │  (ActionListener)          │
      │  MenuControler             │
      │  PartieControler           │
      │  JoueurControler           │
      └────────────┬───────────────┘
                   │ commandes
                   │
      ┌────────────▼───────────────┐
      │        MODÈLE              │
      │  (Observable)              │
      │  Partie, Menu, Joueur      │
      │  Jeu, Carte, Regle         │
      └────────────────────────────┘
---

## Patterns de Conception

### 1. **Visitor Pattern** 
*Pour le calcul des scores*

```java
// CalculateurScore visite chaque joueur
public class CalculateurScore implements Visitor {
    public void visit(Joueur player) {
        int score = calculScore(player.getCollection());
        player.setScore(score);
    }
}
```

**Avantages** :
- ✅ Séparation des responsabilités
- ✅ Facile d'ajouter de nouveaux visiteurs
- ✅ Pas de modification des classes Joueur

### 2. **Strategy Pattern**
*Pour les Joueurs Virtuels*

```java
public interface Strategie {
    int executeFaireUneOffre(...);
    List<Integer> executeChoisirUneCarte(...);
}
```

**Implémentations** :
- `StrategieRandom` : Choix aléatoires
- `StrategieIntelligent` : Analyse des cartes et priorités

**Avantages** :
- ✅ Changement de stratégie à la volée
- ✅ Ajout facile de nouvelles Stratégies
- ✅ Code des joueurs simplifié

### 3. **Observer Pattern**
*Communication Vue-Modèle*

```
public class Partie extends Observable {
    public void distribuer() {
        // ... logique ...
        this.setChanged();
        this.notifyObservers();
    }
}

public class TestPartie implements Observer {
    public void update(Observable o, Object arg) {
        if (o instanceof Partie) {
            // Mettre à jour l'affichage
        }
    }
}
```
### 4. **MVC Pattern**
*Architecture de l'application*

- Modèle : Classes métier (Partie, Joueur, Carte...)
- Vue : Interfaces Swing (TestPartie, MenuPrincipal...)
- Contrôleur : Gestionnaires d'événements (PartieControler...)

**Avantages** :
- ✅ Séparation des responsabilités
- ✅  Vue interchangeable (Swing → JavaFX)
- ✅ Testabilité du modèle

### 3. **Observer Pattern**
*Communication Vue-Modèle*
---

## Tests

```bash
# Exécuter les tests unitaires
mvn test

# Générer un rapport de couverture
mvn jacoco:report
```

---

## Documentation

La Javadoc complète est disponible après génération :

```bash
mvn javadoc:javadoc
open target/site/apidocs/index.html
```

---

## Contributeurs

<table>
  <tr>
    <td align="center">
      <b>Nina & Emeline</b><br>
      <sub>Développeuses principales</sub>
    </td>
  </tr>
</table>

---

## Améliorations Possibles

- [ ] Mode en ligne multijoueur
- [ ] Statistiques et historique des parties
- [ ] Système de classement
- [ ] Mode tournoi

---

<div align="center">

**⭐ N'oubliez pas de mettre une étoile si vous avez aimé le projet ! ⭐**

[⬆ Retour en haut](#-jest---jeu-de-cartes-en-java)

</div>
