# CalGeneratorBack


## Description du projet

## Installation

## Documentation de l'API

### UniteFormation

#### Lister tous les uniteFormation

Méthode : GET

Route : localhost:9000/uniteFormations

Retour : Liste l'ensemble de uniteFormation présent en base de données

Exemple :

```json
[
  {
    "libelle": "Développement Java SOPRA-STERIA (2017)",
    "dureeEnHeures": 280,
    "dureeEnSemaines": 8,
    "libelleCourt": "17SOPJAV",
    "idUniteFormation": 1,
    "archiver": false
  },
  {
    "libelle": "Assistance aux utilisateurs du poste de travail",
    "dureeEnHeures": 350,
    "dureeEnSemaines": 10,
    "libelleCourt": "IM14ASS17",
    "idUniteFormation": 2,
    "archiver": false
  }
]
```

#### Détail d'un uniteFormation

Méthode : GET

Route : localhost:9000/uniteFormations/:idUniteFormation

Paramètre :

    - :idUniteFormation : identifiant du uniteFormation souhaité
    
Retour : Affiche les détails du uniteFormation souhaité

Exemple :

```json
{
  "libelle": "Développement Java SOPRA-STERIA (2017)",
  "dureeEnHeures": 280,
  "dureeEnSemaines": 8,
  "libelleCourt": "17SOPJAV",
  "idUniteFormation": 1,
  "archiver": false
}
```