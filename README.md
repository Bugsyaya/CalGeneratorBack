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

### F

| Nom      | Type    | Optionnel | Valeur défaut |
| -------- | ------- | --------- | ------------- |
| priority | Int     | Oui       | -1            |
| value    | Boolean | Non       |               |
| id       | String  | Non       |               | 

```json
{
  "priority": 2,
  "value": true,
  "id": "78c8fg03-bg47-52dg-013c-6782b8fc64g6"
}
```

### ConstraintPriorityInteger

| Nom      | Type | Optionnel | Valeur défaut |
| -------- | ---- | --------- | ------------- |
| priority | Int  | Oui       | -1            |
| value    | Int  | Non       |               |
| id       | String  | Non       |               |

```json
{
  "priority": 2,
  "value": 20,
  "id": "78c8fg03-bg47-52dg-013c-6782b8fc64g6"
}
```

### ConstraintPriorityPeriod

| Nom      | Type   | Optionnel | Valeur défaut |
| -------- | ------ | --------- | ------------- |
| priority | Int    | Oui       | -1            |
| value    | Period | Non       |               |
| id       | String  | Non       |               |

```json
{
  "priority": 2,
  "value": {
    "start": "",
    "end": ""
  },
  "id": "78c8fg03-bg47-52dg-013c-6782b8fc64g6"
}
```

### ConstraintPriorityStudent

| Nom      | Type              | Optionnel | Valeur défaut |
| -------- | ----------------- | --------- | ------------- |
| priority | Int               | Oui       | -1            |
| value    | ConstraintStudent | Non       |               |
| id       | String  | Non       |               |

```json
{
  "priority": 2,
  "value": {
    "idStudent": 20,
    "listClassees": []
  },
  "id": "78c8fg03-bg47-52dg-013c-6782b8fc64g6"
}
```

### ConstraintPriorityStudentCompany

