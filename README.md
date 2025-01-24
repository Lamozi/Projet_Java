<p align="center">
  <img src="https://img.shields.io/github/v/release/ton-compte/bibliotheque?style=flat-square&color=00a8f0" alt="Version" />
  <a href="https://github.com/ton-compte/bibliotheque/releases">
    <img src="https://img.shields.io/github/downloads/ton-compte/bibliotheque/total?style=flat-square&color=00a8f0" alt="Downloads" />
  </a>
  <img src="https://img.shields.io/badge/License-MIT-blue.svg?style=flat-square&color=00a8f0" alt="License" />
  <a href="https://discord.gg/tonserveur">
    <img src="https://img.shields.io/discord/1328277792730779648?style=flat-square&color=7289DA&label=Discord&logo=discord&logoColor=white" alt="Discord" />
  </a>
  <a href="https://twitter.com/ton-compte">
    <img src="https://img.shields.io/twitter/follow/ton-compte?style=flat-square" alt="Twitter" />
  </a>
</p>


# 📚 Bibliothèque - Gestion de Livres, Utilisateurs et Emprunts

## 🌟 Description du projet

Le projet "Bibliothèque" est une application de gestion de bibliothèque qui permet de gérer les **livres**, les **utilisateurs** et les **emprunts**. L'application permet à un administrateur de :

1. Ajouter des **livres** à la bibliothèque.
2. Ajouter des **utilisateurs** qui pourront emprunter des livres.
3. Gérer les **emprunts** de livres, y compris la date d'emprunt et la date de retour.
4. **Exporter** la base de données en format **JSON**.
5. **Importer** une base de données à partir d'un fichier **JSON**.

Les données sont stockées dans une base de données et peuvent être exportées ou importées sous forme de fichier JSON pour faciliter le transfert des données entre différentes instances de l'application.

## 🎯 Fonctionnalités principales

### 1. 📖 Gestion des livres
   - Ajouter, modifier ou supprimer un livre.
   - Lister les livres disponibles et empruntés.
   - Recherche par **titre**, **auteur** ou **genre**.

### 2. 👥 Gestion des utilisateurs
   - Ajouter, modifier ou supprimer un utilisateur.
   - Afficher l’historique des **emprunts** d’un utilisateur.

### 3. 📅 Gestion des emprunts
   - Enregistrer un emprunt de livre par un utilisateur.
   - Vérification de la disponibilité d'un livre avant de l'emprunter.
   - Possibilité de consulter l'historique des emprunts.

### 4. 💾 Exportation et importation JSON
   - Exporter la base de données sous forme de fichier JSON avec une structure prédéfinie.
   - Importer des données depuis un fichier JSON pour peupler la base de données.

## 🛠️ Technologies utilisées

- **Java** : Langage de programmation principal.
- **Jackson** : Bibliothèque pour la sérialisation et la désérialisation des objets Java en JSON.
- **SQL** : Base de données utilisée pour stocker les informations des livres, utilisateurs et emprunts.
- **LocalDate** : Pour la gestion des dates (emprunt et retour).

## 📁 Structure du projet

Le projet est structuré de la manière suivante :

bibliotheque ├── src │ ├── esgi │ │ └── b3 │ │ ├── exports # Classe pour l'exportation et l'importation JSON │ │ ├── models # Classes représentant les modèles (Livre, User, Emprunt) │ │ ├── services # Services pour gérer les livres, utilisateurs et emprunts │ │ └── ui # Interfaces utilisateur pour interagir avec l'application ├── target # Dossier de sortie contenant les fichiers compilés └── README.md # Ce fichier README


### Exemple de structure du fichier JSON exporté :

#### Exporter en JSON

L'utilisateur peut exporter les données (livres, utilisateurs, emprunts) dans un fichier JSON. Le fichier contient une structure comme suit :

```json
{
  "livres": [
    {
      "id": 1,
      "titre": "Le Livre Exemple",
      "genre": "Fiction",
      "auteur": "Auteur Exemple",
      "status": "disponible"
    }
  ],
  "users": [
    {
      "id": 2,
      "name": "Utilisateur Exemple",
      "email": "exemple@utilisateur.com"
    }
  ],
  "emprunts": [
    {
      "id": 8,
      "user_id": 2,
      "livre_id": 1,
      "date_emprunt": "2025-01-23",
      "date_retour": "2025-02-07"
    }
  ]
}
```
```json
{
  "id" : 8,
  "userId" : 2,
  "livreId" : -1,
  "date_emprunt" : [ 2025, 1, 23 ],
  "date_retour" : [ 2025, 2, 7 ]
}
```
🌍 📄 Resources

<a href="https://github.com/FasterXML/jackson">Jackson Documentation</a>

<a href="https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html">LocalDate API</a>

<a href="https://github.com/FasterXML/jackson">SQL Database Guide</a>

👨‍💻 Contributeurs
Merci à tous les contributeurs de ce projet. Si vous souhaitez participer, vous pouvez proposer une pull request ou créer une issue sur GitHub.

Contributeurs :
- Gedeon Mutikanga
- Jonas AMOZIGH
