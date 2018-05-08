package models

import java.time.Instant

case class Probleme (
                        periodeFormation : Periode,
                        formation: Formation,
                        cours: Seq[Cours],
                        contraintes : Seq[Contrainte]

                        // Partie contrainte

                      )