| Nom      | Type                     | Optionnel | Valeur défaut |
| -------- | ------------------------ | --------- | ------------- |
| priority | Int                      | Oui       | -1            |
| value    | ConstraintStudentCompany | Non       |               |
| id       | String  | Non       |               |

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
  },
  "id": "78c8fg03-bg47-52dg-013c-6782b8fc64g6"
}
```

### ConstraintPriorityTrainingFrequency

| Nom      | Type                        | Optionnel | Valeur défaut |
| -------- | --------------------------- | --------- | ------------- |
| priority | Int                         | Oui       | -1            |
| value    | ConstraintTrainingFrequency | Non       |               |
| id       | String  | Non       |               |

```json
{
  "priority": 2,
  "value": {
    "maxWeekInTraining": 2,
    "minWeekInCompagny": 3
  },
  "id": "78c8fg03-bg47-52dg-013c-6782b8fc64g6"
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
| POST    | /generationCal | periodOfTraining: Period    | Non       |               |
|         |                | numberOfCalendarToFound: Int | Oui       | 5             |
|         |                | idProblem: String            | Oui       |               |
|         |                | idConstraint: String         | Oui       |               |
|         |                | idModuleFormation: String    | Oui       |               |

Exemple d'entrée : POST https://myServer/generationCal

```json
{
   "periodOfTraining": {
     "start": "2016-09-11",
     "end": "2020-11-24"
   },
   "idConstraint": "67b7ef92-af36-41cf-902b-5671a7eb53f5",
   "idModulePrerequisPlanning": "qwerty",
   "numberOfCalendarToFound": 1,
   "codeFormation": "17ASR"
 }
```

Exemple de retour :

```json
[
  {
    "idCalendrier": "d647b3bc-9397-4ad9-9f8d-09d32fd56247",
    "constraint": [],
    "cours": [
      {
        "debut": "2018-04-09 00:00:00.0",
        "fin": "2018-04-20 00:00:00.0",
        "dureeReelleEnHeures": 70,
        "codePromotion": "HASR_107",
        "idCours": "8AEB9E0F-482D-4A3F-9DAC-5DB08852B033",
        "idModule": 506,
        "libelleCours": "UTIL-POSTE",
        "dureePrevueEnHeures": 70,
        "dateAdefinir": false,
        "codeLieu": 1
      },
      {
        "debut": "2018-06-04 00:00:00.0",
        "fin": "2018-06-15 00:00:00.0",
        "dureeReelleEnHeures": 70,
        "codePromotion": "HASR_107",
        "idCours": "1458FA7A-9E7C-43B0-A1AA-46805F70555B",
        "idModule": 721,
        "libelleCours": "SERV-RES17",
        "dureePrevueEnHeures": 70,
        "dateAdefinir": false,
        "codeLieu": 1
      },
      {
        "debut": "2018-06-18 00:00:00.0",
        "fin": "2018-06-29 00:00:00.0",
        "dureeReelleEnHeures": 70,
        "codePromotion": "HTSRI108",
        "idCours": "C9437277-C1A7-4822-9C4C-C9DC8CFA204C",
        "idModule": 478,
        "libelleCours": "CISCO-1",
        "dureePrevueEnHeures": 70,
        "dateAdefinir": false,
        "codeLieu": 1
      },
      {
        "debut": "2018-07-30 00:00:00.0",
        "fin": "2018-08-10 00:00:00.0",
        "dureeReelleEnHeures": 70,
        "codePromotion": "HASR_107",
        "idCours": "F4F273BF-471F-487F-8B61-474CAAEA2251",
        "idModule": 519,
        "libelleCours": "TOIP-MESSAGERIE",
        "dureePrevueEnHeures": 70,
        "dateAdefinir": false,
        "codeLieu": 1
      },
      {
        "debut": "2018-08-20 00:00:00.0",
        "fin": "2018-08-24 00:00:00.0",
        "dureeReelleEnHeures": 35,
        "codePromotion": "HASR_107",
        "idCours": "12BC3217-6F00-4855-9068-30F0E00D1CEB",
        "idModule": 317,
        "libelleCours": "NAGIOS",
        "dureePrevueEnHeures": 35,
        "dateAdefinir": false,
        "codeLieu": 1
      },
      {
        "debut": "2018-08-27 00:00:00.0",
        "fin": "2018-09-14 00:00:00.0",
        "dureeReelleEnHeures": 105,
        "codePromotion": "HASR_107",
        "idCours": "D196835A-9F7A-4742-BA90-989450AEC145",
        "idModule": 523,
        "libelleCours": "CISCO-2-3",
        "dureePrevueEnHeures": 105,
        "dateAdefinir": false,
        "codeLieu": 1
      },
      {
        "debut": "2018-10-01 00:00:00.0",
        "fin": "2018-10-05 00:00:00.0",
        "dureeReelleEnHeures": 35,
        "codePromotion": "HASR_108",
        "idCours": "5DA4D845-D665-4662-955C-2D56121F7981",
        "idModule": 625,
        "libelleCours": "PROJ-TSRIT",
        "dureePrevueEnHeures": 35,
        "dateAdefinir": false,
        "codeLieu": 1
      },
      {
        "debut": "2018-10-08 00:00:00.0",
        "fin": "2018-10-26 00:00:00.0",
        "dureeReelleEnHeures": 105,
        "codePromotion": "HASR_109",
        "idCours": "76D478D0-9F9E-491D-A39F-2E2E187DD0A6",
        "idModule": 509,
        "libelleCours": "ADMIN-SYST",
        "dureePrevueEnHeures": 105,
        "dateAdefinir": false,
        "codeLieu": 1
      },
      {
        "debut": "2018-11-12 00:00:00.0",
        "fin": "2018-11-23 00:00:00.0",
        "dureeReelleEnHeures": 70,
        "codePromotion": "HTSRI109",
        "idCours": "E18B0C3B-0AA9-40E5-8374-186877C6E37D",
        "idModule": 722,
        "libelleCours": "SRVRES-MSP",
        "dureePrevueEnHeures": 70,
        "dateAdefinir": false,
        "codeLieu": 1
      },
      {
        "debut": "2018-11-26 00:00:00.0",
        "fin": "2018-12-07 00:00:00.0",
        "dureeReelleEnHeures": 70,
        "codePromotion": "HASR_107",
        "idCours": "2FE7C23A-CB78-497F-9967-A05F7621774D",
        "idModule": 527,
        "libelleCours": "UTIL-ADMIN-BD",
        "dureePrevueEnHeures": 70,
        "dateAdefinir": false,
        "codeLieu": 1
      },
      {
        "debut": "2018-12-10 00:00:00.0",
        "fin": "2018-12-21 00:00:00.0",
        "dureeReelleEnHeures": 70,
        "codePromotion": "HASR_109",
        "idCours": "38B15181-8B6E-48AC-92FF-59558A0785F6",
        "idModule": 517,
        "libelleCours": "SCRIPT-PWSHEL",
        "dureePrevueEnHeures": 70,
        "dateAdefinir": false,
        "codeLieu": 1
      },
      {
        "debut": "2019-01-02 00:00:00.0",
        "fin": "2019-01-04 00:00:00.0",
        "dureeReelleEnHeures": 21,
        "codePromotion": "HASR_108",
        "idCours": "46220A43-1142-4A47-90CD-8C6CE6B37B3C",
        "idModule": 93,
        "libelleCours": "STAGE-ASR",
        "dureePrevueEnHeures": 280,
        "dateAdefinir": false,
        "codeLieu": 1
      },
      {
        "debut": "2019-01-07 00:00:00.0",
        "fin": "2019-01-18 00:00:00.0",
        "dureeReelleEnHeures": 70,
        "codePromotion": "HASR_108",
        "idCours": "681F4C54-31A5-4059-A608-E86B8E1ED444",
        "idModule": 529,
        "libelleCours": "ADMIN-SYST-AV",
        "dureePrevueEnHeures": 70,
        "dateAdefinir": false,
        "codeLieu": 1
      },
      {
        "debut": "2019-01-21 00:00:00.0",
        "fin": "2019-02-01 00:00:00.0",
        "dureeReelleEnHeures": 70,
        "codePromotion": "HASR_107",
        "idCours": "F76EFA16-6F3B-4ADC-9CF8-311E3B20AFF3",
        "idModule": 536,
        "libelleCours": "SERV-WEB",
        "dureePrevueEnHeures": 70,
        "dateAdefinir": false,
        "codeLieu": 1
      },
      {
        "debut": "2019-02-04 00:00:00.0",
        "fin": "2019-02-22 00:00:00.0",
        "dureeReelleEnHeures": 105,
        "codePromotion": "HASR_108",
        "idCours": "4B3E5C9C-3AB0-44A1-A6C9-53F9E0EDE219",
        "idModule": 531,
        "libelleCours": "SERV-RES-SEC",
        "dureePrevueEnHeures": 105,
        "dateAdefinir": false,
        "codeLieu": 1
      },
      {
        "debut": "2019-02-25 00:00:00.0",
        "fin": "2019-03-01 00:00:00.0",
        "dureeReelleEnHeures": 35,
        "codePromotion": "HASR_108",
        "idCours": "0CB5D014-8B29-4DC1-A2DA-8E7437A8F597",
        "idModule": 533,
        "libelleCours": "VIRT-ASR",
        "dureePrevueEnHeures": 35,
        "dateAdefinir": false,
        "codeLieu": 1
      },
      {
        "debut": "2019-03-04 00:00:00.0",
        "fin": "2019-03-08 00:00:00.0",
        "dureeReelleEnHeures": 35,
        "codePromotion": "HTSRI110",
        "idCours": "B08018B1-8B5E-4CAD-B6D5-991D3039708F",
        "idModule": 513,
        "libelleCours": "VIRT-TSRIT",
        "dureePrevueEnHeures": 35,
        "dateAdefinir": false,
        "codeLieu": 1
      },
      {
        "debut": "2019-03-11 00:00:00.0",
        "fin": "2019-03-15 00:00:00.0",
        "dureeReelleEnHeures": 35,
        "codePromotion": "HTSRI110",
        "idCours": "902A5C40-FB6C-48BD-8655-5FF56BD06FD8",
        "idModule": 515,
        "libelleCours": "GPROJ-TSRIT",
        "dureePrevueEnHeures": 35,
        "dateAdefinir": false,
        "codeLieu": 1
      },
      {
        "debut": "2019-03-18 00:00:00.0",
        "fin": "2019-05-10 00:00:00.0",
        "dureeReelleEnHeures": 259,
        "codePromotion": "HASR_108",
        "idCours": "77E75ED7-475A-4ED4-B4A3-FF9C2849215A",
        "idModule": 93,
        "libelleCours": "STAGE-ASR",
        "dureePrevueEnHeures": 280,
        "dateAdefinir": false,
        "codeLieu": 1
      }
    ]
  }
]
```

### Constraints

#### Création de contraintes

Création de contraintes.

| Méthode | Route        | Paramètres                                               | Optionnel | Valeur défaut |
| ------- | ------------ | -------------------------------------------------------- | --------- | ------------- |
| POST    | /constraints | id: String                                               | Non       |               |
|         |              | place: ConstraintPriorityInteger                         | Oui       |               |
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
  "idConstraint": "67b7ef92-af36-41cf-902b-5671a7eb53f5",
  "place": {
    "value": 1,
    "id": "78c8fg03-bg47-52dg-013c-6782b8fc64g6"
  }
}
```

Exemple de retour :

```json
{
  "idConstraint": "67b7ef92-af36-41cf-902b-5671a7eb53f5",
  "place": {
    "value": 1,
    "id": "78c8fg03-bg47-52dg-013c-6782b8fc64g6"
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
      "value": 1,
      "id": "78c8fg03-bg47-52dg-013c-6782b8fc64g6"
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
    "value": 1,
    "id": "78c8fg03-bg47-52dg-013c-6782b8fc64g6"
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

### ModulesPrerequis

Création de prerequis pour un module.

| Méthode | Route               | Paramètres                         | Optionnel | Valeur défaut |
| ------- | ------------------- | ---------------------------------- | --------- | ------------- |
| POST    | /modulesPrerequis   | idModulePrerequis: String          | Non       |               |
|         |                     | idModule: Int                      | Non       |               |
|         |                     | idModuleObligatoire: Seq[Int]      | Oui       | Liste vide    |
|         |                     | idModuleOpionnel: Seq[Int]         | Oui       | Liste vide    |
|         |                     | titre: String                      | Non       |               |
|         |                     | description: String                | Non       |               |

Exemple d'entrée : POST https://myServer/modulesPrerequis

```json
{
	"idModulePrerequis": "1234567890",
	"idModule": 731,
    "idModuleObligatoire": [
    	735,
    	738
    	],
    "idModuleOpionnel": [
    	739
    	],
    "titre": "Titre pour le module 731",
    "description": "Description pour le module"
}
```

Exemple de retour :

```json
TODO
```

### ModulesPrerequisPlanning

Création de prerequis pour un module.

| Méthode | Route               | Paramètres                         | Optionnel | Valeur défaut |
| ------- | ------------------- | ---------------------------------- | --------- | ------------- |
| POST    | /modulesPrerequis   | idModulePrerequisPlanning: String  | Non       |               |
|         |                     | idModulePrerequis: Seq[Int]        | Non       |               |
|         |                     | titre: String                      | Non       |               |
|         |                     | description: String                | Non       |               |

Exemple d'entrée : POST https://myServer/modulesPrerequisPlanning

```json
{
	"idModulePrerequisPlanning": "qwerty",
	"idModulePrerequis": [
		"1234567890"
		],
    "titre": "Titre pour la liste des modulesPrerequis",
    "description": "Description pour la liste des modulesPrerequis"
}
```

Exemple de retour :

```json
TODO
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
