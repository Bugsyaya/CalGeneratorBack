package models

import java.time.Instant

case class Probleme (
                        // ces instant ne seront pas utiliser au travers de l'API, on peut les garder ? stp
                        debutFormation: Instant = Instant.now(),
                        finFormation: Instant = Instant.now(),
                        formation: Formation,
                        cours: Seq[Cours],
                        // Partie contrainte
                        lieux: Seq[Lieu],
                        nbHeureAnnuel: Int = 0,
                        dureeMaxFormation : Int = 0,
                        maxSemaineFormation: Int = 0,
                        maxStagiaireEnFormationParCours: Int = 0
                        // todo
                        // Il reste périodes de formation à exclure
                        // Peut être une autre liste d'une case class avec date de début et date de fin
                        // Période de formation obligatoire, peut-être la même case class
                        // Stagiaires dont la présence est requise
                        // une autre case class stagaire avec le calendrier qui la liste des cours à suivre?
                        //
                      )