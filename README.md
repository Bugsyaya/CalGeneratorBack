# CalGeneratorBack


## Description du projet

## Installation

## Documentation de l'API

### Cours

| Méthode | Route | Paramètre | Retour | Exemple | 
|---------|-------|-----------|------- |---------|
| Get | /cours | Aucun | Liste l'ensemble de `cours` présent en base de données | 1 |
| Get | /cours/:idCours | idCours : String (identifiant de `cours` souhaité) | Affiche les détails de `cours` souhaité | 2 |

Exemple 1 :

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

Exemple 2 :

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

### Entreprise

| Méthode | Route | Paramètre | Retour | Exemple | 
|---------|-------|-----------|------- |---------|
| Get | /entreprises | Aucun | Liste l'ensemble de `entreprise` présent en base de données | 1 |
| Get | /entreprises/:codeEntreprise | codeEntreprise : Int (identifiant de `entreprise` souhaité) | Affiche les détails de `entreprise` souhaité | 2 |

Exemple 1 :

```json
[]
```

Exemple 2 :

```json
{
  "todo": "TODO"
}
```

### Formation

| Méthode | Route | Paramètre | Retour | Exemple | 
|---------|-------|-----------|------- |---------|
| Get | /formations | Aucun | Liste l'ensemble de `formation` présent en base de données | 1 |
| Get | /formations/:codeFormation | codeEntreprise : String (identifiant de `formation` souhaité) | Affiche les détails de `formation` souhaité | 2 |

Exemple 1 :

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

Exemple 2 :

```json
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
}
```

### UniteFormation

| Méthode | Route | Paramètre | Retour | Exemple | 
|---------|-------|-----------|------- |---------|
| GET | /uniteFormations | Aucun | Liste l'ensemble de `uniteFormation` présent en base de données | 1 |
| GET | /uniteFormations/:idUniteFormation | idUniteFormation : Int (identifiant du `uniteFormation` souhaité) | Affiche les détails du uniteFormation souhaité | 2 |

Exemple 1 :

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

Exemple 2 :

```json
{
  "libelle": "Développement Java SOPRA-STERIA (2017)",
  "dureeEnHeures": 280,
  "dureeEnSemaines": 8,
  "libelleCourt": "17SOPJAV",
  "idUniteFormation": 1
}
```



### Module

| Méthode | Route | Paramètre | Retour | Exemple | 
|---------|-------|-----------|------- |---------|
| Get | /modules | Aucun | Liste l'ensemble de `module` présent en base de données | 1 |
| Get | /modules/:idModule | idModule : Int (identifiant du `module` souhaité) | Affiche les détails du `module` souhaité | 2 |

Exemple 1 :

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

Exemple 2 :

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



### Salle

| Méthode | Route | Paramètre | Retour | Exemple | 
|---------|-------|-----------|------- |---------|
| Get | /salles | Aucun | Liste l'ensemble de `salle` présent en base de données | 1 |
| Get | /salles/:codeSalle | codeSalle : String (identifiant du `salle` souhaité) | Affiche les détails de `salle` souhaité | 2 |

Exemple 1 :

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

Exemple 2 :

```json
{
  "codeSalle": "0001",
  "libelle": "Salle 1",
  "capacite": 13,
  "lieu": 4
}
```



### Lieu

| Méthode | Route | Paramètre | Retour | Exemple | 
|---------|-------|-----------|------- |---------|
| Get | /lieux | Aucun | Liste l'ensemble de `lieu` présent en base de données | 1 |
| Get | /lieux/:codeLieu | codeLieu : Int (identifiant de `lieu` souhaité) | Affiche les détails du `lieu` souhaité | 2 |

Exemple 1 :

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

Exemple 2 :

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




### Module

| Méthode | Route | Paramètre | Retour | Exemple | 
|---------|-------|-----------|------- |---------|
| Get | /uniteFormations | Aucun | Liste l'ensemble de uniteFormation présent en base de données | 1 |
| Get | /uniteFormations/:idUniteFormation | idUniteFormation : identifiant du uniteFormation souhaité | Affiche les détails du uniteFormation souhaité | 2 |

Exemple 1 :

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

Exemple 2 :

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



### Salle

| Méthode | Route | Paramètre | Retour | Exemple | 
|---------|-------|-----------|------- |---------|
| Get | /uniteFormations | Aucun | Liste l'ensemble de uniteFormation présent en base de données | 1 |
| Get | /uniteFormations/:idUniteFormation | idUniteFormation : identifiant du uniteFormation souhaité | Affiche les détails du uniteFormation souhaité | 2 |

Exemple 1 :

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

Exemple 2 :

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

