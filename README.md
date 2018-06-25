# CalGeneratorBack

## Description du projet

Ce projet expose une API REST permettant la réalisation la génération d'un calendrier par contraintes. Ce projet a été demandé par l'ENI Ecole Informatique Nantes.

## Installation de l'environnement de développement

### Docker

#### Pour Windows

Pour l'installation de docker sur l'OS Windows, suivre la documentation suivante : `https://docs.docker.com/docker-for-windows/install/`

#### Pour Mac

Pour l'installation de docker sur l'OS Mac, suivre la documentation suivante : `https://docs.docker.com/docker-for-mac/install/`

#### Pour Linux (Ubunutu)

Pour l'installation de docker sur l'OS Linux (Ubuntu), suivre la documentation suivante : `https://docs.docker.com/install/linux/docker-ce/ubuntu/`

### Dockerisation et initialisation de la base de données MSSQL

Les commandes suivantes doivent être executer à partir d'un terminal

1.  Importer l'image nécessaire : `docker pull microsoft/mssql-server-linux`

2.  Créer le container : `docker run -e 'ACCEPT_EULA=Y' -e 'SA_PASSWORD=yourStrong(!)Password' -p 1433:1433 -d microsoft/mssql-server-linux:2017-latest`

3.  Télécharger et installer `SQL Operation Studio` : `https://docs.microsoft.com/en-us/sql/sql-operations-studio/download?view=sql-server-2017`

4.  Importer et exécuter les scripts

/!\ Le mot de passe doit vraiment être complexe sinon le container ne se lance pas /!\

### Dockerisation et initialisation de la base de données MongoDB

1.  Importer l'image nécessaire : `docker pull mongo`

2.  Créer le container : `docker run -p 27017:27017 mongo`

3.  Télécharger et installer `RoboMongo` : `https://robomongo.org/download`

### Nota Bene

Pour relancer un container arrété, suivre les explications suivantes :

1.  Connaitre le `CONTAINER ID` du container qui nous interresse : `docker ps -a`

| CONTAINER ID | IMAGE                                    | COMMAND                | CREATED      | STATUS                    | PORTs                  | NAME                |
| ------------ | ---------------------------------------- | ---------------------- | ------------ | ------------------------- | ---------------------- | ------------------- |
| f9c555e51a41 | mongo                                    | "docker-entrypoint..." | 27 hours ago | Exited (0) 23 hours ago   |                        | nervous_kepler      |
| 50ef1a944cca | microsoft/mssql-server-linux:2017-latest | "/opt/mssql/bin/sq..." | 13 days ago  | Exited (255) 23 hours ago | 0.0.0.0:1433->1433/tcp | heuristic_goldstine |

2.  Si le souhait est de redémarrer le container MSSQL, exectuer la commande : `docker start 50ef1a944cca`

## Description des objects

### Classes

| Nom          | Type   | Optionnel | Valeur défaut |
| ------------ | ------ | --------- | ------------- |
| idClasses    | String | Non       |               |
| period       | Period | Non       |               |
| realDuration | Int    | Non       |               |
| idPlace      | Int    | Non       |               |

```json
{
  "idClasses": "",
  "period": {
    "start": "",
    "end: ""
  },
  "realDuration": 35,
  "idPlace": 1
}
```

### ConstraintPriorityBoolean

| Nom      | Type    | Optionnel | Valeur défaut |
| -------- | ------- | --------- | ------------- |
| priority | Int     | Oui       | -1            |
| value    | Boolean | Non       |               |

```json
{
  "priority": 2,
  "value": true
}
```

### ConstraintPriorityInteger

| Nom      | Type | Optionnel | Valeur défaut |
| -------- | ---- | --------- | ------------- |
| priority | Int  | Oui       | -1            |
| value    | Int  | Non       |               |

```json
{
  "priority": 2,
  "value": 20
}
```

### ConstraintPriorityPeriod

| Nom      | Type   | Optionnel | Valeur défaut |
| -------- | ------ | --------- | ------------- |
| priority | Int    | Oui       | -1            |
| value    | Period | Non       |               |

```json
{
  "priority": 2,
  "value": {
    "start": "",
    "end": ""
  }
}
```

### ConstraintPriorityStudent

| Nom      | Type              | Optionnel | Valeur défaut |
| -------- | ----------------- | --------- | ------------- |
| priority | Int               | Oui       | -1            |
| value    | ConstraintStudent | Non       |               |

