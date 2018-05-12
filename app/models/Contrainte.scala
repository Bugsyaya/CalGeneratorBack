package models

case class Contrainte (

                        lieux: Seq[Lieu],
                        nbHeureAnnuel: Int = 0,
                        dureeMaxFormation : Int = 0,
                        maxSemaineFormation: Int = 0,
                        maxStagiaireEnFormationParCours: Int = 0,

                        periodeFormationExclusion : Seq[Periode],
                        periodeFormationInclusion : Seq[Periode]

                        // todo
                        // Stagiaires dont la présence est requise
                        // une autre case class stagaire avec le calendrier qui la liste des cours à suivre?
                        //
                      )
