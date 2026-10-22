# Bibliothèque API

API REST de gestion d'une bibliothèque : livres, utilisateurs, emprunts et retours.
Projet d'apprentissage réalisé avec Spring Boot.

## Technologies

- Java 21
- Spring Boot (Spring Web, Spring Data JPA, Validation)
- MySQL
- Maven
- API REST (JSON)

## Lancer l'application

1. Installer Java 21 et MySQL.
2. Créer la base de données :
   ```sql
   CREATE DATABASE bibliotheque;
   ```
3. Vérifier la configuration dans `src/main/resources/application.yaml` (par défaut : MySQL en local, utilisateur `root`, sans mot de passe). Adapter si besoin.
4. Lancer l'application depuis l'IDE (classe principale) ou avec :
   ```bash
   ./mvnw spring-boot:run
   ```

L'API est alors disponible sur `http://localhost:8080`. Les tables sont créées automatiquement au démarrage.

## Routes

### Livres : `/api/livres`

| Méthode | Route | Description |
|---|---|---|
| GET | `/api/livres` | Lister les livres |
| GET | `/api/livres/{id}` | Trouver un livre |
| POST | `/api/livres` | Ajouter un livre |
| PUT | `/api/livres/{id}` | Modifier un livre |
| DELETE | `/api/livres/{id}` | Supprimer un livre |

### Utilisateurs : `/api/utilisateurs`

Mêmes opérations que pour les livres. Le nom est obligatoire, l'email doit être valide et unique.

### Emprunts : `/api/emprunts`

| Méthode | Route | Description |
|---|---|---|
| GET | `/api/emprunts` | Lister les emprunts |
| POST | `/api/emprunts` | Emprunter un livre |
| POST | `/api/emprunts/{id}/retour` | Retourner un livre |

## Exemple

Emprunter un livre :

```http
POST /api/emprunts
Content-Type: application/json

{
  "livreId": 1,
  "utilisateurId": 1
}
```

Réponse : l'emprunt est créé avec la date du jour, et le livre passe à `"disponible": false`.

Retourner le livre : `POST /api/emprunts/1/retour`. La date de retour est renseignée et le livre redevient disponible.

## Règles métier

- Un livre déjà emprunté ne peut pas être emprunté à nouveau (erreur 409).
- Un livre emprunté est marqué comme indisponible, et redevient disponible au retour.
- Un emprunt ne peut être retourné qu'une seule fois (erreur 409).
- Un livre, un utilisateur ou un emprunt inexistant renvoie une erreur 404.
- Les données d'un utilisateur sont validées (nom obligatoire, email valide et unique).

## Améliorations prévues

- Authentification avec rôles (bibliothécaire, lecteur)
- Tests automatisés
- Réponse propre (409) quand un email existe déjà