```json
{
  "priority": 2,
  "value": {
    "idStudent": 20,
    "listClassees": []
  }
}
```

### ConstraintPriorityStudentCompany

| Nom      | Type                     | Optionnel | Valeur défaut |
| -------- | ------------------------ | --------- | ------------- |
| priority | Int                      | Oui       | -1            |
| value    | ConstraintStudentCompany | Non       |               |

```json
{
  "priority": 2,
  "value": {
    "maxStudentInTraining": 1,
    "listStudentCompany": [
      {
        "idStudent": 1,
        "listClassees": []
      },
      {
        "idStudent": 2,
        "listClassees": []
      }
    ]
  }
}
```

### ConstraintPriorityTrainingFrequency

| Nom      | Type                        | Optionnel | Valeur défaut |
| -------- | --------------------------- | --------- | ------------- |
| priority | Int                         | Oui       | -1            |
| value    | ConstraintTrainingFrequency | Non       |               |

```json
{
  "priority": 2,
  "value": {
    "maxWeekInTraining": 2,
    "minWeekInCompagny": 3
  }
}
```

### ConstraintStudent

| Nom          | Type         | Optionnel | Valeur défaut |
| ------------ | ------------ | --------- | ------------- |
| idStudent    | Int          | Non       |               |
| listClassees | Seq[Classes] | Non       |               |

```json
{
  "idStudent": 20,
  "listClassees": []
}
```

### ConstraintStudentCompany

| Nom                  | Type                   | Optionnel | Valeur défaut |
| -------------------- | ---------------------- | --------- | ------------- |
| maxStudentInTraining | Int                    | Non       |               |
| listStudentCompany   | Seq[ConstraintStudent] | Non       |               |

```json
{
  "maxStudentInTraining": 1,
  "listStudentCompany": [
    {
      "idStudent": 1,
      "listClassees": []
    },
    {
      "idStudent": 2,
      "listClassees": []
    }
  ]
}
```

### ConstraintTrainingFrequency

| Nom               | Type | Optionnel | Valeur défaut |
| ----------------- | ---- | --------- | ------------- |
| maxWeekInTraining | Int  | Non       |               |
| minWeekInCompagny | Int  | Non       |               |

```json
{
  "maxWeekInTraining": 2,
  "minWeekInCompagny": 3
}
```

### Period

| Nom   | Type   | Optionnel |
| ----- | ------ | --------- |
| start | String | Non       |
| end   | String | Non       |

```json
{
  "start": "",
  "end": ""
}
```

## Documentation de l'API

### CalendrierGenerationChoco

#### Génération de planning

Génère n calendrier(s).

| Méthode | Route          | Paramètres                   | Optionnel | Valeur défaut |
| ------- | -------------- | ---------------------------- | --------- | ------------- |
| POST    | /generationCal | periodOfTrainning: Period    | Non       |               |
|         |                | numberOfCalendarToFound: Int | Oui       | 5             |
|         |                | idProblem: String            | Oui       |               |
|         |                | idConstraint: String         | Oui       |               |
|         |                | idModuleFormation: String    | Oui       |               |

Exemple d'entrée : POST https://myServer/generationCal

```json
{
  "periodOfTrainning": {
    "start": "",
    "end": ""
  },
  "idProblem": "",
  "idConstraint": "",
  "idModuleFormation": ""
}
```

Exemple de retour :

```json

```

### Constraints

#### Création de contraintes

Création de contraintes.

| Méthode | Route        | Paramètres                                               | Optionnel | Valeur défaut |
| ------- | ------------ | -------------------------------------------------------- | --------- | ------------- |
| POST    | /constraints | place: ConstraintPriorityInteger                         | Oui       |               |
|         |              | annualNumberOfHour: ConstraintPriorityInteger            | Oui       |               |
|         |              | maxDurationOfTraining: ConstraintPriorityInteger         | Oui       |               |
|         |              | trainingFrequency: ConstraintPriorityTrainingFrequency   | Oui       |               |
|         |              | maxStudentInTraining: ConstraintPriorityStudentCompany   | Oui       |               |
|         |              | listStudentRequired: ConstraintPriorityStudent           | Oui       |               |
|         |              | listPeriodeOfTrainingExclusion: ConstraintPriorityPeriod | Oui       |               |
|         |              | listPeriodeOfTrainingInclusion: ConstraintPriorityPeriod | Oui       |               |
|         |              | prerequisModule: ConstraintPriorityBoolean               | Oui       |               |

