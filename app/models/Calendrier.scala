package models

import java.time.Instant

case class Calendrier (
                        periode: Periode,
                        formation: Formation,
                        lieu : Lieu,
                        cours : Seq[Cours],
                        contrainteResolu : Seq[Contrainte],
                        contrainteNonResolu : Seq[Contrainte]
                      )