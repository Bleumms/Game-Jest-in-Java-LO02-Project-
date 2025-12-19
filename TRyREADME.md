# 🃏 Jest - Jeu de Cartes en Java

<div align="center">

![Java](https://img.shields.io/badge/Java-17+-orange?style=flat&logo=java)
![Maven](https://img.shields.io/badge/Maven-3.8+-blue?style=flat&logo=apachemaven)
![License](https://img.shields.io/badge/License-Educational-green?style=flat)
![Last Commit](https://img.shields.io/github/last-commit/Bleumms/Game-Jest-in-Java-LO02-Project-?style=flat&logo=git)

*Un jeu de cartes stratégique développé dans le cadre du projet LO02*

[Caractéristiques](#-caractéristiques) • [Installation](#-installation) • [Utilisation](#-utilisation) • [Architecture](#-architecture) • [Règles du Jeu](#-règles-du-jeu)

</div>

---

## 📋 Table des Matières

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

## 🎮 À Propos

**Jest** est un jeu de cartes stratégique développé en Java dans le cadre du projet LO02. Le jeu oppose 3 ou 4 joueurs (humains ou IA) dans une bataille tactique où chaque carte a une valeur qui dépend de règles complexes et interdépendantes.

Le projet met en œuvre plusieurs concepts avancés de programmation orientée objet :
- Héritage et polymorphisme
- Patterns de conception (Visitor, Strategy)
- Sérialisation et persistance
- Architecture modulaire et extensible

---

## ✨ Caractéristiques

### 🎯 Gameplay
- **3-4 joueurs** : Parties multijoueurs avec joueurs humains et IA
- **Système de points dynamique** : 5 règles qui modifient la valeur des cartes
- **Cartes trophées** : Objectifs variés à accomplir pour gagner
- **Stratégies IA** : Deux niveaux de difficulté (Random et Intelligent)

### 🔧 Fonctionnalités Techniques
- **Sauvegarde/Chargement** : Reprenez vos parties à tout moment
- **Interface console** : Interaction claire et intuitive
- **Architecture modulaire** : Ajout facile de nouvelles règles et stratégies
- **Jeux personnalisables** : Mode "MINI" (9 cartes) et "TOUT" (17 cartes)

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

## 📦 Installation

### 1️⃣ Cloner le dépôt

```bash
git clone https://github.com/Bleumms/Game-Jest-in-Java-LO02-Project-.git
cd Game-Jest-in-Java-LO02-Project-
```

### 2️⃣ Compiler le projet

```bash
mvn clean install
```

### 3️⃣ Générer la documentation Javadoc (optionnel)

```bash
mvn javadoc:javadoc
# La documentation sera dans target/site/apidocs/
```

---

## 🚀 Utilisation

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
   - Sélection du jeu (MINI ou TOUT)
   - Nombre de joueurs (3 ou 4)
   - Type de joueurs (Réel ou Virtuel + stratégie)
3. **Phase de jeu** :
   - Distribution de 2 cartes par joueur
   - Chaque joueur choisit une carte visible et une cachée
   - Tour par tour, les joueurs volent des cartes aux autres
   - Le joueur avec la carte visible la plus haute joue en premier
4. **Fin de partie** :
   - Attribution des trophées selon leurs conditions
   - Calcul des scores finaux
   - Annonce du vainqueur

---

## 🎲 Règles du Jeu

### Valeur de Base
Chaque carte a une valeur égale à son numéro (1 à 4).

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

## 🏗️ Architecture

### Structure du Projet

```
Jest/
├── 📁 Cartes
│   ├── Carte.java (abstraite)
│   ├── CarteClassique.java
│   └── Jocker.java
│
├── 📁 Joueurs
│   ├── Joueur.java (abstraite)
│   ├── JoueurPhysique.java
│   └── JoueurVirtuel.java
│
├── 📁 Stratégies
│   ├── Strategie.java (interface)
│   ├── StrategieRandom.java
│   └── StrategieIntelligent.java
│
├── 📁 Règles
│   ├── Regle.java (abstraite)
│   ├── RegleCoeur.java
│   ├── RegleCarreau.java
│   ├── RegleAs.java
│   ├── RegleDoubleNoir.java
│   └── RegleJocker.java
│
├── 📁 Conditions
│   ├── ConditionVictoire.java (interface)
│   ├── ConditionMaxScore.java
│   ├── ConditionMaxMinSymbole.java
│   ├── ConditonPlusCarteValeur.java
│   └── ConditionJocker.java
│
├── 📁 Gestion
│   ├── Jeu.java
│   ├── Partie.java
│   ├── Reference.java
│   ├── CalculateurScore.java
│   ├── ValeurParCarte.java
│   └── Menu.java
│
├── 📁 Patterns
│   ├── Visitor.java (interface)
│   └── Visitable.java (interface)
│
├── 📁 Enums
│   └── Symbole.java
│
└── Main.java
```

### Diagramme de Classes Simplifié

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

---

## 🎨 Patterns de Conception

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
*Pour les IA*

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
- ✅ Ajout facile de nouvelles IA
- ✅ Code des joueurs simplifié

### 3. **Template Method**
*Dans la classe Regle*

```java
public abstract class Regle {
    public void modifierValeurCarte(List<Carte> cartes, List<ValeurParCarte> valeurs) {
        // Implémenté dans les sous-classes
    }
}
```

---

## 🧪 Tests

```bash
# Exécuter les tests unitaires
mvn test

# Générer un rapport de couverture
mvn jacoco:report
```

---

## 📚 Documentation

La Javadoc complète est disponible après génération :

```bash
mvn javadoc:javadoc
open target/site/apidocs/index.html
```

---

## 🤝 Contributeurs

<table>
  <tr>
    <td align="center">
      <b>Nina & Emeline</b><br>
      <sub>Développeuses principales</sub>
    </td>
  </tr>
</table>

---

## 📄 Licence

Ce projet est développé dans un cadre éducatif pour le cours LO02.

---

## 🔮 Améliorations Futures

- [ ] Interface graphique (JavaFX ou Swing)
- [ ] Mode en ligne multijoueur
- [ ] Statistiques et historique des parties
- [ ] Plus de stratégies IA (Machine Learning ?)
- [ ] Système de classement/ELO
- [ ] Animations et effets sonores
- [ ] Mode tournoi

---

## 📞 Contact

Pour toute question ou suggestion concernant le projet :
- 🐛 [Ouvrir une issue](https://github.com/Bleumms/Game-Jest-in-Java-LO02-Project-/issues)
- 💬 Discuter dans les [Discussions](https://github.com/Bleumms/Game-Jest-in-Java-LO02-Project-/discussions)

---

<div align="center">

**⭐ N'oubliez pas de mettre une étoile si vous avez aimé le projet ! ⭐**

[⬆ Retour en haut](#-jest---jeu-de-cartes-en-java)

</div>