Exemple d'entrée : POST https://myServer/constraints

```json
{
  "place": {
    "value": 1
  }
}
```

Exemple de retour :

```json
{
  "idConstraint": "67b7ef92-af36-41cf-902b-5671a7eb53f5",
  "place": {
    "value": 1
  }
}
```

#### Toutes les contraintes

Liste l'ensemble des containtes.

| Méthode | Route        | Paramètres |
| ------- | ------------ | ---------- |
| GET     | /constraints | Aucun      |

Exemple d'entrée : GET https://myServer/constraints

Exemple de retour :

```json
[
  {
    "idConstraint": "67b7ef92-af36-41cf-902b-5671a7eb53f5",
    "place": {
      "value": 1
    }
  }
]
```

#### Recherche de contraintes

Affiche les détails pour une containte donnée.

| Méthode | Route                      | Paramètres            |
| ------- | -------------------------- | --------------------- |
| GET     | /constraints/:idConstraint | idConstraint : String |

Exemple d'entrée : GET https://myServer/constraints/67b7ef92-af36-41cf-902b-5671a7eb53f5

Exemple de retour :

```json
{
  "idConstraint": "67b7ef92-af36-41cf-902b-5671a7eb53f5",
  "place": {
    "value": 1
  }
}
```

### Cours

#### Tous les cours

Liste l'ensemble des cours.

| Méthode | Route  | Paramètres |
| ------- | ------ | ---------- |
| GET     | /cours | Aucun      |

Exemple d'entrée : GET https://myServer/cours

Exemple de retour :

```json
[
  {
    "debut": "2018-08-20 00:00:00.0",
    "fin": "2018-08-24 00:00:00.0",
    "dureeReelleEnHeures": 35,
    "codePromotion": "ACDI_001",
    "idCours": "CDF20F3C-94D4-4069-A640-00E3100FDAE1",
    "idModule": 541,
    "libelleCours": "DVWEBCL",
    "dureePrevueEnHeures": 35,
    "dateAdefinir": false,
    "codeSalle": null,
    "codeFormateur": 0,
    "codeLieu": 11
  },
  {
    "debut": "2018-03-26 00:00:00.0",
    "fin": "2018-03-30 00:00:00.0",
    "dureeReelleEnHeures": 35,
    "codePromotion": "ADL__001",
    "idCours": "9AC9F5B9-BE0F-418D-AC3C-00EBB8582246",
    "idModule": 20,
    "libelleCours": "SQL",
    "dureePrevueEnHeures": 35,
    "dateAdefinir": false,
    "codeSalle": null,
    "codeFormateur": 0,
    "codeLieu": 11
  }
]
```

#### Détails pour un cours

Affiche les détails pour un cours donnée.

| Méthode | Route           | Paramètres      |
| ------- | --------------- | --------------- |
| GET     | /cours/:idCours | idCours: String |

Exemple d'entrée : GET https://myServer/cours:CDF20F3C-94D4-4069-A640-00E3100FDAE1

Exemple de retour :

```json
{
  "debut": "2018-08-20 00:00:00.0",
  "fin": "2018-08-24 00:00:00.0",
  "dureeReelleEnHeures": 35,
  "codePromotion": "ACDI_001",
  "idCours": "CDF20F3C-94D4-4069-A640-00E3100FDAE1",
  "idModule": 541,
  "libelleCours": "DVWEBCL",
  "dureePrevueEnHeures": 35,
  "dateAdefinir": false,
  "codeSalle": null,
  "codeFormateur": 0,
  "codeLieu": 11
}
```

### Entreprises

#### Toutes les entreprises

Liste l'ensemble des entreprises.

| Méthode | Route        | Paramètres |
| ------- | ------------ | ---------- |
| GET     | /entreprises | Aucun      |

Exemple d'entrée : GET https://myServer/entreprises

Exemple de retour :

```json
[
  {
    "TODO"
  }
]
```

#### Détails pour une entreprise

Affiche les détails pour une entreprise donnée.

| Méthode | Route                      | Paramètres        |
| ------- | -------------------------- | ----------------- |
| GET     | /entreprises/:idEntreprise | idEntreprise: Int |

