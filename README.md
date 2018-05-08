# CalGeneratorBack


## Description du projet

## Installation

## Documentation de l'API

### UniteFormation

| Méthode | Route | Paramètre | Retour | Exemple | 
|---------|-------|-----------|------- |---------|
| GET | /uniteFormations | Aucun | Liste l'ensemble de uniteFormation présent en base de données | 1 |
| GET | /uniteFormations/:idUniteFormation | idUniteFormation : identifiant du uniteFormation souhaité | Affiche les détails du uniteFormation souhaité | 2 |

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

### Cours

| Méthode | Route | Paramètre | Retour | Exemple | 
|---------|-------|-----------|------- |---------|
| Get | /cours | Aucun | Liste l'ensemble des cours présent en base de données | 1 |
| Get | /cours/:idCours | idCours : identifiant du cours souhaité | Affiche les détails du cours souhaité | 2 |

Exemple 1 :

```json
[
  {
      "debut": "2018-08-20 00:00:00.0",
      "fin": "2018-08-24 00:00:00.0",
      "dureeReelleEnHeures": 35,
      "codePromotion": "ACDI_001",
      "idCours": "CDF20F3C-94D4-4069-A640-00E3100FDAE1",
      "prixPublicAffecte": 0,
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
      "prixPublicAffecte": 0,
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
    "prixPublicAffecte": 0,
    "idModule": 541,
    "libelleCours": "DVWEBCL",
    "dureePrevueEnHeures": 35,
    "dateAdefinir": false,
    "codeSalle": null,
    "codeFormateur": 0,
    "codeLieu": 11
}
```



### Entrepirse

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



### Formation

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



### Lieu

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