Exemple d'entrée : GET https://myServer/entreprises/:idEntreprise

Exemple de retour :

```json
{
  "TODO"
}
```

### Formations

#### Liste l'ensemble des formations.

| Méthode | Route       | Paramètres |
| ------- | ----------- | ---------- |
| GET     | /formations | Aucun      |

Exemple d'entrée : GET https://myServer/formations

Exemple de retour :

```json
[
  {
    "codeFormation": "17ASR   ",
    "libelleLong": "Administrateur Système Et Réseau",
    "libelleCourt": "[2017]ASR",
    "dureeEnHeures": 1680,
    "tauxHoraire": 0,
    "codeTitre": "ASR     ",
    "heuresCentre": 1120,
    "heuresStage": 560,
    "semainesCentre": 32,
    "semainesStage": 16,
    "dureeEnSemaines": 48,
    "typeFormation": 1,
    "codeLieu": 0
  },
  {
    "codeFormation": "17CDI   ",
    "libelleLong": "Concepteur Développeur Informatique",
    "libelleCourt": "[2017]CDI",
    "dureeEnHeures": 1295,
    "tauxHoraire": 0,
    "codeTitre": "CDI     ",
    "heuresCentre": 1015,
    "heuresStage": 280,
    "semainesCentre": 29,
    "semainesStage": 8,
    "dureeEnSemaines": 37,
    "typeFormation": 1,
    "codeLieu": 0
  }
]
```

#### Détails pour une formation

Affiche les détails pour une formation donnée.

| Méthode | Route                      | Paramètres            |
| ------- | -------------------------- | --------------------- |
| GET     | /formations/:codeFormation | codeFormation: String |

Exemple d'entrée : GET https://myServer/formations/17CDI

Exemple de retour :

```json
{
  "codeFormation": "17CDI   ",
  "libelleLong": "Concepteur Développeur Informatique",
  "libelleCourt": "[2017]CDI",
  "dureeEnHeures": 1295,
  "tauxHoraire": 0,
  "codeTitre": "CDI     ",
  "heuresCentre": 1015,
  "heuresStage": 280,
  "semainesCentre": 29,
  "semainesStage": 8,
  "dureeEnSemaines": 37,
  "typeFormation": 1,
  "codeLieu": 0
}
```

#### Détails des modules pour une formation

Affiche les modules pour une formation donnée.

| Méthode | Route                              | Paramètres            |
| ------- | ---------------------------------- | --------------------- |
| GET     | /formations/:codeFormation/modules | codeFormation: String |

Exemple d'entrée : GET https://myServer/formations/17CDI/modules

Exemple de retour :

```json
[
  {
    "libelle": "Le langage de requête SQL",
    "dureeEnHeures": 35,
    "dureeEnSemaines": 1,
    "libelleCourt": "SQL",
    "idModule": 20,
    "typeModule": 1
  },
  {
    "libelle": "Procédures stockées avec PL-SQL",
    "dureeEnHeures": 35,
    "dureeEnSemaines": 1,
    "libelleCourt": "PL-SQL",
    "idModule": 21,
    "typeModule": 1
  }
]
```

### Lieux

#### Tous les lieux

Liste l'ensemble des lieux.

| Méthode | Route  | Paramètres |
| ------- | ------ | ---------- |
| GET     | /lieux | Aucun      |

Exemple d'entrée : GET https://myServer/lieux

Exemple de retour :

```json
[
  {
    "codeLieu": 1,
    "libelle": "NANTES",
    "debutAM": "9H00",
    "finAM": "12H30",
    "debutPM": "14H00",
    "finPM": "17H30",
    "adresse": "ZAC du Moulin Neuf - 2 B Rue Benjamin Franklin BP 80009",
    "cP": 44801,
    "ville": "SAINT-HERBLAIN"
  },
  {
    "codeLieu": 2,
    "libelle": "RENNES",
    "debutAM": "9H00",
    "finAM": "12H30",
    "debutPM": "13H30",
    "finPM": "17H00",
    "adresse": "ZAC de la Conterie - 8 rue Léo Lagrange",
    "cP": 35131,
    "ville": "CHARTRES DE BRETAGNE"
  }
]
```

#### Détails pour un lieu

Affiche les détails pour un lieu donnée.

| Méthode | Route            | Paramètres    |
| ------- | ---------------- | ------------- |
| GET     | /lieux/:codelieu | codeLieu: Int |

Exemple d'entrée : GET https://myServer/lieux/1

Exemple de retour :

```json
{
  "codeLieu": 1,
  "libelle": "NANTES",
  "debutAM": "9H00",
  "finAM": "12H30",
  "debutPM": "14H00",
  "finPM": "17H30",
  "adresse": "ZAC du Moulin Neuf - 2 B Rue Benjamin Franklin BP 80009",
  "cP": 44801,
  "ville": "SAINT-HERBLAIN"
}
```

### Modules

#### Tous les modules

Liste l'ensemble des modules.

| Méthode | Route    | Paramètres |
| ------- | -------- | ---------- |
| GET     | /modules | Aucun      |

Exemple d'entrée : GET https://myServer/modules

Exemple de retour :

```json
[
  {
    "libelle": "Le langage de requête SQL",
    "dureeEnHeures": 35,
    "dureeEnSemaines": 1,
    "libelleCourt": "SQL",
    "idModule": 20,
    "typeModule": 1
  },
  {
    "libelle": "Procédures stockées avec PL-SQL",
    "dureeEnHeures": 35,
    "dureeEnSemaines": 1,
    "libelleCourt": "PL-SQL",
    "idModule": 21,
    "typeModule": 1
  }
]
```

#### Détails pour un module

Affiche les détails pour un module donnée.

| Méthode | Route              | Paramètres    |
| ------- | ------------------ | ------------- |
| GET     | /modules/:idModule | idModule: Int |

Exemple d'entrée : GET https://myServer/modules/20

Exemple de retour :

```json
{
  "libelle": "Le langage de requête SQL",
  "dureeEnHeures": 35,
  "dureeEnSemaines": 1,
  "libelleCourt": "SQL",
  "idModule": 20,
  "typeModule": 1
}
```

### ModulesConstraints

Création de contraintes de module.

| Méthode | Route               | Paramètres                         | Optionnel | Valeur défaut |
| ------- | ------------------- | ---------------------------------- | --------- | ------------- |
| POST    | /modulesConstraints | idModule: Int                      | Non       |               |
|         |                     | codeFormation: String              | Non       |               |
|         |                     | listIdModulePrerequisite: Seq[Int] | Oui       |               |
|         |                     | listIdModuleOptional: Seq[Int]     | Oui       |               |

Exemple d'entrée : POST https://myServer/modulesConstraints

```json
[
  {
    "idModule": 20,
    "codeFormation": "17CDI",
    "listIdModulePrerequisite": [4, 5]
  }
]
```

Exemple de retour :

```json
[
  {
    "idModule": 20,
    "codeFormation": "17CDI",
    "listIdModulePrerequisite": [4, 5]
  }
]
```

### Promotions

#### Toutes les promotions

Liste l'ensemble des promotions.

| Méthode | Route       | Paramètres |
| ------- | ----------- | ---------- |
| GET     | /promotions | Aucun      |

Exemple d'entrée : GET https://myServer/promotions

Exemple de retour :

```json
[
  {
    "codePromotion": "AASR_001",
    "libelle": "AASR_001",
    "debut": "2018-04-09 00:00:00.0",
    "fin": "2019-03-29 00:00:00.0",
    "codeFormation": "17ASR   ",
    "codeLieu": 11
  },
  {
    "codePromotion": "ACDI_001",
    "libelle": "ACDI_001",
    "debut": "2018-06-11 00:00:00.0",
    "fin": "2019-03-15 00:00:00.0",
    "codeFormation": "17CDI   ",
    "codeLieu": 11
  }
]
```

#### Détails pour une promotion

Affiche les détails pour une promotion donnée.

| Méthode | Route                      | Paramètres            |
| ------- | -------------------------- | --------------------- |
| GET     | /promotions/:codePromotion | codePromotion: String |

Exemple d'entrée : GET https://myServer/promotions/ACDI_001

Exemple de retour :

```json
{
  "codePromotion": "ACDI_001",
  "libelle": "ACDI_001",
  "debut": "2018-06-11 00:00:00.0",
  "fin": "2019-03-15 00:00:00.0",
  "codeFormation": "17CDI   ",
  "codeLieu": 11
}
```

### Salles

#### Toutes les salles

Liste l'ensemble des salles.

| Méthode | Route   | Paramètres |
| ------- | ------- | ---------- |
| GET     | /salles | Aucun      |

Exemple d'entrée : GET https://myServer/salles

Exemple de retour :

```json
[
  {
    "codeSalle": "0001",
    "libelle": "Salle 1",
    "capacite": 13,
    "lieu": 4
  },
  {
    "codeSalle": "0002",
    "libelle": "Salle 2",
    "capacite": 13,
    "lieu": 4
  }
]
```

#### Détails pour une salle

Affiche les détails pour une salle donnée.

| Méthode | Route              | Paramètres        |
| ------- | ------------------ | ----------------- |
| GET     | /salles/:codeSalle | codeSalle: String |

Exemple d'entrée : GET https://myServer/salle/0002

Exemple de retour :

```json
{
  "codeSalle": "0002",
  "libelle": "Salle 2",
  "capacite": 13,
  "lieu": 4
}
```

### Stagiaires

#### Tous les stagiaires

Liste l'ensemble des stagiaires.

| Méthode | Route       | Paramètres |
| ------- | ----------- | ---------- |
| GET     | /stagiaires | Aucun      |

Exemple d'entrée : GET https://myServer/stagiaires

Exemple de retour :

```json
[
  {
    "TODO": ""
  }
]
```

#### Détails pour un stagiaire

Affiche les détails pour un stagiaire donné.

| Méthode | Route                       | Paramètres          |
| ------- | --------------------------- | ------------------- |
| GET     | /stagiaires/:codeStagiaires | codeStagiaires: Int |

Exemple d'entrée : GET https://myServer/stagiaires/:codeStagiaires

Exemple de retour :

```json
{
  "TODO": ""
}
```

### Titres

#### Tous les titres

Liste l'ensemble des titres.

| Méthode | Route   | Paramètres |
| ------- | ------- | ---------- |
| GET     | /titres | Aucun      |

Exemple d'entrée : GET https://myServer/titres

Exemple de retour :

```json
[
  {
    "codeTitre": "AL      ",
    "libelleCourt": "ARCHITECTE LOGICIEL",
    "libelleLong": "Architecte Logiciel",
    "niveau": "I",
    "millesime": null
  },
  {
    "codeTitre": "ASR     ",
    "libelleCourt": "ASR",
    "libelleLong": "Administrateur Système et Réseau",
    "niveau": "II",
    "millesime": null
  }
]
```

#### Détails pour un titre

Affiche les détails pour un titre donné.

| Méthode | Route              | Paramètres     |
| ------- | ------------------ | -------------- |
| GET     | /titres/:codeTitre | codeTitre: Int |

Exemple d'entrée : GET https://myServer/titres/ASR

Exemple de retour :

```json
{
  "codeTitre": "ASR     ",
  "libelleCourt": "ASR",
  "libelleLong": "Administrateur Système et Réseau",
  "niveau": "II",
  "millesime": null
}
```

### UnitesFormations

#### Toutes les unités de formations

Liste l'ensemble des unités de formation.

| Méthode | Route            | Paramètres |
| ------- | ---------------- | ---------- |
| GET     | /uniteFormations | Aucun      |

Exemple d'entrée : GET https://myServer/uniteFormations

Exemple de retour :

```json
[
  {
    "libelle": "Développement Java SOPRA-STERIA (2017)",
    "dureeEnHeures": 280,
    "dureeEnSemaines": 8,
    "libelleCourt": "17SOPJAV",
    "idUniteFormation": 1
  },
  {
    "libelle": "Assistance aux utilisateurs du poste de travail",
    "dureeEnHeures": 350,
    "dureeEnSemaines": 10,
    "libelleCourt": "IM14ASS17",
    "idUniteFormation": 2
  }
]
```

#### Détails pour une unité de formations

Affiche les détails pour une unité de formations donnés.

| Méthode | Route                              | Paramètres            |
| ------- | ---------------------------------- | --------------------- |
| GET     | /uniteFormations/:idUniteFormation | idUniteFormation: Int |

Exemple d'entrée : GET https://myServer/uniteFormations/2

Exemple de retour :

```json
{
  "libelle": "Assistance aux utilisateurs du poste de travail",
  "dureeEnHeures": 350,
  "dureeEnSemaines": 10,
  "libelleCourt": "IM14ASS17",
  "idUniteFormation": 2
}
```
